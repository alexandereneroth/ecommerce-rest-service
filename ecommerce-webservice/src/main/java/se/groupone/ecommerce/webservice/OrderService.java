package se.groupone.ecommerce.webservice;

import se.groupone.ecommerce.model.Order;

import se.groupone.ecommerce.exception.ShopServiceException;
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
import javax.ws.rs.core.Response.Status;

@Path("orders")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public final class OrderService
{
	@Context
	private ServletContext context;
	@Context
	private UriInfo uriInfo;
	private ShopService ss;

	@GET
	@Path("{orderId}")
	public Response getOrder(@PathParam("orderId") final int orderId)
	{
		ss = (ShopService) context.getAttribute("ss");
		try
		{
			Order order = ss.getOrder(orderId);
			return Response.ok(order).build();
		}
		catch (ShopServiceException e)
		{
			return Response.status(Status.BAD_REQUEST).entity(e.getMessage()).build();
		}
	}

	@POST
	public Response createOrder(final String username)
	{
		ss = (ShopService) context.getAttribute("ss");
		try
		{
			ss.createOrder(username);
			return Response.ok().build();
		}
		catch (ShopServiceException e)
		{
			return Response.status(Status.BAD_REQUEST).entity(e.getMessage()).build();
		}
	}

	@PUT
	public Response updateOrder(final Order order)
	{
		ss = (ShopService) context.getAttribute("ss");
		try
		{
			ss.updateOrder(order);
			return Response.ok().build();
		}
		catch (ShopServiceException e)
		{
			return Response.status(Status.BAD_REQUEST).entity(e.getMessage()).build();
		}
	}

	@DELETE
	@Path("{orderId}")
	public Response removeOrder(@PathParam("orderId") final Integer orderId)
	{
		ss = (ShopService) context.getAttribute("ss");

		try
		{
			ss.removeOrder(orderId);
			return Response.ok().build();
		}
		catch (ShopServiceException e)
		{
			return Response.status(Status.BAD_REQUEST).entity(e.getMessage()).build();
		}
	}
}
