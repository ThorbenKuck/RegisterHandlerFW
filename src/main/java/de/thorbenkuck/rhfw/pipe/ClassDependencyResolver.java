package de.thorbenkuck.rhfw.pipe;

import de.thorbenkuck.rhfw.annotations.DataModule;
import de.thorbenkuck.rhfw.duplicate.Duplicator;
import de.thorbenkuck.rhfw.exceptions.CriticalErrorException;
import de.thorbenkuck.rhfw.exceptions.NoSuitableConstructorException;
import de.thorbenkuck.rhfw.interfaces.RegisterModuleInterface;
import io.github.lukehutch.fastclasspathscanner.FastClasspathScanner;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

class ClassDependencyResolver implements DependencyResolver {

	private DataOutputPipe dataOutputPipe;
	private ClassFactory classFactory;
	private Set<Class> toCreate = new HashSet<>();
	private Set<Class> abounded = new HashSet<>();
	private Logger logger = LogManager.getLogger(getClass());
	private Duplicator duplicator;

	public ClassDependencyResolver(DataOutputPipe dataOutputPipe) {
		this.dataOutputPipe = dataOutputPipe;
		classFactory = new ClassFactory(dataOutputPipe.getModules());
		duplicator = new Duplicator(classFactory);
	}

	@Override
	public void resolve() {
		FastClasspathScanner fastClasspathScanner = new FastClasspathScanner();
		logger.info("Started scanning ..");
		fastClasspathScanner.matchClassesWithAnnotation(DataModule.class, aClass -> {
			if (! toCreate.contains(aClass)) {
				if (aClass.getAnnotation(DataModule.class).included()) {
					logger.debug("Found class: " + aClass);
					toCreate.add(aClass);
				} else if (! abounded.contains(aClass)) {
					abounded.add(aClass);
					logger.debug("Class [" + aClass + "] disabled via Annotation \"" + DataModule.class + "\"");
				}
			}
		}).matchClassesImplementing(RegisterModuleInterface.class, implementingClass -> {
			if (! toCreate.contains(implementingClass)) {
				if (implementingClass.getAnnotation(DataModule.class) == null ||
						(implementingClass.getAnnotation(DataModule.class) != null && implementingClass.getAnnotation(DataModule.class).included())) {
					logger.debug("Found class: " + implementingClass);
					toCreate.add(implementingClass);
				} else if (! abounded.contains(implementingClass)) {
					// TODO: log
					abounded.add(implementingClass);
					logger.debug("Class [" + implementingClass + "] disabled via Annotation \"" + DataModule.class + "\"");
				}
			}
		}).scan();
		logger.debug("Finished Scanning");
		resolveIndependently();
	}

	private void resolveIndependently() {
		boolean continuing = true;

		while (continuing) {
			final boolean[] hit = { false };
			List<Class> toDelete = new ArrayList<>();
			toCreate.forEach(aClass -> {
				boolean succ = tryInstantiate(aClass);
				if (succ) {
					toDelete.add(aClass);
				}
				hit[0] |= succ;
			});
			cleanUp(toDelete);
			continuing = hit[0];
		}
		checkForError();
	}

	private boolean tryInstantiate(Class clazz) {
		try {
			Object o = classFactory.create(clazz);
			dataOutputPipe.add(clazz.getName(), duplicator.duplicate(o));
			logger.debug("Included Class [" + clazz + "]");
			return true;
		} catch (IllegalAccessException | InstantiationException | InvocationTargetException | CriticalErrorException e) {
			return false;
		}
	}

	private void cleanUp(List<Class> toDelete) {
		toCreate.removeAll(toDelete);
	}

	private void checkForError() {
		if (toCreate.size() > 0) {
			NoSuitableConstructorException noSuitableConstructorException = null;
			for (Class c : toCreate) {
				if (noSuitableConstructorException == null) {
					noSuitableConstructorException = new NoSuitableConstructorException("Error while resolving dependencies!\nNo suitable constructor found for Class: " + c
							+ "\nRequired Dependencies: " + Arrays.toString(classFactory.getRequiredDependencies(c))
							+ "\nGiven Dependencies: " + getGivenDependenciesNames());
				} else {
					noSuitableConstructorException = new NoSuitableConstructorException("Error while resolving dependencies!\nNo suitable constructor found for Class: " + c
							+ "\nRequired Dependencies: " + Arrays.toString(classFactory.getRequiredDependencies(c))
							+ "\nGiven Dependencies: " + getGivenDependenciesNames(), noSuitableConstructorException);
				}
			}
			throw new NoSuitableConstructorException("Could not resolve dependencies", noSuitableConstructorException);
		}
	}

	private String getGivenDependenciesNames() {
		final StringJoiner stringJoiner = new StringJoiner(", ", "[", "]");
		dataOutputPipe.getModules()
				.forEach(o -> stringJoiner.add(o.getClass().toString()));
		return stringJoiner.toString();
	}
}
