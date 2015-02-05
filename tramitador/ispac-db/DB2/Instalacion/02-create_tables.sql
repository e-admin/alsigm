--
-- Tablas del Publicador
--
CREATE TABLE pub_acciones (
    id INTEGER NOT NULL,
    nombre VARCHAR(255),
    clase VARCHAR(255),
    activa SMALLINT DEFAULT 0,
    descripcion VARCHAR(255),
    tipo SMALLINT DEFAULT 1
);
CREATE TABLE pub_aplicaciones_hitos (
    id INTEGER NOT NULL,
    nombre VARCHAR(255),
    activa SMALLINT
);
CREATE TABLE pub_condiciones (
    id INTEGER NOT NULL,
    nombre VARCHAR(255),
    clase VARCHAR(255),
    descripcion VARCHAR(255)
);
CREATE TABLE pub_errores (
    id_hito INTEGER NOT NULL,
    id_aplicacion INTEGER NOT NULL,
    id_sistema VARCHAR(32) NOT NULL,
    id_objeto VARCHAR(64) NOT NULL,
    descripcion VARCHAR(255),
    id_error INTEGER
);
CREATE TABLE pub_hitos_activos (
    id_hito INTEGER NOT NULL,
    id_pcd INTEGER,
    id_fase INTEGER,
    id_tramite INTEGER,
    tipo_doc INTEGER,
    id_objeto VARCHAR(64),
    id_evento INTEGER,
    estado SMALLINT,
    id_aplicacion INTEGER NOT NULL,
    ip_maquina VARCHAR(15),
    fecha TIMESTAMP,
    id_sistema VARCHAR(32) NOT NULL,
    info_aux CLOB,
    id_info INTEGER
);
CREATE TABLE pub_reglas (
    id INTEGER NOT NULL,
    id_pcd INTEGER,
    id_fase INTEGER,
    id_tramite INTEGER,
    tipo_doc INTEGER,
    id_evento INTEGER,
    id_accion INTEGER,
    id_condicion INTEGER,
    atributos CLOB,
    id_aplicacion INTEGER,
    orden INTEGER,
    id_info INTEGER
);
CREATE TABLE pub_ultimo_hito_tratado (
    id_sistema VARCHAR(32) NOT NULL,
    id_hito INTEGER
);

--
-- Tablas internas del tramitador
--
CREATE TABLE spac_anotaciones (
    id INTEGER NOT NULL,
    id_exp INTEGER,
    id_fase INTEGER,
    id_tramite INTEGER,
    fecha TIMESTAMP,
    autor VARCHAR(250),
    texto VARCHAR(4000)
);
CREATE TABLE spac_avisos_electronicos (
    id_aviso INTEGER NOT NULL,
    id_proc INTEGER,
    id_resp INTEGER,
    tipo_destinatario SMALLINT,
    id_destinatario INTEGER,
    fecha DATE,
    id_expediente VARCHAR(64),
    id_fase INTEGER,
    id_tramite INTEGER,
    id_fase_pcd INTEGER,
    id_tramite_pcd INTEGER,
    estado_aviso SMALLINT,
    mensaje VARCHAR(254),
    tipo_aviso VARCHAR(64),
    id_mproc INTEGER,
    uid_resp VARCHAR(250),
    uid_destinatario VARCHAR(250)
);

CREATE TABLE spac_calendarios
(
  id INTEGER NOT NULL,
  calendario CLOB,
  nombre VARCHAR(256)

);

CREATE TABLE spac_ct_aplicaciones (
    id INTEGER NOT NULL,
    nombre VARCHAR(100),
    descripcion VARCHAR(250),
    clase VARCHAR(250),
    pagina VARCHAR(250),
    parametros CLOB,
    formateador VARCHAR(250),
	xml_formateador CLOB,
    frm_jsp CLOB,
    frm_version SMALLINT,
    ent_principal_id INTEGER,
    ent_principal_nombre VARCHAR(100),
	documentos  VARCHAR(2) DEFAULT 'NO'
);

