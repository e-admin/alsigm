@echo off

setlocal

set CURRENT_DIRECTORY="%~dp0"
rem set JAVA_OPTS=

set CLASSPATH=.
FOR /R %CURRENT_DIRECTORY% %%G IN (*.jar) DO call buildclasspath_append.cmd %%G

java %JAVA_OPTS% ieci.tdw.ispac.update.ImportProcedures %1 %2 %3 %4 %5


