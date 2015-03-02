package se.groupone.ecommerce.repository.sql;

import java.sql.Array;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public final class SQLConnector
{
	private final String sqlDriver = "com.mysql.jdbc.Driver";
	private final String host,
						 port,
						 username,
						 password,
						 database;
	
	private Connection con;
	private Statement statement;
	private ResultSet rs;
	
	public SQLConnector(final String host,
				 final String port,
				 final String username,
				 final String password,
				 final String database)
	{
		this.host = host;
		this.port = port;
		this.username = username;
		this.password = password;
		this.database = database;
		
		try { Class.forName(sqlDriver); }
		catch (ClassNotFoundException e) { System.err.println("Could not load database driver: "+e.getMessage()); }
	}
	
	public boolean connect()
	{
		try
		{
			con = DriverManager.getConnection("jdbc:mysql://"+host+":"+port+"/"+database, username, password);
			statement = con.createStatement();
			return true;
		}
		catch (SQLException e)
		{
			System.err.println("Could not connect to database: "+e.getMessage());
			e.printStackTrace();
			return false;
		}
	}

	public boolean disconnect()
	{
		try { con.close(); return true; }
		catch (SQLException e) { System.err.println(e.getMessage()); return false; }
	}
	
	public ResultSet queryResult(final String query)
	{
		try
		{
			return statement.executeQuery(query);
		}
		catch (SQLException e)
		{
			System.err.println("Error performing query: "+e.getMessage()); return null;
		}
	}
	
	public boolean queryInsert(final String query)
	{
		try
		{
			statement.execute(query);
			return true;
		}
		catch (SQLException e)
		{
			System.err.println("Error performing query: "+e.getMessage());
			return false;
		}
	}
}
