

-----------------------------------
-- Actualización de v5.2.6 a v5.2.7
-----------------------------------

--
-- Información de versión
--
INSERT INTO spac_infosistema (id, codigo, valor, fecha_actualizacion) VALUES (nextval('spac_sq_id_infosistema'), 'VERSIONBD', '5.2.7', current_timestamp);
INSERT INTO spac_infosistema (id, codigo, valor, fecha_actualizacion) VALUES (nextval('spac_sq_id_infosistema'), 'VERSIONAPP', '5.2.7', current_timestamp);


--
-- Añadir campo a la tabla SPAC_P_TRAMITES
--
ALTER TABLE spac_p_tramites ADD obligatorio smallint;


--
-- Añadir campo de condicion a la tabla SPAC_P_EVENTOS
--
ALTER TABLE spac_p_eventos ADD condicion text;


--
-- Tabulación en el formulario de expediente
-- Tabulación en el formulario de intervinientes
-- Actualización de nombres de tablas en validaciones de entidad, parámetros de formularios y JSPs
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

UPDATE spac_ct_aplicaciones SET frm_jsp = '<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ispac-util.tld" prefix="ispac" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>

<!-- DEFINICION DE LAS ANCLAS A LOS BLOQUES DE CAMPOS -->
<table border="0" cellspacing="0" cellpadding="0">
<tr>
<td class="select" id="tdlink1" align="center" onclick="showTab(1)">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(SPAC_EXPEDIENTES:SPAC_EXPEDIENTES_TAB_DATOS_EXPEDIENTE)" />
</nobr>
</td>
<td width="5px"><img height="1" width="5px" src=''<ispac:rewrite href="img/pixel.gif"/>''/></td>
<td class="unselect" id="tdlink2" align="center" onclick="showTab(2)">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(SPAC_EXPEDIENTES:SPAC_EXPEDIENTES_TAB_INFORMACION_ADICIONAL)" />
</nobr>
</td>
</tr>
</table>

<table width="100%" border="0" class="formtable" cellspacing="0" cellpadding="0">
<tr>
<td><img height="8" src=''<ispac:rewrite href="img/pixel.gif"/>''/></td>
</tr>
<tr>
<td>

<!-- BLOQUE1 DE CAMPOS -->
<div style="display:block" id="block1">
<div id="dataBlock_SPAC_EXPEDIENTES_TAB_DATOS_EXPEDIENTE" style="position: relative; height: 690px; width: 600px;">
<div id="label_SPAC_EXPEDIENTES:NUMEXP" style="position: absolute; top: 23px; left: 10px;" class="formsTitleB">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(SPAC_EXPEDIENTES:NUMEXP)" />:</nobr>
</div>
<div id="data_SPAC_EXPEDIENTES:NUMEXP" style="position: absolute; top: 20px; left: 170px;">
<ispac:htmlText property="property(SPAC_EXPEDIENTES:NUMEXP)" readonly="true" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="30" maxlength="30" tabindex="101">
</ispac:htmlText>
</div>
<div id="label_SPAC_EXPEDIENTES:FINICIOPLAZO" style="position: absolute; top: 54px; left: 391px;" class="formsTitleB">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(SPAC_EXPEDIENTES:FINICIOPLAZO)" />:</nobr>
</div>
<div id="data_SPAC_EXPEDIENTES:FINICIOPLAZO" style="position: absolute; top: 51px; left: 505px;">
<nobr>
<ispac:htmlTextCalendar property="property(SPAC_EXPEDIENTES:FINICIOPLAZO)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="14" maxlength="10" image=''<%= buttoncalendar %>'' format="dd/mm/yyyy" enablePast="true" tabindex="103">
</ispac:htmlTextCalendar>
</nobr>
</div>
<div id="label_SPAC_EXPEDIENTES:NREG" style="position: absolute; top: 149px; left: 10px;" class="formsTitleB">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(SPAC_EXPEDIENTES:NREG)" />:</nobr>
</div>
<div id="data_SPAC_EXPEDIENTES:NREG" style="position: absolute; top: 146px; left: 170px;">
<nobr>
	<ispac:htmlTextImageFrame property="property(SPAC_EXPEDIENTES:NREG)"
				  readonly="false"
				  readonlyTag="false"
				  propertyReadonly="readonly"
				  styleClass="input"
				  styleClassReadonly="inputReadOnly"
				  size="25"
				  maxlength="16"
			  	  id="SEARCH_SPAC_EXPEDIENTES_NREG"
				  target="workframe"
			  	  action="searchInputRegistry.do" 
			  	  image="img/search-mg.gif" 
			  	  titleKeyLink="search.intray" 
			  	  showFrame="true"
			  	  inputField="SPAC_EXPEDIENTES:NREG"
			  	  width="500"
			  	  height="300"
			  	  jsDelete="delete_EXPEDIENT_SEARCH_INPUT_REGISTRY"
			  	  titleKeyImageDelete="forms.exp.title.delete.data.register" tabindex="105">
		
		<ispac:parameter name="SEARCH_SPAC_EXPEDIENTES_NREG" id="property(SPAC_EXPEDIENTES:ASUNTO)" property="ASUNTO"/>
		<ispac:parameter name="SEARCH_SPAC_EXPEDIENTES_NREG" id="property(SPAC_EXPEDIENTES:FREG)" property="FREG"/>
		<ispac:parameter name="SEARCH_SPAC_EXPEDIENTES_NREG" id="property(SPAC_EXPEDIENTES:TIPOPERSONA)" property="TIPOPERSONA"/>
		<ispac:parameter name="SEARCH_SPAC_EXPEDIENTES_NREG" id="property(SPAC_EXPEDIENTES:IDTITULAR)" property="IDTITULAR"/>
		<ispac:parameter name="SEARCH_SPAC_EXPEDIENTES_NREG" id="property(SPAC_EXPEDIENTES:NIFCIFTITULAR)" property="NIFCIFTITULAR"/>
		<ispac:parameter name="SEARCH_SPAC_EXPEDIENTES_NREG" id="property(SPAC_EXPEDIENTES:IDENTIDADTITULAR)" property="IDENTIDADTITULAR"/>
		<ispac:parameter name="SEARCH_SPAC_EXPEDIENTES_NREG" id="property(SPAC_EXPEDIENTES:DOMICILIO)" property="DOMICILIO"/>
		<ispac:parameter name="SEARCH_SPAC_EXPEDIENTES_NREG" id="property(SPAC_EXPEDIENTES:CPOSTAL)" property="CPOSTAL"/>
		<ispac:parameter name="SEARCH_SPAC_EXPEDIENTES_NREG" id="property(SPAC_EXPEDIENTES:CIUDAD)" property="CIUDAD"/>
		<ispac:parameter name="SEARCH_SPAC_EXPEDIENTES_NREG" id="property(SPAC_EXPEDIENTES:REGIONPAIS)" property="REGIONPAIS"/>
		<ispac:parameter name="SEARCH_SPAC_EXPEDIENTES_NREG" id="property(SPAC_EXPEDIENTES:TFNOFIJO)" property="TFNOFIJO"/>
		<ispac:parameter name="SEARCH_SPAC_EXPEDIENTES_NREG" id="property(SPAC_EXPEDIENTES:TFNOMOVIL)" property="TFNOMOVIL"/>
		<ispac:parameter name="SEARCH_SPAC_EXPEDIENTES_NREG" id="property(SPAC_EXPEDIENTES:DIRECCIONTELEMATICA)" property="DIRECCIONTELEMATICA"/>
		<ispac:parameter name="SEARCH_SPAC_EXPEDIENTES_NREG" id="property(SPAC_EXPEDIENTES:TIPODIRECCIONINTERESADO)" property="TIPODIRECCIONINTERESADO"/>
		<ispac:parameter name="SEARCH_SPAC_EXPEDIENTES_NREG" id="property(TIPODIRECCIONINTERESADO_SPAC_TBL_005:SUSTITUTO)" property="SUSTITUTOTIPODIRECCIONINTERESADO"/>
		<ispac:parameter name="SEARCH_SPAC_EXPEDIENTES_NREG" id="JAVASCRIPT" property="accept_EXPEDIENT_SEARCH_INPUT_REGISTRY"/>
		
	</ispac:htmlTextImageFrame>
