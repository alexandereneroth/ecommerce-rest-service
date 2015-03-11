package se.groupone.ecommerce.webservice;

import se.groupone.ecommerce.exception.RepositoryException;
import se.groupone.ecommerce.repository.sql.SQLCustomerRepository;
import se.groupone.ecommerce.repository.sql.SQLOrderRepository;
import se.groupone.ecommerce.repository.sql.SQLProductRepository;
import se.groupone.ecommerce.service.ShopService;

public abstract class WebShopService
{
	static ShopService shopService;

	WebShopService() throws RepositoryException
	{
		if(shopService == null){
			shopService = new ShopService(
					new SQLCustomerRepository(),
					new SQLProductRepository(),
					new SQLOrderRepository());
		}
	}
}
