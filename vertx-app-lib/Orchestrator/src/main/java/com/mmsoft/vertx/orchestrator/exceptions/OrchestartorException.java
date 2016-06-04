package com.mmsoft.vertx.orchestrator.exceptions;

public class OrchestartorException extends Exception{

	/**
	 * Generated serial version Id.
	 */
	private static final long serialVersionUID = 2184483866788025427L;
	
	int statusCode;
	
	public void setStatusCode(int statusCode){
		this.statusCode = statusCode;
	}
	
	public int getStatusCode(){
		return statusCode;
	}
	
	public OrchestartorException() {
		super();
	}

	public OrchestartorException(String message) {
		super(message);
	}

	public OrchestartorException(String message, Throwable cause) {
		super(message, cause);
	}

}