</nobr>
</div>
<div id="label_SPAC_EXPEDIENTES:FREG" style="position: absolute; top: 151px; left: 392px;" class="formsTitleB">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(SPAC_EXPEDIENTES:FREG)" />:</nobr>
</div>
<div id="data_SPAC_EXPEDIENTES:FREG" style="position: absolute; top: 148px; left: 505px;">
<nobr>
<ispac:htmlTextCalendar property="property(SPAC_EXPEDIENTES:FREG)" readonly="true" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="14" maxlength="10" image=''<%= buttoncalendar %>'' format="dd/mm/yyyy" enablePast="true" tabindex="106">
</ispac:htmlTextCalendar>
</nobr>
</div>
<div id="label_SPAC_EXPEDIENTES:SEP_INTERESADO_PRINCIPAL" style="position: absolute; top: 285px; left: 10px; width: 620px" class="textbar">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(SPAC_EXPEDIENTES:SEP_INTERESADO_PRINCIPAL)" /></nobr>
<hr class="formbar"/>
</div>
<div id="label_SPAC_EXPEDIENTES:LBL_VALIDADO" style="position: absolute; top: 324px; left: 10px;"  class="formsTitleB">
<nobr>
<input type="radio" name="validationThirdParty" id="validatedThirdParty" onclick="javascript: clickValidatedThirdParty();" checked="checked" tabindex="110"/>
&nbsp;<bean:write name="defaultForm" property="entityApp.label(SPAC_EXPEDIENTES:LBL_VALIDADO)" /></nobr>
</div>
<div id="label_SPAC_EXPEDIENTES:LBL_NO_VALIDADO" style="position: absolute; top: 324px; left: 100px;"  class="formsTitleB">
<nobr>
<input type="radio" name="validationThirdParty" id="notValidatedThirdParty" onclick="javascript: clickNotValidatedThirdParty();" tabindex="111"/>
&nbsp;<bean:write name="defaultForm" property="entityApp.label(SPAC_EXPEDIENTES:LBL_NO_VALIDADO)" /></nobr>
</div>
<div id="label_SPAC_EXPEDIENTES:NIFCIFTITULAR" style="position: absolute; top: 357px; left: 10px;" class="formsTitleB">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(SPAC_EXPEDIENTES:NIFCIFTITULAR)" />:</nobr>
</div>
<div id="data_SPAC_EXPEDIENTES:NIFCIFTITULAR" style="position: absolute; top: 354px; left: 170px;">
<nobr>
	<ispac:htmlTextImageFrame property="property(SPAC_EXPEDIENTES:NIFCIFTITULAR)"
				  readonly="false"
				  readonlyTag="false"
				  propertyReadonly="readonly"
				  styleClass="input"
				  styleClassReadonly="inputReadOnly"
				  size="34"
				  maxlength="16"
			  	  id="SEARCH_SPAC_EXPEDIENTES_NIFCIFTITULAR"
				  target="workframe"
			  	  action="searchThirdParty.do" 
			  	  image="img/search-mg.gif" 
			  	  titleKeyLink="search.thirdparty" 
			  	  showFrame="false"
			  	  inputField="SPAC_EXPEDIENTES:NIFCIFTITULAR"
			  	  jsDelete="delete_EXPEDIENT_SEARCH_THIRD_PARTY"
			  	  titleKeyImageDelete="forms.exp.title.delete.data.thirdParty"
			  	  jsExecuteFrame="selectThirdParty" tabindex="112">

		<ispac:parameter name="SEARCH_SPAC_EXPEDIENTES_NIFCIFTITULAR" id="property(SPAC_EXPEDIENTES:TIPOPERSONA)" property="TIPOPERSONA"/>
		<ispac:parameter name="SEARCH_SPAC_EXPEDIENTES_NIFCIFTITULAR" id="property(SPAC_EXPEDIENTES:IDTITULAR)" property="IDTITULAR"/>
		<ispac:parameter name="SEARCH_SPAC_EXPEDIENTES_NIFCIFTITULAR" id="property(SPAC_EXPEDIENTES:IDENTIDADTITULAR)" property="IDENTIDADTITULAR"/>
		<ispac:parameter name="SEARCH_SPAC_EXPEDIENTES_NIFCIFTITULAR" id="property(SPAC_EXPEDIENTES:DOMICILIO)" property="DOMICILIO"/>
		<ispac:parameter name="SEARCH_SPAC_EXPEDIENTES_NIFCIFTITULAR" id="property(SPAC_EXPEDIENTES:CPOSTAL)" property="CPOSTAL"/>
		<ispac:parameter name="SEARCH_SPAC_EXPEDIENTES_NIFCIFTITULAR" id="property(SPAC_EXPEDIENTES:CIUDAD)" property="CIUDAD"/>
		<ispac:parameter name="SEARCH_SPAC_EXPEDIENTES_NIFCIFTITULAR" id="property(SPAC_EXPEDIENTES:REGIONPAIS)" property="REGIONPAIS"/>
		<ispac:parameter name="SEARCH_SPAC_EXPEDIENTES_NIFCIFTITULAR" id="property(SPAC_EXPEDIENTES:TFNOFIJO)" property="TFNOFIJO"/>
		<ispac:parameter name="SEARCH_SPAC_EXPEDIENTES_NIFCIFTITULAR" id="property(SPAC_EXPEDIENTES:TFNOMOVIL)" property="TFNOMOVIL"/>
		<ispac:parameter name="SEARCH_SPAC_EXPEDIENTES_NIFCIFTITULAR" id="property(SPAC_EXPEDIENTES:DIRECCIONTELEMATICA)" property="DIRECCIONTELEMATICA"/>
		<ispac:parameter name="SEARCH_SPAC_EXPEDIENTES_NIFCIFTITULAR" id="property(SPAC_EXPEDIENTES:TIPODIRECCIONINTERESADO)" property="TIPODIRECCIONINTERESADO"/>
		<ispac:parameter name="SEARCH_SPAC_EXPEDIENTES_NIFCIFTITULAR" id="property(TIPODIRECCIONINTERESADO_SPAC_TBL_005:SUSTITUTO)" property="SUSTITUTOTIPODIRECCIONINTERESADO"/>
		<ispac:parameter name="SEARCH_SPAC_EXPEDIENTES_NIFCIFTITULAR" id="JAVASCRIPT" property="return_EXPEDIENT_SEARCH_THIRD_PARTY"/>
		
	</ispac:htmlTextImageFrame>
