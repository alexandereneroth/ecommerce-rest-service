package se.groupone.ecommerce.webservice.util;

import se.groupone.ecommerce.model.Order;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.stream.JsonWriter;

@Provider
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public final class OrderMapper implements MessageBodyWriter<Order>
{
	private Gson gson;

	public OrderMapper()
	{
		gson = new GsonBuilder().registerTypeAdapter(Order.class, new OrderAdapter()).create();
	}

	// MessageBodyWriter
	@Override
	public boolean isWriteable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType)
	{
		return type.isAssignableFrom(Order.class);
	}

	@Override
	public long getSize(Order t, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType)
	{
		return -1;
	}

	@Override
	public void writeTo(Order Order, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, Object> httpHeaders,
			OutputStream entityStream) throws IOException, WebApplicationException
	{
		try (final JsonWriter writer = new JsonWriter(new OutputStreamWriter(entityStream)))
		{
			gson.toJson(Order, Order.class, writer);
		}
	}

	private static final class OrderAdapter implements JsonSerializer<Order>
	{
		@Override
		public JsonElement serialize(Order order, Type typeOfSrc, JsonSerializationContext context)
		{
			final JsonObject orderJson = new JsonObject();
			final JsonArray productIdsJsonArray = new JsonArray();

			orderJson.add("username", new JsonPrimitive(order.getUsername()));
			for (int productId : order.getProductIds())
			{
				productIdsJsonArray.add(new JsonPrimitive(productId));
			}
			orderJson.add("shoppingCart", productIdsJsonArray);

			return orderJson;
		}
	}
}
