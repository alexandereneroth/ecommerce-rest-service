package se.groupone.ecommerce.service;

import se.groupone.ecommerce.exception.ShopServiceException;
import se.groupone.ecommerce.exception.RepositoryException;
import se.groupone.ecommerce.model.Customer;
import se.groupone.ecommerce.model.Order;
import se.groupone.ecommerce.model.Product;
import se.groupone.ecommerce.repository.CustomerRepository;
import se.groupone.ecommerce.repository.OrderRepository;
import se.groupone.ecommerce.repository.ProductRepository;

import java.util.HashMap;

public class ShopService
{
	private CustomerRepository cR;
	private ProductRepository pR;
	private OrderRepository oR;

	public ShopService(CustomerRepository cR, ProductRepository pR, OrderRepository oR)
	{
		this.cR = cR;
		this.pR = pR;
		this.oR = oR;
	}

	public void addProduct(Product product)
	{
		pR.addProduct(product);
	}

	public void addProductToCustomer(String title, String username)
	{
		addProductToCustomer(title, username, 1);
	}

	public void addProductToCustomer(String title, String username, int amount)
	{
		try
		{
			if (pR.getProduct(title).getQuantity() >= amount)
			{
				for (int i = 0; i < amount; i++)
				{
					cR.getCustomer(username).addProduct(title);
				}
			}
		}
		catch (RepositoryException e)
		{
			throw new ShopServiceException("Could not add product to customer.", e);
		}
	}

	public Product getProduct(String title)
	{
		return pR.getProduct(title);
	}

	public HashMap<String, Product> getProducts()
	{
		return pR.getProducts();
	}

	public void removeProduct(String title)
	{
		for (Customer c : cR.getCustomers().values())
		{
			c.removeProduct(title);
		}
		pR.removeProduct(title);
	}

	public void updateProduct(Product product)
	{
		pR.updateProduct(product);
	}

	public void addCustomer(Customer customer)
	{
		try
		{
			cR.addCustomer(customer);
		}
		catch (RepositoryException e)
		{
			throw new ShopServiceException("Could not add customer.", e);
		}
	}

	public Customer getCustomer(String username)
	{
		try
		{
			return cR.getCustomer(username);
		}
		catch (RepositoryException e)
		{
			throw new ShopServiceException("Could not get customer.", e);
		}
	}

	public void updateCustomer(Customer customer)
	{
		try
		{
			cR.updateCustomer(customer);
		}
		catch (RepositoryException e)
		{
			throw new ShopServiceException("Could not update customer.", e);
		}
	}

	public void removeCustomer(String username)
	{
		try
		{
			cR.removeCustomer(username);
		}
		catch (RepositoryException e)
		{
			throw new ShopServiceException("Could not remove customer.", e);
		}
	}

	public void addOrder(String username)
	{
		try
		{
			Customer customer = cR.getCustomer(username);

			// Will decrease the product quantity by one for each item in the
			// shoppingCart
			for (String title : customer.getShoppingCart())
			{
				pR.getProduct(title).decreaseQuantity(1);
			}
			oR.addOrder(customer);
		}
		catch (RepositoryException e)
		{
			throw new ShopServiceException("Could not add order.", e);
		}
	}

	public Order getOrder(String key)
	{
		return oR.getOrder(key);
	}

	public HashMap<String, Order> getOrders()
	{
		return oR.getOrders();
	}
}