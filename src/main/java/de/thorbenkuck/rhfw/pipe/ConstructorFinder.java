package de.thorbenkuck.rhfw.pipe;

import de.thorbenkuck.rhfw.annotations.AutoResolve;
import de.thorbenkuck.rhfw.exceptions.CriticalErrorException;
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
		Constructor<?> toReturn = null;
		for(Constructor<?> constructor : clazz.getConstructors()) {
			if(!excluded.contains(constructor) && constructor.getAnnotation(AutoResolve.class) != null) {
				if(toReturn == null) {
					toReturn = constructor;
				} else {
					throw new NoSuitableConstructorException("Could not determine constructor to use for " + clazz
							+ "\nCou cannot mark more than one Constructor to be auto resolved!\nYou have to:\n\n"
							+ "1) Create exactly ONE Constructor with the @AutoResolve annotation or\n\n"
							+ "2) Have at least ONE default Constructor\n\nStackTrace:");
				}
			}
		}
		return toReturn == null ? fallBack() : toReturn;
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
