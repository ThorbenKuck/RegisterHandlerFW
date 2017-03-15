package de.thorbenkuck.rhfw.register.pushing;

import de.thorbenkuck.rhfw.duplicate.Duplicator;
import de.thorbenkuck.rhfw.pipe.DataOutputPipe;

import java.util.HashMap;

public class PushHandler {

	private final DataOutputPipe dataOutputPipe;
	private final HashMap<Object, Object> registerInternals;

	public PushHandler(DataOutputPipe dataOutputPipe, HashMap<Object, Object> registerInternals) {
		this.dataOutputPipe = dataOutputPipe;
		this.registerInternals = registerInternals;
	}

	public <T> void toRegister(T object) {
		toRegister(object.getClass(), object);
	}

	/**
	 * TODO Duplicate instead of direct put!
	 */
	public void toRegister(Object key, Object object) {
		registerInternals.put(key, duplicate(object));
	}

	public void toDataOutputPipe(Object object) {
		toDataOutputPipe(object.getClass(), object);
	}

	public void toDataOutputPipe(Object key, Object object) {
		dataOutputPipe.add(key, duplicate(object));
	}

	static Object duplicate(Object object) {
		return Duplicator.tryAccess().duplicate(object);
	}

}
