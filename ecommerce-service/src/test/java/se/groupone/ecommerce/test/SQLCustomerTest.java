/*
package se.groupone.ecommerce.test;
import static org.junit.Assert.*;

import java.util.HashMap;

import org.junit.Test;
import org.junit.rules.ExpectedException;

import se.groupone.ecommerce.exception.RepositoryException;
import se.groupone.ecommerce.model.Customer;
import se.groupone.ecommerce.repository.sql.SQLCustomer;
public class SQLCustomerTest
{
	final String username = "kira";
	final String password = "Elf";
	final String email = "erik.welander@hotmailcom";
	final String firstName = "Erik";
	final String lastName = "Welander";
	final String address = "Ter";
	final String mobile = "073";
	
	final String username2 = "rednalew";
	final String password2 = "Albin1";
	final String email2 = "viktor.welander@hotmail.com";
	final String firstName2 = "Viktor";
	final String lastName2 = "Welander";
	final String address2 = "Ter2";
	final String mobile2 = "0732";
	
	Customer cu = new Customer(username, password, email, firstName, lastName, address, mobile);
	Customer cu2 = new Customer(username2, password2, email2, firstName2, lastName2, address2, mobile2); 
	
	
	@Test
	public void addCustomers() throws RepositoryException
	{
		SQLCustomer sqlcu = new SQLCustomer();
		
		sqlcu.addCustomer(cu);
		sqlcu.addCustomer(cu2);
	}
	
	@Test
	public void getCustomer() throws RepositoryException
	{
		SQLCustomer sqlcu = new SQLCustomer();
		assertEquals(cu, sqlcu.getCustomer(cu.getUsername()));
		assertEquals(cu2, sqlcu.getCustomer(cu2.getUsername()));
	}
	
	@Test
	public void getCustomers() throws RepositoryException
	{
		SQLCustomer sqlcu = new SQLCustomer();
		HashMap<String, Customer> allCustomers = new HashMap<>();
		allCustomers.put(cu.getUsername(), cu);
		allCustomers.put(cu2.getUsername(), cu2);
		
		assertEquals(allCustomers, sqlcu.getCustomers());
	}
	
	@Test
	public void updateCustomer() throws RepositoryException
	{
		SQLCustomer sqlcu = new SQLCustomer();
		
		Customer testCustomer = new Customer(cu.getUsername(),
											 cu2.getPassword(),
											 cu2.getEmail(),
											 cu2.getFirstName(),
											 cu2.getLastName(),
											 cu2.getAddress(),
											 cu2.getPhoneNumber());
		sqlcu.updateCustomer(testCustomer);
		
		assertEquals(testCustomer, sqlcu.getCustomer(cu.getUsername()));
		
		sqlcu.updateCustomer(cu);
		
		assertEquals(cu, sqlcu.getCustomer(cu.getUsername()));		
		
	}
	
	@Test
	public void removeCustomer() throws RepositoryException
	{
		SQLCustomer sqlcu = new SQLCustomer();
		sqlcu.removeCustomer(cu2.getUsername());
	}
	
	@Test (expected=RepositoryException.class)
	public void getCustomerThatDoesNotExist() throws RepositoryException
	{
		SQLCustomer sqlcu = new SQLCustomer();
		sqlcu.getCustomer(cu2.getUsername());
	}
	
	
	//@Test
	//public void removeAllCustomers() throws RepositoryException
	//{
	//	SQLCustomer sqlcu = new SQLCustomer();
	//	sqlcu.removeCustomer("*");
	//}
	
}
*/