package com.dibmap.util;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class Initializer implements ServletContextListener
{
	private DB db;
	
	public void contextInitialized(ServletContextEvent e)
	{
		ServletContext sc = e.getServletContext();
		
		db = new DB(sc);
		sc.setAttribute("DB", db);
	}

	public void contextDestroyed(ServletContextEvent e)
	{
		db.destroyAll();
	}
}