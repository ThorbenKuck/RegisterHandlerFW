package test.tester;

import annotations.RegisterModule;

@RegisterModule
public class C {

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
