package se.groupone.ecommerce.webservice.exception;

public class MapperException extends Exception
{
	private static final long serialVersionUID = 13123L;

	public MapperException(String message, Throwable cause)
	{
		super(message, cause);
	}

	public MapperException(String message)
	{
		super(message);
	}
}