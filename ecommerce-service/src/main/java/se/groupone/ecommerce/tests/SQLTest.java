package se.groupone.ecommerce.tests;
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
	
	
	
}
