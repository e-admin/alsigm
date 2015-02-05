REM Definir classpath

SET BASEDIR=..\..\..\..
SET CLASSES_DIR=%BASEDIR%\target\classes
SET LIB_DIR=%BASEDIR%\target\Archivo\WEB-INF\lib
SET CP=.;%CLASSES_DIR%

REM Definir valores del servidor

SET PORT=8080
SET HOSTNAME=localhost
SET APPLICATION=archidoc

REM Definir valores para generar el wsdl

SET WSDL_DIR=%BASEDIR%\src\main\webapp\docs\wsdl
SET WSDL=%WSDL_DIR%\WSDocumentosVitales.wsdl
SET LOCATION=http://%HOSTNAME%:%PORT%/%APPLICATION%/services/WSDocumentosVitales
SET NAMESPACE=http://docvitales.ws
SET INTERFACE=ws.docvitales.WSDocumentosVitales
SET CLASS=ws.docvitales.WSDocumentosVitalesImpl
SET STYLE=WRAPPED
REM SET METHODS=*

Rem Definir valores para generar el deploy.wsdd y undeploy.wsdd

SET DEPLOY_SCOPE=request
SET DELETE_JAVA_DIR=%WSDL_DIR%\ws\docvitales
SET DELETE_JAVA_DIR2=%WSDL_DIR%\common
SET DELETE_JAVA_DIR3=%WSDL_DIR%\docvitales

REM Definir valores para generar el server-config.wsdd

SET URL_ADMIN=http://%HOSTNAME%:%PORT%/%APPLICATION%/services/AdminService
SET DEPLOY_FILE=%WSDL_DIR%\ws\docvitales\deploy.wsdd

REM Definir valores para generar el cliente
SET CLIENT_DIR=C:\desarrollo\java\proyectos.maven.sigem\archivoTrunk\archivo_ws_clients\src-proxy

REM Añadir las librerías de axis
for %%i in (%LIB_DIR%\axis*.jar) do call ..\comun\cpappend.bat %%i
for %%i in (%LIB_DIR%\jaxrpc*.jar) do call ..\comun\cpappend.bat %%i
for %%i in (%LIB_DIR%\saaj*.jar) do call ..\comun\cpappend.bat %%i
for %%i in (%LIB_DIR%\wsdl*.jar) do call ..\comun\cpappend.bat %%i
for %%i in (%LIB_DIR%\mail*.jar) do call ..\comun\cpappend.bat %%i
for %%i in (%LIB_DIR%\commons-logging*.jar) do call ..\comun\cpappend.bat %%i
for %%i in (%LIB_DIR%\commons-discovery*.jar) do call ..\comun\cpappend.bat %%i
for %%i in (%LIB_DIR%\activation*.jar) do call ..\comun\cpappend.bat %%i
for %%i in (%LIB_DIR%\archigest*.jar) do call ..\comun\cpappend.bat %%i
for %%i in (%LIB_DIR%\log4j*.jar) do call ..\comun\cpappend.bat %%i