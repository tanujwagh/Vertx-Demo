package com.mmsoft.vertx.orchestrator.interfaces;

import io.vertx.core.Vertx;
import io.vertx.core.eventbus.EventBus;

public class EventBusAware extends VertxConfigAware{	
	static{
		System.out.println(Vertx.vertx());
	}
	protected EventBus eventBus = Vertx.vertx().eventBus();	
}
