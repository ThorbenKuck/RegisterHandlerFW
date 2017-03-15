package de.thorbenkuck.rhfw.register.fetching;

import de.thorbenkuck.rhfw.duplicate.Duplicator;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

abstract class FetchRepoHelper<T> {

	private final List<Predicate<T>> predicateList = new ArrayList<>();

	protected void addPredicate(Predicate<T> predicate) {
		predicateList.add(predicate);
	}

	protected boolean validate(T t) {
		for(Predicate<T> predicate : predicateList) {
			if(!predicate.test(t)) {
				return false;
			}
		}
		return true;
	}

	protected Object duplicate(Object object) {
		return Duplicator.tryAccess().duplicate(object);
	}
}
