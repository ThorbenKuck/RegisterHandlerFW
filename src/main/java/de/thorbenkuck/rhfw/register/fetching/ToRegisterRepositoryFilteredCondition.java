package de.thorbenkuck.rhfw.register.fetching;

import de.thorbenkuck.rhfw.pipe.DataOutputPipe;
import de.thorbenkuck.rhfw.register.ToRepositoryFilteredCondition;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class ToRegisterRepositoryFilteredCondition<T> extends FetchRepoHelper<T> implements ToRepositoryFilteredCondition<T> {

	private final DataOutputPipe dataOutputPipe;
	private final HashMap<Object, Object> registerInternals;
	private final Class<T> t;

	public ToRegisterRepositoryFilteredCondition(DataOutputPipe dataOutputPipe,
												 HashMap<Object, Object> registerInternals,
												 Class<T> t) {
		this.dataOutputPipe = dataOutputPipe;
		this.registerInternals = registerInternals;
		this.t = t;
		addPredicate(aClass -> aClass.getClass().equals(t));
	}

	@SuppressWarnings ("unchecked") // We check at runtime. This is a bit ugly, but hey
	@Override
	public T toRegisterAndGetFirst() {
		Optional<Object> optional = getFilteredStream().findFirst();
		if(optional.isPresent()) {
			T toReturn = (T) optional.get();
			setModuleInRegister(toReturn);
			return (T) duplicate(toReturn);
		} else {
			return null;
		}
	}

	@SuppressWarnings ("unchecked") // We check at runtime. This is a bit ugly, but hey
	@Override
	public List<T> toRegisterAndGetAll() {
		final List<T> toReturn = new ArrayList<>();
		getFilteredStream().forEach(o -> {
			toReturn.add((T) duplicate(o));
			setModuleInRegister(o);
		});
		return toReturn;
	}

	private void setModuleInRegister(Object o) {
		if(registerInternals.get(o.getClass()) == null) {
			registerInternals.putIfAbsent(o.getClass(), duplicate(o));
		}
	}

	@SuppressWarnings ("unchecked")
	@Override
	public void toRegister() {
		getFilteredStream().forEach(this::setModuleInRegister);
	}

	@SuppressWarnings ("unchecked") // We check at runtime. This is a bit ugly, but hey
	private Stream<Object> getFilteredStream() {
		return dataOutputPipe.getModules()
				.stream()
				.filter(o -> validate((T) o));
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
}
