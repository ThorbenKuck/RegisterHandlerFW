package de.thorbenkuck.rhfw.handler.register;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class RegisterID {

    private static List<String> allRegisterIDs;
    private String currentRegisterID;

    RegisterID() {
        allRegisterIDs = new ArrayList<>();
        randomID();
    }

    public String toString() {
        return currentRegisterID;
    }

    void randomID() {
        String toTest = UUID.randomUUID().toString();
        while(allRegisterIDs.contains(toTest)) {
            toTest = UUID.randomUUID().toString();
        }
        RegisterID.allRegisterIDs.add(toTest);
        currentRegisterID = toTest;
    }
}
