
--
-- Trazas de auditorias
--

CREATE SEQUENCE AUDIT_TRAZA_SEQ;

CREATE TABLE AUDIT_TRAZA (
  ID bigint NOT NULL,
  APP_ID bigint NOT NULL,
  APP_DESCRIPTION character varying (128),
  
  EVENT_TYPE bigint NOT NULL,
  EVENT_DATE timestamp without time zone NOT NULL,
  EVENT_DESCRIPTION character varying (512),

  USER_ID  character varying (128) NOT NULL,
  USER_NAME  character varying (256) NOT NULL,
  USER_HOST_NAME character varying (256) NOT NULL,
  USER_IP character varying (256) NOT NULL,

  OBJECT_TYPE character varying (128) NOT NULL,
  OBJECT_TYPE_DESCRIPTION character varying (512),
  OBJECT_ID  character varying (128),
  OBJECT_FIELD character varying (128),

  OLD_VALUE character varying (4000),
  NEW_VALUE character varying (4000)
  
);


ALTER TABLE AUDIT_TRAZA ADD CONSTRAINT PK_AUDIT_TRAZA PRIMARY KEY(ID);

--comentario de los campos de la tabla
COMMENT ON COLUMN AUDIT_TRAZA.ID IS 'Identificador único de traza';
COMMENT ON COLUMN AUDIT_TRAZA.APP_ID IS 'Identificador único de la aplicación que lanza la traza';
COMMENT ON COLUMN AUDIT_TRAZA.APP_DESCRIPTION IS 'Descripción de la aplicación que lanza la traza';
COMMENT ON COLUMN AUDIT_TRAZA.EVENT_TYPE IS 'Identificador del tipo de evento en el que consiste la traza';
COMMENT ON COLUMN AUDIT_TRAZA.EVENT_DATE IS 'Fecha y hora en la que ocurre la traza';
COMMENT ON COLUMN AUDIT_TRAZA.EVENT_DESCRIPTION IS 'Descripción del evento que lanza la traza';
COMMENT ON COLUMN AUDIT_TRAZA.USER_ID IS 'Identificador del usuario que lanza el evento';
COMMENT ON COLUMN AUDIT_TRAZA.USER_NAME IS 'Nombre del usuario que lanza el evento que se traza';
COMMENT ON COLUMN AUDIT_TRAZA.USER_HOST_NAME IS 'Nombre del host del equipo del usuario que lanza el evento que se traza';
COMMENT ON COLUMN AUDIT_TRAZA.USER_IP IS 'Ip del equipo del usuario que lanza el evento que se traza';
COMMENT ON COLUMN AUDIT_TRAZA.OBJECT_TYPE IS 'Identificador del tipo de objeto sobre el que se lanza el evento que se traza';
COMMENT ON COLUMN AUDIT_TRAZA.OBJECT_TYPE_DESCRIPTION IS 'Descripción del tipo de objeto sobre el que se lanza el evento que se traza';
COMMENT ON COLUMN AUDIT_TRAZA.OBJECT_ID IS 'Identificador del objeto sobre el que se lanza la traza';
COMMENT ON COLUMN AUDIT_TRAZA.OBJECT_FIELD IS 'Atributo del objeto sobre el que se lanza la traza';
COMMENT ON COLUMN AUDIT_TRAZA.OLD_VALUE IS 'Valor del atributo del objeto antes de lanzar el evento que modifica el valor';
COMMENT ON COLUMN AUDIT_TRAZA.NEW_VALUE IS 'Valor del atributo del objeto después de lanzar el evento que modifica el valor';


CREATE TABLE AUDIT_APPS (
  APP_DESCRIPTION character varying (128) NOT NULL,
  APP_ID bigint NOT NULL  
);

ALTER TABLE AUDIT_APPS ADD CONSTRAINT PK_AUDIT_APPS PRIMARY KEY(APP_DESCRIPTION);
ALTER TABLE AUDIT_APPS ADD CONSTRAINT UQ_AUDIT_APPS_APP_ID UNIQUE (APP_ID);

--comentario de los campos de la tabla
COMMENT ON COLUMN AUDIT_APPS.APP_DESCRIPTION IS 'Descripción de la aplicación que se registra';
COMMENT ON COLUMN AUDIT_APPS.APP_ID IS 'Identificador de la aplicación que se registra';