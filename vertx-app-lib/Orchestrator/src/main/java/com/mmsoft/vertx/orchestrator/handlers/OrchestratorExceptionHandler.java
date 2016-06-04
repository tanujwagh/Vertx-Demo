package com.mmsoft.vertx.orchestrator.handlers;

import io.netty.handler.codec.http.HttpResponseStatus;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

public class OrchestratorExceptionHandler implements Handler<RoutingContext> {

	@Override
	public void handle(RoutingContext routingContext) {
		
		Throwable exception = routingContext.failure();
		int statusCode = routingContext.statusCode() != 0 ? routingContext.statusCode() : 500;
		
		final JsonObject error = new JsonObject().put("timestamp", System.nanoTime())
				.put("status", statusCode)
				.put("reason", HttpResponseStatus.valueOf(statusCode).reasonPhrase())
				.put("path", routingContext.normalisedPath()).put("exception", exception.getClass().getName());

		if (exception.getMessage() != null) {
			error.put("message", exception.getMessage());
		}

		routingContext.response().setStatusCode(statusCode);
		routingContext.response().end(error.encode());
	}

}
