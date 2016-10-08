package de.thorbenkuck.rhfw.registers;

import de.thorbenkuck.rhfw.register.RegisterID;
import de.thorbenkuck.rhfw.register.handler.RegisterHandler;
import de.thorbenkuck.rhfw.register.Register;
import de.thorbenkuck.rhfw.pipe.DataOutputPipe;
import org.junit.Test;

import static junit.framework.TestCase.*;

public class RegisterClassTest {

    private static String legereID = "example";

    private static int testInteger = 10;

    @Test
    public void testGetRegister() {
        Register register = RegisterHandler.pullAndGetNewRegister();
        assertNotNull(register);
    }

    @Test
    public void testGetRegisterById() {
        Register register1 = RegisterHandler.pullAndGetNewRegister();
        Register register2 = RegisterHandler.getRegisterForId(register1.getRegisterId());
        assertEquals(register1, register2);
    }

    @Test
    public void testGetBoundRegister() {
        Register register1 = RegisterHandler.pullAndGetNewRegister();
        RegisterHandler.bindRegister(legereID, register1.getRegisterId());
        Register register2 = RegisterHandler.getRegisterForId(legereID);
        assertEquals(register1, register2);
    }

    @Test
    public void testSeperateRegistersNotTheSame() {
        Register register1 = RegisterHandler.pullAndGetNewRegister();
        Register register2 = RegisterHandler.pullAndGetNewRegister();
        assertNotSame(register1, register2);
    }

    @Test
    public void getID() {
        RegisterID id = RegisterHandler.pullNewRegister();
        assertTrue(RegisterHandler.registerIDTaken(id));
    }

    @Test
    public void getIdFollowedByRegister() {
        RegisterID id = RegisterHandler.pullNewRegister();
        Register register = RegisterHandler.getRegisterForId(id);
        assertNotNull(register);
    }

    @Test
    public void asynchronusCallOfSameObject() {
        DataOutputPipe.loadAnnotatedModules();
        Register register1 = RegisterHandler.pullAndGetNewRegister();
        Register register2 = RegisterHandler.getRegisterForId(register1.getRegisterId());
        Tester t1 = register1.fetchAndGetModuleFromPipe(Tester.class.getName());
        t1.setInteger(testInteger);
        int first = t1.getInteger();
        register1.pushModuleToRegister(t1);
        Tester t2 = register2.fetchAndGetModuleFromPipe(Tester.class.getName());
        int second = t2.getInteger();
        assertEquals(first, second, testInteger);
    }
}
