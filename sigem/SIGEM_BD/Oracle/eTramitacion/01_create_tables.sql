ALTER session SET nls_length_semantics = CHAR;

--
-- Table: sgm_au_usuarios
--

CREATE TABLE sgm_au_usuarios (
    nombre character varying(50) NOT NULL,
    apellidos character varying(50) NOT NULL,
    nif character varying(32) NOT NULL,
    correo_electronico character varying(60) NOT NULL,
    usuario character varying(15) NOT NULL,
    password character varying(100) NOT NULL
);


--
-- Table: sgm_cnxusr
--

CREATE TABLE sgm_cnxusr (
    cnif character varying(16) NOT NULL,
    cflags integer NOT NULL,
    cfechahora timestamp NOT NULL
);


--
-- Table: sgm_ctfichhito
--

CREATE TABLE sgm_ctfichhito (
    cguidhito character varying(32) NOT NULL,
    cguidfich character varying(32) NOT NULL,
    ctitulo character varying(128) NOT NULL
);


--
-- Table: sgm_cthitoestexp
--

CREATE TABLE sgm_cthitoestexp (
    cnumexp character varying(32) NOT NULL,
    cguid character varying(32) NOT NULL,
    ccod character varying(32),
    cfecha timestamp NOT NULL,
    cdescr character varying(4000) NOT NULL,
    cinfoaux blob
);


--
-- Table: sgm_cthitohistexp
--

CREATE TABLE sgm_cthitohistexp (
    cnumexp character varying(32) NOT NULL,
    cguid character varying(32) NOT NULL,
    ccod character varying(32),
    cfecha timestamp NOT NULL,
    cdescr character varying(4000) NOT NULL,
    cinfoaux blob
);


--
-- Table: sgm_ctinfoexp
--

CREATE TABLE sgm_ctinfoexp (
    cnumexp character varying(32) NOT NULL,
    cproc character varying(254),
    cfhinicio date NOT NULL,
    cnumregini character varying(32),
    cfhregini date,
    cinfoaux character varying(4000),
    caportacion character(1),
    ccodpres character varying(32),
    estado character varying(1) DEFAULT '0' NOT NULL
);


--
-- Table: sgm_ctintexp
--

CREATE TABLE sgm_ctintexp (
    cnumexp character varying(32) NOT NULL,
    cnif character varying(16) NOT NULL,
    cnom character varying(128),
    cprincipal character(1) NOT NULL,
    cinfoaux character varying(4000)
);


--
-- Table: sgm_ntfichnotif
--

CREATE TABLE sgm_ntfichnotif (
    cguid character varying(32) NOT NULL,
    cguidnotif character varying(32) NOT NULL,
    ccodigo character varying(32),
    crol character varying(32),
    ctitulo character varying(128) NOT NULL
);


--
-- Table: sgm_ntinfonotif
--

CREATE TABLE sgm_ntinfonotif (
    cguid character varying(32) NOT NULL,
    cnifdest character varying(16) NOT NULL,
    cnumreg character varying(32) NOT NULL,
    cfhreg timestamp NOT NULL,
    cnumexp character varying(32) NOT NULL,
    cproc character varying(254) NOT NULL,
    cinfoaux character varying(4000),
    cavisada integer NOT NULL,
    cfhaviso timestamp,
    centregada character(1) NOT NULL,
    cfhentrega timestamp,
    cdirce character varying(128) NOT NULL,
    casuntomce character varying(254) NOT NULL,
    ctextomce character varying(254) NOT NULL,
    ctitulo character varying(128) NOT NULL,
    ctitulode character varying(128) NOT NULL,
    ctituloar character varying(128) NOT NULL
);


--
-- Table: sgm_pe_al12nsec
--

CREATE TABLE sgm_pe_al12nsec (
    numsec number NOT NULL,
    minimo number NOT NULL,
    maximo number NOT NULL
);


--
-- Table: sgm_pe_al3nsec
--

CREATE TABLE sgm_pe_al3nsec (
    modelo character varying(3) NOT NULL,
    numsec number NOT NULL,
    minimo number NOT NULL,
    maximo number NOT NULL
);


--
-- Table: sgm_pe_liquidaciones
--

CREATE TABLE sgm_pe_liquidaciones (
    referencia character varying(13) NOT NULL,
    id_entidad_emisora character varying(6) NOT NULL,
    id_tasa character varying(3) NOT NULL,
    ejercicio character varying(4),
    vencimiento character varying(8),
    discriminante_periodo character varying(1),
    remesa character varying(2),
    nif character varying(9) NOT NULL,
    importe character varying(12) NOT NULL,
    nrc character varying(22),
    estado character varying(2),
    nombre character varying(512),
    datos_especificos character varying(2048),
    inicio_periodo date,
    fin_periodo date,
    solicitud blob,
    fecha_pago timestamp
);


