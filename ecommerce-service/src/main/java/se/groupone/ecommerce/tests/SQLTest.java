package se.groupone.ecommerce.tests;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.Test;

import se.groupone.ecommerce.repository.sql.SQLConnector;
import static org.junit.Assert.*;

public class SQLTest
{
	private final String host = "home.erikwelander.se";
	private final String port = "3939";
	private final String username = "ecom";
	private final String password = "wearetheone";
	private final String database = "ecomm";

	@Test
	public void connectionTest()
	{
		SQLConnector sql = new SQLConnector(host, port, username, password, database);
		assertTrue(sql.connect());
		assertTrue(sql.disconnect());
	}
	
	@Test
	public void addRemove() throws SQLException
	{
		SQLConnector sql = new SQLConnector(host, port, username, password, database);
		assertTrue(sql.connect());
		
		String queryInsert = "INSERT INTO customer(user_name, password, email, first_name, last_name, address, phone)"
				+ " VALUES('kira', 'Elf', 'erik.welander@hotmail.com', 'Erik', 'Welander', 'Terserusvägen 12', '073');";
		
		String queryResult = "SELECT user_name FROM ecomm.customer;";
		
		assertTrue(sql.queryInsert(queryInsert));
		ResultSet rs = sql.queryResult(queryResult);
		rs.next();
		assertEquals("kira", rs.getString("user_name"));
		
		assertTrue(sql.disconnect());
	}
	
	
	
}
