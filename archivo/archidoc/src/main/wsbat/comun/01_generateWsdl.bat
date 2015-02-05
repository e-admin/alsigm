ECHO OFF
ECHO -------------------------------------------------------------------------------------------------------------------
ECHO Antes de ejecutar este bat hay que establecer el valor de las variables CLASSES_DIR y LIB_DIR en %1
ECHO -------------------------------------------------------------------------------------------------------------------

CALL %1

REM -m%METHODS%
java -cp %CP% org.apache.axis.wsdl.Java2WSDL -o %WSDL% -l%LOCATION% -y %STYLE% -n %NAMESPACE% -i %CLASS% %INTERFACE%
ECHO -------------------------------------------------------------------------------------------------------------------
ECHO Ejecucion finalizada
ECHO -------------------------------------------------------------------------------------------------------------------
PAUSE