--
-- PostgreSQL database dump
--

-- Started on 2008-11-14 13:23:11

SET client_encoding = 'LATIN9';
SET standard_conforming_strings = off;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET escape_string_warning = off;

--
-- TOC entry 1842 (class 0 OID 0)
-- Dependencies: 5
-- Name: SCHEMA public; Type: COMMENT; Schema: -; Owner: postgres
--

COMMENT ON SCHEMA public IS 'Standard public schema';


--
-- TOC entry 429 (class 2612 OID 205144)
-- Name: plpgsql; Type: PROCEDURAL LANGUAGE; Schema: -; Owner: postgres
--

SET search_path = public, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;


CREATE SEQUENCE sn_doc_notificacion_cod_notificacion_seq
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.sn_doc_notificacion_cod_notificacion_seq OWNER TO postgres;

--
-- TOC entry 1846 (class 0 OID 0)
-- Dependencies: 1471
-- Name: sn_doc_notificacion_cod_notificacion_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('sn_doc_notificacion_cod_notificacion_seq', 14, true);


--
-- TOC entry 1475 (class 1259 OID 205343)
-- Dependencies: 5
-- Name: sn_procedimiento; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--
CREATE TABLE SN_PROCEDIMIENTO (
	COD_PROCEDIMIENTO VARCHAR(20) PRIMARY KEY
	, ORG_EMISOR VARCHAR(10) NOT NULL
	, CATEGORIA VARCHAR(100)
	, DESCRIPCION VARCHAR(200) NOT NULL
	, TIPO_PROCEDIMIENTO CHAR NOT NULL DEFAULT 'A'
	, FECHA_INICIO_VIGENCIA DATE
	, FECHA_FIN_VIGENCIA DATE NOT NULL
	, DIR_URL VARCHAR(200) NOT NULL
	, ESTADO CHAR NOT NULL
	, ALTA_ASUNTO_EMAIL VARCHAR(4000)
	, ALTA_MENSAJE_EMAIL VARCHAR(4000)
	, ALTA_MENSAJE_SMS VARCHAR(160)
	, BAJA_ASUNTO_EMAIL VARCHAR(4000)
	, BAJA_MENSAJE_EMAIL VARCHAR(4000)
	, BAJA_MENSAJE_SMS VARCHAR(160)
	, MODIF_ASUNTO_EMAIL VARCHAR(4000)
	, MODIF_MENSAJE_EMAIL VARCHAR(4000)
	, MODIF_MENSAJE_SMS VARCHAR(160)
);

ALTER TABLE public.sn_procedimiento OWNER TO postgres;

--
-- TOC entry 1478 (class 1259 OID 205356)
-- Dependencies: 5
-- Name: sn_sistema_emisor; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE SN_SISTEMA_EMISOR (
	COD_EMISOR VARCHAR(10) PRIMARY KEY 
	, DIR_SERVICIO VARCHAR(100)
	, DESCRIPCION VARCHAR(200)
);


ALTER TABLE public.sn_sistema_emisor OWNER TO postgres;

--
-- TOC entry 1481 (class 1259 OID 205371)
-- Dependencies: 5
-- Name: sn_usuario; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE SN_USUARIO (
	ID_USUARIO VARCHAR(30) PRIMARY KEY
	, DEU VARCHAR(13) NOT NULL
	, TIPO_PERSONA CHAR NOT NULL
	, NIFCIF VARCHAR(9) NOT NULL
	, NOMBRE VARCHAR(40) NOT NULL
	, COD_PRESTADOR VARCHAR(20) NOT NULL
);


ALTER TABLE public.sn_usuario OWNER TO postgres;

