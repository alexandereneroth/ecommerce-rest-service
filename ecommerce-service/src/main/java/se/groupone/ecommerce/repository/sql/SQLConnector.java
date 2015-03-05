package se.groupone.ecommerce.repository.sql;

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

	public SQLConnector(final String host,
						final String port,
						final String username,
						final String password,
						final String database) throws SQLException
	{
		this.host = host;
		this.port = port;
		this.username = username;
		this.password = password;
		this.database = database;
		
		try
		{
			Class.forName(sqlDriver);
		}
		catch (ClassNotFoundException e)
		{
			throw new SQLException("Could not load database driver: " + e.getMessage());
		}
		connect();
	}

	protected void finalize() throws SQLException
	{
		disconnect();
	}
	
	private void connect() throws SQLException
	{
		try
		{
			con = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database, username, password);
			statement = con.createStatement();
		}
		catch (SQLException e)
		{
			throw new SQLException("Could not connect to database: " + e.getMessage());
		}
	}

	private void disconnect() throws SQLException
	{
		try
		{
			statement.close();
			con.close();
		}
		catch (SQLException e)
		{
			throw new SQLException("Could not disconnect from database: " + e.getMessage());
		}
	}

	public ResultSet queryResult(final String query) throws SQLException
	{
		try
		{
			ResultSet rs = statement.executeQuery(query);
			return rs;
		}
		catch (SQLException e)
		{
			throw new SQLException("Error performing query: " + e.getMessage());
		}
	}

	public void query(final String query) throws SQLException
	{
		try
		{
			statement.execute(query);
		}
		catch (SQLException e)
		{
			throw new SQLException("Error performing query: " + e.getMessage());
		}
	}
}
