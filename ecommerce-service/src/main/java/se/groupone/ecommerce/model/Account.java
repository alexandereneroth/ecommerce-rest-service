package se.groupone.ecommerce.model;

public class Account 
{	
	private final String username;
	private String password, email;
	private boolean loginStatus = false;
	
	public Account(String username, String password, String email)
	{
		this.username = username;
		this.password = password;
		this.email = email;
	}
	
	public String getUsername()
	{
		return username;
	}

	public String getPassword() 
	{
		return password;
	}

	public void setPassword(String password) 
	{
		this.password = password;
	}

	public String getEmail() 
	{
		return email;
	}

	public void setEmail(String email) 
	{
		this.email = email;
	}

	public boolean isLoginStatus() 
	{
		return loginStatus;
	}

	public void setLoginStatus(boolean loginStatus) 
	{
		this.loginStatus = loginStatus;
	}
	
	public boolean getLoginStatus()
	{
		return loginStatus;
	}

	@Override
	public String toString() 
	{
		return username;
	}
	
}
