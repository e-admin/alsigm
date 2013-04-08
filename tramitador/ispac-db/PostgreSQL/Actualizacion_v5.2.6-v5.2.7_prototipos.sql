

-----------------------------------------------------
-- Actualización de los prototipos de v5.2.6 a v5.2.7
-----------------------------------------------------


--
-- Actualización de las tablas de validación (editable a S en la definición)
--
UPDATE spac_ct_entidades SET definicion = '<entity version=''1.00''><editable>S</editable><status>0</status><fields><field id=''1''><logicalName><![CDATA[id]]></logicalName><physicalName>id</physicalName><type>3</type></field><field id=''2''><logicalName><![CDATA[valor]]></logicalName><physicalName>valor</physicalName><type>0</type><size>8</size></field><field id=''3''><logicalName><![CDATA[sustituto]]></logicalName><physicalName>sustituto</physicalName><type>0</type><size>64</size></field><field id=''4''><logicalName><![CDATA[vigente]]></logicalName><physicalName>vigente</physicalName><type>2</type><size>1</size></field></fields></entity>'
WHERE id = 137;
UPDATE spac_ct_entidades SET definicion = '<entity version=''1.00''><editable>S</editable><status>0</status><fields><field id=''1''><logicalName><![CDATA[id]]></logicalName><physicalName>id</physicalName><type>3</type></field><field id=''2''><logicalName><![CDATA[valor]]></logicalName><physicalName>valor</physicalName><type>0</type><size>8</size></field><field id=''3''><logicalName><![CDATA[sustituto]]></logicalName><physicalName>sustituto</physicalName><type>0</type><size>64</size></field><field id=''4''><logicalName><![CDATA[vigente]]></logicalName><physicalName>vigente</physicalName><type>2</type><size>1</size></field></fields></entity>'
WHERE id = 138;
UPDATE spac_ct_entidades SET definicion = '<entity version=''1.00''><editable>S</editable><status>0</status><fields><field id=''1''><logicalName><![CDATA[id]]></logicalName><physicalName>id</physicalName><type>3</type></field><field id=''2''><logicalName><![CDATA[valor]]></logicalName><physicalName>valor</physicalName><type>0</type><size>8</size></field><field id=''3''><logicalName><![CDATA[sustituto]]></logicalName><physicalName>sustituto</physicalName><type>0</type><size>64</size></field><field id=''4''><logicalName><![CDATA[vigente]]></logicalName><physicalName>vigente</physicalName><type>2</type><size>1</size></field></fields></entity>'
WHERE id = 139;
UPDATE spac_ct_entidades SET definicion = '<entity version=''1.00''><editable>S</editable><status>0</status><fields><field id=''1''><logicalName><![CDATA[id]]></logicalName><physicalName>id</physicalName><type>3</type></field><field id=''2''><logicalName><![CDATA[valor]]></logicalName><physicalName>valor</physicalName><type>0</type><size>8</size></field><field id=''3''><logicalName><![CDATA[sustituto]]></logicalName><physicalName>sustituto</physicalName><type>0</type><size>64</size></field><field id=''4''><logicalName><![CDATA[vigente]]></logicalName><physicalName>vigente</physicalName><type>2</type><size>1</size></field></fields></entity>'
WHERE id = 140;


--
-- Actualización de nombres de tablas en validaciones de entidad, parámetros de formularios y JSPs
--
UPDATE spac_ct_entidades SET definicion ='<entity version=''1.00''>
<editable>S</editable>
<dropable>S</dropable>
<status>0</status>
<fields>
	<field id=''1''>
		<physicalName>tipo_objeto</physicalName>
		<type>0</type>
		<size>15</size>
	</field>
	<field id=''2''>
		<physicalName>tipo_localizador</physicalName>
		<type>0</type>
		<size>15</size>
	</field>
	<field id=''3''>
		<physicalName>localizador</physicalName>
		<type>0</type>
		<size>15</size>
	</field>
	<field id=''4''>
		<physicalName>actuacion_casco_historico</physicalName>
		<type>0</type>
		<size>2</size>
	</field>
	<field id=''5''>
		<physicalName>tipo_finca</physicalName>
		<type>0</type>
		<size>15</size>
	</field>
	<field id=''6''>
		<physicalName>tipo_suelo</physicalName>
		<type>0</type>
		<size>15</size>
	</field>
	<field id=''7''>
		<physicalName>ubicacion_inmueble</physicalName>
		<type>0</type>
		<size>512</size>
	</field>
	<field id=''8''>
		<physicalName>referencia_catastral</physicalName>
		<type>0</type>
		<size>20</size>
	</field>
	<field id=''9''>
		<physicalName>localizacion</physicalName>
		<type>0</type>
		<size>15</size>
	</field>
	<field id=''10''>
		<physicalName>descripcion</physicalName>
		<type>1</type>
	</field>
	<field id=''11''>
		<physicalName>presupuesto_total</physicalName>
		<type>4</type>
		<size>15</size>
		<precision>2</precision>
	</field>
	<field id=''12''>
		<physicalName>fecha_inicio</physicalName>
		<type>6</type>
	</field>
	<field id=''13''>
		<physicalName>fecha_terminacion</physicalName>
		<type>6</type>
	</field>
	<field id=''14''>
		<physicalName>ocupacion_via_publica</physicalName>
		<type>0</type>
		<size>2</size>
	</field>
	<field id=''15''>
		<physicalName>id</physicalName>
		<descripcion><![CDATA[Campos Clave de la entidad (Uso interno del sistema)]]></descripcion>
		<type>3</type>
	</field>
	<field id=''16''>
		<physicalName>numexp</physicalName>
		<descripcion><![CDATA[Campo que relaciona la entidad con un número de expediente (Uso interno del sistema)]]></descripcion>
		<type>0</type>
		<size>30</size>
	</field>
