package se.groupone.ecommerce.test.webservice;

import static org.junit.Assert.*;

import javax.ws.rs.client.Entity;

import org.junit.Test;

public class CustomerServiceTest
{

	private static final String HOST_NAME = "localhost";
	private static final String HOST_IP = 9999;
	private static final String PROJECT_NAME = "ecommerce_webservice";
	private static final String URL_BASE = "http://" + HOST_NAME + ":" + HOST_IP + "/" + PROJECT_NAME;

	private static final Client client = ClientFactory.newClient();

	//  Skapa en ny användare
	@Test
	public void canCreateCustomer()
	{

		WebTarget target = client.target("http://www.myserver.com/book");
		Invocation invocation = target.request(MediaType.TEXT_PLAIN).buildGet();
		Response response = invocation.invoke();
		
		Response response = client.target(URL_BASE + "/customer").request().post(Entity.json(""));
		
		assertTrue("unimplemented", false); // TODO
	}

	//  Skapa en ny användare – detta ska returnera en länk till den skapade
	// användaren i Location-headern
	@Test
	public void assertLocationHeaderOfCreatedCustomerIsCorrect()
	{
		assertTrue("unimplemented", false); // TODO
	}

	//  Hämta en användare med ett visst id
	@Test
	public void canGetCustomerOfId()
	{
		assertTrue("unimplemented", false); // TODO
	}

	//  Uppdatera en användare
	@Test
	public void canUpdateUser()
	{
		assertTrue("unimplemented", false); // TODO
	}

	//  Ta bort en användare (eller sätta den som inaktiv)
	@Test
	public void canRemoveCustomer()
	{
		assertTrue("unimplemented", false); // TODO
	}

	//  Skapa en order för en användare
	@Test
	public void canCreateCustomerOrder()
	{
		assertTrue("unimplemented", false); // TODO
	}

	//  Hämta en användares alla order
	@Test
	public void canGetCustomerOrders()
	{
		assertTrue("unimplemented", false); // TODO
	}

	//  Hämta en viss order för en användare
	@Test
	public void canGetCustomerOrder()
	{
		assertTrue("unimplemented", false); // TODO
	}

	//  Uppdatera en order för en användare
	@Test
	public void canUpdateCustomerOrder()
	{
		assertTrue("unimplemented", false); // TODO
	}

	//  Ta bort en order för en användare
	@Test
	public void canRemoveCustomerOrder()
	{
		assertTrue("unimplemented", false); // TODO
	}
}
