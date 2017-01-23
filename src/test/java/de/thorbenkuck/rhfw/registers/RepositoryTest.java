package de.thorbenkuck.rhfw.registers;

import de.thorbenkuck.rhfw.interfaces.RegisterModuleInterface;
import de.thorbenkuck.rhfw.pipe.DataOutputPipe;
import de.thorbenkuck.rhfw.register.Register;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNull;

public class RepositoryTest {

	private Register register;
	private DataOutputPipe dataOutputPipe;

	@Test
	public void testGetSpecificClassFetchedFromDataOutputPipe() {
		// Arrange
		dataOutputPipe = new DataOutputPipe();
		register = new Register(dataOutputPipe);

		// Act
		TestClassOne toGetBack = new TestClassOne();
		dataOutputPipe.add(toGetBack);
		dataOutputPipe.add(new TestClassTwo());

		// Assert
		assertEquals(toGetBack, register.fetch().fromDataOutputPipe().ofClassType(TestClassOne.class).getFirst());
		assertNull(register.fetch().fromDataOutputPipe().ofClassType(TestClassThree.class).getFirst());
	}

}

class TestClassOne implements RegisterModuleInterface {

}

class TestClassTwo implements RegisterModuleInterface {

}

class TestClassThree implements RegisterModuleInterface {

}