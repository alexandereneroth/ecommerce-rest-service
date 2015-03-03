package se.groupone.ecommerce.webservice.util;

import se.groupone.ecommerce.model.Customer;

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
public final class CustomerMapper implements MessageBodyReader<Customer>, MessageBodyWriter<Customer>
{
	private Gson gson;

	public CustomerMapper()
	{
		gson = new GsonBuilder().registerTypeAdapter(Customer.class, new CustomerAdapter()).create();
	}

	// MessageBodyWriter
	@Override
	public boolean isWriteable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType)
	{
		return type.isAssignableFrom(Customer.class);
	}

	@Override
	public long getSize(Customer t, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType)
	{
		return -1;
	}

	@Override
	public void writeTo(Customer customer, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, Object> httpHeaders,
			OutputStream entityStream) throws IOException, WebApplicationException
	{
		try(final JsonWriter writer = new JsonWriter(new OutputStreamWriter(entityStream)))
		{
			gson.toJson(customer, Customer.class, writer);
		}
	}
	
	// MessageBodyReader
	@Override
	public boolean isReadable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType)
	{
		return type.isAssignableFrom(Customer.class);
	}

	@Override
	public Customer readFrom(Class<Customer> type, Type genericType, Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, String> httpHeaders,
			InputStream entityStream) throws IOException, WebApplicationException
	{
		final Customer customer = gson.fromJson(new InputStreamReader(entityStream), Customer.class);
		return customer;
	}
	
	private static final class CustomerAdapter implements JsonDeserializer<Customer>, JsonSerializer<Customer>
	{

		@Override
		public JsonElement serialize(Customer customer, Type typeOfSrc, JsonSerializationContext context)
		{   
			final JsonObject customerJson = new JsonObject();
			customerJson.add("username", new JsonPrimitive(customer.getUsername()));
			customerJson.add("password", new JsonPrimitive(customer.getPassword()));
			customerJson.add("email", new JsonPrimitive(customer.getEmail()));
			customerJson.add("firstName", new JsonPrimitive(customer.getFirstName()));
			customerJson.add("lastName", new JsonPrimitive(customer.getLastName()));
			customerJson.add("address", new JsonPrimitive(customer.getAddress()));
			customerJson.add("mobileNumber", new JsonPrimitive(customer.getMobileNumber()));
			
			return customerJson;
		}

		@Override
		public Customer deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
		{		
			final JsonObject customerJson = json.getAsJsonObject();
			final String userName = customerJson.get("username").getAsString();
			final String password = customerJson.get("password").getAsString();
			final String email = customerJson.get("email").getAsString();
			final String firstName = customerJson.get("firstName").getAsString();
			final String lastName = customerJson.get("lastName").getAsString();
			final String address = customerJson.get("address").getAsString();
			final String mobileNumber = customerJson.get("mobileNumber").getAsString();
			
			return new Customer(userName, password, email, firstName, lastName, address, mobileNumber );
		}
		
	}
}
