package com.mmsoft.vertx.databasemock.handlers;

import io.vertx.core.Handler;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.eventbus.Message;
import io.vertx.core.json.JsonObject;

public class DatabaseMockHandler implements Handler<Message<JsonObject>> {

	private String userConsumerAddress; 
	private EventBus eventBus;
	private JsonObject config;
	
	public DatabaseMockHandler(EventBus eventBus, JsonObject config) {
		this.eventBus = eventBus;
		this.config = config;
		userConsumerAddress = this.config.getString("user.consumer.address", "user.consumer.service");
	}

	@Override
	public void handle(Message<JsonObject> message) {
		eventBus.send(userConsumerAddress, message.body(), asyncResult -> {
			if(asyncResult.succeeded())
				message.reply(asyncResult.result().body());		
			else
				asyncResult.cause().printStackTrace();
		});
	}

}
