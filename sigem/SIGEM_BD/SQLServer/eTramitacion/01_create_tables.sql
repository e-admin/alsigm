--
-- Table: sgm_au_usuarios
--

CREATE TABLE sgm_au_usuarios (
    nombre varchar(50) NOT NULL,
    apellidos varchar(50) NOT NULL,
    nif varchar(32) NOT NULL,
    correo_electronico varchar(60) NOT NULL,
    usuario varchar(15) NOT NULL,
    "password" varchar(100) NOT NULL
);


--
-- Table: sgm_cnxusr
--

CREATE TABLE sgm_cnxusr (
    cnif varchar(16) NOT NULL,
    cflags INT NOT NULL,
    cfechahora datetime NOT NULL
);


--
-- Table: sgm_ctfichhito
--

CREATE TABLE sgm_ctfichhito (
    cguidhito varchar(32) NOT NULL,
    cguidfich varchar(32) NOT NULL,
    ctitulo varchar(128) NOT NULL
);


--
-- Table: sgm_cthitoestexp
--

CREATE TABLE sgm_cthitoestexp (
    cnumexp varchar(32) NOT NULL,
    cguid varchar(32) NOT NULL,
    ccod varchar(32),
    cfecha DATETIME NOT NULL,
    cdescr varchar(4096) NOT NULL,
    cinfoaux varchar(4096)
);


--
-- Table: sgm_cthitohistexp
--

CREATE TABLE sgm_cthitohistexp (
    cnumexp varchar(32) NOT NULL,
    cguid varchar(32) NOT NULL,
    ccod varchar(32),
    cfecha DATETIME NOT NULL,
    cdescr varchar(4096) NOT NULL,
    cinfoaux varchar(4096)
);


--
-- Table: sgm_ctinfoexp
--

CREATE TABLE sgm_ctinfoexp (
    cnumexp varchar(32) NOT NULL,
    cproc varchar(254),
    cfhinicio DATETIME NOT NULL,
    cnumregini varchar(32),
    cfhregini DATETIME,
    cinfoaux varchar(4096),
    caportacion character(1),
    ccodpres varchar(32),
    estado varchar(1) DEFAULT 0 NOT NULL
);


--
-- Table: sgm_ctintexp
--

CREATE TABLE sgm_ctintexp (
    cnumexp varchar(32) NOT NULL,
    cnif varchar(16) NOT NULL,
    cnom varchar(128),
    cprincipal character(1) NOT NULL,
    cinfoaux varchar(4096)
);


--
-- Table: sgm_ntfichnotif
--

CREATE TABLE sgm_ntfichnotif (
    cguid varchar(32) NOT NULL,
    cguidnotif varchar(32) NOT NULL,
    ccodigo varchar(32),
    crol varchar(32),
    ctitulo varchar(128) NOT NULL
);


--
-- Table: sgm_ntinfonotif
--

CREATE TABLE sgm_ntinfonotif (
    cguid varchar(32) NOT NULL,
    cnifdest varchar(16) NOT NULL,
    cnumreg varchar(32) NOT NULL,
    cfhreg datetime NOT NULL,
    cnumexp varchar(32) NOT NULL,
    cproc varchar(254) NOT NULL,
    cinfoaux varchar(4096),
    cavisada INT NOT NULL,
    cfhaviso datetime,
    centregada character(1) NOT NULL,
    cfhentrega datetime,
    cdirce varchar(128) NOT NULL,
    casuntomce varchar(254) NOT NULL,
    ctextomce varchar(254) NOT NULL,
    ctitulo varchar(128) NOT NULL,
    ctitulode varchar(128) NOT NULL,
    ctituloar varchar(128) NOT NULL
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
    modelo varchar(3) NOT NULL,
    numsec bigint NOT NULL,
    minimo bigint NOT NULL,
    maximo bigint NOT NULL
);


--
-- Table: sgm_pe_liquidaciones
--

CREATE TABLE sgm_pe_liquidaciones (
    referencia varchar(13) NOT NULL,
    id_entidad_emisora varchar(6) NOT NULL,
    id_tasa varchar(3) NOT NULL,
    ejercicio varchar(4),
    vencimiento varchar(8),
    discriminante_periodo varchar(1),
    remesa varchar(2),
    nif varchar(9) NOT NULL,
    importe varchar(12) NOT NULL,
    nrc varchar(22),
    estado varchar(2),
    nombre varchar(512),
    datos_especificos varchar(2048),
    inicio_periodo DATETIME,
    fin_periodo DATETIME,
    solicitud image,
    fecha_pago datetime
);


--
-- Table: sgm_pe_tasas
--

