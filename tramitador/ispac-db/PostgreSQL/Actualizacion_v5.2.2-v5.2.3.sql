

-----------------------------------
-- Actualización de v5.2.2 a v5.2.3
-----------------------------------

--
-- Información de versión
--
INSERT INTO spac_infosistema (id, codigo, valor, fecha_actualizacion) VALUES (nextval('spac_sq_id_infosistema'), 'VERSIONBD', '5.2.3', current_timestamp);
INSERT INTO spac_infosistema (id, codigo, valor, fecha_actualizacion) VALUES (nextval('spac_sq_id_infosistema'), 'VERSIONAPP', '5.2.3', current_timestamp);


--
-- Eliminación de plantillas de tipos de documentos repetidas
--
DELETE FROM spac_p_plantdoc where id in (26,27,28,29,30,31,32,33,36,37);