</fields>
<validations>
	<validation id=''1''>
		<fieldId>9</fieldId>
		<table>SPAC_TBL_012</table>
		<tableType>3</tableType>
		<class/>
		<mandatory>N</mandatory>
	</validation>
	<validation id=''2''>
		<fieldId>5</fieldId>
		<table>SPAC_TBL_011</table>
		<tableType>3</tableType>
		<class/>
		<mandatory>N</mandatory>
	</validation>
	<validation id=''3''>
		<fieldId>6</fieldId>
		<table>SPAC_TBL_010</table>
		<tableType>3</tableType>
		<class/>
		<mandatory>N</mandatory>
	</validation>
	<validation id=''4''>
		<fieldId>4</fieldId>
		<table>SPAC_TBL_009</table>
		<tableType>2</tableType>
		<class/>
		<mandatory>N</mandatory>
	</validation>
	<validation id=''5''>
		<fieldId>14</fieldId>
		<table>SPAC_TBL_009</table>
		<tableType>2</tableType>
		<class/>
		<mandatory>N</mandatory>
	</validation>
</validations>
</entity>'
WHERE id = 200;

UPDATE spac_ct_aplicaciones 
SET parametros = '<?xml version=''1.0'' encoding=''ISO-8859-1''?><parameters><entity type=''VALIDATION''><table>SPAC_TBL_012</table><relation type=''FLD''><primary-field>LOCALIZACION</primary-field><secondary-field>VALOR</secondary-field></relation></entity><entity type=''VALIDATION''><table>SPAC_TBL_011</table><relation type=''FLD''><primary-field>TIPO_FINCA</primary-field><secondary-field>VALOR</secondary-field></relation></entity><entity type=''VALIDATION''><table>SPAC_TBL_010</table><relation type=''FLD''><primary-field>TIPO_SUELO</primary-field><secondary-field>VALOR</secondary-field></relation></entity></parameters>'
WHERE id = 10;

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
<bean:write name="defaultForm" property="entityApp.label(SGL_OBRAS_MENORES:SGL_OBRAS_MENORES)" />
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
<div id="dataBlock_SGL_OBRAS_MENORES" style="position: relative; height: 320px; width: 600px;">
<div id="label_SGL_OBRAS_MENORES:TIPO_OBJETO" style="position: absolute; top: 61px; left: 10px;" class="formsTitleB">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(SGL_OBRAS_MENORES:TIPO_OBJETO)" />:</nobr>
</div>
<div id="data_SGL_OBRAS_MENORES:TIPO_OBJETO" style="position: absolute; top: 58px; left: 150px;">
<ispac:htmlText property="property(SGL_OBRAS_MENORES:TIPO_OBJETO)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="25" maxlength="15" tabindex="102">
</ispac:htmlText>
</div>
<div id="label_SGL_OBRAS_MENORES:TIPO_LOCALIZADOR" style="position: absolute; top: 61px; left: 310px;" class="formsTitleB">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(SGL_OBRAS_MENORES:TIPO_LOCALIZADOR)" />:</nobr>
</div>
<div id="data_SGL_OBRAS_MENORES:TIPO_LOCALIZADOR" style="position: absolute; top: 58px; left: 440px;">
<ispac:htmlText property="property(SGL_OBRAS_MENORES:TIPO_LOCALIZADOR)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="25" maxlength="15" tabindex="103">
</ispac:htmlText>
</div>
<div id="label_SGL_OBRAS_MENORES:LOCALIZADOR" style="position: absolute; top: 92px; left: 10px;" class="formsTitleB">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(SGL_OBRAS_MENORES:LOCALIZADOR)" />:</nobr>
</div>
<div id="data_SGL_OBRAS_MENORES:LOCALIZADOR" style="position: absolute; top: 89px; left: 150px;">
<ispac:htmlText property="property(SGL_OBRAS_MENORES:LOCALIZADOR)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="25" maxlength="15" tabindex="104">
</ispac:htmlText>
</div>
<div id="label_SGL_OBRAS_MENORES:ACTUACION_CASCO_HISTORICO" style="position: absolute; top: 222px; left: 10px; width: 130px;" class="formsTitleB">
<bean:write name="defaultForm" property="entityApp.label(SGL_OBRAS_MENORES:ACTUACION_CASCO_HISTORICO)" />:
</div>
<div id="data_SGL_OBRAS_MENORES:ACTUACION_CASCO_HISTORICO" style="position: absolute; top: 219px; left: 150px;">
<nobr>
<ispac:htmlTextImageFrame property="property(SGL_OBRAS_MENORES:ACTUACION_CASCO_HISTORICO)" readonly="true" readonlyTag="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="5" id="SEARCH_SGL_OBRAS_MENORES_ACTUACION_CASCO_HISTORICO" target="workframe" action="selectValue.do?entity=SPAC_TBL_009" image="img/search-mg.gif" titleKeyLink="title.link.data.selection" imageDelete="img/borrar.gif" titleKeyImageDelete="title.delete.data.selection" styleClassDeleteLink="tdlink" confirmDeleteKey="msg.delete.data.selection" showDelete="true" showFrame="true" width="640" height="480" tabindex="110">
<ispac:parameter name="SEARCH_SGL_OBRAS_MENORES_ACTUACION_CASCO_HISTORICO" id="property(SGL_OBRAS_MENORES:ACTUACION_CASCO_HISTORICO)" property="VALOR" />
</ispac:htmlTextImageFrame>
</nobr>
</div>
<div id="label_SGL_OBRAS_MENORES:TIPO_FINCA" style="position: absolute; top: 189px; left: 10px;" class="formsTitleB">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(SGL_OBRAS_MENORES:TIPO_FINCA)" />:</nobr>
</div>
<div id="data_SGL_OBRAS_MENORES:TIPO_FINCA" style="position: absolute; top: 186px; left: 150px;">
<html:hidden property="property(SGL_OBRAS_MENORES:TIPO_FINCA)" />
<nobr>
<ispac:htmlTextImageFrame property="property(TIPO_FINCA_SPAC_TBL_011:SUSTITUTO)" readonly="true" readonlyTag="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="18" id="SEARCH_SGL_OBRAS_MENORES_TIPO_FINCA" target="workframe" action="selectSubstitute.do?entity=SPAC_TBL_011" image="img/search-mg.gif" titleKeyLink="title.link.data.selection" imageDelete="img/borrar.gif" titleKeyImageDelete="title.delete.data.selection" styleClassDeleteLink="tdlink" confirmDeleteKey="msg.delete.data.selection" showDelete="true" showFrame="true" width="640" height="480" tabindex="108">
<ispac:parameter name="SEARCH_SGL_OBRAS_MENORES_TIPO_FINCA" id="property(SGL_OBRAS_MENORES:TIPO_FINCA)" property="VALOR" />
<ispac:parameter name="SEARCH_SGL_OBRAS_MENORES_TIPO_FINCA" id="property(TIPO_FINCA_SPAC_TBL_011:SUSTITUTO)" property="SUSTITUTO" />
</ispac:htmlTextImageFrame>
</nobr>
</div>
<div id="label_SGL_OBRAS_MENORES:TIPO_SUELO" style="position: absolute; top: 189px; left: 310px;" class="formsTitleB">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(SGL_OBRAS_MENORES:TIPO_SUELO)" />:</nobr>
</div>
<div id="data_SGL_OBRAS_MENORES:TIPO_SUELO" style="position: absolute; top: 186px; left: 440px;">
<html:hidden property="property(SGL_OBRAS_MENORES:TIPO_SUELO)" />
<nobr>
<ispac:htmlTextImageFrame property="property(TIPO_SUELO_SPAC_TBL_010:SUSTITUTO)" readonly="true" readonlyTag="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="18" id="SEARCH_SGL_OBRAS_MENORES_TIPO_SUELO" target="workframe" action="selectSubstitute.do?entity=SPAC_TBL_010" image="img/search-mg.gif" titleKeyLink="title.link.data.selection" imageDelete="img/borrar.gif" titleKeyImageDelete="title.delete.data.selection" styleClassDeleteLink="tdlink" confirmDeleteKey="msg.delete.data.selection" showDelete="true" showFrame="true" width="640" height="480" tabindex="109">
<ispac:parameter name="SEARCH_SGL_OBRAS_MENORES_TIPO_SUELO" id="property(SGL_OBRAS_MENORES:TIPO_SUELO)" property="VALOR" />
<ispac:parameter name="SEARCH_SGL_OBRAS_MENORES_TIPO_SUELO" id="property(TIPO_SUELO_SPAC_TBL_010:SUSTITUTO)" property="SUSTITUTO" />
</ispac:htmlTextImageFrame>
</nobr>
</div>
<div id="label_SGL_OBRAS_MENORES:UBICACION_INMUEBLE" style="position: absolute; top: 123px; left: 10px;" class="formsTitleB">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(SGL_OBRAS_MENORES:UBICACION_INMUEBLE)" />:</nobr>
</div>
<div id="data_SGL_OBRAS_MENORES:UBICACION_INMUEBLE" style="position: absolute; top: 120px; left: 150px;">
<ispac:htmlText property="property(SGL_OBRAS_MENORES:UBICACION_INMUEBLE)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="83" maxlength="512" tabindex="105">
</ispac:htmlText>
</div>
<div id="label_SGL_OBRAS_MENORES:REFERENCIA_CATASTRAL" style="position: absolute; top: 155px; left: 10px;" class="formsTitleB">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(SGL_OBRAS_MENORES:REFERENCIA_CATASTRAL)" />:</nobr>
</div>
<div id="data_SGL_OBRAS_MENORES:REFERENCIA_CATASTRAL" style="position: absolute; top: 152px; left: 150px;">
<ispac:htmlText property="property(SGL_OBRAS_MENORES:REFERENCIA_CATASTRAL)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="25" maxlength="20" tabindex="106">
</ispac:htmlText>
</div>
<div id="label_SGL_OBRAS_MENORES:LOCALIZACION" style="position: absolute; top: 155px; left: 310px;" class="formsTitleB">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(SGL_OBRAS_MENORES:LOCALIZACION)" />:</nobr>
</div>
<div id="data_SGL_OBRAS_MENORES:LOCALIZACION" style="position: absolute; top: 152px; left: 440px;">
<html:hidden property="property(SGL_OBRAS_MENORES:LOCALIZACION)" />
<nobr>
<ispac:htmlTextImageFrame property="property(LOCALIZACION_SPAC_TBL_012:SUSTITUTO)" readonly="true" readonlyTag="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="18" id="SEARCH_SGL_OBRAS_MENORES_LOCALIZACION" target="workframe" action="selectSubstitute.do?entity=SPAC_TBL_012" image="img/search-mg.gif" titleKeyLink="title.link.data.selection" imageDelete="img/borrar.gif" titleKeyImageDelete="title.delete.data.selection" styleClassDeleteLink="tdlink" confirmDeleteKey="msg.delete.data.selection" showDelete="true" showFrame="true" width="640" height="480" tabindex="107">
<ispac:parameter name="SEARCH_SGL_OBRAS_MENORES_LOCALIZACION" id="property(SGL_OBRAS_MENORES:LOCALIZACION)" property="VALOR" />
<ispac:parameter name="SEARCH_SGL_OBRAS_MENORES_LOCALIZACION" id="property(LOCALIZACION_SPAC_TBL_012:SUSTITUTO)" property="SUSTITUTO" />
</ispac:htmlTextImageFrame>
</nobr>
</div>
<div id="label_SGL_OBRAS_MENORES:DESCRIPCION" style="position: absolute; top: 20px; left: 10px;" class="formsTitleB">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(SGL_OBRAS_MENORES:DESCRIPCION)" />:</nobr>
</div>
<div id="data_SGL_OBRAS_MENORES:DESCRIPCION" style="position: absolute; top: 17px; left: 150px;">
<ispac:htmlTextarea property="property(SGL_OBRAS_MENORES:DESCRIPCION)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" rows="2" cols="82" tabindex="101">
</ispac:htmlTextarea>
</div>
<div id="label_SGL_OBRAS_MENORES:PRESUPUESTO_TOTAL" style="position: absolute; top: 254px; left: 10px; width: 120px;" class="formsTitleB">
<bean:write name="defaultForm" property="entityApp.label(SGL_OBRAS_MENORES:PRESUPUESTO_TOTAL)" />:
</div>
<div id="data_SGL_OBRAS_MENORES:PRESUPUESTO_TOTAL" style="position: absolute; top: 251px; left: 150px;" class="formsTitleB">
<ispac:htmlText property="property(SGL_OBRAS_MENORES:PRESUPUESTO_TOTAL)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="18" maxlength="15" tabindex="112">
</ispac:htmlText>
&nbsp;<bean:message key="common.message.currency"/>
</div>
<div id="label_SGL_OBRAS_MENORES:FECHA_INICIO" style="position: absolute; top: 289px; left: 10px;" class="formsTitleB">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(SGL_OBRAS_MENORES:FECHA_INICIO)" />:</nobr>
</div>
<div id="data_SGL_OBRAS_MENORES:FECHA_INICIO" style="position: absolute; top: 286px; left: 150px;">
<nobr>
<ispac:htmlTextCalendar property="property(SGL_OBRAS_MENORES:FECHA_INICIO)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="14" maxlength="10" image=''<%= buttoncalendar %>'' format="dd/mm/yyyy" enablePast="true" tabindex="113">
</ispac:htmlTextCalendar>
</nobr>
</div>
<div id="label_SGL_OBRAS_MENORES:FECHA_TERMINACION" style="position: absolute; top: 289px; left: 310px;" class="formsTitleB">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(SGL_OBRAS_MENORES:FECHA_TERMINACION)" />:</nobr>
</div>
<div id="data_SGL_OBRAS_MENORES:FECHA_TERMINACION" style="position: absolute; top: 286px; left: 440px;">
<nobr>
<ispac:htmlTextCalendar property="property(SGL_OBRAS_MENORES:FECHA_TERMINACION)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="14" maxlength="10" image=''<%= buttoncalendar %>'' format="dd/mm/yyyy" enablePast="true" tabindex="114">
</ispac:htmlTextCalendar>
</nobr>
</div>
<div id="label_SGL_OBRAS_MENORES:OCUPACION_VIA_PUBLICA" style="position: absolute; top: 222px; left: 310px; width: 130px;" class="formsTitleB">
<bean:write name="defaultForm" property="entityApp.label(SGL_OBRAS_MENORES:OCUPACION_VIA_PUBLICA)" />:
</div>
<div id="data_SGL_OBRAS_MENORES:OCUPACION_VIA_PUBLICA" style="position: absolute; top: 219px; left: 440px;">
<nobr>
<ispac:htmlTextImageFrame property="property(SGL_OBRAS_MENORES:OCUPACION_VIA_PUBLICA)" readonly="true" readonlyTag="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="5" id="SEARCH_SGL_OBRAS_MENORES_OCUPACION_VIA_PUBLICA" target="workframe" action="selectValue.do?entity=SPAC_TBL_009" image="img/search-mg.gif" titleKeyLink="title.link.data.selection" imageDelete="img/borrar.gif" titleKeyImageDelete="title.delete.data.selection" styleClassDeleteLink="tdlink" confirmDeleteKey="msg.delete.data.selection" showDelete="true" showFrame="true" width="640" height="480" tabindex="111">
<ispac:parameter name="SEARCH_SGL_OBRAS_MENORES_OCUPACION_VIA_PUBLICA" id="property(SGL_OBRAS_MENORES:OCUPACION_VIA_PUBLICA)" property="VALOR" />
</ispac:htmlTextImageFrame>
</nobr>
</div>
</div>
</div>
</td>
</tr>
<tr>
<td height="15"><img src=''<ispac:rewrite href="img/pixel.gif"/>''/></td>
</tr>
</table>'
WHERE id = 10;

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
<bean:write name="defaultForm" property="entityApp.label(SGQ_QUEJAS:SGQ_QUEJAS)" />
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
<div id="dataBlock_SGQ_QUEJAS" style="position: relative; height: 235px; width: 600px;">
<div id="label_SGQ_QUEJAS:ID_ORGANO_OBJETO" style="position: absolute; top: 203px; left: 10px;" class="formsTitleB">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(SGQ_QUEJAS:ID_ORGANO_OBJETO)" />:</nobr>
</div>
<div id="data_SGQ_QUEJAS:ID_ORGANO_OBJETO" style="position: absolute; top: 200px; left: 150px;">
<html:hidden property="property(SGQ_QUEJAS:ID_ORGANO_OBJETO)"/>
<nobr>
<ispac:htmlTextImageFrame property="property(SGQ_QUEJAS:ORGANO_OBJETO)"
			  readonly="true"
			  readonlyTag="false"
			  propertyReadonly="readonly"
			  styleClass="input"
			  styleClassReadonly="inputReadOnly"
			  size="46"
			  id="SELECT_SGQ_QUEJAS_ORGANO_OBJETO"
			  target="workframe"
			  action="viewUsersManager.do?captionkey=app.selectOrg.seleccionar&noviewusers=true&noviewgroups=true"
			  image="img/search-mg.gif"
			  showFrame="true" tabindex="102">
	<ispac:parameter name="SELECT_SGQ_QUEJAS_ORGANO_OBJETO" id="property(SGQ_QUEJAS:ID_ORGANO_OBJETO)" property="UID"/>
	<ispac:parameter name="SELECT_SGQ_QUEJAS_ORGANO_OBJETO" id="property(SGQ_QUEJAS:ORGANO_OBJETO)" property="NAME"/>