CREATE TABLE sgm_pe_tasas (
    codigo varchar(3) NOT NULL,
    id_entidad_emisora varchar(6) NOT NULL,
    nombre varchar(255),
    tipo varchar(3) NOT NULL,
    modelo varchar(3),
    captura varchar(1),
    datos_especificos varchar(2048)
);


--
-- Table: sgmafconector_autenticacion
--

CREATE TABLE sgmafconector_autenticacion (
    tramiteid varchar(32) NOT NULL,
    conectorid varchar(32) NOT NULL
);


--
-- Table: sgmafsesion_info
--

CREATE TABLE sgmafsesion_info (
    sesionid varchar(32) NOT NULL,
    conectorid varchar(32) NOT NULL,
    fecha_login varchar(19) NOT NULL,
    nombre_solicitante varchar(128) NOT NULL,
    solicitante_id varchar(32) NOT NULL,
    correo_electronico_solicitante varchar(60) NOT NULL,
    emisor_certificado_solicitante varchar(256),
    solicitante_certificado_sn varchar(256),
    solicitante_inquality varchar(32),
    razon_social varchar(256),
    cif varchar(9),
    id_entidad varchar(10),
    solicitante_firstname varchar(36),
    solicitante_surname varchar(45),
    solicitante_surname2 varchar(45)
);


--
-- Table: sgmcertificacion
--

CREATE TABLE sgmcertificacion (
    id_fichero varchar(32) NOT NULL,
    id_usuario varchar(32) NOT NULL
);


--
-- Table: sgmctnotificacion
--

CREATE TABLE sgmctnotificacion (
    notificacion_id varchar(32) NOT NULL,
    deu varchar(32),
    servicio_notificaciones_id varchar(32),
    expediente varchar(32),
    descripcion varchar(4096),
    hito_id varchar(32) NOT NULL,
    fecha_notificacion datetime NOT NULL
);


--
-- Table: sgmctpago
--

CREATE TABLE sgmctpago (
    entidad_emisora_id varchar(32),
    autoliquidacion_id varchar(32),
    pago_id varchar(32) NOT NULL,
    documento_id varchar(32),
    mensaje varchar(4096),
    hito_id varchar(32),
    expediente varchar(32),
    importe varchar(12),
    fecha_pago datetime NOT NULL
);


--
-- Table: sgmctsubsanacion
--

CREATE TABLE sgmctsubsanacion (
    subsanacion_id varchar(32) NOT NULL,
    documento_id varchar(32),
    mensaje varchar(4096),
    hito_id varchar(32),
    expediente varchar(32),
    fecha_subsanacion datetime NOT NULL
);


--
-- Table: sgmntinfo_documento
--

CREATE TABLE sgmntinfo_documento (
    cnotiexpe varchar(32) NOT NULL,
    cnotinifdest varchar(10) NOT NULL,
    ccodigo varchar(200),
    cguid varchar(100),
	cnotiid varchar(64) NOT NULL,
	ctipodoc integer DEFAULT 1
);


--
-- Table: sgmntinfo_estado_noti
--

CREATE TABLE sgmntinfo_estado_noti (
    cguid INT NOT NULL,
    cuidsisnot varchar(100),
    cdescripcion varchar(100)
);


--
-- Table: sgmntinfo_notificacion
--

CREATE TABLE sgmntinfo_notificacion (
	cguid varchar(64),
    cnifdest varchar(10) NOT NULL,
    cnumreg varchar(16),
    cfhreg DATETIME,
    cnumexp varchar(32) NOT NULL,
    cproc varchar(50),
    cestado INT,
    cfhestado DATETIME,
    cfhentrega DATETIME,
    cusuario varchar(10),
    ctipocorrespondencia varchar(10),
    corganismo varchar(100),
    casunto varchar(100),
    ctipo varchar(20),
    ctexto varchar(1000),
    cnombredest varchar(100),
    capellidosdest varchar(150),
    ccorreodest varchar(200),
    cidioma varchar(20),
    ctipovia varchar(10),
    cvia varchar(200),
    cnumerovia varchar(10),
    cescaleravia varchar(10),
    cpisovia varchar(10),
    cpuertavia varchar(10),
    ctelefono varchar(20),
    cmunicipio varchar(100),
    cprovincia varchar(100),
    ccp varchar(10),
    cerror varchar(100),
	cnotiid varchar(64) NOT NULL,
	csistemaid varchar(32),
	cdeu varchar(20),
	ctfnomovil varchar(14)
);


--
-- Table: sgmrdedocumentos
--