--
-- TOC entry 1479 (class 1259 OID 205361)
-- Dependencies: 5
-- Name: sn_suscripcion; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE SN_SUSCRIPCION (
	COD_PROCEDIMIENTO VARCHAR(20) NOT NULL
	, ID_USUARIO VARCHAR(30) NOT NULL
	, ESTADO CHAR NOT NULL
	, FECHA_ESTADO TIMESTAMP
	, COD_ERROR VARCHAR(4) DEFAULT '0000'
	, PRIMARY KEY (COD_PROCEDIMIENTO, ID_USUARIO)
	, FOREIGN KEY (COD_PROCEDIMIENTO) REFERENCES SN_PROCEDIMIENTO (COD_PROCEDIMIENTO)
	, FOREIGN KEY (ID_USUARIO) REFERENCES SN_USUARIO (ID_USUARIO)
);


ALTER TABLE public.sn_suscripcion OWNER TO postgres;

--
-- TOC entry 1480 (class 1259 OID 205366)
-- Dependencies: 5
-- Name: sn_tipo_correspondencia; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE SN_TIPO_CORRESPONDENCIA (
	TIPO_CORRESPONDENCIA VARCHAR(8) PRIMARY KEY
	, ENCRIPTAR BOOLEAN DEFAULT FALSE
	, DESCRIPCION VARCHAR(200)
);


ALTER TABLE public.sn_tipo_correspondencia OWNER TO postgres;

--
-- TOC entry 1472 (class 1259 OID 205329)
-- Dependencies: 1812 5
-- Name: sn_doc_notificacion; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE SN_DOC_NOTIFICACION (
	COD_NOTIFICACION NUMERIC(13) PRIMARY KEY DEFAULT nextval('SN_DOC_NOTIFICACION_COD_NOTIFICACION_SEQ') 
	, NOTIFICACION BYTEA NOT NULL
	, NOMBRE_ARCHIVO VARCHAR(12)
	, NUM_EXPEDIENTE VARCHAR(20)
	, NUM_REGISTRO VARCHAR(8)
	, FECHA_REGISTRO DATE
	, ASUNTO VARCHAR(100) NOT NULL
	, CUERPO VARCHAR(200)
);


ALTER TABLE public.sn_doc_notificacion OWNER TO postgres;

--
-- TOC entry 1473 (class 1259 OID 205335)
-- Dependencies: 5
-- Name: sn_notificacion_ncc_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE sn_notificacion_ncc_seq
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.sn_notificacion_ncc_seq OWNER TO postgres;

--
-- TOC entry 1847 (class 0 OID 0)
-- Dependencies: 1473
-- Name: sn_notificacion_ncc_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('sn_notificacion_ncc_seq', 12, true);

--
-- TOC entry 1476 (class 1259 OID 205348)
-- Dependencies: 5
-- Name: sn_remesa_cod_remesa_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE sn_remesa_cod_remesa_seq
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.sn_remesa_cod_remesa_seq OWNER TO postgres;

--
-- TOC entry 1848 (class 0 OID 0)
-- Dependencies: 1476
-- Name: sn_remesa_cod_remesa_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('sn_remesa_cod_remesa_seq', 27, true);


--
-- TOC entry 1477 (class 1259 OID 205350)
-- Dependencies: 1814 5
-- Name: sn_remesa; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE SN_REMESA (
	COD_REMESA NUMERIC(10) PRIMARY KEY DEFAULT nextval('SN_REMESA_COD_REMESA_SEQ') 
	, COD_EMISOR VARCHAR(10) NOT NULL
	, TIPO_CORRESPONDENCIA VARCHAR(8)
	, COD_ERROR VARCHAR(4)  DEFAULT '0000'
	, FECHA_ENVIO TIMESTAMP DEFAULT CURRENT_TIMESTAMP
	, TIPO_REMESA CHAR NOT NULL
	, ULT_COD_PROCEDIMIENTO VARCHAR(20)
	, ULT_USUARIO VARCHAR(30)
	, FOREIGN KEY (ULT_COD_PROCEDIMIENTO, ULT_USUARIO) REFERENCES SN_SUSCRIPCION (COD_PROCEDIMIENTO, ID_USUARIO)
	, FOREIGN KEY (TIPO_CORRESPONDENCIA) REFERENCES SN_TIPO_CORRESPONDENCIA (TIPO_CORRESPONDENCIA)

);


ALTER TABLE public.sn_remesa OWNER TO postgres;


