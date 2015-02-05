
--
-- Asientos registrales
--

CREATE TABLE sir_aregs_seq (
  id bigint IDENTITY(1, 1) NOT NULL
);


CREATE TABLE sir_asientos_registrales (
  id bigint NOT NULL,
  cd_ent_reg varchar(21) NOT NULL,
  cd_intercambio varchar(33),
  cd_estado integer NOT NULL,
  fe_estado datetime NOT NULL,
  fe_envio datetime,
  fe_recepcion datetime,
  num_reintentos integer DEFAULT 0,
  num_registro varchar(20) ,
  fe_registro datetime ,
  num_registro_inicial varchar(20) NOT NULL,
  fe_registro_inicial datetime NOT NULL,
  ts_registro_inicial image,
  num_exp varchar(80),
  referencia_externa varchar(16),
  ts_entrada image,
  cd_ent_reg_origen varchar(21) NOT NULL,
  ds_ent_reg_origen varchar(80),
  cd_org_origen varchar(21),
  ds_org_origen varchar(80),
  cd_ent_reg_destino varchar(21) NOT NULL,
  ds_ent_reg_destino varchar(80),
  cd_org_destino varchar(21),
  ds_org_destino varchar(80),
  ds_resumen varchar(240),
  cd_asunto_destino varchar(16),
  cd_tipo_transporte varchar(2),
  ds_num_transporte varchar(20),
  ds_nombre_usuario varchar(80),
  ds_contacto_usuario varchar(160),
  cd_app_emisora varchar(4),
  cd_tipo_anotacion varchar(2),
  ds_tipo_anotacion varchar(80),
  cd_tipo_registro varchar(1) NOT NULL,
  cd_doc_fisica varchar(2) NOT NULL,
  ds_observaciones varchar(50),
  cd_prueba varchar(1) NOT NULL,
  cd_ent_reg_inicio varchar(21) NOT NULL,
  ds_ent_reg_inicio varchar(80),
  ds_expone varchar(4000),
  ds_solicita varchar(4000),
  cd_error varchar(4),
  ds_error varchar(4000)
);

ALTER TABLE sir_asientos_registrales ADD CONSTRAINT pk_sir_asientos_registrales PRIMARY KEY(id);
CREATE INDEX ix_sir_asientos_registrales_er_int ON sir_asientos_registrales(cd_ent_reg, cd_intercambio);


--
-- Interesados de asientos registrales
--

CREATE TABLE sir_interesados_seq (
  id bigint IDENTITY(1, 1) NOT NULL
);

CREATE TABLE sir_interesados (
  id bigint NOT NULL,
  id_asiento_registral bigint NOT NULL,
  cd_tipo_identificador varchar(1),
  ds_identificador varchar(17),
  ds_razon_social varchar(80),
  ds_nombre varchar(30),
  ds_apellido1 varchar(30),
  ds_apellido2 varchar(30),
  cd_pais varchar(4),
  cd_provincia varchar(2),
  cd_municipio varchar(5),
  ds_direccion varchar(160),
  cd_postal varchar(5),
  ds_email varchar(160),
  ds_telefono varchar(20),
  ds_deu varchar(160),
  cd_canal_pref varchar(2),
  cd_tipo_identificador_rep varchar(1),
  ds_identificador_rep varchar(17),
  ds_razon_social_rep varchar(80),
  ds_nombre_rep varchar(30),
  ds_apellido1_rep varchar(30),
  ds_apellido2_rep varchar(30),
  cd_pais_rep varchar(4),
  cd_provincia_rep varchar(2),
  cd_municipio_rep varchar(5),
  ds_direccion_rep varchar(160),
  cd_postal_rep varchar(5),
  ds_email_rep varchar(160),
  ds_telefono_rep varchar(20),
  ds_deu_rep varchar(160),
  cd_canal_pref_rep varchar(2),
  ds_observaciones varchar(160)
);

ALTER TABLE sir_interesados ADD CONSTRAINT pk_sir_interesados PRIMARY KEY(id);
ALTER TABLE sir_interesados ADD CONSTRAINT fk_sir_interesados_asiento FOREIGN KEY (id_asiento_registral) REFERENCES sir_asientos_registrales (id);
CREATE INDEX fki_sir_interesados_asiento ON sir_interesados(id_asiento_registral);


--
-- Anexos de asientos registrales
--

CREATE TABLE sir_anexos_seq (
  id bigint IDENTITY(1, 1) NOT NULL
);

CREATE TABLE sir_anexos (
  id bigint NOT NULL,
  id_asiento_registral bigint NOT NULL,
  cd_id_fichero varchar(50),
  ds_nombre_fichero varchar(80) NOT NULL,
  cd_validez varchar(2),
  cd_tipo_documento varchar(2) NOT NULL,
  certificado image,
  validacion_ocsp image,
  firma image,
  ts_fichero image,
  hash image,
  tipo_mime varchar(128),
  --contenido image,
  uid_dm varchar(128),
  id_fichero_firmado bigint,
  cd_id_documento_firmado varchar(50),
  ds_observaciones varchar(50)
);

ALTER TABLE sir_anexos ADD CONSTRAINT pk_sir_anexos PRIMARY KEY(id);
ALTER TABLE sir_anexos ADD CONSTRAINT fk_sir_anexos_asiento FOREIGN KEY (id_asiento_registral) REFERENCES sir_asientos_registrales (id);
CREATE INDEX fki_sir_anexos_asiento ON sir_anexos(id_asiento_registral);


--
-- Contadores para los códigos
--

CREATE TABLE sir_contadores (
  cd_ent_reg varchar(21) NOT NULL,
  tipo integer NOT NULL,
  contador bigint NOT NULL
);

ALTER TABLE sir_contadores ADD CONSTRAINT pk_sir_contadores PRIMARY KEY(cd_ent_reg,tipo);


--
-- Configuración
--

CREATE TABLE sir_config_seq (
  id bigint IDENTITY(1, 1) NOT NULL
);

CREATE TABLE sir_configuracion (
  id bigint NOT NULL,
  nombre varchar(250) NOT NULL,
  descripcion text,
  valor text
);

ALTER TABLE sir_configuracion ADD CONSTRAINT pk_sir_configuracion PRIMARY KEY(id);
CREATE UNIQUE INDEX ix_sir_configuracion_nombre ON sir_configuracion(nombre);
