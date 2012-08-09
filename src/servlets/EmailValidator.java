package servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import util.DB;

public class EmailValidator extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,4})$";
	private Pattern pattern;
	private DB db;
	
    public EmailValidator()
    {
    	super();
    }
    
    public void init()
    {
    	db = (DB)getServletContext().getAttribute("DB");
    	pattern = Pattern.compile(EMAIL_PATTERN);
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		String email = request.getParameter("email");
		String status = "ok";
		if(!pattern.matcher(email).matches())
		{
			status = "invalid";
		}
		else if(!isAvailable(email))
		{
			status = "used";
		}
		
		response.setHeader("Cache-Control", "no-cache");
		response.setContentType("text/html");
		response.getWriter().write(status);
	}
	
	private boolean isAvailable(String email)
	{
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		boolean available = true;
    	
		try
		{
			String query = "SELECT * FROM users WHERE email=? LIMIT 1;";
			conn = db.getConnection();
			stmt = conn.prepareStatement(query);
			stmt.setString(1, email);
			rs = stmt.executeQuery();
			available = !rs.next();
		} 
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		db.closeConnection(conn, stmt, rs);
		
		return available;
	}

}
