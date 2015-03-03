package se.groupone.ecommerce.model;
import java.util.ArrayList;

public final class Customer
{
	private final String username;
    private String firstName,
    			   lastName,
                   address,
                   mobileNumber,
                   password,
                   email;
    private ArrayList<String> orders = new ArrayList<String>();
    private ShoppingCart shoppingCart = new ShoppingCart();
    
    public Customer(
                    String username,
                    String password,
                    String email,
                    String firstName,
                    String lastName,
                    String address,
                    String mobileNumber
                    )
    {
        this.username = username;
        this.password = password;
        this.email = email;
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
    		orders.add(username.concat(new Integer(orders.size()+1).toString()));
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
		return null;
    }
	
	@Override
	public String toString()
	{
		return firstName+" "+lastName;
	}

	public String getPassword()
	{
		return password;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

	public String getEmail()
	{
		return email;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}

	public String getUsername()
	{
		return username;
	}
}
