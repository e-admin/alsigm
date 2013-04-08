cd ..
call mvn initialize -Ddb=db2 -DdatabaseHost=10.228.75.74 -DdatabaseName=S3AR000 -DusernameJdbc=sigm3 -DpasswordJdbc=sigm3 -Pgenerate-bd,init-db2-archivo
cd cmds
pause
