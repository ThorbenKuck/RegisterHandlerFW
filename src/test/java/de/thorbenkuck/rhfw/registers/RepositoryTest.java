package de.thorbenkuck.rhfw.registers;

import de.thorbenkuck.rhfw.interfaces.RegisterModuleInterface;
import de.thorbenkuck.rhfw.pipe.DataOutputPipe;
import de.thorbenkuck.rhfw.register.Register;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNull;

public class RepositoryTest {

	private Register register;
	private DataOutputPipe dataOutputPipe;

	@Before
	public void setup() {
		dataOutputPipe = new DataOutputPipe();
		dataOutputPipe.loadAnnotatedModules();
	}

	@Test
	public void testGetSpecificClassFetchedFromDataOutputPipe() {
		// Arrange
		register = new Register(dataOutputPipe);

		// Act
		TestClassOne toGetBack = new TestClassOne();
		dataOutputPipe.add(toGetBack);
		dataOutputPipe.add(new TestClassTwo());

		// Assert
		assertEquals(toGetBack, register.fetch().fromDataOutputPipe().ofClassType(TestClassOne.class).toRegisterAndGetFirst());
		assertNull(register.fetch().fromDataOutputPipe().ofClassType(TestClassThree.class).toRegisterAndGetFirst());
	}

	@Test
	public void testGetSpecificClassFetchedFromDataOutputPipeByInterface() {
		// Arrange
		register = new Register(dataOutputPipe);

		// Act
		TestClassThree toGetBack = new TestClassThree();
		dataOutputPipe.add(toGetBack);
		dataOutputPipe.add(new TestClassOne());
		dataOutputPipe.add(new TestClassTwo());

		// Assert
		assertEquals(toGetBack, register.fetch()
				.fromDataOutputPipe()
				.ofInterface(TestInterface.class)
				.toRegisterAndGetFirst());

		TestInterface testInterface = register.pullModule(TestInterface.class);

		assertNull(register.pullModule(TestClassThree.class));
		assertEquals(toGetBack, testInterface);

		register.push().toRegister(TestInterface.class, testInterface);
		assertNull(register.pullModule(TestClassThree.class));

		System.out.println(register.fetch()
				.fromDataOutputPipe()
				.ofInterface(RegisterModuleInterface.class)
				.toRegisterAndGetAll());
	}

	@Test
	public void testPushToRegister() {
		// Arrange
		dataOutputPipe = new DataOutputPipe();
		register = new Register(dataOutputPipe);

		// Act
		TestClassTwo toGetBack = new TestClassTwo();
		toGetBack.setS("Hallo");
		dataOutputPipe.add(toGetBack);
		dataOutputPipe.add(new TestClassOne());
		dataOutputPipe.add(new TestClassThree());
		register.fetch().fromDataOutputPipe().ofClassType(TestClassTwo.class).toRegister();

		// Assert
		TestClassTwo testClassTwo = register.pull().fromDataOutputPipe(TestClassTwo.class).toRegister().get();
		assertEquals("Hallo", testClassTwo.getS());
		testClassTwo.setS("Nicht-Hallo");
		register.push().toRegister(testClassTwo);
		testClassTwo = register.pull().object(TestClassTwo.class);
		assertEquals("Nicht-Hallo", testClassTwo.getS());
	}

}