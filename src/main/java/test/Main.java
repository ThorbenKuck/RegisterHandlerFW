package test;

import de.thorbenkuck.rhfw.handler.RegisterHandler;
import de.thorbenkuck.rhfw.handler.register.Register;
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

        for(int i = 0 ; i < 4 ; i++) {

            tester.run();
            tester2.run();

        }
    }
}