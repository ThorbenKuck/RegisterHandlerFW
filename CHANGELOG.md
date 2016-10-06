# Changelog #
Current version: (Alpha) v.0.1

## Code's ##

* "__\+__" added something  
* "__\-__" removed something  
* "__\*__" altert something  
* "__\>__" bugfix  
* "__\<__" new bug  
* "__\|__" no change  
* "__$__" example  
* "__?__" explanation
* "__§__" rule

## Changelog for version: ##

### (Alpha) ###

#### v.0.1 ####

* __\+__ *Register*-class  
  * __\+__ *RegisterID*-class  
* __\+__ *RegisterHandler*-class  
* __\+__ *DataOutputPipe*-class  
* __\+__ *RegisterTemplate*-class  
* __\+__ *RegisterModuleInterface*-class  
* __\+__ *RegisterModule*-annotation  
  * __§__ If a class is annoted with __@RegisterModule__, it must implement the __*Serializable*__-interface. If not, you may encounter Problems with complex objects, comeing from any Register.  
* __\+__ The __*RHFW*__ is now using the [FastClasspathScanner](https://github.com/lukehutch/fast-classpath-scanner).  
* __\+__ *ObjectedModuleContainerList*-class  
  * __?__ This class ist mapping object's to names  