CREATE TABLE spac_ct_entidades (
    id INTEGER NOT NULL,
    tipo SMALLINT,
    nombre VARCHAR(100),
    campo_pk VARCHAR(100),
    campo_numexp VARCHAR(100),
    schema_expr VARCHAR(100),
    frm_edit INTEGER,
    descripcion VARCHAR(250),
    sec_pk VARCHAR(100),
    definicion CLOB,
    frm_jsp CLOB,
    fecha TIMESTAMP
);
CREATE TABLE spac_ct_entidades_resources (
    id INTEGER NOT NULL,
    id_ent INTEGER NOT NULL,
    idioma VARCHAR(5),
    clave VARCHAR(250),
    valor VARCHAR(250)
);
CREATE TABLE spac_ct_fases (
    id INTEGER NOT NULL,
    nombre VARCHAR(250),
    orden INTEGER,
    cod_fase VARCHAR(16),
    falta TIMESTAMP,
    descripcion VARCHAR(1024),
    observaciones VARCHAR(500),
    autor VARCHAR(64)
);
CREATE TABLE spac_ct_frmbusqueda (
    id INTEGER NOT NULL,
    descripcion VARCHAR(250),
    autor VARCHAR(100),
    fecha TIMESTAMP,
    frm_bsq CLOB,
    tipo SMALLINT
);

CREATE TABLE spac_ct_frmbusqueda_org (
  id INTEGER NOT NULL,
  id_searchfrm INTEGER,
  uid_usr VARCHAR(32)
);

CREATE TABLE spac_ct_fstr (
    id INTEGER NOT NULL,
    id_fase INTEGER,
    id_tramite INTEGER
);
CREATE TABLE spac_ct_pcdorg (
    id INTEGER NOT NULL,
    cod_pcd VARCHAR(100) NOT NULL,
    id_unidad_tramitadora VARCHAR(250) NOT NULL,
    fecha_ini_prod DATE,
    orden INTEGER
);
CREATE TABLE spac_ct_procedimientos (
    id INTEGER NOT NULL,
    cod_pcd VARCHAR(100),
    nombre VARCHAR(250),
    id_padre INTEGER,
    titulo VARCHAR(254),
    objeto VARCHAR(254),
    asunto VARCHAR(512),
    act_func VARCHAR(2),
    mtrs_comp VARCHAR(2),
    sit_tlm VARCHAR(2),
    url VARCHAR(254),
    interesado VARCHAR(64),
    tp_rel VARCHAR(4),
    org_rsltr VARCHAR(80),
    forma_inic VARCHAR(1),
    plz_resol INTEGER,
    unid_plz VARCHAR(1),
    finicio DATE,
    ffin DATE,
    efec_silen VARCHAR(1),
    fin_via_admin VARCHAR(2),
    recursos VARCHAR(500),
    fcatalog DATE,
    autor VARCHAR(64),
    estado VARCHAR(1),
    nversion SMALLINT,
    observaciones VARCHAR(500),
    lugar_present VARCHAR(500),
    cnds_ecnmcs VARCHAR(500),
    ingresos VARCHAR(1024),
    f_cbr_pgo VARCHAR(1024),
    infr_sanc VARCHAR(1024),
    calendario VARCHAR(1024),
    dscr_tram VARCHAR(1024),
    normativa VARCHAR(1024),
    cnd_particip VARCHAR(500),
    documentacion CLOB,
    grupos_delegacion VARCHAR(1000),
    cod_sistema_productor VARCHAR(2) DEFAULT '00',
    mapeo_rt CLOB
);
CREATE TABLE spac_ct_reglas (
    id INTEGER NOT NULL,
    nombre VARCHAR(100),
    descripcion VARCHAR(250),
    clase VARCHAR(250),
    tipo SMALLINT
);
CREATE TABLE spac_ct_tpdoc (
    id INTEGER NOT NULL,
    nombre VARCHAR(100),
    cod_tpdoc VARCHAR(16),
    tipo VARCHAR(64),
    autor VARCHAR(64),
    descripcion VARCHAR(1024),
    fecha TIMESTAMP,
    tiporeg VARCHAR(64),
    observaciones VARCHAR(512)
);
CREATE TABLE spac_ct_tramites (
    id INTEGER NOT NULL,
    nombre VARCHAR(250),
    cod_tram VARCHAR(16),
    descripcion VARCHAR(1024),
    tipo VARCHAR(32),
    ordenacion VARCHAR(2),
    observaciones VARCHAR(512),
    fcreacion TIMESTAMP,
    autor VARCHAR(64),
    id_subproceso INTEGER
);
CREATE TABLE spac_ct_trtd (
    id INTEGER NOT NULL,
    id_tramite INTEGER,
    id_tpdoc INTEGER
);
CREATE TABLE spac_ctos_firma (
    id INTEGER NOT NULL,
    id_instancia_circuito INTEGER,
    fecha DATE,
    id_documento INTEGER,
    id_circuito INTEGER,
    id_paso INTEGER,
    id_firmante VARCHAR(128),
    nombre_firmante VARCHAR(32),
    estado SMALLINT
);

