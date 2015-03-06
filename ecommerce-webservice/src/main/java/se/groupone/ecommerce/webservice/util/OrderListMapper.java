package se.groupone.ecommerce.webservice.util;

import se.groupone.ecommerce.model.Order;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.ArrayList;

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
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonWriter;

@Provider
@Produces(MediaType.APPLICATION_JSON)
public final class OrderListMapper implements MessageBodyWriter<ArrayList<Order>>
{
	private Gson gson;
	private Type orderListType = new TypeToken<ArrayList<Order>>()
	{
	}.getType();

	public OrderListMapper()
	{
		gson = new GsonBuilder().registerTypeAdapter(orderListType, new OrderListAdapter()).create();
	}

	// MessageBodyWriter
	@Override
	public boolean isWriteable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType)
	{
		return genericType.equals(orderListType);
	}

	@Override
	public long getSize(ArrayList<Order> t, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType)
	{
		return -1;
	}

	@Override
	public void writeTo(ArrayList<Order> integerList, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType,
			MultivaluedMap<String, Object> httpHeaders,
			OutputStream entityStream) throws IOException, WebApplicationException
	{
		try (final JsonWriter writer = new JsonWriter(new OutputStreamWriter(entityStream)))
		{
			gson.toJson(integerList, orderListType, writer);
		}
	}

	private static final class OrderListAdapter implements JsonSerializer<ArrayList<Order>>
	{
		@Override
		public JsonElement serialize(ArrayList<Order> orderList, Type typeOfSrc, JsonSerializationContext context)
		{
			final JsonObject orderListJson = new JsonObject();
			final JsonArray orderArray = new JsonArray();

			for (Order order : orderList)
			{
				orderArray.add(serializeOrder(order));
			}
			
			orderListJson.add("orderArray", orderArray);
			return orderListJson;
		}

		private JsonObject serializeOrder(Order order)
		{
			final JsonObject orderJson = new JsonObject();
			final JsonArray productIdsJsonArray = new JsonArray();
			
			orderJson.add("id", new JsonPrimitive(order.getId()));
			orderJson.add("username", new JsonPrimitive(order.getUsername()));
			for (int productId : order.getProductIds())
			{
				productIdsJsonArray.add(new JsonPrimitive(productId));
			}
			orderJson.add("productIds", productIdsJsonArray);
			return orderJson;
		}
	}
}
