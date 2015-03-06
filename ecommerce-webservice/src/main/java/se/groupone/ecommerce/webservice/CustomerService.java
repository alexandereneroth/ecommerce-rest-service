package se.groupone.ecommerce.webservice;

import se.groupone.ecommerce.exception.ShopServiceException;

import se.groupone.ecommerce.model.Customer;
import se.groupone.ecommerce.model.Order;
import se.groupone.ecommerce.model.Product;

import se.groupone.ecommerce.service.ShopService;

import javax.servlet.ServletContext;
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

@Path("customers")
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
	
	@GET
	@Path("{username}/orders")
	public Response getOrders(@PathParam("username") final String username)
	{
		ArrayList<Order> orderList;
		ShopService ss = (ShopService) context.getAttribute("ss");

		try
		{
			orderList = new ArrayList<Order>(ss.getOrders(username));
			return Response.ok(new GenericEntity<ArrayList<Order>>(orderList)
					{
					}).build();
		}
		catch (ShopServiceException e)
		{
			return Response.status(Status.BAD_REQUEST).entity(e.getMessage()).build();
		}
	}

	@POST
	public Response createCustomer(final Customer customer)
	{
		ss = (ShopService) context.getAttribute("ss");
		ss.addCustomer(customer);

		final URI location = uriInfo.getAbsolutePathBuilder().path(customer.getUsername()).build();
		return Response.created(location).build();
	}

	@PUT
	@Path("{username}")
	public Response putCustomer(@PathParam("username") final String username, final Customer customer)
	{
		ss = (ShopService) context.getAttribute("ss");

		// if path username and new customer username matches then update
		// repository
		if (username.equals(customer.getUsername()))
		{
			try
			{
				ss.updateCustomer(customer);
			}
			catch (ShopServiceException e)
			{
				return Response.status(Status.BAD_REQUEST).entity(e.getMessage()).build();
			}

			return Response.status(Status.NO_CONTENT).build();
		}

		// otherwise send error code
		return Response.status(Status.BAD_REQUEST).entity("Username mismatch between path and new customer info").build();
	}

	@DELETE
	@Path("{username}")
	public Response deleteCustomer(@PathParam("username") final String username)
	{
		ss = (ShopService) context.getAttribute("ss");
		try
		{
			ss.removeCustomer(username);
			return Response.noContent().build();
		}
		catch (ShopServiceException e)
		{
			return Response.status(Status.BAD_REQUEST).entity(e.getMessage()).build();
		}
	}

	@GET
	@Path("{username}/cart")
	public Response getOrder(@PathParam("username") final String username)
	{
		ArrayList<Integer> cartList;
		StringBuilder builder = new StringBuilder();
		ss = (ShopService) context.getAttribute("ss");

		try
		{
			cartList = ss.getCustomer(username).getShoppingCart();

			for (Integer productId : cartList)
			{
				builder.append(ss.getProductWithId(productId).toString());
				builder.append("<br>");
			}

			return Response.ok(new GenericEntity<ArrayList<Integer>>(cartList)
			{
			}).build();
		}
		catch (ShopServiceException e)
		{
			return Response.status(Status.BAD_REQUEST).entity(e.getMessage()).build();
		}
	}

	@POST
	@Path("{username}/cart")
	public Response addToCart(@PathParam("username") final String username,
			@QueryParam("amount") @DefaultValue("1") final Integer amount,
			final String productId)
	{
		ss = (ShopService) context.getAttribute("ss");
		try
		{
			int productIdInt = Integer.parseInt(productId);
			try
			{
				ss.addProductToCustomer(productIdInt, username, amount);
				final URI location = uriInfo.getAbsolutePathBuilder().build();

				return Response.created(location).build();
			}
			catch (ShopServiceException e)
			{
				return Response.status(Status.BAD_REQUEST).entity(e.getMessage()).build();
			}
		}
		catch (NumberFormatException e)
		{
			return Response.status(Status.BAD_REQUEST).entity("Expected body to be parsable as integers").build();
		}
	}
}