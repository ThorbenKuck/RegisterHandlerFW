package de.thorbenkuck.rhfw.register.pulling;

public class DataOutputPipePullStreamFinal<T> implements PullStream {

	private final T object;

	public DataOutputPipePullStreamFinal(T object) {
		this.object = object;
	}

	@SuppressWarnings ("unchecked")
	@Override
	public T get() {
		return (T) PullHandler.duplicate(object);
	}

}
