package se.groupone.ecommerce.model;

import java.util.ArrayList;
import java.util.Collections;

public class ShoppingCart
{
	private ArrayList<Integer> productIds = new ArrayList<>();

	public void addProduct(int product)
	{
		productIds.add(product);
	}

	public void removeProduct(Integer productId) throws ModelException
	{
		if (productIds.contains(productId))
		{
			productIds.remove(productId);
		}
		else
		{
			throw new ModelException("Cannot remove product from cart: product does not exist.");
		}
	}

	public void removeAllProductsWithId(Integer productId) throws ModelException
	{
		if (productIds.contains(productId))
		{
			productIds.removeAll(Collections.singleton(productId));
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