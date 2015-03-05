package se.groupone.ecommerce.webservice;

import se.groupone.ecommerce.model.Customer;

import se.groupone.ecommerce.service.ShopService;

import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import java.net.URI;

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
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CustomerService
{

	@Context
	private ServletContext context;
	@Context
	private UriInfo uriInfo;
	
	private ShopService ss;

	@GET
	@Path("{username}")
	public Response getCustomer(@PathParam("username") final String username)
	{
		ss = (ShopService) context.getAttribute("ss");
		Customer customer = ss.getCustomer(username);
		return Response.ok(customer).build();
	}

	@POST
	public Response createCustomer(Customer customer)
	{
		ss = (ShopService) context.getAttribute("ss");
		ss.addCustomer(customer);
		
		final URI location = uriInfo.getAbsolutePathBuilder().path(customer.getUsername()).build();
		return Response.created(location).build();
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