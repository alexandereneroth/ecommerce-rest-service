package se.groupone.ecommerce.repository.sql;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.text.SimpleDateFormat;
import java.util.Date;

import se.groupone.ecommerce.exception.RepositoryException;
import se.groupone.ecommerce.model.Order;
import se.groupone.ecommerce.repository.OrderRepository;


public class SQLOrder implements OrderRepository
{
	private final String dbTableOrders = "order";
	private final String dbTableOrderItems = "order_items";
	private final SQLConnector sql;
	private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");	

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
		final String sqlDateCreated = sdf.format(order.getDateCreated());
		String sqlDateShipped;
		if(order.getDateShipped() == null)
		{
			sqlDateShipped = sdf.format(new Date(0L));
		}
		else
		{
			sqlDateShipped = sdf.format(order.getDateShipped());
		}
		
		StringBuilder toOrderTable = new StringBuilder();
		toOrderTable.append("INSERT INTO " + DBInfo.database + ".`" + dbTableOrders +"` ");
		toOrderTable.append("(id_order, customer_name, created, shipped) ");
		toOrderTable.append("VALUES(" + order.getId() + ", ");
		toOrderTable.append("'" + order.getUsername() + "', ");
		toOrderTable.append("'" + sqlDateCreated + "', ");
		toOrderTable.append("'" + sqlDateShipped + "');");
		try
		{
			sql.query(toOrderTable.toString());
		}
		catch(SQLException e)
		{
			throw new RepositoryException("Could not add Order values to database!", e);
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
			}
		}
		catch(SQLException e)
		{
			throw new RepositoryException("Could not add OrderID values to database!", e);
		}
	}
	
	@Override
	public Order getOrder(final int orderID) throws RepositoryException
	{
		final String customerUserName;
		Date dateOrderCreated;
		Date dateOrderShipped;
		ResultSet rs;
		
		StringBuilder orderInfoQuery = new StringBuilder();
		orderInfoQuery.append("SELECT customer_name, created, shipped FROM " + DBInfo.database + ".`" + dbTableOrders +"` ");
		orderInfoQuery.append("WHERE id_order = " + orderID + ";");
		try
		{
			rs = sql.queryResult(orderInfoQuery.toString());
			if (!rs.isBeforeFirst())
			{
				throw new RepositoryException("No matches for found for query: "+orderInfoQuery.toString());
			}
		}
		catch(SQLException e)
		{
			throw new RepositoryException("Could not query database for Order info!", e);
		}
		
		try
		{
			rs.next();
			customerUserName = rs.getString("customer_name");
			dateOrderCreated = rs.getDate("created");
			dateOrderShipped = rs.getDate("shipped");
			rs.close();
		}
		catch(SQLException e)
		{
			throw new RepositoryException("Could not parse SQL values!", e);
		}
		
		StringBuilder numOfItemIDsQuery = new StringBuilder();
		numOfItemIDsQuery.append("SELECT count(id_product) FROM " + DBInfo.database + "." + dbTableOrderItems +" ");
		numOfItemIDsQuery.append("WHERE id_order = " + orderID + ";");
		try
		{
			rs = sql.queryResult(numOfItemIDsQuery.toString());
			if (!rs.isBeforeFirst())
			{
				throw new RepositoryException("No matches for count(id_product) in database!\nSQL QUERY: "+numOfItemIDsQuery.toString());
			}
		}
		catch(SQLException e)
		{
			throw new RepositoryException("Could not count product IDs from OrderItems database!", e);
		}
		
		final int numProductsInOrder;
		try
		{
			rs.next();
			numProductsInOrder = rs.getInt(1);
			rs.close();
		}
		catch(SQLException e)
		{
			throw new RepositoryException("Could not parse SQL values!", e);
		}
		
		StringBuilder orderIDsQuery = new StringBuilder();
		orderIDsQuery.append("SELECT id_product FROM " + DBInfo.database + "." + dbTableOrderItems +" ");
		orderIDsQuery.append("WHERE id_order = " + orderID + ";");
		try
		{
			rs = sql.queryResult(orderIDsQuery.toString());
			if (!rs.isBeforeFirst())
			{
				throw new RepositoryException("No matches for orderID found in database!\nSQL QUERY: "+orderIDsQuery.toString());
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
			return new Order(orderID, customerUserName, productIds, dateOrderCreated, dateOrderShipped);
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
		}
		catch(SQLException e)
		{
			throw new RepositoryException("Could not fetch OrderIDs from database!\nSQL: "+countOrderIDs.toString(), e);
		}
		
		try
		{
			rs.next();
			numOrderIDs = rs.getInt(1);
			rs.close();
		}
		catch(SQLException e)
		{
			throw new RepositoryException("Could not parse SQL values!", e);
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
	public void updateOrder(final Order order) throws RepositoryException
	{
		//Firstly, lets convert java.util.Date format into MYSQL DATE format String
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");	
		final String sqlDateCreated = sdf.format(order.getDateCreated());
		String sqlDateShipped;
		if(order.getDateShipped() == null)
		{
			sqlDateShipped = sdf.format(new Date(0L));
		}
		else
		{
			sqlDateShipped = sdf.format(order.getDateShipped());
		}
		
		//Now lets update the orders created and shipped fields
		StringBuilder updateTableOrders = new StringBuilder();
		updateTableOrders.append("UPDATE " + DBInfo.database + ".`" + dbTableOrders +"` SET ");
		updateTableOrders.append("created = '" + sqlDateCreated + "', ");
		updateTableOrders.append("shipped = '" + sqlDateShipped + "' ");
		updateTableOrders.append("WHERE id_order = " + order.getId() + ";");
		
		try
		{
			sql.query(updateTableOrders.toString());
		}
		catch(SQLException e)
		{
			throw new RepositoryException("Could not query Order update!", e);
		}
		
		StringBuilder deleteOrderItems = new StringBuilder();
		deleteOrderItems.append("DELETE FROM " + DBInfo.database + "." + dbTableOrderItems +" ");
		deleteOrderItems.append("WHERE id_order = " + order.getId() + ";");
		try
		{
			sql.query(deleteOrderItems.toString());
		}
		catch(SQLException e)
		{
			throw new RepositoryException("Could not delete Order items!", e);
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
			}
		}
		catch(SQLException e)
		{
			throw new RepositoryException("Could not add OrderIDs to Order! in database!", e);
		}
	}
}
