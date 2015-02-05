@echo off
rem -----------------------------------------------------------
rem --- Inserta listas y volúmenes de invesdoc
rem                      VERSIÓN: 1.0
rem -----------------------------------------------------------

setlocal
if "%3"=="" GOTO PARAMS
SET US=%1
SET PW=%2
SET BD=%3

cd clobs_data

rem Volumenes
..\ADfichero2CLOB ivolvolhdr.txt                %US% %PW% %BD% ID '1' INFO IVOLVOLHDR

rem Repositorios
..\ADfichero2CLOB ivolrephdr.txt			%US% %PW% %BD% ID '1' INFO IVOLREPHDR

cd..

GOTO FIN


:PARAMS
echo.
echo SINTAXIS: %0 {usuario} {clave} {bd}
echo.
:FIN

endlocal
