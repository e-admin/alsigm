--
-- Claves primarias
--
ALTER TABLE spac_ct_entidades_resources
    ADD CONSTRAINT pk_ct_entresources PRIMARY KEY (id);
ALTER TABLE spac_anotaciones
    ADD CONSTRAINT pk_anotaciones PRIMARY KEY (id);
ALTER TABLE spac_ct_entidades
    ADD CONSTRAINT pk_ct_entidades PRIMARY KEY (id);
ALTER TABLE spac_ct_fases
    ADD CONSTRAINT pk_ct_fases PRIMARY KEY (id);
ALTER TABLE spac_p_fstd
    ADD CONSTRAINT pk_p_fstd PRIMARY KEY (id);
ALTER TABLE spac_ct_fstr
    ADD CONSTRAINT pk_ct_fstr PRIMARY KEY (id);
ALTER TABLE spac_ct_pcdorg
    ADD CONSTRAINT pk_ct_pcdorg PRIMARY KEY (id);
ALTER TABLE spac_ct_procedimientos
    ADD CONSTRAINT pk_ct_pcds PRIMARY KEY (id);
ALTER TABLE spac_ct_tpdoc
    ADD CONSTRAINT pk_ct_tpdoc PRIMARY KEY (id);
ALTER TABLE spac_ct_tramites
    ADD CONSTRAINT pk_ct_tramites PRIMARY KEY (id);
ALTER TABLE spac_ctos_firma
    ADD CONSTRAINT pk_ctos_firma PRIMARY KEY (id);
ALTER TABLE spac_ctos_firma_cabecera
    ADD CONSTRAINT pk_ctos_fcab PRIMARY KEY (id_circuito);
ALTER TABLE spac_p_ctosfirma
    ADD CONSTRAINT pk_p_ctosfirma PRIMARY KEY (id);
ALTER TABLE spac_ctos_firma_detalle
    ADD CONSTRAINT pk_ctos_fdet PRIMARY KEY (id);
ALTER TABLE spac_dt_documentos
    ADD CONSTRAINT pk_dt_documentos PRIMARY KEY (id);
ALTER TABLE spac_fases
    ADD CONSTRAINT pk_fases PRIMARY KEY (id);
ALTER TABLE spac_hitos
    ADD CONSTRAINT pk_hitos PRIMARY KEY (id);
ALTER TABLE spac_p_entidades
    ADD CONSTRAINT pk_p_entidades PRIMARY KEY (id);
ALTER TABLE spac_p_fases
    ADD CONSTRAINT pk_p_fases PRIMARY KEY (id);
ALTER TABLE spac_p_flujos
    ADD CONSTRAINT pk_p_flujos PRIMARY KEY (id);
ALTER TABLE spac_p_nodos
    ADD CONSTRAINT pk_p_nodos PRIMARY KEY (id);
ALTER TABLE spac_p_plantdoc
    ADD CONSTRAINT pk_p_plantdoc PRIMARY KEY (id);
ALTER TABLE spac_p_procedimientos
    ADD CONSTRAINT pk_p_pcds PRIMARY KEY (id);
ALTER TABLE spac_p_tramites
    ADD CONSTRAINT pk_p_tramites PRIMARY KEY (id);
ALTER TABLE spac_p_dep_tramites
    ADD CONSTRAINT pk_p_dep_tramites PRIMARY KEY (id);
ALTER TABLE spac_ct_trtd
    ADD CONSTRAINT pk_ct_trtd PRIMARY KEY (id);
ALTER TABLE pub_acciones
    ADD CONSTRAINT pk_pub_acciones PRIMARY KEY (id);
ALTER TABLE pub_aplicaciones_hitos
    ADD CONSTRAINT pk_pub_apphitos PRIMARY KEY (id);
ALTER TABLE pub_condiciones
    ADD CONSTRAINT pk_pub_condiciones PRIMARY KEY (id);
ALTER TABLE pub_errores
    ADD CONSTRAINT pk_pub_errores PRIMARY KEY (id_hito, id_aplicacion, id_sistema, id_objeto);
ALTER TABLE pub_hitos_activos
    ADD CONSTRAINT pk_pub_hitosact PRIMARY KEY (id_hito, id_aplicacion, id_sistema);
ALTER TABLE pub_reglas
    ADD CONSTRAINT pk_pub_reglas PRIMARY KEY (id);
