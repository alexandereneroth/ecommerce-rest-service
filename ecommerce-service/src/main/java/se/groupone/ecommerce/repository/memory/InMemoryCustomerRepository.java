package se.groupone.ecommerce.repository.memory;

import se.groupone.ecommerce.exception.RepositoryException;
import se.groupone.ecommerce.model.Customer;
import se.groupone.ecommerce.repository.CustomerRepository;

import java.util.HashMap;

public class InMemoryCustomerRepository implements CustomerRepository
{

	private HashMap<String, Customer> accounts = new HashMap<String, Customer>();

	@Override
	public void addCustomer(Customer customer) throws RepositoryException
	{
		if (accounts.containsKey(customer.getUsername()))
		{
			throw new RepositoryException("Could not add customer: customer already exists");
		}
		accounts.put(customer.getUsername(), customer);
	}

	@Override
	public Customer getCustomer(String username) throws RepositoryException
	{
		if (accounts.containsKey(username))
		{
			return accounts.get(username);
		}
		throw new RepositoryException("Could not get customer: customer goes not exist");
	}

	@SuppressWarnings("unchecked")
	@Override
	public HashMap<String, Customer> getCustomers()
	{
		return (HashMap<String, Customer>) accounts.clone();
	}

	@Override
	public void updateCustomer(Customer customer)
	{
		accounts.replace(customer.getUsername(), customer);
	}

	@Override
	public void removeCustomer(String username) throws RepositoryException
	{
		if (accounts.containsKey(username))
		{
			accounts.remove(username);
			return;
		}
		throw new RepositoryException("Could not remove customer: customer does not exist.");
	}

}
