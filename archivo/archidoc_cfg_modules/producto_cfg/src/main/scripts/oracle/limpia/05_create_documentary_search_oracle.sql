spool 05_create_documentary_search.log
@@busqueda_documental\ARCHIVOFTSTH.sql
@@busqueda_documental\ARCHIVOFTSTB.sql
execute archivo.preferencias
execute archivo.createkey( 'ADVCTEXTCF', 'IDELEMENTOCF,IDCAMPO,ORDEN')
execute archivo.createPolicy('ADVCTEXTCF','VALOR','IDELEMENTOCF,IDCAMPO,ORDEN','','-/',0)
execute archivo.createkey( 'ADVCTEXTLCF', 'IDELEMENTOCF,IDCAMPO,ORDEN')
execute archivo.createPolicy('ADVCTEXTLCF','VALOR','IDELEMENTOCF,IDCAMPO,ORDEN','','-/',0)
execute archivo.createkey( 'ADVCTEXTDESCR', 'IDDESCR, IDCAMPO, ORDEN')
execute archivo.createPolicy('ADVCTEXTDESCR','VALOR','IDDESCR, IDCAMPO, ORDEN','','-/',0)
execute archivo.createkey( 'ADVCTEXTLDESCR', 'IDDESCR, IDCAMPO, ORDEN')
execute archivo.createPolicy('ADVCTEXTLDESCR','VALOR','IDDESCR, IDCAMPO, ORDEN','','-/',0)
execute archivo.createkey( 'ASGFELEMENTOCF', 'ID,TIPO')
execute archivo.createPolicy('ASGFELEMENTOCF','TITULO','ID,TIPO','','-/',0)
execute archivo.createkey( 'ADDESCRIPTOR', 'ID')
execute archivo.createPolicy('ADDESCRIPTOR','NOMBRE','ID','','-/',0)
@@busqueda_documental\ARCHIVOINTERMEDIA.sql
@@busqueda_documental\ARCHIVOJOBINTERMEDIA.sql
@@busqueda_documental\ARCHIVOOPT.sql
spool off