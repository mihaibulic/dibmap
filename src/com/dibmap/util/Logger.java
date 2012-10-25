package com.dibmap.util;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger
{
	FileIO io;
	
	public Logger()
	{
		this(new File(File.separator + "tmp" + File.separator + "DibmapLog.txt"));
	}

	public Logger(String file)
	{
		this(new File(file));
	}
	
	//primary constructor
	public Logger(File file)
	{
		io = new FileIO(file);
	}
	
	public void log(String msg)
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();

        io.write(dateFormat.format(date) + "\t" + msg + "\n", true);
	}
	
	public static void main(String[] args)
	{
		new Logger(); 
	}
}
