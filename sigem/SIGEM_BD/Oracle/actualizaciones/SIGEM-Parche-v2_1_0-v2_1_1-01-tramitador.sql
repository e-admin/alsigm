
--
-- Información de versión
--

INSERT INTO spac_infosistema (id, codigo, valor, fecha_actualizacion) VALUES (SPAC_SQ_ID_INFOSISTEMA.NEXTVAL, 'VERSIONBD', '6.1.1', current_timestamp);
INSERT INTO spac_infosistema (id, codigo, valor, fecha_actualizacion) VALUES (SPAC_SQ_ID_INFOSISTEMA.NEXTVAL, 'VERSIONAPP', '6.1.1', current_timestamp);

--
---Actualizacion regla publicación para el pago de tasas
---

UPDATE pub_reglas 
SET atributos= '<?xml version=''1.0'' encoding=''ISO-8859-1''?><attributes><attribute name=''TASA_1''><tax><name>Resguardo del pago de tasa</name><labels><label locale=''es''>Resguardo del pago de tasa</label><label locale=''eu''>Tasaren ordainagiria</label><label locale=''ca''>Protegeixo del pagament de taxa</label><label locale=''gl''>Resgardo do pago de taxa</label></labels><import>1000</import><sender_entity_id>000000</sender_entity_id><self_settlement_id>200</self_settlement_id></tax></attribute><attribute name=''MENSAJE_SUBSANACION''><labels><label locale=''es''>Documentos a subsanar: ${NOMBRE_DOC}.</label><label locale=''eu''>Konpontzera dokumentuak: ${NOMBRE_DOC}.</label><label locale=''ca''>Documents a esmenar: ${NOMBRE_DOC}.</label><label locale=''gl''>Documentos a emendar: ${NOMBRE_DOC}.</label></labels></attribute><attribute name=''MENSAJE_PAGO''><labels><label locale=''es''>Durante la tramitación de su expediente ${NUMEXP} de ${NOMBREPROCEDIMIENTO} se le comunica que es necesario que acredite el pago de la tasa de ${NOMBRE_PAGO} por un valor de ${IMPORTE_PAGO} euros.</label><label locale=''eu''>${NOMBREPROCEDIMIENTO}(e)ko ${NUMEXP} espedientea bideratzen ari dela, jakinarazten zaizu ${NOMBRE_PAGO}ren tasa (${IMPORTE_PAGO} eurokoa) ordaindu izana ziurtatu behar duzula.</label><label locale=''ca''>Durant la tramitacio del vostre expedient ${NUMEXP} de ${NOMBREPROCEDIMIENTO} se us comunica que acrediteu el pagament de la taxa de ${NOMBRE_PAGO} per un valor de ${IMPORTE_PAGO} euros.</label><label locale=''gl''>Durante a tramitación do seu expediente ${NUMEXP} de ${NOMBREPROCEDIMIENTO} comunicaráselle que é necesario que acredite o pagamento da taxa de ${NOMBRE_PAGO} por un valor de ${IMPORTE_PAGO} euros.</label></labels></attribute></attributes>'
WHERE id IN (
	SELECT id
	FROM pub_reglas 
	WHERE atributos LIKE '%<self_settlement_id>100</self_settlement_id>%');

-- 
-- Incluir en el campo frm_bsq de spac_ct_fmrbusqueda del formulario con id=1: 
--   SIGEM-Parche-v2_1_0-V2_1_1-01-tramitador_Files/frm_busqueda/formularioBusquedaAvanzada.xml
--

--
-- Formulario para intervinientes
--
--     SIGEM-Parche-v2_1_0-V2_1_1-01-tramitador_Files\frm_entidades\thirdForm.jsp 
--         --> SPAC_CT_APLICACIONES.FRM_JSP cuando id=3;
--     Aumentar la versión del formulario
--

--
-- Formulario para expedientes
--
--     SIGEM-Parche-v2_1_0-V2_1_1-01-tramitador_Files\frm_entidades\expedienteForm.jsp 
--         --> SPAC_CT_APLICACIONES.FRM_JSP cuando id=1;
--     Aumentar la versión del formulario
--

--
-- Formulario corregidos
--
--     SIGEM-Parche-v2_1_0-V2_1_1-01-tramitador_Files\frm_entidades\frm_fase_instruccion_cerrado.jsp 
--         --> SPAC_CT_APLICACIONES.FRM_JSP cuando id=19;
--     Aumentar la versión del formulario
--
--     SIGEM-Parche-v2_1_0-V2_1_1-01-tramitador_Files\frm_entidades\frm_fase_inicio.jsp 
--         --> SPAC_CT_APLICACIONES.FRM_JSP cuando id=20;
--     Aumentar la versión del formulario
--
--     SIGEM-Parche-v2_1_0-V2_1_1-01-tramitador_Files\frm_entidades\FrmCrEnt_OBRA_MENOR.jsp 
--         --> SPAC_CT_APLICACIONES.FRM_JSP cuando id=21;
--     Aumentar la versión del formulario
--
--     SIGEM-Parche-v2_1_0-V2_1_1-01-tramitador_Files\frm_entidades\lotes.jsp 
--         --> SPAC_CT_APLICACIONES.FRM_JSP cuando id=24;
--     Aumentar la versión del formulario
--
--     SIGEM-Parche-v2_1_0-V2_1_1-01-tramitador_Files\frm_entidades\contrato.jsp 
--         --> SPAC_CT_APLICACIONES.FRM_JSP cuando id=25;
--     Aumentar la versión del formulario
--

-- Informes
-- Actualizar el informe de 'Etiqueta del expediente' (ID = 1) con el contenido del fichero incluido en: 
-- SIGEM-Parche-v2_1_0-V2_1_1-01-tramitador_Files/informes/01_etiqueta.jrxml
--
-- Actualizar el informe de 'Ficha del Expediente' (ID = 2) con el contenido del fichero incluido en: 
-- SIGEM-Parche-v2_1_0-V2_1_1-01-tramitador_Files/informes/02_fichaExpediente.jrxml







