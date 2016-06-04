package com.mmsoft.vertx.user.objects;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class UserDBStore {

	ConcurrentHashMap<Integer, User> userStore = new  ConcurrentHashMap<Integer, User>();
	
	public User getUserById(int id){
		return userStore.get(id);
	}
	
	public List<User> getAllUsers(){
		return new ArrayList<>(userStore.values());
	}
	
	public User saveUser(User user){
		userStore.put(user.getUserId(), user);
		return user;
	}
	
	public User removeUser(int id){
		return userStore.remove(id);
	}
	
	public UserMessage saveUserMessage(int id, UserMessage message){
		userStore.get(id).setMessages(message);
		return message;
	}
	
	public List<UserMessage> getUserMessages(int id){
		return userStore.get(id).getMessages();
	}
	
}
