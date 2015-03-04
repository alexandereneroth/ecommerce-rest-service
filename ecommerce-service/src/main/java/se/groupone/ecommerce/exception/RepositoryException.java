package se.groupone.ecommerce.exception;

public class RepositoryException extends Exception
{
	private static final long serialVersionUID = -8719484731847098752L;

	public RepositoryException(String message, Throwable cause)
	{
		super(message, cause);
	}

	public RepositoryException(String message)
	{
		super(message);
	}
}
