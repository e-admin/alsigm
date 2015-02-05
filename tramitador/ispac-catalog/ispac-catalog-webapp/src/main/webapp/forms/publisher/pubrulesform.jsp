<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/ispac-util.tld" prefix="ispac" %>

<%@page import="ieci.tdw.ispac.api.item.IProcedure"%>

<script language='JavaScript' type='text/javascript'><!--

	function selectVersion(target, action, width, height) {

		pcdId = document.defaultForm.elements[ 'property(PUB_REGLAS:ID_PCD)' ].value;
		if ((pcdId != '') && (pcdId > 0)) {
			groupId = document.defaultForm.elements[ 'property(SPAC_P_PROCEDIMIENTOS:ID_GROUP)' ].value;
			action = action + "&sqlquery=WHERE ID_GROUP=" + groupId + " ORDER BY NVERSION DESC";
			showFrame(target, action, width, height);
		}
	}

	function setVersionBorrador() {
		pcdId = document.defaultForm.elements[ 'property(PUB_REGLAS:ID_PCD)' ].value;
		if ((pcdId != '') && (pcdId > 0)) {
			estado = document.defaultForm.elements[ 'property(SPAC_P_PROCEDIMIENTOS:ESTADO)' ].value;
			if (estado == '<%=String.valueOf(IProcedure.PCD_STATE_DRAFT)%>') {
				document.defaultForm.elements[ 'property(SPAC_P_PROCEDIMIENTOS:NVERSION)' ].value = '<bean:message key="procedure.versions.draft" />'
			}
		}
	}

	function selectStage(target, action, width, height) {

		pcdId = document.defaultForm.elements[ 'property(PUB_REGLAS:ID_PCD)' ].value;
		if ((pcdId != '') && (pcdId > 0)) {
			action = action + "&sqlquery=WHERE ID_PCD=" + pcdId;
			showFrame(target, action, width, height);
		}
	}

	function selectTask(target, action, width, height) {

		stageId = document.defaultForm.elements[ 'property(PUB_REGLAS:ID_FASE)' ].value;
		if ((stageId != '') && (stageId > 0)) {
			action = action + "&sqlquery=WHERE ID_FASE=" + stageId;
			showFrame(target, action, width, height);
		}
	}

	function selectTypeDoc(target, action, width, height) {

		taskId = document.defaultForm.elements[ 'property(PUB_REGLAS:ID_TRAMITE)' ].value;
		if ((taskId != '') && (taskId > 0)) {
			action = action + "&sqlquery=WHERE ID IN (SELECT ID_TPDOC FROM SPAC_CT_TRTD WHERE ID_TRAMITE IN (SELECT ID_CTTRAMITE FROM SPAC_P_TRAMITES WHERE ID = " + taskId + "))";
			showFrame(target, action, width, height);
		}
	}

	function checkEvent() {
		var select = document.defaultForm.elements[ 'property(PUB_REGLAS:ID_EVENTO)' ]
		if (select) {
			var form = select.form;
			if (form) {
				var element = form.elements[ 'property(PUB_REGLAS:ID_INFO)' ];
				if (element) {
					var type = select.options[select.selectedIndex].value;
					if (type != 8) {
						element.value = "";
						element.className = "inputReadOnly";
						element.disabled = true;
					} else {
						element.className = "inputSelectS";
						element.disabled = false;
						//element.value = "1";
					}
				}
			}
		}
	}

	function clearStage() {

		if (document.defaultForm.elements[ 'property(PUB_REGLAS:ID_PCD)' ].value != -1) {
			document.defaultForm.elements[ 'property(PUB_REGLAS:ID_FASE)' ].value = '';
			document.defaultForm.elements[ 'property(SPAC_P_FASES:NOMBRE)' ].value = '';
			clearTask();
		}
	}

	function clearTask() {

		if (document.defaultForm.elements[ 'property(PUB_REGLAS:ID_FASE)' ].value != -1) {
			document.defaultForm.elements[ 'property(PUB_REGLAS:ID_TRAMITE)' ].value = '';
			document.defaultForm.elements[ 'property(SPAC_P_TRAMITES:NOMBRE)' ].value = '';
			clearTypeDoc();
		}
	}

	function clearTypeDoc() {

		if (document.defaultForm.elements[ 'property(PUB_REGLAS:ID_TRAMITE)' ].value != -1) {
			document.defaultForm.elements[ 'property(PUB_REGLAS:TIPO_DOC)' ].value = '';
			document.defaultForm.elements[ 'property(SPAC_CT_TPDOC:NOMBRE)' ].value = '';
		 	ispac_needToConfirm = true;
		 }
	}

	function allProcedures() {
		document.defaultForm.elements[ 'property(PUB_REGLAS:ID_PCD)' ].value = '-1';
		document.defaultForm.elements[ 'property(SPAC_P_PROCEDIMIENTOS:NOMBRE)' ].value = '<bean:message key="form.pubRule.msg.all"/>';
		document.defaultForm.elements[ 'property(SPAC_P_PROCEDIMIENTOS:NVERSION)' ].value = '';
		document.defaultForm.elements[ 'property(SPAC_P_PROCEDIMIENTOS:ESTADO)' ].value = '';
		document.defaultForm.elements[ 'property(SPAC_P_PROCEDIMIENTOS:ID_GROUP)' ].value = '';
		allStages();
	}

	function allStages() {
		if (document.defaultForm.elements[ 'property(SPAC_P_PROCEDIMIENTOS:NOMBRE)' ].value != '') {
			document.defaultForm.elements[ 'property(PUB_REGLAS:ID_FASE)' ].value = '-1';
			document.defaultForm.elements[ 'property(SPAC_P_FASES:NOMBRE)' ].value = '<bean:message key="form.pubRule.msg.all.a"/>';
			allTasks();
		}
		else {
			ispac_needToConfirm = true;
		}
	}

	function allTasks() {
		if (document.defaultForm.elements[ 'property(SPAC_P_FASES:NOMBRE)' ].value != '') {
			document.defaultForm.elements[ 'property(PUB_REGLAS:ID_TRAMITE)' ].value = '-1';
			document.defaultForm.elements[ 'property(SPAC_P_TRAMITES:NOMBRE)' ].value = '<bean:message key="form.pubRule.msg.all"/>';
			allTypeDocs();
		}
		else {
			ispac_needToConfirm = true;
		}
	}

	function allTypeDocs() {
		if (document.defaultForm.elements[ 'property(SPAC_P_TRAMITES:NOMBRE)' ].value != '') {
			document.defaultForm.elements[ 'property(PUB_REGLAS:TIPO_DOC)' ].value = '-1';
			document.defaultForm.elements[ 'property(SPAC_CT_TPDOC:NOMBRE)' ].value = '<bean:message key="form.pubRule.msg.all"/>';
		}
		ispac_needToConfirm = true;
	}