CREATE TABLE spac_ctos_firma_cabecera (
    id_circuito INTEGER NOT NULL,
    num_pasos SMALLINT,
    descripcion VARCHAR(256),
	tipo SMALLINT,
	sistema VARCHAR(64),
	secuencia VARCHAR(64),
	aplicacion VARCHAR(64)
);
CREATE TABLE spac_p_ctosfirma (
    id INTEGER NOT NULL,
    id_circuito INTEGER,
		id_pcd INTEGER
);
CREATE TABLE spac_ctos_firma_detalle (
    id INTEGER NOT NULL,
    id_circuito INTEGER,
    id_paso INTEGER,
    id_firmante VARCHAR(128),
    nombre_firmante VARCHAR(32),
	tipo_notif VARCHAR(2),
	dir_notif VARCHAR(64),
	tipo_firmante VARCHAR(16)
);
CREATE TABLE spac_docobj (
    id INTEGER NOT NULL,
    tam INTEGER,
    obj BLOB(1G),
    mimetype VARCHAR(40)
);
CREATE TABLE spac_dt_documentos (
    id INTEGER NOT NULL,
    numexp VARCHAR(30),
    fdoc TIMESTAMP,
    nombre VARCHAR(100),
    autor VARCHAR(250),
    id_fase INTEGER,
    id_tramite INTEGER,
    id_tpdoc INTEGER,
    tp_reg VARCHAR(16),
    nreg VARCHAR(64),
    freg TIMESTAMP,
    infopag VARCHAR(100),
    id_fase_pcd INTEGER,
    id_tramite_pcd INTEGER,
    estado VARCHAR(16),
    origen VARCHAR(250),
    descripcion VARCHAR(250),
    origen_id VARCHAR(20),
    destino VARCHAR(250),
    autor_info VARCHAR(250),
    estadofirma VARCHAR(2),
	id_notificacion VARCHAR(64),
    estadonotificacion VARCHAR(2),
    destino_id VARCHAR(20),
    fnotificacion TIMESTAMP,
    faprobacion TIMESTAMP,
    origen_tipo VARCHAR(1),
    destino_tipo VARCHAR(1),
    id_plantilla INTEGER,
    bloqueo character(2),
    repositorio VARCHAR(8),
    extension VARCHAR(64),
    ffirma TIMESTAMP,
    infopag_rde VARCHAR(256),
    extension_rde VARCHAR(64),
 	id_reg_entidad INTEGER,
	id_entidad INTEGER,
	id_proceso_firma CLOB,
	id_circuito INTEGER
);
CREATE TABLE spac_dt_intervinientes (
    id INTEGER NOT NULL,
    id_ext VARCHAR(32),
    numexp VARCHAR(30),
    rol VARCHAR(4),
    tipo SMALLINT,
    tipo_persona VARCHAR(1),
    ndoc VARCHAR(12),
    nombre VARCHAR(250),
    iddireccionpostal VARCHAR(32),
    dirnot VARCHAR(250),
    email VARCHAR(250),
    c_postal VARCHAR(10),
    localidad VARCHAR(150),
    caut VARCHAR(150),
    tfno_fijo VARCHAR(32),
    tfno_movil VARCHAR(32),
    tipo_direccion character(1),
    direcciontelematica VARCHAR(60)
);
CREATE TABLE spac_dt_tramites (
    id INTEGER NOT NULL,
    numexp VARCHAR(30),
    nombre VARCHAR(250),
    estado SMALLINT,
    id_tram_pcd INTEGER,
    id_fase_pcd INTEGER,
    id_fase_exp INTEGER,
    id_tram_exp INTEGER,
    id_tram_ctl INTEGER,
    num_acto VARCHAR(16),
    cod_acto VARCHAR(16),
    estado_info VARCHAR(100),
    fecha_inicio TIMESTAMP,
    fecha_cierre TIMESTAMP,
    fecha_limite TIMESTAMP,
    fecha_fin TIMESTAMP,
    fecha_inicio_plazo TIMESTAMP,
    plazo INTEGER,
    uplazo VARCHAR(1),
    observaciones VARCHAR(254),
    descripcion VARCHAR(100),
    und_resp VARCHAR(250),
    fase_pcd VARCHAR(250),
    id_subproceso INTEGER,
    id_resp_closed VARCHAR(250)
);
CREATE TABLE spac_registros_es(
    id INTEGER NOT NULL,
    numexp VARCHAR(30),
	nreg VARCHAR(32),
	freg TIMESTAMP,
	asunto CLOB,
	id_interesado VARCHAR(32),
	interesado VARCHAR(128),
	tp_reg VARCHAR(16),
	id_tramite INTEGER,
	origino_expediente VARCHAR(2)
);
CREATE TABLE spac_exp_relacionados (
    id INTEGER NOT NULL,
    numexp_padre VARCHAR(30) NOT NULL,
    numexp_hijo VARCHAR(30) NOT NULL,
    relacion VARCHAR(64)
);
CREATE TABLE spac_expedientes (
    id INTEGER NOT NULL,
    id_pcd INTEGER,
    numexp VARCHAR(30),
    referencia_interna VARCHAR(30),
    nreg VARCHAR(64),
    freg TIMESTAMP,
    estadoinfo VARCHAR(128),
    festado TIMESTAMP,
    nifciftitular VARCHAR(16),
    idtitular VARCHAR(32),
    iddireccionpostal VARCHAR(32),
    domicilio VARCHAR(128),
    ciudad VARCHAR(64),
    regionpais VARCHAR(64),
    cpostal VARCHAR(5),
    identidadtitular VARCHAR(128),
    tipopersona VARCHAR(1),
    roltitular VARCHAR(4),
    asunto VARCHAR(512),
    finicioplazo TIMESTAMP,
    poblacion VARCHAR(64),
    municipio VARCHAR(64),
    localizacion VARCHAR(256),
    exprelacionados VARCHAR(256),
    codprocedimiento VARCHAR(16),
    nombreprocedimiento VARCHAR(128),
    plazo INTEGER,
    uplazo VARCHAR(1),
    formaterminacion VARCHAR(1),
    utramitadora VARCHAR(256),
    funcionactividad VARCHAR(80),
    materias VARCHAR(2),
    servpresactuaciones VARCHAR(128),
    tipodedocumental VARCHAR(16),
    palabrasclave VARCHAR(64),
    fcierre TIMESTAMP,
    estadoadm VARCHAR(128),
    hayrecurso VARCHAR(2),
    efectosdelsilencio VARCHAR(1),
    fapertura TIMESTAMP,
    observaciones VARCHAR(256),
    idunidadtramitadora VARCHAR(250),
    idproceso INTEGER,
    tipodireccioninteresado VARCHAR(16),
    nversion VARCHAR(16),
    idseccioniniciadora VARCHAR(64),
    seccioniniciadora VARCHAR(250),
    tfnofijo VARCHAR(32),
    tfnomovil VARCHAR(32),
    direcciontelematica VARCHAR(60)
);

