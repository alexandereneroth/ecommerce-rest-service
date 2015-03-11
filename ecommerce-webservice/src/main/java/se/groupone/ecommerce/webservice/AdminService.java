package se.groupone.ecommerce.webservice;

import java.sql.SQLException;

import se.groupone.ecommerce.exception.RepositoryException;
import se.groupone.ecommerce.repository.sql.DBConnectionConfig;
import se.groupone.ecommerce.repository.sql.SQLConnector;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("admin")
public class AdminService extends WebShopService
{
	public AdminService() throws RepositoryException
	{
		super();
	}

	@POST
	public Response readAdminCommand(String command) throws RepositoryException, SQLException
	{
		if (command.equals("reset-repo"))
		{
			SQLConnector sql = new SQLConnector(DBConnectionConfig.HOST, 
					DBConnectionConfig.PORT, 
					DBConnectionConfig.USERNAME, 
					DBConnectionConfig.PASSWORD, DBConnectionConfig.DATABASE);
			sql.queryUpdate("TRUNCATE TABLE customer_cart;");
			sql.queryUpdate("TRUNCATE TABLE order_items;");
			sql.queryUpdate("TRUNCATE TABLE product;");
			sql.queryUpdate("TRUNCATE TABLE `order`;");
			sql.queryUpdate("TRUNCATE TABLE customer;");

			return Response.ok("SQLRepo has been reset").build();

//			 Code below is for InMemory Repo reset
//			 shopService = new ShopService(new
//			 InMemoryCustomerRepository(),
//			 new InMemoryProductRepository(), new InMemoryOrderRepository());
//			
//			 return Response.ok("InMemoryRepo has been reset").build();
		}
		return Response.status(400).entity("Invalid command received").build();
	}
}