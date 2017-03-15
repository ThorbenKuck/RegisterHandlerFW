package de.thorbenkuck.rhfw.templates;

import java.util.ArrayList;
import java.util.List;

public abstract class RegisterTemplate {

    private List<Class<?>> classesToImplement = new ArrayList<>();
    private String legerId;
    private boolean autoImport = true;

    public RegisterTemplate() {
        legerId = null;
        autoImport = true;
    }

    public void addToAutoPull(Class<?> className) {
        classesToImplement.add(className);
    }

    public void setlegerId(String legerId) {
        this.legerId = legerId;
    }

    public String getlegerId() {
        return this.legerId;
    }

    public List<Class<?>> getClassesToImplement() {
        return this.classesToImplement;
    }

    public boolean classesToImplementEmpty() {
        return classesToImplement.isEmpty();
    }

    public boolean autoImport() {
        return autoImport;
    }

    public void setAutoPull(boolean autoImport) {
        this.autoImport = autoImport;
    }
}
