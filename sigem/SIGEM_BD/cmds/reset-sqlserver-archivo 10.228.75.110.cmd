cd ..
call mvn initialize -Ddb=sqlserver -DdatabaseHost=10.228.75.110 -DdatabaseName=SIGM_3_ARCHIVO_000 -DusernameJdbc=SIGM_3_ARCHIVO_000 -DpasswordJdbc=SIGM_3_ARCHIVO_000 -Pgenerate-bd,reset-sqlserver-archivo
cd cmds
pause