package se.groupone.ecommerce.webservice;

import se.groupone.ecommerce.service.ShopService;

import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

@Path("order")
public final class OrderService
{
	@Context
	private ServletContext context;
	private static ShopService shopService;
	
	@GET
	public Response getCustomer()
	{
		shopService = (ShopService) context.getAttribute("ss");
		return Response.ok("hello order " + shopService).build();
	}
}