</ispac:htmlTextImageFrame>
</nobr>
</div>
<div id="label_SGQ_QUEJAS:ASUNTO" style="position: absolute; top: 20px; left: 10px;" class="formsTitleB">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(SGQ_QUEJAS:ASUNTO)" />:</nobr>
</div>
<div id="data_SGQ_QUEJAS:ASUNTO" style="position: absolute; top: 37px; left: 11px;">
<ispac:htmlTextarea property="property(SGQ_QUEJAS:ASUNTO)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" rows="12" cols="80" tabindex="101">
</ispac:htmlTextarea>
</div>
</div>
</div>
</td>
</tr>
<tr>
<td height="15"><img src=''<ispac:rewrite href="img/pixel.gif"/>''/></td>
</tr>
</table>'
WHERE id = 11;

UPDATE spac_ct_entidades SET definicion ='<entity version=''1.00''>
<editable>S</editable>
<dropable>S</dropable>
<status>0</status>
<fields>
	<field id=''1''>
		<physicalName>id_pcd</physicalName>
		<type>3</type>
	</field>
	<field id=''2''>
		<physicalName>anio_presupuestario</physicalName>
		<type>2</type>
		<size>4</size>
	</field>
	<field id=''3''>
		<physicalName>tipo_subvencion</physicalName>
		<type>0</type>
		<size>8</size>
	</field>
	<field id=''4''>
		<physicalName>fecha_convocatoria</physicalName>
		<type>6</type>
	</field>
	<field id=''5''>
		<physicalName>denominacion_proyecto</physicalName>
		<type>0</type>
		<size>10</size>
	</field>
	<field id=''6''>
		<physicalName>resumen_proyecto</physicalName>
		<type>1</type>
	</field>
	<field id=''7''>
		<physicalName>fecha_inicio_ejecucion</physicalName>
		<type>6</type>
	</field>
	<field id=''8''>
		<physicalName>fecha_fin_ejecucion</physicalName>
		<type>6</type>
	</field>
	<field id=''9''>
		<physicalName>duracion_proyecto</physicalName>
		<type>2</type>
		<size>2</size>
	</field>
	<field id=''10''>
		<physicalName>colectivo_destino</physicalName>
		<type>0</type>
		<size>64</size>
	</field>
	<field id=''11''>
		<physicalName>importe_solicitado</physicalName>
		<type>4</type>
		<size>13</size>
		<precision>2</precision>
	</field>
	<field id=''12''>
		<physicalName>importe_concedido</physicalName>
		<type>4</type>
		<size>13</size>
		<precision>2</precision>
	</field>
	<field id=''13''>
		<physicalName>prevision_gastos</physicalName>
		<type>1</type>
	</field>
	<field id=''14''>
		<physicalName>importe_gastos</physicalName>
		<type>4</type>
		<size>10</size>
		<precision>2</precision>
	</field>
	<field id=''15''>
		<physicalName>total_gastos</physicalName>
		<type>4</type>
		<size>10</size>
		<precision>2</precision>
	</field>
	<field id=''16''>
		<physicalName>aportacion_ayto</physicalName>
		<type>4</type>
		<size>13</size>
		<precision>2</precision>
	</field>
	<field id=''17''>
		<physicalName>aportacion_entidad</physicalName>
		<type>4</type>
		<size>13</size>
		<precision>2</precision>
	</field>
	<field id=''18''>
		<physicalName>otras_aportaciones</physicalName>
		<type>4</type>
		<size>13</size>
		<precision>2</precision>
	</field>
	<field id=''19''>
		<physicalName>total_aportaciones</physicalName>
		<type>4</type>
		<size>13</size>
		<precision>2</precision>
	</field>
	<field id=''20''>
		<physicalName>finalidad_subvencion</physicalName>
		<type>1</type>
	</field>
	<field id=''21''>
		<physicalName>observaciones</physicalName>
		<type>1</type>
	</field>
	<field id=''22''>
		<physicalName>id</physicalName>
		<descripcion><![CDATA[Campos Clave de la entidad (Uso interno del sistema)]]></descripcion>
		<type>3</type>
	</field>
	<field id=''23''>
		<physicalName>numexp</physicalName>
		<descripcion><![CDATA[Campo que relaciona la entidad con un número de expediente (Uso interno del sistema)]]></descripcion>
		<type>0</type>
		<size>30</size>
	</field>
