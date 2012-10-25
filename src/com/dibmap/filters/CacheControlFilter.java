package com.dibmap.filters;

import java.io.IOException;
import java.util.Date;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

public class CacheControlFilter implements Filter 
{
	private String modTime = new Date().toString();
	
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException 
    {
        HttpServletResponse resp = (HttpServletResponse) response;
        
        resp.setHeader("Last-Modified", modTime);
        resp.setHeader("Cache-Control", "private, must-revalidate, max-age=0");
        chain.doFilter(request, resp);
    }

//    private long getSeconds(int numOfDays)
//    {
//    	return numOfDays*24*60*60;
//    }
    
	@Override
	public void destroy()
	{}

	@Override
	public void init(FilterConfig fc) throws ServletException
	{}
}