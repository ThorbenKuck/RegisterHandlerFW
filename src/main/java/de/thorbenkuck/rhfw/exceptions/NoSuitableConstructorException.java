package de.thorbenkuck.rhfw.exceptions;

public class NoSuitableConstructorException extends CriticalErrorException {
	public NoSuitableConstructorException(String message) {
		super(message);
	}

	public NoSuitableConstructorException(Throwable throwable) {
		super(throwable);
	}

	public NoSuitableConstructorException(String message, Throwable throwable) {
		super(message, throwable);
	}
}
