package filters;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

public class CacheControlFilter implements Filter 
{
	private String modTime = new Date().toString();
	
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException 
    {
        HttpServletResponse resp = (HttpServletResponse) response;
        
        resp.setHeader("Last-Modified", modTime);
        resp.setHeader("Cache-Control", "public, must-revalidate, max-age=0");
        chain.doFilter(request, resp);
    }

    private long getSeconds(int numOfDays)
    {
    	return numOfDays*24*60*60;
    }
    
	@Override
	public void destroy()
	{}

	@Override
	public void init(FilterConfig fc) throws ServletException
	{}
}