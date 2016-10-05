package de.thorbenkuck.rhfw.pipe;

import de.thorbenkuck.rhfw.handler.AnnotationScanner;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

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
        T toreturn = null;
        Class<?> classToInstance = moduleContainerList.getObjectedModule(name).getClass();
        String classNameToInstance = classToInstance.getName();
        try {
            Class<?> clazz = Class.forName(classNameToInstance);
            Constructor<?> ctor = clazz.getConstructor();
            Object object = ctor.newInstance();
            toreturn = type.cast(object);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return toreturn;
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
        List<Class<?>> allModules = AnnotationScanner.getInstance().getAllAnnotadedClasses();
        int numberAllModules = allModules.size();

        /* Warum nicht foreach?
         *
         * Es gibt mehrere Stellen, an denen die Listen iterriert werden. Manchmal 2 oder mehr gleichzeitig.
         * Wenn an jeder Stelle eine foreach schleife verwendet werden würde, würder der Listeninterne Interator ziemlich durcheinander kommen.
         * Deswegen nutzen wir zum Iterieren der Listen besser normale for-schleifen.
         */
        for(int i = 0 ; i < numberAllModules ; i++) {
            try {
                DataOutputPipe.add(allModules.get(i).getName(), allModules.get(i).newInstance());
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

}
