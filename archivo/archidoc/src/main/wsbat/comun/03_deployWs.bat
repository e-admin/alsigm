ECHO OFF
ECHO -------------------------------------------------------------------------------------------------------------------
ECHO Antes de ejecutar este bat tienen que estar generados los ficheros wsdd y arrancado el servidor con el servicio web
ECHO -------------------------------------------------------------------------------------------------------------------

CALL %1
ECHO -------------------------------------------------------------------------------------------------------------------

java -cp %CP% org.apache.axis.client.AdminClient -l%URL_ADMIN% -h%HOSTNAME% -d -p%PORT% %DEPLOY_FILE%

ECHO -------------------------------------------------------------------------------------------------------------------
ECHO Si la ejecucion es correcta copiar el server-config.wsdd generado a WEB-INF de la aplicacion
ECHO -------------------------------------------------------------------------------------------------------------------

PAUSE