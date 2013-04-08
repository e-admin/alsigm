--------------------------------
-- CONSTRAINTS DE LAS TABLAS ---
--------------------------------
ALTER TABLE sgmntinfo_notificacion DROP CONSTRAINT nt_notif_est_noti;
ALTER TABLE sgmntinfo_documento DROP CONSTRAINT nt_docs_noti;
ALTER TABLE sgmrtcatalogo_docstramite DROP CONSTRAINT rdecat_proc_doc_id;
ALTER TABLE sgmrtcatalogo_docstramite DROP CONSTRAINT rdecat_proc_doc_pd;
ALTER TABLE sgmafsesion_info DROP CONSTRAINT rdeses_info_hookid;

ALTER TABLE sgmrdedocumentos DROP CONSTRAINT documents_pkey;
ALTER TABLE sgm_pe_liquidaciones DROP CONSTRAINT pk_pe_liq;
ALTER TABLE sgm_pe_al12nsec DROP CONSTRAINT pk_sgm_pe_al12nsec;
ALTER TABLE sgm_pe_al3nsec DROP CONSTRAINT pk_sgm_pe_al3nsec;
ALTER TABLE sgm_cthitoestexp DROP CONSTRAINT sgm_cthitoestex_pk;
ALTER TABLE sgm_ctinfoexp DROP CONSTRAINT sgm_ctinfoexp_pkey;
ALTER TABLE sgm_ctintexp DROP CONSTRAINT sgm_ctintexp_cn_pk;
ALTER TABLE sgm_ntfichnotif DROP CONSTRAINT sgm_ntfichnotif_pk;
ALTER TABLE sgm_ntinfonotif DROP CONSTRAINT sgm_ntnotif_cn_key;
ALTER TABLE sgm_ntinfonotif DROP CONSTRAINT sgm_ntinfonotif_pk;
ALTER TABLE sgmafconector_autenticacion DROP CONSTRAINT sgmafcon_aut_pk;
ALTER TABLE sgm_au_usuarios DROP CONSTRAINT sgmafusuarios_pkey;
ALTER TABLE sgmcertificacion DROP CONSTRAINT sgmcert_pk;
ALTER TABLE sgmntinfo_estado_noti DROP CONSTRAINT sgmnt_est_noti_pk;
ALTER TABLE sgmntinfo_notificacion DROP CONSTRAINT sgmntinfo_notif_pk;
ALTER TABLE sgmrtcatalogo_organos DROP CONSTRAINT sgmrdecat_org_pk;
ALTER TABLE sgmrtcatalogo_documentos DROP CONSTRAINT sgmrdecat_doc_pk;
ALTER TABLE sgmrtcatalogo_conectores DROP CONSTRAINT sgmrdecat_hook_pk;
ALTER TABLE sgmrtcatalogo_tipoconector DROP CONSTRAINT sgmrdecat_hookt_pk;
ALTER TABLE sgmrtcatalogo_docstramite DROP CONSTRAINT sgmcat_proc_doc_pk;
ALTER TABLE sgmrtcatalogo_tramites DROP CONSTRAINT sgmrdecat_proc_pk;
ALTER TABLE sgmrtregistro DROP CONSTRAINT sgmrderegistry_pk;
ALTER TABLE sgmafsesion_info DROP CONSTRAINT sgmrdeses_info_pk;
ALTER TABLE sgmrtcatalogo_diascalendario DROP CONSTRAINT diascalendar_pkey;
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
