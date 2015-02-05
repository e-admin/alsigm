<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/ispac-util.tld" prefix="ispac"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ page import='ieci.tdw.ispac.api.ISPACEntities'%>

<script language='JavaScript' type='text/javascript'><!--

	function save() {
	
		document.defaultForm.target = "ParentWindow";
		document.defaultForm.action = "storeEntity.do";
		
		<c:if test="${!empty param.form}">
			document.defaultForm.action = document.defaultForm.action + "?form=" + '<c:out value='${param.form}'/>';
		</c:if>
		
		<logic:notEmpty scope="request" name="displayTagOrderParams">
		
			<c:choose>
				<c:when test="${!empty param.form}">
					document.defaultForm.action = document.defaultForm.action + "&";
				</c:when>
				<c:otherwise>
					document.defaultForm.action = document.defaultForm.action + "?";
				</c:otherwise>
			</c:choose>

			document.defaultForm.action = document.defaultForm.action + '<bean:write scope="request" name="displayTagOrderParams" filter="false"/>';
		</logic:notEmpty>
		
		document.defaultForm.submit();
		ispac_needToConfirm = true;
	}

//--></script>

<ispac:rewrite id="imgcalendar" href="img/calendar/"/>
<ispac:rewrite id="jscalendar" href="../scripts/calendar.js"/>
<ispac:rewrite id="buttoncalendar" href="img/calendar/calendarM.gif"/>
<ispac:calendar-config imgDir='<%= imgcalendar %>' scriptFile='<%= jscalendar %>'/>

<html:form action="storeEntity.do">

	<!-- Solapa que se muestra -->
	<html:hidden property="block" value="1"/>
	
	<!-- Nombre de Aplicación -->
	<html:hidden property="entityAppName"/>
	<!-- Identificador de la entidad -->
	<html:hidden property="entity"/>
	<!-- Identificador del registro -->
	<html:hidden property="key"/>
	<!-- Indicador de sólo lectura -->
	<html:hidden property="readonly"/>
	
	<c:set var="ENTITY_NULLREGKEYID" scope="page"><%=ISPACEntities.ENTITY_NULLREGKEYID%></c:set>

	<table cellpadding="0" cellspacing="0" width="100%">
	
		<tr>
			<td>
			
				<table class="boxTab" width="100%" border="0" cellspacing="0" cellpadding="0">
				
					<tr>
						<td class="title" height="28px">
						
							<table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%">
							
								<tr>
									<td>
									
										<table align="center" cellpadding="0" cellspacing="0" border="0" width="100%">
										
											<tr>
												<td>
												
													<!-- COMIENZO DE LAS ACCIONES -->
													<table cellpadding="0" cellspacing="0" border="0" height="100%">
													
														<tr>
															<td width="4px" height="28px"><img height="1" width="4px" src='<ispac:rewrite href="img/pixel.gif"/>'/></td>
																		
															<logic:equal value="false" property="readonly" name="defaultForm">
	
																<!--ACCION NUEVO -->
																	<td class="formaction" height="28px" width="70px">
																		<c:url value="${urlExpDisplayTagOrderParams}" var="link">
																			<c:if test="${!empty param.stageId}">
																				<c:param name="stageId" value='${param.stageId}'/>
																			</c:if>
																			<c:if test="${!empty param.taskId}">
																				<c:param name="taskId" value='${param.taskId}'/>
																			</c:if>
																			<c:if test="${!empty param.activityId}">
																				<c:param name="activityId" value='${param.activityId}'/>
																			</c:if>
																			<c:param name="entity" value="${defaultForm.entity}"/>
																			<c:param name="key" value="${ENTITY_NULLREGKEYID}"/>
																			<c:if test="${!empty param.form}">
																				<c:param name="form" value='${param.form}'/>
																			</c:if>
																		</c:url>
<div id="blockNew" >												
																		<a href='<c:out value="${link}"/>' class="formaction"><bean:message key="forms.button.new"/></a>
</div>

																	</td>
																<!--FIN ACCION NUEVO -->
																
																<td height="28px"><img height="1" src='<ispac:rewrite href="img/pixel.gif"/>'/></td>
																
																
																<!--ACCION SALVAR -->
																<td class="formaction" height="28px" width="70px">
<div id="blockSave">												
																	<a onclick="javascript: ispac_needToConfirm = false;" href="javascript:save();" class="formaction">
																		<bean:message key="forms.button.save"/>
																	</a>
