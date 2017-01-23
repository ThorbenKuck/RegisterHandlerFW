package de.thorbenkuck.rhfw.pipe;

import org.apache.logging.log4j.LogManager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class DataOutputPipe {

    private ObjectedModuleContainerList<String, Object> moduleContainerList = new ObjectedModuleContainerList<>();
    private Object identifier;
    private static final HashMap<Object, DataOutputPipe> instance = new HashMap<>();

    public DataOutputPipe() {
    	this("");
	}

    public DataOutputPipe(Object identifier) {
    	this.identifier = identifier;
    }

	@Deprecated
    public static DataOutputPipe getInstance() {
		instance.computeIfAbsent("", k -> new DataOutputPipe(""));
		LogManager.getLogger().warn("The use of DataOutputPipe#getInstance() is discouraged!");
        return instance.get("");
    }

    public static DataOutputPipe access() {
    	return access("main");
	}

    public static DataOutputPipe access(Object key) {
		instance.computeIfAbsent(key, k -> new DataOutputPipe(key));
		return instance.get(key);
	}

	public static boolean exists(Object key) {
    	return instance.get(key) != null;
	}

	public static boolean accessable(Object key) {
		// TODO
		return exists(key);
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
    }

    public void add(Object component) {
    	add(component.getClass().getName(), component);
	}


    /**
     * TODO Exception-Handling
     * @param name
     * @param type
     * @param <T>
     * @return
     */
    @Deprecated
    public <T> T getModule(String name, Class<T> type) {
        return getModule(name);
    }

    public <T> T getModule(String name) {
        Object o = moduleContainerList.getObjectedModule(name);
        return (T) o;
    }

    public Class<?> getType(String name) {
        return moduleContainerList.getObjectedModule(name).getClass();
    }

    public ArrayList<String> getAllKeys() {
        return new ArrayList<>(moduleContainerList.getKeys());
    }

    public boolean keyInDataOutputPipe(String key) {
        return moduleContainerList.getKeys().contains(key);
    }

    public void loadAnnotatedModules() {
    	LogManager.getLogger().info("Loading annotated classes for DataOutputPipe(" + identifier + ")");
        new ClassDependencyResolver(this).resolve();
    }

}
