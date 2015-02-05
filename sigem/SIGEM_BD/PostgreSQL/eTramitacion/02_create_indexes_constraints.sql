--
-- Indices
--

CREATE INDEX fki_ ON sgmrtcatalogo_docstramite USING btree (documento_id);
CREATE INDEX fki_session_info_hookid ON sgmafsesion_info USING btree (conectorid);
CREATE INDEX fki_sgmnt_info_notificacion_estado_noti_fk ON sgmntinfo_notificacion USING btree (cestado);
CREATE INDEX fki_sgmntinfo_documentos_noti_fk ON sgmntinfo_documento USING btree (cnotiexpe, cnotinifdest);
CREATE INDEX fki_sgmrdecat_hook_type ON sgmrtcatalogo_conectores USING btree (tipo);
CREATE INDEX sgm_cnxusr1 ON sgm_cnxusr USING btree (cnif);
CREATE INDEX sgm_ctfichhito1 ON sgm_ctfichhito USING btree (cguidhito);
CREATE INDEX sgm_cthitoestexp1 ON sgm_cthitoestexp USING btree (cnumexp);
CREATE INDEX sgm_ctinfoexp1 ON sgm_ctinfoexp USING btree (cnumexp);
CREATE INDEX sgm_ctintexp1 ON sgm_ctintexp USING btree (cnumexp);
CREATE INDEX sgm_ctintexp2 ON sgm_ctintexp USING btree (cnif);
CREATE INDEX sgm_ntfichnotif1 ON sgm_ntfichnotif USING btree (cguidnotif);
CREATE INDEX sgm_ntinfonotif1 ON sgm_ntinfonotif USING btree (cguid);
CREATE INDEX sgm_ntinfonotif2 ON sgm_ntinfonotif USING btree (cnifdest);
CREATE INDEX sgm_ntinfonotif3 ON sgm_ntinfonotif USING btree (cavisada);
CREATE INDEX sgm_ntinfonotif4 ON sgm_ntinfonotif USING btree (centregada);
CREATE INDEX sgmrde_registry_ix ON sgmrtregistro_documentos USING btree (numero_registro);
CREATE INDEX sgmrde_registry_ix1 ON sgmrtregistro USING btree (emisor_id);
CREATE INDEX sgmrde_registry_ix2 ON sgmrtregistro USING btree (fecha_registro);
CREATE INDEX sgmrde_registry_ix4 ON sgmrtregistro USING btree (estado);
CREATE INDEX sgmrde_registry_pk ON sgmrtregistro USING btree (numero_registro);
CREATE INDEX sgmrdecat_document_index ON sgmrtcatalogo_documentos USING btree (id);
CREATE INDEX sgmrdecat_procedure_index ON sgmrtcatalogo_tramites USING btree (id);
CREATE INDEX sgmrdedocuments_index ON sgmrdedocumentos USING btree (guid);
CREATE INDEX fki_sgmrtregistro_docs_csv ON sgmrtregistro_docs_csv (guid,csv);


--
-- Constraints
--

