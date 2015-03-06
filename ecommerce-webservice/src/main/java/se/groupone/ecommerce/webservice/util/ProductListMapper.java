package se.groupone.ecommerce.webservice.util;

import se.groupone.ecommerce.model.Product;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonWriter;

@Provider
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public final class ProductListMapper implements MessageBodyWriter<ArrayList<Product>>
{
	private Gson gson;
	private Type productListType = new TypeToken<ArrayList<Product>>()
	{
	}.getType();

	public ProductListMapper()
	{
		gson = new GsonBuilder().registerTypeAdapter(productListType, new ProductAdapter()).create();
	}

	// MessageBodyWriter
	@Override
	public boolean isWriteable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType)
	{
		return genericType.equals(productListType);
	}

	@Override
	public long getSize(ArrayList<Product> t, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType)
	{
		return -1;
	}

	@Override
	public void writeTo(ArrayList<Product> productList, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType,
			MultivaluedMap<String, Object> httpHeaders,
			OutputStream entityStream) throws IOException, WebApplicationException
	{
		try (final JsonWriter writer = new JsonWriter(new OutputStreamWriter(entityStream)))
		{
			gson.toJson(productList, productListType, writer);
		}
	}

	private static final class ProductAdapter implements JsonSerializer<ArrayList<Product>>
	{
		@Override
		public JsonElement serialize(ArrayList<Product> productList, Type typeOfSrc, JsonSerializationContext context)
		{
			final JsonObject productListJson = new JsonObject();

			for (Product product : productList)
			{
				final JsonObject productJson = new JsonObject();

				productJson.add("id", new JsonPrimitive(product.getId()));
				productJson.add("title", new JsonPrimitive(product.getTitle()));
				productJson.add("category", new JsonPrimitive(product.getCategory()));
				productJson.add("manufacturer", new JsonPrimitive(product.getManufacturer()));
				productJson.add("description", new JsonPrimitive(product.getDescription()));
				productJson.add("img", new JsonPrimitive(product.getImg()));
				productJson.add("price", new JsonPrimitive(product.getPrice()));
				productJson.add("quantity", new JsonPrimitive(product.getQuantity()));
				productListJson.add("" + product.getId(), productJson);
			}
			return productListJson;
		}
	}
}
