package de.thorbenkuck.rhfw.register;

import java.util.List;
import java.util.function.Predicate;

interface FilteredRepositoryCondition<T> {

	T toRegisterAndGetFirst();

	List<T> toRegisterAndGetAll();

	void toRegister();

	FilteredRepositoryCondition<T> where(Predicate<T> resultMatcher);

	FilteredRepositoryCondition<T> without(Predicate<T> resultMatcher);
}
