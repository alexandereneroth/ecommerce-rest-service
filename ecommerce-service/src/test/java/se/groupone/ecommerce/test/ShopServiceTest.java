package se.groupone.ecommerce.test;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import se.groupone.ecommerce.exception.RepositoryException;
import se.groupone.ecommerce.model.Customer;
import se.groupone.ecommerce.model.Order;
import se.groupone.ecommerce.model.Product;
import se.groupone.ecommerce.model.ProductParameters;
import se.groupone.ecommerce.repository.CustomerRepository;
import se.groupone.ecommerce.repository.OrderRepository;
import se.groupone.ecommerce.repository.ProductRepository;
import se.groupone.ecommerce.service.ShopService;

@RunWith(MockitoJUnitRunner.class)
public class ShopServiceTest
{
	private static ShopService shopService;

	@Mock
	private CustomerRepository cR;

	@Mock
	private ProductRepository pR;

	@Mock
	private OrderRepository oR;

	// // PRODUCTS ////
	// ProductsParameters
	private static final ProductParameters PRODUCT_PARAMETERS_TOMATO = new ProductParameters("Tomato", "Vedgetables", "Spain", "A beautiful tomato",
			"http://google.com/tomato.jpg", 45, 5);
	private static final ProductParameters PRODUCT_PARAMETERS_LETTUCE = new ProductParameters("Lettuce", "Vedgetables", "France", "A mound of lettuce",
			"http://altavista.com/lettuce.jpg", 88, 2);

	// ProductIDs
	private static final int PRODUCT_ID_TOMATO = 0;
	private static final int PRODUCT_ID_LETTUCE = 1;
	// Products
	private static final Product PRODUCT_TOMATO = new Product(PRODUCT_ID_TOMATO, PRODUCT_PARAMETERS_TOMATO);
	private static final Product PRODUCT_LETTUCE = new Product(PRODUCT_ID_LETTUCE, PRODUCT_PARAMETERS_LETTUCE);
	// Product collection
	private static final List<Product> PRODUCTS = new ArrayList<>();

	// // CUSTOMERS ////
	private static final Customer CUSTOMER_1 = new Customer("kero", "dreamhack",
			"dsv@su.se", "Kiran", "Arvidsson",
			"Östgötagatan 5 Stockholm", "0706566556");
	private static final Customer CUSTOMER_2 = new Customer("santaclaus", "rudolph",
			"santa@northpole.com", "Santa", "Claus",
			"North Pole", "000000000");
	private static final Customer CUSTOMER_3 = new Customer("martetheboy", "silkmuj44",
			"marte_carl@spray.se", "Martin", "Carlsson",
			"Jumkils-dalkarlsbo Dalsäter 230", "0708663760");
	// Customers
	private static final List<Customer> CUSTOMERS = new ArrayList<>();

	// // ORDERS ////
	// Order IDs
	private static final int ORDER_ID_TWO_TOMATOES = 0;
	// Ordered products
	private static final ArrayList<Integer> ORDER_PRODUCTS_TWO_TOMATOES = new ArrayList<Integer>();
	private static final Order ORDER_TWO_TOMATOES;

	private static final List<Order> ORDERS = new ArrayList<>();

	static
	{
		PRODUCTS.add(PRODUCT_TOMATO);
		PRODUCTS.add(PRODUCT_LETTUCE);

		CUSTOMER_1.addProductToShoppingCart(PRODUCT_ID_LETTUCE);
		CUSTOMER_1.addProductToShoppingCart(PRODUCT_ID_LETTUCE);
		CUSTOMER_2.addProductToShoppingCart(PRODUCT_ID_LETTUCE);
		CUSTOMER_2.addProductToShoppingCart(PRODUCT_ID_LETTUCE);
		CUSTOMER_3.addProductToShoppingCart(PRODUCT_ID_LETTUCE);
		CUSTOMER_3.addProductToShoppingCart(PRODUCT_ID_LETTUCE);

		CUSTOMERS.add(CUSTOMER_1);
		CUSTOMERS.add(CUSTOMER_2);
		CUSTOMERS.add(CUSTOMER_3);

		ORDER_PRODUCTS_TWO_TOMATOES.add(PRODUCT_ID_TOMATO);
		ORDER_PRODUCTS_TWO_TOMATOES.add(PRODUCT_ID_TOMATO);
		ORDER_TWO_TOMATOES = new Order(ORDER_ID_TWO_TOMATOES, CUSTOMER_1.getUsername(), ORDER_PRODUCTS_TWO_TOMATOES);
		ORDERS.add(ORDER_TWO_TOMATOES);
	}

