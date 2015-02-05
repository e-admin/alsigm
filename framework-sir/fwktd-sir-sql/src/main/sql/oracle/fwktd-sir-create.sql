
--
-- Asientos registrales
--

CREATE SEQUENCE sir_aregs_seq;

CREATE TABLE sir_asientos_registrales (
  id number NOT NULL,
  cd_ent_reg varchar2(21 CHAR) NOT NULL,
  cd_intercambio varchar2(33 CHAR),
  cd_estado integer NOT NULL,
  fe_estado timestamp NOT NULL,
  fe_envio timestamp,
  fe_recepcion timestamp,
  num_reintentos integer DEFAULT 0,
  num_registro varchar2(20 CHAR) ,
  fe_registro timestamp ,
  num_registro_inicial varchar2(20 CHAR) NOT NULL,
  fe_registro_inicial timestamp NOT NULL,
  ts_registro_inicial blob,
  num_exp varchar2(80 CHAR),
  referencia_externa varchar2(16 CHAR),
  ts_entrada blob,
  cd_ent_reg_origen varchar2(21 CHAR) NOT NULL,
  ds_ent_reg_origen varchar2(80 CHAR),
  cd_org_origen varchar2(21 CHAR),
  ds_org_origen varchar2(80 CHAR),
  cd_ent_reg_destino varchar2(21 CHAR) NOT NULL,
  ds_ent_reg_destino varchar2(80 CHAR),
  cd_org_destino varchar2(21 CHAR),
  ds_org_destino varchar2(80 CHAR),
  ds_resumen varchar2(240 CHAR),
  cd_asunto_destino varchar2(16 CHAR),
  cd_tipo_transporte varchar2(2 CHAR),
  ds_num_transporte varchar2(20 CHAR),
  ds_nombre_usuario varchar2(80 CHAR),
  ds_contacto_usuario varchar2(160 CHAR),
  cd_app_emisora varchar2(4 CHAR),
  cd_tipo_anotacion varchar2(2 CHAR),
  ds_tipo_anotacion varchar2(80 CHAR),
  cd_tipo_registro varchar2(1 CHAR) NOT NULL,
  cd_doc_fisica varchar2(2 CHAR) NOT NULL,
  ds_observaciones varchar2(50 CHAR),
  cd_prueba varchar2(1 CHAR) NOT NULL,
  cd_ent_reg_inicio varchar2(21 CHAR) NOT NULL,
  ds_ent_reg_inicio varchar2(80 CHAR),
  ds_expone varchar2(4000 CHAR),
  ds_solicita varchar2(4000 CHAR),
  cd_error varchar2(4 CHAR),
  ds_error varchar2(4000 CHAR)
);

