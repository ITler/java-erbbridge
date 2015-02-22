# JAVA-ERB-Bridge
Provides basic functionality for using ERB templates with JAVA.

## Usage
Class calls can be seen in JUnit-Tests.
Templates have to be placed in the `classpath`.

```
ERB.render("<name of template file>", <variables to substitute>)
```

## Parameter Hints
Use for mvn package

* `maven.test.skip = true` 
* `user.name = <overwrite username used for build>`