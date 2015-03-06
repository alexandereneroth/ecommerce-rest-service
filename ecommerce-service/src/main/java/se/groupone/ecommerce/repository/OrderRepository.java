package se.groupone.ecommerce.repository;

import se.groupone.ecommerce.exception.RepositoryException;
import se.groupone.ecommerce.model.Order;

import java.util.List;

public interface OrderRepository
{
	public void addOrder(Order order) throws RepositoryException;

	public Order getOrder(int id) throws RepositoryException;
	
	public void removeOrder(int id) throws RepositoryException;

	public List<Order> getOrders();

	public int getHighestId();
}