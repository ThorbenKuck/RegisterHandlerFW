package de.thorbenkuck.rhfw.register;

public interface ToRepositoryFilteredCondition<T> extends FilteredRepositoryCondition<T> {

	void toRegister();

}
