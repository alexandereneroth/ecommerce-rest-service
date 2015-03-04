package se.groupone.ecommerce.exception;

public class ShopServiceException extends RuntimeException
{
	private static final long serialVersionUID = 1337133669580611151L;

	public ShopServiceException(String message, Throwable cause)
	{
		super(message, cause);
	}

	public ShopServiceException(String message)
	{
		super(message);
	}
}
