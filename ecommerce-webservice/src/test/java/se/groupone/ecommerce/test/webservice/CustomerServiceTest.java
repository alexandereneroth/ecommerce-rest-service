package se.groupone.ecommerce.test.webservice;

import static org.junit.Assert.*;

import se.groupone.ecommerce.model.Customer;
import se.groupone.ecommerce.model.Product;
import se.groupone.ecommerce.model.ProductParameters;

import java.net.URI;
import java.net.URISyntaxException;

import se.groupone.ecommerce.webservice.util.CustomerMapper;
import se.groupone.ecommerce.webservice.util.ProductMapper;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
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

	private static final Customer CUSTOMER1;
	private static final Customer CUSTOMER2;
	
	static
	{
		RESOURCE_TARGET = client.target(RESOURCE_URL);
		CUSTOMER1 = new Customer("tom", "password", "email@email.com", "Tomcat", "Blackmore", "C3LStreet", "123456");
		CUSTOMER2 = new Customer("alex", "password", "alex@email.com", "Alexander", "Sol", "Banangatan 1", "543211");
	}
	
	@After
	public void tearDown() 
	{
		WebTarget admin = client.target(URL_BASE + "/admin");
		admin.request().buildPost(Entity.entity("reset-repo", MediaType.TEXT_HTML)).invoke();
	}

	//  Skapa en ny användare
	@Test
	public void canCreateCustomer()
	{
		//POST
		Response response = RESOURCE_TARGET.request(MediaType.APPLICATION_JSON)
										   .buildPost(Entity.entity(CUSTOMER2,MediaType.APPLICATION_JSON))
										   .invoke();
		assertEquals(201, response.getStatus());
		
		//GET
		Customer createdCustomer = RESOURCE_TARGET.path(CUSTOMER2.getUsername())
												  .request(MediaType.APPLICATION_JSON)
												  .get(Customer.class);
		assertEquals(createdCustomer, CUSTOMER2);
	}

	//  Skapa en ny användare – detta ska returnera en länk till den skapade
	// användaren i Location-headern
	@Test
	public void assertLocationHeaderOfCreatedCustomerIsCorrect() throws URISyntaxException
	{
		final URI EXPECTED_URI = new URI("http://localhost:8080/ecommerce-webservice/customers/" 
										 + CUSTOMER2.getUsername());
		//POST
		Response response = RESOURCE_TARGET.request(MediaType.APPLICATION_JSON)
										   .buildPost(Entity.entity(CUSTOMER2,MediaType.APPLICATION_JSON))
										   .invoke();
		assertEquals(201, response.getStatus());
		assertEquals(EXPECTED_URI, response.getLocation());
	}

	//  Uppdatera en användare
	@Test
	public void canUpdateUser()
	{
		//POST - create Customer2 in repo
		Response postResponse = RESOURCE_TARGET.request(MediaType.APPLICATION_JSON)
										   .buildPost(Entity.entity(CUSTOMER2,MediaType.APPLICATION_JSON))
										   .invoke();
		assertEquals(201, postResponse.getStatus());
		
		// Updated customer2 with changed password.
		Customer updatedCustomer2 = new Customer(CUSTOMER2.getUsername(), "secret", CUSTOMER2.getEmail(), 
												CUSTOMER2.getFirstName(), CUSTOMER2.getLastName(), 
												CUSTOMER2.getAddress(), CUSTOMER2.getPhoneNumber());
		//POST
		Response putResponse = RESOURCE_TARGET.path(CUSTOMER2.getUsername())
											.request(MediaType.APPLICATION_JSON)
										    .buildPut(Entity.entity(updatedCustomer2,MediaType.APPLICATION_JSON))
										    .invoke();
		assertEquals(204, putResponse.getStatus());
		
		//GET
		Customer updatedCustomer2FromRepo = RESOURCE_TARGET.path(CUSTOMER2.getUsername())
												  .request(MediaType.APPLICATION_JSON)
												  .get(Customer.class);
		assertEquals(updatedCustomer2, updatedCustomer2FromRepo);
	}

	//  Ta bort en användare (eller sätta den som inaktiv)
	@Test
	public void canRemoveCustomer()
	{
		//POST
		Response postResponse = RESOURCE_TARGET.request(MediaType.APPLICATION_JSON)
										   .buildPost(Entity.entity(CUSTOMER2,MediaType.APPLICATION_JSON))
										   .invoke();
		assertEquals(201, postResponse.getStatus());
		
		//GET
		Response thisShouldSucceedResponse = RESOURCE_TARGET.path(CUSTOMER2.getUsername())
												  .request(MediaType.APPLICATION_JSON)
												  .get();
		assertEquals(200, thisShouldSucceedResponse.getStatus());
		
		//DELETE
		Response deleteResponse = RESOURCE_TARGET.path(CUSTOMER2.getUsername())
												 .request(MediaType.APPLICATION_JSON)
												 .buildDelete()
												 .invoke();
		assertEquals(204, deleteResponse.getStatus());
		
		//GET - Trying to retrieve deleted customer
		Response thisShouldFailResponse = RESOURCE_TARGET.path(CUSTOMER2.getUsername())
												  .request(MediaType.APPLICATION_JSON)
												  .get();
		assertEquals(400, thisShouldFailResponse.getStatus());
	}
}
