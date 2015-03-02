package se.groupone.ecommerce.repository;
import se.groupone.ecommerce.model.Customer;
import se.groupone.ecommerce.model.Order;
import java.util.HashMap;

public interface OrderRepository
{
    public void addOrder(Customer customer);
    
    public Order getOrder(String key);
    
    public HashMap<String,Order> getOrders();   
}