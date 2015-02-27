package se.coredev.jaxrs.service;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

@Path("exception-demo")
public final class ExceptionDemoService
{
	private static final MessageStorage storage = new MessageStorage();
	
	@GET
	@Path("{messageId}")
	public Response getMessage(@PathParam("messageId") final Long messageId)
	{
		if(messageId.equals(1001L))
		{
			return Response.ok("This is a message from space!").build();
		}
		
		throw new BadMessageException("No message with id:" + messageId);
	}
	
	@GET
	@Path("storage/{messageId}")
	public Response getMessageFromStorage(@PathParam("messageId") final Long messageId)
	{
		return Response.ok(storage.getMessage(messageId)).build();
	}
}
