package de.thorbenkuck.rhfw.pipe;

import java.util.*;

/**
 * Eine DataContainerList wird genutzt um Objekte zu speichern
 */
public class ObjectedModuleContainerList<String, Object> {

    private Map<String, Object> objectedModuleHashMap;

    ObjectedModuleContainerList() {
        objectedModuleHashMap = Collections.synchronizedMap(new HashMap<String,Object>());
    }

    void addObjectedModule(String key, Object data) {
        objectedModuleHashMap.put(key, data);
    }

    Object getObjectedModule(String key) {
        return objectedModuleHashMap.get(key);
    }

    void updateObjectedModule(String key, Object data) {
        objectedModuleHashMap.put(key, data);
    }

    void removeObjectedModule(String key) {
        objectedModuleHashMap.remove(key);
    }

    boolean contains(String key) { return objectedModuleHashMap.containsKey(key); }

	public List<String> getKeys() {
		return new ArrayList<>(objectedModuleHashMap.keySet());
	}

	public Collection<Object> getValues() {
		return objectedModuleHashMap.values();
	}
}