CREATE TABLE spac_fases (
    id INTEGER NOT NULL,
	id_fase_bpm VARCHAR(64),
    id_exp INTEGER,
    id_pcd INTEGER,
    id_fase INTEGER,
    numexp VARCHAR(30),
    fecha_inicio DATE,
    fecha_limite DATE,
    estado SMALLINT,
    observaciones VARCHAR(4000),
    id_resp VARCHAR(250),
	resp VARCHAR(250),
    id_resp_sec VARCHAR(250),
	resp_sec VARCHAR(250),
	estado_plazo INTEGER,
	num_dias_plazo INTEGER,
	id_calendario INTEGER,
	tipo SMALLINT,
	fecha_inicio_plazo DATE,
	estado_anterior SMALLINT
);

CREATE TABLE spac_gestion_aplicaciones (
    id INTEGER NOT NULL,
    nombre_aplicacion_gestion VARCHAR(100),
    enlace VARCHAR(50),
    clsid VARCHAR(50),
    codebase VARCHAR(50)
);
CREATE TABLE spac_gestion_fases (
    id INTEGER NOT NULL,
    id_pcd INTEGER NOT NULL,
    id_fase INTEGER,
    id_aplicacion_gestion INTEGER NOT NULL
);
CREATE TABLE spac_hitos (
    id INTEGER NOT NULL,
    id_exp INTEGER,
    id_fase INTEGER,
    id_tramite INTEGER,
    hito INTEGER,
    fecha TIMESTAMP,
    fecha_limite TIMESTAMP,
    info CLOB,
    autor VARCHAR(250),
    descripcion VARCHAR(250),
    id_info INTEGER
);
CREATE TABLE spac_infotramite (
    id INTEGER NOT NULL,
    numexp VARCHAR(30),
    id_tramite INTEGER,
    id_ct_tramite INTEGER,
    id_pcd INTEGER,
    id_p_fase INTEGER,
    info CLOB
);
CREATE TABLE spac_infosistema (
	id INTEGER NOT NULL,
  	codigo VARCHAR (16) NOT NULL,
	valor VARCHAR (16) NOT NULL,
	fecha_actualizacion TIMESTAMP NOT NULL
);
CREATE TABLE spac_p_blp (
    id INTEGER NOT NULL,
    nbytes INTEGER,
    bloque BLOB(1G),
    mimetype VARCHAR(40)
);
CREATE TABLE spac_p_entidades (
    id INTEGER NOT NULL,
    id_ent INTEGER,
    id_pcd INTEGER,
    tp_rel INTEGER,
    orden INTEGER,
    frm_edit INTEGER,
	frm_readonly SMALLINT,
	id_rule_frm INTEGER,
	id_rule_visible INTEGER
);
CREATE TABLE spac_p_eventos (
    id_obj INTEGER NOT NULL,
    tp_obj INTEGER NOT NULL,
    evento INTEGER NOT NULL,
    orden INTEGER NOT NULL,
    id_regla INTEGER,
	condicion CLOB
);
CREATE TABLE spac_p_fases (
    id INTEGER NOT NULL,
    id_ctfase INTEGER,
    id_pcd INTEGER,
    nombre VARCHAR(250),
    id_resp VARCHAR(250),
    id_resp_sec VARCHAR(250)
);
CREATE TABLE spac_p_flujos (
    id INTEGER NOT NULL,
    id_flujo_bpm VARCHAR(64),
    id_pcd INTEGER,
    id_origen INTEGER,
    id_destino INTEGER
);
CREATE TABLE spac_p_frmfases (
    id INTEGER NOT NULL,
    id_ent INTEGER,
    id_fase INTEGER,
    frm_edit INTEGER,
	frm_readonly SMALLINT,
	id_rule_frm INTEGER,
	id_rule_visible INTEGER
);
CREATE TABLE spac_p_frmtramites (
    id INTEGER NOT NULL,
    id_ent INTEGER,
    id_tramite INTEGER,
    frm_edit INTEGER,
	frm_readonly SMALLINT,
	id_rule_frm INTEGER,
	id_rule_visible INTEGER
);
CREATE TABLE spac_p_fstd (
    id INTEGER NOT NULL,
    id_fase INTEGER,
    id_tpdoc INTEGER
);
CREATE TABLE spac_p_nodos (
    id INTEGER NOT NULL,
    id_nodo_bpm VARCHAR(64),
    id_pcd INTEGER,
    tipo INTEGER,
    g_info VARCHAR(4000)
);
CREATE TABLE spac_p_plantdoc (
    id INTEGER NOT NULL,
    id_tpdoc INTEGER,
    fecha TIMESTAMP,
    nombre VARCHAR(250),
	cod_plant VARCHAR(16),
    expresion VARCHAR(2000),
    mimetype VARCHAR(60),
    path VARCHAR(250),
    estacion VARCHAR(250)
);
CREATE TABLE spac_p_plantillas (
    id INTEGER NOT NULL,
    id_p_plantdoc INTEGER,
    id_pcd INTEGER
);
CREATE TABLE spac_p_plazos (
    id INTEGER NOT NULL,
    plazo VARCHAR(250)
);
CREATE TABLE spac_p_procedimientos (
    id INTEGER NOT NULL,
    id_pcd_bpm VARCHAR(64),
    nversion SMALLINT,
    nombre VARCHAR(250),
    estado SMALLINT,
    tipo SMALLINT DEFAULT 1,
    id_resp VARCHAR(250),
    id_resp_sec VARCHAR(250),
    id_group INTEGER,
    id_crt VARCHAR(250),
		nombre_crt VARCHAR(250),
    ts_crt DATE,
    id_upd VARCHAR(250),
		nombre_upd VARCHAR(250),
    ts_upd DATE
);
CREATE TABLE spac_p_relplazos (
    id INTEGER NOT NULL,
    tp_obj INTEGER NOT NULL,
    id_obj INTEGER NOT NULL
);
CREATE TABLE spac_p_sincnodo (
    id INTEGER NOT NULL,
    id_pcd INTEGER,
    tipo SMALLINT
);
CREATE TABLE spac_p_tramites (
    id INTEGER NOT NULL,
    id_cttramite INTEGER,
    id_tramite_bpm VARCHAR(64),
    id_pcd INTEGER,
    id_fase INTEGER,
    nombre VARCHAR(250),
		orden INTEGER,
		obligatorio SMALLINT,
    libre SMALLINT,
    id_resp VARCHAR(250),
    id_resp_sec VARCHAR(250),
    id_pcd_sub INTEGER
);
CREATE TABLE spac_p_dep_tramites (
    id INTEGER NOT NULL,
    id_tramite_padre INTEGER NOT NULL,
    id_tramite_hijo INTEGER NOT NULL
);
CREATE TABLE spac_p_versplant (
    id INTEGER NOT NULL,
    id_plant INTEGER,
    fecha TIMESTAMP,
    usuario VARCHAR(250),
    estado SMALLINT
);