ALTER TABLE pub_ultimo_hito_tratado
    ADD CONSTRAINT pk_pub_ulthito PRIMARY KEY (id_sistema);
ALTER TABLE spac_gestion_aplicaciones
    ADD CONSTRAINT pk_spac_appgest PRIMARY KEY (id);
ALTER TABLE spac_calendarios
    ADD CONSTRAINT pk_spac_calends PRIMARY KEY (id);
ALTER TABLE spac_ct_aplicaciones
    ADD CONSTRAINT pk_ct_aplicaciones PRIMARY KEY (id);
ALTER TABLE spac_ct_frmbusqueda
    ADD CONSTRAINT pk_ct_frmbusqueda PRIMARY KEY (id);
ALTER TABLE spac_ct_frmbusqueda_org
    ADD CONSTRAINT pk_ct_frmbusqorg PRIMARY KEY (id);
ALTER TABLE spac_ct_reglas
    ADD CONSTRAINT pk_spac_ct_reglas PRIMARY KEY (id);
ALTER TABLE spac_docobj
    ADD CONSTRAINT pk_spac_docobj PRIMARY KEY (id);
ALTER TABLE spac_dt_intervinientes
    ADD CONSTRAINT pk_dt_intervs PRIMARY KEY (id);
ALTER TABLE spac_exp_relacionados
    ADD CONSTRAINT pk_exp_rels PRIMARY KEY (id);
ALTER TABLE spac_infotramite
    ADD CONSTRAINT pk_infotramite PRIMARY KEY (id);
ALTER TABLE spac_p_blp
    ADD CONSTRAINT pk_spac_p_blp PRIMARY KEY (id);
ALTER TABLE spac_p_frmfases
    ADD CONSTRAINT pk_spac_p_frmfases PRIMARY KEY (id);
ALTER TABLE spac_p_frmtramites
    ADD CONSTRAINT pk_p_frmtramites PRIMARY KEY (id);
ALTER TABLE spac_p_plazos
    ADD CONSTRAINT pk_spac_p_plazos PRIMARY KEY (id);
ALTER TABLE spac_p_relplazos
    ADD CONSTRAINT pk_p_relplazos PRIMARY KEY (id, tp_obj, id_obj);
ALTER TABLE spac_p_sincnodo
    ADD CONSTRAINT pk_spac_p_sincnodo PRIMARY KEY (id);
ALTER TABLE spac_p_eventos
    ADD CONSTRAINT pk_spac_peventos PRIMARY KEY (evento, tp_obj, orden, id_obj);
ALTER TABLE spac_procesos
    ADD CONSTRAINT pk_spac_procesos PRIMARY KEY (id);
ALTER TABLE spac_s_bloqueos
    ADD CONSTRAINT pk_spac_s_bloqueos PRIMARY KEY (tp_obj, id_obj, numexp);
ALTER TABLE spac_s_sesiones
    ADD CONSTRAINT pk_spac_s_sesiones PRIMARY KEY (id);
ALTER TABLE spac_s_vars
    ADD CONSTRAINT pk_spac_s_vars PRIMARY KEY (id);
ALTER TABLE spac_sincnodo
    ADD CONSTRAINT pk_spac_sincnodo PRIMARY KEY (id);
ALTER TABLE spac_ss_funciones
    ADD CONSTRAINT pk_ss_funciones PRIMARY KEY (id);
ALTER TABLE spac_ss_permisos
    ADD CONSTRAINT pk_ss_permisos PRIMARY KEY (id);
ALTER TABLE spac_ss_supervision
    ADD CONSTRAINT pk_ss_supervision PRIMARY KEY (id);
ALTER TABLE spac_tbl_001
    ADD CONSTRAINT pk_spac_tbl_001 PRIMARY KEY (id);
ALTER TABLE spac_tbl_002
    ADD CONSTRAINT pk_spac_tbl_002 PRIMARY KEY (id);
ALTER TABLE spac_tbl_003
    ADD CONSTRAINT pk_spac_tbl_003 PRIMARY KEY (id);
ALTER TABLE spac_tbl_004
    ADD CONSTRAINT pk_spac_tbl_004 PRIMARY KEY (id);
ALTER TABLE spac_tbl_005
    ADD CONSTRAINT pk_spac_tbl_005 PRIMARY KEY (id);
