package test;

import handler.RegisterHandler;
import handler.register.RegisterID;
import test.tester.C;
import test.tester.Tester;
import test.tester.Tester2;


public class Main {
    public static void main(String[] args) {

        RegisterHandler.setScanRootPackage("test");

        RegisterID id = RegisterHandler.pullNewRegister(new TestTemplate());

        Tester tester = new Tester();
        Tester2 tester2 = new Tester2();

        C c = RegisterHandler.getRegisterForId(id).pullModule(C.class.getName());
        c.higher();

        int longer = 0;

        while(longer < 4) {

            tester.run();
            tester2.run();

            longer++;
        }

    }
}