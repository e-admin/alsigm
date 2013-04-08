<%@ page language="java" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/ispac-util.tld" prefix="ispac" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>


<script language='JavaScript' type='text/javascript'><!--

	function allParentValues() {
/*
		if (document.defaultForm.elements[ 'property(SPAC_P_FASES:NOMBRE)' ].value != '') {
			document.defaultForm.elements[ 'property(PUB_REGLAS:ID_TRAMITE)' ].value = '-1';
			document.defaultForm.elements[ 'property(PARENT_VALUE)' ].value = '<bean:message key="form.pubRule.msg.all"/>';
		}
		else {
			ispac_needToConfirm = true;
		}
*/		
			
		document.defaultForm.elements[ 'property(PARENT_VALUE_ID)' ].value = '-1';
		document.defaultForm.elements[ 'property(PARENT_VALUE)' ].value = '<bean:message key="form.hierarchicalTables.values.msg.all"/>';
	}

	function selectParentValues(target, action, width, height) {
/*	
		stageId = document.defaultForm.elements[ 'property(PUB_REGLAS:ID_FASE)' ].value;
		if ((stageId != '') && (stageId > 0)) {
			action = action + "&sqlquery=WHERE ID=" + stageId;
			showFrame(target, action, width, height);
		}
*/		
		showFrame(target, action, width, height);
	}
	
	function clearParentValues() {
		document.defaultForm.elements[ 'property(PARENT_VALUE_ID)' ].value = '';
		document.defaultForm.elements[ 'property(PARENT_VALUE)' ].value = '';
		document.defaultForm.elements[ 'property(PARENT_VALUE_VALUE)' ].value = '';
	}



	function allDescendantValues() {
		document.defaultForm.elements[ 'property(DESCENDANT_VALUE_ID)' ].value = '-1';
		document.defaultForm.elements[ 'property(DESCENDANT_VALUE)' ].value = '<bean:message key="form.hierarchicalTables.values.msg.all"/>';
	}
	function clearDescendantValues() {
		document.defaultForm.elements[ 'property(DESCENDANT_VALUE_ID)' ].value = '';
		document.defaultForm.elements[ 'property(DESCENDANT_VALUE)' ].value = '';
		document.defaultForm.elements[ 'property(DESCENDANT_VALUE_VALUE)' ].value = '';
	}

	function selectDescendantValues(target, action, width, height) {
/*	
		stageId = document.defaultForm.elements[ 'property(PUB_REGLAS:ID_FASE)' ].value;
		if ((stageId != '') && (stageId > 0)) {
			action = action + "&sqlquery=WHERE ID=" + stageId;
			showFrame(target, action, width, height);
		}
*/		
		showFrame(target, action, width, height);
	}




//--></script>

<c:set var="_localReadonly"><%= request.getAttribute("readOnly")%></c:set>
<jsp:useBean id="_localReadonly" type="java.lang.String" />
<c:set var="_style">inputSelectS</c:set>
<c:if test='${_localReadonly}'>
	<c:set var="_style">inputReadOnly</c:set>
</c:if>
<jsp:useBean id="_style" type="java.lang.String" />


<html:form action="/hierarchicalTableManage.do">

<c:set var="regId" value="${param.regId}"/>
<jsp:useBean id="regId" type="java.lang.String"/>

