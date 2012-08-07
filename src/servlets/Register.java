package servlets;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import util.DB;
import util.FileIO;
import util.MailSender;
import util.Validation;

public class Register extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	private DB db;
	private String root;
	
    public Register()
    {
    	super();
    }
    
    public void init()
    {
    	db = (DB)getServletContext().getAttribute("DB");
    	root = getServletContext().getInitParameter("root");
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		String email = request.getParameter("email");
		String name = request.getParameter("name");
		String pw = request.getParameter("password");
		String description = request.getParameter("description");
		
		try
		{
			addUser(email, name, pw, description);
		} catch (NoSuchAlgorithmException e)
		{
			e.printStackTrace();
		}
		
		response.sendRedirect("index.jsp");
	}

	private boolean addUser(String email, String name, String password, String description) throws NoSuchAlgorithmException, UnsupportedEncodingException
	{
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		boolean isDbHealthy = true;
		boolean success = false;
    	
		try
		{
			conn = db.getConnection();

			String insertParty = "INSERT INTO parties (name) VALUES (?)";
			stmt = conn.prepareStatement(insertParty, PreparedStatement.RETURN_GENERATED_KEYS);
			stmt.setString(1, name);
			
			if(stmt.executeUpdate() > 0) //successfully created new party
			{
				rs = stmt.getGeneratedKeys();
				if(rs.next()) // A primary key was returned
				{
					int partyId = rs.getInt(1); 
					String salt = Validation.getRandomKey(64);
					byte[] pwdHash = Validation.getHash(password, salt);
					String regKey = Validation.getRandomKey(32);

					String insertUser = "INSERT INTO users (email, pwd_hash, salt, party_id, reg_key) VALUES (?, ?, ?, ?, ?)";
					stmt = conn.prepareStatement(insertUser);
					stmt.setString(1, email);
					stmt.setBytes(2, pwdHash);
					stmt.setString(3, salt);
					stmt.setInt(4, partyId);
					stmt.setString(5, regKey);
					
					if(stmt.executeUpdate() <= 0) // could not add user, roll back
					{
						String removeParty = "DELETE FROM parties WHERE party_id = ?";
						stmt = conn.prepareStatement(removeParty);
						stmt.setInt(1, partyId);
						isDbHealthy = stmt.executeUpdate() > 0; // ensure party was deleted
					}
					else
					{
						FileIO output = new FileIO(new File(root + File.separator + "parties" + 
															File.separator + partyId + ".desc"));
						success = output.write(description);
						
						if(success)
						{
							ServletContext sc = this.getServletContext();
							MailSender ms = new MailSender(sc);
							
							String body = "Welcome to dibmap " + name + 
									"!  Click on the link below to confirm your registration:\n\n\t"+
									sc.getInitParameter("url") + "/" + sc.getInitParameter("confirm") + "/" + regKey;
							
							ms.sendEmail(email, "dibmap confirmation", body);
							System.out.println("email sent");
						}
					}
				}
			}
			
			System.out.println( isDbHealthy ? "DB intact" : "DB failure");
			System.out.println( success ? "user added :)" : "user addition failure");
		} 
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			db.closeConnection(conn, stmt, rs);
		}
		
		return isDbHealthy;
	}
}
