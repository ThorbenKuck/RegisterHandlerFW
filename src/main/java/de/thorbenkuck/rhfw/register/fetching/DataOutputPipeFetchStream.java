package de.thorbenkuck.rhfw.register.fetching;

import de.thorbenkuck.rhfw.pipe.ObjectedModuleContainerList;
import de.thorbenkuck.rhfw.register.RepositoryCondition;
import de.thorbenkuck.rhfw.register.ResultMatcher;
import de.thorbenkuck.rhfw.register.ToRepositoryFilteredCondition;

import java.util.*;

public class DataOutputPipeFetchStream<T> implements RepositoryCondition<T, ToRepositoryFilteredCondition<T>> {

	private Queue<T> result = new LinkedList<>();
	private List<Object> dataOutputPipeModules;
	private List<Object> registerInternals;

	public DataOutputPipeFetchStream(List<Object> dataOutputPipeModules, List<Object> registerInternals) {
		this.dataOutputPipeModules = dataOutputPipeModules;
		this.registerInternals = registerInternals;
	}

	@Override
	public ToRepositoryFilteredCondition<T> ofClassType(Class clazz) {
		return null;
	}

	@Override
	public ToRepositoryFilteredCondition<T> where(ResultMatcher<Class<?>> resultMatcher) {
		return null;
	}

	@Override
	public ToRepositoryFilteredCondition<T> without(ResultMatcher<Class<?>> resultMatcher) {
		return null;
	}
}