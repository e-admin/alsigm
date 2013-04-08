--
-- Establecer los responsables de los prototipos
--
UPDATE SPAC_P_FASES SET ID_RESP='2-4';
UPDATE SPAC_P_PROCEDIMIENTOS SET ID_RESP='2-4';


--
-- Asignar permisos de inicio de expediente en los prototipos
--

DECLARE @sequence_id int
UPDATE SPAC_SQ_SEQUENCES SET @sequence_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_SSPERMISOS';
INSERT INTO SPAC_SS_PERMISOS (id, id_pcd, uid_usr, permiso) VALUES (@sequence_id, 3, '2-4', 1);
UPDATE SPAC_SQ_SEQUENCES SET @sequence_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_SSPERMISOS';
INSERT INTO SPAC_SS_PERMISOS (id, id_pcd, uid_usr, permiso) VALUES (@sequence_id, 4, '2-4', 1);
UPDATE SPAC_SQ_SEQUENCES SET @sequence_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_SSPERMISOS';
INSERT INTO SPAC_SS_PERMISOS (id, id_pcd, uid_usr, permiso) VALUES (@sequence_id, 5, '2-4', 1);
UPDATE SPAC_SQ_SEQUENCES SET @sequence_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_SSPERMISOS';
INSERT INTO SPAC_SS_PERMISOS (id, id_pcd, uid_usr, permiso) VALUES (@sequence_id, 7, '2-4', 1);
UPDATE SPAC_SQ_SEQUENCES SET @sequence_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_SSPERMISOS';
INSERT INTO SPAC_SS_PERMISOS (id, id_pcd, uid_usr, permiso) VALUES (@sequence_id, 8, '2-4', 1);
UPDATE SPAC_SQ_SEQUENCES SET @sequence_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_SSPERMISOS';
INSERT INTO SPAC_SS_PERMISOS (id, id_pcd, uid_usr, permiso) VALUES (@sequence_id, 9, '2-4', 1);
UPDATE SPAC_SQ_SEQUENCES SET @sequence_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_SSPERMISOS';
INSERT INTO SPAC_SS_PERMISOS (id, id_pcd, uid_usr, permiso) VALUES (@sequence_id, 10, '2-4', 1);
UPDATE SPAC_SQ_SEQUENCES SET @sequence_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_SSPERMISOS';
INSERT INTO SPAC_SS_PERMISOS (id, id_pcd, uid_usr, permiso) VALUES (@sequence_id, 11, '2-4', 1);
UPDATE SPAC_SQ_SEQUENCES SET @sequence_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_SSPERMISOS';
INSERT INTO SPAC_SS_PERMISOS (id, id_pcd, uid_usr, permiso) VALUES (@sequence_id, 12, '2-4', 1);
UPDATE SPAC_SQ_SEQUENCES SET @sequence_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_SSPERMISOS';
INSERT INTO SPAC_SS_PERMISOS (id, id_pcd, uid_usr, permiso) VALUES (@sequence_id, 13, '2-4', 1);
UPDATE SPAC_SQ_SEQUENCES SET @sequence_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_SSPERMISOS';
INSERT INTO SPAC_SS_PERMISOS (id, id_pcd, uid_usr, permiso) VALUES (@sequence_id, 14, '2-4', 1);
UPDATE SPAC_SQ_SEQUENCES SET @sequence_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_SSPERMISOS';
INSERT INTO SPAC_SS_PERMISOS (id, id_pcd, uid_usr, permiso) VALUES (@sequence_id, 15, '2-4', 1);
UPDATE SPAC_SQ_SEQUENCES SET @sequence_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_SSPERMISOS';
INSERT INTO SPAC_SS_PERMISOS (id, id_pcd, uid_usr, permiso) VALUES (@sequence_id, 16, '2-4', 1);