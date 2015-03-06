package se.groupone.ecommerce.webservice.util;

import se.groupone.ecommerce.exception.ShopServiceException;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public final class ShopServiceExceptionMapper implements ExceptionMapper<ShopServiceException> 
{

	@Override
	public Response toResponse(ShopServiceException e)
	{
		return Response.status(Status.BAD_REQUEST).entity("This is from MAPPER: " + e.getMessage()).build();
	}
}
