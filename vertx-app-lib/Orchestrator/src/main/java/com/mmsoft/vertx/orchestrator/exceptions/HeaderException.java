package com.mmsoft.vertx.orchestrator.exceptions;

public class HeaderException extends OrchestartorException {

	/**
	 * Generated serial version Id.
	 */
	private static final long serialVersionUID = -250075197492488350L;

	public HeaderException() {
		super();
	}

	public HeaderException(String message) {
		super(message);
	}
	
	public HeaderException(String message, int statusCode) {
		super(message);
		this.statusCode = statusCode;
	}

	public HeaderException(String message, Throwable cause) {
		super(message, cause);
	}

}
