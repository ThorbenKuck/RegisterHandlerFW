package test.tester;

import annotations.RegisterModule;
import handler.RegisterHandler;
import handler.register.Register;

@RegisterModule
public class Tester {

    private Register register;

    public Tester() {
        this.register = RegisterHandler.getInstance().getRegisterForId("1");
    }

    public void run() {
        C c = register.fetchAndGetModuleFromPipe(C.class.getName());
        c.howMuch();
        c.higher();
        register.pushModuleToRegister(C.class.getName() , c);
    }

}
