package se.groupone.ecommerce.webservice;

import se.groupone.ecommerce.model.Order;

import java.util.ArrayList;

import se.groupone.ecommerce.exception.ShopServiceException;
import se.groupone.ecommerce.service.ShopService;

import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.core.Response.Status;

@Path("customers")
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
	@Path("{username}/orders")
	public Response getOrders(@PathParam("username") final String username)
	{
		ArrayList<Order> orderList;
		StringBuilder builder = new StringBuilder();
		ShopService ss = (ShopService) context.getAttribute("ss");

		try
		{
			orderList = new ArrayList<Order>(ss.getOrders(username));

			for (Order order : orderList)
			{
				builder.append(order);
				builder.append("<br>");
			}
			return Response.ok(builder.toString()).build();
		}
		catch (ShopServiceException e)
		{
			return Response.status(Status.BAD_REQUEST).entity(e.getMessage()).build();
		}
	}

	@GET
	@Path("{username}/orders/{orderId}")
	public Response getOrder(@PathParam("username") final String username, @PathParam("orderId") final int orderId)
	{
		ss = (ShopService) context.getAttribute("ss");

		try
		{
			Order order = ss.getOrder(orderId);

			// if path username and order username matches then return order
			if (order.getUsername().equals(username))
			{
				return Response.ok(order).build();
			}

			// otherwise send error code
			return Response.status(Status.BAD_REQUEST).entity("Username mismatch between path and order info").build();
		}
		catch (ShopServiceException e)
		{
			return Response.status(Status.BAD_REQUEST).entity(e.getMessage()).build();
		}
	}
}
