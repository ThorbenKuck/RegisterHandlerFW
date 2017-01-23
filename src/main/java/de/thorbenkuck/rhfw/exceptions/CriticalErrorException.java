package de.thorbenkuck.rhfw.exceptions;

public class CriticalErrorException extends RuntimeException {

	private static final String messagePrefix = "[CRITICAL] ";

	CriticalErrorException(String message) {
		super(messagePrefix + message);
	}

	CriticalErrorException(Throwable throwable) {
		super(throwable);
	}

	CriticalErrorException(String message, Throwable throwable) {
		super(messagePrefix + message, throwable);
	}

}
