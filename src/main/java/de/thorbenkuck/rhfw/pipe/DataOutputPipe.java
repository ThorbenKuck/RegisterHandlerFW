package de.thorbenkuck.rhfw.pipe;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;

public class DataOutputPipe {

    private ArrayList<String> dataKeyList = new ArrayList<>();
    private ObjectedModuleContainerList<String, Object> moduleContainerList = new ObjectedModuleContainerList<>();
    private static DataOutputPipe instance;

    private DataOutputPipe() {
    }

    public static DataOutputPipe getInstance() {
        if(instance == null) {
            instance = new DataOutputPipe();
        }
        return instance;
    }

    public Collection<Object> getModules() {
        return moduleContainerList.getValues();
    }

    public void add(String name, Object component) {
        if(moduleContainerList.contains(name)) {
            // TODO Exceptionhandling
            return;
        }
        moduleContainerList.addObjectedModule(name, component);
        dataKeyList.add(name);
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
        Object o = moduleContainerList.getObjectedModule(name);
        Class<T> type = (Class<T>) o.getClass();
        return getModule(name, type);
    }

    public Class<?> getType(String name) {
        return moduleContainerList.getObjectedModule(name).getClass();
    }

    public ArrayList<String> getAllKeys() {
        return dataKeyList;
    }

    public boolean keyInDataOutputPipe(String key) {
        return dataKeyList.contains(key);
    }

    public void loadAnnotatedModules() {
        new ClassDependencyResolver(this).resolve();
    }

}
