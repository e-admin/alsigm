--
-- Establecer los responsables de los prototipos
--
UPDATE SPAC_P_FASES SET ID_RESP='2-4';
UPDATE SPAC_P_PROCEDIMIENTOS SET ID_RESP='2-4';

--
-- Asignar permisos de inicio de expediente en los prototipos
--
INSERT INTO SPAC_SS_PERMISOS (id, id_pcd, uid_usr, permiso) VALUES (nextval('spac_sq_id_sspermisos'), 3, '2-4', 1);
INSERT INTO SPAC_SS_PERMISOS (id, id_pcd, uid_usr, permiso) VALUES (nextval('spac_sq_id_sspermisos'), 4, '2-4', 1);
INSERT INTO SPAC_SS_PERMISOS (id, id_pcd, uid_usr, permiso) VALUES (nextval('spac_sq_id_sspermisos'), 5, '2-4', 1);