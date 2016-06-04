package com.mmsoft.vertx.orchestrator;

import com.mmsoft.vertx.orchestrator.handlers.OrchestratorExceptionHandler;
import com.mmsoft.vertx.orchestrator.handlers.OrchestratorHandlerImpl;
import com.mmsoft.vertx.orchestrator.interfaces.OrchestratorHandler;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.StaticHandler;

public class Orchestrator extends AbstractVerticle {

	Router router;
	OrchestratorHandler<RoutingContext> handler;
	
	@Override
	public void start(Future<Void> future) {
		router = Router.router(vertx);
		handler = new OrchestratorHandlerImpl(vertx.eventBus(), config());
		intilizeRouterRoutes();
		registerHttpClient(future);		
	}

	private void registerHttpClient(Future<Void> future) {
		vertx
        .createHttpServer()
        .requestHandler(router::accept)
        .listen(
            config().getInteger("http.port", 8080),
            result -> {
              if (result.succeeded()) {
            	 future.complete();
              } else {
            	 future.fail(result.cause());
              }
            }
        );	
	}

	private void intilizeRouterRoutes() {
		
		router.route("/pages/*").handler(StaticHandler.create("pages"));
		
	    router.get("/v1/api/users").produces("application/json").handler(handler::getAllUsers);
	    router.get("/v1/api/users/:id").produces("application/json").handler(handler::getUserById);
	    router.delete("/v1/api/users/:id").consumes("application/json").produces("application/json").handler(handler::removeUser);
	    router.route("/v1/api/users").handler(BodyHandler.create());
	    router.post("/v1/api/users").consumes("application/json").produces("application/json").handler(handler::saveUser);
	    
	    
	    router.get("/v1/api/users/:id/messages").produces("application/json").handler(handler::getUserMessages);
	    router.route("/v1/api/users/:id/messages").handler(BodyHandler.create());
	    router.post("/v1/api/users/:id/messages").consumes("application/json").produces("application/json").handler(handler::saveUserMessage);
	    
	    router.route().failureHandler(new OrchestratorExceptionHandler());
		
	}
}
