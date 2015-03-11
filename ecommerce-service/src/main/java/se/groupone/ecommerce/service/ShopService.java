package se.groupone.ecommerce.service;

import se.groupone.ecommerce.exception.ShopServiceException;
import se.groupone.ecommerce.exception.RepositoryException;
import se.groupone.ecommerce.model.Customer;
import se.groupone.ecommerce.model.Order;
import se.groupone.ecommerce.model.Product;
import se.groupone.ecommerce.model.ProductParameters;
import se.groupone.ecommerce.repository.CustomerRepository;
import se.groupone.ecommerce.repository.OrderRepository;
import se.groupone.ecommerce.repository.ProductRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public final class ShopService
{
	private final CustomerRepository customerRepository;
	private final ProductRepository productRepository;
	private final OrderRepository orderRepository;

	private final AtomicInteger productIDGenerator;
	private final AtomicInteger orderIDGenerator;

	public ShopService(CustomerRepository customerRepository,
			ProductRepository productRepository,
			OrderRepository orderRepository)
	{
		this.customerRepository = customerRepository;
		this.productRepository = productRepository;
		this.orderRepository = orderRepository;
		try
		{
			productIDGenerator = new AtomicInteger(productRepository.getHighestId());
			orderIDGenerator = new AtomicInteger(orderRepository.getHighestId());
		}
		catch (RepositoryException e)
		{
			throw new ShopServiceException("Could not instantiate ShopService: "
					+ e.getMessage(), e);
		}
	}

	public synchronized Product addProduct(ProductParameters productParams)
	{
		Product newProduct;
		try
		{
			newProduct = new Product(getNextProductId(), productParams);
			productRepository.addProduct(newProduct);
		}
		catch (RepositoryException e)
		{
			throw new ShopServiceException("Could not add product: "
					+ e.getMessage(), e);
		}

		return newProduct;
	}

	// Defaults to quantity: 1 if no amount is provided.
	public void addProductToCustomer(int productId, String customerUsername)
	{
		addProductToCustomer(productId, customerUsername, 1);
	}

	public synchronized void addProductToCustomer(int productId, String customerUsername, int amount)
	{
		try
		{
			if (productRepository.getProduct(productId).getQuantity() >= amount)
			{
				Customer customer = customerRepository.getCustomer(customerUsername);

				for (int i = 0; i < amount; i++)
				{
					customer.addProductToShoppingCart(productId);
				}
				// Make the repository record the changes to customer
				customerRepository.updateCustomer(customer);
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
			return productRepository.getProduct(productId);
		}
		catch (RepositoryException e)
		{
			throw new ShopServiceException("Could not getProduct: " + e.getMessage(), e);
		}
	}

	public synchronized List<Product> getProducts()
	{
		try
		{
			return productRepository.getProducts();
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
			try
			{
				// If there are any customers first remove the item from their
				// carts
				for (Customer c : customerRepository.getCustomers())
				{
					boolean aProductWasRemoved = c.removeProductsWithIdFromShoppingCart(productId);
					if (aProductWasRemoved)
					{
						updateCustomer(c);
					}
				}

			}
			catch (Exception e) // TODO Be more specific and handle exception
			{
				// No users in DB - no action needed
			}

			productRepository.removeProduct(productId);
		}
		catch (RepositoryException e)
		{
			throw new ShopServiceException("Could not remove product: " + e.getMessage(), e);
		}
	}

	public synchronized void updateProduct(int productId, ProductParameters productParams)
	{
		try
		{
			productRepository.updateProduct(new Product(productId, productParams));
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
			customerRepository.addCustomer(customer);
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
			return customerRepository.getCustomer(customerUsername);
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
			customerRepository.updateCustomer(customer);
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
			customerRepository.removeCustomer(customerUsername);
		}
		catch (RepositoryException e)
		{
			throw new ShopServiceException("Could not remove customer: " + e.getMessage(), e);
		}
	}

	public synchronized Order createOrder(String customerUsername)
	{
		Order newOrder;
		try
		{
			Customer customer = customerRepository.getCustomer(customerUsername);
			ArrayList<Integer> orderedProductIds = customer.getShoppingCart();
			if (orderedProductIds.isEmpty())
			{
				throw new ShopServiceException("This user has no items in their cart");
			}
			// decrease stock quantity of products in product repository
			productRepository.decreaseQuantityOfProductsByOne(orderedProductIds);
			try
			{
				// place a order containing the products removed from stock
				int orderId = getNextOrderId();
				newOrder = new Order(orderId, customerUsername, orderedProductIds);
				orderRepository.addOrder(newOrder);
				try
				{
					// clear the customers shopping cart
					customer.getShoppingCart().clear();
					customerRepository.updateCustomer(customer);
				}
				catch (RepositoryException e)
				{
					// FIXME Problem: om det går fel med återställningen i catch
					// satserna, blir det fel i repot
					orderRepository.removeOrder(orderId);
					throw e;
				}
			}
			catch (RepositoryException e)
			{
				productRepository.increaseQuantityOfProductsByOne(orderedProductIds);
				throw e;
			}
		}
		catch (RepositoryException e)
		{
			throw new ShopServiceException("Could not create order: " + e.getMessage(), e);
		}
		return newOrder;
	}

	public synchronized Order getOrder(int orderId)
	{
		try
		{
			Order order = orderRepository.getOrder(orderId);
			return order;
		}
		catch (RepositoryException e)
		{
			throw new ShopServiceException("Could not get order: " + e.getMessage(), e);
		}
	}

	public synchronized List<Order> getOrders(String customerUsername)
	{
		try
		{
			customerRepository.getCustomer(customerUsername); // Should throw
																// exception if
																// user
			// does not exist
			return orderRepository.getOrders(customerUsername);
		}
		catch (RepositoryException e)
		{
			throw new ShopServiceException("Could not get orders: " + e.getMessage(), e);
		}
	}

	public synchronized void updateOrder(Order order)
	{
		try
		{
			orderRepository.updateOrder(order);
		}
		catch (RepositoryException e)
		{
			throw new ShopServiceException("Could not update order: " + e.getMessage(), e);
		}
	}

	public synchronized void removeOrder(int orderId)
	{
		try
		{
			orderRepository.removeOrder(orderId);
		}
		catch (RepositoryException e)
		{
			throw new ShopServiceException("Could not remove order: " + e.getMessage(), e);
		}
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