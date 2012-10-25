package com.dibmap.models;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletContext;

import com.dibmap.util.FileIO;
import com.dibmap.util.FormValidator;
import com.dibmap.util.MailSender;
import com.dibmap.util.SecurityValidator;

public class Users extends Model
{
	public Users(ServletContext sc)
	{
		super(sc);
	}
	
	public ArrayList<String> add(String email, String name, String password, String description) throws NoSuchAlgorithmException, UnsupportedEncodingException
	{
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		ArrayList<String> errors = new ArrayList<String>();

		if (!FormValidator.isValidEmail(email))
			errors.add("Invalid Email");
		
		if (!FormValidator.isValidName(name))
			errors.add("Invalid Name");
		
		if (!FormValidator.isValidPassword(password))
			errors.add("Invalid Password");
		
		if(errors.isEmpty())
		{
			try
			{
				conn = db.getConnection();

				String query = "SELECT * FROM users WHERE email=? LIMIT 1;";
				conn = db.getConnection();
				stmt = conn.prepareStatement(query);
				stmt.setString(1, email);
				rs = stmt.executeQuery();
				
				if(rs.next())
				{
					errors.add("Email address already in use");
				}
				else
				{
					String insertParty = "INSERT INTO parties (name) VALUES (?)";
					stmt = conn.prepareStatement(insertParty,
							PreparedStatement.RETURN_GENERATED_KEYS);
					stmt.setString(1, name);
	
					if (stmt.executeUpdate() > 0) // successfully created new party
					{
						rs = stmt.getGeneratedKeys();
						if (rs.next()) // A primary key was returned
						{
							int partyId = rs.getInt(1);
							String salt = SecurityValidator.getRandomKey(64);
							byte[] pwdHash = SecurityValidator.getHash(password, salt);
							String regKey = SecurityValidator.getRandomKey(32);
	
							String insertUser = "INSERT INTO users (email, pwd_hash, salt, party_id, reg_key) VALUES (?, ?, ?, ?, ?)";
							stmt = conn.prepareStatement(insertUser);
							stmt.setString(1, email);
							stmt.setBytes(2, pwdHash);
							stmt.setString(3, salt);
							stmt.setInt(4, partyId);
							stmt.setString(5, regKey);
	
							if (stmt.executeUpdate() <= 0) // could not add user, roll back
							{
								String removeParty = "DELETE FROM parties WHERE party_id = ?";
								stmt = conn.prepareStatement(removeParty);
								stmt.setInt(1, partyId);
								
								// ensure party was deleted
								if(stmt.executeUpdate() == 0) 
									errors.add("DB Failure");
							} else
							{
								FileIO output = new FileIO(new File(root
										+ File.separator + "parties"
										+ File.separator + partyId + ".desc"));
	
								if (output.write(description))
								{
									MailSender ms = new MailSender(sc);
	
									String body = "Welcome to dibmap "
											+ name
											+ "!  Click on the link below to confirm your registration:\n\n\t"
											+ sc.getInitParameter("url")
											+ sc.getInitParameter("confirm")
											+ "?key=" + regKey + "&email=" + email;
	
									ms.sendEmail(email, "dibmap confirmation", body);
								}
								else
								{
									errors.add("User addition failed");
								}
							}
						}
					}
				}
			} catch (SQLException e)
			{
				e.printStackTrace();
			}
			finally
			{
				db.closeConnection(conn, stmt, rs);
			}
		}
		
		return errors;
	}

	public boolean confirm(String email, String regKey)
	{
		Connection conn = db.getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		boolean confirmed = false;
		String sql = "SELECT count(*) FROM users WHERE email = ? AND reg_key = ? LIMIT 1";
		
		try
		{
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, email);
			stmt.setString(2, regKey);
			rs = stmt.executeQuery();
			
			if(rs.next()) //good
			{
				sql = "UPDATE users SET confirmed = TRUE WHERE email = ?";
				stmt = conn.prepareStatement(sql);
				stmt.setString(1, email);
				confirmed = stmt.executeUpdate() > 0;
			}
			
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			db.closeConnection(conn, stmt, rs);
		}
		
		return confirmed;
	}
}

