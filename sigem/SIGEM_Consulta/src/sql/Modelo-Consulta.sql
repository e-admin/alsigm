-- Table: sgm_cnxusr

-- DROP TABLE sgm_cnxusr;

CREATE TABLE sgm_cnxusr
(
  cnif character varying(16) NOT NULL,
  cflags integer NOT NULL,
  cfechahora timestamp without time zone NOT NULL
) 
WITHOUT OIDS;
ALTER TABLE sgm_cnxusr OWNER TO postgres;


-- Index: sgm_cnxusr1

-- DROP INDEX sgm_cnxusr1;

CREATE INDEX sgm_cnxusr1
  ON sgm_cnxusr
  USING btree
  (cnif);



-- Table: sgm_ctfichhito

-- DROP TABLE sgm_ctfichhito;

CREATE TABLE sgm_ctfichhito
(
  cguidhito character varying(32) NOT NULL,
  cguidfich character varying(32) NOT NULL,
  ctitulo character varying(128) NOT NULL
) 
WITHOUT OIDS;
ALTER TABLE sgm_ctfichhito OWNER TO postgres;
COMMENT ON TABLE sgm_ctfichhito IS 'Ficheros de Hitos';


-- Index: sgm_ctfichhito1

-- DROP INDEX sgm_ctfichhito1;

CREATE INDEX sgm_ctfichhito1
  ON sgm_ctfichhito
  USING btree
  (cguidhito);



-- Table: sgm_cthitoestexp

-- DROP TABLE sgm_cthitoestexp;

CREATE TABLE sgm_cthitoestexp
(
  cnumexp character varying(32) NOT NULL,
  cguid character varying(32) NOT NULL,
  ccod character varying(32),
  cfecha date NOT NULL,
  cdescr character varying(254) NOT NULL,
  cinfoaux character varying(4096),
  CONSTRAINT sgm_cthitoestexp_pkey PRIMARY KEY (cnumexp)
) 
WITHOUT OIDS;
ALTER TABLE sgm_cthitoestexp OWNER TO postgres;
COMMENT ON TABLE sgm_cthitoestexp IS 'Hitos de estado de un expediente';


-- Index: sgm_cthitoestexp1

-- DROP INDEX sgm_cthitoestexp1;

CREATE INDEX sgm_cthitoestexp1
  ON sgm_cthitoestexp
  USING btree
  (cnumexp);



-- Table: sgm_cthitohistexp

-- DROP TABLE sgm_cthitohistexp;

CREATE TABLE sgm_cthitohistexp
(
  cnumexp character varying(32) NOT NULL,
  cguid character varying(32) NOT NULL,
  ccod character varying(32),
  cfecha date NOT NULL,
  cdescr character varying(254) NOT NULL,
  cinfoaux character varying(4096)
) 
WITHOUT OIDS;
ALTER TABLE sgm_cthitohistexp OWNER TO postgres;
COMMENT ON TABLE sgm_cthitohistexp IS 'Historico de Hitos de un Expediente';




-- Table: sgm_ctinfoexp

-- DROP TABLE sgm_ctinfoexp;

CREATE TABLE sgm_ctinfoexp
(
  cnumexp character varying(32) NOT NULL,
  cproc character varying(254),
  cfhinicio date NOT NULL,
  cnumregini character varying(32),
  cfhregini date,
  cinfoaux character varying(4096),
  caportacion character(1),
  ccodpres character varying(32),
  estado character varying(1) NOT NULL DEFAULT 0,
  CONSTRAINT sgm_ctinfoexp_pkey PRIMARY KEY (cnumexp)
) 
WITHOUT OIDS;
ALTER TABLE sgm_ctinfoexp OWNER TO postgres;
COMMENT ON TABLE sgm_ctinfoexp IS 'Informacion acerca de un expediente';


-- Index: sgm_ctinfoexp1

-- DROP INDEX sgm_ctinfoexp1;

CREATE INDEX sgm_ctinfoexp1
  ON sgm_ctinfoexp
  USING btree
  (cnumexp);



-- Table: sgm_ctintexp

-- DROP TABLE sgm_ctintexp;

CREATE TABLE sgm_ctintexp
(
  cnumexp character varying(32) NOT NULL,
  cnif character varying(16) NOT NULL,
  cnom character varying(128),
  cprincipal character(1) NOT NULL,
  cinfoaux character varying(4096),
  CONSTRAINT sgm_ctintexp_cnumexp_key UNIQUE (cnumexp, cnif)
) 
WITHOUT OIDS;
ALTER TABLE sgm_ctintexp OWNER TO postgres;
COMMENT ON TABLE sgm_ctintexp IS 'Interesado de un expediente';


