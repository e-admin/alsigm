<%@ page language="java" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/ispac-util.tld" prefix="ispac" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>

<c:set var="isOnlyView">
   <c:out value="${requestScope.READ_ONLY}" default="false"/>
</c:set>

<c:set var="action">
   <c:out value="${requestScope['ActionDestino']}" default="/showEntityWizard.do"/>
</c:set>

<jsp:useBean id="action" type="java.lang.String"/>

<script language='JavaScript' type='text/javascript'><!--

	function getValueOfElementByName(name){

		objects = window.document.getElementsByName(name);
		return objects[0].value;
	}
	
	function getValueOfCheckByName(name){

		value = "";
		objects = window.document.getElementsByName(name);
		if (objects[0].checked)
			value = objects[0].value;
		
		return value;
	}
	
	function getActionDeleteFields(){
	
		var actionURL = '<c:url value="${action}?method=deleteIndexFields"/>';
	
		actionURL = actionURL + "&property(NAME)=" + getValueOfElementByName('property(NAME)');
		actionURL = actionURL + "&property(UNIQUE)=" + getValueOfCheckByName('property(UNIQUE)');
		//actionURL = actionURL + "&property(DOCUMENTAL)=" + getValueOfCheckByName('property(DOCUMENTAL)');

		return actionURL;
	}


	
	function deleteIndexes(url) {
		document.forms['defaultForm'].action = url;
		if (checkboxElement(document.defaultForm.multibox) == "") {
			jAlert('<bean:message key="error.entity.noSelected"/>', '<bean:message key="common.alert"/>' , '<bean:message key="common.message.ok"/>' , '<bean:message key="common.message.cancel"/>');
		} else {
			document.forms['defaultForm'].submit();
		}	
	}



//--></script>

