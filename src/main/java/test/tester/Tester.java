package test.tester;

import handler.RegisterHandler;
import handler.register.Register;

public class Tester {

    private Register register;

    public Tester() {
        this.register = RegisterHandler.getRegisterForId("1");
    }

    public void run() {
        C c = register.fetchAndGetModuleFromPipe(C.class.getName());
        c.howMuch();
        c.higher();
        register.pushModuleToRegister(C.class.getName() , c);
    }

}
