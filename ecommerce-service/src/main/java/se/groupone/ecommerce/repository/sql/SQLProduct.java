package se.groupone.ecommerce.repository.sql;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import se.groupone.ecommerce.exception.RepositoryException;
import se.groupone.ecommerce.model.Product;
import se.groupone.ecommerce.model.ProductParameters;
import se.groupone.ecommerce.repository.ProductRepository;

public class SQLProduct implements ProductRepository
{
	private final String dbName = "ecomm";
	private final String dbTable = "product";
	private final SQLConnector sql;
	public SQLProduct() throws RepositoryException
	{
		try
		{
			sql = new SQLConnector(DBInfo.host, DBInfo.port, DBInfo.username, DBInfo.password, dbName);
		}
		catch(SQLException e)
		{
			throw new RepositoryException("Could not construct SQLProduct: Could not construct database object", e);
		}
	}
	
	@Override
	public void addProduct(final Product product) throws RepositoryException
	{
		StringBuilder builder = new StringBuilder();
		builder.append("INSERT INTO " + dbName + "." + dbTable +" ");
		builder.append("(id_product, title, category, manufacturer, description, img, price, quantity) ");
		builder.append("VALUES(" + product.getId() + ", ");
		builder.append("'" + product.getTitle() + "', ");
		builder.append("'" + product.getCategory() + "', ");
		builder.append("'" + product.getManufacturer() + "', ");
		builder.append("'" + product.getDescription() + "', ");
		builder.append("'" + product.getImg() + "', ");
		builder.append("" + product.getPrice() + ", ");
		builder.append("" + product.getQuantity() + ");");
		try
		{
			sql.query(builder.toString());
		}
		catch(SQLException e)
		{
			throw new RepositoryException("Could not add Product to database!", e);
		}
	}

	@Override
	public Product getProduct(final int productID) throws RepositoryException
	{
		StringBuilder builder = new StringBuilder();
		builder.append("SELECT * FROM " + dbName+"."+dbTable + " ");
		builder.append("WHERE id_product = " + productID + ";");
		ResultSet rs;
		try
		{
			rs = sql.queryResult(builder.toString());
			if (!rs.isBeforeFirst())
			{
				throw new RepositoryException("No matches for Product found in database!\nSQL QUERY: "+builder.toString());
			}
			rs.next();
		}
		catch(SQLException e)
		{
			throw new RepositoryException("Failed to retrieve Product data from database!", e);
		}
		
		try
		{
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
	public HashMap<Integer, Product> getProducts() throws RepositoryException
	{
		ResultSet rs;
		final int numRows;
		try
		{
			final String numRowsQuery = "SELECT count(id_product) FROM "+dbName+"."+dbTable+";";
			rs = sql.queryResult(numRowsQuery);
			rs.next();
			numRows = rs.getInt(1);
		}
		catch(SQLException e)
		{
			throw new RepositoryException("No ProductCount recieved from database!", e);
		}
		
		try
		{
			final String fetchAllQuery = "SELECT * FROM "+dbName+"."+dbTable+";";
			rs = sql.queryResult(fetchAllQuery);
		}
		catch(SQLException e)
		{
			throw new RepositoryException("Could not fetch all Products from database!", e);
		}
		
		try
		{
			HashMap<Integer, Product> productList = new HashMap<>();
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
				productList.put(productID, product);
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
		final String removeQuery = "DELETE FROM " + dbName + "." + dbTable + " WHERE id_product = " + productID + ";";
		try
		{
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
		StringBuilder builder = new StringBuilder();
		builder.append("UPDATE "+dbName+"."+dbTable+" SET ");
		builder.append("id_product = "+product.getId()+", ");
		builder.append("title = '"+product.getTitle()+"', ");
		builder.append("category = '"+product.getCategory()+"', ");
		builder.append("manufacturer = '"+product.getManufacturer()+"', ");
		builder.append("description = '"+product.getDescription()+"', ");
		builder.append("img = '"+product.getImg()+"', ");
		builder.append("price = "+product.getPrice()+", ");
		builder.append("quantity = "+product.getQuantity()+" ");
		builder.append("WHERE id_product = "+product.getId()+";");
		
		try
		{
			sql.query(builder.toString());
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
		final String query = "SELECT MAX(id_product) FROM "+dbName+"."+dbTable;
		ResultSet rs;
		try
		{
			rs = sql.queryResult(query);
			rs.next();
			final int highestProductID = rs.getInt(1);
			rs.close();
			return highestProductID;
		}
		catch(SQLException e)
		{
			throw new RepositoryException("Could not get query MAX Product ID!", e);
		}
	}
	
	private void productsQuantityChange(final List<Integer> ids, final int quantityChange) throws RepositoryException
	{
		for(int i = 0; i < ids.size(); i++)
		{
			Product product = getProduct(ids.get(i));
			StringBuilder builder = new StringBuilder();
			builder.append("UPDATE "+dbName+"."+dbTable+" SET ");
			builder.append("quantity = "+(product.getQuantity()+quantityChange)+" ");
			builder.append("WHERE id_product = "+product.getId()+";");
			try
			{
				sql.query(builder.toString());
			}
			catch(SQLException e)
			{
				throw new RepositoryException("Could not query Product quantity update!", e);
			}
		}
	}
}
