set CLASSPATH=classes;jogl.jar
set LIBPATH=java.library.path=lib

java.exe -D%LIBPATH% -cp %CLASSPATH% %1 %2
