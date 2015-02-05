--
-- Table: sgm_au_usuarios
--

CREATE TABLE sgm_au_usuarios (
    nombre character varying(50) NOT NULL,
    apellidos character varying(50) NOT NULL,
    nif character varying(32) NOT NULL,
    correo_electronico character varying(60) NOT NULL,
    usuario character varying(15) NOT NULL,
    "password" character varying(100) NOT NULL
);


--
-- Table: sgm_cnxusr
--

CREATE TABLE sgm_cnxusr (
    cnif character varying(16) NOT NULL,
    cflags integer NOT NULL,
    cfechahora timestamp without time zone NOT NULL
);


--
-- Table: sgm_ctfichhito
--

CREATE TABLE sgm_ctfichhito (
    cguidhito character varying(32) NOT NULL,
    cguidfich character varying(32) NOT NULL,
    ctitulo character varying(128) NOT NULL
);

COMMENT ON TABLE sgm_ctfichhito IS 'Ficheros de Hitos';


--
-- Table: sgm_cthitoestexp
--

CREATE TABLE sgm_cthitoestexp (
    cnumexp character varying(32) NOT NULL,
    cguid character varying(32) NOT NULL,
    ccod character varying(32),
    cfecha timestamp without time zone NOT NULL,
    cdescr character varying(4096) NOT NULL,
    cinfoaux character varying(4096)
);

COMMENT ON TABLE sgm_cthitoestexp IS 'Hitos de estado de un expediente';


--
-- Table: sgm_cthitohistexp
--

CREATE TABLE sgm_cthitohistexp (
    cnumexp character varying(32) NOT NULL,
    cguid character varying(32) NOT NULL,
    ccod character varying(32),
    cfecha timestamp without time zone NOT NULL,
    cdescr character varying(4096) NOT NULL,
    cinfoaux character varying(4096)
);

COMMENT ON TABLE sgm_cthitohistexp IS 'Historico de Hitos de un Expediente';


--
-- Table: sgm_ctinfoexp
--

CREATE TABLE sgm_ctinfoexp (
    cnumexp character varying(32) NOT NULL,
    cproc character varying(254),
    cfhinicio date NOT NULL,
    cnumregini character varying(32),
    cfhregini date,
    cinfoaux character varying(4096),
    caportacion character(1),
    ccodpres character varying(32),
    estado character varying(1) DEFAULT 0 NOT NULL
);

COMMENT ON TABLE sgm_ctinfoexp IS 'Informacion acerca de un expediente';


--
-- Table: sgm_ctintexp
--

CREATE TABLE sgm_ctintexp (
    cnumexp character varying(32) NOT NULL,
    cnif character varying(16) NOT NULL,
    cnom character varying(128),
    cprincipal character(1) NOT NULL,
    cinfoaux character varying(4096)
);

COMMENT ON TABLE sgm_ctintexp IS 'Interesado de un expediente';


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
    cfhreg timestamp without time zone NOT NULL,
    cnumexp character varying(32) NOT NULL,
    cproc character varying(254) NOT NULL,
    cinfoaux character varying(4096),
    cavisada integer NOT NULL,
    cfhaviso timestamp without time zone,
    centregada character(1) NOT NULL,
    cfhentrega timestamp without time zone,
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
    numsec bigint NOT NULL,
    minimo bigint NOT NULL,
    maximo bigint NOT NULL
);


--
-- Table: sgm_pe_al3nsec
--

