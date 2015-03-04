package se.groupone.ecommerce.repository;

import se.groupone.ecommerce.model.Customer;
import java.util.HashMap;

public interface CustomerRepository
{
	public boolean addCustomer(Customer customer);

	public Customer getCustomer(String username);

	public HashMap<String, Customer> getCustomers();

	public void updateCustomer(Customer customer);

	public boolean removeCustomer(String username);

}
