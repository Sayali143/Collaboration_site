package com.niit.collaboration.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DATE_TIME 
{
	public String getDateTime()
	{
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
	       Date dateobj = new Date();
	       String datetime = dateFormat.format(dateobj).toString();
	       System.out.println("Date - "+datetime);
	       return datetime;
	}
	
	public static void main(String[] args) 
	{
		DATE_TIME dt = new DATE_TIME();
		System.out.println(dt.getDateTime());
	}
}