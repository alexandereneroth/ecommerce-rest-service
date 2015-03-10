package se.groupone.ecommerce.repository.sql;

import se.groupone.ecommerce.exception.RepositoryException;
import se.groupone.ecommerce.model.Customer;
import se.groupone.ecommerce.model.Product;
import se.groupone.ecommerce.model.ProductParameters;

public class SQLTest
{
	public static void main(String[] args) throws RepositoryException
	{
		SQLCustomer sql = new SQLCustomer();
		
		final String username = "kira";
		final String password = "Elf";
		final String email = "erik.welander@hotmailcom";
		final String firstName = "Erik";
		final String lastName = "Welander";
		final String address = "Ter";
		final String mobile = "073";
		
		final String username2 = "rednalew";
		final String password2 = "Albin1";
		final String email2 = "viktor.welander@hotmail.com";
		final String firstName2 = "Viktor";
		final String lastName2 = "Welander";
		final String address2 = "Ter2";
		final String mobile2 = "0732";
		
		Customer cu = new Customer(username, password, email, firstName, lastName, address, mobile);
		Customer cu2 = new Customer(username2, password2, email2, firstName2, lastName2, address2, mobile2);
		
		final int pID = 1;
		final String pName = "Voffla";
		final String pCategory = "Bakverk";
		final String pManufacturer = "Steffe";
		final String pDescription = "En våffa gjord av steffe!";
		final String pImg = "delicious.png";
		final double pPrice = 10;
		final int pQuantity = 100;
		
		final int pID2 = 2;
		final String pName2 = "Pannkaka";
		final String pCategory2 = "Bakverk";
		final String pManufacturer2 = "SteffeKeff";
		final String pDescription2 = "En pannkaka gjord av steffe!";
		final String pImg2 = "delicious2.png";
		final double pPrice2 = 5;
		final int pQuantity2 = 200;
		
		final Product p1 = new Product(pID,
				     new ProductParameters(pName,
										   pCategory,
										   pManufacturer,
										   pDescription,
										   pImg,
										   pPrice,
									       pQuantity));
		
		final Product p2 = new Product(pID2,
				     new ProductParameters(pName2,
								    	   pCategory2,
										   pManufacturer2,
										   pDescription2,
										   pImg2,
										   pPrice2,
										   pQuantity2));
		
		
		try
		{
			sql.addCustomer(cu);
			sql.addCustomer(cu2);
			System.out.println(cu);
			System.out.println(cu2);
			
			System.out.println(sql.getCustomer(cu.getUsername()));
			System.out.println(sql.getCustomer(cu2.getUsername()));
		}
		catch(RepositoryException e)
		{
			System.out.println(e);
		}

		
		System.out.println(cu);
		System.out.println(cu2);
		
		
	}
}
