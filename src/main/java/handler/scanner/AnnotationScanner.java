package handler.scanner;

import annotations.RegisterModule;

import java.util.ArrayList;
import java.util.List;

public class AnnotationScanner {

    private static List<Class<?>> annotadedClasses;


    private static PathScanner pathScanner;

    private static String rootPath;

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
        pathScanner = PathScanner.getInstance();
    }

    public List<Class<?>> getAllAnnotadedClasses() {
        if(rootPath == null) {
            rootPath = "test";
        }
        return getClassesWithAnnotatedType();
    }

    private List<Class<?>> getClassesWithAnnotatedType() {
        if(AnnotationScanner.classesWithAnnotatedType != null) {
            return AnnotationScanner.classesWithAnnotatedType;
        }
        System.out.println("$ Loading annotaded-classes for the first Time ... ");
        List<Class<?>> allAnnotadedClasses = new ArrayList<>();
        List<Class<?>> allClasses = pathScanner.find(rootPath);
        int numberClasses = allClasses.size();
        for(int i = 0 ; i < numberClasses ; i++) {
            if(allClasses.get(i).isAnnotationPresent(RegisterModule.class)) {
                allAnnotadedClasses.add(allClasses.get(i));
            }
        }

        AnnotationScanner.classesWithAnnotatedType = allAnnotadedClasses;
        return allAnnotadedClasses;
    }

}
