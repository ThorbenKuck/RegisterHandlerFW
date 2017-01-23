package de.thorbenkuck.rhfw.exceptions;

class CriticalErrorException extends RuntimeException {

	private static final String messagePrefix = "[CRITICAL]";

	public CriticalErrorException(String message) {
		super(messagePrefix + message);
	}

	public CriticalErrorException(Throwable throwable) {
		super(throwable);
	}

	public CriticalErrorException(String message, Throwable throwable) {
		super(messagePrefix + message, throwable);
	}

}
