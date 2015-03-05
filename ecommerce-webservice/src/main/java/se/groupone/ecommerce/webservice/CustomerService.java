package se.groupone.ecommerce.webservice;

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
	private static ShopService shopService;
	
	static
	{
//		shopService = (ShopService) context.getAttribute("shopservice");
		ShopService shopService = new ShopService(new InMemoryCustomerRepository(), 
				new InMemoryProductRepository(), new InMemoryOrderRepository());
		System.out.println("in static constructor");
	}
	
	@GET
	public Response getCustomer()
	{
		System.out.println(shopService);
		return Response.ok("hello").build();
	}

	@GET
	@Path("{username}")
	public Response getCustomer(@PathParam("username") final String username)
	{
		
		throw new RuntimeException("unimplemented");// TODO
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