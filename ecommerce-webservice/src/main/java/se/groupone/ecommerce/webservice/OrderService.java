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
import javax.ws.rs.core.Response.Status;

@Path("orders")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public final class OrderService
{
	@Context
	private ServletContext context;
	private static ShopService ss;
	
	@GET
	@Path("{username}")
	public Response getOrders(@PathParam("username") final String username )
	{
		ShopService ss = (ShopService) context.getAttribute("ss");
		ArrayList<Order> orderList;
		try
		{
			orderList = new ArrayList<Order>(ss.getOrders(username));
			System.out.println("Order list is empty: " + orderList.isEmpty());
			StringBuilder builder = new StringBuilder();
			for (Order order : orderList) {
				builder.append(order.toString());
			}
			System.out.println(builder.toString());
			return Response.ok(builder.toString()).build();
		}
		catch (ShopServiceException e)
		{
			return Response.status(Status.BAD_REQUEST).entity(e.getMessage()).build();
		}
	}
	
//	@GET
//	@Path("{orderId}")
//	public Response getOrder(@PathParam("orderId") final int orderId )
//	{
//		try
//		{
//			Order order = ss.getOrder(orderId);
//			return Response.ok(order).build();
//		}
//		catch (ShopServiceException e)
//		{
//			return Response.status(Status.BAD_REQUEST).entity(e.getMessage()).build();
//		}
//	}
}
