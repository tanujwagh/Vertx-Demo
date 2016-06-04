package com.mmsoft.vertx.orchestrator.handlers;

import java.util.concurrent.atomic.AtomicInteger;

import com.mmsoft.vertx.orchestrator.interfaces.OrchestratorHandler;

import io.vertx.core.eventbus.EventBus;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

public class OrchestratorHandlerImpl implements OrchestratorHandler<RoutingContext> {

	private final String KEY_HEADER = "KEY";
	private final String KEY_HEADER_VALUE = "ThisIsMyKey";

	private final String databaseMockConsumerAddress;
	private EventBus eventBus;
	private JsonObject config;

	static AtomicInteger userCounter = new AtomicInteger(1);
	static AtomicInteger messageCounter = new AtomicInteger(1);
	
	JsonObject unathorizedResponse = new JsonObject().put("status", 403).put("reason", "Unauthorized");

	public OrchestratorHandlerImpl(EventBus eventBus, JsonObject config) {
		this.eventBus = eventBus;
		this.config = config;
		databaseMockConsumerAddress = this.config.getString("database.mock.consumer.addresss", "database.mock.service");
	}

	@Override
	public void getAllUsers(RoutingContext routingContext) {
		if (checkHTTPHeader(routingContext)) {
			int userId = 0;
			JsonObject jsonObject = new JsonObject().put("userId", userId).put("action", "GET").put("type", "USER");

			eventBus.send(databaseMockConsumerAddress, jsonObject, asyncResult -> {
				if (asyncResult.succeeded())
					routingContext.response().putHeader("Content-Type", "application/json; charset=utf-8")
							.end(asyncResult.result().body().toString());
				else {
					asyncResult.cause().printStackTrace();
					routingContext.fail(asyncResult.cause());
				}
			});
		} else {
			routingContext.response().setStatusCode(403).end(unathorizedResponse.encodePrettily());
		}

	}

	@Override
	public void getUserById(RoutingContext routingContext) {
		if (checkHTTPHeader(routingContext)) {
			int userId = Integer.parseInt(routingContext.request().getParam("id"));
			JsonObject jsonObject = new JsonObject().put("userId", userId).put("action", "GET").put("type", "USER");
			eventBus.send(databaseMockConsumerAddress, jsonObject, asyncResult -> {
				if (asyncResult.succeeded())
					routingContext.response().putHeader("Content-Type", "application/json; charset=utf-8")
							.end(asyncResult.result().body().toString());
				else {
					asyncResult.cause().printStackTrace();
					routingContext.fail(asyncResult.cause());
				}
			});
		} else {
			routingContext.response().setStatusCode(403).end(unathorizedResponse.encodePrettily());
		}
	}

	@Override
	public void saveUser(RoutingContext routingContext) {
		if (checkHTTPHeader(routingContext)) {
			int userId = userCounter.getAndIncrement();
			JsonObject jsonObject = new JsonObject().put("userId", userId).put("action", "SAVE").put("type", "USER")
					.put("user", routingContext.getBodyAsString());
			eventBus.send(databaseMockConsumerAddress, jsonObject, asyncResult -> {
				if (asyncResult.succeeded())
					routingContext.response().putHeader("Content-Type", "application/json; charset=utf-8")
							.end(asyncResult.result().body().toString());
				else {
					asyncResult.cause().printStackTrace();
					routingContext.fail(asyncResult.cause());
				}
			});
		} else {
			routingContext.response().setStatusCode(403).end(unathorizedResponse.encodePrettily());
		}
	}

	@Override
	public void removeUser(RoutingContext routingContext) {
		if (checkHTTPHeader(routingContext)) {
			int userId = Integer.parseInt(routingContext.request().getParam("id"));
			JsonObject jsonObject = new JsonObject().put("userId", userId).put("action", "REMOVE").put("type", "USER");
			eventBus.send(databaseMockConsumerAddress, jsonObject, asyncResult -> {
				if (asyncResult.succeeded())
					routingContext.response().putHeader("Content-Type", "application/json; charset=utf-8")
							.end(asyncResult.result().body().toString());

				else {
					asyncResult.cause().printStackTrace();
					routingContext.fail(asyncResult.cause());
				}
			});
		} else {
			routingContext.response().setStatusCode(403).end(unathorizedResponse.encodePrettily());
		}
	}

	@Override
	public void getUserMessages(RoutingContext routingContext) {
		if (checkHTTPHeader(routingContext)) {
			int userId = Integer.parseInt(routingContext.request().getParam("id"));
			JsonObject jsonObject = new JsonObject().put("userId", userId).put("action", "GET").put("type", "MESSAGE");
			eventBus.send(databaseMockConsumerAddress, jsonObject, asyncResult -> {
				if (asyncResult.succeeded())
					routingContext.response().putHeader("Content-Type", "application/json; charset=utf-8")
							.end(asyncResult.result().body().toString());
				else {
					asyncResult.cause().printStackTrace();
					routingContext.fail(asyncResult.cause());
				}
			});
		} else {
			routingContext.response().setStatusCode(403).end(unathorizedResponse.encodePrettily());
		}
	}

	@Override
	public void saveUserMessage(RoutingContext routingContext) {
		if (checkHTTPHeader(routingContext)) {
			int userId = Integer.parseInt(routingContext.request().getParam("id"));
			JsonObject jsonObject = new JsonObject().put("userId", userId).put("action", "SAVE").put("type", "MESSAGE")
					.put("message", routingContext.getBodyAsString());
			eventBus.send(databaseMockConsumerAddress, jsonObject, asyncResult -> {
				if (asyncResult.succeeded())
					routingContext.response().putHeader("Content-Type", "application/json; charset=utf-8")
							.end(asyncResult.result().body().toString());
				else {
					asyncResult.cause().printStackTrace();
					routingContext.fail(asyncResult.cause());
				}
			});
		} else {
			routingContext.response().setStatusCode(403).end(unathorizedResponse.encodePrettily());
		}
	}

	private boolean checkHTTPHeader(RoutingContext routingContext) {
		if (routingContext.request().getHeader(KEY_HEADER) == null
				|| !routingContext.request().getHeader(KEY_HEADER).equals(KEY_HEADER_VALUE)) {
			return false;
		}
		return true;
	}

}
