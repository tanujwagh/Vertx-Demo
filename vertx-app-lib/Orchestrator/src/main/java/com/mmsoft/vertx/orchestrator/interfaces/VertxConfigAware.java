package com.mmsoft.vertx.orchestrator.interfaces;

import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;

public class VertxConfigAware {
	protected Vertx vertx = Vertx.vertx();
	
	protected JsonObject config(){
		return Vertx.currentContext().config();
	}
}
