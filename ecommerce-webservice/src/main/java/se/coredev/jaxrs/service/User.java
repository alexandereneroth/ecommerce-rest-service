package se.coredev.jaxrs.service;

//import javax.xml.bind.annotation.XmlElement;
//import javax.xml.bind.annotation.XmlRootElement;

//@XmlRootElement
public final class User
{
//	@XmlElement
	private Long userId;
//	@XmlElement
	private String username;
//	@XmlElement
	private String password;

//	@SuppressWarnings("unused")
//	private User(){}
	
	public User(Long userId, String username, String password)
	{
		this.userId = userId;
		this.username = username;
		this.password = password;
	}

	public Long getUserId()
	{
		return userId;
	}

	public String getUsername()
	{
		return username;
	}

	public String getPassword()
	{
		return password;
	}
}
