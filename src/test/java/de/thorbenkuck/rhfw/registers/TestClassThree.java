package de.thorbenkuck.rhfw.registers;

import de.thorbenkuck.rhfw.annotations.DataModule;
import de.thorbenkuck.rhfw.interfaces.RegisterModuleInterface;

@DataModule(included = false)
class TestClassThree implements RegisterModuleInterface, TestInterface {

	public TestClassThree() {

	}
	@Override
	public boolean equals(Object o) {
		return o != null && getClass().equals(o.getClass());
	}
}
