package se.onlinepannkaka.onlineshop.repositories;

import java.util.HashMap;

import se.onlinepannkaka.onlineshop.models.Customer;

public interface CustomerRepository 
{
	
	public void addCustomer(Customer customer);
	
	public Customer getCustomer(String username);
	
	public HashMap<String, Customer> getCustomers();
	
	public void updateCustomer(Customer customer);
	
	public void removeCustomer(String username);
	
}
