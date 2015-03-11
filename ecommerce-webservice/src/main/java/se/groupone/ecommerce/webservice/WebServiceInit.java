package se.groupone.ecommerce.webservice;

import se.groupone.ecommerce.exception.RepositoryException;
import se.groupone.ecommerce.repository.sql.SQLCustomerRepository;
import se.groupone.ecommerce.repository.sql.SQLOrderRepository;
import se.groupone.ecommerce.repository.sql.SQLProductRepository;
import se.groupone.ecommerce.service.ShopService;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class WebServiceInit implements ServletContextListener
{
	// Initializes shopService instance and adds it to servlet context so all
	// resources are only working with this one instance of ShopService.
	@Override
	public void contextInitialized(ServletContextEvent sce)
	{

		try
		{
			ShopService newShopService = new ShopService(
					new SQLCustomerRepository(),
					new SQLProductRepository(),
					new SQLOrderRepository());
			sce.getServletContext().setAttribute("ss", newShopService);
		}
		catch (RepositoryException e)
		{
			System.err.println("WebServiceInit failed to initialize SQL Repositories");
			e.printStackTrace();
		}

		// Uncommet below to switch to InMemoryRepo
		// ShopService ss;
		// ss = new ShopService(new InMemoryCustomerRepository(),
		// new InMemoryProductRepository(), new InMemoryOrderRepository());
		// sce.getServletContext().setAttribute("ss", ss);
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce)
	{
	}
}