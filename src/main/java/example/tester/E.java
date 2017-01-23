package example.tester;

import de.thorbenkuck.rhfw.annotations.AutoResolve;
import de.thorbenkuck.rhfw.annotations.RegisterModule;
import de.thorbenkuck.rhfw.interfaces.RegisterModuleInterface;

@RegisterModule
public class E implements RegisterModuleInterface {

	@AutoResolve
	public E(C c, A a) {

	}

}
