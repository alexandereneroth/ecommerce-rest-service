// Disabled because mvn install failure

//package se.groupone.ecommerce.test;
//
//import static org.junit.Assert.*;
//import static org.hamcrest.CoreMatchers.is;
//import static org.mockito.Mockito.*;
//
//import java.util.HashMap;
//
//import org.junit.Before;
//import org.junit.After;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.Mock;
//import org.mockito.runners.MockitoJUnitRunner;
//
//import se.groupone.ecommerce.exception.RepositoryException;
//import se.groupone.ecommerce.model.Customer;
//import se.groupone.ecommerce.model.Order;
//import se.groupone.ecommerce.repository.CustomerRepository;
//import se.groupone.ecommerce.repository.OrderRepository;
//import se.groupone.ecommerce.repository.ProductRepository;
//import se.groupone.ecommerce.service.ShopService;
//
//@RunWith(MockitoJUnitRunner.class)
//public class ShopServiceTest
//{
//	private static ShopService shopService;
//
//	@Mock
//	private CustomerRepository cR;
//
//	@Mock
//	private ProductRepository pR;
//
//	@Mock
//	private OrderRepository oR;
//
//	private static final Customer TEST_CUSTOMER_1 = new Customer("kero", "dreamhack",
//			"dsv@su.se", "Kiran", "Arvidsson",
//			"Östgötagatan 5 Stockholm", "0706566556");
//	private static final Customer TEST_CUSTOMER_2 = new Customer("santaclaus", "rudolph",
//			"santa@northpole.com", "Santa", "Claus",
//			"North Pole", "000000000");
//	private static final Customer TEST_CUSTOMER_3 = new Customer("martetheboy", "silkmuj44",
//			"marte_carl@spray.se", "Martin", "Carlsson",
//			"Jumkils-dalkarlsbo Dalsäter 230", "0708663760");
//
//	private static final HashMap<String, Customer> CUSTOMERS = new HashMap<>();
//
//	static
//	{
//		CUSTOMERS.put(TEST_CUSTOMER_1.getUsername(), TEST_CUSTOMER_1);
//		CUSTOMERS.put(TEST_CUSTOMER_2.getUsername(), TEST_CUSTOMER_2);
//		CUSTOMERS.put(TEST_CUSTOMER_3.getUsername(), TEST_CUSTOMER_3);
//	}
//
//	@Before
//	public void init() throws RepositoryException
//	{
//		
//		// Customer method stubbing ////
//		
//		when(cR.getCustomer(TEST_CUSTOMER_1.getUsername())).thenReturn(TEST_CUSTOMER_1);
//		when(cR.getCustomer(TEST_CUSTOMER_2.getUsername())).thenReturn(TEST_CUSTOMER_2);
//		when(cR.getCustomer(TEST_CUSTOMER_3.getUsername())).thenReturn(TEST_CUSTOMER_3);
//
//		when(cR.getCustomers()).thenReturn(CUSTOMERS);
//		
//		// Order method stubbing //// TODO
//		
////		public void addOrder(Order order) throws RepositoryException;
////
////		public Order getOrder(int id) throws RepositoryException;
////
////		public HashMap<Integer, Order> getOrders();
////
////		public int getHighestId();
//		
//		shopService = new ShopService(cR, pR, oR);
//	}
//
//	//  Skapa en ny användare
//	@Test
//	public void canAddCustomer()
//	{
//		Customer testCustomer = new Customer("kero", "dreamhack",
//				"dsv@su.se", "Kiran", "Arvidsson",
//				"Östgötagatan 5 Stockholm", "0706566556");
//
//		fail("unimplemented"); // TODO
//	}
//
//	//  Hämta en användare med ett visst id
//	@Test
//	public void canGetCustomerOfId()
//	{
//		fail("unimplemented"); // TODO
//	}
//
//	//  Skapa en ny användare – detta ska returnera en länk till den skapade
//	// användaren i Location-headern
//	@Test
//	public void assertLocationHeaderOfCreatedCustomerIsCorrect()
//	{
//		fail("unimplemented"); // TODO
//	}
//
//	//  Uppdatera en användare
//	@Test
//	public void canUpdateUser()
//	{
//		fail("unimplemented"); // TODO
//	}
//
//	//  Ta bort en användare (eller sätta den som inaktiv)
//	@Test
//	public void canRemoveCustomer()
//	{
//		fail("unimplemented"); // TODO
//	}
//
//	//  Skapa en order för en användare
//	@Test
//	public void canCreateCustomerOrder()
//	{
//		fail("unimplemented"); // TODO
//	}
//
//	//  Hämta en användares alla order
//	@Test
//	public void canGetCustomerOrders()
//	{
//		fail("unimplemented"); // TODO
//	}
//
//	//  Hämta en viss order för en användare
//	@Test
//	public void canGetCustomerOrder()
//	{
//		fail("unimplemented"); // TODO
//	}
//
//	//  Uppdatera en order för en användare
//	@Test
//	public void canUpdateCustomerOrder()
//	{
//		fail("unimplemented"); // TODO
//	}
//
//	//  Ta bort en order för en användare
//	@Test
//	public void canRemoveCustomerOrder()
//	{
//		fail("unimplemented"); // TODO
//	}
//}
