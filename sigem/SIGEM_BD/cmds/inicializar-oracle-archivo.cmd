cd ..
call mvn initialize -Ddb=oracle -DdatabaseHost=10.228.75.70 -DdatabaseName=SIGM_3_ARCHIVO_000 -DusernameJdbc=SIGM_3_ARCHIVO_000 -DpasswordJdbc=SIGM_3_ARCHIVO_000 -DindexTablespace=USERS -Pgenerate-bd,init-oracle-archivo
cd cmds
pause