--  Eliminar las tablas del conector de organización
spool 07_drop_tables_organizacion.log
DROP TABLE AOESTRORG;
DROP TABLE AOUSR;
DROP TABLE AOUSRORGV;

COMMIT;

spool off