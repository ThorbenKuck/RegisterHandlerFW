package de.thorbenkuck.rhfw.duplicate;

import de.thorbenkuck.rhfw.pipe.ClassFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.stream.Stream;

public class Duplicator implements IDuplicator {

	private static ClassFactory lastUsedClassFactory;
	private final Logger logger = LogManager.getLogger();
	private ClassFactory classFactory;

	public Duplicator(ClassFactory classFactory) {
		this.classFactory = classFactory;
		lastUsedClassFactory = classFactory;
	}

	private Duplicator() {
		classFactory = lastUsedClassFactory;
	}

	public static IDuplicator tryAccess() {
		if(lastUsedClassFactory == null) {
			throw new IllegalAccessError("No ClassFactory set!");
		}
		return new Duplicator();
	}

	@Override
	public Object duplicate(Object object) {
		Object clone = null;
		if(isSerializable(object)) {
			clone = new DeepCloneDuplicator().cloneSerializable(object);
			if(clone != null) {
				return clone;
			}
		} else {
			logger.warn("Deep-Clone is NOT supported for " + object.getClass() + "!\nThis is MANDATORY for production use!");
			try {
				return classFactory.create(object.getClass());
			} catch (IllegalAccessException | InvocationTargetException | InstantiationException e) {
				logger.catching(e);
			}
		}
		return null;
	}

	private boolean isSerializable(Object o) {
		return testInterfacesForSerializable(o.getClass().getInterfaces());
	}

	private boolean testInterfacesForSerializable(Class[] interfaces) {
		return Stream.of(interfaces)
				.filter(Class::isInterface)
				.anyMatch(this::checkInterfaceForSerializable);
	}

	private boolean checkInterfaceForSerializable(Class clazz) {
		if(clazz.equals(Serializable.class)) {
			return true;
		} else if(clazz.isInterface()) {
			return testInterfacesForSerializable(clazz.getInterfaces());
		} else if(clazz.getInterfaces().length > 0) {
			return testInterfacesForSerializable(clazz.getInterfaces());
		} else {
			return false;
		}
	}

}
