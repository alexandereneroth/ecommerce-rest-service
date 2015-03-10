package se.groupone.ecommerce.webservice;

import java.sql.SQLException;

import se.groupone.ecommerce.exception.RepositoryException;
import se.groupone.ecommerce.repository.memory.InMemoryCustomerRepository;
import se.groupone.ecommerce.repository.memory.InMemoryOrderRepository;
import se.groupone.ecommerce.repository.memory.InMemoryProductRepository;
import se.groupone.ecommerce.repository.sql.DBInfo;
import se.groupone.ecommerce.repository.sql.SQLConnector;
import se.groupone.ecommerce.repository.sql.SQLCustomer;
import se.groupone.ecommerce.repository.sql.SQLOrder;
import se.groupone.ecommerce.repository.sql.SQLProduct;
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
	public Response readAdminCommand(String command) throws RepositoryException, SQLException 
	{
		if(command.equals("reset-repo"))
		{
			ShopService newShopService = new ShopService(new SQLCustomer(), 
			new SQLProduct(), new SQLOrder());
			context.setAttribute("ss", newShopService);
			
			SQLConnector sql = new SQLConnector(DBInfo.host, DBInfo.port, DBInfo.username, DBInfo.password, DBInfo.database);
//			sql.queryUpdate("TRUNCATE TABLE customer_cart;");
//			sql.queryUpdate("TRUNCATE TABLE order_items;");
			sql.queryUpdate("TRUNCATE TABLE product;");
//			sql.queryUpdate("TRUNCATE TABLE order;");
//			sql.queryUpdate("TRUNCATE TABLE customer;");
//			sql.query("TRUNCATE TABLE customer_cart;");
//			sql.query("TRUNCATE TABLE order_items;");
//			sql.query("TRUNCATE TABLE product;");
//			sql.query("TRUNCATE TABLE order;");
			sql.queryUpdate("TRUNCATE TABLE customer;");
			
			return Response.ok("SQLRepo has been reset").build();
			
//			Code below is for InMemory Repo reset
//			ShopService newShopService = new ShopService(new InMemoryCustomerRepository(), 
//					new InMemoryProductRepository(), new InMemoryOrderRepository());
//			
//			context.setAttribute("ss", newShopService);
//			return Response.ok("InMemoryRepo has been reset").build();
		}
		return Response.status(400).entity("Invalid command received").build();

	}
}