--
-- TOC entry 1474 (class 1259 OID 205337)
-- Dependencies: 1813 5
-- Name: sn_notificacion; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE SN_NOTIFICACION (
	NCC NUMERIC(13) PRIMARY KEY DEFAULT nextval('SN_NOTIFICACION_NCC_SEQ') 
	, ESTADO VARCHAR(2) NOT NULL DEFAULT '0'
	, COD_REMESA NUMERIC(10) 
	, COD_NOTIFICACION NUMERIC(13) 
	, ID_USUARIO VARCHAR(30) 
	, COD_PROCEDIMIENTO VARCHAR(20) 
	, COD_ERROR VARCHAR(4) DEFAULT '0000'
	, COD_EMISOR VARCHAR(10) 
	, TIPO_CORRESPONDENCIA VARCHAR(8) NOT NULL
	, FECHA_CREACION TIMESTAMP DEFAULT CURRENT_TIMESTAMP
	, FECHA_ADMISION TIMESTAMP
	, FECHA_PUESTA_DISPOSICION TIMESTAMP
	, FECHA_RECIBIDO TIMESTAMP
	, FECHA_ANULACION TIMESTAMP
	, TIPO_ACUSE CHAR
	, MOTIVO VARCHAR(256)
	, CLAVE_ENCRIPTACION BYTEA
	, ACUSE_RECIBO BYTEA
	, FOREIGN KEY (COD_REMESA) REFERENCES SN_REMESA (COD_REMESA)
	, FOREIGN KEY (COD_PROCEDIMIENTO, ID_USUARIO) REFERENCES SN_SUSCRIPCION (COD_PROCEDIMIENTO, ID_USUARIO)
	, FOREIGN KEY (COD_NOTIFICACION) REFERENCES SN_DOC_NOTIFICACION (COD_NOTIFICACION)
);


ALTER TABLE public.sn_notificacion OWNER TO postgres;



	
INSERT INTO sn_procedimiento(COD_PROCEDIMIENTO,TIPO_PROCEDIMIENTO,ESTADO,DESCRIPCION,FECHA_FIN_VIGENCIA,DIR_URL,FECHA_INICIO_VIGENCIA,CATEGORIA,ORG_EMISOR,ALTA_ASUNTO_EMAIL,ALTA_MENSAJE_EMAIL,ALTA_MENSAJE_SMS,BAJA_ASUNTO_EMAIL,BAJA_MENSAJE_EMAIL,BAJA_MENSAJE_SMS,MODIF_ASUNTO_EMAIL,MODIF_MENSAJE_EMAIL,MODIF_MENSAJE_SMS) VALUES ('PCD-4','A','1','Subvencion','2037-12-31','http://192.168.8.4:8080/portal',null,'Tramitacion','SIGEM',
	'Alta procedimiento PCD-4','Alta de suscripción a procedimiento Subvención','Alta Subvención','Baja procedimiento PCD-4','Baja de suscripción a procedimiento Subvención','Baja Subvención','Modificación procedimiento PCD-4','Modificación de suscripción a procedimiento Subvención','Modificación Subvención');
INSERT INTO sn_procedimiento(COD_PROCEDIMIENTO,TIPO_PROCEDIMIENTO,ESTADO,DESCRIPCION,FECHA_FIN_VIGENCIA,DIR_URL,FECHA_INICIO_VIGENCIA,CATEGORIA,ORG_EMISOR,ALTA_ASUNTO_EMAIL,ALTA_MENSAJE_EMAIL,ALTA_MENSAJE_SMS,BAJA_ASUNTO_EMAIL,BAJA_MENSAJE_EMAIL,BAJA_MENSAJE_SMS,MODIF_ASUNTO_EMAIL,MODIF_MENSAJE_EMAIL,MODIF_MENSAJE_SMS) VALUES ('013','A','1','Solicitud de Tarjeta de Estacionamiento para Minúsvalidos','2037-12-31','http://192.168.8.4:8080/portal',null,'Tramitacion','SIGEM',
	'Alta procedimiento 013','Alta de suscripción a procedimiento Solicitud de Tarjeta de Estacionamiento para Minúsvalidos','Alta Solicitud de Tarjeta de Estacionamiento para Minúsvalidos','Baja procedimiento 013','Baja de suscripción a procedimiento Solicitud de Tarjeta de Estacionamiento para Minúsvalidos','Baja olicitud de Tarjeta de Estacionamiento para Minúsvalidos','Modificación procedimiento 013','Modificación de suscripción a procedimiento Solicitud de Tarjeta de Estacionamiento para Minúsvalidos','Modificación Solicitud de Tarjeta de Estacionamiento para Minúsvalidos');
