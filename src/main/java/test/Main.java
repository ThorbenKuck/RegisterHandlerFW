package test;

import handler.RegisterHandler;
import handler.register.RegisterID;
import test.tester.C;
import test.tester.Tester;
import test.tester.Tester2;


public class Main {
    public static void main(String[] args) {

        RegisterHandler registerHandler = RegisterHandler.getInstance();
        RegisterHandler.setScanRootPackage("test");

        RegisterID id = registerHandler.pullNewRegister();

        RegisterHandler.getInstance().bindRegister("1" , id);

        Tester tester = registerHandler.getRegisterForId(id).fetchAndGetModuleFromPipe(Tester.class.getName());
        Tester2 tester2 = registerHandler.getRegisterForId(id).fetchAndGetModuleFromPipe(Tester2.class.getName());

        C c = RegisterHandler.getInstance().getRegisterForId("1").pullAndGetModuleFromPipe(C.class.getName());

        RegisterHandler.getInstance().getRegisterForId("1").pushModuleToRegister("c", c);

        c.higher();

        int longer = 0;

        while(longer < 4) {

            tester.run();
            tester2.run();

            longer++;
        }


    }
}