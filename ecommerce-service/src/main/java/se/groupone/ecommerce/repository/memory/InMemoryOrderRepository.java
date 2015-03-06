package se.groupone.ecommerce.repository.memory;

import se.groupone.ecommerce.exception.RepositoryException;
import se.groupone.ecommerce.model.Order;
import se.groupone.ecommerce.repository.OrderRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InMemoryOrderRepository implements OrderRepository
{
	private HashMap<Integer, Order> orders = new HashMap<Integer, Order>();

	@Override
	public void addOrder(Order order) throws RepositoryException
	{
		if (orders.containsKey(order.getId()))
		{
			throw new RepositoryException("Could not add order: order already exists in repository.");
		}
		orders.put(order.getId(), order);
		System.out.println("Order added: " + order.getUsername() + " : " 
							+ order.getId() + " : " + order.getProductIds().toString());
	}

	@Override
	public void removeOrder(int id) throws RepositoryException
	{
		if (orders.containsKey(id))
		{
			orders.remove(id);
		}
		throw new RepositoryException("Could not remove order: order does not exist in repository:");
	}

	@Override
	public Order getOrder(int orderId) throws RepositoryException
	{
		if (orders.containsKey(orderId))
		{
			return orders.get(orderId);
		}
		throw new RepositoryException("Cannot get order: order does not exist in repository.");
	}

	// Get orders for a specific user
	@Override
	public List<Order> getOrders(String customerUsername)
	{
		ArrayList<Order> orderList = new ArrayList<Order>();
		for (Order order : orders.values())
		{
			if (order.getUsername().equals(customerUsername))
			{
				orderList.add(order);
			}
		}
		return orderList;
	}

	@Override
	public int getHighestId()
	{
		return 0;
	}

	@Override
	public void updateOrder(Order order) throws RepositoryException
	{
		// TODO Auto-generated method stub
		
	}

}