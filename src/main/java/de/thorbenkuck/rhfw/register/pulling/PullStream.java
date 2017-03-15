package de.thorbenkuck.rhfw.register.pulling;

public interface PullStream {

	<Type> Type get();

}
