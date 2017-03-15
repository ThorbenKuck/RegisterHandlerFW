package de.thorbenkuck.rhfw.register.pulling;

import de.thorbenkuck.rhfw.pipe.DataOutputPipe;

import java.util.HashMap;

public class DataOutputPipePullStream implements PipePullStream {

	private final HashMap<Object, Object> registerInternals;
	private final DataOutputPipe dataOutputPipe;
	private final Object key;

	public DataOutputPipePullStream(HashMap<Object, Object> registerInternals, DataOutputPipe dataOutputPipe, Object key) {
		this.registerInternals = registerInternals;
		this.dataOutputPipe = dataOutputPipe;
		this.key = key;
	}

	@Override
	public PullStream toRegister() {
		Object object = dataOutputPipe.getModule(key);
		if(object != null) {
			registerInternals.put(object.getClass(), object);
		}
		return new DataOutputPipePullStreamFinal<>(object);
	}
}
