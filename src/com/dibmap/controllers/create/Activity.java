package com.dibmap.controllers.create;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dibmap.models.Activities;

public class Activity extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	private Activities activities;   
	
    public Activity() 
    {
        super();
    }
    
    public void init()
    {
    	activities = new Activities(getServletContext());
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		int id = Integer.parseInt(request.getParameter("creator")); 
		int activity = Integer.parseInt(request.getParameter("activity")); 
		int location = Integer.parseInt(request.getParameter("location"));
		int intensity = Integer.parseInt(request.getParameter("intensity")); 
		String startTime = request.getParameter("start_time");
		String endTime = request.getParameter("end_time");
		
		ArrayList<String> errors = activities.add(id, activity, location, intensity, startTime, endTime);
		if(!errors.isEmpty())
		{
			request.setAttribute("errors", errors);
		}
		
		request.getRequestDispatcher("/create/activity.jsp").forward(request, response);
	}
}
