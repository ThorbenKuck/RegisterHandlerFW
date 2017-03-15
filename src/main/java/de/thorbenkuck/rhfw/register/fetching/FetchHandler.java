package de.thorbenkuck.rhfw.register.fetching;

import de.thorbenkuck.rhfw.pipe.DataOutputPipe;
import de.thorbenkuck.rhfw.register.Register;

import java.util.HashMap;

public class FetchHandler {

	private HashMap<Object, Object> registerInternals;
	private DataOutputPipe source;
	private Register register;

	public FetchHandler(HashMap<Object, Object> registerInternals, DataOutputPipe source) {
		this.registerInternals = registerInternals;
		this.source = source;
	}

	public DataOutputPipeRepositoryCondition fromDataOutputPipe() {
		return new DataOutputPipeRepositoryCondition(source, registerInternals);
	}
}
