package com.mmsoft.vertx.orchestrator.test;

import java.io.IOException;
import java.net.ServerSocket;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.mmsoft.vertx.databasemock.DatabaseMock;
import com.mmsoft.vertx.orchestrator.Orchestrator;
import com.mmsoft.vertx.user.objects.User;

import io.netty.handler.codec.http.HttpResponseStatus;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;

@RunWith(VertxUnitRunner.class)
public class OrchestratorTest {

  private Vertx vertx;
  private Integer port;
  
  @Before
  public void setUp(TestContext context) throws IOException {
    vertx = Vertx.vertx();
    
    ServerSocket socket = new ServerSocket(0);
    port = socket.getLocalPort();
    socket.close();

    DeploymentOptions options = new DeploymentOptions()
        .setConfig(new JsonObject().put("http.port", port)
        );

    vertx.deployVerticle(Orchestrator.class.getName(), options, context.asyncAssertSuccess());
  }
  
  @After
  public void tearDown(TestContext context) {
    vertx.close(context.asyncAssertSuccess());
  }
  
  @Test
  public void testUserPage(TestContext context) {
	  
    final Async async = context.async();

    vertx.createHttpClient().getNow(port, "localhost", "/pages/users.html", response -> {
      response.handler(body -> {
        context.assertTrue(body.toString().contains("User - Messaging"));
        async.complete();
      });
    });
  }
  
  @Test
  public void testHTTPHeader(TestContext context) {
	  
    final Async async = context.async();
    JsonObject unathorizedResponse = new JsonObject().put("status", 403).put("reason", "Unauthorized");
    
    vertx.createHttpClient().getNow(port, "localhost", "/v1/api/users", response -> {
      response.handler(body -> {
    	context.assertEquals(response.statusCode(), 403);
    	context.assertEquals(response.statusMessage(), HttpResponseStatus.valueOf(403).reasonPhrase());
        context.assertEquals(body.toJsonObject(), unathorizedResponse);
        async.complete();
      });
    });
  }
  
}
