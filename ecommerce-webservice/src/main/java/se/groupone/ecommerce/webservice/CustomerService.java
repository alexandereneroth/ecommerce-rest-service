package se.groupone.ecommerce.webservice;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

@Path("customer")
public class CustomerService
{
	@GET
	public Response getAllCustomers()
	{
		throw new RuntimeException("unimplemented");// TODO
	}

	@GET
	@Path("{user-id}")
	public Response getCustomer(@PathParam("customer-id") final String customerId)
	{
		throw new RuntimeException("unimplemented");// TODO
	}

	@POST
	public Response postCustomer()
	{
		throw new RuntimeException("unimplemented");// TODO
	}

	@PUT
	@Path("{customer-id}")
	public Response putCustomer(@PathParam("customer-id") final String customerId)
	{
		throw new RuntimeException("unimplemented");// TODO
	}

	@DELETE
	@Path("{customer-id}")
	public Response deleteCustomer(@PathParam("customer-id") final String customerId)
	{
		throw new RuntimeException("unimplemented");// TODO
	}
}
