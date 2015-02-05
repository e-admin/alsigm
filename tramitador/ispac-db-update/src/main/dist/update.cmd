@echo off

setlocal

set CURRENT_DIRECTORY="%~dp0"
rem set JAVA_OPTS=

set CLASSPATH=.
FOR /R %CURRENT_DIRECTORY% %%G IN (*.jar) DO call buildclasspath_append.cmd %%G

java %JAVA_OPTS% ieci.tdw.ispac.update.Update_v6_3_To_v6_4 %1 %2 %3 %4 %5


