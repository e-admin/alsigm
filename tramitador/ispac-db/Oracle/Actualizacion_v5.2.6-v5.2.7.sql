-----------------------------------
-- Actualización de v5.2.6 a v5.2.7
-----------------------------------

--
-- Información de versión
--

INSERT INTO spac_infosistema (id, codigo, valor, fecha_actualizacion) VALUES (SPAC_SQ_ID_INFOSISTEMA.NEXTVAL, 'VERSIONBD', '5.2.7', current_timestamp);
INSERT INTO spac_infosistema (id, codigo, valor, fecha_actualizacion) VALUES (SPAC_SQ_ID_INFOSISTEMA.NEXTVAL, 'VERSIONAPP', '5.2.7', current_timestamp);


--
-- Añadir campo a la tabla SPAC_P_TRAMITES
--
ALTER TABLE spac_p_tramites ADD obligatorio number;


--
-- Añadir campo de condicion a la tabla SPAC_P_EVENTOS
--
ALTER TABLE spac_p_eventos ADD condicion clob;


--
-- Actualización de nombres de tablas en validaciones de entidad y parámetros de formularios
--
UPDATE spac_ct_aplicaciones 
SET parametros = '<?xml version=''1.0'' encoding=''ISO-8859-1''?><parameters><entity type=''VALIDATION''><table>SPAC_TBL_003</table><relation type=''FLD''><primary-field>FORMATERMINACION</primary-field><secondary-field>VALOR</secondary-field></relation></entity><entity type=''VALIDATION''><table>SPAC_TBL_004</table><relation type=''FLD''><primary-field>ESTADOADM</primary-field><secondary-field>VALOR</secondary-field></relation></entity><entity type=''VALIDATION''><table>SPAC_TBL_005</table><relation type=''FLD''><primary-field>TIPODIRECCIONINTERESADO</primary-field><secondary-field>VALOR</secondary-field></relation></entity><entity type=''VALIDATION''><table>SPAC_TBL_002</table><relation type=''FLD''><primary-field>ROLTITULAR</primary-field><secondary-field>VALOR</secondary-field></relation></entity></parameters>'
WHERE id = 1;
UPDATE spac_ct_aplicaciones 
SET parametros = '<?xml version=''1.0'' encoding=''ISO-8859-1''?><parameters><entity type=''COMPOSITE''><table>SPAC_DT_TRAMITES</table><relation type=''FLD''><primary-field>ID_TRAMITE</primary-field><secondary-field>ID_TRAM_EXP</secondary-field></relation></entity><entity type=''VALIDATION'' primaryTable=''SPAC_DT_TRAMITES''><table>SPAC_TBL_001</table><relation type=''FLD''><primary-field>UPLAZO</primary-field><secondary-field>VALOR</secondary-field></relation></entity><entity type=''VALIDATION''><table>SPAC_TBL_006</table><relation type=''FLD''><primary-field>ESTADONOTIFICACION</primary-field><secondary-field>VALOR</secondary-field></relation></entity><entity type=''VALIDATION''><table>SPAC_TBL_008</table><relation type=''FLD''><primary-field>ESTADOFIRMA</primary-field><secondary-field>VALOR</secondary-field></relation></entity></parameters>'
WHERE id = 2;
UPDATE spac_ct_aplicaciones 
SET parametros = '<?xml version=''1.0'' encoding=''ISO-8859-1''?><parameters><list-order>NOMBRE</list-order><entity type=''VALIDATION''><table>SPAC_TBL_005</table><relation type=''FLD''><primary-field>TIPO_DIRECCION</primary-field><secondary-field>VALOR</secondary-field></relation></entity><entity type=''VALIDATION''><table>SPAC_TBL_002</table><relation type=''FLD''><primary-field>ROL</primary-field><secondary-field>VALOR</secondary-field></relation></entity></parameters>'
WHERE id = 3;
UPDATE spac_ct_aplicaciones 
SET parametros = '<?xml version=''1.0'' encoding=''ISO-8859-1''?><parameters><entity type=''DETAIL''><table>SPAC_DT_DOCUMENTOS</table><relation type=''FLD''><primary-field>ID_TRAM_EXP</primary-field><secondary-field>ID_TRAMITE</secondary-field></relation></entity><entity type=''VALIDATION''><table>SPAC_TBL_001</table><relation type=''FLD''><primary-field>UPLAZO</primary-field><secondary-field>VALOR</secondary-field></relation></entity></parameters>'
WHERE id = 4;
UPDATE spac_ct_aplicaciones 
SET parametros = '<?xml version=''1.0'' encoding=''ISO-8859-1''?><parameters><list-order>NOMBRE</list-order><entity type=''VALIDATION''><table>SPAC_TBL_006</table><relation type=''FLD''><primary-field>ESTADONOTIFICACION</primary-field><secondary-field>VALOR</secondary-field></relation></entity><entity type=''VALIDATION''><table>SPAC_TBL_008</table><relation type=''FLD''><primary-field>ESTADOFIRMA</primary-field><secondary-field>VALOR</secondary-field></relation></entity></parameters>'
WHERE id = 5;

