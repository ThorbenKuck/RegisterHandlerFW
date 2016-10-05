package de.thorbenkuck.rhfw.registers;

import de.thorbenkuck.rhfw.interfaces.RegisterModuleInterface;

public class Tester implements RegisterModuleInterface {
    private int integer;
    public Tester() {
        integer = 1;
    }
    public int getInteger() {
        return this.integer;
    }
    public void higherInteger() {
        this.integer++;
    }
    public void setInteger(int integer) {
        this.integer = integer;
    }
}