CREATE TABLE spac_procesos (
    id INTEGER NOT NULL,
    id_proceso_bpm VARCHAR(64),
		id_pcd INTEGER,
    numexp VARCHAR(30),
    estado SMALLINT,
    fecha_inicio TIMESTAMP,
    fecha_limite DATE,
    fecha_cierre TIMESTAMP,
    id_resp VARCHAR(250),
	resp VARCHAR (250),
    id_resp_sec VARCHAR (250),
	resp_sec VARCHAR (250),
    estado_plazo INTEGER,
	num_dias_plazo INTEGER,
	id_calendario INTEGER,
	tipo SMALLINT,
	fecha_inicio_plazo TIMESTAMP,
	fecha_eliminacion TIMESTAMP,
	estado_anterior SMALLINT
);


CREATE TABLE spac_s_bloqueos (
    id VARCHAR(200),
    tp_obj INTEGER NOT NULL,
    id_obj INTEGER NOT NULL,
    numexp VARCHAR(30) NOT NULL,
    fecha TIMESTAMP
);

CREATE TABLE spac_s_sesiones (
    id VARCHAR(200) NOT NULL,
    fecha TIMESTAMP,
    datos VARCHAR(4000),
    usuario VARCHAR(250),
    hits INTEGER,
    tiempo_op INTEGER,
    tiempo_ss INTEGER,
    host VARCHAR(150),
    keep_alive TIMESTAMP,
    aplicacion VARCHAR(32)
);

