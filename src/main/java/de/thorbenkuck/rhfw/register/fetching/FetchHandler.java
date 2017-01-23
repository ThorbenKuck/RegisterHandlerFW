package de.thorbenkuck.rhfw.register.fetching;

import de.thorbenkuck.rhfw.pipe.DataOutputPipe;
import de.thorbenkuck.rhfw.register.Register;

public class FetchHandler {

	private Register responsibleFor;
	private DataOutputPipe source;

	public FetchHandler(Register responsibleFor, DataOutputPipe source) {
		this.responsibleFor = responsibleFor;
		this.source = source;
	}

	public RegisterStream fromDataOutputPipe() {
		return new RegisterStream();
	}
}