//--></script>

<div id="navmenutitle">
	<bean:message key="form.pubRule.mainTitle"/>
</div>
<div id="navSubMenuTitle">
</div>

<div id="navmenu">
	<ul class="actionsMenuList">
		<logic:equal name="defaultForm" property="key" value="-1">
			<ispac:hasFunction functions="FUNC_PUB_RULES_EDIT">
			<li>
				<a href="javascript:submit('<%= request.getContextPath() + "/storeCTEntity.do"%>')">
					<nobr><bean:message key="forms.button.accept"/></nobr>
				</a>
			</li>
			</ispac:hasFunction>
			<li>
				<a href='<%=request.getContextPath() + "/showPubRulesGroupList.do"%>'>
					<nobr><bean:message key="forms.button.cancel"/></nobr>
				</a>
			</li>
		</logic:equal>
		<logic:notEqual name="defaultForm" property="key" value="-1">
			<logic:notPresent name="pcdId">
				<ispac:hasFunction functions="FUNC_PUB_RULES_EDIT">
				<li>
					<a href="javascript:submit('<%= request.getContextPath() + "/storeCTEntity.do"%>')">
						<nobr><bean:message key="forms.button.save"/></nobr>
					</a>
				</li>
				</ispac:hasFunction>
				<li>
					<a href='<%=request.getContextPath() + "/showPubRulesGroupList.do"%>'>
						<nobr><bean:message key="forms.button.close"/></nobr>
					</a>
				</li>
				<ispac:hasFunction functions="FUNC_PUB_RULES_EDIT">
				<li>
					<a href="javascript:query('<%= request.getContextPath() + "/deletePubRule.do"%>', '<bean:message key="message.deleteConfirm"/>','<bean:message key="common.confirm"/>', '<bean:message key="common.message.ok"/>','<bean:message key="common.message.cancel"/>')">
						<nobr><bean:message key="forms.button.delete"/></nobr>
					</a>
			  	</li>
			  	</ispac:hasFunction>
			</logic:notPresent>
			<logic:present name="pcdId">

				<bean:define name="pcdId" id="pcdId"/>
				<bean:define name="stageId" id="stageId"/>
				<bean:define name="taskId" id="taskId"/>
				<bean:define name="typeDoc" id="typeDoc"/>

				<% String queryString = "?pcdId=" + pcdId + "&stageId=" + stageId + "&taskId=" + taskId + "&typeDoc=" + typeDoc;%>
				<ispac:hasFunction functions="FUNC_PUB_RULES_EDIT">
				<li>
					<a href="javascript:submit('<%= request.getContextPath() + "/storeCTEntity.do" + queryString%>')">
						<nobr><bean:message key="forms.button.save"/></nobr>
					</a>
				</li>
				</ispac:hasFunction>
				<li>
					<a href='<%=request.getContextPath() + "/showPubRulesList.do" + queryString%>')">
						<nobr><bean:message key="forms.button.close"/></nobr>
					</a>
			  	</li>
			  	<ispac:hasFunction functions="FUNC_PUB_RULES_EDIT">
				<li>
					<a href="javascript:query('<%= request.getContextPath() + "/deletePubRule.do" + queryString%>', '<bean:message key="message.deleteConfirm"/>','<bean:message key="common.confirm"/>', '<bean:message key="common.message.ok"/>','<bean:message key="common.message.cancel"/>')">
						<nobr><bean:message key="forms.button.delete"/></nobr>
					</a>
			  	</li>
			  	</ispac:hasFunction>
			</logic:present>
		</logic:notEqual>
	</ul>
