package example;

import de.thorbenkuck.rhfw.pipe.DataOutputPipe;
import de.thorbenkuck.rhfw.register.handler.RegisterHandler;
import de.thorbenkuck.rhfw.register.Register;
import example.tester.C;
import example.tester.IC;
import example.tester.Tester;
import example.tester.Tester2;
import org.apache.logging.log4j.core.config.ConfigurationFactory;


public class Main {
    public static void main(String[] args) {
    	setup();

        Register register = RegisterHandler.pullAndGetNewRegister(new TestTemplate());

        prepareTest(register);

        Tester tester = new Tester();
        Tester2 tester2 = new Tester2();

        for(int i = 0 ; i < 4 ; i++) {
            tester.run();
            tester2.run();
        }
    }

    private static void prepareTest(Register register) {
		C c = register.fetch().fromDataOutputPipe().ofClassType(C.class).toRegisterAndGetFirst();

		c.higher();
		c.howMuch();

		IC ic = register.fetch()
				.fromDataOutputPipe()
				.ofInterface(IC.class)
				.toRegisterAndGetFirst();

		ic.howMuch();
		ic.higher();
		ic.higher();
		ic.howMuch();

		register.push().toRegister(IC.class, ic);

		ic = register.pull().object(IC.class);
		ic.howMuch();

		System.out.println("#########");
	}

	private static void setup() {
		ConfigurationFactory.setConfigurationFactory(new CustomLog4J2ConfigurationFactory());

		// This is alternative.
		// It will not help the test, neither is it necessary for the test to work.
		// The following line simply shows the multiton-behavior for the DataOutputPipe
		DataOutputPipe.access(Register.class).loadAnnotatedModules();
	}
}