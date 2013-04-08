create table markup (query_id number, Document clob);
@@CONTEXTH.sql
@@CONTEXTB_9i.sql
execute invesdoc.preferencias
execute invesdoc.createkey( 'IVOLFILEFTS', 'ID')
execute invesdoc.createpolicyfile
execute invesdoc.createkey( 'IDOCGBLDOCH', 'ID')
execute invesdoc.createpolicyDOC
execute invesdoc.createkey( 'IVOLVOLTBL', 'LOCID')
execute invesdoc.createPolicy('IVOLVOLTBL','FILEVAL','LOCID','','-/',0)
