package se.groupone.ecommerce.repository;

import se.groupone.ecommerce.exception.RepositoryException;
import se.groupone.ecommerce.model.Customer;

import java.util.HashMap;

public interface CustomerRepository
{
	public void addCustomer(Customer customer) throws RepositoryException;

	public Customer getCustomer(String customerUsername) throws RepositoryException;

	public HashMap<String, Customer> getCustomers() throws RepositoryException;

	public void updateCustomer(Customer customer) throws RepositoryException;

	public void removeCustomer(String username) throws RepositoryException;

	public int getHighestId();
}
