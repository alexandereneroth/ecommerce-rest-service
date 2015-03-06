package se.groupone.ecommerce.test.webservice;

import static org.junit.Assert.*;

//import java.io.InputStream;
//import java.io.InputStreamReader;
//
//import javax.ws.rs.client.Client;
//import javax.ws.rs.client.ClientBuilder;
//import javax.ws.rs.client.Invocation;
//import javax.ws.rs.client.WebTarget;
//import javax.ws.rs.core.MediaType;
//import javax.ws.rs.core.Response;

import org.junit.Test;

public class CustomerServiceTest
{
//	private static final String HOST_NAME = "localhost";
//	private static final int HOST_IP = 9999;
//	private static final String PROJECT_NAME = "ecommerce_webservice";
//	private static final String URL_BASE = "http://" + HOST_NAME + ":" + HOST_IP + "/" + PROJECT_NAME;
//
//	private static final Client client = ClientBuilder.newClient();

	//  Skapa en ny användare
	@Test
	public void canCreateCustomer()
	{
//
//		WebTarget target = client.target("http://www.myserver.com/book");
//		Invocation invocation = target.request(MediaType.TEXT_PLAIN).buildGet();
//		Response response = invocation.invoke();
//		System.out.println(new InputStreamReader((InputStream) response.getEntity()));

		// Response response = client.target(URL_BASE +
		// "/customer").request().post(Entity.json(""));
 // TODO
	}

	//  Hämta en användare med ett visst id
	@Test
	public void canGetCustomerOfId()
	{ // TODO
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

	//  Skapa en order för en användare
	@Test
	public void canCreateCustomerOrder()
	{// TODO
	}

	//  Hämta en användares alla order
	@Test
	public void canGetCustomerOrders()
	{// TODO
	}

	//  Hämta en viss order för en användare
	@Test
	public void canGetCustomerOrder()
	{ // TODO
	}

	//  Uppdatera en order för en användare
	@Test
	public void canUpdateCustomerOrder()
	{// TODO
	}

	//  Ta bort en order för en användare
	@Test
	public void canRemoveCustomerOrder()
	{ // TODO
	}
}
