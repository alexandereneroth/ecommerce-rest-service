package se.groupone.ecommerce.service;

import se.groupone.ecommerce.exception.ShopServiceException;
import se.groupone.ecommerce.exception.RepositoryException;
import se.groupone.ecommerce.model.Customer;
import se.groupone.ecommerce.model.ModelException;
import se.groupone.ecommerce.model.Order;
import se.groupone.ecommerce.model.Product;
import se.groupone.ecommerce.model.ProductParameters;
import se.groupone.ecommerce.repository.CustomerRepository;
import se.groupone.ecommerce.repository.OrderRepository;
import se.groupone.ecommerce.repository.ProductRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class ShopService
{
	private final CustomerRepository cR;
	private final ProductRepository pR;
	private final OrderRepository oR;

	private final AtomicInteger productIDGenerator;
	private final AtomicInteger orderIDGenerator;

	public ShopService(CustomerRepository cR, ProductRepository pR, OrderRepository oR)
	{
		this.cR = cR;
		this.pR = pR;
		this.oR = oR;

		productIDGenerator = new AtomicInteger(pR.getHighestId());
		orderIDGenerator = new AtomicInteger(oR.getHighestId());
	}
	
	public void addProduct(ProductParameters product)
	{
		try
		{
			pR.addProduct(new Product(getNextProductId(), product));
		}
		catch (RepositoryException e)
		{
			throw new ShopServiceException("Could not add product.", e);
		}
	}

	public void addProductToCustomer(int productId, String username)
	{
		addProductToCustomer(productId, username, 1);
	}

	public void addProductToCustomer(int productId, String username, int amount)
	{
		try
		{
			if (pR.getProduct(productId).getQuantity() >= amount)
			{
				for (int i = 0; i < amount; i++)
				{
					cR.getCustomer(username).addProduct(productId);
				}
			}
		}
		catch (RepositoryException e)
		{
			throw new ShopServiceException("Could not add product to customer.", e);
		}
	}

	public Product getProductWithId(int productId)
	{
		try
		{
			return pR.getProduct(productId);
		}
		catch (RepositoryException e)
		{
			throw new ShopServiceException("Could not getProduct.", e);
		}
	}

	public HashMap<Integer, Product> getProducts()
	{
		return pR.getProducts();
	}

	public void removeProduct(int title)
	{
		try
		{
			for (Customer c : cR.getCustomers().values())
			{
				c.removeProduct(title);
			}
			pR.removeProduct(title);
		}
		catch (RepositoryException | ModelException e)
		{
			throw new ShopServiceException("Could not remove product.", e);
		}
	}

	public void updateProduct(Product product)
	{
		try
		{
			pR.updateProduct(product);
		}
		catch (RepositoryException e)
		{
			throw new ShopServiceException("Could not updateProduct", e);
		}
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

	public void createOrder(String customerUsername, ArrayList<Integer> orderedProductIds)
	{
		try
		{
			/*
			// Decreases the product quantity for each ordered item
			for (Integer productId : order.getProductIds())
			{
				Product productToBeUpdated = pR.getProduct(productId);
				productToBeUpdated.decreaseQuantity(1);
				pR.updateProduct(productToBeUpdated);
			}
			*/
			
			// TODO Decrease of quantity of product in product repository needs to
			// happen in a transaction
			oR.addOrder(new Order(getNextOrderId(), customerUsername, orderedProductIds));
		}
		catch (RepositoryException e)
		{
			throw new ShopServiceException("Could not add order.", e);
		}
	}

	public Order getOrder(int orderId)
	{
		try
		{
			return oR.getOrder(orderId);
		}
		catch (RepositoryException e)
		{
			throw new ShopServiceException("Could not get order.", e);
		}
	}

	public HashMap<Integer, Order> getOrders()
	{
			return oR.getOrders();
	}

	private int getNextProductId()
	{
		return productIDGenerator.incrementAndGet();
	}

	private int getNextOrderId()
	{
		return orderIDGenerator.incrementAndGet();
	}
}