package de.thorbenkuck.rhfw.register;

public interface ResultMatcher<T> {
	boolean matchWith(T t);
}
