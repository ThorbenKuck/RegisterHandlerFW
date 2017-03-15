package de.thorbenkuck.rhfw.register.pulling;

import java.util.HashMap;

public class RegisterPullStream<T> implements PullStream {

	private final Class<T> key;
	private final HashMap<Object, Object> registerInternals;

	public RegisterPullStream(Class<T> key, HashMap<Object, Object> registerInternals) {
		this.key = key;
		this.registerInternals = registerInternals;
	}

	@Override
	public <Type> Type get() {
		return (Type) registerInternals.get(key);
	}
}
