package test.tester;

import de.thorbenkuck.rhfw.interfaces.RegisterModuleInterface;

public class C implements RegisterModuleInterface {

    private int counter;

    public C() {
        counter = 1;
    }

    public void higher() {
        counter++;
    }

    public void howMuch() {
        System.out.println("$ Ich zähle " + counter);
    }

}
