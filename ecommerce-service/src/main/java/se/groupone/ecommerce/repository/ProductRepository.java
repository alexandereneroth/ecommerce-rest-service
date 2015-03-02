package se.groupone.ecommerce.repository;
import se.groupone.ecommerce.model.Product;
import java.util.HashMap;

public interface ProductRepository 
{
	public void addProduct(Product product);
	
	public Product getProduct(String title);
	
	public HashMap<String,Product> getProducts();
	
	public void removeProduct(String title);
	
	public void updateProduct(Product product);
}
