package com.mmsoft.vertx.databasemock;

import com.mmsoft.vertx.databasemock.handlers.DatabaseMockHandler;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.eventbus.MessageConsumer;
import io.vertx.core.json.JsonObject;

public class DatabaseMock extends AbstractVerticle {

	private String databaseMockConsumerAddress;
	EventBus eventBus;

	@Override
	public void start() throws Exception {
		databaseMockConsumerAddress = config().getString("database.mock.consumer.addresss", "database.mock.service");
		eventBus = vertx.eventBus();

		MessageConsumer<JsonObject> consumer = eventBus.consumer(databaseMockConsumerAddress);
		consumer.handler(new DatabaseMockHandler(eventBus, config()));

		consumer.completionHandler(res -> {
			if (res.succeeded()) {
				System.out.println("The handler registration has reached all nodes");
			} else {
				System.out.println("Registration failed!");
			}
		});

	}
}
