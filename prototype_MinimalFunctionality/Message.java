package prototype_MinimalFunctionality;

import java.sql.Date;
import java.sql.Timestamp;

public class Message {
	private String receiverUserName;
	private String message;
	private String senderUserName;
	private int messageID;
	
	public Message(String receiver, String message, String sender) {
		this.receiverUserName = receiver;
		this.message = message;
		this.senderUserName = sender; 
	}
	
	public Message()
	{
		
	}
	
	public void setReceiver(String receiver)
	{
		this.receiverUserName = receiver;
	}
	public String getReceiver()
	{
		return receiverUserName;
	}
	
	public void setMessage(String message)
	{
		this.message = message;
	}
	public String getMessage()
	{
		return message;
	}
	
	public void setSender(String sender)
	{
		this.senderUserName = sender;
	}
	public String getSender()
	{
		return senderUserName;
	}
	
	public void setMessageID(int id)
	{
		this.messageID = id;
	}
	public int getMessageID()
	{
		return messageID;
	}
}