INSERT INTO sn_procedimiento(COD_PROCEDIMIENTO,TIPO_PROCEDIMIENTO,ESTADO,DESCRIPCION,FECHA_FIN_VIGENCIA,DIR_URL,FECHA_INICIO_VIGENCIA,CATEGORIA,ORG_EMISOR,ALTA_ASUNTO_EMAIL,ALTA_MENSAJE_EMAIL,ALTA_MENSAJE_SMS,BAJA_ASUNTO_EMAIL,BAJA_MENSAJE_EMAIL,BAJA_MENSAJE_SMS,MODIF_ASUNTO_EMAIL,MODIF_MENSAJE_EMAIL,MODIF_MENSAJE_SMS) VALUES ('PCD-3','A','1','Solicitud de Reclamación, queja y sugerencias','2037-12-31','http://192.168.8.4:8080/portal',null,'Tramitacion','SIGEM',
	'Alta procedimiento PCD-3','Alta de suscripción a procedimiento Solicitud de Reclamación, queja y sugerencias','Alta Solicitud de Reclamación, queja y sugerencias','Baja procedimiento PCD-3','Baja de suscripción a procedimiento Solicitud de Reclamación, queja y sugerencias','Baja Solicitud de Reclamación, queja y sugerencias','Modificación procedimiento PCD-3','Modificación de suscripción a procedimiento Solicitud de Reclamación, queja y sugerencias','Modificación Solicitud de Reclamación, queja y sugerencias');
INSERT INTO sn_procedimiento(COD_PROCEDIMIENTO,TIPO_PROCEDIMIENTO,ESTADO,DESCRIPCION,FECHA_FIN_VIGENCIA,DIR_URL,FECHA_INICIO_VIGENCIA,CATEGORIA,ORG_EMISOR,ALTA_ASUNTO_EMAIL,ALTA_MENSAJE_EMAIL,ALTA_MENSAJE_SMS,BAJA_ASUNTO_EMAIL,BAJA_MENSAJE_EMAIL,BAJA_MENSAJE_SMS,MODIF_ASUNTO_EMAIL,MODIF_MENSAJE_EMAIL,MODIF_MENSAJE_SMS) VALUES ('PCD-11','A','1','Solicitud de Certificado Urbanístico','2037-12-31','http://192.168.8.4:8080/portal',null,'Tramitacion','SIGEM',
	'Alta procedimiento PCD-11','Alta de suscripción a procedimiento Solicitud de Certificado Urbanístico','Alta Solicitud de Certificado Urbanístico','Baja procedimiento PCD-11','Baja de suscripción a procedimiento Solicitud de Certificado Urbanístico','Baja Solicitud de Certificado Urbanístico','Modificación procedimiento PCD-11','Modificación de suscripción a procedimiento Solicitud de Certificado Urbanístico','Modificación Solicitud de Certificado Urbanístico');
