package de.thorbenkuck.rhfw.register;

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

    /**
     * TODO-Later Exception-Handling
     * @param toCopy
     */
    public RegisterID(RegisterID toCopy) {
        if(RegisterID.allRegisterIDs.contains(toCopy.toString())) {
            this.currentRegisterID = toCopy.toString();
        } else {
            // TODO-Later Exception-Handling
            System.out.println("NO! This is not allowed.. Also, here will be thrown an exception!");
        }
    }

    public RegisterID(Register toCopy) {
        RegisterID idToCopy = toCopy.getRegisterId();
        if(RegisterID.allRegisterIDs.contains(idToCopy.toString())) {
            this.currentRegisterID = idToCopy.toString();
        } else {
            // TODO-Later Exception-Handling
            System.out.println("NO! This is not allowed.. Also, here will be thrown an exception!");
        }
    }

    @Override
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
