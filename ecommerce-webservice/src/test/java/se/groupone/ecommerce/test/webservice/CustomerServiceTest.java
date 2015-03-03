//package se.groupone.ecommerce.test.webservice;
//
//import static org.junit.Assert.assertTrue;
//
//import java.io.InputStream;
//import java.io.InputStreamReader;
//
//import javax.ws.rs.DELETE;
//import javax.ws.rs.GET;
//import javax.ws.rs.POST;
//import javax.ws.rs.PUT;
//import javax.ws.rs.Path;
//import javax.ws.rs.PathParam;
//import javax.ws.rs.client.Client;
//import javax.ws.rs.client.ClientBuilder;
//import javax.ws.rs.client.Entity;
//import javax.ws.rs.client.Invocation;
//import javax.ws.rs.client.WebTarget;
//import javax.ws.rs.core.MediaType;
//import javax.ws.rs.core.Response;
//
//import org.junit.Test;
//
//// Hämta en användare med ett visst id
////
//// Skapa en ny användare – detta ska returnera en länk till den skapade 
////
////användaren i Location-headern
////
//// Uppdatera en användare 
////
//// Ta bort en användare (eller sätta den som inaktiv)
////
//// Skapa en order för en användare
////
//// Hämta en användares alla order
////
//// Hämta en viss order för en användare 
////
//// Uppdatera en order för en användare 
////
//// Ta bort en order för en användare
//
//@Path("customer")
//public class CustomerServiceTest
//{
//	private static final String HOST_NAME = "localhost";
//	private static final int HOST_IP = 9999;
//	private static final String PROJECT_NAME = "ecommerce_webservice";
//	private static final String URL_BASE = "http://" + HOST_NAME + ":" + HOST_IP + "/" + PROJECT_NAME;
//
//	private static final Client client = ClientBuilder.newClient();
//
//	//  Skapa en ny användare
//	@Test
//	public void canCreateCustomer()
//	{
//
//		WebTarget target = client.target("http://www.myserver.com/book");
//		Invocation invocation = target.request(MediaType.TEXT_PLAIN).buildGet();
//		Response response = invocation.invoke();
//		System.out.println(new InputStreamReader((InputStream) response.getEntity()));
//		
////		Response response = client.target(URL_BASE + "/customer").request().post(Entity.json(""));
//		
//		assertTrue("unimplemented", false); // TODO
//	}
//	
//	@GET
//	@Path("{user-id}")
//	public Response getCustomer(@PathParam("customer-id") final String customerId)
//	{
//		throw new RuntimeException("unimplemented");// TODO
//	}
//
//	@POST
//	public Response postCustomer()
//	{
//		throw new RuntimeException("unimplemented");// TODO
//	}
//
//	@PUT
//	@Path("{customer-id}")
//	public Response putCustomer(@PathParam("customer-id") final String customerId)
//	{
//		throw new RuntimeException("unimplemented");// TODO
//	}
//
//	@DELETE
//	@Path("{customer-id}")
//	public Response deleteCustomer(@PathParam("customer-id") final String customerId)
//	{
//		throw new RuntimeException("unimplemented");// TODO
//	}
//}
