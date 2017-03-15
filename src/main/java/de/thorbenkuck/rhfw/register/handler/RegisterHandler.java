package de.thorbenkuck.rhfw.register.handler;

import de.thorbenkuck.rhfw.register.Register;
import de.thorbenkuck.rhfw.register.RegisterID;
import de.thorbenkuck.rhfw.templates.RegisterTemplate;

import java.util.HashMap;

public class RegisterHandler {

    // Eine statische Liste alle Register, damit kein Name 2 mal vor kommt
    private static HashMap<RegisterID, Register> registerList = new HashMap<>();
    private static HashMap<String, RegisterID> boundRegisters = new HashMap<>();

    public static boolean registerIDTaken(RegisterID id) {
        for(RegisterID registerID : registerList.keySet()) {
            if(registerID.equals(id)) {
                return true;
            }
        }
        return false;
    }

    public static boolean registerBound(String legerID) {
        return RegisterHandler.boundRegisters.containsKey(legerID);
    }

    public static RegisterID pullNewRegister() {
        Register newRegister = new Register();
        RegisterHandler.registerList.put(newRegister.getRegisterId(), newRegister);
        return newRegister.getRegisterId();
    }

    public static Register pullAndGetNewRegister() {
        return getFromRegisterList(pullNewRegister());
    }

    public static RegisterID pullNewRegister(RegisterTemplate template) {
        Register newRegister = new Register();

        if (!template.classesToImplementEmpty()) {
            if (template.autoImport()) {
                for (Class<?> clazz : template.getClassesToImplement()) {
                   newRegister.fetch().fromDataOutputPipe().ofClassType(clazz).toRegister();
                }
            }
        }

		RegisterID id = newRegister.getRegisterId();
		RegisterHandler.registerList.put(id, newRegister);
        if (template.getlegerId() != null) {
            bindRegister(template.getlegerId(), newRegister.getRegisterId());
        }

        return id;
    }

    public static Register pullAndGetNewRegister(RegisterTemplate template) {
        return getFromRegisterList(pullNewRegister(template));
    }

    public static <T> T getModuleFromRegister(RegisterID registerID, String className) {
        return getRegisterForId(registerID).pullModule(className);
    }

    public static <T> T getModuleFromRegister(String legerID, String className) {
        return getModuleFromRegister(getRegisterID(legerID), className);
    }

    public static Register getRegisterForId(RegisterID registerID) {
        return getFromRegisterList(registerID);
    }

    public static Register getRegisterForId(String legerId) {
        return getRegisterForId(getRegisterID(legerId));
    }

    public static void bindRegister(String legerID, RegisterID id) {
        RegisterHandler.boundRegisters.put(legerID, id);
    }

    public static void bindRegister(String legerID, Register register) {
        bindRegister(legerID, register.getRegisterId());
    }

    public synchronized static RegisterID getRegisterID(String legerID) {
        if(RegisterHandler.boundRegisters.get(legerID) == null) {
			throw new IllegalArgumentException("No Register exists for legerID " + legerID);
        }
        return RegisterHandler.boundRegisters.get(legerID);
    }

    private static Register getFromRegisterList(RegisterID id) {
        for (RegisterID currentRegisterID : registerList.keySet()) {
            if(currentRegisterID.equals(id)) {
                return RegisterHandler.registerList.get(currentRegisterID);
            }
        }
		throw new IllegalArgumentException("No Register exists for RegisterID(" + id + ")");
    }
}
