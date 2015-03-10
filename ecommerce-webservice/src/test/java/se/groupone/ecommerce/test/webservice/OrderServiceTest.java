package se.groupone.ecommerce.test.webservice;

import static org.junit.Assert.assertEquals;

import se.groupone.ecommerce.model.Customer;
import se.groupone.ecommerce.model.Order;
import se.groupone.ecommerce.model.Product;
import se.groupone.ecommerce.model.ProductParameters;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import se.groupone.ecommerce.webservice.util.CustomerMapper;
import se.groupone.ecommerce.webservice.util.IntegerListMapper;
import se.groupone.ecommerce.webservice.util.OrderMapper;
import se.groupone.ecommerce.webservice.util.ProductMapper;
import se.groupone.ecommerce.webservice.util.ProductParamMapper;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class OrderServiceTest
{
	private static final String HOST_NAME = "localhost";
	private static final int HOST_IP = 8080;
	private static final String PROJECT_NAME = "ecommerce-webservice";
	private static final String URL_BASE = "http://" + HOST_NAME + ":" + HOST_IP + "/"
			+ PROJECT_NAME;
	private static final String CUSTOMERS_URL = URL_BASE + "/customers";
	private static final String PRODUCTS_URL = URL_BASE + "/products";
	private static final String ORDERS_URL = URL_BASE + "/orders";
	private static final Client client = ClientBuilder.newBuilder()
			.register(CustomerMapper.class)
			.register(IntegerListMapper.class)
			.register(ProductMapper.class)
			.register(ProductParamMapper.class)
			.register(OrderMapper.class)
			.build();
	private static final WebTarget CUSTOMERS_TARGET;
	private static final WebTarget PRODUCTS_TARGET;
	private static final WebTarget ORDERS_TARGET;
	private static final Customer CUSTOMER1;
	private static final Customer CUSTOMER2;
	private static final ProductParameters PRODUCT_PARAMETERS_TOMATO;
	private static final ProductParameters PRODUCT_PARAMETERS_LETTUCE;
	Response createProductResponse2;
	Response createProductResponse1;
	Response createCustomerResponse;

	static
	{
		CUSTOMERS_TARGET = client.target(CUSTOMERS_URL);
		PRODUCTS_TARGET = client.target(PRODUCTS_URL);
		ORDERS_TARGET = client.target(ORDERS_URL);
		CUSTOMER1 = new Customer("tom", "password", "email@email.com", "Tomcat", "Blackmore", "C3LStreet", "123456");
		CUSTOMER2 = new Customer("alex", "password", "alex@email.com", "Alexander", "Sol", "Banangatan 1", "543211");

		PRODUCT_PARAMETERS_TOMATO = new ProductParameters("Tomato", "Vegetables", "Spain", "A beautiful tomato",
				"http://google.com/tomato.jpg", 45, 5);
		PRODUCT_PARAMETERS_LETTUCE = new ProductParameters("Lettuce", "Vegetables", "France", "A mound of lettuce",
				"http://altavista.com/lettuce.jpg", 88, 2);
	}

	@Before
	public void init()
	{
		// POST - Create products
		createProductResponse1 = PRODUCTS_TARGET.request(MediaType.APPLICATION_JSON)
				.buildPost(Entity.entity(PRODUCT_PARAMETERS_TOMATO, MediaType.APPLICATION_JSON))
				.invoke();
		assertEquals(201, createProductResponse1.getStatus());

		createProductResponse2 = PRODUCTS_TARGET.request(MediaType.APPLICATION_JSON)
				.buildPost(Entity.entity(PRODUCT_PARAMETERS_LETTUCE, MediaType.APPLICATION_JSON))
				.invoke();
		assertEquals(201, createProductResponse2.getStatus());
		
		// POST - Create customer
		createCustomerResponse = CUSTOMERS_TARGET.request(MediaType.APPLICATION_JSON)
				.buildPost(Entity.entity(CUSTOMER2, MediaType.APPLICATION_JSON))
				.invoke();
		assertEquals(201, createCustomerResponse.getStatus());
	}

	@After
	public void tearDown()
	{
		WebTarget admin = client.target(URL_BASE + "/admin");
		admin.request().buildPost(Entity.entity("reset-repo", MediaType.TEXT_HTML)).invoke();
	}
	
	//  Skapa en order för en användare
	@Test
	public void canCreateCustomerOrder() throws IOException
	{
		final Product PRODUCT_TOMATO = client.target(createProductResponse1.getLocation())
				.request(MediaType.APPLICATION_JSON)
				.get(Product.class);
		
		// POST - Add products to cart
		final Response addProductsToCartResponse = CUSTOMERS_TARGET
				.path(CUSTOMER2.getUsername())
				.path("cart")
				.request()
				.buildPost(Entity.entity(Integer.toString(PRODUCT_TOMATO.getId()), MediaType.APPLICATION_JSON))
				.invoke();
		assertEquals(201, addProductsToCartResponse.getStatus());
		
		// POST - Create order
		final Response createOrderResponse = ORDERS_TARGET
				.request()
				.buildPost(Entity.entity(CUSTOMER2.getUsername(), MediaType.APPLICATION_JSON))
				.invoke();
		assertEquals(201, createOrderResponse.getStatus());
		
		// GET - Retrieve created order and check contents
		final Order createdOrder = client.target(createOrderResponse.getLocation())
				.request()
				.get(Order.class);
		
		assertEquals(PRODUCT_TOMATO.getId(), (int)createdOrder.getProductIds().get(0));
	}

	//  Uppdatera en order för en användare
	@Test
	public void canUpdateCustomerOrder()
	{
		final Product PRODUCT_TOMATO = client.target(createProductResponse1.getLocation())
				.request(MediaType.APPLICATION_JSON)
				.get(Product.class);
		
		// POST - Add products to cart
		final Response addProductsToCartResponse = CUSTOMERS_TARGET
				.path(CUSTOMER2.getUsername())
				.path("cart")
				.request()
				.buildPost(Entity.entity(Integer.toString(PRODUCT_TOMATO.getId()), MediaType.APPLICATION_JSON))
				.invoke();
		assertEquals(201, addProductsToCartResponse.getStatus());
		
		// POST - Create order
		final Response createOrderResponse = ORDERS_TARGET
				.request()
				.buildPost(Entity.entity(CUSTOMER2.getUsername(), MediaType.APPLICATION_JSON))
				.invoke();
		assertEquals(201, createOrderResponse.getStatus());
		
		// GET - Retrieve created order and check contents
		final Order createdOrder = client.target(createOrderResponse.getLocation())
				.request()
				.get(Order.class);
		
		assertEquals(PRODUCT_TOMATO.getId(), (int)createdOrder.getProductIds().get(0));
		
		// PUT - Update created order
		ArrayList<Integer> newShoppingCart = new ArrayList<Integer>();
		newShoppingCart.add(PRODUCT_TOMATO.getId());
		newShoppingCart.add(PRODUCT_TOMATO.getId());
		Order updatedOrder = new Order(createdOrder.getId(), CUSTOMER2.getUsername(), newShoppingCart);
		
		final Response updateOrderResponse = ORDERS_TARGET
				.request()
				.buildPut(Entity.entity(updatedOrder, MediaType.APPLICATION_JSON))
				.invoke();
		assertEquals(200, updateOrderResponse.getStatus());
		
		// GET - Get updated order and compare shoppingcarts
		final Order updatedOrderFromServer = client.target(createOrderResponse.getLocation())
				.request()
				.get(Order.class);
		assertEquals(updatedOrder.getProductIds(), updatedOrderFromServer.getProductIds());
	}

	//  Ta bort en order för en användare
	@Test
	public void canRemoveCustomerOrder()
	{ 
		final Product PRODUCT_TOMATO = client.target(createProductResponse1.getLocation())
				.request(MediaType.APPLICATION_JSON)
				.get(Product.class);
		
		// POST - Add products to cart
		final Response addProductsToCartResponse = CUSTOMERS_TARGET
				.path(CUSTOMER2.getUsername())
				.path("cart")
				.request()
				.buildPost(Entity.entity(Integer.toString(PRODUCT_TOMATO.getId()), MediaType.APPLICATION_JSON))
				.invoke();
		assertEquals(201, addProductsToCartResponse.getStatus());
		
		// POST - Create order
		final Response createOrderResponse = ORDERS_TARGET
				.request()
				.buildPost(Entity.entity(CUSTOMER2.getUsername(), MediaType.APPLICATION_JSON))
				.invoke();
		assertEquals(201, createOrderResponse.getStatus());
		
		WebTarget newOrderTarget = client.target(createOrderResponse.getLocation());
		
		// GET - Retrieve created order and check contents
		final Order createdOrder = newOrderTarget
				.request()
				.get(Order.class);
		assertEquals(PRODUCT_TOMATO.getId(), (int)createdOrder.getProductIds().get(0));
		
		// DELETE - Delete created order
		final Response deleteOrderResponse = newOrderTarget
				.request()
				.delete();
		assertEquals(204, deleteOrderResponse.getStatus());
		
		// GET - Try to retrieve deleted order, should fail
		final Response thisShouldFailResponse = newOrderTarget
				.request()
				.get();
		assertEquals(400, thisShouldFailResponse.getStatus());
	}
	
	private void addOrder(Customer customer) 
	{
		final Product PRODUCT_TOMATO = client.target(createProductResponse1.getLocation())
				.request(MediaType.APPLICATION_JSON)
				.get(Product.class);
		
		// POST - Add products to cart
		final Response addProductsToCartResponse = CUSTOMERS_TARGET
				.path(customer.getUsername())
				.path("cart")
				.request()
				.buildPost(Entity.entity(Integer.toString(PRODUCT_TOMATO.getId()), MediaType.APPLICATION_JSON))
				.invoke();
		assertEquals(201, addProductsToCartResponse.getStatus());
		
		// POST - Create order
		final Response createOrderResponse = ORDERS_TARGET
				.request()
				.buildPost(Entity.entity(CUSTOMER2.getUsername(), MediaType.APPLICATION_JSON))
				.invoke();
		assertEquals(201, createOrderResponse.getStatus());
	}
}
