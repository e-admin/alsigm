spool create_db.log

set define off;

@@01-create_sequences.sql
@@02-create_tables.sql
@@03-create_views.sql
@@04-datos_iniciales.sql
@@06.1-datos_prototipos.sql
@@06.2-datos_prototipos_v1.9.sql
@@06.3-informes_estadisticos.sql
@@07-actualizacion_permisos.sql
@@08-configuracion_publicador.sql

spool off
