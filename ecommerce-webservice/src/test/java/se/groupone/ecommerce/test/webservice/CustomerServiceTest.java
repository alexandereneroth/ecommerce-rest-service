package se.groupone.ecommerce.test.webservice;

import static org.junit.Assert.*;

import se.groupone.ecommerce.model.Customer;
import se.groupone.ecommerce.model.Product;
import se.groupone.ecommerce.model.ProductParameters;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

import se.groupone.ecommerce.webservice.util.CustomerMapper;
import se.groupone.ecommerce.webservice.util.IntegerListMapper;
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
	private static final Client client = ClientBuilder.newBuilder()
			.register(CustomerMapper.class)
			.register(IntegerListMapper.class)
			.register(ProductMapper.class)
			.register(ProductParamMapper.class)
			.build();
	private static final WebTarget CUSTOMER_TARGET;
	private static final WebTarget PRODUCT_TARGET;
	private static final Customer CUSTOMER1;
	private static final Customer CUSTOMER2;
	private static final ProductParameters PRODUCT_PARAMETERS_TOMATO;
	private static final ProductParameters PRODUCT_PARAMETERS_LETTUCE;

	static
	{
		CUSTOMER_TARGET = client.target(CUSTOMERS_URL);
		PRODUCT_TARGET = client.target(PRODUCTS_URL);
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
		CUSTOMER_TARGET.path(CUSTOMER2.getUsername())
				.request(MediaType.APPLICATION_JSON)
				.buildDelete()
				.invoke();
	}

	//  Skapa en ny användare
	@Test
	public void canCreateCustomer()
	{
		// POST - Create customer
		Response response = CUSTOMER_TARGET.request(MediaType.APPLICATION_JSON)
				.buildPost(Entity.entity(CUSTOMER2, MediaType.APPLICATION_JSON))
				.invoke();
		assertEquals(201, response.getStatus());

		// GET - Retrieve created customer
		Customer createdCustomer = CUSTOMER_TARGET.path(CUSTOMER2.getUsername())
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
		Response response = CUSTOMER_TARGET.request(MediaType.APPLICATION_JSON)
				.buildPost(Entity.entity(CUSTOMER2, MediaType.APPLICATION_JSON))
				.invoke();
		assertEquals(201, response.getStatus());

		// Check returned location URI
		assertEquals(EXPECTED_URI, response.getLocation());
	}

	//  Uppdatera en användare
	@Test
	public void canUpdateUser()
	{
		// POST - create Customer2 in repo
		Response postResponse = CUSTOMER_TARGET.request(MediaType.APPLICATION_JSON)
				.buildPost(Entity.entity(CUSTOMER2, MediaType.APPLICATION_JSON))
				.invoke();
		assertEquals(201, postResponse.getStatus());

		// Updated customer2 with changed password.
		Customer updatedCustomer2 = new Customer(CUSTOMER2.getUsername(), "secret", CUSTOMER2.getEmail(),
				CUSTOMER2.getFirstName(), CUSTOMER2.getLastName(),
				CUSTOMER2.getAddress(), CUSTOMER2.getPhoneNumber());
		// POST - Update customer
		Response putResponse = CUSTOMER_TARGET.path(CUSTOMER2.getUsername())
				.request(MediaType.APPLICATION_JSON)
				.buildPut(Entity.entity(updatedCustomer2, MediaType.APPLICATION_JSON))
				.invoke();
		assertEquals(204, putResponse.getStatus());

		// GET - Check that customer is updated
		Customer updatedCustomer2FromRepo = CUSTOMER_TARGET.path(CUSTOMER2.getUsername())
				.request(MediaType.APPLICATION_JSON)
				.get(Customer.class);
		assertEquals(updatedCustomer2, updatedCustomer2FromRepo);
	}

	//  Ta bort en användare (eller sätta den som inaktiv)
	@Test
	public void canRemoveCustomer()
	{
		// POST - Create customer
		Response postResponse = CUSTOMER_TARGET.request(MediaType.APPLICATION_JSON)
				.buildPost(Entity.entity(CUSTOMER2, MediaType.APPLICATION_JSON))
				.invoke();
		assertEquals(201, postResponse.getStatus());

		// GET - Check that it is in repo
		Response thisShouldSucceedResponse = CUSTOMER_TARGET.path(CUSTOMER2.getUsername())
				.request(MediaType.APPLICATION_JSON)
				.get();
		assertEquals(200, thisShouldSucceedResponse.getStatus());

		// DELETE - Delete it
		Response deleteResponse = CUSTOMER_TARGET.path(CUSTOMER2.getUsername())
				.request(MediaType.APPLICATION_JSON)
				.buildDelete()
				.invoke();
		assertEquals(204, deleteResponse.getStatus());

		// GET - Try to retrieve deleted customer, should fail
		Response thisShouldFailResponse = CUSTOMER_TARGET.path(CUSTOMER2.getUsername())
				.request(MediaType.APPLICATION_JSON)
				.get();
		assertEquals(400, thisShouldFailResponse.getStatus());
	}

	@Test
	public void canAddProductToCart()
	{

		// POST - Create customer
		Response createCustomerResponse = CUSTOMER_TARGET.request(MediaType.APPLICATION_JSON)
				.buildPost(Entity.entity(CUSTOMER2, MediaType.APPLICATION_JSON))
				.invoke();
		assertEquals(201, createCustomerResponse.getStatus());

		// POST - Create products
		Response createProductResponse1 = PRODUCT_TARGET.request(MediaType.APPLICATION_JSON)
				.buildPost(Entity.entity(PRODUCT_PARAMETERS_TOMATO, MediaType.APPLICATION_JSON))
				.invoke();
		assertEquals(201, createProductResponse1.getStatus());

//		Response createProductResponse2 = PRODUCT_TARGET.request(MediaType.APPLICATION_JSON)
//				.buildPost(Entity.entity(PRODUCT_PARAMETERS_LETTUCE, MediaType.APPLICATION_JSON))
//				.invoke();
//		assertEquals(201, createProductResponse2.getStatus());

		// GET - Get created products
		
		final Product PRODUCT_TOMATO = client.target(createProductResponse1.getLocation())
				.request(MediaType.APPLICATION_JSON)
				.get(Product.class);

		// POST - Add products to cart
		final Response addProductsToCartResponse = CUSTOMER_TARGET
				.path(CUSTOMER2.getUsername())
				.path("cart")
				.request()
				.buildPost(Entity.entity(Integer.toString(PRODUCT_TOMATO.getId()), MediaType.APPLICATION_JSON))
				.invoke();
		assertEquals(201, addProductsToCartResponse.getStatus());
		
		// GET - Get cart contents and verify
		final Response shoppingCartGetResponse = CUSTOMER_TARGET
				.path(CUSTOMER2.getUsername())
				.path("cart")
				.request(MediaType.APPLICATION_JSON)
				.get();
		System.out.println(shoppingCartGetResponse.getStatus());
		
		final String shoppingCartJson = CUSTOMER_TARGET
				.path(CUSTOMER2.getUsername())
				.path("cart")
				.request(MediaType.APPLICATION_JSON)
				.get(String.class);
		
		// Fulkod...
		Gson gson = new Gson();
		JsonObject shoppingCartJsonObject = gson.fromJson(shoppingCartJson, JsonObject.class);
		System.out.println(shoppingCartJsonObject.get("integerArray"));
		ArrayList<Integer> cartArrayList = new ArrayList<Integer>();
		JsonArray cartJsonArray = shoppingCartJsonObject.get("integerArray").getAsJsonArray();
		for(JsonElement element : cartJsonArray) 
		{
			cartArrayList.add(element.getAsInt());
		}
		
		assertEquals(PRODUCT_TOMATO.getId(), (int) cartArrayList.get(0));
		
		
	}
}
