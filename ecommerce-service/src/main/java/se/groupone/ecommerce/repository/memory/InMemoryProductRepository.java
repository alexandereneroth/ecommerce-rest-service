package se.groupone.ecommerce.repository.memory;

import se.groupone.ecommerce.exception.RepositoryException;
import se.groupone.ecommerce.model.Product;
import se.groupone.ecommerce.repository.ProductRepository;

import java.util.HashMap;

public class InMemoryProductRepository implements ProductRepository
{

	private HashMap<Integer, Product> products = new HashMap<Integer, Product>();

	@Override
	public void addProduct(Product product) throws RepositoryException
	{
		if (!products.containsKey(product.getId()))
		{
			products.put(product.getId(), product);
			return;
		}
		throw new RepositoryException("Cannot get add: product with this id already exist in repository");
	}

	@Override
	public Product getProduct(int id) throws RepositoryException
	{
		if (products.containsKey(id))
		{
			return products.get(id);
		}
		throw new RepositoryException("Cannot get product: product with this id does not exist in repository");
	}

	@Override
	public HashMap<Integer, Product> getProducts()
	{
		return products;
	}

	@Override
	public void removeProduct(int productId)
	{
		products.remove(productId);
	}

	@Override
	public void updateProduct(Product product)
	{
		products.replace(product.getId(), product);
	}

	@Override
	public int getHighestId()
	{
		return 0;
	}
}