INSERT INTO sn_procedimiento(COD_PROCEDIMIENTO,TIPO_PROCEDIMIENTO,ESTADO,DESCRIPCION,FECHA_FIN_VIGENCIA,DIR_URL,FECHA_INICIO_VIGENCIA,CATEGORIA,ORG_EMISOR,ALTA_ASUNTO_EMAIL,ALTA_MENSAJE_EMAIL,ALTA_MENSAJE_SMS,BAJA_ASUNTO_EMAIL,BAJA_MENSAJE_EMAIL,BAJA_MENSAJE_SMS,MODIF_ASUNTO_EMAIL,MODIF_MENSAJE_EMAIL,MODIF_MENSAJE_SMS) VALUES ('PCD-15','A','1','Solicitud de Acometida de Agua','2037-12-31','http://192.168.8.4:8080/portal',null,'Tramitacion','SIGEM',
	'Alta procedimiento PCD-15','Alta de suscripción a procedimiento Solicitud de Acometida de Agua','Alta Solicitud de Acometida de Agua','Baja procedimiento PCD-15','Baja de suscripción a procedimiento Solicitud de Acometida de Agua','Baja Solicitud de Acometida de Agua','Modificación procedimiento PCD-15','Modificación de suscripción a procedimiento Solicitud de Acometida de Agua','Modificación Solicitud de Acometida de Agua');
INSERT INTO sn_procedimiento(COD_PROCEDIMIENTO,TIPO_PROCEDIMIENTO,ESTADO,DESCRIPCION,FECHA_FIN_VIGENCIA,DIR_URL,FECHA_INICIO_VIGENCIA,CATEGORIA,ORG_EMISOR,ALTA_ASUNTO_EMAIL,ALTA_MENSAJE_EMAIL,ALTA_MENSAJE_SMS,BAJA_ASUNTO_EMAIL,BAJA_MENSAJE_EMAIL,BAJA_MENSAJE_SMS,MODIF_ASUNTO_EMAIL,MODIF_MENSAJE_EMAIL,MODIF_MENSAJE_SMS) VALUES ('PCD-16','A','1','Reclamación Tributos','2037-12-31','http://192.168.8.4:8080/portal',null,'Tramitacion','SIGEM',
	'Alta procedimiento PCD-16','Alta de suscripción a procedimiento Reclamación Tributos','Alta Reclamación Tributos','Baja procedimiento PCD-16','Baja de suscripción a procedimiento Reclamación Tributos','Baja Reclamación Tributos','Modificación procedimiento PCD-16','Modificación de suscripción a procedimiento Reclamación Tributos','Modificación Reclamación Tributos');	
INSERT INTO sn_procedimiento(COD_PROCEDIMIENTO,TIPO_PROCEDIMIENTO,ESTADO,DESCRIPCION,FECHA_FIN_VIGENCIA,DIR_URL,FECHA_INICIO_VIGENCIA,CATEGORIA,ORG_EMISOR,ALTA_ASUNTO_EMAIL,ALTA_MENSAJE_EMAIL,ALTA_MENSAJE_SMS,BAJA_ASUNTO_EMAIL,BAJA_MENSAJE_EMAIL,BAJA_MENSAJE_SMS,MODIF_ASUNTO_EMAIL,MODIF_MENSAJE_EMAIL,MODIF_MENSAJE_SMS) VALUES ('023','A','1','Licencia de Vado','2037-12-31','http://192.168.8.4:8080/portal',null,'Tramitacion','SIGEM',
	'Alta procedimiento 023','Alta de suscripción a procedimiento Licencia de Vado','Alta Licencia de Vado','Baja procedimiento 023','Baja de suscripción a procedimiento Licencia de Vado','Baja Licencia de Vado','Modificación procedimiento 023','Modificación de suscripción a procedimiento Licencia de Vado','Modificación Licencia de Vado');	
INSERT INTO sn_procedimiento(COD_PROCEDIMIENTO,TIPO_PROCEDIMIENTO,ESTADO,DESCRIPCION,FECHA_FIN_VIGENCIA,DIR_URL,FECHA_INICIO_VIGENCIA,CATEGORIA,ORG_EMISOR,ALTA_ASUNTO_EMAIL,ALTA_MENSAJE_EMAIL,ALTA_MENSAJE_SMS,BAJA_ASUNTO_EMAIL,BAJA_MENSAJE_EMAIL,BAJA_MENSAJE_SMS,MODIF_ASUNTO_EMAIL,MODIF_MENSAJE_EMAIL,MODIF_MENSAJE_SMS) VALUES ('PCD-5','A','1','Licencia de Obra Menor','2037-12-31','http://192.168.8.4:8080/portal',null,'Tramitacion','SIGEM',
	'Alta procedimiento PCD-5','Alta de suscripción a procedimiento Licencia de Obra Menor','Alta Licencia de Obra Menor','Baja procedimiento PCD-5','Baja de suscripción a procedimiento Licencia de Obra Menor','Baja Licencia de Obra Menor','Modificación procedimiento PCD-5','Modificación de suscripción a procedimiento Licencia de Obra Menor','Modificación Licencia de Obra Menor');
