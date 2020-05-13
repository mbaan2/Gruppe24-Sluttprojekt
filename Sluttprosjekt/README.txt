Thank you for trusting us to write this programme.
Here are some extra tips:
- The project was made using intellij. Its a module-based Maven Java 13 project.
-SUPERUSER ACCESS
 User: admin
 Password: admin

- ERROR HANDLING
If encountering error:
"Error occured during initialization of boot layer
java.lang.LayerInstantiationException: Class loader (instance of): 'app' tried to define prohibited package name: java.Gruppe24.OSLOMET.DataValidation",
This error means that the test directory hasn't been properly set and can occur based on previous saved preferences in intellij.
To fix this error:
Go to src/test/java,
right click on the "java" directory,
click on "Mark directory as" and
select "Test Sources Root"