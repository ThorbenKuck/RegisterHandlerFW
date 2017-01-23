package example.tester;

import de.thorbenkuck.rhfw.annotations.AutoResolve;
import de.thorbenkuck.rhfw.annotations.RegisterModule;
import de.thorbenkuck.rhfw.interfaces.RegisterModuleInterface;

@RegisterModule
public class F implements RegisterModuleInterface {

	@AutoResolve
	public F(E e, D d) {
	}

}
