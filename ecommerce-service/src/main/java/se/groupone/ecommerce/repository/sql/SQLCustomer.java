package se.groupone.ecommerce.repository.sql;

import se.groupone.ecommerce.model.Customer;
import se.groupone.ecommerce.repository.CustomerRepository;
import se.groupone.ecommerce.repository.sql.SQLConnector;
import se.groupone.ecommerce.exception.RepositoryException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public final class SQLCustomer implements CustomerRepository
{
	private final SQLConnector sql;
	private final String dbName;
	private final String dbTable;

	public SQLCustomer() throws RepositoryException
	{
		final String host = "home.erikwelander.se";
		final String port = "3939";
		final String username = "ecom";
		final String password = "wearetheone";
		dbName = "ecomm";
		dbTable = "customer";
		
		try
		{
			sql = new SQLConnector(host, port, username, password, dbName);
			sql.connect();
		}
		catch(SQLException e)
		{
			throw new RepositoryException("Could not construct SQLCustomer", e);
		}

	}

	protected void finalize() throws RepositoryException
	{
		try
		{
			sql.disconnect();
		}
		catch(SQLException e)
		{
			throw new RepositoryException("FINALIZE: Could not disconnect from database during object destruction!", e);
		}
	}
	
	@Override
	public void addCustomer(final Customer customer) throws RepositoryException
	{
		StringBuilder builder = new StringBuilder();
		builder.append("INSERT INTO " + dbTable + " ");
		builder.append("(user_name, password, email, first_name, last_name, address, phone) ");
		builder.append("VALUES('" + customer.getUsername() + "', ");
		builder.append("'" + customer.getPassword() + "', ");
		builder.append("'" + customer.getEmail() + "', ");
		builder.append("'" + customer.getFirstName() + "', ");
		builder.append("'" + customer.getLastName() + "', ");
		builder.append("'" + customer.getAddress() + "', ");
		builder.append("'" + customer.getPhoneNumber() + "');");
		
		try
		{
			sql.query(builder.toString());			
		}
		catch(SQLException e)
		{
			throw new RepositoryException("Could not add Custumer to database!", e);
		}
	}

	@Override
	public Customer getCustomer(final String username) throws RepositoryException
	{
		StringBuilder builder = new StringBuilder();
		builder.append("SELECT * FROM " + dbTable + " ");
		builder.append("WHERE user_name = '" + username + "';");
		ResultSet rs;
		try
		{
			rs = sql.queryResult(builder.toString());
			if (!rs.isBeforeFirst())
			{
				throw new RepositoryException("No matches for Customer found in database!\nSQL QUERY: "+builder.toString());
			}
			rs.next();
		}
		catch(SQLException e)
		{
			throw new RepositoryException("Failed to retrieve Customer data from database!", e);
		}
		
		try
		{
			return new Customer(rs.getString("user_name"),
								rs.getString("password"),
								rs.getString("email"),
								rs.getString("first_name"),
								rs.getString("last_name"),
								rs.getString("address"),
								rs.getString("phone"));
		}
		catch(SQLException e)
		{
			throw new RepositoryException("Failed to construct Customer from database!", e);
		}

	}

	@Override
	public HashMap<String, Customer> getCustomers() throws RepositoryException
	{
		ResultSet rs;
		final int numRows;
		try
		{
			final String numRowsQuery = "SELECT count(user_name) FROM "+dbName+"."+dbTable+";";
			rs = sql.queryResult(numRowsQuery);
			rs.next();
			numRows = rs.getInt(0);
		}
		catch(SQLException e)
		{
			throw new RepositoryException("No CustomerCount recieved from database!", e);
		}
		
		try
		{
			final String fetchAllUsersQuery = "SELECT * FROM "+dbName+"."+dbTable+";";
			rs = sql.queryResult(fetchAllUsersQuery);
		}
		catch(SQLException e)
		{
			throw new RepositoryException("Could not fetch all users from database!", e);
		}
		
		try
		{
			HashMap<String, Customer> customerList = new HashMap<>();
			for(int i = 0; i < numRows; i++)
			{
				rs.next();
				Customer cu = new Customer(rs.getString("user_name"),
										   rs.getString("password"),
										   rs.getString("email"),
										   rs.getString("first_name"),
										   rs.getString("last_name"),
										   rs.getString("address"),
										   rs.getString("phone"));
				customerList.put(cu.getUsername(), cu);
			}
			return customerList;
		}
		catch(SQLException e)
		{
			throw new RepositoryException("Could not parse customers in database!", e);
		}
	}

	@Override
	public void updateCustomer(final Customer customer) throws RepositoryException
	{
		StringBuilder builder = new StringBuilder();
		builder.append("UPDATE "+dbName+"."+dbTable+" SET ");
		builder.append("password = '"+customer.getPassword()+"', ");
		builder.append("email = '"+customer.getEmail()+"', ");
		builder.append("first_name = '"+customer.getFirstName()+"', ");
		builder.append("last_name = '"+customer.getLastName()+"', ");
		builder.append("address = '"+customer.getAddress()+"', ");
		builder.append("phone = '"+customer.getPhoneNumber()+"' ");
		builder.append("WHERE user_name = '"+customer.getUsername()+"';");
		
		try
		{
			sql.query(builder.toString());
		}
		catch(SQLException e)
		{
			throw new RepositoryException("Could not query Customer update!", e);
		}
		
	}

	@Override
	public void removeCustomer(final String username) throws RepositoryException
	{
		if (getCustomer(username) != null)
		{
			final String removeQuery = "DELETE FROM " + dbName + "." + dbTable + " WHERE user_name = '" + username + "';";
			try
			{
				sql.query(removeQuery);
			}
			catch(SQLException e)
			{
				throw new RepositoryException("Could not query removal of Customer!", e);
			}
			
			if (getCustomer(username) != null)
			{
				throw new RepositoryException("Queried removal of Customer, but Customer still exists??");
			}
		}
	}
}
