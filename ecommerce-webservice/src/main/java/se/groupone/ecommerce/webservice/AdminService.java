package se.groupone.ecommerce.webservice;

import se.groupone.ecommerce.exception.RepositoryException;
import se.groupone.ecommerce.repository.memory.InMemoryCustomerRepository;
import se.groupone.ecommerce.repository.memory.InMemoryOrderRepository;
import se.groupone.ecommerce.repository.memory.InMemoryProductRepository;
import se.groupone.ecommerce.repository.sql.SQLCustomer;
import se.groupone.ecommerce.service.ShopService;

import javax.servlet.ServletContext;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

@Path("admin")
public class AdminService
{
	@Context
	private ServletContext context;
	
	@POST
	public Response readAdminCommand(String command) throws RepositoryException 
	{
		if(command.equals("reset-repo"))
		{
			ShopService newShopService = new ShopService(new InMemoryCustomerRepository(), 
					new InMemoryProductRepository(), new InMemoryOrderRepository());
			
			context.setAttribute("ss", newShopService);
			return Response.ok("InMemoryRepo has been reset").build();
		}
		return Response.status(400).entity("Invalid command received").build();
	}
}
