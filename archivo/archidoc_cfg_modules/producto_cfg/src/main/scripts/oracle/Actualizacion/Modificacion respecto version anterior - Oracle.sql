--/*************************/
--/* Versión 4.8 > 4.9     */
--/*************************/

spool actualizacion49.log

INSERT INTO AGINFOSISTEMA (AUTID,NOMBRE,VALOR,FECHAACTUALIZACION) VALUES (SEQ_INFO.NextVal,'VERSIONBD','4.8->4.9',SYSDATE);


spool off