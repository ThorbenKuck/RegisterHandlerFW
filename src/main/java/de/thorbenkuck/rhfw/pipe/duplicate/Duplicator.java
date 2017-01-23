package de.thorbenkuck.rhfw.pipe.duplicate;

import de.thorbenkuck.rhfw.pipe.ClassFactory;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;

public class Duplicator {

	private ClassFactory classFactory;

	public Duplicator(ClassFactory classFactory) {
		this.classFactory = classFactory;
	}

	public Object duplicate(Object object) {
		Object clone = null;
		if(isSerializable(object)) {
			clone = new DeepCloneDuplicator().cloneSerializable(object);
			if(clone != null) {
				return clone;
			}
		} else {
			try {
				classFactory.create(object.getClass());
			} catch (IllegalAccessException | InvocationTargetException | InstantiationException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	private boolean isSerializable(Object o) {
		for(Class c : o.getClass().getInterfaces()) {
			if(c.equals(Serializable.class)) {
				return true;
			}
		}
		return false;
	}

}
