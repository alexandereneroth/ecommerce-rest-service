package se.groupone.ecommerce.repository.memory;

import se.groupone.ecommerce.model.Customer;
import se.groupone.ecommerce.model.Order;
import se.groupone.ecommerce.repository.OrderRepository;
import java.util.ArrayList;
import java.util.HashMap;

public class InMemoryOrders implements OrderRepository
{
	private HashMap<String, Order> orders = new HashMap<String, Order>();

	@Override
	public void addOrder(Customer customer)
	{
		if (!customer.getShoppingCart().isEmpty())
		{
			orders.put(customer.getUsername().concat(new Integer(customer.getOrders().size() + 1).toString()),
					new Order(customer.getUsername(), customer.getShoppingCart()));
			// Will also add the order to the customer
			customer.addOrder();
			// And clear the shoppingCart
			customer.getShoppingCart().clear();
		}
	}

	@Override
	public Order getOrder(String key)
	{
		if (orders.containsKey(key))
		{
			return orders.get(key);
		}
		return new Order("", new ArrayList<String>()); // Will return an empty
														// order if key doesnt
														// exist
	}

	@Override
	public HashMap<String, Order> getOrders()
	{
		return orders;
	}

}