--
-- Table: sgm_pe_tasas
--

CREATE TABLE sgm_pe_tasas (
    codigo character varying(3) NOT NULL,
    id_entidad_emisora character varying(6) NOT NULL,
    nombre character varying(255),
    tipo character varying(3) NOT NULL,
    modelo character varying(3),
    captura character varying(1),
    datos_especificos character varying(2048)
);


--
-- Table: sgmafconector_autenticacion
--

CREATE TABLE sgmafconector_autenticacion (
    tramiteid character varying(32) NOT NULL,
    conectorid character varying(32) NOT NULL
);


--
-- Table: sgmafsesion_info
--

CREATE TABLE sgmafsesion_info (
    sesionid character varying(32) NOT NULL,
    conectorid character varying(32) NOT NULL,
    fecha_login character varying(19) NOT NULL,
    nombre_solicitante character varying(128) NOT NULL,
    solicitante_id character varying(32) NOT NULL,
    correo_electronico_solicitante character varying(60),
    emisor_certificado_solicitante character varying(256),
    solicitante_certificado_sn character varying(256),
    solicitante_inquality character varying(32),
    razon_social character varying(256),
    cif character varying(9),
    id_entidad character varying(10),
    solicitante_firstname character varying(36),
    solicitante_surname character varying(45),
    solicitante_surname2 character varying(45)
);


--
-- Table: sgmcertificacion
--

CREATE TABLE sgmcertificacion (
    id_fichero character varying(32) NOT NULL,
    id_usuario character varying(32) NOT NULL
);


--
-- Table: sgmctnotificacion
--

CREATE TABLE sgmctnotificacion (
    notificacion_id character varying(32) NOT NULL,
    fecha_notificacion timestamp NOT NULL,
    deu character varying(32),
    servicio_notificaciones_id character varying(32),
    expediente character varying(32),
    descripcion character varying(4000),
    hito_id character varying(32) NOT NULL
);


--
-- Table: sgmctpago
--

CREATE TABLE sgmctpago (
    entidad_emisora_id character varying(32),
    autoliquidacion_id character varying(32),
    pago_id character varying(32) NOT NULL,
    documento_id character varying(32),
    mensaje character varying(4000),
    hito_id character varying(32),
    fecha_pago timestamp,
    expediente character varying(32),
    importe character varying(12)
);


--
-- Table: sgmctsubsanacion
--

CREATE TABLE sgmctsubsanacion (
    subsanacion_id character varying(32) NOT NULL,
    documento_id character varying(32),
    mensaje character varying(4000),
    hito_id character varying(32),
    fecha_subsanacion timestamp,
    expediente character varying(32)
);


--
-- Table: sgmntinfo_documento
--

CREATE TABLE sgmntinfo_documento (
    cnotiexpe character varying(32) NOT NULL,
    cnotinifdest character varying(10) NOT NULL,
    ccodigo character varying(200),
    cguid character varying(100),
	cnotiid character varying(64) NOT NULL,
	ctipodoc integer DEFAULT 1
);


--
-- Table: sgmntinfo_estado_noti
--

CREATE TABLE sgmntinfo_estado_noti (
    cguid integer NOT NULL,
    cuidsisnot character varying(100),
    cdescripcion character varying(100)
);


--
-- Table: sgmntinfo_notificacion
--

CREATE TABLE sgmntinfo_notificacion (
    cguid character varying(64),
    cnifdest character varying(10) NOT NULL,
    cnumreg character varying(16),
    cfhreg date,
    cnumexp character varying(32) NOT NULL,
    cproc character varying(50),
    cestado integer,
    cfhestado date,
    cfhentrega date,
    cusuario character varying(10),
    ctipocorrespondencia character varying(10),
    corganismo character varying(100),
    casunto character varying(100),
    ctipo character varying(20),
    ctexto character varying(1000),
    cnombredest character varying(100),
    capellidosdest character varying(150),
    ccorreodest character varying(200),
    cidioma character varying(20),
    ctipovia character varying(10),
    cvia character varying(200),
    cnumerovia character varying(10),
    cescaleravia character varying(10),
    cpisovia character varying(10),
    cpuertavia character varying(10),
    ctelefono character varying(20),
    cmunicipio character varying(100),
    cprovincia character varying(100),
    ccp character varying(10),
    cerror character varying(100),
	cnotiid character varying(64) NOT NULL,
	csistemaid character varying(32),
	cdeu character varying(20),
	ctfnomovil character varying(14)
);


--
-- Table: sgmrdedocumentos
--

