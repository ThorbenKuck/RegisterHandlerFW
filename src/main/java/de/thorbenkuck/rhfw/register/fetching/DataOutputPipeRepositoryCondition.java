package de.thorbenkuck.rhfw.register.fetching;

import de.thorbenkuck.rhfw.pipe.DataOutputPipe;
import de.thorbenkuck.rhfw.register.Register;
import de.thorbenkuck.rhfw.register.RepositoryCondition;
import de.thorbenkuck.rhfw.register.ResultMatcher;
import de.thorbenkuck.rhfw.register.ToRepositoryFilteredCondition;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class DataOutputPipeRepositoryCondition implements RepositoryCondition {

	private DataOutputPipe dataOutputPipe;
	private HashMap<Object, Object> registerInternals;

	public DataOutputPipeRepositoryCondition(DataOutputPipe dataOutputPipe, HashMap<Object, Object> registerInternals) {
		this.dataOutputPipe = dataOutputPipe;
		this.registerInternals = registerInternals;
	}

	@Override
	public <T> ToRepositoryFilteredCondition<T> ofClassType(Class<T> clazz) {
		return new ToRegisterRepositoryFilteredCondition<>(dataOutputPipe, registerInternals, clazz);
	}

	@Override
	public <T> ToRepositoryFilteredCondition<T> ofInterface(Class<T> clazz) {
		return new InterfaceToRegisterRepositoryFilteredCondition<>(dataOutputPipe, registerInternals, clazz);
	}

	@Override
	public ToRepositoryFilteredCondition<Object> any() {
		return new ToRegisterRepositoryFilteredCondition<>(dataOutputPipe, registerInternals, Object.class);
	}
}