package se.groupone.ecommerce.webservice;

import se.groupone.ecommerce.repository.memory.InMemoryCustomerRepository;
import se.groupone.ecommerce.repository.memory.InMemoryOrderRepository;
import se.groupone.ecommerce.repository.memory.InMemoryProductRepository;
import se.groupone.ecommerce.service.ShopService;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class WebServiceInit implements ServletContextListener
{	
	// Initializes shopService instance and adds it to servlet context so all resources are only
	// working with this one instance of shopService
	@Override
	public void contextInitialized(ServletContextEvent sce)
	{
		ShopService shopService = new ShopService(new InMemoryCustomerRepository(), 
				new InMemoryProductRepository(), new InMemoryOrderRepository());
		sce.getServletContext().setAttribute("ss", shopService);
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce)
	{
		System.out.println("ServletContextListener destroyed");
	}

}