COMMENT ON COLUMN sir_asientos_registrales.id IS 'Identificador único del asiento registral';
COMMENT ON COLUMN sir_asientos_registrales.cd_intercambio IS 'Identificador de intercambio';
COMMENT ON COLUMN sir_asientos_registrales.cd_estado IS 'Código de estado';
COMMENT ON COLUMN sir_asientos_registrales.fe_estado IS 'Fecha de estado';
COMMENT ON COLUMN sir_asientos_registrales.fe_envio IS 'Fecha de envío';
COMMENT ON COLUMN sir_asientos_registrales.fe_recepcion IS 'Fecha de recepción';
COMMENT ON COLUMN sir_asientos_registrales.num_reintentos IS 'Número de reintentos de envío';
COMMENT ON COLUMN sir_asientos_registrales.num_registro IS 'Número de registro de entrada (en origen)';
COMMENT ON COLUMN sir_asientos_registrales.fe_registro IS 'Fecha y hora de entrada (en origen)';
COMMENT ON COLUMN sir_asientos_registrales.num_registro_inicial IS 'Número de registro inicial';
COMMENT ON COLUMN sir_asientos_registrales.fe_registro_inicial IS 'Fecha y hora del registro inicial';
COMMENT ON COLUMN sir_asientos_registrales.ts_registro_inicial IS 'Timestamp del registro inicial';
COMMENT ON COLUMN sir_asientos_registrales.num_exp IS 'Número de expediente objeto de la tramitación administrativa';
COMMENT ON COLUMN sir_asientos_registrales.referencia_externa IS 'Referencia que el destino precise conocer y sea conocida por el solicitante';
COMMENT ON COLUMN sir_asientos_registrales.ts_entrada IS 'Sello de tiempo (timestamp) del registro de entrada (en origen)';
COMMENT ON COLUMN sir_asientos_registrales.cd_ent_reg_origen IS 'Código de la entidad registral de origen';
COMMENT ON COLUMN sir_asientos_registrales.ds_ent_reg_origen IS 'Descripción de la entidad registral de origen';
COMMENT ON COLUMN sir_asientos_registrales.cd_org_origen IS 'Código de la unidad de tramitación de origen';
COMMENT ON COLUMN sir_asientos_registrales.ds_org_origen IS 'Descripción de la unidad de tramitación de origen';
COMMENT ON COLUMN sir_asientos_registrales.cd_ent_reg_destino IS 'Código de la entidad registral de destino';
COMMENT ON COLUMN sir_asientos_registrales.ds_ent_reg_destino IS 'Descripción de la entidad registral de destino';
COMMENT ON COLUMN sir_asientos_registrales.cd_org_destino IS 'Código de la unidad de tramitación de destino';
COMMENT ON COLUMN sir_asientos_registrales.ds_org_destino IS 'Descripción de la unidad de tramitación de destino';
COMMENT ON COLUMN sir_asientos_registrales.ds_resumen IS 'Abstract o resumen';
COMMENT ON COLUMN sir_asientos_registrales.cd_asunto_destino IS 'Código de asunto en destino';
COMMENT ON COLUMN sir_asientos_registrales.cd_tipo_transporte IS 'Tipo de transporte de entrada';
COMMENT ON COLUMN sir_asientos_registrales.ds_num_transporte IS 'Número de transporte de entrada';
COMMENT ON COLUMN sir_asientos_registrales.ds_nombre_usuario IS 'Nombre de usuario de origen';
COMMENT ON COLUMN sir_asientos_registrales.ds_contacto_usuario IS 'Contacto del usuario de origen (teléfono o dirección de correo electrónico)';
COMMENT ON COLUMN sir_asientos_registrales.cd_app_emisora IS 'Identifica la aplicación emisora y su versión';
COMMENT ON COLUMN sir_asientos_registrales.cd_tipo_anotacion IS 'Tipo de la anotación';
COMMENT ON COLUMN sir_asientos_registrales.ds_tipo_anotacion IS 'Descripción del tipo de anotación';
COMMENT ON COLUMN sir_asientos_registrales.cd_tipo_registro IS 'Tipo de registro';
COMMENT ON COLUMN sir_asientos_registrales.cd_doc_fisica IS 'Indica si el fichero va acompañado de documentación física';
COMMENT ON COLUMN sir_asientos_registrales.ds_observaciones IS 'Observaciones recogidas por el funcionario del registro';
COMMENT ON COLUMN sir_asientos_registrales.cd_prueba IS 'Indica si el asiento registral es una prueba';
COMMENT ON COLUMN sir_asientos_registrales.cd_ent_reg_inicio IS 'Código de la entidad registral de inicio';
COMMENT ON COLUMN sir_asientos_registrales.ds_ent_reg_inicio IS 'Descripción de la entidad registral de inicio';
COMMENT ON COLUMN sir_asientos_registrales.ds_expone IS 'Exposición de los hechos y antecedentes relacionados con la solicitud';
COMMENT ON COLUMN sir_asientos_registrales.ds_solicita IS 'Descripción del objeto de la solicitud';
COMMENT ON COLUMN sir_asientos_registrales.cd_error IS 'Código del error';
COMMENT ON COLUMN sir_asientos_registrales.ds_error IS 'Descripción del error';

ALTER TABLE sir_asientos_registrales ADD CONSTRAINT pk_sir_asientos_registrales PRIMARY KEY(id);
CREATE INDEX ix_sir_aregs_er_int ON sir_asientos_registrales(cd_ent_reg, cd_intercambio);


--
-- Interesados de asientos registrales
--

CREATE SEQUENCE sir_interesados_seq;

CREATE TABLE sir_interesados (
  id number NOT NULL,
  id_asiento_registral number NOT NULL,
  cd_tipo_identificador varchar2(1 CHAR),
  ds_identificador varchar2(17 CHAR),
  ds_razon_social varchar2(80 CHAR),
  ds_nombre varchar2(30 CHAR),
  ds_apellido1 varchar2(30 CHAR),
  ds_apellido2 varchar2(30 CHAR),
  cd_pais varchar2(4 CHAR),
  cd_provincia varchar2(2 CHAR),
  cd_municipio varchar2(5 CHAR),
  ds_direccion varchar2(160 CHAR),
  cd_postal varchar2(5 CHAR),
  ds_email varchar2(160 CHAR),
  ds_telefono varchar2(20 CHAR),
  ds_deu varchar2(160 CHAR),
  cd_canal_pref varchar2(2 CHAR),
  cd_tipo_identificador_rep varchar2(1 CHAR),
  ds_identificador_rep varchar2(17 CHAR),
  ds_razon_social_rep varchar2(80 CHAR),
  ds_nombre_rep varchar2(30 CHAR),
  ds_apellido1_rep varchar2(30 CHAR),
  ds_apellido2_rep varchar2(30 CHAR),
  cd_pais_rep varchar2(4 CHAR),
  cd_provincia_rep varchar2(2 CHAR),
  cd_municipio_rep varchar2(5 CHAR),
  ds_direccion_rep varchar2(160 CHAR),
  cd_postal_rep varchar2(5 CHAR),
  ds_email_rep varchar2(160 CHAR),
  ds_telefono_rep varchar2(20 CHAR),
  ds_deu_rep varchar2(160 CHAR),
  cd_canal_pref_rep varchar2(2 CHAR),
  ds_observaciones varchar2(160 CHAR)
);

