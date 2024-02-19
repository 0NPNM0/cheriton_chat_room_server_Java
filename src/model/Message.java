package model;

import java.io.Serializable;
import java.util.Date;

public class Message implements Serializable, MessageType{
	String messageType;
	
	String sender;
	String receiver;
	String chatContent;
	
	String onLineFriend;
	
	Date sendTime;
	

	public Date getSendTime() {
		return sendTime;
	}

	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}
	
	
	public String getOnLineFriend() {
		return onLineFriend;
	}

	public void setOnLineFriend(String onLineFriend) {
		this.onLineFriend = onLineFriend;
	}


	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	public String getChatContent() {
		return chatContent;
	}

	public void setChatContent(String chatContent) {
		this.chatContent = chatContent;
	}

	public String getMessageType() {
		return messageType;
	}

	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}
	
}
