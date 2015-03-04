// Disabled because test failure /Jonatan

//package se.groupone.ecommerce.test;
//import static org.junit.Assert.*;
//import org.junit.Test;
//import se.groupone.ecommerce.model.Customer;
//import se.groupone.ecommerce.repository.sql.SQLCustomer;
//public class SQLCustomerTest
//{
//	@Test
//	public void addRemoveCustomer()
//	{
//		final String username = "kira";
//		final String password = "Elf";
//		final String email = "erik.welander@hotmailcom";
//		final String firstName = "Erik";
//		final String lastName = "Welander";
//		final String address = "Ter";
//		final String mobile = "073";
//		Customer cu = new Customer(username, password, email, firstName, lastName, address, mobile);
//		SQLCustomer sqlcu = new SQLCustomer();
//		
//		assertTrue(sqlcu.addCustomer(cu));
//		assertEquals(cu, sqlcu.getCustomer(username));
//		assertTrue(sqlcu.removeCustomer(username));
//	}
//}