UPDATE spac_ct_entidades SET definicion = '<entity version=''1.00''>
<editable>S</editable>
<dropable>N</dropable>
<status>0</status>
<fields>
	<field id=''1''>
		<physicalName>id_pcd</physicalName>
		<type>3</type>
	</field>
	<field id=''2''>
		<physicalName>numexp</physicalName>
		<type>0</type>
		<size>30</size>
	</field>
	<field id=''3''>
		<physicalName>referencia_interna</physicalName>
		<type>0</type>
		<size>30</size>
	</field>
	<field id=''4''>
		<physicalName>nreg</physicalName>
		<type>0</type>
		<size>16</size>
	</field>
	<field id=''5''>
		<physicalName>freg</physicalName>
		<type>6</type>
	</field>
	<field id=''6''>
		<physicalName>estadoinfo</physicalName>
		<type>0</type>
		<size>128</size>
	</field>
	<field id=''7''>
		<physicalName>festado</physicalName>
		<type>6</type>
	</field>
	<field id=''8''>
		<physicalName>nifciftitular</physicalName>
		<type>0</type>
		<size>16</size>
	</field>
	<field id=''9''>
		<physicalName>idtitular</physicalName>
		<type>3</type>
	</field>
	<field id=''10''>
		<physicalName>domicilio</physicalName>
		<type>0</type>
		<size>128</size>
	</field>
	<field id=''11''>
		<physicalName>ciudad</physicalName>
		<type>0</type>
		<size>64</size>
	</field>
	<field id=''12''>
		<physicalName>regionpais</physicalName>
		<type>0</type>
		<size>64</size>
	</field>
	<field id=''13''>
		<physicalName>cpostal</physicalName>
		<type>0</type>
		<size>5</size>
	</field>
	<field id=''14''>
		<physicalName>identidadtitular</physicalName>
		<type>0</type>
		<size>128</size>
	</field>
	<field id=''15''>
		<physicalName>tipopersona</physicalName>
		<type>0</type>
		<size>1</size>
	</field>
	<field id=''16''>
		<physicalName>roltitular</physicalName>
		<type>0</type>
		<size>4</size>
	</field>
	<field id=''17''>
		<physicalName>asunto</physicalName>
		<type>0</type>
		<size>512</size>
	</field>
	<field id=''18''>
		<physicalName>finicioplazo</physicalName>
		<type>6</type>
	</field>
	<field id=''19''>
		<physicalName>poblacion</physicalName>
		<type>0</type>
		<size>64</size>
	</field>
	<field id=''20''>
		<physicalName>municipio</physicalName>
		<type>0</type>
		<size>64</size>
	</field>
	<field id=''21''>
		<physicalName>localizacion</physicalName>
		<type>0</type>
		<size>256</size>
	</field>
	<field id=''22''>
		<physicalName>exprelacionados</physicalName>
		<type>0</type>
		<size>256</size>
	</field>
	<field id=''23''>
		<physicalName>codprocedimiento</physicalName>
		<type>0</type>
		<size>16</size>
	</field>
	<field id=''24''>
		<physicalName>nombreprocedimiento</physicalName>
		<type>0</type>
		<size>128</size>
	</field>
	<field id=''25''>
		<physicalName>plazo</physicalName>
		<type>3</type>
	</field>
	<field id=''26''>
		<physicalName>uplazo</physicalName>
		<type>0</type>
		<size>1</size>
	</field>
	<field id=''27''>
		<physicalName>formaterminacion</physicalName>
		<type>0</type>
		<size>1</size>
	</field>
	<field id=''28''>
		<physicalName>utramitadora</physicalName>
		<type>0</type>
		<size>256</size>
	</field>
	<field id=''29''>
		<physicalName>funcionactividad</physicalName>
		<type>0</type>
		<size>80</size>
	</field>
	<field id=''30''>
		<physicalName>materias</physicalName>
		<type>0</type>
		<size>2</size>
	</field>
	<field id=''31''>
		<physicalName>servpresactuaciones</physicalName>
		<type>0</type>
		<size>128</size>
	</field>
	<field id=''32''>
		<physicalName>tipodedocumental</physicalName>
		<type>0</type>
		<size>16</size>
	</field>
	<field id=''33''>
		<physicalName>palabrasclave</physicalName>
		<type>0</type>
		<size>64</size>
	</field>
	<field id=''34''>
		<physicalName>fcierre</physicalName>
		<type>6</type>
	</field>
	<field id=''35''>
		<physicalName>estadoadm</physicalName>
		<type>0</type>
		<size>128</size>
	</field>
	<field id=''36''>
		<physicalName>hayrecurso</physicalName>
		<type>0</type>
		<size>2</size>
	</field>
	<field id=''37''>
		<physicalName>efectosdelsilencio</physicalName>
		<type>0</type>
		<size>1</size>
	</field>
	<field id=''38''>
		<physicalName>fapertura</physicalName>
		<type>6</type>
	</field>
	<field id=''39''>
		<physicalName>observaciones</physicalName>
		<type>0</type>
		<size>256</size>
	</field>
	<field id=''40''>
		<physicalName>idunidadtramitadora</physicalName>
		<type>3</type>
	</field>
	<field id=''41''>
		<physicalName>idproceso</physicalName>
		<type>3</type>
	</field>
	<field id=''42''>
		<physicalName>tipodireccioninteresado</physicalName>
		<type>0</type>
		<size>16</size>
	</field>
	<field id=''43''>
		<physicalName>version</physicalName>
		<type>0</type>
		<size>16</size>
	</field>
	<field id=''44''>
		<physicalName>idseccioniniciadora</physicalName>
		<type>0</type>
		<size>64</size>
	</field>
	<field id=''45''>
		<physicalName>seccioniniciadora</physicalName>
		<type>0</type>
		<size>250</size>
	</field>
	<field id=''46''>
		<physicalName>tfnofijo</physicalName>
		<type>0</type>
		<size>32</size>
	</field>
	<field id=''47''>
		<physicalName>tfnomovil</physicalName>
		<type>0</type>
		<size>32</size>
	</field>
	<field id=''48''>
		<physicalName>direcciontelematica</physicalName>
		<type>0</type>
		<size>60</size>
	</field>
	<field id=''49''>
		<physicalName>id</physicalName>
		<type>3</type>
	</field>
