package com.mmsoft.vertx.databasemock.interfaces;

import io.vertx.core.Context;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;

public class VertxConfigAware {
	Context context = Vertx.currentContext();
	
	protected JsonObject config(){
		return context.config();
	}
}
