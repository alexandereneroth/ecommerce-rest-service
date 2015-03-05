package se.groupone.ecommerce.webservice;

import se.groupone.ecommerce.model.Customer;

import se.groupone.ecommerce.repository.memory.InMemoryCustomerRepository;
import se.groupone.ecommerce.repository.memory.InMemoryOrderRepository;
import se.groupone.ecommerce.repository.memory.InMemoryProductRepository;
import se.groupone.ecommerce.service.ShopService;

import javax.servlet.ServletContext;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_ADDPeer;

// Hämta en användare med ett visst id
//
// Skapa en ny användare – detta ska returnera en länk till den skapade 
//
//användaren i Location-headern
//
// Uppdatera en användare 
//
// Ta bort en användare (eller sätta den som inaktiv)
//
// Skapa en order för en användare
//
// Hämta en användares alla order
//
// Hämta en viss order för en användare 
//
// Uppdatera en order för en användare 
//
// Ta bort en order för en användare

@Path("customer")
public class CustomerService
{
	
	@Context
	private ServletContext context;
	private static ShopService ss;
	
	// get shopService instance
	{
		ss = (ShopService) context.getAttribute("ss");
		
		// some dummy data
		ss.addCustomer(new Customer("tom", "password", "email@email.com", "Tomcat", "Blackmore", "C3LStreet", "123456"));
	}
	
	
	@GET
	@Path("{username}")
	public Response getCustomer(@PathParam("username") final String username)
	{
		Customer customer = ss.getCustomer(username);
		return Response.ok(customer).build();
	}

	@POST
	public Response postCustomer()
	{
		throw new RuntimeException("unimplemented");// TODO
	}

	@PUT
	@Path("{username}")
	public Response putCustomer(@PathParam("username") final String customerId)
	{
		throw new RuntimeException("unimplemented");// TODO
	}

	@DELETE
	@Path("{username}")
	public Response deleteCustomer(@PathParam("username") final String customerId)
	{
		throw new RuntimeException("unimplemented");// TODO
	}
}