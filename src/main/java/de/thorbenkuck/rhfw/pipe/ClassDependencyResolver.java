package de.thorbenkuck.rhfw.pipe;

import de.thorbenkuck.rhfw.annotations.RegisterModule;
import de.thorbenkuck.rhfw.exceptions.CriticalErrorException;
import de.thorbenkuck.rhfw.exceptions.NoSuitableConstructorException;
import de.thorbenkuck.rhfw.interfaces.RegisterModuleInterface;
import io.github.lukehutch.fastclasspathscanner.FastClasspathScanner;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

class ClassDependencyResolver implements DependencyResolver {

	private DataOutputPipe dataOutputPipe;
	private ClassFactory classFactory;
	private Set<Class> toCreate = new HashSet<>();
	private Set<Class> abounded = new HashSet<>();
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
			if (!toCreate.contains(aClass)) {
				if (aClass.getAnnotation(RegisterModule.class).included()) {
					toCreate.add(aClass);
				} else if (! abounded.contains(aClass)) {
					abounded.add(aClass);
					logger.debug("Class [" + aClass + "] disabled via Annotation \"" + RegisterModule.class + "\"");
				}
			}
		}).matchClassesImplementing(RegisterModuleInterface.class, implementingClass -> {
			if (!toCreate.contains(implementingClass)) {
				if (implementingClass.getAnnotation(RegisterModule.class) == null ||
						(implementingClass.getAnnotation(RegisterModule.class) != null && implementingClass.getAnnotation(RegisterModule.class).included())) {
					toCreate.add(implementingClass);
				} else if (! abounded.contains(implementingClass)) {
					// TODO: log
					abounded.add(implementingClass);
					logger.debug("Class [" + implementingClass + "] disabled via Annotation \"" + RegisterModule.class + "\"");
				}
			}
		}).scan();

		resolveIndependently();
	}

	private void resolveIndependently() {
		boolean continuing = true;

		while (continuing) {
			final boolean[] hit = { false };
			List<Class> toDelete = new ArrayList<>();
			toCreate.forEach(aClass -> {
				boolean succ = tryInstantiate(aClass);
				if(succ) {
					toDelete.add(aClass);
				}
				hit[0] |= succ;
			});
			cleanUp(toDelete);
			continuing = hit[0];
		}
		checkForError();
	}

	private void checkForError() {
		NoSuitableConstructorException noSuitableConstructorException = null;
		if(toCreate.size() > 0) {
			for(Class c : toCreate) {
				if(noSuitableConstructorException == null) {
					noSuitableConstructorException = new NoSuitableConstructorException("No suitable constructor found for Class: " + c);
				} else {
					noSuitableConstructorException = new NoSuitableConstructorException("No suitable constructor found for Class: " + c, noSuitableConstructorException);
				}
			}
			throw new NoSuitableConstructorException("Could not resolve dependencies", noSuitableConstructorException);
		}
	}

	private void cleanUp(List<Class> toDelete) {
		toCreate.removeAll(toDelete);
	}

	private boolean tryInstantiate(Class clazz) {
		try {
			Object o = classFactory.create(clazz);
			if (o != null) {
				dataOutputPipe.add(clazz.getName(), o);
				logger.debug("Included Class [" + clazz + "]");
				return true;
			}
			return false;
		} catch (IllegalAccessException | InstantiationException | InvocationTargetException e) {
			logger.debug("Skipped Class [" + clazz + "]");
			return false;
		} catch (CriticalErrorException e) {
			logger.debug("Skipped Class [" + clazz + "]");
			return false;
		}
	}
}
