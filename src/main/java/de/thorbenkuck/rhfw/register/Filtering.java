package de.thorbenkuck.rhfw.register;

interface Filtering<Result> {

	Result ofClassType(Class clazz);

	Result where(ResultMatcher<Class<?>> resultMatcher);

	Result without(ResultMatcher<Class<?>> resultMatcher);
}