</fields>
<validations>
	<validation id=''1''>
		<fieldId>27</fieldId>
		<table>SPAC_TBL_003</table>
		<tableType>3</tableType>
		<class/>
		<mandatory>N</mandatory>
	</validation>
	<validation id=''2''>
		<fieldId>35</fieldId>
		<table>SPAC_TBL_004</table>
		<tableType>3</tableType>
		<class/>
		<mandatory>N</mandatory>
		</validation>
	<validation id=''3''>
		<fieldId>42</fieldId>
		<table>SPAC_TBL_005</table>
		<tableType>3</tableType>
		<class/>
		<mandatory>N</mandatory>
	</validation>
	<validation id=''4''>
		<fieldId>16</fieldId>
		<table>SPAC_TBL_002</table>
		<tableType>3</tableType>
		<class/>
		<mandatory>N</mandatory>
	</validation>
	<validation id=''5''>
		<fieldId>17</fieldId>
		<table/>
		<tableType/>
		<class/>
		<mandatory>S</mandatory>
	</validation>
</validations>
</entity>'
WHERE id = 1;

UPDATE spac_ct_entidades SET definicion = '<entity version=''1.00''>
<editable>S</editable>
<dropable>N</dropable>
<status>0</status>
<fields>
	<field id=''1''>
		<physicalName>id_ext</physicalName>
		<type>0</type>
		<size>20</size>
	</field>
	<field id=''2''>
		<physicalName>rol</physicalName>
		<type>0</type>
		<size>4</size>
	</field>
	<field id=''3''>
		<physicalName>tipo</physicalName>
		<type>3</type>
	</field>
	<field id=''4''>
		<physicalName>tipo_persona</physicalName>
		<type>0</type>
		<size>1</size>
	</field>
	<field id=''5''>
		<physicalName>ndoc</physicalName>
		<type>0</type>
		<size>12</size>
	</field>
	<field id=''6''>
		<physicalName>nombre</physicalName>
		<type>0</type>
		<size>250</size>
	</field>
	<field id=''7''>
		<physicalName>dirnot</physicalName>
		<type>0</type>
		<size>250</size>
	</field>
	<field id=''8''>
		<physicalName>email</physicalName>
		<type>0</type>
		<size>250</size>
	</field>
	<field id=''9''>
		<physicalName>c_postal</physicalName>
		<type>0</type>
		<size>10</size>
	</field>
	<field id=''10''>
		<physicalName>localidad</physicalName>
		<type>0</type>
		<size>150</size>
	</field>
	<field id=''11''>
		<physicalName>caut</physicalName>
		<type>0</type>
		<size>150</size>
	</field>
	<field id=''12''>
		<physicalName>tfno_fijo</physicalName>
		<type>0</type>
		<size>32</size>
	</field>
	<field id=''13''>
		<physicalName>tfno_movil</physicalName>
		<type>0</type>
		<size>32</size>
	</field>
	<field id=''14''>
		<physicalName>tipo_direccion</physicalName>
		<type>0</type>
		<size>1</size>
	</field>
	<field id=''15''>
		<physicalName>direcciontelematica</physicalName>
		<type>0</type>
		<size>60</size>
		</field>
	<field id=''16''>
		<physicalName>id</physicalName>
		<type>3</type>
	</field>
	<field id=''17''>
		<physicalName>numexp</physicalName>
		<type>0</type>
		<size>30</size>
	</field>
