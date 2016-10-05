package registers;

import de.thorbenkuck.rhfw.handler.RegisterHandler;
import de.thorbenkuck.rhfw.handler.register.Register;
import junit.framework.TestCase;

public class RegisterClassTest extends TestCase {

    private static String legereID = "test";

    public void testGetRegister() {
        Register register = RegisterHandler.pullAndGetNewRegister();
        assertNotNull(register);
    }

    public void testGetRegisterById() {
        Register register1 = RegisterHandler.pullAndGetNewRegister();
        Register register2 = RegisterHandler.getRegisterForId(register1.getRegisterId());
        assertEquals(register1, register2);
    }

    public void testGetBoundRegister() {
        Register register1 = RegisterHandler.pullAndGetNewRegister();
        RegisterHandler.bindRegister(legereID, register1.getRegisterId());
        Register register2 = RegisterHandler.getRegisterForId(legereID);
        assertEquals(register1, register2);
    }
}
