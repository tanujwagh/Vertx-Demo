package com.mmsoft.vertx.user.handlers;

import java.util.concurrent.atomic.AtomicInteger;

import com.mmsoft.vertx.user.objects.User;
import com.mmsoft.vertx.user.objects.UserDBStore;
import com.mmsoft.vertx.user.objects.UserMessage;

import io.vertx.core.Handler;
import io.vertx.core.eventbus.Message;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;

public class UserModelHandler implements Handler<Message<JsonObject>> {

	private UserDBStore userDBStore;
	
	static AtomicInteger userCounter = new AtomicInteger(1);
	
	public UserModelHandler() {
		this.userDBStore = new UserDBStore();
		mockTestData();
	}

	private void mockTestData() {
		for(int i = 0; i<5; i++){
			int id = userCounter.getAndIncrement();
			User user = new User();
			user.setUserId(id);
			user.setUserName("Test" + id);
			user.setUserEmail("Test"+id+"@email.com");
			user.setMessages(new UserMessage("Message " + id, "Message Content " + id));
			userDBStore.saveUser(user);
		}
		
	}

	@Override
	public void handle(Message<JsonObject> message) {
		int userId = message.body().getInteger("userId");
		String action = message.body().getString("action");
		String type = message.body().getString("type");

		if (action.equalsIgnoreCase("GET")) {
			if (type.equalsIgnoreCase("USER")) {
				if(userId == 0){
					message.reply(Json.encodePrettily(userDBStore.getAllUsers()));
				}else{
					message.reply(Json.encodePrettily(userDBStore.getUserById(userId)));
				}		
			} else if (type.equalsIgnoreCase("MESSAGE")) {
				message.reply(Json.encodePrettily(userDBStore.getUserMessages(userId)));
			}
		} else if (action.equalsIgnoreCase("SAVE")) {
			if (type.equalsIgnoreCase("USER")) {
				User user = Json.decodeValue(message.body().getString("user"), User.class);
				user.setUserId(userCounter.getAndIncrement());
				message.reply(Json.encodePrettily(userDBStore.saveUser(user)));
			} else if (type.equalsIgnoreCase("MESSAGE")) {
				UserMessage userMessage = Json.decodeValue(message.body().getString("message"), UserMessage.class);
				message.reply(Json.encodePrettily(userDBStore.saveUserMessage(userId, userMessage)));
			}
		}else if (action.equalsIgnoreCase("REMOVE")) {
			if (type.equalsIgnoreCase("USER")) {
				message.reply(Json.encodePrettily(userDBStore.removeUser(userId)));
			}
		}

	}

}
