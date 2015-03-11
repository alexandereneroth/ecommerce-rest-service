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

public class SQLOrderRepository implements OrderRepository
{
	private final String dbTableOrders = "order";
	private final String dbTableOrderItems = "order_items";
	private final SQLConnector sql;
	private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	public SQLOrderRepository() throws RepositoryException
	{
		try
		{
			sql = new SQLConnector(DBConnectionConfig.HOST, DBConnectionConfig.PORT, DBConnectionConfig.USERNAME, DBConnectionConfig.PASSWORD, DBConnectionConfig.DATABASE);
		}
		catch (SQLException e)
		{
			throw new RepositoryException("Could not construct SQLOrder: Could not construct database object", e);
		}
	}

	@Override
	public void addOrder(final Order order) throws RepositoryException
	{
		final String sqlDateCreated = sdf.format(order.getDateCreated());
		String sqlDateShipped;
		if (order.getDateShipped() == null)
		{
			sqlDateShipped = sdf.format(new Date(0L));
		}
		else
		{
			sqlDateShipped = sdf.format(order.getDateShipped());
		}

		try
		{
			StringBuilder orderInsertQuery = new StringBuilder();
			orderInsertQuery.append("INSERT INTO " + DBConnectionConfig.DATABASE + ".`" + dbTableOrders + "` ");
			orderInsertQuery.append("(id_order, customer_name, created, shipped) ");
			orderInsertQuery.append("VALUES(" + order.getId() + ", ");
			orderInsertQuery.append("'" + order.getUsername() + "', ");
			orderInsertQuery.append("'" + sqlDateCreated + "', ");
			orderInsertQuery.append("'" + sqlDateShipped + "');");

			sql.query(orderInsertQuery.toString());
		}
		catch (SQLException e)
		{
			throw new RepositoryException("Could not add Order values to database!", e);
		}

		ArrayList<Integer> productIDs = order.getProductIds();
		try
		{
			StringBuilder toOrderItemsTable = new StringBuilder();
			for (int i = 0; i < productIDs.size(); i++)
			{
				toOrderItemsTable.append("INSERT INTO " + DBConnectionConfig.DATABASE + "." + dbTableOrderItems + " ");
				toOrderItemsTable.append("(id_order, id_product) ");
				toOrderItemsTable.append("VALUES(" + order.getId() + ", ");
				toOrderItemsTable.append("" + productIDs.get(i) + ");");

				sql.query(toOrderItemsTable.toString());
				toOrderItemsTable.delete(0, toOrderItemsTable.length());
			}
		}
		catch (SQLException e)
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

		try
		{
			StringBuilder orderInfoQuery = new StringBuilder();
			orderInfoQuery.append("SELECT customer_name, created, shipped FROM " + DBConnectionConfig.DATABASE + ".`" + dbTableOrders + "` ");
			orderInfoQuery.append("WHERE id_order = " + orderID + ";");

			rs = sql.queryResult(orderInfoQuery.toString());
			if (!rs.isBeforeFirst())
			{
				throw new RepositoryException("No matches for found for query: " + orderInfoQuery.toString());
			}
		}
		catch (SQLException e)
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

			if (sdf.format(dateOrderShipped).equals(sdf.format(new Date(0L))))
			{
				dateOrderShipped = null;
			}
		}
		catch (SQLException e)
		{
			throw new RepositoryException("Could not parse SQL values!", e);
		}

		try
		{
			StringBuilder numOfItemIDsQuery = new StringBuilder();
			numOfItemIDsQuery.append("SELECT count(id_product) FROM " + DBConnectionConfig.DATABASE + "." + dbTableOrderItems + " ");
			numOfItemIDsQuery.append("WHERE id_order = " + orderID + ";");

			rs = sql.queryResult(numOfItemIDsQuery.toString());
			if (!rs.isBeforeFirst())
			{
				throw new RepositoryException("No matches for count(id_product) in database!\nSQL QUERY: " + numOfItemIDsQuery.toString());
			}
		}
		catch (SQLException e)
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
		catch (SQLException e)
		{
			throw new RepositoryException("Could not parse SQL values!", e);
		}

