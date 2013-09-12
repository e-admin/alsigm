--
-- Trazas de auditorias
--

CREATE SEQUENCE AUDIT_TRAZA_SEQ;

CREATE TABLE AUDIT_TRAZA (
  ID integer NOT NULL,
  APP_ID integer NOT NULL,
  APP_DESCRIPTION VARCHAR (128),

  EVENT_TYPE integer NOT NULL,
  EVENT_DATE timestamp  NOT NULL,
  EVENT_DESCRIPTION VARCHAR (512),

  USER_ID  VARCHAR (128) NOT NULL,
  USER_NAME  VARCHAR(256) NOT NULL,
  USER_HOST_NAME VARCHAR (256) NOT NULL,
  USER_IP VARCHAR (256) NOT NULL,

  OBJECT_TYPE VARCHAR (128) NOT NULL,
  OBJECT_TYPE_DESCRIPTION VARCHAR (512),
  OBJECT_ID  VARCHAR (128),
  OBJECT_FIELD VARCHAR (128),

  OLD_VALUE VARCHAR (4000),
  NEW_VALUE VARCHAR (4000)

);


ALTER TABLE AUDIT_TRAZA ADD CONSTRAINT PK_AUDIT_TRAZA PRIMARY KEY(ID);


--comentario de los campos de la tabla
COMMENT ON COLUMN AUDIT_TRAZA.ID IS 'Identificador unico de traza';
COMMENT ON COLUMN AUDIT_TRAZA.APP_ID IS 'Identificador unico de la aplicacion que lanza la traza';
COMMENT ON COLUMN AUDIT_TRAZA.APP_DESCRIPTION IS 'Descripcion de la aplicacion que lanza la traza';
COMMENT ON COLUMN AUDIT_TRAZA.EVENT_TYPE IS 'Identificador del tipo de evento en el que consiste la traza';
COMMENT ON COLUMN AUDIT_TRAZA.EVENT_DATE IS 'Fecha y hora en la que ocurre la traza';
COMMENT ON COLUMN AUDIT_TRAZA.EVENT_DESCRIPTION IS 'Descripcion del evento que lanza la traza';
COMMENT ON COLUMN AUDIT_TRAZA.USER_ID IS 'Identificador del usuario que lanza el evento';
COMMENT ON COLUMN AUDIT_TRAZA.USER_NAME IS 'Nombre del usuario que lanza el evento que se traza';
COMMENT ON COLUMN AUDIT_TRAZA.USER_HOST_NAME IS 'Nombre del host del equipo del usuario que lanza el evento que se traza';
COMMENT ON COLUMN AUDIT_TRAZA.USER_IP IS 'IP del equipo del usuario que lanza el evento que se traza';
COMMENT ON COLUMN AUDIT_TRAZA.OBJECT_TYPE IS 'Identificador del tipo de objeto sobre el que se lanza el evento que se traza';
COMMENT ON COLUMN AUDIT_TRAZA.OBJECT_TYPE_DESCRIPTION IS 'Descripcion del tipo de objeto sobre el que se lanza el evento que se traza';
COMMENT ON COLUMN AUDIT_TRAZA.OBJECT_ID IS 'Identificador del objeto sobre el que se lanza la traza';
COMMENT ON COLUMN AUDIT_TRAZA.OBJECT_FIELD IS 'Atributo del objeto sobre el que se lanza la traza';
COMMENT ON COLUMN AUDIT_TRAZA.OLD_VALUE IS 'Valor del atributo del objeto antes de lanzar el evento que modifica el valor';
COMMENT ON COLUMN AUDIT_TRAZA.NEW_VALUE IS 'Valor del atributo del objeto despues de lanzar el evento que modifica el valor';


CREATE TABLE AUDIT_APPS (
  APP_DESCRIPTION VARCHAR (128) NOT NULL,
  APP_ID integer NOT NULL

);

ALTER TABLE AUDIT_APPS ADD CONSTRAINT PK_AUDIT_APPS PRIMARY KEY(APP_DESCRIPTION);
ALTER TABLE AUDIT_APPS ADD CONSTRAINT UQ_AUDIT_APPS_APP_ID UNIQUE (APP_ID);

--comentario de los campos de la tabla
COMMENT ON COLUMN AUDIT_APPS.APP_DESCRIPTION IS 'Descripcion de la aplicacion que se registra';
COMMENT ON COLUMN AUDIT_APPS.APP_ID IS 'Identificador de la aplicacion que se registra';
