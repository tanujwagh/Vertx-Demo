package com.mmsoft.vertx.user.objects;

import java.util.ArrayList;
import java.util.List;

public class User {
	
	private int userId;
	private String userName;
	private String userEmail;
	private List<UserMessage> messages;
	
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserEmail() {
		return userEmail;
	}
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	public List<UserMessage> getMessages() {
		if(messages == null){
			return new ArrayList<UserMessage>();
		}
		return messages;
	}
	public void setMessages(UserMessage message) {
		if(messages != null){
			messages.add(message);
		}else{
			messages = new ArrayList<UserMessage>();
			messages.add(message);
		}
	}

}
