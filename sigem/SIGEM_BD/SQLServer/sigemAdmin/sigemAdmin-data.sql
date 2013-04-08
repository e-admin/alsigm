-- Inserción de datos

-- Tabla sgm_adm_entidades
INSERT INTO sgm_adm_entidades (id, nombrecorto, nombrelargo, codigo_ine) VALUES ('000', 'Entidad por defecto', 'Entidad por defecto', '');

-- Tabla sgm_adm_usuarios
INSERT INTO sgm_adm_usuarios (usuario, password, nombre, apellidos, fecha_alta) VALUES ('administrador', 'kfUWfDTEAHWBFcKmgm7C4w==', 'Usuario', 'Administrador', '06-05-2008');

-- Tabla sgm_adm_perfiles
INSERT INTO sgm_adm_perfiles (id_entidad, id_usuario, id_aplicacion) VALUES ('000', 'administrador', '1');
INSERT INTO sgm_adm_perfiles (id_entidad, id_usuario, id_aplicacion) VALUES ('000', 'administrador', '2');
INSERT INTO sgm_adm_perfiles (id_entidad, id_usuario, id_aplicacion) VALUES ('000', 'administrador', '3');
INSERT INTO sgm_adm_perfiles (id_entidad, id_usuario, id_aplicacion) VALUES ('000', 'administrador', '4');
INSERT INTO sgm_adm_perfiles (id_entidad, id_usuario, id_aplicacion) VALUES ('000', 'administrador', '5');
INSERT INTO sgm_adm_perfiles (id_entidad, id_usuario, id_aplicacion) VALUES ('000', 'administrador', '6');
INSERT INTO sgm_adm_perfiles (id_entidad, id_usuario, id_aplicacion) VALUES ('000', 'administrador', '7');
INSERT INTO sgm_adm_perfiles (id_entidad, id_usuario, id_aplicacion) VALUES ('000', 'administrador', '8');

-- Tabla sgm_adm_acciones
INSERT INTO sgm_adm_acciones (id, nombre, clase_config, clase_exec, info_adicional) VALUES (1, 'Comparar/Importar reglas', 'ieci.tecdoc.sgm.admsistema.action.accionesmultientidad.CompararImportarReglasAccionConfiguracion', 'ieci.tecdoc.sgm.admsistema.proceso.accionmultientidad.CompararImportarReglasAccionEjecucion', NULL);
INSERT INTO sgm_adm_acciones (id, nombre, clase_config, clase_exec, info_adicional) VALUES (2, 'Importar Procedimiento', 'ieci.tecdoc.sgm.admsistema.action.accionesmultientidad.ImportarProcedimientoAccionConfiguracion', 'ieci.tecdoc.sgm.admsistema.proceso.accionmultientidad.ImportarProcedimientoAccionEjecucion', NULL);