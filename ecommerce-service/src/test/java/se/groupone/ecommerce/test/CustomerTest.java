package se.groupone.ecommerce.test;

import se.groupone.ecommerce.model.*;
import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

public class CustomerTest
{
	Customer cu = new Customer("Kira", "elf", "erik.welander@hotmail.com", "Erik", "Welander", "Tersv", "073");

	@Test
	public void get()
	{
		assertEquals(cu.getUsername(), "Kira");
		assertEquals(cu.getPassword(), "elf");
		assertEquals(cu.getEmail(), "erik.welander@hotmail.com");
		assertEquals(cu.getFirstName(), "Erik");
		assertEquals(cu.getLastName(), "Welander");
		assertEquals(cu.getAddress(), "Tersv");
		assertEquals(cu.getPhoneNumber(), "073");
		assertEquals(cu.toString(), "Erik Welander");
	}

	@Test
	public void set()
	{
		cu.setPassword("Keff");
		cu.setEmail("steffekeff@n");
		cu.setFirstName("Stefan");
		cu.setLastName("De Geer");
		cu.setAddress("SommarBo 228");
		cu.setMobileNumber("0768646474");

		assertEquals(cu.getPassword(), "Keff");
		assertEquals(cu.getEmail(), "steffekeff@n");
		assertEquals(cu.getFirstName(), "Stefan");
		assertEquals(cu.getLastName(), "De Geer");
		assertEquals(cu.getAddress(), "SommarBo 228");
		assertEquals(cu.getPhoneNumber(), "0768646474");
	}

	@Test
	public void assertAddRemoveFromShoppingCartWorks() throws ModelException
	{
		final int dummyProductId1 = 0;
		final int dummyProductId2 = 1;
		
		Product product1 = new Product(dummyProductId1, new ProductParameters("Klassisk pannkaka", "Pannkakor", "Stefan", "Vår klassiska och mycket utsökta pannkaka", "klassiskPannkaka.png", 10.90, 10));
		cu.addProduct(product1.getId());

		ArrayList<Integer> customerCart = cu.getShoppingCart();
		assertEquals((int)customerCart.get(0), product1.getId());
		assertEquals(customerCart.size(), 1);

		Product product2 = new Product(dummyProductId2, new ProductParameters("Amerikansk pannkaka", "Pannkakor", "Erik", "En lite tjockare men mycket god pannkaka som passar till sirap", "amerikanskPannkaka.png",
				13.90, 10));
		
		cu.addProduct(product1.getId());
		cu.addProduct(product2.getId());

		assertEquals((int)customerCart.get(1), product2.getId());
		assertEquals(customerCart.size(), 2);

		cu.removeProduct(product2.getId());
		assertEquals(customerCart.size(), 1);
	}
}
