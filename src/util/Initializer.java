package util;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class Initializer implements ServletContextListener
{
	public void contextInitialized(ServletContextEvent e)
	{
		ServletContext sc = e.getServletContext();
		sc.setAttribute("DB", new DB(sc));
	}

	public void contextDestroyed(ServletContextEvent e)
	{}
}