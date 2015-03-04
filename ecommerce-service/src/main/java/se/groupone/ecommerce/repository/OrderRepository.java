package se.groupone.ecommerce.repository;

import se.groupone.ecommerce.exception.RepositoryException;
import se.groupone.ecommerce.model.Order;

import java.util.HashMap;

public interface OrderRepository
{
	public void addOrder(Order order) throws RepositoryException;

	public Order getOrder(int id) throws RepositoryException;

	public HashMap<Integer, Order> getOrders();

	public int getHighestId();
}