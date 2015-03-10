package se.groupone.ecommerce.model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;

public final class Order
{
	private final int id;
	private final String customerUsername;

	private Date dateCreated, dateShipped = null;
	private ArrayList<Integer> productIds = new ArrayList<>();
	private final SimpleDateFormat sqlDateFormat = new SimpleDateFormat("yyyy-MM-dd");	

	@SuppressWarnings("unchecked")
	public Order(int id,
			     String customerUsername,
			     ArrayList<Integer> shoppingCartProductIds)
	{
		this.id = id;
		this.productIds = (ArrayList<Integer>) shoppingCartProductIds.clone();
		this.customerUsername = customerUsername;
		this.dateCreated = new Date(System.currentTimeMillis());
	}
	@SuppressWarnings("unchecked")
	public Order(int id,
				 String customerUsername,
				 ArrayList<Integer> shoppingCartProductIds,
				 Date dateCreated,
				 Date dateShipped)
	{
		this.id = id;
		this.productIds = (ArrayList<Integer>) shoppingCartProductIds.clone();
		this.customerUsername = customerUsername;
		this.dateCreated = dateCreated;
	}

	public int getId()
	{
		return id;
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
		return customerUsername;
	}

	public Date getDateCreated()
	{
		return dateCreated;
	}

	public ArrayList<Integer> getProductIds()
	{
		return productIds;
	}

	@Override
	public String toString()
	{
		return "Order [userName=" + customerUsername
				+ ", dateCreated=" + dateCreated + ", dateShipped="
				+ dateShipped + ", products=" + productIds + "]";
	}
	@Override
	public boolean equals(Object other)
	{
		if (other == this)
		{
			return true;
		}
		if (other instanceof Order)
		{
			Order o = (Order) other;
			if (this.getId() == o.getId()
			 && this.getUsername().equals(o.getUsername())
			 && this.getProductIds().equals(o.getProductIds())
			 && sqlDateFormat.format(this.getDateCreated()).equals(sqlDateFormat.format(o.getDateCreated()))
			 && this.isShipped() == o.isShipped()
			 )
			{
				return true;
			}
		}
		return false;
	}
	

}