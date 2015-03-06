package se.groupone.ecommerce.repository.sql;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import se.groupone.ecommerce.exception.RepositoryException;
import se.groupone.ecommerce.model.Order;
import se.groupone.ecommerce.repository.OrderRepository;


public class SQLOrder implements OrderRepository
{
	private final String dbTableOrders = "order";
	private final String dbTableOrderItems = "order_items";
	private final SQLConnector sql;

	public SQLOrder() throws RepositoryException
	{
		try
		{
			sql = new SQLConnector(DBInfo.host, DBInfo.port, DBInfo.username, DBInfo.password, DBInfo.database);
		}
		catch(SQLException e)
		{
			throw new RepositoryException("Could not construct SQLOrder: Could not construct database object", e);
		}
	}

	@Override
	public void addOrder(final Order order) throws RepositoryException
	{
		StringBuilder toOrderTable = new StringBuilder();
		toOrderTable.append("INSERT INTO " + DBInfo.database + ".`" + dbTableOrders +"` ");
		toOrderTable.append("(id_order, customer_name) ");
		toOrderTable.append("VALUES(" + order.getId() + ", ");
		toOrderTable.append("'" + order.getUsername() + "');");
		try
		{
			sql.query(toOrderTable.toString());
		}
		catch(SQLException e)
		{
			throw new RepositoryException("Could not Customer to Order table in database!", e);
		}
		
	
		ArrayList<Integer> productIDs = order.getProductIds();
		try
		{
			for(int i = 0; i < productIDs.size(); i++)
			{
				StringBuilder toOrderItemsTable = new StringBuilder();
				toOrderItemsTable.append("INSERT INTO " + DBInfo.database + "." + dbTableOrderItems +" ");
				toOrderItemsTable.append("(id_order, id_product) ");
				toOrderItemsTable.append("VALUES(" + order.getId() + ", ");
				toOrderItemsTable.append("" + productIDs.get(i) + ");");
				sql.query(toOrderItemsTable.toString());
				System.out.println("SQL: "+toOrderItemsTable.toString());
			}
		}
		catch(SQLException e)
		{
			throw new RepositoryException("Could not add OrderIDs to Order! in database!", e);
		}
	}
	
	@Override
	public Order getOrder(final int orderID) throws RepositoryException
	{
		final String customerUserName;
		final int numProductsInOrder;
		StringBuilder customerQuery = new StringBuilder();
		customerQuery.append("SELECT customer_name FROM " + DBInfo.database + "." + dbTableOrders +" ");
		customerQuery.append("WHERE id_order = " + orderID + ";");
		ResultSet rs;
		try
		{
			rs = sql.queryResult(customerQuery.toString());
			if (!rs.isBeforeFirst())
			{
				throw new RepositoryException("No matches for CustomerUserName found in database!\nSQL QUERY: "+customerQuery.toString());
			}
			rs.next();
			customerUserName = rs.getString(1);
			rs.close();
		}
		catch(SQLException e)
		{
			throw new RepositoryException("Could not query CustomerUserName from Order!", e);
		}
		
		StringBuilder numOfItemIDsQuery = new StringBuilder();
		numOfItemIDsQuery.append("SELECT count(id_product) FROM " + DBInfo.database + "." + dbTableOrderItems +" ");
		numOfItemIDsQuery.append("WHERE id_order = " + orderID + ";");
		try
		{
			rs = sql.queryResult(numOfItemIDsQuery.toString());
			if (!rs.isBeforeFirst())
			{
				throw new RepositoryException("No matches for count(id_product) in database!\nSQL QUERY: "+customerQuery.toString());
			}
			rs.next();
			numProductsInOrder = rs.getInt(1);
			rs.close();
		}
		catch(SQLException e)
		{
			throw new RepositoryException("Could not count product IDs from OrderItems database!", e);
		}
		
		StringBuilder orderIDsQuery = new StringBuilder();
		orderIDsQuery.append("SELECT id_product FROM " + DBInfo.database + "." + dbTableOrderItems +" ");
		orderIDsQuery.append("WHERE id_order = " + orderID + ";");
		try
		{
			rs = sql.queryResult(orderIDsQuery.toString());
			if (!rs.isBeforeFirst())
			{
				throw new RepositoryException("No matches for orderID found in OrderItems database!\nSQL QUERY: "+customerQuery.toString());
			}
		}
		catch(SQLException e)
		{
			throw new RepositoryException("Could not query CustomerUserName from Order!", e);
		}
		
		
		try
		{
			ArrayList<Integer> productIds = new ArrayList<>();
			for(int i = 0; i < numProductsInOrder; i++)
			{
				rs.next();
				productIds.add(rs.getInt(1));
			}
			rs.close();
			Order order = new Order(orderID, customerUserName, productIds);
			return order;
			
		}
		catch(SQLException e)
		{
			throw new RepositoryException("Could not construct Order from database!", e);
		}
	}

