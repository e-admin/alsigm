@@ARCHIVOFTSTH.sql
@@ARCHIVOFTSTB.sql
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
@@ARCHIVOINTERMEDIA.sql
@@ARCHIVOJOBINTERMEDIA.sql
@@ARCHIVOOPT.sql