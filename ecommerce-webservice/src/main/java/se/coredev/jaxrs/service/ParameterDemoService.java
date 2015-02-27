package se.coredev.jaxrs.service;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

@Path("parameter-demo")
public final class ParameterDemoService
{
	@GET	
	//?sort-order=asc
	public Response echoParameters(@QueryParam("sort-order") @DefaultValue("asc") final String sortOrder,
								   @QueryParam("pageSize") @DefaultValue("100") final Integer pageSize)
	{
		return Response.ok("Sort order:" + sortOrder + ", pageSize:" + pageSize).build();
	}
	
}
