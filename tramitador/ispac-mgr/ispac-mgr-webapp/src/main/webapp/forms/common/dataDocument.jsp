<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/ispac-util.tld" prefix="ispac"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@page import='ieci.tdw.ispac.ispaclib.common.constants.NotifyStatesConstants'%>

<c:set var="sicresConnectorClass" value="${ISPACConfiguration.SICRES_CONNECTOR_CLASS}" />
<c:set var="numRegistro"><bean:write name="defaultForm" property="property(SPAC_DT_DOCUMENTOS:NREG)" /></c:set>


<script type="text/javascript" src='<ispac:rewrite href="../../dwr/interface/RegisterAPI.js"/>'></script>
<script type="text/javascript" src='<ispac:rewrite href="../../dwr/engine.js"/>'></script>
<script type="text/javascript" src='<ispac:rewrite href="../../dwr/util.js"/>'></script>
<script type="text/javascript" src='<ispac:rewrite href="../scripts/jquery-1.3.2.min.js"/>'></script>
<script type="text/javascript" src='<ispac:rewrite href="../scripts/jquery-ui-1.7.2.custom.min.js"/>'></script>
<script type="text/javascript" src='<ispac:rewrite href="../scripts/jquery.alerts.js"/>'></script>


<script language='JavaScript' type='text/javascript'>
//<!--

	//var idContenido="";

	function acceptRegistry() {

		setReadOnly(document.defaultForm.elements['property(SPAC_DT_DOCUMENTOS:NREG)']);

		//Deshabilitamos el enlace de registrar de salida
		$("#outputRegisterDiv").removeClass("visible");
		$("#outputRegisterDiv").addClass("oculto");

		hideInfo();
	}

	function cancelRegistry(){

		//document.defaultForm.elements[ 'property(SPAC_DT_DOCUMENTOS:NREG)' ].value = '';

		//Habilitamos el enlace de registrar de salida
		$("#outputRegisterDiv").removeClass("oculto");
		$("#outputRegisterDiv").addClass("visible");

		hideInfo();
		//idContenido="";
	}

	function errorHandler(message, exception) {
    	jAlert(message, '<bean:message key="common.alert"/>',  '<bean:message key="common.message.ok"/>' , '<bean:message key="common.message.cancel"/>');
    }

	$(document).ready(function() {

		// Establecer el gestor de errores
		dwr.engine.setErrorHandler(errorHandler);
	});


    function accept_DOCUMENT_SELECT_EXPEDIENT_THIRD_PARTY(){
      	 showFrame('workframe', 'getInfoThirdParty.do?nombre='+document.defaultForm.elements[ 'property(SPAC_DT_DOCUMENTOS:DESTINO)' ].value);
    }

	function delete_SEARCH_SPAC_DT_DOCUMENTOS_NREG_SALIDA() {
		delete_SEARCH_SPAC_DT_DOCUMENTOS_NREG() ;
	}

	function delete_SEARCH_SPAC_DT_DOCUMENTOS_NREG() {
		jConfirm('<bean:message key="msg.delete.data.register"/>', '<bean:message key="common.confirm"/>', '<bean:message key="common.message.ok"/>' , '<bean:message key="common.message.cancel"/>', function(r) {
			if(r){

				document.defaultForm.elements[ 'property(SPAC_DT_DOCUMENTOS:FREG)' ].value = '';

				document.defaultForm.elements[ 'property(SPAC_DT_DOCUMENTOS:DESTINO_ID)' ].value = '';
				document.defaultForm.elements[ 'property(SPAC_DT_DOCUMENTOS:DESTINO)' ].value = '';
				document.defaultForm.elements[ 'property(SPAC_DT_DOCUMENTOS:DESTINO_TIPO)' ].value = '';

				document.defaultForm.elements[ 'property(SPAC_DT_DOCUMENTOS:ORIGEN_ID)' ].value = '';
				document.defaultForm.elements[ 'property(SPAC_DT_DOCUMENTOS:ORIGEN)' ].value = '';
				document.defaultForm.elements[ 'property(SPAC_DT_DOCUMENTOS:ORIGEN_TIPO)' ].value = '';

				nreg = document.defaultForm.elements[ 'property(SPAC_DT_DOCUMENTOS:NREG)' ];
				nreg.value = '';
				// Habilitar el campo del numero de registro para poder introducir otro
			 	setNotReadOnly(nreg);
			 	nreg.focus();

			 	// Mostrar la accion para registrar de salida
				element = document.getElementById('outputRegisterDiv')
				if ((element != null) && (element != 'undefined')) {
					element.style.display = 'block';
				}
			}
		});
		ispac_needToConfirm = true;
	}

	function sure_(action){
		sure(action, '<bean:message key="ispac.action.document.delete.msg"/>','<bean:message key="common.confirm"/>','<bean:message key="common.message.ok"/>','<bean:message key="common.message.cancel"/>');
	}
//-->
</script>




<ispac:rewrite id="imgcalendar" href="img/calendar/" />
<ispac:rewrite id="jscalendar" href="../scripts/calendar.js" />
<ispac:rewrite id="buttoncalendar" href="img/calendar/calendarM.gif" />



<ispac:calendar-config imgDir='<%= imgcalendar %>'
	scriptFile='<%= jscalendar %>' />

<!-- Identificador del registro -->
<html:hidden property="key" />
<bean:define id="key" name="defaultForm" property="key" />

<c:set var="_entryPoint">
	<tiles:insert attribute="entryPoint" ignore="true" flush="false" />
</c:set>
<c:set var="_localReadonly">
	<tiles:insert attribute="localReadonly" ignore="true" flush="false" />
</c:set>

<%-- Comprobamos si el documento se creo en la fase activa,
	 en caso contrario se vera en solo lectura independientemente si esta asociado a Fase o a Tramite --%>
<c:choose>
	<c:when test="${!empty param.stagePcdId}">
		<c:set var="_stagePcdId">
			<c:out value="${param.stagePcdId}" />
		</c:set>
	</c:when>
	<c:when test="${!empty requestScope.stagePcdId}">
		<c:set var="_stagePcdId">
			<c:out value="${requestScope.stagePcdId}" />
		</c:set>
	</c:when>
	<c:otherwise>
		<c:set var="_stagePcdId">
			<c:out value="${requestScope['Params'].stagePcdId}" />
		</c:set>
	</c:otherwise>
</c:choose>
<jsp:useBean id="_stagePcdId" type="java.lang.String" />

<logic:notEqual name="defaultForm"
	property="property(SPAC_DT_DOCUMENTOS:ID_FASE_PCD)"
	value='<%=_stagePcdId%>'>
	<c:set var="_localReadonly">true</c:set>
</logic:notEqual>

<%-- Comprobamos si el documento se creo en la actividad activa,
	 en caso contrario se vera en solo lectura --%>
<c:choose>
	<c:when test="${!empty param.taskId}">
		<c:set var="_taskId">
			<c:out value="${param.taskId}" />
		</c:set>
	</c:when>
	<c:when test="${!empty requestScope.taskId}">
		<c:set var="_taskId">
			<c:out value="${requestScope.taskId}" />
		</c:set>
	</c:when>
	<c:otherwise>
		<c:set var="_taskId">
			<c:out value="${requestScope['Params'].taskId}" />
		</c:set>
	</c:otherwise>
</c:choose>
<jsp:useBean id="_taskId" type="java.lang.String" />

<logic:equal name="defaultForm"
	property="property(SPAC_DT_DOCUMENTOS:ID_TRAMITE)" value='<%=_taskId%>'>

	<c:choose>
		<c:when test="${!empty param.activityPcdId}">
			<c:set var="_activityPcdId">
				<c:out value="${param.activityPcdId}" />
			</c:set>
		</c:when>
		<c:when test="${!empty requestScope.activityPcdId}">
			<c:set var="_activityPcdId">
				<c:out value="${requestScope.activityPcdId}" />
			</c:set>
		</c:when>
		<c:otherwise>
			<c:set var="_activityPcdId">
				<c:out value="${requestScope['Params'].activityPcdId}" />
			</c:set>
		</c:otherwise>
	</c:choose>
	<jsp:useBean id="_activityPcdId" type="java.lang.String" />

	<logic:equal name="defaultForm"
		property="property(SPAC_DT_DOCUMENTOS:ID_FASE_PCD)"
		value='<%=_activityPcdId%>'>
		<c:set var="_localReadonly">false</c:set>
	</logic:equal>

</logic:equal>

<%-- Documentos asociados en la fase en sólo lectura en el subproceso --%>
<logic:equal name="defaultForm"
	property="property(SPAC_DT_DOCUMENTOS:ID_TRAMITE)" value='0'>
	<c:if test="${!empty _taskId}">
		<c:set var="_localReadonly">true</c:set>
	</c:if>
</logic:equal>

<jsp:useBean id="_localReadonly" type="java.lang.String" />

<%-- Activamos el boton de guardar si el valor de solo lectura es falso--%>
<logic:equal name="_localReadonly" value="false">

	<script>
		var blockSave = document.getElementById('blockSave');
		if (blockSave != null) {
			blockSave.style.display='block';
		}
	</script>
</logic:equal>

<%-- Calculamos a que esta asociado el documento, a tramite o a fase--%>
<c:set var="documentAsotiation">
	<logic:notEqual name="defaultForm"
		property="property(SPAC_DT_DOCUMENTOS:ID_TRAMITE)" value="0">Task</logic:notEqual>
	<logic:equal name="defaultForm"
		property="property(SPAC_DT_DOCUMENTOS:ID_TRAMITE)" value="0">Stage</logic:equal>
