package de.thorbenkuck.rhfw.register;

import de.thorbenkuck.rhfw.pipe.DataOutputPipe;
import de.thorbenkuck.rhfw.register.fetching.FetchHandler;
import de.thorbenkuck.rhfw.register.pulling.PullHandler;
import de.thorbenkuck.rhfw.register.pushing.PushHandler;

import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;

/**
 * <p>
 *  This Register is a container, in witch Classes can pull, fetch, and push objects, witch are called "modules". Modules are Classes, annotated with @DataModule.
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
    private HashMap<Object, Object> moduleContainerList = new HashMap<>();

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
    	this(getDataOutputPipe());
    }

    public Register(DataOutputPipe dataOutputPipe) {
		registerId = new RegisterID();
		this.dataOutputPipe = dataOutputPipe;
	}

    /**
     * <p>
     *     Fetch a DataModule from the DataOutputPipe.
     * </p>
     * <p>
     *     Fetch means, if the DataModule (class) is not contained within the register, it will be safed.
     *     But if it is already contained within the register, it will not be overwritten.
     * </p>
     *
     * @param className The name of the module, witch should be fetched from the DataOutputPipe
     */
    @Deprecated
    public void fetchModuleFromPipe(Object className) {
        if(!moduleContainerList.containsKey(className)) {
            moduleContainerList.put(className, dataOutputPipe.getModule(className));
        }
    }

    /**
     * <p>
     *     Pulls a DataModule from the DataOutputPipe.
     * </p>
     * <p>
     *     Pull means, if the DataModule (class) is not contained within the register, it will be safed.
     *     But if it is already contained within the register, it will be overwritten.
     *     This also counts for manually pushed modules!
     * </p>
     * @param className The name of the module, witch should be pulled from the DataOutputPipe
     */
    @Deprecated
    public void pullModuleFromPipe(Object className) {
        moduleContainerList.put(className, dataOutputPipe.getModule(className));
    }

    /**
     * <p>
     *     Fetch a DataModule from the DataOutputPipe and returns an instance of the module.
     * </p>
     * <p>
     *     Fetch means, if the DataModule (class) is not contained within the register, it will be safed.
     *     But if it is already contained within the register, it will not be overwritten.
     * </p>
     * @param className The name of the module, witch should be fetched from the DataOutputPipe
     * @return module The module, witch had been fetched.
     */
    @Deprecated
    public <T> T fetchAndGetModuleFromPipe(Object className) {
        fetchModuleFromPipe(className);
        return pullModule(className);
    }

    /**
     * <p>
     *     Pulls a DataModule from the DataOutputPipe and returns an instance of the module.
     * </p>
     * <p>
     *     Pull means, if the DataModule (class) is not contained within the register, it will be safed.
     *     But if it is already contained within the register, it will be overwritten.
     *     This also counts for manually pushed modules!
     * </p>
     * @param className The name of the module, witch should be pulled from the DataOutputPipe
     * @return module The module, witch had been pulled.
     */
    @Deprecated
    public <T> T pullAndGetModuleFromPipe(Object className) {
        pullModuleFromPipe(className);
        return pullModule(className);
    }

    public FetchHandler fetch() {
		return new FetchHandler(moduleContainerList, dataOutputPipe);
	}

	public PullHandler pull() {
    	return new PullHandler(moduleContainerList, dataOutputPipe);
	}

	public PushHandler push() {
    	return new PushHandler(dataOutputPipe, moduleContainerList);
	}

    /**
     * <p>
     *     Pulls a DataModule from the current Register. If it is not contained within the Register ... TODO
     * </p>
     * @param key The name of the module, witch should be pulled from the Register
     * @return DataModule
     */
    public <T> T pullModule(Object key) {
        return (T) cloneObject(moduleContainerList.get(key));
    }

    /**
     * <p>
     *     Pushes a DataModule to the Register. This overwrites contained Modules, if the name is already taken.
     * </p>
     * @param object The object, that should be safed within the Register
     */
    @Deprecated
    public void pushModuleToRegister(Object object) {
        pushModuleToRegister(object.getClass(), object);
    }

    /**
     * <p>
     *     Pushes a DataModule to the Register and safes it to a custom name. This overwrites contained Modules, if the name is already taken.
     * </p>
     * @param key The key, under witch the Object should be safed.
     * @param object The object, that should be safed within the Register
     */
    @Deprecated
    public void pushModuleToRegister(Object key, Object object) {
        moduleContainerList.put(key, object);
    }

    /**
     * <p>
     *     Get the RegisterID from the current Register
     * </p>
     *
     * @return RegisterID
     */
    public RegisterID getRegisterId() {
        return new RegisterID(registerId);
    }

    // TODO
    public void removeModule(Object key) {
        moduleContainerList.remove(key);
    }

    private synchronized Object cloneObject(Object object) {
        if(!(object instanceof Serializable)) {
            return primitiveClone(object);
        } else {
            try {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ObjectOutputStream oos = new ObjectOutputStream(baos);
                oos.writeObject(object);
                ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
                ObjectInputStream ois = new ObjectInputStream(bais);
                return ois.readObject();
            } catch (ClassNotFoundException | IOException e) {
                // TODO-Later Exception-Handling
                e.printStackTrace();
                return null;
            }
        }
    }

    private synchronized Object primitiveClone(Object object) {
        try {
            Object clone = object.getClass().newInstance();
            for (Field field : object.getClass().getDeclaredFields()) {
                field.setAccessible(true);
                if(field.get(object) == null || Modifier.isFinal(field.getModifiers())) {
                    continue;
                }
                if(field.getType().isPrimitive() || field.getType().equals(String.class)
                        || field.getType().getSuperclass().equals(Number.class)
                        || field.getType().equals(Boolean.class)) {

                    field.set(clone, field.get(object));
                } else {
                    Object childObj = field.get(object);
                    if(childObj == object) {
                        field.set(clone, clone);
                    } else {
                        field.set(clone, cloneObject(field.get(object)));
                    }
                }
            }
            return clone;
        } catch(Exception e) {
            // TODO-Later Exception-Handling
            return null;
        }
    }

	private static synchronized DataOutputPipe getDataOutputPipe() {
    	if(!DataOutputPipe.exists(Register.class)) {
			DataOutputPipe dataOutputPipe = DataOutputPipe.access(Register.class);
			dataOutputPipe.loadAnnotatedModules();
			return dataOutputPipe;
		} else {
    		return DataOutputPipe.access(Register.class);
		}
	}
}
