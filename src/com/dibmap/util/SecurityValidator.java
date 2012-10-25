package com.dibmap.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

// <% if(!Validation.isLoggedIn(session)) request.follow("Index"); %>

public class SecurityValidator
{
	private DB db;
	
	public SecurityValidator(ServletContext sc)
	{
		db = (DB)sc.getAttribute("DB");
	}
	
	public static void inValidate(HttpServletRequest request)
	{
		inValidate(request.getSession(false));
	}

	public static void inValidate(HttpSession session)
	{
		if(session != null)
		{
			session.invalidate();
		}
	}
	
	public static boolean isLoggedIn(HttpServletRequest request)
	{
		return isLoggedIn(request.getSession(false));
	}
	
	public static boolean isLoggedIn(HttpSession session)
	{
		boolean isLoggedIn = false;
		if(session != null)
		{
			String logged = (String)session.getAttribute("logged");
			if(logged != null && logged.equals("in"))
			{
				isLoggedIn = true;
			}
		}
		
		return isLoggedIn;
	}
	
	public static boolean verify(String pw, byte[] correctHash, String salt) throws NoSuchAlgorithmException, UnsupportedEncodingException
	{
		try
		{
			Thread.sleep(1000); //slow down
		} catch (InterruptedException e)
		{
			e.printStackTrace();
		} 
		
		byte[] hash = getHash(pw,salt);
		boolean equal = (hash.length == correctHash.length);
		
		for(int x = 0; equal && x < hash.length; x++)
		{
			equal = (hash[x] == correctHash[x]);
		}
		
		return equal;
	}

	public static byte[] getHash(String pw) throws NoSuchAlgorithmException, UnsupportedEncodingException
	{
		return getHash(pw, getRandomKey(64));
	}
	
	public static byte[] getHash(String pw, String salt) throws NoSuchAlgorithmException, UnsupportedEncodingException
	{
		int itterations = 5000;
        MessageDigest digest = MessageDigest.getInstance("SHA-1");

        digest.reset();
        digest.update(salt.getBytes("UTF-8"));
        digest.update(pw.getBytes("UTF-8"));
        byte input[] = digest.digest();
        for (int i = 1; i < itterations; i++) 
        {
            input = digest.digest(input);
        }
        
        return input;
	}

	public static String getRandomKey(int length)
	{
		StringBuilder sb = new StringBuilder(System.currentTimeMillis() + "");
		SecureRandom random = new SecureRandom();
		
		if(length < sb.length())
		{
			throw new RuntimeException("The length for this key was set to " + length + 
					" while the minimum is " + sb.length());
		}
		
		while(sb.length() < length)
		{
			sb.append((char)(random.nextInt(26) + 'a'));
		}
		
		if(sb.length() > length)
		{
			sb.substring(0, length);
		}

		return sb.toString();
	}
	
	public boolean login(HttpServletRequest request, String email, String password) throws UnsupportedEncodingException
	{
		String salt;
		byte[] hash;
		boolean isGood = false;
		
		inValidate(request);	
		
		String sql = "SELECT salt, pwd_hash, party_id, confirmed FROM users WHERE email = ? LIMIT 1";
		Connection conn = db.getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try
		{
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, email);
			rs = stmt.executeQuery();
			
			if(rs.next() && rs.getBoolean("confirmed")) // good
			{
				salt = rs.getString("salt");
				hash = rs.getBytes("pwd_hash");
				int id = rs.getInt("party_id");
				
				if(SecurityValidator.verify(password, hash, salt)) // good
				{
					rs.close();
					stmt.close();
					
					sql = "SELECT name FROM parties WHERE id = ? LIMIT 1";
					stmt = conn.prepareStatement(sql);
					stmt.setInt(1, id);
					rs = stmt.executeQuery();
					
					if(rs.next())
					{
						HttpSession session = request.getSession();
						session.setAttribute("logged", "in");
						session.setAttribute("party_id", id);
						session.setAttribute("name", rs.getString("name"));
						isGood = true;
					}
				}
			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e)
		{
			e.printStackTrace();
		}
		finally
		{
			db.closeConnection(conn, stmt, rs);
		}
		
		return isGood;
	}
	
	public static void logout(HttpServletRequest request)
	{
		inValidate(request);
	}
}
