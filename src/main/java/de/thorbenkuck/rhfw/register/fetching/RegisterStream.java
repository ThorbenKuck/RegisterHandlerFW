package de.thorbenkuck.rhfw.register.fetching;

import de.thorbenkuck.rhfw.register.ResultMatcher;

class RegisterStream<T> {

	private T t;

	void findFirst() {

	}

	void getAll() {

	}

	RegisterStream where(ResultMatcher<Object> resultMatcher) {
		return this;
	}
}
