-----------------------------------
-- Actualización de v5.5.1 a v5.6
-----------------------------------


--
-- Información de versión
--

INSERT INTO spac_infosistema (id, codigo, valor, fecha_actualizacion) VALUES (SPAC_SQ_ID_INFOSISTEMA.NEXTVAL, 'VERSIONBD', '5.6', SYSDATE);
INSERT INTO spac_infosistema (id, codigo, valor, fecha_actualizacion) VALUES (SPAC_SQ_ID_INFOSISTEMA.NEXTVAL, 'VERSIONAPP', '5.6', SYSDATE);


--
-- Eliminar en los trámites con subprocesos las relaciones con tipos de documentos
--
DELETE FROM SPAC_CT_TRTD WHERE ID_TRAMITE IN (SELECT ID FROM SPAC_CT_TRAMITES WHERE ID_SUBPROCESO IS NOT NULL);


--
-- Añade el campo documentos que indica si tiene activado o no la pestaña de documentos para el formulario de la entidad
--
ALTER TABLE SPAC_CT_APLICACIONES ADD DOCUMENTOS  VARCHAR2(2) DEFAULT 'NO';


--
-- Cambio del tipo de la columna VALOR en SPAC_VARS
--
ALTER TABLE SPAC_VARS ADD  AUX VARCHAR2(128);
UPDATE SPAC_VARS SET AUX = VALOR;
ALTER TABLE SPAC_VARS DROP COLUMN VALOR;
ALTER TABLE SPAC_VARS ADD VALOR CLOB;
UPDATE SPAC_VARS SET VALOR = AUX;
ALTER TABLE SPAC_VARS DROP COLUMN AUX;



--
-- Configuración del sello de documentos
--
INSERT INTO spac_vars (id, nombre, valor, descripcion) VALUES (SPAC_SQ_ID_VARS.NEXTVAL, 'STAMP_CONFIG', '<stamp width="4500" height="4000"><image width="250" height="100"><rectangle x="0" y="0" width="250" height="100" color="#000000"/><rectangle x="2" y="2" width="245" height="95" color="#0000FF"/><string x="20" y="25" color="#0000FF"><labels><label locale="ca">NOM DE L''ORGANITZACIO</label><label locale="eu">ERAKUNDEAREN IZENA</label><label locale="es">NOMBRE DE ORGANIZACIÓN</label><label locale="gl">NOME DE ORGANIZACIÓN</label></labels></string><string x="50" y="45" color="#0000FF"><labels><label locale="ca">REGISTRE DE ${TP_REG}</label><label locale="eu">ERREGISTROA ${TP_REG}</label><label locale="es">REGISTRO DE ${TP_REG}</label><label locale="gl">REXISTRO DE ${TP_REG}</label></labels></string><string x="40" y="65" color="#0000FF"><labels><label locale="ca">NUMERO: ${NREG}</label><label locale="eu">ZENBAKIA: ${NREG}</label><label locale="es">NÚMERO: ${NREG}</label><label locale="gl">NÚMERO: ${NREG}</label></labels></string><string x="55" y="85" color="#0000FF"><labels><label locale="ca">DATA: ${FREG}</label><label locale="eu">DATA: ${FREG}</label><label locale="es">FECHA: ${FREG}</label><label locale="gl">DATA: ${FREG}</label></labels></string></image></stamp>', 'Configuración del sello de documentos');


-- 
-- Incluir en el campo frm_bsq de spac_ct_fmrbusqueda del formulario con id=1: 
--   SIGEM-Parche-v1_8_0-v1_9_0-01-tramitador_Files/frm_busqueda/busquedaAvanzada.xml
--

--
-- Formulario para intervinientes
--
--     SIGEM-Parche-v1_8_0-v1_9_0-01-tramitador_Files\frm_entidades\thirdForm.jsp 
--         --> SPAC_CT_APLICACIONES.FRM_JSP cuando id=3;
--     Aumentar la versión del formulario
--