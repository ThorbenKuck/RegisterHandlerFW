package handler.register;

import pipe.DataOutputPipe;

import java.util.HashMap;

/**
 * <p>
 *  This Register is a container, in witch Classes can pull, fetch, and push objects, witch are called "modules". Modules are Classes, annotated with @RegisterModule.
 *  Registers should not be instantiated manually. This would negate the logic behind the RegisterHandler
 * </p>
 *
 * @version 1.0
 * @author Thorben Kuck
 */
public class Register {

    /**
     * The identifier of this register
     */
    private RegisterID registerId;

    /**
     * The instance of the DataOutputPipe
     */
    private DataOutputPipe dataOutputPipe;

    /**
     * Modules, that this register pulled / fetched.
     */
    private HashMap<String, Object> moduleContainerList;

    /**
     * Up on creating a new Register, the Register instances 3 things:
     * <p>
     *     - A containerList, for all Modules in this register
     *     - A RegisterID
     *     - The instance of the DataOutputPipe
     * </p>
     * All these things cooperate with one another.
     */
    public Register() {
        moduleContainerList = new HashMap<>();
        registerId = new RegisterID();
        dataOutputPipe = DataOutputPipe.getInstance();
    }

    /**
     * <p>
     *     Fetch a Module from the DataOutputPipe.
     * </p>
     * <p>
     *     Fetch means, if the Module (class) is not contained within the register, it will be safed.
     *     But if it is already contained within the register, it will not be overwritten.
     * </p>
     *
     * @param className The name of the module, witch should be fetched from the DataOutputPipe
     */
    public void fetchModuleFromPipe(String className) {
        if(!moduleContainerList.containsKey(className)) {
            moduleContainerList.put(className, dataOutputPipe.getModule(className));
        }
    }

    /**
     * <p>
     *     Pulls a Module from the DataOutputPipe.
     * </p>
     * <p>
     *     Pull means, if the Module (class) is not contained within the register, it will be safed.
     *     But if it is already contained within the register, it will be overwritten.
     *     This also counts for manually pushed modules!
     * </p>
     * @param className The name of the module, witch should be pulled from the DataOutputPipe
     */
    public void pullModuleFromPipe(String className) {
        moduleContainerList.put(className, dataOutputPipe.getModule(className));
    }

    /**
     * <p>
     *     Fetch a Module from the DataOutputPipe and returns an instance of the module.
     * </p>
     * <p>
     *     Fetch means, if the Module (class) is not contained within the register, it will be safed.
     *     But if it is already contained within the register, it will not be overwritten.
     * </p>
     * @param className The name of the module, witch should be fetched from the DataOutputPipe
     * @return module The module, witch had been fetched.
     */
    public <T> T fetchAndGetModuleFromPipe(String className) {
        fetchModuleFromPipe(className);
        return (T) moduleContainerList.get(className);
    }

    /**
     * <p>
     *     Pulls a Module from the DataOutputPipe and returns an instance of the module.
     * </p>
     * <p>
     *     Pull means, if the Module (class) is not contained within the register, it will be safed.
     *     But if it is already contained within the register, it will be overwritten.
     *     This also counts for manually pushed modules!
     * </p>
     * @param className The name of the module, witch should be pulled from the DataOutputPipe
     * @return module The module, witch had been pulled.
     */
    public <T> T pullAndGetModuleFromPipe(String className) {
        pullModuleFromPipe(className);
        return (T) moduleContainerList.get(className);
    }

    /**
     * <p>
     *     Pulls a Module from the current Register. If it is not contained within the Register ... TODO
     * </p>
     * @param className The name of the module, witch should be pulled from the Register
     * @return Module
     */
    public <T> T pullModule(String className) {
        return (T) moduleContainerList.get(className);
    }

    /**
     * <p>
     *     Pushes a Module to the Register. This overwrites contained Modules, if the name is already taken.
     * </p>
     * @param object The object, that should be safed within the Register
     */
    public void pushModuleToRegister(Object object) {
        pushModuleToRegister(object.getClass().getName(), object);
    }

    /**
     * <p>
     *     Pushes a Module to the Register and safes it to a custom name. This overwrites contained Modules, if the name is already taken.
     * </p>
     * @param className The name, under witch the Object should be safed.
     * @param object The object, that should be safed within the Register
     */
    public void pushModuleToRegister(String className, Object object) {
        moduleContainerList.put(className, object);
    }

    /**
     * <p>
     *     Get the RegisterID from the current Register
     * </p>
     *
     * @return RegisterID
     */
    public RegisterID getRegisterId() {
        return registerId;
    }

    // TODO
    public void removeModule() {

    }
}
