package example.tester;

import de.thorbenkuck.rhfw.annotations.AutoResolve;
import de.thorbenkuck.rhfw.annotations.DataModule;
import de.thorbenkuck.rhfw.interfaces.RegisterModuleInterface;

@DataModule
public class B implements RegisterModuleInterface {

    private String name;

    @AutoResolve
    public B (A a) {
        name = "B";
    }

    public String getDataName() { return this.name; }

    public void setName(String name) { this.name = name; }

    public void whoAmI() { System.out.println("Und mein Name ist \""+getDataName()+"\""); }
}
