package se.groupone.ecommerce.model;

public class ModelException extends Exception
{
	private static final long serialVersionUID = -5632885544317796907L;

	public ModelException(String message, Throwable cause)
	{
		super(message, cause);
	}

	public ModelException(String message)
	{
		super(message);
	}

}
