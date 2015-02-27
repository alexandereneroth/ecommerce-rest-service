package se.coredev.jaxrs.service;

import java.net.URI;
import java.util.UUID;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

@Path("uri-demo")
public final class UriDemoService
{
	@Context
	public UriInfo uriInfo; 
	
	
	@GET
	@Path("echo")
	public Response echoUriInfo()
	{
		final StringBuilder builder = new StringBuilder();
		builder.append("<b>Path:</b>").append(uriInfo.getPath());
		builder.append("<br/><b>AbsolutePath:</b> ").append(uriInfo.getAbsolutePath());
		builder.append("<br/><b>Base uri: </b>").append(uriInfo.getBaseUri());
		builder.append("<br/><b>Request uri: </b>").append(uriInfo.getRequestUri());
		builder.append("<br/><b>Path segments: </b>").append(uriInfo.getPathSegments());
		builder.append("<br/><b>Query parameters: </b>").append(uriInfo.getQueryParameters());
		
		return Response.ok(builder.toString()).build();
	}
	
	@POST
	public Response createNewThing()
	{		
		final String id = UUID.randomUUID().toString();
		final URI location = uriInfo.getAbsolutePathBuilder().path(id).build();
		
		return Response.created(location).build();
	}
	
	
}












