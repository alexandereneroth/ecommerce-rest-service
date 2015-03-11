package se.groupone.ecommerce.webservice;

import se.groupone.ecommerce.exception.RepositoryException;
import se.groupone.ecommerce.model.Customer;
import se.groupone.ecommerce.model.Order;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

import java.net.URI;
import java.util.ArrayList;

@Path("customers")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public final class CustomerService extends WebShopService
{
	public CustomerService() throws RepositoryException
	{
		super();
	}

	@Context
	private UriInfo uriInfo;

	//  Hämta en användare med ett visst id
	@GET
	@Path("{username}")
	public Response getCustomer(@PathParam("username") final String username)
	{
		Customer customer = shopService.getCustomer(username);
		return Response.ok(customer).build();
	}

	//  Skapa en ny användare – detta ska returnera en länk till den skapade
	// användaren i Location-headern
	@POST
	public Response createCustomer(final Customer customer)
	{
		shopService.addCustomer(customer);

		final URI location = uriInfo.getAbsolutePathBuilder().path(customer.getUsername()).build();
		return Response.created(location).build();
	}

	//  Uppdatera en användare
	@PUT
	@Path("{username}")
	public Response putCustomer(@PathParam("username") final String username, final Customer customer)
	{
		// if path username and new customer username matches then update
		// repository
		if (username.equals(customer.getUsername()))
		{
			shopService.updateCustomer(customer);
			return Response.status(Status.NO_CONTENT).build();
		}
		// otherwise send error code
		return Response.status(Status.BAD_REQUEST).entity("Username mismatch between path and new customer info").build();
	}

	//  Ta bort en användare (eller sätta den som inaktiv)
	@DELETE
	@Path("{username}")
	public Response deleteCustomer(@PathParam("username") final String username)
	{
		shopService.removeCustomer(username);

		return Response.noContent().build();
	}

	@GET
	@Path("{username}/cart")
	public Response getOrder(@PathParam("username") final String username)
	{
		ArrayList<Integer> cartList;
		cartList = shopService.getCustomer(username).getShoppingCart();

		// GenericEntity is created for IntegerListMapper generic handling
		return Response.ok(new GenericEntity<ArrayList<Integer>>(cartList)
		{
		}).build();
	}

	@POST
	@Path("{username}/cart")
	public Response addToCart(@PathParam("username") final String username,
			@QueryParam("amount") @DefaultValue("1") final Integer amount,
			final String productId)
	{
		try
		{
			int productIdInt = Integer.parseInt(productId);
			shopService.addProductToCustomer(productIdInt, username, amount);

			final URI location = uriInfo.getAbsolutePathBuilder().build();
			return Response.created(location).build();
		}
		catch (NumberFormatException e)
		{
			return Response.status(Status.BAD_REQUEST).entity("Expected body to be parsable as integers").build();
		}
	}

	//  Hämta en användares alla order
	@GET
	@Path("{username}/orders")
	public Response getOrders(@PathParam("username") final String username)
	{
		ArrayList<Order> orderList;
		orderList = new ArrayList<Order>(shopService.getOrders(username));

		// GenericEntity is created for IntegerListMapper generic handling
		return Response.ok(new GenericEntity<ArrayList<Order>>(orderList)
		{
		}).build();
	}
}