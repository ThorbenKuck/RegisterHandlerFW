package handler;

import handler.register.Register;
import handler.register.RegisterID;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RegisterHandler {

    private static RegisterHandler instance;

    // Eine statische Liste alle Register, damit kein Name 2 mal vor kommt
    private static HashMap<RegisterID, Register> registerList;
    private static HashMap<String, RegisterID> boundRegisters;
    private static List<String> rootPackages;
    public static final int ROOT_PACKAGES_EMPTY = -1;

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

    public static void setScanRootPackage(String packageName) {
        rootPackages = new ArrayList<>();
        rootPackages.add(packageName);
    }

    public static void setScanRootPackage(List<String> packages) {
        if(rootPackages == null) {
            rootPackages = new ArrayList<>();
        }
        rootPackages = packages;
    }

    public static void addToScanPackages(String packageName) {
        if(rootPackages == null) {
            rootPackages = new ArrayList<>();
        }
        rootPackages.add(packageName);
    }

    public static void addToScanPackages (List<String> packageNames) {
        int packageSize = packageNames.size();
        for(int i = 0 ; i < packageSize ; i++) {
            setScanRootPackage(packageNames.get(i));
        }
    }

    public static int getSizeOfScannedPackages() {
        if(rootPackages == null) {
            return ROOT_PACKAGES_EMPTY;
        } else {
            return rootPackages.size();
        }
    }

    public static ArrayList<String> getAllRootPaths() {
        return (ArrayList<String>) rootPackages;
    }
}