INSERT INTO sn_procedimiento(COD_PROCEDIMIENTO,TIPO_PROCEDIMIENTO,ESTADO,DESCRIPCION,FECHA_FIN_VIGENCIA,DIR_URL,FECHA_INICIO_VIGENCIA,CATEGORIA,ORG_EMISOR,ALTA_ASUNTO_EMAIL,ALTA_MENSAJE_EMAIL,ALTA_MENSAJE_SMS,BAJA_ASUNTO_EMAIL,BAJA_MENSAJE_EMAIL,BAJA_MENSAJE_SMS,MODIF_ASUNTO_EMAIL,MODIF_MENSAJE_EMAIL,MODIF_MENSAJE_SMS) VALUES ('024','A','1','Licencia de Apertura de Actividad No Clasificada ','2037-12-31','http://192.168.8.4:8080/portal',null,'Tramitacion','SIGEM',
	'Alta procedimiento 024','Alta de suscripción a procedimiento Licencia de Apertura de Actividad No Clasificada','Alta Licencia de Apertura de Actividad No Clasificada','Baja procedimiento 024','Baja de suscripción a procedimiento Licencia de Apertura de Actividad No Clasificada','Baja Licencia de Apertura de Actividad No Clasificada','Modificación procedimiento 024','Modificación de suscripción a procedimiento Licencia de Apertura de Actividad No Clasificada','Modificación Licencia de Apertura de Actividad No Clasificada');
INSERT INTO sn_procedimiento(COD_PROCEDIMIENTO,TIPO_PROCEDIMIENTO,ESTADO,DESCRIPCION,FECHA_FIN_VIGENCIA,DIR_URL,FECHA_INICIO_VIGENCIA,CATEGORIA,ORG_EMISOR,ALTA_ASUNTO_EMAIL,ALTA_MENSAJE_EMAIL,ALTA_MENSAJE_SMS,BAJA_ASUNTO_EMAIL,BAJA_MENSAJE_EMAIL,BAJA_MENSAJE_SMS,MODIF_ASUNTO_EMAIL,MODIF_MENSAJE_EMAIL,MODIF_MENSAJE_SMS) VALUES ('026','A','1','Licencia de Apertura de Actividad Clasificada ','2037-12-31','http://192.168.8.4:8080/portal',null,'Tramitacion','SIGEM',
	'Alta procedimiento 026','Alta de suscripción a procedimiento Licencia de Apertura de Actividad No Clasificada','Alta Licencia de Apertura de Actividad No Clasificada','Baja procedimiento 026','Baja de suscripción a procedimiento Licencia de Apertura de Actividad Clasificada','Baja Licencia de Apertura de Actividad Clasificada','Modificación procedimiento 026','Modificación de suscripción a procedimiento Licencia de Apertura de Actividad Clasificada','Modificación Licencia de Apertura de Actividad Clasificada');
INSERT INTO sn_procedimiento(COD_PROCEDIMIENTO,TIPO_PROCEDIMIENTO,ESTADO,DESCRIPCION,FECHA_FIN_VIGENCIA,DIR_URL,FECHA_INICIO_VIGENCIA,CATEGORIA,ORG_EMISOR,ALTA_ASUNTO_EMAIL,ALTA_MENSAJE_EMAIL,ALTA_MENSAJE_SMS,BAJA_ASUNTO_EMAIL,BAJA_MENSAJE_EMAIL,BAJA_MENSAJE_SMS,MODIF_ASUNTO_EMAIL,MODIF_MENSAJE_EMAIL,MODIF_MENSAJE_SMS) VALUES ('019','A','1','Expediente Sancionador','2037-12-31','http://192.168.8.4:8080/portal',null,'Tramitacion','SIGEM',
	'Alta procedimiento 019','Alta de suscripción a procedimiento Expediente Sancionador','Alta Expediente Sancionador','Baja procedimiento 019','Baja de suscripción a procedimiento Expediente Sancionador','Baja Expediente Sancionador','Modificación procedimiento 019','Modificación de suscripción a procedimiento Expediente Sancionador','Modificación Expediente Sancionador');
