package com.dibmap.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Pattern;

import javax.servlet.ServletContext;

public class FormValidator
{
	private DB db;
//	private ServletContext sc;
	
	public FormValidator(ServletContext sc)
	{
//		this.sc = sc;
		db = (DB)sc.getAttribute("DB");
	}
	
	public static boolean isValidName(String name)
	{
		return name != null && !name.isEmpty() && !name.matches("[Nn]ame");
	}
	
	public static boolean isValidEmail(String email)
	{
		Pattern pattern = Pattern.compile("^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,4})$");
		
		return email != null && pattern.matcher(email).matches();
	}
	
	public static boolean isValidPassword(String password)
	{
		return password != null && password.length() >= 6 && !password.matches("[Pp]assword");
	}
	
	public boolean isEmailAvailable(String email)
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
		finally
		{
			db.closeConnection(conn, stmt, rs);
		}
		
		return available;
	}
}