ALTER TABLE spac_tbl_006
    ADD CONSTRAINT pk_spac_tbl_006 PRIMARY KEY (id);
ALTER TABLE spac_tbl_007
    ADD CONSTRAINT pk_spac_tbl_007 PRIMARY KEY (id);
ALTER TABLE spac_tbl_008
    ADD CONSTRAINT pk_spac_tbl_008 PRIMARY KEY (id);
ALTER TABLE spac_tbl_009
    ADD CONSTRAINT pk_spac_tbl_009 PRIMARY KEY (id);
ALTER TABLE spac_vldtbl_tipo_notif
	ADD CONSTRAINT pk_spac_tipo_notif PRIMARY KEY (id);
ALTER TABLE spac_ct_informes_org
	ADD CONSTRAINT pk_ctinformes_org PRIMARY KEY (id);

ALTER TABLE spac_vldtbl_acts_funcs
    ADD CONSTRAINT pk_vt_acts_funcs PRIMARY KEY (id);
ALTER TABLE spac_vldtbl_efec_slncio
	ADD CONSTRAINT pk_vt_efec_slncio PRIMARY KEY (id);
ALTER TABLE spac_vldtbl_forma_inic
	ADD CONSTRAINT pk_vt_forma_inic PRIMARY KEY (id);
ALTER TABLE spac_vldtbl_mats_comp
	ADD CONSTRAINT pk_vt_mats_comp PRIMARY KEY (id);
ALTER TABLE spac_vldtbl_recursos
	ADD CONSTRAINT pk_vt_recursos PRIMARY KEY (id);
ALTER TABLE spac_vldtbl_sist_productores
    ADD CONSTRAINT pk_vt_sistprod PRIMARY KEY (id);
ALTER TABLE spac_vldtbl_sit_tlm
    ADD CONSTRAINT pk_vt_sit_tlm PRIMARY KEY (id);
ALTER TABLE spac_vldtbl_tiporeg
	ADD CONSTRAINT pk_vt_tiporeg PRIMARY KEY (id);
ALTER TABLE spac_vldtbl_tipos_docs
	ADD CONSTRAINT pk_vt_tipos_docs PRIMARY KEY (id);
ALTER TABLE spac_vldtbl_tipos_tram
	ADD CONSTRAINT pk_vt_tipos_tram PRIMARY KEY (id);
ALTER TABLE spac_tramitaciones_agrupadas
    ADD CONSTRAINT pk_tram_agrup PRIMARY KEY (id);
ALTER TABLE spac_tramitcs_agrupadas_exps
    ADD CONSTRAINT pk_tram_agrup_exps PRIMARY KEY (id);
ALTER TABLE spac_vars
    ADD CONSTRAINT pk_spac_vars PRIMARY KEY (id);
ALTER TABLE spac_tramites
    ADD CONSTRAINT pk_tramites PRIMARY KEY (id);
ALTER TABLE spac_avisos_electronicos
    ADD CONSTRAINT pk_avisos_elec PRIMARY KEY (id_aviso);
ALTER TABLE spac_p_plantillas
    ADD CONSTRAINT pk_p_plantillas PRIMARY KEY (id);
ALTER TABLE spac_dt_tramites
    ADD CONSTRAINT spac_pk_dttramites PRIMARY KEY (id);
ALTER TABLE spac_registros_es
    ADD CONSTRAINT spac_pk_registros_es PRIMARY KEY (id);
ALTER TABLE spac_ss_sustitucion
	ADD CONSTRAINT pk_ss_sustitucion PRIMARY KEY (id);
ALTER TABLE spac_ss_fechsustituciones
	ADD CONSTRAINT pk_ss_fechsust PRIMARY KEY (id);
ALTER TABLE spac_ss_sustitucionfecha
	ADD CONSTRAINT pk_ss_sustfecha PRIMARY KEY (id);
ALTER TABLE spac_numexp_contador
	ADD CONSTRAINT pk_numexp_cont PRIMARY KEY (id_pcd);
ALTER TABLE spac_ct_informes
    ADD CONSTRAINT pk_ct_informes PRIMARY KEY (id);
ALTER TABLE spac_p_informes
    ADD CONSTRAINT pk_p_informes PRIMARY KEY (id_obj, tp_obj, id_informe);
ALTER TABLE spac_ct_jerarquias
	ADD CONSTRAINT pk_spac_jerarquias PRIMARY KEY (id);