<html:hidden property="regId" value='<%=regId%>'/>
<table cellpadding="1" cellspacing="0" border="0" width="100%">
	<tr>
		<td>
			<table class="box" width="100%" border="0" cellspacing="1" cellpadding="0">
				<!-- FORMULARIO -->
				<tr>
					<td class="blank">
						<table width="100%" border="0" cellspacing="2" cellpadding="2">
							<tr>
								<td height="5px" colspan="3">
									
								</td>
							</tr>
							<tr>
								<td width="8"><img height="1" width="8px" src='<ispac:rewrite href="img/pixel.gif"/>'/></td>
								<td width="100%">
									<table width="100%" border="0" class="formtable" cellspacing="0" cellpadding="0">
									
										<logic:messagesPresent>
											<tr>
												<td>
													<div id="formErrorsMessage">
														<bean:message key="forms.errors.formErrorsMessage"/>
													</div>	
												</td>
											</tr>
										</logic:messagesPresent>
										
										<tr>
											<td height="15px"><img src='<ispac:rewrite href="img/pixel.gif"/>'/></td>
										</tr>
										<tr>
											<td>
												<div style="display:block" id="page1">
													<table border="0" cellspacing="0" cellpadding="0" align="center" width="90%">
														<tr>
															<td>
																<table border="0" cellspacing="0" cellpadding="0" width="100%">
																	<tr>
																		<td height="20" class="formsTitle" width="1%">
																			<ispac:tooltipLabel labelKey="form.hierarchicalTables.values.propertyLabel.parentValue" tooltipKey="form.pubRule.propertyLabel.task.tooltip"/>
																		</td>
																		<td height="20">
																			<html:hidden property="property(PARENT_VALUE_ID)"/>
																			<c:set var="_searchFieldParentTable" value="${requestScope['searchFieldParentTable']}"/>
																			<jsp:useBean id="_searchFieldParentTable" type="java.lang.String" />
																			<html:text property="property(PARENT_VALUE_VALUE)" styleClass="inputReadOnly" readonly="true"/>
																				&nbsp;&nbsp;<nobr><ispac:htmlTextImageFrame property="property(PARENT_VALUE)"
																															readonly="true"
																															readonlyTag="false"
																											  				propertyReadonly="readonly"
																											  				styleClass="input"
																													  		styleClassReadonly="inputReadOnly"
																															size="64"
																														  	id="SELECT_PARENT_VALUE"
																											  				target="workframe"
																										  	  				action='<%="selectObject.do?codetable=" + request.getAttribute("PARENT_ENTITY") + "&codefield=ID&valuefield="+_searchFieldParentTable+"&caption=select.value.title&sqlquery=where vigente = 1&decorator=/formatters/entities/selectSustitutoformatter.xml"%>'
																										  	  				image="img/search-mg.gif" 
																										  	  				titleKeyLink="select.hierarchical.parentValue" 
																										  	  				showFrame="true"
																										  	  				jsShowFrame="selectParentValues"
																										  	  				jsDelete="clearParentValues"
																										  	  				titleKeyImageDelete="form.hierarchicalTables.values.msg.clear.parentValues">
																								  	  
																					<ispac:parameter name="SELECT_PARENT_VALUE" id="property(PARENT_VALUE_ID)" property="ID"/>
	                                        										<ispac:parameter name="SELECT_PARENT_VALUE" id="property(PARENT_VALUE)" property="SUSTITUTO"/>
	                                        										<ispac:parameter name="SELECT_PARENT_VALUE" id="property(PARENT_VALUE_VALUE)" property="VALOR"/>
																				</ispac:htmlTextImageFrame>
																				<a href="javascript:allParentValues()" class="aActionButton" onclick="ispac_needToConfirm = false;">
																				<bean:message key="form.hierarchicalTables.values.msg.all"/></a>
																				</nobr>
																				<div id="formErrors">
																						<html:errors property="property(PARENT_VALUE_ID)"/>
																				</div>	
																		</td>
																	</tr>
																	<tr>
																		<td height="20" class="formsTitle" width="1%">
																			<ispac:tooltipLabel labelKey="form.hierarchicalTables.values.propertyLabel.descendantValue" tooltipKey="form.pubRule.propertyLabel.task.tooltip"/>
																		</td>
																		<td height="20">
																			<html:hidden property="property(DESCENDANT_VALUE_ID)"/>
																			<c:set var="_searchFieldDescendantTable" value="${requestScope['searchFieldDescendantTable']}"/>
																			<jsp:useBean id="_searchFieldDescendantTable" type="java.lang.String" />
																			<html:text property="property(DESCENDANT_VALUE_VALUE)" styleClass="inputReadOnly" readonly="true"/>
																				&nbsp;&nbsp;<nobr><ispac:htmlTextImageFrame property="property(DESCENDANT_VALUE)"
																															readonly="true"
																															readonlyTag="false"
																											  				propertyReadonly="readonly"
																											  				styleClass="input"
																													  		styleClassReadonly="inputReadOnly"
																															size="64"
																														  	id="SELECT_DESCENDANT_VALUE"
																											  				target="workframe"
																										  	  				action='<%="selectObject.do?codetable=" + request.getAttribute("DESCENDANT_ENTITY") + "&codefield=ID&valuefield="+_searchFieldDescendantTable+"&caption=select.value.title&sqlquery=where vigente = 1&decorator=/formatters/entities/selectSustitutoformatter.xml"%>'
																										  	  				image="img/search-mg.gif" 
																										  	  				titleKeyLink="select.hierarchical.descendantValue" 
																										  	  				showFrame="true"
																										  	  				jsShowFrame="selectDescendantValues"
																										  	  				jsDelete="clearDescendantValues"
																										  	  				titleKeyImageDelete="form.hierarchicalTables.values.msg.clear.descendantValues">
																								  	  
																					<ispac:parameter name="SELECT_DESCENDANT_VALUE" id="property(DESCENDANT_VALUE_ID)" property="ID"/>
	                                        										<ispac:parameter name="SELECT_DESCENDANT_VALUE" id="property(DESCENDANT_VALUE)" property="SUSTITUTO"/>
	                                        										<ispac:parameter name="SELECT_DESCENDANT_VALUE" id="property(DESCENDANT_VALUE_VALUE)" property="VALOR"/>
																				</ispac:htmlTextImageFrame>
																				<a href="javascript:allDescendantValues()" class="aActionButton" onclick="ispac_needToConfirm = false;">
																				<bean:message key="form.hierarchicalTables.values.msg.all"/></a>
																				</nobr>
																				<div id="formErrors">
																					<html:errors property="property(DESCENDANT_VALUE_ID)"/>
																				</div>	
																		</td>
																	</tr>
																	<tr>
																		<td colspan="2" height="20"></td>
																	</tr>
																	
																</table>
															</td>
														</tr>
													</table>
												</div>
											</td>
										</tr>
									</table>
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>







</html:form>