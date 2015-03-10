//
//package se.groupone.ecommerce.test;
//import static org.junit.Assert.*;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import org.junit.FixMethodOrder;
//import org.junit.Test;
//import org.junit.runners.MethodSorters;
//
//import se.groupone.ecommerce.exception.RepositoryException;
//import se.groupone.ecommerce.model.Customer;
//import se.groupone.ecommerce.model.Product;
//import se.groupone.ecommerce.model.ProductParameters;
//import se.groupone.ecommerce.repository.sql.SQLCustomer;
//@FixMethodOrder(MethodSorters.NAME_ASCENDING)
//public class SQLCustomerTest
//{
//	final String username = "kira";
//	final String password = "Elf";
//	final String email = "erik.welander@hotmailcom";
//	final String firstName = "Erik";
//	final String lastName = "Welander";
//	final String address = "Ter";
//	final String mobile = "073";
//	
//	final String username2 = "rednalew";
//	final String password2 = "Albin1";
//	final String email2 = "viktor.welander@hotmail.com";
//	final String firstName2 = "Viktor";
//	final String lastName2 = "Welander";
//	final String address2 = "Ter2";
//	final String mobile2 = "0732";
//	
//	Customer cu = new Customer(username, password, email, firstName, lastName, address, mobile);
//	Customer cu2 = new Customer(username2, password2, email2, firstName2, lastName2, address2, mobile2); 
//	
//	final int pID = 1;
//	final String pName = "Voffla";
//	final String pCategory = "Bakverk";
//	final String pManufacturer = "Steffe";
//	final String pDescription = "En våffa gjord av steffe!";
//	final String pImg = "delicious.png";
//	final double pPrice = 10;
//	final int pQuantity = 100;
//	
//	final int pID2 = 2;
//	final String pName2 = "Pannkaka";
//	final String pCategory2 = "Bakverk";
//	final String pManufacturer2 = "SteffeKeff";
//	final String pDescription2 = "En pannkaka gjord av steffe!";
//	final String pImg2 = "delicious2.png";
//	final double pPrice2 = 5;
//	final int pQuantity2 = 200;
//	
//	final Product p1 = new Product(pID,
//			     new ProductParameters(pName,
//									   pCategory,
//									   pManufacturer,
//									   pDescription,
//									   pImg,
//									   pPrice,
//								       pQuantity));
//	
//	final Product p2 = new Product(pID2,
//			     new ProductParameters(pName2,
//							    	   pCategory2,
//									   pManufacturer2,
//									   pDescription2,
//									   pImg2,
//									   pPrice2,
//									   pQuantity2));
//	
//	
//	@Test
//	public void a_addCustomers() throws RepositoryException
//	{
//		SQLCustomer sqlcu = new SQLCustomer();
//		sqlcu.addCustomer(cu);
//		sqlcu.addCustomer(cu2);
//		
//		assertEquals(cu, sqlcu.getCustomer(cu.getUsername()));
//		assertEquals(cu2, sqlcu.getCustomer(cu2.getUsername()));
//	}
//	
//	@Test
//	public void b_addItems() throws RepositoryException
//	{
//		SQLCustomer sqlcu = new SQLCustomer();
//		cu.addProductToShoppingCart(p1.getId());
//		cu2.addProductToShoppingCart(p1.getId());
//		cu2.addProductToShoppingCart(p2.getId());
//		
//		assertNotEquals(cu, sqlcu.getCustomer(cu.getUsername()));
//		assertNotEquals(cu, sqlcu.getCustomer(cu.getUsername()));
//	}
//	
//	@Test
//	public void c_updateCustomer() throws RepositoryException
//	{
//		SQLCustomer sqlcu = new SQLCustomer();
//		sqlcu.updateCustomer(cu);
//		sqlcu.updateCustomer(cu2);
//		
//		assertEquals(cu, sqlcu.getCustomer(cu.getUsername()));
//		assertEquals(cu2, sqlcu.getCustomer(cu2.getUsername()));
//		
//		Customer testCustomer = new Customer(cu.getUsername(),
//											 cu2.getPassword(),
//											 cu2.getEmail(),
//											 cu2.getFirstName(),
//											 cu2.getLastName(),
//											 cu2.getAddress(),
//											 cu2.getPhoneNumber());
//		sqlcu.updateCustomer(testCustomer);
//		assertEquals(testCustomer, sqlcu.getCustomer(cu.getUsername()));
//		sqlcu.updateCustomer(cu);
//		assertEquals(cu, sqlcu.getCustomer(cu.getUsername()));	
//	}
//	
//	
//	@Test
//	public void c_getCustomers() throws RepositoryException
//	{
//		SQLCustomer sqlcu = new SQLCustomer();
//		List<Customer> allCustomers = new ArrayList<>();
//		allCustomers.add(cu);
//		allCustomers.add(cu2);
//		assertEquals(allCustomers, sqlcu.getCustomers());
//	}
//	
//	@Test
//	public void e_removeCustomers() throws RepositoryException
//	{
//		SQLCustomer sqlcu = new SQLCustomer();
//		sqlcu.removeCustomer(cu.getUsername());
//		sqlcu.removeCustomer(cu2.getUsername());
//	}
//	
//	@Test (expected=Exception.class)
//	public void f_getCustomerThatDoesNotExist() throws RepositoryException
//	{
//		SQLCustomer sqlcu = new SQLCustomer();
//		sqlcu.getCustomer(cu2.getUsername());
//	}
//}
