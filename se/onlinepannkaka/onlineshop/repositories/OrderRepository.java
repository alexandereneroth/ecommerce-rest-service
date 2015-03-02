package se.onlinepannkaka.onlineshop.repositories;
import java.util.HashMap;

import se.onlinepannkaka.onlineshop.models.Customer;
import se.onlinepannkaka.onlineshop.models.Order;

public interface OrderRepository
{
	
    public void addOrder(Customer customer);
    
    public Order getOrder(String key);
    
    public HashMap<String,Order> getOrders();
    
}