</div>
<html:form action='/showCTEntity.do'>

	<%--
		Nombre de Aplicación.
		 Necesario para realizar la validación
 	--%>
	<html:hidden property="entityAppName"/>
	<!-- Identificador de la entidad -->
	<html:hidden property="entity"/>
	<!-- Identificador del registro -->
	<html:hidden property="key"/>
	<!-- Registro de solo lectura-->
	<html:hidden property="readonly"/>

	<html:hidden property="property(PUB_REGLAS:ID_PCD)"/>
	<html:hidden property="property(PUB_REGLAS:ID_FASE)"/>
	<html:hidden property="property(PUB_REGLAS:ID_TRAMITE)"/>
	<html:hidden property="property(PUB_REGLAS:TIPO_DOC)"/>

	<html:hidden property="property(SPAC_P_PROCEDIMIENTOS:ESTADO)"/>
	<html:hidden property="property(SPAC_P_PROCEDIMIENTOS:ID_GROUP)"/>

	<table cellpadding="1" cellspacing="0" border="0" width="100%">
		<tr>
			<td>
				<table class="box" width="100%" border="0" cellspacing="1" cellpadding="0">
					<!-- FORMULARIO -->
					<tr>
						<td class="blank">
							<table width="100%" border="0" cellspacing="2" cellpadding="2">
								<tr>
									<td height="5px" colspan="3"></td>
								</tr>
								<tr>
									<td width="8"><img height="1" width="8px" src='<ispac:rewrite href="img/pixel.gif"/>'/></td>
									<td width="100%">
										<table width="100%" border="0" class="formtable" cellspacing="0" cellpadding="0">
											<tr>
												<td>
												<logic:messagesPresent>
													<div id="formErrorsMessage">
														<bean:message key="forms.errors.formErrorsMessage"/>
													</div>
												</logic:messagesPresent>
												</td>
											</tr>
											<tr>
												<td>
													<div style="display:block" id="page1">
														<table border="0" cellspacing="0" cellpadding="0" align="center" width="90%">
															<tr>
																<td>
																	<table border="0" cellspacing="0" cellpadding="0" width="100%">

																		<tr>
																			<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="8px"/></td>
																		</tr>
																		<logic:notEqual name="defaultForm" property="key" value="-1">
																			<tr>
																				<td height="20" class="formsTitle" width="1%">
																					<ispac:tooltipLabel labelKey="form.pubRule.propertyLabel.id" tooltipKey="form.pubRule.propertyLabel.id.tooltip"/>
																				</td>
																				<td height="20">
																					&nbsp;&nbsp;<html:text property="property(PUB_REGLAS:ID)" styleClass="inputReadOnly" size="5" readonly="true" maxlength="10"/>
																					<div id="formErrors">
																						<html:errors property="property(PUB_REGLAS:ID)"/>
																					</div>
																				</td>
																			</tr>
																		</logic:notEqual>

																		<tr>
																			<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="8px"/></td>
																		</tr>
																		<tr>
																			<td height="20" class="formsTitle" width="1%">
																				<ispac:tooltipLabel labelKey="form.pubRule.propertyLabel.procedure" tooltipKey="form.pubRule.propertyLabel.procedure.tooltip"/>
																			</td>
																			<td height="20">
																				<%--
																				<logic:notEqual name="defaultForm" property="key" value="-1">
																					&nbsp;&nbsp;<html:text property="property(SPAC_P_PROCEDIMIENTOS:NOMBRE)" size="80" styleClass="inputReadOnly" readonly="true" />
																				</logic:notEqual>
																				<logic:equal name="defaultForm" property="key" value="-1">
																				--%>

																					&nbsp;&nbsp;<nobr><ispac:htmlTextImageFrame property="property(SPAC_P_PROCEDIMIENTOS:NOMBRE)"
																																readonly="true"
																																readonlyTag="false"
																																propertyReadonly="readonly"
																																styleClass="input"
																																styleClassReadonly="inputReadOnly"
																																size="61"
																																id="SELECT_PROCEDURE"
																																target="workframe"
																																action="selectProcedure.do?codetable=SPAC_P_PROCEDIMIENTOS&codefield=ID&valuefield=NOMBRE&caption=select.procedure.caption&decorator=/formatters/selectproceduretreedefaultformatter.xml"
																																image="img/search-mg.gif"
																																titleKeyLink="select.procedure"
																																showFrame="true"
																																showDelete="false">

																					<%--
																					&nbsp;&nbsp;<nobr><ispac:htmlTextImageFrame property="property(SPAC_P_PROCEDIMIENTOS:NOMBRE)"
																																readonly="true"
																																readonlyTag="false"
																												  				propertyReadonly="readonly"
																												  				styleClass="input"
																														  		styleClassReadonly="inputReadOnly"
																																size="61"
																															  	id="SELECT_PROCEDURE"
																												  				target="workframe"
																											  	  				action="selectObject.do?codetable=SPAC_P_PROCEDIMIENTOS&codefield=ID&valuefield=NOMBRE&caption=select.procedure.caption&decorator=/formatters/entities/selectobjectformatter.xml"
																											  	  				image="img/search-mg.gif"
																											  	  				titleKeyLink="select.procedure"
																											  	  				showFrame="true"
																											  	  				showDelete="false">
																					--%>

																						<ispac:parameter name="SELECT_PROCEDURE" id="property(PUB_REGLAS:ID_PCD)" property="ID"/>
		                                        										<ispac:parameter name="SELECT_PROCEDURE" id="property(SPAC_P_PROCEDIMIENTOS:NOMBRE)" property="NOMBRE"/>
																						<ispac:parameter name="SELECT_PROCEDURE" id="property(SPAC_P_PROCEDIMIENTOS:NVERSION)" property="NVERSION"/>
																						<ispac:parameter name="SELECT_PROCEDURE" id="property(SPAC_P_PROCEDIMIENTOS:ESTADO)" property="ESTADO"/>
																						<ispac:parameter name="SELECT_PROCEDURE" id="property(SPAC_P_PROCEDIMIENTOS:ID_GROUP)" property="ID_GROUP"/>
		                                        										<ispac:parameter name="SELECT_PROCEDURE" id="property(PUB_REGLAS:ID_TRAMITE)" property="EMPTY"/>
		                                        										<ispac:parameter name="SELECT_PROCEDURE" id="property(SPAC_P_TRAMITES:NOMBRE)" property="EMPTY"/>
		                                        										<ispac:parameter name="SELECT_PROCEDURE" id="property(PUB_REGLAS:ID_FASE)" property="EMPTY"/>
		                                        										<ispac:parameter name="SELECT_PROCEDURE" id="property(SPAC_P_FASES:NOMBRE)" property="EMPTY"/>
																						<ispac:parameter name="SELECT_PROCEDURE" id="property(PUB_REGLAS:TIPO_DOC)" property="EMPTY"/>
		                                        										<ispac:parameter name="SELECT_PROCEDURE" id="property(SPAC_CT_TPDOC:NOMBRE)" property="EMPTY"/>
																						<ispac:parameter name="SELECT_PROCEDURE" id="JAVASCRIPT" property="setVersionBorrador"/>

																					</ispac:htmlTextImageFrame>
																					<a href="javascript:allProcedures()" class="aActionButton" onclick="ispac_needToConfirm = false;">
																					<bean:message key="form.pubRule.msg.all"/></a>
																					</nobr>&nbsp; <bean:message key="catalog.data.obligatory"/>
																					<div id="formErrors">
																						<html:errors property="property(PUB_REGLAS:ID_PCD)"/>
																					</div>

																				<%--
																				</logic:equal>
																				--%>
																			</td>
																		</tr>

																		<tr>
																			<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="8px"/></td>
																		</tr>
																		<tr>
																			<td height="20" class="formsTitle" width="1%">
																				<ispac:tooltipLabel labelKey="form.pubRule.propertyLabel.version" tooltipKey="form.pubRule.propertyLabel.version.tooltip"/>
																			</td>
																			<td height="20">
																					&nbsp;&nbsp;<nobr><ispac:htmlTextImageFrame property="property(SPAC_P_PROCEDIMIENTOS:NVERSION)"
																																readonly="true"
																																readonlyTag="false"
																																propertyReadonly="readonly"
																																styleClass="input"
																																styleClassReadonly="inputReadOnly"
																																size="10"
																																id="SELECT_PROCEDURE_VERSION"
																																target="workframe"
																																action="selectObject.do?codetable=SPAC_P_PROCEDIMIENTOS&codefield=ID&valuefield=NVERSION&caption=select.procedure.version.caption&decorator=/formatters/entities/selectpcdversionformatter.xml&noSearch=true"
																																image="img/search-mg.gif"
																																titleKeyLink="select.procedure.version"
																																showFrame="true"
																																jsShowFrame="selectVersion"
																																showDelete="false">

																						<ispac:parameter name="SELECT_PROCEDURE_VERSION" id="property(PUB_REGLAS:ID_PCD)" property="ID"/>
		                                        										<ispac:parameter name="SELECT_PROCEDURE_VERSION" id="property(SPAC_P_PROCEDIMIENTOS:NVERSION)" property="NVERSION"/>
																						<ispac:parameter name="SELECT_PROCEDURE_VERSION" id="property(SPAC_P_PROCEDIMIENTOS:ESTADO)" property="ESTADO"/>
		                                        										<ispac:parameter name="SELECT_PROCEDURE_VERSION" id="property(PUB_REGLAS:ID_TRAMITE)" property="EMPTY"/>
		                                        										<ispac:parameter name="SELECT_PROCEDURE_VERSION" id="property(SPAC_P_TRAMITES:NOMBRE)" property="EMPTY"/>
		                                        										<ispac:parameter name="SELECT_PROCEDURE_VERSION" id="property(PUB_REGLAS:ID_FASE)" property="EMPTY"/>
		                                        										<ispac:parameter name="SELECT_PROCEDURE_VERSION" id="property(SPAC_P_FASES:NOMBRE)" property="EMPTY"/>
																						<ispac:parameter name="SELECT_PROCEDURE_VERSION" id="property(PUB_REGLAS:TIPO_DOC)" property="EMPTY"/>
		                                        										<ispac:parameter name="SELECT_PROCEDURE_VERSION" id="property(SPAC_CT_TPDOC:NOMBRE)" property="EMPTY"/>
																						<ispac:parameter name="SELECT_PROCEDURE_VERSION" id="JAVASCRIPT" property="setVersionBorrador"/>

																					</ispac:htmlTextImageFrame>
																					</nobr>
																			</td>
																		</tr>

																		<tr>
																			<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="8px"/></td>
																		</tr>
																		<tr>
																			<td height="20" class="formsTitle" width="1%">
																				<ispac:tooltipLabel labelKey="form.pubRule.propertyLabel.stage" tooltipKey="form.pubRule.propertyLabel.stage.tooltip"/>
																			</td>
																			<td height="20">
																				<%--
																				<logic:notEqual name="defaultForm" property="key" value="-1">
																					&nbsp;&nbsp;<html:text property="property(SPAC_P_FASES:NOMBRE)" size="80" styleClass="inputReadOnly" readonly="true" />
																				</logic:notEqual>
																				<logic:equal name="defaultForm" property="key" value="-1">
																				--%>
																					&nbsp;&nbsp;<nobr><ispac:htmlTextImageFrame property="property(SPAC_P_FASES:NOMBRE)"
																																readonly="true"
																																readonlyTag="false"
																												  				propertyReadonly="readonly"
																												  				styleClass="input"
																														  		styleClassReadonly="inputReadOnly"
																																size="61"
																															  	id="SELECT_STAGE"
																												  				target="workframe"
																											  	  				action="selectObject.do?codetable=SPAC_P_FASES&codefield=ID&valuefield=NOMBRE&caption=select.stage.caption&decorator=/formatters/entities/selectobjectdesformatter.xml"
																											  	  				image="img/search-mg.gif"
																											  	  				titleKeyLink="select.stage"
																											  	  				showFrame="true"
																											  	  				jsShowFrame="selectStage"
																											  	  				jsDelete="clearStage"
																											  	  				titleKeyImageDelete="form.pubRule.msg.clear.stage">

																						<ispac:parameter name="SELECT_STAGE" id="property(PUB_REGLAS:ID_FASE)" property="ID"/>
		                                        										<ispac:parameter name="SELECT_STAGE" id="property(SPAC_P_FASES:NOMBRE)" property="NOMBRE"/>
		                                        										<ispac:parameter name="SELECT_STAGE" id="property(PUB_REGLAS:ID_TRAMITE)" property="EMPTY"/>
		                                        										<ispac:parameter name="SELECT_STAGE" id="property(SPAC_P_TRAMITES:NOMBRE)" property="EMPTY"/>
																						<ispac:parameter name="SELECT_STAGE" id="property(PUB_REGLAS:TIPO_DOC)" property="EMPTY"/>
		                                        										<ispac:parameter name="SELECT_STAGE" id="property(SPAC_CT_TPDOC:NOMBRE)" property="EMPTY"/>

																					</ispac:htmlTextImageFrame>
																					<a href="javascript:allStages()" class="aActionButton" onclick="ispac_needToConfirm = false;">
																					<bean:message key="form.pubRule.msg.all.a"/></a>
																					</nobr>
																				<%--
																				</logic:equal>
																				--%>
																			</td>
																		</tr>

																		<tr>
																			<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="8px"/></td>
																		</tr>
																		<tr>
																			<td height="20" class="formsTitle" width="1%">
																				<ispac:tooltipLabel labelKey="form.pubRule.propertyLabel.task" tooltipKey="form.pubRule.propertyLabel.task.tooltip"/>
																			</td>
																			<td height="20">
																				<%--
																				<logic:notEqual name="defaultForm" property="key" value="-1">
																					&nbsp;&nbsp;<html:text property="property(SPAC_P_TRAMITES:NOMBRE)" size="80" styleClass="inputReadOnly" readonly="true" />
																				</logic:notEqual>
																				<logic:equal name="defaultForm" property="key" value="-1">
																				--%>
																					&nbsp;&nbsp;<nobr><ispac:htmlTextImageFrame property="property(SPAC_P_TRAMITES:NOMBRE)"
																																readonly="true"
																																readonlyTag="false"
																												  				propertyReadonly="readonly"
																												  				styleClass="input"
																														  		styleClassReadonly="inputReadOnly"
																																size="61"
																															  	id="SELECT_TASK"
																												  				target="workframe"
																											  	  				action="selectObject.do?codetable=SPAC_P_TRAMITES&codefield=ID&valuefield=NOMBRE&caption=select.task.caption&decorator=/formatters/entities/selectobjectdesformatter.xml"
																											  	  				image="img/search-mg.gif"
																											  	  				titleKeyLink="select.task"
																											  	  				showFrame="true"
																											  	  				jsShowFrame="selectTask"
																											  	  				jsDelete="clearTask"
																											  	  				titleKeyImageDelete="form.pubRule.msg.clear.task">

																						<ispac:parameter name="SELECT_TASK" id="property(PUB_REGLAS:ID_TRAMITE)" property="ID"/>
		                                        										<ispac:parameter name="SELECT_TASK" id="property(SPAC_P_TRAMITES:NOMBRE)" property="NOMBRE"/>
																						<ispac:parameter name="SELECT_TASK" id="property(PUB_REGLAS:TIPO_DOC)" property="EMPTY"/>
		                                        										<ispac:parameter name="SELECT_TASK" id="property(SPAC_CT_TPDOC:NOMBRE)" property="EMPTY"/>

																					</ispac:htmlTextImageFrame>
																					<a href="javascript:allTasks()" class="aActionButton" onclick="ispac_needToConfirm = false;">
																					<bean:message key="form.pubRule.msg.all"/></a>
																					</nobr>
																				<%--
																				</logic:equal>
																				--%>
																			</td>
																		</tr>

																		<tr>
																			<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="8px"/></td>
																		</tr>
																		<tr>
																			<td height="20" class="formsTitle" width="1%">
																				<ispac:tooltipLabel labelKey="form.pubRule.propertyLabel.typeDoc" tooltipKey="form.pubRule.propertyLabel.typeDoc.tooltip"/>
																			</td>
																			<td height="20">
																				<%--
																				<logic:notEqual name="defaultForm" property="key" value="-1">
																					&nbsp;&nbsp;<html:text property="property(SPAC_CT_TPDOC:NOMBRE)" size="80" styleClass="inputReadOnly" readonly="true" />
																				</logic:notEqual>
																				<logic:equal name="defaultForm" property="key" value="-1">
																				--%>
																					&nbsp;&nbsp;<nobr><ispac:htmlTextImageFrame property="property(SPAC_CT_TPDOC:NOMBRE)"
																																readonly="true"
																																readonlyTag="false"
																												  				propertyReadonly="readonly"
																												  				styleClass="input"
																														  		styleClassReadonly="inputReadOnly"
																																size="61"
																															  	id="SELECT_TYPEDOC"
																												  				target="workframe"
																											  	  				action="selectObject.do?codetable=SPAC_CT_TPDOC&codefield=ID&valuefield=NOMBRE&caption=select.typeDoc.caption&decorator=/formatters/entities/selectobjectdesformatter.xml"
																											  	  				image="img/search-mg.gif"
																											  	  				titleKeyLink="select.typeDoc"
																											  	  				showFrame="true"
																											  	  				jsShowFrame="selectTypeDoc"
																											  	  				jsDelete="clearTypeDoc"
																											  	  				titleKeyImageDelete="form.pubRule.msg.clear.typeDoc">

																						<ispac:parameter name="SELECT_TYPEDOC" id="property(PUB_REGLAS:TIPO_DOC)" property="ID"/>
		                                        										<ispac:parameter name="SELECT_TYPEDOC" id="property(SPAC_CT_TPDOC:NOMBRE)" property="NOMBRE"/>

																					</ispac:htmlTextImageFrame>
																					<a href="javascript:allTypeDocs()" class="aActionButton" onclick="ispac_needToConfirm = false;">
																					<bean:message key="form.pubRule.msg.all"/></a>
																					</nobr>
																				<%--
																				</logic:equal>
																				--%>
																			</td>
																		</tr>

																		<tr>
																			<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="8px"/></td>
																		</tr>
																		<tr>
																			<td height="20" class="formsTitle" width="1%">
																				<ispac:tooltipLabel labelKey="form.pubRule.propertyLabel.event" tooltipKey="form.pubRule.propertyLabel.event.tooltip"/>
																			</td>
																			<td height="20">
																				&nbsp;&nbsp;<html:select property="property(PUB_REGLAS:ID_EVENTO)" styleClass="inputSelectS" onchange="javascript:checkEvent()">
																					<html:option value="1" key="event.milestone.type.1"/>
																					<html:option value="2" key="event.milestone.type.2"/>
																					<html:option value="21" key="event.milestone.type.21"/>
																					<html:option value="3" key="event.milestone.type.3"/>
																					<html:option value="4" key="event.milestone.type.4"/>
																					<html:option value="20" key="event.milestone.type.20"/>
																					<html:option value="5" key="event.milestone.type.5"/>
																					<html:option value="6" key="event.milestone.type.6"/>
																					<html:option value="7" key="event.milestone.type.7"/>
																					<html:option value="8" key="event.milestone.type.8"/>
																					<html:option value="9" key="event.milestone.type.9"/>
																					<html:option value="10" key="event.milestone.type.10"/>
																					<html:option value="11" key="event.milestone.type.11"/>
																					<html:option value="12" key="event.milestone.type.12"/>
																					<html:option value="13" key="event.milestone.type.13"/>
																					<html:option value="14" key="event.milestone.type.14"/>
																					<html:option value="15" key="event.milestone.type.15"/>
																					<html:option value="16" key="event.milestone.type.16"/>
																					<html:option value="17" key="event.milestone.type.17"/>
																					<html:option value="18" key="event.milestone.type.18"/>
																					<html:option value="19" key="event.milestone.type.19"/>
																				</html:select>
																				&nbsp; <bean:message key="catalog.data.obligatory"/>
																				<div id="formErrors">
																					<html:errors property="property(PUB_REGLAS:ID_EVENTO)"/>
																				</div>
																			</td>
																		</tr>

																		<tr>
																			<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="8px"/></td>
																		</tr>
																		<tr>
																			<td height="20" class="formsTitle" width="1%">
																				<ispac:tooltipLabel labelKey="form.pubRule.propertyLabel.info" tooltipKey="form.pubRule.propertyLabel.info.tooltip"/>
																			</td>
																			<td height="20">
																				&nbsp;&nbsp;<html:select property="property(PUB_REGLAS:ID_INFO)" styleClass="inputReadOnly" disabled="true">
																					<html:option value="1" key="id.info.type.1"/>
																					<html:option value="2" key="id.info.type.2"/>
																				</html:select>
																			</td>
																		</tr>

																		<tr>
																			<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="8px"/></td>
																		</tr>
																		<tr>
																			<td height="20" class="formsTitle" width="1%">
																				<ispac:tooltipLabel labelKey="form.pubRule.propertyLabel.action" tooltipKey="form.pubRule.propertyLabel.action.tooltip"/>
																			</td>
																			<td height="20">
																				<html:hidden property="property(PUB_REGLAS:ID_ACCION)"/>
																				&nbsp;&nbsp;<nobr><ispac:htmlTextImageFrame property="property(PUB_ACCIONES:NOMBRE)"
																															readonly="true"
																															readonlyTag="false"
																											  				propertyReadonly="readonly"
																											  				styleClass="input"
																													  		styleClassReadonly="inputReadOnly"
																															size="61"
																														  	id="SELECT_ACTION"
																											  				target="workframe"
																										  	  				action="selectObject.do?codetable=PUB_ACCIONES&codefield=ID&valuefield=NOMBRE&caption=select.action.caption&decorator=/formatters/entities/selectobjectdesformatter.xml"
																										  	  				image="img/search-mg.gif"
																										  	  				titleKeyLink="select.action"
																										  	  				showFrame="true"
																										  	  				showDelete="false">

																						<ispac:parameter name="SELECT_ACTION" id="property(PUB_REGLAS:ID_ACCION)" property="ID"/>
			                                        									<ispac:parameter name="SELECT_ACTION" id="property(PUB_ACCIONES:NOMBRE)" property="NOMBRE"/>

																					</ispac:htmlTextImageFrame>
																				</nobr>&nbsp; <bean:message key="catalog.data.obligatory"/>
																				<div id="formErrors">
																					<html:errors property="property(PUB_REGLAS:ID_ACCION)"/>
																				</div>
																			</td>
																		</tr>

																		<tr>
																			<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="8px"/></td>
																		</tr>
																		<tr>
																			<td height="20" class="formsTitle" width="1%">
																				<ispac:tooltipLabel labelKey="form.pubRule.propertyLabel.condition" tooltipKey="form.pubRule.propertyLabel.condition.tooltip"/>
																			</td>
																			<td height="20">
																				<html:hidden property="property(PUB_REGLAS:ID_CONDICION)"/>
																				&nbsp;&nbsp;<nobr><ispac:htmlTextImageFrame property="property(PUB_CONDICIONES:NOMBRE)"
																															readonly="true"
																															readonlyTag="false"
																											  				propertyReadonly="readonly"
																											  				styleClass="input"
																													  		styleClassReadonly="inputReadOnly"
																															size="61"
																														  	id="SELECT_CONDITION"
																											  				target="workframe"
																										  	  				action="selectObject.do?codetable=PUB_CONDICIONES&codefield=ID&valuefield=NOMBRE&caption=select.condition.caption&decorator=/formatters/entities/selectobjectdesformatter.xml"
																										  	  				image="img/search-mg.gif"
																										  	  				titleKeyLink="select.condition"
																										  	  				showFrame="true"
																										  	  				showDelete="false">

																						<ispac:parameter name="SELECT_CONDITION" id="property(PUB_REGLAS:ID_CONDICION)" property="ID"/>
			                                        									<ispac:parameter name="SELECT_CONDITION" id="property(PUB_CONDICIONES:NOMBRE)" property="NOMBRE"/>

																					</ispac:htmlTextImageFrame>
																				</nobr>&nbsp; <bean:message key="catalog.data.obligatory"/>
																				<div id="formErrors">
																					<html:errors property="property(PUB_REGLAS:ID_CONDICION)"/>
																				</div>
																			</td>
																		</tr>

																		<tr>
																			<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="8px"/></td>
																		</tr>
																		<tr>
																			<td height="20" class="formsTitle" width="1%">
																				<ispac:tooltipLabel labelKey="form.pubRule.propertyLabel.attributes" tooltipKey="form.pubRule.propertyLabel.attributes.tooltip"/>
																			</td>
																			<td height="20">
																				&nbsp;&nbsp;<html:textarea property="property(PUB_REGLAS:ATRIBUTOS)" styleClass="input" readonly="false" cols="60" rows="10" />
																			</td>
																		</tr>

																		<tr>
																			<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="8px"/></td>
																		</tr>
																		<tr>
																			<td height="20" class="formsTitle" width="1%">
																				<ispac:tooltipLabel labelKey="form.pubRule.propertyLabel.application" tooltipKey="form.pubRule.propertyLabel.application.tooltip"/>
																			</td>
																			<td height="20">
																				<html:hidden property="property(PUB_REGLAS:ID_APLICACION)"/>
																				&nbsp;&nbsp;<nobr><ispac:htmlTextImageFrame property="property(PUB_APLICACIONES_HITOS:NOMBRE)"
																															readonly="true"
																															readonlyTag="false"
																											  				propertyReadonly="readonly"
																											  				styleClass="input"
																													  		styleClassReadonly="inputReadOnly"
																															size="61"
																														  	id="SELECT_APPLICATION"
																											  				target="workframe"
																										  	  				action="selectObject.do?codetable=PUB_APLICACIONES_HITOS&codefield=ID&valuefield=NOMBRE&caption=select.application.caption&decorator=/formatters/entities/selectobjectformatter.xml"
																										  	  				image="img/search-mg.gif"
																										  	  				titleKeyLink="select.application"
																										  	  				showFrame="true"
																										  	  				showDelete="false">

																						<ispac:parameter name="SELECT_APPLICATION" id="property(PUB_REGLAS:ID_APLICACION)" property="ID"/>
			                                        									<ispac:parameter name="SELECT_APPLICATION" id="property(PUB_APLICACIONES_HITOS:NOMBRE)" property="NOMBRE"/>

																					</ispac:htmlTextImageFrame>
																				</nobr>&nbsp; <bean:message key="catalog.data.obligatory"/>
																				<div id="formErrors">
																					<html:errors property="property(PUB_REGLAS:ID_APLICACION)"/>
																				</div>
																			</td>
																		</tr>
																		<tr>
																			<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="8px"/></td>
																		</tr>
																	</table>
																</td>
															</tr>
														</table>
													</div>
												</td>
											</tr>
											<tr>
												<td height="15px"><img src='<ispac:rewrite href="img/pixel.gif"/>'/></td>
											</tr>
										</table>
									</td>
									<td width="8"><img height="1" width="8px" src='<ispac:rewrite href="img/pixel.gif"/>'/></td>
								</tr>
								<tr>
									<td height="5px" colspan="3"><img src='<ispac:rewrite href="img/pixel.gif"/>' height="5px"/></td>
								</tr>
							</table>
						</td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
</html:form>

<script language='JavaScript' type='text/javascript'><!--
	checkEvent();
	setVersionBorrador();
//--></script>