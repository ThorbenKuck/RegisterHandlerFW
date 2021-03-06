# README #

## What is the RegisterHandlerFW
The RegisterHandlerFrameWork (short RegisterHandlerFW or RHFW) is a framework, dedicated to DI (dependency injection), MVP (Model View Presenter principal) and something i like to call the "Register-Pattern", the idea, that instances are bound to Registers and not to procedures.

However, it is not meant to be used for implicated DI! This framework can not resolve direct dependencies! It is more like a tool for MVP.

It uses the [FastClasspathScanner](https://github.com/lukehutch/fast-classpath-scanner).

## A short example ##

Before we can dive into some code, let's first explore some of the key-components:

   * Register

   A Register is a main Container for sharing object with other procedures.
   It provides a closed eco-system for objects (like a global cache).
    
   * RegisterID
   
     The RegisterID is the the identifier for a register
    
   * RegisterHandler
   
     The RegisterHandler is a centralised Object for:  
     - creating registers 
     - binding registers to custom (leger) ID's  
     You can "pull" a register from the RegisterHandler  
    
   * DataOutputPipe  
   
     The DataOutputPipe contains object, which are either  
     - annoteded with "@RegisterModule" or  
     - implementing the RegisterModuleInterface  
     You can add and get Module's from it.  
     NOTE: If you directly access the DataOutputPipe, you might encounter problems, like overriding instances
    
   * There is more ...
   
But let's dive into some Code. If you want to try it out for yourself, you can go inside the provided example (src.main.java.example.Main)

The basic principle is the following:  

1. Get a register  
2. get a object  
3. modify this object  
4. push it back to the register to make it accessible for all procedures, using the same Register

To get a register we have multiple options, but i will go with the easiest on here:  
```Java
Register register = RegisterHandler.pullAndGetNewRegister();
```  
We now have a Register, which will provide us with cross-procedural-accessibility to certain Objects

To make it accessible to other procedures, we have 2 options.

1. share the RegisterID  
    * ``register.getRegisterId()`` 
    
2. bind it to a "legerID"  
    * ``RegisterHandler.bindRegister("our_leger_id", register.getRegisterId())``
    
If we have done that, we can now get Object's from the Register. We also have 2 Options here:  

1. fetchModule  
    * Tells the register to pull an object from the DataOutputPipe and safe it, but __only__
    if it is not already contained within the Register
    
2. pullModule  
    * Tells the register to pull an object from the DataOutputPipe and override any existing
    one with the same Class-Name
    
Let's assume now, we have the following class:

```Java
public class Tester implements RegisterModuleInterface {
    private int i = 0;
    public int getI() {
        return this.i;
     }
     public void setI(int newI) {
        this.i = newI;
     }
}
```

We now do the following: 
 
first we get the current object from the register  
```Java
Tester t = register.fetchAndGetModuleFromPipe(Tester.class.getName());
``` 
then we set i from Tester to 3  
```Java
t.setI(3)
```  
and push it back up to the register  
```Java
register.pushModuleToRegister(t);
```  
The next procedure, which is using the same Register, will now have the same Object.  
So, for example, let's assume some other procedure uses the Tester-Class from this Register.  
The call of 
```Java
t.getI()
```
would result in __3__

## Current State ##
__*(Alpha) v.0.1*__

## Participating ###

## If the tests don't work... ###
... make sure, that the depending libraries are globally excessive for JUnit!

## How do I get set up? ###

* Clone -> run gradlewrapper.  
* If you want to use it as a jar... well... jeah, i am pretty new to this stuff.. maybe someone can explain in detail how to do this. However, you should not include the JUnit-Test's and not the example package. You should just include the package:  
__src.main.java.de.thorbenkuck.rhfw__

## Contribution guidelines ###

* Pull-Request are never (!) accepted manually! Please let the pull-request be reviewed multiple Times and never accept your own requests (except you are the only contributor).
* If you want to contribute, check out the WIKI (as soon as it is set up) for stuff like coding-standards.

## Who do I talk to? ###

* Repo owner (ThorbenKuck) or admin

## License ##
 
MIT License
