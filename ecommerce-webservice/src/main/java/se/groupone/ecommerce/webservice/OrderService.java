package se.groupone.ecommerce.webservice;

import se.groupone.ecommerce.model.Order;

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
		Order order = ss.getOrder(orderId);
		return Response.ok(order).build();
	}

	@POST
	public Response createOrder(final String username)
	{
		ss = (ShopService) context.getAttribute("ss");
		ss.createOrder(username);
		return Response.ok().build();
	}

	@PUT
	public Response updateOrder(final Order order)
	{
		ss = (ShopService) context.getAttribute("ss");
		ss.updateOrder(order);
		return Response.ok().build();
	}

	@DELETE
	@Path("{orderId}")
	public Response removeOrder(@PathParam("orderId") final Integer orderId)
	{
		ss = (ShopService) context.getAttribute("ss");
		ss.removeOrder(orderId);
		return Response.ok().build();
	}
}
