DROP VIEW sgm_adm_usuarios_v;

ALTER TABLE sgm_adm_perfiles
    ALTER COLUMN  id_entidad TYPE character varying(3);

CREATE OR REPLACE VIEW sgm_adm_usuarios_v AS 
 SELECT DISTINCT sgm_adm_perfiles.id_entidad, sgm_adm_perfiles.id_usuario, sgm_adm_usuarios."password", sgm_adm_usuarios.nombre, sgm_adm_usuarios.apellidos, sgm_adm_usuarios.fecha_alta
 FROM sgm_adm_perfiles, sgm_adm_usuarios
 WHERE sgm_adm_perfiles.id_usuario::text = sgm_adm_usuarios.usuario::text
 ORDER BY sgm_adm_perfiles.id_entidad, sgm_adm_perfiles.id_usuario, sgm_adm_usuarios."password", sgm_adm_usuarios.nombre, sgm_adm_usuarios.apellidos, sgm_adm_usuarios.fecha_alta;