</nobr>
</div>
<div id="label_SPAC_EXPEDIENTES:DOMICILIO" style="position: absolute; top: 439px; left: 10px;" class="formsTitleB">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(SPAC_EXPEDIENTES:DOMICILIO)" />:</nobr>
</div>
<div id="data_SPAC_EXPEDIENTES:DOMICILIO" style="position: absolute; top: 436px; left: 170px;">
<ispac:htmlTextarea property="property(SPAC_EXPEDIENTES:DOMICILIO)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" rows="2" cols="85" tabindex="114">
</ispac:htmlTextarea>
</div>
<div id="label_SPAC_EXPEDIENTES:CIUDAD" style="position: absolute; top: 480px; left: 10px;" class="formsTitleB">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(SPAC_EXPEDIENTES:CIUDAD)" />:</nobr>
</div>
<div id="data_SPAC_EXPEDIENTES:CIUDAD" style="position: absolute; top: 477px; left: 170px;">
<ispac:htmlText property="property(SPAC_EXPEDIENTES:CIUDAD)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="30" maxlength="64" tabindex="115">
</ispac:htmlText>
</div>
<div id="label_SPAC_EXPEDIENTES:REGIONPAIS" style="position: absolute; top: 514px; left: 10px;" class="formsTitleB">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(SPAC_EXPEDIENTES:REGIONPAIS)" />:</nobr>
</div>
<div id="data_SPAC_EXPEDIENTES:REGIONPAIS" style="position: absolute; top: 511px; left: 170px;">
<ispac:htmlText property="property(SPAC_EXPEDIENTES:REGIONPAIS)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="80" maxlength="64" tabindex="117">
</ispac:htmlText>
</div>
<div id="label_SPAC_EXPEDIENTES:CPOSTAL" style="position: absolute; top: 481px; left: 370px;" class="formsTitleB">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(SPAC_EXPEDIENTES:CPOSTAL)" />:</nobr>
</div>
<div id="data_SPAC_EXPEDIENTES:CPOSTAL" style="position: absolute; top: 478px; left: 470px;">
<ispac:htmlText property="property(SPAC_EXPEDIENTES:CPOSTAL)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="10" maxlength="5" tabindex="116">
</ispac:htmlText>
</div>
<div id="label_SPAC_EXPEDIENTES:IDENTIDADTITULAR" style="position: absolute; top: 390px; left: 10px;" class="formsTitleB">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(SPAC_EXPEDIENTES:IDENTIDADTITULAR)" />:</nobr>
</div>
<div id="data_SPAC_EXPEDIENTES:IDENTIDADTITULAR" style="position: absolute; top: 387px; left: 170px;">
<ispac:htmlTextarea property="property(SPAC_EXPEDIENTES:IDENTIDADTITULAR)" readonly="true" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" rows="2" cols="85" tabindex="113">
</ispac:htmlTextarea>
</div>
<div id="label_SPAC_EXPEDIENTES:ROLTITULAR" style="position: absolute; top: 655px; left: 10px;" class="formsTitleB">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(SPAC_EXPEDIENTES:ROLTITULAR)" />:</nobr>
</div>
<div id="data_SPAC_EXPEDIENTES:ROLTITULAR" style="position: absolute; top: 652px; left: 170px;">
<html:hidden property="property(SPAC_EXPEDIENTES:ROLTITULAR)" />
<nobr>
<ispac:htmlTextImageFrame property="property(ROLTITULAR_SPAC_TBL_002:SUSTITUTO)" readonly="true" readonlyTag="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="34" id="SEARCH_SPAC_EXPEDIENTES_ROLTITULAR" target="workframe" action="selectSubstitute.do?entity=SPAC_TBL_002" image="img/search-mg.gif" titleKeyLink="select.rol" imageDelete="img/borrar.gif" titleKeyImageDelete="title.delete.data.selection" styleClassDeleteLink="tdlink" confirmDeleteKey="msg.delete.data.selection" showDelete="true" showFrame="true" width="640" height="480" tabindex="122">
<ispac:parameter name="SEARCH_SPAC_EXPEDIENTES_ROLTITULAR" id="property(SPAC_EXPEDIENTES:ROLTITULAR)" property="VALOR" />
<ispac:parameter name="SEARCH_SPAC_EXPEDIENTES_ROLTITULAR" id="property(ROLTITULAR_SPAC_TBL_002:SUSTITUTO)" property="SUSTITUTO" />
</ispac:htmlTextImageFrame>
</nobr>
</div>
<div id="label_SPAC_EXPEDIENTES:ASUNTO" style="position: absolute; top: 83px; left: 10px;" class="formsTitleB">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(SPAC_EXPEDIENTES:ASUNTO)" />:</nobr>
</div>
<div id="data_SPAC_EXPEDIENTES:ASUNTO" style="position: absolute; top: 80px; left: 170px;">
<ispac:htmlTextarea property="property(SPAC_EXPEDIENTES:ASUNTO)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" rows="4" cols="85" tabindex="104">
</ispac:htmlTextarea>
</div>
<div id="label_SPAC_EXPEDIENTES:FORMATERMINACION" style="position: absolute; top: 181px; left: 10px;" class="formsTitleB">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(SPAC_EXPEDIENTES:FORMATERMINACION)" />:</nobr>
</div>
<div id="data_SPAC_EXPEDIENTES:FORMATERMINACION" style="position: absolute; top: 178px; left: 170px;">
<html:hidden property="property(SPAC_EXPEDIENTES:FORMATERMINACION)" />
<nobr>
<ispac:htmlTextImageFrame property="property(FORMATERMINACION_SPAC_TBL_003:SUSTITUTO)" readonly="true" readonlyTag="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="50" id="SEARCH_SPAC_EXPEDIENTES_FORMATERMINACION" target="workframe" action="selectSubstitute.do?entity=SPAC_TBL_003" image="img/search-mg.gif" titleKeyLink="select.termination" imageDelete="img/borrar.gif" titleKeyImageDelete="title.delete.data.selection" styleClassDeleteLink="tdlink" confirmDeleteKey="msg.delete.data.selection" showDelete="true" showFrame="true" width="640" height="480" tabindex="107">
<ispac:parameter name="SEARCH_SPAC_EXPEDIENTES_FORMATERMINACION" id="property(SPAC_EXPEDIENTES:FORMATERMINACION)" property="VALOR" />
<ispac:parameter name="SEARCH_SPAC_EXPEDIENTES_FORMATERMINACION" id="property(FORMATERMINACION_SPAC_TBL_003:SUSTITUTO)" property="SUSTITUTO" />
</ispac:htmlTextImageFrame>
</nobr>
</div>
<div id="label_SPAC_EXPEDIENTES:ESTADOADM" style="position: absolute; top: 214px; left: 10px;" class="formsTitleB">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(SPAC_EXPEDIENTES:ESTADOADM)" />:</nobr>
</div>
<div id="data_SPAC_EXPEDIENTES:ESTADOADM" style="position: absolute; top: 211px; left: 170px;">
<html:hidden property="property(SPAC_EXPEDIENTES:ESTADOADM)" />
<nobr>
<ispac:htmlTextImageFrame property="property(ESTADOADM_SPAC_TBL_004:SUSTITUTO)" readonly="true" readonlyTag="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="50" id="SEARCH_SPAC_EXPEDIENTES_ESTADOADM" target="workframe" action="selectSubstitute.do?entity=SPAC_TBL_004" image="img/search-mg.gif" titleKeyLink="select.estadoadm" imageDelete="img/borrar.gif" titleKeyImageDelete="title.delete.data.selection" styleClassDeleteLink="tdlink" confirmDeleteKey="msg.delete.data.selection" showDelete="true" showFrame="true" width="640" height="480" tabindex="108">
<ispac:parameter name="SEARCH_SPAC_EXPEDIENTES_ESTADOADM" id="property(SPAC_EXPEDIENTES:ESTADOADM)" property="VALOR" />
<ispac:parameter name="SEARCH_SPAC_EXPEDIENTES_ESTADOADM" id="property(ESTADOADM_SPAC_TBL_004:SUSTITUTO)" property="SUSTITUTO" />
</ispac:htmlTextImageFrame>
</nobr>
</div>
<div id="label_SPAC_EXPEDIENTES:FAPERTURA" style="position: absolute; top: 52px; left: 10px;" class="formsTitleB">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(SPAC_EXPEDIENTES:FAPERTURA)" />:</nobr>
</div>
<div id="data_SPAC_EXPEDIENTES:FAPERTURA" style="position: absolute; top: 49px; left: 170px;">
<nobr>
<ispac:htmlTextCalendar property="property(SPAC_EXPEDIENTES:FAPERTURA)" readonly="true" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="14" maxlength="10" image=''<%= buttoncalendar %>'' format="dd/mm/yyyy" enablePast="true" tabindex="102">
</ispac:htmlTextCalendar>
</nobr>
</div>
<div id="label_SPAC_EXPEDIENTES:TIPODIRECCIONINTERESADO" style="position: absolute; top: 620px; left: 10px;" class="formsTitleB">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(SPAC_EXPEDIENTES:TIPODIRECCIONINTERESADO)" />:</nobr>
</div>
<div id="data_SPAC_EXPEDIENTES:TIPODIRECCIONINTERESADO" style="position: absolute; top: 617px; left: 170px;">
<html:hidden property="property(SPAC_EXPEDIENTES:TIPODIRECCIONINTERESADO)" />
<nobr>
<ispac:htmlTextImageFrame property="property(TIPODIRECCIONINTERESADO_SPAC_TBL_005:SUSTITUTO)" readonly="true" readonlyTag="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="34" id="SEARCH_SPAC_EXPEDIENTES_TIPODIRECCIONINTERESADO" target="workframe" action="selectSubstitute.do?entity=SPAC_TBL_005" image="img/search-mg.gif" titleKeyLink="select.tipoDireccionNotificacion" imageDelete="img/borrar.gif" titleKeyImageDelete="title.delete.data.selection" styleClassDeleteLink="tdlink" confirmDeleteKey="msg.delete.data.selection" showDelete="true" showFrame="true" width="640" height="480" tabindex="121">
<ispac:parameter name="SEARCH_SPAC_EXPEDIENTES_TIPODIRECCIONINTERESADO" id="property(SPAC_EXPEDIENTES:TIPODIRECCIONINTERESADO)" property="VALOR" />
<ispac:parameter name="SEARCH_SPAC_EXPEDIENTES_TIPODIRECCIONINTERESADO" id="property(TIPODIRECCIONINTERESADO_SPAC_TBL_005:SUSTITUTO)" property="SUSTITUTO" />
</ispac:htmlTextImageFrame>
</nobr>
</div>
<div id="label_SPAC_EXPEDIENTES:SECCIONINICIADORA" style="position: absolute; top: 247px; left: 10px;" class="formsTitleB">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(SPAC_EXPEDIENTES:SECCIONINICIADORA)" />:</nobr>
</div>
<div id="data_SPAC_EXPEDIENTES:SECCIONINICIADORA" style="position: absolute; top: 244px; left: 170px;">
<ispac:htmlText property="property(SPAC_EXPEDIENTES:SECCIONINICIADORA)" readonly="true" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="50" maxlength="250" tabindex="109">
</ispac:htmlText>
</div>
<div id="label_SPAC_EXPEDIENTES:TFNOFIJO" style="position: absolute; top: 547px; left: 10px;" class="formsTitleB">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(SPAC_EXPEDIENTES:TFNOFIJO)" />:</nobr>
</div>
<div id="data_SPAC_EXPEDIENTES:TFNOFIJO" style="position: absolute; top: 544px; left: 170px;">
<ispac:htmlText property="property(SPAC_EXPEDIENTES:TFNOFIJO)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="20" maxlength="32" tabindex="118">
</ispac:htmlText>
</div>
<div id="label_SPAC_EXPEDIENTES:TFNOMOVIL" style="position: absolute; top: 546px; left: 370px;" class="formsTitleB">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(SPAC_EXPEDIENTES:TFNOMOVIL)" />:</nobr>
</div>
<div id="data_SPAC_EXPEDIENTES:TFNOMOVIL" style="position: absolute; top: 543px; left: 470px;">
<ispac:htmlText property="property(SPAC_EXPEDIENTES:TFNOMOVIL)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="20" maxlength="32" tabindex="119">
</ispac:htmlText>
</div>
<div id="label_SPAC_EXPEDIENTES:DIRECCIONTELEMATICA" style="position: absolute; top: 583px; left: 10px;" class="formsTitleB">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(SPAC_EXPEDIENTES:DIRECCIONTELEMATICA)" />:</nobr>
</div>
<div id="data_SPAC_EXPEDIENTES:DIRECCIONTELEMATICA" style="position: absolute; top: 580px; left: 170px;">
<ispac:htmlText property="property(SPAC_EXPEDIENTES:DIRECCIONTELEMATICA)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="80" maxlength="60" tabindex="120">
</ispac:htmlText>
</div>
</div>
</div>

