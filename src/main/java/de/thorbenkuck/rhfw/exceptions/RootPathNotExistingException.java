package de.thorbenkuck.rhfw.exceptions;

import java.io.FileNotFoundException;

public class RootPathNotExistingException extends FileNotFoundException {
    public RootPathNotExistingException(String msg) {
        super(msg);
    }
}
