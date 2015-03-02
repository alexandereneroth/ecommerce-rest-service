package se.onlinepannkaka.onlineshop.repositories.memory;
import java.util.HashMap;

import se.onlinepannkaka.onlineshop.models.Customer;
import se.onlinepannkaka.onlineshop.repositories.CustomerRepository;

public class InMemoryCustomers implements CustomerRepository 
{

	private HashMap<String, Customer> accounts = new HashMap<String, Customer>();
	
	@Override
	public void addCustomer(Customer customer)
	{
		if(!accounts.containsKey(customer.getUsername()))
		{
			accounts.put(customer.getUsername(), customer);
		}
	}

	@Override
	public Customer getCustomer(String username) 
	{
		if(accounts.containsKey(username))
		{
			return accounts.get(username);
		}
		return new Customer("","","","","","",""); //Will return an empty customer to counter nullPointer
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
	public void removeCustomer(String username) 
	{
		accounts.remove(username);
	}

}
