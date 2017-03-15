package de.thorbenkuck.rhfw.register.pulling;

import de.thorbenkuck.rhfw.duplicate.Duplicator;
import de.thorbenkuck.rhfw.pipe.DataOutputPipe;

import java.util.HashMap;

public class PullHandler {

	private final HashMap<Object, Object> registerInternals;
	private final DataOutputPipe dataOutputPipe;

	public PullHandler(HashMap<Object, Object> registerInternals, DataOutputPipe dataOutputPipe) {
		this.registerInternals = registerInternals;
		this.dataOutputPipe = dataOutputPipe;
	}

	public <T> PipePullStream fromDataOutputPipe(Class<T> key) {
		return new DataOutputPipePullStream(registerInternals, dataOutputPipe, key);
	}

	public <T> T object(Class<T> key) {
		return (T) duplicate(registerInternals.get(key));
	}

	static Object duplicate(Object object) {
		return Duplicator.tryAccess().duplicate(object);
	}

}
