package de.thorbenkuck.rhfw.pipe;

import de.thorbenkuck.rhfw.exceptions.NoSuitableConstructorException;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ClassFactory {

	private Collection<Object> possibleParameters;

	ClassFactory(Collection<Object> possibleParameters) {
		this.possibleParameters = possibleParameters;
	}

	public Object create(Class clazz) throws IllegalAccessException, InvocationTargetException, InstantiationException {
		ConstructorFinder constructorFinder = new ConstructorFinder(clazz);
		Constructor constructor = constructorFinder.find();
		if(canResolveDependencies(constructor)) {
			return constructor.newInstance(getParametersAsArray(constructor.getParameterTypes()));
		} else {
			throw new NoSuitableConstructorException("Could not find suitable constructor for class [" + clazz + "]: Could not resolve dependencies\nGiven dependencies: " + possibleParameters);
		}
	}

	private boolean canResolveDependencies(Constructor constructor) {
		for(Class clazz : constructor.getParameterTypes())  {
			if(!parameterGiven(clazz)) {
				return false;
			}
		}
		return true;
	}

	private boolean parameterGiven(Class clazz) {
		for(Object o : possibleParameters) {
			if(o.getClass().equals(clazz)) {
				return true;
			}
		}
		return false;
	}

	private Object[] getParametersAsArray(Class[] parameterTypes) {
		return getSortedParameterList(parameterTypes).toArray();
	}

	public List<Object> getSortedParameterList(Class[] parameterTypes) {
		List<Object> objects = new ArrayList<>();

		// TODO schleife verbessern
		for(Class clazz : parameterTypes) {
			for(Object o : possibleParameters) {
				if(clazz.equals(o.getClass())) {
					objects.add(o);
				}
			}
		}

		return objects;
	}
}
