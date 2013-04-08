ECHO OFF
SET CP=.

for %%i in (lib/*.jar) do call cpappend.bat lib/%%i
for %%i in (*.jar) do call cpappend.bat %%i

java -cp %CP% clients/TiposDocVitalesClient
pause