ALTER TABLE spac_ayudas
	ADD CONSTRAINT pk_spac_ayudas PRIMARY KEY (id);
ALTER TABLE SPAC_PERMISOS
	ADD CONSTRAINT PK_SPAC_PERMISOS PRIMARY KEY (TP_OBJ, ID_OBJ, ID_RESP , PERMISO) ;


--
-- Restricciones
--
--ALTER TABLE spac_ct_fases
--    ADD CONSTRAINT u_ct_fases UNIQUE (nombre);
--ALTER TABLE spac_ct_reglas
--    ADD CONSTRAINT u_ct_reglas_clase UNIQUE (clase);
--ALTER TABLE spac_ct_reglas
--    ADD CONSTRAINT u_ct_reglas_nombre UNIQUE (nombre);
--ALTER TABLE spac_ct_tpdoc
--    ADD CONSTRAINT u_ct_tpdoc UNIQUE (nombre);
--ALTER TABLE spac_ct_tramites
--    ADD CONSTRAINT u_ct_tramites UNIQUE (nombre);
--ALTER TABLE spac_ss_funciones
--    ADD CONSTRAINT u_spac_ss_funciones UNIQUE (uid_usr, funcion);
--ALTER TABLE spac_ss_permisos
--    ADD CONSTRAINT u_spac_ss_permisos UNIQUE (uid_usr, permiso, id_pcd);
--ALTER TABLE spac_ct_aplicaciones
--    ADD CONSTRAINT spac_ct_aplicaciones_nom_key UNIQUE (nombre);
--ALTER TABLE spac_ct_frmbusqueda
--    ADD CONSTRAINT spac_ct_frmbusqueda_desc_key UNIQUE (descripcion);
--ALTER TABLE spac_ss_supervision
--    ADD CONSTRAINT u_spac_ss_supervision UNIQUE (uid_supervisor, uid_supervisado);
--ALTER TABLE spac_ss_sustitucion
--    ADD CONSTRAINT u_spac_ss_sustitucion UNIQUE (uid_sustituto, uid_sustituido);
--ALTER TABLE spac_ss_sustitucionfecha
--    ADD CONSTRAINT u_spac_ss_sustitucionfecha UNIQUE (id_sustitucion, id_fechsustitucion);

--
-- Índices
--
CREATE INDEX ix_avisos_elec02 ON spac_avisos_electronicos (id_aviso, id_destinatario, estado_aviso, tipo_destinatario);
CREATE INDEX ix_ct_pcds ON spac_ct_procedimientos (nombre);
CREATE INDEX ix_p_pcds_grp ON spac_p_procedimientos (id_group);
CREATE INDEX ix_p_pcds_nombre ON spac_p_procedimientos (nombre);
CREATE UNIQUE INDEX ui_ct_fstr ON spac_ct_fstr (id_fase, id_tramite);
CREATE UNIQUE INDEX ui_ct_trtd ON spac_ct_trtd (id_tpdoc, id_tramite);
CREATE UNIQUE INDEX ui_p_fstd ON spac_p_fstd (id_fase, id_tpdoc);

CREATE INDEX ix_exps_numexp ON spac_expedientes (numexp);
CREATE INDEX ix_dtdocs_numexp ON spac_dt_documentos (numexp);
CREATE INDEX ix_dttram_numexp ON spac_dt_tramites (numexp);
CREATE INDEX ix_dttram_idtrex ON  spac_dt_tramites (id_tram_exp);
CREATE INDEX ix_dtint_numexp ON  spac_dt_intervinientes (numexp);
CREATE INDEX ix_reges_numexp ON spac_registros_es (numexp);

CREATE INDEX ix_procs_numexp ON spac_procesos (numexp);
CREATE INDEX ix_procs_idresps ON spac_procesos (id_resp);
CREATE INDEX ix_fases_numexp ON spac_fases (numexp);
CREATE INDEX ix_fases_idresps ON spac_fases (id_resp);
CREATE INDEX ix_trams_numexp ON SPAC_tramites (numexp);
CREATE INDEX ix_trams_idresps ON SPAC_tramites (id_resp);

CREATE INDEX ix_PERMISOS_TPID ON SPAC_PERMISOS (TP_OBJ, ID_OBJ);
CREATE INDEX ix_PERMISOS_PERM ON SPAC_PERMISOS (PERMISO);