</c:set>
<c:set var="_verBorrarDocumento" value="0"/>
<jsp:useBean id="_verBorrarDocumento" type="java.lang.String"></jsp:useBean>
<logic:notEmpty name="defaultForm"
	property="property(SPAC_DT_DOCUMENTOS:ID)">

	<table cellspacing="0" cellpadding="0" align="center" width="90%">

		<tr>
			<td height="10px"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="10px" /></td>
		</tr>
		<tr>
			<td class="textbar">

				<table border="0" cellspacing="0" cellpadding="0" width="100%">

					<tr>
						<td class="textbar"><bean:message key="forms.tasks.document" />:</td>
						<td><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0"
							width="10px" /></td>
						<td class="text11"><nobr><bean:write name="defaultForm"
							property="property(SPAC_DT_DOCUMENTOS:NOMBRE)" /></nobr></td>
						<td><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0"
							width="10px" /></td>
						<td class="textbar" align="right" width="80%">

						<%-- Si el documento esta asociado a la fase no se muestra enlace para ir al tramite --%>
						<logic:equal name="documentAsotiation" value="Task">
							<logic:equal name="_entryPoint" value="Task">

								<c:url value="showTask.do" var="_link">
									<c:param name="taskId">
										<bean:write name="defaultForm"
											property="property(SPAC_DT_TRAMITES:ID_TRAM_EXP)" />
									</c:param>
									<c:param name="key">
										<bean:write name="defaultForm"
											property="property(SPAC_DT_TRAMITES:ID)" />
									</c:param>
									<c:param name="entity" value="7" />
									<c:if test="${!empty param.readonly}">
										<c:param name="readonly" value='${param.readonly}' />
									</c:if>
								</c:url>

								<a href='<c:out value="${_link}"/>' class="tdlink"><bean:message
									key="forms.tasks.return.document.list" /></a>

							</logic:equal>
						</logic:equal></td>
					</tr>

				</table>

			</td>
		</tr>
		<tr>
			<td width="100%" valign="bottom" height="5px"
				style="font-size:4px;">&nbsp;</td>
		</tr>
		<tr>
			<td valign="top">

			<table class="caja" cellspacing="0" cellpadding="0" width="100%">

				<tr>
					<td height="8px"><img src='<ispac:rewrite href="img/pixel.gif"/>'
						border="0" height="8px" /></td>
				</tr>
				<tr>
					<td>

					<table style="border:0px solid cyan;" cellspacing="0"
						cellpadding="0" align="center" width="97%">

						<tr>
							<td>

								<!-- ACCIONES SOBRE EL DOCUMENTO -->
								<table border="0" cellspacing="0" cellpadding="0" width="100%">

									<tr>
										<td colspan="3" align="right" class="formsTitleB"
											style="border:0px solid red">

										<%-- Identificador del documento--%>
										<c:set var="_document">
											<bean:write name="defaultForm"
												property="entityApp.property(SPAC_DT_DOCUMENTOS:ID)" />
										</c:set>
										<jsp:useBean id="_document" type="java.lang.String" />

										<%-- Tipo de registro --%>
										<c:set var="_tpReg">
											<bean:write name="defaultForm"
												property="property(SPAC_DT_DOCUMENTOS:TP_REG)" />
										</c:set>

										<!-- DOCUMENTO EDITABLE -->
										<%--
										<logic:equal value="false" property="readonly" name="defaultForm">
										--%>
										<logic:equal name="_localReadonly" value="false">

											<%-- Estado de firma --%>
											<c:set var="_signState">
												<bean:write name="defaultForm"
													property="property(SPAC_DT_DOCUMENTOS:ESTADOFIRMA)" />
											</c:set>

											<%-- Estado de Bloqueo --%>
											<c:set var="_lockState">
												<bean:write name="defaultForm"
													property="property(SPAC_DT_DOCUMENTOS:BLOQUEO)" />
											</c:set>

											<%-- Enlace de borrar documento a nivel de trámite --%>
											<logic:notEqual name="defaultForm"
												property="property(SPAC_DT_DOCUMENTOS:ID_TRAMITE)" value="0">
												<c:url value="deleteDocument.do" var="_link_deleteDocument">
													<c:param name="documentId">
														<bean:write name="defaultForm"
															property="property(SPAC_DT_DOCUMENTOS:ID)" />
													</c:param>
													<c:param name="keyId">
														<bean:write name="defaultForm"
															property="property(SPAC_DT_TRAMITES:ID)" />
													</c:param>
												</c:url>
											</logic:notEqual>

											<%-- Enlace de borrar documento a nivel de fase --%>
											<logic:equal name="defaultForm"
												property="property(SPAC_DT_DOCUMENTOS:ID_TRAMITE)" value="0">
												<c:url value="deleteDocument.do" var="_link_deleteDocument">
													<c:param name="documentId">
														<bean:write name="defaultForm"
															property="property(SPAC_DT_DOCUMENTOS:ID)" />
													</c:param>
												</c:url>
											</logic:equal>

											<!-- TIPO DE REGISTRO ASOCIADO AL DOCUMENTO -->
											<c:choose>
												<c:when test="${_tpReg == 'ENTRADA'}">
													<!-- DOCUMENTO ENTRADA -->
													<%--
													<logic:equal name="defaultForm" property="property(SPAC_DT_DOCUMENTOS:TP_REG)" value="ENTRADA">
													--%>

													<%-- Documento no bloqueado --%>
													<c:if
														test="${(empty _lockState) || (_lockState == appConstants.documentLockStates.UNLOCK)}">

														<table border="0" cellspacing="0" cellpadding="0">
															<tr>
																<td class="formsTitle">
																	<!-- SUSTITUIR DOCUMENTO -->
																	<logic:equal value="false" property="readonly" name="defaultForm">
																		<bean:message key="forms.tasks.replace.document" />:
																	</logic:equal>
																</td>
																<td><img src='<ispac:rewrite href="img/pixel.gif"/>'
																	border="0" width="10px" /></td>
																<td><ispac:linkframe id="SustituirDocumentoPlantilla"
																	target="workframe"
																	action="selectTemplateDocumentType.do"
																	titleKey="forms.tasks.generate.document.from.template"
																	showFrame="true"
																	inputField="property(SPAC_DT_DOCUMENTOS:ID_TPDOC)"
																	styleClass="tdlink" width="500" height="300"
																	needToConfirm="true">
																</ispac:linkframe></td>
																<td><img src='<ispac:rewrite href="img/pixel.gif"/>'
																	border="0" width="10px" /></td>
																<td><ispac:linkframe id="SustituirDocumentoAnexar"
																	target="workframe" action="attachFile.do"
																	titleKey="forms.tasks.generate.document.attach"
																	showFrame="true" inputField="" styleClass="tdlink"
																	width="500" height="300" needToConfirm="true">
																</ispac:linkframe></td>
																<td><img src='<ispac:rewrite href="img/pixel.gif"/>'
																	border="0" width="10px" /></td>
																<td><ispac:linkframe id="GenerarDocumentoEscanear"
																	target="workframe" action="scanFiles.do"
																	titleKey="forms.tasks.generate.document.scan"
																	showFrame="true" inputField="" styleClass="tdlink"
																	width="500" height="300" needToConfirm="true">
																</ispac:linkframe></td>
																<td><img src='<ispac:rewrite href="img/pixel.gif"/>'
																	border="0" width="10px" /></td>
																<td><ispac:linkframe id="GenerarDocumentoRepositorio"
																	target="workframe" action="uploadRepoFiles.do"
																	titleKey="forms.tasks.generate.document.from.repository"
																	showFrame="true" inputField="" styleClass="tdlink"
																	width="640" height="480" needToConfirm="true">
																</ispac:linkframe></td>
																<td><img src='<ispac:rewrite href="img/pixel.gif"/>'
																	border="0" width="10px" /></td>
															</tr>
															<tr>
																<td><img src='<ispac:rewrite href="img/pixel.gif"/>'
																	border="0" height="5px" /></td>
															</tr>
														</table>

													</c:if>

													<table border="0" cellspacing="0" cellpadding="0">
														<tr>

															<logic:equal name="_entryPoint" value="Task">

																<!-- DOCUMENTO FÍSICO CREADO -->
																<logic:notEmpty name="defaultForm"
																	property="property(SPAC_DT_DOCUMENTOS:INFOPAG)">

																	<!-- VISUALIZAR DOCUMENTO -->
																	<td class="formsTitleB"><ispac:showdocument
																		document='<%=_document%>'
																		message="forms.tasks.view.file" styleClass="tdlink"
																		image="" /></td>
																	<td><img src='<ispac:rewrite href="img/pixel.gif"/>'
																		border="0" width="10px" /></td>

																</logic:notEmpty>

															</logic:equal>

															<!-- BORRAR DOCUMENTO -->
															<%-- Documento no bloqueado --%>
															<c:if test="${(empty _lockState) || (_lockState != appConstants.documentLockStates.TOTAL_LOCK)}">

																<c:set var="_verBorrarDocumento" value="1"/>

															</c:if>

														</tr>
														<tr>
															<td><img src='<ispac:rewrite href="img/pixel.gif"/>'
																border="0" height="5px" /></td>
														</tr>
													</table>

												<!-- FIN DOCUMENTO ENTRADA -->
												</c:when>
												<%--
												</logic:equal>
												--%>

												<c:when test="${_tpReg == 'SALIDA'}">
												<!-- DOCUMENTO SALIDA -->
													<%--
													<logic:equal name="defaultForm" property="property(SPAC_DT_DOCUMENTOS:TP_REG)" value="SALIDA">
													--%>

													<!-- DOCUMENTO FÍSICO CREADO -->
													<logic:notEmpty name="defaultForm"
														property="property(SPAC_DT_DOCUMENTOS:INFOPAG)">

														<c:choose>
														<c:when test="${empty numRegistro || empty sicresConnectorClass}">

															<!-- DOCUMENTO NO REGISTRADO -->
															<c:if
																test="${(empty _lockState) || (_lockState == appConstants.documentLockStates.UNLOCK)}">

																<table border="0" cellspacing="0" cellpadding="0">
																	<tr>
																		<td class="formsTitle">
																			<!-- SUSTITUIR DOCUMENTO -->
																			<logic:equal value="false" property="readonly" name="defaultForm">
																				<bean:message key="forms.tasks.replace.document" />:
																			</logic:equal>
																		</td>
																		<td><img src='<ispac:rewrite href="img/pixel.gif"/>'
																			border="0" width="10px" /></td>
																		<td><ispac:linkframe id="SustituirDocumentoPlantilla"
																			target="workframe"
																			action="selectTemplateDocumentType.do"
																			titleKey="forms.tasks.generate.document.from.template"
																			showFrame="true"
																			inputField="property(SPAC_DT_DOCUMENTOS:ID_TPDOC)"
																			styleClass="tdlink" width="500" height="300"
																			needToConfirm="true">
																		</ispac:linkframe></td>
																		<td><img src='<ispac:rewrite href="img/pixel.gif"/>'
																			border="0" width="10px" /></td>
																		<td><ispac:linkframe id="SustituirDocumentoAnexar"
																			target="workframe" action="attachFile.do"
																			titleKey="forms.tasks.generate.document.attach"
																			showFrame="true" inputField="" styleClass="tdlink"
																			width="500" height="300" needToConfirm="true">
																		</ispac:linkframe></td>
																		<td><img src='<ispac:rewrite href="img/pixel.gif"/>'
																			border="0" width="10px" /></td>
																		<td><ispac:linkframe id="GenerarDocumentoEscanear"
																			target="workframe" action="scanFiles.do"
																			titleKey="forms.tasks.generate.document.scan"
																			showFrame="true" inputField="" styleClass="tdlink"
																			width="500" height="300" needToConfirm="true">
																		</ispac:linkframe></td>
																		<td><img src='<ispac:rewrite href="img/pixel.gif"/>'
																			border="0" width="10px" /></td>
																		<td><ispac:linkframe id="GenerarDocumentoRepositorio"
																			target="workframe" action="uploadRepoFiles.do"
																			titleKey="forms.tasks.generate.document.from.repository"
																			showFrame="true" inputField="" styleClass="tdlink"
																			width="640" height="480" needToConfirm="true">
																		</ispac:linkframe></td>
																		<td><img src='<ispac:rewrite href="img/pixel.gif"/>'
																			border="0" width="10px" /></td>
																	</tr>
																	<tr>
																		<td><img src='<ispac:rewrite href="img/pixel.gif"/>'
																			border="0" height="5px" /></td>
																	</tr>
																</table>

															</c:if>

															<table border="0" cellspacing="0" cellpadding="0">
																<tr>

																	<%-- Si el documento no tiene ningun bloqueo permitimos ediatarlo, en caso contrario solo se permite consultar --%>
																	<c:choose>

																		<c:when
																			test="${(empty _lockState) || (_lockState == appConstants.documentLockStates.UNLOCK)}">

																			<!-- EDITAR DOCUMENTO -->
																			<td class="formsTitleB"><ispac:getdocument
																				document='<%= key.toString() %>' styleClass="tdlink"
																				message="forms.tasks.edit.file" /></td>
																			<td><img src='<ispac:rewrite href="img/pixel.gif"/>'
																				border="0" width="10px" /></td>

																		</c:when>

																		<c:otherwise>

																			<!-- VISUALIZAR DOCUMENTO -->
																			<td class="formsTitleB"><logic:equal
																				name="_entryPoint" value="Task">

																				<ispac:showdocument document='<%=_document%>'
																					message="forms.tasks.view.file" styleClass="tdlink"
																					image="" />

																			</logic:equal></td>
																			<td><img src='<ispac:rewrite href="img/pixel.gif"/>'
																				border="0" width="10px" /></td>

																		</c:otherwise>

																	</c:choose>

																	<!-- BORRAR DOCUMENTO -->
																	<%-- Documento no bloqueado --%>
																	<c:if test="${(empty _lockState) || (_lockState != appConstants.documentLockStates.TOTAL_LOCK)}">

																		<c:set var="_verBorrarDocumento" value="1"/>

																	</c:if>

																</tr>
																<tr>
																	<td><img src='<ispac:rewrite href="img/pixel.gif"/>'
																		border="0" height="5px" /></td>
																</tr>
															</table>

														</c:when>
														<c:otherwise>
															<!-- DOCUMENTO REGISTRADO -->
															<table border="0" cellspacing="0" cellpadding="0">
																<tr>
																	<td class="formsTitleB">
																		<logic:equal name="_entryPoint" value="Task">
																			<!-- VISUALIZAR DOCUMENTO -->
																			<ispac:showdocument document='<%=_document%>'
																				message="forms.tasks.view.file" styleClass="tdlink"
																				image="" />
																		</logic:equal>
																	</td>
																</tr>
															</table>
														</c:otherwise>
														</c:choose>

													</logic:notEmpty>
													<!-- FIN DOCUMENTO FÍSICO CREADO -->

													<!-- DOCUMENTO ??? -->

													<logic:empty name="defaultForm"
														property="property(SPAC_DT_DOCUMENTOS:INFOPAG)">

														<c:if
															test="${(empty _lockState) || (_lockState == appConstants.documentLockStates.UNLOCK)}">

															<!-- SUSTITUIR DOCUMENTO -->
															<table border="0" cellspacing="0" cellpadding="0">
																<tr>
																	<td class="formsTitle">
																		<!-- SUSTITUIR DOCUMENTO -->
																		<logic:equal value="false" property="readonly" name="defaultForm">
																			<bean:message key="forms.tasks.replace.document" />:
																		</logic:equal>
																	</td>
																	<td><img src='<ispac:rewrite href="img/pixel.gif"/>'
																		border="0" width="10px" /></td>
																	<td><ispac:linkframe id="SustituirDocumentoPlantilla"
																		target="workframe"
																		action="selectTemplateDocumentType.do"
																		titleKey="forms.tasks.generate.document.from.template"
																		showFrame="true"
																		inputField="property(SPAC_DT_DOCUMENTOS:ID_TPDOC)"
																		styleClass="tdlink" width="500" height="300"
																		needToConfirm="true">
																	</ispac:linkframe></td>
																	<td><img src='<ispac:rewrite href="img/pixel.gif"/>'
																		border="0" width="10px" /></td>
																	<td><ispac:linkframe id="SustituirDocumentoAnexar"
																		target="workframe" action="attachFile.do"
																		titleKey="forms.tasks.generate.document.attach"
																		showFrame="true" inputField="" styleClass="tdlink"
																		width="500" height="300" needToConfirm="true">
																	</ispac:linkframe></td>
																	<td><img src='<ispac:rewrite href="img/pixel.gif"/>'
																		border="0" width="10px" /></td>
																	<td><ispac:linkframe id="GenerarDocumentoEscanear"
																		target="workframe" action="scanFiles.do"
																		titleKey="forms.tasks.generate.document.scan"
																		showFrame="true" inputField="" styleClass="tdlink"
																		width="500" height="300" needToConfirm="true">
																	</ispac:linkframe></td>
																	<td><img src='<ispac:rewrite href="img/pixel.gif"/>'
																		border="0" width="10px" /></td>
																	<td><ispac:linkframe id="GenerarDocumentoRepositorio"
																		target="workframe" action="uploadRepoFiles.do"
																		titleKey="forms.tasks.generate.document.from.repository"
																		showFrame="true" inputField="" styleClass="tdlink"
																		width="640" height="480" needToConfirm="true">
																	</ispac:linkframe></td>
																	<td><img src='<ispac:rewrite href="img/pixel.gif"/>'
																		border="0" width="10px" /></td>
																</tr>
																<tr>
																	<td><img src='<ispac:rewrite href="img/pixel.gif"/>'
																		border="0" height="5px" /></td>
																</tr>
															</table>

														</c:if>

														<!-- BORRAR DOCUMENTO -->
														<%-- Documento no bloqueado --%>
														<c:if
															test="${(empty _lockState) || (_lockState != appConstants.documentLockStates.TOTAL_LOCK)}">
															<c:set var="_verBorrarDocumento" value="1"/>

														</c:if>

													</logic:empty>
													<!-- FIN DOCUMENTO ??? -->

												<!-- FIN DOCUMENTO SALIDA -->
												</c:when>
												<%--
												</logic:equal>
												--%>

												<c:otherwise>
												<!-- DOCUMENTO DE OTRO TIPO ??? -->
													<%--
													<logic:empty name="defaultForm" property="property(SPAC_DT_DOCUMENTOS:TP_REG)">
													--%>

													<!-- DOCUMENTO FIRMADO -->
													<c:if test="${(_signState != appConstants.signStates.SIN_FIRMA) && (_signState != appConstants.signStates.PENDIENTE_FIRMA)}">
														<!-- VISUALIZAR DOCUMENTO -->
														<table border="0" cellspacing="0" cellpadding="0">
															<tr>
																<td class="formsTitleB"><ispac:showdocument
																	document='<%=_document%>'
																	message="forms.tasks.view.file" styleClass="tdlink"
																	image="" />
																</td>
																<td><img src='<ispac:rewrite href="img/pixel.gif"/>'
																	border="0" width="10px" />
																</td>
															</tr>
														</table>
													</c:if>

													<c:if test="${(empty _lockState) || (_lockState == appConstants.documentLockStates.UNLOCK)}">

														<!-- SUSTITUIR DOCUMENTO -->
														<table border="0" cellspacing="0" cellpadding="0">
															<tr>
																<td class="formsTitle">
																	<!-- SUSTITUIR DOCUMENTO -->
																	<logic:equal value="false" property="readonly" name="defaultForm">
																		<bean:message key="forms.tasks.replace.document" />:
																	</logic:equal>
																</td>
																<td><img src='<ispac:rewrite href="img/pixel.gif"/>'
																	border="0" width="10px" /></td>
																<td><ispac:linkframe id="SustituirDocumentoPlantilla"
																	target="workframe"
																	action="selectTemplateDocumentType.do"
																	titleKey="forms.tasks.generate.document.from.template"
																	showFrame="true"
																	inputField="property(SPAC_DT_DOCUMENTOS:ID_TPDOC)"
																	styleClass="tdlink" width="500" height="300"
																	needToConfirm="true">
																</ispac:linkframe></td>
																<td><img src='<ispac:rewrite href="img/pixel.gif"/>'
																	border="0" width="10px" /></td>
																<td><ispac:linkframe id="SustituirDocumentoAnexar"
																	target="workframe" action="attachFile.do"
																	titleKey="forms.tasks.generate.document.attach"
																	showFrame="true" inputField="" styleClass="tdlink"
																	width="500" height="300" needToConfirm="true">
																</ispac:linkframe></td>
																<td><img src='<ispac:rewrite href="img/pixel.gif"/>'
																	border="0" width="10px" /></td>
																<td><ispac:linkframe id="GenerarDocumentoEscanear"
																	target="workframe" action="scanFiles.do"
																	titleKey="forms.tasks.generate.document.scan"
																	showFrame="true" inputField="" styleClass="tdlink"
																	width="500" height="300" needToConfirm="true">
																</ispac:linkframe></td>
																<td><img src='<ispac:rewrite href="img/pixel.gif"/>'
																	border="0" width="10px" /></td>
																<td><ispac:linkframe id="GenerarDocumentoRepositorio"
																	target="workframe" action="uploadRepoFiles.do"
																	titleKey="forms.tasks.generate.document.from.repository"
																	showFrame="true" inputField="" styleClass="tdlink"
																	width="640" height="480" needToConfirm="true">
																</ispac:linkframe></td>
																<td><img src='<ispac:rewrite href="img/pixel.gif"/>'
																	border="0" width="10px" /></td>
															</tr>
															<tr>
																<td><img src='<ispac:rewrite href="img/pixel.gif"/>'
																	border="0" height="5px" /></td>
															</tr>
														</table>

														<!-- DOCUMENTO FÍSICO CREADO -->
														<logic:notEmpty name="defaultForm"
															property="property(SPAC_DT_DOCUMENTOS:INFOPAG)">

															<table border="0" cellspacing="0" cellpadding="0">
																<tr>
																	<!-- EDITAR DOCUMENTO -->
																	<td class="formsTitleB"><ispac:getdocument
																		document='<%= key.toString() %>' styleClass="tdlink"
																		message="forms.tasks.edit.file" /></td>
																	<td><img src='<ispac:rewrite href="img/pixel.gif"/>'
																		border="0" width="10px" /></td>
																</tr>
																<tr>
																	<td><img src='<ispac:rewrite href="img/pixel.gif"/>'
																		border="0" height="5px" /></td>
																</tr>
															</table>

														</logic:notEmpty>

													</c:if>

													<!-- BORRAR DOCUMENTO -->
													<%-- Documento no bloqueado --%>
													<c:if test="${(empty _lockState) || (_lockState != appConstants.documentLockStates.TOTAL_LOCK)}">

														<c:set var="_verBorrarDocumento" value="1"/>

													</c:if>

												</c:otherwise>
												<%--
												</logic:empty>
												--%>
											</c:choose>
											<!-- FIN DOCUMENTO DE OTRO TIPO ??? -->
											<!-- FIN TIPO DE REGISTRO ASOCIADO AL DOCUMENTO -->

										</logic:equal> <!-- FIN DOCUMENTO EDITABLE --> <!-- SÓLO LECTURA -->
										<%--
														<logic:equal value="true" property="readonly" name="defaultForm">
														--%> <logic:equal name="_localReadonly" value="true">

											<!-- DOCUMENTO FÍSICO CREADO -->
											<logic:notEmpty name="defaultForm"
												property="property(SPAC_DT_DOCUMENTOS:INFOPAG)">

												<table border="0" cellspacing="0" cellpadding="0">
													<tr>
														<!-- VISUALIZAR DOCUMENTO -->
														<td class="formsTitleB"><%--
																			<ispac:getdocument document='<%= key.toString() %>'
																						   readonly="true"
																						   styleClass="tdlink"
																						   message="forms.tasks.view.file"/>
																			--%> <logic:equal name="_entryPoint" value="Task">

															<ispac:showdocument document='<%=_document%>'
																message="forms.tasks.view.file" styleClass="tdlink"
																image="" />

														</logic:equal></td>
														<td><img src='<ispac:rewrite href="img/pixel.gif"/>'
															border="0" width="10px" /></td>
													</tr>
													<tr>
														<td><img src='<ispac:rewrite href="img/pixel.gif"/>'
															border="0" height="5px" /></td>
													</tr>
												</table>

											</logic:notEmpty>

										</logic:equal> <!-- FIN SÓLO LECTURA --></td>
									</tr>
									<tr>
										<td colspan="3" style="border-bottom: 1px DOTTED #5C65A0;"><img
											src='<ispac:rewrite href="img/pixel.gif"/>' border="0"
											height="5px" /></td>
									</tr>

								</table>
								<!-- FIN ACCIONES SOBRE EL DOCUMENTO -->

							</td>
						</tr>
						<tr>
							<td colspan="3"><img src='<ispac:rewrite href="img/pixel.gif"/>'
								border="0" height="8px" /></td>
						</tr>
						<tr>
							<td>

								<!-- INFORMACION DEL DOCUMENTO -->
								<table border="0" cellspacing="0" cellpadding="0" width="100%">

									<tr>
										<!-- Descripcion -->
										<td height="20" class="formsTitleB" valign="top"
											width="160px"><nobr><bean:message
											key="documento.etiqueta.descripcion" />:</nobr></td>
										<td colspan="3" height="20"><ispac:htmlText
											property="property(SPAC_DT_DOCUMENTOS:DESCRIPCION)"
											readonly='<%=new Boolean(_localReadonly).booleanValue()%>'
											propertyReadonly="readonly" styleClass="input"
											styleClassReadonly="inputReadOnly" size="98" maxlength="250" />
										</td>
									</tr>

									<tr>
										<td colspan="4"><img
											src='<ispac:rewrite href="img/pixel.gif"/>' border="0"
											height="4px" /></td>
									</tr>

									<tr>
										<!-- Fecha Aprobacion -->
										<td height="20" class="formsTitleB" width="160px"><nobr><bean:message
											key="documento.etiqueta.fecha.aprobacion" />:</nobr></td>
										<td height="20" colspan="3"><nobr> <ispac:htmlTextCalendar
											property="property(SPAC_DT_DOCUMENTOS:FAPROBACION)"
											readonly='<%=new Boolean(_localReadonly).booleanValue()%>'
											propertyReadonly="readonly" styleClass="input"
											styleClassReadonly="inputReadOnly" size="12" maxlength="10"
											image='<%= buttoncalendar %>' format="dd/mm/yyyy"
											enablePast="true" /> </nobr></td>
									</tr>

									<tr>
										<td colspan="4"><img
											src='<ispac:rewrite href="img/pixel.gif"/>' border="0"
											height="4px" /></td>
									</tr>

									<tr>
										<td colspan="4" height="4px"
											style="font-size:4px; border-bottom:0px dotted #5C65A0;">&nbsp;</td>
									</tr>

									<tr>
										<td colspan="4"><img
											src='<ispac:rewrite href="img/pixel.gif"/>' border="0"
											height="4px" /></td>
									</tr>

									<%-- NOTIFICACION --%>
									<%-- Sólo cuando el documento es de salida --%>
									<c:if test="${_tpReg == 'SALIDA'}">

										<%--
											En el caso de que el tipo de dirección sea TELEMATICA y la notificación no se haya realizado
											mostramos un boton para Iniciar Notificacion
										--%>
										<c:set var="_tipoDireccion">
											<bean:write name="defaultForm"
												property="property(SPAC_DT_INTERVINIENTES:TIPODIRECCIONINTERESADO)" />
										</c:set>

										<c:set var="_estadoNotificacion">
											<bean:write name="defaultForm"
												property="property(SPAC_DT_DOCUMENTOS:ESTADONOTIFICACION)" />
										</c:set>
										<c:set var="_destinoId">
											<bean:write name="defaultForm"
												property="property(SPAC_DT_DOCUMENTOS:DESTINO_ID)" />
										</c:set>

										<c:set var="_nreg">
											<bean:write name="defaultForm"
												property="property(SPAC_DT_DOCUMENTOS:NREG)" />
										</c:set>

										<c:if test="${ empty _estadoNotificacion }">
										<c:set var="_estadoNotificacion" ><%= NotifyStatesConstants.UNRESOLVED %></c:set>
										</c:if>


										<%--  //TODO Sacar estos valores de constantes --%>
										<c:if test="${_tipoDireccion == 'T' && (empty _estadoNotificacion || _estadoNotificacion == appConstants.notifyStatesConstants.UNRESOLVED) && (not empty _nreg) }">

											<tr>
												<td colspan="4" style="text-align:right">

													<table border="0" cellspacing="0" cellpadding="0">
														<tr>
															<!-- INICIAR NOTIFICACION -->



															<td class="formsTitleB">
																<ispac:rewrite id="initNotificacion"
																				action="initNotification.do" />
																			<a class="tdlink"
																				onclick="javascript:ispac_needToConfirm=false;"
																				href="javascript:confirmAction('<%=initNotificacion%>', '<bean:message key="notificacion.telematica.init.msg"/>', '<bean:message key="common.confirm"/>',  '<bean:message key="common.message.ok"/>' , '<bean:message key="common.message.cancel"/>');">
																			<bean:message key="init.notificacion" /> </a>

														</td>
															<td><img src='<ispac:rewrite href="img/pixel.gif"/>'
																border="0" width="10px" /></td>
														</tr>
													</table>

												</td>
											</tr>

										</c:if>

										<tr>
											<td colspan="4" height="4px"
												style="font-size:4px; border-bottom:1px dotted #5C65A0;">&nbsp;</td>
										</tr>

										<tr>
											<td colspan="4"><img
												src='<ispac:rewrite href="img/pixel.gif"/>' border="0"
												height="4px" /></td>
										</tr>

										<tr>



										<!-- Tipo Notificacion -->
											<td height="20" class="formsTitleB" width="160px"><nobr><bean:message
												key="documento.etiqueta.tipo.notificacion" />:</nobr></td>

											<c:if test="${ empty _destinoId}">

												<c:if test="${_tipoDireccion == 'T'}">
												<td colspan="3" height="20">
													<input type="text"  id="tipoDireccionT" style="display:block" styleClass="inputReadOnly" size="20"  class="inputReadOnly" readonly="true" value='<bean:message
														key="forms.exp.tipoDir.telematica" />' >

													</input>

													<input type="text" id="tipoDireccionP" style="display:none" styleClass="inputReadOnly" size="20" readonly="true" class="inputReadOnly" value='<bean:message
														key="forms.exp.tipoDir.postal" />'>

													</input>
													<div id="tipoDireccionVacio"  style="display:none">

														<input type="text"  styleClass="inputReadOnly" size="20"  class="inputReadOnly" readonly="true" value="" >
													</div>

													<div id="tipoDireccionTercero" style="display:none">
												<html:text
												property="property(THIRD_PARTY:TIPO_DIRECCION_TELEMATICA_SUSTITUTO)"
												styleClass="inputReadOnly" size="20" readonly="true" />
												</div>
												</td>
												</c:if>


												<c:if test="${_tipoDireccion == 'P'}">
												<td colspan="3" height="20">
													<input type="text" id="tipoDireccionP" style="display:block" styleClass="inputReadOnly" size="20" readonly="true" class="inputReadOnly" value='<bean:message
														key="forms.exp.tipoDir.postal" />'>

													</input>

													<input type="text"  id="tipoDireccionT"  style="display:none" styleClass="inputReadOnly" size="20"  class="inputReadOnly" readonly="true" value='<bean:message
														key="forms.exp.tipoDir.telematica" />' >

													</input>
													<div id="tipoDireccionVacio"  style="display:none">

													<input type="text"   styleClass="inputReadOnly" size="20"  class="inputReadOnly" readonly="true" value="" >
													</div>


													<div id="tipoDireccionTercero" style="display:none">
												<html:text
												property="property(THIRD_PARTY:TIPO_DIRECCION_TELEMATICA_SUSTITUTO)"
												styleClass="inputReadOnly" size="20" readonly="true" />
												</div>
													</td>
												</c:if>

												<c:if test="${ empty _destinoId && _tipoDireccion != 'P' && _tipoDireccion != 'T'}">

													<td height="20" width="220px">
													<div id="tipoDireccionVacio"  style="display:block">

														<html:text
															property="property(SPAC_DT_INTERVINIENTES:TIPODIRECCIONINTERESADO)"
															styleClass="inputReadOnly" size="20" readonly="true"  />
													</div>

													<div id="tipoDireccionTercero" style="display:none">
													<html:text
													property="property(THIRD_PARTY:TIPO_DIRECCION_TELEMATICA_SUSTITUTO)"
													styleClass="inputReadOnly" size="20" readonly="true" />
													</div>


													<input type="text" id="tipoDireccionP" style="display:none" styleClass="inputReadOnly" size="20" readonly="true" class="inputReadOnly" value='<bean:message
														key="forms.exp.tipoDir.postal" />'>

													</input>

													<input type="text"  id="tipoDireccionT"  style="display:none" styleClass="inputReadOnly" size="20"  class="inputReadOnly" readonly="true" value='<bean:message
														key="forms.exp.tipoDir.telematica" />' >

													</input>

												</td>
											</c:if>

											</c:if>
											<c:if test="${ not empty _destinoId }">
											<td height="20" width="220px">
											<div id="tipoDireccionTercero" style="display:block">
											<html:text
												property="property(THIRD_PARTY:TIPO_DIRECCION_TELEMATICA_SUSTITUTO)"
												styleClass="inputReadOnly" size="20" readonly="true" />
												</div>



													<input type="text" id="tipoDireccionP" style="display:none" styleClass="inputReadOnly" size="20" readonly="true" class="inputReadOnly" value='<bean:message
														key="forms.exp.tipoDir.postal" />'>

													</input>

													<input type="text"  id="tipoDireccionT"  style="display:none" styleClass="inputReadOnly" size="20"  class="inputReadOnly" readonly="true" value='<bean:message
														key="forms.exp.tipoDir.telematica" />' >

													</input>
													</td>
											</c:if>


										</tr>

										<tr>
											<td colspan="4"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="4px" /></td>
										</tr>

										<tr>

											<!-- Estado Notificacion -->
											<td height="20" class="formsTitleB" width="120px"><nobr><bean:message
												key="documento.etiqueta.estado.notificacion" />:</nobr></td>
											<td height="20">
											<jsp:useBean id="_estadoNotificacion" type="java.lang.String"></jsp:useBean>
											<c:set var="_estadoNotificacionProperty" ><%= _estadoNotificacion + "_ESTADO_NOTIF" %></c:set>
											<jsp:useBean id="_estadoNotificacionProperty" type="java.lang.String"></jsp:useBean>


											<c:if test="${ empty _destinoId  && _tipoDireccion != 'P' && _tipoDireccion != 'T'}">

												<div id="estadoNotificacionVacio" style="display: block">

												<input type="text" styleClass="inputReadOnly" size="20" readonly="true" class="inputReadOnly" value="">
												</div>
												<div id="estadoNotificacionP" style="display: none">
												<ispac:htmlTextImageFrame property="property(ESTADONOTIFICACION_SPAC_TBL_006:SUSTITUTO)"
																		  readonly="true"
																		  readonlyTag="false"
																		  propertyReadonly="readonly"
																		  styleClass="input"
																		  styleClassReadonly="inputReadOnly"
																		  size="34"
																	  	  id="SEARCH_SPAC_DT_TRAMITES_ESTADONOTIFICACION"
																		  target="workframe"
																	  	  action="selectSubstitute.do?entity=SPAC_TBL_006"
																	  	  image="img/search-mg.gif"
																	  	  titleKeyLink="documento.etiqueta.estado.notificacion"
																	  	  showFrame="true">
													<ispac:parameter name="SEARCH_SPAC_DT_TRAMITES_ESTADONOTIFICACION" id="property(SPAC_DT_DOCUMENTOS:ESTADONOTIFICACION)" property="VALOR" />

			              							<ispac:parameter name="SEARCH_SPAC_DT_TRAMITES_ESTADONOTIFICACION" id="property(ESTADONOTIFICACION_SPAC_TBL_006:SUSTITUTO)" property="SUSTITUTO"/>
												</ispac:htmlTextImageFrame>
												</div>
												<input type="text" id="estadoNotificacionT" style="display: none" styleClass="inputReadOnly" size="20" readonly="true" class="inputReadOnly" value='<bean:message
												key='<%= _estadoNotificacionProperty %>' />'>
												</c:if>


												<!--<c:if test="${_tipoDireccion == 'P'}">-->


												<c:if test="${_tipoDireccion == 'P' }">
													<html:hidden property="property(SPAC_DT_DOCUMENTOS:ESTADONOTIFICACION)"/>
													<div id="estadoNotificacionP" style="display: block">
													<ispac:htmlTextImageFrame property="property(ESTADONOTIFICACION_SPAC_TBL_006:SUSTITUTO)"
																			  readonly="true"
																			  readonlyTag="false"
																			  propertyReadonly="readonly"
																			  styleClass="input"
																			  styleClassReadonly="inputReadOnly"
																			  size="34"
																		  	  id="SEARCH_SPAC_DT_TRAMITES_ESTADONOTIFICACION"
																			  target="workframe"
																		  	  action="selectSubstitute.do?entity=SPAC_TBL_006"
																		  	  image="img/search-mg.gif"
																		  	  titleKeyLink="documento.etiqueta.estado.notificacion"
																		  	  showFrame="true">
														<ispac:parameter name="SEARCH_SPAC_DT_TRAMITES_ESTADONOTIFICACION" id="property(SPAC_DT_DOCUMENTOS:ESTADONOTIFICACION)" property="VALOR" />

				              							<ispac:parameter name="SEARCH_SPAC_DT_TRAMITES_ESTADONOTIFICACION" id="property(ESTADONOTIFICACION_SPAC_TBL_006:SUSTITUTO)" property="SUSTITUTO"/>
													</ispac:htmlTextImageFrame>
													</div>
														<div id="estadoNotificacionVacio" style="display: none">

														<input type="text" styleClass="inputReadOnly" size="20" readonly="true" class="inputReadOnly" value="">
														</div>
													<input type="text" id="estadoNotificacionT" style="display: none" styleClass="inputReadOnly" size="20" readonly="true" class="inputReadOnly" value='<bean:message
													key='<%= _estadoNotificacionProperty %>' />'></c:if>


												<!--</c:if>-->


												<c:if test="${_tipoDireccion == 'T'}">

												<input type="text" id="estadoNotificacionT" style="display: block" styleClass="inputReadOnly" size="20" readonly="true" class="inputReadOnly" value='<bean:message
												key='<%= _estadoNotificacionProperty %>' />'>
												<div id="estadoNotificacionP" style="display: none">
												<ispac:htmlTextImageFrame property="property(ESTADONOTIFICACION_SPAC_TBL_006:SUSTITUTO)"
																		  readonly="true"
																		  readonlyTag="false"
																		  propertyReadonly="readonly"
																		  styleClass="input"
																		  styleClassReadonly="inputReadOnly"
																		  size="34"
																	  	  id="SEARCH_SPAC_DT_TRAMITES_ESTADONOTIFICACION"
																		  target="workframe"
																	  	  action="selectSubstitute.do?entity=SPAC_TBL_006"
																	  	  image="img/search-mg.gif"
																	  	  titleKeyLink="documento.etiqueta.estado.notificacion"
																	  	  showFrame="true">
													<ispac:parameter name="SEARCH_SPAC_DT_TRAMITES_ESTADONOTIFICACION" id="property(SPAC_DT_DOCUMENTOS:ESTADONOTIFICACION)" property="VALOR" />

			              							<ispac:parameter name="SEARCH_SPAC_DT_TRAMITES_ESTADONOTIFICACION" id="property(ESTADONOTIFICACION_SPAC_TBL_006:SUSTITUTO)" property="SUSTITUTO"/>
												</ispac:htmlTextImageFrame>
												</div>
												<div id="estadoNotificacionVacio" style="display: none">

												<input type="text" styleClass="inputReadOnly" size="20" readonly="true" class="inputReadOnly" value="">
												</div>
												</c:if>

											</td>

										<!-- Fecha Estado Notificacion -->
											<td height="20" class="formsTitleB" width="160px"><nobr><bean:message
												key="documento.etiqueta.fecha.estado.notificacion" />:</nobr>
											</td>
											<!-- Solo se permitirá modificar la fecha del estado de la notificación si es un tipo de notificación postal -->
											<c:if test="${_tipoDireccion == 'P' || empty _tipoDireccion}">
												<td height="20"  width="220px" ><nobr> <ispac:htmlTextCalendar
													property="property(SPAC_DT_DOCUMENTOS:FNOTIFICACION)"
													readonly='<%=new Boolean(_localReadonly).booleanValue()%>'
													propertyReadonly="readonly" styleClass="input"
													styleClassReadonly="inputReadOnly" size="12" maxlength="10"
													image='<%= buttoncalendar %>' format="dd/mm/yyyy"
													enablePast="true" /> </nobr></td>
											</c:if>
											<c:if test="${_tipoDireccion == 'T'}">

											<td height="20"  width="220px" ><nobr> <ispac:htmlText
													property="property(SPAC_DT_DOCUMENTOS:FNOTIFICACION)"
													readonly="true"
													propertyReadonly="readonly" styleClass="input"
													styleClassReadonly="inputReadOnly" size="12" maxlength="10"/> </nobr>
											</td>
											</c:if>
										</tr>

									</c:if>

									<tr>
										<td colspan="4" height="4px"
											style="font-size:4px; border-bottom:0px dotted #5C65A0;">&nbsp;</td>
									</tr>

									<c:set var="digitalSignManagementActive" value="${ISPACConfiguration.DIGITAL_SIGN_CONNECTOR_CLASS}" />
									<logic:notEqual name="defaultForm"
												property="property(SPAC_DT_DOCUMENTOS:TP_REG)"
												value="ENTRADA">

									<c:if test="${!empty digitalSignManagementActive}">

										<tr>
											<td colspan="4"><img
												src='<ispac:rewrite href="img/pixel.gif"/>' border="0"
												height="4px" /></td>
										</tr>

										<%-- DATOS DE FIRMA --%>


										<logic:equal value="false" name="_localReadonly">

											<logic:notEmpty name="defaultForm" property="property(SPAC_DT_DOCUMENTOS:INFOPAG)">

												<%--  FIRMA DEL DOCUMENTO --%>
												<c:if test="${(_signState == appConstants.signStates.SIN_FIRMA) || (_signState == appConstants.signStates.PENDIENTE_FIRMA)}">

													<tr>
														<td colspan="4" style="text-align:right">

															<table border="0" cellspacing="0" cellpadding="0">
																<tr>
																	<td class="formsTitleB" height="20px">

																		<c:set var="_method" value="${appConstants.actions.SELECT_OPTION}" />
																		<jsp:useBean id="_method" type="java.lang.String" />
																		<ispac:linkframe
																			target="workframe"
																			action='<%="signDocument.do?method="+_method%>'
																			titleKey="forms.tasks.sign"
																			inputField="property(SPAC_DT_DOCUMENTOS:ID)"
																			showFrame="true" styleClass="tdlink" height="480"
																			width="640" needToConfirm="true">
																		</ispac:linkframe>

																	</td>
																	<td><img src='<ispac:rewrite href="img/pixel.gif"/>'
																		border="0" width="10px" /></td>
																</tr>
															</table>

														</td>
													</tr>
													<tr>
														<td colspan="4" height="4px"
															style="font-size:4px; border-bottom:1px dotted #5C65A0;">&nbsp;</td>
													</tr>

													<tr>
														<td colspan="4"><img
															src='<ispac:rewrite href="img/pixel.gif"/>' border="0"
															height="4px" /></td>
													</tr>

												</c:if>

											</logic:notEmpty>

										</logic:equal>

											<logic:notEmpty name="defaultForm" property="property(SPAC_DT_DOCUMENTOS:INFOPAG)">

											<c:if test="${(_signState == appConstants.signStates.PENDIENTE_CIRCUITO_FIRMA)&& empty sessionScope['defaultPortafirmas']}">

												<tr>
														<td colspan="4" style="text-align:right">

															<table border="0" cellspacing="0" cellpadding="0">
																<tr>
																	<td class="formsTitleB" height="20px">


																		<c:set var="_metodo" value="${appConstants.actions.GET_STATE_DOCUMENT_IN_PORTAFIRMAS}" />
																		<jsp:useBean id="_metodo" type="java.lang.String" />


																		<ispac:linkframe
																			target="workframe"
																			action='<%="signDocument.do?method="+_metodo%>'
																			titleKey="ver.state.document.portafirmas"
																			inputField="property(SPAC_DT_DOCUMENTOS:ID)"
																			showFrame="false" styleClass="tdlink" height="480"
																			width="640" needToConfirm="true">
																		</ispac:linkframe>

																	</td>
																	<td><img src='<ispac:rewrite href="img/pixel.gif"/>'
																		border="0" width="10px" /></td>
																</tr>
															</table>

														</td>
													</tr>
													<tr>
														<td colspan="4" height="4px"
															style="font-size:4px; border-bottom:1px dotted #5C65A0;">&nbsp;</td>
													</tr>

													<tr>
														<td colspan="4"><img
															src='<ispac:rewrite href="img/pixel.gif"/>' border="0"
															height="4px" /></td>
													</tr>

												</c:if>
											</logic:notEmpty>


										<logic:equal value="false" name="_localReadonly">

											<logic:notEmpty name="defaultForm" property="property(SPAC_DT_DOCUMENTOS:INFOPAG)">
													<%--  Detalle de la firma del documento--%>
												<c:if test="${(_signState == appConstants.signStates.FIRMADO)}">

													<tr>
														<td colspan="4" style="text-align:right">

															<table border="0" cellspacing="0" cellpadding="0">
																<tr>
																	<td class="formsTitleB" height="20px">


																		<ispac:linkframe
																			target="workframe"
																			action='<%="showSignDetail.do?"%>'
																			titleKey="forms.tasks.detailSign"
																			inputField="SPAC_DT_DOCUMENTOS:ID"
																			showFrame="true" styleClass="tdlink" height="480"
																			width="640" needToConfirm="true">
																			<ispac:parameter
																			name="field"
																			id="property(SPAC_DT_DOCUMENTOS:DESTINO_ID)"
																			property="ID" />

																		</ispac:linkframe>

																	</td>
																	<td><img src='<ispac:rewrite href="img/pixel.gif"/>'
																		border="0" width="10px" /></td>
																</tr>
															</table>

														</td>
													</tr>
													<tr>
														<td colspan="4" height="4px"
															style="font-size:4px; border-bottom:1px dotted #5C65A0;">&nbsp;</td>
													</tr>

													<tr>
														<td colspan="4"><img
															src='<ispac:rewrite href="img/pixel.gif"/>' border="0"
															height="4px" /></td>
													</tr>

												</c:if>
											</logic:notEmpty>
										</logic:equal>

										<%-- Presentar el estado de firma si tiene valor --%>
										<logic:notEmpty name="defaultForm" property="property(SPAC_DT_DOCUMENTOS:ESTADOFIRMA)">

											<tr>
												<td colspan="4"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0"	height="4px" /></td>
											</tr>

											<tr>
												<!-- Fecha Estado Firma -->
												<td height="20" width="160px" class="formsTitleB"><nobr><bean:message
													key="documento.etiqueta.fecha.estado.firma" />:</nobr></td>
												<td height="20" width="220px"><html:text
													property="property(SPAC_DT_DOCUMENTOS:FFIRMA)"
													styleClass="inputReadOnly" size="12" readonly="true" /></td>
												<!-- Estado Firma -->
												<td height="20" width="120px" class="formsTitleB"><nobr><bean:message
													key="documento.etiqueta.estado.firma" />:</nobr></td>
												<td height="20"><html:text
													property="property(ESTADOFIRMA_SPAC_TBL_008:SUSTITUTO)"
													styleClass="inputReadOnly" size="30" readonly="true" /></td>
											</tr>

										</logic:notEmpty>

										<tr>
											<td colspan="4" height="4px" style="font-size:4px; border-bottom:0px dotted #5C65A0;">&nbsp;</td>
										</tr>

									</c:if>
									</logic:notEqual>

									<tr>
										<td colspan="4"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="4px" /></td>
									</tr>

									<%-- DATOS DE REGISTRO --%>
									<logic:notEmpty name="defaultForm" property="property(SPAC_DT_DOCUMENTOS:TP_REG)">

										<!-- DOCUMENTO EDITABLE -->
										<logic:equal value="false" name="_localReadonly">

											<!-- ACCIONES DE REGISTRO -->

											<!-- DOCUMENTO DE ENTRADA -->
											<%--
											<logic:equal name="defaultForm" property="property(SPAC_DT_DOCUMENTOS:TP_REG)" value="ENTRADA">

												<!-- BUSCAR REGISTRO ENTRADA -->
												<tr><td colspan="4" style="text-align:right">

													<table border="0" cellspacing="0" cellpadding="0">
														<tr>
															<td class="formsTitleB" >

																<ispac:linkframe 	id="DOCUMENT_SEARCH_INPUT_REGISTRY"
																					target="workframe"
																					action="searchInputRegistry.do"
																					titleKey="search.intray"
																					showFrame="true"
																					inputField="SPAC_DT_DOCUMENTOS:NREG"
																					styleClass="tdlink"
																					width="500"
																					height="300" >

																	<ispac:parameter name="DOCUMENT_SEARCH_INPUT_REGISTRY" id="property(SPAC_DT_DOCUMENTOS:DESCRIPCION)" property="ASUNTO"/>
																	<ispac:parameter name="DOCUMENT_SEARCH_INPUT_REGISTRY" id="property(SPAC_DT_DOCUMENTOS:ORIGEN)" property="IDENTIDADTITULAR"/>
																	<ispac:parameter name="DOCUMENT_SEARCH_INPUT_REGISTRY" id="property(SPAC_DT_DOCUMENTOS:ORIGEN_ID)" property="IDTITULAR"/>
																	<ispac:parameter name="DOCUMENT_SEARCH_INPUT_REGISTRY" id="property(SPAC_DT_DOCUMENTOS:ORIGEN_TIPO)" property="TIPOPERSONA"/>
																	<ispac:parameter name="DOCUMENT_SEARCH_INPUT_REGISTRY" id="property(SPAC_DT_DOCUMENTOS:DESTINO)" property="DESTINO"/>
																	<ispac:parameter name="DOCUMENT_SEARCH_INPUT_REGISTRY" id="property(SPAC_DT_DOCUMENTOS:DESTINO_ID)" property="DESTINO_ID"/>
																	<ispac:parameter name="DOCUMENT_SEARCH_INPUT_REGISTRY" id="property(SPAC_DT_DOCUMENTOS:DESTINO_TIPO)" property="DESTINO_TIPO"/>
																	<ispac:parameter name="DOCUMENT_SEARCH_INPUT_REGISTRY" id="property(SPAC_DT_DOCUMENTOS:FREG)" property="FREG"/>
																</ispac:linkframe>

															</td>
															<td><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" width="10px"/></td>
														</tr>
													</table>

												</td></tr>

												<tr><td colspan="4" height="4px" style="font-size:4px; border-bottom:1px dotted #5C65A0;">&nbsp;</td></tr>
												<tr><td colspan="4"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="4px"/></td></tr>

											</logic:equal>
											--%>

											<!-- DOCUMENTO SALIDA -->
											<logic:equal name="defaultForm"
												property="property(SPAC_DT_DOCUMENTOS:TP_REG)"
												value="SALIDA">

												<!-- DOCUMENTO FÍSICO CREADO -->
												<logic:notEmpty name="defaultForm"
													property="property(SPAC_DT_DOCUMENTOS:INFOPAG)">

													<tr>
														<td colspan="4" style="text-align:right">

															<table border="0" cellspacing="0" cellpadding="0">
																<tr>
																	<td class="formsTitleB" height="20px">

																		<!-- DOCUMENTO NO REGISTRADO -->
																		<c:if test="${empty numRegistro && !empty sicresConnectorClass}">

																			<!-- REGISTRAR SALIDA -->
																			<div id="outputRegisterDiv" class="visible">

																				<html:hidden property="property(SPAC_DT_DOCUMENTOS:NOMBRE)"/>
																				<ispac:linkframe id="DOCUMENT_INSERT_OUTPUT_REGISTRY"
																					target="workframe" action="insertOutputRegistry.do"
																					titleKey="registro.salida.crear" showFrame="true"
																					inputField="property(SPAC_DT_DOCUMENTOS:ID);property(SPAC_DT_DOCUMENTOS:DESTINO)"
																					styleClass="tdlink" width="700" height="400"
																					needToConfirm="true"
																					>
																				</ispac:linkframe>

																			</div>
																		</c:if>

																		<!-- DOCUMENTO REGISTRADO QUE SE PUEDE SELLAR -->
																		<c:if test="${!empty numRegistro && !empty sicresConnectorClass}">

																			<!-- SELLADO -->
																			<ispac:rewrite id="stampDocument"
																				action="stampDocumentAction.do" />
																			<a class="tdlink"
																				onclick="javascript:ispac_needToConfirm=false;"
																				href="javascript:confirmAction('<%=stampDocument%>?documentId=<bean:write name="defaultForm" property="property(SPAC_DT_DOCUMENTOS:ID)"/>', '<bean:message key="forms.task.confirm.stamp.document"/>', '<bean:message key="common.confirm"/>',  '<bean:message key="common.message.ok"/>' , '<bean:message key="common.message.cancel"/>');">
																			<bean:message key="forms.tasks.mark.document" /> </a>
																		</c:if>
																	</td>
																	<td><img src='<ispac:rewrite href="img/pixel.gif"/>'
																		border="0" width="10px" /></td>
																</tr>
															</table>

														</td>
													</tr>

													<tr>
														<td colspan="4" height="4px"
															style="font-size:4px; border-bottom:1px dotted #5C65A0;">&nbsp;</td>
													</tr>

													<tr>
														<td colspan="4"><img
															src='<ispac:rewrite href="img/pixel.gif"/>' border="0"
															height="4px" /></td>
													</tr>

												</logic:notEmpty>
												<!-- FIN DOCUMENTO FÍSICO CREADO -->

											</logic:equal>
											<!-- FIN DOCUMENTO SALIDA -->

										</logic:equal>

										<c:set var="_tipoReg">
											<bean:write name="defaultForm"
												property="property(SPAC_DT_DOCUMENTOS:TP_REG)" />
										</c:set>

										<c:if test="${(!empty _tipoReg) && (_tipoReg != 'NINGUNO')}">

											<tr>
												<td colspan="4"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0"	height="4px" /></td>
											</tr>

											<tr>
												<!-- Tipo de Registro -->
												<td height="20" class="formsTitleB" width="160px"><nobr><bean:message
													key="documento.etiqueta.tipo.registro" />:</nobr></td>
												<td height="20" colspan="3"><html:text
													property="property(SPAC_DT_DOCUMENTOS:TP_REG)"
													styleClass="inputReadOnly" size="12" readonly="true"
													maxlength="16" /></td>
											</tr>

											<tr>
												<td colspan="4"><img
													src='<ispac:rewrite href="img/pixel.gif"/>' border="0"
													height="4px" /></td>
											</tr>

											<tr>
												<!-- Numero de Registro -->
												<td height="20" width="160px" class="formsTitleB"><nobr><bean:message
													key="documento.etiqueta.numero.registro" />:</nobr></td>
												<td height="20" width="220px">

													<%--
													<logic:equal value="false" property="readonly" name="defaultForm">
													--%>
													<logic:equal value="false" name="_localReadonly">

														<logic:equal name="defaultForm"
															property="property(SPAC_DT_DOCUMENTOS:TP_REG)"
															value="ENTRADA">

															<c:choose>
															<c:when test="${!empty sicresConnectorClass}">
																	<ispac:htmlTextImage
																	property="property(SPAC_DT_DOCUMENTOS:NREG)"
																	id="SEARCH_SPAC_DT_DOCUMENTOS_NREG_SALIDA"

																	readonlyTag="false"
																	styleClassLink="search"
																	styleClassDeleteLink="delete"
																	styleClassReadonly="inputReadOnly"
																	size="25"
																	maxlength="16"

																	action="searchRegistry.do?tp_reg=ENTRADA"
																	image="img/search-mg.gif" titleKeyLink="search.intray"
																	inputField="SPAC_DT_DOCUMENTOS:NREG"
																	width="500" height="300"
																	jsDelete="delete_SEARCH_SPAC_DT_DOCUMENTOS_NREG"
																	titleKeyImageDelete="forms.exp.title.delete.data.register">

																	<ispac:parameter
																		name="SEARCH_SPAC_DT_DOCUMENTOS_NREG_SALIDA"
																		id="property(SPAC_DT_DOCUMENTOS:FREG)"
																		property="FREG" />
																	<ispac:parameter
																		name="SEARCH_SPAC_DT_DOCUMENTOS_NREG_SALIDA"
																		id="property(SPAC_DT_DOCUMENTOS:DESTINO_ID)"
																		property="DESTINO_ID" />
																	<ispac:parameter
																		name="SEARCH_SPAC_DT_DOCUMENTOS_NREG_SALIDA"
																		id="property(SPAC_DT_DOCUMENTOS:DESTINO)"
																		property="DESTINO" />
																	<ispac:parameter
																		name="SEARCH_SPAC_DT_DOCUMENTOS_NREG_SALIDA"
																		id="property(SPAC_DT_DOCUMENTOS:DESTINO_TIPO)"
																		property="DESTINO_TIPO" />
																	<ispac:parameter
																		name="SEARCH_SPAC_DT_DOCUMENTOS_NREG_SALIDA"
																		id="property(SPAC_DT_DOCUMENTOS:ORIGEN_ID)"
																		property="IDTITULAR" />
																	<ispac:parameter
																		name="SEARCH_SPAC_DT_DOCUMENTOS_NREG_SALIDA"
																		id="property(SPAC_DT_DOCUMENTOS:ORIGEN)"
																		property="IDENTIDADTITULAR" />
																	<ispac:parameter
																		name="SEARCH_SPAC_DT_DOCUMENTOS_NREG_SALIDA"
																		id="property(SPAC_DT_DOCUMENTOS:ORIGEN_TIPO)"
																		property="TIPOPERSONA" />
																	<ispac:parameter
																		name="SEARCH_SPAC_DT_DOCUMENTOS_NREG_SALIDA"
																		id="JAVASCRIPT"
																		property="accept_DOCUMENT_SEARCH_REGISTRY" />
																	</ispac:htmlTextImage>
															</c:when>
															<c:otherwise>
																<ispac:htmlText property="property(SPAC_DT_DOCUMENTOS:NREG)"
																	styleClass="input" size="20"
																	readonly='<%=new Boolean(_localReadonly).booleanValue()%>'
																	maxlength="16" />
															</c:otherwise>
															</c:choose>

														</logic:equal>

														<logic:equal name="defaultForm"
															property="property(SPAC_DT_DOCUMENTOS:TP_REG)"
															value="SALIDA">

															<c:choose>
															<c:when test="${!empty sicresConnectorClass}">

																<logic:empty name="defaultForm"
																	property="property(SPAC_DT_DOCUMENTOS:NREG)">

																	<ispac:htmlTextImage
																	property="property(SPAC_DT_DOCUMENTOS:NREG)"
																	id="SEARCH_SPAC_DT_DOCUMENTOS_NREG_SALIDA"

																	readonlyTag="false"
																	styleClassLink="search"
																	styleClassDeleteLink="delete"
																	styleClassReadonly="inputReadOnly"
																	size="25"
																	maxlength="16"

																	action="searchRegistry.do?tp_reg=SALIDA"
																	image="img/search-mg.gif" titleKeyLink="search.intray"
																	inputField="SPAC_DT_DOCUMENTOS:NREG"
																	width="500" height="300"
																	jsDelete="delete_SEARCH_SPAC_DT_DOCUMENTOS_NREG"
																	titleKeyImageDelete="forms.exp.title.delete.data.register">

																	<ispac:parameter
																		name="SEARCH_SPAC_DT_DOCUMENTOS_NREG_SALIDA"
																		id="property(SPAC_DT_DOCUMENTOS:FREG)"
																		property="FREG" />
																	<ispac:parameter
																		name="SEARCH_SPAC_DT_DOCUMENTOS_NREG_SALIDA"
																		id="property(SPAC_DT_DOCUMENTOS:ORIGEN_ID)"
																		property="ORIGEN_ID" />
																	<ispac:parameter
																		name="SEARCH_SPAC_DT_DOCUMENTOS_NREG_SALIDA"
																		id="property(SPAC_DT_DOCUMENTOS:ORIGEN)"
																		property="ORIGEN" />
																	<ispac:parameter
																		name="SEARCH_SPAC_DT_DOCUMENTOS_NREG_SALIDA"
																		id="property(SPAC_DT_DOCUMENTOS:ORIGEN_TIPO)"
																		property="ORIGEN_TIPO" />
																	<ispac:parameter
																		name="SEARCH_SPAC_DT_DOCUMENTOS_NREG_SALIDA"
																		id="property(SPAC_DT_DOCUMENTOS:DESTINO_ID)"
																		property="IDTITULAR" />
																	<ispac:parameter
																		name="SEARCH_SPAC_DT_DOCUMENTOS_NREG_SALIDA"
																		id="property(SPAC_DT_DOCUMENTOS:DESTINO)"
																		property="IDENTIDADTITULAR" />
																	<ispac:parameter
																		name="SEARCH_SPAC_DT_DOCUMENTOS_NREG_SALIDA"
																		id="property(SPAC_DT_DOCUMENTOS:DESTINO_TIPO)"
																		property="TIPOPERSONA" />
																	<ispac:parameter
																		name="SEARCH_SPAC_DT_DOCUMENTOS_NREG_SALIDA"
																		id="JAVASCRIPT"
																		property="accept_DOCUMENT_SEARCH_REGISTRY" />
																	</ispac:htmlTextImage>



																</logic:empty>

																<logic:notEmpty name="defaultForm"
																	property="property(SPAC_DT_DOCUMENTOS:NREG)">
																	<ispac:htmlText property="property(SPAC_DT_DOCUMENTOS:NREG)"
																		styleClass="inputReadOnly" size="20" readonly="true"
																		maxlength="16" />
																</logic:notEmpty>

															</c:when>
															<c:otherwise>
																<ispac:htmlText property="property(SPAC_DT_DOCUMENTOS:NREG)"
																	styleClass="input" size="20" readonly="false"
																	maxlength="16" />
															</c:otherwise>
															</c:choose>

														</logic:equal>

													</logic:equal>

													<%--
													<logic:equal value="true" property="readonly" name="defaultForm">
													--%>
													<logic:equal value="true" name="_localReadonly">

														<html:text property="property(SPAC_DT_DOCUMENTOS:NREG)"
															styleClass="inputReadOnly" size="20" readonly="true"
															maxlength="16" />

													</logic:equal>

												</td>
												<!-- Fecha de Registro -->
												<td height="20" class="formsTitleB" width="120px"><nobr><bean:message
													key="documento.etiqueta.fecha.registro" />:</nobr></td>
												<td height="20">
													<c:choose>
													<c:when test="${!empty sicresConnectorClass}">
														<html:text property="property(SPAC_DT_DOCUMENTOS:FREG)"
															styleClass="inputReadOnly" size="12" readonly="true"
															maxlength="10" />
													</c:when>
													<c:otherwise>
														<nobr><ispac:htmlTextCalendar
															property="property(SPAC_DT_DOCUMENTOS:FREG)"
															readonly='<%=new Boolean(_localReadonly).booleanValue()%>'
															propertyReadonly="readonly" styleClass="input"
															styleClassReadonly="inputReadOnly" size="12" maxlength="10"
															image='<%= buttoncalendar %>' format="dd/mm/yyyy"
															enablePast="true" /></nobr>
													</c:otherwise>
													</c:choose>
												</td>
											</tr>

											<tr>
												<td colspan="4"><img
													src='<ispac:rewrite href="img/pixel.gif"/>' border="0"
													height="6px" /></td>
											</tr>

											<tr>
												<!-- Origen -->
												<td height="20" width="160px" class="formsTitleB">
													<nobr><bean:message key="documento.etiqueta.origen" />:</nobr>
												</td>
												<td height="20" width="220px">
													<c:choose>
													<c:when test="${!empty sicresConnectorClass || _localReadonly}">
														<html:textarea property="property(SPAC_DT_DOCUMENTOS:ORIGEN)" styleClass="inputReadOnly" rows="2" cols="35" readonly="true"/>
													</c:when>
													<c:otherwise>
														<ispac:htmlTextarea property="property(SPAC_DT_DOCUMENTOS:ORIGEN)" styleClass="input" rows="2" cols="35"
															readonly='<%=new Boolean(_localReadonly).booleanValue()%>'/>
													</c:otherwise>
													</c:choose>
												</td>
												<!-- Destino -->
												<td height="20" class="formsTitleB" width="120px">
													<nobr><bean:message	key="documento.etiqueta.destino" />:</nobr>
												</td>
												<td height="20">

													<%--
													<logic:equal value="false" property="readonly" name="defaultForm">
													--%>
													<logic:equal value="false" name="_localReadonly">

														<nobr>
														<!-- SELECCIONAR DESTINO -->
														<logic:equal name="defaultForm"
															property="property(SPAC_DT_DOCUMENTOS:TP_REG)"
															value="SALIDA">
															<html:textarea property="property(SPAC_DT_DOCUMENTOS:DESTINO)" styleClass="inputReadOnly" rows="2" cols="35" readonly="true"/>
															<c:if test="${empty numRegistro || empty sicresConnectorClass}">

																<%-- Si ya se ha enviado la notificacion telematica, no permitimos cambiar el destinatario --%>
																<c:if test="${empty _estadoNotificacion || _estadoNotificacion == appConstants.notifyStatesConstants.UNRESOLVED}">

																	<logic:equal value="false" property="readonly" name="defaultForm">




																	<ispac:imageframe
																		id="DOCUMENT_SELECT_EXPEDIENT_THIRD_PARTY"
																		target="workframe"
																		action="selectThirdPartyExpedient.do"
																		image="img/search-mg.gif"
																		titleKey="forms.tasks.select.destiny" showFrame="true"

																		>

																		<ispac:parameter
																			name="DOCUMENT_SELECT_EXPEDIENT_THIRD_PARTY"
																			id="property(SPAC_DT_DOCUMENTOS:DESTINO_ID)"
																			property="ID" />
																		<ispac:parameter
																			name="DOCUMENT_SELECT_EXPEDIENT_THIRD_PARTY"
																			id="property(SPAC_DT_DOCUMENTOS:DESTINO)"
																			property="NOMBRE" />
																		<ispac:parameter
																			name="DOCUMENT_SELECT_EXPEDIENT_THIRD_PARTY"
																			id="property(SPAC_DT_DOCUMENTOS:DESTINO_TIPO)"
																			property="TIPO" />




																	<ispac:parameter
																			name="DOCUMENT_SELECT_EXPEDIENT_THIRD_PARTY"
																			id="JAVASCRIPT"
																			property="accept_DOCUMENT_SELECT_EXPEDIENT_THIRD_PARTY" />

																	</ispac:imageframe>


																		<%--
																		<ispac:htmlTextImageFrame property="property(SPAC_DT_DOCUMENTOS:DESTINO)"
																		  readonly="true"
																		  readonlyTag="false"
																		  propertyReadonly="readonly"
																		  styleClass="input"
																		  styleClassReadonly="inputReadOnly"
																		  inputField="SPAC_DT_DOCUMENTOS:DESTINO"
																		  size="34"
																	  	  id="DOCUMENT_SELECT_EXPEDIENT_THIRD_PARTY"
																		  target="workframe"
																	  	  action="selectThirdPartyExpedient.do"
																	  	  image="img/search-mg.gif"
																	  	  titleKey="forms.tasks.select.destiny"
																	  	  showFrame="true"
																	  	  jsDelete="delete_DOCUMENT_SELECT_EXPEDIENT_THIRD_PARTY"

																	  	  >

																		 	<ispac:parameter
																			name="DOCUMENT_SELECT_EXPEDIENT_THIRD_PARTY"
																			id="property(SPAC_DT_DOCUMENTOS:DESTINO_ID)"
																			property="ID" />
																		<ispac:parameter
																			name="DOCUMENT_SELECT_EXPEDIENT_THIRD_PARTY"
																			id="property(SPAC_DT_DOCUMENTOS:DESTINO)"
																			property="NOMBRE" />


																		<ispac:parameter
																			name="DOCUMENT_SELECT_EXPEDIENT_THIRD_PARTY"
																			id="property(SPAC_DT_DOCUMENTOS:DESTINO_TIPO)"
																			property="TIPO" />

																			<ispac:parameter
																			name="DOCUMENT_SELECT_EXPEDIENT_THIRD_PARTY"
																			id="JAVASCRIPT"
																			property="accept_DOCUMENT_SELECT_EXPEDIENT_THIRD_PARTY" />

																			</ispac:htmlTextImageFrame>
																	--%>
																	</logic:equal>
																	<logic:notEqual value="false" property="readonly" name="defaultForm">

																	<html:textarea property="property(SPAC_DT_DOCUMENTOS:DESTINO)" styleClass="inputReadOnly" rows="2" cols="35" readonly="true"/>
																	</logic:notEqual>
																</c:if>
															</c:if>
														</logic:equal>

														<logic:notEqual name="defaultForm"
															property="property(SPAC_DT_DOCUMENTOS:TP_REG)"
															value="SALIDA">

															<c:choose>
															<c:when test="${!empty sicresConnectorClass}">
																<html:textarea property="property(SPAC_DT_DOCUMENTOS:DESTINO)" styleClass="inputReadOnly" rows="2" cols="35" readonly="true"/>
															</c:when>
															<c:otherwise>
																<ispac:htmlTextarea property="property(SPAC_DT_DOCUMENTOS:DESTINO)" styleClass="input" rows="2" cols="35"
																	readonly='<%=new Boolean(_localReadonly).booleanValue()%>'/>
															</c:otherwise>
															</c:choose>

														</logic:notEqual>
														</nobr>

													</logic:equal>

													<%--
													<logic:equal value="true" property="readonly" name="defaultForm">
													--%>
													<logic:equal value="true" name="_localReadonly">

														<html:textarea property="property(SPAC_DT_DOCUMENTOS:DESTINO)" styleClass="inputReadOnly" rows="2" cols="35" readonly="true"/>

													</logic:equal>

												</td>
											</tr>

										</c:if>

									</logic:notEmpty>

								</table>

							</td>
						</tr>
						<tr>
							<td height="12px"><img src='<ispac:rewrite href="img/pixel.gif"/>'
								border="0" height="12px" /></td>
						</tr>

						<c:if test="${_verBorrarDocumento eq '1'}">
					<tr>
						<td colspan="4" height="4px" style="font-size:4px; border-bottom:1px dotted #5C65A0;">&nbsp;</td>
					</tr>
					<tr>
						<td height="12px"><img src='<ispac:rewrite href="img/pixel.gif"/>'
								border="0" height="12px" /></td>
					</tr>
					<tr>
					<td  class="alignRight">
						<logic:equal value="false" property="readonly" name="defaultForm">
							<a href='<c:out value="javascript:sure_('${_link_deleteDocument}')"/>'
								 class="tdlink" > <bean:message key="forms.tasks.delete.document" /> </a>
							</logic:equal>
					</td>
					<td><img src='<ispac:rewrite href="img/pixel.gif"/>'
						border="0" width="10px" /></td>
					</tr>
					<tr>
							<td height="12px"><img src='<ispac:rewrite href="img/pixel.gif"/>'
								border="0" height="12px" /></td>
						</tr>
				</c:if>
					</table>
					</td>
				</tr>

			</table>
			</td>
		</tr>
		<tr>
			<td height="4px"><img src='<ispac:rewrite href="img/pixel.gif"/>'
				border="0" height="4px" /></td>
		</tr>


	</table>

</logic:notEmpty>


<iframe src='' id='workframe_document' name='workframe_document' style='visibility:visible;height:0px;margin:0px;padding:0px;border:none;' allowtransparency='true'></iframe>

<%-- Control de campos al mostrar el formulario --%>
<script language='JavaScript' type='text/javascript'><!--

	var sicresConnectorClass = '<c:out value="${sicresConnectorClass}"/>';
	if (sicresConnectorClass != '') {
	   	freg = document.defaultForm.elements[ 'property(SPAC_DT_DOCUMENTOS:FREG)' ];
	    if ((typeof freg != 'undefined') && (freg.value != '')) {
			setReadOnly(document.defaultForm.elements[ 'property(SPAC_DT_DOCUMENTOS:NREG)' ]);
	    }
	}

//--></script>
