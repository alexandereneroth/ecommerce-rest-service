package se.groupone.ecommerce.webservice;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

@Path("user")
public class UserService
{
	@GET
	public Response getAllUsers()
	{
		throw new RuntimeException("unimplemented");// TODO
	}

	@GET
	@Path("{user-id}")
	public Response getUser(@PathParam("user-id") final String userId)
	{
		throw new RuntimeException("unimplemented");// TODO
	}

	@POST
	public Response postUser()
	{
		throw new RuntimeException("unimplemented");// TODO
	}

	@PUT
	@Path("{user-id}")
	public Response putUser(@PathParam("user-id") final String userId)
	{
		throw new RuntimeException("unimplemented");// TODO
	}

	@DELETE
	@Path("{user-id}")
	public Response deleteUser(@PathParam("user-id") final String userId)
	{
		throw new RuntimeException("unimplemented");// TODO
	}
}
