@echo off

echo MIGRACION CODIGOS COTEJO TRAMITACION A MODULO GESTION CSV
setlocal
set CURRENT_DIRECTORY="%~dp0"
set JARS_DIRECTORY=%CURRENT_DIRECTORY%\lib
set JAVA_OPTS=-Xms256M -Xmx512M  -XX:PermSize=128m -XX:MaxPermSize=256m -XX:NewSize=64m

echo INICIO CARGA LIBRERIAS
set CLASSPATH=.
FOR /R %JARS_DIRECTORY% %%G IN (*.jar) DO call buildclasspath_append.cmd %%G

echo EJECUCION DE LA IMPORTACION
java %JAVA_OPTS% ieci.tecdoc.sgm.gestioncsv.db.migracion.ImportTramitacionDocumentos %1 %2 %3 %4 %5 %6 %7
