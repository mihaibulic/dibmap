package com.dibmap.controllers.create;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dibmap.models.Users;

public class Confirm extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
    private Users users;   
	
    public Confirm() 
    {
        super();
    }

    public void init()
    {
    	users = new Users(getServletContext());
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		String email = request.getParameter("email");
		String regKey = request.getParameter("key");	
		boolean confirmed = users.confirm(email, regKey);
		
		request.setAttribute("confirmed", confirmed?"yes":"no");
		response.sendRedirect("/open/create/confirmed.jsp");
	}
}
