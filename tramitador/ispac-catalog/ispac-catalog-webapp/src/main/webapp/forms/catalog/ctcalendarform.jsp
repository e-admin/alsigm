<%@ page language="java" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/ispac-util.tld" prefix="ispac" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>

<c:set var="entityId" value="${param.entityId}"/>
<jsp:useBean id="entityId" type="java.lang.String"/>

<c:set var="regId" value="${param.regId}"/>
<jsp:useBean id="regId" type="java.lang.String"/>

<c:set var="action" >/showCTCalendar.do</c:set>
<jsp:useBean id="action" type="java.lang.String"/>

<script language='JavaScript' type='text/javascript'>

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
		tokenIdsSeleccionados = "";		
		if (window.top.document.calendarForm.weekDaysSelect.length){
			for (i=0;i<window.top.document.calendarForm.weekDaysSelect.length;i++){
				element = window.top.document.calendarForm.weekDaysSelect[i];
				if (tokenIdsSeleccionados == "" && element.checked)
					tokenIdsSeleccionados = element.value;
				else if (element.checked)				
					tokenIdsSeleccionados = tokenIdsSeleccionados + "," + element.value;
			}
		}	
		var actionURL = '<c:url value="${action}?method=deleteHolyday"/>';
		actionURL = actionURL + "&entityId=" + <%=entityId%>;
		actionURL = actionURL + "&regId=" + <%=regId%>;		 
		actionURL = actionURL + '&nombre=' + getValueOfElementByName('property(NOMBRE)');
		actionURL = actionURL + '&weekDaysSelect=' + tokenIdsSeleccionados;
		return actionURL;	  
		  
	}

	function getActionUpdateDays(){
	
		var actionURL = '<c:url value="${action}?method=updateWeekEnd"/>';
		return actionURL;
	}
	
	
	function submit(url){
		document.forms['calendarForm'].action = url;
		document.forms['calendarForm'].submit();
			
	}
	
	function deleteDays(url) {
		document.forms['calendarForm'].action = url;
		if (checkboxElement(document.calendarForm.multibox) == "") {
			jAlert('<bean:message key="error.entity.noSelected"/>', '<bean:message key="common.alert"/>', '<bean:message key="common.message.ok"/>' , '<bean:message key="common.message.cancel"/>');
		} else {
			document.forms['calendarForm'].submit();
		}	
	}
	
	
	
//--></script>

<div id="navmenutitle">
	<bean:message key="form.calendar.mainTitle"/>
</div>

<div id="navSubMenuTitle">
</div>

