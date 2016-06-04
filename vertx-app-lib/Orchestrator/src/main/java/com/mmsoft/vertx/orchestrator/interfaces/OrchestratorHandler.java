package com.mmsoft.vertx.orchestrator.interfaces;

/**
 * @author Tanuj Wagh
 * @version 0.0.1
 * @description This is generic interface for all the user and user message related operations.
 * @param <T>
 */
public interface OrchestratorHandler<T>{
	void getAllUsers(T t);
	void getUserById(T t);
	void saveUser(T t);
	void removeUser(T t);
	void getUserMessages(T t);
	void saveUserMessage(T t);
}