</fields>
<validations>
	<validation id=''1''>
		<fieldId>3</fieldId>
		<table>SPAC_TBL_013</table>
		<tableType>3</tableType>
		<class/>
		<mandatory>N</mandatory>
	</validation>
</validations>
</entity>'
WHERE id = 202;

UPDATE spac_ct_aplicaciones 
SET parametros = '<?xml version=''1.0'' encoding=''ISO-8859-1''?><parameters><entity type=''VALIDATION''><table>SPAC_TBL_013</table><relation type=''FLD''><primary-field>TIPO_SUBVENCION</primary-field><secondary-field>VALOR</secondary-field></relation></entity></parameters>'
WHERE id = 12;

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
<bean:write name="defaultForm" property="entityApp.label(SGS_SUBVENCIONES:SGS_SUBVENCIONES)" />
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
<div id="dataBlock_SGS_SUBVENCIONES" style="position: relative; height: 550px; width: 640px;">
  <div id="label_SGS_SUBVENCIONES:ANIO_PRESUPUESTARIO" style="position: absolute; top: 23px; left: 10px;" class="formsTitleB">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(SGS_SUBVENCIONES:ANIO_PRESUPUESTARIO)" />:</nobr>
</div>
<div id="data_SGS_SUBVENCIONES:ANIO_PRESUPUESTARIO" style="position: absolute; top: 20px; left: 190px;">
<ispac:htmlText property="property(SGS_SUBVENCIONES:ANIO_PRESUPUESTARIO)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="10" maxlength="4" tabindex="101">
</ispac:htmlText>
</div>
<div id="label_SGS_SUBVENCIONES:TIPO_SUBVENCION" style="position: absolute; top: 53px; left: 10px;" class="formsTitleB">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(SGS_SUBVENCIONES:TIPO_SUBVENCION)" />:</nobr>
</div>
<div id="data_SGS_SUBVENCIONES:TIPO_SUBVENCION" style="position: absolute; top: 50px; left: 190px;">
<html:hidden property="property(SGS_SUBVENCIONES:TIPO_SUBVENCION)" />
<nobr>
<ispac:htmlTextImageFrame property="property(TIPO_SUBVENCION_SPAC_TBL_013:SUSTITUTO)" readonly="true" readonlyTag="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="50" id="SEARCH_SGS_SUBVENCIONES_TIPO_SUBVENCION" target="workframe" action="selectSubstitute.do?entity=SPAC_TBL_013" image="img/search-mg.gif" titleKeyLink="title.link.data.selection" imageDelete="img/borrar.gif" titleKeyImageDelete="title.delete.data.selection" styleClassDeleteLink="tdlink" confirmDeleteKey="msg.delete.data.selection" showDelete="true" showFrame="true" width="640" height="480" tabindex="103">
<ispac:parameter name="SEARCH_SGS_SUBVENCIONES_TIPO_SUBVENCION" id="property(SGS_SUBVENCIONES:TIPO_SUBVENCION)" property="VALOR" />
<ispac:parameter name="SEARCH_SGS_SUBVENCIONES_TIPO_SUBVENCION" id="property(TIPO_SUBVENCION_SPAC_TBL_013:SUSTITUTO)" property="SUSTITUTO" />
</ispac:htmlTextImageFrame>
</nobr>
</div>
<div id="label_SGS_SUBVENCIONES:FECHA_CONVOCATORIA" style="position: absolute; top: 23px; left: 352px;" class="formsTitleB">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(SGS_SUBVENCIONES:FECHA_CONVOCATORIA)" />:</nobr>
</div>
<div id="data_SGS_SUBVENCIONES:FECHA_CONVOCATORIA" style="position: absolute; top: 20px; left: 490px;">
<nobr>
<ispac:htmlTextCalendar property="property(SGS_SUBVENCIONES:FECHA_CONVOCATORIA)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="14" maxlength="10" image=''<%= buttoncalendar %>'' format="dd/mm/yyyy" enablePast="true" tabindex="102">
</ispac:htmlTextCalendar>
</nobr>
</div>
<div id="label_SGS_SUBVENCIONES:DENOMINACION_PROYECTO" style="position: absolute; top: 113px; left: 10px;" class="formsTitleB">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(SGS_SUBVENCIONES:DENOMINACION_PROYECTO)" />:</nobr>
</div>
<div id="data_SGS_SUBVENCIONES:DENOMINACION_PROYECTO" style="position: absolute; top: 110px; left: 190px;">
<ispac:htmlText property="property(SGS_SUBVENCIONES:DENOMINACION_PROYECTO)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="80" maxlength="120" tabindex="105">
</ispac:htmlText>
</div>
<div id="label_SGS_SUBVENCIONES:RESUMEN_PROYECTO" style="position: absolute; top: 143px; left: 10px;" class="formsTitleB">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(SGS_SUBVENCIONES:RESUMEN_PROYECTO)" />:</nobr>
</div>
<div id="data_SGS_SUBVENCIONES:RESUMEN_PROYECTO" style="position: absolute; top: 140px; left: 190px;">
<ispac:htmlTextarea property="property(SGS_SUBVENCIONES:RESUMEN_PROYECTO)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" rows="2" cols="80" tabindex="106">
</ispac:htmlTextarea>
</div>
<div id="label_SGS_SUBVENCIONES:FECHA_INICIO_EJECUCION" style="position: absolute; top: 183px; left: 10px;" class="formsTitleB">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(SGS_SUBVENCIONES:FECHA_INICIO_EJECUCION)" />:</nobr>
</div>
<div id="data_SGS_SUBVENCIONES:FECHA_INICIO_EJECUCION" style="position: absolute; top: 180px; left: 190px;">
<nobr>
<ispac:htmlTextCalendar property="property(SGS_SUBVENCIONES:FECHA_INICIO_EJECUCION)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="14" maxlength="10" image=''<%= buttoncalendar %>'' format="dd/mm/yyyy" enablePast="true" tabindex="107">
</ispac:htmlTextCalendar>
</nobr>
</div>
<div id="label_SGS_SUBVENCIONES:FECHA_FIN_EJECUCION" style="position: absolute; top: 183px; left: 354px;" class="formsTitleB">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(SGS_SUBVENCIONES:FECHA_FIN_EJECUCION)" />:</nobr>
</div>
<div id="data_SGS_SUBVENCIONES:FECHA_FIN_EJECUCION" style="position: absolute; top: 180px; left: 490px;">
<nobr>
<ispac:htmlTextCalendar property="property(SGS_SUBVENCIONES:FECHA_FIN_EJECUCION)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="14" maxlength="10" image=''<%= buttoncalendar %>'' format="dd/mm/yyyy" enablePast="true" tabindex="108">
</ispac:htmlTextCalendar>
</nobr>
</div>
<div id="label_SGS_SUBVENCIONES:DURACION_PROYECTO" style="position: absolute; top: 213px; left: 10px;" class="formsTitleB">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(SGS_SUBVENCIONES:DURACION_PROYECTO)" />:</nobr>
</div>
<div id="data_SGS_SUBVENCIONES:DURACION_PROYECTO" style="position: absolute; top: 210px; left: 190px;" class="formsTitleB">
<ispac:htmlText property="property(SGS_SUBVENCIONES:DURACION_PROYECTO)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="5" maxlength="2" tabindex="109">
</ispac:htmlText>
&nbsp;<bean:message key="common.message.months"/>
</div>
<div id="label_SGS_SUBVENCIONES:COLECTIVO_DESTINO" style="position: absolute; top: 83px; left: 10px;" class="formsTitleB">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(SGS_SUBVENCIONES:COLECTIVO_DESTINO)" />:</nobr>
</div>
<div id="data_SGS_SUBVENCIONES:COLECTIVO_DESTINO" style="position: absolute; top: 80px; left: 190px;">
<ispac:htmlText property="property(SGS_SUBVENCIONES:COLECTIVO_DESTINO)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="80" maxlength="64" tabindex="104">
</ispac:htmlText>
</div>
<div id="label_SGS_SUBVENCIONES:IMPORTE_SOLICITADO" style="position: absolute; top: 243px; left: 10px;" class="formsTitleB">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(SGS_SUBVENCIONES:IMPORTE_SOLICITADO)" />:</nobr>
</div>
<div id="data_SGS_SUBVENCIONES:IMPORTE_SOLICITADO" style="position: absolute; top: 240px; left: 190px;" class="formsTitleB">
<ispac:htmlText property="property(SGS_SUBVENCIONES:IMPORTE_SOLICITADO)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="15" maxlength="17" tabindex="110">
</ispac:htmlText>
&nbsp;<bean:message key="common.message.currency"/>
</div>
<div id="label_SGS_SUBVENCIONES:IMPORTE_CONCEDIDO" style="position: absolute; top: 243px; left: 372px;" class="formsTitleB">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(SGS_SUBVENCIONES:IMPORTE_CONCEDIDO)" />:</nobr>
</div>
<div id="data_SGS_SUBVENCIONES:IMPORTE_CONCEDIDO" style="position: absolute; top: 240px; left: 490px;" class="formsTitleB">
<ispac:htmlText property="property(SGS_SUBVENCIONES:IMPORTE_CONCEDIDO)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="15" maxlength="17" tabindex="111">
</ispac:htmlText>
&nbsp;<bean:message key="common.message.currency"/>
</div>
<div id="label_SGS_SUBVENCIONES:PREVISION_GASTOS" style="position: absolute; top: 273px; left: 10px;" class="formsTitleB">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(SGS_SUBVENCIONES:PREVISION_GASTOS)" />:</nobr>
</div>
<div id="data_SGS_SUBVENCIONES:PREVISION_GASTOS" style="position: absolute; top: 270px; left: 190px;">
<ispac:htmlTextarea property="property(SGS_SUBVENCIONES:PREVISION_GASTOS)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" rows="2" cols="80" tabindex="112">
</ispac:htmlTextarea>
</div>
<div id="label_SGS_SUBVENCIONES:IMPORTE_GASTOS" style="position: absolute; top: 313px; left: 10px;" class="formsTitleB">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(SGS_SUBVENCIONES:IMPORTE_GASTOS)" />:</nobr>
</div>
<div id="data_SGS_SUBVENCIONES:IMPORTE_GASTOS" style="position: absolute; top: 310px; left: 190px;" class="formsTitleB">
<ispac:htmlText property="property(SGS_SUBVENCIONES:IMPORTE_GASTOS)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="15" maxlength="13" tabindex="113">
</ispac:htmlText>
&nbsp;<bean:message key="common.message.currency"/>
</div>
<div id="label_SGS_SUBVENCIONES:APORTACION_AYTO" style="position: absolute; top: 343px; left: 10px;" class="formsTitleB">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(SGS_SUBVENCIONES:APORTACION_AYTO)" />:</nobr>
</div>
<div id="data_SGS_SUBVENCIONES:APORTACION_AYTO" style="position: absolute; top: 340px; left: 190px;" class="formsTitleB">
<ispac:htmlText property="property(SGS_SUBVENCIONES:APORTACION_AYTO)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="15" maxlength="17" tabindex="114">
</ispac:htmlText>
&nbsp;<bean:message key="common.message.currency"/>
</div>
<div id="label_SGS_SUBVENCIONES:APORTACION_ENTIDAD" style="position: absolute; top: 373px; left: 10px;" class="formsTitleB">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(SGS_SUBVENCIONES:APORTACION_ENTIDAD)" />:</nobr>
</div>
<div id="data_SGS_SUBVENCIONES:APORTACION_ENTIDAD" style="position: absolute; top: 370px; left: 190px;" class="formsTitleB">
<ispac:htmlText property="property(SGS_SUBVENCIONES:APORTACION_ENTIDAD)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="15" maxlength="17" tabindex="115">
</ispac:htmlText>
&nbsp;<bean:message key="common.message.currency"/>
</div>
<div id="label_SGS_SUBVENCIONES:OTRAS_APORTACIONES" style="position: absolute; top: 403px; left: 10px;" class="formsTitleB">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(SGS_SUBVENCIONES:OTRAS_APORTACIONES)" />:</nobr>
</div>
<div id="data_SGS_SUBVENCIONES:OTRAS_APORTACIONES" style="position: absolute; top: 400px; left: 190px;" class="formsTitleB">
<ispac:htmlText property="property(SGS_SUBVENCIONES:OTRAS_APORTACIONES)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="15" maxlength="17" tabindex="116">
</ispac:htmlText>
&nbsp;<bean:message key="common.message.currency"/>
</div>
<div id="label_SGS_SUBVENCIONES:TOTAL_APORTACIONES" style="position: absolute; top: 433px; left: 10px;" class="formsTitleB">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(SGS_SUBVENCIONES:TOTAL_APORTACIONES)" />:</nobr>
</div>
<div id="data_SGS_SUBVENCIONES:TOTAL_APORTACIONES" style="position: absolute; top: 430px; left: 190px;" class="formsTitleB">
<logic:empty name="defaultForm" property="property(SGS_SUBVENCIONES:TOTAL_APORTACIONES)">0</logic:empty>
<logic:notEmpty name="defaultForm" property="property(SGS_SUBVENCIONES:TOTAL_APORTACIONES)">
	<bean:write name="defaultForm" property="property(SGS_SUBVENCIONES:TOTAL_APORTACIONES)" />
