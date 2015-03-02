package se.onlinepannkaka.onlineshop.tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.Test;

import se.onlinepannkaka.onlineshop.models.Customer;
import se.onlinepannkaka.onlineshop.models.Order;
import se.onlinepannkaka.onlineshop.models.Product;
import se.onlinepannkaka.onlineshop.repositories.memory.InMemoryCustomers;
import se.onlinepannkaka.onlineshop.repositories.memory.InMemoryOrders;
import se.onlinepannkaka.onlineshop.repositories.memory.InMemoryProducts;
import se.onlinepannkaka.onlineshop.services.ShopService;

public class MainTest 
{

	ShopService ss = new ShopService(new InMemoryCustomers(), new InMemoryProducts(), new InMemoryOrders());
	
	@Test
	public void test()
	{
		assertNotNull(ss);
		
		Customer c1 = new Customer("Steffe", "Keff", "sdgkeff@gmail.com", "Stefan", "De Geer", "Sommarbo 228", "0768646474");
		Customer c2 = new Customer("Be", "oz", "beoz@hotmail.com", "be", "oz", "Nynäsvägen 1", "070123456789");
		
		ss.addCustomer(c1);
		ss.addCustomer(c2);
		
		assertEquals(c1, ss.getCustomer("Steffe"));
		assertEquals(c2, ss.getCustomer("Be"));
		
		Product p1 = new Product("Klassisk pannkaka", "Pannkakor" , "Stefan", "Vår klassiska och mycket utsökta pannkaka", "klassiskPannkaka.png", 10.90, 60);
		Product p2 = new Product("Amerikansk pannkaka", "Pannkakor" , "Erik", "En lite tjockare men mycket god pannkaka som passar till sirap", "amerikanskPannkaka.png", 13.90, 40);
		Product p3 = new Product("Belgisk våffla", "Våfflor", "Osama", "Den belgiska våfflan är lite tjock och mycket frasig", "belgiskVaffla.png", 12.90, 50);
		
		ss.addProduct(p1);
		ss.addProduct(p2);
		ss.addProduct(p3);
		
		assertEquals(p1, ss.getProduct("Klassisk pannkaka"));
		assertEquals(p2, ss.getProduct("Amerikansk pannkaka"));
		assertEquals(p3, ss.getProduct("Belgisk våffla"));
		assertEquals(3, ss.getProducts().size());
		
		assertEquals(0, ss.getCustomer("Steffe").getShoppingCart().size());
		ss.getCustomer("Steffe").addProduct(p1.getTitle());
		ss.getCustomer("Steffe").addProduct(p2.getTitle());
		ss.getCustomer("Steffe").addProduct(p3.getTitle());
		assertEquals(3, ss.getCustomer("Steffe").getShoppingCart().size());
		
		assertEquals(ss.getCustomer("Steffe").getShoppingCart().get(0), p1.getTitle());
		assertEquals(ss.getCustomer("Steffe").getShoppingCart().get(1), p2.getTitle());
		assertEquals(ss.getCustomer("Steffe").getShoppingCart().get(2), p3.getTitle());
		
		assertEquals(ss.getOrders(), new HashMap<String,Order>());
		
		ss.addOrder("Steffe");
		ss.getOrder("Steffe0").shipIt();
		assertEquals(1, ss.getOrders().size());
		assertEquals(1, ss.getCustomer("Steffe").getOrders().size());
		assertEquals(0, ss.getCustomer("Steffe").getShoppingCart().size());
		
		ss.getCustomer("Steffe").addProduct(p1.getTitle());
		ss.removeProduct("Klassisk pannkaka");
		
		assertEquals(new ArrayList<Product>(), ss.getCustomer("Steffe").getShoppingCart());
		
	}

}
