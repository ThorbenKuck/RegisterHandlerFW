package test;

import handler.RegisterHandler;
import handler.register.Register;
import handler.register.RegisterID;
import test.tester.A;
import test.tester.C;
import test.tester.Tester;
import test.tester.Tester2;


public class Main {
    public static void main(String[] args) {

        RegisterHandler.setScanRootPackage("test");

        Register register = RegisterHandler.pullAndGetNewRegister(new TestTemplate());

        Tester tester = new Tester();
        Tester2 tester2 = new Tester2();

        C c = register.pullModule(C.class.getName());

        c.higher();
        c.howMuch();

        int longer = 0;

        while(longer < 4) {

            tester.run();
            tester2.run();

            longer++;
        }
    }
}