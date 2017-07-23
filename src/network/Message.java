package network;

import java.io.Serializable;

public class Message implements Serializable {

	private String sender;
	private String text;

	public Message(String sender, String message) {
		this.sender = sender;
		this.text = message;
	}

	public String getSender() {
		return sender;
	}

	public String getText() {
		return text;
	}

}
