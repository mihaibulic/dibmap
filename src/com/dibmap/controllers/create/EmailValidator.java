package com.dibmap.controllers.create;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dibmap.util.FormValidator;

public class EmailValidator extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	private FormValidator validate;
	
    public EmailValidator()
    {
    	super();
    }
    
    public void init()
    {
    	validate = new FormValidator(getServletContext());
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		String email = request.getParameter("email");
		String status = "ok";
		if(!FormValidator.isValidEmail(email))
		{
			status = "invalid";
		}
		else if(!validate.isEmailAvailable(email))
		{
			status = "used";
		}
		
		response.setHeader("Cache-Control", "no-cache");
		response.setContentType("text/html");
		response.getWriter().write(status);
	}
}
