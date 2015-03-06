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
		try
		{
			productIDGenerator = new AtomicInteger(pR.getHighestId());
			orderIDGenerator = new AtomicInteger(oR.getHighestId());
		}
		catch (RepositoryException e)
		{
			throw new ShopServiceException("Could not instantiate ShopService: " + e.getMessage(), e);
		}
	}

	public synchronized void addProduct(ProductParameters product)
	{
		try
		{
			pR.addProduct(new Product(getNextProductId(), product));
		}
		catch (RepositoryException e)
		{
			throw new ShopServiceException("Could not add product: " + e.getMessage(), e);
		}
	}

	public void addProductToCustomer(int productId, String customerUsername)
	{
		addProductToCustomer(productId, customerUsername, 1);
	}

	public synchronized void addProductToCustomer(int productId, String customerUsername, int amount)
	{
		try
		{
			if (pR.getProduct(productId).getQuantity() >= amount)
			{
				Customer customer = cR.getCustomer(customerUsername);

				for (int i = 0; i < amount; i++)
				{
					customer.addProductToShoppingCart(productId);
				}
				// Make the repository record the changes to customer
				cR.updateCustomer(customer);
			}
		}
		catch (RepositoryException e)
		{
			throw new ShopServiceException("Could not add product to customer: " + e.getMessage(), e);
		}
	}

	public synchronized Product getProductWithId(int productId)
	{
		try
		{
			return pR.getProduct(productId);
		}
		catch (RepositoryException e)
		{
			throw new ShopServiceException("Could not getProduct: " + e.getMessage(), e);
		}
	}

	public synchronized HashMap<Integer, Product> getProducts()
	{
		try
		{
			return pR.getProducts();
		}
		catch (RepositoryException e)
		{
			throw new ShopServiceException("Could not get products.: " + e.getMessage(), e);
		}
	}

	public synchronized void removeProduct(int productId)
	{
		try
		{
			for (Customer c : cR.getCustomers().values())
			{
				boolean aProductWasRemoved = c.removeProductsWithIdFromShoppingCart(productId);
				if (aProductWasRemoved)
				{
					updateCustomer(c);
				}
			}
			pR.removeProduct(productId);
		}
		catch (RepositoryException | ModelException e)
		{
			throw new ShopServiceException("Could not remove product: " + e.getMessage(), e);
		}
	}

	public synchronized void updateProduct(int productId, ProductParameters productParams)
	{
		try
		{
			pR.updateProduct(new Product(productId, productParams));
		}
		catch (RepositoryException e)
		{
			throw new ShopServiceException("Could not updateProduct: " + e.getMessage(), e);
		}
	}

	public synchronized void addCustomer(Customer customer)
	{
		try
		{
			cR.addCustomer(customer);
		}
		catch (RepositoryException e)
		{
			throw new ShopServiceException("Could not add customer: " + e.getMessage(), e);
		}
	}

	public synchronized Customer getCustomer(String customerUsername)
	{
		try
		{
			return cR.getCustomer(customerUsername);
		}
		catch (RepositoryException e)
		{
			throw new ShopServiceException("Could not get customer: " + e.getMessage(), e);
		}
	}

	public synchronized void updateCustomer(Customer customer)
	{
		try
		{
			cR.updateCustomer(customer);
		}
		catch (RepositoryException e)
		{
			throw new ShopServiceException("Could not update customer: " + e.getMessage(), e);
		}
	}

	public synchronized void removeCustomer(String customerUsername)
	{
		try
		{
			cR.removeCustomer(customerUsername);
		}
		catch (RepositoryException e)
		{
			throw new ShopServiceException("Could not remove customer: " + e.getMessage(), e);
		}
	}

	public synchronized void createOrder(String customerUsername)
	{
		try
		{
			Customer customer = cR.getCustomer(customerUsername);
			ArrayList<Integer> orderedProductIds = customer.getShoppingCart();

			// decrease stock quantity of products in product repository
			pR.decreaseQuantityOfProductsByOne(orderedProductIds);
			try
			{
				// place a order containing the products removed from stock
				int orderId = getNextOrderId();
				oR.addOrder(new Order(orderId, customerUsername, orderedProductIds));
				try
				{
					// clear the customers shopping cart
					customer.getShoppingCart().clear();
					cR.updateCustomer(customer);
				}
				catch (RepositoryException e)
				{
					// FIXME Problem: om det går fel med återställningen i catch
					// satserna, blir det fel i repot
					oR.removeOrder(orderId);
					throw e;
				}
			}
			catch (RepositoryException e)
			{
				pR.increaseQuantityOfProductsByOne(orderedProductIds);
				throw e;
			}
		}
		catch (RepositoryException e)
		{
			throw new ShopServiceException("Could not create order: " + e.getMessage(), e);
		}
	}

	public synchronized Order getOrder(int orderId)
	{
		try
		{
			return oR.getOrder(orderId);
		}
		catch (RepositoryException e)
		{
			throw new ShopServiceException("Could not get order: " + e.getMessage(), e);
		}
	}

	public synchronized HashMap<Integer, Order> getOrders()
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