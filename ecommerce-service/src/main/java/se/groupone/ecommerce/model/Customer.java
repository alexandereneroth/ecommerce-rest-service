package se.groupone.ecommerce.model;
import java.util.ArrayList;

public final class Customer extends Account
{
    private String firstName,
    			   lastName,
                   address,
                   mobileNumber;
    private ArrayList<String> orders = new ArrayList<String>();
    private ShoppingCart shoppingCart = new ShoppingCart();
    
    public Customer(
                    String userName,
                    String password,
                    String email,
                    String firstName,
                    String lastName,
                    String address,
                    String mobileNumber
                    )
    {
        super(userName, password, email); 
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.mobileNumber = mobileNumber;
    }
    
    public void addProduct(String product)
    {
    	if(product != null)
    	{
    		shoppingCart.addProduct(product);
    	}
    }
    
    public void removeProduct(String product)
    {
    	shoppingCart.removeProduct(product);
    }
    
    public ArrayList<String> getShoppingCart()
    {
        return shoppingCart.getProducts();
    }
    
    public ArrayList<String> getOrders()
    {
        return orders;
    }
    
    public void addOrder()
    {
    	if(!shoppingCart.getProducts().isEmpty())
    	{
    		orders.add(super.getUsername().concat(new Integer(orders.size()+1).toString()));
    	}
    }
    
    public String getFirstName() 
    {
		return firstName;
	}

	public void setFirstName(String firstName) 
	{
		this.firstName = firstName;
	}

	public String getLastName() 
	{
		return lastName;
	}

	public void setLastName(String lastName) 
	{
		this.lastName = lastName;
	}

	public String getAddress() 
	{
		return address;
	}

	public void setAddress(String address) 
	{
		this.address = address;
	}

	public String getMobileNumber() 
	{
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) 
	{
		this.mobileNumber = mobileNumber;
	}

	public String getOrder(int index)
    {
		if(index >= 0 && index < orders.size()){
			return orders.get(index);
		}
		return "";
    }
	
	@Override
	public String toString()
	{
		return firstName+" "+lastName;
	}
}
