-----------------------------------
-- Actualización de v6.4 a v6.4.1
-----------------------------------


--
-- Información de versión
--
INSERT INTO spac_infosistema (id, codigo, valor, fecha_actualizacion) VALUES (nextval('spac_sq_id_infosistema'), 'VERSIONBD', '6.5', current_timestamp);
INSERT INTO spac_infosistema (id, codigo, valor, fecha_actualizacion) VALUES (nextval('spac_sq_id_infosistema'), 'VERSIONAPP', '6.5', current_timestamp);



--
-- Actualización del formulario de la entidad de Registros Entrada/Salida
-- para la selección de tipo de documento al anexar registro a trámite abierto
--
UPDATE spac_ct_aplicaciones SET frm_jsp = '<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ispac-util.tld" prefix="ispac" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c1" %>


<script>

		function show_SEARCH_SPAC_REGISTROS_ES_NREG(target, action, width, height) {
			registerType = document.defaultForm[''property(SPAC_REGISTROS_ES:TP_REG)''].value
			if (registerType == '''' || registerType == ''NINGUNO''){
				jAlert(''<bean:message key="registro.tipoRegistro.invalido"/>'', ''<bean:message key="common.alert"/>'', ''<bean:message key="common.message.ok"/>'' , ''<bean:message key="common.message.cancel"/>'');
				return false;
			}
			registerNumber = document.defaultForm[''property(SPAC_REGISTROS_ES:NREG)''].value
			if (registerNumber == ''''){
				jAlert(''<bean:message key="registro.numeroRegistro.vacio"/>'', ''<bean:message key="common.alert"/>'', ''<bean:message key="common.message.ok"/>'' , ''<bean:message key="common.message.cancel"/>'');
				return false;
			}

			action = action + ''&registerType=''+registerType;
			showFrame(target, action, width, height) ;
		}

</script>

<c1:set var="sicresConnectorClass" value="${ISPACConfiguration.SICRES_CONNECTOR_CLASS}" />
<c1:set var="_key"><bean:write name="defaultForm" property="entityApp.property(SPAC_REGISTROS_ES:ID)" /></c1:set>

<c1:set var="aux0"><bean:write name="defaultForm" property="entityApp.label(SPAC_REGISTROS_ES:SPAC_REGISTROS_ES)" /></c1:set><jsp:useBean id="aux0" type="java.lang.String"/>
<ispac:tabs>
<ispac:tab  title=''<%=aux0%>''><div id="dataBlock_SPAC_REGISTROS_ES" style="position: relative; height: 160px; width: 600px">

<html:hidden property="property(SPAC_REGISTROS_ES:ID_INTERESADO)"/>
<html:hidden property="property(SPAC_REGISTROS_ES:ID_TRAMITE)"/>
<html:hidden property="property(ID_TIPO_DOC)"/>

<div id="label_SPAC_REGISTROS_ES:TP_REG" style="position: absolute; top: 10px; left: 10px; width: 150px;" class="formsTitleB">
<bean:write name="defaultForm" property="entityApp.label(SPAC_REGISTROS_ES:TP_REG)" />:</div>
<div id="data_SPAC_REGISTROS_ES:TP_REG" style="position: absolute; top: 10px; left: 160px; width:100% ;" >
<nobr>

		<c:choose>
			<c:when test="${_key == ''''}">
				<ispac:htmlTextImageFrame property="property(SPAC_REGISTROS_ES:TP_REG)" readonly="true" readonlyTag="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="20" imageTabIndex="true" id="SEARCH_SPAC_REGISTROS_ES_TP_REG" target="workframe" action="selectValue.do?entity=SPAC_VLDTBL_TIPOREG" image="img/search-mg.gif" titleKeyLink="title.link.data.selection" imageDelete="img/borrar.gif" titleKeyImageDelete="title.delete.data.selection" styleClassDeleteLink="tdlink" confirmDeleteKey="msg.delete.data.selection" showDelete="false" showFrame="true" width="640" height="480" >
				<ispac:parameter name="SEARCH_SPAC_REGISTROS_ES_TP_REG" id="property(SPAC_REGISTROS_ES:TP_REG)" property="VALOR" />
				</ispac:htmlTextImageFrame>
			</c:when>
			<c:otherwise>
				<ispac:htmlText property="property(SPAC_REGISTROS_ES:TP_REG)"
					styleClass="input"
					size="20"
					readonly=''true''
					/>
			</c:otherwise>
		</c:choose>

</nobr>
</div>


