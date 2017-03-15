package example.tester;

import de.thorbenkuck.rhfw.register.handler.RegisterHandler;
import de.thorbenkuck.rhfw.register.Register;

public class Tester2 {

    private Register register;

    public Tester2() {
        this.register = RegisterHandler.getRegisterForId("1");
    }

    public void run() {
        C c = register.pull().object(C.class);
        c.howMuch();
        c.higher();
        register.push().toRegister(c);
    }

}
