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

	public Constructor find() {
		for(Constructor constructor : clazz.getConstructors()) {
			if(!excluded.contains(constructor) && constructor.getAnnotation(AutoResolve.class) != null) {
				return constructor;
			}
		}
		return fallBack();
	}

	private Constructor fallBack() {
		throw new NoSuitableConstructorException("No suitable Constructor find for Class: " + clazz);
	}
}