		try
		{
			StringBuilder orderIDsQuery = new StringBuilder();
			orderIDsQuery.append("SELECT id_product FROM " + DBConnectionConfig.DATABASE + "." + dbTableOrderItems + " ");
			orderIDsQuery.append("WHERE id_order = " + orderID + ";");

			rs = sql.queryResult(orderIDsQuery.toString());
			if (!rs.isBeforeFirst())
			{
				throw new RepositoryException("No matches for orderID found in database!\nSQL QUERY: " + orderIDsQuery.toString());
			}
		}
		catch (SQLException e)
		{
			throw new RepositoryException("Could not query CustomerUserName from Order!", e);
		}

		try
		{
			ArrayList<Integer> productIds = new ArrayList<>();
			for (int i = 0; i < numProductsInOrder; i++)
			{
				rs.next();
				productIds.add(rs.getInt(1));
			}
			rs.close();
			return new Order(orderID, customerUserName, productIds, dateOrderCreated, dateOrderShipped);
		}
		catch (SQLException e)
		{
			throw new RepositoryException("Could not construct Order from database!", e);
		}
	}

	@Override
	public void removeOrder(final int orderID) throws RepositoryException
	{
		try
		{
			StringBuilder removeOrderItemsQuery = new StringBuilder();
			removeOrderItemsQuery.append("DELETE FROM " + DBConnectionConfig.DATABASE + "." + dbTableOrderItems + " ");
			removeOrderItemsQuery.append("WHERE id_order = " + orderID + ";");

			sql.query(removeOrderItemsQuery.toString());
		}
		catch (SQLException e)
		{
			throw new RepositoryException("Could not query removal of OrderItems!", e);
		}

		try
		{
			StringBuilder removeOrderQuery = new StringBuilder();
			removeOrderQuery.append("DELETE FROM " + DBConnectionConfig.DATABASE + "." + dbTableOrders + " ");
			removeOrderQuery.append("WHERE id_order = " + orderID + ";");

			sql.query(removeOrderQuery.toString());
		}
		catch (SQLException e)
		{
			throw new RepositoryException("Could not query removal of order!", e);
		}
	}

	@Override
	public List<Order> getOrders(final String customerUsername) throws RepositoryException
	{
		final int numOrderIDs;
		ResultSet rs;
		try
		{
			StringBuilder countOrderIDsQuery = new StringBuilder();
			countOrderIDsQuery.append("SELECT count(id_order) FROM " + DBConnectionConfig.DATABASE + ".`" + dbTableOrders + "` ");
			countOrderIDsQuery.append("WHERE customer_name = '" + customerUsername + "';");

			rs = sql.queryResult(countOrderIDsQuery.toString());
			if (!rs.isBeforeFirst())
			{
				throw new RepositoryException("No matches for count(customer_name) in database!\nSQL QUERY: " + countOrderIDsQuery.toString());
			}
		}
		catch (SQLException e)
		{
			throw new RepositoryException("Could not fetch OrderIDs from database!", e);
		}

		try
		{
			rs.next();
			numOrderIDs = rs.getInt(1);
			rs.close();
		}
		catch (SQLException e)
		{
			throw new RepositoryException("Could not parse numeber of Order IDs!", e);
		}

		try
		{
			StringBuilder getOrderIDsQuery = new StringBuilder();
			getOrderIDsQuery.append("SELECT id_order FROM " + DBConnectionConfig.DATABASE + ".`" + dbTableOrders + "` ");
			getOrderIDsQuery.append("WHERE customer_name = '" + customerUsername + "';");

			rs = sql.queryResult(getOrderIDsQuery.toString());
			if (!rs.isBeforeFirst())
			{
				throw new RepositoryException("No matches found customerUserName in database!\nSQL QUERY: " + getOrderIDsQuery.toString());
			}
		}
		catch (SQLException e)
		{
			throw new RepositoryException("Could not fetch OrderIDs from database!", e);
		}

		int customerOrderIDs[] = new int[numOrderIDs];
		try
		{
			for (int i = 0; i < numOrderIDs; i++)
			{
				rs.next();
				customerOrderIDs[i] = rs.getInt(1);
			}
			rs.close();
		}
		catch (SQLException e)
		{
			throw new RepositoryException("Could not parse Order IDs from ResultSet!", e);
		}

		List<Order> orderList = new ArrayList<>();
		for (int i = 0; i < numOrderIDs; i++)
		{
			orderList.add(getOrder(customerOrderIDs[i]));
		}
		return orderList;
	}

	@Override
	public int getHighestId() throws RepositoryException
	{
		ResultSet rs;
		try
		{
			final String highestOrderIDQuery = "SELECT MAX(id_order) FROM " + DBConnectionConfig.DATABASE + ".`" + dbTableOrders + "` ";
			rs = sql.queryResult(highestOrderIDQuery);
			if (!rs.isBeforeFirst())
			{
				throw new RepositoryException("No matches found for MAX(id_order) in Orders!\nSQL QUERY: " + highestOrderIDQuery.toString());
			}
		}
		catch (SQLException e)
		{
			throw new RepositoryException("Could not get query MAX(id_order)!", e);
		}

		try
		{
			rs.next();
			final int highestID = rs.getInt(1);
			rs.close();
			return highestID;
		}
		catch (SQLException e)
		{
			throw new RepositoryException("Could not parse MAX(id_order)!", e);
		}
	}

	@Override
	public void updateOrder(final Order order) throws RepositoryException
	{
		// Firstly, lets convert java.util.Date format into MYSQL DATE format
		// String
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		final String sqlDateCreated = sdf.format(order.getDateCreated());
		String sqlDateShipped;
		if (order.getDateShipped() == null)
		{
			sqlDateShipped = sdf.format(new Date(0L));
		}
		else
		{
			sqlDateShipped = sdf.format(order.getDateShipped());
		}

		// Now lets update the orders created and shipped fields
		try
		{
			StringBuilder updateTableOrderQuery = new StringBuilder();
			updateTableOrderQuery.append("UPDATE " + DBConnectionConfig.DATABASE + ".`" + dbTableOrders + "` SET ");
			updateTableOrderQuery.append("created = '" + sqlDateCreated + "', ");
			updateTableOrderQuery.append("shipped = '" + sqlDateShipped + "' ");
			updateTableOrderQuery.append("WHERE id_order = " + order.getId() + ";");

			sql.query(updateTableOrderQuery.toString());
		}
		catch (SQLException e)
		{
			throw new RepositoryException("Could not query Order update!", e);
		}

		try
		{
			StringBuilder deleteOrderItemsQuery = new StringBuilder();
			deleteOrderItemsQuery.append("DELETE FROM " + DBConnectionConfig.DATABASE + "." + dbTableOrderItems + " ");
			deleteOrderItemsQuery.append("WHERE id_order = " + order.getId() + ";");

			sql.query(deleteOrderItemsQuery.toString());
		}
		catch (SQLException e)
		{
			throw new RepositoryException("Could not delete Order items!", e);
		}

		ArrayList<Integer> productIDs = order.getProductIds();
		try
		{
			StringBuilder toOrderItemsQuery = new StringBuilder();
			for (int i = 0; i < productIDs.size(); i++)
			{
				toOrderItemsQuery.append("INSERT INTO " + DBConnectionConfig.DATABASE + "." + dbTableOrderItems + " ");
				toOrderItemsQuery.append("(id_order, id_product) ");
				toOrderItemsQuery.append("VALUES(" + order.getId() + ", ");
				toOrderItemsQuery.append("" + productIDs.get(i) + ");");

				sql.query(toOrderItemsQuery.toString());
				toOrderItemsQuery.delete(0, toOrderItemsQuery.length());
			}
		}
		catch (SQLException e)
		{
			throw new RepositoryException("Could not add OrderIDs to Order! in database!", e);
		}
	}
}