	@Override
	public void removeOrder(final int orderID) throws RepositoryException
	{
		StringBuilder removeInOrdersItems = new StringBuilder();
		removeInOrdersItems.append("DELETE FROM " + DBInfo.database + "." + dbTableOrderItems +" ");
		removeInOrdersItems.append("WHERE id_order = " + orderID + ";");
		try
		{
			sql.query(removeInOrdersItems.toString());
		}
		catch(SQLException e)
		{
			throw new RepositoryException("Could not query removal of OrderItems!", e);
		}
		
		StringBuilder removeInOrders = new StringBuilder();
		removeInOrders.append("DELETE FROM " + DBInfo.database + "." + dbTableOrders +" ");
		removeInOrders.append("WHERE id_order = " + orderID + ";");
		try
		{
			sql.query(removeInOrders.toString());
		}
		catch(SQLException e)
		{
			throw new RepositoryException("Could not query removal of order!", e);
		}
	}
	
	@Override
	public List<Order> getOrders(final String customerUsername) throws RepositoryException
	{
		final int numOrderIDs;
		ResultSet rs;
		StringBuilder countOrderIDs = new StringBuilder();
		countOrderIDs.append("SELECT count(id_order) FROM " + DBInfo.database + ".`" + dbTableOrders +"` ");
		countOrderIDs.append("WHERE customer_name = '" + customerUsername + "';");
		try
		{
			rs = sql.queryResult(countOrderIDs.toString());
			if (!rs.isBeforeFirst())
			{
				throw new RepositoryException("No matches for count(customer_name) in database!\nSQL QUERY: "+countOrderIDs.toString());
			}
			rs.next();
			numOrderIDs = rs.getInt(1);
		}
		catch(SQLException e)
		{
			throw new RepositoryException("Could not fetch OrderIDs from database!\nSQL: "+countOrderIDs.toString(), e);
		}
		
		StringBuilder getOrderIDs = new StringBuilder();
		getOrderIDs.append("SELECT id_order FROM " + DBInfo.database + ".`" + dbTableOrders +"` ");
		getOrderIDs.append("WHERE customer_name = '" + customerUsername + "';");
		try
		{
			rs = sql.queryResult(getOrderIDs.toString());
			if (!rs.isBeforeFirst())
			{
				throw new RepositoryException("No matches found customerUserName in database!\nSQL QUERY: "+getOrderIDs.toString());
			}
		}
		catch(SQLException e)
		{
			throw new RepositoryException("Could not fetch OrderIDs from database!\nSQL QUERY: "+getOrderIDs.toString(), e);
		}
		
		
		int customerOrderIDs[] = new int[numOrderIDs];
		try
		{
			for(int i = 0; i < numOrderIDs; i++)
			{
				rs.next();
				customerOrderIDs[i] = rs.getInt(1);
			}
		}
		catch(SQLException e)
		{
			throw new RepositoryException("Could not parse Order IDs from ResultSet!", e);
		}
		
		List<Order> orderList = new ArrayList<>();
		for(int i = 0; i < numOrderIDs; i++)
		{
			orderList.add(getOrder(customerOrderIDs[i]));
		}

		return orderList;
	}

	@Override
	public int getHighestId() throws RepositoryException
	{
		final String query = "SELECT MAX(id_order) FROM " + DBInfo.database + ".`" + dbTableOrders +"` ";
		ResultSet rs;
		try
		{
			rs = sql.queryResult(query);
			rs.next();
			final int highestID = rs.getInt(1);
			rs.close();
			return highestID;
		}
		catch(SQLException e)
		{
			throw new RepositoryException("Could not get query MAX Ordert ID!", e);
		}
	}

	@Override
	public void updateOrder(Order order) throws RepositoryException
	{
		// TODO Auto-generated method stub
		
	}
}