<div id="label_SPAC_REGISTROS_ES:NREG" style="position: absolute; top: 10px; left: 310px; width: 150px;" class="formsTitleB">
<bean:write name="defaultForm" property="entityApp.label(SPAC_REGISTROS_ES:NREG)" />:</div>
<div id="data_SPAC_REGISTROS_ES:NREG" style="position: absolute; top: 10px; left: 430px; width:100% ;" >
		<c:choose>
			<c:when test="${!empty sicresConnectorClass && _key == ''''}">
				<script>
					function accept_SEARCH_SPAC_REGISTROS_ES_NREG(){
					 var element;
					 var elements;
					 var i;

					 element = document.getElementById(''waitOperationInProgress'');
					 if (element != null)
					 {
						// Deshabilitar el scroll
						document.body.style.overflow = "hidden";

						element.style.position = "absolute";

						element.style.height = document.body.scrollHeight + 1200;
						element.style.width = document.body.clientWidth + 1200;
						element.style.left = -600;
						element.style.top = -1000;

						element.style.display = "block";

						if (isIE())
						{
						 elements = document.getElementsByTagName("SELECT");

						 for (i = 0; i < elements.length; i++)
						 {
							elements[i].style.visibility = "hidden";
						 }
						}
					 }
					}

				</script>
				<ispac:htmlTextImageFrame
					property="property(SPAC_REGISTROS_ES:NREG)"
					id="SEARCH_SPAC_REGISTROS_ES_NREG"
					readonlyTag="false"
					styleClassLink="search"
					styleClassReadonly="inputReadOnly"
					styleClass="input"
					size="25"
					maxlength="16"
					action="selectTaskRegisterES.do"
					image="img/search-mg.gif"
					titleKeyLink="search.intray"
					inputField="SPAC_REGISTROS_ES:NREG"
					width="500"
					height="300"
					titleKeyImageDelete="forms.exp.title.delete.data.register"
					target="workframe"
					showFrame="true"
					readonly="false"
					showDelete="false"
					jsShowFrame="show_SEARCH_SPAC_REGISTROS_ES_NREG"
				>
					<ispac:parameter name="SEARCH_SPAC_REGISTROS_ES_NREG" id="property(SPAC_REGISTROS_ES:FREG)" property="FREG" />
					<ispac:parameter name="SEARCH_SPAC_REGISTROS_ES_NREG" id="property(SPAC_REGISTROS_ES:ASUNTO)" property="ASUNTO" />
					<ispac:parameter name="SEARCH_SPAC_REGISTROS_ES_NREG" id="property(SPAC_REGISTROS_ES:ID_INTERESADO)" property="IDTITULAR" />
					<ispac:parameter name="SEARCH_SPAC_REGISTROS_ES_NREG" id="property(SPAC_REGISTROS_ES:INTERESADO)" property="IDENTIDADTITULAR" />
					<ispac:parameter name="SEARCH_SPAC_REGISTROS_ES_NREG" id="property(SPAC_REGISTROS_ES:ID_TRAMITE)" property="ID_TRAMITE" />
					<ispac:parameter name="SEARCH_SPAC_REGISTROS_ES_NREG" id="property(SPAC_REGISTROS_ES:ORIGINO_EXPEDIENTE)" property="ORIGINO_EXPEDIENTE" />
					<ispac:parameter name="SEARCH_SPAC_REGISTROS_ES_NREG" id="property(ID_TIPO_DOC)" property="ID_TIPO_DOC" />
					<ispac:parameter name="SEARCH_SPAC_REGISTROS_ES_NREG" id="JAVASCRIPT" property="accept_SEARCH_SPAC_REGISTROS_ES_NREG" />
				</ispac:htmlTextImageFrame>
			</c:when>
			<c:otherwise>
				<ispac:htmlText property="property(SPAC_REGISTROS_ES:NREG)"
					styleClass="input" size="20"
					readonly=''true'' />
			</c:otherwise>
		</c:choose>
