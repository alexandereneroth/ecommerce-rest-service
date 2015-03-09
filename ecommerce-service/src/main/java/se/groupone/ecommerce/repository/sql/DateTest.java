package se.groupone.ecommerce.repository.sql;

import java.text.SimpleDateFormat;
import java.util.Date;
public class DateTest 
{
	public static void main(String[] args)
	{
		Date d = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");	
		final String sqlDateCreated = sdf.format(d);
		System.out.println(d);
	}
}
