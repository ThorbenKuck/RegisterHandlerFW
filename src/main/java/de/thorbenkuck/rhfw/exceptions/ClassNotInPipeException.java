package de.thorbenkuck.rhfw.exceptions;

public class ClassNotInPipeException extends ClassNotFoundException {
    public ClassNotInPipeException(String msg) {
        super(msg);
    }
}