CREATE TABLE spac_s_vars (
    id INTEGER NOT NULL,
    nombre VARCHAR(60),
    valor VARCHAR(4000),
    id_ses VARCHAR(200)
);
CREATE TABLE spac_sincnodo (
    id INTEGER NOT NULL,
    id_exp INTEGER,
    id_nodo INTEGER,
    estado VARCHAR(4000),
    id_pcd INTEGER,
    tipo SMALLINT
);
CREATE TABLE spac_ss_funciones (
    id INTEGER NOT NULL,
    uid_usr VARCHAR(120),
    funcion INTEGER
);
CREATE TABLE spac_ss_permisos (
    id INTEGER NOT NULL,
    id_pcd INTEGER,
    uid_usr VARCHAR(120),
    permiso INTEGER
);
CREATE TABLE spac_ss_supervision (
    id INTEGER NOT NULL,
    uid_supervisor VARCHAR(120),
    uid_supervisado VARCHAR(120),
    tipo INTEGER
);
CREATE TABLE spac_tbl_001 (
    id INTEGER NOT NULL,
    valor VARCHAR(1),
    sustituto VARCHAR(64),
    vigente SMALLINT,
	orden SMALLINT
);
CREATE TABLE spac_tbl_002 (
    id INTEGER NOT NULL,
    valor VARCHAR(4),
    sustituto VARCHAR(64),
    vigente SMALLINT,
	orden SMALLINT
);
CREATE TABLE spac_tbl_003 (
    id INTEGER NOT NULL,
    valor VARCHAR(8),
    sustituto VARCHAR(64),
    vigente SMALLINT,
	orden SMALLINT
);
CREATE TABLE spac_tbl_004 (
    id INTEGER NOT NULL,
    valor VARCHAR(8),
    sustituto VARCHAR(64),
    vigente SMALLINT,
	orden SMALLINT
);
CREATE TABLE spac_tbl_005 (
    id INTEGER NOT NULL,
    valor VARCHAR(8),
    sustituto VARCHAR(64),
    vigente SMALLINT,
	orden SMALLINT
);
CREATE TABLE spac_tbl_006 (
    id INTEGER NOT NULL,
    valor VARCHAR(8),
    sustituto VARCHAR(64),
    vigente SMALLINT,
	orden SMALLINT
);
CREATE TABLE spac_tbl_008 (
    id INTEGER NOT NULL,
    valor VARCHAR(8),
    sustituto VARCHAR(64),
    vigente SMALLINT,
	orden SMALLINT
);
CREATE TABLE spac_tbl_009 (
  	id INTEGER NOT NULL,
    valor VARCHAR(2),
    vigente SMALLINT,
	orden SMALLINT
);
CREATE TABLE spac_tramitaciones_agrupadas (
    id INTEGER NOT NULL,
    id_resp VARCHAR(250),
    estado SMALLINT,
    id_fase INTEGER,
    id_ultimo_tramite INTEGER,
    id_ultimo_tipodoc INTEGER,
    id_ultimo_template INTEGER,
    fecha_creacion TIMESTAMP
);
CREATE TABLE spac_tramitcs_agrupadas_exps (
    id INTEGER NOT NULL,
    id_tram_agrup INTEGER NOT NULL,
    numexp VARCHAR(30)
);
CREATE TABLE spac_tramites (
    id INTEGER NOT NULL,
	id_tramite_bpm VARCHAR(64),
    id_exp INTEGER,
    id_pcd INTEGER,
    id_fase_exp INTEGER,
    id_fase_pcd INTEGER,
    id_tramite INTEGER,
    id_cttramite INTEGER,
    numexp VARCHAR(30),
    nombre VARCHAR(250),
    fecha_inicio DATE,
    fecha_limite DATE,
    estado SMALLINT,
    observaciones VARCHAR(4000),
    id_resp VARCHAR(250),
	resp VARCHAR(250),
    id_resp_sec VARCHAR(250),
	resp_sec VARCHAR(250),
    estado_plazo INTEGER,
	num_dias_plazo INTEGER,
	id_calendario INTEGER,
	tipo SMALLINT,
	id_subproceso INTEGER,
	fecha_inicio_plazo DATE,
	estado_anterior SMALLINT
);

