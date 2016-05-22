package handler;

import handler.register.Register;
import handler.register.RegisterID;
import templates.RegisterTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RegisterHandler {

    // Eine statische Liste alle Register, damit kein Name 2 mal vor kommt
    private static HashMap<RegisterID, Register> registerList = new HashMap<>();
    private static HashMap<String, RegisterID> boundRegisters = new HashMap<>();
    private static List<String> rootPackages;
    public static final int ROOT_PACKAGES_EMPTY = -1;

    public static boolean registerIDTaken(RegisterID id) {
        return RegisterHandler.registerList.containsKey(id);
    }

    public static boolean registerBound(String legereID) {
        return RegisterHandler.boundRegisters.containsKey(legereID);
    }

    public static RegisterID pullNewRegister() {
        Register newRegister = new Register();
        RegisterHandler.registerList.put(newRegister.getRegisterId(), newRegister);
        return newRegister.getRegisterId();
    }

    public static Register pullAndGetNewRegister() {
        return RegisterHandler.registerList.get(pullNewRegister());
    }

    public static <T extends RegisterTemplate> RegisterID pullNewRegister(RegisterTemplate template) {
        Register newRegister = new Register();
        RegisterID id = newRegister.getRegisterId();
        RegisterHandler.registerList.put(id, newRegister);
        List<String> classesToImplement = null;
        String legereId;

        if(!template.classesToImplementEmpyt()) {
            if(template.autoImport()) {
                classesToImplement = template.getClassesToImplement();
                for (String s : classesToImplement) {
                    RegisterHandler.registerList.get(id).fetchModuleFromPipe(s);
                }
            }
        }
        if(template.getLegereId() != null) {
            legereId = template.getLegereId();
            bindRegister(legereId, newRegister.getRegisterId());
        }

        return newRegister.getRegisterId();
    }

    public static <T extends RegisterTemplate> Register pullAndGetNewRegister(RegisterTemplate template) {
        return RegisterHandler.registerList.get(pullNewRegister(template));
    }

    public static <T> T getModuleFromRegister(RegisterID registerID, String className) {
        return RegisterHandler.registerList.get(registerID.toString()).pullModule(className);
    }

    public static Register getRegisterForId(RegisterID registerID) {
        return RegisterHandler.registerList.get(registerID);
    }

    public static Register getRegisterForId(String legereId) {
        return getRegisterForId(RegisterHandler.boundRegisters.get(legereId));
    }

    public static void bindRegister(String legereID, RegisterID id) {
        RegisterHandler.boundRegisters.put(legereID, id);
    }

    public synchronized static RegisterID getRegisterID(String legereID) {
        System.out.println(boundRegisters.get(legereID).toString());
        return RegisterHandler.boundRegisters.get(legereID);
    }

    public static void setScanRootPackage(String packageName) {
        RegisterHandler.rootPackages = new ArrayList<>();
        RegisterHandler.rootPackages.add(packageName);
    }

    public static void setScanRootPackage(List<String> packages) {
        if(RegisterHandler.rootPackages == null) {
            RegisterHandler.rootPackages = new ArrayList<>();
        }
        RegisterHandler.rootPackages = packages;
    }

    public static void addToScanPackages(String packageName) {
        if(RegisterHandler.rootPackages == null) {
            RegisterHandler.rootPackages = new ArrayList<>();
        }
        RegisterHandler.rootPackages.add(packageName);
    }

    public static void addToScanPackages (List<String> packageNames) {
        int packageSize = packageNames.size();
        for(int i = 0 ; i < packageSize ; i++) {
            setScanRootPackage(packageNames.get(i));
        }
    }

    public static int getSizeOfScannedPackages() {
        if(RegisterHandler.rootPackages == null) {
            return ROOT_PACKAGES_EMPTY;
        } else {
            return RegisterHandler.rootPackages.size();
        }
    }

    public static ArrayList<String> getAllRootPaths() {
        return (ArrayList<String>) RegisterHandler.rootPackages;
    }
}
