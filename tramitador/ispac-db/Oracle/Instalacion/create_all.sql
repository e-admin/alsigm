spool create_db.log

set define off;

@@01-create_sequences.sql
@@02-create_tables.sql
@@03-create_views.sql
@@04-datos_iniciales.sql
@@06-datos_prototipos.sql
@@07-create_tables_conector_sharepoint.sql
@@08-fwktd-audit-create.sql
@@09-fwktd-audit-insert.sql

spool off
