package com.mmsoft.vertx.databasemock.interfaces;

import io.vertx.core.Vertx;
import io.vertx.core.eventbus.EventBus;

public class EventBusAware extends VertxConfigAware{	
	Vertx vertx = Vertx.vertx();
	protected EventBus eventBus = vertx.eventBus();
}
