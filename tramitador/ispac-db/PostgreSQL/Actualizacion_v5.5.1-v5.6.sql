

-----------------------------------
-- Actualización de v5.5.1 a v5.6
-----------------------------------

--
-- Información de versión
--
INSERT INTO spac_infosistema (id, codigo, valor, fecha_actualizacion) VALUES (nextval('spac_sq_id_infosistema'), 'VERSIONBD', '5.6', current_timestamp);
INSERT INTO spac_infosistema (id, codigo, valor, fecha_actualizacion) VALUES (nextval('spac_sq_id_infosistema'), 'VERSIONAPP', '5.6', current_timestamp);


--
-- Eliminar en los trámites con subprocesos las relaciones con tipos de documentos
--
DELETE FROM SPAC_CT_TRTD WHERE ID_TRAMITE IN (SELECT ID FROM SPAC_CT_TRAMITES WHERE ID_SUBPROCESO IS NOT NULL);

--
-- Añade el campo documentos que indica si tiene activado o no la pestaña de documentos para el formulario de la entidad
--
ALTER TABLE SPAC_CT_APLICACIONES ADD COLUMN DOCUMENTOS character varying(2) DEFAULT 'NO'::character varying;

--
-- Cambio del tipo de la columna VALOR en SPAC_VARS
--
ALTER TABLE SPAC_VARS ALTER VALOR TYPE text;

--
-- Configuración del sello de documentos
--
INSERT INTO spac_vars (id, nombre, valor, descripcion) VALUES (nextval('spac_sq_id_vars'), 'STAMP_CONFIG', '<stamp width="4500" height="4000"><image width="250" height="100"><rectangle x="0" y="0" width="250" height="100" color="#000000"/><rectangle x="2" y="2" width="245" height="95" color="#0000FF"/><string x="20" y="25" color="#0000FF"><labels><label locale="ca">NOM DE L''ORGANITZACIO</label><label locale="eu">ERAKUNDEAREN IZENA</label><label locale="es">NOMBRE DE ORGANIZACIÓN</label><label locale="gl">NOME DE ORGANIZACIÓN</label></labels></string><string x="50" y="45" color="#0000FF"><labels><label locale="ca">REGISTRE DE ${TP_REG}</label><label locale="eu">ERREGISTROA ${TP_REG}</label><label locale="es">REGISTRO DE ${TP_REG}</label><label locale="gl">REXISTRO DE ${TP_REG}</label></labels></string><string x="40" y="65" color="#0000FF"><labels><label locale="ca">NUMERO: ${NREG}</label><label locale="eu">ZENBAKIA: ${NREG}</label><label locale="es">NÚMERO: ${NREG}</label><label locale="gl">NÚMERO: ${NREG}</label></labels></string><string x="55" y="85" color="#0000FF"><labels><label locale="ca">DATA: ${FREG}</label><label locale="eu">DATA: ${FREG}</label><label locale="es">FECHA: ${FREG}</label><label locale="gl">DATA: ${FREG}</label></labels></string></image></stamp>', 'Configuración del sello de documentos');


--
-- Correción del tab orden en el formulario de intervinientes
--
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
<div id="dataBlock_SPAC_DT_INTERVINIENTES_TAB_DATOS_PARTICIPANTE" style="position: relative; height: 400px; width: 600px;">

