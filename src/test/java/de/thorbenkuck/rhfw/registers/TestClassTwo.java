package de.thorbenkuck.rhfw.registers;


import de.thorbenkuck.rhfw.annotations.DataModule;
import de.thorbenkuck.rhfw.interfaces.RegisterModuleInterface;

@DataModule(included = false)
class TestClassTwo implements RegisterModuleInterface {

	private String s = "";

	public TestClassTwo() {
	}

	@Override
	public boolean equals(Object o) {
		return o != null && getClass().equals(o.getClass());
	}

	public String getS() {
		return s;
	}

	public void setS(String s) {
		this.s = s;
	}
}