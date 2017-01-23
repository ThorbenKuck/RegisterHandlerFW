package de.thorbenkuck.rhfw.pipe;

import de.thorbenkuck.rhfw.annotations.RegisterModule;
import de.thorbenkuck.rhfw.interfaces.RegisterModuleInterface;
import io.github.lukehutch.fastclasspathscanner.FastClasspathScanner;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.InvocationTargetException;
import java.util.HashSet;
import java.util.Set;

class ClassDependencyResolver implements DependencyResolver {

	private DataOutputPipe dataOutputPipe;
	private ClassFactory classFactory;
	private Set<Class> toCreate = new HashSet<>();
	private Logger logger = LogManager.getLogger(getClass());

	public ClassDependencyResolver(DataOutputPipe dataOutputPipe) {
		this.dataOutputPipe = dataOutputPipe;
		classFactory = new ClassFactory(dataOutputPipe.getModules());
	}

	@Override
	public void resolve() {
		FastClasspathScanner fastClasspathScanner = new FastClasspathScanner();
		logger.info("Started scanning ..");
		fastClasspathScanner.matchClassesWithAnnotation(RegisterModule.class, aClass -> {
			if(aClass.getAnnotation(RegisterModule.class).included()) {
				toCreate.add(aClass);
				logger.debug("Included class [" + aClass + "]");
			} else {
				logger.debug("Class [" + aClass + "] disabled via Annotation \"" + RegisterModule.class + "\"");
			}
		}).matchClassesImplementing(RegisterModuleInterface.class, implementingClass -> {
			if(implementingClass.getAnnotation(RegisterModule.class) == null ||
					(implementingClass.getAnnotation(RegisterModule.class) != null && implementingClass.getAnnotation(RegisterModule.class).included())) {
				toCreate.add(implementingClass);
				logger.debug("Included Class [" + implementingClass + "]");
			} else {
				// TODO: log
				logger.debug("Class [" + implementingClass + "] disabled via Annotation \"" + RegisterModule.class + "\"");
			}
		}).scan();

		resolveIndependently();
	}

	private void resolveIndependently() {
		boolean continuing = true;

		while(continuing) {
			final boolean[] hit = { false };
			toCreate.forEach(aClass -> hit[0] &= tryInstantiate(aClass));
			continuing = hit[0];
		}
	}

	private boolean tryInstantiate(Class clazz)  {
		try {
			Object o = classFactory.create(clazz);
			if(o != null) {
				dataOutputPipe.add(clazz.getName(), o);
				return true;
			}
			return false;
		} catch (IllegalAccessException | InvocationTargetException | InstantiationException e) {
			return false;
		}
	}
}
