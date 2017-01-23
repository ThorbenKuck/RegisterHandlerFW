package de.thorbenkuck.rhfw.register.fetching;

import de.thorbenkuck.rhfw.pipe.DataOutputPipe;

import java.util.HashMap;

public class FetchHandler {

	private HashMap<String, Object> registerInternals;
	private DataOutputPipe source;

	public FetchHandler(HashMap<String, Object> registerInternals, DataOutputPipe source) {
		this.registerInternals = registerInternals;
		this.source = source;
	}

	public DataOutputPipeFetchStream fromDataOutputPipe() {
		return new DataOutputPipeFetchStream(source.getModuleContainerList(), registerInternals);
	}
}
