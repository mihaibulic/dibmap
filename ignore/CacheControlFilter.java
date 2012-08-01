package filters;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

public class CacheControlFilter implements Filter 
{
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException 
    {
        HttpServletResponse resp = (HttpServletResponse) response;
        
        resp.setHeader("Last-Modified", new Date().toString());
        resp.setHeader("Cache-Control", "must-revalidate, max-age="+System.currentTimeMillis());

        chain.doFilter(request, response);
    }

	@Override
	public void destroy()
	{}

	@Override
	public void init(FilterConfig arg0) throws ServletException
	{}

}