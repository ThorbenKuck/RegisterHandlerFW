package test.tester;

import de.thorbenkuck.rhfw.annotations.RegisterModule;

@RegisterModule
public class A {

    private String name;

    public A () {
        name = "A";
    }

    public String getDataName() { return this.name; }

    public void setName(String name) { this.name = name; }

    public void whoAmI() {
        System.out.println("Mein Name ist \""+getDataName()+"\"");
    }
}
