package se.groupone.ecommerce.webservice;

import se.groupone.ecommerce.model.Order;

import se.groupone.ecommerce.exception.ShopServiceException;
import se.groupone.ecommerce.service.ShopService;

import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

@Path("customer")
public final class OrderService
{
	@Context
	private ServletContext context;
	private static ShopService ss;
	
	@GET
	@Path("{orderId}")
	public Response getOrder(@PathParam("orderId") final int orderId )
	{
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
}