ALTER TABLE ONLY sgmrdedocumentos ADD CONSTRAINT documents_pkey PRIMARY KEY (guid);
ALTER TABLE ONLY sgm_pe_liquidaciones ADD CONSTRAINT pk_pe_liquidaciones PRIMARY KEY (referencia);
ALTER TABLE ONLY sgm_pe_al12nsec ADD CONSTRAINT pk_sgm_pe_al12nsec PRIMARY KEY (numsec);
ALTER TABLE ONLY sgm_pe_al3nsec ADD CONSTRAINT pk_sgm_pe_al3nsec PRIMARY KEY (modelo);
ALTER TABLE ONLY sgm_cthitoestexp ADD CONSTRAINT sgm_cthitoestexp_pkey PRIMARY KEY (cnumexp);
ALTER TABLE ONLY sgm_ctinfoexp ADD CONSTRAINT sgm_ctinfoexp_pkey PRIMARY KEY (cnumexp);
ALTER TABLE ONLY sgm_ctintexp ADD CONSTRAINT sgm_ctintexp_cnumexp_key UNIQUE (cnumexp, cnif);
ALTER TABLE ONLY sgm_ntfichnotif ADD CONSTRAINT sgm_ntfichnotif_pkey PRIMARY KEY (cguid);
ALTER TABLE ONLY sgm_ntinfonotif ADD CONSTRAINT sgm_ntinfonotif_cnumexp_key UNIQUE (cnumexp, cnifdest);
ALTER TABLE ONLY sgm_ntinfonotif ADD CONSTRAINT sgm_ntinfonotif_pkey PRIMARY KEY (cguid);
ALTER TABLE ONLY sgmafconector_autenticacion ADD CONSTRAINT sgmafconector_autenticacion_pkey PRIMARY KEY (tramiteid, conectorid);
ALTER TABLE ONLY sgm_au_usuarios ADD CONSTRAINT sgmafusuarios_pkey PRIMARY KEY (usuario);
ALTER TABLE ONLY sgmcertificacion ADD CONSTRAINT sgmcertificacion_pkey PRIMARY KEY (id_fichero);
ALTER TABLE ONLY sgmntinfo_estado_noti ADD CONSTRAINT sgmntinfo_estado_noti_pk PRIMARY KEY (cguid);
ALTER TABLE ONLY sgmntinfo_notificacion ADD CONSTRAINT sgmntinfo_notificacion_pk PRIMARY KEY (cnotiid);
ALTER TABLE ONLY sgmrtcatalogo_organos ADD CONSTRAINT sgmrdecat_addressee_pkey PRIMARY KEY (organo);
ALTER TABLE ONLY sgmrtcatalogo_documentos ADD CONSTRAINT sgmrdecat_document_pkey PRIMARY KEY (id);
ALTER TABLE ONLY sgmrtcatalogo_conectores ADD CONSTRAINT sgmrdecat_hook_pkey PRIMARY KEY (conectorid);
ALTER TABLE ONLY sgmrtcatalogo_tipoconector ADD CONSTRAINT sgmrdecat_hooktype_pkey PRIMARY KEY (tipoid);
ALTER TABLE ONLY sgmrtcatalogo_docstramite ADD CONSTRAINT sgmrdecat_procedure_document_pkey PRIMARY KEY (tramite_id, documento_id, codigo_documento);
ALTER TABLE ONLY sgmrtcatalogo_tramites ADD CONSTRAINT sgmrdecat_procedure_pkey PRIMARY KEY (id);
ALTER TABLE ONLY sgmrtregistro ADD CONSTRAINT sgmrderegistry_pkey PRIMARY KEY (numero_registro);
ALTER TABLE ONLY sgmafsesion_info ADD CONSTRAINT sgmrdesession_info_pkey PRIMARY KEY (sesionid);
ALTER TABLE ONLY sgmrtcatalogo_diascalendario ADD CONSTRAINT sgmrtcatalogo_diascalendario_pkey PRIMARY KEY (id);

ALTER TABLE ONLY sgmntinfo_notificacion ADD CONSTRAINT sgmnt_info_notificacion_estado_noti_fk FOREIGN KEY (cestado) REFERENCES sgmntinfo_estado_noti(cguid);
ALTER TABLE ONLY sgmntinfo_documento ADD CONSTRAINT sgmntinfo_documentos_noti_fk FOREIGN KEY (cnotiid) REFERENCES sgmntinfo_notificacion(cnotiid);
ALTER TABLE ONLY sgmrtcatalogo_docstramite ADD CONSTRAINT sgmrdecat_procedure_document_document_id_fkey FOREIGN KEY (documento_id) REFERENCES sgmrtcatalogo_documentos(id);
ALTER TABLE ONLY sgmrtcatalogo_docstramite ADD CONSTRAINT sgmrdecat_procedure_document_procedure_id_fkey FOREIGN KEY (tramite_id) REFERENCES sgmrtcatalogo_tramites(id);
ALTER TABLE ONLY sgmafsesion_info ADD CONSTRAINT sgmrdesession_info_hookid_fkey FOREIGN KEY (conectorid) REFERENCES sgmrtcatalogo_conectores(conectorid);


ALTER TABLE ONLY sgmrtregistro_docs_csv ADD CONSTRAINT sgmrtregistro_docs_csv_pkey PRIMARY KEY (guid);
ALTER TABLE ONLY sgmrtregistro_docs_csv ADD CONSTRAINT sgmrtregistro_docs_csv_key UNIQUE (csv);