</logic:notEmpty>
&nbsp;<bean:message key="common.message.currency"/>
</div>
<div id="label_SGS_SUBVENCIONES:FINALIDAD_SUBVENCION" style="position: absolute; top: 463px; left: 10px;" class="formsTitleB">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(SGS_SUBVENCIONES:FINALIDAD_SUBVENCION)" />:</nobr>
</div>
<div id="data_SGS_SUBVENCIONES:FINALIDAD_SUBVENCION" style="position: absolute; top: 460px; left: 190px;">
<ispac:htmlTextarea property="property(SGS_SUBVENCIONES:FINALIDAD_SUBVENCION)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" rows="2" cols="80" tabindex="117">
</ispac:htmlTextarea>
</div>
<div id="label_SGS_SUBVENCIONES:OBSERVACIONES" style="position: absolute; top: 503px; left: 10px;" class="formsTitleB">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(SGS_SUBVENCIONES:OBSERVACIONES)" />:</nobr>
</div>
<div id="data_SGS_SUBVENCIONES:OBSERVACIONES" style="position: absolute; top: 500px; left: 190px;">
<ispac:htmlTextarea property="property(SGS_SUBVENCIONES:OBSERVACIONES)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" rows="2" cols="80" tabindex="118">
</ispac:htmlTextarea>
</div>
</div>
</div>
</td>
</tr>
<tr>
<td height="15"><img src=''<ispac:rewrite href="img/pixel.gif"/>''/></td>
</tr>
</table>'
WHERE id = 12;

