package se.coredev.jaxrs.service;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

public final class BadMessageException extends WebApplicationException
{
	private static final long serialVersionUID = 3899197009093505203L;

	public BadMessageException(String message)
	{
		super(Response.status(Status.BAD_REQUEST)
				      .entity("This is a bad message:" + message)
				      .build());
	}
	
}