<html:form action='<%=action%>'>
	
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
														<html:errors property="property(ERROR_OPCION)"/>
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
																			<td height="20" class="formsTitle">
																				<div id="form.entity.index.propertyLabel.name">
																					<ispac:tooltipLabel labelKey="form.entity.index.propertyLabel.name" tooltipKey="form.entity.index.propertyLabel.name.tooltip"/>
																				</div>
																			</td>
																			<td height="20" class="formsTitle" width="99%">
																			<c:choose>
																				<c:when test="${isOnlyView}">
																				&nbsp;&nbsp;<html:text property="property(NAME)" styleClass="inputReadOnly" readonly="true" maxlength="30"/>&nbsp; <bean:message key="catalog.data.obligatory"/>
																				</c:when>
																				<c:otherwise>
																				&nbsp;&nbsp;<html:text property="property(NAME)" styleClass="inputSelectS" readonly="false" maxlength="30"/>&nbsp; <bean:message key="catalog.data.obligatory"/>
																				</c:otherwise>
																			</c:choose>
																				<div id="formErrors">
																					<html:errors property="property(NAME)"/>
																				</div>
																			</td>
																		</tr>
																		<tr>
																			<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="8px"/></td>
																		</tr>
																		<c:set var="valueIsUniqueKey"><bean:write name="defaultForm" property="property(UNIQUE)"/></c:set>
																		<c:set var="isUniqueKey" value="${valueIsUniqueKey=='S'}"/>

																		<tr>
																			<td height="20" class="formsTitle">
																				<div id="form.entity.index.propertyLabel.uniqueKey">
																					<ispac:tooltipLabel labelKey="form.entity.index.propertyLabel.uniqueKey" tooltipKey="form.entity.index.propertyLabel.uniqueKey.tooltip"/>
																				</div>
																			</td>
																			<td height="20">
																			<c:choose>
																				<c:when test="${isOnlyView}">
																					<c:choose>
																						<c:when test="${isUniqueKey}">
																					&nbsp;&nbsp;<bean:message key="bool.yes" />
																						</c:when>
																						<c:otherwise>
																						&nbsp;&nbsp;<bean:message key="bool.no" />
																						</c:otherwise>
																					</c:choose>
																				</c:when>
																				<c:otherwise>
																				&nbsp;&nbsp;<html:checkbox property="property(UNIQUE)" styleClass="checkbox" value="S" />
																				</c:otherwise>
																			</c:choose>
																				<div id="formErrors">
																					<html:errors property="property(UNIQUE)"/>
																				</div>	
																			</td>
																		</tr>
																		<tr>
																			<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="8px"/></td>
																		</tr>
																		
																		<%--
																		<c:set var="valueIsDocumentalKey"><bean:write name="defaultForm" property="property(DOCUMENTAL)"/></c:set>
																		<c:set var="isDocumentalKey" value="${valueIsDocumentalKey=='S'}"/>

																		<tr>
																			<td height="20" class="formsTitle">
																				<div id="form.entity.index.propertyLabel.documental">
																					<ispac:tooltipLabel labelKey="form.entity.index.propertyLabel.documental" tooltipKey="form.entity.index.propertyLabel.documental.tooltip"/>
																				</div>
													     				</td>
																			<td height="20">
																			<c:choose>
																				<c:when test="${isOnlyView}">
																					<c:choose>
																					<c:when test="${isDocumentalKey}">
																				&nbsp;&nbsp;<bean:message key="bool.yes" />
																					</c:when>
																					<c:otherwise>
																					&nbsp;&nbsp;<bean:message key="bool.no" />
																					</c:otherwise>
																					</c:choose>
																				</c:when>
																				<c:otherwise>
																				&nbsp;&nbsp;<html:checkbox property="property(DOCUMENTAL)" styleClass="checkbox" value="S" />
																				</c:otherwise>
																			</c:choose>
																				<div id="formErrors">
																					<html:errors property="property(DOCUMENTAL)"/>
																				</div>
																			</td>
																		</tr>
																		<tr>
																			<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="8px"/></td>
																		</tr>
																		--%>
																		
																		<tr valign="top">
																			<td height="20" class="formsTitle">
																				<div id="form.entity.index.propertyLabel.fields">
																					<ispac:tooltipLabel labelKey="form.entity.index.propertyLabel.fields" tooltipKey="form.entity.index.propertyLabel.fields.tooltip"/>
																				</div>
														     				</td>
																			<td height="20">
																				&nbsp;&nbsp;<html:hidden property="property(FIELDS_COUNT)" />
																				<div id="fields">
																				<c:if test="${!isOnlyView}">	
																					<div class="buttonList">
																						<ul>
																							<li>
																								<a href="javascript:showFrame('workframe','<ispac:rewrite action='<%=action%>'/>?method=indexFields',640,480)"><nobr><bean:message key="forms.button.add"/></nobr></a>
																							</li>
																							
																							<c:choose>
																							<c:when test="${!empty sessionScope['INDEX_FIELD_LIST']}">
																							<li>
																								<a href="javascript:deleteIndexes(getActionDeleteFields())"><nobr><bean:message key="forms.button.delete"/></nobr></a>
																							</li>
																							</c:when>
																							<c:otherwise>
																								<li style="background-color: #ddd; color: #aaa; cursor: default;">
																									<nobr><bean:message key="forms.button.delete"/></nobr>
																								</li>
																							</c:otherwise>
																							</c:choose>
																						</ul>
																					</div>
																				</c:if>	
																
																					<display:table name="sessionScope.INDEX_FIELD_LIST" 
																								   id="field" 
																								   class="tableDisplay" 
																								   export="false" 
																								   sort="list">
																						
																						<logic:present name="field">
																						   
																							<bean:define id='item' name='field' type='ieci.tdw.ispac.ispaclib.bean.ObjectBean'/>
																									   
																							<c:if test="${!isOnlyView}">
																																											
																								<display:column title='<%="<input type=\'checkbox\' onclick=\'javascript:checkAll(document.forms[0].multibox, this);\'/>"%>'
																												style="text-align:center;">
																									<html:multibox property="multibox">
																										<c:out value="${field_rowNum}"/>
																									</html:multibox>
																								</display:column>
																								
																							</c:if>
																							
																							<display:column titleKey="form.entity.field.propertyLabel.physicalName" 
																											property="property(PHYSICALNAME)" 
																											style="width:25%"/>
																											
																							<display:column titleKey="form.entity.field.propertyLabel.logicalName" 
																											property="property(ETIQUETA)" 
																											style="width:50%"/>
																											
																							<display:column titleKey="form.entity.field.propertyLabel.type" 
																											property="property(TYPEDESCR)" 
																											style="width:15%;white-space: nowrap;"/>
																											
																							<display:column titleKey="form.entity.field.propertyLabel.size" 
																											style="width:10%">
																											
																								<c:set var="size"><%=item.getProperty("SIZE")%></c:set>
																								<c:if test="${size!='0'}">
																									<%=item.getProperty("SIZE")%>
																								</c:if>
	
																							</display:column>
																							
																						</logic:present>
																							
																					</display:table>
																				</div>
																				<div id="formErrors">
																					<html:errors property="property(FIELDS_COUNT)"/>
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
