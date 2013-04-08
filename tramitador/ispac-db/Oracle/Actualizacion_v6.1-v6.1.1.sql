-----------------------------------
-- Actualización de v6.1 a v6.1.1
-----------------------------------


--
-- Información de versión
--

INSERT INTO spac_infosistema (id, codigo, valor, fecha_actualizacion) VALUES (SPAC_SQ_ID_INFOSISTEMA.NEXTVAL, 'VERSIONBD', '6.1.1', current_timestamp);
INSERT INTO spac_infosistema (id, codigo, valor, fecha_actualizacion) VALUES (SPAC_SQ_ID_INFOSISTEMA.NEXTVAL, 'VERSIONAPP', '6.1.1', current_timestamp);


-- 
-- Incluir en el campo frm_bsq de spac_ct_fmrbusqueda del formulario con id=1: 
--   Actualizacion_v6.1-v6.1.1_Files/frm_busqueda/formularioBusquedaAvanzada.xml
--




--
-- Formulario para intervinientes
--
--     Actualizacion_v6.1-v6.1.1_Files\frm_entidades\thirdForm.jsp 
--         --> SPAC_CT_APLICACIONES.FRM_JSP cuando id=3;
--     Aumentar la versión del formulario
--

--
-- Formulario para expedientes
--
--     Actualizacion_v6.1-v6.1.1_Files\frm_entidades\expedienteForm.jsp 
--         --> SPAC_CT_APLICACIONES.FRM_JSP cuando id=1;
--     Aumentar la versión del formulario
--





