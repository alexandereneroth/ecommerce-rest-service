package se.groupone.ecommerce.webservice.util;

import se.groupone.ecommerce.model.Product;
import se.groupone.ecommerce.model.ProductParameters;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.stream.JsonWriter;

@Provider
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public final class ProductMapper implements MessageBodyWriter<Product>, MessageBodyReader<Product>
{
	private Gson gson;

	public ProductMapper()
	{
		gson = new GsonBuilder().registerTypeAdapter(Product.class, new ProductAdapter()).create();
	}

	// MessageBodyWriter
	@Override
	public boolean isWriteable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType)
	{
		return type.isAssignableFrom(Product.class);
	}

	@Override
	public long getSize(Product t, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType)
	{
		return -1;
	}

	@Override
	public void writeTo(Product product, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, Object> httpHeaders,
			OutputStream entityStream) throws IOException, WebApplicationException
	{
		try (final JsonWriter writer = new JsonWriter(new OutputStreamWriter(entityStream)))
		{
			gson.toJson(product, Product.class, writer);
		}
	}

	// MessageBodyReader
	@Override
	public boolean isReadable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType)
	{
		return type.isAssignableFrom(Product.class);
	}

	@Override
	public Product readFrom(Class<Product> type, Type genericType, Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, String> httpHeaders,
			InputStream entityStream) throws IOException, WebApplicationException
	{
		final Product product = gson.fromJson(new InputStreamReader(entityStream), Product.class);
		return product;
	}

	private static final class ProductAdapter implements JsonSerializer<Product>, JsonDeserializer<Product>
	{
		@Override
		public JsonElement serialize(Product product, Type typeOfSrc, JsonSerializationContext context)
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

			return productJson;
		}

		@Override
		public Product deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
		{
			final JsonObject productJson = json.getAsJsonObject();
			final int id = productJson.get("id").getAsInt();
			ProductParameters params = new ProductParameters(
					productJson.get("title").getAsString(),
					productJson.get("category").getAsString(),
					productJson.get("manufacturer").getAsString(),
					productJson.get("description").getAsString(),
					productJson.get("img").getAsString(),
					productJson.get("price").getAsDouble(),
					productJson.get("quantity").getAsInt());
			return new Product(id, params);
		}
	}

}
