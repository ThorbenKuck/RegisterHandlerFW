package de.thorbenkuck.rhfw.outputpipe;

import de.thorbenkuck.rhfw.pipe.DataOutputPipe;
import org.junit.BeforeClass;
import org.junit.Test;

import static junit.framework.TestCase.assertNotNull;

public class OutputPipeClassTest {

    private static DataOutputPipe dataOutputPipe;

    @BeforeClass
    public static void setup() {
        dataOutputPipe = DataOutputPipe.getInstance();
        dataOutputPipe.loadAnnotatedModules();
    }

    @Test
    public void existsTest() {
        assertNotNull(dataOutputPipe);
    }

    @Test
    public void testGetClassTestFromPipe() {
        Tester tester = dataOutputPipe.getModule(Tester.class.getName());
    }

}