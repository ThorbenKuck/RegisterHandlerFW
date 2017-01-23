package de.thorbenkuck.rhfw.pipe;

import de.thorbenkuck.rhfw.annotations.RegisterModule;
import de.thorbenkuck.rhfw.interfaces.RegisterModuleInterface;
import io.github.lukehutch.fastclasspathscanner.FastClasspathScanner;

import java.lang.reflect.InvocationTargetException;
import java.util.HashSet;
import java.util.Set;

class ClassDependencyResolver implements DependencyResolver {

	private DataOutputPipe dataOutputPipe;
	private ClassFactory classFactory;
	private Set<Class> toCreate = new HashSet<>();

	public ClassDependencyResolver(DataOutputPipe dataOutputPipe) {
		this.dataOutputPipe = dataOutputPipe;
		classFactory = new ClassFactory(dataOutputPipe.getModules());
	}

	@Override
	public void resolve() {
		FastClasspathScanner fastClasspathScanner = new FastClasspathScanner();
		System.out.println("scanning .. ");
		fastClasspathScanner.matchClassesWithAnnotation(RegisterModule.class, aClass -> {
			if(aClass.getAnnotation(RegisterModule.class).included()) {
				toCreate.add(aClass);
				System.out.println("Found class to include: " + aClass);
			} else {
				// TODO: log
				System.out.println("disabled class : " + aClass);
			}
		}).matchClassesImplementing(RegisterModuleInterface.class, implementingClass -> {
			if(implementingClass.getAnnotation(RegisterModule.class) == null ||
					(implementingClass.getAnnotation(RegisterModule.class) != null && implementingClass.getAnnotation(RegisterModule.class).included())) {
				toCreate.add(implementingClass);
				System.out.println("Found class to include: " + implementingClass);
			} else {
				// TODO: log
				System.out.println("disabled class : " + implementingClass);
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
				System.out.println("included class: " + clazz);
				return true;
			}
			return false;
		} catch (IllegalAccessException | InvocationTargetException | InstantiationException e) {
			return false;
		}
	}
}
