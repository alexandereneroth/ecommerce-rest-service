package se.groupone.ecommerce.model;
import java.util.Date;
import java.util.ArrayList;

public final class Order
{
    private final String username;

	private Date dateCreated, dateShipped = null;
    private ArrayList<String> products = new ArrayList<String>();
    
    @SuppressWarnings("unchecked") //För att kunna göra en kloning av shoppingcart
	public Order(String username, ArrayList<String> shoppingCart)
    {   
        //Klonar shoppingcart, ej referens då shoppingcarten kommer tömmas.
    	products = (ArrayList<String>) shoppingCart.clone();
    	this.username = username;
        dateCreated = new Date(System.currentTimeMillis());
    }
    
    public Order(String username, Date dateCreated, Date dateShipped, ArrayList<String> products)
	{
		this.username = username;
		this.dateCreated = dateCreated;
		this.dateShipped = dateShipped;
		this.products = products;
	}

	public void shipIt()
    {
        dateShipped = new Date(System.currentTimeMillis());
    }
    
    public boolean isShipped()
    {
    	if(dateShipped != null)
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
		return products;
	}
	
	@Override
	public String toString() 
	{
		return "Order [userName=" + username
				+ ", dateCreated=" + dateCreated + ", dateShipped="
				+ dateShipped + ", products=" + products + "]";
	}
	
}