<!-- BLOQUE2 DE CAMPOS -->
<div style="display:none" id="block2">
<div id="dataBlock_SPAC_EXPEDIENTES_TAB_INFORMACION_ADICIONAL" style="position: relative; height: 280px; width: 600px;">
<div id="label_SPAC_EXPEDIENTES:UTRAMITADORA" style="position: absolute; top: 23px; left: 10px;" class="formsTitleB">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(SPAC_EXPEDIENTES:UTRAMITADORA)" />:</nobr>
</div>
<div id="data_SPAC_EXPEDIENTES:UTRAMITADORA" style="position: absolute; top: 20px; left: 170px;">
<nobr>
	<ispac:htmlTextImageFrame property="property(SPAC_EXPEDIENTES:UTRAMITADORA)"
				  readonly="true"
				  readonlyTag="false"
				  propertyReadonly="readonly"
				  styleClass="input"
				  styleClassReadonly="inputReadOnly"
				  size="50"
			  	  id="SEARCH_SPAC_EXPEDIENTES_UTRAMITADORA"
				  target="workframe"
			  	  action="viewUsersManager.do?captionkey=select.unidadTramitadora&noviewusers=true&noviewgroups=true"
			  	  image="img/search-mg.gif" 
			  	  titleKeyLink="select.unidadTramitadora" 
			  	  showFrame="true" tabindex="201">
						  	  
		<ispac:parameter name="SEARCH_SPAC_EXPEDIENTES_UTRAMITADORA" id="property(SPAC_EXPEDIENTES:UTRAMITADORA)" property="NAME"/>
		
	</ispac:htmlTextImageFrame>
