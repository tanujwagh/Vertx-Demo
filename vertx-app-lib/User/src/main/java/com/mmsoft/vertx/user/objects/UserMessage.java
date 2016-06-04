package com.mmsoft.vertx.user.objects;

public class UserMessage {
	private String messageTitle;
	private String messageContent;
	
	public UserMessage(String messageTitle, String messageContent) {
		this.messageTitle = messageTitle;
		this.messageContent = messageContent;
	}
	public String getMessageTitle() {
		return messageTitle;
	}
	public void setMessageTitle(String messageTitle) {
		this.messageTitle = messageTitle;
	}
	public String getMessageContent() {
		return messageContent;
	}
	public void setMessageContent(String messageContent) {
		this.messageContent = messageContent;
	}
	
	
}
