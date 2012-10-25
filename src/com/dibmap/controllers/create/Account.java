package com.dibmap.controllers.create;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.dibmap.models.Users;

public class Account extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	private Users users;
	
    public Account()
    {
    	super();
    }
    
    public void init()
    {
    	users = new Users(getServletContext());
    }
    
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		try
		{
			ArrayList<String> errors = users.add(request.getParameter("email"), 
							 				request.getParameter("name"), 
					 						request.getParameter("password"), 
					 						request.getParameter("description"));

			if(errors.isEmpty())
			{
				request.setAttribute("message", "Check your email to confirm your account");
			}
			else
			{
				request.setAttribute("errors", errors);
			}
			
			request.getRequestDispatcher("/open/home/welcome.jsp").forward(request, response);
		} catch (NoSuchAlgorithmException e)
		{
			e.printStackTrace();
		}
	}
}
