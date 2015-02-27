package se.coredev.jaxrs.service;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("user")
@Produces({MediaType.APPLICATION_JSON})//,MediaType.APPLICATION_XML})
@Consumes({MediaType.APPLICATION_JSON})//, MediaType.APPLICATION_XML})
public final class UserDemoService
{
	private static final Map<Long, User> users;
	
	static {
		users = new HashMap<>();
		
		users.put(1001L, new User(1001L, "Yoda", "secret"));
		users.put(1002L, new User(1002L, "Master", "top-secret"));
		users.put(1003L, new User(1003L, "Jones", "mega-secret"));
	}

	@GET
	@Path("{userId}")
	public Response getUser(@PathParam("userId") final Long userId)
	{
		final User user = users.get(userId);
		return Response.ok(user).build();
	}
	
	@PUT
	@Path("{userId}")
	public Response getUser(@PathParam("userId") final Long userId, final User user)
	{
		users.put(userId, user);
		return Response.ok().build();
	}
	
	
	
	
	
	
	
	
}
