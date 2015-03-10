package se.groupone.ecommerce.repository.sql;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import se.groupone.ecommerce.exception.RepositoryException;
import se.groupone.ecommerce.model.Product;
import se.groupone.ecommerce.model.ProductParameters;
import se.groupone.ecommerce.repository.ProductRepository;

public class SQLProduct implements ProductRepository
{
	private final String dbTable = "product";
	private final SQLConnector sql;
	public SQLProduct() throws RepositoryException
	{
		try
		{
			sql = new SQLConnector(DBInfo.host, DBInfo.port, DBInfo.username, DBInfo.password, DBInfo.database);
		}
		catch(SQLException e)
		{
			throw new RepositoryException("Could not construct SQLProduct: Could not construct database object", e);
		}
	}
	
	@Override
	public void addProduct(final Product product) throws RepositoryException
	{
		try
		{
			StringBuilder addProductQuery = new StringBuilder();
			addProductQuery.append("INSERT INTO " + DBInfo.database + "." + dbTable +" ");
			addProductQuery.append("(id_product, title, category, manufacturer, description, img, price, quantity) ");
			addProductQuery.append("VALUES(" + product.getId() + ", ");
			addProductQuery.append("'" + product.getTitle() + "', ");
			addProductQuery.append("'" + product.getCategory() + "', ");
			addProductQuery.append("'" + product.getManufacturer() + "', ");
			addProductQuery.append("'" + product.getDescription() + "', ");
			addProductQuery.append("'" + product.getImg() + "', ");
			addProductQuery.append("" + product.getPrice() + ", ");
			addProductQuery.append("" + product.getQuantity() + ");");
			
			sql.query(addProductQuery.toString());
		}
		catch(SQLException e)
		{
			throw new RepositoryException("Could not add Product to database!", e);
		}
	}

	@Override
	public Product getProduct(final int productID) throws RepositoryException
	{
		ResultSet rs;
		try
		{
			StringBuilder getProductQuery = new StringBuilder();
			getProductQuery.append("SELECT * FROM " + DBInfo.database+"."+dbTable + " ");
			getProductQuery.append("WHERE id_product = " + productID + ";");
			
			rs = sql.queryResult(getProductQuery.toString());
			if (!rs.isBeforeFirst())
			{
				throw new RepositoryException("No matches for Product found in database!\nSQL QUERY: "+getProductQuery.toString());
			}
		}
		catch(SQLException e)
		{
			throw new RepositoryException("Failed to retrieve Product data from database!", e);
		}
		
		try
		{
			rs.next();
			final ProductParameters productParams = new ProductParameters(
													  rs.getString("title"),
													  rs.getString("category"),
													  rs.getString("manufacturer"),
													  rs.getString("description"),
													  rs.getString("img"),
													  rs.getDouble("price"),
													  rs.getInt("quantity"));
		
			final Product product = new Product(productID, productParams);
			rs.close();
			return product;
		}
		catch(SQLException e)
		{
			throw new RepositoryException("Failed to construct Product from database!", e);
		}

	}

	@Override
	public List<Product> getProducts() throws RepositoryException
	{
		ResultSet rs;
		final int numRows;
		try
		{
			final String numRowsQuery = "SELECT count(id_product) FROM " + DBInfo.database+"." + dbTable + ";";
			
			rs = sql.queryResult(numRowsQuery);
			if (!rs.isBeforeFirst())
			{
				throw new RepositoryException("No matches COUNT(id_product) in products!\nSQL QUERY: " + numRowsQuery);
			}
		}
		catch(SQLException e)
		{
			throw new RepositoryException("No ProductCount recieved from database!", e);
		}
		
		try
		{
			rs.next();
			numRows = rs.getInt(1);
			rs.close();
		}
		catch(SQLException e)
		{
			throw new RepositoryException("Could not parse COUNT(id_product) from Product database!", e);
		}
		
		try
		{
			final String fetchAllQuery = "SELECT * FROM " + DBInfo.database+"." + dbTable + ";";
			rs = sql.queryResult(fetchAllQuery);
		}
		catch(SQLException e)
		{
			throw new RepositoryException("Could not fetch all Products from database!", e);
		}
		
		try
		{
			List<Product> productList = new ArrayList<>();
			for(int i = 0; i < numRows; i++)
			{
				rs.next();
				final int productID = rs.getInt("id_product");
				final ProductParameters productParams = new ProductParameters(
													  rs.getString("title"),
													  rs.getString("category"),
													  rs.getString("manufacturer"),
													  rs.getString("description"),
													  rs.getString("img"),
													  rs.getDouble("price"),
													  rs.getInt("quantity"));
				final Product product = new Product(productID, productParams);
				productList.add(product);
			}
			rs.close();
			return productList;
		}
		catch(SQLException e)
		{
			throw new RepositoryException("Could not parse Products in database!", e);
		}
	}

