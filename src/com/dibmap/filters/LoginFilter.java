package com.dibmap.filters;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dibmap.util.SecurityValidator;


public class LoginFilter implements Filter 
{
	private ArrayList<String> openFolders;
	
	public void init(FilterConfig fConfig) throws ServletException 
	{
		openFolders = new ArrayList<String>();
		openFolders.add("/scripts/");
		openFolders.add("/css/");
		openFolders.add("/images/");
		openFolders.add("/support/");
		openFolders.add("/open/");
		openFolders.add("/errors/");
	}
	
	public void destroy() 
	{}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException 
	{
		HttpServletRequest req = (HttpServletRequest)request;
		HttpServletResponse res = (HttpServletResponse)response;
		
		if(!SecurityValidator.isLoggedIn(req))
		{
			String path = req.getServletPath();
			boolean auth = false;
			for(String folder : openFolders)
			{
				if(path.startsWith(folder))
				{
					auth = true;
					break;
				}
			}
			
			if(!auth)
				res.sendRedirect("/open/home/welcome.jsp");
			else
				chain.doFilter(request, response);
		}
		else
			chain.doFilter(request, response);
	}
}