COMMENT ON COLUMN sir_interesados.id IS 'Identificador del interesado';
COMMENT ON COLUMN sir_interesados.id_asiento_registral IS 'Identificador del asiento registral';
COMMENT ON COLUMN sir_interesados.cd_tipo_identificador IS 'Código del tipo de identificador del interesado';
COMMENT ON COLUMN sir_interesados.ds_identificador IS 'Identificador del interesado';
COMMENT ON COLUMN sir_interesados.ds_razon_social IS 'Razón social para el caso en el que el interesado sea persona jurídica';
COMMENT ON COLUMN sir_interesados.ds_nombre IS 'Nombre del interesado';
COMMENT ON COLUMN sir_interesados.ds_apellido1 IS 'Primer apellido del interesado';
COMMENT ON COLUMN sir_interesados.ds_apellido2 IS 'Segundo apellido del interesado';
COMMENT ON COLUMN sir_interesados.cd_pais IS 'Código del país del interesado';
COMMENT ON COLUMN sir_interesados.cd_provincia IS 'Código de la provincia del interesado';
COMMENT ON COLUMN sir_interesados.cd_municipio IS 'Código del municipio del interesado';
COMMENT ON COLUMN sir_interesados.ds_direccion IS 'Dirección postal del interesado';
COMMENT ON COLUMN sir_interesados.cd_postal IS 'Código postal del interesado';
COMMENT ON COLUMN sir_interesados.ds_email IS 'Dirección de correo electrónico del interesado';
COMMENT ON COLUMN sir_interesados.ds_telefono IS 'Teléfono de contacto del interesado';
COMMENT ON COLUMN sir_interesados.ds_deu IS 'Dirección electrónica habilitada del interesado';
COMMENT ON COLUMN sir_interesados.cd_canal_pref IS 'Canal preferente de notificación del interesado';
COMMENT ON COLUMN sir_interesados.cd_tipo_identificador_rep IS 'Código del tipo de identificador del representante';
COMMENT ON COLUMN sir_interesados.ds_identificador_rep IS 'Identificador del representante';
COMMENT ON COLUMN sir_interesados.ds_razon_social_rep IS 'Razón social para el caso en el que el representante sea persona jurídica';
COMMENT ON COLUMN sir_interesados.ds_nombre_rep IS 'Nombre del representante';
COMMENT ON COLUMN sir_interesados.ds_apellido1_rep IS 'Primer apellido del representante';
COMMENT ON COLUMN sir_interesados.ds_apellido2_rep IS 'Segundo apellido del representante';
COMMENT ON COLUMN sir_interesados.cd_pais_rep IS 'Código del país del representante';
COMMENT ON COLUMN sir_interesados.cd_provincia_rep IS 'Código de la provincia del representante';
COMMENT ON COLUMN sir_interesados.cd_municipio_rep IS 'Código del municipio del representante';
COMMENT ON COLUMN sir_interesados.ds_direccion_rep IS 'Dirección postal del representante';
COMMENT ON COLUMN sir_interesados.cd_postal_rep IS 'Código postal del representante';
COMMENT ON COLUMN sir_interesados.ds_email_rep IS 'Dirección de correo electrónico del representante';
COMMENT ON COLUMN sir_interesados.ds_telefono_rep IS 'Teléfono de contacto del representante';
COMMENT ON COLUMN sir_interesados.ds_deu_rep IS 'Dirección electrónica habilitada del representante';
COMMENT ON COLUMN sir_interesados.cd_canal_pref_rep IS 'Canal preferente de notificación del representante';
COMMENT ON COLUMN sir_interesados.ds_observaciones IS 'Observaciones del interesado y/o del representante';

ALTER TABLE sir_interesados ADD CONSTRAINT pk_sir_interesados PRIMARY KEY(id);
ALTER TABLE sir_interesados ADD CONSTRAINT fk_sir_interesados_asiento FOREIGN KEY (id_asiento_registral) REFERENCES sir_asientos_registrales (id);
CREATE INDEX fki_sir_interesados_asiento ON sir_interesados(id_asiento_registral);


