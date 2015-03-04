package se.groupone.ecommerce.repository.memory;

import se.groupone.ecommerce.model.Customer;
import se.groupone.ecommerce.repository.CustomerRepository;
import java.util.HashMap;

public class InMemoryCustomers implements CustomerRepository
{

	private HashMap<String, Customer> accounts = new HashMap<String, Customer>();

	@Override
	public boolean addCustomer(Customer customer)
	{
		if (!accounts.containsKey(customer.getUsername()))
		{
			accounts.put(customer.getUsername(), customer);
			return true;
		}
		return false;
	}

	@Override
	public Customer getCustomer(String username)
	{
		if (accounts.containsKey(username))
		{
			return accounts.get(username);
		}
		return new Customer("", "", "", "", "", "", ""); // Will return an empty
															// TODO
															// customer to
															// counter
															// nullPointer
	}

	@Override
	public HashMap<String, Customer> getCustomers()
	{
		return accounts;
	}

	@Override
	public void updateCustomer(Customer customer)
	{
		accounts.replace(customer.getUsername(), customer);
	}

	@Override
	public boolean removeCustomer(String username)
	{
		if (accounts.containsKey(username))
		{
			accounts.remove(username);
			return true;
		}
		return false;
	}

}
