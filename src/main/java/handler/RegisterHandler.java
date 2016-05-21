package handler;

import handler.register.Register;
import handler.register.RegisterID;

import java.util.HashMap;

public class RegisterHandler {

    private static RegisterHandler instance;

    // Eine statische Liste alle Register, damit kein Name 2 mal vor kommt
    private static HashMap<RegisterID, Register> registerList;
    private static HashMap<String, RegisterID> boundRegisters;

    public synchronized static RegisterHandler getInstance() {
        if(instance == null) {
            instance = new RegisterHandler();
        }
        return instance;
    }

    private RegisterHandler() {
        System.out.println("$ Initialing RegisterHandler for the first time ...");
        if(registerList == null) {
            registerList = new HashMap<>();
        }
        if(boundRegisters == null) {
            boundRegisters = new HashMap<>();
        }

    }

    public boolean registerIDTaken(RegisterID id) {
        return registerList.containsKey(id);
    }

    public boolean registerBound(String legereID) {
        return boundRegisters.containsKey(legereID);
    }

    public RegisterID pullNewRegister() {
        Register newRegister = new Register();
        registerList.put(newRegister.getRegisterId(), newRegister);
        return newRegister.getRegisterId();
    }

    public <T> T getModuleFromRegister(RegisterID registerID, String className) {
        return registerList.get(registerID.toString()).pullModule(className);
    }

    public Register getRegisterForId(RegisterID registerID) {
        return registerList.get(registerID);
    }

    public Register getRegisterForId(String legereId) {
        return getRegisterForId(boundRegisters.get(legereId));
    }

    public void bindRegister(String legereID, RegisterID id) {
        boundRegisters.put(legereID, id);
    }

    public RegisterID getRegisterID(String legereID) {
        return boundRegisters.get(legereID);
    }

}
