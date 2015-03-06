package se.groupone.ecommerce.webservice;

import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import se.groupone.ecommerce.model.Product;
import se.groupone.ecommerce.service.ShopService;

import com.mysql.fabric.Response;

@Path("products")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProductService
{
	@Context
	ServletContext context;
	
	// Hämta alla produkter
	@GET
	public Response getProducts()
	{
		ShopService shopService = (ShopService) context.getAttribute("ss");
		
		shopService.getProducts();
		return null;
	}

	// Hämta en produkt med ett visst id 

	// Skapa en ny produkt – detta ska returnera en länk till den skapade 
	//produkten i Location-headern

	// Uppdatera en produkt

	// Ta bort en produkt (eller sätta den som inaktiv)
}