INSERT INTO sn_procedimiento(COD_PROCEDIMIENTO,TIPO_PROCEDIMIENTO,ESTADO,DESCRIPCION,FECHA_FIN_VIGENCIA,DIR_URL,FECHA_INICIO_VIGENCIA,CATEGORIA,ORG_EMISOR,ALTA_ASUNTO_EMAIL,ALTA_MENSAJE_EMAIL,ALTA_MENSAJE_SMS,BAJA_ASUNTO_EMAIL,BAJA_MENSAJE_EMAIL,BAJA_MENSAJE_SMS,MODIF_ASUNTO_EMAIL,MODIF_MENSAJE_EMAIL,MODIF_MENSAJE_SMS) VALUES ('PCD-10','A','1','Contrato Negociado','2037-12-31','http://192.168.8.4:8080/portal',null,'Tramitacion','SIGEM',
	'Alta procedimiento PCD-10','Alta de suscripción a procedimiento Contrato Negociado','Alta Contrato Negociado','Baja procedimiento PCD-10','Baja de suscripción a procedimiento Contrato Negociado','Baja Contrato Negociado','Modificación procedimiento PCD-10','Modificación de suscripción a procedimiento Contrato Negociado','Modificación Contrato Negociado');
INSERT INTO sn_procedimiento(COD_PROCEDIMIENTO,TIPO_PROCEDIMIENTO,ESTADO,DESCRIPCION,FECHA_FIN_VIGENCIA,DIR_URL,FECHA_INICIO_VIGENCIA,CATEGORIA,ORG_EMISOR,ALTA_ASUNTO_EMAIL,ALTA_MENSAJE_EMAIL,ALTA_MENSAJE_SMS,BAJA_ASUNTO_EMAIL,BAJA_MENSAJE_EMAIL,BAJA_MENSAJE_SMS,MODIF_ASUNTO_EMAIL,MODIF_MENSAJE_EMAIL,MODIF_MENSAJE_SMS) VALUES ('025','A','1','Cambio de Titularidad de Licencia de Apertura','2037-12-31','http://192.168.8.4:8080/portal',null,'Tramitacion','SIGEM',
	'Alta procedimiento 025','Alta de suscripción a procedimiento Cambio de Titularidad de Licencia de Apertura','Alta Cambio de Titularidad de Licencia de Apertura','Baja procedimiento 025','Baja de suscripción a procedimiento Cambio de Titularidad de Licencia de Apertura','Baja Cambio de Titularidad de Licencia de Apertura','Modificación procedimiento 025','Modificación de suscripción a procedimiento Cambio de Titularidad de Licencia de Apertura','Modificación Cambio de Titularidad de Licencia de Apertura');
	
INSERT INTO sn_sistema_emisor(cod_emisor, dir_servicio, descripcion) VALUES ('SIGEM',null,'Organismo Emisor SIGEM');

INSERT INTO sn_tipo_correspondencia (tipo_correspondencia, descripcion, encriptar) VALUES ('CENSO','CENSO','f');
INSERT INTO sn_tipo_correspondencia (tipo_correspondencia, descripcion, encriptar) VALUES ('NOT1CIFR','CIFRADAS','t');
INSERT INTO sn_tipo_correspondencia (tipo_correspondencia, descripcion, encriptar) VALUES ('NOT1','SIN CIFRAR','f');



--
-- TOC entry 1843 (class 0 OID 0)
-- Dependencies: 5
-- Name: public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;


-- Completed on 2008-11-14 13:23:12

--
-- PostgreSQL database dump complete
--

