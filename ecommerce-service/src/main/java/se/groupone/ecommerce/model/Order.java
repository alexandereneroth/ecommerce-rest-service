package se.groupone.ecommerce.model;

import java.util.Date;
import java.util.ArrayList;

public final class Order
{
	private final int id;
	private final String customerUsername;

	private Date dateCreated, dateShipped = null;
	private ArrayList<Integer> productIds = new ArrayList<>();

	@SuppressWarnings("unchecked")
	// För att kunna göra en kloning av shoppingcart
	public Order(String username, ArrayList<String> shoppingCart)
	{
		// Klonar shoppingcart, ej referens då shoppingcarten kommer tömmas.
		productIds = (ArrayList<String>) shoppingCart.clone();
		this.username = username;
		dateCreated = new Date(System.currentTimeMillis());
	}

	public Order(String username, Date dateCreated, Date dateShipped, ArrayList<String> products)
	{
		this.username = username;
		this.dateCreated = dateCreated;
		this.dateShipped = dateShipped;
		this.productIds = products;
	}

	public void shipIt()
	{
		dateShipped = new Date(System.currentTimeMillis());
	}

	public boolean isShipped()
	{
		if (dateShipped != null)
		{
			return true;
		}
		return false;
	}

	public Date getDateShipped()
	{
		return dateShipped;
	}

	public String getUsername()
	{
		return username;
	}

	public Date getDateCreated()
	{
		return dateCreated;
	}

	public ArrayList<String> getProducts()
	{
		return productIds;
	}

	@Override
	public String toString()
	{
		return "Order [userName=" + username
				+ ", dateCreated=" + dateCreated + ", dateShipped="
				+ dateShipped + ", products=" + productIds + "]";
	}

}