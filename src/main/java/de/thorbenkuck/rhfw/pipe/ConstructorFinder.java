package de.thorbenkuck.rhfw.pipe;

import de.thorbenkuck.rhfw.annotations.AutoResolve;
import de.thorbenkuck.rhfw.exceptions.NoSuitableConstructorException;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

class ConstructorFinder {

	private Class clazz;
	private List<Constructor> excluded = new ArrayList<>();

	ConstructorFinder(Class clazz) {
		this.clazz = clazz;
	}

	public void exclude(Constructor constructor) {
		excluded.add(constructor);
	}

	public Constructor<?> find() {
		for(Constructor<?> constructor : clazz.getConstructors()) {
			if(!excluded.contains(constructor) && constructor.getAnnotation(AutoResolve.class) != null) {
				return constructor;
			}
		}
		return fallBack();
	}

	/**
	 * Try to find a default-Constructor
	 * @return
	 */
	private Constructor fallBack() {
		for(Constructor constructor : clazz.getConstructors()) {
			if(constructor.getParameterTypes().length == 0) {
				return constructor;
			}
		}
		throw new NoSuitableConstructorException("No suitable Constructor found for Class: " + clazz + "\nIf the class does not have a default-constructor, at least one constructor must be marked with the Annotation AutoResolve");
	}
}
