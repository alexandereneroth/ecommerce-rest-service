package se.coredev.jaxrs.service;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Singleton;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("demo")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public final class DemoService
{
	@PUT
	public Response echoMessage(final String message)
	{
		return Response.ok("[" + message + "," + message + "]").build();
	}
	
	@GET
	public Response getJsonTextMessage()
	{
		return Response.ok("{\"messge\":\"Hello!\"}").build();
	}
	
	//Content-Type:text/plain
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public Response getPlainTextMessage()
	{
		return Response.ok("This is plain text").build();
	}
	
	//Content-Type:text/html
	@GET
	@Produces(MediaType.TEXT_HTML)
	public Response getHtmlTextMessage()
	{
		return Response.ok("<b>This is HTML</b>").build();
	}
	
}
