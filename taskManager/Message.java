package taskManager;

public class Message {
	private String receiverUserName;
	private String message;
	private String senderUserName;
	private int messageID;

	/**
	 * @param receiver
	 * @param message
	 * @param sender
	 */
	public Message(String receiver, String message, String sender) {
		this.receiverUserName = receiver;
		this.message = message;
		this.senderUserName = sender;
	}

	/**
	 * 
	 */
	public Message() {
	}

	/**
	 * @param receiver
	 */
	public void setReceiver(String receiver) {
		this.receiverUserName = receiver;
	}

	/**
	 * @return
	 */
	public String getReceiver() {
		return receiverUserName;
	}

	/**
	 * @param message
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @return
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param sender
	 */
	public void setSender(String sender) {
		this.senderUserName = sender;
	}

	/**
	 * @return
	 */
	public String getSender() {
		return senderUserName;
	}

	/**
	 * @param id
	 */
	public void setMessageID(int id) {
		this.messageID = id;
	}

	/**
	 * @return
	 */
	public int getMessageID() {
		return messageID;
	}
}