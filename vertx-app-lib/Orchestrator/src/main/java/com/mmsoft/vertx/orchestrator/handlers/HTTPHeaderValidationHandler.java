package com.mmsoft.vertx.orchestrator.handlers;

import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;

public class HTTPHeaderValidationHandler implements Handler<RoutingContext> {

	private final String KEY_HEADER = "KEY";
	private final String KEY_HEADER_VALUE = "ThisIsMyKey";
	
	@Override
	public void handle(RoutingContext routingContext) {
		if(routingContext.request().getHeader(KEY_HEADER) == null || !routingContext.request().getHeader(KEY_HEADER).equals(KEY_HEADER_VALUE)){
			routingContext.fail(403);
		}else {
			routingContext.next();
		}
	}

}
