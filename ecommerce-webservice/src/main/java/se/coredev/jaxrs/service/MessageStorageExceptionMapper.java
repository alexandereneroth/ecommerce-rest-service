package se.coredev.jaxrs.service;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public final class MessageStorageExceptionMapper implements ExceptionMapper<MessageStorageException>
{

	@Override
	public Response toResponse(final MessageStorageException exception)
	{
		return Response.status(Status.NOT_FOUND).entity(exception.getMessage()).build();
	}

}
