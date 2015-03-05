package se.groupone.ecommerce.webservice;

import se.groupone.ecommerce.model.Customer;

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
		ShopService ss = new ShopService(new InMemoryCustomerRepository(), 
				new InMemoryProductRepository(), new InMemoryOrderRepository());
		sce.getServletContext().setAttribute("ss", ss);
		
		// add some dummy data for InMemoryRepo
		ss.addCustomer(new Customer("tom", "password", "email@email.com", "Tomcat", "Blackmore", "C3LStreet", "123456"));
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce)
	{
		System.out.println("ServletContextListener destroyed");
	}

}
