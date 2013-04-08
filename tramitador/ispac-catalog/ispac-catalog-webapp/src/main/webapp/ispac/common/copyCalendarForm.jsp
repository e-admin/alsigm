<%@ page language="java" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/ispac-util.tld" prefix="ispac" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>

<script type="text/javascript" src='<ispac:rewrite href="../scripts/jquery-1.3.2.min.js"/>'></script>
<script type="text/javascript" src='<ispac:rewrite href="../scripts/jquery-ui-1.7.2.custom.min.js"/>'></script>
<script type="text/javascript" src='<ispac:rewrite href="../scripts/jquery.alerts.js"/>'></script>

<div id="move">
<c:set var="entityId" value="${param.entityId}"/>
<jsp:useBean id="entityId" type="java.lang.String"/>

<c:set var="regId" value="${param.regId}"/>
<jsp:useBean id="regId" type="java.lang.String"/>

<div class="encabezado_ficha">
	<div class="titulo_ficha">
		<h4><bean:message key="form.calendar.clone.mainTitle"/></h4>
		<div class="acciones_ficha">
			
			<a href="#" id="btnOk" class="btnOk"><bean:message key="forms.button.accept"/></a>		
			<a href="#" id="btnCancel" class="btnCancel"><bean:message key="forms.button.cancel"/></a>
		</div>
	</div>
</div>

<div id="navSubMenuTitle">
</div>
<%--
<div id="navmenu">
	<ul class="actionsMenuList">
		<li>
				<a href="javascript:submit('<%= request.getContextPath() + "/showCTCalendar.do?method=createCopyCalendar&entityId=" + entityId + "&regId=" + regId %>')">
					<nobr><bean:message key="forms.button.accept"/></nobr>
				</a>
		</li>
		<li>
			<a href="javascript:parent.hideFrame('workframe','<ispac:rewrite page="wait.jsp"/>')">
				<bean:message key="forms.button.cancel"/>
			</a>
		</li>
	</ul>
</div>
--%>
<html:form action="/showCTCalendar.do">

	<input type="hidden" name="method" value="createCopyCalendar"/>
	<input type="hidden" name="entityId" value='<%=entityId%>'/>
	<input type="hidden" name="regId" value='<%=regId%>'/>
	
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
																			<td height="20" class="formsTitle">
																				<div id="form.calendar.clone.propertyLabel.name">
																					<ispac:tooltipLabel labelKey="form.calendar.clone.propertyLabel.name" tooltipKey="form.calendar.clone.propertyLabel.name.tooltip"/>
																				</div>
																			</td>
																			<td height="20" class="formsTitle">
																				&nbsp;&nbsp;<html:text property="property(NEW_CALENDAR_NAME)" styleClass="inputSelectS" readonly="false" maxlength="250"/>
																				&nbsp; <bean:message key="catalog.data.obligatory"/>
																				<div id="formErrors">
																					<html:errors property="property(NEW_CALENDAR_NAME)"/>
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
</div>
<script language='JavaScript' type='text/javascript'>

	function doSubmit() {
		document.calendarForm.submit();
	}
	
		$(document).ready(function(){
		$("#move").draggable();
		$("#btnOk").click(function() {
				submit('<%= request.getContextPath() + "/showCTCalendar.do?method=createCopyCalendar&entityId=" + entityId + "&regId=" + regId %>');
		});
			 
		$("#btnCancel").click(function() {
				parent.hideFrame('workframe','<ispac:rewrite page="wait.jsp"/>')
		});
	});

//--></script>