</div>
<div id="label_SPAC_REGISTROS_ES:FREG" style="position: absolute; top: 35px; left: 10px; width: 150px;" class="formsTitleB">
<bean:write name="defaultForm" property="entityApp.label(SPAC_REGISTROS_ES:FREG)" />:</div>
<div id="data_SPAC_REGISTROS_ES:FREG" style="position: absolute; top: 35px; left: 160px; width:100% ;" >
<nobr>
<ispac:htmlTextCalendar property="property(SPAC_REGISTROS_ES:FREG)" readonly="true" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="14" maxlength="10" image=''<%= buttoncalendar %>'' format="dd/mm/yyyy" enablePast="true" >
</ispac:htmlTextCalendar>
</nobr>
</div>
<div id="label_SPAC_REGISTROS_ES:ASUNTO" style="position: absolute; top: 60px; left: 10px; width: 150px;" class="formsTitleB">
<bean:write name="defaultForm" property="entityApp.label(SPAC_REGISTROS_ES:ASUNTO)" />:</div>
<div id="data_SPAC_REGISTROS_ES:ASUNTO" style="position: absolute; top: 60px; left: 160px; width:100% ;" >
<ispac:htmlTextarea property="property(SPAC_REGISTROS_ES:ASUNTO)" readonly="true" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" rows="2" cols="80" >
</ispac:htmlTextarea>
</div>
<div id="label_SPAC_REGISTROS_ES:INTERESADO" style="position: absolute; top: 100px; left: 10px; width: 150px;" class="formsTitleB">
<bean:write name="defaultForm" property="entityApp.label(SPAC_REGISTROS_ES:INTERESADO)" />:</div>
<div id="data_SPAC_REGISTROS_ES:INTERESADO" style="position: absolute; top: 100px; left: 160px; width:100% ;" >
<ispac:htmlText property="property(SPAC_REGISTROS_ES:INTERESADO)" readonly="true" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="80" maxlength="128" >
</ispac:htmlText>
</div>
<div id="label_SPAC_REGISTROS_ES:ORIGINO_EXPEDIENTE" style="position: absolute; top: 125px; left: 10px; width: 150px;" class="formsTitleB">
<bean:write name="defaultForm" property="entityApp.label(SPAC_REGISTROS_ES:ORIGINO_EXPEDIENTE)" />:</div>
<div id="data_SPAC_REGISTROS_ES:ORIGINO_EXPEDIENTE" style="position: absolute; top: 125px; left: 160px; width:100% ;" >
<nobr>
<ispac:htmlTextImageFrame property="property(SPAC_REGISTROS_ES:ORIGINO_EXPEDIENTE)" readonly="true" readonlyTag="true" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="5" imageTabIndex="true" id="SEARCH_SPAC_REGISTROS_ES_ORIGINO_EXPEDIENTE" target="workframe" action="selectValue.do?entity=SPAC_TBL_009" image="img/search-mg.gif" titleKeyLink="title.link.data.selection" imageDelete="img/borrar.gif" titleKeyImageDelete="title.delete.data.selection" styleClassDeleteLink="tdlink" confirmDeleteKey="msg.delete.data.selection" showDelete="true" showFrame="true" width="640" height="480" >
<ispac:parameter name="SEARCH_SPAC_REGISTROS_ES_ORIGINO_EXPEDIENTE" id="property(SPAC_REGISTROS_ES:ORIGINO_EXPEDIENTE)" property="VALOR" />
</ispac:htmlTextImageFrame>
</nobr>
</div>

<div id="label_SPAC_REGISTROS_ES:ID_TRAMITE" style="position: absolute; top: 150px; left: 10px; width: 150px;" class="formsTitleB">
<logic:notEmpty name="defaultForm" property="property(SPAC_REGISTROS_ES:ID_TRAMITE)">
	<bean:define id="_stageId" name="stageId"/>
	<c:url value="showTask.do" var="_link">
		<c:param name="stagePcdIdActual" value=''${requestScope.stagePcdId}''/>
		<c:param name="taskId"><bean:write name="defaultForm" property="entityApp.property(SPAC_REGISTROS_ES:ID_TRAMITE)"/></c:param>
		<%-- id en spac_dt_tramites --%>
		<c:param name="key"><bean:write name="defaultForm" property="entityApp.property(KEY)"/></c:param>
	</c:url>
	<a href=''<c:out value="${_link}"/>'' class="formaction">
		<nobr><bean:message key="registro.task.link"/></nobr>
	</a>
</logic:notEmpty>
</div>
</div>
</ispac:tab></ispac:tabs>

<script language=''JavaScript'' type=''text/javascript''><!--
	document.getElementById(''blockSave'').style.display=''none'';
	<%--
	Se permite eliminar el vinculo del apunto de registro con el expediente en los casos en los que el apunte de registro no anexa documentos, es decir, no incorpora
	documentos al expediente,ya que de esta manera no va a poder ser eliminado desde un documento al no crearse.
	--%>
	object = document.getElementById(''blockDelete'');
	if(object != null && object != ''undefined''){
		valueBlockDelete = ''none'';
		<logic:empty name="defaultForm" property="property(SPAC_REGISTROS_ES:ID_TRAMITE)">
			<logic:equal name="defaultForm" property="property(SPAC_REGISTROS_ES:ORIGINO_EXPEDIENTE)" value="NO">
				valueBlockDelete = ''block'';
			</logic:equal>
		</logic:empty>
		object.style.display=valueBlockDelete;
	}

//-->
</script>'
, frm_version = frm_version + 1
WHERE id = 6;