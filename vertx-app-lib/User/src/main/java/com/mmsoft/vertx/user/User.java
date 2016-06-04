package com.mmsoft.vertx.user;

import com.mmsoft.vertx.user.handlers.UserModelHandler;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.eventbus.MessageConsumer;
import io.vertx.core.json.JsonObject;

public class User extends AbstractVerticle{

	private String userConsumerAddress;
	EventBus eventBus;
	
	@Override
	public void start() throws Exception {
		userConsumerAddress = config().getString("user.consumer.address", "user.consumer.service");
		eventBus = vertx.eventBus();
		MessageConsumer<JsonObject> consumer = eventBus.consumer(userConsumerAddress);
		consumer.handler(new UserModelHandler());
	}
	
}