</fields>
<validations>
	<validation id=''1''>
		<fieldId>6</fieldId>
		<table/>
		<tableType/>
		<class/>
		<mandatory>S</mandatory>
	</validation>
	<validation id=''2''>
		<fieldId>14</fieldId>
		<table>SPAC_TBL_005</table>
		<tableType>3</tableType>
		<class/>
		<mandatory>N</mandatory>
	</validation>
	<validation id=''3''>
		<fieldId>2</fieldId>
		<table>SPAC_TBL_002</table>
		<tableType>3</tableType>
		<class/>
		<mandatory>N</mandatory>
	</validation>
</validations>
</entity>'
WHERE id = 3;

UPDATE spac_ct_entidades SET definicion = '<entity version=''1.00''>
<editable>S</editable>
<dropable>N</dropable>
<status>0</status>
<fields>
	<field id=''1''>
		<physicalName>nombre</physicalName>
		<type>0</type>
		<size>250</size>
	</field>
	<field id=''2''>
		<physicalName>estado</physicalName>
		<type>2</type>
		<size>1</size>
	</field>
	<field id=''3''>
		<physicalName>id_tram_pcd</physicalName>
		<type>3</type>
	</field>
	<field id=''4''>
		<physicalName>id_fase_pcd</physicalName>
		<type>3</type>
	</field>
	<field id=''5''>
		<physicalName>id_fase_exp</physicalName>
		<type>3</type>
	</field>
	<field id=''6''>
		<physicalName>id_tram_exp</physicalName>
		<type>3</type>
	</field>
	<field id=''7''>
		<physicalName>id_tram_ctl</physicalName>
		<type>3</type>
	</field>
	<field id=''8''>
		<physicalName>num_acto</physicalName>
		<type>0</type>
		<size>16</size>
	</field>
	<field id=''9''>
		<physicalName>cod_acto</physicalName>
		<type>0</type>
		<size>16</size>
	</field>
	<field id=''10''>
		<physicalName>estado_info</physicalName>
		<type>0</type>
		<size>100</size>
	</field>
	<field id=''11''>
		<physicalName>fecha_inicio</physicalName>
		<type>6</type>
	</field>
	<field id=''12''>
		<physicalName>fecha_cierre</physicalName>
		<type>6</type>
	</field>
	<field id=''13''>
		<physicalName>fecha_limite</physicalName>
		<type>6</type>
	</field>
	<field id=''14''>
		<physicalName>fecha_fin</physicalName>
		<type>6</type>
	</field>
	<field id=''15''>
		<physicalName>fecha_inicio_plazo</physicalName>
		<type>6</type>
	</field>
	<field id=''16''>
		<physicalName>plazo</physicalName>
		<type>3</type>
	</field>
	<field id=''17''>
		<physicalName>uplazo</physicalName>
		<type>0</type>
		<size>1</size>
	</field>
	<field id=''18''>
		<physicalName>observaciones</physicalName>
		<type>0</type>
		<size>254</size>
	</field>
	<field id=''19''>
		<physicalName>descripcion</physicalName>
		<type>0</type>
		<size>100</size>
	</field>
	<field id=''20''>
		<physicalName>und_resp</physicalName>
		<type>0</type>
		<size>250</size>
	</field>
	<field id=''21''>
		<physicalName>fase_pcd</physicalName>
		<type>0</type>
		<size>250</size>
	</field>
	<field id=''22''>
		<physicalName>id</physicalName>
		<type>3</type>
	</field>
	<field id=''23''>
		<physicalName>numexp</physicalName>
		<type>0</type>
		<size>30</size>
	</field>
