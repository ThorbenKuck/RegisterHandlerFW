package test.tester;

import de.thorbenkuck.rhfw.handler.RegisterHandler;
import de.thorbenkuck.rhfw.handler.register.Register;

public class Tester2 {

    private Register register;

    public Tester2() {
        this.register = RegisterHandler.getRegisterForId("1");
    }

    public void run() {
        C c = register.pullModule(C.class.getName());
        c.howMuch();
        c.higher();
        register.pushModuleToRegister(C.class.getName() , c);
    }

}
