--
-- Indices
--

CREATE INDEX fki_ ON sgmrtcatalogo_docstramite (documento_id);
CREATE INDEX fki_ses_inf_hookid ON sgmafsesion_info (conectorid);
CREATE INDEX fki_nt_notif_estad ON sgmntinfo_notificacion (cestado);
CREATE INDEX fki_nt_docs_notif ON sgmntinfo_documento (cnotiexpe, cnotinifdest);
CREATE INDEX fki_cat_hook_type ON sgmrtcatalogo_conectores (tipo);
CREATE INDEX fki_cnx_usr1 ON sgm_cnxusr (cnif);
CREATE INDEX fki_ct_fichhito1 ON sgm_ctfichhito (cguidhito);
--CREATE INDEX fki_ct_hitoestexp1 ON sgm_cthitoestexp (cnumexp);
--CREATE INDEX fki_ct_infoexp1 ON sgm_ctinfoexp (cnumexp);
CREATE INDEX fki_ct_intexp1 ON sgm_ctintexp (cnumexp);
CREATE INDEX fki_ct_intexp2 ON sgm_ctintexp (cnif);
CREATE INDEX fki_nt_fichnotif1 ON sgm_ntfichnotif (cguidnotif);
--CREATE INDEX fki_nt_infonotif1 ON sgm_ntinfonotif (cguid);
CREATE INDEX fki_nt_infonotif2 ON sgm_ntinfonotif (cnifdest);
CREATE INDEX fki_nt_infonotif3 ON sgm_ntinfonotif (cavisada);
CREATE INDEX fki_nt_infonotif4 ON sgm_ntinfonotif (centregada);
CREATE INDEX fki_rde_registry ON sgmrtregistro_documentos (numero_registro);
CREATE INDEX fki_rde_registry_1 ON sgmrtregistro (emisor_id);
CREATE INDEX fki_rde_registry_2 ON sgmrtregistro (fecha_registro);
CREATE INDEX fki_rde_registry_4 ON sgmrtregistro (estado);
CREATE INDEX fki_sgmrtregistro_docs_csv ON sgmrtregistro_docs_csv (guid,csv);
--CREATE INDEX fki_rde_registry_p ON sgmrtregistro (numero_registro);
--CREATE INDEX fki_rdecat_doc_ind ON sgmrtcatalogo_documentos (id);
--CREATE INDEX fki_rdecat_prc_ind ON sgmrtcatalogo_tramites (id);
--CREATE INDEX fki_rdedocs_ind ON sgmrdedocumentos (guid);


--
-- Constraints
--

ALTER TABLE sgmrdedocumentos ADD CONSTRAINT documents_pkey PRIMARY KEY (guid);
ALTER TABLE sgm_pe_liquidaciones ADD CONSTRAINT pk_pe_liq PRIMARY KEY (referencia);
ALTER TABLE sgm_pe_al12nsec ADD CONSTRAINT pk_sgm_pe_al12nsec PRIMARY KEY (numsec);
ALTER TABLE sgm_pe_al3nsec ADD CONSTRAINT pk_sgm_pe_al3nsec PRIMARY KEY (modelo);
ALTER TABLE sgm_cthitoestexp ADD CONSTRAINT sgm_cthitoestex_pk PRIMARY KEY (cnumexp);
ALTER TABLE sgm_ctinfoexp ADD CONSTRAINT sgm_ctinfoexp_pkey PRIMARY KEY (cnumexp);
ALTER TABLE sgm_ctintexp ADD CONSTRAINT sgm_ctintexp_cn_pk UNIQUE (cnumexp, cnif);
ALTER TABLE sgm_ntfichnotif ADD CONSTRAINT sgm_ntfichnotif_pk PRIMARY KEY (cguid);
ALTER TABLE sgm_ntinfonotif ADD CONSTRAINT sgm_ntnotif_cn_key UNIQUE (cnumexp, cnifdest);
ALTER TABLE sgm_ntinfonotif ADD CONSTRAINT sgm_ntinfonotif_pk PRIMARY KEY (cguid);
ALTER TABLE sgmafconector_autenticacion ADD CONSTRAINT sgmafcon_aut_pk PRIMARY KEY (tramiteid, conectorid);
ALTER TABLE sgm_au_usuarios ADD CONSTRAINT sgmafusuarios_pkey PRIMARY KEY (usuario);
ALTER TABLE sgmcertificacion ADD CONSTRAINT sgmcert_pk PRIMARY KEY (id_fichero);
ALTER TABLE sgmntinfo_estado_noti ADD CONSTRAINT sgmnt_est_noti_pk PRIMARY KEY (cguid);
ALTER TABLE sgmntinfo_notificacion ADD CONSTRAINT sgmntinfo_notif_pk PRIMARY KEY (cnotiid);
ALTER TABLE sgmrtcatalogo_organos ADD CONSTRAINT sgmrdecat_org_pk PRIMARY KEY (organo);
ALTER TABLE sgmrtcatalogo_documentos ADD CONSTRAINT sgmrdecat_doc_pk PRIMARY KEY (id);
ALTER TABLE sgmrtcatalogo_conectores ADD CONSTRAINT sgmrdecat_hook_pk PRIMARY KEY (conectorid);
ALTER TABLE sgmrtcatalogo_tipoconector ADD CONSTRAINT sgmrdecat_hookt_pk PRIMARY KEY (tipoid);
ALTER TABLE sgmrtcatalogo_docstramite ADD CONSTRAINT sgmcat_proc_doc_pk PRIMARY KEY (tramite_id, documento_id, codigo_documento);
ALTER TABLE sgmrtcatalogo_tramites ADD CONSTRAINT sgmrdecat_proc_pk PRIMARY KEY (id);
ALTER TABLE sgmrtregistro ADD CONSTRAINT sgmrderegistry_pk PRIMARY KEY (numero_registro);
ALTER TABLE sgmafsesion_info ADD CONSTRAINT sgmrdeses_info_pk PRIMARY KEY (sesionid);
ALTER TABLE sgmrtcatalogo_diascalendario ADD CONSTRAINT diascalendar_pkey PRIMARY KEY (id);

ALTER TABLE sgmntinfo_notificacion ADD CONSTRAINT nt_notif_est_noti FOREIGN KEY (cestado) REFERENCES sgmntinfo_estado_noti(cguid);
ALTER TABLE sgmntinfo_documento ADD CONSTRAINT nt_docs_noti FOREIGN KEY (cnotiid) REFERENCES sgmntinfo_notificacion(cnotiid);
ALTER TABLE sgmrtcatalogo_docstramite ADD CONSTRAINT rdecat_proc_doc_id FOREIGN KEY (documento_id) REFERENCES sgmrtcatalogo_documentos(id);
ALTER TABLE sgmrtcatalogo_docstramite ADD CONSTRAINT rdecat_proc_doc_pd FOREIGN KEY (tramite_id) REFERENCES sgmrtcatalogo_tramites(id);
ALTER TABLE sgmafsesion_info ADD CONSTRAINT rdeses_info_hookid FOREIGN KEY (conectorid) REFERENCES sgmrtcatalogo_conectores(conectorid);

ALTER TABLE sgmrtregistro_docs_csv ADD CONSTRAINT sgmrtregistro_docs_csv_pkey PRIMARY KEY (guid);
ALTER TABLE sgmrtregistro_docs_csv ADD CONSTRAINT sgmrtregistro_docs_csv_key UNIQUE (csv);
