package se.groupone.ecommerce.test.webservice;

import se.groupone.ecommerce.model.Product;
import se.groupone.ecommerce.model.ProductParameters;

import java.net.URI;
import java.util.ArrayList;

import se.groupone.ecommerce.service.ShopService;
import se.groupone.ecommerce.webservice.util.ProductListMapper;
import se.groupone.ecommerce.webservice.util.ProductMapper;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import static org.junit.Assert.*;

import org.junit.Test;

public class ProductServiceTest
{
	private static final String HOST_NAME = "localhost";
	private static final int HOST_IP = 8080;
	private static final String PROJECT_NAME = "ecommerce-webservice";
	private static final String RESOURCE = "products";
	private static final String URL_BASE = "http://" + HOST_NAME + ":" + HOST_IP + "/" 
			+ PROJECT_NAME;
	private static final String URL = "http://" + HOST_NAME + ":" + HOST_IP + "/" 
			+ PROJECT_NAME + "/" + RESOURCE;
	private static final Client client = ClientBuilder.newBuilder().register(ProductMapper.class).build();
	

	//  Hämta en produkt med ett visst id
	@Test
	public void canGetProductAndProductByIdWithMediaTypeJson()
	{
		WebTarget target = client.target(URL);
		WebTarget targetProductId1 = target.path("1");
		WebTarget targetProductId2 = target.path("2");
		
		Invocation invocationProd1 = targetProductId1.request(MediaType.APPLICATION_JSON).buildGet();
		Invocation invocationProd2 = targetProductId2.request(MediaType.APPLICATION_JSON).buildGet();
		
		Response responseProd1 = invocationProd1.invoke();
		Response responseProd2 = invocationProd2.invoke();
		
		assertEquals(MediaType.APPLICATION_JSON, responseProd1.getMediaType().toString());
		assertEquals(MediaType.APPLICATION_JSON, responseProd2.getMediaType().toString());
		
//		System.out.println(responseProd1.readEntity(String.class).getClass());
		System.out.println();
		
		
		System.out.println("Requst status code: " + responseProd1.getStatus());
		System.out.println("Product with ID 1:");
		System.out.println(responseProd1.readEntity(Product.class));
		
		System.out.println();
		
		System.out.println("Requst status code: " + responseProd2.getStatus());
		System.out.println("Product with ID 2:");
		System.out.println(responseProd2.readEntity(String.class));
		
		
	}


	//  Skapa en ny produkt – detta ska returnera en länk till den skapade
	// produkten i Location-headern
	@Test
	public void canCreateProduct()
	{
		fail("Not yet implemented");
	}

	//  Hämta alla produkter
	@Test
	public void canGetAllProducts()
	{
		fail("Not yet implemented");
	}

	//  Uppdatera en produkt
	@Test
	public void canUpdateAProduct()
	{
		fail("Not yet implemented");
	}

	//  Ta bort en produkt (eller sätta den som inaktiv)
	@Test
	public void canDeleteAProduct()
	{
		fail("Not yet implemented");
	}
	
}
