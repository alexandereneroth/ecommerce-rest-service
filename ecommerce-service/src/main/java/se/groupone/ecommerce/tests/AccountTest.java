package se.groupone.ecommerce.tests;
import static org.junit.Assert.*;

import org.junit.Test;

import se.groupone.ecommerce.model.Account;


public class AccountTest 
{

	Account erik = new Account("Erik", "Elf", "erik.welander@hotmail.com");
	
	@Test
	public void get()
	{
		assertEquals(erik.getUsername(), "Erik");
		assertEquals(erik.getPassword(), "Elf");
		assertEquals(erik.getEmail(), "erik.welander@hotmail.com");
		assertEquals(erik.toString(), "Erik");
		assertEquals(erik.getLoginStatus(), false);
	}
	
	@Test
	public void set()
	{
		erik.setPassword("Lucy");
		erik.setLoginStatus(true);
		assertEquals(erik.getPassword(), "Lucy");
		assertEquals(erik.getLoginStatus(), true);
	}
	
}
