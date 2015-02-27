package se.coredev.jaxrs.service;

import java.util.HashMap;
import java.util.Map;

public final class MessageStorage
{
	private static final Map<Long, String> messages = new HashMap<>();
	
	static
	{
		messages.put(1001L, "Hello");
		messages.put(1002L, " World");
		messages.put(1003L, "!");		
	}
	
	public String getMessage(Long messageId)
	{
		if(messages.containsKey(messageId))
		{
			return messages.get(messageId);
		}
		throw new MessageStorageException("Could not find message with id:"+ messageId);
	}
}
