package example;

import de.thorbenkuck.rhfw.register.handler.RegisterHandler;
import de.thorbenkuck.rhfw.register.Register;
import example.tester.C;
import example.tester.Tester;
import example.tester.Tester2;


public class Main {
    public static void main(String[] args) {

        Register register = RegisterHandler.pullAndGetNewRegister(new TestTemplate());

        System.out.println(register);

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