	@Override
	public void removeProduct(final int productID) throws RepositoryException
	{
		try
		{
			final String removeQuery = "DELETE FROM " + DBInfo.database + "." + dbTable + " WHERE id_product = " + productID + ";";
			
			sql.query(removeQuery);
		}
		catch(SQLException e)
		{
			throw new RepositoryException("Could not query removal of Product!", e);
		}
		
	}

	@Override
	public void updateProduct(Product product) throws RepositoryException
	{		
		try
		{
			StringBuilder updateProductQuery = new StringBuilder();
			updateProductQuery.append("UPDATE "+DBInfo.database+"."+dbTable+" SET ");
			updateProductQuery.append("title = '"+product.getTitle()+"', ");
			updateProductQuery.append("category = '"+product.getCategory()+"', ");
			updateProductQuery.append("manufacturer = '"+product.getManufacturer()+"', ");
			updateProductQuery.append("description = '"+product.getDescription()+"', ");
			updateProductQuery.append("img = '"+product.getImg()+"', ");
			updateProductQuery.append("price = "+product.getPrice()+", ");
			updateProductQuery.append("quantity = "+product.getQuantity()+" ");
			updateProductQuery.append("WHERE id_product = "+product.getId()+";");
			
			sql.query(updateProductQuery.toString());
		}
		catch(SQLException e)
		{
			throw new RepositoryException("Could not query Product update!", e);
		}
	}

	@Override
	public void decreaseQuantityOfProductsByOne(List<Integer> ids) throws RepositoryException
	{
		 productsQuantityChange(ids, -1);
	}

	@Override
	public void increaseQuantityOfProductsByOne(List<Integer> ids) throws RepositoryException
	{
		productsQuantityChange(ids, 1);
	}

	@Override
	public int getHighestId() throws RepositoryException
	{
		ResultSet rs;
		try
		{
			final String highestIDQuery = "SELECT MAX(id_product) FROM "+DBInfo.database+"."+dbTable;
			rs = sql.queryResult(highestIDQuery);
			if (!rs.isBeforeFirst())
			{
				throw new RepositoryException("No matches MAX(id_product) in Products!\nSQL QUERY: " + highestIDQuery);
			}
		}
		catch(SQLException e)
		{
			throw new RepositoryException("Could not get query MAX Product ID!", e);
		}
		
		try
		{
			rs.next();
			final int highestProductID = rs.getInt(1);
			rs.close();
			return highestProductID;	
		}
		catch(SQLException e)
		{
			throw new RepositoryException("Could not parse MAX(id_product) Product database!", e);
		}
		
	}
	
	private void productsQuantityChange(final List<Integer> ids, final int quantityChange) throws RepositoryException
	{
		
		Product product[] = new Product[ids.size()];
		for(int i = 0; i < ids.size(); i++)
		{
			product[i] = getProduct(ids.get(i));
		}
		
		StringBuilder updateQuantityQuery = new StringBuilder();
		for(int i = 0; i < ids.size(); i++)
		{
			updateQuantityQuery.append("UPDATE " + DBInfo.database+"." + dbTable + " SET ");
			updateQuantityQuery.append("quantity = " + (product[i].getQuantity() + quantityChange) + " ");
			updateQuantityQuery.append("WHERE id_product = " + product[i].getId() + ";");
			try
			{
				sql.query(updateQuantityQuery.toString());
				
				updateQuantityQuery.delete(0, updateQuantityQuery.length());
			}
			catch(SQLException e)
			{
				throw new RepositoryException("Could not query Product quantity update!", e);
			}
		}
	}
}
