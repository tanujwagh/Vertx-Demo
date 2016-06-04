package com.mmsoft.vertx.deployment;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.IOUtils;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.AsyncResult;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;

public class Deployment extends AbstractVerticle {

	Logger logger = LoggerFactory.getLogger(Deployment.class);
	File configFile;

	@SuppressWarnings("unchecked")
	@Override
	public void start() throws Exception {

		if (System.getProperty("config") != null) {
			configFile = new File(System.getProperty("config"));
		} else {
			logger.fatal("No configuration file provided : Usage -Dconfig=filepath");
			throw new Exception("No configuration file provided : Usage -Dconfig=filepath");
		}

		if (!configFile.exists())
			throw new Exception("Cofiguration file [" + configFile.getAbsolutePath() + "] not found!");

		InputStream inputStream = new FileInputStream(configFile);
		StringWriter writer = new StringWriter();
		IOUtils.copy(inputStream, writer, "UTF-8");
		String ConfigString = writer.toString();

		JsonObject config = new JsonObject(ConfigString);

		DeploymentOptions deploymentOptions = new DeploymentOptions(config);

		JsonArray modules = config.getJsonArray("modules");
		

		for (LinkedHashMap<String, String> module : (List<LinkedHashMap<String, String>>) modules.getList()) {
			logger.info("Started deploying module [" + module + "]");
			Long deployStartTime = System.currentTimeMillis();
			if (module.get("type").equalsIgnoreCase("worker")) {
				deploymentOptions.setWorker(true);
				vertx.deployVerticle(module.get("class"), deploymentOptions, new Handler<AsyncResult<String>>() {
					@Override
					public void handle(AsyncResult<String> event) {
						if (event.succeeded()) {
							Long elapsed = System.currentTimeMillis() - deployStartTime;
							logger.info(module + " is deployed succesfully in "
									+ (TimeUnit.MILLISECONDS.toSeconds(elapsed) % 60) + " Seconds.");
						} else {
							logger.fatal("Failed to deploy module : " + module);
							event.cause().printStackTrace();
						}
					}
				});
			} else {
				deploymentOptions.setWorker(false);
				vertx.deployVerticle(module.get("class"), deploymentOptions, new Handler<AsyncResult<String>>() {
					@Override
					public void handle(AsyncResult<String> event) {
						if (event.succeeded()) {
							Long elapsed = System.currentTimeMillis() - deployStartTime;
							logger.info(module + " is deployed succesfully in "
									+ (TimeUnit.MILLISECONDS.toSeconds(elapsed) % 60) + " Seconds.");
						} else {
							logger.fatal("Failed to deploy module : " + module);
							event.cause().printStackTrace();
						}
					}
				});
			}
		}

	}
}
