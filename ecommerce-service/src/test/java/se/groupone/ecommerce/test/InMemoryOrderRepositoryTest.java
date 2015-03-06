package se.groupone.ecommerce.test;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.is;

import org.junit.Before;
import org.junit.Test;

import se.groupone.ecommerce.exception.RepositoryException;
import se.groupone.ecommerce.model.Customer;
import se.groupone.ecommerce.model.Order;
import se.groupone.ecommerce.repository.memory.InMemoryOrderRepository;

public class InMemoryOrderRepositoryTest
{
	private static final Customer CUSTOMER = new Customer("anders", "12", "fjnkds", "anders", "nfdf", "fndfjk", "isnf");

	private static final Order ORDER;
	static
	{
		CUSTOMER.addProductToShoppingCart(0);
		CUSTOMER.addProductToShoppingCart(1);
		CUSTOMER.addProductToShoppingCart(2);
		ORDER = new Order(0, CUSTOMER.getUsername(), CUSTOMER.getShoppingCart());
	}

	InMemoryOrderRepository oR;

	@Before
	public void setup()
	{
		oR = new InMemoryOrderRepository();
	}

	@Test
	public void testAddGetOrder() throws RepositoryException
	{
		oR.addOrder(ORDER);
		
		assertThat(oR.getOrder(ORDER.getId()), is(ORDER));
	}

//	@Test
//	public void testRemoveOrder()
//	{
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testGetOrders()
//	{
//		fail("Not yet implemented");
//	}

	// @Test
	// public void testGetHighestId()
	// {
	// fail("Not yet implemented");
	// }

}