CREATE TABLE sgmrdedocumentos (
    guid character varying(64) NOT NULL,
    contenido blob NOT NULL,
    filehash character varying(64) NOT NULL,
    extension character varying(16) NOT NULL,
    fecha timestamp NOT NULL
);


--
-- Table: sgmrdetiposmime
--

CREATE TABLE sgmrdetiposmime (
    extension character varying(8) NOT NULL,
    tipomime character varying(64) NOT NULL
);


--
-- Table: sgmrtcatalogo_conectores
--

CREATE TABLE sgmrtcatalogo_conectores (
    conectorid character varying(32) NOT NULL,
    descripcion character varying(256) NOT NULL,
    tipo character varying(32) NOT NULL
);


--
-- Table: sgmrtcatalogo_docstramite
--

CREATE TABLE sgmrtcatalogo_docstramite (
    tramite_id character varying(32) NOT NULL,
    documento_id character varying(32) NOT NULL,
    codigo_documento character varying(32) NOT NULL,
    documento_obligatorio character varying(1) NOT NULL
);


--
-- Table: sgmrtcatalogo_documentos
--

CREATE TABLE sgmrtcatalogo_documentos (
    id character varying(32) NOT NULL,
    descripcion character varying(256) NOT NULL,
    extension character varying(128) NOT NULL,
    conector_firma character varying(32),
    conector_contenido character varying(32)
);


--
-- Table: sgmrtcatalogo_organos
--

CREATE TABLE sgmrtcatalogo_organos (
    organo character varying(16) NOT NULL,
    descripcion character varying(250) NOT NULL
);

--
-- Table: sgmrtcatalogo_tipoconector
--

CREATE TABLE sgmrtcatalogo_tipoconector (
    tipoid character varying(32) NOT NULL,
    descripcion character varying(256) NOT NULL
);


--
-- Table: sgmrtcatalogo_tramites
--

CREATE TABLE sgmrtcatalogo_tramites (
    id character varying(32) NOT NULL,
    asunto character varying(32) NOT NULL,
    descripcion character varying(256) NOT NULL,
    organo character varying(16) NOT NULL,
    limite_documentos character varying(1) NOT NULL,
    firma character varying(1) DEFAULT '1' NOT NULL,
    oficina character varying(32) NOT NULL,
    id_procedimiento character varying(32)
);


--
-- Table: sgmrtregistro
--

CREATE TABLE sgmrtregistro (
    numero_registro character varying(16) NOT NULL,
    fecha_registro timestamp NOT NULL,
    emisor_id character varying(128) NOT NULL,
    nombre character varying(128) NOT NULL,
    correo_electronico character varying(60),
    asunto character varying(200) NOT NULL,
    organo character varying(16) NOT NULL,
    tipo_emisor_id character varying(1) NOT NULL,
    info_adicional blob,
    estado character varying(1) NOT NULL,
    oficina character varying(32),
    numero_expediente character varying(32),
	fecha_efectiva timestamp
);


--
-- Table: sgmrtregistro_documentos
--

CREATE TABLE sgmrtregistro_documentos (
    numero_registro character varying(16) NOT NULL,
    codigo character varying(32) NOT NULL,
    guid character varying(32) NOT NULL,
    conector_firma character varying(32),
    conector_documento character varying(32)
);


--
-- Table: sgmrtregistro_secuencia
--

CREATE TABLE sgmrtregistro_secuencia (
    anyo character varying(4) NOT NULL,
    etiqueta character varying(8) NOT NULL,
    secuencia integer NOT NULL
);


--
-- Table: sgmrttmp_documentos
--

CREATE TABLE sgmrttmp_documentos (
    sesionid character varying(64) NOT NULL,
    guid character varying(32) NOT NULL,
    fecha timestamp NOT NULL
);


--
-- Table: sgmrtcatalogo_calendario (configuracion del calendario de tramitacion electronica)
--

CREATE TABLE sgmrtcatalogo_calendario (
    laborables character varying(13) NOT NULL,
    hora_inicio character varying(2),
    minuto_inicio character varying(2),
    hora_fin character varying(2),
    minuto_fin character varying(2)
);


--
-- Table: sgmrtcatalogo_diascalendario (configuracion de los dias festivos del calendario de tramitacion electronica)
--

CREATE TABLE sgmrtcatalogo_diascalendario (
    id integer NOT NULL,
    fecha character varying(5) NOT NULL,
    descripcion character varying(256) NOT NULL,
    fijo integer NOT NULL
);

---
--- Table: sgmrtregistro_docs_csv (Asociacion entre el guid de un documento y el csv generado para dicho documento)
---
CREATE TABLE sgmrtregistro_docs_csv (
	guid character varying (32) NOT NULL,
	csv character varying (128) NOT NULL
);