</div>
																</td>
																<!--FIN ACCION SALVAR -->
																
																<!--ACCION ELIMINAR -->
																<c:if test="${defaultForm.key != ENTITY_NULLREGKEYID}">
																	<td height="28px"><img height="1" src='<ispac:rewrite href="img/pixel.gif"/>'/></td>
																	
																	<td class="formaction" height="28px" width="70px">
																	
																		<c:url value='deleteRegEntity.do' var="deleteLink">
																			<c:if test="${!empty param.stageId}">
																				<c:param name="stageId" value='${param.stageId}'/>
																			</c:if>
																			<c:if test="${!empty param.taskId}">
																				<c:param name="taskId" value='${param.taskId}'/>
																			</c:if>
																			<c:if test="${!empty param.activityId}">
																				<c:param name="activityId" value='${param.activityId}'/>
																			</c:if>
																			<c:param name="entity" value="${defaultForm.entity}"/>
																			<c:param name="key" value="${defaultForm.key}"/>
																			<c:if test="${!empty param.form}">
																				<c:param name="form" value='${param.form}'/>
																			</c:if>
																		</c:url>
<div id="blockDelete">												
																		
																		<logic:notEmpty scope="request" name="displayTagOrderParams">
																		
																			<a onclick="javascript: ispac_needToConfirm = false;" href="javascript: _confirm('<c:out value="${deleteLink}"/>' + '&' + '<bean:write scope="request" name="displayTagOrderParams" filter="false"/>', '<bean:message key="ispac.action.entity.delete"/>', true,  '<bean:message key="common.confirm"/>','<bean:message key="common.message.ok"/>','<bean:message key="common.message.cancel"/>');" class="formaction">
																				<bean:message key="forms.button.delete"/>
																			</a>
																			
																		</logic:notEmpty>
																		
																		<logic:empty scope="request" name="displayTagOrderParams">
																		
																			<a onclick="javascript: ispac_needToConfirm = false;" href="javascript: _confirm('<c:out value="${deleteLink}"/>', '<bean:message key="ispac.action.entity.delete"/>', true, '<bean:message key="common.confirm"/>','<bean:message key="common.message.ok"/>','<bean:message key="common.message.cancel"/>');" class="formaction">
																				<bean:message key="forms.button.delete"/>
																			</a>
																			
																		</logic:empty>
</div>																	
																		
																	</td>
																</c:if>
																<!--FIN ACCION ELIMINAR -->
																
															</logic:equal>
															
															<td height="28px"><img height="1" src='<ispac:rewrite href="img/pixel.gif"/>'/></td>
															
															<!-- BOTON DE CERRAR CUANDO SE ABRE EL FORMULARIO EN EL WORKFRAME -->
															<c:if test="${(!empty param.form) && (param.form == 'single')}">

																<td class="formaction" height="28px" width="100%" style="text-align:right">
																	<%-- Si el workframe se abre con needToConfirm="true"
																		 cuando el formulario se está editando es necesario refrescar la página principal al cerrarlo
																		 cuando el formulario es de sólo lectura sólo es necesario cerrarlo y no hace falta refrescar
																	<logic:equal value="false" property="readonly" name="defaultForm">
																		<input type="button" value='<bean:message key="common.message.close"/>' class="form_button_white" onclick='javascript:top.ispac_needToConfirm=false;<ispac:hideframe refresh="true"/>'/>
																	</logic:equal>
																	<logic:notEqual value="false" property="readonly" name="defaultForm">
																		<input type="button" value='<bean:message key="common.message.close"/>' class="form_button_white" onclick='<ispac:hideframe/>'/>
																	</logic:notEqual>
																	--%>
																	<!-- Si el workframe se abre con needToConfirm="false"
																		 sólo es necesario cerrar el formulario y no hace falta refrescar la página principal -->
																	<input type="button" value='<bean:message key="common.message.close"/>' class="form_button_white" onclick='javascript:ispac_needToConfirm=false;<ispac:hideframe/>'/>
																</td>
																<td width="4px" height="28px"><img height="1" width="4px" src='<ispac:rewrite href="img/pixel.gif"/>'/></td>
																
															</c:if>
																
														</tr>
														
													</table>
													<!-- FINAL DE LAS ACCIONES -->
													
												</td>
											</tr>
											
										</table>
										
									</td>
								</tr>
								
							</table>
							
						</td>
					</tr>
					
					<!-- FORMULARIO -->
					<tr>
						<td class="blank">
						
							<table width="100%" border="0" cellspacing="2" cellpadding="2">
							
								<tr>
									<td height="5px" colspan="3"><html:errors/></td>
								</tr>
								<tr>
									<td width="8"><img height="1" width="8px" src='<ispac:rewrite href="img/pixel.gif"/>'/></td>
									<td width="100%">
