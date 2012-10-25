package com.dibmap.models;

import java.util.ArrayList;

import javax.servlet.ServletContext;

public class Activities extends Model
{
	public Activities(ServletContext sc)
	{
		super(sc);
	}

	public ArrayList<String> add(int id, int activity, int location, int intensity, String startTime, String endTime)
	{
		ArrayList<String> errors = new ArrayList<String>();
		
		return errors;
	}
}
