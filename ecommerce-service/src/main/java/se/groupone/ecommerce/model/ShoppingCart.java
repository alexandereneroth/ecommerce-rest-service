package se.groupone.ecommerce.model;

import java.util.ArrayList;

public class ShoppingCart
{
	private ArrayList<String> products = new ArrayList<String>();

	public void addProduct(String product)
	{
		products.add(product);
	}

	public void removeProduct(String product)
	{
		products.remove(product);
	}

	public ArrayList<String> getProducts()
	{
		return products;
	}

	public void clear()
	{
		products.clear();
	}

	@Override
	public String toString()
	{
		return "ShoppingCart [products=" + products + "]";
	}

}