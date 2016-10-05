package de.thorbenkuck.rhfw.pipe;

import de.thorbenkuck.rhfw.annotations.RegisterModule;
import io.github.lukehutch.fastclasspathscanner.FastClasspathScanner;
import io.github.lukehutch.fastclasspathscanner.scanner.ScanResult;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class DataOutputPipe {

    private static ArrayList<String> dataKeyList = new ArrayList<>();

    private static ObjectedModuleContainerList<String, Object> moduleContainerList = new ObjectedModuleContainerList<>();

    private static DataOutputPipe instance;

    private DataOutputPipe() {

    }

    public static DataOutputPipe getInstance() {
        if(instance == null) {
            DataOutputPipe.loadAnnotatedModules();
            instance = new DataOutputPipe();
        }
        return instance;
    }

    public static void add(String name, Object component) {
        System.out.println();
        if(moduleContainerList.contains(name)) {
            // TODO Exceptionhandling
            return;
        }
        DataOutputPipe.moduleContainerList.addObjectedModule(name, component);
        DataOutputPipe.dataKeyList.add(name);
    }


    /**
     * TODO Exception-Handling
     * @param name
     * @param type
     * @param <T>
     * @return
     */
    public <T> T getModule(String name, Class<T> type) {
        T toReturn = null;
        Class<?> classToInstance = moduleContainerList.getObjectedModule(name).getClass();
        String classNameToInstance = classToInstance.getName();
        try {
            Class<?> clazz = Class.forName(classNameToInstance);
            Constructor<?> ctor = clazz.getConstructor();
            Object object = ctor.newInstance();
            toReturn = type.cast(object);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | ClassNotFoundException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        return toReturn;
    }

    public <T> T getModule(String name) {
        Object o = DataOutputPipe.moduleContainerList.getObjectedModule(name);
        Class<T> type = (Class<T>) o.getClass();
        return getModule(name, type);
    }

    public Class<?> getType(String name) {
        return DataOutputPipe.moduleContainerList.getObjectedModule(name).getClass();
    }

    public ArrayList<String> getAllKeys() {
        return DataOutputPipe.dataKeyList;
    }

    public boolean keyInDataOutputPipe(String key) {
        return DataOutputPipe.dataKeyList.contains(key);
    }

    public static void loadAnnotatedModules() {
        FastClasspathScanner fastClasspathScanner = new FastClasspathScanner();
        fastClasspathScanner.matchClassesWithAnnotation(RegisterModule.class, aClass -> {});

        ScanResult scanResult = fastClasspathScanner.scan();
        List<String> names = scanResult.getNamesOfClassesWithAnnotation(RegisterModule.class);
        List<Class<?>> allModules = names.stream().map((Function<String, Class<?>>) DataOutputPipe::instantiateClass).collect(Collectors.toList());

        int numberAllModules = allModules.size();

        /* Warum nicht foreach?
         *
         * Es gibt mehrere Stellen, an denen die Listen iterriert werden. Manchmal 2 oder mehr gleichzeitig.
         * Wenn an jeder Stelle eine foreach schleife verwendet werden würde, würder der Listeninterne Interator ziemlich durcheinander kommen.
         * Deswegen nutzen wir zum Iterieren der Listen besser normale for-schleifen.
         */
        for (Class<?> allModule : allModules) {
            try {
                DataOutputPipe.add(allModule.getName(), allModule.newInstance());
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * TODO Exception-Handling
     * @param className
     * @return
     */
    private static Class<?> instantiateClass(String className) {
        try {
            return Class.forName(className);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

}
