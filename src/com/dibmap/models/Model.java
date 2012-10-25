package com.dibmap.models;

import javax.servlet.ServletContext;
import com.dibmap.util.DB;

public class Model
{
	ServletContext sc;
	DB db;
	String root;

	public Model(ServletContext sc)
	{
		db = (DB) sc.getAttribute("DB");
		root = sc.getInitParameter("root");
		this.sc = sc;
	}
}
