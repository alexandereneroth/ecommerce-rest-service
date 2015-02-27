package se.coredev.jaxrs.service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;



// http://127.0.0.1:8080/jax-rs/message
@Path("message")
public final class MessageService
{
	private static final String LOCATION_HEADER = "Location";
	private static final Map<Long, String> messages = new HashMap<>();
	private static final AtomicLong messageIds = new AtomicLong(1000);

	// http://127.0.0.1:8080/jax-rs/message
	@POST
	public Response addMessage(final String message)
	{
		final Long messageId = messageIds.incrementAndGet();
		messages.put(messageId, message);

		return Response.status(Status.CREATED).header(LOCATION_HEADER, "message/" + messageId).build();
	}

	// http://127.0.0.1:8080/jax-rs/message/1001
	@GET
	@Path("{messageId}")
	public Response getMessage(@PathParam("messageId") final Long messageId)
	{
		if (messages.containsKey(messageId))
		{
			final String message = messages.get(messageId);
			return Response.ok(message).build();		           
		}

		return Response.status(Status.NOT_FOUND).build();
	}

	// http://127.0.0.1:8080/jax-rs/message/1001
	@PUT
	@Path("{messageId}")
	public Response updateMessage(@PathParam("messageId") final Long messageId, final String newMessage)
	{
		if (messages.containsKey(messageId))
		{
			messages.put(messageId, newMessage);
			return Response.status(Status.NO_CONTENT).build();
		}

		return Response.status(Status.BAD_REQUEST).build();
	}

	// http://127.0.0.1:8080/jax-rs/message/1001
	@DELETE
	@Path("{messageId}")
	public Response deleteMessage(@PathParam("messageId") final Long messageId)
	{
		if (messages.containsKey(messageId))
		{
			final String deletedMessage = messages.remove(messageId);
//			return Response.status(Status.NO_CONTENT).build();
			return Response.status(Status.OK).entity(deletedMessage).build();
		}
		return Response.status(Status.BAD_REQUEST).build();
	}

}
