package se.groupone.ecommerce.repository.memory;

import se.groupone.ecommerce.model.Product;
import se.groupone.ecommerce.repository.ProductRepository;
import java.util.HashMap;

public class InMemoryProductRepository implements ProductRepository
{

	private HashMap<String, Product> products = new HashMap<String, Product>();

	@Override
	public void addProduct(Product product)
	{
		if (!products.containsKey(product.getTitle()))
		{
			products.put(product.getTitle(), product);
		}
	}

	@Override
	public Product getProduct(String title)
	{
		if (products.containsKey(title))
		{
			return products.get(title);
		}
		return new Product("", "", "", "", "", 0, 0); // TODO Will return an
														// empty
														// product to counter
														// nullPointer
	}

	@Override
	public HashMap<String, Product> getProducts()
	{
		return products;
	}

	@Override
	public void removeProduct(String title)
	{
		products.remove(title);
	}

	@Override
	public void updateProduct(Product product)
	{
		products.replace(product.getTitle(), product);
	}
}
