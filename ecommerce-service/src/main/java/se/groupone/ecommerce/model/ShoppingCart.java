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

	/**
	 * Removes all products with the specified id from this shopping cart.
	 * 
	 * @param productId
	 * @return true if a product was removed
	 * @throws ModelException
	 */
	public boolean removeAllProductsWithId(Integer productId) throws ModelException
	{
		if (productIds.contains(productId))
		{
			return productIds.removeAll(Collections.singleton(productId));
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