CREATE TABLE sgmrdedocumentos (
    guid varchar(64) NOT NULL,
    contenido image NOT NULL,
    filehash varchar(64) NOT NULL,
    extension varchar(16) NOT NULL,
    fecha datetime NOT NULL
);


--
-- Table: sgmrdetiposmime
--

CREATE TABLE sgmrdetiposmime (
    extension varchar(8) NOT NULL,
    tipomime varchar(64) NOT NULL
);


--
-- Table: sgmrtcatalogo_conectores
--

CREATE TABLE sgmrtcatalogo_conectores (
    conectorid varchar(32) NOT NULL,
    descripcion varchar(256) NOT NULL,
    tipo varchar(32) NOT NULL
);


--
-- Table: sgmrtcatalogo_docstramite
--

CREATE TABLE sgmrtcatalogo_docstramite (
    tramite_id varchar(32) NOT NULL,
    documento_id varchar(32) NOT NULL,
    codigo_documento varchar(32) NOT NULL,
    documento_obligatorio varchar(1) NOT NULL
);


--
-- Table: sgmrtcatalogo_documentos
--

CREATE TABLE sgmrtcatalogo_documentos (
    id varchar(32) NOT NULL,
    descripcion varchar(256) NOT NULL,
    extension varchar(128) NOT NULL,
    conector_firma varchar(32),
    conector_contenido varchar(32)
);


--
-- Table: sgmrtcatalogo_organos
--

CREATE TABLE sgmrtcatalogo_organos (
    organo varchar(16) NOT NULL,
    descripcion varchar(250) NOT NULL
);


--
-- Table: sgmrtcatalogo_tipoconector
--

CREATE TABLE sgmrtcatalogo_tipoconector (
    tipoid varchar(32) NOT NULL,
    descripcion varchar(256) NOT NULL
);


--
-- Table: sgmrtcatalogo_tramites
--

CREATE TABLE sgmrtcatalogo_tramites (
    id varchar(32) NOT NULL,
    asunto varchar(32) NOT NULL,
    descripcion varchar(256) NOT NULL,
    organo varchar(16) NOT NULL,
    limite_documentos varchar(1) NOT NULL,
    firma varchar(1) DEFAULT 1 NOT NULL,
    oficina varchar(32) NOT NULL,
    id_procedimiento varchar(32)
);


--
-- Table: sgmrtregistro
--

CREATE TABLE sgmrtregistro (
    numero_registro varchar(16) NOT NULL,
    fecha_registro datetime NOT NULL,
    emisor_id varchar(128) NOT NULL,
    nombre varchar(128) NOT NULL,
    correo_electronico varchar(60),
    asunto varchar(200) NOT NULL,
    organo varchar(16) NOT NULL,
    tipo_emisor_id varchar(1) NOT NULL,
    info_adicional image,
    estado varchar(1) NOT NULL,
    oficina varchar(32),
    numero_expediente varchar(32),
	fecha_efectiva datetime
);


--
-- Table: sgmrtregistro_documentos
--

CREATE TABLE sgmrtregistro_documentos (
    numero_registro varchar(16) NOT NULL,
    codigo varchar(32) NOT NULL,
    guid varchar(32) NOT NULL,
    conector_firma varchar(32),
    conector_documento varchar(32)
);


--
-- Table: sgmrtregistro_secuencia
--

CREATE TABLE sgmrtregistro_secuencia (
    anyo varchar(4) NOT NULL,
    etiqueta varchar(8) NOT NULL,
    secuencia numeric NOT NULL
);


--
-- Table: sgmrttmp_documentos
--

CREATE TABLE sgmrttmp_documentos (
    sesionid varchar(64) NOT NULL,
    guid varchar(32) NOT NULL,
    fecha datetime NOT NULL
);


--
-- Table: sgmrtcatalogo_calendario (configuracion del calendario de tramitacion electronica)
--

CREATE TABLE sgmrtcatalogo_calendario (
    laborables varchar(13) NOT NULL,
    hora_inicio varchar(2),
    minuto_inicio varchar(2),
    hora_fin varchar(2),
    minuto_fin varchar(2)
);


--
-- Table: sgmrtcatalogo_diascalendario (configuracion de los dias festivos del calendario de tramitacion electronica)
--

CREATE TABLE sgmrtcatalogo_diascalendario (
    id numeric NOT NULL,
    fecha varchar(5) NOT NULL,
    descripcion varchar(256) NOT NULL,
    fijo int NOT NULL
);

---
--- Table: sgmrtregistro_docs_csv (Asociacion entre el guid de un documento y el csv generado para dicho documento)
---
CREATE TABLE sgmrtregistro_docs_csv (
	guid varchar (32) NOT NULL,
	csv varchar (128) NOT NULL
);