	@Before
	public void setUp() throws RepositoryException
	{

		// Product metod stubbing ////

		when(pR.getProduct(PRODUCT_ID_TOMATO)).thenReturn(PRODUCT_TOMATO);

		when(pR.getProducts()).thenReturn(PRODUCTS);

		when(pR.getHighestId()).thenReturn(0);

		shopService = new ShopService(cR, pR, oR);

		// Customer method stubbing ////

		when(cR.getCustomer(CUSTOMER_1.getUsername())).thenReturn(CUSTOMER_1);
		when(cR.getCustomer(CUSTOMER_2.getUsername())).thenReturn(CUSTOMER_2);
		when(cR.getCustomer(CUSTOMER_3.getUsername())).thenReturn(CUSTOMER_3);

		when(cR.getCustomers()).thenReturn(CUSTOMERS);

		// Order method stubbing ////

		when(oR.getOrder(ORDER_ID_TWO_TOMATOES)).thenReturn(ORDER_TWO_TOMATOES);
		when(oR.getOrders()).thenReturn(ORDERS);
		when(oR.getHighestId()).thenReturn(0);
	}

	@After
	public void tearDown()
	{
		reset(cR, pR, oR);
	}

	@Test
	public void shopServiceTest() throws RepositoryException
	{
		verify(pR, times(1)).getHighestId();
		verify(oR, times(1)).getHighestId();
	}

	@Test
	public void testAddProduct() throws RepositoryException
	{
		shopService.addProduct(PRODUCT_PARAMETERS_TOMATO);

		verify(pR, times(1)).addProduct(any());
	}

	@Test
	public void testAddProductToCustomerIntString() throws RepositoryException
	{
		shopService.addProductToCustomer(PRODUCT_ID_TOMATO, CUSTOMER_1.getUsername());
		verify(cR, times(1)).updateCustomer(any());
	}

	@Test
	public void testAddProductToCustomerIntStringInt() throws RepositoryException
	{
		shopService.addProductToCustomer(PRODUCT_ID_TOMATO, CUSTOMER_1.getUsername(), 5);
		verify(cR, times(1)).updateCustomer(any());
	}

	@Test
	public void testGetProductWithId() throws RepositoryException
	{
		Product product = shopService.getProductWithId(PRODUCT_ID_TOMATO);

		verify(pR, times(1)).getProduct(PRODUCT_ID_TOMATO);
		assertThat(product, is(PRODUCT_TOMATO));
	}

	@Test
	public void testGetProducts() throws RepositoryException
	{
		List<Product> products = shopService.getProducts();

		verify(pR, times(1)).getProducts();
		assertThat(products, is(PRODUCTS));
	}

	@Test
	public void testRemoveProduct() throws RepositoryException
	{
		shopService.removeProduct(PRODUCT_ID_LETTUCE);

		verify(cR, times(1)).getCustomers();

		// update all three customers, because all had lettuce
		// that was removed from their shopping carts
		verify(cR, times(1)).updateCustomer(CUSTOMER_1);
		verify(cR, times(1)).updateCustomer(CUSTOMER_2);
		verify(cR, times(1)).updateCustomer(CUSTOMER_3);

		verify(pR, times(1)).removeProduct(PRODUCT_ID_LETTUCE);
	}

	@Test
	public void testUpdateProduct() throws RepositoryException
	{
		shopService.updateProduct(PRODUCT_ID_LETTUCE, PRODUCT_PARAMETERS_LETTUCE);

		verify(pR).updateProduct(any());
	}

	@Test
	public void testAddCustomer() throws RepositoryException
	{
		shopService.addCustomer(CUSTOMER_1);

		verify(cR).addCustomer(CUSTOMER_1);
	}

	@Test
	public void testGetCustomer() throws RepositoryException
	{
		Customer returnedCustomer = shopService.getCustomer(CUSTOMER_3.getUsername());

		verify(cR, times(1)).getCustomer(CUSTOMER_3.getUsername());
		assertThat(returnedCustomer, is(CUSTOMER_3));
	}

	@Test
	public void testUpdateCustomer() throws RepositoryException
	{
		shopService.updateCustomer(CUSTOMER_2);

		verify(cR, times(1)).updateCustomer(CUSTOMER_2);
	}

	@Test
	public void testRemoveCustomer() throws RepositoryException
	{
		shopService.removeCustomer(CUSTOMER_3.getUsername());

		verify(cR, times(1)).removeCustomer(CUSTOMER_3.getUsername());
	}

	@Test
	public void testCreateOrder() throws RepositoryException
	{
		String customer2Username = CUSTOMER_2.getUsername();

		shopService.createOrder(customer2Username);

		// Get the ordering customer

		verify(cR, times(1)).getCustomer(customer2Username);

		// Decrease stock count of the ordered products
		verify(pR, times(1)).decreaseQuantityOfProductsByOne(any());

		// Place order
		verify(oR).addOrder(any());

		// Update the ordering customer after his/her shopping cart
		// have been emptied.
		verify(cR).updateCustomer(CUSTOMER_2);
	}

	@Test
	public void testGetOrder() throws RepositoryException
	{
		Order returnedOrder = shopService.getOrder(ORDER_ID_TWO_TOMATOES);

		verify(oR).getOrder(ORDER_ID_TWO_TOMATOES);
		assertThat(returnedOrder, is(ORDER_TWO_TOMATOES));
	}

	@Test
	public void testGetOrders()
	{
		List<Order> returnedOrders = shopService.getOrders();

		verify(oR).getOrders();
		assertThat(returnedOrders, is(ORDERS));
	}

}