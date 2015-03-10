package se.groupone.ecommerce.repository.sql;

import se.groupone.ecommerce.model.Customer;
import se.groupone.ecommerce.repository.CustomerRepository;
import se.groupone.ecommerce.repository.sql.SQLConnector;
import se.groupone.ecommerce.exception.RepositoryException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public final class SQLCustomer implements CustomerRepository
{
	private final SQLConnector sql;
	private final String dbCustomer = "customer";
	private final String dbCustomerItems = "customer_cart";

	public SQLCustomer() throws RepositoryException
	{
		try
		{
			sql = new SQLConnector(DBInfo.host, DBInfo.port, DBInfo.username, DBInfo.password, DBInfo.database);
		}
		catch(SQLException e)
		{
			throw new RepositoryException("Could not construct SQLCustomer: Could not construct database object", e);
		}

	}
	
	@Override
	public void addCustomer(final Customer customer) throws RepositoryException
	{
		StringBuilder costumerQuery = new StringBuilder();
		costumerQuery.append("INSERT INTO " + DBInfo.database + "." + dbCustomer + " ");
		costumerQuery.append("(user_name, password, email, first_name, last_name, address, phone) ");
		costumerQuery.append("VALUES('" + customer.getUsername() + "', ");
		costumerQuery.append("'" + customer.getPassword() + "', ");
		costumerQuery.append("'" + customer.getEmail() + "', ");
		costumerQuery.append("'" + customer.getFirstName() + "', ");
		costumerQuery.append("'" + customer.getLastName() + "', ");
		costumerQuery.append("'" + customer.getAddress() + "', ");
		costumerQuery.append("'" + customer.getPhoneNumber() + "');");
		try
		{
			sql.query(costumerQuery.toString());
		}
		catch(SQLException e)
		{
			throw new RepositoryException("Could not add Custumer to database!", e);
		}
		
		ArrayList<Integer> productIds = customer.getShoppingCart();
		try
		{
			for(int i = 0; i < productIds.size(); i++)
			{
				StringBuilder customerCartQuery = new StringBuilder();
				customerCartQuery.append("INSERT INTO " + DBInfo.database + "." + dbCustomerItems + " ");
				customerCartQuery.append("(id_item, user_name) ");
				customerCartQuery.append("VALUES(" + productIds.get(i) + ", ");
				customerCartQuery.append("'" + customer.getUsername() + "'); ");
				sql.query(customerCartQuery.toString());
			}
		}
		catch(SQLException e)
		{
			throw new RepositoryException("Could not add Shopping cart IDs to Customer!" + e.getMessage(), e);
		}
	}

	@Override
	public Customer getCustomer(final String username) throws RepositoryException
	{
		StringBuilder customerQuery = new StringBuilder();
		customerQuery.append("SELECT * FROM " + DBInfo.database+"." + dbCustomer + " ");
		customerQuery.append("WHERE user_name = '" + username + "';");
		ResultSet rs;
		try
		{
			rs = sql.queryResult(customerQuery.toString());
			if (!rs.isBeforeFirst())
			{
				throw new RepositoryException("No matches for Customer found in database!\nSQL QUERY: "+customerQuery.toString());
			}
		}
		catch(SQLException e)
		{
			throw new RepositoryException("Failed to retrieve Customer data from database!", e);
		}
		
		Customer customer;
		try
		{
			rs.next();
			 customer = new Customer(rs.getString("user_name"),
									 rs.getString("password"),
									 rs.getString("email"),
									 rs.getString("first_name"),
									 rs.getString("last_name"),
									 rs.getString("address"),
									 rs.getString("phone"));
			rs.close();
		}
		catch(SQLException e)
		{
			throw new RepositoryException("Failed to construct Customer from database!", e);
		}
		
		
		
		final int numCartItems;
		StringBuilder numCartItemsQuery = new StringBuilder();
		numCartItemsQuery.append("SELECT COUNT(id_item) FROM " + DBInfo.database+"." + dbCustomerItems + " ");
		numCartItemsQuery.append("WHERE user_name = '" + username + "';");
		try
		{
			rs = sql.queryResult(numCartItemsQuery.toString());
			if (!rs.isBeforeFirst())
			{
				throw new RepositoryException("No matches COUNT(id_item)In Customer shopping cart!\nSQL QUERY: "+numCartItemsQuery.toString());
			}
		}
		catch(SQLException e)
		{
			throw new RepositoryException("Failed to retrieve Customer shopping cart data!", e);
		}
		try
		{
			rs.next();
			numCartItems = rs.getInt(1);
			rs.close();
		}
		catch(SQLException e)
		{
			throw new RepositoryException("Failed to parse Shopping cart data!", e);
		}
		
		
		
		StringBuilder cartItemsQuery = new StringBuilder();
		cartItemsQuery.append("SELECT id_item FROM " + DBInfo.database+"." + dbCustomerItems + " ");
		cartItemsQuery.append("WHERE user_name = '" + username + "';");
		try
		{
			rs = sql.queryResult(numCartItemsQuery.toString());
			if (!rs.isBeforeFirst())
			{
				throw new RepositoryException("No matches for user_name in Shoppingcart!\nSQL QUERY: "+cartItemsQuery.toString());
			}
		}
		catch(SQLException e)
		{
			throw new RepositoryException("Failed to retrieve Customer shopping cart data!", e);
		}
		
		try
		{
			for(int i = 0; i < numCartItems; i++)
			{
				rs.next();
				customer.addProductToShoppingCart(rs.getInt(1));
			}
			rs.close();
		}
		catch(SQLException e)
		{
			throw new RepositoryException("Failed to parse Shopping cart data!", e);
		}
		return customer;
	}

	@Override
	public List<Customer> getCustomers() throws RepositoryException
	{
		ResultSet rs;
		final int numRows;
		try
		{
			final String numRowsQuery = "SELECT COUNT(user_name) FROM "+DBInfo.database+"." + dbCustomer + ";";
			rs = sql.queryResult(numRowsQuery);
			if (!rs.isBeforeFirst())
			{
				throw new RepositoryException("No matches for user_name in Customers!\nSQL QUERY: "+numRowsQuery);
			}
		}
		catch(SQLException e)
		{
			throw new RepositoryException("Could not count all Customers usernames from database!", e);
		}
		
		try
		{
			rs.next();
			numRows = rs.getInt(1);
			rs.close();
		}
		catch(SQLException e)
		{
			throw new RepositoryException("Could not parse Customer username value!", e);
		}
		
		try
		{
			final String allCustomersQuery = "SELECT user_name FROM "+DBInfo.database+"." + dbCustomer + ";";
			rs = sql.queryResult(allCustomersQuery);
			if (!rs.isBeforeFirst())
			{
				throw new RepositoryException("No matches for user_name in Customers!\nSQL QUERY: "+allCustomersQuery);
			}
		}
		catch(SQLException e)
		{
			throw new RepositoryException("Could not fetch all Customers usernames from database!", e);
		}
		
		String allUserNames[] = new String[numRows];
		try
		{
			for(int i = 0; i < numRows; i++)
			{
				rs.next();
				allUserNames[i] = rs.getString(1);
			}
			rs.close();
		}
		catch(SQLException e)
		{
			throw new RepositoryException("Could not parse all Customer user names!", e);
		}

		List<Customer> customerList = new ArrayList<>();
		for(int i = 0; i < numRows; i++)
		{
			customerList.add(getCustomer(allUserNames[i]));
		}
		return customerList;
	}

	@Override
	public void updateCustomer(final Customer customer) throws RepositoryException
	{
		StringBuilder updateCustomerQuery = new StringBuilder();
		updateCustomerQuery.append("UPDATE " + DBInfo.database + "." + dbCustomer + " SET ");
		updateCustomerQuery.append("password = '"+customer.getPassword()+"', ");
		updateCustomerQuery.append("email = '"+customer.getEmail()+"', ");
		updateCustomerQuery.append("first_name = '"+customer.getFirstName()+"', ");
		updateCustomerQuery.append("last_name = '"+customer.getLastName()+"', ");
		updateCustomerQuery.append("address = '"+customer.getAddress()+"', ");
		updateCustomerQuery.append("phone = '"+customer.getPhoneNumber()+"' ");
		updateCustomerQuery.append("WHERE user_name = '"+customer.getUsername()+"';");
		try
		{
			sql.query(updateCustomerQuery.toString());
		}
		catch(SQLException e)
		{
			throw new RepositoryException("Could not query Customer update!", e);
		}
		
		final String removeCartItems = "DELETE FROM " + DBInfo.database + "." + dbCustomerItems + " WHERE user_name = '" + customer.getUsername() + "';";
		try
		{
			sql.query(removeCartItems);
		}
		catch(SQLException e)
		{
			throw new RepositoryException("Could not query Customer Items deletion!", e);
		}
		
		ArrayList<Integer> productIds = customer.getShoppingCart();
		try
		{
			for(int i = 0; i < productIds.size(); i++)
			{
				StringBuilder customerCartQuery = new StringBuilder();
				customerCartQuery.append("INSERT INTO " + DBInfo.database + "." + dbCustomerItems + " ");
				customerCartQuery.append("(id_item, user_name) ");
				customerCartQuery.append("VALUES(" + productIds.get(i) + ", ");
				customerCartQuery.append("'" + customer.getUsername() + "'); ");
				sql.query(customerCartQuery.toString());
			}
		}
		catch(SQLException e)
		{
			throw new RepositoryException("Could not add Shopping cart IDs to Customer!", e);
		}
	}

	@Override
	public void removeCustomer(final String username) throws RepositoryException
	{
		final String removeQuery = "DELETE FROM " + DBInfo.database + "." + dbCustomer + " WHERE user_name = '" + username + "';";
		try
		{
			sql.query(removeQuery);
		}
		catch(SQLException e)
		{
			throw new RepositoryException("Could not query removal of Customer!", e);
		}
		
		final String removeCartItems = "DELETE FROM " + DBInfo.database + "." + dbCustomerItems + " WHERE user_name = '" + username + "';";
		try
		{
			sql.query(removeCartItems);
		}
		catch(SQLException e)
		{
			throw new RepositoryException("Could not query Customer Items deletion!", e);
		}
	}
}
