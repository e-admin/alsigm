

--
-- Crear el usuario en el tablespace por defecto: USERS
--
CREATE USER &1 IDENTIFIED BY "&2";


--
-- Para crear el usuario en un tablespace especifico
-- hay que comentar la sentencia anterior y descomentar
-- las siguientes.
--

-- Crea el tablespace
--CREATE TABLESPACE &1
--    LOGGING 
--    DATAFILE '/home/oracle/oracle/product/10.2.0/db_1/dbs/&1..dbf' 
--    SIZE 100M 
--    REUSE 
--    AUTOEXTEND ON 
--    NEXT 32M MAXSIZE 32767M 
--    EXTENT MANAGEMENT LOCAL;

-- Crear el usuario
--CREATE USER &1 IDENTIFIED BY "&2"
--    DEFAULT TABLESPACE &1
--    TEMPORARY TABLESPACE "TEMP";


-- Roles del usuario
GRANT CONNECT TO &1;
GRANT CTXAPP TO &1;
GRANT EXP_FULL_DATABASE TO &1;
GRANT IMP_FULL_DATABASE TO &1;
GRANT RESOURCE TO &1;

QUIT;