CREATE TABLE spac_vars (
    id INTEGER NOT NULL,
    nombre VARCHAR(64),
    valor CLOB,
    descripcion VARCHAR(256)
);
CREATE TABLE spac_vldtbl_acts_funcs (
    id INTEGER NOT NULL,
    valor VARCHAR(2),
    sustituto VARCHAR(64),
    vigente SMALLINT,
	orden SMALLINT
);
CREATE TABLE spac_vldtbl_efec_slncio (
    id INTEGER NOT NULL,
    valor VARCHAR(1),
    sustituto VARCHAR(64),
    vigente SMALLINT,
	orden SMALLINT
);
CREATE TABLE spac_vldtbl_forma_inic (
    id INTEGER NOT NULL,
    valor VARCHAR(1),
    sustituto VARCHAR(64),
    vigente SMALLINT,
	orden SMALLINT
);
CREATE TABLE spac_vldtbl_mats_comp (
    id INTEGER NOT NULL,
    valor VARCHAR(2),
    sustituto VARCHAR(64),
    vigente SMALLINT,
	orden SMALLINT
);
CREATE TABLE spac_vldtbl_recursos (
    id INTEGER NOT NULL,
    valor VARCHAR(2),
    sustituto VARCHAR(64),
    vigente SMALLINT,
	orden SMALLINT
);
CREATE TABLE spac_vldtbl_sist_productores (
    id INTEGER NOT NULL,
    valor VARCHAR(2),
    sustituto VARCHAR(64),
    vigente SMALLINT,
	orden SMALLINT
);
CREATE TABLE spac_vldtbl_sit_tlm (
    id INTEGER NOT NULL,
    valor VARCHAR(2),
    sustituto VARCHAR(64),
    vigente SMALLINT,
	orden SMALLINT
);
CREATE TABLE spac_vldtbl_tiporeg (
    id INTEGER NOT NULL,
    valor VARCHAR(10),
    vigente SMALLINT,
	orden SMALLINT
);
CREATE TABLE spac_vldtbl_tipos_docs (
    id INTEGER NOT NULL,
    valor VARCHAR(64),
    vigente SMALLINT,
	orden SMALLINT
);
CREATE TABLE spac_vldtbl_tipos_tram (
    id INTEGER NOT NULL,
    valor VARCHAR(32),
    vigente SMALLINT,
	orden SMALLINT
);
CREATE TABLE spac_tbl_007 (
    id INTEGER NOT NULL,
    valor VARCHAR(64),
    vigente SMALLINT,
	orden SMALLINT
);

