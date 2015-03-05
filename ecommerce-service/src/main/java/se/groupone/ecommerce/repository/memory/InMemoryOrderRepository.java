package se.groupone.ecommerce.repository.memory;

import se.groupone.ecommerce.exception.RepositoryException;
import se.groupone.ecommerce.model.Order;
import se.groupone.ecommerce.repository.OrderRepository;

import java.util.HashMap;

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

	@SuppressWarnings("unchecked")
	@Override
	public HashMap<Integer, Order> getOrders()
	{
		return (HashMap<Integer, Order>) orders.clone();
	}

	@Override
	public int getHighestId()
	{
		return 0;
	}

}