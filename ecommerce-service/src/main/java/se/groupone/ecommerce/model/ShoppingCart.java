package se.groupone.ecommerce.model;

import java.util.ArrayList;

public class ShoppingCart
{
	private ArrayList<Integer> productIds = new ArrayList<>();

	public void addProduct(int product)
	{
		productIds.add(product);
	}

	// The argument needs to be an object,
	// otherwise remove removes at the index of the int.
	public void removeProduct(Integer product) throws ModelException
	{
		if (productIds.contains(product))
		{
			productIds.remove(product);
		}
		else
		{
			throw new ModelException("Cannot remove product from cart: product does not exist.");
		}
	}

	public ArrayList<Integer> getProductIds()
	{
		return productIds;
	}

	public void clear()
	{
		productIds.clear();
	}

	@Override
	public String toString()
	{
		return "ShoppingCart [products=" + productIds + "]";
	}

}