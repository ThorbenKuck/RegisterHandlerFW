package de.thorbenkuck.rhfw.templates;

import java.util.ArrayList;
import java.util.List;

public abstract class RegisterTemplate {

    private List<String> classesToImplement = new ArrayList<>();
    private String legerId;
    private boolean autoImport = true;

    public RegisterTemplate() {
        legerId = null;
        autoImport = true;
    }

    public void addToAutoPull(Class<?> clazz) {
        classesToImplement.add(clazz.getName());
    }

    public void addToAutoPull(String className) {
        classesToImplement.add(className);
    }

    public void setlegerId(String legerId) {
        this.legerId = legerId;
    }

    public String getlegerId() {
        return this.legerId;
    }

    public List<String> getClassesToImplement() {
        return this.classesToImplement;
    }

    public boolean classesToImplementEmpyt() {
        return classesToImplement.isEmpty();
    }

    public boolean autoImport() {
        return autoImport;
    }

    public void setAutoPull(boolean autoImport) {
        this.autoImport = autoImport;
    }
}
