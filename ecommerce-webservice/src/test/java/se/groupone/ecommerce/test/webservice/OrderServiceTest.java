package se.groupone.ecommerce.test.webservice;

import se.groupone.ecommerce.model.Customer;
import se.groupone.ecommerce.model.ProductParameters;

import org.junit.Before;
import org.junit.Test;

public class OrderServiceTest
{
	private ProductParameters productParams1;
	private ProductParameters productParams2;
	private Customer customer1;
	private Customer customer2;
	

	@Before
	public void Init()
	{
		final String pName = "Voffla";
		final String pCategory = "Bakverk";
		final String pManufacturer = "Steffe";
		final String pDescription = "En våffa gjord av steffe!";
		final String pImg = "delicious.png";
		final double pPrice = 10;
		final int pQuantity = 100;
		
		final String pName2 = "Pannkaka";
		final String pCategory2 = "Bakverk";
		final String pManufacturer2 = "SteffeKeff";
		final String pDescription2 = "En pannkaka gjord av steffe!";
		final String pImg2 = "delicious2.png";
		final double pPrice2 = 5;
		final int pQuantity2 = 200;
		
		
		productParams1 = new ProductParameters(pName,
									   pCategory,
									   pManufacturer,
									   pDescription,
									   pImg,
									   pPrice,
								       pQuantity);
	
		productParams2 = new ProductParameters(pName2,
							    	   pCategory2,
									   pManufacturer2,
									   pDescription2,
									   pImg2,
									   pPrice2,
									   pQuantity2);
		customer1 = new Customer("tom", "password", "email@email.com", "Tomcat", "Blackmore", "C3LStreet", "123456");
		customer2 = new Customer("alex", "password", "alex@email.com", "Alexander", "Sol", "Banangatan 1", "543211");
	}
	
	//  Skapa en order för en användare
	@Test
	public void canCreateCustomerOrder()
	{// TODO
	}

	//  Hämta en användares alla order
	@Test
	public void canGetCustomerOrders()
	{// TODO
	}

	//  Hämta en viss order för en användare
	@Test
	public void canGetCustomerOrder()
	{ // TODO
	}

	//  Uppdatera en order för en användare
	@Test
	public void canUpdateCustomerOrder()
	{// TODO
	}

	//  Ta bort en order för en användare
	@Test
	public void canRemoveCustomerOrder()
	{ // TODO
	}
}
