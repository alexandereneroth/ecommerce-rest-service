package se.groupone.ecommerce.test.webservice;

import static org.junit.Assert.*;

import se.groupone.ecommerce.model.Customer;
import se.groupone.ecommerce.model.Product;
import se.groupone.ecommerce.model.ProductParameters;

import se.groupone.ecommerce.webservice.util.CustomerMapper;
import se.groupone.ecommerce.webservice.util.ProductMapper;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.sun.org.apache.xml.internal.security.Init;

public class CustomerServiceTest
{
	private static final String HOST_NAME = "localhost";
	private static final int HOST_IP = 8080;
	private static final String PROJECT_NAME = "ecommerce-webservice";
	private static final String RESOURCE = "customers";
	private static final String URL_BASE = "http://" + HOST_NAME + ":" + HOST_IP + "/" 
			+ PROJECT_NAME;
	private static final String RESOURCE_URL = "http://" + HOST_NAME + ":" + HOST_IP + "/" 
			+ PROJECT_NAME + "/" + RESOURCE;
	private static final Client client = ClientBuilder.newBuilder().register(CustomerMapper.class).build();
	private static final WebTarget RESOURCE_TARGET;

	private Customer customer1;
	private Customer customer2;
	
	static
	{
		RESOURCE_TARGET = client.target(RESOURCE_URL);
	}

	@Before
	public void Init()
	{
		customer1 = new Customer("tom", "password", "email@email.com", "Tomcat", "Blackmore", "C3LStreet", "123456");
		customer2 = new Customer("alex", "password", "alex@email.com", "Alexander", "Sol", "Banangatan 1", "543211");
	}
	
	@After
	public void tearDown() 
	{
		
	}

	//  Skapa en ny användare
	@Test
	public void canCreateCustomer()
	{
		//POST

		Response response = RESOURCE_TARGET.request(MediaType.APPLICATION_JSON)
										   .buildPost(Entity.entity(customer2,MediaType.APPLICATION_JSON))
										   .invoke();
		assertEquals(Response.Status.CREATED.toString(), response.getStatus());
		
		//GET
		Customer createdCustomer = RESOURCE_TARGET.path(customer2.getUsername())
												  .request(MediaType.APPLICATION_JSON)
												  .get(Customer.class);
		
		System.out.println(createdCustomer + " equals " + customer1);
		assertEquals(createdCustomer, customer2);
	}

	//  Hämta en användare med ett visst id
	@Test
	public void canGetCustomerOfId()
	{
		//POST
		Invocation createInvocation = RESOURCE_TARGET.request(MediaType.APPLICATION_JSON)
										.buildPost(Entity.entity(customer1,MediaType.APPLICATION_JSON));
		Response response = createInvocation.invoke();
		
		//GET
		Customer createdCustomer = RESOURCE_TARGET.path(customer1.getUsername())
												  .request(MediaType.APPLICATION_JSON)
												  .get(Customer.class);
		assertEquals(createdCustomer, customer1);
	}

	//  Skapa en ny användare – detta ska returnera en länk till den skapade
	// användaren i Location-headern
	@Test
	public void assertLocationHeaderOfCreatedCustomerIsCorrect()
	{ // TODO
	}

	//  Uppdatera en användare
	@Test
	public void canUpdateUser()
	{ // TODO
	}

	//  Ta bort en användare (eller sätta den som inaktiv)
	@Test
	public void canRemoveCustomer()
	{ // TODO
	}
}
