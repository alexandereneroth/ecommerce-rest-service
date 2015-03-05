package se.groupone.ecommerce.webservice.util;

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
public final class ProductParamMapper implements MessageBodyReader<ProductParameters>, MessageBodyWriter<ProductParameters>
{
	private Gson gson;

	public ProductParamMapper()
	{
		gson = new GsonBuilder().registerTypeAdapter(ProductParameters.class, new ProductParametersAdapter()).create();
	}

	// MessageBodyWriter
	@Override
	public boolean isWriteable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType)
	{
		return type.isAssignableFrom(ProductParameters.class);
	}

	@Override
	public long getSize(ProductParameters t, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType)
	{
		return -1;
	}

	@Override
	public void writeTo(ProductParameters productParams, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, Object> httpHeaders,
			OutputStream entityStream) throws IOException, WebApplicationException
	{
		try(final JsonWriter writer = new JsonWriter(new OutputStreamWriter(entityStream)))
		{
			gson.toJson(productParams, ProductParameters.class, writer);
		}
	}
	
	// MessageBodyReader
	@Override
	public boolean isReadable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType)
	{
		return type.isAssignableFrom(ProductParameters.class);
	}

	@Override
	public ProductParameters readFrom(Class<ProductParameters> type, Type genericType, Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, String> httpHeaders,
			InputStream entityStream) throws IOException, WebApplicationException
	{
		final ProductParameters productParams = gson.fromJson(new InputStreamReader(entityStream), ProductParameters.class);
		return productParams;
	}
	
	private static final class ProductParametersAdapter implements JsonDeserializer<ProductParameters>, JsonSerializer<ProductParameters>
	{

		// This is never used, kept for reference, will be removed in final build.
		@Override
		public JsonElement serialize(ProductParameters productParams, Type typeOfSrc, JsonSerializationContext context)
		{   
			final JsonObject productParamsJson = new JsonObject();
			productParamsJson.add("title", new JsonPrimitive(productParams.getTitle()));
			productParamsJson.add("category", new JsonPrimitive(productParams.getCategory()));
			productParamsJson.add("manufacturer", new JsonPrimitive(productParams.getManufacturer()));
			productParamsJson.add("description", new JsonPrimitive(productParams.getDescription()));
			productParamsJson.add("img", new JsonPrimitive(productParams.getImg()));
			productParamsJson.add("price", new JsonPrimitive(productParams.getPrice()));
			productParamsJson.add("quantity", new JsonPrimitive(productParams.getQuantity()));
			
			return productParamsJson;
		}

		@Override
		public ProductParameters deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
		{		
			final JsonObject productParamsJson = json.getAsJsonObject();
			final String title = productParamsJson.get("title").getAsString();
			final String category = productParamsJson.get("category").getAsString();
			final String manufacturer = productParamsJson.get("manufacturer").getAsString();
			final String description = productParamsJson.get("description").getAsString();
			final String img = productParamsJson.get("img").getAsString();
			final Double price = productParamsJson.get("price").getAsDouble();
			final Integer quantity = productParamsJson.get("quantity").getAsInt();
			
			return new ProductParameters(title, category, manufacturer, description, img, price, quantity);
		}
		
	}
}