CREATE TABLE spac_vldtbl_tipo_notif (
    id INTEGER NOT NULL,
    valor VARCHAR(2),
    sustituto VARCHAR(64),
    vigente SMALLINT,
	orden SMALLINT
);


CREATE TABLE spac_ss_sustitucion (
	id INTEGER NOT NULL,
	uid_sustituto VARCHAR(120),
	uid_sustituido VARCHAR(120)
);
CREATE TABLE spac_ss_fechsustituciones (
  id INTEGER NOT NULL,
  fecha_inicio DATE,
  fecha_fin DATE,
  descripcion VARCHAR(255)
);
CREATE TABLE spac_ss_sustitucionfecha (
	id INTEGER NOT NULL,
	id_sustitucion INTEGER,
	id_fechsustitucion INTEGER
);

CREATE TABLE spac_numexp_contador (
	anio INTEGER NOT NULL,
	contador INTEGER,
	formato VARCHAR(32),
	id_pcd INTEGER NOT NULL DEFAULT -1
);


CREATE TABLE spac_ct_informes (
	id INTEGER NOT NULL,
	nombre VARCHAR(100),
  	descripcion VARCHAR(255),
  	xml CLOB,
  	fecha TIMESTAMP,
  	tipo INTEGER,
  	filas SMALLINT,
  	columnas SMALLINT,
	frm_params CLOB,
	visibilidad SMALLINT DEFAULT 0
);

CREATE TABLE spac_p_informes (
  id_obj INTEGER NOT NULL,
  tp_obj INTEGER NOT NULL,
  id_informe INTEGER NOT NULL
);

CREATE TABLE spac_ct_frmbusq_informes (
  	id_fmrbusqueda INTEGER NOT NULL,
  	id_informe INTEGER NOT NULL
);

CREATE TABLE spac_ct_informes_org
(
  id          INTEGER  NOT NULL,
  id_informe  INTEGER,
  uid_usr    VARCHAR(32)
);

CREATE TABLE spac_ct_jerarquias (
  id INTEGER NOT NULL,
  id_entidad_padre INTEGER,
  id_entidad_hija INTEGER,
  nombre VARCHAR(64),
  descripcion VARCHAR(4000),
  tipo INTEGER DEFAULT 1
);

CREATE TABLE spac_ayudas
(
	id 			INTEGER				NOT NULL,
	nombre		VARCHAR(100),
	tipo_obj 	INTEGER,
	id_obj 		INTEGER,
	idioma 		VARCHAR(5),
	contenido   CLOB
);


CREATE TABLE SPAC_PERMISOS
(
   tp_obj   INTEGER NOT NULL ,
   id_obj   INTEGER NOT NULL ,
   id_resp  VARCHAR (250) NOT NULL ,
   resp_name VARCHAR (250) NOT NULL ,
   permiso  INTEGER NOT NULL
);
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

