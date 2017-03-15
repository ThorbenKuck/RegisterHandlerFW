package de.thorbenkuck.rhfw.register.fetching;

import de.thorbenkuck.rhfw.pipe.DataOutputPipe;
import de.thorbenkuck.rhfw.register.ToRepositoryFilteredCondition;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class InterfaceToRegisterRepositoryFilteredCondition<T> extends FetchRepoHelper<T> implements ToRepositoryFilteredCondition<T> {

	private final Class<T> interfaceType;
	private final DataOutputPipe dataOutputPipe;
	private final HashMap<Object, Object> registerInternals;

	public InterfaceToRegisterRepositoryFilteredCondition(DataOutputPipe dataOutputPipe,
														  HashMap<Object, Object> registerInternals,
														  Class<T> interfaceType) {
		this.interfaceType = interfaceType;
		addPredicate(this::containsInterface);
		this.dataOutputPipe = dataOutputPipe;
		this.registerInternals = registerInternals;
	}

	@SuppressWarnings ("unchecked")
	@Override
	public T toRegisterAndGetFirst() {
		Optional<Object> first = getFilteredStream().findFirst();
		if(first.isPresent()) {
			T toReturn = (T) first.get();
			addToRegister(toReturn);
			return (T) duplicate(toReturn);
		} else {
			return null;
		}
	}

	private void addToRegister(Object o) {
		registerInternals.putIfAbsent(interfaceType, o);
	}

	@SuppressWarnings ("unchecked")
	@Override
	public List<T> toRegisterAndGetAll() {
		final List<T> toReturn = new ArrayList<>();
		getFilteredStream().forEach(o -> {
			toReturn.add((T) duplicate(o));
			addToRegister(o);
		});
		return toReturn;
	}

	@Override
	public ToRepositoryFilteredCondition<T> where(Predicate<T> resultMatcher) {
		addPredicate(resultMatcher);
		return this;
	}

	@Override
	public ToRepositoryFilteredCondition<T> without(Predicate<T> resultMatcher) {
		addPredicate(resultMatcher);
		return this;
	}

	@Override
	public void toRegister() {
		getFilteredStream().forEach(this::addToRegister);
	}

	private boolean containsInterface(Object o) {
		return Stream.of(o.getClass().getInterfaces())
				.anyMatch(aClass -> aClass.equals(interfaceType));
	}

	@SuppressWarnings ("unchecked") // We check at runtime. This is a bit ugly, but hey
	private Stream<Object> getFilteredStream() {
		return dataOutputPipe.getModules()
				.stream()
				.filter(o -> validate((T) o));
	}
}
