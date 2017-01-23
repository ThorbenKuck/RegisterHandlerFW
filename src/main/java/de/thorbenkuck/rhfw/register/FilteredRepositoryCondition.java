package de.thorbenkuck.rhfw.register;

import java.util.List;

interface FilteredRepositoryCondition<T> {

	T getFirst();

	List<T> getAny();
}
