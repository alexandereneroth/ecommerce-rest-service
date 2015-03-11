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
import java.util.ArrayList;

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
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonWriter;

@Provider
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public final class ProductListMapper implements MessageBodyWriter<ArrayList<Product>>,
		MessageBodyReader<ArrayList<Product>>
{
	private Gson gson;
	private Type productListType = new TypeToken<ArrayList<Product>>(){}.getType();

	public ProductListMapper()
	{
		gson = new GsonBuilder().registerTypeAdapter(productListType, new ProductListAdapter()).create();
	}

	// MessageBodyWriter
	@Override
	public boolean isWriteable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType)
	{
		return genericType.equals(productListType);
	}

	@Override
	public long getSize(ArrayList<Product> t, Class<?> type, Type genericType, Annotation[] annotations,
			MediaType mediaType)
	{
		return -1;
	}

	@Override
	public void writeTo(ArrayList<Product> productList, Class<?> type, Type genericType, Annotation[] annotations,
			MediaType mediaType,
			MultivaluedMap<String, Object> httpHeaders,
			OutputStream entityStream) throws IOException, WebApplicationException
	{
		try (final JsonWriter writer = new JsonWriter(new OutputStreamWriter(entityStream)))
		{
			gson.toJson(productList, productListType, writer);
		}
	}

	@Override
	public boolean isReadable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType)
	{
		return genericType.equals(productListType);
	}

	@Override
	public ArrayList<Product> readFrom(Class<ArrayList<Product>> type, Type genericType, Annotation[] annotations,
			MediaType mediaType,
			MultivaluedMap<String, String> httpHeaders, InputStream entityStream) throws IOException,
			WebApplicationException
	{
		final ArrayList<Product> productArrayList = gson.fromJson(new InputStreamReader(entityStream), productListType);
		return productArrayList;
	}

	private static final class ProductListAdapter implements JsonSerializer<ArrayList<Product>>,
			JsonDeserializer<ArrayList<Product>>
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

		@Override
		public ArrayList<Product> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
				throws JsonParseException
		{
			final JsonObject productListJson = json.getAsJsonObject();
			final ArrayList<Product> products = new ArrayList<>();
			
			// TODO This looks weird, refactor?
			final int NUMBER_OF_PRODUCT_PARAMS = 8;

			for (int i = 1; i < NUMBER_OF_PRODUCT_PARAMS; ++i)
			{
				JsonObject productJson = (JsonObject) productListJson.get(String.valueOf(i));

				final int id = productJson.get("id").getAsInt();
				ProductParameters params = new ProductParameters(
						productJson.get("title").getAsString(),
						productJson.get("category").getAsString(),
						productJson.get("manufacturer").getAsString(),
						productJson.get("description").getAsString(),
						productJson.get("img").getAsString(),
						productJson.get("price").getAsDouble(),
						productJson.get("quantity").getAsInt());

				products.add(new Product(id, params));
			}
			return products;
		}
	}
}