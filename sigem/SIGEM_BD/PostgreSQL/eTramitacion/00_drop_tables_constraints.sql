--------------------------------
-- CONSTRAINTS DE LAS TABLAS ---
--------------------------------
ALTER TABLE ONLY sgmntinfo_notificacion DROP CONSTRAINT sgmnt_info_notificacion_estado_noti_fk;
ALTER TABLE ONLY sgmntinfo_documento DROP CONSTRAINT sgmntinfo_documentos_noti_fk;
ALTER TABLE ONLY sgmrtcatalogo_docstramite DROP CONSTRAINT sgmrdecat_procedure_document_document_id_fkey;
ALTER TABLE ONLY sgmrtcatalogo_docstramite DROP CONSTRAINT sgmrdecat_procedure_document_procedure_id_fkey;
ALTER TABLE ONLY sgmafsesion_info DROP CONSTRAINT sgmrdesession_info_hookid_fkey;

ALTER TABLE ONLY sgmrdedocumentos DROP CONSTRAINT documents_pkey;
ALTER TABLE ONLY sgm_pe_liquidaciones DROP CONSTRAINT pk_pe_liquidaciones;
ALTER TABLE ONLY sgm_pe_al12nsec DROP CONSTRAINT pk_sgm_pe_al12nsec;
ALTER TABLE ONLY sgm_pe_al3nsec DROP CONSTRAINT pk_sgm_pe_al3nsec;
ALTER TABLE ONLY sgm_cthitoestexp DROP CONSTRAINT sgm_cthitoestexp_pkey;
ALTER TABLE ONLY sgm_ctinfoexp DROP CONSTRAINT sgm_ctinfoexp_pkey;
ALTER TABLE ONLY sgm_ctintexp DROP CONSTRAINT sgm_ctintexp_cnumexp_key;
ALTER TABLE ONLY sgm_ntfichnotif DROP CONSTRAINT sgm_ntfichnotif_pkey;
ALTER TABLE ONLY sgm_ntinfonotif DROP CONSTRAINT sgm_ntinfonotif_cnumexp_key;
ALTER TABLE ONLY sgm_ntinfonotif DROP CONSTRAINT sgm_ntinfonotif_pkey;
ALTER TABLE ONLY sgmafconector_autenticacion DROP CONSTRAINT sgmafconector_autenticacion_pkey;
ALTER TABLE ONLY sgm_au_usuarios DROP CONSTRAINT sgmafusuarios_pkey;
ALTER TABLE ONLY sgmcertificacion DROP CONSTRAINT sgmcertificacion_pkey;
ALTER TABLE ONLY sgmntinfo_estado_noti DROP CONSTRAINT sgmntinfo_estado_noti_pk;
ALTER TABLE ONLY sgmntinfo_notificacion DROP CONSTRAINT sgmntinfo_notificacion_pk;
ALTER TABLE ONLY sgmrtcatalogo_organos DROP CONSTRAINT sgmrdecat_addressee_pkey;
ALTER TABLE ONLY sgmrtcatalogo_documentos DROP CONSTRAINT sgmrdecat_document_pkey;
ALTER TABLE ONLY sgmrtcatalogo_conectores DROP CONSTRAINT sgmrdecat_hook_pkey;
ALTER TABLE ONLY sgmrtcatalogo_tipoconector DROP CONSTRAINT sgmrdecat_hooktype_pkey;
ALTER TABLE ONLY sgmrtcatalogo_docstramite DROP CONSTRAINT sgmrdecat_procedure_document_pkey;
ALTER TABLE ONLY sgmrtcatalogo_tramites DROP CONSTRAINT sgmrdecat_procedure_pkey;
ALTER TABLE ONLY sgmrtregistro DROP CONSTRAINT sgmrderegistry_pkey;
ALTER TABLE ONLY sgmafsesion_info DROP CONSTRAINT sgmrdesession_info_pkey;
ALTER TABLE ONLY sgmrtcatalogo_diascalendario DROP CONSTRAINT sgmrtcatalogo_diascalendario_pkey;
ALTER TABLE sgmrtregistro_docs_csv DROP CONSTRAINT sgmrtregistro_docs_csv_pkey;
ALTER TABLE sgmrtregistro_docs_csv DROP CONSTRAINT sgmrtregistro_docs_csv_key;

-----------------
--   TABLAS   ---
-----------------
DROP TABLE sgm_au_usuarios;
DROP TABLE sgm_cnxusr;
DROP TABLE sgm_ctfichhito;
DROP TABLE sgm_cthitoestexp;
DROP TABLE sgm_cthitohistexp;
DROP TABLE sgm_ctinfoexp;
DROP TABLE sgm_ctintexp;
DROP TABLE sgm_ntfichnotif;
DROP TABLE sgm_ntinfonotif;
DROP TABLE sgm_pe_al12nsec;
DROP TABLE sgm_pe_al3nsec;
DROP TABLE sgm_pe_liquidaciones;
DROP TABLE sgm_pe_tasas;
DROP TABLE sgmafconector_autenticacion;
DROP TABLE sgmafsesion_info;
DROP TABLE sgmcertificacion;
DROP TABLE sgmctnotificacion;
DROP TABLE sgmctpago;
DROP TABLE sgmctsubsanacion;
DROP TABLE sgmntinfo_documento;
DROP TABLE sgmntinfo_estado_noti;
DROP TABLE sgmntinfo_notificacion;
DROP TABLE sgmrdedocumentos;
DROP TABLE sgmrdetiposmime;
DROP TABLE sgmrtcatalogo_conectores;
DROP TABLE sgmrtcatalogo_docstramite;
DROP TABLE sgmrtcatalogo_documentos;
DROP TABLE sgmrtcatalogo_organos;
DROP TABLE sgmrtcatalogo_tipoconector;
DROP TABLE sgmrtcatalogo_tramites;
DROP TABLE sgmrtregistro;
DROP TABLE sgmrtregistro_documentos;
DROP TABLE sgmrtregistro_secuencia;
DROP TABLE sgmrttmp_documentos;
DROP TABLE sgmrtcatalogo_calendario;
DROP TABLE sgmrtcatalogo_diascalendario;
DROP TABLE sgmrtregistro_docs_csv;
