package se.groupone.ecommerce.repository;

import se.groupone.ecommerce.exception.RepositoryException;
import se.groupone.ecommerce.model.Product;

import java.util.HashMap;

public interface ProductRepository
{
	public void addProduct(Product product) throws RepositoryException;

	public Product getProduct(int id) throws RepositoryException;

	public HashMap<Integer, Product> getProducts();

	public void removeProduct(int id) throws RepositoryException;

	public void updateProduct(Product product) throws RepositoryException;
	
	public int getHighestId();
}
