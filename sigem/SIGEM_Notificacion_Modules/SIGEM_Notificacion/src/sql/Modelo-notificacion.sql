-- Table: sgmntinfo_estado_noti

-- DROP TABLE sgmntinfo_estado_noti;

CREATE TABLE sgmntinfo_estado_noti
(
  cguid integer NOT NULL, -- Indentificador interno del estado
  "cuidSisnot" character(100), -- Estado equivalente en sisnot
  cdescripcion character(100), -- Descripcion valida para mostrar al usuario
  CONSTRAINT sgmntinfo_estado_noti_pk PRIMARY KEY (cguid)
) 
WITHOUT OIDS;
ALTER TABLE sgmntinfo_estado_noti OWNER TO postgres;
COMMENT ON COLUMN sgmntinfo_estado_noti.cguid IS 'Indentificador interno del estado';
COMMENT ON COLUMN sgmntinfo_estado_noti."cuidSisnot" IS 'Estado equivalente en sisnot';
COMMENT ON COLUMN sgmntinfo_estado_noti.cdescripcion IS 'Descripcion valida para mostrar al usuario';

INSERT INTO sgmntinfo_estado_noti (cguid, cuidsisnot, cdescripcion) VALUES (-1, 'Fallo', 'Fallo
');
INSERT INTO sgmntinfo_estado_noti (cguid, cuidsisnot, cdescripcion) VALUES (7, 'Usuario no suscrito
', 'Usuario no suscrito
');
INSERT INTO sgmntinfo_estado_noti (cguid, cuidsisnot, cdescripcion) VALUES (2, 'Notificación puesta a disposición', 'Notificación puesta a disposición');
INSERT INTO sgmntinfo_estado_noti (cguid, cuidsisnot, cdescripcion) VALUES (0, 'Notificación creada', 'Notificación creada
');
INSERT INTO sgmntinfo_estado_noti (cguid, cuidsisnot, cdescripcion) VALUES (1, 'Notificación enviada', 'Notificación enviada');
INSERT INTO sgmntinfo_estado_noti (cguid, cuidsisnot, cdescripcion) VALUES (4, 'Notificación expirada después de 10 días
', 'Notificación expirada después de 10 días');
INSERT INTO sgmntinfo_estado_noti (cguid, cuidsisnot, cdescripcion) VALUES (5, 'Notificación rechazada
', 'Notificación rechazada

');
INSERT INTO sgmntinfo_estado_noti (cguid, cuidsisnot, cdescripcion) VALUES (6, 'Notificación finalizada (Cambio de estado comunicado al sistema emisor)
', 'Notificación finalizada (Cambio de estado comunicado al sistema emisor)');
INSERT INTO sgmntinfo_estado_noti (cguid, cuidsisnot, cdescripcion) VALUES (3, 'Notificacion leída', 'Notificacion leída');



-- Table: sgmntinfo_notificacion

-- DROP TABLE sgmntinfo_notificacion;

CREATE TABLE sgmntinfo_notificacion
(
  cguid character varying(10), -- Identificador de la notificacion
  cnifdest character(10) NOT NULL, -- nif del destinatario de la notificacion
  cnumreg character(16), -- numero de registro
  cfhreg date, -- fecha de registro
  cnumexp character(10) NOT NULL, -- numero de expediente
  cproc character(50), -- procedimiento asociado a la notficacion
  cestado integer, -- estado de la notificacion en la ultima actualizacion
  cfhestado date, -- fecha de la ultima actualizacion del estado
  cfhentrega date, -- fecha de creacion de la notificacion en el sistema
  cusuario character(10), -- usuario asociado a la notificacion
  ctipocorrespondencia character(10), -- Tipo de correspondencia de la notifiacacoin
  corganismo character(100), -- organismo responsable de la notificacion. Es la entidad emisora con respecto al sisnot
  casunto character(100), -- asunto de la notificacion
  ctipo character(20), -- tipo de notificacion
  ctexto character(1000), -- texto de la notificacion
  cnombredest character(100), -- nombre del destinatario
  capellidosdest character(150), -- apellidos de la notifiaccion
  ccorreodest character(200), -- correo del destinatario
  cidioma character(20), -- idioma asociada a la notificacion
  ctipovia character(10), -- tipo de via de la direccion
  cvia character(200), -- Nombre de la via
  cnumerovia character(10), -- numero de la direccion
  cescaleravia character(10), -- escalera de la direccion
  cpisovia character(10), -- piso de la direccion
  cpuertavia character(10), -- puerta de la direccion
  ctelefono character(20), -- telefono de la direccion
  cmunicipio character(100), -- municipio de la direccion
  cprovincia character(100), -- provincia de la direccion
  ccp character(10), -- codigo postal de la direccion
  cerror character(100),
  CONSTRAINT sgmntinfo_notificacion_pk PRIMARY KEY (cnumexp, cnifdest),
  CONSTRAINT sgmnt_info_notificacion_estado_noti_fk FOREIGN KEY (cestado)
      REFERENCES sgmntinfo_estado_noti (cguid) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
) 
WITHOUT OIDS;
ALTER TABLE sgmntinfo_notificacion OWNER TO postgres;
COMMENT ON COLUMN sgmntinfo_notificacion.cguid IS 'Identificador de la notificacion';
COMMENT ON COLUMN sgmntinfo_notificacion.cnifdest IS 'nif del destinatario de la notificacion';
COMMENT ON COLUMN sgmntinfo_notificacion.cnumreg IS 'numero de registro';
COMMENT ON COLUMN sgmntinfo_notificacion.cfhreg IS 'fecha de registro';
COMMENT ON COLUMN sgmntinfo_notificacion.cnumexp IS 'numero de expediente';
COMMENT ON COLUMN sgmntinfo_notificacion.cproc IS 'procedimiento asociado a la notficacion';
COMMENT ON COLUMN sgmntinfo_notificacion.cestado IS 'estado de la notificacion en la ultima actualizacion';
COMMENT ON COLUMN sgmntinfo_notificacion.cfhestado IS 'fecha de la ultima actualizacion del estado';
COMMENT ON COLUMN sgmntinfo_notificacion.cfhentrega IS 'fecha de creacion de la notificacion en el sistema';
COMMENT ON COLUMN sgmntinfo_notificacion.cusuario IS 'usuario asociado a la notificacion';
COMMENT ON COLUMN sgmntinfo_notificacion.ctipocorrespondencia IS 'Tipo de correspondencia de la notifiacacoin';
COMMENT ON COLUMN sgmntinfo_notificacion.corganismo IS 'organismo responsable de la notificacion. Es la entidad emisora con respecto al sisnot';
COMMENT ON COLUMN sgmntinfo_notificacion.casunto IS 'asunto de la notificacion';
COMMENT ON COLUMN sgmntinfo_notificacion.ctipo IS 'tipo de notificacion';
COMMENT ON COLUMN sgmntinfo_notificacion.ctexto IS 'texto de la notificacion';
COMMENT ON COLUMN sgmntinfo_notificacion.cnombredest IS 'nombre del destinatario';
COMMENT ON COLUMN sgmntinfo_notificacion.capellidosdest IS 'apellidos de la notifiaccion';
COMMENT ON COLUMN sgmntinfo_notificacion.ccorreodest IS 'correo del destinatario';
COMMENT ON COLUMN sgmntinfo_notificacion.cidioma IS 'idioma asociada a la notificacion';
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


-- Index: fki_sgmnt_info_notificacion_estado_noti_fk

-- DROP INDEX fki_sgmnt_info_notificacion_estado_noti_fk;

CREATE INDEX fki_sgmnt_info_notificacion_estado_noti_fk
  ON sgmntinfo_notificacion
  USING btree
  (cestado);

-- Table: sgmntinfo_documento

-- DROP TABLE sgmntinfo_documento;

CREATE TABLE sgmntinfo_documento
(
  cnotiexpe character(10) NOT NULL,
  cnotinifdest character(10) NOT NULL,
  ccodigo character(200),
  cguid character(100),
  CONSTRAINT sgmntinfo_documentos_noti_fk FOREIGN KEY (cnotiexpe, cnotinifdest)
      REFERENCES sgmntinfo_notificacion (cnumexp, cnifdest) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
) 
WITHOUT OIDS;
ALTER TABLE sgmntinfo_documento OWNER TO postgres;


-- Index: fki_sgmntinfo_documentos_noti_fk

-- DROP INDEX fki_sgmntinfo_documentos_noti_fk;

CREATE INDEX fki_sgmntinfo_documentos_noti_fk
  ON sgmntinfo_documento
  USING btree
  (cnotiexpe, cnotinifdest);


