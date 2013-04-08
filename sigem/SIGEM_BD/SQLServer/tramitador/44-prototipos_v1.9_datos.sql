-------------------------
-- DECLARACION DE VARIABLES
-------------------------

DECLARE @sequence_pentidades_id int;


--
-- Formularios
--

INSERT INTO spac_ct_aplicaciones (id, nombre, descripcion, clase, pagina, parametros, formateador, frm_jsp, frm_version, ent_principal_id, ent_principal_nombre, documentos) VALUES (14, 'Listado Licitadores', NULL, 'ieci.tdw.ispac.ispaclib.app.GenericEntityListEntityApp', '/forms/SPAC_DT_INTERVINIENTES/Listado_Licitadores.jsp', '<?xml version=''1.0'' encoding=''ISO-8859-1''?><parameters><entity type=''VALIDATION''><table>SPAC_TBL_005</table><relation type=''FLD''><primary-field>TIPO_DIRECCION</primary-field><secondary-field>VALOR</secondary-field></relation></entity><entity type=''VALIDATION''><table>SPAC_TBL_002</table><relation type=''FLD''><primary-field>ROL</primary-field><secondary-field>VALOR</secondary-field></relation></entity><entity type=''COMPOSITE''><table>OFERTA</table><relation type=''PK''><secondary-field>ID_LICITADOR</secondary-field></relation></entity><entity type=''VALIDATION'' primaryTable=''OFERTA''><table>SPAC_TBL_001</table><relation type=''FLD''><primary-field>UD_PLAZO</primary-field><secondary-field>VALOR</secondary-field></relation></entity></parameters>', '/ispac/digester/thirdsformatter.xml', '<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ispac-util.tld" prefix="ispac" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>

<!-- DEFINICION DE LAS ANCLAS A LOS BLOQUES DE CAMPOS -->
<table border="0" cellspacing="0" cellpadding="0">
<tr>
<td class="select" id="tdlink1" align="center">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(OFERTA:OFERTA_TAB_LICITADORES)" />
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
<div id="dataBlock_SPAC_DT_INTERVINIENTES_TAB_DATOS_PARTICIPANTE" style="position: relative; height: 410px; width: 600px;">
<div id="label_SPAC_DT_INTERVINIENTES:LBL_VALIDADO" style="position: absolute; top: 5px; left: 10px;"  class="formsTitleB">
<nobr>
<input type="radio" name="validationThirdParty" id="validatedThirdParty" onclick="javascript: clickValidatedThirdParty();" checked="checked"/>
&nbsp;<bean:write name="defaultForm" property="entityApp.label(SPAC_DT_INTERVINIENTES:LBL_VALIDADO)" /></nobr>
</div>
<div id="label_SPAC_DT_INTERVINIENTES:LBL_NO_VALIDADO" style="position: absolute; top: 5px; left: 100px;"  class="formsTitleB">
<nobr>
<input type="radio" name="validationThirdParty" id="notValidatedThirdParty" onclick="javascript: clickNotValidatedThirdParty();"/>
&nbsp;<bean:write name="defaultForm" property="entityApp.label(SPAC_DT_INTERVINIENTES:LBL_NO_VALIDADO)" /></nobr>
</div>
<div id="label_SPAC_DT_INTERVINIENTES:ROL" style="position: absolute; top: 321px; left: 10px;" class="formsTitleB">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(SPAC_DT_INTERVINIENTES:ROL)" />:</nobr>
</div>
<div id="data_SPAC_DT_INTERVINIENTES:ROL" style="position: absolute; top: 318px; left: 175px;">
<html:hidden property="property(SPAC_DT_INTERVINIENTES:ROL)" />
<nobr>
<ispac:htmlTextImageFrame property="property(ROL_SPAC_TBL_002:SUSTITUTO)" readonly="true" readonlyTag="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="50" id="SEARCH_SPAC_DT_INTERVINIENTES_ROL" target="workframe" action="selectSubstitute.do?entity=SPAC_TBL_002" image="img/search-mg.gif" titleKeyLink="select.rol" imageDelete="img/borrar.gif" titleKeyImageDelete="title.delete.data.selection" styleClassDeleteLink="tdlink" confirmDeleteKey="msg.delete.data.selection" showDelete="true" showFrame="true" width="640" height="480" >
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
			  	  jsExecuteFrame="selectThirdParty">
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
<ispac:htmlTextarea property="property(SPAC_DT_INTERVINIENTES:NOMBRE)" readonly="true" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" rows="2" cols="80" >
</ispac:htmlTextarea>
</div>
<div id="label_SPAC_DT_INTERVINIENTES:DIRNOT" style="position: absolute; top: 103px; left: 10px;" class="formsTitleB">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(SPAC_DT_INTERVINIENTES:DIRNOT)" />:</nobr>
</div>
<div id="data_SPAC_DT_INTERVINIENTES:DIRNOT" style="position: absolute; top: 100px; left: 175px;">
<ispac:htmlTextarea property="property(SPAC_DT_INTERVINIENTES:DIRNOT)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" rows="2" cols="80" >
</ispac:htmlTextarea>
</div>
<div id="label_SPAC_DT_INTERVINIENTES:C_POSTAL" style="position: absolute; top: 165px; left: 10px;" class="formsTitleB">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(SPAC_DT_INTERVINIENTES:C_POSTAL)" />:</nobr>
</div>
<div id="data_SPAC_DT_INTERVINIENTES:C_POSTAL" style="position: absolute; top: 162px; left: 175px;">
<ispac:htmlText property="property(SPAC_DT_INTERVINIENTES:C_POSTAL)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="10" maxlength="10" >
</ispac:htmlText>
</div>
<div id="label_SPAC_DT_INTERVINIENTES:LOCALIDAD" style="position: absolute; top: 139px; left: 10px;" class="formsTitleB">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(SPAC_DT_INTERVINIENTES:LOCALIDAD)" />:</nobr>
</div>
<div id="data_SPAC_DT_INTERVINIENTES:LOCALIDAD" style="position: absolute; top: 136px; left: 175px;">
<ispac:htmlText property="property(SPAC_DT_INTERVINIENTES:LOCALIDAD)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="80" maxlength="150" >
</ispac:htmlText>
</div>
<div id="label_SPAC_DT_INTERVINIENTES:CAUT" style="position: absolute; top: 191px; left: 10px;" class="formsTitleB">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(SPAC_DT_INTERVINIENTES:CAUT)" />:</nobr>
</div>
<div id="data_SPAC_DT_INTERVINIENTES:CAUT" style="position: absolute; top: 188px; left: 175px;">
<ispac:htmlText property="property(SPAC_DT_INTERVINIENTES:CAUT)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="80" maxlength="150" >
</ispac:htmlText>
</div>
<div id="label_SPAC_DT_INTERVINIENTES:TFNO_FIJO" style="position: absolute; top: 217px; left: 10px;" class="formsTitleB">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(SPAC_DT_INTERVINIENTES:TFNO_FIJO)" />:</nobr>
</div>
<div id="data_SPAC_DT_INTERVINIENTES:TFNO_FIJO" style="position: absolute; top: 214px; left: 175px;">
<ispac:htmlText property="property(SPAC_DT_INTERVINIENTES:TFNO_FIJO)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="20" maxlength="32" >
</ispac:htmlText>
</div>
<div id="label_SPAC_DT_INTERVINIENTES:TFNO_MOVIL" style="position: absolute; top: 243px; left: 10px;" class="formsTitleB">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(SPAC_DT_INTERVINIENTES:TFNO_MOVIL)" />:</nobr>
</div>
<div id="data_SPAC_DT_INTERVINIENTES:TFNO_MOVIL" style="position: absolute; top: 240px; left: 175px;">
<ispac:htmlText property="property(SPAC_DT_INTERVINIENTES:TFNO_MOVIL)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="20" maxlength="32" >
</ispac:htmlText>
</div>
<div id="label_SPAC_DT_INTERVINIENTES:TIPO_DIRECCION" style="position: absolute; top: 295px; left: 10px;" class="formsTitleB">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(SPAC_DT_INTERVINIENTES:TIPO_DIRECCION)" />:</nobr>
</div>
<div id="data_SPAC_DT_INTERVINIENTES:TIPO_DIRECCION" style="position: absolute; top: 292px; left: 175px;">
<html:hidden property="property(SPAC_DT_INTERVINIENTES:TIPO_DIRECCION)" />
<nobr>
<ispac:htmlTextImageFrame property="property(TIPO_DIRECCION_SPAC_TBL_005:SUSTITUTO)" readonly="true" readonlyTag="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="35" id="SEARCH_SPAC_DT_INTERVINIENTES_TIPO_DIRECCION" target="workframe" action="selectSubstitute.do?entity=SPAC_TBL_005" image="img/search-mg.gif" titleKeyLink="select.tipoDireccionNotificacion" imageDelete="img/borrar.gif" titleKeyImageDelete="title.delete.data.selection" styleClassDeleteLink="tdlink" confirmDeleteKey="msg.delete.data.selection" showDelete="true" showFrame="true" width="640" height="480" >
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
<ispac:htmlText property="property(SPAC_DT_INTERVINIENTES:DIRECCIONTELEMATICA)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="80" maxlength="60" >
</ispac:htmlText>
</div>
<div id="label_OFERTA:IMPORTE" style="position: absolute; top: 355px; left: 10px; width: 110px;" class="formsTitleB">
<bean:write name="defaultForm" property="entityApp.label(OFERTA:IMPORTE)" />:</div>
<div id="data_OFERTA:IMPORTE" style="position: absolute; top: 352px; left: 175px;">
<ispac:htmlText property="property(OFERTA:IMPORTE)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="20" maxlength="26" >
</ispac:htmlText>&nbsp;<bean:message key="moneda"/>
</div>
<div id="label_OFERTA:PLAZO" style="position: absolute; top: 381px; left: 10px; width: 110px;" class="formsTitleB">
<bean:write name="defaultForm" property="entityApp.label(OFERTA:PLAZO)" />:</div>
<div id="data_OFERTA:PLAZO" style="position: absolute; top: 378px; left: 175px;">
<ispac:htmlText property="property(OFERTA:PLAZO)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="10" maxlength="10" >
</ispac:htmlText>
</div>


<div id="label_OFERTA:UD_PLAZO" style="position: absolute; top: 381px; left: 261px; width: 110px;" class="formsTitleB">
<bean:write name="defaultForm" property="entityApp.label(OFERTA:UD_PLAZO)" />:</div>
<div id="data_OFERTA:UD_PLAZO" style="position: absolute; top: 376px; left: 379px;">
<nobr>
<html:hidden property="property(OFERTA:UD_PLAZO)" />
<ispac:htmlTextImageFrame property="property(UD_PLAZO_SPAC_TBL_001:SUSTITUTO)" readonly="true" readonlyTag="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="25" id="SEARCH_OFERTA_UD_PLAZO" target="workframe" action="selectSubstitute.do?entity=SPAC_TBL_001" image="img/search-mg.gif" titleKeyLink="title.link.data.selection" imageDelete="img/borrar.gif" titleKeyImageDelete="title.delete.data.selection" styleClassDeleteLink="tdlink" confirmDeleteKey="msg.delete.data.selection" showDelete="true" showFrame="true" width="640" height="480" >
<ispac:parameter name="SEARCH_OFERTA_UD_PLAZO" id="property(OFERTA:UD_PLAZO)" property="VALOR" />
<ispac:parameter name="SEARCH_OFERTA_UD_PLAZO" id="property(UD_PLAZO_SPAC_TBL_001:SUSTITUTO)" property="SUSTITUTO" />
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
</table>', 1, 3, 'SPAC_DT_INTERVINIENTES', 'NO');

INSERT INTO spac_ct_aplicaciones (id, nombre, descripcion, clase, pagina, parametros, formateador, frm_jsp, frm_version, ent_principal_id, ent_principal_nombre, documentos) VALUES (15, 'frm_informe_tecnico', NULL, 'ieci.tdw.ispac.ispaclib.app.GenericSecondaryEntityApp', '/forms/SPAC_DT_TRAMITES/frm_informe_tecnico.jsp', '<?xml version=''1.0'' encoding=''ISO-8859-1''?><parameters><entity type=''VALIDATION''><table>SPAC_TBL_001</table><relation type=''FLD''><primary-field>UPLAZO</primary-field><secondary-field>VALOR</secondary-field></relation></entity></parameters>', NULL, '<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/ispac-util.tld" prefix="ispac"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>

<ispac:rewrite id="imgcalendar" href="img/calendar/"/>
<ispac:rewrite id="jscalendar" href="../scripts/calendar.js"/>
<ispac:rewrite id="buttoncalendar" href="img/calendar/calendarM.gif"/>

<ispac:calendar-config imgDir=''<%= imgcalendar %>'' scriptFile=''<%= jscalendar %>''/>

<table cellspacing="0" cellpadding="0" align="center" width="90%">

	<tr>
		<td height="10px"><img src=''<ispac:rewrite href="img/pixel.gif"/>'' border="0" height="10px"/></td>
	</tr>
	<tr>
		<td class="textbar">
		
			<table cellspacing="0" cellpadding="0" border="0">
			
				<tr>
					<td class="textbar">
						<bean:message key="forms.task.task"/>:
					</td>
					<td>
						<img src=''<ispac:rewrite href="img/pixel.gif"/>'' border="0" width="10px"/></td>
					<td class="text11">
						<bean:write name="defaultForm" property="property(SPAC_DT_TRAMITES:NOMBRE)"/>
					</td>
					<td>
						<img src=''<ispac:rewrite href="img/pixel.gif"/>'' border="0" width="10px"/></td>
					<td class="textbar">
						<bean:message key="forms.task.initiated"/>: 
					</td>
					<td>
						<img src=''<ispac:rewrite href="img/pixel.gif"/>'' border="0" width="10px"/></td>
					<td class="text11">
						<bean:write name="defaultForm" property="property(SPAC_DT_TRAMITES:FECHA_INICIO)"/>
					</td>
				</tr>
				
			</table>
			
		</td>
	</tr>
	<tr>
		<td width="100%" valign="bottom" height="5px" style="font-size:4px; border-bottom:1px solid #5C65A0;">
			&nbsp;
		</td>
	</tr>
	<tr>
		<td valign="top">
		
			<table class="caja" cellspacing="0" cellpadding="0" width="100%">
				<tr>
					<td height="8px"><img src=''<ispac:rewrite href="img/pixel.gif"/>'' border="0" height="8px"/></td>
				</tr>
				<tr>
					<td width="8"><img height="1" width="8px" src=''<ispac:rewrite href="img/pixel.gif"/>''/>
					</td>
					<td colspan="2">
						<table cellspacing="0" cellpadding="0" width="100%">
							<tr valign="top">
								<td height="20" style="width:180px;" class="formsTitleB">
									<nobr><bean:message key="forms.task.responsibleDept"/>:</nobr>
								</td>
								<td height="20">
									<bean:write name="defaultForm" property="entityApp.property(SPAC_DT_TRAMITES:UND_RESP)"/>
								</td>
							</tr>
							
							<logic:notEmpty name="defaultForm" property="entityApp.property(ID_RESP_NAME)">
								<tr valign="top">
									<td height="20" class="formsTitleB">
										<nobr><bean:message key="forms.task.processDept"/>:</nobr>
									</td>
									<td height="20">
										<bean:write name="defaultForm" property="entityApp.property(ID_RESP_NAME)"/>
									</td>
								</tr>
							</logic:notEmpty>

							<tr valign="top">
								<td height="20" class="formsTitleB">
									<nobr><bean:message key="forms.task.remarks"/>:</nobr>
								</td>
								<td height="20">
									<ispac:htmlTextarea property="property(SPAC_DT_TRAMITES:OBSERVACIONES)"
										readonly="false" styleClass="input" rows="2" cols="85" ></ispac:htmlTextarea>
								</td>
							</tr>

						</table>
					</td>
					<td width="8"><img height="1" width="8px" src=''<ispac:rewrite href="img/pixel.gif"/>''/>
					</td>
				</tr>
				<tr>
					<td><img src=''<ispac:rewrite href="img/pixel.gif"/>'' border="0" height="10px"/></td>
				</tr>
				<tr>
					<td width="8"><img height="1" width="8px" src=''<ispac:rewrite href="img/pixel.gif"/>''/>
					</td>
					<td colspan="2">
						<table style="border:0px solid #616EAA;" cellspacing="0" cellpadding="0" width="100%">
							<tr>
								<td height="20" width="25%" class="formsTitleB"><bean:message key="forms.task.term.initDate"/>:<br>
								
									<nobr>
										<ispac:htmlTextCalendar property="property(SPAC_DT_TRAMITES:FECHA_INICIO_PLAZO)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="12" maxlength="10" image=''<%= buttoncalendar %>'' format="dd/mm/yyyy" enablePast="true" />
									</nobr>
																		
								</td>
								<td height="20" width="10%" class="formsTitleB">
								
									<bean:message key="forms.task.term"/>:<br>
									<ispac:htmlText property="property(SPAC_DT_TRAMITES:PLAZO)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="4" maxlength="4" />
									
								</td>
								<td height="20" width="40%" class="formsTitleB"><bean:message key="forms.task.term.uds"/>:<br>

									<html:hidden property="property(SPAC_DT_TRAMITES:UPLAZO)"/>
									
									<ispac:htmlTextImageFrame property="property(UPLAZO_SPAC_TBL_001:SUSTITUTO)"
															  readonly="true"
															  readonlyTag="false"
															  propertyReadonly="readonly"
															  styleClass="input"
															  styleClassReadonly="inputReadOnly"
															  size="34"
														  	  id="TASK_SELECT_TERM_UNITS"
															  target="workframe"
														  	  action="selectSubstitute.do?entity=SPAC_TBL_001" 
														  	  image="img/search-mg.gif" 
														  	  titleKeyLink="forms.tasks.select.uplazo" 
														  	  showFrame="true">
														  	  
                                  		<ispac:parameter name="TASK_SELECT_TERM_UNITS" id="property(SPAC_DT_TRAMITES:UPLAZO)" property="VALOR"/>
              							<ispac:parameter name="TASK_SELECT_TERM_UNITS" id="property(UPLAZO_SPAC_TBL_001:SUSTITUTO)" property="SUSTITUTO"/>
									</ispac:htmlTextImageFrame>
									
								</td>
								<td height="20" width="15%" class="formsTitleB">
									
									<bean:message key="forms.task.dateAlarm"/>:<br>
									<html:text property="property(SPAC_DT_TRAMITES:FECHA_LIMITE)" styleClass="inputReadOnly" size="12" maxlength="12" readonly="true"/>
									
								</td>
								<td width="8"><img height="1" width="8px" src=''<ispac:rewrite href="img/pixel.gif"/>''/>
								</td>
							</tr>
							<tr>
								<td height="6px"><img src=''<ispac:rewrite href="img/pixel.gif"/>'' border="0" height="6px"/></td>
							</tr>
							
						</table>
						
					</td>
					<td width="8"><img height="1" width="8px" src=''<ispac:rewrite href="img/pixel.gif"/>''/>
					</td>
				</tr>
				<tr>
					<td height="4px"><img src=''<ispac:rewrite href="img/pixel.gif"/>'' border="0" height="4px"/></td>
				</tr>
				
			</table>
			
		</td>
	</tr>
	<tr>
		<td height="4px"><img src=''<ispac:rewrite href="img/pixel.gif"/>'' border="0" height="4px"/></td>
	</tr>
<!-- DESDE AQUÍ -->

	<tr>
		<td class="textbar">
		
			<table cellspacing="0" cellpadding="0" border="0">
			
				<tr>
					<td class="textbar">
						<bean:message key="forms.task.task"/>:
					</td>
					<td>
						<img src=''<ispac:rewrite href="img/pixel.gif"/>'' border="0" width="10px"/></td>
					<td class="text11">
						<bean:write name="defaultForm" property="property(SPAC_DT_TRAMITES:NOMBRE)"/>
					</td>
					<td>
						<img src=''<ispac:rewrite href="img/pixel.gif"/>'' border="0" width="10px"/></td>
					<td class="textbar">
						<bean:message key="forms.task.initiated"/>: 
					</td>
					<td>
						<img src=''<ispac:rewrite href="img/pixel.gif"/>'' border="0" width="10px"/></td>
					<td class="text11">
						<bean:write name="defaultForm" property="property(SPAC_DT_TRAMITES:FECHA_INICIO)"/>
					</td>
				</tr>
				
			</table>
			
		</td>
	</tr>
	<tr>
		<td width="100%" valign="bottom" height="5px" style="font-size:4px; border-bottom:1px solid #5C65A0;">
			&nbsp;
		</td>
	</tr>
	<tr>
		<td valign="top">
		
			<table class="caja" cellspacing="0" cellpadding="0" width="100%">
				<tr>
					<td height="8px"><img src=''<ispac:rewrite href="img/pixel.gif"/>'' border="0" height="8px"/></td>
				</tr>
				<tr>
					<td width="8"><img height="1" width="8px" src=''<ispac:rewrite href="img/pixel.gif"/>''/>
					</td>
					<td colspan="2">
						<table cellspacing="0" cellpadding="0" width="100%">
							<tr valign="top">
								<td height="20" style="width:180px;" class="formsTitleB">TITULO
								</td>
								<td height="20">CELDA
								</td>
							</tr>
						</table>
					</td>
					<td width="8"><img height="1" width="8px" src=''<ispac:rewrite href="img/pixel.gif"/>''/>
					</td>
				</tr>
				<tr>
					<td height="4px"><img src=''<ispac:rewrite href="img/pixel.gif"/>'' border="0" height="4px"/></td>
				</tr>
			</table>
		</td>
	</tr>
	<tr>
		<td height="4px"><img src=''<ispac:rewrite href="img/pixel.gif"/>'' border="0" height="4px"/></td>
	</tr>


<!-- HASTA AQUÍ -->	
</table>

<table style="border:0px solid yellow;" cellspacing="0" cellpadding="0" align="center" width="90%">

	<tr>
		<td height="10px"><img src=''<ispac:rewrite href="img/pixel.gif"/>'' border="0" height="10px"/></td>
	</tr>															
	<tr>
		<td class="textbar">
		
			<table cellspacing="0" cellpadding="0" border="0" width="100%">
			
				<tr>
					<td class="textbar">
						<bean:message key="forms.tasks.attached.documents"/>:
					</td>
				</tr>
				
			</table>
			
		</td>
	</tr>
	<tr>
		<td height="4px"><img src=''<ispac:rewrite href="img/pixel.gif"/>'' border="0" height="4px"/></td>
	</tr>
	
</table>
', 1, 7, 'SPAC_DT_TRAMITES', 'NO');

INSERT INTO spac_ct_aplicaciones (id, nombre, descripcion, clase, pagina, parametros, formateador, frm_jsp, frm_version, ent_principal_id, ent_principal_nombre, documentos) VALUES (16, 'Informe Técnico', NULL, 'ieci.tdw.ispac.ispaclib.app.GenericSecondaryEntityApp', '/forms/tramites/informeTecnicoForm.jsp', '<?xml version=''1.0'' encoding=''ISO-8859-1''?><parameters><entity type=''VALIDATION''><table>SPAC_TBL_001</table><relation type=''FLD''><primary-field>UPLAZO</primary-field><secondary-field>VALOR</secondary-field></relation></entity></parameters>', NULL, '<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/ispac-util.tld" prefix="ispac"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>

<ispac:rewrite id="imgcalendar" href="img/calendar/"/>
<ispac:rewrite id="jscalendar" href="../scripts/calendar.js"/>
<ispac:rewrite id="buttoncalendar" href="img/calendar/calendarM.gif"/>

<ispac:calendar-config imgDir=''<%= imgcalendar %>'' scriptFile=''<%= jscalendar %>''/>

<table cellspacing="0" cellpadding="0" align="center" width="90%">

	<tr>
		<td height="10px"><img src=''<ispac:rewrite href="img/pixel.gif"/>'' border="0" height="10px"/></td>
	</tr>
	<tr>
		<td class="textbar">
		
			<table cellspacing="0" cellpadding="0" border="0">
			
				<tr>
					<td class="textbar">
						<bean:message key="forms.task.task"/>:
					</td>
					<td>
						<img src=''<ispac:rewrite href="img/pixel.gif"/>'' border="0" width="10px"/></td>
					<td class="text11">
						<bean:write name="defaultForm" property="property(SPAC_DT_TRAMITES:NOMBRE)"/>
					</td>
					<td>
						<img src=''<ispac:rewrite href="img/pixel.gif"/>'' border="0" width="10px"/></td>
					<td class="textbar">
						<bean:message key="forms.task.initiated"/>: 
					</td>
					<td>
						<img src=''<ispac:rewrite href="img/pixel.gif"/>'' border="0" width="10px"/></td>
					<td class="text11">
						<bean:write name="defaultForm" property="property(SPAC_DT_TRAMITES:FECHA_INICIO)"/>
					</td>
				</tr>
				
			</table>
			
		</td>
	</tr>
	<tr>
		<td width="100%" valign="bottom" height="5px" style="font-size:4px; border-bottom:1px solid #5C65A0;">
			&nbsp;
		</td>
	</tr>
	<tr>
		<td valign="top">
		
			<table class="caja" cellspacing="0" cellpadding="0" width="100%">
				<tr>
					<td height="8px"><img src=''<ispac:rewrite href="img/pixel.gif"/>'' border="0" height="8px"/></td>
				</tr>
				<tr>
					<td width="8"><img height="1" width="8px" src=''<ispac:rewrite href="img/pixel.gif"/>''/>
					</td>
					<td colspan="2">
						<table cellspacing="0" cellpadding="0" width="100%">
							<tr valign="top">
								<td height="20" style="width:180px;" class="formsTitleB">
									<nobr><bean:message key="forms.task.responsibleDept"/>:</nobr>
								</td>
								<td height="20">
									<bean:write name="defaultForm" property="entityApp.property(SPAC_DT_TRAMITES:UND_RESP)"/>
								</td>
							</tr>
							
							<logic:notEmpty name="defaultForm" property="entityApp.property(ID_RESP_NAME)">
								<tr valign="top">
									<td height="20" class="formsTitleB">
										<nobr><bean:message key="forms.task.processDept"/>:</nobr>
									</td>
									<td height="20">
										<bean:write name="defaultForm" property="entityApp.property(ID_RESP_NAME)"/>
									</td>
								</tr>
							</logic:notEmpty>

							<tr valign="top">
								<td height="20" class="formsTitleB">
									<nobr><bean:message key="forms.task.remarks"/>:</nobr>
								</td>
								<td height="20">
									<ispac:htmlTextarea property="property(SPAC_DT_TRAMITES:OBSERVACIONES)"
										readonly="false" styleClass="input" rows="2" cols="85" ></ispac:htmlTextarea>
								</td>
							</tr>

						</table>
					</td>
					<td width="8"><img height="1" width="8px" src=''<ispac:rewrite href="img/pixel.gif"/>''/>
					</td>
				</tr>
				<tr>
					<td><img src=''<ispac:rewrite href="img/pixel.gif"/>'' border="0" height="10px"/></td>
				</tr>
				<tr>
					<td width="8"><img height="1" width="8px" src=''<ispac:rewrite href="img/pixel.gif"/>''/>
					</td>
					<td colspan="2">
						<table style="border:0px solid #616EAA;" cellspacing="0" cellpadding="0" width="100%">
							<tr>
								<td height="20" width="25%" class="formsTitleB"><bean:message key="forms.task.term.initDate"/>:<br>
								
									<nobr>
										<ispac:htmlTextCalendar property="property(SPAC_DT_TRAMITES:FECHA_INICIO_PLAZO)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="12" maxlength="10" image=''<%= buttoncalendar %>'' format="dd/mm/yyyy" enablePast="true" />
									</nobr>
																		
								</td>
								<td height="20" width="10%" class="formsTitleB">
								
									<bean:message key="forms.task.term"/>:<br>
									<ispac:htmlText property="property(SPAC_DT_TRAMITES:PLAZO)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="4" maxlength="4" />
									
								</td>
								<td height="20" width="40%" class="formsTitleB"><bean:message key="forms.task.term.uds"/>:<br>

									<html:hidden property="property(SPAC_DT_TRAMITES:UPLAZO)"/>
									
									<ispac:htmlTextImageFrame property="property(UPLAZO_SPAC_TBL_001:SUSTITUTO)"
															  readonly="true"
															  readonlyTag="false"
															  propertyReadonly="readonly"
															  styleClass="input"
															  styleClassReadonly="inputReadOnly"
															  size="34"
														  	  id="TASK_SELECT_TERM_UNITS"
															  target="workframe"
														  	  action="selectSubstitute.do?entity=SPAC_TBL_001" 
														  	  image="img/search-mg.gif" 
														  	  titleKeyLink="forms.tasks.select.uplazo" 
														  	  showFrame="true">
														  	  
                                  		<ispac:parameter name="TASK_SELECT_TERM_UNITS" id="property(SPAC_DT_TRAMITES:UPLAZO)" property="VALOR"/>
              							<ispac:parameter name="TASK_SELECT_TERM_UNITS" id="property(UPLAZO_SPAC_TBL_001:SUSTITUTO)" property="SUSTITUTO"/>
									</ispac:htmlTextImageFrame>
									
								</td>
								<td height="20" width="15%" class="formsTitleB">
									
									<bean:message key="forms.task.dateAlarm"/>:<br>
									<html:text property="property(SPAC_DT_TRAMITES:FECHA_LIMITE)" styleClass="inputReadOnly" size="12" maxlength="12" readonly="true"/>
									
								</td>
								<td width="8"><img height="1" width="8px" src=''<ispac:rewrite href="img/pixel.gif"/>''/>
								</td>
							</tr>
							<tr>
								<td height="6px"><img src=''<ispac:rewrite href="img/pixel.gif"/>'' border="0" height="6px"/></td>
							</tr>
							
						</table>
						
					</td>
					<td width="8"><img height="1" width="8px" src=''<ispac:rewrite href="img/pixel.gif"/>''/>
					</td>
				</tr>
				<tr>
					<td height="4px"><img src=''<ispac:rewrite href="img/pixel.gif"/>'' border="0" height="4px"/></td>
				</tr>
				
			</table>
			
		</td>
	</tr>
	<tr>
		<td height="4px"><img src=''<ispac:rewrite href="img/pixel.gif"/>'' border="0" height="4px"/></td>
	</tr>
<!-- DESDE AQUÍ -->

	<tr>
		<td class="textbar">
		
			<table cellspacing="0" cellpadding="0" border="0">
			
				<tr>
					<td class="textbar">
						<bean:message key="forms.task.task"/>:
					</td>
					<td>
						<img src=''<ispac:rewrite href="img/pixel.gif"/>'' border="0" width="10px"/></td>
					<td class="text11">
						<bean:write name="defaultForm" property="property(SPAC_DT_TRAMITES:NOMBRE)"/>
					</td>
					<td>
						<img src=''<ispac:rewrite href="img/pixel.gif"/>'' border="0" width="10px"/></td>
					<td class="textbar">
						<bean:message key="forms.task.initiated"/>: 
					</td>
					<td>
						<img src=''<ispac:rewrite href="img/pixel.gif"/>'' border="0" width="10px"/></td>
					<td class="text11">
						<bean:write name="defaultForm" property="property(SPAC_DT_TRAMITES:FECHA_INICIO)"/>
					</td>
				</tr>
				
			</table>
			
		</td>
	</tr>
	<tr>
		<td width="100%" valign="bottom" height="5px" style="font-size:4px; border-bottom:1px solid #5C65A0;">
			&nbsp;
		</td>
	</tr>
	<tr>
		<td valign="top">
		
			<table class="caja" cellspacing="0" cellpadding="0" width="100%">
				<tr>
					<td height="8px"><img src=''<ispac:rewrite href="img/pixel.gif"/>'' border="0" height="8px"/></td>
				</tr>
				<tr>
					<td width="8"><img height="1" width="8px" src=''<ispac:rewrite href="img/pixel.gif"/>''/>
					</td>
					<td colspan="2">
						<table cellspacing="0" cellpadding="0" width="100%">
							<tr valign="top">
								<td height="20" style="width:180px;" class="formsTitleB">TITULO
								</td>
								<td height="20">CELDA
								</td>
							</tr>
						</table>
					</td>
					<td width="8"><img height="1" width="8px" src=''<ispac:rewrite href="img/pixel.gif"/>''/>
					</td>
				</tr>
				<tr>
					<td height="4px"><img src=''<ispac:rewrite href="img/pixel.gif"/>'' border="0" height="4px"/></td>
				</tr>
			</table>
		</td>
	</tr>
	<tr>
		<td height="4px"><img src=''<ispac:rewrite href="img/pixel.gif"/>'' border="0" height="4px"/></td>
	</tr>


<!-- HASTA AQUÍ -->	
</table>

<table style="border:0px solid yellow;" cellspacing="0" cellpadding="0" align="center" width="90%">

	<tr>
		<td height="10px"><img src=''<ispac:rewrite href="img/pixel.gif"/>'' border="0" height="10px"/></td>
	</tr>															
	<tr>
		<td class="textbar">
		
			<table cellspacing="0" cellpadding="0" border="0" width="100%">
			
				<tr>
					<td class="textbar">
						<bean:message key="forms.tasks.attached.documents"/>:
					</td>
				</tr>
				
			</table>
			
		</td>
	</tr>
	<tr>
		<td height="4px"><img src=''<ispac:rewrite href="img/pixel.gif"/>'' border="0" height="4px"/></td>
	</tr>
	
</table>

', 1, 7, 'SPAC_DT_TRAMITES', 'NO');

INSERT INTO spac_ct_aplicaciones (id, nombre, descripcion, clase, pagina, parametros, formateador, frm_jsp, frm_version, ent_principal_id, ent_principal_nombre, documentos) VALUES (17, 'VBForm', NULL, 'ieci.tdw.ispac.ispaclib.app.GenericSecondaryEntityApp', '/forms/SPAC_DT_TRAMITES/VBForm.jsp', '<?xml version=''1.0'' encoding=''ISO-8859-1''?><parameters><entity type="VALIDATION"><table>SPAC_TBL_001</table><relation type="FLD"><primary-field>UPLAZO</primary-field><secondary-field>VALOR</secondary-field></relation></entity><entity type="COMPOSITE"><table>VISTO_BUENO</table><relation type="PK"><secondary-field>ID_TRAM_EXP</secondary-field></relation></entity></parameters>', NULL, '<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/ispac-util.tld" prefix="ispac"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>

<ispac:rewrite id="imgcalendar" href="img/calendar/"/>
<ispac:rewrite id="jscalendar" href="../scripts/calendar.js"/>
<ispac:rewrite id="buttoncalendar" href="img/calendar/calendarM.gif"/>

<ispac:calendar-config imgDir=''<%= imgcalendar %>'' scriptFile=''<%= jscalendar %>''/>

<table cellspacing="0" cellpadding="0" align="center" width="90%">

	<tr>
		<td height="10px"><img src=''<ispac:rewrite href="img/pixel.gif"/>'' border="0" height="10px"/></td>
	</tr>
	<tr>
		<td class="textbar">
		
			<table cellspacing="0" cellpadding="0" border="0">
			
				<tr>
					<td class="textbar">
						<bean:message key="forms.task.task"/>:
					</td>
					<td>
						<img src=''<ispac:rewrite href="img/pixel.gif"/>'' border="0" width="10px"/></td>
					<td class="text11">
						<bean:write name="defaultForm" property="property(SPAC_DT_TRAMITES:NOMBRE)"/>
					</td>
					<td>
						<img src=''<ispac:rewrite href="img/pixel.gif"/>'' border="0" width="10px"/></td>
					<td class="textbar">
						<bean:message key="forms.task.initiated"/>: 
					</td>
					<td>
						<img src=''<ispac:rewrite href="img/pixel.gif"/>'' border="0" width="10px"/></td>
					<td class="text11">
						<bean:write name="defaultForm" property="property(SPAC_DT_TRAMITES:FECHA_INICIO)"/>
					</td>
				</tr>
				
			</table>
			
		</td>
	</tr>
	<tr>
		<td width="100%" valign="bottom" height="5px" style="font-size:4px; border-bottom:1px solid #5C65A0;">
			&nbsp;
		</td>
	</tr>
	<tr>
		<td valign="top">
		
			<table class="caja" cellspacing="0" cellpadding="0" width="100%">
				<tr>
					<td height="8px"><img src=''<ispac:rewrite href="img/pixel.gif"/>'' border="0" height="8px"/></td>
				</tr>
				<tr>
					<td width="8"><img height="1" width="8px" src=''<ispac:rewrite href="img/pixel.gif"/>''/>
					</td>
					<td colspan="2">
						<table cellspacing="0" cellpadding="0" width="100%">
							<tr valign="top">
								<td height="20" style="width:180px;" class="formsTitleB">
									<nobr><bean:message key="forms.task.responsibleDept"/>:</nobr>
								</td>
								<td height="20">
									<bean:write name="defaultForm" property="entityApp.property(SPAC_DT_TRAMITES:UND_RESP)"/>
								</td>
							</tr>
							
							<logic:notEmpty name="defaultForm" property="entityApp.property(ID_RESP_NAME)">
								<tr valign="top">
									<td height="20" class="formsTitleB">
										<nobr><bean:message key="forms.task.processDept"/>:</nobr>
									</td>
									<td height="20">
										<bean:write name="defaultForm" property="entityApp.property(ID_RESP_NAME)"/>
									</td>
								</tr>
							</logic:notEmpty>

							<tr valign="top">
								<td height="20" class="formsTitleB">
									<nobr><bean:message key="forms.task.remarks"/>:</nobr>
								</td>
								<td height="20">
									<ispac:htmlTextarea property="property(SPAC_DT_TRAMITES:OBSERVACIONES)"
										readonly="false" styleClass="input" rows="2" cols="85" ></ispac:htmlTextarea>
								</td>
							</tr>

						</table>
					</td>
					<td width="8"><img height="1" width="8px" src=''<ispac:rewrite href="img/pixel.gif"/>''/>
					</td>
				</tr>
				<tr>
					<td><img src=''<ispac:rewrite href="img/pixel.gif"/>'' border="0" height="10px"/></td>
				</tr>
				<tr>
					<td width="8"><img height="1" width="8px" src=''<ispac:rewrite href="img/pixel.gif"/>''/>
					</td>
					<td colspan="2">
						<table style="border:0px solid #616EAA;" cellspacing="0" cellpadding="0" width="100%">
							<tr>
								<td height="20" width="25%" class="formsTitleB"><bean:message key="forms.task.term.initDate"/>:<br>
								
									<nobr>
										<ispac:htmlTextCalendar property="property(SPAC_DT_TRAMITES:FECHA_INICIO_PLAZO)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="12" maxlength="10" image=''<%= buttoncalendar %>'' format="dd/mm/yyyy" enablePast="true" />
									</nobr>
																		
								</td>
								<td height="20" width="10%" class="formsTitleB">
								
									<bean:message key="forms.task.term"/>:<br>
									<ispac:htmlText property="property(SPAC_DT_TRAMITES:PLAZO)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="4" maxlength="4" />
									
								</td>
								<td height="20" width="40%" class="formsTitleB"><bean:message key="forms.task.term.uds"/>:<br>

									<html:hidden property="property(SPAC_DT_TRAMITES:UPLAZO)"/>
									
									<ispac:htmlTextImageFrame property="property(UPLAZO_SPAC_TBL_001:SUSTITUTO)"
															  readonly="true"
															  readonlyTag="false"
															  propertyReadonly="readonly"
															  styleClass="input"
															  styleClassReadonly="inputReadOnly"
															  size="34"
														  	  id="TASK_SELECT_TERM_UNITS"
															  target="workframe"
														  	  action="selectSubstitute.do?entity=SPAC_TBL_001" 
														  	  image="img/search-mg.gif" 
														  	  titleKeyLink="forms.tasks.select.uplazo" 
														  	  showFrame="true">
														  	  
                                  		<ispac:parameter name="TASK_SELECT_TERM_UNITS" id="property(SPAC_DT_TRAMITES:UPLAZO)" property="VALOR"/>
              							<ispac:parameter name="TASK_SELECT_TERM_UNITS" id="property(UPLAZO_SPAC_TBL_001:SUSTITUTO)" property="SUSTITUTO"/>
									</ispac:htmlTextImageFrame>
									
								</td>
								<td height="20" width="15%" class="formsTitleB">
									
									<bean:message key="forms.task.dateAlarm"/>:<br>
									<html:text property="property(SPAC_DT_TRAMITES:FECHA_LIMITE)" styleClass="inputReadOnly" size="12" maxlength="12" readonly="true"/>
									
								</td>
								<td width="8"><img height="1" width="8px" src=''<ispac:rewrite href="img/pixel.gif"/>''/>
								</td>
							</tr>
							<tr>
								<td height="6px"><img src=''<ispac:rewrite href="img/pixel.gif"/>'' border="0" height="6px"/></td>
							</tr>
							
						</table>
						
					</td>
					<td width="8"><img height="1" width="8px" src=''<ispac:rewrite href="img/pixel.gif"/>''/>
					</td>
				</tr>
				<tr>
					<td height="4px"><img src=''<ispac:rewrite href="img/pixel.gif"/>'' border="0" height="4px"/></td>
				</tr>
				
			</table>
			
		</td>
	</tr>
	<tr>
		<td height="4px"><img src=''<ispac:rewrite href="img/pixel.gif"/>'' border="0" height="4px"/></td>
	</tr>
<!-- DESDE AQUÍ -->
<tr>
<td class="textbar">
		
			<table cellspacing="0" cellpadding="0" border="0">
			
				<tr>
					<td class="textbar" >
						<bean:write name="defaultForm" property="entityApp.label(VISTO_BUENO:VB)" />:&nbsp;
						<bean:write name="defaultForm" property="property(VISTO_BUENO:TASK_NAME)"/>
					</td>
					
				</tr>
				
			</table>
			
		</td>
</tr>
	<tr>
		<td class="textbar">
<table width="100%" border="0" class="formtable" cellspacing="0" cellpadding="0">
<tr>
<td><img height="8" src=''<ispac:rewrite href="img/pixel.gif"/>''/></td>
</tr>
<tr>

<td>

<!-- BLOQUE1 DE CAMPOS -->
<div style="display:block">
<div id="dataBlock_VISTO_BUENO" style="position: relative; height: 135px; width: 600px">
<div id="label_VISTO_BUENO:TASK_NAME" style="position: absolute; top: 10px; left: 10px; width: 110px;" class="formsTitleB">
<bean:write name="defaultForm" property="entityApp.label(VISTO_BUENO:TASK_NAME)" />:</div>
<div id="data_VISTO_BUENO:TASK_NAME" style="position: absolute; top: 10px; left: 130px;">
<ispac:htmlText property="property(VISTO_BUENO:TASK_NAME)" readonly="true" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="80" maxlength="250" >
</ispac:htmlText>
</div>
<div id="label_VISTO_BUENO:VB" style="position: absolute; top: 35px; left: 10px; width: 110px;" class="formsTitleB">
<bean:write name="defaultForm" property="entityApp.label(VISTO_BUENO:VB)" />:</div>
<div id="data_VISTO_BUENO:VB" style="position: absolute; top: 35px; left: 130px;">
<nobr>
<ispac:htmlTextImageFrame property="property(VISTO_BUENO:VB)" readonly="true" readonlyTag="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="80" id="SEARCH_VISTO_BUENO_VB" target="workframe" action="selectValue.do?entity=SPAC_TBL_009" image="img/search-mg.gif" titleKeyLink="title.link.data.selection" imageDelete="img/borrar.gif" titleKeyImageDelete="title.delete.data.selection" styleClassDeleteLink="tdlink" confirmDeleteKey="msg.delete.data.selection" showDelete="true" showFrame="true" width="640" height="480" >
<ispac:parameter name="SEARCH_VISTO_BUENO_VB" id="property(VISTO_BUENO:VB)" property="VALOR" />
</ispac:htmlTextImageFrame>
</nobr>
</div>

<div id="label_VISTO_BUENO:VB" style="position: absolute; top: 65px; left: 10px; width: 110px;" class="formsTitleB">
<bean:write name="defaultForm" property="entityApp.label(VISTO_BUENO:OBSERVACIONES)" />:</div>
<div id="data_VISTO_BUENO:VB" style="position: absolute; top: 65px; left: 130px;">
<nobr>
<ispac:htmlTextarea property="property(VISTO_BUENO:OBSERVACIONES)"
										readonly="false" styleClass="input" rows="5" cols="85" ></ispac:htmlTextarea>
</nobr>
</div>

</div>
</div>
</td>
</tr>

</table>
			
			
		</td>
	</tr>
	
	
		
		


<!-- HASTA AQUÍ -->	
</table>


', 1, 7, 'SPAC_DT_TRAMITES', 'NO');

INSERT INTO spac_ct_aplicaciones (id, nombre, descripcion, clase, pagina, parametros, formateador, frm_jsp, frm_version, ent_principal_id, ent_principal_nombre, documentos) VALUES (18, 'Cambio de titular', 'Formulario autogenerado al crear la entidad CAMB_TITULAR_LA', 'ieci.tdw.ispac.ispaclib.app.GenericSecondaryEntityApp', '/forms/cambiotitular/cambioTitularForm.jsp', NULL, NULL, '<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ispac-util.tld" prefix="ispac" %>

<!-- DEFINICION DE LAS ANCLAS A LOS BLOQUES DE CAMPOS -->
<table border="0" cellspacing="0" cellpadding="0">
<tr>
<td class="select" id="tdlink1" align="center">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(CAMB_TITULAR_LA:CAMB_TITULAR_LA)" />
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
<div id="dataBlock_CAMB_TITULAR_LA" style="position: relative; height: 210px; width: 600px">
<div id="label_CAMB_TITULAR_LA:DENOMINACION" style="position: absolute;  top: 10px; left: 10px; width: 110px;" class="formsTitleB">
<bean:write name="defaultForm" property="entityApp.label(CAMB_TITULAR_LA:DENOMINACION)" />:</div>
<div id="data_CAMB_TITULAR_LA:DENOMINACION" style="position: absolute; top: 10px; left: 130px;">
<ispac:htmlText property="property(CAMB_TITULAR_LA:DENOMINACION)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="80" maxlength="254" >
</ispac:htmlText>
</div>
<div id="label_CAMB_TITULAR_LA:ACTIVIDAD" style="position: absolute; top: 35px; left: 10px; width: 110px;" class="formsTitleB">
<bean:write name="defaultForm" property="entityApp.label(CAMB_TITULAR_LA:ACTIVIDAD)" />:</div>
<div id="data_CAMB_TITULAR_LA:ACTIVIDAD" style="position: absolute; top: 35px; left: 130px;">
<ispac:htmlText property="property(CAMB_TITULAR_LA:ACTIVIDAD)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="80" maxlength="254" >
</ispac:htmlText>
</div>
<div id="label_CAMB_TITULAR_LA:EMPLAZAMIENTO" style="position: absolute; top: 60px;  left: 10px; width: 110px;" class="formsTitleB">
<bean:write name="defaultForm" property="entityApp.label(CAMB_TITULAR_LA:EMPLAZAMIENTO)" />:</div>
<div id="data_CAMB_TITULAR_LA:EMPLAZAMIENTO" style="position: absolute; top: 60px;  left: 130px;">
<ispac:htmlTextarea property="property(CAMB_TITULAR_LA:EMPLAZAMIENTO)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" rows="2" cols="80" >
</ispac:htmlTextarea>
</div>
<div id="label_CAMB_TITULAR_LA:FECHA_LICENCIA" style="position: absolute; top: 110px; left: 10px; width: 110px;" class="formsTitleB">
<bean:write name="defaultForm" property="entityApp.label(CAMB_TITULAR_LA:FECHA_LICENCIA)" />:</div>
<div id="data_CAMB_TITULAR_LA:FECHA_LICENCIA" style="position: absolute; top: 110px; left: 130px;">
<nobr>
<ispac:htmlTextCalendar property="property(CAMB_TITULAR_LA:FECHA_LICENCIA)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="14" maxlength="10" image=''<%= buttoncalendar %>'' format="dd/mm/yyyy" enablePast="true" >
</ispac:htmlTextCalendar>
</nobr>
</div>
<div id="label_CAMB_TITULAR_LA:FECHA_APROBACION" style="position: absolute; top: 135px; left: 10px; width: 110px;" class="formsTitleB">
<bean:write name="defaultForm" property="entityApp.label(CAMB_TITULAR_LA:FECHA_APROBACION)" />:</div>
<div id="data_CAMB_TITULAR_LA:FECHA_APROBACION" style="position: absolute; top: 135px; left: 130px;">
<nobr>
<ispac:htmlTextCalendar property="property(CAMB_TITULAR_LA:FECHA_APROBACION)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="14" maxlength="10" image=''<%= buttoncalendar %>'' format="dd/mm/yyyy" enablePast="true" >
</ispac:htmlTextCalendar>
</nobr>
</div>
<div id="label_CAMB_TITULAR_LA:TASAS" style="position: absolute; top: 160px; left: 10px; width: 110px;" class="formsTitleB">
<bean:write name="defaultForm" property="entityApp.label(CAMB_TITULAR_LA:TASAS)" />:</div>
<div id="data_CAMB_TITULAR_LA:TASAS" style="position: absolute; top: 160px; left: 130px;">
<ispac:htmlText property="property(CAMB_TITULAR_LA:TASAS)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="15" maxlength="18" >
</ispac:htmlText>
</div>
<div style="position: absolute; top: 160px; left: 225px; width: 110px;" class="formsTitleB">
&#8364;</div>
</div>
</div>
</td>
</tr>
<tr>
<td height="15"><img src=''<ispac:rewrite href="img/pixel.gif"/>''/></td>
</tr>
</table>
', 1, 206, 'CAMB_TITULAR_LA', 'NO');

INSERT INTO spac_ct_aplicaciones (id, nombre, descripcion, clase, pagina, parametros, formateador, frm_jsp, frm_version, ent_principal_id, ent_principal_nombre, documentos) VALUES (19, 'frm_fase_instruccion_cerrado', 'Formulario para la fase de instrucción con datos de fase de inicio en sólo lectura', 'ieci.tdw.ispac.ispaclib.app.GenericSecondaryEntityApp', '/forms/OBRA_MENOR/frm_fase_instruccion.jsp', '<?xml version=''1.0'' encoding=''ISO-8859-1''?><parameters><entity type=''VALIDATION''><table>OBRA_MENOR</table><relation type=''FLD''><primary-field>CLASIFICACION_SUELO</primary-field><secondary-field>VALOR</secondary-field></relation></entity><entity type=''VALIDATION''><table/><relation type=''FLD''><primary-field>CUALIFICACION_SUELO</primary-field><secondary-field>VALOR</secondary-field></relation></entity></parameters>', NULL, '<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ispac-util.tld" prefix="ispac" %>

<!-- DEFINICION DE LAS ANCLAS A LOS BLOQUES DE CAMPOS -->
<table border="0" cellspacing="0" cellpadding="0">
<tr>
<td class="select" id="tdlink1" align="center">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(OBRA_MENOR:OBRA_MENOR)" />
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
<div id="dataBlock_OBRA_MENOR" style="position: relative; height: 480px; width: 600px;">
<div id="label_OBRA_MENOR:OBRA_SOLICITADA" style="position: absolute; top: 6px; left: 10px; width: 110px;" class="formsTitleB">
<bean:write name="defaultForm" property="entityApp.label(OBRA_MENOR:OBRA_SOLICITADA)" />:</div>
<div id="data_OBRA_MENOR:OBRA_SOLICITADA" style="position: absolute; top: 6px; left: 130px;">
<ispac:htmlTextarea property="property(OBRA_MENOR:OBRA_SOLICITADA)" readonly="true" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" rows="5" cols="80" >
</ispac:htmlTextarea>
</div>
<div id="label_OBRA_MENOR:SITUACION_OBRA" style="position: absolute; top: 85px; left: 10px; width: 110px;" class="formsTitleB">
<bean:write name="defaultForm" property="entityApp.label(OBRA_MENOR:SITUACION_OBRA)" />:</div>
<div id="data_OBRA_MENOR:SITUACION_OBRA" style="position: absolute; top: 85px; left: 130px; height: 19px;">
<ispac:htmlText property="property(OBRA_MENOR:SITUACION_OBRA)" readonly="true" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="80" maxlength="500" >
</ispac:htmlText>
</div>
<div id="label_OBRA_MENOR:PROMOTOR" style="position: absolute; top: 196px; left: 10px; width: 110px;" class="formsTitleB">
<bean:write name="defaultForm" property="entityApp.label(OBRA_MENOR:PROMOTOR)" />:</div>
<div id="data_OBRA_MENOR:PROMOTOR" style="position: absolute; top: 194px; left: 130px;">
<ispac:htmlText property="property(OBRA_MENOR:PROMOTOR)" readonly="true" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="80" maxlength="100" >
</ispac:htmlText>
</div>
<div id="label_OBRA_MENOR:DIRECTOR_OBRA" style="position: absolute; top: 217px; left: 10px; width: 110px;" class="formsTitleB">
<bean:write name="defaultForm" property="entityApp.label(OBRA_MENOR:DIRECTOR_OBRA)" />:</div>
<div id="data_OBRA_MENOR:DIRECTOR_OBRA" style="position: absolute; top: 217px; left: 130px;">
<ispac:htmlText property="property(OBRA_MENOR:DIRECTOR_OBRA)" readonly="true" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="80" maxlength="100" >
</ispac:htmlText>
</div>
<div id="label_OBRA_MENOR:DIRECTOR_EJECUCION" style="position: absolute; top: 241px; left: 10px; width: 110px;" class="formsTitleB">
<bean:write name="defaultForm" property="entityApp.label(OBRA_MENOR:DIRECTOR_EJECUCION)" />:</div>
<div id="data_OBRA_MENOR:DIRECTOR_EJECUCION" style="position: absolute; top: 241px; left: 130px;">
<ispac:htmlText property="property(OBRA_MENOR:DIRECTOR_EJECUCION)" readonly="true" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="80" maxlength="100" >
</ispac:htmlText>
</div>
<div id="label_OBRA_MENOR:PRESUPUESTO_PROMOTOR" style="position: absolute; top: 169px; left: 10px; width: 110px;" class="formsTitleB">
<bean:write name="defaultForm" property="entityApp.label(OBRA_MENOR:PRESUPUESTO_PROMOTOR)" />:</div>
<div id="data_OBRA_MENOR:PRESUPUESTO_PROMOTOR" style="position: absolute; top: 168px; left: 130px;">
<ispac:htmlText property="property(OBRA_MENOR:PRESUPUESTO_PROMOTOR)" readonly="true" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="15" maxlength="18" >
</ispac:htmlText>
</div>
<div style="position: absolute; top: 169px; left: 225px; width: 110px;" class="formsTitleB">
&nbsp;&#8364;</div>
<div id="label_OBRA_MENOR:PRESUPUESTO_TÉCNICO_MUNICIPAL" style="position: absolute; top: 287px; left: 10px; width: 110px;" class="formsTitleB">
<bean:write name="defaultForm" property="entityApp.label(OBRA_MENOR:PRESUPUESTO_TÉCNICO_MUNICIPAL)" />:</div>
<div id="data_OBRA_MENOR:PRESUPUESTO_TÉCNICO_MUNICIPAL" style="position: absolute; top: 286px; left: 130px; width: 78px;">
<ispac:htmlText property="property(OBRA_MENOR:PRESUPUESTO_TÉCNICO_MUNICIPAL)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="15" maxlength="18" >
</ispac:htmlText>
</div>
<div style="position: absolute; top: 286px; left: 225px; width: 110px;" class="formsTitleB">
&nbsp;&#8364;</div>
<div id="label_OBRA_MENOR:AUTOR_PROYECTO" style="position: absolute; top: 142px; left: 10px; width: 110px;" class="formsTitleB">
<bean:write name="defaultForm" property="entityApp.label(OBRA_MENOR:AUTOR_PROYECTO)" />:</div>
<div id="data_OBRA_MENOR:AUTOR_PROYECTO" style="position: absolute; top: 141px; left: 130px;">
<ispac:htmlText property="property(OBRA_MENOR:AUTOR_PROYECTO)" readonly="true" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="80" maxlength="100" >
</ispac:htmlText>
</div>
<div id="label_OBRA_MENOR:USO_OBRA" style="position: absolute; top: 115px; left: 10px; width: 110px;" class="formsTitleB">
<bean:write name="defaultForm" property="entityApp.label(OBRA_MENOR:USO_OBRA)" />:</div>
<div id="data_OBRA_MENOR:USO_OBRA" style="position: absolute; top: 114px; left: 130px;">
<ispac:htmlText property="property(OBRA_MENOR:USO_OBRA)" readonly="true" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="80" maxlength="200" >
</ispac:htmlText>
</div>
<div id="label_OBRA_MENOR:CLASIFICACION_SUELO" style="position: absolute; top: 317px; left: 10px; width: 110px;" class="formsTitleB">
<bean:write name="defaultForm" property="entityApp.label(OBRA_MENOR:CLASIFICACION_SUELO)" />:</div>
<div id="data_OBRA_MENOR:CLASIFICACION_SUELO" style="position: absolute; top: 316px; left: 130px;">
<nobr>
<html:hidden property="property(OBRA_MENOR:CLASIFICACION_SUELO)" />
<ispac:htmlTextImageFrame property="property(CLASIFICACION_SUELO_VLDTBL_CLASIF_SUELO:SUSTITUTO)" readonly="true" readonlyTag="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="80" id="SEARCH_OBRA_MENOR_CLASIFICACION_SUELO" target="workframe" action="selectSubstitute.do?entity=OBRA_MENOR" image="img/search-mg.gif" titleKeyLink="title.link.data.selection" imageDelete="img/borrar.gif" titleKeyImageDelete="title.delete.data.selection" styleClassDeleteLink="tdlink" confirmDeleteKey="msg.delete.data.selection" showDelete="true" showFrame="true" width="640" height="480" >
<ispac:parameter name="SEARCH_OBRA_MENOR_CLASIFICACION_SUELO" id="property(OBRA_MENOR:CLASIFICACION_SUELO)" property="VALOR" />
<ispac:parameter name="SEARCH_OBRA_MENOR_CLASIFICACION_SUELO" id="property(CLASIFICACION_SUELO_VLDTBL_CLASIF_SUELO:SUSTITUTO)" property="SUSTITUTO" />
</ispac:htmlTextImageFrame>
</nobr>
</div>
<div id="label_OBRA_MENOR:CUALIFICACION_SUELO" style="position: absolute; top: 342px; left: 10px; width: 110px;" class="formsTitleB">
<bean:write name="defaultForm" property="entityApp.label(OBRA_MENOR:CUALIFICACION_SUELO)" />:</div>
<div id="data_OBRA_MENOR:CUALIFICACION_SUELO" style="position: absolute; top: 341px; left: 130px;">
<nobr>
<html:hidden property="property(OBRA_MENOR:CUALIFICACION_SUELO)" />
<ispac:htmlTextImageFrame property="property(CUALIFICACION_SUELO_VLDTBL_CALIF_SUELO:SUSTITUTO)" readonly="true" readonlyTag="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="80" id="SEARCH_OBRA_MENOR_CUALIFICACION_SUELO" target="workframe" action="selectSubstitute.do?entity=" image="img/search-mg.gif" titleKeyLink="title.link.data.selection" imageDelete="img/borrar.gif" titleKeyImageDelete="title.delete.data.selection" styleClassDeleteLink="tdlink" confirmDeleteKey="msg.delete.data.selection" showDelete="true" showFrame="true" width="640" height="480" >
<ispac:parameter name="SEARCH_OBRA_MENOR_CUALIFICACION_SUELO" id="property(OBRA_MENOR:CUALIFICACION_SUELO)" property="VALOR" />
<ispac:parameter name="SEARCH_OBRA_MENOR_CUALIFICACION_SUELO" id="property(CUALIFICACION_SUELO_VLDTBL_CALIF_SUELO:SUSTITUTO)" property="SUSTITUTO" />
</ispac:htmlTextImageFrame>
</nobr>
</div>
<div id="label_OBRA_MENOR:IMPORTE_TASAS" style="position: absolute; top: 389px; left: 10px; width: 110px;" class="formsTitleB">
<bean:write name="defaultForm" property="entityApp.label(OBRA_MENOR:IMPORTE_TASAS)" />:</div>
<div id="data_OBRA_MENOR:IMPORTE_TASAS" style="position: absolute; top: 388px; left: 130px; width: 78px;">
<ispac:htmlText property="property(OBRA_MENOR:IMPORTE_TASAS)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="15" maxlength="18" >
</ispac:htmlText>
</div>
<div style="position: absolute; top: 388px; left: 225px; width: 110px;" class="formsTitleB">
&nbsp;&#8364;</div>
<div id="label_OBRA_MENOR:IMPORTE_ICIO" style="position: absolute; top: 414px; left: 10px; width: 110px;" class="formsTitleB">
<bean:write name="defaultForm" property="entityApp.label(OBRA_MENOR:IMPORTE_ICIO)" />:</div>
<div id="data_OBRA_MENOR:IMPORTE_ICIO" style="position: absolute; top: 413px; left: 130px;">
<ispac:htmlText property="property(OBRA_MENOR:IMPORTE_ICIO)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="15" maxlength="18" >
</ispac:htmlText>
</div>
<div style="position: absolute; top: 413px; left: 225px; width: 110px;" class="formsTitleB">
&nbsp;&#8364;</div>
<div id="label_OBRA_MENOR:TOTAL_LIQUIDACION" style="position: absolute; top: 439px; left: 10px; width: 110px;" class="formsTitleB">
<bean:write name="defaultForm" property="entityApp.label(OBRA_MENOR:TOTAL_LIQUIDACION)" />:</div>
<div id="data_OBRA_MENOR:TOTAL_LIQUIDACION" style="position: absolute; top: 438px; left: 130px;">
<ispac:htmlText property="property(OBRA_MENOR:TOTAL_LIQUIDACION)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="15" maxlength="18" >
</ispac:htmlText>
</div>
<div style="position: absolute; top: 438px; left: 225px; width: 110px;" class="formsTitleB">
&nbsp;&#8364;</div>
</div>
</div>
</td>
</tr>
<tr>
<td height="15"><img src=''<ispac:rewrite href="img/pixel.gif"/>''/></td>
</tr>
</table>
', 1, 210, 'OBRA_MENOR', 'NO');

INSERT INTO spac_ct_aplicaciones (id, nombre, descripcion, clase, pagina, parametros, formateador, frm_jsp, frm_version, ent_principal_id, ent_principal_nombre, documentos) VALUES (20, 'frm_fase_inicio', 'Formulario para la fase de inicio', 'ieci.tdw.ispac.ispaclib.app.GenericSecondaryEntityApp', '/forms/OBRA_MENOR/frm_fase_inicio.jsp', '<?xml version=''1.0'' encoding=''ISO-8859-1''?><parameters><entity type=''VALIDATION''><table>OBRA_MENOR</table><relation type=''FLD''><primary-field>CLASIFICACION_SUELO</primary-field><secondary-field>VALOR</secondary-field></relation></entity><entity type=''VALIDATION''><table/><relation type=''FLD''><primary-field>CUALIFICACION_SUELO</primary-field><secondary-field>VALOR</secondary-field></relation></entity></parameters>', NULL, '<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ispac-util.tld" prefix="ispac" %>

<!-- DEFINICION DE LAS ANCLAS A LOS BLOQUES DE CAMPOS -->
<table border="0" cellspacing="0" cellpadding="0">
<tr>
<td class="select" id="tdlink1" align="center">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(OBRA_MENOR:OBRA_MENOR)" />
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
<div id="dataBlock_OBRA_MENOR" style="position: relative; height: 280px; width: 600px;">
<div id="label_OBRA_MENOR:OBRA_SOLICITADA" style="position: absolute; top: 6px; left: 10px; width: 110px;" class="formsTitleB">
<bean:write name="defaultForm" property="entityApp.label(OBRA_MENOR:OBRA_SOLICITADA)" />:</div>
<div id="data_OBRA_MENOR:OBRA_SOLICITADA" style="position: absolute; top: 6px; left: 130px;">
<ispac:htmlTextarea property="property(OBRA_MENOR:OBRA_SOLICITADA)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" rows="5" cols="80" >
</ispac:htmlTextarea>
</div>
<div id="label_OBRA_MENOR:SITUACION_OBRA" style="position: absolute; top: 85px; left: 10px; width: 110px;" class="formsTitleB">
<bean:write name="defaultForm" property="entityApp.label(OBRA_MENOR:SITUACION_OBRA)" />:</div>
<div id="data_OBRA_MENOR:SITUACION_OBRA" style="position: absolute; top: 85px; left: 130px; height: 19px;">
<ispac:htmlText property="property(OBRA_MENOR:SITUACION_OBRA)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="80" maxlength="500" >
</ispac:htmlText>
</div>
<div id="label_OBRA_MENOR:PROMOTOR" style="position: absolute; top: 196px; left: 10px; width: 110px;" class="formsTitleB">
<bean:write name="defaultForm" property="entityApp.label(OBRA_MENOR:PROMOTOR)" />:</div>
<div id="data_OBRA_MENOR:PROMOTOR" style="position: absolute; top: 194px; left: 130px;">
<ispac:htmlText property="property(OBRA_MENOR:PROMOTOR)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="80" maxlength="100" >
</ispac:htmlText>
</div>
<div id="label_OBRA_MENOR:DIRECTOR_OBRA" style="position: absolute; top: 217px; left: 10px; width: 110px;" class="formsTitleB">
<bean:write name="defaultForm" property="entityApp.label(OBRA_MENOR:DIRECTOR_OBRA)" />:</div>
<div id="data_OBRA_MENOR:DIRECTOR_OBRA" style="position: absolute; top: 217px; left: 130px;">
<ispac:htmlText property="property(OBRA_MENOR:DIRECTOR_OBRA)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="80" maxlength="100" >
</ispac:htmlText>
</div>
<div id="label_OBRA_MENOR:DIRECTOR_EJECUCION" style="position: absolute; top: 241px; left: 10px; width: 110px;" class="formsTitleB">
<bean:write name="defaultForm" property="entityApp.label(OBRA_MENOR:DIRECTOR_EJECUCION)" />:</div>
<div id="data_OBRA_MENOR:DIRECTOR_EJECUCION" style="position: absolute; top: 241px; left: 130px;">
<ispac:htmlText property="property(OBRA_MENOR:DIRECTOR_EJECUCION)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="80" maxlength="100" >
</ispac:htmlText>
</div>
<div id="label_OBRA_MENOR:PRESUPUESTO_PROMOTOR" style="position: absolute; top: 169px; left: 10px; width: 110px;" class="formsTitleB">
<bean:write name="defaultForm" property="entityApp.label(OBRA_MENOR:PRESUPUESTO_PROMOTOR)" />:</div>
<div id="data_OBRA_MENOR:PRESUPUESTO_PROMOTOR" style="position: absolute; top: 168px; left: 130px;">
<ispac:htmlText property="property(OBRA_MENOR:PRESUPUESTO_PROMOTOR)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="15" maxlength="18" >
</ispac:htmlText>
</div>
<div style="position: absolute; top: 169px; left: 225px; width: 110px;" class="formsTitleB">
&nbsp;&#8364;</div>
<div id="label_OBRA_MENOR:AUTOR_PROYECTO" style="position: absolute; top: 142px; left: 10px; width: 110px;" class="formsTitleB">
<bean:write name="defaultForm" property="entityApp.label(OBRA_MENOR:AUTOR_PROYECTO)" />:</div>
<div id="data_OBRA_MENOR:AUTOR_PROYECTO" style="position: absolute; top: 141px; left: 130px;">
<ispac:htmlText property="property(OBRA_MENOR:AUTOR_PROYECTO)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="80" maxlength="100" >
</ispac:htmlText>
</div>
<div id="label_OBRA_MENOR:USO_OBRA" style="position: absolute; top: 115px; left: 10px; width: 110px;" class="formsTitleB">
<bean:write name="defaultForm" property="entityApp.label(OBRA_MENOR:USO_OBRA)" />:</div>
<div id="data_OBRA_MENOR:USO_OBRA" style="position: absolute; top: 114px; left: 130px;">
<ispac:htmlText property="property(OBRA_MENOR:USO_OBRA)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="80" maxlength="200" >
</ispac:htmlText>
</div>
</div>
</div>
</td>
</tr>
<tr>
<td height="15"><img src=''<ispac:rewrite href="img/pixel.gif"/>''/></td>
</tr>
</table>
', 1, 210, 'OBRA_MENOR', 'NO');

INSERT INTO spac_ct_aplicaciones (id, nombre, descripcion, clase, pagina, parametros, formateador, frm_jsp, frm_version, ent_principal_id, ent_principal_nombre, documentos) VALUES (21, 'FrmCrEnt_OBRA_MENOR', 'Formulario autogenerado al crear la entidad OBRA_MENOR', 'ieci.tdw.ispac.ispaclib.app.GenericSecondaryEntityApp', '/forms/OBRA_MENOR/FrmCrEnt_OBRA_MENOR.jsp', '<?xml version=''1.0'' encoding=''ISO-8859-1''?><parameters><entity type=''VALIDATION''><table>OBRA_MENOR</table><relation type=''FLD''><primary-field>CLASIFICACION_SUELO</primary-field><secondary-field>VALOR</secondary-field></relation></entity><entity type=''VALIDATION''><table/><relation type=''FLD''><primary-field>CUALIFICACION_SUELO</primary-field><secondary-field>VALOR</secondary-field></relation></entity></parameters>', NULL, '<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ispac-util.tld" prefix="ispac" %>

<!-- DEFINICION DE LAS ANCLAS A LOS BLOQUES DE CAMPOS -->
<table border="0" cellspacing="0" cellpadding="0">
<tr>
<td class="select" id="tdlink1" align="center">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(OBRA_MENOR:OBRA_MENOR)" />
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
<div id="dataBlock_OBRA_MENOR" style="position: relative; height: 480px; width: 600px;">
<div id="label_OBRA_MENOR:OBRA_SOLICITADA" style="position: absolute; top: 6px; left: 10px; width: 110px;" class="formsTitleB">
<bean:write name="defaultForm" property="entityApp.label(OBRA_MENOR:OBRA_SOLICITADA)" />:</div>
<div id="data_OBRA_MENOR:OBRA_SOLICITADA" style="position: absolute; top: 6px; left: 130px;">
<ispac:htmlTextarea property="property(OBRA_MENOR:OBRA_SOLICITADA)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" rows="5" cols="80" >
</ispac:htmlTextarea>
</div>
<div id="label_OBRA_MENOR:SITUACION_OBRA" style="position: absolute; top: 85px; left: 10px; width: 110px;" class="formsTitleB">
<bean:write name="defaultForm" property="entityApp.label(OBRA_MENOR:SITUACION_OBRA)" />:</div>
<div id="data_OBRA_MENOR:SITUACION_OBRA" style="position: absolute; top: 85px; left: 130px; height: 19px;">
<ispac:htmlText property="property(OBRA_MENOR:SITUACION_OBRA)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="80" maxlength="500" >
</ispac:htmlText>
</div>
<div id="label_OBRA_MENOR:PROMOTOR" style="position: absolute; top: 196px; left: 10px; width: 110px;" class="formsTitleB">
<bean:write name="defaultForm" property="entityApp.label(OBRA_MENOR:PROMOTOR)" />:</div>
<div id="data_OBRA_MENOR:PROMOTOR" style="position: absolute; top: 194px; left: 130px;">
<ispac:htmlText property="property(OBRA_MENOR:PROMOTOR)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="80" maxlength="100" >
</ispac:htmlText>
</div>
<div id="label_OBRA_MENOR:DIRECTOR_OBRA" style="position: absolute; top: 217px; left: 10px; width: 110px;" class="formsTitleB">
<bean:write name="defaultForm" property="entityApp.label(OBRA_MENOR:DIRECTOR_OBRA)" />:</div>
<div id="data_OBRA_MENOR:DIRECTOR_OBRA" style="position: absolute; top: 217px; left: 130px;">
<ispac:htmlText property="property(OBRA_MENOR:DIRECTOR_OBRA)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="80" maxlength="100" >
</ispac:htmlText>
</div>
<div id="label_OBRA_MENOR:DIRECTOR_EJECUCION" style="position: absolute; top: 241px; left: 10px; width: 110px;" class="formsTitleB">
<bean:write name="defaultForm" property="entityApp.label(OBRA_MENOR:DIRECTOR_EJECUCION)" />:</div>
<div id="data_OBRA_MENOR:DIRECTOR_EJECUCION" style="position: absolute; top: 241px; left: 130px;">
<ispac:htmlText property="property(OBRA_MENOR:DIRECTOR_EJECUCION)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="80" maxlength="100" >
</ispac:htmlText>
</div>
<div id="label_OBRA_MENOR:PRESUPUESTO_PROMOTOR" style="position: absolute; top: 169px; left: 10px; width: 110px;" class="formsTitleB">
<bean:write name="defaultForm" property="entityApp.label(OBRA_MENOR:PRESUPUESTO_PROMOTOR)" />:</div>
<div id="data_OBRA_MENOR:PRESUPUESTO_PROMOTOR" style="position: absolute; top: 168px; left: 130px;">
<ispac:htmlText property="property(OBRA_MENOR:PRESUPUESTO_PROMOTOR)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="15" maxlength="18" >
</ispac:htmlText>
</div>
<div style="position: absolute; top: 169px; left: 225px; width: 110px;" class="formsTitleB">
&nbsp;&#8364;</div>
<div id="label_OBRA_MENOR:PRESUPUESTO_TÉCNICO_MUNICIPAL" style="position: absolute; top: 287px; left: 10px; width: 110px;" class="formsTitleB">
<bean:write name="defaultForm" property="entityApp.label(OBRA_MENOR:PRESUPUESTO_TÉCNICO_MUNICIPAL)" />:</div>
<div id="data_OBRA_MENOR:PRESUPUESTO_TÉCNICO_MUNICIPAL" style="position: absolute; top: 286px; left: 130px; width: 78px;">
<ispac:htmlText property="property(OBRA_MENOR:PRESUPUESTO_TÉCNICO_MUNICIPAL)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="15" maxlength="18" >
</ispac:htmlText>
</div>
<div style="position: absolute; top: 286px; left: 225px; width: 110px;" class="formsTitleB">
&nbsp;&#8364;</div>
<div id="label_OBRA_MENOR:AUTOR_PROYECTO" style="position: absolute; top: 142px; left: 10px; width: 110px;" class="formsTitleB">
<bean:write name="defaultForm" property="entityApp.label(OBRA_MENOR:AUTOR_PROYECTO)" />:</div>
<div id="data_OBRA_MENOR:AUTOR_PROYECTO" style="position: absolute; top: 141px; left: 130px;">
<ispac:htmlText property="property(OBRA_MENOR:AUTOR_PROYECTO)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="80" maxlength="100" >
</ispac:htmlText>
</div>
<div id="label_OBRA_MENOR:USO_OBRA" style="position: absolute; top: 115px; left: 10px; width: 110px;" class="formsTitleB">
<bean:write name="defaultForm" property="entityApp.label(OBRA_MENOR:USO_OBRA)" />:</div>
<div id="data_OBRA_MENOR:USO_OBRA" style="position: absolute; top: 114px; left: 130px;">
<ispac:htmlText property="property(OBRA_MENOR:USO_OBRA)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="80" maxlength="200" >
</ispac:htmlText>
</div>
<div id="label_OBRA_MENOR:CLASIFICACION_SUELO" style="position: absolute; top: 317px; left: 10px; width: 110px;" class="formsTitleB">
<bean:write name="defaultForm" property="entityApp.label(OBRA_MENOR:CLASIFICACION_SUELO)" />:</div>
<div id="data_OBRA_MENOR:CLASIFICACION_SUELO" style="position: absolute; top: 316px; left: 130px;">
<nobr>
<html:hidden property="property(OBRA_MENOR:CLASIFICACION_SUELO)" />
<ispac:htmlTextImageFrame property="property(CLASIFICACION_SUELO_VLDTBL_CLASIF_SUELO:SUSTITUTO)" readonly="true" readonlyTag="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="80" id="SEARCH_OBRA_MENOR_CLASIFICACION_SUELO" target="workframe" action="selectSubstitute.do?entity=OBRA_MENOR" image="img/search-mg.gif" titleKeyLink="title.link.data.selection" imageDelete="img/borrar.gif" titleKeyImageDelete="title.delete.data.selection" styleClassDeleteLink="tdlink" confirmDeleteKey="msg.delete.data.selection" showDelete="true" showFrame="true" width="640" height="480" >
<ispac:parameter name="SEARCH_OBRA_MENOR_CLASIFICACION_SUELO" id="property(OBRA_MENOR:CLASIFICACION_SUELO)" property="VALOR" />
<ispac:parameter name="SEARCH_OBRA_MENOR_CLASIFICACION_SUELO" id="property(CLASIFICACION_SUELO_VLDTBL_CLASIF_SUELO:SUSTITUTO)" property="SUSTITUTO" />
</ispac:htmlTextImageFrame>
</nobr>
</div>
<div id="label_OBRA_MENOR:CUALIFICACION_SUELO" style="position: absolute; top: 342px; left: 10px; width: 110px;" class="formsTitleB">
<bean:write name="defaultForm" property="entityApp.label(OBRA_MENOR:CUALIFICACION_SUELO)" />:</div>
<div id="data_OBRA_MENOR:CUALIFICACION_SUELO" style="position: absolute; top: 341px; left: 130px;">
<nobr>
<html:hidden property="property(OBRA_MENOR:CUALIFICACION_SUELO)" />
<ispac:htmlTextImageFrame property="property(CUALIFICACION_SUELO_VLDTBL_CALIF_SUELO:SUSTITUTO)" readonly="true" readonlyTag="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="80" id="SEARCH_OBRA_MENOR_CUALIFICACION_SUELO" target="workframe" action="selectSubstitute.do?entity=" image="img/search-mg.gif" titleKeyLink="title.link.data.selection" imageDelete="img/borrar.gif" titleKeyImageDelete="title.delete.data.selection" styleClassDeleteLink="tdlink" confirmDeleteKey="msg.delete.data.selection" showDelete="true" showFrame="true" width="640" height="480" >
<ispac:parameter name="SEARCH_OBRA_MENOR_CUALIFICACION_SUELO" id="property(OBRA_MENOR:CUALIFICACION_SUELO)" property="VALOR" />
<ispac:parameter name="SEARCH_OBRA_MENOR_CUALIFICACION_SUELO" id="property(CUALIFICACION_SUELO_VLDTBL_CALIF_SUELO:SUSTITUTO)" property="SUSTITUTO" />
</ispac:htmlTextImageFrame>
</nobr>
</div>
<div id="label_OBRA_MENOR:IMPORTE_TASAS" style="position: absolute; top: 389px; left: 10px; width: 110px;" class="formsTitleB">
<bean:write name="defaultForm" property="entityApp.label(OBRA_MENOR:IMPORTE_TASAS)" />:</div>
<div id="data_OBRA_MENOR:IMPORTE_TASAS" style="position: absolute; top: 388px; left: 130px; width: 78px;">
<ispac:htmlText property="property(OBRA_MENOR:IMPORTE_TASAS)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="15" maxlength="18" >
</ispac:htmlText>
</div>
<div style="position: absolute; top: 388px; left: 225px; width: 110px;" class="formsTitleB">
&nbsp;&#8364;</div>
<div id="label_OBRA_MENOR:IMPORTE_ICIO" style="position: absolute; top: 414px; left: 10px; width: 110px;" class="formsTitleB">
<bean:write name="defaultForm" property="entityApp.label(OBRA_MENOR:IMPORTE_ICIO)" />:</div>
<div id="data_OBRA_MENOR:IMPORTE_ICIO" style="position: absolute; top: 413px; left: 130px;">
<ispac:htmlText property="property(OBRA_MENOR:IMPORTE_ICIO)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="15" maxlength="18" >
</ispac:htmlText>
</div>
<div style="position: absolute; top: 413px; left: 225px; width: 110px;" class="formsTitleB">
&nbsp;&#8364;</div>
<div id="label_OBRA_MENOR:TOTAL_LIQUIDACION" style="position: absolute; top: 439px; left: 10px; width: 110px;" class="formsTitleB">
<bean:write name="defaultForm" property="entityApp.label(OBRA_MENOR:TOTAL_LIQUIDACION)" />:</div>
<div id="data_OBRA_MENOR:TOTAL_LIQUIDACION" style="position: absolute; top: 438px; left: 130px;">
<ispac:htmlText property="property(OBRA_MENOR:TOTAL_LIQUIDACION)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="15" maxlength="18" >
</ispac:htmlText>
</div>
<div style="position: absolute; top: 438px; left: 225px; width: 110px;" class="formsTitleB">
&nbsp;&#8364;</div>
</div>
</div>
</td>
</tr>
<tr>
<td height="15"><img src=''<ispac:rewrite href="img/pixel.gif"/>''/></td>
</tr>
</table>
', 1, 210, 'OBRA_MENOR', 'NO');

INSERT INTO spac_ct_aplicaciones (id, nombre, descripcion, clase, pagina, parametros, formateador, frm_jsp, frm_version, ent_principal_id, ent_principal_nombre, documentos) VALUES (22, 'frm_cert_urb_inicio', 'Formulario Certificado Urbanístico (inicio)', 'ieci.tdw.ispac.ispaclib.app.GenericSecondaryEntityApp', '/forms/CERT_URBANISTICO/frm_cert_urb_inicio.jsp', '<?xml version=''1.0'' encoding=''ISO-8859-1''?><parameters><entity type=''VALIDATION''><table>TIPO_TECNICO</table><relation type=''FLD''><primary-field>TECNICO_CARGO</primary-field><secondary-field>VALOR</secondary-field></relation></entity></parameters>', NULL, '<html>
<head>
<style type="text/css">
.input{color: #003b73; background-color: #FFFFFF; font-family: Verdana, Arial, Helvetica, Sans-serif; font-size: 10px; border-color: #616EAA; border-style: solid; border-width: 1px; }
.inputReadOnly{color: #606060; background-color: #EEEEEE; font-family: Verdana, Arial, Helvetica, Sans-serif; font-size: 10px; border-color: #5C65A0; border-style: solid; border-width: 1px;}
.select {border: #D3D5DD 1px solid; border-bottom: none; padding: 3px 6px 3px 6px; color: #5C65A0; background-color: #D7EDFA; font-family: Verdana, Arial, Helvetica, Sans-serif; font-size: 11px; font-weight: bold;}
.dataBlock {border:1px solid #D3D5DD;}
.formsTitleB{color: #5C65A0; font-family: Verdana, Arial, Helvetica, Sans-serif; font-size: 10px; font-weight: bold;}
body {font-family: Verdana, Arial, Helvetica, Sans-serif; font-size: 10px;}
</style>
</head>
<body>
<table border="0px" cellpadding="0px" cellspacing="0px"><tr><td class="select" align="center">Certificado urbanístico</td></tr></table>
<div id="dataBlock_CERT_URBANISTICO" style="position: relative; height: 160px; width: 600px;" class="dataBlock">
<div id="label_CERT_URBANISTICO:FECHA_EMISION" style="position: absolute; top: 10px; left: 10px; width: 110px;" class="formsTitleB">
Fecha de emisión:</div>
<div id="data_CERT_URBANISTICO:FECHA_EMISION" style="position: absolute; top: 10px; left: 150px;">
	<INPUT name="property(CERT_URBANISTICO:FECHA_EMISION)" size="14" value="Fecha de emisión" class="input"></div>
<div id="label_CERT_URBANISTICO:SITUACION" style="position: absolute; top: 40px; left: 10px; width: 110px;" class="formsTitleB">
Situación finca/parcela/edificio:</div>
<div id="data_CERT_URBANISTICO:SITUACION" style="position: absolute; top: 40px; left: 150px;">
	<TEXTAREA name="property(CERT_URBANISTICO:SITUACION)" cols="77" rows="3" class="input">Situación finca/parcela/edificio</textarea></div>
<div id="label_CERT_URBANISTICO:TECNICO_NOMBRE" style="position: absolute; top: 100px; left: 10px; width: 110px;" class="formsTitleB">
Técnico:</div>
<div id="data_CERT_URBANISTICO:TECNICO_NOMBRE" style="position: absolute; top: 100px; left: 150px;">
	<INPUT name="property(CERT_URBANISTICO:TECNICO_NOMBRE)" size="80" value="Técnico" class="input"></div>
<div id="label_CERT_URBANISTICO:TECNICO_CARGO" style="position: absolute; top: 130px; left: 10px; width: 110px;" class="formsTitleB">
Cargo:</div>
<div id="data_CERT_URBANISTICO:TECNICO_CARGO" style="position: absolute; top: 130px; left: 150px;">
	<INPUT name="property(CERT_URBANISTICO:TECNICO_CARGO)" size="80" value="Cargo" class="input"></div>
</div><br/></body>
</html>
', 1, 211, 'CERT_URBANISTICO', 'NO');

INSERT INTO spac_ct_aplicaciones (id, nombre, descripcion, clase, pagina, parametros, formateador, frm_jsp, frm_version, ent_principal_id, ent_principal_nombre, documentos) VALUES (23, 'FrmCrEnt_CERT_URBANISTICO', 'Formulario autogenerado al crear la entidad CERT_URBANISTICO', 'ieci.tdw.ispac.ispaclib.app.GenericSecondaryEntityApp', '/forms/CERT_URBANISTICO/FrmCrEnt_CERT_URBANISTICO.jsp', '<?xml version=''1.0'' encoding=''ISO-8859-1''?><parameters><entity type=''VALIDATION''><table>TIPO_TECNICO</table><relation type=''FLD''><primary-field>TECNICO_CARGO</primary-field><secondary-field>VALOR</secondary-field></relation></entity></parameters>', NULL, '<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ispac-util.tld" prefix="ispac" %>

<!-- DEFINICION DE LAS ANCLAS A LOS BLOQUES DE CAMPOS -->
<table border="0" cellspacing="0" cellpadding="0">
<tr>
<td class="select" id="tdlink1" align="center">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(CERT_URBANISTICO:CERT_URBANISTICO)" />
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
<div id="dataBlock_CERT_URBANISTICO" style="position: relative; height: 135px; width: 600px;">
<div id="label_CERT_URBANISTICO:FECHA_EMISION" style="position: absolute; top: 10px; left: 10px; width: 110px;" class="formsTitleB">
<bean:write name="defaultForm" property="entityApp.label(CERT_URBANISTICO:FECHA_EMISION)" />:</div>
<div id="data_CERT_URBANISTICO:FECHA_EMISION" style="position: absolute; top: 10px; left: 159px;">
<nobr>
<ispac:htmlTextCalendar property="property(CERT_URBANISTICO:FECHA_EMISION)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="14" maxlength="10" image=''<%= buttoncalendar %>'' format="dd/mm/yyyy" enablePast="true" >
</ispac:htmlTextCalendar>
</nobr>
</div>
<div id="label_CERT_URBANISTICO:SITUACION" style="position: absolute; top: 35px; left: 10px; width: 110px;" class="formsTitleB">
<bean:write name="defaultForm" property="entityApp.label(CERT_URBANISTICO:SITUACION)" />:</div>
<div id="data_CERT_URBANISTICO:SITUACION" style="position: absolute; top: 44px; left: 159px;">
<ispac:htmlText property="property(CERT_URBANISTICO:SITUACION)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="80" maxlength="500" >
</ispac:htmlText>
</div>
<div id="label_CERT_URBANISTICO:TECNICO_NOMBRE" style="position: absolute; top: 69px; left: 10px; width: 110px;" class="formsTitleB">
<bean:write name="defaultForm" property="entityApp.label(CERT_URBANISTICO:TECNICO_NOMBRE)" />:</div>
<div id="data_CERT_URBANISTICO:TECNICO_NOMBRE" style="position: absolute; top: 69px; left: 159px;">
<ispac:htmlText property="property(CERT_URBANISTICO:TECNICO_NOMBRE)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="80" maxlength="100" >
</ispac:htmlText>
</div>
<div id="label_CERT_URBANISTICO:TECNICO_CARGO" style="position: absolute; top: 94px; left: 10px; width: 110px;" class="formsTitleB">
<bean:write name="defaultForm" property="entityApp.label(CERT_URBANISTICO:TECNICO_CARGO)" />:</div>
<div id="data_CERT_URBANISTICO:TECNICO_CARGO" style="position: absolute; top: 94px; left: 159px;">
<nobr>
<html:hidden property="property(CERT_URBANISTICO:TECNICO_CARGO)" />
<ispac:htmlTextImageFrame property="property(TECNICO_CARGO_TIPO_TECNICO:SUSTITUTO)" readonly="true" readonlyTag="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="80" id="SEARCH_CERT_URBANISTICO_TECNICO_CARGO" target="workframe" action="selectSubstitute.do?entity=TIPO_TECNICO" image="img/search-mg.gif" titleKeyLink="title.link.data.selection" imageDelete="img/borrar.gif" titleKeyImageDelete="title.delete.data.selection" styleClassDeleteLink="tdlink" confirmDeleteKey="msg.delete.data.selection" showDelete="true" showFrame="true" width="640" height="480" >
<ispac:parameter name="SEARCH_CERT_URBANISTICO_TECNICO_CARGO" id="property(CERT_URBANISTICO:TECNICO_CARGO)" property="VALOR" />
<ispac:parameter name="SEARCH_CERT_URBANISTICO_TECNICO_CARGO" id="property(TECNICO_CARGO_TIPO_TECNICO:SUSTITUTO)" property="SUSTITUTO" />
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
</table>
', 1, 211, 'CERT_URBANISTICO', 'NO');

INSERT INTO spac_ct_aplicaciones (id, nombre, descripcion, clase, pagina, parametros, formateador, frm_jsp, frm_version, ent_principal_id, ent_principal_nombre, documentos) VALUES (24, 'Lotes', 'Gestión de la entidad LOTES', 'ieci.tdw.ispac.ispaclib.app.GenericEntityListEntityApp', '/forms/lotes/lotesForm.jsp', NULL, '/ispac/digester/lotesformatter.xml', '<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ispac-util.tld" prefix="ispac" %>

<!-- DEFINICION DE LAS ANCLAS A LOS BLOQUES DE CAMPOS -->
<table border="0" cellspacing="0" cellpadding="0">
<tr>
<td class="select" id="tdlink1" align="center">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(LOTES:LOTES)" />
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
<div id="dataBlock_LOTES" style="position: relative; height: 85px; width: 600px">
<div id="label_LOTES:NOMBRE" style="position: absolute; top: 10px; left: 10px; width: 110px;" class="formsTitleB">
<bean:write name="defaultForm" property="entityApp.label(LOTES:NOMBRE)" />:</div>
<div id="data_LOTES:NOMBRE" style="position: absolute; top: 10px; left: 130px;">
<ispac:htmlText property="property(LOTES:NOMBRE)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="80" maxlength="250" >
</ispac:htmlText>
</div>
<div id="label_LOTES:PRESUPUESTO" style="position: absolute; top: 35px; left: 10px; width: 110px;" class="formsTitleB">
<bean:write name="defaultForm" property="entityApp.label(LOTES:PRESUPUESTO)" />:</div>
<div id="data_LOTES:PRESUPUESTO" style="position: absolute; top: 35px; left: 130px;">
<ispac:htmlText property="property(LOTES:PRESUPUESTO)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="80" maxlength="26" >
</ispac:htmlText>
</div>
</div>
</div>
</td>
</tr>
<tr>
<td height="15"><img src=''<ispac:rewrite href="img/pixel.gif"/>''/></td>
</tr>
</table>
', 1, 214, 'LOTES', 'NO');

INSERT INTO spac_ct_aplicaciones (id, nombre, descripcion, clase, pagina, parametros, formateador, frm_jsp, frm_version, ent_principal_id, ent_principal_nombre, documentos) VALUES (25, 'Contrato', 'Gestión de la entidad CONTRATO', 'ieci.tdw.ispac.ispaclib.app.GenericSecondaryEntityApp', '/forms/contrato/contratoForm.jsp', '<?xml version=''1.0'' encoding=''ISO-8859-1''?><parameters><entity type=''VALIDATION''><table>SPAC_TBL_001</table><relation type=''FLD''><primary-field>UD_PLAZO</primary-field><secondary-field>VALOR</secondary-field></relation></entity></parameters>', NULL, '<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ispac-util.tld" prefix="ispac" %>

<!-- DEFINICION DE LAS ANCLAS A LOS BLOQUES DE CAMPOS -->
<table border="0" cellspacing="0" cellpadding="0">
<tr>
<td class="select" id="tdlink1" align="center">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(CONTRATO:CONTRATO)" />
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
<div id="dataBlock_CONTRATO" style="position: relative; height: 335px; width: 600px;">
<div id="label_CONTRATO:TIPO" style="position: absolute; top: 10px; left: 10px; width: 110px;" class="formsTitleB">
<bean:write name="defaultForm" property="entityApp.label(CONTRATO:TIPO)" />:</div>
<div id="data_CONTRATO:TIPO" style="position: absolute; top: 10px; left: 130px;">
<nobr>
<ispac:htmlTextImageFrame property="property(CONTRATO:TIPO)" readonly="true" readonlyTag="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="80" id="SEARCH_CONTRATO_TIPO" target="workframe" action="selectValue.do?entity=TIPO_CONTRATO" image="img/search-mg.gif" titleKeyLink="title.link.data.selection" imageDelete="img/borrar.gif" titleKeyImageDelete="title.delete.data.selection" styleClassDeleteLink="tdlink" confirmDeleteKey="msg.delete.data.selection" showDelete="true" showFrame="true" width="640" height="480" >
<ispac:parameter name="SEARCH_CONTRATO_TIPO" id="property(CONTRATO:TIPO)" property="VALOR" />
</ispac:htmlTextImageFrame>
</nobr>
</div>
<div id="label_CONTRATO:FORMA_ADJUDICACION" style="position: absolute; top: 35px; left: 10px; width: 110px;" class="formsTitleB">
<bean:write name="defaultForm" property="entityApp.label(CONTRATO:FORMA_ADJUDICACION)" />:</div>
<div id="data_CONTRATO:FORMA_ADJUDICACION" style="position: absolute; top: 35px; left: 130px;">
<nobr>
<ispac:htmlTextImageFrame property="property(CONTRATO:FORMA_ADJUDICACION)" readonly="true" readonlyTag="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="30" id="SEARCH_CONTRATO_FORMA_ADJUDICACION" target="workframe" action="selectValue.do?entity=FORMA_ADJUDICACION" image="img/search-mg.gif" titleKeyLink="title.link.data.selection" imageDelete="img/borrar.gif" titleKeyImageDelete="title.delete.data.selection" styleClassDeleteLink="tdlink" confirmDeleteKey="msg.delete.data.selection" showDelete="true" showFrame="true" width="640" height="480" >
<ispac:parameter name="SEARCH_CONTRATO_FORMA_ADJUDICACION" id="property(CONTRATO:FORMA_ADJUDICACION)" property="VALOR" />
</ispac:htmlTextImageFrame>
</nobr>
</div>
<div id="label_CONTRATO:PRESUPUESTO_MAXIMO" style="position: absolute; top: 63px; left: 10px; width: 110px;" class="formsTitleB">
<bean:write name="defaultForm" property="entityApp.label(CONTRATO:PRESUPUESTO_MAXIMO)" />:</div>
<div id="data_CONTRATO:PRESUPUESTO_MAXIMO" style="position: absolute; top: 68px; left: 130px;">
<ispac:htmlText property="property(CONTRATO:PRESUPUESTO_MAXIMO)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="20" maxlength="26" >
</ispac:htmlText>
</div>
<div id="label_CONTRATO:APLICACION" style="position: absolute; top: 95px; left: 10px; width: 110px;" class="formsTitleB">
<bean:write name="defaultForm" property="entityApp.label(CONTRATO:APLICACION)" />:</div>
<div id="data_CONTRATO:APLICACION" style="position: absolute; top: 95px; left: 130px;">
<ispac:htmlText property="property(CONTRATO:APLICACION)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="80" maxlength="250" >
</ispac:htmlText>
</div>
<div id="label_CONTRATO:PRECIO_ADJUDICACION" style="position: absolute; top: 63px; left: 310px; width: 110px;" class="formsTitleB">
<bean:write name="defaultForm" property="entityApp.label(CONTRATO:PRECIO_ADJUDICACION)" />:</div>
<div id="data_CONTRATO:PRECIO_ADJUDICACION" style="position: absolute; top: 68px; left: 430px;">
<ispac:htmlText property="property(CONTRATO:PRECIO_ADJUDICACION)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="20" maxlength="26" >
</ispac:htmlText>
</div>
<div id="label_CONTRATO:PLAZO_EJECUCION" style="position: absolute; top: 126px; left: 10px; width: 110px;" class="formsTitleB">
<bean:write name="defaultForm" property="entityApp.label(CONTRATO:PLAZO_EJECUCION)" />:</div>
<div id="data_CONTRATO:PLAZO_EJECUCION" style="position: absolute; top: 126px; left: 130px;">
<ispac:htmlText property="property(CONTRATO:PLAZO_EJECUCION)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="20" maxlength="10" >
</ispac:htmlText>
</div>
<div id="label_CONTRATO:UD_PLAZO" style="position: absolute; top: 126px; left: 261px; width: 110px;" class="formsTitleB">
<bean:write name="defaultForm" property="entityApp.label(CONTRATO:UD_PLAZO)" />:</div>
<div id="data_CONTRATO:UD_PLAZO" style="position: absolute; top: 126px; left: 381px;">
<nobr>
<html:hidden property="property(CONTRATO:UD_PLAZO)" />
<ispac:htmlTextImageFrame property="property(UD_PLAZO_SPAC_TBL_001:SUSTITUTO)" readonly="true" readonlyTag="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="30" id="SEARCH_CONTRATO_UD_PLAZO" target="workframe" action="selectSubstitute.do?entity=SPAC_TBL_001" image="img/search-mg.gif" titleKeyLink="title.link.data.selection" imageDelete="img/borrar.gif" titleKeyImageDelete="title.delete.data.selection" styleClassDeleteLink="tdlink" confirmDeleteKey="msg.delete.data.selection" showDelete="true" showFrame="true" width="640" height="480" >
<ispac:parameter name="SEARCH_CONTRATO_UD_PLAZO" id="property(CONTRATO:UD_PLAZO)" property="VALOR" />
<ispac:parameter name="SEARCH_CONTRATO_UD_PLAZO" id="property(UD_PLAZO_SPAC_TBL_001:SUSTITUTO)" property="SUSTITUTO" />
</ispac:htmlTextImageFrame>
</nobr>
</div>
<div id="label_CONTRATO:GARANTIA_PROVISIONAL" style="position: absolute; top: 154px; left: 10px; width: 110px;" class="formsTitleB">
<bean:write name="defaultForm" property="entityApp.label(CONTRATO:GARANTIA_PROVISIONAL)" />:</div>
<div id="data_CONTRATO:GARANTIA_PROVISIONAL" style="position: absolute; top: 154px; left: 130px;">
<ispac:htmlText property="property(CONTRATO:GARANTIA_PROVISIONAL)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="20" maxlength="26" >
</ispac:htmlText>
</div>
<div id="label_CONTRATO:GARANTIA_DEFINITIVA" style="position: absolute; top: 154px; left: 312px; width: 110px;" class="formsTitleB">
<bean:write name="defaultForm" property="entityApp.label(CONTRATO:GARANTIA_DEFINITIVA)" />:</div>
<div id="data_CONTRATO:GARANTIA_DEFINITIVA" style="position: absolute; top: 154px; left: 430px; width: 99px;">
<ispac:htmlText property="property(CONTRATO:GARANTIA_DEFINITIVA)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="20" maxlength="26" >
</ispac:htmlText>
</div>
<div id="label_CONTRATO:CLASIFICACION" style="position: absolute; top: 185px; left: 10px; width: 110px;" class="formsTitleB">
<bean:write name="defaultForm" property="entityApp.label(CONTRATO:CLASIFICACION)" />:</div>
<div id="data_CONTRATO:CLASIFICACION" style="position: absolute; top: 185px; left: 130px;">
<ispac:htmlText property="property(CONTRATO:CLASIFICACION)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="80" maxlength="500" >
</ispac:htmlText>
</div>
<div id="label_CONTRATO:FECHA_FIN_EJECUCION" style="position: absolute; top: 210px; left: 10px; width: 110px;" class="formsTitleB">
<bean:write name="defaultForm" property="entityApp.label(CONTRATO:FECHA_FIN_EJECUCION)" />:</div>
<div id="data_CONTRATO:FECHA_FIN_EJECUCION" style="position: absolute; top: 215px; left: 130px;">
<nobr>
<ispac:htmlTextCalendar property="property(CONTRATO:FECHA_FIN_EJECUCION)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="14" maxlength="10" image=''<%= buttoncalendar %>'' format="dd/mm/yyyy" enablePast="true" >
</ispac:htmlTextCalendar>
</nobr>
</div>
<div id="label_CONTRATO:FECHA_FIN_GARANTIA" style="position: absolute; top: 222px; left: 313px; width: 110px;" class="formsTitleB">
<bean:write name="defaultForm" property="entityApp.label(CONTRATO:FECHA_FIN_GARANTIA)" />:</div>
<div id="data_CONTRATO:FECHA_FIN_GARANTIA" style="position: absolute; top: 215px; left: 433px;">
<nobr>
<ispac:htmlTextCalendar property="property(CONTRATO:FECHA_FIN_GARANTIA)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="14" maxlength="10" image=''<%= buttoncalendar %>'' format="dd/mm/yyyy" enablePast="true" >
</ispac:htmlTextCalendar>
</nobr>
</div>
</div>
</div>
</td>
</tr>
<tr>
<td height="15"><img src=''<ispac:rewrite href="img/pixel.gif"/>''/></td>
</tr>
</table>
', 1, 215, 'CONTRATO', 'NO');

INSERT INTO spac_ct_aplicaciones (id, nombre, descripcion, clase, pagina, parametros, formateador, frm_jsp, frm_version, ent_principal_id, ent_principal_nombre, documentos) VALUES (26, 'Sanción', 'Gestión de la entidad SANCION', 'ieci.tdw.ispac.ispaclib.app.GenericSecondaryEntityApp', '/forms/sancion/sancionForm.jsp', NULL, NULL, '<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ispac-util.tld" prefix="ispac" %>

<!-- DEFINICION DE LAS ANCLAS A LOS BLOQUES DE CAMPOS -->
<table border="0" cellspacing="0" cellpadding="0">
<tr>
<td class="select" id="tdlink1" align="center">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(SANCION:SANCION)" />
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
<div id="dataBlock_SANCION" style="position: relative; height: 285px; width: 600px">
<div id="label_SANCION:INSTRUCTOR" style="position: absolute; top: 10px; left: 10px; width: 110px;" class="formsTitleB">
<bean:write name="defaultForm" property="entityApp.label(SANCION:INSTRUCTOR)" />:</div>
<div id="data_SANCION:INSTRUCTOR" style="position: absolute; top: 10px; left: 130px;">
<ispac:htmlText property="property(SANCION:INSTRUCTOR)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="80" maxlength="254" >
</ispac:htmlText>
</div>
<div id="label_SANCION:CALIFICACION" style="position: absolute; top: 35px; left: 10px; width: 110px;" class="formsTitleB">
<bean:write name="defaultForm" property="entityApp.label(SANCION:CALIFICACION)" />:</div>
<div id="data_SANCION:CALIFICACION" style="position: absolute; top: 35px; left: 130px;">
<nobr>
<ispac:htmlTextImageFrame property="property(SANCION:CALIFICACION)" readonly="true" readonlyTag="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="80" id="SEARCH_SANCION_CALIFICACION" target="workframe" action="selectValue.do?entity=VLDTBL_SAN_CALIFICACION" image="img/search-mg.gif" titleKeyLink="title.link.data.selection" imageDelete="img/borrar.gif" titleKeyImageDelete="title.delete.data.selection" styleClassDeleteLink="tdlink" confirmDeleteKey="msg.delete.data.selection" showDelete="true" showFrame="true" width="640" height="480" >
<ispac:parameter name="SEARCH_SANCION_CALIFICACION" id="property(SANCION:CALIFICACION)" property="VALOR" />
</ispac:htmlTextImageFrame>
</nobr>
</div>
<div id="label_SANCION:IMPORTE" style="position: absolute; top: 60px; left: 10px; width: 110px;" class="formsTitleB">
<bean:write name="defaultForm" property="entityApp.label(SANCION:IMPORTE)" />:</div>
<div id="data_SANCION:IMPORTE" style="position: absolute; top: 60px; left: 130px;">
<ispac:htmlText property="property(SANCION:IMPORTE)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="80" maxlength="26" >
</ispac:htmlText>
</div>
<div id="label_SANCION:GRADO" style="position: absolute; top: 85px; left: 10px; width: 110px;" class="formsTitleB">
<bean:write name="defaultForm" property="entityApp.label(SANCION:GRADO)" />:</div>
<div id="data_SANCION:GRADO" style="position: absolute; top: 85px; left: 130px;">
<nobr>
<ispac:htmlTextImageFrame property="property(SANCION:GRADO)" readonly="true" readonlyTag="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="80" id="SEARCH_SANCION_GRADO" target="workframe" action="selectValue.do?entity=VLDTBL_SAN_GRADO" image="img/search-mg.gif" titleKeyLink="title.link.data.selection" imageDelete="img/borrar.gif" titleKeyImageDelete="title.delete.data.selection" styleClassDeleteLink="tdlink" confirmDeleteKey="msg.delete.data.selection" showDelete="true" showFrame="true" width="640" height="480" >
<ispac:parameter name="SEARCH_SANCION_GRADO" id="property(SANCION:GRADO)" property="VALOR" />
</ispac:htmlTextImageFrame>
</nobr>
</div>
<div id="label_SANCION:INFRACCION" style="position: absolute; top: 110px; left: 10px; width: 110px;" class="formsTitleB">
<bean:write name="defaultForm" property="entityApp.label(SANCION:INFRACCION)" />:</div>
<div id="data_SANCION:INFRACCION" style="position: absolute; top: 110px; left: 130px;">
<ispac:htmlTextarea property="property(SANCION:INFRACCION)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" rows="2" cols="80" >
</ispac:htmlTextarea>
</div>
<div id="label_SANCION:HECHOS" style="position: absolute; top: 160px; left: 10px; width: 110px;" class="formsTitleB">
<bean:write name="defaultForm" property="entityApp.label(SANCION:HECHOS)" />:</div>
<div id="data_SANCION:HECHOS" style="position: absolute; top: 160px; left: 130px;">
<ispac:htmlTextarea property="property(SANCION:HECHOS)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" rows="2" cols="80" >
</ispac:htmlTextarea>
</div>
<div id="label_SANCION:ACTUACION" style="position: absolute; top: 210px; left: 10px; width: 110px;" class="formsTitleB">
<bean:write name="defaultForm" property="entityApp.label(SANCION:ACTUACION)" />:</div>
<div id="data_SANCION:ACTUACION" style="position: absolute; top: 210px; left: 130px;">
<ispac:htmlTextarea property="property(SANCION:ACTUACION)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" rows="2" cols="80" >
</ispac:htmlTextarea>
</div>
</div>
</div>
</td>
</tr>
<tr>
<td height="15"><img src=''<ispac:rewrite href="img/pixel.gif"/>''/></td>
</tr>
</table>
', 1, 218, 'SANCION', 'NO');

INSERT INTO spac_ct_aplicaciones (id, nombre, descripcion, clase, pagina, parametros, formateador, frm_jsp, frm_version, ent_principal_id, ent_principal_nombre, documentos) VALUES (27, 'Actividad clasificada', 'Formulario autogenerado al crear la entidad ACTIVIDAD_CLASIFICADA', 'ieci.tdw.ispac.ispaclib.app.GenericSecondaryEntityApp', '/forms/actividadclasificada/actividadClasificadaForm.jsp', '<?xml version=''1.0'' encoding=''ISO-8859-1''?><parameters><entity type=''VALIDATION''><table>CLASIFICACION_ACTIVIDADES</table><relation type=''FLD''><primary-field>CLASIFICACION</primary-field><secondary-field>VALOR</secondary-field></relation></entity></parameters>', NULL, '<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ispac-util.tld" prefix="ispac" %>

<!-- DEFINICION DE LAS ANCLAS A LOS BLOQUES DE CAMPOS -->
<table border="0" cellspacing="0" cellpadding="0">
<tr>
<td class="select" id="tdlink1" align="center">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(ACTIVIDAD_CLASIFICADA:ACTIVIDAD_CLASIFICADA)" />
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
<div id="dataBlock_ACTIVIDAD_CLASIFICADA" style="position: relative; height: 160px; width: 600px;">
<div id="label_ACTIVIDAD_CLASIFICADA:ACTIVIDAD" style="position: absolute; top: 10px; left: 10px; width: 110px;" class="formsTitleB">
<bean:write name="defaultForm" property="entityApp.label(ACTIVIDAD_CLASIFICADA:ACTIVIDAD)" />:</div>
<div id="data_ACTIVIDAD_CLASIFICADA:ACTIVIDAD" style="position: absolute; top: 10px; left: 130px;">
<ispac:htmlText property="property(ACTIVIDAD_CLASIFICADA:ACTIVIDAD)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="155" maxlength="254" >
</ispac:htmlText>
</div>
<div id="label_ACTIVIDAD_CLASIFICADA:CLASIFICACION" style="position: absolute; top: 35px; left: 10px; width: 110px;" class="formsTitleB">
<bean:write name="defaultForm" property="entityApp.label(ACTIVIDAD_CLASIFICADA:CLASIFICACION)" />:</div>
<div id="data_ACTIVIDAD_CLASIFICADA:CLASIFICACION" style="position: absolute; top: 35px; left: 130px;">
<nobr>
<html:hidden property="property(ACTIVIDAD_CLASIFICADA:CLASIFICACION)" />
<ispac:htmlTextImageFrame property="property(CLASIFICACION_CLASIFICACION_ACTIVIDADES:SUSTITUTO)" readonly="true" readonlyTag="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="155" id="SEARCH_ACTIVIDAD_CLASIFICADA_CLASIFICACION" target="workframe" action="selectSubstitute.do?entity=CLASIFICACION_ACTIVIDADES" image="img/search-mg.gif" titleKeyLink="title.link.data.selection" imageDelete="img/borrar.gif" titleKeyImageDelete="title.delete.data.selection" styleClassDeleteLink="tdlink" confirmDeleteKey="msg.delete.data.selection" showDelete="true" showFrame="true" width="640" height="480" >
<ispac:parameter name="SEARCH_ACTIVIDAD_CLASIFICADA_CLASIFICACION" id="property(ACTIVIDAD_CLASIFICADA:CLASIFICACION)" property="VALOR" />
<ispac:parameter name="SEARCH_ACTIVIDAD_CLASIFICADA_CLASIFICACION" id="property(CLASIFICACION_CLASIFICACION_ACTIVIDADES:SUSTITUTO)" property="SUSTITUTO" />
</ispac:htmlTextImageFrame>
</nobr>
</div>
<div id="label_ACTIVIDAD_CLASIFICADA:EMPLAZAMIENTO" style="position: absolute; top: 70px; left: 10px; width: 110px;" class="formsTitleB">
<bean:write name="defaultForm" property="entityApp.label(ACTIVIDAD_CLASIFICADA:EMPLAZAMIENTO)" />:</div>
<div id="data_ACTIVIDAD_CLASIFICADA:EMPLAZAMIENTO" style="position: absolute; top: 70px; left: 130px;">
<ispac:htmlTextarea property="property(ACTIVIDAD_CLASIFICADA:EMPLAZAMIENTO)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" rows="2" cols="155" >
</ispac:htmlTextarea>
</div>
<div id="label_ACTIVIDAD_CLASIFICADA:TASAS" style="position: absolute; top: 120px; left: 10px; width: 110px;" class="formsTitleB">
<bean:write name="defaultForm" property="entityApp.label(ACTIVIDAD_CLASIFICADA:TASAS)" />:</div>
<div id="data_ACTIVIDAD_CLASIFICADA:TASAS" style="position: absolute; top: 120px; left: 130px;">
<ispac:htmlText property="property(ACTIVIDAD_CLASIFICADA:TASAS)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="15" maxlength="18" >
</ispac:htmlText>
</div>
<div style="position: absolute; top: 120px; left: 225px; width: 110px;" class="formsTitleB">
&euro;</div>
</div>
</div>
</td>
</tr>
<tr>
<td height="15"><img src=''<ispac:rewrite href="img/pixel.gif"/>''/></td>
</tr>
</table>
', 1, 220, 'ACTIVIDAD_CLASIFICADA', 'NO');

INSERT INTO spac_ct_aplicaciones (id, nombre, descripcion, clase, pagina, parametros, formateador, frm_jsp, frm_version, ent_principal_id, ent_principal_nombre, documentos) VALUES (28, 'FrmCrEnt_ACTIVIDAD_NO_CLASIFICADA', 'Formulario autogenerado al crear la entidad ACTIVIDAD_NO_CLASIFICADA', 'ieci.tdw.ispac.ispaclib.app.GenericSecondaryEntityApp', '/forms/ACTIVIDAD_NO_CLASIFICADA/FrmCrEnt_ACTIVIDAD_NO_CLASIFICADA.jsp', NULL, NULL, '<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ispac-util.tld" prefix="ispac" %>

<!-- DEFINICION DE LAS ANCLAS A LOS BLOQUES DE CAMPOS -->
<table border="0" cellspacing="0" cellpadding="0">
<tr>
<td class="select" id="tdlink1" align="center">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(ACTIVIDAD_NO_CLASIFICADA:ACTIVIDAD_NO_CLASIFICADA)" />
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
<div id="dataBlock_ACTIVIDAD_NO_CLASIFICADA" style="position: relative; height: 135px; width: 600px">
<div id="label_ACTIVIDAD_NO_CLASIFICADA:ACTIVIDAD" style="position: absolute; top: 10px; left: 10px; width: 110px;" class="formsTitleB">
<bean:write name="defaultForm" property="entityApp.label(ACTIVIDAD_NO_CLASIFICADA:ACTIVIDAD)" />:</div>
<div id="data_ACTIVIDAD_NO_CLASIFICADA:ACTIVIDAD" style="position: absolute; top: 10px; left: 130px;">
<ispac:htmlText property="property(ACTIVIDAD_NO_CLASIFICADA:ACTIVIDAD)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="80" maxlength="254" >
</ispac:htmlText>
</div>
<div id="label_ACTIVIDAD_NO_CLASIFICADA:EMPLAZAMIENTO" style="position: absolute; top: 35px; left: 10px; width: 110px;" class="formsTitleB">
<bean:write name="defaultForm" property="entityApp.label(ACTIVIDAD_NO_CLASIFICADA:EMPLAZAMIENTO)" />:</div>
<div id="data_ACTIVIDAD_NO_CLASIFICADA:EMPLAZAMIENTO" style="position: absolute; top: 35px; left: 130px;">
<ispac:htmlTextarea property="property(ACTIVIDAD_NO_CLASIFICADA:EMPLAZAMIENTO)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" rows="2" cols="80" >
</ispac:htmlTextarea>
</div>
<div id="label_ACTIVIDAD_NO_CLASIFICADA:TASAS" style="position: absolute; top: 85px; left: 10px; width: 110px;" class="formsTitleB">
<bean:write name="defaultForm" property="entityApp.label(ACTIVIDAD_NO_CLASIFICADA:TASAS)" />:</div>
<div id="data_ACTIVIDAD_NO_CLASIFICADA:TASAS" style="position: absolute; top: 85px; left: 130px;">
<ispac:htmlText property="property(ACTIVIDAD_NO_CLASIFICADA:TASAS)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="15" maxlength="18" >
</ispac:htmlText>
</div>
<div style="position: absolute; top: 85px; left: 225px; width: 110px;" class="formsTitleB">
&nbsp;&#8364;</div>
</div>
</div>
</td>
</tr>
<tr>
<td height="15"><img src=''<ispac:rewrite href="img/pixel.gif"/>''/></td>
</tr>
</table>
', 1, 221, 'ACTIVIDAD_NO_CLASIFICADA', 'NO');

INSERT INTO spac_ct_aplicaciones (id, nombre, descripcion, clase, pagina, parametros, formateador, frm_jsp, frm_version, ent_principal_id, ent_principal_nombre, documentos) VALUES (29, 'FrmCrEnt_VADO', 'Formulario autogenerado al crear la entidad VADO', 'ieci.tdw.ispac.ispaclib.app.GenericSecondaryEntityApp', '/forms/VADO/FrmCrEnt_VADO.jsp', NULL, NULL, '<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ispac-util.tld" prefix="ispac" %>

<!-- DEFINICION DE LAS ANCLAS A LOS BLOQUES DE CAMPOS -->
<table border="0" cellspacing="0" cellpadding="0">
<tr>
<td class="select" id="tdlink1" align="center">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(VADO:VADO)" />
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
<div id="dataBlock_VADO" style="position: relative; height: 310px; width: 600px">
<div id="label_VADO:NUMLICENCIA" style="position: absolute; top: 10px; left: 10px; width: 150px;" class="formsTitleB">
<bean:write name="defaultForm" property="entityApp.label(VADO:NUMLICENCIA)" />:</div>
<div id="data_VADO:NUMLICENCIA" style="position: absolute; top: 10px; left: 170px;">
<ispac:htmlText property="property(VADO:NUMLICENCIA)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="80" maxlength="100" >
</ispac:htmlText>
</div>
<div id="label_VADO:UBICACION" style="position: absolute; top: 35px; left: 10px; width: 150px;" class="formsTitleB">
<bean:write name="defaultForm" property="entityApp.label(VADO:UBICACION)" />:</div>
<div id="data_VADO:UBICACION" style="position: absolute; top: 35px; left: 170px;">
<ispac:htmlText property="property(VADO:UBICACION)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="80" maxlength="250" >
</ispac:htmlText>
</div>
<div id="label_VADO:TIPO" style="position: absolute; top: 60px; left: 10px; width: 150px;" class="formsTitleB">
<bean:write name="defaultForm" property="entityApp.label(VADO:TIPO)" />:</div>
<div id="data_VADO:TIPO" style="position: absolute; top: 60px; left: 170px;">
<nobr>
<ispac:htmlTextImageFrame property="property(VADO:TIPO)" readonly="true" readonlyTag="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="40" id="SEARCH_VADO_TIPO" target="workframe" action="selectValue.do?entity=TIPO_VADO" image="img/search-mg.gif" titleKeyLink="title.link.data.selection" imageDelete="img/borrar.gif" titleKeyImageDelete="title.delete.data.selection" styleClassDeleteLink="tdlink" confirmDeleteKey="msg.delete.data.selection" showDelete="true" showFrame="true" width="640" height="480" >
<ispac:parameter name="SEARCH_VADO_TIPO" id="property(VADO:TIPO)" property="VALOR" />
</ispac:htmlTextImageFrame>
</nobr>
</div>
<div id="label_VADO:LARGOENTRADA" style="position: absolute; top: 85px; left: 10px; width: 150px;" class="formsTitleB">
<bean:write name="defaultForm" property="entityApp.label(VADO:LARGOENTRADA)" />:</div>
<div id="data_VADO:LARGOENTRADA" style="position: absolute; top: 85px; left: 170px;">
<ispac:htmlText property="property(VADO:LARGOENTRADA)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="20" maxlength="18" >
</ispac:htmlText>
</div>
<div style="position: absolute; top: 85px; left: 295px; width: 150px;" class="formsTitleB">
metros</div>
<div id="label_VADO:ANCHOACERA" style="position: absolute; top: 110px; left: 10px; width: 150px;" class="formsTitleB">
<bean:write name="defaultForm" property="entityApp.label(VADO:ANCHOACERA)" />:</div>
<div id="data_VADO:ANCHOACERA" style="position: absolute; top: 110px; left: 170px;">
<ispac:htmlText property="property(VADO:ANCHOACERA)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="20" maxlength="18" >
</ispac:htmlText>
</div>
<div style="position: absolute; top: 110px; left: 295px; width: 150px;" class="formsTitleB">
metros</div>
<div id="label_VADO:SUPERFICIEACERA" style="position: absolute; top: 135px; left: 10px; width: 150px;" class="formsTitleB">
<bean:write name="defaultForm" property="entityApp.label(VADO:SUPERFICIEACERA)" />:</div>
<div id="data_VADO:SUPERFICIEACERA" style="position: absolute; top: 135px; left: 170px;">
<ispac:htmlText property="property(VADO:SUPERFICIEACERA)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="20" maxlength="18" >
</ispac:htmlText>
</div>
<div style="position: absolute; top: 135px; left: 295px; width: 150px;" class="formsTitleB">
metros cuadrados</div>
<div id="label_VADO:SUPERFICIELOCAL" style="position: absolute; top: 160px; left: 10px; width: 150px;" class="formsTitleB">
<bean:write name="defaultForm" property="entityApp.label(VADO:SUPERFICIELOCAL)" />:</div>
<div id="data_VADO:SUPERFICIELOCAL" style="position: absolute; top: 160px; left: 170px;">
<ispac:htmlText property="property(VADO:SUPERFICIELOCAL)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="20" maxlength="18" >
</ispac:htmlText>
</div>
<div style="position: absolute; top: 160px; left: 295px; width: 150px;" class="formsTitleB">
metros cuadrados</div>
<div id="label_VADO:ACTIVIDAD" style="position: absolute; top: 185px; left: 10px; width: 150px;" class="formsTitleB">
<bean:write name="defaultForm" property="entityApp.label(VADO:ACTIVIDAD)" />:</div>
<div id="data_VADO:ACTIVIDAD" style="position: absolute; top: 185px; left: 170px;">
<ispac:htmlText property="property(VADO:ACTIVIDAD)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="80" maxlength="250" >
</ispac:htmlText>
</div>
<div id="label_VADO:NUMVEHICULOS" style="position: absolute; top: 210px; left: 10px; width: 150px;" class="formsTitleB">
<bean:write name="defaultForm" property="entityApp.label(VADO:NUMVEHICULOS)" />:</div>
<div id="data_VADO:NUMVEHICULOS" style="position: absolute; top: 210px; left: 170px;">
<ispac:htmlText property="property(VADO:NUMVEHICULOS)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="20" maxlength="20" >
</ispac:htmlText>
</div>
<div style="position: absolute; top: 210px; left: 295px; width: 150px;" class="formsTitleB">
plazas</div>
<div id="label_VADO:REBAJE" style="position: absolute; top: 235px; left: 10px; width: 150px;" class="formsTitleB">
<bean:write name="defaultForm" property="entityApp.label(VADO:REBAJE)" />:</div>
<div id="data_VADO:REBAJE" style="position: absolute; top: 235px; left: 170px;">
<nobr>
<ispac:htmlTextImageFrame property="property(VADO:REBAJE)" readonly="true" readonlyTag="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="40" id="SEARCH_VADO_REBAJE" target="workframe" action="selectValue.do?entity=REBAJE_ACERA" image="img/search-mg.gif" titleKeyLink="title.link.data.selection" imageDelete="img/borrar.gif" titleKeyImageDelete="title.delete.data.selection" styleClassDeleteLink="tdlink" confirmDeleteKey="msg.delete.data.selection" showDelete="true" showFrame="true" width="640" height="480" >
<ispac:parameter name="SEARCH_VADO_REBAJE" id="property(VADO:REBAJE)" property="VALOR" />
</ispac:htmlTextImageFrame>
</nobr>
</div>
<div id="label_VADO:TASAS" style="position: absolute; top: 260px; left: 10px; width: 150px;" class="formsTitleB">
<bean:write name="defaultForm" property="entityApp.label(VADO:TASAS)" />:</div>
<div id="data_VADO:TASAS" style="position: absolute; top: 260px; left: 170px;">
<ispac:htmlText property="property(VADO:TASAS)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="20" maxlength="18" >
</ispac:htmlText>
</div>
<div style="position: absolute; top: 260px; left: 295px; width: 150px;" class="formsTitleB">
&nbsp;&#8364;</div>
</div>
</div>
</td>
</tr>
<tr>
<td height="15"><img src=''<ispac:rewrite href="img/pixel.gif"/>''/></td>
</tr>
</table>', 1, 224, 'VADO', 'NO');

INSERT INTO spac_ct_aplicaciones (id, nombre, descripcion, clase, pagina, parametros, formateador, frm_jsp, frm_version, ent_principal_id, ent_principal_nombre, documentos) VALUES (30, 'FrmCrEnt_RECLAMACION', 'Reclamación', 'ieci.tdw.ispac.ispaclib.app.GenericSecondaryEntityApp', '/forms/RECLAMACION/FrmCrEnt_RECLAMACION.jsp', NULL, NULL, '<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ispac-util.tld" prefix="ispac" %>

<!-- DEFINICION DE LAS ANCLAS A LOS BLOQUES DE CAMPOS -->
<table border="0" cellspacing="0" cellpadding="0">
<tr>
<td class="select" id="tdlink1" align="center">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(RECLAMACION:RECLAMACION)" />
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
<div id="dataBlock_RECLAMACION" style="position: relative; height: 160px; width: 600px">
<div id="label_RECLAMACION:TIPO" style="position: absolute; top: 10px; left: 10px; width: 110px;" class="formsTitleB">
<bean:write name="defaultForm" property="entityApp.label(RECLAMACION:TIPO)" />:</div>
<div id="data_RECLAMACION:TIPO" style="position: absolute; top: 10px; left: 130px;">
<nobr>
<ispac:htmlTextImageFrame property="property(RECLAMACION:TIPO)" readonly="true" readonlyTag="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="80" id="SEARCH_RECLAMACION_TIPO" target="workframe" action="selectValue.do?entity=VLDTBL_TIPO_RECLAM" image="img/search-mg.gif" titleKeyLink="title.link.data.selection" imageDelete="img/borrar.gif" titleKeyImageDelete="title.delete.data.selection" styleClassDeleteLink="tdlink" confirmDeleteKey="msg.delete.data.selection" showDelete="true" showFrame="true" width="640" height="480" >
<ispac:parameter name="SEARCH_RECLAMACION_TIPO" id="property(RECLAMACION:TIPO)" property="VALOR" />
</ispac:htmlTextImageFrame>
</nobr>
</div>
<div id="label_RECLAMACION:DESCRIPCION" style="position: absolute; top: 35px; left: 10px; width: 110px;" class="formsTitleB">
<bean:write name="defaultForm" property="entityApp.label(RECLAMACION:DESCRIPCION)" />:</div>
<div id="data_RECLAMACION:DESCRIPCION" style="position: absolute; top: 35px; left: 130px;">
<ispac:htmlTextarea property="property(RECLAMACION:DESCRIPCION)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" rows="2" cols="80" >
</ispac:htmlTextarea>
</div>
<div id="label_RECLAMACION:DESTINATARIO" style="position: absolute; top: 85px; left: 10px; width: 110px;" class="formsTitleB">
<bean:write name="defaultForm" property="entityApp.label(RECLAMACION:DESTINATARIO)" />:</div>
<div id="data_RECLAMACION:DESTINATARIO" style="position: absolute; top: 85px; left: 130px;">
<nobr>
<ispac:htmlTextImageFrame property="property(RECLAMACION:DESTINATARIO)" readonly="true" readonlyTag="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="80" id="SEARCH_RECLAMACION_DESTINATARIO" target="workframe" action="selectValue.do?entity=VLDTBL_TIPO_DESTI" image="img/search-mg.gif" titleKeyLink="title.link.data.selection" imageDelete="img/borrar.gif" titleKeyImageDelete="title.delete.data.selection" styleClassDeleteLink="tdlink" confirmDeleteKey="msg.delete.data.selection" showDelete="true" showFrame="true" width="640" height="480" >
<ispac:parameter name="SEARCH_RECLAMACION_DESTINATARIO" id="property(RECLAMACION:DESTINATARIO)" property="VALOR" />
</ispac:htmlTextImageFrame>
</nobr>
</div>
<div id="label_RECLAMACION:ACEPTADA" style="position: absolute; top: 110px; left: 10px; width: 110px;" class="formsTitleB">
<bean:write name="defaultForm" property="entityApp.label(RECLAMACION:ACEPTADA)" />:</div>
<div id="data_RECLAMACION:ACEPTADA" style="position: absolute; top: 110px; left: 130px;">
<nobr>
<ispac:htmlTextImageFrame property="property(RECLAMACION:ACEPTADA)" readonly="true" readonlyTag="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="80" id="SEARCH_RECLAMACION_ACEPTADA" target="workframe" action="selectValue.do?entity=SPAC_TBL_009" image="img/search-mg.gif" titleKeyLink="title.link.data.selection" imageDelete="img/borrar.gif" titleKeyImageDelete="title.delete.data.selection" styleClassDeleteLink="tdlink" confirmDeleteKey="msg.delete.data.selection" showDelete="true" showFrame="true" width="640" height="480" >
<ispac:parameter name="SEARCH_RECLAMACION_ACEPTADA" id="property(RECLAMACION:ACEPTADA)" property="VALOR" />
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
</table>
', 1, 227, 'RECLAMACION', 'NO');

INSERT INTO spac_ct_aplicaciones (id, nombre, descripcion, clase, pagina, parametros, formateador, frm_jsp, frm_version, ent_principal_id, ent_principal_nombre, documentos) VALUES (31, 'FrmCrEnt_DATOS_TARJETA_MINUS', 'Formulario autogenerado al crear la entidad DATOS_TARJETA_MINUS', 'ieci.tdw.ispac.ispaclib.app.GenericSecondaryEntityApp', '/forms/DATOS_TARJETA_MINUS/FrmCrEnt_DATOS_TARJETA_MINUS.jsp', NULL, NULL, '<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ispac-util.tld" prefix="ispac" %>

<!-- DEFINICION DE LAS ANCLAS A LOS BLOQUES DE CAMPOS -->
<table border="0" cellspacing="0" cellpadding="0">
<tr>
<td class="select" id="tdlink1" align="center">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(DATOS_TARJETA_MINUS:DATOS_TARJETA_MINUS)" />
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
<div id="dataBlock_DATOS_TARJETA_MINUS" style="position: relative; height: 85px; width: 600px">
<div id="label_DATOS_TARJETA_MINUS:TIPO_TARJETA" style="position: absolute; top: 10px; left: 10px; width: 110px;" class="formsTitleB">
<bean:write name="defaultForm" property="entityApp.label(DATOS_TARJETA_MINUS:TIPO_TARJETA)" />:</div>
<div id="data_DATOS_TARJETA_MINUS:TIPO_TARJETA" style="position: absolute; top: 10px; left: 130px;">
<nobr>
<ispac:htmlTextImageFrame property="property(DATOS_TARJETA_MINUS:TIPO_TARJETA)" readonly="true" readonlyTag="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="80" id="SEARCH_DATOS_TARJETA_MINUS_TIPO_TARJETA" target="workframe" action="selectValue.do?entity=VLDTBL_TIPO_TARJETA" image="img/search-mg.gif" titleKeyLink="title.link.data.selection" imageDelete="img/borrar.gif" titleKeyImageDelete="title.delete.data.selection" styleClassDeleteLink="tdlink" confirmDeleteKey="msg.delete.data.selection" showDelete="true" showFrame="true" width="640" height="480" >
<ispac:parameter name="SEARCH_DATOS_TARJETA_MINUS_TIPO_TARJETA" id="property(DATOS_TARJETA_MINUS:TIPO_TARJETA)" property="VALOR" />
</ispac:htmlTextImageFrame>
</nobr>
</div>
<div id="label_DATOS_TARJETA_MINUS:FECHA_CADUCIDAD" style="position: absolute; top: 35px; left: 10px; width: 110px;" class="formsTitleB">
<bean:write name="defaultForm" property="entityApp.label(DATOS_TARJETA_MINUS:FECHA_CADUCIDAD)" />:</div>
<div id="data_DATOS_TARJETA_MINUS:FECHA_CADUCIDAD" style="position: absolute; top: 35px; left: 130px;">
<nobr>
<ispac:htmlTextCalendar property="property(DATOS_TARJETA_MINUS:FECHA_CADUCIDAD)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="14" maxlength="10" image=''<%= buttoncalendar %>'' format="dd/mm/yyyy" enablePast="true" >
</ispac:htmlTextCalendar>
</nobr>
</div>
</div>
</div>
</td>
</tr>
<tr>
<td height="15"><img src=''<ispac:rewrite href="img/pixel.gif"/>''/></td>
</tr>
</table>
', 1, 229, 'DATOS_TARJETA_MINUS', 'NO');


-- Actualizar secuencia de spac_ct_aplicaciones
UPDATE SPAC_SQ_SEQUENCES SET SEQUENCE_ID = 32 WHERE SEQUENCE_NAME = 'SPAC_SQ_ID_CTAPLICACIONES';


--
-- Entidades
--

INSERT INTO spac_ct_entidades (id, tipo, nombre, campo_pk, campo_numexp, schema_expr, frm_edit, descripcion, sec_pk, definicion, frm_jsp, fecha) VALUES (204, 1, 'OFERTA', 'ID', 'NUMEXP', 'NUMEXP', NULL, 'Ofertas de los licitadores de contratación administrativa', 'SPAC_SQ_ID_OFERTA', '<entity version=''1.00''><editable>S</editable><dropable>S</dropable><status>0</status><fields><field id=''1''><physicalName>id_licitador</physicalName><descripcion><![CDATA[Id licitador]]></descripcion><type>3</type><nullable>S</nullable><documentarySearch>N</documentarySearch><multivalue>N</multivalue></field><field id=''2''><physicalName>importe</physicalName><descripcion><![CDATA[Importe]]></descripcion><type>4</type><size>20</size><precision>2</precision><nullable>S</nullable><documentarySearch>N</documentarySearch><multivalue>N</multivalue></field><field id=''3''><physicalName>plazo</physicalName><descripcion><![CDATA[Plazo]]></descripcion><type>3</type><nullable>S</nullable><documentarySearch>N</documentarySearch><multivalue>N</multivalue></field><field id=''4''><physicalName>ud_plazo</physicalName><descripcion><![CDATA[Ud plazo]]></descripcion><type>0</type><size>1</size><nullable>S</nullable><documentarySearch>N</documentarySearch><multivalue>N</multivalue></field><field id=''5''><physicalName>numexp</physicalName><descripcion><![CDATA[Campo que relaciona la entidad con un número de expediente (Uso interno del sistema)]]></descripcion><type>0</type><size>30</size><nullable>S</nullable><documentarySearch>N</documentarySearch><multivalue>N</multivalue></field><field id=''6''><physicalName>id</physicalName><descripcion><![CDATA[Campos Clave de la entidad (Uso interno del sistema)]]></descripcion><type>3</type><nullable>S</nullable><documentarySearch>N</documentarySearch><multivalue>N</multivalue></field><field id=''7''><physicalName>orden</physicalName><descripcion><![CDATA[Indica el orden del valor]]></descripcion><type>2</type><size>10</size><nullable>S</nullable><documentarySearch>N</documentarySearch><multivalue>N</multivalue></field></fields><validations><validation id=''1''><fieldId>4</fieldId><table>SPAC_TBL_001</table><tableType>3</tableType><class/><mandatory>N</mandatory></validation></validations></entity>', '<div id="dataBlock_OFERTA" style="position: relative; height: 135px; width: 600px">
<div id="label_OFERTA:ID_LICITADOR" style="position: absolute; top: 10px; left: 10px; width: 110px;" class="formsTitleB">
<bean:write name="defaultForm" property="entityApp.label(OFERTA:ID_LICITADOR)" />:</div>
<div id="data_OFERTA:ID_LICITADOR" style="position: absolute; top: 10px; left: 130px;">
<ispac:htmlText property="property(OFERTA:ID_LICITADOR)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="80" maxlength="10" >
</ispac:htmlText>
</div>
<div id="label_OFERTA:IMPORTE" style="position: absolute; top: 35px; left: 10px; width: 110px;" class="formsTitleB">
<bean:write name="defaultForm" property="entityApp.label(OFERTA:IMPORTE)" />:</div>
<div id="data_OFERTA:IMPORTE" style="position: absolute; top: 35px; left: 130px;">
<ispac:htmlText property="property(OFERTA:IMPORTE)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="80" maxlength="26" >
</ispac:htmlText>
</div>
<div id="label_OFERTA:PLAZO" style="position: absolute; top: 60px; left: 10px; width: 110px;" class="formsTitleB">
<bean:write name="defaultForm" property="entityApp.label(OFERTA:PLAZO)" />:</div>
<div id="data_OFERTA:PLAZO" style="position: absolute; top: 60px; left: 130px;">
<ispac:htmlText property="property(OFERTA:PLAZO)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="80" maxlength="10" >
</ispac:htmlText>
</div>
<div id="label_OFERTA:UD_PLAZO" style="position: absolute; top: 85px; left: 10px; width: 110px;" class="formsTitleB">
<bean:write name="defaultForm" property="entityApp.label(OFERTA:UD_PLAZO)" />:</div>
<div id="data_OFERTA:UD_PLAZO" style="position: absolute; top: 85px; left: 130px;">
<nobr>
<html:hidden property="property(OFERTA:UD_PLAZO)" />
<ispac:htmlTextImageFrame property="property(UD_PLAZO_SPAC_TBL_001:SUSTITUTO)" readonly="true" readonlyTag="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="80" id="SEARCH_OFERTA_UD_PLAZO" target="workframe" action="selectSubstitute.do?entity=SPAC_TBL_001" image="img/search-mg.gif" titleKeyLink="title.link.data.selection" imageDelete="img/borrar.gif" titleKeyImageDelete="title.delete.data.selection" styleClassDeleteLink="tdlink" confirmDeleteKey="msg.delete.data.selection" showDelete="true" showFrame="true" width="640" height="480" >
<ispac:parameter name="SEARCH_OFERTA_UD_PLAZO" id="property(OFERTA:UD_PLAZO)" property="VALOR" />
<ispac:parameter name="SEARCH_OFERTA_UD_PLAZO" id="property(UD_PLAZO_SPAC_TBL_001:SUSTITUTO)" property="SUSTITUTO" />
</ispac:htmlTextImageFrame>
</nobr>
</div>
</div>
', getDate());
INSERT INTO spac_ct_entidades (id, tipo, nombre, campo_pk, campo_numexp, schema_expr, frm_edit, descripcion, sec_pk, definicion, frm_jsp, fecha) VALUES (205, 1, 'VISTO_BUENO', 'ID', 'NUMEXP', 'NUMEXP', NULL, NULL, 'SPAC_SQ_VISTO_BUENO', '<entity version=''1.00''><editable>S</editable><dropable>S</dropable><status>0</status><fields><field id=''1''><physicalName>id</physicalName><descripcion><![CDATA[Campos Clave de la entidad (Uso interno del sistema)]]></descripcion><type>3</type><nullable>S</nullable><documentarySearch>N</documentarySearch><multivalue>N</multivalue></field><field id=''2''><physicalName>numexp</physicalName><descripcion><![CDATA[Campo que relaciona la entidad con un número de expediente (Uso interno del sistema)]]></descripcion><type>0</type><size>30</size><nullable>S</nullable><documentarySearch>N</documentarySearch><multivalue>N</multivalue></field><field id=''3''><physicalName>TASK_NAME</physicalName><descripcion><![CDATA[Nombre de la tarea sobre la que se hace el VB]]></descripcion><type>0</type><size>250</size><nullable>S</nullable><documentarySearch>N</documentarySearch><multivalue>N</multivalue></field><field id=''4''><physicalName>VB</physicalName><descripcion><![CDATA[Visto bueno]]></descripcion><type>0</type><size>2</size><nullable>S</nullable><documentarySearch>N</documentarySearch><multivalue>N</multivalue></field><field id=''5''><physicalName>TASK_ID</physicalName><descripcion><![CDATA[ID DE LA TAREA SOBRE]]></descripcion><type>3</type><nullable>S</nullable><documentarySearch>N</documentarySearch><multivalue>N</multivalue></field><field id=''6''><physicalName>STAGE_NAME</physicalName><descripcion><![CDATA[Nombre de la fase de la tarea sobre la que se realiza el VB]]></descripcion><type>0</type><size>250</size><nullable>S</nullable><documentarySearch>N</documentarySearch><multivalue>N</multivalue></field><field id=''7''><physicalName>ID_TRAM_EXP</physicalName><descripcion><![CDATA[Id del tramite de VB creado]]></descripcion><type>3</type><nullable>S</nullable><documentarySearch>N</documentarySearch><multivalue>N</multivalue></field><field id=''8''><physicalName>OBSERVACIONES</physicalName><type>1</type><nullable>S</nullable><documentarySearch>N</documentarySearch><multivalue>N</multivalue></field><field id=''9''><physicalName>orden</physicalName><descripcion><![CDATA[Indica el orden del valor]]></descripcion><type>2</type><size>10</size><nullable>S</nullable><documentarySearch>N</documentarySearch><multivalue>N</multivalue></field></fields><indexes><index id=''6''><name><![CDATA[IND_825593649]]></name><fields><field idField=''2''/></fields></index></indexes><validations><validation id=''1''><fieldId>4</fieldId><table>SPAC_TBL_009</table><tableType>2</tableType><class/><mandatory>S</mandatory></validation></validations></entity>', '<div id="dataBlock_VISTO_BUENO" style="position: relative; height: 210px; width: 600px">
<div id="label_VISTO_BUENO:TASK_NAME" style="position: absolute; top: 10px; left: 10px; width: 110px;" class="formsTitleB">
<bean:write name="defaultForm" property="entityApp.label(VISTO_BUENO:TASK_NAME)" />:</div>
<div id="data_VISTO_BUENO:TASK_NAME" style="position: absolute; top: 10px; left: 130px;">
<ispac:htmlText property="property(VISTO_BUENO:TASK_NAME)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="80" maxlength="250" >
</ispac:htmlText>
</div>
<div id="label_VISTO_BUENO:VB" style="position: absolute; top: 35px; left: 10px; width: 110px;" class="formsTitleB">
<bean:write name="defaultForm" property="entityApp.label(VISTO_BUENO:VB)" />:</div>
<div id="data_VISTO_BUENO:VB" style="position: absolute; top: 35px; left: 130px;">
<nobr>
<ispac:htmlTextImageFrame property="property(VISTO_BUENO:VB)" readonly="true" readonlyTag="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="80" id="SEARCH_VISTO_BUENO_VB" target="workframe" action="selectValue.do?entity=SPAC_TBL_009" image="img/search-mg.gif" titleKeyLink="title.link.data.selection" imageDelete="img/borrar.gif" titleKeyImageDelete="title.delete.data.selection" styleClassDeleteLink="tdlink" confirmDeleteKey="msg.delete.data.selection" showDelete="true" showFrame="true" width="640" height="480" >
<ispac:parameter name="SEARCH_VISTO_BUENO_VB" id="property(VISTO_BUENO:VB)" property="VALOR" />
</ispac:htmlTextImageFrame>
</nobr>
</div>
<div id="label_VISTO_BUENO:TASK_ID" style="position: absolute; top: 60px; left: 10px; width: 110px;" class="formsTitleB">
<bean:write name="defaultForm" property="entityApp.label(VISTO_BUENO:TASK_ID)" />:</div>
<div id="data_VISTO_BUENO:TASK_ID" style="position: absolute; top: 60px; left: 130px;">
<ispac:htmlText property="property(VISTO_BUENO:TASK_ID)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="80" maxlength="10" >
</ispac:htmlText>
</div>
<div id="label_VISTO_BUENO:STAGE_NAME" style="position: absolute; top: 85px; left: 10px; width: 110px;" class="formsTitleB">
<bean:write name="defaultForm" property="entityApp.label(VISTO_BUENO:STAGE_NAME)" />:</div>
<div id="data_VISTO_BUENO:STAGE_NAME" style="position: absolute; top: 85px; left: 130px;">
<ispac:htmlText property="property(VISTO_BUENO:STAGE_NAME)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="80" maxlength="250" >
</ispac:htmlText>
</div>
<div id="label_VISTO_BUENO:ID_TRAM_EXP" style="position: absolute; top: 110px; left: 10px; width: 110px;" class="formsTitleB">
<bean:write name="defaultForm" property="entityApp.label(VISTO_BUENO:ID_TRAM_EXP)" />:</div>
<div id="data_VISTO_BUENO:ID_TRAM_EXP" style="position: absolute; top: 110px; left: 130px;">
<ispac:htmlText property="property(VISTO_BUENO:ID_TRAM_EXP)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="80" maxlength="10" >
</ispac:htmlText>
</div>
<div id="label_VISTO_BUENO:OBSERVACIONES" style="position: absolute; top: 135px; left: 10px; width: 110px;" class="formsTitleB">
<bean:write name="defaultForm" property="entityApp.label(VISTO_BUENO:OBSERVACIONES)" />:</div>
<div id="data_VISTO_BUENO:OBSERVACIONES" style="position: absolute; top: 135px; left: 130px;">
<ispac:htmlTextarea property="property(VISTO_BUENO:OBSERVACIONES)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" rows="2" cols="80" >
</ispac:htmlTextarea>
</div>
</div>
', getDate());
INSERT INTO spac_ct_entidades (id, tipo, nombre, campo_pk, campo_numexp, schema_expr, frm_edit, descripcion, sec_pk, definicion, frm_jsp, fecha) VALUES (206, 1, 'CAMB_TITULAR_LA', 'ID', 'NUMEXP', 'NUMEXP', 18, 'Entidad para describir los datos relativos a la licencia de apertura a traspasar.', 'SPAC_SQ_ID_CAMB_TITULAR_LA', '<entity version=''1.00''><editable>S</editable><dropable>S</dropable><status>0</status><fields><field id=''1''><physicalName>actividad</physicalName><descripcion><![CDATA[Actividad a la que se refiere la licencia]]></descripcion><type>0</type><size>254</size><nullable>S</nullable><documentarySearch>N</documentarySearch><multivalue>N</multivalue></field><field id=''2''><physicalName>emplazamiento</physicalName><descripcion><![CDATA[Situación del local en el que se desarrollará la actividad]]></descripcion><type>1</type><nullable>S</nullable><documentarySearch>N</documentarySearch><multivalue>N</multivalue></field><field id=''3''><physicalName>tasas</physicalName><descripcion><![CDATA[Tasas por el cambio de titularidad de la licencia de apertura]]></descripcion><type>4</type><size>14</size><precision>2</precision><nullable>S</nullable><documentarySearch>N</documentarySearch><multivalue>N</multivalue></field><field id=''4''><physicalName>numexp</physicalName><descripcion><![CDATA[Campo que relaciona la entidad con un número de expediente (Uso interno del sistema)]]></descripcion><type>0</type><size>30</size><nullable>S</nullable><documentarySearch>N</documentarySearch><multivalue>N</multivalue></field><field id=''5''><physicalName>id</physicalName><descripcion><![CDATA[Campos Clave de la entidad (Uso interno del sistema)]]></descripcion><type>3</type><nullable>S</nullable><documentarySearch>N</documentarySearch><multivalue>N</multivalue></field><field id=''6''><physicalName>fecha_licencia</physicalName><descripcion><![CDATA[Fecha en que fue aprobada la licencia a traspasar]]></descripcion><type>6</type><nullable>S</nullable><documentarySearch>N</documentarySearch><multivalue>N</multivalue></field><field id=''7''><physicalName>fecha_aprobacion</physicalName><descripcion><![CDATA[Fecha en la que se aprueba el traspaso de la licencia de apertura]]></descripcion><type>6</type><nullable>S</nullable><documentarySearch>N</documentarySearch><multivalue>N</multivalue></field><field id=''8''><physicalName>denominacion</physicalName><descripcion><![CDATA[Denominación del establecimiento]]></descripcion><type>0</type><size>254</size><nullable>S</nullable><documentarySearch>N</documentarySearch><multivalue>N</multivalue></field><field id=''9''><physicalName>orden</physicalName><descripcion><![CDATA[Indica el orden del valor]]></descripcion><type>2</type><size>10</size><nullable>S</nullable><documentarySearch>N</documentarySearch><multivalue>N</multivalue></field></fields></entity>', '<div id="dataBlock_CAMB_TITULAR_LA" style="position: relative; height: 210px; width: 600px">
<div id="label_CAMB_TITULAR_LA:ACTIVIDAD" style="position: absolute; top: 10px; left: 10px; width: 110px;" class="formsTitleB">
<bean:write name="defaultForm" property="entityApp.label(CAMB_TITULAR_LA:ACTIVIDAD)" />:</div>
<div id="data_CAMB_TITULAR_LA:ACTIVIDAD" style="position: absolute; top: 10px; left: 130px;">
<ispac:htmlText property="property(CAMB_TITULAR_LA:ACTIVIDAD)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="80" maxlength="254" >
</ispac:htmlText>
</div>
<div id="label_CAMB_TITULAR_LA:EMPLAZAMIENTO" style="position: absolute; top: 35px; left: 10px; width: 110px;" class="formsTitleB">
<bean:write name="defaultForm" property="entityApp.label(CAMB_TITULAR_LA:EMPLAZAMIENTO)" />:</div>
<div id="data_CAMB_TITULAR_LA:EMPLAZAMIENTO" style="position: absolute; top: 35px; left: 130px;">
<ispac:htmlTextarea property="property(CAMB_TITULAR_LA:EMPLAZAMIENTO)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" rows="2" cols="80" >
</ispac:htmlTextarea>
</div>
<div id="label_CAMB_TITULAR_LA:TASAS" style="position: absolute; top: 85px; left: 10px; width: 110px;" class="formsTitleB">
<bean:write name="defaultForm" property="entityApp.label(CAMB_TITULAR_LA:TASAS)" />:</div>
<div id="data_CAMB_TITULAR_LA:TASAS" style="position: absolute; top: 85px; left: 130px;">
<ispac:htmlText property="property(CAMB_TITULAR_LA:TASAS)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="80" maxlength="18" >
</ispac:htmlText>
</div>
<div id="label_CAMB_TITULAR_LA:FECHA_LICENCIA" style="position: absolute; top: 110px; left: 10px; width: 110px;" class="formsTitleB">
<bean:write name="defaultForm" property="entityApp.label(CAMB_TITULAR_LA:FECHA_LICENCIA)" />:</div>
<div id="data_CAMB_TITULAR_LA:FECHA_LICENCIA" style="position: absolute; top: 110px; left: 130px;">
<nobr>
<ispac:htmlTextCalendar property="property(CAMB_TITULAR_LA:FECHA_LICENCIA)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="14" maxlength="10" image=''<%= buttoncalendar %>'' format="dd/mm/yyyy" enablePast="true" >
</ispac:htmlTextCalendar>
</nobr>
</div>
<div id="label_CAMB_TITULAR_LA:FECHA_APROBACION" style="position: absolute; top: 135px; left: 10px; width: 110px;" class="formsTitleB">
<bean:write name="defaultForm" property="entityApp.label(CAMB_TITULAR_LA:FECHA_APROBACION)" />:</div>
<div id="data_CAMB_TITULAR_LA:FECHA_APROBACION" style="position: absolute; top: 135px; left: 130px;">
<nobr>
<ispac:htmlTextCalendar property="property(CAMB_TITULAR_LA:FECHA_APROBACION)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="14" maxlength="10" image=''<%= buttoncalendar %>'' format="dd/mm/yyyy" enablePast="true" >
</ispac:htmlTextCalendar>
</nobr>
</div>
<div id="label_CAMB_TITULAR_LA:DENOMINACION" style="position: absolute; top: 160px; left: 10px; width: 110px;" class="formsTitleB">
<bean:write name="defaultForm" property="entityApp.label(CAMB_TITULAR_LA:DENOMINACION)" />:</div>
<div id="data_CAMB_TITULAR_LA:DENOMINACION" style="position: absolute; top: 160px; left: 130px;">
<ispac:htmlText property="property(CAMB_TITULAR_LA:DENOMINACION)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="80" maxlength="254" >
</ispac:htmlText>
</div>
</div>
', getDate());
INSERT INTO spac_ct_entidades (id, tipo, nombre, campo_pk, campo_numexp, schema_expr, frm_edit, descripcion, sec_pk, definicion, frm_jsp, fecha) VALUES (207, 3, 'VLDTBL_CLASIF_SUELO', 'ID', NULL, NULL, NULL, 'Clasificación suelo', 'SPAC_SQ_VLDTBL_CLASIF_SUELO', '<entity version=''1.00''><editable>S</editable><dropable>S</dropable><status>0</status><fields><field id=''1''><physicalName>id</physicalName><descripcion><![CDATA[Identificador inequívoco del valor]]></descripcion><type>3</type><nullable>N</nullable><documentarySearch>N</documentarySearch><multivalue>N</multivalue></field><field id=''2''><physicalName>valor</physicalName><descripcion><![CDATA[Valor que se almacena en el campo validado]]></descripcion><type>0</type><size>10</size><nullable>S</nullable><documentarySearch>N</documentarySearch><multivalue>N</multivalue></field><field id=''3''><physicalName>sustituto</physicalName><descripcion><![CDATA[Texto que se muestra en la lista de selección]]></descripcion><type>0</type><size>100</size><nullable>S</nullable><documentarySearch>N</documentarySearch><multivalue>N</multivalue></field><field id=''4''><physicalName>vigente</physicalName><descripcion><![CDATA[Indica si el valor está en vigencia para ser seleccionado]]></descripcion><type>2</type><size>1</size><nullable>S</nullable><documentarySearch>N</documentarySearch><multivalue>N</multivalue></field><field id=''5''><physicalName>ORDEN</physicalName><descripcion><![CDATA[Indica el orden del valor]]></descripcion><type>2</type><size>10</size><nullable>S</nullable><documentarySearch>N</documentarySearch><multivalue>N</multivalue></field></fields></entity>', NULL, getDate());
INSERT INTO spac_ct_entidades (id, tipo, nombre, campo_pk, campo_numexp, schema_expr, frm_edit, descripcion, sec_pk, definicion, frm_jsp, fecha) VALUES (208, 3, 'VLDTBL_CALIF_SUELO', 'ID', NULL, NULL, NULL, 'Calificación suelo', 'SPAC_SQ_VLDTBL_CALIF_SUELO', '<entity version=''1.00''><editable>S</editable><dropable>S</dropable><status>0</status><fields><field id=''1''><physicalName>id</physicalName><descripcion><![CDATA[Identificador inequívoco del valor]]></descripcion><type>3</type><nullable>N</nullable><documentarySearch>N</documentarySearch><multivalue>N</multivalue></field><field id=''2''><physicalName>valor</physicalName><descripcion><![CDATA[Valor que se almacena en el campo validado]]></descripcion><type>0</type><size>10</size><nullable>S</nullable><documentarySearch>N</documentarySearch><multivalue>N</multivalue></field><field id=''3''><physicalName>sustituto</physicalName><descripcion><![CDATA[Texto que se muestra en la lista de selección]]></descripcion><type>0</type><size>100</size><nullable>S</nullable><documentarySearch>N</documentarySearch><multivalue>N</multivalue></field><field id=''4''><physicalName>vigente</physicalName><descripcion><![CDATA[Indica si el valor está en vigencia para ser seleccionado]]></descripcion><type>2</type><size>1</size><nullable>S</nullable><documentarySearch>N</documentarySearch><multivalue>N</multivalue></field><field id=''5''><physicalName>ORDEN</physicalName><descripcion><![CDATA[Indica el orden del valor]]></descripcion><type>2</type><size>10</size><nullable>S</nullable><documentarySearch>N</documentarySearch><multivalue>N</multivalue></field></fields></entity>', NULL, getDate());
INSERT INTO spac_ct_entidades (id, tipo, nombre, campo_pk, campo_numexp, schema_expr, frm_edit, descripcion, sec_pk, definicion, frm_jsp, fecha) VALUES (209, 3, 'TIPO_TECNICO', 'ID', NULL, NULL, NULL, 'Tipos de técnico', 'SPAC_SQ_TIPO_TECNICO', '<entity version=''1.00''><editable>S</editable><dropable>S</dropable><status>0</status><fields><field id=''1''><physicalName>id</physicalName><descripcion><![CDATA[Identificador inequívoco del valor]]></descripcion><type>3</type><nullable>N</nullable><documentarySearch>N</documentarySearch><multivalue>N</multivalue></field><field id=''2''><physicalName>valor</physicalName><descripcion><![CDATA[Valor que se almacena en el campo validado]]></descripcion><type>0</type><size>10</size><nullable>S</nullable><documentarySearch>N</documentarySearch><multivalue>N</multivalue></field><field id=''3''><physicalName>sustituto</physicalName><descripcion><![CDATA[Texto que se muestra en la lista de selección]]></descripcion><type>0</type><size>64</size><nullable>S</nullable><documentarySearch>N</documentarySearch><multivalue>N</multivalue></field><field id=''4''><physicalName>vigente</physicalName><descripcion><![CDATA[Indica si el valor está en vigencia para ser seleccionado]]></descripcion><type>2</type><size>1</size><nullable>S</nullable><documentarySearch>N</documentarySearch><multivalue>N</multivalue></field><field id=''5''><physicalName>ORDEN</physicalName><descripcion><![CDATA[Indica el orden del valor]]></descripcion><type>2</type><size>10</size><nullable>S</nullable><documentarySearch>N</documentarySearch><multivalue>N</multivalue></field></fields></entity>', NULL, getDate());
INSERT INTO spac_ct_entidades (id, tipo, nombre, campo_pk, campo_numexp, schema_expr, frm_edit, descripcion, sec_pk, definicion, frm_jsp, fecha) VALUES (210, 1, 'OBRA_MENOR', 'ID', 'NUMEXP', 'NUMEXP', 21, 'Datos Obra Menor', 'SPAC_SQ_ID_OBRA_MENOR', '<entity version=''1.00''><editable>S</editable><dropable>S</dropable><status>0</status><fields><field id=''16''><physicalName>id</physicalName><descripcion><![CDATA[Campos Clave de la entidad (Uso interno del sistema)]]></descripcion><type>3</type><nullable>N</nullable><documentarySearch>N</documentarySearch><multivalue>N</multivalue></field><field id=''15''><physicalName>numexp</physicalName><descripcion><![CDATA[Campo que relaciona la entidad con un número de expediente (Uso interno del sistema)]]></descripcion><type>0</type><size>30</size><nullable>S</nullable><documentarySearch>N</documentarySearch><multivalue>N</multivalue></field><field id=''1''><physicalName>obra_solicitada</physicalName><descripcion><![CDATA[Obra solicitada]]></descripcion><type>1</type><size>1024</size><nullable>S</nullable><documentarySearch>N</documentarySearch><multivalue>N</multivalue></field><field id=''2''><physicalName>situacion_obra</physicalName><descripcion><![CDATA[Situación de la obra]]></descripcion><type>0</type><size>500</size><nullable>S</nullable><documentarySearch>N</documentarySearch><multivalue>N</multivalue></field><field id=''3''><physicalName>promotor</physicalName><descripcion><![CDATA[Promotor]]></descripcion><type>0</type><size>100</size><nullable>S</nullable><documentarySearch>N</documentarySearch><multivalue>N</multivalue></field><field id=''4''><physicalName>director_obra</physicalName><descripcion><![CDATA[Director de obra]]></descripcion><type>0</type><size>100</size><nullable>S</nullable><documentarySearch>N</documentarySearch><multivalue>N</multivalue></field><field id=''5''><physicalName>director_ejecucion</physicalName><descripcion><![CDATA[Director de ejecución de la obra]]></descripcion><type>0</type><size>100</size><nullable>S</nullable><documentarySearch>N</documentarySearch><multivalue>N</multivalue></field><field id=''6''><physicalName>presupuesto_promotor</physicalName><descripcion><![CDATA[Presupuesto promotor]]></descripcion><type>4</type><size>14</size><precision>2</precision><nullable>S</nullable><documentarySearch>N</documentarySearch><multivalue>N</multivalue></field><field id=''7''><physicalName>presupuesto_técnico_municipal</physicalName><descripcion><![CDATA[Presupuesto del técnico municipal]]></descripcion><type>4</type><size>14</size><precision>2</precision><nullable>S</nullable><documentarySearch>N</documentarySearch><multivalue>N</multivalue></field><field id=''8''><physicalName>autor_proyecto</physicalName><descripcion><![CDATA[Técnico autor del proyecto]]></descripcion><type>0</type><size>100</size><nullable>S</nullable><documentarySearch>N</documentarySearch><multivalue>N</multivalue></field><field id=''9''><physicalName>uso_obra</physicalName><descripcion><![CDATA[Uso al que se destinará el objeto de la obra]]></descripcion><type>0</type><size>200</size><nullable>S</nullable><documentarySearch>N</documentarySearch><multivalue>N</multivalue></field><field id=''10''><physicalName>clasificacion_suelo</physicalName><descripcion><![CDATA[Clasificación suelo]]></descripcion><type>0</type><size>100</size><nullable>S</nullable><documentarySearch>N</documentarySearch><multivalue>N</multivalue></field><field id=''11''><physicalName>cualificacion_suelo</physicalName><descripcion><![CDATA[Cualificación suelo]]></descripcion><type>0</type><size>100</size><nullable>S</nullable><documentarySearch>N</documentarySearch><multivalue>N</multivalue></field><field id=''12''><physicalName>importe_tasas</physicalName><descripcion><![CDATA[Importe tasas]]></descripcion><type>4</type><size>14</size><precision>2</precision><nullable>S</nullable><documentarySearch>N</documentarySearch><multivalue>N</multivalue></field><field id=''13''><physicalName>importe_icio</physicalName><descripcion><![CDATA[Importe ICIO]]></descripcion><type>4</type><size>14</size><precision>2</precision><nullable>S</nullable><documentarySearch>N</documentarySearch><multivalue>N</multivalue></field><field id=''14''><physicalName>total_liquidacion</physicalName><descripcion><![CDATA[Total liquidación]]></descripcion><type>4</type><size>14</size><precision>2</precision><nullable>S</nullable><documentarySearch>N</documentarySearch><multivalue>N</multivalue></field><field id=''17''><physicalName>orden</physicalName><descripcion><![CDATA[Indica el orden del valor]]></descripcion><type>2</type><size>10</size><nullable>S</nullable><documentarySearch>N</documentarySearch><multivalue>N</multivalue></field></fields><validations><validation id=''1''><fieldId>10</fieldId><table>VLDTBL_CLASIF_SUELO</table><tableType>3</tableType><class/><mandatory>N</mandatory></validation><validation id=''2''><fieldId>11</fieldId><table>VLDTBL_CALIF_SUELO</table><tableType>3</tableType><class/><mandatory>N</mandatory></validation></validations></entity>', '<div id="dataBlock_OBRA_MENOR" style="position: relative; height: 410px; width: 600px">
<div id="label_OBRA_MENOR:OBRA_SOLICITADA" style="position: absolute; top: 10px; left: 10px; width: 110px;" class="formsTitleB">
<bean:write name="defaultForm" property="entityApp.label(OBRA_MENOR:OBRA_SOLICITADA)" />:</div>
<div id="data_OBRA_MENOR:OBRA_SOLICITADA" style="position: absolute; top: 10px; left: 130px;">
<ispac:htmlTextarea property="property(OBRA_MENOR:OBRA_SOLICITADA)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" rows="2" cols="80" >
</ispac:htmlTextarea>
</div>
<div id="label_OBRA_MENOR:SITUACION_OBRA" style="position: absolute; top: 60px; left: 10px; width: 110px;" class="formsTitleB">
<bean:write name="defaultForm" property="entityApp.label(OBRA_MENOR:SITUACION_OBRA)" />:</div>
<div id="data_OBRA_MENOR:SITUACION_OBRA" style="position: absolute; top: 60px; left: 130px;">
<ispac:htmlText property="property(OBRA_MENOR:SITUACION_OBRA)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="80" maxlength="500" >
</ispac:htmlText>
</div>
<div id="label_OBRA_MENOR:PROMOTOR" style="position: absolute; top: 85px; left: 10px; width: 110px;" class="formsTitleB">
<bean:write name="defaultForm" property="entityApp.label(OBRA_MENOR:PROMOTOR)" />:</div>
<div id="data_OBRA_MENOR:PROMOTOR" style="position: absolute; top: 85px; left: 130px;">
<ispac:htmlText property="property(OBRA_MENOR:PROMOTOR)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="80" maxlength="100" >
</ispac:htmlText>
</div>
<div id="label_OBRA_MENOR:DIRECTOR_OBRA" style="position: absolute; top: 110px; left: 10px; width: 110px;" class="formsTitleB">
<bean:write name="defaultForm" property="entityApp.label(OBRA_MENOR:DIRECTOR_OBRA)" />:</div>
<div id="data_OBRA_MENOR:DIRECTOR_OBRA" style="position: absolute; top: 110px; left: 130px;">
<ispac:htmlText property="property(OBRA_MENOR:DIRECTOR_OBRA)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="80" maxlength="100" >
</ispac:htmlText>
</div>
<div id="label_OBRA_MENOR:DIRECTOR_EJECUCION" style="position: absolute; top: 135px; left: 10px; width: 110px;" class="formsTitleB">
<bean:write name="defaultForm" property="entityApp.label(OBRA_MENOR:DIRECTOR_EJECUCION)" />:</div>
<div id="data_OBRA_MENOR:DIRECTOR_EJECUCION" style="position: absolute; top: 135px; left: 130px;">
<ispac:htmlText property="property(OBRA_MENOR:DIRECTOR_EJECUCION)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="80" maxlength="100" >
</ispac:htmlText>
</div>
<div id="label_OBRA_MENOR:PRESUPUESTO_PROMOTOR" style="position: absolute; top: 160px; left: 10px; width: 110px;" class="formsTitleB">
<bean:write name="defaultForm" property="entityApp.label(OBRA_MENOR:PRESUPUESTO_PROMOTOR)" />:</div>
<div id="data_OBRA_MENOR:PRESUPUESTO_PROMOTOR" style="position: absolute; top: 160px; left: 130px;">
<ispac:htmlText property="property(OBRA_MENOR:PRESUPUESTO_PROMOTOR)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="80" maxlength="18" >
</ispac:htmlText>
</div>
<div id="label_OBRA_MENOR:PRESUPUESTO_TÉCNICO_MUNICIPAL" style="position: absolute; top: 185px; left: 10px; width: 110px;" class="formsTitleB">
<bean:write name="defaultForm" property="entityApp.label(OBRA_MENOR:PRESUPUESTO_TÉCNICO_MUNICIPAL)" />:</div>
<div id="data_OBRA_MENOR:PRESUPUESTO_TÉCNICO_MUNICIPAL" style="position: absolute; top: 185px; left: 130px;">
<ispac:htmlText property="property(OBRA_MENOR:PRESUPUESTO_TÉCNICO_MUNICIPAL)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="80" maxlength="18" >
</ispac:htmlText>
</div>
<div id="label_OBRA_MENOR:AUTOR_PROYECTO" style="position: absolute; top: 210px; left: 10px; width: 110px;" class="formsTitleB">
<bean:write name="defaultForm" property="entityApp.label(OBRA_MENOR:AUTOR_PROYECTO)" />:</div>
<div id="data_OBRA_MENOR:AUTOR_PROYECTO" style="position: absolute; top: 210px; left: 130px;">
<ispac:htmlText property="property(OBRA_MENOR:AUTOR_PROYECTO)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="80" maxlength="100" >
</ispac:htmlText>
</div>
<div id="label_OBRA_MENOR:USO_OBRA" style="position: absolute; top: 235px; left: 10px; width: 110px;" class="formsTitleB">
<bean:write name="defaultForm" property="entityApp.label(OBRA_MENOR:USO_OBRA)" />:</div>
<div id="data_OBRA_MENOR:USO_OBRA" style="position: absolute; top: 235px; left: 130px;">
<ispac:htmlText property="property(OBRA_MENOR:USO_OBRA)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="80" maxlength="200" >
</ispac:htmlText>
</div>
<div id="label_OBRA_MENOR:CLASIFICACION_SUELO" style="position: absolute; top: 260px; left: 10px; width: 110px;" class="formsTitleB">
<bean:write name="defaultForm" property="entityApp.label(OBRA_MENOR:CLASIFICACION_SUELO)" />:</div>
<div id="data_OBRA_MENOR:CLASIFICACION_SUELO" style="position: absolute; top: 260px; left: 130px;">
<nobr>
<html:hidden property="property(OBRA_MENOR:CLASIFICACION_SUELO)" />
<ispac:htmlTextImageFrame property="property(CLASIFICACION_SUELO_VLDTBL_CLASIF_SUELO:SUSTITUTO)" readonly="true" readonlyTag="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="80" id="SEARCH_OBRA_MENOR_CLASIFICACION_SUELO" target="workframe" action="selectSubstitute.do?entity=" image="img/search-mg.gif" titleKeyLink="title.link.data.selection" imageDelete="img/borrar.gif" titleKeyImageDelete="title.delete.data.selection" styleClassDeleteLink="tdlink" confirmDeleteKey="msg.delete.data.selection" showDelete="true" showFrame="true" width="640" height="480" >
<ispac:parameter name="SEARCH_OBRA_MENOR_CLASIFICACION_SUELO" id="property(OBRA_MENOR:CLASIFICACION_SUELO)" property="VALOR" />
<ispac:parameter name="SEARCH_OBRA_MENOR_CLASIFICACION_SUELO" id="property(CLASIFICACION_SUELO_VLDTBL_CLASIF_SUELO:SUSTITUTO)" property="SUSTITUTO" />
</ispac:htmlTextImageFrame>
</nobr>
</div>
<div id="label_OBRA_MENOR:CUALIFICACION_SUELO" style="position: absolute; top: 285px; left: 10px; width: 110px;" class="formsTitleB">
<bean:write name="defaultForm" property="entityApp.label(OBRA_MENOR:CUALIFICACION_SUELO)" />:</div>
<div id="data_OBRA_MENOR:CUALIFICACION_SUELO" style="position: absolute; top: 285px; left: 130px;">
<nobr>
<html:hidden property="property(OBRA_MENOR:CUALIFICACION_SUELO)" />
<ispac:htmlTextImageFrame property="property(CUALIFICACION_SUELO_VLDTBL_CALIF_SUELO:SUSTITUTO)" readonly="true" readonlyTag="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="80" id="SEARCH_OBRA_MENOR_CUALIFICACION_SUELO" target="workframe" action="selectSubstitute.do?entity=" image="img/search-mg.gif" titleKeyLink="title.link.data.selection" imageDelete="img/borrar.gif" titleKeyImageDelete="title.delete.data.selection" styleClassDeleteLink="tdlink" confirmDeleteKey="msg.delete.data.selection" showDelete="true" showFrame="true" width="640" height="480" >
<ispac:parameter name="SEARCH_OBRA_MENOR_CUALIFICACION_SUELO" id="property(OBRA_MENOR:CUALIFICACION_SUELO)" property="VALOR" />
<ispac:parameter name="SEARCH_OBRA_MENOR_CUALIFICACION_SUELO" id="property(CUALIFICACION_SUELO_VLDTBL_CALIF_SUELO:SUSTITUTO)" property="SUSTITUTO" />
</ispac:htmlTextImageFrame>
</nobr>
</div>
<div id="label_OBRA_MENOR:IMPORTE_TASAS" style="position: absolute; top: 310px; left: 10px; width: 110px;" class="formsTitleB">
<bean:write name="defaultForm" property="entityApp.label(OBRA_MENOR:IMPORTE_TASAS)" />:</div>
<div id="data_OBRA_MENOR:IMPORTE_TASAS" style="position: absolute; top: 310px; left: 130px;">
<ispac:htmlText property="property(OBRA_MENOR:IMPORTE_TASAS)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="80" maxlength="18" >
</ispac:htmlText>
</div>
<div id="label_OBRA_MENOR:IMPORTE_ICIO" style="position: absolute; top: 335px; left: 10px; width: 110px;" class="formsTitleB">
<bean:write name="defaultForm" property="entityApp.label(OBRA_MENOR:IMPORTE_ICIO)" />:</div>
<div id="data_OBRA_MENOR:IMPORTE_ICIO" style="position: absolute; top: 335px; left: 130px;">
<ispac:htmlText property="property(OBRA_MENOR:IMPORTE_ICIO)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="80" maxlength="18" >
</ispac:htmlText>
</div>
<div id="label_OBRA_MENOR:TOTAL_LIQUIDACION" style="position: absolute; top: 360px; left: 10px; width: 110px;" class="formsTitleB">
<bean:write name="defaultForm" property="entityApp.label(OBRA_MENOR:TOTAL_LIQUIDACION)" />:</div>
<div id="data_OBRA_MENOR:TOTAL_LIQUIDACION" style="position: absolute; top: 360px; left: 130px;">
<ispac:htmlText property="property(OBRA_MENOR:TOTAL_LIQUIDACION)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="80" maxlength="18" >
</ispac:htmlText>
</div>
</div>
', getDate());
INSERT INTO spac_ct_entidades (id, tipo, nombre, campo_pk, campo_numexp, schema_expr, frm_edit, descripcion, sec_pk, definicion, frm_jsp, fecha) VALUES (211, 1, 'CERT_URBANISTICO', 'ID', 'NUMEXP', 'NUMEXP', 23, 'Datos de Certificado Urbanístico', 'SPAC_SQ_ID_CERT_URBANISTICO', '<entity version=''1.00''><editable>S</editable><dropable>S</dropable><status>0</status><fields><field id=''1''><physicalName>fecha_emision</physicalName><descripcion><![CDATA[Fecha de emisión del certificado]]></descripcion><type>6</type><nullable>S</nullable><documentarySearch>N</documentarySearch><multivalue>N</multivalue></field><field id=''2''><physicalName>situacion</physicalName><descripcion><![CDATA[Situación de la finca/parcela/edificio sobre el que se solicita la certificación]]></descripcion><type>0</type><size>500</size><nullable>S</nullable><documentarySearch>N</documentarySearch><multivalue>N</multivalue></field><field id=''3''><physicalName>tecnico_nombre</physicalName><descripcion><![CDATA[Nombre del técnico que realiza el informe de certificación]]></descripcion><type>0</type><size>100</size><nullable>S</nullable><documentarySearch>N</documentarySearch><multivalue>N</multivalue></field><field id=''4''><physicalName>tecnico_cargo</physicalName><descripcion><![CDATA[Cargo del técnico que realiza el informe de certificación]]></descripcion><type>0</type><size>50</size><nullable>S</nullable><documentarySearch>N</documentarySearch><multivalue>N</multivalue></field><field id=''5''><physicalName>numexp</physicalName><descripcion><![CDATA[Campo que relaciona la entidad con un número de expediente (Uso interno del sistema)]]></descripcion><type>0</type><size>30</size><nullable>S</nullable><documentarySearch>N</documentarySearch><multivalue>N</multivalue></field><field id=''6''><physicalName>id</physicalName><descripcion><![CDATA[Campos Clave de la entidad (Uso interno del sistema)]]></descripcion><type>3</type><nullable>N</nullable><documentarySearch>N</documentarySearch><multivalue>N</multivalue></field><field id=''7''><physicalName>orden</physicalName><descripcion><![CDATA[Indica el orden del valor]]></descripcion><type>2</type><size>10</size><nullable>S</nullable><documentarySearch>N</documentarySearch><multivalue>N</multivalue></field></fields><validations><validation id=''1''><fieldId>4</fieldId><table>TIPO_TECNICO</table><tableType>3</tableType><class/><mandatory>S</mandatory></validation></validations></entity>', '<div id="dataBlock_CERT_URBANISTICO" style="position: relative; height: 135px; width: 600px">
<div id="label_CERT_URBANISTICO:FECHA_EMISION" style="position: absolute; top: 10px; left: 10px; width: 110px;" class="formsTitleB">
<bean:write name="defaultForm" property="entityApp.label(CERT_URBANISTICO:FECHA_EMISION)" />:</div>
<div id="data_CERT_URBANISTICO:FECHA_EMISION" style="position: absolute; top: 10px; left: 130px;">
<nobr>
<ispac:htmlTextCalendar property="property(CERT_URBANISTICO:FECHA_EMISION)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="14" maxlength="10" image=''<%= buttoncalendar %>'' format="dd/mm/yyyy" enablePast="true" >
</ispac:htmlTextCalendar>
</nobr>
</div>
<div id="label_CERT_URBANISTICO:SITUACION" style="position: absolute; top: 35px; left: 10px; width: 110px;" class="formsTitleB">
<bean:write name="defaultForm" property="entityApp.label(CERT_URBANISTICO:SITUACION)" />:</div>
<div id="data_CERT_URBANISTICO:SITUACION" style="position: absolute; top: 35px; left: 130px;">
<ispac:htmlText property="property(CERT_URBANISTICO:SITUACION)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="80" maxlength="500" >
</ispac:htmlText>
</div>
<div id="label_CERT_URBANISTICO:TECNICO_NOMBRE" style="position: absolute; top: 60px; left: 10px; width: 110px;" class="formsTitleB">
<bean:write name="defaultForm" property="entityApp.label(CERT_URBANISTICO:TECNICO_NOMBRE)" />:</div>
<div id="data_CERT_URBANISTICO:TECNICO_NOMBRE" style="position: absolute; top: 60px; left: 130px;">
<ispac:htmlText property="property(CERT_URBANISTICO:TECNICO_NOMBRE)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="80" maxlength="100" >
</ispac:htmlText>
</div>
<div id="label_CERT_URBANISTICO:TECNICO_CARGO" style="position: absolute; top: 85px; left: 10px; width: 110px;" class="formsTitleB">
<bean:write name="defaultForm" property="entityApp.label(CERT_URBANISTICO:TECNICO_CARGO)" />:</div>
<div id="data_CERT_URBANISTICO:TECNICO_CARGO" style="position: absolute; top: 85px; left: 130px;">
<nobr>
<html:hidden property="property(CERT_URBANISTICO:TECNICO_CARGO)" />
<ispac:htmlTextImageFrame property="property(TECNICO_CARGO_TIPO_TECNICO:SUSTITUTO)" readonly="true" readonlyTag="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="80" id="SEARCH_CERT_URBANISTICO_TECNICO_CARGO" target="workframe" action="selectSubstitute.do?entity=TIPO_TECNICO" image="img/search-mg.gif" titleKeyLink="title.link.data.selection" imageDelete="img/borrar.gif" titleKeyImageDelete="title.delete.data.selection" styleClassDeleteLink="tdlink" confirmDeleteKey="msg.delete.data.selection" showDelete="true" showFrame="true" width="640" height="480" >
<ispac:parameter name="SEARCH_CERT_URBANISTICO_TECNICO_CARGO" id="property(CERT_URBANISTICO:TECNICO_CARGO)" property="VALOR" />
<ispac:parameter name="SEARCH_CERT_URBANISTICO_TECNICO_CARGO" id="property(TECNICO_CARGO_TIPO_TECNICO:SUSTITUTO)" property="SUSTITUTO" />
</ispac:htmlTextImageFrame>
</nobr>
</div>
</div>
', getDate());
INSERT INTO spac_ct_entidades (id, tipo, nombre, campo_pk, campo_numexp, schema_expr, frm_edit, descripcion, sec_pk, definicion, frm_jsp, fecha) VALUES (212, 2, 'TIPO_CONTRATO', 'ID', NULL, NULL, NULL, 'Tabla de clasificación para contratación administrativa', 'SPAC_SQ_TIPO_CONTRATO', '<entity version=''1.00''><editable>S</editable><dropable>S</dropable><status>0</status><fields><field id=''1''><physicalName>id</physicalName><descripcion><![CDATA[Identificador inequívoco del valor]]></descripcion><type>3</type><nullable>N</nullable><documentarySearch>N</documentarySearch><multivalue>N</multivalue></field><field id=''2''><physicalName>valor</physicalName><descripcion><![CDATA[Valor que se almacena en el campo validado]]></descripcion><type>0</type><size>100</size><nullable>S</nullable><documentarySearch>N</documentarySearch><multivalue>N</multivalue></field><field id=''3''><physicalName>vigente</physicalName><descripcion><![CDATA[Indica si el valor está en vigencia para ser seleccionado]]></descripcion><type>2</type><size>1</size><nullable>S</nullable><documentarySearch>N</documentarySearch><multivalue>N</multivalue></field><field id=''4''><physicalName>ORDEN</physicalName><descripcion><![CDATA[Indica el orden del valor]]></descripcion><type>2</type><size>10</size><nullable>S</nullable><documentarySearch>N</documentarySearch><multivalue>N</multivalue></field></fields></entity>', NULL, getDate());
INSERT INTO spac_ct_entidades (id, tipo, nombre, campo_pk, campo_numexp, schema_expr, frm_edit, descripcion, sec_pk, definicion, frm_jsp, fecha) VALUES (213, 2, 'FORMA_ADJUDICACION', 'ID', NULL, NULL, NULL, 'Tabla que indica la forma de adjudicar expedientes de contratación', 'SPAC_SQ_FORMA_ADJUDICACION', '<entity version=''1.00''><editable>S</editable><dropable>S</dropable><status>0</status><fields><field id=''1''><physicalName>id</physicalName><descripcion><![CDATA[Identificador inequívoco del valor]]></descripcion><type>3</type><nullable>N</nullable><documentarySearch>N</documentarySearch><multivalue>N</multivalue></field><field id=''2''><physicalName>valor</physicalName><descripcion><![CDATA[Valor que se almacena en el campo validado]]></descripcion><type>0</type><size>100</size><nullable>S</nullable><documentarySearch>N</documentarySearch><multivalue>N</multivalue></field><field id=''3''><physicalName>vigente</physicalName><descripcion><![CDATA[Indica si el valor está en vigencia para ser seleccionado]]></descripcion><type>2</type><size>1</size><nullable>S</nullable><documentarySearch>N</documentarySearch><multivalue>N</multivalue></field><field id=''4''><physicalName>ORDEN</physicalName><descripcion><![CDATA[Indica el orden del valor]]></descripcion><type>2</type><size>10</size><nullable>S</nullable><documentarySearch>N</documentarySearch><multivalue>N</multivalue></field></fields></entity>', NULL, getDate());
INSERT INTO spac_ct_entidades (id, tipo, nombre, campo_pk, campo_numexp, schema_expr, frm_edit, descripcion, sec_pk, definicion, frm_jsp, fecha) VALUES (214, 1, 'LOTES', 'ID', 'NUMEXP', 'NUMEXP', 24, 'Lotes en la contratación administrativa', 'SPAC_SQ_ID_LOTES', '<entity version=''1.00''><editable>S</editable><dropable>S</dropable><status>0</status><fields><field id=''4''><physicalName>id</physicalName><descripcion><![CDATA[Campos Clave de la entidad (Uso interno del sistema)]]></descripcion><type>3</type><nullable>N</nullable><documentarySearch>N</documentarySearch><multivalue>N</multivalue></field><field id=''3''><physicalName>numexp</physicalName><descripcion><![CDATA[Campo que relaciona la entidad con un número de expediente (Uso interno del sistema)]]></descripcion><type>0</type><size>30</size><nullable>S</nullable><documentarySearch>N</documentarySearch><multivalue>N</multivalue></field><field id=''1''><physicalName>nombre</physicalName><descripcion><![CDATA[Nombre o descripción del lote]]></descripcion><type>0</type><size>250</size><nullable>S</nullable><documentarySearch>N</documentarySearch><multivalue>N</multivalue></field><field id=''2''><physicalName>presupuesto</physicalName><descripcion><![CDATA[Presupuesto máximo]]></descripcion><type>4</type><size>20</size><precision>2</precision><nullable>S</nullable><documentarySearch>N</documentarySearch><multivalue>N</multivalue></field><field id=''5''><physicalName>orden</physicalName><descripcion><![CDATA[Indica el orden del valor]]></descripcion><type>2</type><size>10</size><nullable>S</nullable><documentarySearch>N</documentarySearch><multivalue>N</multivalue></field></fields></entity>', '<div id="dataBlock_CONC_LOTES" style="position: relative; height: 85px; width: 600px">
<div id="label_CONC_LOTES:NOMBRE" style="position: absolute; top: 10px; left: 10px; width: 110px;" class="formsTitleB">
<bean:write name="defaultForm" property="entityApp.label(CONC_LOTES:NOMBRE)" />:</div>
<div id="data_CONC_LOTES:NOMBRE" style="position: absolute; top: 10px; left: 130px;">
<ispac:htmlText property="property(CONC_LOTES:NOMBRE)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="80" maxlength="250" >
</ispac:htmlText>
</div>
<div id="label_CONC_LOTES:PRESUPUESTO" style="position: absolute; top: 35px; left: 10px; width: 110px;" class="formsTitleB">
<bean:write name="defaultForm" property="entityApp.label(CONC_LOTES:PRESUPUESTO)" />:</div>
<div id="data_CONC_LOTES:PRESUPUESTO" style="position: absolute; top: 35px; left: 130px;">
<ispac:htmlText property="property(CONC_LOTES:PRESUPUESTO)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="80" maxlength="26" >
</ispac:htmlText>
</div>
</div>
', getDate());
INSERT INTO spac_ct_entidades (id, tipo, nombre, campo_pk, campo_numexp, schema_expr, frm_edit, descripcion, sec_pk, definicion, frm_jsp, fecha) VALUES (215, 1, 'CONTRATO', 'ID', 'NUMEXP', 'NUMEXP', 25, 'Entidad para datos específicos de contratación administrativa.', 'SPAC_SQ_ID_CONTRATO', '<entity version=''1.00''><editable>S</editable><dropable>S</dropable><status>0</status><fields><field id=''14''><physicalName>id</physicalName><descripcion><![CDATA[Campos Clave de la entidad (Uso interno del sistema)]]></descripcion><type>3</type><nullable>N</nullable><documentarySearch>N</documentarySearch><multivalue>N</multivalue></field><field id=''13''><physicalName>numexp</physicalName><descripcion><![CDATA[Campo que relaciona la entidad con un número de expediente (Uso interno del sistema)]]></descripcion><type>0</type><size>30</size><nullable>S</nullable><documentarySearch>N</documentarySearch><multivalue>N</multivalue></field><field id=''1''><physicalName>tipo</physicalName><descripcion><![CDATA[Tipo de contrato]]></descripcion><type>0</type><size>100</size><nullable>S</nullable><documentarySearch>N</documentarySearch><multivalue>N</multivalue></field><field id=''2''><physicalName>forma_adjudicacion</physicalName><descripcion><![CDATA[Forma de adjudicación]]></descripcion><type>0</type><size>100</size><nullable>S</nullable><documentarySearch>N</documentarySearch><multivalue>N</multivalue></field><field id=''3''><physicalName>presupuesto_maximo</physicalName><descripcion><![CDATA[Presupuesto máximo]]></descripcion><type>4</type><size>20</size><precision>2</precision><nullable>S</nullable><documentarySearch>N</documentarySearch><multivalue>N</multivalue></field><field id=''4''><physicalName>aplicacion</physicalName><descripcion><![CDATA[Aplicación presupuestaria]]></descripcion><type>0</type><size>250</size><nullable>S</nullable><documentarySearch>N</documentarySearch><multivalue>N</multivalue></field><field id=''5''><physicalName>precio_adjudicacion</physicalName><descripcion><![CDATA[Precio de adjudicación]]></descripcion><type>4</type><size>20</size><precision>2</precision><nullable>S</nullable><documentarySearch>N</documentarySearch><multivalue>N</multivalue></field><field id=''6''><physicalName>plazo_ejecucion</physicalName><descripcion><![CDATA[Plazo de ejecución]]></descripcion><type>3</type><size>10</size><nullable>S</nullable><documentarySearch>N</documentarySearch><multivalue>N</multivalue></field><field id=''7''><physicalName>Ud_plazo</physicalName><descripcion><![CDATA[Unidades de plazo]]></descripcion><type>0</type><size>1</size><nullable>S</nullable><documentarySearch>N</documentarySearch><multivalue>N</multivalue></field><field id=''8''><physicalName>garantia_provisional</physicalName><descripcion><![CDATA[Garantía provisional]]></descripcion><type>4</type><size>20</size><precision>2</precision><nullable>S</nullable><documentarySearch>N</documentarySearch><multivalue>N</multivalue></field><field id=''9''><physicalName>garantia_definitiva</physicalName><descripcion><![CDATA[Garantía definitiva]]></descripcion><type>4</type><size>20</size><precision>2</precision><nullable>S</nullable><documentarySearch>N</documentarySearch><multivalue>N</multivalue></field><field id=''10''><physicalName>clasificacion</physicalName><descripcion><![CDATA[Clasificación]]></descripcion><type>0</type><size>500</size><nullable>S</nullable><documentarySearch>N</documentarySearch><multivalue>N</multivalue></field><field id=''11''><physicalName>fecha_fin_ejecucion</physicalName><descripcion><![CDATA[Fecha fin de ejecución]]></descripcion><type>6</type><size>10</size><nullable>S</nullable><documentarySearch>N</documentarySearch><multivalue>N</multivalue></field><field id=''12''><physicalName>fecha_fin_garantia</physicalName><descripcion><![CDATA[Fecha fin de garantía]]></descripcion><type>6</type><size>10</size><nullable>S</nullable><documentarySearch>N</documentarySearch><multivalue>N</multivalue></field><field id=''15''><physicalName>orden</physicalName><descripcion><![CDATA[Indica el orden del valor]]></descripcion><type>2</type><size>10</size><nullable>S</nullable><documentarySearch>N</documentarySearch><multivalue>N</multivalue></field></fields><validations><validation id=''1''><fieldId>1</fieldId><table>TIPO_CONTRATO</table><tableType>2</tableType><class/><mandatory>N</mandatory></validation><validation id=''2''><fieldId>2</fieldId><table>FORMA_ADJUDICACION</table><tableType>2</tableType><class/><mandatory>N</mandatory></validation><validation id=''3''><fieldId>7</fieldId><table>SPAC_TBL_001</table><tableType>3</tableType><class/><mandatory>N</mandatory></validation></validations></entity>', '<div id="dataBlock_CONC_CONTRATO" style="position: relative; height: 335px; width: 600px">
<div id="label_CONC_CONTRATO:TIPO" style="position: absolute; top: 10px; left: 10px; width: 110px;" class="formsTitleB">
<bean:write name="defaultForm" property="entityApp.label(CONC_CONTRATO:TIPO)" />:</div>
<div id="data_CONC_CONTRATO:TIPO" style="position: absolute; top: 10px; left: 130px;">
<nobr>
<ispac:htmlTextImageFrame property="property(CONC_CONTRATO:TIPO)" readonly="true" readonlyTag="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="80" id="SEARCH_CONC_CONTRATO_TIPO" target="workframe" action="selectValue.do?entity=TIPO_CONTRATO" image="img/search-mg.gif" titleKeyLink="title.link.data.selection" imageDelete="img/borrar.gif" titleKeyImageDelete="title.delete.data.selection" styleClassDeleteLink="tdlink" confirmDeleteKey="msg.delete.data.selection" showDelete="true" showFrame="true" width="640" height="480" >
<ispac:parameter name="SEARCH_CONC_CONTRATO_TIPO" id="property(CONC_CONTRATO:TIPO)" property="VALOR" />
</ispac:htmlTextImageFrame>
</nobr>
</div>
<div id="label_CONC_CONTRATO:FORMA_ADJUDICACION" style="position: absolute; top: 35px; left: 10px; width: 110px;" class="formsTitleB">
<bean:write name="defaultForm" property="entityApp.label(CONC_CONTRATO:FORMA_ADJUDICACION)" />:</div>
<div id="data_CONC_CONTRATO:FORMA_ADJUDICACION" style="position: absolute; top: 35px; left: 130px;">
<nobr>
<ispac:htmlTextImageFrame property="property(CONC_CONTRATO:FORMA_ADJUDICACION)" readonly="true" readonlyTag="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="80" id="SEARCH_CONC_CONTRATO_FORMA_ADJUDICACION" target="workframe" action="selectValue.do?entity=FORMA_ADJUDICACION" image="img/search-mg.gif" titleKeyLink="title.link.data.selection" imageDelete="img/borrar.gif" titleKeyImageDelete="title.delete.data.selection" styleClassDeleteLink="tdlink" confirmDeleteKey="msg.delete.data.selection" showDelete="true" showFrame="true" width="640" height="480" >
<ispac:parameter name="SEARCH_CONC_CONTRATO_FORMA_ADJUDICACION" id="property(CONC_CONTRATO:FORMA_ADJUDICACION)" property="VALOR" />
</ispac:htmlTextImageFrame>
</nobr>
</div>
<div id="label_CONC_CONTRATO:PRESUPUESTO_MAXIMO" style="position: absolute; top: 60px; left: 10px; width: 110px;" class="formsTitleB">
<bean:write name="defaultForm" property="entityApp.label(CONC_CONTRATO:PRESUPUESTO_MAXIMO)" />:</div>
<div id="data_CONC_CONTRATO:PRESUPUESTO_MAXIMO" style="position: absolute; top: 60px; left: 130px;">
<ispac:htmlText property="property(CONC_CONTRATO:PRESUPUESTO_MAXIMO)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="80" maxlength="26" >
</ispac:htmlText>
</div>
<div id="label_CONC_CONTRATO:APLICACION" style="position: absolute; top: 85px; left: 10px; width: 110px;" class="formsTitleB">
<bean:write name="defaultForm" property="entityApp.label(CONC_CONTRATO:APLICACION)" />:</div>
<div id="data_CONC_CONTRATO:APLICACION" style="position: absolute; top: 85px; left: 130px;">
<ispac:htmlText property="property(CONC_CONTRATO:APLICACION)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="80" maxlength="250" >
</ispac:htmlText>
</div>
<div id="label_CONC_CONTRATO:PRECIO_ADJUDICACION" style="position: absolute; top: 110px; left: 10px; width: 110px;" class="formsTitleB">
<bean:write name="defaultForm" property="entityApp.label(CONC_CONTRATO:PRECIO_ADJUDICACION)" />:</div>
<div id="data_CONC_CONTRATO:PRECIO_ADJUDICACION" style="position: absolute; top: 110px; left: 130px;">
<ispac:htmlText property="property(CONC_CONTRATO:PRECIO_ADJUDICACION)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="80" maxlength="26" >
</ispac:htmlText>
</div>
<div id="label_CONC_CONTRATO:PLAZO_EJECUCION" style="position: absolute; top: 135px; left: 10px; width: 110px;" class="formsTitleB">
<bean:write name="defaultForm" property="entityApp.label(CONC_CONTRATO:PLAZO_EJECUCION)" />:</div>
<div id="data_CONC_CONTRATO:PLAZO_EJECUCION" style="position: absolute; top: 135px; left: 130px;">
<ispac:htmlText property="property(CONC_CONTRATO:PLAZO_EJECUCION)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="80" maxlength="10" >
</ispac:htmlText>
</div>
<div id="label_CONC_CONTRATO:UD_PLAZO" style="position: absolute; top: 160px; left: 10px; width: 110px;" class="formsTitleB">
<bean:write name="defaultForm" property="entityApp.label(CONC_CONTRATO:UD_PLAZO)" />:</div>
<div id="data_CONC_CONTRATO:UD_PLAZO" style="position: absolute; top: 160px; left: 130px;">
<nobr>
<html:hidden property="property(CONC_CONTRATO:UD_PLAZO)" />
<ispac:htmlTextImageFrame property="property(UD_PLAZO_SPAC_TBL_001:SUSTITUTO)" readonly="true" readonlyTag="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="80" id="SEARCH_CONC_CONTRATO_UD_PLAZO" target="workframe" action="selectSubstitute.do?entity=SPAC_TBL_001" image="img/search-mg.gif" titleKeyLink="title.link.data.selection" imageDelete="img/borrar.gif" titleKeyImageDelete="title.delete.data.selection" styleClassDeleteLink="tdlink" confirmDeleteKey="msg.delete.data.selection" showDelete="true" showFrame="true" width="640" height="480" >
<ispac:parameter name="SEARCH_CONC_CONTRATO_UD_PLAZO" id="property(CONC_CONTRATO:UD_PLAZO)" property="VALOR" />
<ispac:parameter name="SEARCH_CONC_CONTRATO_UD_PLAZO" id="property(UD_PLAZO_SPAC_TBL_001:SUSTITUTO)" property="SUSTITUTO" />
</ispac:htmlTextImageFrame>
</nobr>
</div>
<div id="label_CONC_CONTRATO:GARANTIA_PROVISIONAL" style="position: absolute; top: 185px; left: 10px; width: 110px;" class="formsTitleB">
<bean:write name="defaultForm" property="entityApp.label(CONC_CONTRATO:GARANTIA_PROVISIONAL)" />:</div>
<div id="data_CONC_CONTRATO:GARANTIA_PROVISIONAL" style="position: absolute; top: 185px; left: 130px;">
<ispac:htmlText property="property(CONC_CONTRATO:GARANTIA_PROVISIONAL)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="80" maxlength="26" >
</ispac:htmlText>
</div>
<div id="label_CONC_CONTRATO:GARANTIA_DEFINITIVA" style="position: absolute; top: 210px; left: 10px; width: 110px;" class="formsTitleB">
<bean:write name="defaultForm" property="entityApp.label(CONC_CONTRATO:GARANTIA_DEFINITIVA)" />:</div>
<div id="data_CONC_CONTRATO:GARANTIA_DEFINITIVA" style="position: absolute; top: 210px; left: 130px;">
<ispac:htmlText property="property(CONC_CONTRATO:GARANTIA_DEFINITIVA)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="80" maxlength="26" >
</ispac:htmlText>
</div>
<div id="label_CONC_CONTRATO:CLASIFICACION" style="position: absolute; top: 235px; left: 10px; width: 110px;" class="formsTitleB">
<bean:write name="defaultForm" property="entityApp.label(CONC_CONTRATO:CLASIFICACION)" />:</div>
<div id="data_CONC_CONTRATO:CLASIFICACION" style="position: absolute; top: 235px; left: 130px;">
<ispac:htmlText property="property(CONC_CONTRATO:CLASIFICACION)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="80" maxlength="500" >
</ispac:htmlText>
</div>
<div id="label_CONC_CONTRATO:FECHA_FIN_EJECUCION" style="position: absolute; top: 260px; left: 10px; width: 110px;" class="formsTitleB">
<bean:write name="defaultForm" property="entityApp.label(CONC_CONTRATO:FECHA_FIN_EJECUCION)" />:</div>
<div id="data_CONC_CONTRATO:FECHA_FIN_EJECUCION" style="position: absolute; top: 260px; left: 130px;">
<nobr>
<ispac:htmlTextCalendar property="property(CONC_CONTRATO:FECHA_FIN_EJECUCION)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="14" maxlength="10" image=''<%= buttoncalendar %>'' format="dd/mm/yyyy" enablePast="true" >
</ispac:htmlTextCalendar>
</nobr>
</div>
<div id="label_CONC_CONTRATO:FECHA_FIN_GARANTIA" style="position: absolute; top: 285px; left: 10px; width: 110px;" class="formsTitleB">
<bean:write name="defaultForm" property="entityApp.label(CONC_CONTRATO:FECHA_FIN_GARANTIA)" />:</div>
<div id="data_CONC_CONTRATO:FECHA_FIN_GARANTIA" style="position: absolute; top: 285px; left: 130px;">
<nobr>
<ispac:htmlTextCalendar property="property(CONC_CONTRATO:FECHA_FIN_GARANTIA)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="14" maxlength="10" image=''<%= buttoncalendar %>'' format="dd/mm/yyyy" enablePast="true" >
</ispac:htmlTextCalendar>
</nobr>
</div>
</div>
', getDate());
INSERT INTO spac_ct_entidades (id, tipo, nombre, campo_pk, campo_numexp, schema_expr, frm_edit, descripcion, sec_pk, definicion, frm_jsp, fecha) VALUES (216, 2, 'VLDTBL_SAN_GRADO', 'ID', NULL, NULL, NULL, 'Debida proporcionalidad entre la gravedad del hecho costitutivo de infracción y la sanción.', 'SPAC_SQ_ID_VLDTBL_SAN_GRADO', '<entity version=''1.00''><editable>S</editable><dropable>S</dropable><status>0</status><fields><field id=''1''><physicalName>id</physicalName><descripcion><![CDATA[Identificador inequívoco del valor]]></descripcion><type>3</type><nullable>N</nullable><documentarySearch>N</documentarySearch><multivalue>N</multivalue></field><field id=''2''><physicalName>valor</physicalName><descripcion><![CDATA[Valor que se almacena en el campo validado]]></descripcion><type>0</type><size>100</size><nullable>S</nullable><documentarySearch>N</documentarySearch><multivalue>N</multivalue></field><field id=''3''><physicalName>vigente</physicalName><descripcion><![CDATA[Indica si el valor está en vigencia para ser seleccionado]]></descripcion><type>2</type><size>1</size><nullable>S</nullable><documentarySearch>N</documentarySearch><multivalue>N</multivalue></field><field id=''4''><physicalName>ORDEN</physicalName><descripcion><![CDATA[Indica el orden del valor]]></descripcion><type>2</type><size>10</size><nullable>S</nullable><documentarySearch>N</documentarySearch><multivalue>N</multivalue></field></fields></entity>', NULL, getDate());
INSERT INTO spac_ct_entidades (id, tipo, nombre, campo_pk, campo_numexp, schema_expr, frm_edit, descripcion, sec_pk, definicion, frm_jsp, fecha) VALUES (217, 2, 'VLDTBL_SAN_CALIFICACION', 'ID', NULL, NULL, NULL, 'Calificación de la gravedad de la infracción', 'SPAC_SQ_ID_VLDTBL_SAN_CALIFICA', '<entity version=''1.00''><editable>S</editable><dropable>S</dropable><status>0</status><fields><field id=''1''><physicalName>id</physicalName><descripcion><![CDATA[Identificador inequívoco del valor]]></descripcion><type>3</type><nullable>N</nullable><documentarySearch>N</documentarySearch><multivalue>N</multivalue></field><field id=''2''><physicalName>valor</physicalName><descripcion><![CDATA[Valor que se almacena en el campo validado]]></descripcion><type>0</type><size>100</size><nullable>S</nullable><documentarySearch>N</documentarySearch><multivalue>N</multivalue></field><field id=''3''><physicalName>vigente</physicalName><descripcion><![CDATA[Indica si el valor está en vigencia para ser seleccionado]]></descripcion><type>2</type><size>1</size><nullable>S</nullable><documentarySearch>N</documentarySearch><multivalue>N</multivalue></field><field id=''4''><physicalName>ORDEN</physicalName><descripcion><![CDATA[Indica el orden del valor]]></descripcion><type>2</type><size>10</size><nullable>S</nullable><documentarySearch>N</documentarySearch><multivalue>N</multivalue></field></fields></entity>', NULL, getDate());
INSERT INTO spac_ct_entidades (id, tipo, nombre, campo_pk, campo_numexp, schema_expr, frm_edit, descripcion, sec_pk, definicion, frm_jsp, fecha) VALUES (218, 1, 'SANCION', 'ID', 'NUMEXP', 'NUMEXP', 26, 'Datos específicos de un expediente sancionador', 'SPAC_SQ_ID_SANCION', '<entity version=''1.00''><editable>S</editable><dropable>S</dropable><status>0</status><fields><field id=''1''><physicalName>instructor</physicalName><descripcion><![CDATA[Instructor]]></descripcion><type>0</type><size>254</size><nullable>S</nullable><documentarySearch>N</documentarySearch><multivalue>N</multivalue></field><field id=''2''><physicalName>calificacion</physicalName><descripcion><![CDATA[Calificación]]></descripcion><type>0</type><size>100</size><nullable>S</nullable><documentarySearch>N</documentarySearch><multivalue>N</multivalue></field><field id=''3''><physicalName>importe</physicalName><descripcion><![CDATA[Importe sanción]]></descripcion><type>4</type><size>20</size><precision>2</precision><nullable>S</nullable><documentarySearch>N</documentarySearch><multivalue>N</multivalue></field><field id=''4''><physicalName>grado</physicalName><descripcion><![CDATA[Grado infracción]]></descripcion><type>0</type><size>100</size><nullable>S</nullable><documentarySearch>N</documentarySearch><multivalue>N</multivalue></field><field id=''5''><physicalName>infraccion</physicalName><descripcion><![CDATA[Descripción de la infracción]]></descripcion><type>1</type><nullable>S</nullable><documentarySearch>N</documentarySearch><multivalue>N</multivalue></field><field id=''6''><physicalName>hechos</physicalName><descripcion><![CDATA[Hechos denunciados]]></descripcion><type>1</type><nullable>S</nullable><documentarySearch>N</documentarySearch><multivalue>N</multivalue></field><field id=''7''><physicalName>actuacion</physicalName><descripcion><![CDATA[Actuación]]></descripcion><type>1</type><nullable>S</nullable><documentarySearch>N</documentarySearch><multivalue>N</multivalue></field><field id=''8''><physicalName>numexp</physicalName><descripcion><![CDATA[Campo que relaciona la entidad con un número de expediente (Uso interno del sistema)]]></descripcion><type>0</type><size>30</size><nullable>S</nullable><documentarySearch>N</documentarySearch><multivalue>N</multivalue></field><field id=''9''><physicalName>id</physicalName><descripcion><![CDATA[Campos Clave de la entidad (Uso interno del sistema)]]></descripcion><type>3</type><nullable>S</nullable><documentarySearch>N</documentarySearch><multivalue>N</multivalue></field><field id=''10''><physicalName>orden</physicalName><descripcion><![CDATA[Indica el orden del valor]]></descripcion><type>2</type><size>10</size><nullable>S</nullable><documentarySearch>N</documentarySearch><multivalue>N</multivalue></field></fields><validations><validation id=''1''><fieldId>2</fieldId><table>VLDTBL_SAN_CALIFICACION</table><tableType>2</tableType><class/><mandatory>N</mandatory></validation><validation id=''2''><fieldId>4</fieldId><table>VLDTBL_SAN_GRADO</table><tableType>2</tableType><class/><mandatory>N</mandatory></validation></validations></entity>', '<div id="dataBlock_CONC_SANCION" style="position: relative; height: 285px; width: 600px">
<div id="label_CONC_SANCION:INSTRUCTOR" style="position: absolute; top: 10px; left: 10px; width: 110px;" class="formsTitleB">
<bean:write name="defaultForm" property="entityApp.label(CONC_SANCION:INSTRUCTOR)" />:</div>
<div id="data_CONC_SANCION:INSTRUCTOR" style="position: absolute; top: 10px; left: 130px;">
<ispac:htmlText property="property(CONC_SANCION:INSTRUCTOR)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="80" maxlength="254" >
</ispac:htmlText>
</div>
<div id="label_CONC_SANCION:CALIFICACION" style="position: absolute; top: 35px; left: 10px; width: 110px;" class="formsTitleB">
<bean:write name="defaultForm" property="entityApp.label(CONC_SANCION:CALIFICACION)" />:</div>
<div id="data_CONC_SANCION:CALIFICACION" style="position: absolute; top: 35px; left: 130px;">
<nobr>
<ispac:htmlTextImageFrame property="property(CONC_SANCION:CALIFICACION)" readonly="true" readonlyTag="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="80" id="SEARCH_CONC_SANCION_CALIFICACION" target="workframe" action="selectValue.do?entity=CONC_VLDTBL_SAN_CALIFICACION" image="img/search-mg.gif" titleKeyLink="title.link.data.selection" imageDelete="img/borrar.gif" titleKeyImageDelete="title.delete.data.selection" styleClassDeleteLink="tdlink" confirmDeleteKey="msg.delete.data.selection" showDelete="true" showFrame="true" width="640" height="480" >
<ispac:parameter name="SEARCH_CONC_SANCION_CALIFICACION" id="property(CONC_SANCION:CALIFICACION)" property="VALOR" />
</ispac:htmlTextImageFrame>
</nobr>
</div>
<div id="label_CONC_SANCION:IMPORTE" style="position: absolute; top: 60px; left: 10px; width: 110px;" class="formsTitleB">
<bean:write name="defaultForm" property="entityApp.label(CONC_SANCION:IMPORTE)" />:</div>
<div id="data_CONC_SANCION:IMPORTE" style="position: absolute; top: 60px; left: 130px;">
<ispac:htmlText property="property(CONC_SANCION:IMPORTE)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="80" maxlength="26" >
</ispac:htmlText>
</div>
<div id="label_CONC_SANCION:GRADO" style="position: absolute; top: 85px; left: 10px; width: 110px;" class="formsTitleB">
<bean:write name="defaultForm" property="entityApp.label(CONC_SANCION:GRADO)" />:</div>
<div id="data_CONC_SANCION:GRADO" style="position: absolute; top: 85px; left: 130px;">
<nobr>
<ispac:htmlTextImageFrame property="property(CONC_SANCION:GRADO)" readonly="true" readonlyTag="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="80" id="SEARCH_CONC_SANCION_GRADO" target="workframe" action="selectValue.do?entity=CONC_VLDTBL_SAN_GRADO" image="img/search-mg.gif" titleKeyLink="title.link.data.selection" imageDelete="img/borrar.gif" titleKeyImageDelete="title.delete.data.selection" styleClassDeleteLink="tdlink" confirmDeleteKey="msg.delete.data.selection" showDelete="true" showFrame="true" width="640" height="480" >
<ispac:parameter name="SEARCH_CONC_SANCION_GRADO" id="property(CONC_SANCION:GRADO)" property="VALOR" />
</ispac:htmlTextImageFrame>
</nobr>
</div>
<div id="label_CONC_SANCION:INFRACCION" style="position: absolute; top: 110px; left: 10px; width: 110px;" class="formsTitleB">
<bean:write name="defaultForm" property="entityApp.label(CONC_SANCION:INFRACCION)" />:</div>
<div id="data_CONC_SANCION:INFRACCION" style="position: absolute; top: 110px; left: 130px;">
<ispac:htmlTextarea property="property(CONC_SANCION:INFRACCION)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" rows="2" cols="80" >
</ispac:htmlTextarea>
</div>
<div id="label_CONC_SANCION:HECHOS" style="position: absolute; top: 160px; left: 10px; width: 110px;" class="formsTitleB">
<bean:write name="defaultForm" property="entityApp.label(CONC_SANCION:HECHOS)" />:</div>
<div id="data_CONC_SANCION:HECHOS" style="position: absolute; top: 160px; left: 130px;">
<ispac:htmlTextarea property="property(CONC_SANCION:HECHOS)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" rows="2" cols="80" >
</ispac:htmlTextarea>
</div>
<div id="label_CONC_SANCION:ACTUACION" style="position: absolute; top: 210px; left: 10px; width: 110px;" class="formsTitleB">
<bean:write name="defaultForm" property="entityApp.label(CONC_SANCION:ACTUACION)" />:</div>
<div id="data_CONC_SANCION:ACTUACION" style="position: absolute; top: 210px; left: 130px;">
<ispac:htmlTextarea property="property(CONC_SANCION:ACTUACION)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" rows="2" cols="80" >
</ispac:htmlTextarea>
</div>
</div>
', getDate());
INSERT INTO spac_ct_entidades (id, tipo, nombre, campo_pk, campo_numexp, schema_expr, frm_edit, descripcion, sec_pk, definicion, frm_jsp, fecha) VALUES (219, 3, 'CLASIFICACION_ACTIVIDADES', 'ID', NULL, NULL, NULL, 'Tabla de validación para la Clasificación de Actividades', 'SPAC_SQ_ID_CLASIFICACION_ACTIV', '<entity version=''1.00''><editable>S</editable><dropable>S</dropable><status>0</status><fields><field id=''1''><physicalName>id</physicalName><descripcion><![CDATA[Identificador inequívoco del valor]]></descripcion><type>3</type><nullable>N</nullable><documentarySearch>N</documentarySearch><multivalue>N</multivalue></field><field id=''2''><physicalName>valor</physicalName><descripcion><![CDATA[Valor que se almacena en el campo validado]]></descripcion><type>0</type><size>20</size><nullable>S</nullable><documentarySearch>N</documentarySearch><multivalue>N</multivalue></field><field id=''3''><physicalName>sustituto</physicalName><descripcion><![CDATA[Texto que se muestra en la lista de selección]]></descripcion><type>0</type><size>500</size><nullable>S</nullable><documentarySearch>N</documentarySearch><multivalue>N</multivalue></field><field id=''4''><physicalName>vigente</physicalName><descripcion><![CDATA[Indica si el valor está en vigencia para ser seleccionado]]></descripcion><type>2</type><size>1</size><nullable>S</nullable><documentarySearch>N</documentarySearch><multivalue>N</multivalue></field><field id=''5''><physicalName>ORDEN</physicalName><descripcion><![CDATA[Indica el orden del valor]]></descripcion><type>2</type><size>10</size><nullable>S</nullable><documentarySearch>N</documentarySearch><multivalue>N</multivalue></field></fields></entity>', NULL, getDate());
INSERT INTO spac_ct_entidades (id, tipo, nombre, campo_pk, campo_numexp, schema_expr, frm_edit, descripcion, sec_pk, definicion, frm_jsp, fecha) VALUES (220, 1, 'ACTIVIDAD_CLASIFICADA', 'ID', 'NUMEXP', 'NUMEXP', 27, 'Entidad para la descripción de Actividades Clasificadas', 'SPAC_SQ_ID_ACTIVIDAD_CLASIFICA', '<entity version=''1.00''><editable>S</editable><dropable>S</dropable><status>0</status><fields><field id=''1''><physicalName>actividad</physicalName><descripcion><![CDATA[Actividad]]></descripcion><type>0</type><size>254</size><nullable>S</nullable><documentarySearch>N</documentarySearch><multivalue>N</multivalue></field><field id=''2''><physicalName>clasificacion</physicalName><descripcion><![CDATA[Clasificación]]></descripcion><type>0</type><size>254</size><nullable>S</nullable><documentarySearch>N</documentarySearch><multivalue>N</multivalue></field><field id=''3''><physicalName>emplazamiento</physicalName><descripcion><![CDATA[Emplazamiento]]></descripcion><type>1</type><nullable>S</nullable><documentarySearch>N</documentarySearch><multivalue>N</multivalue></field><field id=''4''><physicalName>tasas</physicalName><descripcion><![CDATA[Tasas]]></descripcion><type>4</type><size>14</size><precision>2</precision><nullable>S</nullable><documentarySearch>N</documentarySearch><multivalue>N</multivalue></field><field id=''5''><physicalName>numexp</physicalName><descripcion><![CDATA[Campo que relaciona la entidad con un número de expediente (Uso interno del sistema)]]></descripcion><type>0</type><size>30</size><nullable>S</nullable><documentarySearch>N</documentarySearch><multivalue>N</multivalue></field><field id=''6''><physicalName>id</physicalName><descripcion><![CDATA[Campos Clave de la entidad (Uso interno del sistema)]]></descripcion><type>3</type><nullable>S</nullable><documentarySearch>N</documentarySearch><multivalue>N</multivalue></field><field id=''7''><physicalName>orden</physicalName><descripcion><![CDATA[Indica el orden del valor]]></descripcion><type>2</type><size>10</size><nullable>S</nullable><documentarySearch>N</documentarySearch><multivalue>N</multivalue></field></fields><validations><validation id=''1''><fieldId>2</fieldId><table>CLASIFICACION_ACTIVIDADES</table><tableType>3</tableType><class/><mandatory>N</mandatory></validation></validations></entity>', '<div id="dataBlock_ACTIVIDAD_CLASIFICADA" style="position: relative; height: 160px; width: 600px">
<div id="label_ACTIVIDAD_CLASIFICADA:ACTIVIDAD" style="position: absolute; top: 10px; left: 10px; width: 110px;" class="formsTitleB">
<bean:write name="defaultForm" property="entityApp.label(ACTIVIDAD_CLASIFICADA:ACTIVIDAD)" />:</div>
<div id="data_ACTIVIDAD_CLASIFICADA:ACTIVIDAD" style="position: absolute; top: 10px; left: 130px;">
<ispac:htmlText property="property(ACTIVIDAD_CLASIFICADA:ACTIVIDAD)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="80" maxlength="254" >
</ispac:htmlText>
</div>
<div id="label_ACTIVIDAD_CLASIFICADA:CLASIFICACION" style="position: absolute; top: 35px; left: 10px; width: 110px;" class="formsTitleB">
<bean:write name="defaultForm" property="entityApp.label(ACTIVIDAD_CLASIFICADA:CLASIFICACION)" />:</div>
<div id="data_ACTIVIDAD_CLASIFICADA:CLASIFICACION" style="position: absolute; top: 35px; left: 130px;">
<nobr>
<html:hidden property="property(ACTIVIDAD_CLASIFICADA:CLASIFICACION)" />
<ispac:htmlTextImageFrame property="property(CLASIFICACION_CLASIFICACION_ACTIVIDADES:SUSTITUTO)" readonly="true" readonlyTag="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="80" id="SEARCH_ACTIVIDAD_CLASIFICADA_CLASIFICACION" target="workframe" action="selectSubstitute.do?entity=CLASIFICACION_ACTIVIDADES" image="img/search-mg.gif" titleKeyLink="title.link.data.selection" imageDelete="img/borrar.gif" titleKeyImageDelete="title.delete.data.selection" styleClassDeleteLink="tdlink" confirmDeleteKey="msg.delete.data.selection" showDelete="true" showFrame="true" width="640" height="480" >
<ispac:parameter name="SEARCH_ACTIVIDAD_CLASIFICADA_CLASIFICACION" id="property(ACTIVIDAD_CLASIFICADA:CLASIFICACION)" property="VALOR" />
<ispac:parameter name="SEARCH_ACTIVIDAD_CLASIFICADA_CLASIFICACION" id="property(CLASIFICACION_CLASIFICACION_ACTIVIDADES:SUSTITUTO)" property="SUSTITUTO" />
</ispac:htmlTextImageFrame>
</nobr>
</div>
<div id="label_ACTIVIDAD_CLASIFICADA:EMPLAZAMIENTO" style="position: absolute; top: 60px; left: 10px; width: 110px;" class="formsTitleB">
<bean:write name="defaultForm" property="entityApp.label(ACTIVIDAD_CLASIFICADA:EMPLAZAMIENTO)" />:</div>
<div id="data_ACTIVIDAD_CLASIFICADA:EMPLAZAMIENTO" style="position: absolute; top: 60px; left: 130px;">
<ispac:htmlTextarea property="property(ACTIVIDAD_CLASIFICADA:EMPLAZAMIENTO)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" rows="2" cols="80" >
</ispac:htmlTextarea>
</div>
<div id="label_ACTIVIDAD_CLASIFICADA:TASAS" style="position: absolute; top: 110px; left: 10px; width: 110px;" class="formsTitleB">
<bean:write name="defaultForm" property="entityApp.label(ACTIVIDAD_CLASIFICADA:TASAS)" />:</div>
<div id="data_ACTIVIDAD_CLASIFICADA:TASAS" style="position: absolute; top: 110px; left: 130px;">
<ispac:htmlText property="property(ACTIVIDAD_CLASIFICADA:TASAS)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="80" maxlength="18" >
</ispac:htmlText>
</div>
</div>
', getDate());
INSERT INTO spac_ct_entidades (id, tipo, nombre, campo_pk, campo_numexp, schema_expr, frm_edit, descripcion, sec_pk, definicion, frm_jsp, fecha) VALUES (221, 1, 'ACTIVIDAD_NO_CLASIFICADA', 'ID', 'NUMEXP', 'NUMEXP', 28, 'Entidad para la descripción de Actividades No Clasificadas', 'SPAC_SQ_ID_ACTIVIDAD_NO_CLASIF', '<entity version=''1.00''><editable>S</editable><dropable>S</dropable><status>0</status><fields><field id=''1''><physicalName>actividad</physicalName><descripcion><![CDATA[Actividad a la que se refiere la licencia]]></descripcion><type>0</type><size>254</size><nullable>S</nullable><documentarySearch>N</documentarySearch><multivalue>N</multivalue></field><field id=''2''><physicalName>emplazamiento</physicalName><descripcion><![CDATA[Situación del local en el que se desarrollará la actividad]]></descripcion><type>1</type><nullable>S</nullable><documentarySearch>N</documentarySearch><multivalue>N</multivalue></field><field id=''3''><physicalName>tasas</physicalName><descripcion><![CDATA[Tasas de la licencia de actividad]]></descripcion><type>4</type><size>14</size><precision>2</precision><nullable>S</nullable><documentarySearch>N</documentarySearch><multivalue>N</multivalue></field><field id=''4''><physicalName>numexp</physicalName><descripcion><![CDATA[Campo que relaciona la entidad con un número de expediente (Uso interno del sistema)]]></descripcion><type>0</type><size>30</size><nullable>S</nullable><documentarySearch>N</documentarySearch><multivalue>N</multivalue></field><field id=''5''><physicalName>id</physicalName><descripcion><![CDATA[Campos Clave de la entidad (Uso interno del sistema)]]></descripcion><type>3</type><nullable>N</nullable><documentarySearch>N</documentarySearch><multivalue>N</multivalue></field><field id=''6''><physicalName>orden</physicalName><descripcion><![CDATA[Indica el orden del valor]]></descripcion><type>2</type><size>10</size><nullable>S</nullable><documentarySearch>N</documentarySearch><multivalue>N</multivalue></field></fields></entity>', '<div id="dataBlock_ACTIVIDAD_NO_CLASIFICADA" style="position: relative; height: 135px; width: 600px">
<div id="label_ACTIVIDAD_NO_CLASIFICADA:ACTIVIDAD" style="position: absolute; top: 10px; left: 10px; width: 110px;" class="formsTitleB">
<bean:write name="defaultForm" property="entityApp.label(ACTIVIDAD_NO_CLASIFICADA:ACTIVIDAD)" />:</div>
<div id="data_ACTIVIDAD_NO_CLASIFICADA:ACTIVIDAD" style="position: absolute; top: 10px; left: 130px;">
<ispac:htmlText property="property(ACTIVIDAD_NO_CLASIFICADA:ACTIVIDAD)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="80" maxlength="254" >
</ispac:htmlText>
</div>
<div id="label_ACTIVIDAD_NO_CLASIFICADA:EMPLAZAMIENTO" style="position: absolute; top: 35px; left: 10px; width: 110px;" class="formsTitleB">
<bean:write name="defaultForm" property="entityApp.label(ACTIVIDAD_NO_CLASIFICADA:EMPLAZAMIENTO)" />:</div>
<div id="data_ACTIVIDAD_NO_CLASIFICADA:EMPLAZAMIENTO" style="position: absolute; top: 35px; left: 130px;">
<ispac:htmlTextarea property="property(ACTIVIDAD_NO_CLASIFICADA:EMPLAZAMIENTO)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" rows="2" cols="80" >
</ispac:htmlTextarea>
</div>
<div id="label_ACTIVIDAD_NO_CLASIFICADA:TASAS" style="position: absolute; top: 85px; left: 10px; width: 110px;" class="formsTitleB">
<bean:write name="defaultForm" property="entityApp.label(ACTIVIDAD_NO_CLASIFICADA:TASAS)" />:</div>
<div id="data_ACTIVIDAD_NO_CLASIFICADA:TASAS" style="position: absolute; top: 85px; left: 130px;">
<ispac:htmlText property="property(ACTIVIDAD_NO_CLASIFICADA:TASAS)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="80" maxlength="18" >
</ispac:htmlText>
</div>
</div>
', getDate());
INSERT INTO spac_ct_entidades (id, tipo, nombre, campo_pk, campo_numexp, schema_expr, frm_edit, descripcion, sec_pk, definicion, frm_jsp, fecha) VALUES (222, 2, 'TIPO_VADO', 'ID', NULL, NULL, NULL, 'Tipos de vado', 'SPAC_SQ_TIPO_VADO', '<entity version=''1.00''><editable>S</editable><dropable>S</dropable><status>0</status><fields><field id=''1''><physicalName>id</physicalName><descripcion><![CDATA[Identificador inequívoco del valor]]></descripcion><type>3</type><nullable>N</nullable><documentarySearch>N</documentarySearch><multivalue>N</multivalue></field><field id=''2''><physicalName>valor</physicalName><descripcion><![CDATA[Valor que se almacena en el campo validado]]></descripcion><type>0</type><size>100</size><nullable>S</nullable><documentarySearch>N</documentarySearch><multivalue>N</multivalue></field><field id=''3''><physicalName>vigente</physicalName><descripcion><![CDATA[Indica si el valor está en vigencia para ser seleccionado]]></descripcion><type>2</type><size>1</size><nullable>S</nullable><documentarySearch>N</documentarySearch><multivalue>N</multivalue></field><field id=''4''><physicalName>ORDEN</physicalName><descripcion><![CDATA[Indica el orden del valor]]></descripcion><type>2</type><size>10</size><nullable>S</nullable><documentarySearch>N</documentarySearch><multivalue>N</multivalue></field></fields></entity>', NULL, getDate());
INSERT INTO spac_ct_entidades (id, tipo, nombre, campo_pk, campo_numexp, schema_expr, frm_edit, descripcion, sec_pk, definicion, frm_jsp, fecha) VALUES (223, 2, 'REBAJE_ACERA', 'ID', NULL, NULL, NULL, 'Tipos de Rebaje en Aceras', 'SPAC_SQ_REBAJE_ACERA', '<entity version=''1.00''><editable>S</editable><dropable>S</dropable><status>0</status><fields><field id=''1''><physicalName>id</physicalName><descripcion><![CDATA[Identificador inequívoco del valor]]></descripcion><type>3</type><nullable>N</nullable><documentarySearch>N</documentarySearch><multivalue>N</multivalue></field><field id=''2''><physicalName>valor</physicalName><descripcion><![CDATA[Valor que se almacena en el campo validado]]></descripcion><type>0</type><size>100</size><nullable>S</nullable><documentarySearch>N</documentarySearch><multivalue>N</multivalue></field><field id=''3''><physicalName>vigente</physicalName><descripcion><![CDATA[Indica si el valor está en vigencia para ser seleccionado]]></descripcion><type>2</type><size>1</size><nullable>S</nullable><documentarySearch>N</documentarySearch><multivalue>N</multivalue></field><field id=''4''><physicalName>ORDEN</physicalName><descripcion><![CDATA[Indica el orden del valor]]></descripcion><type>2</type><size>10</size><nullable>S</nullable><documentarySearch>N</documentarySearch><multivalue>N</multivalue></field></fields></entity>', NULL, getDate());
INSERT INTO spac_ct_entidades (id, tipo, nombre, campo_pk, campo_numexp, schema_expr, frm_edit, descripcion, sec_pk, definicion, frm_jsp, fecha) VALUES (224, 1, 'VADO', 'ID', 'NUMEXP', 'NUMEXP', 29, 'Datos específicos de un Vado (reserva de espacio para entrada/salida de vehículos)', 'SPAC_SQ_ID_VADO', '<entity version=''1.00''><editable>S</editable><dropable>S</dropable><status>0</status><fields><field id=''1''><physicalName>NumLicencia</physicalName><descripcion><![CDATA[Número de la Licencia/Placa del Vado]]></descripcion><type>0</type><size>100</size><nullable>S</nullable><documentarySearch>N</documentarySearch><multivalue>N</multivalue></field><field id=''2''><physicalName>Ubicacion</physicalName><descripcion><![CDATA[Ubicación del Vado]]></descripcion><type>0</type><size>250</size><nullable>S</nullable><documentarySearch>N</documentarySearch><multivalue>N</multivalue></field><field id=''3''><physicalName>Tipo</physicalName><descripcion><![CDATA[Tipo de Vado]]></descripcion><type>0</type><size>100</size><nullable>S</nullable><documentarySearch>N</documentarySearch><multivalue>N</multivalue></field><field id=''4''><physicalName>LargoEntrada</physicalName><descripcion><![CDATA[Largo de la entrada (en metros)]]></descripcion><type>4</type><size>14</size><precision>2</precision><nullable>S</nullable><documentarySearch>N</documentarySearch><multivalue>N</multivalue></field><field id=''5''><physicalName>AnchoAcera</physicalName><descripcion><![CDATA[Ancho de la acera (en metros)]]></descripcion><type>4</type><size>14</size><precision>2</precision><nullable>S</nullable><documentarySearch>N</documentarySearch><multivalue>N</multivalue></field><field id=''6''><physicalName>SuperficieAcera</physicalName><descripcion><![CDATA[Superficie de la acera (en metros cuadrados)]]></descripcion><type>4</type><size>14</size><precision>2</precision><nullable>S</nullable><documentarySearch>N</documentarySearch><multivalue>N</multivalue></field><field id=''7''><physicalName>SuperficieLocal</physicalName><descripcion><![CDATA[Superficie del local (en metros cuadrados)]]></descripcion><type>4</type><size>14</size><precision>2</precision><nullable>S</nullable><documentarySearch>N</documentarySearch><multivalue>N</multivalue></field><field id=''8''><physicalName>Actividad</physicalName><descripcion><![CDATA[Actividad o uso del local]]></descripcion><type>0</type><size>250</size><nullable>S</nullable><documentarySearch>N</documentarySearch><multivalue>N</multivalue></field><field id=''9''><physicalName>Rebaje</physicalName><descripcion><![CDATA[Rebaje de la acera]]></descripcion><type>0</type><size>100</size><nullable>S</nullable><documentarySearch>N</documentarySearch><multivalue>N</multivalue></field><field id=''10''><physicalName>Tasas</physicalName><descripcion><![CDATA[Tasas de la licencia]]></descripcion><type>4</type><size>14</size><precision>2</precision><nullable>S</nullable><documentarySearch>N</documentarySearch><multivalue>N</multivalue></field><field id=''11''><physicalName>numexp</physicalName><descripcion><![CDATA[Campo que relaciona la entidad con un número de expediente (Uso interno del sistema)]]></descripcion><type>0</type><size>30</size><nullable>S</nullable><documentarySearch>N</documentarySearch><multivalue>N</multivalue></field><field id=''12''><physicalName>id</physicalName><descripcion><![CDATA[Campos Clave de la entidad (Uso interno del sistema)]]></descripcion><type>3</type><nullable>N</nullable><documentarySearch>N</documentarySearch><multivalue>N</multivalue></field><field id=''13''><physicalName>NumVehiculos</physicalName><descripcion><![CDATA[Número de plazas para vehículos]]></descripcion><type>2</type><size>20</size><nullable>S</nullable><documentarySearch>N</documentarySearch><multivalue>N</multivalue></field><field id=''14''><physicalName>orden</physicalName><descripcion><![CDATA[Indica el orden del valor]]></descripcion><type>2</type><size>10</size><nullable>S</nullable><documentarySearch>N</documentarySearch><multivalue>N</multivalue></field></fields><validations><validation id=''1''><fieldId>3</fieldId><table>TIPO_VADO</table><tableType>2</tableType><class/><mandatory>N</mandatory></validation><validation id=''2''><fieldId>9</fieldId><table>REBAJE_ACERA</table><tableType>2</tableType><class/><mandatory>N</mandatory></validation></validations></entity>', '<div id="dataBlock_VADO" style="position: relative; height: 310px; width: 600px">
<div id="label_VADO:NUMLICENCIA" style="position: absolute; top: 10px; left: 10px; width: 110px;" class="formsTitleB">
<bean:write name="defaultForm" property="entityApp.label(VADO:NUMLICENCIA)" />:</div>
<div id="data_VADO:NUMLICENCIA" style="position: absolute; top: 10px; left: 130px;">
<ispac:htmlText property="property(VADO:NUMLICENCIA)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="80" maxlength="100" >
</ispac:htmlText>
</div>
<div id="label_VADO:UBICACION" style="position: absolute; top: 35px; left: 10px; width: 110px;" class="formsTitleB">
<bean:write name="defaultForm" property="entityApp.label(VADO:UBICACION)" />:</div>
<div id="data_VADO:UBICACION" style="position: absolute; top: 35px; left: 130px;">
<ispac:htmlText property="property(VADO:UBICACION)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="80" maxlength="250" >
</ispac:htmlText>
</div>
<div id="label_VADO:TIPO" style="position: absolute; top: 60px; left: 10px; width: 110px;" class="formsTitleB">
<bean:write name="defaultForm" property="entityApp.label(VADO:TIPO)" />:</div>
<div id="data_VADO:TIPO" style="position: absolute; top: 60px; left: 130px;">
<nobr>
<ispac:htmlTextImageFrame property="property(VADO:TIPO)" readonly="true" readonlyTag="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="80" id="SEARCH_VADO_TIPO" target="workframe" action="selectValue.do?entity=TIPO_VADO" image="img/search-mg.gif" titleKeyLink="title.link.data.selection" imageDelete="img/borrar.gif" titleKeyImageDelete="title.delete.data.selection" styleClassDeleteLink="tdlink" confirmDeleteKey="msg.delete.data.selection" showDelete="true" showFrame="true" width="640" height="480" >
<ispac:parameter name="SEARCH_VADO_TIPO" id="property(VADO:TIPO)" property="VALOR" />
</ispac:htmlTextImageFrame>
</nobr>
</div>
<div id="label_VADO:LARGOENTRADA" style="position: absolute; top: 85px; left: 10px; width: 110px;" class="formsTitleB">
<bean:write name="defaultForm" property="entityApp.label(VADO:LARGOENTRADA)" />:</div>
<div id="data_VADO:LARGOENTRADA" style="position: absolute; top: 85px; left: 130px;">
<ispac:htmlText property="property(VADO:LARGOENTRADA)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="80" maxlength="18" >
</ispac:htmlText>
</div>
<div id="label_VADO:ANCHOACERA" style="position: absolute; top: 110px; left: 10px; width: 110px;" class="formsTitleB">
<bean:write name="defaultForm" property="entityApp.label(VADO:ANCHOACERA)" />:</div>
<div id="data_VADO:ANCHOACERA" style="position: absolute; top: 110px; left: 130px;">
<ispac:htmlText property="property(VADO:ANCHOACERA)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="80" maxlength="18" >
</ispac:htmlText>
</div>
<div id="label_VADO:SUPERFICIEACERA" style="position: absolute; top: 135px; left: 10px; width: 110px;" class="formsTitleB">
<bean:write name="defaultForm" property="entityApp.label(VADO:SUPERFICIEACERA)" />:</div>
<div id="data_VADO:SUPERFICIEACERA" style="position: absolute; top: 135px; left: 130px;">
<ispac:htmlText property="property(VADO:SUPERFICIEACERA)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="80" maxlength="18" >
</ispac:htmlText>
</div>
<div id="label_VADO:SUPERFICIELOCAL" style="position: absolute; top: 160px; left: 10px; width: 110px;" class="formsTitleB">
<bean:write name="defaultForm" property="entityApp.label(VADO:SUPERFICIELOCAL)" />:</div>
<div id="data_VADO:SUPERFICIELOCAL" style="position: absolute; top: 160px; left: 130px;">
<ispac:htmlText property="property(VADO:SUPERFICIELOCAL)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="80" maxlength="18" >
</ispac:htmlText>
</div>
<div id="label_VADO:ACTIVIDAD" style="position: absolute; top: 185px; left: 10px; width: 110px;" class="formsTitleB">
<bean:write name="defaultForm" property="entityApp.label(VADO:ACTIVIDAD)" />:</div>
<div id="data_VADO:ACTIVIDAD" style="position: absolute; top: 185px; left: 130px;">
<ispac:htmlText property="property(VADO:ACTIVIDAD)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="80" maxlength="250" >
</ispac:htmlText>
</div>
<div id="label_VADO:NUMVEHÍCULOS" style="position: absolute; top: 210px; left: 10px; width: 110px;" class="formsTitleB">
<bean:write name="defaultForm" property="entityApp.label(VADO:NUMVEHÍCULOS)" />:</div>
<div id="data_VADO:NUMVEHÍCULOS" style="position: absolute; top: 210px; left: 130px;">
<ispac:htmlText property="property(VADO:NUMVEHÍCULOS)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="80" maxlength="20" >
</ispac:htmlText>
</div>
<div id="label_VADO:REBAJE" style="position: absolute; top: 235px; left: 10px; width: 110px;" class="formsTitleB">
<bean:write name="defaultForm" property="entityApp.label(VADO:REBAJE)" />:</div>
<div id="data_VADO:REBAJE" style="position: absolute; top: 235px; left: 130px;">
<nobr>
<ispac:htmlTextImageFrame property="property(VADO:REBAJE)" readonly="true" readonlyTag="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="80" id="SEARCH_VADO_REBAJE" target="workframe" action="selectValue.do?entity=REBAJE_ACERA" image="img/search-mg.gif" titleKeyLink="title.link.data.selection" imageDelete="img/borrar.gif" titleKeyImageDelete="title.delete.data.selection" styleClassDeleteLink="tdlink" confirmDeleteKey="msg.delete.data.selection" showDelete="true" showFrame="true" width="640" height="480" >
<ispac:parameter name="SEARCH_VADO_REBAJE" id="property(VADO:REBAJE)" property="VALOR" />
</ispac:htmlTextImageFrame>
</nobr>
</div>
<div id="label_VADO:TASAS" style="position: absolute; top: 260px; left: 10px; width: 110px;" class="formsTitleB">
<bean:write name="defaultForm" property="entityApp.label(VADO:TASAS)" />:</div>
<div id="data_VADO:TASAS" style="position: absolute; top: 260px; left: 130px;">
<ispac:htmlText property="property(VADO:TASAS)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="80" maxlength="18" >
</ispac:htmlText>
</div>
</div>
', getDate());
INSERT INTO spac_ct_entidades (id, tipo, nombre, campo_pk, campo_numexp, schema_expr, frm_edit, descripcion, sec_pk, definicion, frm_jsp, fecha) VALUES (225, 2, 'VLDTBL_TIPO_RECLAM', 'ID', NULL, NULL, NULL, 'Tipo de reclamación: baja, devolución total/parcial, etc.', 'SPAC_SQ_VLDTBL_TIPO_RECLAM', '<entity version=''1.00''><editable>S</editable><dropable>S</dropable><status>0</status><fields><field id=''1''><physicalName>id</physicalName><descripcion><![CDATA[Identificador inequívoco del valor]]></descripcion><type>3</type><nullable>N</nullable><documentarySearch>N</documentarySearch><multivalue>N</multivalue></field><field id=''2''><physicalName>valor</physicalName><descripcion><![CDATA[Valor que se almacena en el campo validado]]></descripcion><type>0</type><size>64</size><nullable>S</nullable><documentarySearch>N</documentarySearch><multivalue>N</multivalue></field><field id=''3''><physicalName>vigente</physicalName><descripcion><![CDATA[Indica si el valor está en vigencia para ser seleccionado]]></descripcion><type>2</type><size>1</size><nullable>S</nullable><documentarySearch>N</documentarySearch><multivalue>N</multivalue></field><field id=''4''><physicalName>ORDEN</physicalName><descripcion><![CDATA[Indica el orden del valor]]></descripcion><type>2</type><size>10</size><nullable>S</nullable><documentarySearch>N</documentarySearch><multivalue>N</multivalue></field></fields></entity>', NULL, getDate());
INSERT INTO spac_ct_entidades (id, tipo, nombre, campo_pk, campo_numexp, schema_expr, frm_edit, descripcion, sec_pk, definicion, frm_jsp, fecha) VALUES (226, 2, 'VLDTBL_TIPO_DESTI', 'ID', NULL, NULL, NULL, 'Órgano al que va dirigida la reclamación del tributo: Ayuntamiento o Diputación', 'SPAC_SQ_VLDTBL_TIPO_DESTI', '<entity version=''1.00''><editable>S</editable><dropable>S</dropable><status>0</status><fields><field id=''1''><physicalName>id</physicalName><descripcion><![CDATA[Identificador inequívoco del valor]]></descripcion><type>3</type><nullable>N</nullable><documentarySearch>N</documentarySearch><multivalue>N</multivalue></field><field id=''2''><physicalName>valor</physicalName><descripcion><![CDATA[Valor que se almacena en el campo validado]]></descripcion><type>0</type><size>32</size><nullable>S</nullable><documentarySearch>N</documentarySearch><multivalue>N</multivalue></field><field id=''3''><physicalName>vigente</physicalName><descripcion><![CDATA[Indica si el valor está en vigencia para ser seleccionado]]></descripcion><type>2</type><size>1</size><nullable>S</nullable><documentarySearch>N</documentarySearch><multivalue>N</multivalue></field><field id=''4''><physicalName>ORDEN</physicalName><descripcion><![CDATA[Indica el orden del valor]]></descripcion><type>2</type><size>10</size><nullable>S</nullable><documentarySearch>N</documentarySearch><multivalue>N</multivalue></field></fields></entity>', NULL, getDate());
INSERT INTO spac_ct_entidades (id, tipo, nombre, campo_pk, campo_numexp, schema_expr, frm_edit, descripcion, sec_pk, definicion, frm_jsp, fecha) VALUES (227, 1, 'RECLAMACION', 'ID', 'NUMEXP', 'NUMEXP', 30, 'Reclamación de tributos', 'SPAC_SQ_RECLAMACION', '<entity version=''1.00''><editable>S</editable><dropable>S</dropable><status>0</status><fields><field id=''1''><physicalName>id</physicalName><descripcion><![CDATA[Campos Clave de la entidad (Uso interno del sistema)]]></descripcion><type>3</type><nullable>S</nullable><documentarySearch>N</documentarySearch><multivalue>N</multivalue></field><field id=''2''><physicalName>numexp</physicalName><descripcion><![CDATA[Campo que relaciona la entidad con un número de expediente (Uso interno del sistema)]]></descripcion><type>0</type><size>30</size><nullable>S</nullable><documentarySearch>N</documentarySearch><multivalue>N</multivalue></field><field id=''3''><physicalName>tipo</physicalName><descripcion><![CDATA[Tipo de reclamación: baja, devolución total/parcial, etc.]]></descripcion><type>0</type><size>64</size><nullable>S</nullable><documentarySearch>N</documentarySearch><multivalue>N</multivalue></field><field id=''4''><physicalName>descripcion</physicalName><descripcion><![CDATA[Descripción breve de la reclamación]]></descripcion><type>1</type><nullable>S</nullable><documentarySearch>N</documentarySearch><multivalue>N</multivalue></field><field id=''5''><physicalName>destinatario</physicalName><descripcion><![CDATA[Órgano al que va dirigida la reclamación del tributo: Ayuntamiento o Diputación]]></descripcion><type>0</type><size>32</size><nullable>S</nullable><documentarySearch>N</documentarySearch><multivalue>N</multivalue></field><field id=''6''><physicalName>aceptada</physicalName><descripcion><![CDATA[Indicador de si la reclamación ha sido aprobada o rechazada]]></descripcion><type>0</type><size>2</size><nullable>S</nullable><documentarySearch>N</documentarySearch><multivalue>N</multivalue></field><field id=''7''><physicalName>orden</physicalName><descripcion><![CDATA[Indica el orden del valor]]></descripcion><type>2</type><size>10</size><nullable>S</nullable><documentarySearch>N</documentarySearch><multivalue>N</multivalue></field></fields><indexes><index id=''5''><name><![CDATA[IND_593287806]]></name><fields><field idField=''2''/></fields></index></indexes><validations><validation id=''1''><fieldId>6</fieldId><table>SPAC_TBL_009</table><tableType>2</tableType><class/><mandatory>N</mandatory></validation><validation id=''2''><fieldId>3</fieldId><table>VLDTBL_TIPO_RECLAM</table><tableType>2</tableType><class/><mandatory>N</mandatory></validation><validation id=''3''><fieldId>5</fieldId><table>VLDTBL_TIPO_DESTI</table><tableType>2</tableType><class/><mandatory>N</mandatory></validation></validations></entity>', '<div id="dataBlock_RECLAMACION" style="position: relative; height: 160px; width: 600px">
<div id="label_RECLAMACION:TIPO" style="position: absolute; top: 10px; left: 10px; width: 110px;" class="formsTitleB">
<bean:write name="defaultForm" property="entityApp.label(RECLAMACION:TIPO)" />:</div>
<div id="data_RECLAMACION:TIPO" style="position: absolute; top: 10px; left: 130px;">
<nobr>
<ispac:htmlTextImageFrame property="property(RECLAMACION:TIPO)" readonly="true" readonlyTag="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="80" id="SEARCH_RECLAMACION_TIPO" target="workframe" action="selectValue.do?entity=VLDTBL_TIPO_RECLAM" image="img/search-mg.gif" titleKeyLink="title.link.data.selection" imageDelete="img/borrar.gif" titleKeyImageDelete="title.delete.data.selection" styleClassDeleteLink="tdlink" confirmDeleteKey="msg.delete.data.selection" showDelete="true" showFrame="true" width="640" height="480" >
<ispac:parameter name="SEARCH_RECLAMACION_TIPO" id="property(RECLAMACION:TIPO)" property="VALOR" />
</ispac:htmlTextImageFrame>
</nobr>
</div>
<div id="label_RECLAMACION:DESCRIPCION" style="position: absolute; top: 35px; left: 10px; width: 110px;" class="formsTitleB">
<bean:write name="defaultForm" property="entityApp.label(RECLAMACION:DESCRIPCION)" />:</div>
<div id="data_RECLAMACION:DESCRIPCION" style="position: absolute; top: 35px; left: 130px;">
<ispac:htmlTextarea property="property(RECLAMACION:DESCRIPCION)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" rows="2" cols="80" >
</ispac:htmlTextarea>
</div>
<div id="label_RECLAMACION:DESTINATARIO" style="position: absolute; top: 85px; left: 10px; width: 110px;" class="formsTitleB">
<bean:write name="defaultForm" property="entityApp.label(RECLAMACION:DESTINATARIO)" />:</div>
<div id="data_RECLAMACION:DESTINATARIO" style="position: absolute; top: 85px; left: 130px;">
<nobr>
<ispac:htmlTextImageFrame property="property(RECLAMACION:DESTINATARIO)" readonly="true" readonlyTag="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="80" id="SEARCH_RECLAMACION_DESTINATARIO" target="workframe" action="selectValue.do?entity=VLDTBL_TIPO_DESTI" image="img/search-mg.gif" titleKeyLink="title.link.data.selection" imageDelete="img/borrar.gif" titleKeyImageDelete="title.delete.data.selection" styleClassDeleteLink="tdlink" confirmDeleteKey="msg.delete.data.selection" showDelete="true" showFrame="true" width="640" height="480" >
<ispac:parameter name="SEARCH_RECLAMACION_DESTINATARIO" id="property(RECLAMACION:DESTINATARIO)" property="VALOR" />
</ispac:htmlTextImageFrame>
</nobr>
</div>
<div id="label_RECLAMACION:ACEPTADA" style="position: absolute; top: 110px; left: 10px; width: 110px;" class="formsTitleB">
<bean:write name="defaultForm" property="entityApp.label(RECLAMACION:ACEPTADA)" />:</div>
<div id="data_RECLAMACION:ACEPTADA" style="position: absolute; top: 110px; left: 130px;">
<nobr>
<ispac:htmlTextImageFrame property="property(RECLAMACION:ACEPTADA)" readonly="true" readonlyTag="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="80" id="SEARCH_RECLAMACION_ACEPTADA" target="workframe" action="selectValue.do?entity=SPAC_TBL_009" image="img/search-mg.gif" titleKeyLink="title.link.data.selection" imageDelete="img/borrar.gif" titleKeyImageDelete="title.delete.data.selection" styleClassDeleteLink="tdlink" confirmDeleteKey="msg.delete.data.selection" showDelete="true" showFrame="true" width="640" height="480" >
<ispac:parameter name="SEARCH_RECLAMACION_ACEPTADA" id="property(RECLAMACION:ACEPTADA)" property="VALOR" />
</ispac:htmlTextImageFrame>
</nobr>
</div>
</div>
', getDate());
INSERT INTO spac_ct_entidades (id, tipo, nombre, campo_pk, campo_numexp, schema_expr, frm_edit, descripcion, sec_pk, definicion, frm_jsp, fecha) VALUES (228, 2, 'VLDTBL_TIPO_TARJETA', 'ID', NULL, NULL, NULL, 'Tipo de tarjeta de estacionamiento para minusválidos', 'SPAC_SQ_VLDTBL_TIPO_TARJETA', '<entity version=''1.00''><editable>S</editable><dropable>S</dropable><status>0</status><fields><field id=''1''><physicalName>id</physicalName><descripcion><![CDATA[Identificador inequívoco del valor]]></descripcion><type>3</type><nullable>N</nullable><documentarySearch>N</documentarySearch><multivalue>N</multivalue></field><field id=''2''><physicalName>valor</physicalName><descripcion><![CDATA[Valor que se almacena en el campo validado]]></descripcion><type>0</type><size>100</size><nullable>S</nullable><documentarySearch>N</documentarySearch><multivalue>N</multivalue></field><field id=''3''><physicalName>vigente</physicalName><descripcion><![CDATA[Indica si el valor está en vigencia para ser seleccionado]]></descripcion><type>2</type><size>1</size><nullable>S</nullable><documentarySearch>N</documentarySearch><multivalue>N</multivalue></field><field id=''4''><physicalName>ORDEN</physicalName><descripcion><![CDATA[Indica el orden del valor]]></descripcion><type>2</type><size>10</size><nullable>S</nullable><documentarySearch>N</documentarySearch><multivalue>N</multivalue></field></fields></entity>', NULL, getDate());
INSERT INTO spac_ct_entidades (id, tipo, nombre, campo_pk, campo_numexp, schema_expr, frm_edit, descripcion, sec_pk, definicion, frm_jsp, fecha) VALUES (229, 1, 'DATOS_TARJETA_MINUS', 'ID', 'NUMEXP', 'NUMEXP', 31, 'Datos de la tarjeta de estacionamiento para minusválidos', 'SPAC_SQ_DATOS_TARJETA_MINUS', '<entity version=''1.00''><editable>S</editable><dropable>S</dropable><status>0</status><fields><field id=''1''><physicalName>tipo_tarjeta</physicalName><descripcion><![CDATA[Tipo de tarjeta]]></descripcion><type>0</type><size>100</size><nullable>S</nullable><documentarySearch>N</documentarySearch><multivalue>N</multivalue></field><field id=''2''><physicalName>fecha_caducidad</physicalName><descripcion><![CDATA[Fecha de caducidad]]></descripcion><type>6</type><nullable>S</nullable><documentarySearch>N</documentarySearch><multivalue>N</multivalue></field><field id=''3''><physicalName>numexp</physicalName><descripcion><![CDATA[Campo que relaciona la entidad con un número de expediente (Uso interno del sistema)]]></descripcion><type>0</type><size>30</size><nullable>S</nullable><documentarySearch>N</documentarySearch><multivalue>N</multivalue></field><field id=''4''><physicalName>id</physicalName><descripcion><![CDATA[Campos Clave de la entidad (Uso interno del sistema)]]></descripcion><type>3</type><nullable>N</nullable><documentarySearch>N</documentarySearch><multivalue>N</multivalue></field><field id=''5''><physicalName>orden</physicalName><descripcion><![CDATA[Indica el orden del valor]]></descripcion><type>2</type><size>10</size><nullable>S</nullable><documentarySearch>N</documentarySearch><multivalue>N</multivalue></field></fields><validations><validation id=''1''><fieldId>1</fieldId><table>VLDTBL_TIPO_TARJETA</table><tableType>2</tableType><class/><mandatory>N</mandatory></validation></validations></entity>', '<div id="dataBlock_DATOS_TARJETA_MINUS" style="position: relative; height: 85px; width: 600px">
<div id="label_DATOS_TARJETA_MINUS:TIPO_TARJETA" style="position: absolute; top: 10px; left: 10px; width: 110px;" class="formsTitleB">
<bean:write name="defaultForm" property="entityApp.label(DATOS_TARJETA_MINUS:TIPO_TARJETA)" />:</div>
<div id="data_DATOS_TARJETA_MINUS:TIPO_TARJETA" style="position: absolute; top: 10px; left: 130px;">
<nobr>
<ispac:htmlTextImageFrame property="property(DATOS_TARJETA_MINUS:TIPO_TARJETA)" readonly="true" readonlyTag="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="80" id="SEARCH_DATOS_TARJETA_MINUS_TIPO_TARJETA" target="workframe" action="selectValue.do?entity=VLDTBL_TIPO_TARJETA" image="img/search-mg.gif" titleKeyLink="title.link.data.selection" imageDelete="img/borrar.gif" titleKeyImageDelete="title.delete.data.selection" styleClassDeleteLink="tdlink" confirmDeleteKey="msg.delete.data.selection" showDelete="true" showFrame="true" width="640" height="480" >
<ispac:parameter name="SEARCH_DATOS_TARJETA_MINUS_TIPO_TARJETA" id="property(DATOS_TARJETA_MINUS:TIPO_TARJETA)" property="VALOR" />
</ispac:htmlTextImageFrame>
</nobr>
</div>
<div id="label_DATOS_TARJETA_MINUS:FECHA_CADUCIDAD" style="position: absolute; top: 35px; left: 10px; width: 110px;" class="formsTitleB">
<bean:write name="defaultForm" property="entityApp.label(DATOS_TARJETA_MINUS:FECHA_CADUCIDAD)" />:</div>
<div id="data_DATOS_TARJETA_MINUS:FECHA_CADUCIDAD" style="position: absolute; top: 35px; left: 130px;">
<nobr>
<ispac:htmlTextCalendar property="property(DATOS_TARJETA_MINUS:FECHA_CADUCIDAD)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="14" maxlength="10" image=''<%= buttoncalendar %>'' format="dd/mm/yyyy" enablePast="true" >
</ispac:htmlTextCalendar>
</nobr>
</div>
</div>
', getDate());


-- Actualizar secuencia de spac_ct_entidades
UPDATE SPAC_SQ_SEQUENCES SET SEQUENCE_ID = 230 WHERE SEQUENCE_NAME = 'SPAC_SQ_ID_CTENTIDADES';



--
-- Recursos de las entidades: 'es'
--
DECLARE @sequence_resources_id int

UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 204, 'es', 'ID_LICITADOR', 'Id licitador');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 204, 'es', 'IMPORTE', 'Importe');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 204, 'es', 'PLAZO', 'Plazo');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 204, 'es', 'UD_PLAZO', 'Ud plazo');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 204, 'es', 'OFERTA', 'Datos oferta');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 205, 'es', 'VISTO_BUENO', 'Visto bueno');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 205, 'es', 'TASK_NAME', 'Tarea');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 205, 'es', 'VB', 'V.B.');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 205, 'es', 'TASK_ID', 'taskid');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 205, 'es', 'STAGE_NAME', 'Fase');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 205, 'es', 'ID_TRAM_EXP', 'ID_TRAM_EXP');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 205, 'es', 'OBSERVACIONES', 'Observaciones');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 206, 'es', 'CAMB_TITULAR_LA', 'Cambio de titular');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 206, 'es', 'ACTIVIDAD', 'Actividad');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 206, 'es', 'EMPLAZAMIENTO', 'Emplazamiento');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 206, 'es', 'TASAS', 'Tasas');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 206, 'es', 'FECHA_LICENCIA', 'Fecha Licencia');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 206, 'es', 'FECHA_APROBACION', 'Fecha Aprobación');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 206, 'es', 'DENOMINACION', 'Denominación');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 206, 'es', 'DENOMINACION', 'Denominación');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 207, 'es', 'VLDTBL_CLASIF_SUELO', 'Clasificación suelo');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 208, 'es', 'VLDTBL_CALIF_SUELO', 'Calificación suelo');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 209, 'es', 'TIPO_TECNICO', 'Tipo de técnico');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 210, 'es', 'OBRA_SOLICITADA', 'Obra solicitada');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 210, 'es', 'SITUACION_OBRA', 'Situación de la obra');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 210, 'es', 'PROMOTOR', 'Promotor');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 210, 'es', 'DIRECTOR_OBRA', 'Director de obra');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 210, 'es', 'PRESUPUESTO_PROMOTOR', 'Presupuesto promotor');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 210, 'es', 'PRESUPUESTO_TÉCNICO_MUNICIPAL', 'Presupuesto del técnico municipal');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 210, 'es', 'AUTOR_PROYECTO', 'Técnico autor del proyecto');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 210, 'es', 'USO_OBRA', 'Uso al que se destinará');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 210, 'es', 'CLASIFICACION_SUELO', 'Clasificación suelo');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 210, 'es', 'CUALIFICACION_SUELO', 'Cualificación suelo');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 210, 'es', 'IMPORTE_TASAS', 'Importe tasas');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 210, 'es', 'IMPORTE_ICIO', 'Importe ICIO');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 210, 'es', 'TOTAL_LIQUIDACION', 'Total liquidación');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 210, 'es', 'OBRA_MENOR', 'Obra menor');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 210, 'es', 'DIRECTOR_EJECUCION', 'Director de ejecución');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 211, 'es', 'CERT_URBANISTICO', 'Certificado urbanístico');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 211, 'es', 'FECHA_EMISION', 'Fecha de emisión');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 211, 'es', 'SITUACION', 'Situación finca/parcela/edificio');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 211, 'es', 'TECNICO_NOMBRE', 'Técnico');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 211, 'es', 'TECNICO_CARGO', 'Cargo');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 212, 'es', 'TIPO_CONTRATO', 'Tipo de contrato');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 213, 'es', 'FORMA_ADJUDICACION', 'Forma de adjudicación');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 214, 'es', 'LOTES', 'Lotes');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 214, 'es', 'NOMBRE', 'Nombre');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 214, 'es', 'PRESUPUESTO', 'Presupuesto');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 215, 'es', 'TIPO', 'Tipo');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 215, 'es', 'FORMA_ADJUDICACION', 'Forma de adjudicación');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 215, 'es', 'PRESUPUESTO_MAXIMO', 'Presupuesto máximo');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 215, 'es', 'APLICACION', 'Aplicación presupuestaria');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 215, 'es', 'PRECIO_ADJUDICACION', 'Precio adjudicación');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 215, 'es', 'PLAZO_EJECUCION', 'Plazo ejecución');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 215, 'es', 'UD_PLAZO', 'Unidades plazo');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 215, 'es', 'GARANTIA_PROVISIONAL', 'Garantía provisional');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 215, 'es', 'GARANTIA_DEFINITIVA', 'Garantía definitiva');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 215, 'es', 'CLASIFICACION', 'Clasificación');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 215, 'es', 'FECHA_FIN_EJECUCION', 'Fecha fin ejecución');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 215, 'es', 'FECHA_FIN_GARANTIA', 'Fecha fin garantía');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 215, 'es', 'CONTRATO', 'Datos contratación');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 216, 'es', 'VLDTBL_SAN_GRADO', 'Grado infracción');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 217, 'es', 'VLDTBL_SAN_CALIFICACION', 'Calificación sanción');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 218, 'es', 'SANCION', 'Datos sanción');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 218, 'es', 'INSTRUCTOR', 'Instructor');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 218, 'es', 'CALIFICACION', 'Calificación');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 218, 'es', 'IMPORTE', 'Importe sanción');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 218, 'es', 'GRADO', 'Grado infracción');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 218, 'es', 'INFRACCION', 'Infracción');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 218, 'es', 'HECHOS', 'Hechos denunciados');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 218, 'es', 'ACTUACION', 'Actuación');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 219, 'es', 'CLASIFICACION_ACTIVIDADES', 'Clasificación de Actividades');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 220, 'es', 'ACTIVIDAD_CLASIFICADA', 'Actividad clasificada');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 220, 'es', 'ACTIVIDAD', 'Actividad');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 220, 'es', 'CLASIFICACION', 'Clasificación');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 220, 'es', 'EMPLAZAMIENTO', 'Emplazamiento');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 220, 'es', 'TASAS', 'Tasas');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 221, 'es', 'ACTIVIDAD_NO_CLASIFICADA', 'Actividad No Clasificada');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 221, 'es', 'ACTIVIDAD', 'Actividad');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 221, 'es', 'EMPLAZAMIENTO', 'Emplazamiento');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 221, 'es', 'TASAS', 'Tasas');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 222, 'es', 'TIPO_VADO', 'Tipo de Vado');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 223, 'es', 'REBAJE_ACERA', 'Tipo de Rebaje de Acera');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 224, 'es', 'VADO', 'Vado');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 224, 'es', 'NUMLICENCIA', 'Número de Licencia');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 224, 'es', 'UBICACION', 'Ubicación');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 224, 'es', 'TIPO', 'Tipo de Vado');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 224, 'es', 'LARGOENTRADA', 'Largo de la entrada (m)');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 224, 'es', 'ANCHOACERA', 'Ancho de la acera (m)');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 224, 'es', 'SUPERFICIEACERA', 'Superficie de la acera (m2)');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 224, 'es', 'SUPERFICIELOCAL', 'Superficie del local (m2)');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 224, 'es', 'ACTIVIDAD', 'Actividad o uso del local');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 224, 'es', 'NUMVEHÍCULOS', 'Número de plazas para vehículos');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 224, 'es', 'REBAJE', 'Rebaje');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 224, 'es', 'TASAS', 'Tasas');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 224, 'es', 'NUMVEHICULOS', 'Número de plazas para vehículos');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 225, 'es', 'VLDTBL_TIPO_RECLAM', 'Tipo de reclamación');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 226, 'es', 'VLDTBL_TIPO_DESTI', 'Tipo de destinatario');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 227, 'es', 'TIPO', 'Tipo');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 227, 'es', 'DESCRIPCION', 'Descripción');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 227, 'es', 'DESTINATARIO', 'Destinatario');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 227, 'es', 'ACEPTADA', 'Aceptada');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 227, 'es', 'RECLAMACION', 'Reclamación');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 228, 'es', 'VLDTBL_TIPO_TARJETA', 'Tipo tarjeta minusvalido');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 229, 'es', 'DATOS_TARJETA_MINUS', 'Datos tarjeta');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 229, 'es', 'TIPO_TARJETA', 'Tipo de tarjeta');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 229, 'es', 'FECHA_CADUCIDAD', 'Fecha caducidad');

--
-- Recursos de las entidades: 'ca'
--

UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 204, 'ca', 'ID_LICITADOR', 'Id licitador');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 204, 'ca', 'IMPORTE', 'Importe');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 204, 'ca', 'PLAZO', 'Plazo');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 204, 'ca', 'UD_PLAZO', 'Ud plazo');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 204, 'ca', 'OFERTA', 'Datos oferta');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 205, 'ca', 'VISTO_BUENO', 'Visto bueno');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 205, 'ca', 'TASK_NAME', 'Tarea');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 205, 'ca', 'VB', 'V.B.');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 205, 'ca', 'TASK_ID', 'taskid');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 205, 'ca', 'STAGE_NAME', 'Fase');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 205, 'ca', 'ID_TRAM_EXP', 'ID_TRAM_EXP');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 205, 'ca', 'OBSERVACIONES', 'Observaciones');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 206, 'ca', 'CAMB_TITULAR_LA', 'Cambio de titular');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 206, 'ca', 'ACTIVIDAD', 'Actividad');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 206, 'ca', 'EMPLAZAMIENTO', 'Emplazamiento');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 206, 'ca', 'TASAS', 'Tasas');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 206, 'ca', 'FECHA_LICENCIA', 'Fecha Licencia');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 206, 'ca', 'FECHA_APROBACION', 'Fecha Aprobación');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 206, 'ca', 'DENOMINACION', 'Denominación');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 206, 'ca', 'DENOMINACION', 'Denominación');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 207, 'ca', 'VLDTBL_CLASIF_SUELO', 'Clasificación suelo');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 208, 'ca', 'VLDTBL_CALIF_SUELO', 'Calificación suelo');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 209, 'ca', 'TIPO_TECNICO', 'Tipo de técnico');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 210, 'ca', 'OBRA_SOLICITADA', 'Obra solicitada');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 210, 'ca', 'SITUACION_OBRA', 'Situación de la obra');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 210, 'ca', 'PROMOTOR', 'Promotor');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 210, 'ca', 'DIRECTOR_OBRA', 'Director de obra');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 210, 'ca', 'PRESUPUESTO_PROMOTOR', 'Presupuesto promotor');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 210, 'ca', 'PRESUPUESTO_TÉCNICO_MUNICIPAL', 'Presupuesto del técnico municipal');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 210, 'ca', 'AUTOR_PROYECTO', 'Técnico autor del proyecto');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 210, 'ca', 'USO_OBRA', 'Uso al que se destinará');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 210, 'ca', 'CLASIFICACION_SUELO', 'Clasificación suelo');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 210, 'ca', 'CUALIFICACION_SUELO', 'Cualificación suelo');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 210, 'ca', 'IMPORTE_TASAS', 'Importe tasas');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 210, 'ca', 'IMPORTE_ICIO', 'Importe ICIO');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 210, 'ca', 'TOTAL_LIQUIDACION', 'Total liquidación');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 210, 'ca', 'OBRA_MENOR', 'Obra menor');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 210, 'ca', 'DIRECTOR_EJECUCION', 'Director de ejecución');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 211, 'ca', 'CERT_URBANISTICO', 'Certificado urbanístico');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 211, 'ca', 'FECHA_EMISION', 'Fecha de emisión');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 211, 'ca', 'SITUACION', 'Situación finca/parcela/edificio');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 211, 'ca', 'TECNICO_NOMBRE', 'Técnico');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 211, 'ca', 'TECNICO_CARGO', 'Cargo');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 212, 'ca', 'TIPO_CONTRATO', 'Tipo de contrato');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 213, 'ca', 'FORMA_ADJUDICACION', 'Forma de adjudicación');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 214, 'ca', 'LOTES', 'Lotes');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 214, 'ca', 'NOMBRE', 'Nombre');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 214, 'ca', 'PRESUPUESTO', 'Presupuesto');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 215, 'ca', 'TIPO', 'Tipo');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 215, 'ca', 'FORMA_ADJUDICACION', 'Forma de adjudicación');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 215, 'ca', 'PRESUPUESTO_MAXIMO', 'Presupuesto máximo');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 215, 'ca', 'APLICACION', 'Aplicación presupuestaria');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 215, 'ca', 'PRECIO_ADJUDICACION', 'Precio adjudicación');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 215, 'ca', 'PLAZO_EJECUCION', 'Plazo ejecución');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 215, 'ca', 'UD_PLAZO', 'Unidades plazo');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 215, 'ca', 'GARANTIA_PROVISIONAL', 'Garantía provisional');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 215, 'ca', 'GARANTIA_DEFINITIVA', 'Garantía definitiva');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 215, 'ca', 'CLASIFICACION', 'Clasificación');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 215, 'ca', 'FECHA_FIN_EJECUCION', 'Fecha fin ejecución');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 215, 'ca', 'FECHA_FIN_GARANTIA', 'Fecha fin garantía');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 215, 'ca', 'CONTRATO', 'Datos contratación');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 216, 'ca', 'VLDTBL_SAN_GRADO', 'Grado infracción');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 217, 'ca', 'VLDTBL_SAN_CALIFICACION', 'Calificación sanción');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 218, 'ca', 'SANCION', 'Datos sanción');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 218, 'ca', 'INSTRUCTOR', 'Instructor');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 218, 'ca', 'CALIFICACION', 'Calificación');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 218, 'ca', 'IMPORTE', 'Importe sanción');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 218, 'ca', 'GRADO', 'Grado infracción');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 218, 'ca', 'INFRACCION', 'Infracción');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 218, 'ca', 'HECHOS', 'Hechos denunciados');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 218, 'ca', 'ACTUACION', 'Actuación');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 219, 'ca', 'CLASIFICACION_ACTIVIDADES', 'Clasificación de Actividades');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 220, 'ca', 'ACTIVIDAD_CLASIFICADA', 'Actividad clasificada');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 220, 'ca', 'ACTIVIDAD', 'Actividad');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 220, 'ca', 'CLASIFICACION', 'Clasificación');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 220, 'ca', 'EMPLAZAMIENTO', 'Emplazamiento');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 220, 'ca', 'TASAS', 'Tasas');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 221, 'ca', 'ACTIVIDAD_NO_CLASIFICADA', 'Actividad No Clasificada');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 221, 'ca', 'ACTIVIDAD', 'Actividad');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 221, 'ca', 'EMPLAZAMIENTO', 'Emplazamiento');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 221, 'ca', 'TASAS', 'Tasas');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 222, 'ca', 'TIPO_VADO', 'Tipo de Vado');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 223, 'ca', 'REBAJE_ACERA', 'Tipo de Rebaje de Acera');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 224, 'ca', 'VADO', 'Vado');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 224, 'ca', 'NUMLICENCIA', 'Número de Licencia');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 224, 'ca', 'UBICACION', 'Ubicación');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 224, 'ca', 'TIPO', 'Tipo de Vado');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 224, 'ca', 'LARGOENTRADA', 'Largo de la entrada (m)');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 224, 'ca', 'ANCHOACERA', 'Ancho de la acera (m)');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 224, 'ca', 'SUPERFICIEACERA', 'Superficie de la acera (m2)');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 224, 'ca', 'SUPERFICIELOCAL', 'Superficie del local (m2)');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 224, 'ca', 'ACTIVIDAD', 'Actividad o uso del local');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 224, 'ca', 'NUMVEHÍCULOS', 'Número de plazas para vehículos');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 224, 'ca', 'REBAJE', 'Rebaje');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 224, 'ca', 'TASAS', 'Tasas');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 224, 'ca', 'NUMVEHICULOS', 'Número de plazas para vehículos');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 225, 'ca', 'VLDTBL_TIPO_RECLAM', 'Tipo de reclamación');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 226, 'ca', 'VLDTBL_TIPO_DESTI', 'Tipo de destinatario');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 227, 'ca', 'TIPO', 'Tipo');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 227, 'ca', 'DESCRIPCION', 'Descripción');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 227, 'ca', 'DESTINATARIO', 'Destinatario');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 227, 'ca', 'ACEPTADA', 'Aceptada');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 227, 'ca', 'RECLAMACION', 'Reclamación');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 228, 'ca', 'VLDTBL_TIPO_TARJETA', 'Tipo tarjeta minusvalido');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 229, 'ca', 'DATOS_TARJETA_MINUS', 'Datos tarjeta');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 229, 'ca', 'TIPO_TARJETA', 'Tipo de tarjeta');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 229, 'ca', 'FECHA_CADUCIDAD', 'Fecha caducidad');

--
-- Recursos de las entidades: 'eu'
--

UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 204, 'eu', 'ID_LICITADOR', 'Id licitador');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 204, 'eu', 'IMPORTE', 'Importe');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 204, 'eu', 'PLAZO', 'Plazo');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 204, 'eu', 'UD_PLAZO', 'Ud plazo');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 204, 'eu', 'OFERTA', 'Datos oferta');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 205, 'eu', 'VISTO_BUENO', 'Visto bueno');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 205, 'eu', 'TASK_NAME', 'Tarea');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 205, 'eu', 'VB', 'V.B.');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 205, 'eu', 'TASK_ID', 'taskid');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 205, 'eu', 'STAGE_NAME', 'Fase');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 205, 'eu', 'ID_TRAM_EXP', 'ID_TRAM_EXP');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 205, 'eu', 'OBSERVACIONES', 'Observaciones');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 206, 'eu', 'CAMB_TITULAR_LA', 'Cambio de titular');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 206, 'eu', 'ACTIVIDAD', 'Actividad');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 206, 'eu', 'EMPLAZAMIENTO', 'Emplazamiento');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 206, 'eu', 'TASAS', 'Tasas');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 206, 'eu', 'FECHA_LICENCIA', 'Fecha Licencia');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 206, 'eu', 'FECHA_APROBACION', 'Fecha Aprobación');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 206, 'eu', 'DENOMINACION', 'Denominación');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 206, 'eu', 'DENOMINACION', 'Denominación');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 207, 'eu', 'VLDTBL_CLASIF_SUELO', 'Clasificación suelo');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 208, 'eu', 'VLDTBL_CALIF_SUELO', 'Calificación suelo');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 209, 'eu', 'TIPO_TECNICO', 'Tipo de técnico');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 210, 'eu', 'OBRA_SOLICITADA', 'Obra solicitada');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 210, 'eu', 'SITUACION_OBRA', 'Situación de la obra');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 210, 'eu', 'PROMOTOR', 'Promotor');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 210, 'eu', 'DIRECTOR_OBRA', 'Director de obra');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 210, 'eu', 'PRESUPUESTO_PROMOTOR', 'Presupuesto promotor');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 210, 'eu', 'PRESUPUESTO_TÉCNICO_MUNICIPAL', 'Presupuesto del técnico municipal');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 210, 'eu', 'AUTOR_PROYECTO', 'Técnico autor del proyecto');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 210, 'eu', 'USO_OBRA', 'Uso al que se destinará');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 210, 'eu', 'CLASIFICACION_SUELO', 'Clasificación suelo');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 210, 'eu', 'CUALIFICACION_SUELO', 'Cualificación suelo');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 210, 'eu', 'IMPORTE_TASAS', 'Importe tasas');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 210, 'eu', 'IMPORTE_ICIO', 'Importe ICIO');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 210, 'eu', 'TOTAL_LIQUIDACION', 'Total liquidación');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 210, 'eu', 'OBRA_MENOR', 'Obra menor');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 210, 'eu', 'DIRECTOR_EJECUCION', 'Director de ejecución');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 211, 'eu', 'CERT_URBANISTICO', 'Certificado urbanístico');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 211, 'eu', 'FECHA_EMISION', 'Fecha de emisión');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 211, 'eu', 'SITUACION', 'Situación finca/parcela/edificio');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 211, 'eu', 'TECNICO_NOMBRE', 'Técnico');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 211, 'eu', 'TECNICO_CARGO', 'Cargo');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 212, 'eu', 'TIPO_CONTRATO', 'Tipo de contrato');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 213, 'eu', 'FORMA_ADJUDICACION', 'Forma de adjudicación');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 214, 'eu', 'LOTES', 'Lotes');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 214, 'eu', 'NOMBRE', 'Nombre');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 214, 'eu', 'PRESUPUESTO', 'Presupuesto');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 215, 'eu', 'TIPO', 'Tipo');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 215, 'eu', 'FORMA_ADJUDICACION', 'Forma de adjudicación');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 215, 'eu', 'PRESUPUESTO_MAXIMO', 'Presupuesto máximo');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 215, 'eu', 'APLICACION', 'Aplicación presupuestaria');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 215, 'eu', 'PRECIO_ADJUDICACION', 'Precio adjudicación');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 215, 'eu', 'PLAZO_EJECUCION', 'Plazo ejecución');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 215, 'eu', 'UD_PLAZO', 'Unidades plazo');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 215, 'eu', 'GARANTIA_PROVISIONAL', 'Garantía provisional');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 215, 'eu', 'GARANTIA_DEFINITIVA', 'Garantía definitiva');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 215, 'eu', 'CLASIFICACION', 'Clasificación');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 215, 'eu', 'FECHA_FIN_EJECUCION', 'Fecha fin ejecución');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 215, 'eu', 'FECHA_FIN_GARANTIA', 'Fecha fin garantía');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 215, 'eu', 'CONTRATO', 'Datos contratación');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 216, 'eu', 'VLDTBL_SAN_GRADO', 'Grado infracción');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 217, 'eu', 'VLDTBL_SAN_CALIFICACION', 'Calificación sanción');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 218, 'eu', 'SANCION', 'Datos sanción');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 218, 'eu', 'INSTRUCTOR', 'Instructor');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 218, 'eu', 'CALIFICACION', 'Calificación');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 218, 'eu', 'IMPORTE', 'Importe sanción');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 218, 'eu', 'GRADO', 'Grado infracción');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 218, 'eu', 'INFRACCION', 'Infracción');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 218, 'eu', 'HECHOS', 'Hechos denunciados');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 218, 'eu', 'ACTUACION', 'Actuación');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 219, 'eu', 'CLASIFICACION_ACTIVIDADES', 'Clasificación de Actividades');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 220, 'eu', 'ACTIVIDAD_CLASIFICADA', 'Actividad clasificada');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 220, 'eu', 'ACTIVIDAD', 'Actividad');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 220, 'eu', 'CLASIFICACION', 'Clasificación');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 220, 'eu', 'EMPLAZAMIENTO', 'Emplazamiento');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 220, 'eu', 'TASAS', 'Tasas');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 221, 'eu', 'ACTIVIDAD_NO_CLASIFICADA', 'Actividad No Clasificada');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 221, 'eu', 'ACTIVIDAD', 'Actividad');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 221, 'eu', 'EMPLAZAMIENTO', 'Emplazamiento');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 221, 'eu', 'TASAS', 'Tasas');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 222, 'eu', 'TIPO_VADO', 'Tipo de Vado');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 223, 'eu', 'REBAJE_ACERA', 'Tipo de Rebaje de Acera');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 224, 'eu', 'VADO', 'Vado');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 224, 'eu', 'NUMLICENCIA', 'Número de Licencia');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 224, 'eu', 'UBICACION', 'Ubicación');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 224, 'eu', 'TIPO', 'Tipo de Vado');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 224, 'eu', 'LARGOENTRADA', 'Largo de la entrada (m)');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 224, 'eu', 'ANCHOACERA', 'Ancho de la acera (m)');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 224, 'eu', 'SUPERFICIEACERA', 'Superficie de la acera (m2)');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 224, 'eu', 'SUPERFICIELOCAL', 'Superficie del local (m2)');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 224, 'eu', 'ACTIVIDAD', 'Actividad o uso del local');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 224, 'eu', 'NUMVEHÍCULOS', 'Número de plazas para vehículos');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 224, 'eu', 'REBAJE', 'Rebaje');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 224, 'eu', 'TASAS', 'Tasas');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 224, 'eu', 'NUMVEHICULOS', 'Número de plazas para vehículos');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 225, 'eu', 'VLDTBL_TIPO_RECLAM', 'Tipo de reclamación');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 226, 'eu', 'VLDTBL_TIPO_DESTI', 'Tipo de destinatario');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 227, 'eu', 'TIPO', 'Tipo');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 227, 'eu', 'DESCRIPCION', 'Descripción');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 227, 'eu', 'DESTINATARIO', 'Destinatario');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 227, 'eu', 'ACEPTADA', 'Aceptada');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 227, 'eu', 'RECLAMACION', 'Reclamación');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 228, 'eu', 'VLDTBL_TIPO_TARJETA', 'Tipo tarjeta minusvalido');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 229, 'eu', 'DATOS_TARJETA_MINUS', 'Datos tarjeta');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 229, 'eu', 'TIPO_TARJETA', 'Tipo de tarjeta');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 229, 'eu', 'FECHA_CADUCIDAD', 'Fecha caducidad');

--
-- Recursos de las entidades: 'gl'
--

UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 204, 'gl', 'ID_LICITADOR', 'Id licitador');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 204, 'gl', 'IMPORTE', 'Importe');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 204, 'gl', 'PLAZO', 'Plazo');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 204, 'gl', 'UD_PLAZO', 'Ud plazo');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 204, 'gl', 'OFERTA', 'Datos oferta');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 205, 'gl', 'VISTO_BUENO', 'Visto bueno');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 205, 'gl', 'TASK_NAME', 'Tarea');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 205, 'gl', 'VB', 'V.B.');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 205, 'gl', 'TASK_ID', 'taskid');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 205, 'gl', 'STAGE_NAME', 'Fase');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 205, 'gl', 'ID_TRAM_EXP', 'ID_TRAM_EXP');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 205, 'gl', 'OBSERVACIONES', 'Observaciones');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 206, 'gl', 'CAMB_TITULAR_LA', 'Cambio de titular');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 206, 'gl', 'ACTIVIDAD', 'Actividad');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 206, 'gl', 'EMPLAZAMIENTO', 'Emplazamiento');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 206, 'gl', 'TASAS', 'Tasas');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 206, 'gl', 'FECHA_LICENCIA', 'Fecha Licencia');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 206, 'gl', 'FECHA_APROBACION', 'Fecha Aprobación');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 206, 'gl', 'DENOMINACION', 'Denominación');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 206, 'gl', 'DENOMINACION', 'Denominación');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 207, 'gl', 'VLDTBL_CLASIF_SUELO', 'Clasificación suelo');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 208, 'gl', 'VLDTBL_CALIF_SUELO', 'Calificación suelo');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 209, 'gl', 'TIPO_TECNICO', 'Tipo de técnico');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 210, 'gl', 'OBRA_SOLICITADA', 'Obra solicitada');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 210, 'gl', 'SITUACION_OBRA', 'Situación de la obra');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 210, 'gl', 'PROMOTOR', 'Promotor');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 210, 'gl', 'DIRECTOR_OBRA', 'Director de obra');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 210, 'gl', 'PRESUPUESTO_PROMOTOR', 'Presupuesto promotor');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 210, 'gl', 'PRESUPUESTO_TÉCNICO_MUNICIPAL', 'Presupuesto del técnico municipal');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 210, 'gl', 'AUTOR_PROYECTO', 'Técnico autor del proyecto');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 210, 'gl', 'USO_OBRA', 'Uso al que se destinará');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 210, 'gl', 'CLASIFICACION_SUELO', 'Clasificación suelo');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 210, 'gl', 'CUALIFICACION_SUELO', 'Cualificación suelo');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 210, 'gl', 'IMPORTE_TASAS', 'Importe tasas');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 210, 'gl', 'IMPORTE_ICIO', 'Importe ICIO');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 210, 'gl', 'TOTAL_LIQUIDACION', 'Total liquidación');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 210, 'gl', 'OBRA_MENOR', 'Obra menor');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 210, 'gl', 'DIRECTOR_EJECUCION', 'Director de ejecución');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 211, 'gl', 'CERT_URBANISTICO', 'Certificado urbanístico');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 211, 'gl', 'FECHA_EMISION', 'Fecha de emisión');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 211, 'gl', 'SITUACION', 'Situación finca/parcela/edificio');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 211, 'gl', 'TECNICO_NOMBRE', 'Técnico');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 211, 'gl', 'TECNICO_CARGO', 'Cargo');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 212, 'gl', 'TIPO_CONTRATO', 'Tipo de contrato');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 213, 'gl', 'FORMA_ADJUDICACION', 'Forma de adjudicación');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 214, 'gl', 'LOTES', 'Lotes');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 214, 'gl', 'NOMBRE', 'Nombre');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 214, 'gl', 'PRESUPUESTO', 'Presupuesto');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 215, 'gl', 'TIPO', 'Tipo');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 215, 'gl', 'FORMA_ADJUDICACION', 'Forma de adjudicación');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 215, 'gl', 'PRESUPUESTO_MAXIMO', 'Presupuesto máximo');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 215, 'gl', 'APLICACION', 'Aplicación presupuestaria');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 215, 'gl', 'PRECIO_ADJUDICACION', 'Precio adjudicación');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 215, 'gl', 'PLAZO_EJECUCION', 'Plazo ejecución');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 215, 'gl', 'UD_PLAZO', 'Unidades plazo');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 215, 'gl', 'GARANTIA_PROVISIONAL', 'Garantía provisional');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 215, 'gl', 'GARANTIA_DEFINITIVA', 'Garantía definitiva');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 215, 'gl', 'CLASIFICACION', 'Clasificación');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 215, 'gl', 'FECHA_FIN_EJECUCION', 'Fecha fin ejecución');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 215, 'gl', 'FECHA_FIN_GARANTIA', 'Fecha fin garantía');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 215, 'gl', 'CONTRATO', 'Datos contratación');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 216, 'gl', 'VLDTBL_SAN_GRADO', 'Grado infracción');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 217, 'gl', 'VLDTBL_SAN_CALIFICACION', 'Calificación sanción');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 218, 'gl', 'SANCION', 'Datos sanción');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 218, 'gl', 'INSTRUCTOR', 'Instructor');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 218, 'gl', 'CALIFICACION', 'Calificación');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 218, 'gl', 'IMPORTE', 'Importe sanción');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 218, 'gl', 'GRADO', 'Grado infracción');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 218, 'gl', 'INFRACCION', 'Infracción');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 218, 'gl', 'HECHOS', 'Hechos denunciados');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 218, 'gl', 'ACTUACION', 'Actuación');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 219, 'gl', 'CLASIFICACION_ACTIVIDADES', 'Clasificación de Actividades');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 220, 'gl', 'ACTIVIDAD_CLASIFICADA', 'Actividad clasificada');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 220, 'gl', 'ACTIVIDAD', 'Actividad');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 220, 'gl', 'CLASIFICACION', 'Clasificación');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 220, 'gl', 'EMPLAZAMIENTO', 'Emplazamiento');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 220, 'gl', 'TASAS', 'Tasas');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 221, 'gl', 'ACTIVIDAD_NO_CLASIFICADA', 'Actividad No Clasificada');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 221, 'gl', 'ACTIVIDAD', 'Actividad');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 221, 'gl', 'EMPLAZAMIENTO', 'Emplazamiento');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 221, 'gl', 'TASAS', 'Tasas');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 222, 'gl', 'TIPO_VADO', 'Tipo de Vado');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 223, 'gl', 'REBAJE_ACERA', 'Tipo de Rebaje de Acera');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 224, 'gl', 'VADO', 'Vado');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 224, 'gl', 'NUMLICENCIA', 'Número de Licencia');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 224, 'gl', 'UBICACION', 'Ubicación');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 224, 'gl', 'TIPO', 'Tipo de Vado');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 224, 'gl', 'LARGOENTRADA', 'Largo de la entrada (m)');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 224, 'gl', 'ANCHOACERA', 'Ancho de la acera (m)');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 224, 'gl', 'SUPERFICIEACERA', 'Superficie de la acera (m2)');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 224, 'gl', 'SUPERFICIELOCAL', 'Superficie del local (m2)');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 224, 'gl', 'ACTIVIDAD', 'Actividad o uso del local');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 224, 'gl', 'NUMVEHÍCULOS', 'Número de plazas para vehículos');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 224, 'gl', 'REBAJE', 'Rebaje');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 224, 'gl', 'TASAS', 'Tasas');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 224, 'gl', 'NUMVEHICULOS', 'Número de plazas para vehículos');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 225, 'gl', 'VLDTBL_TIPO_RECLAM', 'Tipo de reclamación');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 226, 'gl', 'VLDTBL_TIPO_DESTI', 'Tipo de destinatario');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 227, 'gl', 'TIPO', 'Tipo');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 227, 'gl', 'DESCRIPCION', 'Descripción');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 227, 'gl', 'DESTINATARIO', 'Destinatario');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 227, 'gl', 'ACEPTADA', 'Aceptada');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 227, 'gl', 'RECLAMACION', 'Reclamación');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 228, 'gl', 'VLDTBL_TIPO_TARJETA', 'Tipo tarjeta minusvalido');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 229, 'gl', 'DATOS_TARJETA_MINUS', 'Datos tarjeta');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 229, 'gl', 'TIPO_TARJETA', 'Tipo de tarjeta');
UPDATE SPAC_SQ_SEQUENCES SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'; 
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (@sequence_resources_id, 229, 'gl', 'FECHA_CADUCIDAD', 'Fecha caducidad');


-- Actualizar secuencia de spac_ct_entidades_resources
--UPDATE SPAC_SQ_SEQUENCES SET SEQUENCE_ID = 504 WHERE SEQUENCE_NAME = 'SPAC_SQ_ID_ENTIDADESRESOURCES';



--
-- Procedimientos
--
INSERT INTO spac_ct_procedimientos (id, cod_pcd, nombre, id_padre, titulo, objeto, asunto, act_func, mtrs_comp, sit_tlm, url, interesado, tp_rel, org_rsltr, forma_inic, plz_resol, unid_plz, finicio, ffin, efec_silen, fin_via_admin, recursos, fcatalog, autor, estado, nversion, observaciones, lugar_present, cnds_ecnmcs, ingresos, f_cbr_pgo, infr_sanc, calendario, dscr_tram, normativa, cnd_particip, documentacion, grupos_delegacion, cod_sistema_productor, mapeo_rt) VALUES (7, 'PCD-15', 'Acometida de agua', 1, 'Acometida de agua', NULL, 'Acometida de agua', NULL, NULL, NULL, NULL, NULL, 'INT', NULL, NULL, 15, '2', NULL, NULL, 'D', 'NO', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'Memoria Técnica suscrita por el Técnico Autor del Proyecto de las obras de Edificación, o, en su caso, de las instalaciones que se trate. 
Escritura de la propiedad o documento que acredite la disponibilidad del inmueble para el que se solicita la acometida.
Licencia municipal de obras, o informe favorable del Ayuntamiento.
Plano de emplazamiento de las acometidas, acotado en fachada a lindes de parcela.
Plano de situación de la parcela a escala 1:1000. 
Documentación gráfica de la edificación (planta y secciones transversales). 
', NULL, '00', NULL);

INSERT INTO spac_ct_procedimientos (id, cod_pcd, nombre, id_padre, titulo, objeto, asunto, act_func, mtrs_comp, sit_tlm, url, interesado, tp_rel, org_rsltr, forma_inic, plz_resol, unid_plz, finicio, ffin, efec_silen, fin_via_admin, recursos, fcatalog, autor, estado, nversion, observaciones, lugar_present, cnds_ecnmcs, ingresos, f_cbr_pgo, infr_sanc, calendario, dscr_tram, normativa, cnd_particip, documentacion, grupos_delegacion, cod_sistema_productor, mapeo_rt) VALUES (8, '025', 'Cambio de Titular de Licencia de Apertura', 1, 'Cambio de Titular de Licencia de Apertura', 'Realización del traspaso de la licencia de apertura de un establecimiento.', 'Cambio de Titular de Licencia de Apertura', 'AL', 'VU', 'NO', NULL, 'Persona física/jurídica para abrir/modificar actividad inocua', 'INT', NULL, 'I', 6, '3', NULL, NULL, 'D', 'NO', 'Reclamación económico-administrativa ante el Tribunal Económico-administrativo Regional. Si el valor catastral excede de 1.800.000 euros, dicha reclamación podrá interponerse directamente, ante el Tribunal Económico-administrativo Central.
Con carácter potestativo y previo a dicha reclamación económico-administrativa se puede interponer recurso de reposición ante la Gerencia/Subgerencia del Catastro, no siendo admisible la interposición simultánea de ambos recursos.', '2008-02-10', 'CVP', NULL, NULL, 'Procedimiento de Cambio de Titular de Licencia de Apertura', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'Texto refundido de la Ley del Catastro Inmobiliario aprobado por Real Decreto Legislativo 1/2004, de 5 de marzo, Real Decreto 417/2006, de 7 de abril, por el que se desarrolla el texto refundido de la Ley del Catastro Inmobiliario y Orden EHA/3482/2006, de 19 de octubre por la que se aprueban los modelos de declaración de alteraciones catastrales de los bienes inmuebles y se determina la información gráfica y alfanumérica necesaria para la tramitación de determinadas comunicaciones catastrales.', NULL, '-Instancia/solicitud de cambio de titular, firmada por ambos (actual titular y adquiriente).
- Fotocopia de la licencia.
- Justificante/fotocopia de alta en I.A.E.
', NULL, '00', '<?xml version="1.0" encoding="ISO-8859-1"?>
<procedure>
	<table name="CAMB_TITULAR_LA">
		<mappings>
			<!-- Mapeos de los datos específicos del expediente -->
			<field name="ACTIVIDAD" value="${xpath:/datos_especificos/Actividad}"/>
			<field name="EMPLAZAMIENTO" value="${xpath:/datos_especificos/Emplazamiento}"/>
			<!--<field name="FECHA_LICENCIA" value="${xpath:/datos_especificos/Fecha_Licencia}"/>-->
			<field name="DENOMINACION" value="${xpath:/datos_especificos/Denominacion}"/>

		</mappings>
	</table>	
	<table name="SPAC_DT_INTERVINIENTES">
		<mappings>
			<!-- Mapeos de los datos específicos del expediente -->
			<field name="NDOC" value="${xpath:/datos_especificos/Documento_Identificacion_Participante}"/>
			<field name="NOMBRE" value="${xpath:/datos_especificos/Nombre_Participante}"/>
			<field name="ROL" value="${xpath:/datos_especificos/Relacion_Participante}"/>
			<field name="DIRNOT" value="${xpath:/datos_especificos/Domicilio_Notificacion_Participante}"/>
			<field name="C_POSTAL" value="${xpath:/datos_especificos/Codigo_Postal_Participante}"/>
			<field name="LOCALIDAD" value="${xpath:/datos_especificos/Localidad_Participante}"/>
			<field name="CAUT" value="${xpath:/datos_especificos/Provincia_Participante}"/>
			<field name="TFNO_FIJO" value="${xpath:/datos_especificos/Telefono_Participante}"/>			
			<field name="EMAIL" value="${xpath:/datos_especificos/Email_Participante}"/>			
		</mappings>
	</table>
	<table name="SPAC_EXPEDIENTES">
		<mappings>
			<!-- Mapeos de los datos específicos del expediente -->
			<field name="ROLTITULAR" value="${xpath:/datos_especificos/Relacion}"/>			
		</mappings>
	</table>
</procedure>');
INSERT INTO spac_ct_procedimientos (id, cod_pcd, nombre, id_padre, titulo, objeto, asunto, act_func, mtrs_comp, sit_tlm, url, interesado, tp_rel, org_rsltr, forma_inic, plz_resol, unid_plz, finicio, ffin, efec_silen, fin_via_admin, recursos, fcatalog, autor, estado, nversion, observaciones, lugar_present, cnds_ecnmcs, ingresos, f_cbr_pgo, infr_sanc, calendario, dscr_tram, normativa, cnd_particip, documentacion, grupos_delegacion, cod_sistema_productor, mapeo_rt) VALUES (9, 'PCD-11', 'Certificado Urbanístico', 1, 'Certificado Urbanístico', 'Solicitud de certificado / información urbanística', 'Certificado Urbanístico', 'AL', '08', NULL, NULL, 'Persona física/jurídica que solicita la certificación urbanísta', 'INT', NULL, 'I', 3, '3', NULL, NULL, 'D', 'SI', NULL, '2008-01-07', 'cvp', NULL, NULL, 'Procedimiento de Certificado Urbanístico para proyecto Concellos Fase 1', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'LBRL: Ley 7/1985, de 2 de abril, de Bases de Régimen Local.
LHL: Ley 39/1988, de 28 de diciembre, reguladora de las Haciendas Locales.
ROF: Real Decreto 2568/1986, de 28 de noviembre, Reglamento de Organización, Funcionamiento y Régimen Jurídico de las Entidades Locales.
RS: Decreto de 17 de junio de 1955, Reglamento de Servicios de las Corporaciones Locales.
LPC: Ley 30/1992, de 26 de noviembre, de Régimen Jurídico de las Administraciones Públicas y del Procedimiento Administrativo Común.
LJ: Ley 29/1998, de 13 de julio, de la Jurisdicción Contencioso- Administrativa.
RDU: Real Decreto 2187/1978, de 23 de junio, Reglamento de Disciplina Urbanística.
LOUGA: Ley 9/2002, de 30 de diciembre, de Ordenación Urbanística y Protección del Medio Rural de Galicia.
LMLOUGA: Ley 15/2004, de 29 de diciembre, de modificación de la Ley 9/2002, de 30 de diciembre, de Ordenación Urbanística y Protección del Medio Rural de Galicia.', NULL, 'Impreso de solicitud
Justificante de autoliquidación de la tasa por emisión de certificados
Plano de localización sobre cartografía oficial, en el que se refleje la posición exacta de la finca/parcela/edificio objeto de la solicitud
Documento de Identificación Personal del solicitante
Acreditación de la condición de interesado (escritura de propiedad, ...)', NULL, '00', '<?xml version="1.0" encoding="ISO-8859-1"?>
<procedure>
	<table name="CERT_URBANISTICO">
		<mappings>
			<!-- Mapeos de los datos específicos del expediente -->
			<field name="SITUACION" value="${xpath:/datos_especificos/Situacion_Inmueble}"/>
		</mappings>
	</table>
	<table name="SPAC_DT_INTERVINIENTES">
		<mappings>
			<!-- Mapeos de los datos específicos del expediente -->
			<field name="NDOC" value="${xpath:/datos_especificos/Nif_Representante}"/>
			<field name="NOMBRE" value="${xpath:/datos_especificos/Nombre_Representante}"/>
			<field name="ROL" value="REPR"/>
			<field name="DIRNOT" value="${xpath:/datos_especificos/Domicilio_Representante}"/>
			<field name="EMAIL" value="${xpath:/datos_especificos/Email_Representante}"/>
			<field name="C_POSTAL" value="${xpath:/datos_especificos/Codigo_Postal_Representante}"/>
			<field name="LOCALIDAD" value="${xpath:/datos_especificos/Localidad_Representante}"/>
			<field name="CAUT" value="${xpath:/datos_especificos/Provincia_Representante}"/>
			<field name="TFNO_FIJO" value="${xpath:/datos_especificos/Telefono_Representante}"/>						
		</mappings>
	</table>
</procedure>');
INSERT INTO spac_ct_procedimientos (id, cod_pcd, nombre, id_padre, titulo, objeto, asunto, act_func, mtrs_comp, sit_tlm, url, interesado, tp_rel, org_rsltr, forma_inic, plz_resol, unid_plz, finicio, ffin, efec_silen, fin_via_admin, recursos, fcatalog, autor, estado, nversion, observaciones, lugar_present, cnds_ecnmcs, ingresos, f_cbr_pgo, infr_sanc, calendario, dscr_tram, normativa, cnd_particip, documentacion, grupos_delegacion, cod_sistema_productor, mapeo_rt) VALUES (10, 'PCD-10', 'Contrato negociado', 1, 'Contrato negociado', 'Contratación administrativa mediante el procedimiento negociado', 'Contratación administrativa mediante el procedimiento negociado', 'CA', '02', NULL, NULL, 'Órgano de contratación', 'INT', NULL, 'O', NULL, NULL, NULL, NULL, NULL, 'SI', 'EC', '2008-20-05', 'sigem', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'LEY 30/2007, de 30 de octubre, de Contratos del Sector Público', NULL, 'Acreditación de la capacidad de obrar
Prueba de la no concurrencia de una prohibición de contratar
Acreditación de solvencia
Oferta técnica
Oferta económica
', NULL, '00', '<?xml version="1.0" encoding="ISO-8859-1"?>
<procedure>
		<table name="CONTRATO">
		<mappings>
			<!-- Mapeos de los datos específicos del expediente -->
			<field name="TIPO" value="${xpath:/datos_especificos/Tipo_Contrato}"/>
			<field name="FORMA_ADJUDICACION" value="${xpath:/datos_especificos/Forma_Adjudicacion}"/>
			<field name="PRESUPUESTO_MAXIMO" value="${xpath:/datos_especificos/Presupuesto_Maximo}"/>
			<!--<field name="APLICACION" value="${xpath:/datos_especificos/Aplicacion_Presupuestaria}"/>-->
			<field name="PRECIO_ADJUDICACION" value="${xpath:/datos_especificos/Precio_Adjudicacion}"/>
			<field name="PLAZO_EJECUCION" value="${xpath:/datos_especificos/Plazo_Ejecucion}"/>
			<field name="UD_PLAZO" value="${xpath:/datos_especificos/Unidades_Plazo}"/>
			<!--<field name="GARANTIA_PROVISIONAL" value="${xpath:/datos_especificos/Garantia_Provisional}"/>
			<field name="GARANTIA_DEFINITIVA" value="${xpath:/datos_especificos/Garantia_Definitiva}"/>
			<field name="CLASIFICACION" value="${xpath:/datos_especificos/Clasificacion}"/>-->
<!--
			<field name="FECHA_FIN_EJECUCION" value="${xpath:/datos_especificos/Fecha_Final_Ejecucion}"/>
			<field name="FECHA_FIN_GARANTIA" value="${xpath:/datos_especificos/Fecha_Final_Garantia}"/>
-->
		</mappings>
	</table>
	<table name="SPAC_DT_INTERVINIENTES">
		<mappings>
			<!-- Mapeos de los datos específicos del expediente -->
			<field name="NDOC" value="${xpath:/datos_especificos/Documento_Identificacion_Contratado}"/>
			<field name="NOMBRE" value="${xpath:/datos_especificos/Nombre_Contratado}"/>
			<field name="ROL" value="${xpath:/datos_especificos/Relacion_Contratado}"/>
			<field name="DIRNOT" value="${xpath:/datos_especificos/Domicilio_Notificacion_Contratado}"/>
			<field name="C_POSTAL" value="${xpath:/datos_especificos/Codigo_Postal_Contratado}"/>
			<field name="LOCALIDAD" value="${xpath:/datos_especificos/Localidad_Contratado}"/>
			<field name="CAUT" value="${xpath:/datos_especificos/Provincia_Contratado}"/>
			<field name="TFNO_FIJO" value="${xpath:/datos_especificos/Telefono_Contratado}"/>			
			<field name="EMAIL" value="${xpath:/datos_especificos/Email_Contratado}"/>			
		</mappings>
	</table>
	<table name="SPAC_EXPEDIENTES">
		<mappings>
			<!-- Mapeos de los datos específicos del expediente -->
			<field name="ROLTITULAR" value="${xpath:/datos_especificos/Relacion}"/>			
		</mappings>
	</table>
</procedure>');
INSERT INTO spac_ct_procedimientos (id, cod_pcd, nombre, id_padre, titulo, objeto, asunto, act_func, mtrs_comp, sit_tlm, url, interesado, tp_rel, org_rsltr, forma_inic, plz_resol, unid_plz, finicio, ffin, efec_silen, fin_via_admin, recursos, fcatalog, autor, estado, nversion, observaciones, lugar_present, cnds_ecnmcs, ingresos, f_cbr_pgo, infr_sanc, calendario, dscr_tram, normativa, cnd_particip, documentacion, grupos_delegacion, cod_sistema_productor, mapeo_rt) VALUES (11, '019', 'Expediente sancionador', 1, 'Expediente sancionador', 'El ejercicio por la Administración Pública de la potestad sancionadora sobre las materias que corresponda, desarrolladas por ordenanzas o reglamentos que contemplen sanciones, en el marco de las competencias atribuidas como propias.', 'Expediente sancionador', 'SA', 'VU', NULL, NULL, 'Órgano competente con competencia expresamente atribuida', 'DENO', NULL, 'O', 6, '3', NULL, NULL, NULL, 'SI', 'Recurso potestativo de reposición
Recurso contencioso-administrativo
Reclamación económico administrativa contra las resoluciones que conlleven una sanción pecuniaria o económica
', NULL, 'sigem', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'Ordenanza reguladora del procedimiento para el ejercicio de la potestad sancionadora por la Administración Pública 

Ley 30/92, de 26 de noviembre Ley de régimen jurídico de las Administraciones Públicas y del procedimiento administrativo común.

Ley 9/2002, de 30 de diciembre, de Ordenación Urbanística y Protección del Medio Rural de Galicia.

Real Decreto 1398/1993, de 4 de agosto, por el que se aprueba el Reglamento del Procedimiento para el Ejercicio de la Potestad Sancionadora.
', NULL, NULL, NULL, '00', '<?xml version="1.0" encoding="ISO-8859-1"?>
<procedure>
<table name="SPAC_EXPEDIENTES">
		<mappings>
			<!-- Mapeos de los datos específicos del expediente -->			<field name="IDENTIDADTITULAR" value="${xpath:/datos_especificos/Nombre_DEN}"/>
			<field name="NIFCIFTITULAR" value="${xpath:/datos_especificos/NIF_DEN}"/>
		</mappings>
	</table>

	<table name="SANCION">
		<mappings>
			<!-- Mapeos de los datos específicos del expediente -->
			<field name="HECHOS" value="${xpath:/datos_especificos/Hechos_Denunciados}"/>
		</mappings>
	</table>
	<table name="SPAC_DT_INTERVINIENTES">
		<mappings>
			<!-- Mapeos de los datos específicos del expediente -->
			<field name="NDOC" value="${xpath:/datos_especificos/Documento_Identidad_Denunciante}"/>
			<field name="NOMBRE" value="${xpath:/datos_especificos/Nombre_Denunciante}"/>
			<field name="ROL" value="DENT"/>
			<field name="DIRNOT" value="${xpath:/datos_especificos/Domicilio_Notificacion_Denunciante}"/>
			<field name="EMAIL" value="${xpath:/datos_especificos/Correo_Electronico_Denunciante}"/>
			<field name="C_POSTAL" value="${xpath:/datos_especificos/Codigo_Postal_Denunciante}"/>
			<field name="LOCALIDAD" value="${xpath:/datos_especificos/Localidad_Denunciante}"/>
			<field name="CAUT" value="${xpath:/datos_especificos/Provincia_Denunciante}"/>
			<field name="TFNO_FIJO" value="${xpath:/datos_especificos/Telefono_Denunciante}"/>						
		</mappings>
	</table>
</procedure>');
INSERT INTO spac_ct_procedimientos (id, cod_pcd, nombre, id_padre, titulo, objeto, asunto, act_func, mtrs_comp, sit_tlm, url, interesado, tp_rel, org_rsltr, forma_inic, plz_resol, unid_plz, finicio, ffin, efec_silen, fin_via_admin, recursos, fcatalog, autor, estado, nversion, observaciones, lugar_present, cnds_ecnmcs, ingresos, f_cbr_pgo, infr_sanc, calendario, dscr_tram, normativa, cnd_particip, documentacion, grupos_delegacion, cod_sistema_productor, mapeo_rt) VALUES (12, '026', 'Licencia de Apertura de Actividad Clasificada', 1, 'Licencia de Apertura de Actividad Clasificada', 'Orden de comprobación de que la solicitud del administrado es conforme con las normas de uso previstas en los planes de urbanismo. ', 'Licencia de Apertura de Actividad Clasificada', 'AL', 'VU', 'NO', NULL, 'Persona física/jurídica para abrir/modificar actividad inocua', 'INT', NULL, 'I', 6, '3', NULL, NULL, 'E', 'SI', 'Recurso potestativo de reposición por plazo de un mes.
Recurso contencioso-administrativo, en el plazo de dos meses, ante el Juzgado de lo Contencioso-Administrativo que corresponda-', '2008-15-09', 'CVP', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '¿AIA: Decreto 133/2008, de 12 de xuño, polo que se regula a Avaliación da Incidencia Ambiental.
¿LBRL: Ley 7/1985, de 2 de abril, de Bases de Régimen Local.
¿LHL: Ley 39/1988, de 28 de diciembre, reguladora de las Haciendas Locales.
¿ROF: Real Decreto 2568/1986, de 28 de noviembre, Reglamento de Organización, Funcionamiento y Régimen Jurídico de las Entidades Locales.
¿RS: Decreto de 17 de junio de 1955, Reglamento de Servicios de las Corporaciones Locales.
¿LPC: Ley 30/1992, de 26 de noviembre, de Régimen Jurídico de las Administraciones Públicas y del Procedimiento Administrativo Común.
¿LJ: Ley 29/1998, de 13 de julio, de la Jurisdicción Contencioso- Administrativa.', NULL, 'Solicitud de licencia de apertura.
Proyecto técnico redactado por un técnico competente en la materia.
Memoria descriptiva en la que se detallen los aspectos básicos relativos a la actividad, a su localicación y repercusiones en el medio ambiente.
Justificante del cumplimiento de la legislación ambiental y sectorial aplicable a la actividad, de la normativa de seguridad, de la relativa a las medidas de autoprotección y salud y, si procede, de las ordenanzas municipales que resulten de aplicación.
Declaración de los datos que a juicio del solicitante gozan de confidencialidad amparada en la normativa vigente.
', NULL, '00', '<?xml version="1.0" encoding="ISO-8859-1"?>
<procedure>
	<table name="ACTIVIDAD_CLASIFICADA">
		<mappings>
			<!-- Mapeos de los datos específicos del expediente -->
			<field name="ACTIVIDAD" value="${xpath:/datos_especificos/Actividad}"/>
			<field name="EMPLAZAMIENTO" value="${xpath:/datos_especificos/Emplazamiento}"/>
			<field name="CLASIFICACION" value="${xpath:/datos_especificos/Clasificacion}"/>

		</mappings>
	</table>
<table name="SPAC_EXPEDIENTES">
		<mappings>
			<!-- Mapeos de los datos específicos del expediente -->
			<field name="ROLTITULAR" value="${xpath:/datos_especificos/Relacion}"/>			
		</mappings>
	</table>
</procedure>');
INSERT INTO spac_ct_procedimientos (id, cod_pcd, nombre, id_padre, titulo, objeto, asunto, act_func, mtrs_comp, sit_tlm, url, interesado, tp_rel, org_rsltr, forma_inic, plz_resol, unid_plz, finicio, ffin, efec_silen, fin_via_admin, recursos, fcatalog, autor, estado, nversion, observaciones, lugar_present, cnds_ecnmcs, ingresos, f_cbr_pgo, infr_sanc, calendario, dscr_tram, normativa, cnd_particip, documentacion, grupos_delegacion, cod_sistema_productor, mapeo_rt) VALUES (13, '024', 'Licencia de Apertura de Actividad No Clasificada', 1, 'Licencia de Apertura de Actividad No Clasificada', 'Orden de comprobación de que la solicitud del administrado es conforme con las normas de uso previstas en los planes de urbanismo. ', 'Licencia de Apertura de Actividad No Clasificada', 'AL', 'VU', 'NO', NULL, 'Persona física/jurídica para abrir/modificar actividad inocua', 'INT', NULL, 'I', 3, '3', NULL, NULL, 'E', 'SI', 'Recurso potestativo de reposición por plazo de un mes.
Recurso contencioso-administrativo, en el plazo de dos meses, ante el Juzgado de lo Contencioso-Administrativo que corresponda-', '2008-15-09', 'CVP', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '¿LBRL: Ley 7/1985, de 2 de abril, de Bases de Régimen Local.
¿LHL: Ley 39/1988, de 28 de diciembre, reguladora de las Haciendas Locales.
¿ROF: Real Decreto 2568/1986, de 28 de noviembre, Reglamento de Organización, Funcionamiento y Régimen Jurídico de las Entidades Locales.
¿RS: Decreto de 17 de junio de 1955, Reglamento de Servicios de las Corporaciones Locales.
¿LPC: Ley 30/1992, de 26 de noviembre, de Régimen Jurídico de las Administraciones Públicas y del Procedimiento Administrativo Común.
¿LJ: Ley 29/1998, de 13 de julio, de la Jurisdicción Contencioso- Administrativa.', NULL, 'Solicitud de licencia de apertura
Proyecto técnico y Memoria valorada descriptiva de la actividad y justificaciónde las condiciones higiénicas, sanitarias y de seguridad
Croquis o planos de planta y sección, acotados, con el máximo detalle posible (indicando el uso de cada dependencia, las luces de emergencia, colocación de extintores, etc.), y de situación del local respecto a las calles donde se halla ubicado y en relación al resto de los locales del edificio
Justificante/fotocopia de alta en I.A.E. (para liquidación)
Contrato de arrendamiento o escritura de la propiedad
', NULL, '00', '<?xml version="1.0" encoding="ISO-8859-1"?>
<procedure>
	<table name="ACTIVIDAD_NO_CLASIFICADA">
		<mappings>
			<!-- Mapeos de los datos específicos del expediente -->
			<field name="ACTIVIDAD" value="${xpath:/datos_especificos/Actividad}"/>
			<field name="EMPLAZAMIENTO" value="${xpath:/datos_especificos/Emplazamiento}"/>

		</mappings>
	</table>
<table name="SPAC_EXPEDIENTES">
		<mappings>
			<!-- Mapeos de los datos específicos del expediente -->
			<field name="ROLTITULAR" value="${xpath:/datos_especificos/Relacion}"/>			
		</mappings>
	</table>
</procedure>');
INSERT INTO spac_ct_procedimientos (id, cod_pcd, nombre, id_padre, titulo, objeto, asunto, act_func, mtrs_comp, sit_tlm, url, interesado, tp_rel, org_rsltr, forma_inic, plz_resol, unid_plz, finicio, ffin, efec_silen, fin_via_admin, recursos, fcatalog, autor, estado, nversion, observaciones, lugar_present, cnds_ecnmcs, ingresos, f_cbr_pgo, infr_sanc, calendario, dscr_tram, normativa, cnd_particip, documentacion, grupos_delegacion, cod_sistema_productor, mapeo_rt) VALUES (14, '023', 'Licencia de Vado', 1, 'Licencia de Vado', 'Establecer los requisitos y el procedimiento para el otorgamiento de licencia de vado.', 'Licencia de Vado', 'AL', 'VU', 'NO', NULL, 'Propietarios de inmuebles,a los que haya que permitir el acceso ', 'INT', NULL, 'I', 3, '3', NULL, NULL, 'D', 'SI', 'Recurso potestativo de reposición por plazo de un mes.
Recurso contencioso-administrativo, en el plazo de dos meses, ante el Juzgado de lo Contencioso-Administrativo que corresponda-', '2008-01-10', 'CVP', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '¿Ordenanza Municipal Reguladora de la Expedición de Licencias de Vado.
¿Plan General de Ordenación Urbanística.
¿Decreto 133/2008 de Avaliación da Incidencia Ambiental
¿Ordenanza Fiscal Municipal Reguladora del Precio Público.
¿LBRL: Ley 7/1985, de 2 de abril, de Bases de Régimen Local.
¿LHL: Ley 39/1988, de 28 de diciembre, reguladora de las Haciendas Locales.
¿ROF: Real Decreto 2568/1986, de 28 de noviembre, Reglamento de Organización, Funcionamiento y Régimen Jurídico de las Entidades Locales.
¿RS: Decreto de 17 de junio de 1955, Reglamento de Servicios de las Corporaciones Locales.
¿LPC: Ley 30/1992, de 26 de noviembre, de Régimen Jurídico de las Administraciones Públicas y del Procedimiento Administrativo Común.
¿LJ: Ley 29/1998, de 13 de julio, de la Jurisdicción Contencioso- Administrativa.', NULL, 'Solicitud de licencia de vado
Licencias de obras y apertura de los inmuebles a los que se accede
Plano de situación a escala 1/200
Plano de planta a escala 1/50
', NULL, '00', '<?xml version="1.0" encoding="ISO-8859-1"?>
<procedure>
	<table name="VADO">
		<mappings>
			<!-- Mapeos de los datos específicos del expediente -->
			<field name="UBICACION" value="${xpath:/datos_especificos/Ubicacion}"/>
			<field name="TIPO" value="${xpath:/datos_especificos/Tipo_Vado}"/>
			<field name="ACTIVIDAD" value="${xpath:/datos_especificos/Actividad}"/>
			<field name="REBAJE" value="${xpath:/datos_especificos/Rebaje}"/>
			<field name="NUMVEHICULOS" value="${xpath:/datos_especificos/Numero}"/>
		</mappings>
	</table>	
</procedure>');
INSERT INTO spac_ct_procedimientos (id, cod_pcd, nombre, id_padre, titulo, objeto, asunto, act_func, mtrs_comp, sit_tlm, url, interesado, tp_rel, org_rsltr, forma_inic, plz_resol, unid_plz, finicio, ffin, efec_silen, fin_via_admin, recursos, fcatalog, autor, estado, nversion, observaciones, lugar_present, cnds_ecnmcs, ingresos, f_cbr_pgo, infr_sanc, calendario, dscr_tram, normativa, cnd_particip, documentacion, grupos_delegacion, cod_sistema_productor, mapeo_rt) VALUES (15, 'PCD-16', 'Reclamación de tributos', 1, 'Reclamación de tributos', NULL, 'Reclamación de tributos', NULL, NULL, NULL, NULL, NULL, 'INT', NULL, 'I', 1, '4', NULL, NULL, 'D', 'SI', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'Escrito de interposición.
Documentos justificantes del motivo de la reclamación (recibo duplicado, cambio de titularidad, datos erróneos, etc.).', NULL, '00', '<?xml version="1.0" encoding="ISO-8859-1"?>
<procedure>
	<table name="RECLAMACION">
		<mappings>
			<!-- Mapeos de los datos específicos del expediente -->
			<field name="DESCRIPCION" value="${xpath:/datos_especificos/Descripcion}"/>
			
		</mappings>
	</table>	
</procedure>');
INSERT INTO spac_ct_procedimientos (id, cod_pcd, nombre, id_padre, titulo, objeto, asunto, act_func, mtrs_comp, sit_tlm, url, interesado, tp_rel, org_rsltr, forma_inic, plz_resol, unid_plz, finicio, ffin, efec_silen, fin_via_admin, recursos, fcatalog, autor, estado, nversion, observaciones, lugar_present, cnds_ecnmcs, ingresos, f_cbr_pgo, infr_sanc, calendario, dscr_tram, normativa, cnd_particip, documentacion, grupos_delegacion, cod_sistema_productor, mapeo_rt) VALUES (16, '013', 'Tarjeta de estacionamiento para minusválidos', 1, 'Tarjeta de estacionamiento para minusválidos', 'Facilite la tarjeta de estacionamiento a lo/as vecino/as con graves problemas de movilidad', 'Tarjeta de estacionamiento para minusválidos', 'AL', 'VU', NULL, NULL, 'Persona física/jurídica que solicita la tarjeta', 'INT', NULL, 'I', 3, '3', NULL, NULL, 'D', 'SI', NULL, '2008-01-07', 'Lidia García Morán', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'Solicitud, DNI, Certificado de minusvalía, Informe médico, Certificado de empadronamiento,
Dos fotografías originales tamaño carnet. 
Permiso de conducir (fotocopia) bien del minusválido, en el caso de que sea éste el conductor del vehículo o bien de la persona que lo transporte habitualmente. 
Declaración jurada del conductor habitual del vehículo alegando dicha condición, en el supuesto de que no sea el propio minusválido. ', NULL, '00', '<?xml version="1.0" encoding="ISO-8859-1"?>
<procedure>
	<table name="DATOS_TARJETA_MINUS">
		<mappings>
			<!-- Mapeos de los datos específicos del expediente -->
			<field name="TIPO_TARJETA" value="${xpath:/datos_especificos/Tipo_Tarjeta}"/>
		</mappings>
	</table>
	<table name="SPAC_DT_INTERVINIENTES">
		<mappings>
			<!-- Mapeos de los datos específicos del expediente -->
			<field name="NDOC" value="${xpath:/datos_especificos/Documento_Identidad_Representante}"/>
			<field name="NOMBRE" value="${xpath:/datos_especificos/Nombre_Representante}"/>
			<field name="DIRNOT" value="${xpath:/datos_especificos/Domicilio_Notificacion_Representante}"/>
			<field name="EMAIL" value="${xpath:/datos_especificos/Email_Representante}"/>
			<field name="C_POSTAL" value="${xpath:/datos_especificos/Codigo_Postal_Representante}"/>
			<field name="LOCALIDAD" value="${xpath:/datos_especificos/Localidad_Representante}"/>
			<field name="CAUT" value="${xpath:/datos_especificos/Provincia_Representante}"/>
			<field name="TFNO_FIJO" value="${xpath:/datos_especificos/Telefono_Representante}"/>						
		</mappings>
	</table>
</procedure>');



--
-- Fases
--

INSERT INTO spac_ct_fases (id, nombre, orden, cod_fase, falta, descripcion, observaciones, autor) VALUES (6, 'Fase Preparación', 5, NULL, getDate(), NULL, NULL, 'SYSSUPERUSER');
INSERT INTO spac_ct_fases (id, nombre, orden, cod_fase, falta, descripcion, observaciones, autor) VALUES (7, 'Fase Licitación', 6, NULL, getDate(), NULL, NULL, 'SYSSUPERUSER');
INSERT INTO spac_ct_fases (id, nombre, orden, cod_fase, falta, descripcion, observaciones, autor) VALUES (8, 'Fase Adjudicación', 7, NULL, getDate(), NULL, NULL, 'SYSSUPERUSER');
INSERT INTO spac_ct_fases (id, nombre, orden, cod_fase, falta, descripcion, observaciones, autor) VALUES (9, 'Fase Ejecución', 8, NULL, getDate(), NULL, NULL, 'SYSSUPERUSER');


-- Actualizar secuencia
UPDATE SPAC_SQ_SEQUENCES SET SEQUENCE_ID = 10 WHERE SEQUENCE_NAME = 'SPAC_SQ_ID_CTFASES';


--
-- Trámites
--

INSERT INTO spac_ct_tramites (id, nombre, cod_tram, descripcion, tipo, ordenacion, observaciones, fcreacion, autor, id_subproceso) VALUES (18, 'Solicitud subsanación', NULL, NULL, NULL, NULL, NULL, getDate(), 'SYSSUPERUSER', NULL);
INSERT INTO spac_ct_tramites (id, nombre, cod_tram, descripcion, tipo, ordenacion, observaciones, fcreacion, autor, id_subproceso) VALUES (19, 'Informe técnico', NULL, NULL, NULL, NULL, NULL, getDate(), 'SYSSUPERUSER', NULL);
INSERT INTO spac_ct_tramites (id, nombre, cod_tram, descripcion, tipo, ordenacion, observaciones, fcreacion, autor, id_subproceso) VALUES (20, 'Actuaciones previas', NULL, 'Posibles actuaciones previas, destinadas a determinar si concurren circunstancias para incoar un procedimiento sancionador.', NULL, NULL, NULL, getDate(), 'SYSSUPERUSER', NULL);
INSERT INTO spac_ct_tramites (id, nombre, cod_tram, descripcion, tipo, ordenacion, observaciones, fcreacion, autor, id_subproceso) VALUES (21, 'Informe jurídico', NULL, NULL, NULL, NULL, NULL, getDate(), 'SYSSUPERUSER', NULL);
INSERT INTO spac_ct_tramites (id, nombre, cod_tram, descripcion, tipo, ordenacion, observaciones, fcreacion, autor, id_subproceso) VALUES (22, 'Resolución de la autorización', NULL, NULL, NULL, NULL, NULL, getDate(), 'SYSSUPERUSER', NULL);
INSERT INTO spac_ct_tramites (id, nombre, cod_tram, descripcion, tipo, ordenacion, observaciones, fcreacion, autor, id_subproceso) VALUES (23, 'Propuesta de liquidación', NULL, NULL, NULL, NULL, NULL, getDate(), 'SYSSUPERUSER', NULL);
INSERT INTO spac_ct_tramites (id, nombre, cod_tram, descripcion, tipo, ordenacion, observaciones, fcreacion, autor, id_subproceso) VALUES (24, 'Licencia de acometida de agua', NULL, NULL, NULL, NULL, NULL, getDate(), 'SYSSUPERUSER', NULL);
INSERT INTO spac_ct_tramites (id, nombre, cod_tram, descripcion, tipo, ordenacion, observaciones, fcreacion, autor, id_subproceso) VALUES (25, 'Resolución expediente', NULL, NULL, NULL, NULL, NULL, getDate(), 'SYSSUPERUSER', NULL);
INSERT INTO spac_ct_tramites (id, nombre, cod_tram, descripcion, tipo, ordenacion, observaciones, fcreacion, autor, id_subproceso) VALUES (26, 'Aportación documentación', NULL, NULL, NULL, NULL, NULL, getDate(), 'SYSSUPERUSER', NULL);
INSERT INTO spac_ct_tramites (id, nombre, cod_tram, descripcion, tipo, ordenacion, observaciones, fcreacion, autor, id_subproceso) VALUES (27, 'Emisión licencia', NULL, NULL, NULL, NULL, NULL, getDate(), 'SYSSUPERUSER', NULL);
INSERT INTO spac_ct_tramites (id, nombre, cod_tram, descripcion, tipo, ordenacion, observaciones, fcreacion, autor, id_subproceso) VALUES (28, 'Emisión Certificado', NULL, 'Emisión de Certificado Urbanístico', NULL, NULL, NULL, getDate(), 'SYSSUPERUSER', NULL);
INSERT INTO spac_ct_tramites (id, nombre, cod_tram, descripcion, tipo, ordenacion, observaciones, fcreacion, autor, id_subproceso) VALUES (29, 'Requerir subsanación', NULL, NULL, NULL, NULL, NULL, getDate(), 'SYSSUPERUSER', NULL);
INSERT INTO spac_ct_tramites (id, nombre, cod_tram, descripcion, tipo, ordenacion, observaciones, fcreacion, autor, id_subproceso) VALUES (30, 'Propuesta de inicio', NULL, NULL, NULL, NULL, NULL, getDate(), 'SYSSUPERUSER', NULL);
INSERT INTO spac_ct_tramites (id, nombre, cod_tram, descripcion, tipo, ordenacion, observaciones, fcreacion, autor, id_subproceso) VALUES (31, 'Cláusulas administrativas', NULL, NULL, NULL, NULL, NULL, getDate(), 'SYSSUPERUSER', NULL);
INSERT INTO spac_ct_tramites (id, nombre, cod_tram, descripcion, tipo, ordenacion, observaciones, fcreacion, autor, id_subproceso) VALUES (32, 'Prescripciones técnicas', NULL, NULL, NULL, NULL, NULL, getDate(), 'SYSSUPERUSER', NULL);
INSERT INTO spac_ct_tramites (id, nombre, cod_tram, descripcion, tipo, ordenacion, observaciones, fcreacion, autor, id_subproceso) VALUES (33, 'Acta de replanteo previo', NULL, NULL, NULL, NULL, NULL, getDate(), 'SYSSUPERUSER', NULL);
INSERT INTO spac_ct_tramites (id, nombre, cod_tram, descripcion, tipo, ordenacion, observaciones, fcreacion, autor, id_subproceso) VALUES (34, 'Informe de intervención', NULL, NULL, NULL, NULL, NULL, getDate(), 'SYSSUPERUSER', NULL);
INSERT INTO spac_ct_tramites (id, nombre, cod_tram, descripcion, tipo, ordenacion, observaciones, fcreacion, autor, id_subproceso) VALUES (35, 'Informe de secretaría', NULL, NULL, NULL, NULL, NULL, getDate(), 'SYSSUPERUSER', NULL);
INSERT INTO spac_ct_tramites (id, nombre, cod_tram, descripcion, tipo, ordenacion, observaciones, fcreacion, autor, id_subproceso) VALUES (36, 'Licencia de obra', NULL, NULL, NULL, NULL, NULL, getDate(), 'SYSSUPERUSER', NULL);
INSERT INTO spac_ct_tramites (id, nombre, cod_tram, descripcion, tipo, ordenacion, observaciones, fcreacion, autor, id_subproceso) VALUES (37, 'Proyecto', NULL, NULL, NULL, NULL, NULL, getDate(), 'SYSSUPERUSER', NULL);
INSERT INTO spac_ct_tramites (id, nombre, cod_tram, descripcion, tipo, ordenacion, observaciones, fcreacion, autor, id_subproceso) VALUES (38, 'Resolución aprobación expediente', NULL, NULL, NULL, NULL, NULL, getDate(), 'SYSSUPERUSER', NULL);
INSERT INTO spac_ct_tramites (id, nombre, cod_tram, descripcion, tipo, ordenacion, observaciones, fcreacion, autor, id_subproceso) VALUES (39, 'Resolución nombramiento mesa', NULL, NULL, NULL, NULL, NULL, getDate(), 'SYSSUPERUSER', NULL);
INSERT INTO spac_ct_tramites (id, nombre, cod_tram, descripcion, tipo, ordenacion, observaciones, fcreacion, autor, id_subproceso) VALUES (40, 'Petición de ofertas', NULL, NULL, NULL, NULL, NULL, getDate(), 'SYSSUPERUSER', NULL);
INSERT INTO spac_ct_tramites (id, nombre, cod_tram, descripcion, tipo, ordenacion, observaciones, fcreacion, autor, id_subproceso) VALUES (41, 'Convocatoria mesa contratación', NULL, NULL, NULL, NULL, NULL, getDate(), 'SYSSUPERUSER', NULL);
INSERT INTO spac_ct_tramites (id, nombre, cod_tram, descripcion, tipo, ordenacion, observaciones, fcreacion, autor, id_subproceso) VALUES (42, 'Certificado presentación de ofertas', NULL, NULL, NULL, NULL, NULL, getDate(), 'SYSSUPERUSER', NULL);
INSERT INTO spac_ct_tramites (id, nombre, cod_tram, descripcion, tipo, ordenacion, observaciones, fcreacion, autor, id_subproceso) VALUES (43, 'Acta mesa', NULL, NULL, NULL, NULL, NULL, getDate(), 'SYSSUPERUSER', NULL);
INSERT INTO spac_ct_tramites (id, nombre, cod_tram, descripcion, tipo, ordenacion, observaciones, fcreacion, autor, id_subproceso) VALUES (44, 'Resolución dirección de obra', NULL, NULL, NULL, NULL, NULL, getDate(), 'SYSSUPERUSER', NULL);
INSERT INTO spac_ct_tramites (id, nombre, cod_tram, descripcion, tipo, ordenacion, observaciones, fcreacion, autor, id_subproceso) VALUES (45, 'Adjudicación provisional', NULL, NULL, NULL, NULL, NULL, getDate(), 'SYSSUPERUSER', NULL);
INSERT INTO spac_ct_tramites (id, nombre, cod_tram, descripcion, tipo, ordenacion, observaciones, fcreacion, autor, id_subproceso) VALUES (46, 'Publicación', NULL, NULL, NULL, NULL, NULL, getDate(), 'SYSSUPERUSER', NULL);
INSERT INTO spac_ct_tramites (id, nombre, cod_tram, descripcion, tipo, ordenacion, observaciones, fcreacion, autor, id_subproceso) VALUES (47, 'Adjudicación definitiva', NULL, NULL, NULL, NULL, NULL, getDate(), 'SYSSUPERUSER', NULL);
INSERT INTO spac_ct_tramites (id, nombre, cod_tram, descripcion, tipo, ordenacion, observaciones, fcreacion, autor, id_subproceso) VALUES (48, 'Devolución de garantías', NULL, NULL, NULL, NULL, NULL, getDate(), 'SYSSUPERUSER', NULL);
INSERT INTO spac_ct_tramites (id, nombre, cod_tram, descripcion, tipo, ordenacion, observaciones, fcreacion, autor, id_subproceso) VALUES (49, 'Contrato', NULL, NULL, NULL, NULL, NULL, getDate(), 'SYSSUPERUSER', NULL);
INSERT INTO spac_ct_tramites (id, nombre, cod_tram, descripcion, tipo, ordenacion, observaciones, fcreacion, autor, id_subproceso) VALUES (50, 'Plan de seguridad', NULL, NULL, NULL, NULL, NULL, getDate(), 'SYSSUPERUSER', NULL);
INSERT INTO spac_ct_tramites (id, nombre, cod_tram, descripcion, tipo, ordenacion, observaciones, fcreacion, autor, id_subproceso) VALUES (51, 'Acta de comprobación de replanteo', NULL, NULL, NULL, NULL, NULL, getDate(), 'SYSSUPERUSER', NULL);
INSERT INTO spac_ct_tramites (id, nombre, cod_tram, descripcion, tipo, ordenacion, observaciones, fcreacion, autor, id_subproceso) VALUES (52, 'Certificación', NULL, NULL, NULL, NULL, NULL, getDate(), 'SYSSUPERUSER', NULL);
INSERT INTO spac_ct_tramites (id, nombre, cod_tram, descripcion, tipo, ordenacion, observaciones, fcreacion, autor, id_subproceso) VALUES (53, 'Prórroga', NULL, NULL, NULL, NULL, NULL, getDate(), 'SYSSUPERUSER', NULL);
INSERT INTO spac_ct_tramites (id, nombre, cod_tram, descripcion, tipo, ordenacion, observaciones, fcreacion, autor, id_subproceso) VALUES (54, 'Paralización', NULL, NULL, NULL, NULL, NULL, getDate(), 'SYSSUPERUSER', NULL);
INSERT INTO spac_ct_tramites (id, nombre, cod_tram, descripcion, tipo, ordenacion, observaciones, fcreacion, autor, id_subproceso) VALUES (55, 'Ampliación de plazos', NULL, NULL, NULL, NULL, NULL, getDate(), 'SYSSUPERUSER', NULL);
INSERT INTO spac_ct_tramites (id, nombre, cod_tram, descripcion, tipo, ordenacion, observaciones, fcreacion, autor, id_subproceso) VALUES (56, 'Acta de recepción', NULL, NULL, NULL, NULL, NULL, getDate(), 'SYSSUPERUSER', NULL);
INSERT INTO spac_ct_tramites (id, nombre, cod_tram, descripcion, tipo, ordenacion, observaciones, fcreacion, autor, id_subproceso) VALUES (57, 'Certificación final', NULL, NULL, NULL, NULL, NULL, getDate(), 'SYSSUPERUSER', NULL);
INSERT INTO spac_ct_tramites (id, nombre, cod_tram, descripcion, tipo, ordenacion, observaciones, fcreacion, autor, id_subproceso) VALUES (58, 'Envío de información', NULL, NULL, NULL, NULL, NULL, getDate(), 'SYSSUPERUSER', NULL);
INSERT INTO spac_ct_tramites (id, nombre, cod_tram, descripcion, tipo, ordenacion, observaciones, fcreacion, autor, id_subproceso) VALUES (59, 'Petición de informes', NULL, NULL, NULL, NULL, NULL, getDate(), 'SYSSUPERUSER', NULL);
INSERT INTO spac_ct_tramites (id, nombre, cod_tram, descripcion, tipo, ordenacion, observaciones, fcreacion, autor, id_subproceso) VALUES (60, 'Acuerdo incoación', NULL, 'Formalización de la incoación del procedimiento sancionador con el contenido correspondiente.', NULL, NULL, NULL, getDate(), 'SYSSUPERUSER', NULL);
INSERT INTO spac_ct_tramites (id, nombre, cod_tram, descripcion, tipo, ordenacion, observaciones, fcreacion, autor, id_subproceso) VALUES (61, 'Acuerdo no incoación', NULL, 'Formalización de la no incoación del procedimiento sancionador.', NULL, NULL, NULL, getDate(), 'SYSSUPERUSER', NULL);
INSERT INTO spac_ct_tramites (id, nombre, cod_tram, descripcion, tipo, ordenacion, observaciones, fcreacion, autor, id_subproceso) VALUES (62, 'Medidas provisionales', NULL, 'El órgano competente podrá adoptar mediante acuerdo motivado, las medidas de carácter provisional que resulten necesarias para asegurar la eficacia de la resolución que pudiera recaer.', NULL, NULL, NULL, getDate(), 'SYSSUPERUSER', NULL);
INSERT INTO spac_ct_tramites (id, nombre, cod_tram, descripcion, tipo, ordenacion, observaciones, fcreacion, autor, id_subproceso) VALUES (63, 'Actuaciones realizadas', NULL, 'Incorporar la documentación presentada por el denunciado mediante la cual se demuestra que ha realizado las actuaciones que se le solicitaban para legalizar la situación por la que se le abrió el expediente sancionador.', NULL, NULL, NULL, getDate(), 'SYSSUPERUSER', NULL);
INSERT INTO spac_ct_tramites (id, nombre, cod_tram, descripcion, tipo, ordenacion, observaciones, fcreacion, autor, id_subproceso) VALUES (64, 'Propuesta liquidación', NULL, NULL, NULL, NULL, NULL, getDate(), 'SYSSUPERUSER', NULL);
INSERT INTO spac_ct_tramites (id, nombre, cod_tram, descripcion, tipo, ordenacion, observaciones, fcreacion, autor, id_subproceso) VALUES (65, 'Información pública', NULL, NULL, NULL, NULL, NULL, getDate(), 'SYSSUPERUSER', NULL);
INSERT INTO spac_ct_tramites (id, nombre, cod_tram, descripcion, tipo, ordenacion, observaciones, fcreacion, autor, id_subproceso) VALUES (66, 'Petición de autorizaciones sectoriales', NULL, NULL, NULL, NULL, NULL, getDate(), 'SYSSUPERUSER', NULL);
INSERT INTO spac_ct_tramites (id, nombre, cod_tram, descripcion, tipo, ordenacion, observaciones, fcreacion, autor, id_subproceso) VALUES (67, 'Concesión Licencia de Instalación', NULL, 'Concesión de la Licencia de Instalación para actividades', NULL, NULL, NULL, getDate(), 'SYSSUPERUSER', NULL);
INSERT INTO spac_ct_tramites (id, nombre, cod_tram, descripcion, tipo, ordenacion, observaciones, fcreacion, autor, id_subproceso) VALUES (68, 'Licencia de vado', NULL, NULL, NULL, NULL, NULL, getDate(), 'SYSSUPERUSER', NULL);
INSERT INTO spac_ct_tramites (id, nombre, cod_tram, descripcion, tipo, ordenacion, observaciones, fcreacion, autor, id_subproceso) VALUES (69, 'Remisión al órgano competente', NULL, NULL, NULL, NULL, NULL, getDate(), 'SYSSUPERUSER', NULL);
INSERT INTO spac_ct_tramites (id, nombre, cod_tram, descripcion, tipo, ordenacion, observaciones, fcreacion, autor, id_subproceso) VALUES (70, 'Emisión de informes', NULL, NULL, NULL, NULL, NULL, getDate(), 'SYSSUPERUSER', NULL);
INSERT INTO spac_ct_tramites (id, nombre, cod_tram, descripcion, tipo, ordenacion, observaciones, fcreacion, autor, id_subproceso) VALUES (71, 'Resolución reclamación', NULL, NULL, NULL, NULL, NULL, getDate(), 'SYSSUPERUSER', NULL);


-- Actualizar secuencia
UPDATE SPAC_SQ_SEQUENCES SET SEQUENCE_ID = 72 WHERE SEQUENCE_NAME = 'SPAC_SQ_ID_CTTRAMITES';



--
-- Fases/Trámites
--

INSERT INTO spac_ct_fstr (id, id_fase, id_tramite) VALUES (20, 1, 18);
INSERT INTO spac_ct_fstr (id, id_fase, id_tramite) VALUES (21, 1, 20);
INSERT INTO spac_ct_fstr (id, id_fase, id_tramite) VALUES (22, 2, 19);
INSERT INTO spac_ct_fstr (id, id_fase, id_tramite) VALUES (23, 2, 21);
INSERT INTO spac_ct_fstr (id, id_fase, id_tramite) VALUES (24, 3, 22);
INSERT INTO spac_ct_fstr (id, id_fase, id_tramite) VALUES (25, 3, 23);
INSERT INTO spac_ct_fstr (id, id_fase, id_tramite) VALUES (26, 3, 24);
INSERT INTO spac_ct_fstr (id, id_fase, id_tramite) VALUES (27, 4, 3);
INSERT INTO spac_ct_fstr (id, id_fase, id_tramite) VALUES (28, 1, 26);
INSERT INTO spac_ct_fstr (id, id_fase, id_tramite) VALUES (29, 3, 25);
INSERT INTO spac_ct_fstr (id, id_fase, id_tramite) VALUES (30, 3, 27);
INSERT INTO spac_ct_fstr (id, id_fase, id_tramite) VALUES (31, 2, 26);
INSERT INTO spac_ct_fstr (id, id_fase, id_tramite) VALUES (32, 3, 28);
INSERT INTO spac_ct_fstr (id, id_fase, id_tramite) VALUES (33, 6, 19);
INSERT INTO spac_ct_fstr (id, id_fase, id_tramite) VALUES (34, 6, 30);
INSERT INTO spac_ct_fstr (id, id_fase, id_tramite) VALUES (35, 6, 31);
INSERT INTO spac_ct_fstr (id, id_fase, id_tramite) VALUES (36, 6, 32);
INSERT INTO spac_ct_fstr (id, id_fase, id_tramite) VALUES (37, 6, 33);
INSERT INTO spac_ct_fstr (id, id_fase, id_tramite) VALUES (38, 6, 34);
INSERT INTO spac_ct_fstr (id, id_fase, id_tramite) VALUES (39, 6, 35);
INSERT INTO spac_ct_fstr (id, id_fase, id_tramite) VALUES (40, 6, 36);
INSERT INTO spac_ct_fstr (id, id_fase, id_tramite) VALUES (41, 6, 37);
INSERT INTO spac_ct_fstr (id, id_fase, id_tramite) VALUES (42, 6, 38);
INSERT INTO spac_ct_fstr (id, id_fase, id_tramite) VALUES (43, 6, 39);
INSERT INTO spac_ct_fstr (id, id_fase, id_tramite) VALUES (44, 7, 19);
INSERT INTO spac_ct_fstr (id, id_fase, id_tramite) VALUES (45, 7, 29);
INSERT INTO spac_ct_fstr (id, id_fase, id_tramite) VALUES (46, 7, 40);
INSERT INTO spac_ct_fstr (id, id_fase, id_tramite) VALUES (47, 7, 41);
INSERT INTO spac_ct_fstr (id, id_fase, id_tramite) VALUES (48, 7, 42);
INSERT INTO spac_ct_fstr (id, id_fase, id_tramite) VALUES (49, 7, 43);
INSERT INTO spac_ct_fstr (id, id_fase, id_tramite) VALUES (50, 8, 44);
INSERT INTO spac_ct_fstr (id, id_fase, id_tramite) VALUES (51, 8, 45);
INSERT INTO spac_ct_fstr (id, id_fase, id_tramite) VALUES (52, 8, 46);
INSERT INTO spac_ct_fstr (id, id_fase, id_tramite) VALUES (53, 8, 47);
INSERT INTO spac_ct_fstr (id, id_fase, id_tramite) VALUES (54, 8, 48);
INSERT INTO spac_ct_fstr (id, id_fase, id_tramite) VALUES (55, 8, 49);
INSERT INTO spac_ct_fstr (id, id_fase, id_tramite) VALUES (56, 8, 50);
INSERT INTO spac_ct_fstr (id, id_fase, id_tramite) VALUES (57, 9, 19);
INSERT INTO spac_ct_fstr (id, id_fase, id_tramite) VALUES (58, 9, 35);
INSERT INTO spac_ct_fstr (id, id_fase, id_tramite) VALUES (59, 9, 48);
INSERT INTO spac_ct_fstr (id, id_fase, id_tramite) VALUES (60, 9, 51);
INSERT INTO spac_ct_fstr (id, id_fase, id_tramite) VALUES (61, 9, 52);
INSERT INTO spac_ct_fstr (id, id_fase, id_tramite) VALUES (62, 9, 53);
INSERT INTO spac_ct_fstr (id, id_fase, id_tramite) VALUES (63, 9, 54);
INSERT INTO spac_ct_fstr (id, id_fase, id_tramite) VALUES (64, 9, 55);
INSERT INTO spac_ct_fstr (id, id_fase, id_tramite) VALUES (65, 9, 56);
INSERT INTO spac_ct_fstr (id, id_fase, id_tramite) VALUES (66, 9, 57);
INSERT INTO spac_ct_fstr (id, id_fase, id_tramite) VALUES (67, 9, 58);
INSERT INTO spac_ct_fstr (id, id_fase, id_tramite) VALUES (68, 1, 60);
INSERT INTO spac_ct_fstr (id, id_fase, id_tramite) VALUES (69, 1, 61);
INSERT INTO spac_ct_fstr (id, id_fase, id_tramite) VALUES (70, 2, 59);
INSERT INTO spac_ct_fstr (id, id_fase, id_tramite) VALUES (71, 2, 62);
INSERT INTO spac_ct_fstr (id, id_fase, id_tramite) VALUES (72, 2, 63);
INSERT INTO spac_ct_fstr (id, id_fase, id_tramite) VALUES (73, 2, 65);
INSERT INTO spac_ct_fstr (id, id_fase, id_tramite) VALUES (74, 2, 66);
INSERT INTO spac_ct_fstr (id, id_fase, id_tramite) VALUES (75, 2, 29);
INSERT INTO spac_ct_fstr (id, id_fase, id_tramite) VALUES (76, 2, 67);
INSERT INTO spac_ct_fstr (id, id_fase, id_tramite) VALUES (77, 2, 64);
INSERT INTO spac_ct_fstr (id, id_fase, id_tramite) VALUES (78, 3, 68);
INSERT INTO spac_ct_fstr (id, id_fase, id_tramite) VALUES (79, 2, 69);
INSERT INTO spac_ct_fstr (id, id_fase, id_tramite) VALUES (80, 2, 70);
INSERT INTO spac_ct_fstr (id, id_fase, id_tramite) VALUES (81, 3, 71);


-- Actualizar secuencia
UPDATE SPAC_SQ_SEQUENCES SET SEQUENCE_ID = 82 WHERE SEQUENCE_NAME = 'SPAC_SQ_ID_CTFSTR';



--
-- Tipos de documentos
--

INSERT INTO spac_ct_tpdoc (id, nombre, cod_tpdoc, tipo, autor, descripcion, fecha, tiporeg, observaciones) VALUES (22, 'Solicitud subsanación', NULL, NULL, 'SYSSUPERUSER', NULL, getDate(), 'SALIDA', NULL);
INSERT INTO spac_ct_tpdoc (id, nombre, cod_tpdoc, tipo, autor, descripcion, fecha, tiporeg, observaciones) VALUES (23, 'Informe técnico', NULL, NULL, 'SYSSUPERUSER', NULL, getDate(), 'NINGUNO', NULL);
INSERT INTO spac_ct_tpdoc (id, nombre, cod_tpdoc, tipo, autor, descripcion, fecha, tiporeg, observaciones) VALUES (24, 'Petición de informes', NULL, NULL, 'SYSSUPERUSER', NULL, getDate(), 'SALIDA', NULL);
INSERT INTO spac_ct_tpdoc (id, nombre, cod_tpdoc, tipo, autor, descripcion, fecha, tiporeg, observaciones) VALUES (25, 'Informe jurídico', NULL, NULL, 'SYSSUPERUSER', NULL, getDate(), 'NINGUNO', NULL);
INSERT INTO spac_ct_tpdoc (id, nombre, cod_tpdoc, tipo, autor, descripcion, fecha, tiporeg, observaciones) VALUES (26, 'Notificación solicitud subsanación', NULL, NULL, 'SYSSUPERUSER', NULL, getDate(), 'SALIDA', NULL);
INSERT INTO spac_ct_tpdoc (id, nombre, cod_tpdoc, tipo, autor, descripcion, fecha, tiporeg, observaciones) VALUES (27, 'Licencia', NULL, NULL, 'SYSSUPERUSER', NULL, getDate(), 'NINGUNO', NULL);
INSERT INTO spac_ct_tpdoc (id, nombre, cod_tpdoc, tipo, autor, descripcion, fecha, tiporeg, observaciones) VALUES (28, 'Propuesta liquidación', NULL, NULL, 'SYSSUPERUSER', NULL, getDate(), 'NINGUNO', NULL);
INSERT INTO spac_ct_tpdoc (id, nombre, cod_tpdoc, tipo, autor, descripcion, fecha, tiporeg, observaciones) VALUES (29, 'Resolución de la autorización', NULL, NULL, 'SYSSUPERUSER', NULL, getDate(), NULL, NULL);
INSERT INTO spac_ct_tpdoc (id, nombre, cod_tpdoc, tipo, autor, descripcion, fecha, tiporeg, observaciones) VALUES (30, 'Carátula', NULL, NULL, 'SYSSUPERUSER', NULL, getDate(), 'NINGUNO', NULL);
INSERT INTO spac_ct_tpdoc (id, nombre, cod_tpdoc, tipo, autor, descripcion, fecha, tiporeg, observaciones) VALUES (31, 'Notificación liquidación', NULL, NULL, 'SYSSUPERUSER', NULL, getDate(), 'SALIDA', NULL);
INSERT INTO spac_ct_tpdoc (id, nombre, cod_tpdoc, tipo, autor, descripcion, fecha, tiporeg, observaciones) VALUES (32, 'Aportación documentación', NULL, NULL, 'SYSSUPERUSER', NULL, getDate(), 'NINGUNO', NULL);
INSERT INTO spac_ct_tpdoc (id, nombre, cod_tpdoc, tipo, autor, descripcion, fecha, tiporeg, observaciones) VALUES (33, 'Justificación pago', NULL, NULL, 'SYSSUPERUSER', NULL, getDate(), 'NINGUNO', NULL);
INSERT INTO spac_ct_tpdoc (id, nombre, cod_tpdoc, tipo, autor, descripcion, fecha, tiporeg, observaciones) VALUES (34, 'Solicitud', NULL, NULL, 'SYSSUPERUSER', NULL, getDate(), 'NINGUNO', NULL);
INSERT INTO spac_ct_tpdoc (id, nombre, cod_tpdoc, tipo, autor, descripcion, fecha, tiporeg, observaciones) VALUES (35, 'Escrito interesado expediente', NULL, NULL, 'SYSSUPERUSER', NULL, getDate(), 'NINGUNO', NULL);
INSERT INTO spac_ct_tpdoc (id, nombre, cod_tpdoc, tipo, autor, descripcion, fecha, tiporeg, observaciones) VALUES (36, 'Certificación', NULL, NULL, 'SYSSUPERUSER', NULL, getDate(), 'SALIDA', NULL);
INSERT INTO spac_ct_tpdoc (id, nombre, cod_tpdoc, tipo, autor, descripcion, fecha, tiporeg, observaciones) VALUES (37, 'Requerimiento subsanación', NULL, NULL, 'SYSSUPERUSER', NULL, getDate(), 'NINGUNO', NULL);
INSERT INTO spac_ct_tpdoc (id, nombre, cod_tpdoc, tipo, autor, descripcion, fecha, tiporeg, observaciones) VALUES (38, 'Notificación requerimiento subsanación', NULL, NULL, 'SYSSUPERUSER', NULL, getDate(), 'SALIDA', NULL);
INSERT INTO spac_ct_tpdoc (id, nombre, cod_tpdoc, tipo, autor, descripcion, fecha, tiporeg, observaciones) VALUES (39, 'Propuesta de inicio', NULL, NULL, 'SYSSUPERUSER', NULL, getDate(), NULL, NULL);
INSERT INTO spac_ct_tpdoc (id, nombre, cod_tpdoc, tipo, autor, descripcion, fecha, tiporeg, observaciones) VALUES (40, 'Cláusulas administrativas', NULL, NULL, 'SYSSUPERUSER', NULL, getDate(), NULL, NULL);
INSERT INTO spac_ct_tpdoc (id, nombre, cod_tpdoc, tipo, autor, descripcion, fecha, tiporeg, observaciones) VALUES (41, 'Prescripciones técnicas', NULL, NULL, 'SYSSUPERUSER', NULL, getDate(), NULL, NULL);
INSERT INTO spac_ct_tpdoc (id, nombre, cod_tpdoc, tipo, autor, descripcion, fecha, tiporeg, observaciones) VALUES (42, 'Acta de replanteo previa', NULL, NULL, 'SYSSUPERUSER', NULL, getDate(), NULL, NULL);
INSERT INTO spac_ct_tpdoc (id, nombre, cod_tpdoc, tipo, autor, descripcion, fecha, tiporeg, observaciones) VALUES (43, 'Informe de intervención', NULL, NULL, 'SYSSUPERUSER', NULL, getDate(), NULL, NULL);
INSERT INTO spac_ct_tpdoc (id, nombre, cod_tpdoc, tipo, autor, descripcion, fecha, tiporeg, observaciones) VALUES (44, 'Informe de secretaría', NULL, NULL, 'SYSSUPERUSER', NULL, getDate(), NULL, NULL);
INSERT INTO spac_ct_tpdoc (id, nombre, cod_tpdoc, tipo, autor, descripcion, fecha, tiporeg, observaciones) VALUES (45, 'Proyecto', NULL, NULL, 'SYSSUPERUSER', NULL, getDate(), NULL, NULL);
INSERT INTO spac_ct_tpdoc (id, nombre, cod_tpdoc, tipo, autor, descripcion, fecha, tiporeg, observaciones) VALUES (46, 'Resolución aprobación expediente', NULL, NULL, 'SYSSUPERUSER', NULL, getDate(), 'SALIDA', NULL);
INSERT INTO spac_ct_tpdoc (id, nombre, cod_tpdoc, tipo, autor, descripcion, fecha, tiporeg, observaciones) VALUES (47, 'Resolución nombramiento mesa', NULL, NULL, 'SYSSUPERUSER', NULL, getDate(), 'SALIDA', NULL);
INSERT INTO spac_ct_tpdoc (id, nombre, cod_tpdoc, tipo, autor, descripcion, fecha, tiporeg, observaciones) VALUES (48, 'Petición de ofertas', NULL, NULL, 'SYSSUPERUSER', NULL, getDate(), 'SALIDA', NULL);
INSERT INTO spac_ct_tpdoc (id, nombre, cod_tpdoc, tipo, autor, descripcion, fecha, tiporeg, observaciones) VALUES (49, 'Convocatoria mesa de contratación', NULL, NULL, 'SYSSUPERUSER', NULL, getDate(), 'SALIDA', NULL);
INSERT INTO spac_ct_tpdoc (id, nombre, cod_tpdoc, tipo, autor, descripcion, fecha, tiporeg, observaciones) VALUES (50, 'Certificado presentación de ofertas', NULL, NULL, 'SYSSUPERUSER', NULL, getDate(), NULL, NULL);
INSERT INTO spac_ct_tpdoc (id, nombre, cod_tpdoc, tipo, autor, descripcion, fecha, tiporeg, observaciones) VALUES (51, 'Acta mesa', NULL, NULL, 'SYSSUPERUSER', NULL, getDate(), NULL, NULL);
INSERT INTO spac_ct_tpdoc (id, nombre, cod_tpdoc, tipo, autor, descripcion, fecha, tiporeg, observaciones) VALUES (52, 'Resolución dirección de obra', NULL, NULL, 'SYSSUPERUSER', NULL, getDate(), 'SALIDA', NULL);
INSERT INTO spac_ct_tpdoc (id, nombre, cod_tpdoc, tipo, autor, descripcion, fecha, tiporeg, observaciones) VALUES (53, 'Adjudicación provisional', NULL, NULL, 'SYSSUPERUSER', NULL, getDate(), 'SALIDA', NULL);
INSERT INTO spac_ct_tpdoc (id, nombre, cod_tpdoc, tipo, autor, descripcion, fecha, tiporeg, observaciones) VALUES (54, 'Publicación', NULL, NULL, 'SYSSUPERUSER', NULL, getDate(), 'SALIDA', NULL);
INSERT INTO spac_ct_tpdoc (id, nombre, cod_tpdoc, tipo, autor, descripcion, fecha, tiporeg, observaciones) VALUES (55, 'Adjudicación definitiva', NULL, NULL, 'SYSSUPERUSER', NULL, getDate(), 'SALIDA', NULL);
INSERT INTO spac_ct_tpdoc (id, nombre, cod_tpdoc, tipo, autor, descripcion, fecha, tiporeg, observaciones) VALUES (56, 'Devolución de garantías', NULL, NULL, 'SYSSUPERUSER', NULL, getDate(), 'SALIDA', NULL);
INSERT INTO spac_ct_tpdoc (id, nombre, cod_tpdoc, tipo, autor, descripcion, fecha, tiporeg, observaciones) VALUES (57, 'Plan de seguridad', NULL, NULL, 'SYSSUPERUSER', NULL, getDate(), NULL, NULL);
INSERT INTO spac_ct_tpdoc (id, nombre, cod_tpdoc, tipo, autor, descripcion, fecha, tiporeg, observaciones) VALUES (58, 'Acta de comprobación de replanteo', NULL, NULL, 'SYSSUPERUSER', NULL, getDate(), 'ENTRADA', NULL);
INSERT INTO spac_ct_tpdoc (id, nombre, cod_tpdoc, tipo, autor, descripcion, fecha, tiporeg, observaciones) VALUES (59, 'Prórroga', NULL, NULL, 'SYSSUPERUSER', NULL, getDate(), 'SALIDA', NULL);
INSERT INTO spac_ct_tpdoc (id, nombre, cod_tpdoc, tipo, autor, descripcion, fecha, tiporeg, observaciones) VALUES (60, 'Paralización', NULL, NULL, 'SYSSUPERUSER', NULL, getDate(), 'SALIDA', NULL);
INSERT INTO spac_ct_tpdoc (id, nombre, cod_tpdoc, tipo, autor, descripcion, fecha, tiporeg, observaciones) VALUES (61, 'Amplicación de plazos', NULL, NULL, 'SYSSUPERUSER', NULL, getDate(), 'SALIDA', NULL);
INSERT INTO spac_ct_tpdoc (id, nombre, cod_tpdoc, tipo, autor, descripcion, fecha, tiporeg, observaciones) VALUES (62, 'Certificación final', NULL, NULL, 'SYSSUPERUSER', NULL, getDate(), 'SALIDA', NULL);
INSERT INTO spac_ct_tpdoc (id, nombre, cod_tpdoc, tipo, autor, descripcion, fecha, tiporeg, observaciones) VALUES (63, 'Envío de información', NULL, NULL, 'SYSSUPERUSER', NULL, getDate(), 'SALIDA', NULL);
INSERT INTO spac_ct_tpdoc (id, nombre, cod_tpdoc, tipo, autor, descripcion, fecha, tiporeg, observaciones) VALUES (64, 'Licencia de obra', NULL, NULL, 'SYSSUPERUSER', NULL, getDate(), 'NINGUNO', NULL);
INSERT INTO spac_ct_tpdoc (id, nombre, cod_tpdoc, tipo, autor, descripcion, fecha, tiporeg, observaciones) VALUES (65, 'Contrato', NULL, NULL, 'SYSSUPERUSER', NULL, getDate(), NULL, NULL);
INSERT INTO spac_ct_tpdoc (id, nombre, cod_tpdoc, tipo, autor, descripcion, fecha, tiporeg, observaciones) VALUES (66, 'Informe externo', NULL, NULL, 'SYSSUPERUSER', NULL, getDate(), 'NINGUNO', NULL);
INSERT INTO spac_ct_tpdoc (id, nombre, cod_tpdoc, tipo, autor, descripcion, fecha, tiporeg, observaciones) VALUES (67, 'Acuerdo incoación', NULL, NULL, 'SYSSUPERUSER', 'Formalización de la incoación del procedimiento sancionador con el contenido correspondiente.', getDate(), 'NINGUNO', NULL);
INSERT INTO spac_ct_tpdoc (id, nombre, cod_tpdoc, tipo, autor, descripcion, fecha, tiporeg, observaciones) VALUES (68, 'Acuerdo no incoación', NULL, NULL, 'SYSSUPERUSER', 'Formalización de la no incoación del procedimiento sancionador.', getDate(), 'NINGUNO', NULL);
INSERT INTO spac_ct_tpdoc (id, nombre, cod_tpdoc, tipo, autor, descripcion, fecha, tiporeg, observaciones) VALUES (69, 'Medidas provisionales', NULL, NULL, 'SYSSUPERUSER', 'El órgano competente podrá adoptar mediante acuerdo motivado, las medidas de carácter provisional que resulten necesarias para asegurar la eficacia de la resolución que pudiera recaer.', getDate(), 'NINGUNO', NULL);
INSERT INTO spac_ct_tpdoc (id, nombre, cod_tpdoc, tipo, autor, descripcion, fecha, tiporeg, observaciones) VALUES (70, 'Actuaciones realizadas', NULL, NULL, 'SYSSUPERUSER', 'Incorporar la documentación presentada por el denunciado mediante la cual se demuestra que ha realizado las actuaciones que se le solicitaban para legalizar la situación por la que se le abrió el expediente sancionador.', getDate(), 'ENTRADA', NULL);
INSERT INTO spac_ct_tpdoc (id, nombre, cod_tpdoc, tipo, autor, descripcion, fecha, tiporeg, observaciones) VALUES (71, 'Propuesta resolución', NULL, NULL, 'SYSSUPERUSER', NULL, getDate(), NULL, NULL);
INSERT INTO spac_ct_tpdoc (id, nombre, cod_tpdoc, tipo, autor, descripcion, fecha, tiporeg, observaciones) VALUES (72, 'Petición de autorizaciones sectoriales', NULL, NULL, 'SYSSUPERUSER', NULL, getDate(), 'SALIDA', NULL);
INSERT INTO spac_ct_tpdoc (id, nombre, cod_tpdoc, tipo, autor, descripcion, fecha, tiporeg, observaciones) VALUES (73, 'Autorización sectorial', NULL, NULL, 'SYSSUPERUSER', NULL, getDate(), 'NINGUNO', NULL);
INSERT INTO spac_ct_tpdoc (id, nombre, cod_tpdoc, tipo, autor, descripcion, fecha, tiporeg, observaciones) VALUES (74, 'Decreto/Acuerdo de Concesión', NULL, NULL, 'SYSSUPERUSER', NULL, getDate(), NULL, NULL);
INSERT INTO spac_ct_tpdoc (id, nombre, cod_tpdoc, tipo, autor, descripcion, fecha, tiporeg, observaciones) VALUES (75, 'Notificación de Concesión', NULL, NULL, 'SYSSUPERUSER', NULL, getDate(), 'SALIDA', NULL);
INSERT INTO spac_ct_tpdoc (id, nombre, cod_tpdoc, tipo, autor, descripcion, fecha, tiporeg, observaciones) VALUES (76, 'Comunicación a interesado(s)', NULL, NULL, 'SYSSUPERUSER', NULL, getDate(), 'SALIDA', NULL);
INSERT INTO spac_ct_tpdoc (id, nombre, cod_tpdoc, tipo, autor, descripcion, fecha, tiporeg, observaciones) VALUES (77, 'Notificación resolución responsabilidad patrimonial', NULL, NULL, 'SYSSUPERUSER', NULL, getDate(), NULL, NULL);


-- Actualizar secuencia
UPDATE SPAC_SQ_SEQUENCES SET SEQUENCE_ID = 78 WHERE SEQUENCE_NAME = 'SPAC_SQ_ID_CTTPDOC';



--
-- Trámites/Tipos de documentos
--

INSERT INTO spac_ct_trtd (id, id_tramite, id_tpdoc) VALUES (19, 18, 22);
INSERT INTO spac_ct_trtd (id, id_tramite, id_tpdoc) VALUES (20, 18, 26);
INSERT INTO spac_ct_trtd (id, id_tramite, id_tpdoc) VALUES (21, 19, 23);
INSERT INTO spac_ct_trtd (id, id_tramite, id_tpdoc) VALUES (22, 20, 23);
INSERT INTO spac_ct_trtd (id, id_tramite, id_tpdoc) VALUES (23, 20, 24);
INSERT INTO spac_ct_trtd (id, id_tramite, id_tpdoc) VALUES (24, 21, 25);
INSERT INTO spac_ct_trtd (id, id_tramite, id_tpdoc) VALUES (25, 22, 29);
INSERT INTO spac_ct_trtd (id, id_tramite, id_tpdoc) VALUES (26, 23, 28);
INSERT INTO spac_ct_trtd (id, id_tramite, id_tpdoc) VALUES (27, 24, 27);
INSERT INTO spac_ct_trtd (id, id_tramite, id_tpdoc) VALUES (28, 3, 4);
INSERT INTO spac_ct_trtd (id, id_tramite, id_tpdoc) VALUES (29, 25, 17);
INSERT INTO spac_ct_trtd (id, id_tramite, id_tpdoc) VALUES (30, 25, 16);
INSERT INTO spac_ct_trtd (id, id_tramite, id_tpdoc) VALUES (31, 25, 31);
INSERT INTO spac_ct_trtd (id, id_tramite, id_tpdoc) VALUES (32, 26, 35);
INSERT INTO spac_ct_trtd (id, id_tramite, id_tpdoc) VALUES (33, 26, 32);
INSERT INTO spac_ct_trtd (id, id_tramite, id_tpdoc) VALUES (34, 27, 33);
INSERT INTO spac_ct_trtd (id, id_tramite, id_tpdoc) VALUES (35, 27, 27);
INSERT INTO spac_ct_trtd (id, id_tramite, id_tpdoc) VALUES (36, 28, 36);
INSERT INTO spac_ct_trtd (id, id_tramite, id_tpdoc) VALUES (37, 29, 37);
INSERT INTO spac_ct_trtd (id, id_tramite, id_tpdoc) VALUES (38, 29, 38);
INSERT INTO spac_ct_trtd (id, id_tramite, id_tpdoc) VALUES (39, 30, 39);
INSERT INTO spac_ct_trtd (id, id_tramite, id_tpdoc) VALUES (40, 31, 40);
INSERT INTO spac_ct_trtd (id, id_tramite, id_tpdoc) VALUES (41, 32, 41);
INSERT INTO spac_ct_trtd (id, id_tramite, id_tpdoc) VALUES (42, 33, 42);
INSERT INTO spac_ct_trtd (id, id_tramite, id_tpdoc) VALUES (43, 34, 43);
INSERT INTO spac_ct_trtd (id, id_tramite, id_tpdoc) VALUES (44, 35, 44);
INSERT INTO spac_ct_trtd (id, id_tramite, id_tpdoc) VALUES (45, 36, 64);
INSERT INTO spac_ct_trtd (id, id_tramite, id_tpdoc) VALUES (46, 37, 45);
INSERT INTO spac_ct_trtd (id, id_tramite, id_tpdoc) VALUES (47, 38, 46);
INSERT INTO spac_ct_trtd (id, id_tramite, id_tpdoc) VALUES (48, 38, 16);
INSERT INTO spac_ct_trtd (id, id_tramite, id_tpdoc) VALUES (49, 39, 47);
INSERT INTO spac_ct_trtd (id, id_tramite, id_tpdoc) VALUES (50, 39, 16);
INSERT INTO spac_ct_trtd (id, id_tramite, id_tpdoc) VALUES (51, 40, 48);
INSERT INTO spac_ct_trtd (id, id_tramite, id_tpdoc) VALUES (52, 41, 49);
INSERT INTO spac_ct_trtd (id, id_tramite, id_tpdoc) VALUES (53, 42, 50);
INSERT INTO spac_ct_trtd (id, id_tramite, id_tpdoc) VALUES (54, 43, 51);
INSERT INTO spac_ct_trtd (id, id_tramite, id_tpdoc) VALUES (55, 44, 52);
INSERT INTO spac_ct_trtd (id, id_tramite, id_tpdoc) VALUES (56, 44, 16);
INSERT INTO spac_ct_trtd (id, id_tramite, id_tpdoc) VALUES (57, 45, 53);
INSERT INTO spac_ct_trtd (id, id_tramite, id_tpdoc) VALUES (58, 45, 16);
INSERT INTO spac_ct_trtd (id, id_tramite, id_tpdoc) VALUES (59, 46, 54);
INSERT INTO spac_ct_trtd (id, id_tramite, id_tpdoc) VALUES (60, 47, 55);
INSERT INTO spac_ct_trtd (id, id_tramite, id_tpdoc) VALUES (61, 47, 16);
INSERT INTO spac_ct_trtd (id, id_tramite, id_tpdoc) VALUES (62, 48, 56);
INSERT INTO spac_ct_trtd (id, id_tramite, id_tpdoc) VALUES (63, 49, 65);
INSERT INTO spac_ct_trtd (id, id_tramite, id_tpdoc) VALUES (64, 50, 57);
INSERT INTO spac_ct_trtd (id, id_tramite, id_tpdoc) VALUES (65, 51, 58);
INSERT INTO spac_ct_trtd (id, id_tramite, id_tpdoc) VALUES (66, 52, 36);
INSERT INTO spac_ct_trtd (id, id_tramite, id_tpdoc) VALUES (67, 53, 59);
INSERT INTO spac_ct_trtd (id, id_tramite, id_tpdoc) VALUES (68, 53, 16);
INSERT INTO spac_ct_trtd (id, id_tramite, id_tpdoc) VALUES (69, 54, 60);
INSERT INTO spac_ct_trtd (id, id_tramite, id_tpdoc) VALUES (70, 54, 16);
INSERT INTO spac_ct_trtd (id, id_tramite, id_tpdoc) VALUES (71, 55, 61);
INSERT INTO spac_ct_trtd (id, id_tramite, id_tpdoc) VALUES (72, 55, 16);
INSERT INTO spac_ct_trtd (id, id_tramite, id_tpdoc) VALUES (73, 56, 2);
INSERT INTO spac_ct_trtd (id, id_tramite, id_tpdoc) VALUES (74, 57, 62);
INSERT INTO spac_ct_trtd (id, id_tramite, id_tpdoc) VALUES (75, 58, 63);
INSERT INTO spac_ct_trtd (id, id_tramite, id_tpdoc) VALUES (76, 12, 71);
INSERT INTO spac_ct_trtd (id, id_tramite, id_tpdoc) VALUES (77, 59, 24);
INSERT INTO spac_ct_trtd (id, id_tramite, id_tpdoc) VALUES (78, 59, 66);
INSERT INTO spac_ct_trtd (id, id_tramite, id_tpdoc) VALUES (79, 60, 67);
INSERT INTO spac_ct_trtd (id, id_tramite, id_tpdoc) VALUES (80, 60, 11);
INSERT INTO spac_ct_trtd (id, id_tramite, id_tpdoc) VALUES (81, 61, 11);
INSERT INTO spac_ct_trtd (id, id_tramite, id_tpdoc) VALUES (82, 61, 68);
INSERT INTO spac_ct_trtd (id, id_tramite, id_tpdoc) VALUES (83, 62, 69);
INSERT INTO spac_ct_trtd (id, id_tramite, id_tpdoc) VALUES (84, 62, 11);
INSERT INTO spac_ct_trtd (id, id_tramite, id_tpdoc) VALUES (85, 63, 70);
INSERT INTO spac_ct_trtd (id, id_tramite, id_tpdoc) VALUES (86, 64, 28);
INSERT INTO spac_ct_trtd (id, id_tramite, id_tpdoc) VALUES (87, 65, 54);
INSERT INTO spac_ct_trtd (id, id_tramite, id_tpdoc) VALUES (88, 65, 5);
INSERT INTO spac_ct_trtd (id, id_tramite, id_tpdoc) VALUES (89, 65, 76);
INSERT INTO spac_ct_trtd (id, id_tramite, id_tpdoc) VALUES (90, 66, 73);
INSERT INTO spac_ct_trtd (id, id_tramite, id_tpdoc) VALUES (91, 66, 72);
INSERT INTO spac_ct_trtd (id, id_tramite, id_tpdoc) VALUES (92, 67, 74);
INSERT INTO spac_ct_trtd (id, id_tramite, id_tpdoc) VALUES (93, 67, 75);
INSERT INTO spac_ct_trtd (id, id_tramite, id_tpdoc) VALUES (94, 68, 33);
INSERT INTO spac_ct_trtd (id, id_tramite, id_tpdoc) VALUES (95, 17, 77);
INSERT INTO spac_ct_trtd (id, id_tramite, id_tpdoc) VALUES (96, 70, 23);
INSERT INTO spac_ct_trtd (id, id_tramite, id_tpdoc) VALUES (97, 71, 17);


-- Actualizar secuencia
UPDATE SPAC_SQ_SEQUENCES SET SEQUENCE_ID = 98 WHERE SEQUENCE_NAME = 'SPAC_SQ_ID_CTTRTD';



--
-- Entidades asociadas a procedimientos
--


UPDATE SPAC_SQ_SEQUENCES	SET @sequence_pentidades_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_PENTIDADES';
INSERT INTO spac_p_entidades (id, id_ent, id_pcd, tp_rel, orden, frm_edit, frm_readonly) VALUES (@sequence_pentidades_id, 1, 7, NULL, 26, 1, NULL);
UPDATE SPAC_SQ_SEQUENCES	SET @sequence_pentidades_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_PENTIDADES';
INSERT INTO spac_p_entidades (id, id_ent, id_pcd, tp_rel, orden, frm_edit, frm_readonly) VALUES (@sequence_pentidades_id, 7, 7, NULL, 27, NULL, NULL);
UPDATE SPAC_SQ_SEQUENCES	SET @sequence_pentidades_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_PENTIDADES';
INSERT INTO spac_p_entidades (id, id_ent, id_pcd, tp_rel, orden, frm_edit, frm_readonly) VALUES (@sequence_pentidades_id, 2, 7, NULL, 28, NULL, NULL);
UPDATE SPAC_SQ_SEQUENCES	SET @sequence_pentidades_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_PENTIDADES';
INSERT INTO spac_p_entidades (id, id_ent, id_pcd, tp_rel, orden, frm_edit, frm_readonly) VALUES (@sequence_pentidades_id, 3, 7, NULL, 29, NULL, NULL);
UPDATE SPAC_SQ_SEQUENCES	SET @sequence_pentidades_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_PENTIDADES';
INSERT INTO spac_p_entidades (id, id_ent, id_pcd, tp_rel, orden, frm_edit, frm_readonly) VALUES (@sequence_pentidades_id, 1, 8, NULL, 30, NULL, NULL);
UPDATE SPAC_SQ_SEQUENCES	SET @sequence_pentidades_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_PENTIDADES';
INSERT INTO spac_p_entidades (id, id_ent, id_pcd, tp_rel, orden, frm_edit, frm_readonly) VALUES (@sequence_pentidades_id, 206, 8, 0, 31, NULL, NULL);
UPDATE SPAC_SQ_SEQUENCES	SET @sequence_pentidades_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_PENTIDADES';
INSERT INTO spac_p_entidades (id, id_ent, id_pcd, tp_rel, orden, frm_edit, frm_readonly) VALUES (@sequence_pentidades_id, 7, 8, NULL, 32, NULL, NULL);
UPDATE SPAC_SQ_SEQUENCES	SET @sequence_pentidades_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_PENTIDADES';
INSERT INTO spac_p_entidades (id, id_ent, id_pcd, tp_rel, orden, frm_edit, frm_readonly) VALUES (@sequence_pentidades_id, 2, 8, NULL, 33, NULL, NULL);
UPDATE SPAC_SQ_SEQUENCES	SET @sequence_pentidades_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_PENTIDADES';
INSERT INTO spac_p_entidades (id, id_ent, id_pcd, tp_rel, orden, frm_edit, frm_readonly) VALUES (@sequence_pentidades_id, 3, 8, NULL, 34, NULL, NULL);
UPDATE SPAC_SQ_SEQUENCES	SET @sequence_pentidades_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_PENTIDADES';
INSERT INTO spac_p_entidades (id, id_ent, id_pcd, tp_rel, orden, frm_edit, frm_readonly) VALUES (@sequence_pentidades_id, 1, 9, NULL, 35, NULL, NULL);
UPDATE SPAC_SQ_SEQUENCES	SET @sequence_pentidades_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_PENTIDADES';
INSERT INTO spac_p_entidades (id, id_ent, id_pcd, tp_rel, orden, frm_edit, frm_readonly) VALUES (@sequence_pentidades_id, 211, 9, 0, 36, NULL, NULL);
UPDATE SPAC_SQ_SEQUENCES	SET @sequence_pentidades_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_PENTIDADES';
INSERT INTO spac_p_entidades (id, id_ent, id_pcd, tp_rel, orden, frm_edit, frm_readonly) VALUES (@sequence_pentidades_id, 7, 9, NULL, 37, NULL, NULL);
UPDATE SPAC_SQ_SEQUENCES	SET @sequence_pentidades_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_PENTIDADES';
INSERT INTO spac_p_entidades (id, id_ent, id_pcd, tp_rel, orden, frm_edit, frm_readonly) VALUES (@sequence_pentidades_id, 2, 9, NULL, 38, NULL, NULL);
UPDATE SPAC_SQ_SEQUENCES	SET @sequence_pentidades_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_PENTIDADES';
INSERT INTO spac_p_entidades (id, id_ent, id_pcd, tp_rel, orden, frm_edit, frm_readonly) VALUES (@sequence_pentidades_id, 3, 9, NULL, 39, NULL, NULL);
UPDATE SPAC_SQ_SEQUENCES	SET @sequence_pentidades_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_PENTIDADES';
INSERT INTO spac_p_entidades (id, id_ent, id_pcd, tp_rel, orden, frm_edit, frm_readonly) VALUES (@sequence_pentidades_id, 1, 10, NULL, 40, NULL, NULL);
UPDATE SPAC_SQ_SEQUENCES	SET @sequence_pentidades_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_PENTIDADES';
INSERT INTO spac_p_entidades (id, id_ent, id_pcd, tp_rel, orden, frm_edit, frm_readonly) VALUES (@sequence_pentidades_id, 214, 10, 0, 41, NULL, NULL);
UPDATE SPAC_SQ_SEQUENCES	SET @sequence_pentidades_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_PENTIDADES';
INSERT INTO spac_p_entidades (id, id_ent, id_pcd, tp_rel, orden, frm_edit, frm_readonly) VALUES (@sequence_pentidades_id, 215, 10, 0, 42, NULL, NULL);
UPDATE SPAC_SQ_SEQUENCES	SET @sequence_pentidades_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_PENTIDADES';
INSERT INTO spac_p_entidades (id, id_ent, id_pcd, tp_rel, orden, frm_edit, frm_readonly) VALUES (@sequence_pentidades_id, 7, 10, NULL, 43, NULL, NULL);
UPDATE SPAC_SQ_SEQUENCES	SET @sequence_pentidades_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_PENTIDADES';
INSERT INTO spac_p_entidades (id, id_ent, id_pcd, tp_rel, orden, frm_edit, frm_readonly) VALUES (@sequence_pentidades_id, 3, 10, NULL, 44, 14, NULL);
UPDATE SPAC_SQ_SEQUENCES	SET @sequence_pentidades_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_PENTIDADES';
INSERT INTO spac_p_entidades (id, id_ent, id_pcd, tp_rel, orden, frm_edit, frm_readonly) VALUES (@sequence_pentidades_id, 2, 10, NULL, 45, NULL, NULL);
UPDATE SPAC_SQ_SEQUENCES	SET @sequence_pentidades_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_PENTIDADES';
INSERT INTO spac_p_entidades (id, id_ent, id_pcd, tp_rel, orden, frm_edit, frm_readonly) VALUES (@sequence_pentidades_id, 1, 11, NULL, 46, 1, NULL);
UPDATE SPAC_SQ_SEQUENCES	SET @sequence_pentidades_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_PENTIDADES';
INSERT INTO spac_p_entidades (id, id_ent, id_pcd, tp_rel, orden, frm_edit, frm_readonly) VALUES (@sequence_pentidades_id, 218, 11, 0, 47, 26, NULL);
UPDATE SPAC_SQ_SEQUENCES	SET @sequence_pentidades_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_PENTIDADES';
INSERT INTO spac_p_entidades (id, id_ent, id_pcd, tp_rel, orden, frm_edit, frm_readonly) VALUES (@sequence_pentidades_id, 7, 11, NULL, 48, NULL, NULL);
UPDATE SPAC_SQ_SEQUENCES	SET @sequence_pentidades_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_PENTIDADES';
INSERT INTO spac_p_entidades (id, id_ent, id_pcd, tp_rel, orden, frm_edit, frm_readonly) VALUES (@sequence_pentidades_id, 3, 11, NULL, 49, NULL, NULL);
UPDATE SPAC_SQ_SEQUENCES	SET @sequence_pentidades_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_PENTIDADES';
INSERT INTO spac_p_entidades (id, id_ent, id_pcd, tp_rel, orden, frm_edit, frm_readonly) VALUES (@sequence_pentidades_id, 2, 11, NULL, 50, NULL, NULL);
UPDATE SPAC_SQ_SEQUENCES	SET @sequence_pentidades_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_PENTIDADES';
INSERT INTO spac_p_entidades (id, id_ent, id_pcd, tp_rel, orden, frm_edit, frm_readonly) VALUES (@sequence_pentidades_id, 1, 12, NULL, 51, NULL, NULL);
UPDATE SPAC_SQ_SEQUENCES	SET @sequence_pentidades_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_PENTIDADES';
INSERT INTO spac_p_entidades (id, id_ent, id_pcd, tp_rel, orden, frm_edit, frm_readonly) VALUES (@sequence_pentidades_id, 220, 12, 0, 52, NULL, NULL);
UPDATE SPAC_SQ_SEQUENCES	SET @sequence_pentidades_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_PENTIDADES';
INSERT INTO spac_p_entidades (id, id_ent, id_pcd, tp_rel, orden, frm_edit, frm_readonly) VALUES (@sequence_pentidades_id, 7, 12, NULL, 53, NULL, NULL);
UPDATE SPAC_SQ_SEQUENCES	SET @sequence_pentidades_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_PENTIDADES';
INSERT INTO spac_p_entidades (id, id_ent, id_pcd, tp_rel, orden, frm_edit, frm_readonly) VALUES (@sequence_pentidades_id, 2, 12, NULL, 54, NULL, NULL);
UPDATE SPAC_SQ_SEQUENCES	SET @sequence_pentidades_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_PENTIDADES';
INSERT INTO spac_p_entidades (id, id_ent, id_pcd, tp_rel, orden, frm_edit, frm_readonly) VALUES (@sequence_pentidades_id, 3, 12, NULL, 55, NULL, NULL);
UPDATE SPAC_SQ_SEQUENCES	SET @sequence_pentidades_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_PENTIDADES';
INSERT INTO spac_p_entidades (id, id_ent, id_pcd, tp_rel, orden, frm_edit, frm_readonly) VALUES (@sequence_pentidades_id, 1, 13, NULL, 56, NULL, NULL);
UPDATE SPAC_SQ_SEQUENCES	SET @sequence_pentidades_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_PENTIDADES';
INSERT INTO spac_p_entidades (id, id_ent, id_pcd, tp_rel, orden, frm_edit, frm_readonly) VALUES (@sequence_pentidades_id, 221, 13, 0, 57, NULL, NULL);
UPDATE SPAC_SQ_SEQUENCES	SET @sequence_pentidades_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_PENTIDADES';
INSERT INTO spac_p_entidades (id, id_ent, id_pcd, tp_rel, orden, frm_edit, frm_readonly) VALUES (@sequence_pentidades_id, 7, 13, NULL, 58, NULL, NULL);
UPDATE SPAC_SQ_SEQUENCES	SET @sequence_pentidades_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_PENTIDADES';
INSERT INTO spac_p_entidades (id, id_ent, id_pcd, tp_rel, orden, frm_edit, frm_readonly) VALUES (@sequence_pentidades_id, 2, 13, NULL, 59, NULL, NULL);
UPDATE SPAC_SQ_SEQUENCES	SET @sequence_pentidades_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_PENTIDADES';
INSERT INTO spac_p_entidades (id, id_ent, id_pcd, tp_rel, orden, frm_edit, frm_readonly) VALUES (@sequence_pentidades_id, 3, 13, NULL, 60, NULL, NULL);
UPDATE SPAC_SQ_SEQUENCES	SET @sequence_pentidades_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_PENTIDADES';
INSERT INTO spac_p_entidades (id, id_ent, id_pcd, tp_rel, orden, frm_edit, frm_readonly) VALUES (@sequence_pentidades_id, 1, 14, NULL, 61, NULL, NULL);
UPDATE SPAC_SQ_SEQUENCES	SET @sequence_pentidades_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_PENTIDADES';
INSERT INTO spac_p_entidades (id, id_ent, id_pcd, tp_rel, orden, frm_edit, frm_readonly) VALUES (@sequence_pentidades_id, 224, 14, 0, 62, NULL, NULL);
UPDATE SPAC_SQ_SEQUENCES	SET @sequence_pentidades_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_PENTIDADES';
INSERT INTO spac_p_entidades (id, id_ent, id_pcd, tp_rel, orden, frm_edit, frm_readonly) VALUES (@sequence_pentidades_id, 7, 14, NULL, 63, NULL, NULL);
UPDATE SPAC_SQ_SEQUENCES	SET @sequence_pentidades_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_PENTIDADES';
INSERT INTO spac_p_entidades (id, id_ent, id_pcd, tp_rel, orden, frm_edit, frm_readonly) VALUES (@sequence_pentidades_id, 2, 14, NULL, 64, NULL, NULL);
UPDATE SPAC_SQ_SEQUENCES	SET @sequence_pentidades_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_PENTIDADES';
INSERT INTO spac_p_entidades (id, id_ent, id_pcd, tp_rel, orden, frm_edit, frm_readonly) VALUES (@sequence_pentidades_id, 3, 14, NULL, 65, NULL, NULL);
UPDATE SPAC_SQ_SEQUENCES	SET @sequence_pentidades_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_PENTIDADES';
INSERT INTO spac_p_entidades (id, id_ent, id_pcd, tp_rel, orden, frm_edit, frm_readonly) VALUES (@sequence_pentidades_id, 1, 15, NULL, 66, NULL, NULL);
UPDATE SPAC_SQ_SEQUENCES	SET @sequence_pentidades_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_PENTIDADES';
INSERT INTO spac_p_entidades (id, id_ent, id_pcd, tp_rel, orden, frm_edit, frm_readonly) VALUES (@sequence_pentidades_id, 7, 15, NULL, 67, NULL, NULL);
UPDATE SPAC_SQ_SEQUENCES	SET @sequence_pentidades_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_PENTIDADES';
INSERT INTO spac_p_entidades (id, id_ent, id_pcd, tp_rel, orden, frm_edit, frm_readonly) VALUES (@sequence_pentidades_id, 227, 15, 0, 68, NULL, NULL);
UPDATE SPAC_SQ_SEQUENCES	SET @sequence_pentidades_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_PENTIDADES';
INSERT INTO spac_p_entidades (id, id_ent, id_pcd, tp_rel, orden, frm_edit, frm_readonly) VALUES (@sequence_pentidades_id, 2, 15, NULL, 69, NULL, NULL);
UPDATE SPAC_SQ_SEQUENCES	SET @sequence_pentidades_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_PENTIDADES';
INSERT INTO spac_p_entidades (id, id_ent, id_pcd, tp_rel, orden, frm_edit, frm_readonly) VALUES (@sequence_pentidades_id, 3, 15, NULL, 70, NULL, NULL);
UPDATE SPAC_SQ_SEQUENCES	SET @sequence_pentidades_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_PENTIDADES';
INSERT INTO spac_p_entidades (id, id_ent, id_pcd, tp_rel, orden, frm_edit, frm_readonly) VALUES (@sequence_pentidades_id, 1, 16, NULL, 71, NULL, NULL);
UPDATE SPAC_SQ_SEQUENCES	SET @sequence_pentidades_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_PENTIDADES';
INSERT INTO spac_p_entidades (id, id_ent, id_pcd, tp_rel, orden, frm_edit, frm_readonly) VALUES (@sequence_pentidades_id, 229, 16, 0, 72, NULL, NULL);
UPDATE SPAC_SQ_SEQUENCES	SET @sequence_pentidades_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_PENTIDADES';
INSERT INTO spac_p_entidades (id, id_ent, id_pcd, tp_rel, orden, frm_edit, frm_readonly) VALUES (@sequence_pentidades_id, 7, 16, NULL, 73, NULL, NULL);
UPDATE SPAC_SQ_SEQUENCES	SET @sequence_pentidades_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_PENTIDADES';
INSERT INTO spac_p_entidades (id, id_ent, id_pcd, tp_rel, orden, frm_edit, frm_readonly) VALUES (@sequence_pentidades_id, 2, 16, NULL, 74, NULL, NULL);
UPDATE SPAC_SQ_SEQUENCES	SET @sequence_pentidades_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_PENTIDADES';
INSERT INTO spac_p_entidades (id, id_ent, id_pcd, tp_rel, orden, frm_edit, frm_readonly) VALUES (@sequence_pentidades_id, 3, 16, NULL, 75, NULL, NULL);
UPDATE SPAC_SQ_SEQUENCES	SET @sequence_pentidades_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_PENTIDADES';
INSERT INTO spac_p_entidades (id, id_ent, id_pcd, tp_rel, orden, frm_edit, frm_readonly) VALUES (@sequence_pentidades_id, 8, 7, 1, 76, NULL, NULL);
UPDATE SPAC_SQ_SEQUENCES	SET @sequence_pentidades_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_PENTIDADES';
INSERT INTO spac_p_entidades (id, id_ent, id_pcd, tp_rel, orden, frm_edit, frm_readonly) VALUES (@sequence_pentidades_id, 8, 8, 1, 77, NULL, NULL);
UPDATE SPAC_SQ_SEQUENCES	SET @sequence_pentidades_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_PENTIDADES';
INSERT INTO spac_p_entidades (id, id_ent, id_pcd, tp_rel, orden, frm_edit, frm_readonly) VALUES (@sequence_pentidades_id, 8, 9, 1, 78, NULL, NULL);
UPDATE SPAC_SQ_SEQUENCES	SET @sequence_pentidades_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_PENTIDADES';
INSERT INTO spac_p_entidades (id, id_ent, id_pcd, tp_rel, orden, frm_edit, frm_readonly) VALUES (@sequence_pentidades_id, 8, 10, 1, 79, NULL, NULL);
UPDATE SPAC_SQ_SEQUENCES	SET @sequence_pentidades_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_PENTIDADES';
INSERT INTO spac_p_entidades (id, id_ent, id_pcd, tp_rel, orden, frm_edit, frm_readonly) VALUES (@sequence_pentidades_id, 8, 11, 1, 80, NULL, NULL);
UPDATE SPAC_SQ_SEQUENCES	SET @sequence_pentidades_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_PENTIDADES';
INSERT INTO spac_p_entidades (id, id_ent, id_pcd, tp_rel, orden, frm_edit, frm_readonly) VALUES (@sequence_pentidades_id, 8, 12, 1, 81, NULL, NULL);
UPDATE SPAC_SQ_SEQUENCES	SET @sequence_pentidades_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_PENTIDADES';
INSERT INTO spac_p_entidades (id, id_ent, id_pcd, tp_rel, orden, frm_edit, frm_readonly) VALUES (@sequence_pentidades_id, 8, 13, 1, 82, NULL, NULL);
UPDATE SPAC_SQ_SEQUENCES	SET @sequence_pentidades_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_PENTIDADES';
INSERT INTO spac_p_entidades (id, id_ent, id_pcd, tp_rel, orden, frm_edit, frm_readonly) VALUES (@sequence_pentidades_id, 8, 14, 1, 83, NULL, NULL);
UPDATE SPAC_SQ_SEQUENCES	SET @sequence_pentidades_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_PENTIDADES';
INSERT INTO spac_p_entidades (id, id_ent, id_pcd, tp_rel, orden, frm_edit, frm_readonly) VALUES (@sequence_pentidades_id, 8, 15, 1, 84, NULL, NULL);
UPDATE SPAC_SQ_SEQUENCES	SET @sequence_pentidades_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_PENTIDADES';
INSERT INTO spac_p_entidades (id, id_ent, id_pcd, tp_rel, orden, frm_edit, frm_readonly) VALUES (@sequence_pentidades_id, 8, 16, 1, 85, NULL, NULL);


-- Actualizar secuencia
UPDATE SPAC_SQ_SEQUENCES SET SEQUENCE_ID = 100 WHERE SEQUENCE_NAME = 'SPAC_SQ_ID_PENTIDADES';



--
-- Instancias de los procedimientos
--

INSERT INTO spac_p_procedimientos (id, id_pcd_bpm, nversion, nombre, estado, tipo, id_resp, id_resp_sec, id_group, id_crt, nombre_crt, ts_crt, id_upd, nombre_upd, ts_upd) VALUES (7, '7', 1, 'Acometida de agua', 2, 1, '2-4', NULL, 7, '1-0', 'SYSSUPERUSER', getDate(), '1-0', 'SYSSUPERUSER', getDate());
INSERT INTO spac_p_procedimientos (id, id_pcd_bpm, nversion, nombre, estado, tipo, id_resp, id_resp_sec, id_group, id_crt, nombre_crt, ts_crt, id_upd, nombre_upd, ts_upd) VALUES (8, '8', 1, 'Cambio de Titular de Licencia de Apertura', 2, 1, '2-4', NULL, 8, '1-0', 'SYSSUPERUSER', getDate(), '1-0', 'SYSSUPERUSER', getDate());
INSERT INTO spac_p_procedimientos (id, id_pcd_bpm, nversion, nombre, estado, tipo, id_resp, id_resp_sec, id_group, id_crt, nombre_crt, ts_crt, id_upd, nombre_upd, ts_upd) VALUES (9, '9', 1, 'Certificado Urbanístico', 2, 1, '2-4', NULL, 9, '1-0', 'SYSSUPERUSER', getDate(), '1-0', 'SYSSUPERUSER', getDate());
INSERT INTO spac_p_procedimientos (id, id_pcd_bpm, nversion, nombre, estado, tipo, id_resp, id_resp_sec, id_group, id_crt, nombre_crt, ts_crt, id_upd, nombre_upd, ts_upd) VALUES (10, '10', 1, 'Contrato negociado', 2, 1, '2-4', NULL, 10, '1-0', 'SYSSUPERUSER', getDate(), '1-0', 'SYSSUPERUSER', getDate());
INSERT INTO spac_p_procedimientos (id, id_pcd_bpm, nversion, nombre, estado, tipo, id_resp, id_resp_sec, id_group, id_crt, nombre_crt, ts_crt, id_upd, nombre_upd, ts_upd) VALUES (11, '11', 1, 'Expediente sancionador', 2, 1, '2-4', NULL, 11, '1-0', 'SYSSUPERUSER', getDate(), '1-0', 'SYSSUPERUSER', getDate());
INSERT INTO spac_p_procedimientos (id, id_pcd_bpm, nversion, nombre, estado, tipo, id_resp, id_resp_sec, id_group, id_crt, nombre_crt, ts_crt, id_upd, nombre_upd, ts_upd) VALUES (12, '12', 1, 'Licencia de Apertura de Actividad Clasificada', 2, 1, '2-4', NULL, 12, '1-0', 'SYSSUPERUSER', getDate(), '1-0', 'SYSSUPERUSER', getDate());
INSERT INTO spac_p_procedimientos (id, id_pcd_bpm, nversion, nombre, estado, tipo, id_resp, id_resp_sec, id_group, id_crt, nombre_crt, ts_crt, id_upd, nombre_upd, ts_upd) VALUES (13, '13', 1, 'Licencia de Apertura de Actividad No Clasificada', 2, 1, '2-4', NULL, 13, '1-0', 'SYSSUPERUSER', getDate(), '1-0', 'SYSSUPERUSER', getDate());
INSERT INTO spac_p_procedimientos (id, id_pcd_bpm, nversion, nombre, estado, tipo, id_resp, id_resp_sec, id_group, id_crt, nombre_crt, ts_crt, id_upd, nombre_upd, ts_upd) VALUES (14, '14', 1, 'Licencia de Vado', 2, 1, '2-4', NULL, 14, '1-0', 'SYSSUPERUSER', getDate(), '1-0', 'SYSSUPERUSER', getDate());
INSERT INTO spac_p_procedimientos (id, id_pcd_bpm, nversion, nombre, estado, tipo, id_resp, id_resp_sec, id_group, id_crt, nombre_crt, ts_crt, id_upd, nombre_upd, ts_upd) VALUES (15, '15', 1, 'Reclamación de tributos', 2, 1, '2-4', NULL, 15, '1-0', 'SYSSUPERUSER', getDate(), '1-0', 'SYSSUPERUSER', getDate());
INSERT INTO spac_p_procedimientos (id, id_pcd_bpm, nversion, nombre, estado, tipo, id_resp, id_resp_sec, id_group, id_crt, nombre_crt, ts_crt, id_upd, nombre_upd, ts_upd) VALUES (16, '16', 1, 'Tarjeta de estacionamiento para minusválidos', 2, 1, '2-4', NULL, 16, '1-0', 'SYSSUPERUSER', getDate(), '1-0', 'SYSSUPERUSER', getDate());


-- Actualizar secuencia
UPDATE SPAC_SQ_SEQUENCES SET SEQUENCE_ID = 17 WHERE SEQUENCE_NAME = 'SPAC_SQ_ID_PPROCEDIMIENTOS';



--
-- Eventos de los procedimientos
--

INSERT INTO spac_p_eventos (id_obj, tp_obj, evento, orden, id_regla, condicion) VALUES (8, 1, 2, 101, 10, NULL);
INSERT INTO spac_p_eventos (id_obj, tp_obj, evento, orden, id_regla, condicion) VALUES (9, 1, 2, 102, 10, NULL);
INSERT INTO spac_p_eventos (id_obj, tp_obj, evento, orden, id_regla, condicion) VALUES (10, 1, 2, 103, 10, NULL);
INSERT INTO spac_p_eventos (id_obj, tp_obj, evento, orden, id_regla, condicion) VALUES (11, 1, 2, 104, 10, NULL);
INSERT INTO spac_p_eventos (id_obj, tp_obj, evento, orden, id_regla, condicion) VALUES (12, 1, 2, 105, 10, NULL);
INSERT INTO spac_p_eventos (id_obj, tp_obj, evento, orden, id_regla, condicion) VALUES (13, 1, 2, 106, 10, NULL);
INSERT INTO spac_p_eventos (id_obj, tp_obj, evento, orden, id_regla, condicion) VALUES (14, 1, 2, 107, 10, NULL);
INSERT INTO spac_p_eventos (id_obj, tp_obj, evento, orden, id_regla, condicion) VALUES (16, 1, 2, 108, 10, NULL);


-- Actualizar secuencia
UPDATE SPAC_SQ_SEQUENCES SET SEQUENCE_ID = 109 WHERE SEQUENCE_NAME = 'SPAC_SQ_ID_PEVENTOS';



--
-- Fases asociadas a los procedimientos
--

INSERT INTO spac_p_fases (id, id_ctfase, id_pcd, nombre, id_resp, id_resp_sec) VALUES (18, 1, 7, 'Fase Inicio', NULL, NULL);
INSERT INTO spac_p_fases (id, id_ctfase, id_pcd, nombre, id_resp, id_resp_sec) VALUES (19, 2, 7, 'Fase Instrucción', NULL, NULL);
INSERT INTO spac_p_fases (id, id_ctfase, id_pcd, nombre, id_resp, id_resp_sec) VALUES (20, 3, 7, 'Fase Terminación', NULL, NULL);
INSERT INTO spac_p_fases (id, id_ctfase, id_pcd, nombre, id_resp, id_resp_sec) VALUES (21, 4, 7, 'Fase Archivo', NULL, NULL);
INSERT INTO spac_p_fases (id, id_ctfase, id_pcd, nombre, id_resp, id_resp_sec) VALUES (22, 1, 8, 'Fase Inicio', NULL, NULL);
INSERT INTO spac_p_fases (id, id_ctfase, id_pcd, nombre, id_resp, id_resp_sec) VALUES (23, 2, 8, 'Fase Instrucción', NULL, NULL);
INSERT INTO spac_p_fases (id, id_ctfase, id_pcd, nombre, id_resp, id_resp_sec) VALUES (24, 3, 8, 'Fase Terminación', NULL, NULL);
INSERT INTO spac_p_fases (id, id_ctfase, id_pcd, nombre, id_resp, id_resp_sec) VALUES (25, 4, 8, 'Fase Archivo', NULL, NULL);
INSERT INTO spac_p_fases (id, id_ctfase, id_pcd, nombre, id_resp, id_resp_sec) VALUES (26, 1, 9, 'Fase Inicio', NULL, NULL);
INSERT INTO spac_p_fases (id, id_ctfase, id_pcd, nombre, id_resp, id_resp_sec) VALUES (27, 2, 9, 'Fase Instrucción', NULL, NULL);
INSERT INTO spac_p_fases (id, id_ctfase, id_pcd, nombre, id_resp, id_resp_sec) VALUES (28, 3, 9, 'Fase Terminación', NULL, NULL);
INSERT INTO spac_p_fases (id, id_ctfase, id_pcd, nombre, id_resp, id_resp_sec) VALUES (29, 4, 9, 'Fase Archivo', NULL, NULL);
INSERT INTO spac_p_fases (id, id_ctfase, id_pcd, nombre, id_resp, id_resp_sec) VALUES (30, 6, 10, 'Fase Preparación', NULL, NULL);
INSERT INTO spac_p_fases (id, id_ctfase, id_pcd, nombre, id_resp, id_resp_sec) VALUES (31, 7, 10, 'Fase Licitación', NULL, NULL);
INSERT INTO spac_p_fases (id, id_ctfase, id_pcd, nombre, id_resp, id_resp_sec) VALUES (32, 8, 10, 'Fase Adjudicación', NULL, NULL);
INSERT INTO spac_p_fases (id, id_ctfase, id_pcd, nombre, id_resp, id_resp_sec) VALUES (33, 9, 10, 'Fase Ejecución', NULL, NULL);
INSERT INTO spac_p_fases (id, id_ctfase, id_pcd, nombre, id_resp, id_resp_sec) VALUES (34, 4, 10, 'Fase Archivo', NULL, NULL);
INSERT INTO spac_p_fases (id, id_ctfase, id_pcd, nombre, id_resp, id_resp_sec) VALUES (35, 1, 11, 'Fase Inicio', NULL, NULL);
INSERT INTO spac_p_fases (id, id_ctfase, id_pcd, nombre, id_resp, id_resp_sec) VALUES (36, 2, 11, 'Fase Instrucción', NULL, NULL);
INSERT INTO spac_p_fases (id, id_ctfase, id_pcd, nombre, id_resp, id_resp_sec) VALUES (37, 3, 11, 'Fase Terminación', NULL, NULL);
INSERT INTO spac_p_fases (id, id_ctfase, id_pcd, nombre, id_resp, id_resp_sec) VALUES (38, 4, 11, 'Fase Archivo', NULL, NULL);
INSERT INTO spac_p_fases (id, id_ctfase, id_pcd, nombre, id_resp, id_resp_sec) VALUES (39, 1, 12, 'Fase Inicio', NULL, NULL);
INSERT INTO spac_p_fases (id, id_ctfase, id_pcd, nombre, id_resp, id_resp_sec) VALUES (40, 2, 12, 'Fase Instrucción', NULL, NULL);
INSERT INTO spac_p_fases (id, id_ctfase, id_pcd, nombre, id_resp, id_resp_sec) VALUES (41, 3, 12, 'Fase Terminación', NULL, NULL);
INSERT INTO spac_p_fases (id, id_ctfase, id_pcd, nombre, id_resp, id_resp_sec) VALUES (42, 4, 12, 'Fase Archivo', NULL, NULL);
INSERT INTO spac_p_fases (id, id_ctfase, id_pcd, nombre, id_resp, id_resp_sec) VALUES (43, 1, 13, 'Fase Inicio', NULL, NULL);
INSERT INTO spac_p_fases (id, id_ctfase, id_pcd, nombre, id_resp, id_resp_sec) VALUES (44, 2, 13, 'Fase Instrucción', NULL, NULL);
INSERT INTO spac_p_fases (id, id_ctfase, id_pcd, nombre, id_resp, id_resp_sec) VALUES (45, 3, 13, 'Fase Terminación', NULL, NULL);
INSERT INTO spac_p_fases (id, id_ctfase, id_pcd, nombre, id_resp, id_resp_sec) VALUES (46, 4, 13, 'Fase Archivo', NULL, NULL);
INSERT INTO spac_p_fases (id, id_ctfase, id_pcd, nombre, id_resp, id_resp_sec) VALUES (47, 1, 14, 'Fase Inicio', NULL, NULL);
INSERT INTO spac_p_fases (id, id_ctfase, id_pcd, nombre, id_resp, id_resp_sec) VALUES (48, 2, 14, 'Fase Instrucción', NULL, NULL);
INSERT INTO spac_p_fases (id, id_ctfase, id_pcd, nombre, id_resp, id_resp_sec) VALUES (49, 3, 14, 'Fase Terminación', NULL, NULL);
INSERT INTO spac_p_fases (id, id_ctfase, id_pcd, nombre, id_resp, id_resp_sec) VALUES (50, 4, 14, 'Fase Archivo', NULL, NULL);
INSERT INTO spac_p_fases (id, id_ctfase, id_pcd, nombre, id_resp, id_resp_sec) VALUES (51, 1, 15, 'Fase Inicio', NULL, NULL);
INSERT INTO spac_p_fases (id, id_ctfase, id_pcd, nombre, id_resp, id_resp_sec) VALUES (52, 2, 15, 'Fase Instrucción', NULL, NULL);
INSERT INTO spac_p_fases (id, id_ctfase, id_pcd, nombre, id_resp, id_resp_sec) VALUES (53, 3, 15, 'Fase Terminación', NULL, NULL);
INSERT INTO spac_p_fases (id, id_ctfase, id_pcd, nombre, id_resp, id_resp_sec) VALUES (54, 4, 15, 'Fase Archivo', NULL, NULL);
INSERT INTO spac_p_fases (id, id_ctfase, id_pcd, nombre, id_resp, id_resp_sec) VALUES (55, 1, 16, 'Fase Inicio', NULL, NULL);
INSERT INTO spac_p_fases (id, id_ctfase, id_pcd, nombre, id_resp, id_resp_sec) VALUES (56, 2, 16, 'Fase Instrucción', NULL, NULL);
INSERT INTO spac_p_fases (id, id_ctfase, id_pcd, nombre, id_resp, id_resp_sec) VALUES (57, 3, 16, 'Fase Terminación', NULL, NULL);
INSERT INTO spac_p_fases (id, id_ctfase, id_pcd, nombre, id_resp, id_resp_sec) VALUES (58, 4, 16, 'Fase Archivo', NULL, NULL);



--
-- Flujos asociados a los procedimientos
--

INSERT INTO spac_p_flujos (id, id_flujo_bpm, id_pcd, id_origen, id_destino) VALUES (21, '21', 7, 18, 19);
INSERT INTO spac_p_flujos (id, id_flujo_bpm, id_pcd, id_origen, id_destino) VALUES (22, '22', 7, 19, 20);
INSERT INTO spac_p_flujos (id, id_flujo_bpm, id_pcd, id_origen, id_destino) VALUES (23, '23', 7, 20, 21);
INSERT INTO spac_p_flujos (id, id_flujo_bpm, id_pcd, id_origen, id_destino) VALUES (24, '24', 8, 22, 23);
INSERT INTO spac_p_flujos (id, id_flujo_bpm, id_pcd, id_origen, id_destino) VALUES (25, '25', 8, 23, 24);
INSERT INTO spac_p_flujos (id, id_flujo_bpm, id_pcd, id_origen, id_destino) VALUES (26, '26', 8, 24, 25);
INSERT INTO spac_p_flujos (id, id_flujo_bpm, id_pcd, id_origen, id_destino) VALUES (27, '27', 9, 26, 27);
INSERT INTO spac_p_flujos (id, id_flujo_bpm, id_pcd, id_origen, id_destino) VALUES (28, '28', 9, 27, 28);
INSERT INTO spac_p_flujos (id, id_flujo_bpm, id_pcd, id_origen, id_destino) VALUES (29, '29', 9, 28, 29);
INSERT INTO spac_p_flujos (id, id_flujo_bpm, id_pcd, id_origen, id_destino) VALUES (30, '30', 10, 30, 31);
INSERT INTO spac_p_flujos (id, id_flujo_bpm, id_pcd, id_origen, id_destino) VALUES (31, '31', 10, 31, 32);
INSERT INTO spac_p_flujos (id, id_flujo_bpm, id_pcd, id_origen, id_destino) VALUES (32, '32', 10, 32, 33);
INSERT INTO spac_p_flujos (id, id_flujo_bpm, id_pcd, id_origen, id_destino) VALUES (33, '33', 10, 33, 34);
INSERT INTO spac_p_flujos (id, id_flujo_bpm, id_pcd, id_origen, id_destino) VALUES (34, '34', 11, 35, 36);
INSERT INTO spac_p_flujos (id, id_flujo_bpm, id_pcd, id_origen, id_destino) VALUES (35, '35', 11, 36, 37);
INSERT INTO spac_p_flujos (id, id_flujo_bpm, id_pcd, id_origen, id_destino) VALUES (36, '36', 11, 37, 38);
INSERT INTO spac_p_flujos (id, id_flujo_bpm, id_pcd, id_origen, id_destino) VALUES (37, '37', 12, 39, 40);
INSERT INTO spac_p_flujos (id, id_flujo_bpm, id_pcd, id_origen, id_destino) VALUES (38, '38', 12, 40, 41);
INSERT INTO spac_p_flujos (id, id_flujo_bpm, id_pcd, id_origen, id_destino) VALUES (39, '39', 12, 41, 42);
INSERT INTO spac_p_flujos (id, id_flujo_bpm, id_pcd, id_origen, id_destino) VALUES (40, '40', 13, 43, 44);
INSERT INTO spac_p_flujos (id, id_flujo_bpm, id_pcd, id_origen, id_destino) VALUES (41, '41', 13, 44, 45);
INSERT INTO spac_p_flujos (id, id_flujo_bpm, id_pcd, id_origen, id_destino) VALUES (42, '42', 13, 45, 46);
INSERT INTO spac_p_flujos (id, id_flujo_bpm, id_pcd, id_origen, id_destino) VALUES (43, '43', 14, 47, 48);
INSERT INTO spac_p_flujos (id, id_flujo_bpm, id_pcd, id_origen, id_destino) VALUES (44, '44', 14, 48, 49);
INSERT INTO spac_p_flujos (id, id_flujo_bpm, id_pcd, id_origen, id_destino) VALUES (45, '45', 14, 49, 50);
INSERT INTO spac_p_flujos (id, id_flujo_bpm, id_pcd, id_origen, id_destino) VALUES (46, '46', 15, 51, 52);
INSERT INTO spac_p_flujos (id, id_flujo_bpm, id_pcd, id_origen, id_destino) VALUES (47, '47', 15, 52, 53);
INSERT INTO spac_p_flujos (id, id_flujo_bpm, id_pcd, id_origen, id_destino) VALUES (48, '48', 15, 53, 54);
INSERT INTO spac_p_flujos (id, id_flujo_bpm, id_pcd, id_origen, id_destino) VALUES (49, '49', 16, 55, 56);
INSERT INTO spac_p_flujos (id, id_flujo_bpm, id_pcd, id_origen, id_destino) VALUES (50, '50', 16, 56, 57);
INSERT INTO spac_p_flujos (id, id_flujo_bpm, id_pcd, id_origen, id_destino) VALUES (51, '51', 16, 57, 58);


-- Actualizar secuencia
UPDATE SPAC_SQ_SEQUENCES SET SEQUENCE_ID = 52 WHERE SEQUENCE_NAME = 'SPAC_SQ_ID_PFLUJOS';



-- 
-- Formularios asociados a las fases de los procedimientos
--

INSERT INTO spac_p_frmfases (id, id_ent, id_fase, frm_edit, frm_readonly) VALUES (1, 210, 26, 20, NULL);
INSERT INTO spac_p_frmfases (id, id_ent, id_fase, frm_edit, frm_readonly) VALUES (2, 210, 27, 21, NULL);


-- Actualizar secuencia
UPDATE SPAC_SQ_SEQUENCES SET SEQUENCE_ID = 3 WHERE SEQUENCE_NAME = 'SPAC_SQ_ID_PFRMFASES';



--
-- Fases/Tipos de documentos específicos en los procedimientos
--

INSERT INTO spac_p_fstd (id, id_fase, id_tpdoc) VALUES (1, 22, 20);
INSERT INTO spac_p_fstd (id, id_fase, id_tpdoc) VALUES (2, 22, 30);
INSERT INTO spac_p_fstd (id, id_fase, id_tpdoc) VALUES (3, 22, 34);
INSERT INTO spac_p_fstd (id, id_fase, id_tpdoc) VALUES (4, 26, 20);
INSERT INTO spac_p_fstd (id, id_fase, id_tpdoc) VALUES (5, 26, 30);
INSERT INTO spac_p_fstd (id, id_fase, id_tpdoc) VALUES (6, 26, 34);
INSERT INTO spac_p_fstd (id, id_fase, id_tpdoc) VALUES (7, 35, 30);
INSERT INTO spac_p_fstd (id, id_fase, id_tpdoc) VALUES (8, 39, 20);
INSERT INTO spac_p_fstd (id, id_fase, id_tpdoc) VALUES (9, 39, 30);
INSERT INTO spac_p_fstd (id, id_fase, id_tpdoc) VALUES (10, 39, 34);
INSERT INTO spac_p_fstd (id, id_fase, id_tpdoc) VALUES (11, 43, 20);
INSERT INTO spac_p_fstd (id, id_fase, id_tpdoc) VALUES (12, 43, 30);
INSERT INTO spac_p_fstd (id, id_fase, id_tpdoc) VALUES (13, 43, 34);
INSERT INTO spac_p_fstd (id, id_fase, id_tpdoc) VALUES (14, 47, 20);
INSERT INTO spac_p_fstd (id, id_fase, id_tpdoc) VALUES (15, 47, 30);
INSERT INTO spac_p_fstd (id, id_fase, id_tpdoc) VALUES (16, 47, 34);
INSERT INTO spac_p_fstd (id, id_fase, id_tpdoc) VALUES (17, 55, 30);
INSERT INTO spac_p_fstd (id, id_fase, id_tpdoc) VALUES (18, 55, 34);
INSERT INTO spac_p_fstd (id, id_fase, id_tpdoc) VALUES (19, 55, 20);


-- Actualizar secuencia
UPDATE SPAC_SQ_SEQUENCES SET SEQUENCE_ID = 19 WHERE SEQUENCE_NAME = 'SPAC_SQ_ID_PFSTD';



--
-- Nodos de los procedimientos
--

INSERT INTO spac_p_nodos (id, id_nodo_bpm, id_pcd, tipo, g_info) VALUES (18, '18', 7, 1, '<ginfo><position x=''755'' y=''324''/></ginfo>');
INSERT INTO spac_p_nodos (id, id_nodo_bpm, id_pcd, tipo, g_info) VALUES (19, '19', 7, 1, '<ginfo><position x=''253'' y=''132''/></ginfo>');
INSERT INTO spac_p_nodos (id, id_nodo_bpm, id_pcd, tipo, g_info) VALUES (20, '20', 7, 1, '<ginfo><position x=''144'' y=''343''/></ginfo>');
INSERT INTO spac_p_nodos (id, id_nodo_bpm, id_pcd, tipo, g_info) VALUES (21, '21', 7, 1, '<ginfo><position x=''710'' y=''102''/></ginfo>');
INSERT INTO spac_p_nodos (id, id_nodo_bpm, id_pcd, tipo, g_info) VALUES (22, '22', 8, 1, '<ginfo><position x=''11'' y=''107''/></ginfo>');
INSERT INTO spac_p_nodos (id, id_nodo_bpm, id_pcd, tipo, g_info) VALUES (23, '23', 8, 1, '<ginfo><position x=''308'' y=''101''/></ginfo>');
INSERT INTO spac_p_nodos (id, id_nodo_bpm, id_pcd, tipo, g_info) VALUES (24, '24', 8, 1, '<ginfo><position x=''654'' y=''91''/></ginfo>');
INSERT INTO spac_p_nodos (id, id_nodo_bpm, id_pcd, tipo, g_info) VALUES (25, '25', 8, 1, '<ginfo><position x=''938'' y=''102''/></ginfo>');
INSERT INTO spac_p_nodos (id, id_nodo_bpm, id_pcd, tipo, g_info) VALUES (26, '26', 9, 1, NULL);
INSERT INTO spac_p_nodos (id, id_nodo_bpm, id_pcd, tipo, g_info) VALUES (27, '27', 9, 1, NULL);
INSERT INTO spac_p_nodos (id, id_nodo_bpm, id_pcd, tipo, g_info) VALUES (28, '28', 9, 1, NULL);
INSERT INTO spac_p_nodos (id, id_nodo_bpm, id_pcd, tipo, g_info) VALUES (29, '29', 9, 1, NULL);
INSERT INTO spac_p_nodos (id, id_nodo_bpm, id_pcd, tipo, g_info) VALUES (30, '30', 10, 1, '<ginfo><position x=''18'' y=''100''/></ginfo>');
INSERT INTO spac_p_nodos (id, id_nodo_bpm, id_pcd, tipo, g_info) VALUES (31, '31', 10, 1, NULL);
INSERT INTO spac_p_nodos (id, id_nodo_bpm, id_pcd, tipo, g_info) VALUES (32, '32', 10, 1, NULL);
INSERT INTO spac_p_nodos (id, id_nodo_bpm, id_pcd, tipo, g_info) VALUES (33, '33', 10, 1, '<ginfo><position x=''342'' y=''497''/></ginfo>');
INSERT INTO spac_p_nodos (id, id_nodo_bpm, id_pcd, tipo, g_info) VALUES (34, '34', 10, 1, '<ginfo><position x=''898'' y=''285''/></ginfo>');
INSERT INTO spac_p_nodos (id, id_nodo_bpm, id_pcd, tipo, g_info) VALUES (35, '35', 11, 1, '<ginfo><position x=''0'' y=''0''/></ginfo>');
INSERT INTO spac_p_nodos (id, id_nodo_bpm, id_pcd, tipo, g_info) VALUES (36, '36', 11, 1, '<ginfo><position x=''0'' y=''0''/></ginfo>');
INSERT INTO spac_p_nodos (id, id_nodo_bpm, id_pcd, tipo, g_info) VALUES (37, '37', 11, 1, '<ginfo><position x=''0'' y=''0''/></ginfo>');
INSERT INTO spac_p_nodos (id, id_nodo_bpm, id_pcd, tipo, g_info) VALUES (38, '38', 11, 1, '<ginfo><position x=''0'' y=''0''/></ginfo>');
INSERT INTO spac_p_nodos (id, id_nodo_bpm, id_pcd, tipo, g_info) VALUES (39, '39', 12, 1, NULL);
INSERT INTO spac_p_nodos (id, id_nodo_bpm, id_pcd, tipo, g_info) VALUES (40, '40', 12, 1, NULL);
INSERT INTO spac_p_nodos (id, id_nodo_bpm, id_pcd, tipo, g_info) VALUES (41, '41', 12, 1, NULL);
INSERT INTO spac_p_nodos (id, id_nodo_bpm, id_pcd, tipo, g_info) VALUES (42, '42', 12, 1, NULL);
INSERT INTO spac_p_nodos (id, id_nodo_bpm, id_pcd, tipo, g_info) VALUES (43, '43', 13, 1, NULL);
INSERT INTO spac_p_nodos (id, id_nodo_bpm, id_pcd, tipo, g_info) VALUES (44, '44', 13, 1, NULL);
INSERT INTO spac_p_nodos (id, id_nodo_bpm, id_pcd, tipo, g_info) VALUES (45, '45', 13, 1, NULL);
INSERT INTO spac_p_nodos (id, id_nodo_bpm, id_pcd, tipo, g_info) VALUES (46, '46', 13, 1, NULL);
INSERT INTO spac_p_nodos (id, id_nodo_bpm, id_pcd, tipo, g_info) VALUES (47, '47', 14, 1, NULL);
INSERT INTO spac_p_nodos (id, id_nodo_bpm, id_pcd, tipo, g_info) VALUES (48, '48', 14, 1, NULL);
INSERT INTO spac_p_nodos (id, id_nodo_bpm, id_pcd, tipo, g_info) VALUES (49, '49', 14, 1, NULL);
INSERT INTO spac_p_nodos (id, id_nodo_bpm, id_pcd, tipo, g_info) VALUES (50, '50', 14, 1, NULL);
INSERT INTO spac_p_nodos (id, id_nodo_bpm, id_pcd, tipo, g_info) VALUES (51, '51', 15, 1, '<ginfo><position x=''0'' y=''0''/></ginfo>');
INSERT INTO spac_p_nodos (id, id_nodo_bpm, id_pcd, tipo, g_info) VALUES (52, '52', 15, 1, '<ginfo><position x=''0'' y=''0''/></ginfo>');
INSERT INTO spac_p_nodos (id, id_nodo_bpm, id_pcd, tipo, g_info) VALUES (53, '53', 15, 1, '<ginfo><position x=''0'' y=''0''/></ginfo>');
INSERT INTO spac_p_nodos (id, id_nodo_bpm, id_pcd, tipo, g_info) VALUES (54, '54', 15, 1, '<ginfo><position x=''0'' y=''0''/></ginfo>');
INSERT INTO spac_p_nodos (id, id_nodo_bpm, id_pcd, tipo, g_info) VALUES (55, '55', 16, 1, NULL);
INSERT INTO spac_p_nodos (id, id_nodo_bpm, id_pcd, tipo, g_info) VALUES (56, '56', 16, 1, NULL);
INSERT INTO spac_p_nodos (id, id_nodo_bpm, id_pcd, tipo, g_info) VALUES (57, '57', 16, 1, NULL);
INSERT INTO spac_p_nodos (id, id_nodo_bpm, id_pcd, tipo, g_info) VALUES (58, '58', 16, 1, NULL);
 

-- Actualizar la secuencia
UPDATE SPAC_SQ_SEQUENCES SET SEQUENCE_ID = 59 WHERE SEQUENCE_NAME = 'SPAC_SQ_ID_PNODOS';



-- 
-- Plantillas
--

INSERT INTO spac_p_plantdoc (id, id_tpdoc, fecha, nombre, expresion, mimetype, path, estacion) VALUES (41, 4, getDate(), 'Archivo del expediente', NULL, NULL, NULL, NULL);
INSERT INTO spac_p_plantdoc (id, id_tpdoc, fecha, nombre, expresion, mimetype, path, estacion) VALUES (42, 4, getDate(), 'Archivo del expediente DPH', NULL, NULL, NULL, NULL);
INSERT INTO spac_p_plantdoc (id, id_tpdoc, fecha, nombre, expresion, mimetype, path, estacion) VALUES (43, 4, getDate(), 'Archivo del expediente AYUNT.', NULL, NULL, NULL, NULL);
INSERT INTO spac_p_plantdoc (id, id_tpdoc, fecha, nombre, expresion, mimetype, path, estacion) VALUES (44, 11, getDate(), 'Notificación de la acometida de agua', NULL, NULL, NULL, NULL);
INSERT INTO spac_p_plantdoc (id, id_tpdoc, fecha, nombre, expresion, mimetype, path, estacion) VALUES (45, 11, getDate(), 'Notificación de requerimiento de documentación ', NULL, NULL, NULL, NULL);
INSERT INTO spac_p_plantdoc (id, id_tpdoc, fecha, nombre, expresion, mimetype, path, estacion) VALUES (46, 11, getDate(), 'Notificación de la Resolución por desistimiento a la compañía de seguros', NULL, NULL, NULL, NULL);
INSERT INTO spac_p_plantdoc (id, id_tpdoc, fecha, nombre, expresion, mimetype, path, estacion) VALUES (47, 11, getDate(), 'Notificación de Resolución (estimación/desestimación)  al interesado', NULL, NULL, NULL, NULL);
INSERT INTO spac_p_plantdoc (id, id_tpdoc, fecha, nombre, expresion, mimetype, path, estacion) VALUES (48, 11, getDate(), 'Notificación de Resolución por acuerdo indemnizatorio al interesado', NULL, NULL, NULL, NULL);
INSERT INTO spac_p_plantdoc (id, id_tpdoc, fecha, nombre, expresion, mimetype, path, estacion) VALUES (49, 11, getDate(), 'Notificación a los proponentes', NULL, NULL, NULL, NULL);
INSERT INTO spac_p_plantdoc (id, id_tpdoc, fecha, nombre, expresion, mimetype, path, estacion) VALUES (50, 22, getDate(), 'Solicitud de subsanación', NULL, NULL, NULL, NULL);
INSERT INTO spac_p_plantdoc (id, id_tpdoc, fecha, nombre, expresion, mimetype, path, estacion) VALUES (51, 23, getDate(), 'Informe técnico de la acometida de agua', NULL, NULL, NULL, NULL);
INSERT INTO spac_p_plantdoc (id, id_tpdoc, fecha, nombre, expresion, mimetype, path, estacion) VALUES (52, 25, getDate(), 'Informe jurídico de la acometida de agua', NULL, NULL, NULL, NULL);
INSERT INTO spac_p_plantdoc (id, id_tpdoc, fecha, nombre, expresion, mimetype, path, estacion) VALUES (53, 27, getDate(), 'Licencia de acometida de agua', NULL, NULL, NULL, NULL);
INSERT INTO spac_p_plantdoc (id, id_tpdoc, fecha, nombre, expresion, mimetype, path, estacion) VALUES (54, 28, getDate(), 'Liquidación de la acometida de agua', NULL, NULL, NULL, NULL);
INSERT INTO spac_p_plantdoc (id, id_tpdoc, fecha, nombre, expresion, mimetype, path, estacion) VALUES (55, 29, getDate(), 'Autorización de la acometida de agua', NULL, NULL, NULL, NULL);
INSERT INTO spac_p_plantdoc (id, id_tpdoc, fecha, nombre, expresion, mimetype, path, estacion) VALUES (56, 22, getDate(), 'Solicitud subsanación cambio titular', NULL, NULL, NULL, NULL);
INSERT INTO spac_p_plantdoc (id, id_tpdoc, fecha, nombre, expresion, mimetype, path, estacion) VALUES (57, 16, getDate(), 'Notificación resolución cambio titular', NULL, NULL, NULL, NULL);
INSERT INTO spac_p_plantdoc (id, id_tpdoc, fecha, nombre, expresion, mimetype, path, estacion) VALUES (58, 17, getDate(), 'Resolución expediente', NULL, NULL, NULL, NULL);
INSERT INTO spac_p_plantdoc (id, id_tpdoc, fecha, nombre, expresion, mimetype, path, estacion) VALUES (59, 17, getDate(), 'Resolución cambio titular', NULL, NULL, NULL, NULL);
INSERT INTO spac_p_plantdoc (id, id_tpdoc, fecha, nombre, expresion, mimetype, path, estacion) VALUES (60, 17, getDate(), 'Resolución del procedimiento (estimación o desestimación)', NULL, NULL, NULL, NULL);
INSERT INTO spac_p_plantdoc (id, id_tpdoc, fecha, nombre, expresion, mimetype, path, estacion) VALUES (61, 17, getDate(), 'Resolución por desistimiento', NULL, NULL, NULL, NULL);
INSERT INTO spac_p_plantdoc (id, id_tpdoc, fecha, nombre, expresion, mimetype, path, estacion) VALUES (62, 17, getDate(), 'Resolución por Acuerdo de indemnización', NULL, NULL, NULL, NULL);
INSERT INTO spac_p_plantdoc (id, id_tpdoc, fecha, nombre, expresion, mimetype, path, estacion) VALUES (63, 17, getDate(), 'Resolución de Adjudicación', NULL, NULL, NULL, NULL);
INSERT INTO spac_p_plantdoc (id, id_tpdoc, fecha, nombre, expresion, mimetype, path, estacion) VALUES (64, 20, getDate(), 'Anexo a solicitud', NULL, NULL, NULL, NULL);
INSERT INTO spac_p_plantdoc (id, id_tpdoc, fecha, nombre, expresion, mimetype, path, estacion) VALUES (65, 30, getDate(), 'Carátula cambio titular', NULL, NULL, NULL, NULL);
INSERT INTO spac_p_plantdoc (id, id_tpdoc, fecha, nombre, expresion, mimetype, path, estacion) VALUES (66, 23, getDate(), 'Informe técnico cambio titular', NULL, NULL, NULL, NULL);
INSERT INTO spac_p_plantdoc (id, id_tpdoc, fecha, nombre, expresion, mimetype, path, estacion) VALUES (67, 31, getDate(), 'Notificación liquidación cambio titular', NULL, NULL, NULL, NULL);
INSERT INTO spac_p_plantdoc (id, id_tpdoc, fecha, nombre, expresion, mimetype, path, estacion) VALUES (68, 25, getDate(), 'Informe jurídico cambio titular', NULL, NULL, NULL, NULL);
INSERT INTO spac_p_plantdoc (id, id_tpdoc, fecha, nombre, expresion, mimetype, path, estacion) VALUES (69, 26, getDate(), 'Notificación solicitud subsanación cambio titular', NULL, NULL, NULL, NULL);
INSERT INTO spac_p_plantdoc (id, id_tpdoc, fecha, nombre, expresion, mimetype, path, estacion) VALUES (70, 27, getDate(), 'Licencia cambio titular', NULL, NULL, NULL, NULL);
INSERT INTO spac_p_plantdoc (id, id_tpdoc, fecha, nombre, expresion, mimetype, path, estacion) VALUES (71, 34, getDate(), 'Solicitud cambio titular', NULL, NULL, NULL, NULL);
INSERT INTO spac_p_plantdoc (id, id_tpdoc, fecha, nombre, expresion, mimetype, path, estacion) VALUES (72, 34, getDate(), 'Solicitud de reclamación', NULL, NULL, NULL, NULL);
INSERT INTO spac_p_plantdoc (id, id_tpdoc, fecha, nombre, expresion, mimetype, path, estacion) VALUES (73, 35, getDate(), 'Escrito interesado cambio titular', NULL, NULL, NULL, NULL);
INSERT INTO spac_p_plantdoc (id, id_tpdoc, fecha, nombre, expresion, mimetype, path, estacion) VALUES (74, 30, getDate(), 'Carátula Certificado Urbanístico', NULL, NULL, NULL, NULL);
INSERT INTO spac_p_plantdoc (id, id_tpdoc, fecha, nombre, expresion, mimetype, path, estacion) VALUES (75, 23, getDate(), 'Informe Certificado Urbanístico', NULL, NULL, NULL, NULL);
INSERT INTO spac_p_plantdoc (id, id_tpdoc, fecha, nombre, expresion, mimetype, path, estacion) VALUES (76, 36, getDate(), 'Certificado Urbanístico', NULL, NULL, NULL, NULL);
INSERT INTO spac_p_plantdoc (id, id_tpdoc, fecha, nombre, expresion, mimetype, path, estacion) VALUES (77, 26, getDate(), 'Notificación subsanación', NULL, NULL, NULL, NULL);
INSERT INTO spac_p_plantdoc (id, id_tpdoc, fecha, nombre, expresion, mimetype, path, estacion) VALUES (78, 34, getDate(), 'Solicitud Certificado Urbanístico', NULL, NULL, NULL, NULL);
INSERT INTO spac_p_plantdoc (id, id_tpdoc, fecha, nombre, expresion, mimetype, path, estacion) VALUES (79, 16, getDate(), 'Notificación resolución contratación', NULL, NULL, NULL, NULL);
INSERT INTO spac_p_plantdoc (id, id_tpdoc, fecha, nombre, expresion, mimetype, path, estacion) VALUES (80, 23, getDate(), 'Informe técnico', NULL, NULL, NULL, NULL);
INSERT INTO spac_p_plantdoc (id, id_tpdoc, fecha, nombre, expresion, mimetype, path, estacion) VALUES (81, 37, getDate(), 'Requerimiento subsanación', NULL, NULL, NULL, NULL);
INSERT INTO spac_p_plantdoc (id, id_tpdoc, fecha, nombre, expresion, mimetype, path, estacion) VALUES (82, 38, getDate(), 'Notificación de subsanación', NULL, NULL, NULL, NULL);
INSERT INTO spac_p_plantdoc (id, id_tpdoc, fecha, nombre, expresion, mimetype, path, estacion) VALUES (83, 39, getDate(), 'Propuesta de inicio', NULL, NULL, NULL, NULL);
INSERT INTO spac_p_plantdoc (id, id_tpdoc, fecha, nombre, expresion, mimetype, path, estacion) VALUES (84, 40, getDate(), 'Pliegos diputación', NULL, NULL, NULL, NULL);
INSERT INTO spac_p_plantdoc (id, id_tpdoc, fecha, nombre, expresion, mimetype, path, estacion) VALUES (85, 40, getDate(), 'Pliegos ayuntamiento', NULL, NULL, NULL, NULL);
INSERT INTO spac_p_plantdoc (id, id_tpdoc, fecha, nombre, expresion, mimetype, path, estacion) VALUES (86, 41, getDate(), 'Prescripciones técnicas', NULL, NULL, NULL, NULL);
INSERT INTO spac_p_plantdoc (id, id_tpdoc, fecha, nombre, expresion, mimetype, path, estacion) VALUES (87, 42, getDate(), 'Acta de replanteo previa', NULL, NULL, NULL, NULL);
INSERT INTO spac_p_plantdoc (id, id_tpdoc, fecha, nombre, expresion, mimetype, path, estacion) VALUES (88, 43, getDate(), 'Informe de intervención', NULL, NULL, NULL, NULL);
INSERT INTO spac_p_plantdoc (id, id_tpdoc, fecha, nombre, expresion, mimetype, path, estacion) VALUES (89, 43, getDate(), 'Informe existencia crédito', NULL, NULL, NULL, NULL);
INSERT INTO spac_p_plantdoc (id, id_tpdoc, fecha, nombre, expresion, mimetype, path, estacion) VALUES (90, 44, getDate(), 'Informe de secretaría', NULL, NULL, NULL, NULL);
INSERT INTO spac_p_plantdoc (id, id_tpdoc, fecha, nombre, expresion, mimetype, path, estacion) VALUES (91, 45, getDate(), 'Proyecto', NULL, NULL, NULL, NULL);
INSERT INTO spac_p_plantdoc (id, id_tpdoc, fecha, nombre, expresion, mimetype, path, estacion) VALUES (92, 46, getDate(), 'Resolución aprobación expediente', NULL, NULL, NULL, NULL);
INSERT INTO spac_p_plantdoc (id, id_tpdoc, fecha, nombre, expresion, mimetype, path, estacion) VALUES (93, 46, getDate(), 'Acuerdo aprobación expediente', NULL, NULL, NULL, NULL);
INSERT INTO spac_p_plantdoc (id, id_tpdoc, fecha, nombre, expresion, mimetype, path, estacion) VALUES (94, 47, getDate(), 'Resolución nombramiento mesa', NULL, NULL, NULL, NULL);
INSERT INTO spac_p_plantdoc (id, id_tpdoc, fecha, nombre, expresion, mimetype, path, estacion) VALUES (95, 48, getDate(), 'Resolución de invitación', NULL, NULL, NULL, NULL);
INSERT INTO spac_p_plantdoc (id, id_tpdoc, fecha, nombre, expresion, mimetype, path, estacion) VALUES (96, 48, getDate(), 'Consulta a licitador', NULL, NULL, NULL, NULL);
INSERT INTO spac_p_plantdoc (id, id_tpdoc, fecha, nombre, expresion, mimetype, path, estacion) VALUES (97, 49, getDate(), 'Convocatoria mesa', NULL, NULL, NULL, NULL);
INSERT INTO spac_p_plantdoc (id, id_tpdoc, fecha, nombre, expresion, mimetype, path, estacion) VALUES (98, 50, getDate(), 'Certificado presentación ofertas', NULL, NULL, NULL, NULL);
INSERT INTO spac_p_plantdoc (id, id_tpdoc, fecha, nombre, expresion, mimetype, path, estacion) VALUES (99, 51, getDate(), 'Acta mesa', NULL, NULL, NULL, NULL);
INSERT INTO spac_p_plantdoc (id, id_tpdoc, fecha, nombre, expresion, mimetype, path, estacion) VALUES (100, 52, getDate(), 'Resolución dirección obra', NULL, NULL, NULL, NULL);
INSERT INTO spac_p_plantdoc (id, id_tpdoc, fecha, nombre, expresion, mimetype, path, estacion) VALUES (101, 53, getDate(), 'Resolución adjudicación provisional', NULL, NULL, NULL, NULL);
INSERT INTO spac_p_plantdoc (id, id_tpdoc, fecha, nombre, expresion, mimetype, path, estacion) VALUES (102, 53, getDate(), 'Acuerdo resolución provisional', NULL, NULL, NULL, NULL);
INSERT INTO spac_p_plantdoc (id, id_tpdoc, fecha, nombre, expresion, mimetype, path, estacion) VALUES (103, 54, getDate(), 'Publicación DOGA', NULL, NULL, NULL, NULL);
INSERT INTO spac_p_plantdoc (id, id_tpdoc, fecha, nombre, expresion, mimetype, path, estacion) VALUES (104, 54, getDate(), 'Publicación', NULL, NULL, NULL, NULL);
INSERT INTO spac_p_plantdoc (id, id_tpdoc, fecha, nombre, expresion, mimetype, path, estacion) VALUES (105, 54, getDate(), 'Publicación BOP', NULL, NULL, NULL, NULL);
INSERT INTO spac_p_plantdoc (id, id_tpdoc, fecha, nombre, expresion, mimetype, path, estacion) VALUES (106, 55, getDate(), 'Acuerdo resolución definitiva', NULL, NULL, NULL, NULL);
INSERT INTO spac_p_plantdoc (id, id_tpdoc, fecha, nombre, expresion, mimetype, path, estacion) VALUES (107, 55, getDate(), 'Resolución adjudicación', NULL, NULL, NULL, NULL);
INSERT INTO spac_p_plantdoc (id, id_tpdoc, fecha, nombre, expresion, mimetype, path, estacion) VALUES (108, 56, getDate(), 'Oficio de devolución', NULL, NULL, NULL, NULL);
INSERT INTO spac_p_plantdoc (id, id_tpdoc, fecha, nombre, expresion, mimetype, path, estacion) VALUES (109, 57, getDate(), 'Decreto aprobación plan de seguridad', NULL, NULL, NULL, NULL);
INSERT INTO spac_p_plantdoc (id, id_tpdoc, fecha, nombre, expresion, mimetype, path, estacion) VALUES (110, 57, getDate(), 'Informe técnico seguridad', NULL, NULL, NULL, NULL);
INSERT INTO spac_p_plantdoc (id, id_tpdoc, fecha, nombre, expresion, mimetype, path, estacion) VALUES (111, 58, getDate(), 'Acta de comprobación de replanteo', NULL, NULL, NULL, NULL);
INSERT INTO spac_p_plantdoc (id, id_tpdoc, fecha, nombre, expresion, mimetype, path, estacion) VALUES (112, 36, getDate(), 'Certificación', NULL, NULL, NULL, NULL);
INSERT INTO spac_p_plantdoc (id, id_tpdoc, fecha, nombre, expresion, mimetype, path, estacion) VALUES (113, 59, getDate(), 'Resolución de prórroga', NULL, NULL, NULL, NULL);
INSERT INTO spac_p_plantdoc (id, id_tpdoc, fecha, nombre, expresion, mimetype, path, estacion) VALUES (114, 60, getDate(), 'Resolución de paralización', NULL, NULL, NULL, NULL);
INSERT INTO spac_p_plantdoc (id, id_tpdoc, fecha, nombre, expresion, mimetype, path, estacion) VALUES (115, 61, getDate(), 'Resolución ampliación de plazos', NULL, NULL, NULL, NULL);
INSERT INTO spac_p_plantdoc (id, id_tpdoc, fecha, nombre, expresion, mimetype, path, estacion) VALUES (116, 62, getDate(), 'Certificación final', NULL, NULL, NULL, NULL);
INSERT INTO spac_p_plantdoc (id, id_tpdoc, fecha, nombre, expresion, mimetype, path, estacion) VALUES (117, 63, getDate(), 'Ficha Junta Consultiva', NULL, NULL, NULL, NULL);
INSERT INTO spac_p_plantdoc (id, id_tpdoc, fecha, nombre, expresion, mimetype, path, estacion) VALUES (118, 63, getDate(), 'Ficha POS', NULL, NULL, NULL, NULL);
INSERT INTO spac_p_plantdoc (id, id_tpdoc, fecha, nombre, expresion, mimetype, path, estacion) VALUES (119, 63, getDate(), 'Ficha cuentas de ayuntamiento', NULL, NULL, NULL, NULL);
INSERT INTO spac_p_plantdoc (id, id_tpdoc, fecha, nombre, expresion, mimetype, path, estacion) VALUES (120, 64, getDate(), 'Petición de licencia', NULL, NULL, NULL, NULL);
INSERT INTO spac_p_plantdoc (id, id_tpdoc, fecha, nombre, expresion, mimetype, path, estacion) VALUES (121, 64, getDate(), 'Licencia', NULL, NULL, NULL, NULL);
INSERT INTO spac_p_plantdoc (id, id_tpdoc, fecha, nombre, expresion, mimetype, path, estacion) VALUES (122, 65, getDate(), 'Contrato', NULL, NULL, NULL, NULL);
INSERT INTO spac_p_plantdoc (id, id_tpdoc, fecha, nombre, expresion, mimetype, path, estacion) VALUES (123, 3, getDate(), 'Alegaciones sancionador', NULL, NULL, NULL, NULL);
INSERT INTO spac_p_plantdoc (id, id_tpdoc, fecha, nombre, expresion, mimetype, path, estacion) VALUES (124, 11, getDate(), 'Notificación acuerdo sancionador', NULL, NULL, NULL, NULL);
INSERT INTO spac_p_plantdoc (id, id_tpdoc, fecha, nombre, expresion, mimetype, path, estacion) VALUES (125, 13, getDate(), 'Propuesta de resolución sancionador', NULL, NULL, NULL, NULL);
INSERT INTO spac_p_plantdoc (id, id_tpdoc, fecha, nombre, expresion, mimetype, path, estacion) VALUES (126, 16, getDate(), 'Notificación resolución sancionador', NULL, NULL, NULL, NULL);
INSERT INTO spac_p_plantdoc (id, id_tpdoc, fecha, nombre, expresion, mimetype, path, estacion) VALUES (127, 17, getDate(), 'Resolución sancionador', NULL, NULL, NULL, NULL);
INSERT INTO spac_p_plantdoc (id, id_tpdoc, fecha, nombre, expresion, mimetype, path, estacion) VALUES (128, 30, getDate(), 'Carátula sancionador', NULL, NULL, NULL, NULL);
INSERT INTO spac_p_plantdoc (id, id_tpdoc, fecha, nombre, expresion, mimetype, path, estacion) VALUES (129, 23, getDate(), 'Informe técnico sancionador', NULL, NULL, NULL, NULL);
INSERT INTO spac_p_plantdoc (id, id_tpdoc, fecha, nombre, expresion, mimetype, path, estacion) VALUES (130, 31, getDate(), 'Notificación liquidación', NULL, NULL, NULL, NULL);
INSERT INTO spac_p_plantdoc (id, id_tpdoc, fecha, nombre, expresion, mimetype, path, estacion) VALUES (131, 24, getDate(), 'Petición de informes sancionador', NULL, NULL, NULL, NULL);
INSERT INTO spac_p_plantdoc (id, id_tpdoc, fecha, nombre, expresion, mimetype, path, estacion) VALUES (132, 67, getDate(), 'Acuerdo de incoación sancionador', NULL, NULL, NULL, NULL);
INSERT INTO spac_p_plantdoc (id, id_tpdoc, fecha, nombre, expresion, mimetype, path, estacion) VALUES (133, 68, getDate(), 'Acuerdo no incoación sancionador', NULL, NULL, NULL, NULL);
INSERT INTO spac_p_plantdoc (id, id_tpdoc, fecha, nombre, expresion, mimetype, path, estacion) VALUES (134, 69, getDate(), 'Medidas provisionales sancionador', NULL, NULL, NULL, NULL);
INSERT INTO spac_p_plantdoc (id, id_tpdoc, fecha, nombre, expresion, mimetype, path, estacion) VALUES (135, 70, getDate(), 'Actuaciones realizadas sancionador', NULL, NULL, NULL, NULL);
INSERT INTO spac_p_plantdoc (id, id_tpdoc, fecha, nombre, expresion, mimetype, path, estacion) VALUES (136, 3, getDate(), 'Alegaciones act. clasificadas', NULL, NULL, NULL, NULL);
INSERT INTO spac_p_plantdoc (id, id_tpdoc, fecha, nombre, expresion, mimetype, path, estacion) VALUES (137, 22, getDate(), 'Solicitud subsanación act. clasificadas', NULL, NULL, NULL, NULL);
INSERT INTO spac_p_plantdoc (id, id_tpdoc, fecha, nombre, expresion, mimetype, path, estacion) VALUES (138, 16, getDate(), 'Notificación resolución act. clasificadas', NULL, NULL, NULL, NULL);
INSERT INTO spac_p_plantdoc (id, id_tpdoc, fecha, nombre, expresion, mimetype, path, estacion) VALUES (139, 17, getDate(), 'Resolución act. clasificadas', NULL, NULL, NULL, NULL);
INSERT INTO spac_p_plantdoc (id, id_tpdoc, fecha, nombre, expresion, mimetype, path, estacion) VALUES (140, 30, getDate(), 'Carátula act. clasificadas', NULL, NULL, NULL, NULL);
INSERT INTO spac_p_plantdoc (id, id_tpdoc, fecha, nombre, expresion, mimetype, path, estacion) VALUES (141, 23, getDate(), 'Informe de compatibilidad', NULL, NULL, NULL, NULL);
INSERT INTO spac_p_plantdoc (id, id_tpdoc, fecha, nombre, expresion, mimetype, path, estacion) VALUES (142, 23, getDate(), 'Informe de incidencia ambiental', NULL, NULL, NULL, NULL);
INSERT INTO spac_p_plantdoc (id, id_tpdoc, fecha, nombre, expresion, mimetype, path, estacion) VALUES (143, 31, getDate(), 'Notificación liquidación act. clasificadas', NULL, NULL, NULL, NULL);
INSERT INTO spac_p_plantdoc (id, id_tpdoc, fecha, nombre, expresion, mimetype, path, estacion) VALUES (144, 37, getDate(), 'Requerimiento subs. act. clasificadas', NULL, NULL, NULL, NULL);
INSERT INTO spac_p_plantdoc (id, id_tpdoc, fecha, nombre, expresion, mimetype, path, estacion) VALUES (145, 38, getDate(), 'Notificación subs. act. clasificadas', NULL, NULL, NULL, NULL);
INSERT INTO spac_p_plantdoc (id, id_tpdoc, fecha, nombre, expresion, mimetype, path, estacion) VALUES (146, 54, getDate(), 'Publicación DOGA  act. clasificadas', NULL, NULL, NULL, NULL);
INSERT INTO spac_p_plantdoc (id, id_tpdoc, fecha, nombre, expresion, mimetype, path, estacion) VALUES (147, 25, getDate(), 'Informe jurídico act. clasificadas', NULL, NULL, NULL, NULL);
INSERT INTO spac_p_plantdoc (id, id_tpdoc, fecha, nombre, expresion, mimetype, path, estacion) VALUES (148, 26, getDate(), 'Notificación subsanación act. clasificadas', NULL, NULL, NULL, NULL);
INSERT INTO spac_p_plantdoc (id, id_tpdoc, fecha, nombre, expresion, mimetype, path, estacion) VALUES (149, 27, getDate(), 'Licencia  act. clasificadas', NULL, NULL, NULL, NULL);
INSERT INTO spac_p_plantdoc (id, id_tpdoc, fecha, nombre, expresion, mimetype, path, estacion) VALUES (150, 34, getDate(), 'Solicitud act. clasificadas', NULL, NULL, NULL, NULL);
INSERT INTO spac_p_plantdoc (id, id_tpdoc, fecha, nombre, expresion, mimetype, path, estacion) VALUES (151, 35, getDate(), 'Escrito interesado act. clasificadas', NULL, NULL, NULL, NULL);
INSERT INTO spac_p_plantdoc (id, id_tpdoc, fecha, nombre, expresion, mimetype, path, estacion) VALUES (152, 28, getDate(), 'Propuesta liquidación act. clasificadas', NULL, NULL, NULL, NULL);
INSERT INTO spac_p_plantdoc (id, id_tpdoc, fecha, nombre, expresion, mimetype, path, estacion) VALUES (153, 72, getDate(), 'Solicitud autorización act. clasificadas', NULL, NULL, NULL, NULL);
INSERT INTO spac_p_plantdoc (id, id_tpdoc, fecha, nombre, expresion, mimetype, path, estacion) VALUES (154, 74, getDate(), 'Acuerdo concesión lic. act. clasificadas', NULL, NULL, NULL, NULL);
INSERT INTO spac_p_plantdoc (id, id_tpdoc, fecha, nombre, expresion, mimetype, path, estacion) VALUES (155, 75, getDate(), 'Notificación concesión lic. act. clasificadas', NULL, NULL, NULL, NULL);
INSERT INTO spac_p_plantdoc (id, id_tpdoc, fecha, nombre, expresion, mimetype, path, estacion) VALUES (156, 76, getDate(), 'Comunicación al interesado', NULL, NULL, NULL, NULL);
INSERT INTO spac_p_plantdoc (id, id_tpdoc, fecha, nombre, expresion, mimetype, path, estacion) VALUES (157, 76, getDate(), 'Cédula de notificación', NULL, NULL, NULL, NULL);
INSERT INTO spac_p_plantdoc (id, id_tpdoc, fecha, nombre, expresion, mimetype, path, estacion) VALUES (158, 22, getDate(), 'Solicitud Subsanacion Actividades', NULL, NULL, NULL, NULL);
INSERT INTO spac_p_plantdoc (id, id_tpdoc, fecha, nombre, expresion, mimetype, path, estacion) VALUES (159, 16, getDate(), 'Notificación Resolución Actividades', NULL, NULL, NULL, NULL);
INSERT INTO spac_p_plantdoc (id, id_tpdoc, fecha, nombre, expresion, mimetype, path, estacion) VALUES (160, 17, getDate(), 'Resolución Actividades', NULL, NULL, NULL, NULL);
INSERT INTO spac_p_plantdoc (id, id_tpdoc, fecha, nombre, expresion, mimetype, path, estacion) VALUES (161, 30, getDate(), 'Carátula Actividades', NULL, NULL, NULL, NULL);
INSERT INTO spac_p_plantdoc (id, id_tpdoc, fecha, nombre, expresion, mimetype, path, estacion) VALUES (162, 23, getDate(), 'Informe técnico Actividades', NULL, NULL, NULL, NULL);
INSERT INTO spac_p_plantdoc (id, id_tpdoc, fecha, nombre, expresion, mimetype, path, estacion) VALUES (163, 31, getDate(), 'Notificación liquidación Actividades', NULL, NULL, NULL, NULL);
INSERT INTO spac_p_plantdoc (id, id_tpdoc, fecha, nombre, expresion, mimetype, path, estacion) VALUES (164, 37, getDate(), 'Requerimiento subsanación Actividades', NULL, NULL, NULL, NULL);
INSERT INTO spac_p_plantdoc (id, id_tpdoc, fecha, nombre, expresion, mimetype, path, estacion) VALUES (165, 38, getDate(), 'Notificación subsanación Actividades', NULL, NULL, NULL, NULL);
INSERT INTO spac_p_plantdoc (id, id_tpdoc, fecha, nombre, expresion, mimetype, path, estacion) VALUES (166, 25, getDate(), 'Informe jurídico Actividades', NULL, NULL, NULL, NULL);
INSERT INTO spac_p_plantdoc (id, id_tpdoc, fecha, nombre, expresion, mimetype, path, estacion) VALUES (167, 26, getDate(), 'Notificacion Subsanacion Actividades', NULL, NULL, NULL, NULL);
INSERT INTO spac_p_plantdoc (id, id_tpdoc, fecha, nombre, expresion, mimetype, path, estacion) VALUES (168, 27, getDate(), 'Licencia Actividades', NULL, NULL, NULL, NULL);
INSERT INTO spac_p_plantdoc (id, id_tpdoc, fecha, nombre, expresion, mimetype, path, estacion) VALUES (169, 34, getDate(), 'Solicitud Actividades', NULL, NULL, NULL, NULL);
INSERT INTO spac_p_plantdoc (id, id_tpdoc, fecha, nombre, expresion, mimetype, path, estacion) VALUES (170, 35, getDate(), 'Escrito interesado Actividades', NULL, NULL, NULL, NULL);
INSERT INTO spac_p_plantdoc (id, id_tpdoc, fecha, nombre, expresion, mimetype, path, estacion) VALUES (171, 28, getDate(), 'Propuesta liquidación Actividades', NULL, NULL, NULL, NULL);
INSERT INTO spac_p_plantdoc (id, id_tpdoc, fecha, nombre, expresion, mimetype, path, estacion) VALUES (172, 72, getDate(), 'Solicitud Autorizacion Actividades', NULL, NULL, NULL, NULL);
INSERT INTO spac_p_plantdoc (id, id_tpdoc, fecha, nombre, expresion, mimetype, path, estacion) VALUES (173, 74, getDate(), 'Decreto/Acuerdo Licencia Instalación', NULL, NULL, NULL, NULL);
INSERT INTO spac_p_plantdoc (id, id_tpdoc, fecha, nombre, expresion, mimetype, path, estacion) VALUES (174, 75, getDate(), 'Notificación Licencia Instalación', NULL, NULL, NULL, NULL);
INSERT INTO spac_p_plantdoc (id, id_tpdoc, fecha, nombre, expresion, mimetype, path, estacion) VALUES (175, 22, getDate(), 'Solicitud subsanación vado', NULL, NULL, NULL, NULL);
INSERT INTO spac_p_plantdoc (id, id_tpdoc, fecha, nombre, expresion, mimetype, path, estacion) VALUES (176, 16, getDate(), 'Notificación resolución vado', NULL, NULL, NULL, NULL);
INSERT INTO spac_p_plantdoc (id, id_tpdoc, fecha, nombre, expresion, mimetype, path, estacion) VALUES (177, 17, getDate(), 'Resolución vado', NULL, NULL, NULL, NULL);
INSERT INTO spac_p_plantdoc (id, id_tpdoc, fecha, nombre, expresion, mimetype, path, estacion) VALUES (178, 30, getDate(), 'Carátula vado', NULL, NULL, NULL, NULL);
INSERT INTO spac_p_plantdoc (id, id_tpdoc, fecha, nombre, expresion, mimetype, path, estacion) VALUES (179, 23, getDate(), 'Informe vado - policía local', NULL, NULL, NULL, NULL);
INSERT INTO spac_p_plantdoc (id, id_tpdoc, fecha, nombre, expresion, mimetype, path, estacion) VALUES (180, 31, getDate(), 'Notificación liquidación vado', NULL, NULL, NULL, NULL);
INSERT INTO spac_p_plantdoc (id, id_tpdoc, fecha, nombre, expresion, mimetype, path, estacion) VALUES (181, 26, getDate(), 'Notificación subsanación vado', NULL, NULL, NULL, NULL);
INSERT INTO spac_p_plantdoc (id, id_tpdoc, fecha, nombre, expresion, mimetype, path, estacion) VALUES (182, 34, getDate(), 'Solicitud vado', NULL, NULL, NULL, NULL);
INSERT INTO spac_p_plantdoc (id, id_tpdoc, fecha, nombre, expresion, mimetype, path, estacion) VALUES (183, 35, getDate(), 'Escrito interesado vado', NULL, NULL, NULL, NULL);
INSERT INTO spac_p_plantdoc (id, id_tpdoc, fecha, nombre, expresion, mimetype, path, estacion) VALUES (184, 28, getDate(), 'Propuesta liquidación vado', NULL, NULL, NULL, NULL);
INSERT INTO spac_p_plantdoc (id, id_tpdoc, fecha, nombre, expresion, mimetype, path, estacion) VALUES (185, 16, getDate(), 'Notificación resolución reclamación de tributos', NULL, NULL, NULL, NULL);
INSERT INTO spac_p_plantdoc (id, id_tpdoc, fecha, nombre, expresion, mimetype, path, estacion) VALUES (186, 17, getDate(), 'Resolución reclamación de tributos', NULL, NULL, NULL, NULL);
INSERT INTO spac_p_plantdoc (id, id_tpdoc, fecha, nombre, expresion, mimetype, path, estacion) VALUES (187, 23, getDate(), 'Informes técnicos', NULL, NULL, NULL, NULL);
INSERT INTO spac_p_plantdoc (id, id_tpdoc, fecha, nombre, expresion, mimetype, path, estacion) VALUES (188, 26, getDate(), 'Notificación solicitud de subsanación', NULL, NULL, NULL, NULL);
INSERT INTO spac_p_plantdoc (id, id_tpdoc, fecha, nombre, expresion, mimetype, path, estacion) VALUES (189, 22, getDate(), 'Solicitud subsanacion tarjeta estacionamiento', NULL, NULL, NULL, NULL);
INSERT INTO spac_p_plantdoc (id, id_tpdoc, fecha, nombre, expresion, mimetype, path, estacion) VALUES (190, 16, getDate(), 'Notificación resolución tarjeta estacionamiento', NULL, NULL, NULL, NULL);
INSERT INTO spac_p_plantdoc (id, id_tpdoc, fecha, nombre, expresion, mimetype, path, estacion) VALUES (191, 17, getDate(), 'Resolución tarjeta estacionamiento', NULL, NULL, NULL, NULL);
INSERT INTO spac_p_plantdoc (id, id_tpdoc, fecha, nombre, expresion, mimetype, path, estacion) VALUES (192, 30, getDate(), 'Carátula Tarjeta Estacionamiento', NULL, NULL, NULL, NULL);
INSERT INTO spac_p_plantdoc (id, id_tpdoc, fecha, nombre, expresion, mimetype, path, estacion) VALUES (193, 23, getDate(), 'Informe expedición tarjeta estacionamiento', NULL, NULL, NULL, NULL);
INSERT INTO spac_p_plantdoc (id, id_tpdoc, fecha, nombre, expresion, mimetype, path, estacion) VALUES (194, 24, getDate(), 'Petición informe EVO', NULL, NULL, NULL, NULL);
INSERT INTO spac_p_plantdoc (id, id_tpdoc, fecha, nombre, expresion, mimetype, path, estacion) VALUES (195, 32, getDate(), 'Aportacion documentación tarjeta estacionamiento', NULL, NULL, NULL, NULL);
INSERT INTO spac_p_plantdoc (id, id_tpdoc, fecha, nombre, expresion, mimetype, path, estacion) VALUES (196, 26, getDate(), 'Notificación subsanación tarjeta estacionamiento', NULL, NULL, NULL, NULL);
INSERT INTO spac_p_plantdoc (id, id_tpdoc, fecha, nombre, expresion, mimetype, path, estacion) VALUES (197, 34, getDate(), 'Solicitud tarjeta estacionamiento', NULL, NULL, NULL, NULL);
INSERT INTO spac_p_plantdoc (id, id_tpdoc, fecha, nombre, expresion, mimetype, path, estacion) VALUES (198, 35, getDate(), 'Escrito interesado tarjeta estacionamiento', NULL, NULL, NULL, NULL);


-- Actualizar la secuencia
UPDATE SPAC_SQ_SEQUENCES SET SEQUENCE_ID = 199 WHERE SEQUENCE_NAME = 'SPAC_SQ_ID_PPLANTILLAS';



--
-- Plantillas específicas
--

INSERT INTO spac_p_plantillas (id, id_p_plantdoc, id_pcd) VALUES (1, 51, 7);
INSERT INTO spac_p_plantillas (id, id_p_plantdoc, id_pcd) VALUES (2, 44, 7);
INSERT INTO spac_p_plantillas (id, id_p_plantdoc, id_pcd) VALUES (3, 55, 7);
INSERT INTO spac_p_plantillas (id, id_p_plantdoc, id_pcd) VALUES (4, 53, 7);
INSERT INTO spac_p_plantillas (id, id_p_plantdoc, id_pcd) VALUES (5, 52, 7);
INSERT INTO spac_p_plantillas (id, id_p_plantdoc, id_pcd) VALUES (6, 54, 7);
INSERT INTO spac_p_plantillas (id, id_p_plantdoc, id_pcd) VALUES (7, 56, 8);
INSERT INTO spac_p_plantillas (id, id_p_plantdoc, id_pcd) VALUES (8, 69, 8);
INSERT INTO spac_p_plantillas (id, id_p_plantdoc, id_pcd) VALUES (9, 73, 8);
INSERT INTO spac_p_plantillas (id, id_p_plantdoc, id_pcd) VALUES (10, 65, 8);
INSERT INTO spac_p_plantillas (id, id_p_plantdoc, id_pcd) VALUES (11, 71, 8);
INSERT INTO spac_p_plantillas (id, id_p_plantdoc, id_pcd) VALUES (12, 66, 8);
INSERT INTO spac_p_plantillas (id, id_p_plantdoc, id_pcd) VALUES (13, 68, 8);
INSERT INTO spac_p_plantillas (id, id_p_plantdoc, id_pcd) VALUES (14, 59, 8);
INSERT INTO spac_p_plantillas (id, id_p_plantdoc, id_pcd) VALUES (15, 57, 8);
INSERT INTO spac_p_plantillas (id, id_p_plantdoc, id_pcd) VALUES (16, 67, 8);
INSERT INTO spac_p_plantillas (id, id_p_plantdoc, id_pcd) VALUES (17, 70, 8);
INSERT INTO spac_p_plantillas (id, id_p_plantdoc, id_pcd) VALUES (18, 77, 9);
INSERT INTO spac_p_plantillas (id, id_p_plantdoc, id_pcd) VALUES (19, 75, 9);
INSERT INTO spac_p_plantillas (id, id_p_plantdoc, id_pcd) VALUES (20, 76, 9);
INSERT INTO spac_p_plantillas (id, id_p_plantdoc, id_pcd) VALUES (21, 78, 9);
INSERT INTO spac_p_plantillas (id, id_p_plantdoc, id_pcd) VALUES (22, 74, 9);
INSERT INTO spac_p_plantillas (id, id_p_plantdoc, id_pcd) VALUES (23, 80, 10);
INSERT INTO spac_p_plantillas (id, id_p_plantdoc, id_pcd) VALUES (24, 83, 10);
INSERT INTO spac_p_plantillas (id, id_p_plantdoc, id_pcd) VALUES (25, 84, 10);
INSERT INTO spac_p_plantillas (id, id_p_plantdoc, id_pcd) VALUES (26, 85, 10);
INSERT INTO spac_p_plantillas (id, id_p_plantdoc, id_pcd) VALUES (27, 86, 10);
INSERT INTO spac_p_plantillas (id, id_p_plantdoc, id_pcd) VALUES (28, 87, 10);
INSERT INTO spac_p_plantillas (id, id_p_plantdoc, id_pcd) VALUES (29, 120, 10);
INSERT INTO spac_p_plantillas (id, id_p_plantdoc, id_pcd) VALUES (30, 121, 10);
INSERT INTO spac_p_plantillas (id, id_p_plantdoc, id_pcd) VALUES (31, 91, 10);
INSERT INTO spac_p_plantillas (id, id_p_plantdoc, id_pcd) VALUES (32, 92, 10);
INSERT INTO spac_p_plantillas (id, id_p_plantdoc, id_pcd) VALUES (33, 93, 10);
INSERT INTO spac_p_plantillas (id, id_p_plantdoc, id_pcd) VALUES (34, 79, 10);
INSERT INTO spac_p_plantillas (id, id_p_plantdoc, id_pcd) VALUES (35, 90, 10);
INSERT INTO spac_p_plantillas (id, id_p_plantdoc, id_pcd) VALUES (36, 88, 10);
INSERT INTO spac_p_plantillas (id, id_p_plantdoc, id_pcd) VALUES (37, 89, 10);
INSERT INTO spac_p_plantillas (id, id_p_plantdoc, id_pcd) VALUES (38, 81, 10);
INSERT INTO spac_p_plantillas (id, id_p_plantdoc, id_pcd) VALUES (39, 82, 10);
INSERT INTO spac_p_plantillas (id, id_p_plantdoc, id_pcd) VALUES (40, 95, 10);
INSERT INTO spac_p_plantillas (id, id_p_plantdoc, id_pcd) VALUES (41, 96, 10);
INSERT INTO spac_p_plantillas (id, id_p_plantdoc, id_pcd) VALUES (42, 97, 10);
INSERT INTO spac_p_plantillas (id, id_p_plantdoc, id_pcd) VALUES (43, 98, 10);
INSERT INTO spac_p_plantillas (id, id_p_plantdoc, id_pcd) VALUES (44, 99, 10);
INSERT INTO spac_p_plantillas (id, id_p_plantdoc, id_pcd) VALUES (45, 100, 10);
INSERT INTO spac_p_plantillas (id, id_p_plantdoc, id_pcd) VALUES (46, 101, 10);
INSERT INTO spac_p_plantillas (id, id_p_plantdoc, id_pcd) VALUES (47, 102, 10);
INSERT INTO spac_p_plantillas (id, id_p_plantdoc, id_pcd) VALUES (48, 105, 10);
INSERT INTO spac_p_plantillas (id, id_p_plantdoc, id_pcd) VALUES (49, 103, 10);
INSERT INTO spac_p_plantillas (id, id_p_plantdoc, id_pcd) VALUES (50, 107, 10);
INSERT INTO spac_p_plantillas (id, id_p_plantdoc, id_pcd) VALUES (51, 106, 10);
INSERT INTO spac_p_plantillas (id, id_p_plantdoc, id_pcd) VALUES (52, 108, 10);
INSERT INTO spac_p_plantillas (id, id_p_plantdoc, id_pcd) VALUES (53, 122, 10);
INSERT INTO spac_p_plantillas (id, id_p_plantdoc, id_pcd) VALUES (54, 110, 10);
INSERT INTO spac_p_plantillas (id, id_p_plantdoc, id_pcd) VALUES (55, 109, 10);
INSERT INTO spac_p_plantillas (id, id_p_plantdoc, id_pcd) VALUES (56, 111, 10);
INSERT INTO spac_p_plantillas (id, id_p_plantdoc, id_pcd) VALUES (57, 112, 10);
INSERT INTO spac_p_plantillas (id, id_p_plantdoc, id_pcd) VALUES (58, 113, 10);
INSERT INTO spac_p_plantillas (id, id_p_plantdoc, id_pcd) VALUES (59, 114, 10);
INSERT INTO spac_p_plantillas (id, id_p_plantdoc, id_pcd) VALUES (60, 115, 10);
INSERT INTO spac_p_plantillas (id, id_p_plantdoc, id_pcd) VALUES (61, 116, 10);
INSERT INTO spac_p_plantillas (id, id_p_plantdoc, id_pcd) VALUES (62, 118, 10);
INSERT INTO spac_p_plantillas (id, id_p_plantdoc, id_pcd) VALUES (63, 117, 10);
INSERT INTO spac_p_plantillas (id, id_p_plantdoc, id_pcd) VALUES (64, 94, 10);
INSERT INTO spac_p_plantillas (id, id_p_plantdoc, id_pcd) VALUES (65, 130, 11);
INSERT INTO spac_p_plantillas (id, id_p_plantdoc, id_pcd) VALUES (66, 127, 11);
INSERT INTO spac_p_plantillas (id, id_p_plantdoc, id_pcd) VALUES (67, 129, 11);
INSERT INTO spac_p_plantillas (id, id_p_plantdoc, id_pcd) VALUES (68, 131, 11);
INSERT INTO spac_p_plantillas (id, id_p_plantdoc, id_pcd) VALUES (69, 132, 11);
INSERT INTO spac_p_plantillas (id, id_p_plantdoc, id_pcd) VALUES (70, 124, 11);
INSERT INTO spac_p_plantillas (id, id_p_plantdoc, id_pcd) VALUES (71, 133, 11);
INSERT INTO spac_p_plantillas (id, id_p_plantdoc, id_pcd) VALUES (72, 128, 11);
INSERT INTO spac_p_plantillas (id, id_p_plantdoc, id_pcd) VALUES (73, 125, 11);
INSERT INTO spac_p_plantillas (id, id_p_plantdoc, id_pcd) VALUES (74, 134, 11);
INSERT INTO spac_p_plantillas (id, id_p_plantdoc, id_pcd) VALUES (75, 123, 11);
INSERT INTO spac_p_plantillas (id, id_p_plantdoc, id_pcd) VALUES (76, 135, 11);
INSERT INTO spac_p_plantillas (id, id_p_plantdoc, id_pcd) VALUES (77, 126, 11);
INSERT INTO spac_p_plantillas (id, id_p_plantdoc, id_pcd) VALUES (78, 137, 12);
INSERT INTO spac_p_plantillas (id, id_p_plantdoc, id_pcd) VALUES (79, 148, 12);
INSERT INTO spac_p_plantillas (id, id_p_plantdoc, id_pcd) VALUES (80, 151, 12);
INSERT INTO spac_p_plantillas (id, id_p_plantdoc, id_pcd) VALUES (81, 140, 12);
INSERT INTO spac_p_plantillas (id, id_p_plantdoc, id_pcd) VALUES (82, 150, 12);
INSERT INTO spac_p_plantillas (id, id_p_plantdoc, id_pcd) VALUES (83, 141, 12);
INSERT INTO spac_p_plantillas (id, id_p_plantdoc, id_pcd) VALUES (84, 142, 12);
INSERT INTO spac_p_plantillas (id, id_p_plantdoc, id_pcd) VALUES (85, 147, 12);
INSERT INTO spac_p_plantillas (id, id_p_plantdoc, id_pcd) VALUES (86, 146, 12);
INSERT INTO spac_p_plantillas (id, id_p_plantdoc, id_pcd) VALUES (87, 157, 12);
INSERT INTO spac_p_plantillas (id, id_p_plantdoc, id_pcd) VALUES (88, 136, 12);
INSERT INTO spac_p_plantillas (id, id_p_plantdoc, id_pcd) VALUES (89, 153, 12);
INSERT INTO spac_p_plantillas (id, id_p_plantdoc, id_pcd) VALUES (90, 144, 12);
INSERT INTO spac_p_plantillas (id, id_p_plantdoc, id_pcd) VALUES (91, 145, 12);
INSERT INTO spac_p_plantillas (id, id_p_plantdoc, id_pcd) VALUES (92, 154, 12);
INSERT INTO spac_p_plantillas (id, id_p_plantdoc, id_pcd) VALUES (93, 155, 12);
INSERT INTO spac_p_plantillas (id, id_p_plantdoc, id_pcd) VALUES (94, 152, 12);
INSERT INTO spac_p_plantillas (id, id_p_plantdoc, id_pcd) VALUES (95, 139, 12);
INSERT INTO spac_p_plantillas (id, id_p_plantdoc, id_pcd) VALUES (96, 138, 12);
INSERT INTO spac_p_plantillas (id, id_p_plantdoc, id_pcd) VALUES (97, 143, 12);
INSERT INTO spac_p_plantillas (id, id_p_plantdoc, id_pcd) VALUES (98, 149, 12);
INSERT INTO spac_p_plantillas (id, id_p_plantdoc, id_pcd) VALUES (99, 158, 13);
INSERT INTO spac_p_plantillas (id, id_p_plantdoc, id_pcd) VALUES (100, 167, 13);
INSERT INTO spac_p_plantillas (id, id_p_plantdoc, id_pcd) VALUES (101, 170, 13);
INSERT INTO spac_p_plantillas (id, id_p_plantdoc, id_pcd) VALUES (102, 161, 13);
INSERT INTO spac_p_plantillas (id, id_p_plantdoc, id_pcd) VALUES (103, 169, 13);
INSERT INTO spac_p_plantillas (id, id_p_plantdoc, id_pcd) VALUES (104, 162, 13);
INSERT INTO spac_p_plantillas (id, id_p_plantdoc, id_pcd) VALUES (105, 166, 13);
INSERT INTO spac_p_plantillas (id, id_p_plantdoc, id_pcd) VALUES (106, 172, 13);
INSERT INTO spac_p_plantillas (id, id_p_plantdoc, id_pcd) VALUES (107, 164, 13);
INSERT INTO spac_p_plantillas (id, id_p_plantdoc, id_pcd) VALUES (108, 165, 13);
INSERT INTO spac_p_plantillas (id, id_p_plantdoc, id_pcd) VALUES (109, 171, 13);
INSERT INTO spac_p_plantillas (id, id_p_plantdoc, id_pcd) VALUES (110, 163, 13);
INSERT INTO spac_p_plantillas (id, id_p_plantdoc, id_pcd) VALUES (111, 160, 13);
INSERT INTO spac_p_plantillas (id, id_p_plantdoc, id_pcd) VALUES (112, 159, 13);
INSERT INTO spac_p_plantillas (id, id_p_plantdoc, id_pcd) VALUES (113, 168, 13);
INSERT INTO spac_p_plantillas (id, id_p_plantdoc, id_pcd) VALUES (114, 174, 13);
INSERT INTO spac_p_plantillas (id, id_p_plantdoc, id_pcd) VALUES (115, 173, 13);
INSERT INTO spac_p_plantillas (id, id_p_plantdoc, id_pcd) VALUES (116, 178, 14);
INSERT INTO spac_p_plantillas (id, id_p_plantdoc, id_pcd) VALUES (117, 182, 14);
INSERT INTO spac_p_plantillas (id, id_p_plantdoc, id_pcd) VALUES (118, 175, 14);
INSERT INTO spac_p_plantillas (id, id_p_plantdoc, id_pcd) VALUES (119, 181, 14);
INSERT INTO spac_p_plantillas (id, id_p_plantdoc, id_pcd) VALUES (120, 183, 14);
INSERT INTO spac_p_plantillas (id, id_p_plantdoc, id_pcd) VALUES (121, 179, 14);
INSERT INTO spac_p_plantillas (id, id_p_plantdoc, id_pcd) VALUES (122, 184, 14);
INSERT INTO spac_p_plantillas (id, id_p_plantdoc, id_pcd) VALUES (123, 177, 14);
INSERT INTO spac_p_plantillas (id, id_p_plantdoc, id_pcd) VALUES (124, 176, 14);
INSERT INTO spac_p_plantillas (id, id_p_plantdoc, id_pcd) VALUES (125, 180, 14);
INSERT INTO spac_p_plantillas (id, id_p_plantdoc, id_pcd) VALUES (126, 188, 15);
INSERT INTO spac_p_plantillas (id, id_p_plantdoc, id_pcd) VALUES (127, 186, 15);
INSERT INTO spac_p_plantillas (id, id_p_plantdoc, id_pcd) VALUES (128, 187, 15);
INSERT INTO spac_p_plantillas (id, id_p_plantdoc, id_pcd) VALUES (129, 185, 15);
INSERT INTO spac_p_plantillas (id, id_p_plantdoc, id_pcd) VALUES (130, 189, 16);
INSERT INTO spac_p_plantillas (id, id_p_plantdoc, id_pcd) VALUES (131, 196, 16);
INSERT INTO spac_p_plantillas (id, id_p_plantdoc, id_pcd) VALUES (132, 198, 16);
INSERT INTO spac_p_plantillas (id, id_p_plantdoc, id_pcd) VALUES (133, 195, 16);
INSERT INTO spac_p_plantillas (id, id_p_plantdoc, id_pcd) VALUES (134, 191, 16);
INSERT INTO spac_p_plantillas (id, id_p_plantdoc, id_pcd) VALUES (135, 190, 16);
INSERT INTO spac_p_plantillas (id, id_p_plantdoc, id_pcd) VALUES (136, 197, 16);
INSERT INTO spac_p_plantillas (id, id_p_plantdoc, id_pcd) VALUES (137, 192, 16);
INSERT INTO spac_p_plantillas (id, id_p_plantdoc, id_pcd) VALUES (138, 194, 16);
INSERT INTO spac_p_plantillas (id, id_p_plantdoc, id_pcd) VALUES (139, 193, 16);


-- Actualizar la secuencia
UPDATE SPAC_SQ_SEQUENCES SET SEQUENCE_ID = 140 WHERE SEQUENCE_NAME = 'SPAC_SQ_ID_P_PLANTILLAS';



--
-- Trámites asociados a los procedimientos
--

INSERT INTO spac_p_tramites (id, id_cttramite, id_tramite_bpm, id_pcd, id_fase, nombre, orden, obligatorio, libre, id_resp, id_resp_sec, id_pcd_sub) VALUES (40, 18, '40', 7, 18, 'Solicitud subsanación', 40, 1, 0, NULL, NULL, NULL);
INSERT INTO spac_p_tramites (id, id_cttramite, id_tramite_bpm, id_pcd, id_fase, nombre, orden, obligatorio, libre, id_resp, id_resp_sec, id_pcd_sub) VALUES (41, 20, '41', 7, 18, 'Actuaciones previas', 41, NULL, 0, NULL, NULL, NULL);
INSERT INTO spac_p_tramites (id, id_cttramite, id_tramite_bpm, id_pcd, id_fase, nombre, orden, obligatorio, libre, id_resp, id_resp_sec, id_pcd_sub) VALUES (42, 19, '42', 7, 19, 'Informe técnico', 42, 1, 0, NULL, NULL, NULL);
INSERT INTO spac_p_tramites (id, id_cttramite, id_tramite_bpm, id_pcd, id_fase, nombre, orden, obligatorio, libre, id_resp, id_resp_sec, id_pcd_sub) VALUES (43, 21, '43', 7, 19, 'Informe jurídico', 43, 1, 0, NULL, NULL, NULL);
INSERT INTO spac_p_tramites (id, id_cttramite, id_tramite_bpm, id_pcd, id_fase, nombre, orden, obligatorio, libre, id_resp, id_resp_sec, id_pcd_sub) VALUES (44, 10, '44', 7, 20, 'Notificación', 44, 1, 0, NULL, NULL, NULL);
INSERT INTO spac_p_tramites (id, id_cttramite, id_tramite_bpm, id_pcd, id_fase, nombre, orden, obligatorio, libre, id_resp, id_resp_sec, id_pcd_sub) VALUES (45, 22, '45', 7, 20, 'Resolución de la autorización', 45, 1, 0, NULL, NULL, NULL);
INSERT INTO spac_p_tramites (id, id_cttramite, id_tramite_bpm, id_pcd, id_fase, nombre, orden, obligatorio, libre, id_resp, id_resp_sec, id_pcd_sub) VALUES (46, 23, '46', 7, 20, 'Propuesta de liquidación', 46, NULL, 0, NULL, NULL, NULL);
INSERT INTO spac_p_tramites (id, id_cttramite, id_tramite_bpm, id_pcd, id_fase, nombre, orden, obligatorio, libre, id_resp, id_resp_sec, id_pcd_sub) VALUES (47, 24, '47', 7, 20, 'Licencia de acometida de agua', 47, NULL, 0, NULL, NULL, NULL);
INSERT INTO spac_p_tramites (id, id_cttramite, id_tramite_bpm, id_pcd, id_fase, nombre, orden, obligatorio, libre, id_resp, id_resp_sec, id_pcd_sub) VALUES (48, 3, '48', 7, 21, 'Archivo del expediente', 48, NULL, 0, NULL, NULL, NULL);
INSERT INTO spac_p_tramites (id, id_cttramite, id_tramite_bpm, id_pcd, id_fase, nombre, orden, obligatorio, libre, id_resp, id_resp_sec, id_pcd_sub) VALUES (49, 18, '49', 8, 22, 'Solicitud subsanación', 49, NULL, 0, NULL, NULL, NULL);
INSERT INTO spac_p_tramites (id, id_cttramite, id_tramite_bpm, id_pcd, id_fase, nombre, orden, obligatorio, libre, id_resp, id_resp_sec, id_pcd_sub) VALUES (50, 26, '50', 8, 22, 'Aportación documentación', 50, NULL, 0, NULL, NULL, NULL);
INSERT INTO spac_p_tramites (id, id_cttramite, id_tramite_bpm, id_pcd, id_fase, nombre, orden, obligatorio, libre, id_resp, id_resp_sec, id_pcd_sub) VALUES (51, 19, '51', 8, 23, 'Informe técnico', 51, NULL, 0, NULL, NULL, NULL);
INSERT INTO spac_p_tramites (id, id_cttramite, id_tramite_bpm, id_pcd, id_fase, nombre, orden, obligatorio, libre, id_resp, id_resp_sec, id_pcd_sub) VALUES (52, 21, '52', 8, 23, 'Informe jurídico', 52, NULL, 0, NULL, NULL, NULL);
INSERT INTO spac_p_tramites (id, id_cttramite, id_tramite_bpm, id_pcd, id_fase, nombre, orden, obligatorio, libre, id_resp, id_resp_sec, id_pcd_sub) VALUES (53, 25, '53', 8, 24, 'Resolución expediente', 53, NULL, 0, NULL, NULL, NULL);
INSERT INTO spac_p_tramites (id, id_cttramite, id_tramite_bpm, id_pcd, id_fase, nombre, orden, obligatorio, libre, id_resp, id_resp_sec, id_pcd_sub) VALUES (54, 27, '54', 8, 24, 'Emisión licencia', 54, NULL, 0, NULL, NULL, NULL);
INSERT INTO spac_p_tramites (id, id_cttramite, id_tramite_bpm, id_pcd, id_fase, nombre, orden, obligatorio, libre, id_resp, id_resp_sec, id_pcd_sub) VALUES (55, 3, '55', 8, 25, 'Archivo del expediente', 55, NULL, 0, NULL, NULL, NULL);
INSERT INTO spac_p_tramites (id, id_cttramite, id_tramite_bpm, id_pcd, id_fase, nombre, orden, obligatorio, libre, id_resp, id_resp_sec, id_pcd_sub) VALUES (56, 18, '56', 9, 26, 'Solicitud subsanación', 56, NULL, 0, NULL, NULL, NULL);
INSERT INTO spac_p_tramites (id, id_cttramite, id_tramite_bpm, id_pcd, id_fase, nombre, orden, obligatorio, libre, id_resp, id_resp_sec, id_pcd_sub) VALUES (57, 26, '57', 9, 27, 'Aportación documentación', 57, NULL, 0, NULL, NULL, NULL);
INSERT INTO spac_p_tramites (id, id_cttramite, id_tramite_bpm, id_pcd, id_fase, nombre, orden, obligatorio, libre, id_resp, id_resp_sec, id_pcd_sub) VALUES (58, 19, '58', 9, 27, 'Informe técnico', 58, NULL, 0, NULL, NULL, NULL);
INSERT INTO spac_p_tramites (id, id_cttramite, id_tramite_bpm, id_pcd, id_fase, nombre, orden, obligatorio, libre, id_resp, id_resp_sec, id_pcd_sub) VALUES (59, 28, '59', 9, 28, 'Emisión Certificado', 59, NULL, 0, NULL, NULL, NULL);
INSERT INTO spac_p_tramites (id, id_cttramite, id_tramite_bpm, id_pcd, id_fase, nombre, orden, obligatorio, libre, id_resp, id_resp_sec, id_pcd_sub) VALUES (60, 3, '60', 9, 29, 'Archivo del expediente', 60, NULL, 0, NULL, NULL, NULL);
INSERT INTO spac_p_tramites (id, id_cttramite, id_tramite_bpm, id_pcd, id_fase, nombre, orden, obligatorio, libre, id_resp, id_resp_sec, id_pcd_sub) VALUES (61, 19, '61', 10, 30, 'Informe técnico', 61, NULL, 0, NULL, NULL, NULL);
INSERT INTO spac_p_tramites (id, id_cttramite, id_tramite_bpm, id_pcd, id_fase, nombre, orden, obligatorio, libre, id_resp, id_resp_sec, id_pcd_sub) VALUES (62, 30, '62', 10, 30, 'Propuesta de inicio', 62, NULL, 0, NULL, NULL, NULL);
INSERT INTO spac_p_tramites (id, id_cttramite, id_tramite_bpm, id_pcd, id_fase, nombre, orden, obligatorio, libre, id_resp, id_resp_sec, id_pcd_sub) VALUES (63, 31, '63', 10, 30, 'Cláusulas administrativas', 63, NULL, 0, NULL, NULL, NULL);
INSERT INTO spac_p_tramites (id, id_cttramite, id_tramite_bpm, id_pcd, id_fase, nombre, orden, obligatorio, libre, id_resp, id_resp_sec, id_pcd_sub) VALUES (64, 32, '64', 10, 30, 'Prescripciones técnicas', 64, NULL, 0, NULL, NULL, NULL);
INSERT INTO spac_p_tramites (id, id_cttramite, id_tramite_bpm, id_pcd, id_fase, nombre, orden, obligatorio, libre, id_resp, id_resp_sec, id_pcd_sub) VALUES (65, 33, '65', 10, 30, 'Acta de replanteo previo', 65, NULL, 0, NULL, NULL, NULL);
INSERT INTO spac_p_tramites (id, id_cttramite, id_tramite_bpm, id_pcd, id_fase, nombre, orden, obligatorio, libre, id_resp, id_resp_sec, id_pcd_sub) VALUES (66, 34, '66', 10, 30, 'Informe de intervención', 66, NULL, 0, NULL, NULL, NULL);
INSERT INTO spac_p_tramites (id, id_cttramite, id_tramite_bpm, id_pcd, id_fase, nombre, orden, obligatorio, libre, id_resp, id_resp_sec, id_pcd_sub) VALUES (67, 35, '67', 10, 30, 'Informe de secretaría', 67, NULL, 0, NULL, NULL, NULL);
INSERT INTO spac_p_tramites (id, id_cttramite, id_tramite_bpm, id_pcd, id_fase, nombre, orden, obligatorio, libre, id_resp, id_resp_sec, id_pcd_sub) VALUES (68, 36, '68', 10, 30, 'Licencia de obra', 68, NULL, 0, NULL, NULL, NULL);
INSERT INTO spac_p_tramites (id, id_cttramite, id_tramite_bpm, id_pcd, id_fase, nombre, orden, obligatorio, libre, id_resp, id_resp_sec, id_pcd_sub) VALUES (69, 37, '69', 10, 30, 'Proyecto', 69, NULL, 0, NULL, NULL, NULL);
INSERT INTO spac_p_tramites (id, id_cttramite, id_tramite_bpm, id_pcd, id_fase, nombre, orden, obligatorio, libre, id_resp, id_resp_sec, id_pcd_sub) VALUES (70, 38, '70', 10, 30, 'Resolución aprobación expediente', 70, NULL, 0, NULL, NULL, NULL);
INSERT INTO spac_p_tramites (id, id_cttramite, id_tramite_bpm, id_pcd, id_fase, nombre, orden, obligatorio, libre, id_resp, id_resp_sec, id_pcd_sub) VALUES (71, 39, '71', 10, 30, 'Resolución nombramiento mesa', 71, NULL, 0, NULL, NULL, NULL);
INSERT INTO spac_p_tramites (id, id_cttramite, id_tramite_bpm, id_pcd, id_fase, nombre, orden, obligatorio, libre, id_resp, id_resp_sec, id_pcd_sub) VALUES (72, 19, '72', 10, 31, 'Informe técnico', 72, NULL, 0, NULL, NULL, NULL);
INSERT INTO spac_p_tramites (id, id_cttramite, id_tramite_bpm, id_pcd, id_fase, nombre, orden, obligatorio, libre, id_resp, id_resp_sec, id_pcd_sub) VALUES (73, 29, '73', 10, 31, 'Requerir subsanación', 73, NULL, 0, NULL, NULL, NULL);
INSERT INTO spac_p_tramites (id, id_cttramite, id_tramite_bpm, id_pcd, id_fase, nombre, orden, obligatorio, libre, id_resp, id_resp_sec, id_pcd_sub) VALUES (74, 40, '74', 10, 31, 'Petición de ofertas', 74, NULL, 0, NULL, NULL, NULL);
INSERT INTO spac_p_tramites (id, id_cttramite, id_tramite_bpm, id_pcd, id_fase, nombre, orden, obligatorio, libre, id_resp, id_resp_sec, id_pcd_sub) VALUES (75, 41, '75', 10, 31, 'Convocatoria mesa contratación', 75, NULL, 0, NULL, NULL, NULL);
INSERT INTO spac_p_tramites (id, id_cttramite, id_tramite_bpm, id_pcd, id_fase, nombre, orden, obligatorio, libre, id_resp, id_resp_sec, id_pcd_sub) VALUES (76, 42, '76', 10, 31, 'Certificado presentación de ofertas', 76, NULL, 0, NULL, NULL, NULL);
INSERT INTO spac_p_tramites (id, id_cttramite, id_tramite_bpm, id_pcd, id_fase, nombre, orden, obligatorio, libre, id_resp, id_resp_sec, id_pcd_sub) VALUES (77, 43, '77', 10, 31, 'Acta mesa', 77, NULL, 0, NULL, NULL, NULL);
INSERT INTO spac_p_tramites (id, id_cttramite, id_tramite_bpm, id_pcd, id_fase, nombre, orden, obligatorio, libre, id_resp, id_resp_sec, id_pcd_sub) VALUES (78, 44, '78', 10, 32, 'Resolución dirección de obra', 78, NULL, 0, NULL, NULL, NULL);
INSERT INTO spac_p_tramites (id, id_cttramite, id_tramite_bpm, id_pcd, id_fase, nombre, orden, obligatorio, libre, id_resp, id_resp_sec, id_pcd_sub) VALUES (79, 45, '79', 10, 32, 'Adjudicación provisional', 79, NULL, 0, NULL, NULL, NULL);
INSERT INTO spac_p_tramites (id, id_cttramite, id_tramite_bpm, id_pcd, id_fase, nombre, orden, obligatorio, libre, id_resp, id_resp_sec, id_pcd_sub) VALUES (80, 46, '80', 10, 32, 'Publicación', 80, NULL, 0, NULL, NULL, NULL);
INSERT INTO spac_p_tramites (id, id_cttramite, id_tramite_bpm, id_pcd, id_fase, nombre, orden, obligatorio, libre, id_resp, id_resp_sec, id_pcd_sub) VALUES (81, 47, '81', 10, 32, 'Adjudicación definitiva', 81, NULL, 0, NULL, NULL, NULL);
INSERT INTO spac_p_tramites (id, id_cttramite, id_tramite_bpm, id_pcd, id_fase, nombre, orden, obligatorio, libre, id_resp, id_resp_sec, id_pcd_sub) VALUES (82, 48, '82', 10, 32, 'Devolución de garantías', 82, NULL, 0, NULL, NULL, NULL);
INSERT INTO spac_p_tramites (id, id_cttramite, id_tramite_bpm, id_pcd, id_fase, nombre, orden, obligatorio, libre, id_resp, id_resp_sec, id_pcd_sub) VALUES (83, 49, '83', 10, 32, 'Contrato', 83, NULL, 0, NULL, NULL, NULL);
INSERT INTO spac_p_tramites (id, id_cttramite, id_tramite_bpm, id_pcd, id_fase, nombre, orden, obligatorio, libre, id_resp, id_resp_sec, id_pcd_sub) VALUES (84, 50, '84', 10, 32, 'Plan de seguridad', 84, NULL, 0, NULL, NULL, NULL);
INSERT INTO spac_p_tramites (id, id_cttramite, id_tramite_bpm, id_pcd, id_fase, nombre, orden, obligatorio, libre, id_resp, id_resp_sec, id_pcd_sub) VALUES (85, 19, '85', 10, 33, 'Informe técnico', 85, NULL, 0, NULL, NULL, NULL);
INSERT INTO spac_p_tramites (id, id_cttramite, id_tramite_bpm, id_pcd, id_fase, nombre, orden, obligatorio, libre, id_resp, id_resp_sec, id_pcd_sub) VALUES (86, 35, '86', 10, 33, 'Informe de secretaría', 86, NULL, 0, NULL, NULL, NULL);
INSERT INTO spac_p_tramites (id, id_cttramite, id_tramite_bpm, id_pcd, id_fase, nombre, orden, obligatorio, libre, id_resp, id_resp_sec, id_pcd_sub) VALUES (87, 48, '87', 10, 33, 'Devolución de garantías', 87, NULL, 0, NULL, NULL, NULL);
INSERT INTO spac_p_tramites (id, id_cttramite, id_tramite_bpm, id_pcd, id_fase, nombre, orden, obligatorio, libre, id_resp, id_resp_sec, id_pcd_sub) VALUES (88, 51, '88', 10, 33, 'Acta de comprobación de replanteo', 88, NULL, 0, NULL, NULL, NULL);
INSERT INTO spac_p_tramites (id, id_cttramite, id_tramite_bpm, id_pcd, id_fase, nombre, orden, obligatorio, libre, id_resp, id_resp_sec, id_pcd_sub) VALUES (89, 52, '89', 10, 33, 'Certificación', 89, NULL, 0, NULL, NULL, NULL);
INSERT INTO spac_p_tramites (id, id_cttramite, id_tramite_bpm, id_pcd, id_fase, nombre, orden, obligatorio, libre, id_resp, id_resp_sec, id_pcd_sub) VALUES (90, 53, '90', 10, 33, 'Prórroga', 90, NULL, 0, NULL, NULL, NULL);
INSERT INTO spac_p_tramites (id, id_cttramite, id_tramite_bpm, id_pcd, id_fase, nombre, orden, obligatorio, libre, id_resp, id_resp_sec, id_pcd_sub) VALUES (91, 54, '91', 10, 33, 'Paralización', 91, NULL, 0, NULL, NULL, NULL);
INSERT INTO spac_p_tramites (id, id_cttramite, id_tramite_bpm, id_pcd, id_fase, nombre, orden, obligatorio, libre, id_resp, id_resp_sec, id_pcd_sub) VALUES (92, 55, '92', 10, 33, 'Ampliación de plazos', 92, NULL, 0, NULL, NULL, NULL);
INSERT INTO spac_p_tramites (id, id_cttramite, id_tramite_bpm, id_pcd, id_fase, nombre, orden, obligatorio, libre, id_resp, id_resp_sec, id_pcd_sub) VALUES (93, 56, '93', 10, 33, 'Acta de recepción', 93, NULL, 0, NULL, NULL, NULL);
INSERT INTO spac_p_tramites (id, id_cttramite, id_tramite_bpm, id_pcd, id_fase, nombre, orden, obligatorio, libre, id_resp, id_resp_sec, id_pcd_sub) VALUES (94, 57, '94', 10, 33, 'Certificación final', 94, NULL, 0, NULL, NULL, NULL);
INSERT INTO spac_p_tramites (id, id_cttramite, id_tramite_bpm, id_pcd, id_fase, nombre, orden, obligatorio, libre, id_resp, id_resp_sec, id_pcd_sub) VALUES (95, 58, '95', 10, 33, 'Envío de información', 95, NULL, 0, NULL, NULL, NULL);
INSERT INTO spac_p_tramites (id, id_cttramite, id_tramite_bpm, id_pcd, id_fase, nombre, orden, obligatorio, libre, id_resp, id_resp_sec, id_pcd_sub) VALUES (96, 3, '96', 10, 34, 'Archivo del expediente', 96, NULL, 0, NULL, NULL, NULL);
INSERT INTO spac_p_tramites (id, id_cttramite, id_tramite_bpm, id_pcd, id_fase, nombre, orden, obligatorio, libre, id_resp, id_resp_sec, id_pcd_sub) VALUES (97, 20, '97', 11, 35, 'Actuaciones previas', 97, NULL, 0, NULL, NULL, NULL);
INSERT INTO spac_p_tramites (id, id_cttramite, id_tramite_bpm, id_pcd, id_fase, nombre, orden, obligatorio, libre, id_resp, id_resp_sec, id_pcd_sub) VALUES (98, 60, '98', 11, 35, 'Acuerdo incoación', 98, NULL, 0, NULL, NULL, NULL);
INSERT INTO spac_p_tramites (id, id_cttramite, id_tramite_bpm, id_pcd, id_fase, nombre, orden, obligatorio, libre, id_resp, id_resp_sec, id_pcd_sub) VALUES (99, 61, '99', 11, 35, 'Acuerdo no incoación', 99, NULL, 0, NULL, NULL, NULL);
INSERT INTO spac_p_tramites (id, id_cttramite, id_tramite_bpm, id_pcd, id_fase, nombre, orden, obligatorio, libre, id_resp, id_resp_sec, id_pcd_sub) VALUES (100, 12, '100', 11, 36, 'Propuesta de resolución', 100, NULL, 0, NULL, NULL, NULL);
INSERT INTO spac_p_tramites (id, id_cttramite, id_tramite_bpm, id_pcd, id_fase, nombre, orden, obligatorio, libre, id_resp, id_resp_sec, id_pcd_sub) VALUES (101, 19, '101', 11, 36, 'Informe técnico', 101, NULL, 0, NULL, NULL, NULL);
INSERT INTO spac_p_tramites (id, id_cttramite, id_tramite_bpm, id_pcd, id_fase, nombre, orden, obligatorio, libre, id_resp, id_resp_sec, id_pcd_sub) VALUES (102, 59, '102', 11, 36, 'Petición de informes', 102, NULL, 0, NULL, NULL, NULL);
INSERT INTO spac_p_tramites (id, id_cttramite, id_tramite_bpm, id_pcd, id_fase, nombre, orden, obligatorio, libre, id_resp, id_resp_sec, id_pcd_sub) VALUES (103, 62, '103', 11, 36, 'Medidas provisionales', 103, NULL, 0, NULL, NULL, NULL);
INSERT INTO spac_p_tramites (id, id_cttramite, id_tramite_bpm, id_pcd, id_fase, nombre, orden, obligatorio, libre, id_resp, id_resp_sec, id_pcd_sub) VALUES (104, 2, '104', 11, 36, 'Alegaciones', 104, NULL, 0, NULL, NULL, NULL);
INSERT INTO spac_p_tramites (id, id_cttramite, id_tramite_bpm, id_pcd, id_fase, nombre, orden, obligatorio, libre, id_resp, id_resp_sec, id_pcd_sub) VALUES (105, 63, '105', 11, 36, 'Actuaciones realizadas', 105, NULL, 0, NULL, NULL, NULL);
INSERT INTO spac_p_tramites (id, id_cttramite, id_tramite_bpm, id_pcd, id_fase, nombre, orden, obligatorio, libre, id_resp, id_resp_sec, id_pcd_sub) VALUES (106, 25, '106', 11, 37, 'Resolución expediente', 106, NULL, 0, NULL, NULL, NULL);
INSERT INTO spac_p_tramites (id, id_cttramite, id_tramite_bpm, id_pcd, id_fase, nombre, orden, obligatorio, libre, id_resp, id_resp_sec, id_pcd_sub) VALUES (107, 3, '107', 11, 38, 'Archivo del expediente', 107, NULL, 0, NULL, NULL, NULL);
INSERT INTO spac_p_tramites (id, id_cttramite, id_tramite_bpm, id_pcd, id_fase, nombre, orden, obligatorio, libre, id_resp, id_resp_sec, id_pcd_sub) VALUES (108, 18, '108', 12, 39, 'Solicitud subsanación', 108, NULL, 0, NULL, NULL, NULL);
INSERT INTO spac_p_tramites (id, id_cttramite, id_tramite_bpm, id_pcd, id_fase, nombre, orden, obligatorio, libre, id_resp, id_resp_sec, id_pcd_sub) VALUES (109, 26, '109', 12, 39, 'Aportación documentación', 109, NULL, 0, NULL, NULL, NULL);
INSERT INTO spac_p_tramites (id, id_cttramite, id_tramite_bpm, id_pcd, id_fase, nombre, orden, obligatorio, libre, id_resp, id_resp_sec, id_pcd_sub) VALUES (110, 19, '110', 12, 40, 'Informe técnico', 110, NULL, 0, NULL, NULL, NULL);
INSERT INTO spac_p_tramites (id, id_cttramite, id_tramite_bpm, id_pcd, id_fase, nombre, orden, obligatorio, libre, id_resp, id_resp_sec, id_pcd_sub) VALUES (111, 21, '111', 12, 40, 'Informe jurídico', 111, NULL, 0, NULL, NULL, NULL);
INSERT INTO spac_p_tramites (id, id_cttramite, id_tramite_bpm, id_pcd, id_fase, nombre, orden, obligatorio, libre, id_resp, id_resp_sec, id_pcd_sub) VALUES (112, 65, '112', 12, 40, 'Información pública', 112, NULL, 0, NULL, NULL, NULL);
INSERT INTO spac_p_tramites (id, id_cttramite, id_tramite_bpm, id_pcd, id_fase, nombre, orden, obligatorio, libre, id_resp, id_resp_sec, id_pcd_sub) VALUES (113, 2, '113', 12, 40, 'Alegaciones', 113, NULL, 0, NULL, NULL, NULL);
INSERT INTO spac_p_tramites (id, id_cttramite, id_tramite_bpm, id_pcd, id_fase, nombre, orden, obligatorio, libre, id_resp, id_resp_sec, id_pcd_sub) VALUES (114, 66, '114', 12, 40, 'Petición de autorizaciones sectoriales', 114, NULL, 0, NULL, NULL, NULL);
INSERT INTO spac_p_tramites (id, id_cttramite, id_tramite_bpm, id_pcd, id_fase, nombre, orden, obligatorio, libre, id_resp, id_resp_sec, id_pcd_sub) VALUES (115, 29, '115', 12, 40, 'Requerir subsanación', 115, NULL, 0, NULL, NULL, NULL);
INSERT INTO spac_p_tramites (id, id_cttramite, id_tramite_bpm, id_pcd, id_fase, nombre, orden, obligatorio, libre, id_resp, id_resp_sec, id_pcd_sub) VALUES (116, 67, '116', 12, 40, 'Concesión Licencia de Instalación', 116, NULL, 0, NULL, NULL, NULL);
INSERT INTO spac_p_tramites (id, id_cttramite, id_tramite_bpm, id_pcd, id_fase, nombre, orden, obligatorio, libre, id_resp, id_resp_sec, id_pcd_sub) VALUES (117, 64, '117', 12, 40, 'Propuesta liquidación', 117, NULL, 0, NULL, NULL, NULL);
INSERT INTO spac_p_tramites (id, id_cttramite, id_tramite_bpm, id_pcd, id_fase, nombre, orden, obligatorio, libre, id_resp, id_resp_sec, id_pcd_sub) VALUES (118, 26, '118', 12, 40, 'Aportación documentación', 118, NULL, 0, NULL, NULL, NULL);
INSERT INTO spac_p_tramites (id, id_cttramite, id_tramite_bpm, id_pcd, id_fase, nombre, orden, obligatorio, libre, id_resp, id_resp_sec, id_pcd_sub) VALUES (119, 25, '119', 12, 41, 'Resolución expediente', 119, NULL, 0, NULL, NULL, NULL);
INSERT INTO spac_p_tramites (id, id_cttramite, id_tramite_bpm, id_pcd, id_fase, nombre, orden, obligatorio, libre, id_resp, id_resp_sec, id_pcd_sub) VALUES (120, 27, '120', 12, 41, 'Emisión licencia', 120, NULL, 0, NULL, NULL, NULL);
INSERT INTO spac_p_tramites (id, id_cttramite, id_tramite_bpm, id_pcd, id_fase, nombre, orden, obligatorio, libre, id_resp, id_resp_sec, id_pcd_sub) VALUES (121, 3, '121', 12, 42, 'Archivo del expediente', 121, NULL, 0, NULL, NULL, NULL);
INSERT INTO spac_p_tramites (id, id_cttramite, id_tramite_bpm, id_pcd, id_fase, nombre, orden, obligatorio, libre, id_resp, id_resp_sec, id_pcd_sub) VALUES (122, 18, '122', 13, 43, 'Solicitud subsanación', 122, NULL, 0, NULL, NULL, NULL);
INSERT INTO spac_p_tramites (id, id_cttramite, id_tramite_bpm, id_pcd, id_fase, nombre, orden, obligatorio, libre, id_resp, id_resp_sec, id_pcd_sub) VALUES (123, 26, '123', 13, 43, 'Aportación documentación', 123, NULL, 0, NULL, NULL, NULL);
INSERT INTO spac_p_tramites (id, id_cttramite, id_tramite_bpm, id_pcd, id_fase, nombre, orden, obligatorio, libre, id_resp, id_resp_sec, id_pcd_sub) VALUES (124, 19, '124', 13, 44, 'Informe técnico', 124, NULL, 0, NULL, NULL, NULL);
INSERT INTO spac_p_tramites (id, id_cttramite, id_tramite_bpm, id_pcd, id_fase, nombre, orden, obligatorio, libre, id_resp, id_resp_sec, id_pcd_sub) VALUES (125, 21, '125', 13, 44, 'Informe jurídico', 125, NULL, 0, NULL, NULL, NULL);
INSERT INTO spac_p_tramites (id, id_cttramite, id_tramite_bpm, id_pcd, id_fase, nombre, orden, obligatorio, libre, id_resp, id_resp_sec, id_pcd_sub) VALUES (126, 66, '126', 13, 44, 'Petición de autorizaciones sectoriales', 126, NULL, 0, NULL, NULL, NULL);
INSERT INTO spac_p_tramites (id, id_cttramite, id_tramite_bpm, id_pcd, id_fase, nombre, orden, obligatorio, libre, id_resp, id_resp_sec, id_pcd_sub) VALUES (127, 29, '127', 13, 44, 'Requerir subsanación', 127, NULL, 0, NULL, NULL, NULL);
INSERT INTO spac_p_tramites (id, id_cttramite, id_tramite_bpm, id_pcd, id_fase, nombre, orden, obligatorio, libre, id_resp, id_resp_sec, id_pcd_sub) VALUES (128, 67, '128', 13, 44, 'Concesión Licencia de Instalación', 128, NULL, 0, NULL, NULL, NULL);
INSERT INTO spac_p_tramites (id, id_cttramite, id_tramite_bpm, id_pcd, id_fase, nombre, orden, obligatorio, libre, id_resp, id_resp_sec, id_pcd_sub) VALUES (129, 64, '129', 13, 44, 'Propuesta liquidación', 129, NULL, 0, NULL, NULL, NULL);
INSERT INTO spac_p_tramites (id, id_cttramite, id_tramite_bpm, id_pcd, id_fase, nombre, orden, obligatorio, libre, id_resp, id_resp_sec, id_pcd_sub) VALUES (130, 26, '130', 13, 44, 'Aportación documentación', 130, NULL, 0, NULL, NULL, NULL);
INSERT INTO spac_p_tramites (id, id_cttramite, id_tramite_bpm, id_pcd, id_fase, nombre, orden, obligatorio, libre, id_resp, id_resp_sec, id_pcd_sub) VALUES (131, 25, '131', 13, 45, 'Resolución expediente', 131, NULL, 0, NULL, NULL, NULL);
INSERT INTO spac_p_tramites (id, id_cttramite, id_tramite_bpm, id_pcd, id_fase, nombre, orden, obligatorio, libre, id_resp, id_resp_sec, id_pcd_sub) VALUES (132, 27, '132', 13, 45, 'Emisión licencia', 132, NULL, 0, NULL, NULL, NULL);
INSERT INTO spac_p_tramites (id, id_cttramite, id_tramite_bpm, id_pcd, id_fase, nombre, orden, obligatorio, libre, id_resp, id_resp_sec, id_pcd_sub) VALUES (133, 3, '133', 13, 46, 'Archivo del expediente', 133, NULL, 0, NULL, NULL, NULL);
INSERT INTO spac_p_tramites (id, id_cttramite, id_tramite_bpm, id_pcd, id_fase, nombre, orden, obligatorio, libre, id_resp, id_resp_sec, id_pcd_sub) VALUES (134, 18, '134', 14, 47, 'Solicitud subsanación', 134, NULL, 0, NULL, NULL, NULL);
INSERT INTO spac_p_tramites (id, id_cttramite, id_tramite_bpm, id_pcd, id_fase, nombre, orden, obligatorio, libre, id_resp, id_resp_sec, id_pcd_sub) VALUES (135, 26, '135', 14, 47, 'Aportación documentación', 135, NULL, 0, NULL, NULL, NULL);
INSERT INTO spac_p_tramites (id, id_cttramite, id_tramite_bpm, id_pcd, id_fase, nombre, orden, obligatorio, libre, id_resp, id_resp_sec, id_pcd_sub) VALUES (136, 26, '136', 14, 48, 'Aportación documentación', 136, NULL, 0, NULL, NULL, NULL);
INSERT INTO spac_p_tramites (id, id_cttramite, id_tramite_bpm, id_pcd, id_fase, nombre, orden, obligatorio, libre, id_resp, id_resp_sec, id_pcd_sub) VALUES (137, 19, '137', 14, 48, 'Informe técnico', 137, NULL, 0, NULL, NULL, NULL);
INSERT INTO spac_p_tramites (id, id_cttramite, id_tramite_bpm, id_pcd, id_fase, nombre, orden, obligatorio, libre, id_resp, id_resp_sec, id_pcd_sub) VALUES (138, 64, '138', 14, 48, 'Propuesta liquidación', 138, NULL, 0, NULL, NULL, NULL);
INSERT INTO spac_p_tramites (id, id_cttramite, id_tramite_bpm, id_pcd, id_fase, nombre, orden, obligatorio, libre, id_resp, id_resp_sec, id_pcd_sub) VALUES (139, 25, '139', 14, 49, 'Resolución expediente', 139, NULL, 0, NULL, NULL, NULL);
INSERT INTO spac_p_tramites (id, id_cttramite, id_tramite_bpm, id_pcd, id_fase, nombre, orden, obligatorio, libre, id_resp, id_resp_sec, id_pcd_sub) VALUES (140, 68, '140', 14, 49, 'Licencia de vado', 140, NULL, 0, NULL, NULL, NULL);
INSERT INTO spac_p_tramites (id, id_cttramite, id_tramite_bpm, id_pcd, id_fase, nombre, orden, obligatorio, libre, id_resp, id_resp_sec, id_pcd_sub) VALUES (141, 3, '141', 14, 50, 'Archivo del expediente', 141, NULL, 0, NULL, NULL, NULL);
INSERT INTO spac_p_tramites (id, id_cttramite, id_tramite_bpm, id_pcd, id_fase, nombre, orden, obligatorio, libre, id_resp, id_resp_sec, id_pcd_sub) VALUES (142, 18, '142', 15, 51, 'Solicitud subsanación', 142, 1, 0, NULL, NULL, NULL);
INSERT INTO spac_p_tramites (id, id_cttramite, id_tramite_bpm, id_pcd, id_fase, nombre, orden, obligatorio, libre, id_resp, id_resp_sec, id_pcd_sub) VALUES (143, 69, '143', 15, 52, 'Remisión al órgano competente', 143, NULL, 0, NULL, NULL, NULL);
INSERT INTO spac_p_tramites (id, id_cttramite, id_tramite_bpm, id_pcd, id_fase, nombre, orden, obligatorio, libre, id_resp, id_resp_sec, id_pcd_sub) VALUES (144, 70, '144', 15, 52, 'Emisión de informes', 144, 1, 0, NULL, NULL, NULL);
INSERT INTO spac_p_tramites (id, id_cttramite, id_tramite_bpm, id_pcd, id_fase, nombre, orden, obligatorio, libre, id_resp, id_resp_sec, id_pcd_sub) VALUES (145, 17, '145', 15, 53, 'Notificación resolución', 145, 1, 0, NULL, NULL, NULL);
INSERT INTO spac_p_tramites (id, id_cttramite, id_tramite_bpm, id_pcd, id_fase, nombre, orden, obligatorio, libre, id_resp, id_resp_sec, id_pcd_sub) VALUES (146, 71, '146', 15, 53, 'Resolución reclamación', 146, 1, 0, NULL, NULL, NULL);
INSERT INTO spac_p_tramites (id, id_cttramite, id_tramite_bpm, id_pcd, id_fase, nombre, orden, obligatorio, libre, id_resp, id_resp_sec, id_pcd_sub) VALUES (147, 3, '147', 15, 54, 'Archivo del expediente', 147, NULL, 0, NULL, NULL, NULL);
INSERT INTO spac_p_tramites (id, id_cttramite, id_tramite_bpm, id_pcd, id_fase, nombre, orden, obligatorio, libre, id_resp, id_resp_sec, id_pcd_sub) VALUES (148, 18, '148', 16, 55, 'Solicitud subsanación', 148, NULL, 0, NULL, NULL, NULL);
INSERT INTO spac_p_tramites (id, id_cttramite, id_tramite_bpm, id_pcd, id_fase, nombre, orden, obligatorio, libre, id_resp, id_resp_sec, id_pcd_sub) VALUES (149, 26, '149', 16, 55, 'Aportación documentación', 149, NULL, 0, NULL, NULL, NULL);
INSERT INTO spac_p_tramites (id, id_cttramite, id_tramite_bpm, id_pcd, id_fase, nombre, orden, obligatorio, libre, id_resp, id_resp_sec, id_pcd_sub) VALUES (150, 26, '150', 16, 56, 'Aportación documentación', 150, NULL, 0, NULL, NULL, NULL);
INSERT INTO spac_p_tramites (id, id_cttramite, id_tramite_bpm, id_pcd, id_fase, nombre, orden, obligatorio, libre, id_resp, id_resp_sec, id_pcd_sub) VALUES (151, 59, '151', 16, 56, 'Petición de informes', 151, NULL, 0, NULL, NULL, NULL);
INSERT INTO spac_p_tramites (id, id_cttramite, id_tramite_bpm, id_pcd, id_fase, nombre, orden, obligatorio, libre, id_resp, id_resp_sec, id_pcd_sub) VALUES (152, 19, '152', 16, 56, 'Informe técnico', 152, NULL, 0, NULL, NULL, NULL);
INSERT INTO spac_p_tramites (id, id_cttramite, id_tramite_bpm, id_pcd, id_fase, nombre, orden, obligatorio, libre, id_resp, id_resp_sec, id_pcd_sub) VALUES (153, 25, '153', 16, 57, 'Resolución expediente', 153, NULL, 0, NULL, NULL, NULL);
INSERT INTO spac_p_tramites (id, id_cttramite, id_tramite_bpm, id_pcd, id_fase, nombre, orden, obligatorio, libre, id_resp, id_resp_sec, id_pcd_sub) VALUES (154, 3, '154', 16, 58, 'Archivo del expediente', 154, NULL, 0, NULL, NULL, NULL);


-- Actualizar la secuencia
UPDATE SPAC_SQ_SEQUENCES SET SEQUENCE_ID = 154 WHERE SEQUENCE_NAME = 'SPAC_SQ_ID_PTRAMITES';