package de.thorbenkuck.rhfw.pipe;

import de.thorbenkuck.rhfw.exceptions.NoSuitableConstructorException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
		Constructor constructor = getConstructor(clazz);
		if(canResolveDependencies(constructor)) {
			return constructor.newInstance(getParametersAsArray(constructor.getParameterTypes()));
		} else {
			throw new NoSuitableConstructorException("Could not find suitable constructor for class [" + clazz + "]: Could not resolve dependencies\nGiven dependencies: " + possibleParameters);
		}
	}

	public Class<?>[] getRequiredDependencies(Class clazz) {
		return getConstructor(clazz).getParameterTypes();
	}

	private Constructor getConstructor(Class clazz) {
		ConstructorFinder constructorFinder = new ConstructorFinder(clazz);
		return constructorFinder.find();
	}

	private boolean canResolveDependencies(Constructor constructor) {
		if(constructor.getParameterTypes().length == 0) {
			return true;
		}
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