</fields>
<validations>
	<validation id=''1''>
		<fieldId>17</fieldId>
		<table>SPAC_TBL_001</table>
		<tableType>3</tableType>
		<class/>
		<mandatory>N</mandatory>
	</validation>
</validations>
</entity>'
WHERE id = 7;


--
-- ¡¡ ATENCIÓN !!
--
-- En los scripts de instalación de la base de datos nueva, se han
-- actualizado los formularios de las entidades de EXPEDIENTES e
-- INTERVINIENTES en la tabla SPAC_CT_APLICACIONES.
--
-- Como las sentencias de actualización dan error por ser el texto
-- demasiado grande, habrá que hacer una actualización de estos
-- dos formularios a mano: 
--
-- Formulario para expedientes
--
--     Actualizacion_v5.2.6-v5.2.7_Files\expedientForm.jsp 
--         --> SPAC_CT_APLICACIONES.FRM_JSP cuando id=1;
--
-- Formulario para intervinientes
--
--     Actualizacion_v5.2.6-v5.2.7_Files\thirdForm.jsp
--         --> SPAC_CT_APLICACIONES.FRM_JSP cuando id=3;
--


-- Soporte a Contador por procedimiento para numeros de expedientes
ALTER TABLE SPAC_NUMEXP_CONTADOR ADD ID_PCD NUMBER DEFAULT -1 NOT NULL;
ALTER TABLE SPAC_NUMEXP_CONTADOR DROP CONSTRAINT PK_SPAC_NUMEXP_CONTADOR;
ALTER TABLE SPAC_NUMEXP_CONTADOR ADD CONSTRAINT PK_SPAC_NUMEXP_CONTADOR PRIMARY KEY (ID_PCD);
