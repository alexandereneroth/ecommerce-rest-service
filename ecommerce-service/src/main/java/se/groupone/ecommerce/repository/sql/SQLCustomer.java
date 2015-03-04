package se.groupone.ecommerce.repository.sql;

import se.groupone.ecommerce.model.Customer;
import se.groupone.ecommerce.repository.CustomerRepository;
import se.groupone.ecommerce.repository.sql.SQLConnector;
import se.groupone.ecommerce.exception.SQLCustomerException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public final class SQLCustomer implements CustomerRepository
{
	private final SQLConnector sql;
	private final String dbName;
	private final String dbTable;

	public SQLCustomer()
	{
		final String host = "home.erikwelander.se";
		final String port = "3939";
		final String username = "ecom";
		final String password = "wearetheone";
		dbName = "ecomm";
		dbTable = "customer";
		sql = new SQLConnector(host, port, username, password, dbName);
		sql.connect();
	}

	@Override
	public boolean addCustomer(final Customer customer)
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
		builder.append("'" + customer.getMobileNumber() + "');");
		return sql.query(builder.toString());
	}

	@Override
	public Customer getCustomer(final String username)
	{
		StringBuilder builder = new StringBuilder();
		builder.append("SELECT * FROM " + dbTable + " ");
		builder.append("WHERE user_name = '" + username + "';");
		ResultSet rs = sql.queryResult(builder.toString());
		try
		{
			if (!rs.isBeforeFirst())
			{
				return null;
			}
			rs.next();
			return new Customer(rs.getString("user_name"),
					rs.getString("password"),
					rs.getString("email"),
					rs.getString("first_name"),
					rs.getString("last_name"),
					rs.getString("address"),
					rs.getString("phone"));
		}
		catch (SQLException e)
		{
			throw new SQLCustomerException(e.getMessage());
		}
	}

	@Override
	public HashMap<String, Customer> getCustomers()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateCustomer(Customer customer)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public boolean removeCustomer(String username)
	{
		if (getCustomer(username) != null)
		{
			final String removeQuery = "DELETE FROM " + dbName + "." + dbTable + " WHERE user_name = '" + username + "';";
			sql.query(removeQuery);
			if (getCustomer(username) == null)
			{
				return true;
			}
			else
			{
				throw new SQLCustomerException("Query did not delete user " + username + "??");
			}
		}
		return false;
	}

}
