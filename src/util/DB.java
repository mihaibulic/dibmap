package util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletContext;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.apache.tomcat.jdbc.pool.PoolProperties;

public class DB
{
	private DataSource datasource = null;
	
	public DB(ServletContext sc)
	{
		String url = (String)(sc.getInitParameter("dbUrl"));
		String driver = (String)(sc.getInitParameter("dbDriver"));
		String name = (String)(sc.getInitParameter("dbName"));
		String user = (String)(sc.getInitParameter("dbUser"));
		String pass = (String)(sc.getInitParameter("dbPass"));
		
		PoolProperties p = new PoolProperties();
		p.setUrl(url + name);
		p.setDriverClassName(driver);
		p.setUsername(user);
		p.setPassword(pass);
		p.setJmxEnabled(true);
		p.setTestWhileIdle(false);
		p.setTestOnBorrow(true);
		p.setValidationQuery("SELECT 1");
		p.setTestOnReturn(false);
		p.setValidationInterval(30000);
		p.setTimeBetweenEvictionRunsMillis(30000);
		p.setMaxActive(100);
		p.setInitialSize(10);
		p.setMaxWait(10000);
		p.setRemoveAbandonedTimeout(60);
		p.setMinEvictableIdleTimeMillis(30000);
		p.setMinIdle(10);
		p.setLogAbandoned(true);
		p.setRemoveAbandoned(true);
		p.setJdbcInterceptors("org.apache.tomcat.jdbc.pool.interceptor.ConnectionState;"
							+ "org.apache.tomcat.jdbc.pool.interceptor.StatementFinalizer");
		datasource = new DataSource();
		datasource.setPoolProperties(p);
	}
	
	public Connection getConnection()
	{
		Connection conn = null;
		try
		{
			conn = datasource.getConnection();
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		
		return conn;
	}
	
    public void closeConnection(Connection conn, PreparedStatement stmt, ResultSet rs)
    {
    	closeResultSet(rs);
		closeStatement(stmt);
        closeConnection(conn);
    }
    
    public void closeConnection(Connection conn)
    {
        if (conn != null) 
        {
            try 
            {
                conn.close();
            } catch (SQLException e) 
            {
                e.printStackTrace();
            }

            conn = null;
        }
    }
    
    public void closeStatement(PreparedStatement stmt)
    {
        if (stmt != null) 
        {
            try 
            {
                stmt.close();
            } catch (SQLException e) 
            {
                e.printStackTrace();
            }

            stmt = null;
        }
    }
    
    public void closeResultSet(ResultSet rs)
    {
        if (rs != null) 
        {
            try 
            {
                rs.close();
            } catch (SQLException e) 
            {
                e.printStackTrace();
            }

            rs = null;
        }
    }
}
