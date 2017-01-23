package example.tester;

import de.thorbenkuck.rhfw.annotations.AutoResolve;
import de.thorbenkuck.rhfw.annotations.DataModule;
import de.thorbenkuck.rhfw.interfaces.RegisterModuleInterface;

@DataModule
public class E implements RegisterModuleInterface {

	@AutoResolve
	public E(C c, A a) {

	}

}
