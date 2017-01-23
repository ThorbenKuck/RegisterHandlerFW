package de.thorbenkuck.rhfw.register.fetching;

import de.thorbenkuck.rhfw.pipe.ObjectedModuleContainerList;

import java.util.*;

public class DataOutputPipeFetchStream<T> {

	private Queue<T> result = new LinkedList<>();
	private ObjectedModuleContainerList<String, Object> dataOutputPipeModules;
	private HashMap<String, Object> registerInternals;

	private DataOutputPipeFetchStream(ObjectedModuleContainerList<String, Object> dataOutputPipeModules, HashMap<String, Object> registerInternals, Queue<T> result) {
		this(dataOutputPipeModules, registerInternals);
		this.result = result;
	}

	public DataOutputPipeFetchStream(ObjectedModuleContainerList<String, Object> dataOutputPipeModules, HashMap<String, Object> registerInternals) {
		this.dataOutputPipeModules = dataOutputPipeModules;
		this.registerInternals = registerInternals;
	}

	public DataOutputPipeFetchStream ofClassType(Class<? extends T> clazz) {
		dataOutputPipeModules.getValues().stream().filter(o -> o.getClass().equals(clazz)).forEach((e) -> result.add((T) e));
		return new DataOutputPipeFetchStream<>(dataOutputPipeModules, registerInternals, result);
	}

	public void toRegister() {
		result.forEach(o -> registerInternals.put(o.getClass().getName(), o));
	}

	public List<T> getAny() {
		return new ArrayList<>(result);
	}

	public T getFirst() {
		return result.poll();
	}
}
