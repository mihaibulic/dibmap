package com.dibmap.controllers.home;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dibmap.util.SecurityValidator;

public class Login extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
    private SecurityValidator validate;   
	
    public Login() 
    {
        super();
    }

    public void init()
    {
    	validate = new SecurityValidator(getServletContext());
    }
    
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		
		if(validate.login(request, email, password))
		{
//			response.sendRedirect("/home/index.jsp");
			request.getRequestDispatcher("/home/index.jsp").include(request, response);
		}
		else
		{
//			response.sendRedirect("/open/home/welcome.jsp?error=invalid");
			request.getRequestDispatcher("/open/home/welcome.jsp?error=invalid").include(request, response);
		}	
	}
}
