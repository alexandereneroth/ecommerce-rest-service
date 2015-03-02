package se.onlinepannkaka.onlineshop.services;
import java.util.HashMap;

import se.onlinepannkaka.onlineshop.models.Customer;
import se.onlinepannkaka.onlineshop.models.Order;
import se.onlinepannkaka.onlineshop.models.Product;
import se.onlinepannkaka.onlineshop.repositories.CustomerRepository;
import se.onlinepannkaka.onlineshop.repositories.OrderRepository;
import se.onlinepannkaka.onlineshop.repositories.ProductRepository;


public class ShopService
{
	
	private CustomerRepository cR;
	private ProductRepository pR;
	private OrderRepository oR;
	
	public ShopService(CustomerRepository cR, ProductRepository pR, OrderRepository oR)
	{
		this.cR = cR;
		this.pR = pR;
		this.oR = oR;
	}
	
	public void addProduct(Product product)
	{
		pR.addProduct(product);
	}
	
	public void addProductToCustomer(String title, String username){
		addProductToCustomer(title,username,1);
	}
	
	public void addProductToCustomer(String title, String username, int amount){
		if(pR.getProduct(title).getQuantity() >= amount)
		{
			for(int i = 0; i < amount; i++){
				cR.getCustomer(username).addProduct(title);	
			}
		}
	}
	
	public Product getProduct(String title)
	{
		return pR.getProduct(title);
	}
	
	public HashMap<String,Product> getProducts()
	{
		return pR.getProducts();
	}
	
	public void removeProduct(String title)
	{
		for(Customer c : cR.getCustomers().values())
		{
			c.removeProduct(title);
		}
		pR.removeProduct(title);
	}	
	
	public void updateProduct(Product product)
	{
		pR.updateProduct(product);
	}
	
	public void addCustomer(Customer customer)
	{
		cR.addCustomer(customer);
	}
	
	public Customer getCustomer(String username)
	{
		return cR.getCustomer(username);	
	}
	
	public void updateCustomer(Customer customer)
	{
		cR.updateCustomer(customer);
	}
	
	public void removeCustomer(String username)
	{
		cR.removeCustomer(username);
	}
	
	public void addOrder(String username)
	{
		//Will decrease the product quantity by one for each item in the shoppingCart
		for(String title: cR.getCustomer(username).getShoppingCart()){
			pR.getProduct(title).decreaseQuantity(1);
		}
		oR.addOrder(cR.getCustomer(username));
	}
	
    public Order getOrder(String key)
    {    	
    	return oR.getOrder(key); 
    }
    
    public HashMap<String,Order> getOrders()
    {
    	return oR.getOrders();
    }
	
}