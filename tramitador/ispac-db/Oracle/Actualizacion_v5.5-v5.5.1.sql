-----------------------------------
-- Actualización de v5.5 a v5.5.1
-----------------------------------


--
-- Información de versión
--

INSERT INTO spac_infosistema (id, codigo, valor, fecha_actualizacion) VALUES (SPAC_SQ_ID_INFOSISTEMA.NEXTVAL, 'VERSIONBD', '5.5.1', current_timestamp);
INSERT INTO spac_infosistema (id, codigo, valor, fecha_actualizacion) VALUES (SPAC_SQ_ID_INFOSISTEMA.NEXTVAL, 'VERSIONAPP', '5.5.1', current_timestamp);


---
--- Informes
---
ALTER TABLE spac_ct_informes ADD filas NUMBER(2);
ALTER TABLE spac_ct_informes ADD columnas NUMBER(2);

UPDATE spac_ct_informes SET fecha = current_timestamp,
descripcion = 'Informe Etiquetas 4x2',
filas = 4,
columnas = 2
WHERE ID=1;
-- 
-- Incluir en el campo XML el contenido del fichero: 
--   Actualizacion_v5.5-v5.5.1_Files/informes/Etiqueta genérica del expediente.jrxml
--

UPDATE spac_ct_informes SET fecha = current_timestamp
WHERE ID=2;
-- 
-- Incluir en el campo XML el contenido del fichero: 
--   Actualizacion_v5.5-v5.5.1_Files/informes/Ficha genérica del expediente.jrxml
--


--
-- Recursos modificados
--
UPDATE SPAC_CT_ENTIDADES_RESOURCES SET CLAVE = 'NVERSION' WHERE ID_ENT = 1 AND CLAVE = 'VERSION';
INSERT INTO SPAC_CT_ENTIDADES_RESOURCES (ID, ID_ENT, IDIOMA, CLAVE, VALOR) VALUES (SPAC_SQ_ID_ENTIDADESRESOURCES.NEXTVAL, 1, 'es', 'IDDIRECCIONPOSTAL', 'Id. de Dirección Postal');
DELETE FROM SPAC_CT_ENTIDADES_RESOURCES WHERE ID_ENT = 2 AND CLAVE = 'NDOC';
DELETE FROM SPAC_CT_ENTIDADES_RESOURCES WHERE ID_ENT = 2 AND CLAVE = 'NUM_ACTO';
DELETE FROM SPAC_CT_ENTIDADES_RESOURCES WHERE ID = 153;
INSERT INTO SPAC_CT_ENTIDADES_RESOURCES (ID, ID_ENT, IDIOMA, CLAVE, VALOR) VALUES (SPAC_SQ_ID_ENTIDADESRESOURCES.NEXTVAL, 2, 'es', 'ID_NOTIFICACION', 'Id. de Notificación');
INSERT INTO SPAC_CT_ENTIDADES_RESOURCES (ID, ID_ENT, IDIOMA, CLAVE, VALOR) VALUES (SPAC_SQ_ID_ENTIDADESRESOURCES.NEXTVAL, 2, 'es', 'AUTOR', 'Autor');
INSERT INTO SPAC_CT_ENTIDADES_RESOURCES (ID, ID_ENT, IDIOMA, CLAVE, VALOR) VALUES (SPAC_SQ_ID_ENTIDADESRESOURCES.NEXTVAL, 3, 'es', 'IDDIRECCIONPOSTAL', 'Id. de Dirección Postal');
INSERT INTO SPAC_CT_ENTIDADES_RESOURCES (ID, ID_ENT, IDIOMA, CLAVE, VALOR) VALUES (SPAC_SQ_ID_ENTIDADESRESOURCES.NEXTVAL, 7, 'es', 'ID_SUBPROCESO', 'Id. de Subproceso');


--
-- Recursos para direcciones en expediente e intervinientes
--
INSERT INTO SPAC_CT_ENTIDADES_RESOURCES (ID, ID_ENT, IDIOMA, CLAVE, VALOR) VALUES (SPAC_SQ_ID_ENTIDADESRESOURCES.NEXTVAL, 1, 'es', 'LBL_LIBRE', 'Libre');
INSERT INTO SPAC_CT_ENTIDADES_RESOURCES (ID, ID_ENT, IDIOMA, CLAVE, VALOR) VALUES (SPAC_SQ_ID_ENTIDADESRESOURCES.NEXTVAL, 1, 'es', 'LBL_CONFIRMADA', 'Verificada');
INSERT INTO SPAC_CT_ENTIDADES_RESOURCES (ID, ID_ENT, IDIOMA, CLAVE, VALOR) VALUES (SPAC_SQ_ID_ENTIDADESRESOURCES.NEXTVAL, 1, 'es', 'SEP_DIRECCIONES', 'DIRECCION NOTIFICACIÓN');
INSERT INTO SPAC_CT_ENTIDADES_RESOURCES (ID, ID_ENT, IDIOMA, CLAVE, VALOR) VALUES (SPAC_SQ_ID_ENTIDADESRESOURCES.NEXTVAL, 3, 'es', 'SEP_DIRECCIONES', 'DIRECCION NOTIFICACIÓN');
INSERT INTO SPAC_CT_ENTIDADES_RESOURCES (ID, ID_ENT, IDIOMA, CLAVE, VALOR) VALUES (SPAC_SQ_ID_ENTIDADESRESOURCES.NEXTVAL, 3, 'es', 'LBL_CONFIRMADA', 'Verificada');
INSERT INTO SPAC_CT_ENTIDADES_RESOURCES (ID, ID_ENT, IDIOMA, CLAVE, VALOR) VALUES (SPAC_SQ_ID_ENTIDADESRESOURCES.NEXTVAL, 3, 'es', 'LBL_LIBRE', 'Libre');

--
-- Formulario para expedientes
--
--     Actualizacion_v5.5-v5.5.1_Files\frm_entidades\expedientForm.jsp 
--         --> SPAC_CT_APLICACIONES.FRM_JSP cuando id=1;
--     Aumentar la versión del formulario
--

--
-- Formulario para intervinientes
--
--     Actualizacion_v5.5-v5.5.1_Files\frm_entidades\thirdForm.jsp 
--         --> SPAC_CT_APLICACIONES.FRM_JSP cuando id=3;
--     Aumentar la versión del formulario
--
