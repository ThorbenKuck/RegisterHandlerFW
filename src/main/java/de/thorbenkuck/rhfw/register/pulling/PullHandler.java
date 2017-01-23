package de.thorbenkuck.rhfw.register.pulling;

import de.thorbenkuck.rhfw.pipe.ObjectedModuleContainerList;
import de.thorbenkuck.rhfw.register.handler.RegisterHandler;

public class PullHandler {

	private RegisterHandler registerHandler;
	private ObjectedModuleContainerList<String, Object> dataOutputPipeModules;

	public PullHandler(RegisterHandler registerHandler, ObjectedModuleContainerList<String, Object> dataOutputPipeModules) {
		this.registerHandler = registerHandler;
		this.dataOutputPipeModules = dataOutputPipeModules;
	}

	void fromDataOutputPipe() {

	}

	void fromRegister() {

	}

}
