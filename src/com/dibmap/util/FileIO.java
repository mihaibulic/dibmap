package com.dibmap.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileIO
{
	private File file;
	
	public FileIO(File file)
	{
		this.file = file;
	}

	public FileIO(String file)
	{
		this.file = new File(file);
	}
	
	public boolean write(String msg)
	{
		return write(msg, false);
	}
	
	public boolean write(String msg, boolean append)
	{
        //try to make a filewriter, if an exception is caught the method stops
		boolean success = true;
        FileWriter f = null;
        try
        {
            f = new FileWriter(file, append); // do not append
            BufferedWriter out = new BufferedWriter(f);
            
            try
            {
            	out.write(msg);
            }
            catch (IOException e)
            {
            	success = false;
            	e.printStackTrace();
            }
            finally
            {
            	try
            	{
            		out.close();
            	}
            	catch (IOException e)
            	{
            		success = false;
            		e.printStackTrace();
            	}
            }
        }
        catch (IOException e)
        {
        	success = false;
            e.printStackTrace();
        }

        return success;
	}
	
	public String read()
	{
		StringBuilder sb = new StringBuilder();
        FileReader f = null;
        try
        {
            f = new FileReader(file); 
            BufferedReader in = new BufferedReader(f);
            
            try
            {
            	while(in.ready())
            	{
            		sb.append(in.readLine());
            	}
            }
            catch (IOException e)
            {
            	e.printStackTrace();
            }
            finally
            {
            	try
            	{
            		in.close();
            	}
            	catch (IOException e)
            	{
            		e.printStackTrace();
            	}
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        
        return sb.toString();
	}
	
}
