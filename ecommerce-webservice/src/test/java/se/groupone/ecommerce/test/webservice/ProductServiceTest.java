package se.groupone.ecommerce.test.webservice;

import se.groupone.ecommerce.model.Product;
import se.groupone.ecommerce.model.ProductParameters;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import se.groupone.ecommerce.webservice.util.ProductMapper;
import se.groupone.ecommerce.webservice.util.ProductParamMapper;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.reflect.TypeToken;

public class ProductServiceTest
{
	private static final String HOST_NAME = "localhost";
	private static final int HOST_IP = 8080;
	private static final String PROJECT_NAME = "ecommerce-webservice";
	private static final String RESOURCE = "products";
	private static final String URL_BASE = "http://" + HOST_NAME + ":" + HOST_IP + "/"
			+ PROJECT_NAME;
	private static final String RESOURCE_URL = "http://" + HOST_NAME + ":" + HOST_IP + "/"
			+ PROJECT_NAME + "/" + RESOURCE;
	private static final Client client = ClientBuilder.newBuilder().register(ProductMapper.class).register(ProductParamMapper.class).build();

	private static final WebTarget RESOURCE_TARGET;
	
	private static final int FIRST_GENERATED_PRODUCT_ID = 1;

	// // PRODUCTS ////
	// ProductsParameters
	private static final ProductParameters PRODUCT_PARAMETERS_TOMATO = new ProductParameters("Tomato", "Vegetables", "Spain", "A beautiful tomato",
			"http://google.com/tomato.jpg", 45, 5);
	private static final ProductParameters PRODUCT_PARAMETERS_LETTUCE = new ProductParameters("Lettuce", "Vegetables", "France", "A mound of lettuce",
			"http://altavista.com/lettuce.jpg", 88, 2);

	// ProductIDs
	private static final int PRODUCT_ID_TOMATO = FIRST_GENERATED_PRODUCT_ID;
	private static final int PRODUCT_ID_LETTUCE = FIRST_GENERATED_PRODUCT_ID + 1;
	// Products
	private static final Product PRODUCT_LETTUCE = new Product(PRODUCT_ID_LETTUCE, PRODUCT_PARAMETERS_LETTUCE);
	
	
	static
	{
		RESOURCE_TARGET = client.target(RESOURCE_URL);
	}
	
	@Before
	public void Init()
	{
		WebTarget admin = client.target(URL_BASE + "/admin");
		admin.request().buildPost(Entity.entity("reset-repo", MediaType.TEXT_HTML)).invoke();
	}
	
	@AfterClass
	public static void tearDown()
	{
		WebTarget admin = client.target(URL_BASE + "/admin");
		admin.request().buildPost(Entity.entity("reset-repo", MediaType.TEXT_HTML)).invoke();
	}

	//  Hämta en produkt med ett visst id
	//  Skapa en ny produkt – detta ska returnera en länk till den skapade
	// produkten i Location-headern
	@Test
	public void canCreateAndGetProduct()
	{
		// POST - Create products
		Response createProductResponse = RESOURCE_TARGET.request(MediaType.APPLICATION_JSON)
				.buildPost(Entity.entity(PRODUCT_PARAMETERS_TOMATO, MediaType.APPLICATION_JSON))
				.invoke();
		assertEquals(201, createProductResponse.getStatus());

		final Product PRODUCT_TOMATO = client.target(createProductResponse.getLocation())
				.request(MediaType.APPLICATION_JSON)
				.get(Product.class);
		
		//GET
		WebTarget createdTarget = client.target(createProductResponse.getLocation());
		Product createdProduct = createdTarget.request(MediaType.APPLICATION_JSON).get(Product.class);
		
		assertThat(createdProduct, is(PRODUCT_TOMATO));
		
	}

	//  Hämta alla produkter
	@Test
	public void canGetAllProducts() throws IOException
	{
		//POST
		RESOURCE_TARGET.request(MediaType.APPLICATION_JSON)
						.buildPost(Entity.entity(PRODUCT_PARAMETERS_TOMATO,MediaType.APPLICATION_JSON))
						.invoke();
		//POST
		RESOURCE_TARGET.request(MediaType.APPLICATION_JSON)
						.buildPost(Entity.entity(PRODUCT_PARAMETERS_LETTUCE,MediaType.APPLICATION_JSON))
						.invoke();

		String json = RESOURCE_TARGET.request(MediaType.APPLICATION_JSON).get(String.class);
//		System.out.println(json);
	}

	//  Uppdatera en produkt
	@Test
	public void canUpdateAProduct()
	{
		// POST - Create products
		Response createProductResponse = RESOURCE_TARGET.request(MediaType.APPLICATION_JSON)
				.buildPost(Entity.entity(PRODUCT_PARAMETERS_TOMATO, MediaType.APPLICATION_JSON))
				.invoke();
		assertEquals(201, createProductResponse.getStatus());

		final Product PRODUCT_TOMATO = client.target(createProductResponse.getLocation())
				.request(MediaType.APPLICATION_JSON)
				.get(Product.class);

		//GET
		WebTarget createdTarget = client.target(createProductResponse.getLocation());
		Product createdProduct = createdTarget
				.request(MediaType.APPLICATION_JSON).get(Product.class);
		
		assertThat(createdProduct, is(PRODUCT_TOMATO));
		

		//PUT
		createdTarget.request(MediaType.APPLICATION_JSON)
						.buildPut(Entity.entity(PRODUCT_PARAMETERS_LETTUCE,MediaType.APPLICATION_JSON))
						.invoke();

		//GET
		Product updatedProduct = createdTarget
				.request(MediaType.APPLICATION_JSON).get(Product.class);
		
		assertThat(updatedProduct.getTitle(), is(PRODUCT_LETTUCE.getTitle()));
		assertThat(updatedProduct.getQuantity(), is(PRODUCT_LETTUCE.getQuantity()));
	}

	//  Ta bort en produkt (eller sätta den som inaktiv)
	@Test
	public void canDeleteAProduct() throws IOException
	{
		// POST - Create products
		Response createProductResponse = RESOURCE_TARGET.request(MediaType.APPLICATION_JSON)
				.buildPost(Entity.entity(PRODUCT_PARAMETERS_TOMATO, MediaType.APPLICATION_JSON))
				.invoke();
		assertEquals(201, createProductResponse.getStatus());

		final Product PRODUCT_TOMATO = client.target(createProductResponse.getLocation())
				.request(MediaType.APPLICATION_JSON)
				.get(Product.class);

		//GET
		Product createdProduct = client.target(createProductResponse.getLocation())
				.request(MediaType.APPLICATION_JSON).get(Product.class);
		
		assertThat(createdProduct, is(PRODUCT_TOMATO));
		
		System.out.println(createProductResponse.getLocation());
		
		//DELETE
		Response deleteProductResponse = client.target(createProductResponse.getLocation())
						.request()
						.delete();
		assertThat(deleteProductResponse.getStatus(), is(Status.NO_CONTENT.getStatusCode()));

		//GET
		Response getDeletedProductResponse = client.target(createProductResponse.getLocation())
				.request(MediaType.APPLICATION_JSON).get();
		
		assertThat(getDeletedProductResponse.getStatus(), is(Status.BAD_REQUEST.getStatusCode()));
	}

}
