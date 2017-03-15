package de.thorbenkuck.rhfw.register;

interface Filtering {

	<T> ToRepositoryFilteredCondition<T> ofClassType(Class<T> clazz);

	<T> ToRepositoryFilteredCondition<T> ofInterface(Class<T> clazz);

	ToRepositoryFilteredCondition<Object> any();
}
