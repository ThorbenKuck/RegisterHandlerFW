package test;

import de.thorbenkuck.rhfw.handler.RegisterHandler;
import de.thorbenkuck.rhfw.handler.register.Register;
import test.tester.C;
import test.tester.Tester;
import test.tester.Tester2;


public class Main {
    public static void main(String[] args) {

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