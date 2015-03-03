package se.groupone.ecommerce.test;
import se.groupone.ecommerce.repository.sql.SQLConnector;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.junit.Test;
import static org.junit.Assert.*;

public class SQLConnectorTest
{
	private final String host = "10.0.0.1";
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
		
		assertTrue(sql.query(queryInsert));
		assertFalse(sql.query(queryInsert));
		ResultSet rs = sql.queryResult(queryResult);
		rs.next();
		assertEquals("kira", rs.getString("user_name"));
		assertTrue(sql.query("DELETE FROM customer where user_name = 'kira'"));
		assertTrue(sql.disconnect());
	}
}

