mkdir classes
set JAVAC=C:\Progra~1\Java\jdk1.5.0_03\bin\javac.exe
set CLASSPATH=classes;jogl.jar

%JAVAC% -d classes -classpath %CLASSPATH% *.java