-- Index: sgm_ctintexp1

-- DROP INDEX sgm_ctintexp1;

CREATE INDEX sgm_ctintexp1
  ON sgm_ctintexp
  USING btree
  (cnumexp);

-- Index: sgm_ctintexp2

-- DROP INDEX sgm_ctintexp2;

CREATE INDEX sgm_ctintexp2
  ON sgm_ctintexp
  USING btree
  (cnif);



-- Table: sgm_ntfichnotif

-- DROP TABLE sgm_ntfichnotif;

CREATE TABLE sgm_ntfichnotif
(
  cguid character varying(32) NOT NULL,
  cguidnotif character varying(32) NOT NULL,
  ccodigo character varying(32),
  crol character varying(32),
  ctitulo character varying(128) NOT NULL,
  CONSTRAINT sgm_ntfichnotif_pkey PRIMARY KEY (cguid)
) 
WITHOUT OIDS;
ALTER TABLE sgm_ntfichnotif OWNER TO postgres;


-- Index: sgm_ntfichnotif1

-- DROP INDEX sgm_ntfichnotif1;

CREATE INDEX sgm_ntfichnotif1
  ON sgm_ntfichnotif
  USING btree
  (cguidnotif);



-- Table: sgm_ntinfonotif

-- DROP TABLE sgm_ntinfonotif;

CREATE TABLE sgm_ntinfonotif
(
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
  ctituloar character varying(128) NOT NULL,
  CONSTRAINT sgm_ntinfonotif_pkey PRIMARY KEY (cguid),
  CONSTRAINT sgm_ntinfonotif_cnumexp_key UNIQUE (cnumexp, cnifdest)
) 
WITHOUT OIDS;
ALTER TABLE sgm_ntinfonotif OWNER TO postgres;


-- Index: sgm_ntinfonotif1

-- DROP INDEX sgm_ntinfonotif1;

CREATE INDEX sgm_ntinfonotif1
  ON sgm_ntinfonotif
  USING btree
  (cguid);

-- Index: sgm_ntinfonotif2

-- DROP INDEX sgm_ntinfonotif2;

CREATE INDEX sgm_ntinfonotif2
  ON sgm_ntinfonotif
  USING btree
  (cnifdest);

-- Index: sgm_ntinfonotif3

-- DROP INDEX sgm_ntinfonotif3;

CREATE INDEX sgm_ntinfonotif3
  ON sgm_ntinfonotif
  USING btree
  (cavisada);

-- Index: sgm_ntinfonotif4

-- DROP INDEX sgm_ntinfonotif4;

CREATE INDEX sgm_ntinfonotif4
  ON sgm_ntinfonotif
  USING btree
  (centregada);



-- Table: sgmctnotificacion

-- DROP TABLE sgmctnotificacion;

CREATE TABLE sgmctnotificacion
(
  notificacion_id character varying(32) NOT NULL,
  fecha_notificacion time without time zone NOT NULL,
  deu character varying(32),
  servicio_notificaciones_id character varying(32),
  expediente character varying(32),
  descripcion character varying(256),
  hito_id character varying(32) NOT NULL
) 
WITHOUT OIDS;
ALTER TABLE sgmctnotificacion OWNER TO postgres;



-- Table: sgmctpago

-- DROP TABLE sgmctpago;

CREATE TABLE sgmctpago
(
  entidad_emisora_id character varying(32),
  autoliquidacion_id character varying(32),
  pago_id character varying(32) NOT NULL,
  documento_id character varying(32),
  mensaje character varying(256),
  hito_id character varying(32),
  fecha_pago time without time zone,
  expediente character varying(32),
  importe character varying(12)
) 
WITHOUT OIDS;
ALTER TABLE sgmctpago OWNER TO postgres;



-- Table: sgmctsubsanacion

-- DROP TABLE sgmctsubsanacion;

CREATE TABLE sgmctsubsanacion
(
  subsanacion_id character varying(32) NOT NULL,
  documento_id character varying(32),
  mensaje character varying(256),
  hito_id character varying(32),
  fecha_subsanacion time without time zone,
  expediente character varying(32)
) 
WITHOUT OIDS;
ALTER TABLE sgmctsubsanacion OWNER TO postgres;