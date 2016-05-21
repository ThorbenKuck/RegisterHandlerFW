package handler;

import annotations.RegisterModule;
import handler.scanner.PathScanner;

import java.util.ArrayList;
import java.util.List;

public class AnnotationScanner {

    private static ArrayList<String> rootPath;

    private static AnnotationScanner ourInstance;
    private static List<Class<?>> classesWithAnnotatedType;


    public static AnnotationScanner getInstance() {
        if(ourInstance == null) {
            ourInstance = new AnnotationScanner();
        }
        return ourInstance;
    }

    private AnnotationScanner() {
        System.out.println("$ Initialing AnnotationScanner for the first time ... ");
    }

    public synchronized List<Class<?>> getAllAnnotadedClasses() {
        if(rootPath == null) {
            if(RegisterHandler.getSizeOfScannedPackages() == RegisterHandler.ROOT_PACKAGES_EMPTY) {
                System.out.println("\n---#--->[RH-WARNING]<---#---\n");
                System.out.println("You have NOT defined a root path, for the path-scan!");
                System.out.println("Let me try to scan the whole Project for you!");
                System.out.println("This may take a WHOLE lot of time!\n");
                System.out.println("Note that i don't know, which prefix you use! So you may encounter problems!");
                System.out.println("\n---#--->[STATEMENT-END]<---#---\n");
                getAllPackageNames();
            } else {
                rootPath = RegisterHandler.getAllRootPaths();
            }
        }
        return getClassesWithAnnotatedType();
    }

    private void getAllPackageNames() {
        rootPath = new ArrayList<>();
        for(Package p : Package.getPackages()) {
            if(!(p.getName().startsWith("sun") || p.getName().startsWith("java") || p.getName().startsWith("com") || p.getName().startsWith("org"))) {
                rootPath.add(p.getName());
            }
        }
    }

    private List<Class<?>> getClassesWithAnnotatedType() {

        if(classesWithAnnotatedType != null) {
            return AnnotationScanner.classesWithAnnotatedType;
        }

        System.out.print("$ Loading annotaded-classes for the first Time ... ");

        classesWithAnnotatedType = new ArrayList<>();
        for(String s : rootPath) {

            PathScanner p = new PathScanner(s);
            List<Class<?>> allClasses = p.find();
            int numberClasses = allClasses.size();

            for(int i = 0 ; i < numberClasses ; i++) {
                if(allClasses.get(i).isAnnotationPresent(RegisterModule.class)) {
                    AnnotationScanner.classesWithAnnotatedType.add(allClasses.get(i));
                }
            }
        }

        System.out.println("found " + AnnotationScanner.classesWithAnnotatedType.size() + " classes with annotation");

        return AnnotationScanner.classesWithAnnotatedType;
    }

}
