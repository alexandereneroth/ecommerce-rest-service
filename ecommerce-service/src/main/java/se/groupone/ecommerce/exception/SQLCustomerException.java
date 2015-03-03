package se.groupone.ecommerce.exception;

public class SQLCustomerException extends RuntimeException
{
	private static final long serialVersionUID = 1L;
	
	public SQLCustomerException(final String message)
	{
		super("SQL Customer exception: "+message);
	}
	
}