<c:choose>
<c:when test="${!empty thirdPartyAPIClass}">
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
				  size="34"
				  maxlength="16"
			  	  id="THIRD_SEARCH_THIRD_PARTY"
				  target="workframe"
			  	  action="searchThirdParty.do" 
			  	  image="img/search-mg.gif" 
			  	  titleKeyLink="search.thirdparty" 
			  	  showFrame="false"
			  	  inputField="SPAC_DT_INTERVINIENTES:NDOC"
			  	  jsDelete="delete_THIRD_SEARCH_THIRD_PARTY"
			  	  titleKeyImageDelete="forms.participantes.title.delete.data.thirdParty"
			  	  jsExecuteFrame="selectThirdParty" 
				  tabindex="112"
				  >

		<ispac:parameter name="THIRD_SEARCH_THIRD_PARTY" id="property(SPAC_DT_INTERVINIENTES:TIPO_PERSONA)" property="TIPOPERSONA"/>
		<ispac:parameter name="THIRD_SEARCH_THIRD_PARTY" id="property(SPAC_DT_INTERVINIENTES:ID_EXT)" property="IDTITULAR"/>
		<ispac:parameter name="THIRD_SEARCH_THIRD_PARTY" id="property(SPAC_DT_INTERVINIENTES:NOMBRE)" property="IDENTIDADTITULAR"/>
		<ispac:parameter name="THIRD_SEARCH_THIRD_PARTY" id="property(SPAC_DT_INTERVINIENTES:DIRNOT)" property="DOMICILIO"/>
		<ispac:parameter name="THIRD_SEARCH_THIRD_PARTY" id="property(SPAC_DT_INTERVINIENTES:C_POSTAL)" property="CPOSTAL"/>
		<ispac:parameter name="THIRD_SEARCH_THIRD_PARTY" id="property(SPAC_DT_INTERVINIENTES:LOCALIDAD)" property="CIUDAD"/>
		<ispac:parameter name="THIRD_SEARCH_THIRD_PARTY" id="property(SPAC_DT_INTERVINIENTES:CAUT)" property="REGIONPAIS"/>
		<ispac:parameter name="THIRD_SEARCH_THIRD_PARTY" id="property(SPAC_DT_INTERVINIENTES:TFNO_FIJO)" property="TFNOFIJO"/>
		<ispac:parameter name="THIRD_SEARCH_THIRD_PARTY" id="property(SPAC_DT_INTERVINIENTES:TFNO_MOVIL)" property="TFNOMOVIL"/>
		<ispac:parameter name="THIRD_SEARCH_THIRD_PARTY" id="property(SPAC_DT_INTERVINIENTES:IDDIRECCIONPOSTAL)" property="IDDIRECCIONPOSTAL"/>
		<ispac:parameter name="THIRD_SEARCH_THIRD_PARTY" id="property(SPAC_DT_INTERVINIENTES:DIRECCIONTELEMATICA)" property="DIRECCIONTELEMATICA"/>
		<ispac:parameter name="THIRD_SEARCH_THIRD_PARTY" id="property(SPAC_DT_INTERVINIENTES:TIPO_DIRECCION)" property="TIPODIRECCIONINTERESADO"/>
		<ispac:parameter name="THIRD_SEARCH_THIRD_PARTY" id="property(TIPODIRECCIONINTERESADO_SPAC_TBL_005:SUSTITUTO)" property="SUSTITUTOTIPODIRECCIONINTERESADO"/>
		<ispac:parameter name="THIRD_SEARCH_THIRD_PARTY" id="JAVASCRIPT" property="return_THIRD_SEARCH_THIRD_PARTY"/>
		
	</ispac:htmlTextImageFrame>

		<ispac:imageframe 
					  id="SEARCH_SPAC_DT_INTERVINIENTES_POSTAL_ADDRESS"
					  target="workframe"
					  action="searchPostalAddressesThirdParty.do" 
					  image="img/icon_barra_3.gif" 
					  showFrame="true"
					  inputField="SPAC_DT_INTERVINIENTES:ID_EXT"
					  jsShowFrame="show_SEARCH_SPAC_DT_INTERVINIENTES_POSTAL_ADDRESS" 
					  >
			<ispac:parameter name="SEARCH_SPAC_DT_INTERVINIENTES_POSTAL_ADDRESS" id="property(SPAC_DT_INTERVINIENTES:IDDIRECCIONPOSTAL)" property="IDDIRECCIONPOSTAL"/>
			<ispac:parameter name="SEARCH_SPAC_DT_INTERVINIENTES_POSTAL_ADDRESS" id="property(SPAC_DT_INTERVINIENTES:DIRNOT)" property="DOMICILIO"/>
			<ispac:parameter name="SEARCH_SPAC_DT_INTERVINIENTES_POSTAL_ADDRESS" id="property(SPAC_DT_INTERVINIENTES:C_POSTAL)" property="CPOSTAL"/>
			<ispac:parameter name="SEARCH_SPAC_DT_INTERVINIENTES_POSTAL_ADDRESS" id="property(SPAC_DT_INTERVINIENTES:LOCALIDAD)" property="CIUDAD"/>
			<ispac:parameter name="SEARCH_SPAC_DT_INTERVINIENTES_POSTAL_ADDRESS" id="property(SPAC_DT_INTERVINIENTES:CAUT)" property="REGIONPAIS"/>
			<ispac:parameter name="SEARCH_SPAC_DT_INTERVINIENTES_POSTAL_ADDRESS" id="property(SPAC_DT_INTERVINIENTES:TFNO_FIJO)" property="TFNOFIJO"/>

		</ispac:imageframe>

		<ispac:imageframe 
					  id="SEARCH_SPAC_DT_INTERVINIENTES_ELECTRONIC_ADDRESS"
					  target="workframe"
					  action="searchElectronicAddressesThirdParty.do" 
					  image="img/icon_barra_2.gif" 
					  showFrame="true"
					  inputField="SPAC_DT_INTERVINIENTES:ID_EXT"
					  jsShowFrame="show_SEARCH_SPAC_DT_INTERVINIENTES_ELECTRONIC_ADDRESS" 
					  >
					  
			<ispac:parameter name="SEARCH_SPAC_DT_INTERVINIENTES_ELECTRONIC_ADDRESS" id="property(SPAC_DT_INTERVINIENTES:DIRECCIONTELEMATICA)" property="DIRECCIONTELEMATICA"/>
		</ispac:imageframe>

		<script language=''JavaScript'' type=''text/javascript''><!--
			function show_SEARCH_SPAC_DT_INTERVINIENTES_POSTAL_ADDRESS(target, action, width, height, msgConfirm, doSubmit, needToConfirm) {
				direccion = document.defaultForm.elements[ ''property(SPAC_DT_INTERVINIENTES:DIRNOT)'' ];
				if (direccion.readOnly) {
					idtitular = document.forms[0].elements[ ''property(SPAC_DT_INTERVINIENTES:ID_EXT)'' ];
					if (idtitular.value != '''') {
						showFrame(target, action, width, height, msgConfirm, doSubmit, needToConfirm);
					}
					else{alert(''<bean:message key="terceros.thirdnotSelected"/>'');}
				}
			}
			
			function show_SEARCH_SPAC_DT_INTERVINIENTES_ELECTRONIC_ADDRESS(target, action, width, height, msgConfirm, doSubmit, needToConfirm) {
				idtitular = document.forms[0].elements[ ''property(SPAC_DT_INTERVINIENTES:ID_EXT)'' ];
				if (idtitular.value != '''') {
					showFrame(target, action, width, height, msgConfirm, doSubmit, needToConfirm);
				}
				else{alert(''<bean:message key="terceros.thirdnotSelected"/>'');}
			}
			//-->
		</script>
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
</c:when>
<c:otherwise>
<div id="label_SPAC_DT_INTERVINIENTES:LBL_VALIDADO" style="position: absolute; top: 5px; left: 10px;"  class="formsTitleB">
<nobr>
<input type="radio" name="validationThirdParty" id="validatedThirdParty" disabled="true" tabindex="101"/>
&nbsp;<bean:write name="defaultForm" property="entityApp.label(SPAC_DT_INTERVINIENTES:LBL_VALIDADO)" /></nobr>
</div>
<div id="label_SPAC_DT_INTERVINIENTES:LBL_NO_VALIDADO" style="position: absolute; top: 5px; left: 100px;"  class="formsTitleB">
<nobr>
<input type="radio" name="validationThirdParty" id="notValidatedThirdParty" checked="checked" disabled="true" tabindex="102"/>
&nbsp;<bean:write name="defaultForm" property="entityApp.label(SPAC_DT_INTERVINIENTES:LBL_NO_VALIDADO)" /></nobr>
</div>
<div id="label_SPAC_DT_INTERVINIENTES:NDOC" style="position: absolute; top: 37px; left: 10px;" class="formsTitleB">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(SPAC_DT_INTERVINIENTES:NDOC)" />:</nobr>
</div>
<div id="data_SPAC_DT_INTERVINIENTES:NDOC" style="position: absolute; top: 34px; left: 175px;">
	<ispac:htmlText property="property(SPAC_DT_INTERVINIENTES:NDOC)" readonly="false" styleClass="input" size="30" maxlength="12" tabindex="103"/>
</div>
<div id="label_SPAC_DT_INTERVINIENTES:NOMBRE" style="position: absolute; top: 67px; left: 10px;" class="formsTitleB">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(SPAC_DT_INTERVINIENTES:NOMBRE)" />:</nobr>
</div>
<div id="data_SPAC_DT_INTERVINIENTES:NOMBRE" style="position: absolute; top: 64px; left: 175px;">
<ispac:htmlTextarea property="property(SPAC_DT_INTERVINIENTES:NOMBRE)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" rows="2" cols="80" tabindex="104">
</ispac:htmlTextarea>
</div>
</c:otherwise>
</c:choose>


<div id="label_SPAC_DT_INTERVINIENTES:SEP_DIRECCIONES" style="position: absolute; top: 103px; left: 10px; width: 200px" class="textbar1">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(SPAC_DT_INTERVINIENTES:SEP_DIRECCIONES)" /></nobr>
<hr class="formbar1"/>
</div>


<div id="label_SPAC_DT_INTERVINIENTES:LBL_VALIDADO_POSTALADDRESS" style="position: absolute; top: 128px; left: 10px;"  class="formsTitleB">
<nobr>
<input type="radio" name="validationPostalAddressThirdParty" id="validatedPostalAddressThirdParty" onclick="javascript: clickValidatedPostalAddressThirdParty();" checked="checked" tabindex="110"/>
&nbsp;<bean:write name="defaultForm" property="entityApp.label(SPAC_DT_INTERVINIENTES:LBL_CONFIRMADA)" /></nobr>
</div>
<div id="label_SPAC_DT_INTERVINIENTES:LBL_NO_VALIDADO_POSTALADDRESS" style="position: absolute; top: 128px; left: 100px;"  class="formsTitleB">
<nobr>
<input type="radio" name="validationPostalAddressThirdParty" id="notValidatedPostalAddressThirdParty" onclick="javascript: clickNotValidatedPostalAddressThirdParty();" tabindex="111"/>
&nbsp;<bean:write name="defaultForm" property="entityApp.label(SPAC_DT_INTERVINIENTES:LBL_LIBRE)" /></nobr>
</div>






<div id="label_SPAC_DT_INTERVINIENTES:ROL" style="position: absolute; top: 371px; left: 10px;" class="formsTitleB">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(SPAC_DT_INTERVINIENTES:ROL)" />:</nobr>
</div>
<div id="data_SPAC_DT_INTERVINIENTES:ROL" style="position: absolute; top: 368px; left: 175px;">
<html:hidden property="property(SPAC_DT_INTERVINIENTES:ROL)" />
<nobr>
<ispac:htmlTextImageFrame property="property(ROL_SPAC_TBL_002:SUSTITUTO)" readonly="true" readonlyTag="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="50" id="SEARCH_SPAC_DT_INTERVINIENTES_ROL" target="workframe" action="selectSubstitute.do?entity=SPAC_TBL_002" image="img/search-mg.gif" titleKeyLink="select.rol" imageDelete="img/borrar.gif" titleKeyImageDelete="title.delete.data.selection" styleClassDeleteLink="tdlink" confirmDeleteKey="msg.delete.data.selection" showDelete="true" showFrame="true" width="640" height="480" tabindex="113">
<ispac:parameter name="SEARCH_SPAC_DT_INTERVINIENTES_ROL" id="property(SPAC_DT_INTERVINIENTES:ROL)" property="VALOR" />
<ispac:parameter name="SEARCH_SPAC_DT_INTERVINIENTES_ROL" id="property(ROL_SPAC_TBL_002:SUSTITUTO)" property="SUSTITUTO" />
</ispac:htmlTextImageFrame>
</nobr>
</div>
<div id="label_SPAC_DT_INTERVINIENTES:DIRNOT" style="position: absolute; top: 153px; left: 10px;" class="formsTitleB">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(SPAC_DT_INTERVINIENTES:DIRNOT)" />:</nobr>
</div>
<div id="data_SPAC_DT_INTERVINIENTES:DIRNOT" style="position: absolute; top: 150px; left: 175px;">
<ispac:htmlTextarea property="property(SPAC_DT_INTERVINIENTES:DIRNOT)" readonly="true" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" rows="2" cols="80" tabindex="105">
</ispac:htmlTextarea>
</div>
<div id="label_SPAC_DT_INTERVINIENTES:C_POSTAL" style="position: absolute; top: 240px; left: 10px;" class="formsTitleB">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(SPAC_DT_INTERVINIENTES:C_POSTAL)" />:</nobr>
</div>
<div id="data_SPAC_DT_INTERVINIENTES:C_POSTAL" style="position: absolute; top: 237px; left: 175px;">
<ispac:htmlText property="property(SPAC_DT_INTERVINIENTES:C_POSTAL)" readonly="true" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="10" maxlength="10" tabindex="108">
</ispac:htmlText>
</div>
<div id="label_SPAC_DT_INTERVINIENTES:LOCALIDAD" style="position: absolute; top: 189px; left: 10px;" class="formsTitleB">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(SPAC_DT_INTERVINIENTES:LOCALIDAD)" />:</nobr>
</div>
<div id="data_SPAC_DT_INTERVINIENTES:LOCALIDAD" style="position: absolute; top: 186px; left: 175px;">
<ispac:htmlText property="property(SPAC_DT_INTERVINIENTES:LOCALIDAD)" readonly="true" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="80" maxlength="150" tabindex="106">
</ispac:htmlText>
</div>
<div id="label_SPAC_DT_INTERVINIENTES:CAUT" style="position: absolute; top: 216px; left: 10px;" class="formsTitleB">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(SPAC_DT_INTERVINIENTES:CAUT)" />:</nobr>
</div>
<div id="data_SPAC_DT_INTERVINIENTES:CAUT" style="position: absolute; top: 213px; left: 175px;">
<ispac:htmlText property="property(SPAC_DT_INTERVINIENTES:CAUT)" readonly="true" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="80" maxlength="150" tabindex="107">
</ispac:htmlText>
</div>
<div id="label_SPAC_DT_INTERVINIENTES:TFNO_FIJO" style="position: absolute; top: 267px; left: 10px;" class="formsTitleB">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(SPAC_DT_INTERVINIENTES:TFNO_FIJO)" />:</nobr>
</div>
<div id="data_SPAC_DT_INTERVINIENTES:TFNO_FIJO" style="position: absolute; top: 264px; left: 175px;">
<ispac:htmlText property="property(SPAC_DT_INTERVINIENTES:TFNO_FIJO)" readonly="true" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="20" maxlength="32" tabindex="109">
</ispac:htmlText>
</div>
<div id="label_SPAC_DT_INTERVINIENTES:TFNO_MOVIL" style="position: absolute; top: 293px; left: 10px;" class="formsTitleB">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(SPAC_DT_INTERVINIENTES:TFNO_MOVIL)" />:</nobr>
</div>
<div id="data_SPAC_DT_INTERVINIENTES:TFNO_MOVIL" style="position: absolute; top: 290px; left: 175px;">
<ispac:htmlText property="property(SPAC_DT_INTERVINIENTES:TFNO_MOVIL)" readonly="true" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="20" maxlength="32" tabindex="110">
</ispac:htmlText>
</div>
<div id="label_SPAC_DT_INTERVINIENTES:TIPO_DIRECCION" style="position: absolute; top: 345px; left: 10px;" class="formsTitleB">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(SPAC_DT_INTERVINIENTES:TIPO_DIRECCION)" />:</nobr>
</div>
<div id="data_SPAC_DT_INTERVINIENTES:TIPO_DIRECCION" style="position: absolute; top: 342px; left: 175px;">
<html:hidden property="property(SPAC_DT_INTERVINIENTES:TIPO_DIRECCION)" />
<nobr>
<ispac:htmlTextImageFrame property="property(TIPO_DIRECCION_SPAC_TBL_005:SUSTITUTO)" readonly="true" readonlyTag="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="35" id="SEARCH_SPAC_DT_INTERVINIENTES_TIPO_DIRECCION" target="workframe" action="selectSubstitute.do?entity=SPAC_TBL_005" image="img/search-mg.gif" titleKeyLink="select.tipoDireccionNotificacion" imageDelete="img/borrar.gif" titleKeyImageDelete="title.delete.data.selection" styleClassDeleteLink="tdlink" confirmDeleteKey="msg.delete.data.selection" showDelete="true" showFrame="true" width="640" height="480" tabindex="112">
<ispac:parameter name="SEARCH_SPAC_DT_INTERVINIENTES_TIPO_DIRECCION" id="property(SPAC_DT_INTERVINIENTES:TIPO_DIRECCION)" property="VALOR" />
<ispac:parameter name="SEARCH_SPAC_DT_INTERVINIENTES_TIPO_DIRECCION" id="property(TIPO_DIRECCION_SPAC_TBL_005:SUSTITUTO)" property="SUSTITUTO" />
</ispac:htmlTextImageFrame>
</nobr>
</div>
<div id="label_SPAC_DT_INTERVINIENTES:DIRECCIONTELEMATICA" style="position: absolute; top: 319px; left: 10px;" class="formsTitleB">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(SPAC_DT_INTERVINIENTES:DIRECCIONTELEMATICA)" />:</nobr>
</div>
<div id="data_SPAC_DT_INTERVINIENTES:DIRECCIONTELEMATICA" style="position: absolute; top: 316px; left: 175px;">
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
, frm_version = 2
WHERE id = 3;



---
--- Cambio en el formulario de búsqueda
---
UPDATE spac_ct_frmbusqueda set frm_bsq='<?xml version=''1.0'' encoding=''ISO-8859-1''?>
<?xml-stylesheet type=''text/xsl'' href=''SearchForm.xsl''?>
<queryform displaywidth=''95%'' defaultSort=''1''>
<!--INICIO ENTIDAD PRINCIPAL DE BUSQUEDA-->
<entity type=''main'' identifier=''1''>
<!--INICIO CUERPO BUSQUEDA-->
	<tablename>spac_expedientes</tablename>
	<description>DATOS DEL EXPEDIENTE</description>
	<field type=''query'' order=''01''>
		<columnname>ID_PCD</columnname>
		<description>Procedimiento</description>
		<datatype>integer</datatype>
		<controltype size=''30''  maxlength=''30'' tipo=''validate'' table=''SPAC_P_PROCEDIMIENTOS'' field=''spac_expedientes:ID_PCD'' label=''NOMBRE''  value=''ID'' clause=''WHERE ESTADO IN (2,3) AND TIPO = 1''>text</controltype>
	</field>
	<field type=''query'' order=''04''>
		<columnname>NUMEXP</columnname>
		<description>Número de Expediente</description>
		<datatype>string</datatype>
		<operators>
		 	<operator><name>&gt;</name></operator>
			<operator><name>&lt;</name></operator>
 			<operator><name>Contiene(Like)</name></operator>
		</operators>
		<controltype size=''30'' maxlength=''30''>text</controltype>
	</field>
	<field type=''query'' order=''05''>
		<columnname>ASUNTO</columnname>
		<description>Asunto</description>
		<datatype>string</datatype>
		<operators>
			<operator><name>=</name></operator>
			<operator><name>&gt;</name></operator>
			<operator><name>&gt;=</name></operator>
			<operator><name>&lt;</name></operator>
			<operator><name>&lt;=</name></operator>
 			<operator><name>Contiene(Like)</name></operator>
		</operators>
		<controltype size=''30'' maxlength=''30''>text</controltype>
	</field>
	<field type=''query'' order=''06''>
		<columnname>NREG</columnname>
		<description>Número Anotación Registro</description>
		<datatype>string</datatype>
		<operators>
			<operator><name>=</name></operator>
			<operator><name>&gt;</name></operator>
			<operator><name>&gt;=</name></operator>
			<operator><name>&lt;</name></operator>
			<operator><name>&lt;=</name></operator>
 			<operator><name>Contiene(Like)</name></operator>
		</operators>
		<controltype size=''16'' maxlength=''16''>text</controltype>
	</field>
	<field type=''query'' order=''07''>
		<columnname>IDENTIDADTITULAR</columnname>
		<description>Interesado Principal</description>
		<datatype>string</datatype>
		<operators>
			<operator><name>=</name></operator>
			<operator><name>&gt;</name></operator>
			<operator><name>&gt;=</name></operator>
			<operator><name>&lt;</name></operator>
			<operator><name>&lt;=</name></operator>
 			<operator><name>Contiene(Like)</name></operator>
		</operators>
		<controltype size=''50'' maxlength=''50''>text</controltype> 
	</field>
	<field type=''query'' order=''08''>
		<columnname>NIFCIFTITULAR</columnname>
		<description>NIF/CIF Interesado</description>
		<datatype>string</datatype>
		<operators>
			<operator><name>=</name></operator>
			<operator><name>&gt;</name></operator>
			<operator><name>&gt;=</name></operator>
			<operator><name>&lt;</name></operator>
			<operator><name>&lt;=</name></operator>
 			<operator><name>Contiene(Like)</name></operator>
		</operators>
		<controltype size=''16'' maxlength=''16''>text</controltype>
	</field>
	<field type=''query'' order=''09''>
		<columnname>FAPERTURA</columnname>
		<description>Fecha Apertura</description>
		<datatype>date</datatype> 
		<controltype size=''22'' maxlength=''22''>text</controltype>
	</field>
	
	<field type=''query'' order=''10''>
		<columnname>ESTADOADM</columnname>
		<description>Estado Administrativo</description>
		<datatype>string</datatype>
		<operators>
			<operator><name>=</name></operator>
			<operator><name>&gt;</name></operator>
			<operator><name>&gt;=</name></operator>
			<operator><name>&lt;</name></operator>
			<operator><name>&lt;=</name></operator>
 			<operator><name>Contiene(Like)</name></operator>
		</operators>
		<controltype size=''20'' maxlength=''20'' tipo=''validate'' table=''SPAC_TBL_004'' field=''spac_expedientes:ESTADOADM'' label=''SUSTITUTO'' value=''VALOR''>text</controltype>
	</field>
	<field type=''query'' order=''11''>     
		<columnname>HAYRECURSO</columnname>
		<description>Recurso(SI/NO)</description>
		<datatype>string</datatype>
		<operators>
			<operator><name>=</name></operator>
			<operator><name>&gt;</name></operator>
			<operator><name>&gt;=</name></operator>
			<operator><name>&lt;</name></operator>
			<operator><name>&lt;=</name></operator>
 			<operator><name>Contiene(Like)</name></operator>
		</operators>
		<controltype size=''2'' maxlength=''2''>text</controltype>
	</field>
	<field type=''query'' order=''12''>
		<columnname>CIUDAD</columnname>
		<description>Ciudad</description>
		<datatype>string</datatype>
		<operators>
			<operator><name>=</name></operator>
			<operator><name>&gt;</name></operator>
			<operator><name>&gt;=</name></operator>
			<operator><name>&lt;</name></operator>
			<operator><name>&lt;=</name></operator>
 			<operator><name>Contiene(Like)</name></operator>
		</operators>
		<controltype size=''50'' maxlength=''50''>text</controltype>
	</field>
		<field type=''query'' order=''13''>
		<columnname>DOMICILIO</columnname>
		<description>Domicilio</description>
		<datatype>string</datatype>
		<operators>
			<operator><name>=</name></operator>
			<operator><name>&gt;</name></operator>
			<operator><name>&gt;=</name></operator>
			<operator><name>&lt;</name></operator>
			<operator><name>&lt;=</name></operator>
 			<operator><name>Contiene(Like)</name></operator>
		</operators>
		<controltype cols=''100'' rows=''5''>textarea</controltype>
	</field>
	
<!--FIN CUERPO BUSQUEDA-->
</entity>
<!--FIN ENTIDAD PRINCIPAL DE BUSQUEDA-->

<!--INICIO SEGUNDA ENTIDAD DE BUSQUEDA-->
<entity type=''secondary'' identifier=''52''>
	<tablename>spac_fases</tablename>
	<bindfield>NUMEXP</bindfield>
	<field type=''query'' order=''02''>
		<columnname>ID_FASE</columnname>
		<description>Fases</description>
		<datatype>stringList</datatype>
        <binding>in (select ID FROM SPAC_P_FASES WHERE ID_CTFASE IN(@VALUES))</binding>
		<controltype height=''75px'' tipo=''list'' table=''SPAC_CT_FASES'' field=''spac_fases:ID_FASE'' label=''NOMBRE''  value=''id''>text</controltype>	
	</field>
</entity>

<entity type=''secondary'' identifier=''51''>
	
	<tablename>spac_tramites</tablename>
		<field type=''query'' order=''03''>
			<columnname>ID_TRAMITE</columnname>
			<description>Trámites</description>
			<datatype>stringList</datatype>
     		<binding>in (select ID FROM SPAC_P_TRAMITES WHERE ID_CTTRAMITE IN(@VALUES))</binding>
			<controltype height=''75px'' tipo=''list'' table=''SPAC_CT_TRAMITES'' field=''spac_tramites:ID_TRAMITE'' label=''NOMBRE''  value=''id''>text</controltype>
		</field>
	<bindfield>NUMEXP</bindfield>
</entity>
<!--FIN SEGUNDA ENTIDAD DE BUSQUEDA-->
<!--INICIO CUERPO RESULTADO-->
<propertyfmt type=''BeanPropertySimpleFmt'' property=''SPAC_FASES.ID'' readOnly=''false'' dataType=''string'' fieldType=''CHECKBOX'' fieldLink=''SPAC_FASES.ID'' />
<propertyfmt type=''BeanPropertySimpleFmt'' property=''SPAC_EXPEDIENTES.NUMEXP'' readOnly=''false'' dataType=''string'' titleKey=''search.numExp'' fieldType=''LINK'' fieldLink=''SPAC_EXPEDIENTES.NUMEXP'' comparator=''ieci.tdw.ispac.ispacweb.comparators.NumexpComparator'' />
<propertyfmt type=''BeanPropertySimpleFmt'' property=''SPAC_EXPEDIENTES.NREG'' readOnly=''false'' dataType=''string'' titleKey=''search.numRegistro'' fieldType=''LIST'' fieldLink=''SPAC_EXPEDIENTES.NREG'' />
<propertyfmt type=''BeanPropertySimpleFmt'' property=''SPAC_EXPEDIENTES.IDENTIDADTITULAR'' readOnly=''false'' dataType=''string'' titleKey=''search.interesado'' fieldType=''LIST'' fieldLink=''SPAC_EXPEDIENTES.IDENTIDADTITULAR'' />
<propertyfmt type=''BeanPropertySimpleFmt'' property=''SPAC_EXPEDIENTES.NIFCIFTITULAR'' readOnly=''false'' dataType=''string'' titleKey=''search.nifInteresado'' fieldType=''LIST'' fieldLink=''SPAC_EXPEDIENTES.NIFCIFTITULAR'' />
<propertyfmt type=''BeanPropertySimpleFmt'' property=''SPAC_EXPEDIENTES.FAPERTURA'' readOnly=''false'' dataType=''date'' titleKey=''search.fechaApertura'' fieldType=''DATE'' fieldLink=''SPAC_EXPEDIENTES.FAPERTURA'' />
<propertyfmt type=''BeanPropertySimpleFmt'' property=''SPAC_EXPEDIENTES.ESTADOADM'' readOnly=''false'' dataType=''string'' titleKey=''search.estadoAdministrativo'' fieldType=''LIST'' fieldLink=''SPAC_EXPEDIENTES.ESTADOADM'' validatetable=''SPAC_TBL_004'' substitute=''SUSTITUTO'' showproperty=''SPAC_TBL_004.SUSTITUTO''  value=''VALOR''/>
<propertyfmt type=''BeanPropertySimpleFmt'' property=''SPAC_EXPEDIENTES.HAYRECURSO'' readOnly=''false'' dataType=''string'' titleKey=''search.recurrido'' fieldType=''LIST'' fieldLink=''SPAC_EXPEDIENTES.HAYRECURSO'' />
<!--FIN CUERPO RESULTADO-->
<!--INICIO ACCIONES-->
<action title=''Cerrar expedientes'' path=''/closeProcess.do'' titleKey=''ispac.action.expedients.close'' />
<action title=''Avanzar fases'' path=''/closeStage.do'' titleKey=''ispac.action.stages.next'' />
<action title=''Retroceder fases'' path=''/redeployPreviousStage.do'' titleKey=''ispac.action.stages.return'' />
<!--FIN ACCIONES-->
</queryform>' where id=1;
