package example.tester;

import de.thorbenkuck.rhfw.interfaces.RegisterModuleInterface;

public class B implements RegisterModuleInterface {

    private String name;

    public B () {
        name = "B";
    }

    public String getDataName() { return this.name; }

    public void setName(String name) { this.name = name; }

    public void whoAmI() { System.out.println("Und mein Name ist \""+getDataName()+"\""); }
}
