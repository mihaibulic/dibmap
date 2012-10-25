package com.dibmap.controllers.home;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dibmap.util.SecurityValidator;

public class Logout extends HttpServlet 
{
	private static final long serialVersionUID = 1L;

	public Logout() 
	{
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		SecurityValidator.logout(request);
		response.sendRedirect("/open/home/welcome.jsp");
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		doGet(request, response);
	}
}
