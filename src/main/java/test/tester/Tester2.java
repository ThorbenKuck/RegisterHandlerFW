package test.tester;

import handler.RegisterHandler;
import handler.register.Register;

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