</nobr>
</div>
<div id="label_SPAC_EXPEDIENTES:FCIERRE" style="position: absolute; top: 58px; left: 10px;" class="formsTitleB">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(SPAC_EXPEDIENTES:FCIERRE)" />:</nobr>
</div>
<div id="data_SPAC_EXPEDIENTES:FCIERRE" style="position: absolute; top: 55px; left: 170px;">
<nobr>
<ispac:htmlTextCalendar property="property(SPAC_EXPEDIENTES:FCIERRE)" readonly="true" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="14" maxlength="10" image=''<%= buttoncalendar %>'' format="dd/mm/yyyy" enablePast="true" tabindex="202">
</ispac:htmlTextCalendar>
</nobr>
</div>
<div id="label_SPAC_EXPEDIENTES:HAYRECURSO" style="position: absolute; top: 58px; left: 306px;" class="formsTitleB">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(SPAC_EXPEDIENTES:HAYRECURSO)" />:</nobr>
</div>
<div id="data_SPAC_EXPEDIENTES:HAYRECURSO" style="position: absolute; top: 55px; left: 364px;">
<ispac:htmlCheckbox property="property(SPAC_EXPEDIENTES:HAYRECURSO)" readonly="false" propertyReadonly="readonly" valueChecked="SI" styleClassCheckbox="inputSelectP" tabindex="203"></ispac:htmlCheckbox>
</div>
<div id="label_SPAC_EXPEDIENTES:OBSERVACIONES" style="position: absolute; top: 93px; left: 10px;" class="formsTitleB">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(SPAC_EXPEDIENTES:OBSERVACIONES)" />:</nobr>
</div>
<div id="data_SPAC_EXPEDIENTES:OBSERVACIONES" style="position: absolute; top: 90px; left: 170px;">
<ispac:htmlTextarea property="property(SPAC_EXPEDIENTES:OBSERVACIONES)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" rows="2" cols="85" tabindex="204">
</ispac:htmlTextarea>
</div>
<div id="label_SPAC_EXPEDIENTES:SEP_TERRITORIO" style="position: absolute; top: 140px; left: 10px; width: 620px" class="textbar">
<nobr><bean:write name="defaultForm" property="entityApp.label(SPAC_EXPEDIENTES:SEP_TERRITORIO)" />
<hr class="formbar"/>
</div>
<div id="label_SPAC_EXPEDIENTES:POBLACION" style="position: absolute; top: 183px; left: 10px;" class="formsTitleB">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(SPAC_EXPEDIENTES:POBLACION)" />:</nobr>
</div>
<div id="data_SPAC_EXPEDIENTES:POBLACION" style="position: absolute; top: 180px; left: 170px;">
<ispac:htmlText property="property(SPAC_EXPEDIENTES:POBLACION)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="50" maxlength="64" tabindex="205">
</ispac:htmlText>
</div>
<div id="label_SPAC_EXPEDIENTES:MUNICIPIO" style="position: absolute; top: 213px; left: 10px;" class="formsTitleB">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(SPAC_EXPEDIENTES:MUNICIPIO)" />:</nobr>
</div>
<div id="data_SPAC_EXPEDIENTES:MUNICIPIO" style="position: absolute; top: 210px; left: 170px;">
<ispac:htmlText property="property(SPAC_EXPEDIENTES:MUNICIPIO)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="50" maxlength="64" tabindex="206">
</ispac:htmlText>
</div>
<div id="label_SPAC_EXPEDIENTES:LOCALIZACION" style="position: absolute; top: 243px; left: 10px;" class="formsTitleB">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(SPAC_EXPEDIENTES:LOCALIZACION)" />:</nobr>
</div>
<div id="data_SPAC_EXPEDIENTES:LOCALIZACION" style="position: absolute; top: 240px; left: 170px;">
<ispac:htmlText property="property(SPAC_EXPEDIENTES:LOCALIZACION)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="50" maxlength="256" tabindex="207">
</ispac:htmlText>
</div>
</div>
</div>
</td>
</tr>
<tr>
<td height="15"><img src=''<ispac:rewrite href="img/pixel.gif"/>''/></td>
</tr>
</table>'
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

