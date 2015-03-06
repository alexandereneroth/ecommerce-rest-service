package se.groupone.ecommerce.webservice;

import se.groupone.ecommerce.model.Customer;
import se.groupone.ecommerce.model.Product;
import se.groupone.ecommerce.model.ProductParameters;

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
		final String pName = "Voffla";
		final String pCategory = "Bakverk";
		final String pManufacturer = "Steffe";
		final String pDescription = "En våffa gjord av steffe!";
		final String pImg = "delicious.png";
		final double pPrice = 10;
		final int pQuantity = 100;
		
		final String pName2 = "Pannkaka";
		final String pCategory2 = "Bakverk";
		final String pManufacturer2 = "SteffeKeff";
		final String pDescription2 = "En pannkaka gjord av steffe!";
		final String pImg2 = "delicious2.png";
		final double pPrice2 = 5;
		final int pQuantity2 = 200;
		
		
		ProductParameters params1 = new ProductParameters(pName,
									   pCategory,
									   pManufacturer,
									   pDescription,
									   pImg,
									   pPrice,
								       pQuantity);
	
		ProductParameters params2 = new ProductParameters(pName2,
							    	   pCategory2,
									   pManufacturer2,
									   pDescription2,
									   pImg2,
									   pPrice2,
									   pQuantity2);
		
		ss.addCustomer(new Customer("tom", "password", "email@email.com", "Tomcat", "Blackmore", "C3LStreet", "123456"));
		Product p1 = ss.addProduct(params1);
		Product p2 = ss.addProduct(params2);
		ss.addProductToCustomer(p1.getId(), "tom", 2);
		ss.addProductToCustomer(p2.getId(), "tom");
		ss.createOrder("tom");
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce)
	{
		System.out.println("ServletContextListener destroyed");
	}

}
