/** LEEME **
   
   Las sentencias en este script reproducen la siguiente sentencia ALTER, que falla en las BBDD 
   DB2  en las que se ha probado ya que trata de disminuir el tamaño de una columna. Se comenta 
   aquí para que se intente de todas formas ejecutar antes de ejecutar el script alternativo.
   
   /** SCRIPT STANDARD - NO FUNCIONA EN VERSIONES DB2 PROBADAS**/
   ALTER TABLE sgm_adm_perfiles
    ALTER COLUMN  id_entidad SET DATA TYPE character varying(3);
 */

/** SCRIPT ALTERNATIVO **/
ALTER TABLE sgmafsesion_info DROP CONSTRAINT sgmrdeses_info_pk;
ALTER TABLE sgmafsesion_info DROP CONSTRAINT rdeses_info_hookid;

CREATE TABLE TMP_DATOS LIKE sgmafsesion_info;
INSERT INTO TMP_DATOS SELECT * FROM sgmafsesion_info;

DROP TABLE sgmafsesion_info;

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
    id_entidad character varying(10)
);

ALTER TABLE sgmafsesion_info ADD CONSTRAINT sgmrdeses_info_pk PRIMARY KEY (sesionid);
CREATE INDEX fki_ses_inf_hookid ON sgmafsesion_info (conectorid);
ALTER TABLE sgmafsesion_info ADD CONSTRAINT rdeses_info_hookid FOREIGN KEY (conectorid) REFERENCES sgmrtcatalogo_conectores(conectorid);

INSERT INTO sgmafsesion_info SELECT * FROM TMP_DATOS;
DROP TABLE TMP_DATOS;
