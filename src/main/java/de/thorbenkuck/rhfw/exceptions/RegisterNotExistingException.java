package de.thorbenkuck.rhfw.exceptions;

public class RegisterNotExistingException extends ClassNotFoundException {
    public RegisterNotExistingException(String msg) {
        super(msg);
    }
}
