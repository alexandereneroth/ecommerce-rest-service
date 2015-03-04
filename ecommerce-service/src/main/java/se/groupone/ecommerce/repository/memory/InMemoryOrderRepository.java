package se.groupone.ecommerce.repository.memory;

import se.groupone.ecommerce.exception.RepositoryException;
import se.groupone.ecommerce.model.Customer;
import se.groupone.ecommerce.model.Order;
import se.groupone.ecommerce.repository.OrderRepository;

import java.util.ArrayList;
import java.util.HashMap;

public class InMemoryOrderRepository implements OrderRepository
{
	private HashMap<Integer, Order> orders = new HashMap<Integer, Order>();

	@Override
	public void addOrder(Order order)
	{
//		if (!customer.getShoppingCart().isEmpty())
//		{
//			int orderId;//generated based on getHighestId()
//			Order order = new Order(customer.getUsername(), customer.getShoppingCart());
			orders.put(orderId, order);
			// And clear the shoppingCart
//			customer.getShoppingCart().clear();
//		}
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

	@Override
	public HashMap<Integer, Order> getOrders()
	{
		return orders;
	}

	@Override
	public int getHighestId()
	{
		return 0;
	}

}