--
-- Anexos de asientos registrales
--

CREATE SEQUENCE sir_anexos_seq;

CREATE TABLE sir_anexos (
  id number NOT NULL,
  id_asiento_registral number NOT NULL,
  cd_id_fichero varchar2(50 CHAR),
  ds_nombre_fichero varchar2(80 CHAR) NOT NULL,
  cd_validez varchar2(2 CHAR),
  cd_tipo_documento varchar2(2 CHAR) NOT NULL,
  certificado blob,
  validacion_ocsp blob,
  firma blob,
  ts_fichero blob,
  hash blob,
  tipo_mime varchar2(128 CHAR),
  --contenido blob,
  uid_dm varchar2(128 CHAR),
  id_fichero_firmado number,
  cd_id_documento_firmado varchar2(50 CHAR),
  ds_observaciones varchar2(50 CHAR)
);

COMMENT ON COLUMN sir_anexos.id IS 'Identificador del anexo';
COMMENT ON COLUMN sir_anexos.id_asiento_registral IS 'Identificador del asiento registral';
COMMENT ON COLUMN sir_anexos.cd_id_fichero IS 'Identificador del fichero intercambiado';
COMMENT ON COLUMN sir_anexos.ds_nombre_fichero IS 'Nombre del fichero original del documento';
COMMENT ON COLUMN sir_anexos.cd_validez IS 'Validez del documento';
COMMENT ON COLUMN sir_anexos.cd_tipo_documento IS 'Código del tipo de documento';
COMMENT ON COLUMN sir_anexos.certificado IS 'Certificado del fichero anexo';
COMMENT ON COLUMN sir_anexos.validacion_ocsp IS 'Validación OCSP del certificado';
COMMENT ON COLUMN sir_anexos.firma IS 'Firma electrónica del fichero anexo';
COMMENT ON COLUMN sir_anexos.ts_fichero IS 'Sello de tiempo del fichero anexo';
COMMENT ON COLUMN sir_anexos.hash IS 'Huella binaria del fichero anexo';
COMMENT ON COLUMN sir_anexos.tipo_mime IS 'Tipo MIME del fichero anexo';
--COMMENT ON COLUMN sir_anexos.contenido IS 'Contenido del anexo';
COMMENT ON COLUMN sir_anexos.uid_dm IS 'UID del contenido del anexo en el gestor documental';
COMMENT ON COLUMN sir_anexos.id_fichero_firmado IS 'Identificador (id) del fichero firmado';
COMMENT ON COLUMN sir_anexos.cd_id_documento_firmado IS 'Identificador (cd_id_fichero) del documento firmado';
COMMENT ON COLUMN sir_anexos.ds_observaciones IS 'Observaciones del fichero adjunto';

ALTER TABLE sir_anexos ADD CONSTRAINT pk_sir_anexos PRIMARY KEY(id);
ALTER TABLE sir_anexos ADD CONSTRAINT fk_sir_anexos_asiento FOREIGN KEY (id_asiento_registral) REFERENCES sir_asientos_registrales (id);
CREATE INDEX fki_sir_anexos_asiento ON sir_anexos(id_asiento_registral);


--
-- Contadores para los códigos
--

CREATE TABLE sir_contadores (
  cd_ent_reg varchar2(21 CHAR) NOT NULL,
  tipo number NOT NULL,
  contador number NOT NULL
);

COMMENT ON COLUMN sir_contadores.cd_ent_reg IS 'Código de la entidad registral';
COMMENT ON COLUMN sir_contadores.tipo IS 'Tipo de contador';
COMMENT ON COLUMN sir_contadores.contador IS 'Contador';

ALTER TABLE sir_contadores ADD CONSTRAINT pk_sir_contadores PRIMARY KEY(cd_ent_reg,tipo);


--
-- Configuración
--

CREATE SEQUENCE sir_config_seq;

CREATE TABLE sir_configuracion (
  id number NOT NULL,
  nombre varchar2(250 CHAR) NOT NULL,
  descripcion clob,
  valor clob
);

COMMENT ON COLUMN sir_configuracion.id IS 'Identificador único del parámetro de configuración';
COMMENT ON COLUMN sir_configuracion.nombre IS 'Nombre del parámetro de configuración';
COMMENT ON COLUMN sir_configuracion.descripcion IS 'Descripción del parámetro de configuración';
COMMENT ON COLUMN sir_configuracion.valor IS 'Valor del parámetro de configuración';

ALTER TABLE sir_configuracion ADD CONSTRAINT pk_sir_configuracion PRIMARY KEY(id);
CREATE UNIQUE INDEX ix_sir_configuracion_nombre ON sir_configuracion(nombre);
