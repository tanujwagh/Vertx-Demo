package com.mmsoft.vertx.orchestrator.exceptions;

public class RouterException extends Exception {

	/**
	 * Generated serial version Id.
	 */
	private static final long serialVersionUID = 1543955066260249336L;

	public RouterException(){
		super();
	}
	
	public RouterException(String message){
		super(message);
	}
	
	public RouterException(String message, Throwable cause){
		super(message, cause);
	}
}