CREATE TABLE sgm_pe_al3nsec (
    modelo character varying(3) NOT NULL,
    numsec bigint NOT NULL,
    minimo bigint NOT NULL,
    maximo bigint NOT NULL
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
    solicitud bytea,
    fecha_pago timestamp without time zone
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
    correo_electronico_solicitante character varying(60) NOT NULL,
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

COMMENT ON COLUMN sgmafsesion_info.nombre_solicitante IS 'Nombre del usuario logado';
COMMENT ON COLUMN sgmafsesion_info.cif IS 'Documento de identidad del usuario logado';


--
-- Table: sgmcertificacion
--

CREATE TABLE sgmcertificacion (
    id_fichero character varying(32) NOT NULL,
    id_usuario character varying(32) NOT NULL
);

COMMENT ON COLUMN sgmcertificacion.id_fichero IS 'GUID del fichero en el RDE';
COMMENT ON COLUMN sgmcertificacion.id_usuario IS 'Identificador de usuario';


--
-- Table: sgmctnotificacion
--

CREATE TABLE sgmctnotificacion (
    notificacion_id character varying(32) NOT NULL,
    deu character varying(32),
    servicio_notificaciones_id character varying(32),
    expediente character varying(32),
    descripcion character varying(4096),
    hito_id character varying(32) NOT NULL,
    fecha_notificacion timestamp without time zone NOT NULL
);


--
-- Table: sgmctpago
--

CREATE TABLE sgmctpago (
    entidad_emisora_id character varying(32),
    autoliquidacion_id character varying(32),
    pago_id character varying(32) NOT NULL,
    documento_id character varying(32),
    mensaje character varying(4096),
    hito_id character varying(32),
    expediente character varying(32),
    importe character varying(12),
    fecha_pago timestamp without time zone NOT NULL
);


--
-- Table: sgmctsubsanacion
--

CREATE TABLE sgmctsubsanacion (
    subsanacion_id character varying(32) NOT NULL,
    documento_id character varying(32),
    mensaje character varying(4096),
    hito_id character varying(32),
    expediente character varying(32),
    fecha_subsanacion timestamp without time zone NOT NULL
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

COMMENT ON COLUMN sgmntinfo_documento.ctipodoc IS 'Tipo de documento asociado a la notificacion. 1 Documento anexado. 2 Justificante entrega notificacion';


--
-- Table: sgmntinfo_estado_noti
--

CREATE TABLE sgmntinfo_estado_noti (
    cguid integer NOT NULL,
    cuidsisnot character(100),
    cdescripcion character(100)
);

COMMENT ON COLUMN sgmntinfo_estado_noti.cguid IS 'Indentificador interno del estado';
COMMENT ON COLUMN sgmntinfo_estado_noti.cuidsisnot IS 'Estado equivalente en sisnot';
COMMENT ON COLUMN sgmntinfo_estado_noti.cdescripcion IS 'Descripcion valida para mostrar al usuario';


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
	cnotiid character varying(64),
	csistemaid character varying(32),
	cdeu character varying(20),
	ctfnomovil character varying(14)
);

COMMENT ON COLUMN sgmntinfo_notificacion.cguid IS 'Identificador de la notificacion';
COMMENT ON COLUMN sgmntinfo_notificacion.cnifdest IS 'nif del destinatario de la notificacion';
COMMENT ON COLUMN sgmntinfo_notificacion.cnumreg IS 'numero de registro';
COMMENT ON COLUMN sgmntinfo_notificacion.cfhreg IS 'fecha de registro';
COMMENT ON COLUMN sgmntinfo_notificacion.cnumexp IS 'numero de expediente';
COMMENT ON COLUMN sgmntinfo_notificacion.cproc IS 'procedimiento asociado a la notificacion';
COMMENT ON COLUMN sgmntinfo_notificacion.cestado IS 'estado de la notificacion en la ultima actualizacion';
COMMENT ON COLUMN sgmntinfo_notificacion.cfhestado IS 'fecha de la ultima actualizacion del estado';
COMMENT ON COLUMN sgmntinfo_notificacion.cfhentrega IS 'fecha de creacion de la notificacion en el sistema';
COMMENT ON COLUMN sgmntinfo_notificacion.cusuario IS 'usuario asociado a la notificacion';
COMMENT ON COLUMN sgmntinfo_notificacion.ctipocorrespondencia IS 'Tipo de correspondencia de la notificacion';
COMMENT ON COLUMN sgmntinfo_notificacion.corganismo IS 'organismo responsable de la notificacion. Es la entidad emisora con respecto al sisnot';
COMMENT ON COLUMN sgmntinfo_notificacion.casunto IS 'asunto de la notificacion';
COMMENT ON COLUMN sgmntinfo_notificacion.ctipo IS 'tipo de notificacion';
COMMENT ON COLUMN sgmntinfo_notificacion.ctexto IS 'texto de la notificacion';
COMMENT ON COLUMN sgmntinfo_notificacion.cnombredest IS 'nombre del destinatario';
COMMENT ON COLUMN sgmntinfo_notificacion.capellidosdest IS 'apellidos del destinatario';
COMMENT ON COLUMN sgmntinfo_notificacion.ccorreodest IS 'correo del destinatario';
COMMENT ON COLUMN sgmntinfo_notificacion.cidioma IS 'idioma asociado a la notificacion';
COMMENT ON COLUMN sgmntinfo_notificacion.ctipovia IS 'tipo de via de la direccion';
COMMENT ON COLUMN sgmntinfo_notificacion.cvia IS 'Nombre de la via';
COMMENT ON COLUMN sgmntinfo_notificacion.cnumerovia IS 'numero de la direccion';
COMMENT ON COLUMN sgmntinfo_notificacion.cescaleravia IS 'escalera de la direccion';
COMMENT ON COLUMN sgmntinfo_notificacion.cpisovia IS 'piso de la direccion';
COMMENT ON COLUMN sgmntinfo_notificacion.cpuertavia IS 'puerta de la direccion';
COMMENT ON COLUMN sgmntinfo_notificacion.ctelefono IS 'telefono de la direccion';
COMMENT ON COLUMN sgmntinfo_notificacion.cmunicipio IS 'municipio de la direccion';
COMMENT ON COLUMN sgmntinfo_notificacion.cprovincia IS 'provincia de la direccion';
COMMENT ON COLUMN sgmntinfo_notificacion.ccp IS 'codigo postal de la direccion';
COMMENT ON COLUMN sgmntinfo_notificacion.cnotiid IS 'identificador unico para la notificacion en SIGEM';


--
-- Table: sgmrdedocumentos
--

CREATE TABLE sgmrdedocumentos (
    guid character varying(64) NOT NULL,
    contenido bytea NOT NULL,
    filehash character varying(64) NOT NULL,
    extension character varying(16) NOT NULL,
    fecha timestamp without time zone NOT NULL
);

COMMENT ON TABLE sgmrdedocumentos IS 'Tabla contenedora de documentos';
COMMENT ON COLUMN sgmrdedocumentos.contenido IS 'Contenido binario del documento';
COMMENT ON COLUMN sgmrdedocumentos.filehash IS 'Hash del documento';


--
-- Table: sgmrdetiposmime
--

CREATE TABLE sgmrdetiposmime (
    extension character varying(8) NOT NULL,
    tipomime character varying(64) NOT NULL
);

COMMENT ON TABLE sgmrdetiposmime IS 'Tabla de tipos mime';
COMMENT ON COLUMN sgmrdetiposmime.tipomime IS 'Tipo mime';


--
-- Table: sgmrtcatalogo_conectores
--

CREATE TABLE sgmrtcatalogo_conectores (
    conectorid character varying(32) NOT NULL,
    descripcion character varying(256) NOT NULL,
    tipo character varying(32) NOT NULL
);

COMMENT ON TABLE sgmrtcatalogo_conectores IS 'Tabla de conectores';
COMMENT ON COLUMN sgmrtcatalogo_conectores.tipo IS 'Tipo de conector';


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

COMMENT ON TABLE sgmrtcatalogo_documentos IS 'Tabla de tipos de documentos';
COMMENT ON COLUMN sgmrtcatalogo_documentos.conector_firma IS 'Conector de firma asociado al tipo de documento';
COMMENT ON COLUMN sgmrtcatalogo_documentos.conector_contenido IS 'Conector de contenido asociado al tipo de documento';


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

COMMENT ON TABLE sgmrtcatalogo_tipoconector IS 'Tabla de tipos de conectores';


--
-- Table: sgmrtcatalogo_tramites
--

CREATE TABLE sgmrtcatalogo_tramites (
    id character varying(32) NOT NULL,
    asunto character varying(32) NOT NULL,
    descripcion character varying(256) NOT NULL,
    organo character varying(16) NOT NULL,
    limite_documentos character varying(1) NOT NULL,
    firma character varying(1) DEFAULT 1 NOT NULL,
    oficina character varying(32) NOT NULL,
    id_procedimiento character varying(32)
);

COMMENT ON TABLE sgmrtcatalogo_tramites IS 'Tabla de tramites';


--
-- Table: sgmrtregistro
--

CREATE TABLE sgmrtregistro (
    numero_registro character varying(16) NOT NULL,
    fecha_registro timestamp without time zone NOT NULL,
    emisor_id character varying(128) NOT NULL,
    nombre character varying(128) NOT NULL,
    correo_electronico character varying(60),
    asunto character varying(200) NOT NULL,
    organo character varying(16) NOT NULL,
    tipo_emisor_id character varying(1) NOT NULL,
    info_adicional bytea,
    estado character varying(1) NOT NULL,
    oficina character varying(32),
    numero_expediente character varying(32),
	fecha_efectiva timestamp without time zone
);

COMMENT ON TABLE sgmrtregistro IS 'Tabla de registros';
COMMENT ON COLUMN sgmrtregistro.nombre IS 'Nombre de emisor';
COMMENT ON COLUMN sgmrtregistro.asunto IS 'Asunto del registro';
COMMENT ON COLUMN sgmrtregistro.estado IS 'Estado actual del registro';


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

COMMENT ON TABLE sgmrtregistro_documentos IS 'Tabla que asocia un registro con documentos';


--
-- Table: sgmrtregistro_secuencia
--

CREATE TABLE sgmrtregistro_secuencia (
    anyo character varying(4) NOT NULL,
    etiqueta character varying(8) NOT NULL,
    secuencia numeric NOT NULL
);

COMMENT ON COLUMN sgmrtregistro_secuencia.etiqueta IS 'Etiqueta intermedia';


--
-- Table: sgmrttmp_documentos
--

CREATE TABLE sgmrttmp_documentos (
    sesionid character varying(64) NOT NULL,
    guid character varying(32) NOT NULL,
    fecha timestamp without time zone NOT NULL
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

COMMENT ON COLUMN sgmrtcatalogo_calendario.laborables IS 'Dias laborables de la semana';
COMMENT ON COLUMN sgmrtcatalogo_calendario.hora_inicio IS 'Hora de inicio para consolidar';
COMMENT ON COLUMN sgmrtcatalogo_calendario.minuto_inicio IS 'Minutos de inicio para consolidar';
COMMENT ON COLUMN sgmrtcatalogo_calendario.hora_fin IS 'Hora de fin para consolidar';
COMMENT ON COLUMN sgmrtcatalogo_calendario.minuto_fin IS 'Minuto de fin para consolidar';


--
-- Table: sgmrtcatalogo_diascalendario (configuracion de los dias festivos del calendario de tramitacion electronica)
--

CREATE TABLE sgmrtcatalogo_diascalendario (
    id integer NOT NULL,
    fecha character varying(5) NOT NULL,
    descripcion character varying(256) NOT NULL,
    fijo integer NOT NULL
);

COMMENT ON COLUMN sgmrtcatalogo_diascalendario.id IS 'Identificador unico del festivo';
COMMENT ON COLUMN sgmrtcatalogo_diascalendario.fecha IS 'Fecha del festivo';
COMMENT ON COLUMN sgmrtcatalogo_diascalendario.descripcion IS 'Nombre del festivo';
COMMENT ON COLUMN sgmrtcatalogo_diascalendario.fijo IS 'Indica si es un festivo fijo o si cambia anualmente';

---
--- Table: sgmrtregistro_docs_csv (Asociacion entre el guid de un documento y el csv generado para dicho documento)
---
CREATE TABLE sgmrtregistro_docs_csv (
	guid character varying (32) NOT NULL,
	csv character varying (128) NOT NULL
);
