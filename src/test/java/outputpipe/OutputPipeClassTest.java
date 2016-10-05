package outputpipe;

import de.thorbenkuck.rhfw.pipe.DataOutputPipe;
import org.junit.BeforeClass;

import static junit.framework.TestCase.assertNotNull;

public class OutputPipeClassTest {

    private static DataOutputPipe dataOutputPipe;

    @BeforeClass
    public static void setup() {
        DataOutputPipe.loadAnnotatedModules();
        dataOutputPipe = DataOutputPipe.getInstance();
    }

    @org.junit.Test
    public void existsTest() {
        assertNotNull(dataOutputPipe);
    }

    @org.junit.Test
    public void testGetClassTestFromPipe() {
        Test test = dataOutputPipe.getModule(Test.class.getName());
    }

}