<html:form action='<%="/showCTCalendar.do?method=store&entityId=" + entityId + "&regId=" + regId %>'>
	<div id="navmenu">
		<ul class="actionsMenuList">
			<logic:equal name="calendarForm" property="key" value="-1">
				<ispac:hasFunction functions="FUNC_COMP_CALENDARS_EDIT">
				<li>
					<a href="javascript:submit('<%= request.getContextPath() + "/showCTCalendar.do?method=store&entityId=" + entityId + "&regId=" + regId %>')">
						<nobr><bean:message key="forms.button.accept"/></nobr>
					</a>
				</li>
				</ispac:hasFunction>
				<li>
					<a href='<%=request.getContextPath() + "/showCTCalendarsList.do"%>'>
						<nobr><bean:message key="forms.button.cancel"/></nobr>
					</a>
				</li>			
			</logic:equal>
			
			<logic:notEqual name="calendarForm" property="key" value="-1">
				<ispac:hasFunction functions="FUNC_COMP_CALENDARS_EDIT">
				<li>
					<a href="javascript:submit('<%= request.getContextPath() + "/showCTCalendar.do?method=store&entityId=" + entityId + "&regId=" + regId %>')">
						<nobr><bean:message key="forms.button.save"/></nobr>
					</a>
				</li>
				</ispac:hasFunction>
				<li>
					<a href='<%=request.getContextPath() + "/showCTCalendarsList.do"%>'>
						<nobr><bean:message key="forms.button.close"/></nobr>
					</a>
				</li>	
				<ispac:hasFunction functions="FUNC_COMP_CALENDARS_EDIT">
				<li>
													
					<a href="javascript:query('<%= request.getContextPath() + "/deleteCTEntity.do"%>', '<bean:message key="message.deleteConfirm"/>','<bean:message key="common.confirm"/>', '<bean:message key="common.message.ok"/>','<bean:message key="common.message.cancel"/>')">
						<nobr><bean:message key="forms.button.delete"/></nobr>
					</a>		
				</li>			
				<li>
					<a href="javascript:showFrame('workframe','<ispac:rewrite action='<%=action%>'/>?method=copyCalendar<%="&entityId="+entityId+"&regId="+regId%>',640,480)"><nobr><bean:message key="form.calendar.button.copy"/></nobr></a>
				</li>		  
				</ispac:hasFunction>
		 	 </logic:notEqual>
		</ul>
	</div>

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
	<!-- Registro de distribución -->
	<html:hidden property="property(ID)"/>

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
																		<logic:notEqual name="calendarForm" property="key" value="-1">
																			<tr>
																				<td height="20" class="formsTitle" width="1%">
																					<ispac:tooltipLabel labelKey="form.calendar.propertyLabel.id" tooltipKey="form.calendar.propertyLabel.id.tooltip"/>
																				</td>
																				<td height="20">
																					&nbsp;&nbsp;<html:text property="property(ID)" styleClass="inputReadOnly" size="5" readonly="true" maxlength="10"/>
																					<div id="formErrors">
																						<html:errors property="property(ID)"/>
																					</div>
																				</td>
																			</tr>
																		</logic:notEqual>
																		<tr>
																			<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="8px"/></td>
																		</tr>
																		<tr>
																			<td height="20" class="formsTitle">
																				<ispac:tooltipLabel labelKey="form.calendar.propertyLabel.name" tooltipKey="form.calendar.propertyLabel.name.tooltip"/>
																			</td>
																			<td height="20">
																				&nbsp;&nbsp;<html:text property="property(NOMBRE)" styleClass="inputSelectS" readonly="false" maxlength="100"/>&nbsp; <bean:message key="catalog.data.obligatory"/>
																				<div id="formErrors">
																					<html:errors property="property(NOMBRE)"/>
																				</div>
																			</td>
																		</tr>
																		<tr>
																			<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="8px"/></td>
																		</tr>
																		
																		<html:hidden property="property(CALENDARIO)"/>
																		
																		<logic:notEqual name="calendarForm" property="key" value="-1">
																			
																			<tr valign="top">
										
																				<td height="20" class="formsTitle">
																					<div id="form.calendar.propertyLabel.diasFinDeSemana">
																						<ispac:tooltipLabel labelKey="form.calendar.propertyLabel.diasFinDeSemana" tooltipKey="form.calendar.propertyLabel.diasFestivos.tooltip"/>
																					</div>
															     				</td>
															     				
																				<td height="20">
																					<div id="fields">

																						<c:forEach var="field" items="${WEEKDAYS_LIST}">
																							<html:multibox property="weekDaysSelect">
																								<c:out value="${field.id}"/>
																							</html:multibox>
																							
																							<c:set var="keyDia" value="form.calendar.propertyLabel.${field.day}"/>
																							<bean:define id="keyDia" name="keyDia" type="java.lang.String"/>
																							<bean:message key='<%=keyDia%>'/>
																																		
																						</c:forEach>

																					</div>
															
																				
																					<div id="formErrors">
																						<html:errors property="property(FIELDS_COUNT)"/>
																					</div>
																				</td>
																		     				
															     			</tr>
																			<tr>
																				<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="8px"/></td>
																			</tr>
																		
																			<tr valign="top">
																				<td height="20" class="formsTitle">
																					<div id="form.calendar.propertyLabel.diasFestivos">
																						<ispac:tooltipLabel labelKey="form.calendar.propertyLabel.diasFestivos" tooltipKey="form.calendar.propertyLabel.diasFestivos.tooltip"/>
																					</div>
															     				</td>
																				<td height="20">
																					<div id="fields">
																						<div class="buttonList">
																							<ispac:hasFunction functions="FUNC_COMP_CALENDARS_EDIT">
																							<ul>
																								<li>
																									<a href="javascript:showFrame('workframe','<ispac:rewrite action='<%=action%>'/>?method=holydays',640,480);"><nobr><bean:message key="forms.button.add"/></nobr></a>
																								</li>
																								
																								<c:choose>
																								<c:when test="${!empty HOLYDAYS_LIST}">
																								<li>
																									<a href="javascript:deleteDays(getActionDeleteFields())"><nobr><bean:message key="forms.button.delete"/></nobr></a>
																								</li>
																								</c:when>
																								<c:otherwise>
																									<li style="background-color: #ddd; color: #aaa; cursor: default;">
																										<nobr><bean:message key="forms.button.delete"/></nobr>
																									</li>
																								</c:otherwise>
																								</c:choose>                                                    				
																								<li>
																									<a href="javascript:showFrame('workframe','<ispac:rewrite action='<%=action%>'/>?method=fixedHolydays',640,480);"><nobr><bean:message key="forms.button.addFixedHolidays"/></nobr></a>				
																								</li>	
																							</ul>
																							</ispac:hasFunction>
																						</div>
																	
																						<display:table name="HOLYDAYS_LIST" 
																									   id="field" 
																									   class="tableDisplay" 
																									   export="false" 
																									   sort="list"
																									   defaultsort="2"
																									   requestURI=''
																									    >
																																											
																							<display:column title='<%="<input type=\'checkbox\' onclick=\'javascript:checkAll(document.forms[0].multibox, this);\'/>"%>'
																											style="text-align:center">
																								<html:multibox property="multibox">
																									<c:out value="${field.date}"/>
																								</html:multibox>
																							</display:column>
																								
																							<display:column titleKey="form.calendar.holiday.propertyLabel.fecha" 
																											property="date" 
																											style="width:20%"
																											headerClass="sortable"
																											comparator="ieci.tdw.ispac.ispacweb.comparators.DateComparator"
																											sortable="true"/>
																																								
																							<display:column titleKey="form.calendar.holiday.propertyLabel.name" 
																											property="name" 
																											style="width:80%"
																											headerClass="sortable"
																											sortable="true"/>								
																											
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
																	</logic:notEqual>
																
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