UPDATE spac_ct_aplicaciones SET frm_jsp = '<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ispac-util.tld" prefix="ispac" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>

<!-- DEFINICION DE LAS ANCLAS A LOS BLOQUES DE CAMPOS -->
<table border="0" cellspacing="0" cellpadding="0">
<tr>
<td class="select" id="tdlink1" align="center">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(SPAC_DT_INTERVINIENTES:SPAC_DT_INTERVINIENTES_TAB_DATOS_PARTICIPANTE)" />
</nobr>
</td>
</tr>
</table>

<table width="100%" border="0" class="formtable" cellspacing="0" cellpadding="0">
<tr>
<td><img height="8" src=''<ispac:rewrite href="img/pixel.gif"/>''/></td>
</tr>
<tr>
<td>

<!-- BLOQUE1 DE CAMPOS -->
<div style="display:block" id="block1">
<div id="dataBlock_SPAC_DT_INTERVINIENTES_TAB_DATOS_PARTICIPANTE" style="position: relative; height: 340px; width: 600px;">
<div id="label_SPAC_DT_INTERVINIENTES:LBL_VALIDADO" style="position: absolute; top: 5px; left: 10px;"  class="formsTitleB">
<nobr>
<input type="radio" name="validationThirdParty" id="validatedThirdParty" onclick="javascript: clickValidatedThirdParty();" checked="checked" tabindex="101"/>
&nbsp;<bean:write name="defaultForm" property="entityApp.label(SPAC_DT_INTERVINIENTES:LBL_VALIDADO)" /></nobr>
</div>
<div id="label_SPAC_DT_INTERVINIENTES:LBL_NO_VALIDADO" style="position: absolute; top: 5px; left: 100px;"  class="formsTitleB">
<nobr>
<input type="radio" name="validationThirdParty" id="notValidatedThirdParty" onclick="javascript: clickNotValidatedThirdParty();" tabindex="102"/>
&nbsp;<bean:write name="defaultForm" property="entityApp.label(SPAC_DT_INTERVINIENTES:LBL_NO_VALIDADO)" /></nobr>
</div>
<div id="label_SPAC_DT_INTERVINIENTES:ROL" style="position: absolute; top: 321px; left: 10px;" class="formsTitleB">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(SPAC_DT_INTERVINIENTES:ROL)" />:</nobr>
</div>
<div id="data_SPAC_DT_INTERVINIENTES:ROL" style="position: absolute; top: 318px; left: 175px;">
<html:hidden property="property(SPAC_DT_INTERVINIENTES:ROL)" />
<nobr>
<ispac:htmlTextImageFrame property="property(ROL_SPAC_TBL_002:SUSTITUTO)" readonly="true" readonlyTag="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="50" id="SEARCH_SPAC_DT_INTERVINIENTES_ROL" target="workframe" action="selectSubstitute.do?entity=SPAC_TBL_002" image="img/search-mg.gif" titleKeyLink="select.rol" imageDelete="img/borrar.gif" titleKeyImageDelete="title.delete.data.selection" styleClassDeleteLink="tdlink" confirmDeleteKey="msg.delete.data.selection" showDelete="true" showFrame="true" width="640" height="480" tabindex="113">
<ispac:parameter name="SEARCH_SPAC_DT_INTERVINIENTES_ROL" id="property(SPAC_DT_INTERVINIENTES:ROL)" property="VALOR" />
<ispac:parameter name="SEARCH_SPAC_DT_INTERVINIENTES_ROL" id="property(ROL_SPAC_TBL_002:SUSTITUTO)" property="SUSTITUTO" />
</ispac:htmlTextImageFrame>
</nobr>
</div>
<div id="label_SPAC_DT_INTERVINIENTES:NDOC" style="position: absolute; top: 37px; left: 10px;" class="formsTitleB">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(SPAC_DT_INTERVINIENTES:NDOC)" />:</nobr>
</div>
<div id="data_SPAC_DT_INTERVINIENTES:NDOC" style="position: absolute; top: 34px; left: 175px;">
<nobr>
	<ispac:htmlTextImageFrame property="property(SPAC_DT_INTERVINIENTES:NDOC)"
				  readonly="false"
				  readonlyTag="false"
				  propertyReadonly="readonly"
				  styleClass="input"
				  styleClassReadonly="inputReadOnly"
				  size="30"
				  maxlength="12"
			  	  id="THIRD_SEARCH_THIRD_PARTY"
				  target="workframe"
			  	  action="searchThirdParty.do" 
			  	  image="img/search-mg.gif" 
			  	  titleKeyLink="search.thirdparty" 
			  	  showFrame="false"
			  	  inputField="SPAC_DT_INTERVINIENTES:NDOC"
			  	  showDelete="true"
			  	  jsDelete="delete_THIRD_SEARCH_THIRD_PARTY"
			  	  titleKeyImageDelete="forms.participantes.title.delete.data.thirdParty"
			  	  jsExecuteFrame="selectThirdParty" tabindex="103">
		<ispac:parameter name="THIRD_SEARCH_THIRD_PARTY" id="property(SPAC_DT_INTERVINIENTES:TIPO_PERSONA)" property="TIPOPERSONA"/>
		<ispac:parameter name="THIRD_SEARCH_THIRD_PARTY" id="property(SPAC_DT_INTERVINIENTES:ID_EXT)" property="IDTITULAR"/>
		<ispac:parameter name="THIRD_SEARCH_THIRD_PARTY" id="property(SPAC_DT_INTERVINIENTES:NOMBRE)" property="IDENTIDADTITULAR"/>
		<ispac:parameter name="THIRD_SEARCH_THIRD_PARTY" id="property(SPAC_DT_INTERVINIENTES:DIRNOT)" property="DOMICILIO"/>
		<ispac:parameter name="THIRD_SEARCH_THIRD_PARTY" id="property(SPAC_DT_INTERVINIENTES:C_POSTAL)" property="CPOSTAL"/>
		<ispac:parameter name="THIRD_SEARCH_THIRD_PARTY" id="property(SPAC_DT_INTERVINIENTES:LOCALIDAD)" property="CIUDAD"/>
		<ispac:parameter name="THIRD_SEARCH_THIRD_PARTY" id="property(SPAC_DT_INTERVINIENTES:CAUT)" property="REGIONPAIS"/>
		<ispac:parameter name="THIRD_SEARCH_THIRD_PARTY" id="property(SPAC_DT_INTERVINIENTES:TFNO_FIJO)" property="TFNOFIJO"/>
		<ispac:parameter name="THIRD_SEARCH_THIRD_PARTY" id="property(SPAC_DT_INTERVINIENTES:TFNO_MOVIL)" property="TFNOMOVIL"/>
		<ispac:parameter name="THIRD_SEARCH_THIRD_PARTY" id="property(SPAC_DT_INTERVINIENTES:DIRECCIONTELEMATICA)" property="DIRECCIONTELEMATICA"/>
		<ispac:parameter name="THIRD_SEARCH_THIRD_PARTY" id="property(SPAC_DT_INTERVINIENTES:TIPO_DIRECCION)" property="TIPODIRECCIONINTERESADO"/>
		<ispac:parameter name="THIRD_SEARCH_THIRD_PARTY" id="property(TIPO_DIRECCION_SPAC_TBL_005:SUSTITUTO)" property="SUSTITUTOTIPODIRECCIONINTERESADO"/>
		<ispac:parameter name="THIRD_SEARCH_THIRD_PARTY" id="JAVASCRIPT" property="return_THIRD_SEARCH_THIRD_PARTY"/>
	</ispac:htmlTextImageFrame>
