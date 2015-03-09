package se.groupone.ecommerce.test.webservice;

import static org.junit.Assert.*;

import se.groupone.ecommerce.model.Customer;
import se.groupone.ecommerce.model.Product;
import se.groupone.ecommerce.model.ProductParameters;

import org.junit.Before;
import org.junit.Test;

import com.sun.org.apache.xml.internal.security.Init;

public class CustomerServiceTest
{
	private ProductParameters productParams1;
	private ProductParameters productParams2;
	private Customer customer1;
	private Customer customer2;
	

	@Before
	public void Init()
	{
		// add some dummy data for DB
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

	//  Skapa en ny användare
	@Test
	public void canCreateCustomer()
	{

	}

	//  Hämta en användare med ett visst id
	@Test
	public void canGetCustomerOfId()
	{ // TODO
	}

	//  Skapa en ny användare – detta ska returnera en länk till den skapade
	// användaren i Location-headern
	@Test
	public void assertLocationHeaderOfCreatedCustomerIsCorrect()
	{ // TODO
	}

	//  Uppdatera en användare
	@Test
	public void canUpdateUser()
	{ // TODO
	}

	//  Ta bort en användare (eller sätta den som inaktiv)
	@Test
	public void canRemoveCustomer()
	{ // TODO
	}
}
