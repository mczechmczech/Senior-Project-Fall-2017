package taskManager;

/**
 * The message class creates a message for one user to send to 
 * another in the taskManager application.
 * 
 * @version 12.20.2017
 */
public class Message {
	private String receiverUserName;
	private String message;
	private String senderUserName;
	private int messageID;

	/**
	 * Constructor for creating a message
	 * 
	 * @param receiver user name of the user who will receive the message
	 * @param message the message that will be sent
	 * @param sender the user name of the user who will send the message
	 */
	public Message(String receiver, String message, String sender) {
		this.receiverUserName = receiver;
		this.message = message;
		this.senderUserName = sender;
	}

	/**
	 * empty message constructor
	 */
	public Message() {
	}

	/**
	 * Set the name of the receiving user
	 * 
	 * @param receiver the name of the receiving user
	 */
	public void setReceiver(String receiver) {
		this.receiverUserName = receiver;
	}

	/**
	 * Get the name of the receiving user
	 * 
	 * @return the name of the receiving user
	 */
	public String getReceiver() {
		return receiverUserName;
	}

	/**
	 * Set the message string
	 * 
	 * @param message the message string that will be sent
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * Get the message string
	 * 
	 * @return the message string
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * Set the name of the sending user
	 * 
	 * @param sender the name of the sending user
	 */
	public void setSender(String sender) {
		this.senderUserName = sender;
	}

	/**
	 * Get the name of the sending user
	 * 
	 * @return the name of the sending user
	 */
	public String getSender() {
		return senderUserName;
	}

	/**
	 * Set the ID of the message
	 * 
	 * @param id the ID of the message
	 */
	public void setMessageID(int id) {
		this.messageID = id;
	}

	/**
	 * Get the ID of the message
	 * 
	 * @return the ID of the message
	 */
	public int getMessageID() {
		return messageID;
	}
}