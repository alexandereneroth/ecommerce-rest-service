package se.groupone.ecommerce.test.webservice;

import static org.junit.Assert.*;

import se.groupone.ecommerce.model.Customer;
import se.groupone.ecommerce.model.Order;
import se.groupone.ecommerce.model.Product;
import se.groupone.ecommerce.model.ProductParameters;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import se.groupone.ecommerce.webservice.util.CustomerMapper;
import se.groupone.ecommerce.webservice.util.IntegerListMapper;
import se.groupone.ecommerce.webservice.util.OrderMapper;
import se.groupone.ecommerce.webservice.util.ProductMapper;
import se.groupone.ecommerce.webservice.util.ProductParamMapper;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import jdk.internal.dynalink.beans.StaticClass;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.sun.org.apache.xml.internal.security.Init;

public class CustomerServiceTest
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
	public void Init()
	{

	}

	@After
	public void tearDown()
	{
		WebTarget admin = client.target(URL_BASE + "/admin");
		admin.request().buildPost(Entity.entity("reset-repo", MediaType.TEXT_HTML)).invoke();

		// Remove customer2 from DB
		CUSTOMERS_TARGET.path(CUSTOMER2.getUsername())
				.request(MediaType.APPLICATION_JSON)
				.buildDelete()
				.invoke();
	}

	//  Skapa en ny användare
	@Test
	public void canCreateCustomer()
	{
		// POST - Create customer
		Response response = CUSTOMERS_TARGET.request(MediaType.APPLICATION_JSON)
				.buildPost(Entity.entity(CUSTOMER2, MediaType.APPLICATION_JSON))
				.invoke();
		assertEquals(201, response.getStatus());

		// GET - Retrieve created customer
		Customer createdCustomer = CUSTOMERS_TARGET.path(CUSTOMER2.getUsername())
				.request(MediaType.APPLICATION_JSON)
				.get(Customer.class);
		assertEquals(createdCustomer, CUSTOMER2);
	}

	//  Skapa en ny användare – detta ska returnera en länk till den skapade
	// användaren i Location-headern
	@Test
	public void createCustomerReturnsCorrectLocationHeaderForCreatedCustomer() throws URISyntaxException
	{
		final URI EXPECTED_URI = new URI("http://localhost:8080/ecommerce-webservice/customers/"
				+ CUSTOMER2.getUsername());
		// POST - Create new customer
		Response response = CUSTOMERS_TARGET.request(MediaType.APPLICATION_JSON)
				.buildPost(Entity.entity(CUSTOMER2, MediaType.APPLICATION_JSON))
				.invoke();
		assertEquals(201, response.getStatus());

		// Check returned location URI
		assertEquals(EXPECTED_URI, response.getLocation());
	}

	//  Uppdatera en användare
	@Test
	public void canUpdateCustomer()
	{
		// POST - create Customer2 in repo
		Response postResponse = CUSTOMERS_TARGET.request(MediaType.APPLICATION_JSON)
				.buildPost(Entity.entity(CUSTOMER2, MediaType.APPLICATION_JSON))
				.invoke();
		assertEquals(201, postResponse.getStatus());

		// Updated customer2 with changed password.
		Customer updatedCustomer2 = new Customer(CUSTOMER2.getUsername(), "secret", CUSTOMER2.getEmail(),
				CUSTOMER2.getFirstName(), CUSTOMER2.getLastName(),
				CUSTOMER2.getAddress(), CUSTOMER2.getPhoneNumber());
		// POST - Update customer
		Response putResponse = CUSTOMERS_TARGET.path(CUSTOMER2.getUsername())
				.request(MediaType.APPLICATION_JSON)
				.buildPut(Entity.entity(updatedCustomer2, MediaType.APPLICATION_JSON))
				.invoke();
		assertEquals(204, putResponse.getStatus());

		// GET - Check that customer is updated
		Customer updatedCustomer2FromRepo = CUSTOMERS_TARGET.path(CUSTOMER2.getUsername())
				.request(MediaType.APPLICATION_JSON)
				.get(Customer.class);
		assertEquals(updatedCustomer2, updatedCustomer2FromRepo);
	}

	//  Ta bort en användare (eller sätta den som inaktiv)
	@Test
	public void canRemoveCustomer()
	{
		// POST - Create customer
		Response postResponse = CUSTOMERS_TARGET.request(MediaType.APPLICATION_JSON)
				.buildPost(Entity.entity(CUSTOMER2, MediaType.APPLICATION_JSON))
				.invoke();
		assertEquals(201, postResponse.getStatus());

		// GET - Check that it is in repo
		Response thisShouldSucceedResponse = CUSTOMERS_TARGET.path(CUSTOMER2.getUsername())
				.request(MediaType.APPLICATION_JSON)
				.get();
		assertEquals(200, thisShouldSucceedResponse.getStatus());

		// DELETE - Delete it
		Response deleteResponse = CUSTOMERS_TARGET.path(CUSTOMER2.getUsername())
				.request(MediaType.APPLICATION_JSON)
				.buildDelete()
				.invoke();
		assertEquals(204, deleteResponse.getStatus());

		// GET - Try to retrieve deleted customer, should fail
		Response thisShouldFailResponse = CUSTOMERS_TARGET.path(CUSTOMER2.getUsername())
				.request(MediaType.APPLICATION_JSON)
				.get();
		assertEquals(400, thisShouldFailResponse.getStatus());
	}

	@Test
	public void canAddProductToCart()
	{

		// POST - Create customer
		Response createCustomerResponse = CUSTOMERS_TARGET.request(MediaType.APPLICATION_JSON)
				.buildPost(Entity.entity(CUSTOMER2, MediaType.APPLICATION_JSON))
				.invoke();
		assertEquals(201, createCustomerResponse.getStatus());

		// POST - Create products
		Response createProductResponse1 = PRODUCTS_TARGET.request(MediaType.APPLICATION_JSON)
				.buildPost(Entity.entity(PRODUCT_PARAMETERS_TOMATO, MediaType.APPLICATION_JSON))
				.invoke();
		assertEquals(201, createProductResponse1.getStatus());

		// Response createProductResponse2 =
		// PRODUCT_TARGET.request(MediaType.APPLICATION_JSON)
		// .buildPost(Entity.entity(PRODUCT_PARAMETERS_LETTUCE,
		// MediaType.APPLICATION_JSON))
		// .invoke();
		// assertEquals(201, createProductResponse2.getStatus());

		// GET - Get created products

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

		// GET - Get cart contents and verify
		final Response shoppingCartGetResponse = CUSTOMERS_TARGET
				.path(CUSTOMER2.getUsername())
				.path("cart")
				.request(MediaType.APPLICATION_JSON)
				.get();

		final String shoppingCartJson = CUSTOMERS_TARGET
				.path(CUSTOMER2.getUsername())
				.path("cart")
				.request(MediaType.APPLICATION_JSON)
				.get(String.class);

		// Fulkod... TODO Avfula detta...
		Gson gson = new Gson();
		JsonObject shoppingCartJsonObject = gson.fromJson(shoppingCartJson, JsonObject.class);
		ArrayList<Integer> cartArrayList = new ArrayList<Integer>();
		JsonArray cartJsonArray = shoppingCartJsonObject.get("integerArray").getAsJsonArray();
		for (JsonElement element : cartJsonArray)
		{
			cartArrayList.add(element.getAsInt());
		}
		assertEquals(PRODUCT_TOMATO.getId(), (int) cartArrayList.get(0));
	}

	//  Hämta en användares alla order
	@Test
	public void canGetCustomerOrders()
	{
		// POST - Create customer
		Response createCustomerResponse = CUSTOMERS_TARGET.request(MediaType.APPLICATION_JSON)
				.buildPost(Entity.entity(CUSTOMER2, MediaType.APPLICATION_JSON))
				.invoke();
		assertEquals(201, createCustomerResponse.getStatus());
		
		Order orderToBeChecked1 = addOrder(CUSTOMER2);
		Order orderToBeChecked2 = addOrder(CUSTOMER2);
		Order orderToBeChecked3 = addOrder(CUSTOMER2);
		
		// GET - Retrieve created order
		final String ordersJson = CUSTOMERS_TARGET
				.path(CUSTOMER2.getUsername())
				.path("orders")
				.request()
				.get(String.class);
		
		// Check orderlist
		HashMap<Integer, Order> customerOrders = parseOrderJsonArrayList(ordersJson);
		
		assertTrue(customerOrders.containsKey(orderToBeChecked1.getId()));
		Order orderFromRepo1 = customerOrders.get(orderToBeChecked1.getId());
		assertEquals(orderToBeChecked1, orderFromRepo1);
		
		assertTrue(customerOrders.containsKey(orderToBeChecked2.getId()));
		Order orderFromRepo2 = customerOrders.get(orderToBeChecked2.getId());
		assertEquals(orderToBeChecked2, orderFromRepo2);
		
		assertTrue(customerOrders.containsKey(orderToBeChecked3.getId()));
		Order orderFromRepo3 = customerOrders.get(orderToBeChecked3.getId());
		assertEquals(orderToBeChecked3, orderFromRepo3);
	}
	
	private HashMap<Integer, Order> parseOrderJsonArrayList (String ordersJson)
	{
		// Fulkod... TODO Avfula detta... kommentera? :(
		Gson gson = new Gson();
		HashMap<Integer, Order> customerOrders = new HashMap<>(); 
		JsonObject orderJsonObject = gson.fromJson(ordersJson, JsonObject.class);
		JsonArray orderJsonArray = orderJsonObject.get("orderArray").getAsJsonArray();
		
		for(JsonElement order : orderJsonArray) 
		{
			ArrayList<Integer> productIdArrayList = new ArrayList<>();
			JsonArray productIds = ((JsonObject) order).get("productIds").getAsJsonArray();
			for(JsonElement jElement : productIds) 
			{
				productIdArrayList.add(jElement.getAsInt());
			}
			JsonObject newOrderJsonObject = (JsonObject) order;
			Order newOrder = new Order(newOrderJsonObject.get("id").getAsInt(),
					newOrderJsonObject.get("username").getAsString(),
					productIdArrayList);
			customerOrders.put(newOrder.getId(), newOrder);
		}
		return customerOrders;
	}
	
	private Order addOrder(Customer customer)
	{
		// POST - Create products
		Response createProductResponse1 = PRODUCTS_TARGET.request(MediaType.APPLICATION_JSON)
				.buildPost(Entity.entity(PRODUCT_PARAMETERS_TOMATO, MediaType.APPLICATION_JSON))
				.invoke();
		assertEquals(201, createProductResponse1.getStatus());

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
		System.out.println(addProductsToCartResponse.readEntity(String.class));
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
		
		return createdOrder;
	}
}
