package se.groupone.ecommerce.repository.memory;

import se.groupone.ecommerce.exception.RepositoryException;
import se.groupone.ecommerce.model.Customer;
import se.groupone.ecommerce.repository.CustomerRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InMemoryCustomerRepository implements CustomerRepository
{

	private HashMap<String, Customer> customers = new HashMap<String, Customer>();

	@Override
	public void addCustomer(Customer customer) throws RepositoryException
	{
		if (customers.containsKey(customer.getUsername()))
		{
			throw new RepositoryException("Could not add customer: customer already exists");
		}
		customers.put(customer.getUsername(), customer);
	}

	@Override
	public Customer getCustomer(String username) throws RepositoryException
	{
		if (customers.containsKey(username))
		{
			return customers.get(username);
		}
		throw new RepositoryException("Could not get customer: customer does not exist");
	}

	@Override
	public List<Customer> getCustomers()
	{
		return new ArrayList<Customer>(customers.values());
	}

	@Override
	public void updateCustomer(Customer customer) throws RepositoryException
	{
		if (customers.containsKey(customer.getUsername()))
		{
			customers.replace(customer.getUsername(), customer);
		}
		else
		{
			throw new RepositoryException("Could not update customer: customer does not exist");
		}
	}

	@Override
	public void removeCustomer(String username) throws RepositoryException
	{
		if (customers.containsKey(username))
		{
			customers.remove(username);
			return;
		}
		throw new RepositoryException("Could not remove customer: customer does not exist.");
	}

}
