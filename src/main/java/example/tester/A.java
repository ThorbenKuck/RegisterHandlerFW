package example.tester;

import de.thorbenkuck.rhfw.annotations.DataModule;
import de.thorbenkuck.rhfw.interfaces.RegisterModuleInterface;

@DataModule
public class A implements RegisterModuleInterface {

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
