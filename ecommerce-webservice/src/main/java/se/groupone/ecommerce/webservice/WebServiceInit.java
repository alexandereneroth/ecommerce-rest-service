package se.groupone.ecommerce.webservice;

import se.groupone.ecommerce.repository.memory.InMemoryCustomerRepository;
import se.groupone.ecommerce.repository.memory.InMemoryOrderRepository;
import se.groupone.ecommerce.repository.memory.InMemoryProductRepository;
import se.groupone.ecommerce.service.ShopService;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.ws.rs.core.Context;

public class WebServiceInit implements ServletContextListener
{
	@Context
	ServletContext context;
	
	@Override
	public void contextInitialized(ServletContextEvent sce)
	{
		System.out.println("ServletContextListener started");
		ShopService shopService = new ShopService(new InMemoryCustomerRepository(), 
				new InMemoryProductRepository(), new InMemoryOrderRepository());
		context.setAttribute("shopservice", shopService);
		System.out.println();
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce)
	{
		System.out.println("ServletContextListener destroyed");
	}

}