</nobr>
</div>
<div id="label_SPAC_DT_INTERVINIENTES:NOMBRE" style="position: absolute; top: 67px; left: 10px;" class="formsTitleB">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(SPAC_DT_INTERVINIENTES:NOMBRE)" />:</nobr>
</div>
<div id="data_SPAC_DT_INTERVINIENTES:NOMBRE" style="position: absolute; top: 64px; left: 175px;">
<ispac:htmlTextarea property="property(SPAC_DT_INTERVINIENTES:NOMBRE)" readonly="true" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" rows="2" cols="80" tabindex="104">
</ispac:htmlTextarea>
</div>
<div id="label_SPAC_DT_INTERVINIENTES:DIRNOT" style="position: absolute; top: 103px; left: 10px;" class="formsTitleB">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(SPAC_DT_INTERVINIENTES:DIRNOT)" />:</nobr>
</div>
<div id="data_SPAC_DT_INTERVINIENTES:DIRNOT" style="position: absolute; top: 100px; left: 175px;">
<ispac:htmlTextarea property="property(SPAC_DT_INTERVINIENTES:DIRNOT)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" rows="2" cols="80" tabindex="105">
</ispac:htmlTextarea>
</div>
<div id="label_SPAC_DT_INTERVINIENTES:C_POSTAL" style="position: absolute; top: 165px; left: 10px;" class="formsTitleB">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(SPAC_DT_INTERVINIENTES:C_POSTAL)" />:</nobr>
</div>
<div id="data_SPAC_DT_INTERVINIENTES:C_POSTAL" style="position: absolute; top: 162px; left: 175px;">
<ispac:htmlText property="property(SPAC_DT_INTERVINIENTES:C_POSTAL)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="10" maxlength="10" tabindex="107">
</ispac:htmlText>
</div>
<div id="label_SPAC_DT_INTERVINIENTES:LOCALIDAD" style="position: absolute; top: 139px; left: 10px;" class="formsTitleB">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(SPAC_DT_INTERVINIENTES:LOCALIDAD)" />:</nobr>
</div>
<div id="data_SPAC_DT_INTERVINIENTES:LOCALIDAD" style="position: absolute; top: 136px; left: 175px;">
<ispac:htmlText property="property(SPAC_DT_INTERVINIENTES:LOCALIDAD)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="80" maxlength="150" tabindex="106">
</ispac:htmlText>
</div>
<div id="label_SPAC_DT_INTERVINIENTES:CAUT" style="position: absolute; top: 191px; left: 10px;" class="formsTitleB">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(SPAC_DT_INTERVINIENTES:CAUT)" />:</nobr>
</div>
<div id="data_SPAC_DT_INTERVINIENTES:CAUT" style="position: absolute; top: 188px; left: 175px;">
<ispac:htmlText property="property(SPAC_DT_INTERVINIENTES:CAUT)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="80" maxlength="150" tabindex="108">
</ispac:htmlText>
</div>
<div id="label_SPAC_DT_INTERVINIENTES:TFNO_FIJO" style="position: absolute; top: 217px; left: 10px;" class="formsTitleB">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(SPAC_DT_INTERVINIENTES:TFNO_FIJO)" />:</nobr>
</div>
<div id="data_SPAC_DT_INTERVINIENTES:TFNO_FIJO" style="position: absolute; top: 214px; left: 175px;">
<ispac:htmlText property="property(SPAC_DT_INTERVINIENTES:TFNO_FIJO)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="20" maxlength="32" tabindex="109">
</ispac:htmlText>
</div>
<div id="label_SPAC_DT_INTERVINIENTES:TFNO_MOVIL" style="position: absolute; top: 243px; left: 10px;" class="formsTitleB">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(SPAC_DT_INTERVINIENTES:TFNO_MOVIL)" />:</nobr>
</div>
<div id="data_SPAC_DT_INTERVINIENTES:TFNO_MOVIL" style="position: absolute; top: 240px; left: 175px;">
<ispac:htmlText property="property(SPAC_DT_INTERVINIENTES:TFNO_MOVIL)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="20" maxlength="32" tabindex="110">
</ispac:htmlText>
</div>
<div id="label_SPAC_DT_INTERVINIENTES:TIPO_DIRECCION" style="position: absolute; top: 295px; left: 10px;" class="formsTitleB">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(SPAC_DT_INTERVINIENTES:TIPO_DIRECCION)" />:</nobr>
</div>
<div id="data_SPAC_DT_INTERVINIENTES:TIPO_DIRECCION" style="position: absolute; top: 292px; left: 175px;">
<html:hidden property="property(SPAC_DT_INTERVINIENTES:TIPO_DIRECCION)" />
<nobr>
<ispac:htmlTextImageFrame property="property(TIPO_DIRECCION_SPAC_TBL_005:SUSTITUTO)" readonly="true" readonlyTag="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="35" id="SEARCH_SPAC_DT_INTERVINIENTES_TIPO_DIRECCION" target="workframe" action="selectSubstitute.do?entity=SPAC_TBL_005" image="img/search-mg.gif" titleKeyLink="select.tipoDireccionNotificacion" imageDelete="img/borrar.gif" titleKeyImageDelete="title.delete.data.selection" styleClassDeleteLink="tdlink" confirmDeleteKey="msg.delete.data.selection" showDelete="true" showFrame="true" width="640" height="480" tabindex="112">
<ispac:parameter name="SEARCH_SPAC_DT_INTERVINIENTES_TIPO_DIRECCION" id="property(SPAC_DT_INTERVINIENTES:TIPO_DIRECCION)" property="VALOR" />
<ispac:parameter name="SEARCH_SPAC_DT_INTERVINIENTES_TIPO_DIRECCION" id="property(TIPO_DIRECCION_SPAC_TBL_005:SUSTITUTO)" property="SUSTITUTO" />
</ispac:htmlTextImageFrame>
</nobr>
</div>
<div id="label_SPAC_DT_INTERVINIENTES:DIRECCIONTELEMATICA" style="position: absolute; top: 269px; left: 10px;" class="formsTitleB">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(SPAC_DT_INTERVINIENTES:DIRECCIONTELEMATICA)" />:</nobr>
</div>
<div id="data_SPAC_DT_INTERVINIENTES:DIRECCIONTELEMATICA" style="position: absolute; top: 266px; left: 175px;">
<ispac:htmlText property="property(SPAC_DT_INTERVINIENTES:DIRECCIONTELEMATICA)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="80" maxlength="60" tabindex="111">
</ispac:htmlText>
</div>
</div>
</div>
</td>
</tr>
<tr>
<td height="15"><img src=''<ispac:rewrite href="img/pixel.gif"/>''/></td>
</tr>
</table>'
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

-- Soporte a Contador por procedimiento para numeros de expedientes
ALTER TABLE spac_numexp_contador ADD id_pcd integer NOT NULL DEFAULT -1;
ALTER TABLE ONLY spac_numexp_contador DROP CONSTRAINT pk_spac_numexp_contador;
ALTER TABLE ONLY spac_numexp_contador ADD CONSTRAINT pk_spac_numexp_contador PRIMARY KEY (id_pcd);
