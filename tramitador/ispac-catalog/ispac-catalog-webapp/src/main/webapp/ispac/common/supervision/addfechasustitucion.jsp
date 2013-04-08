<%@ page language="java" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/ispac-util.tld" prefix="ispac" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>

<ispac:rewrite id="imgcalendar" href="img/calendar/"/>
<ispac:rewrite id="jscalendar" href="../scripts/calendar.js"/>
<ispac:rewrite id="buttoncalendar" href="img/calendar/calendarM.gif"/>
<script type="text/javascript" src='<ispac:rewrite href="../scripts/jquery-1.3.2.min.js"/>'></script>
<script type="text/javascript" src='<ispac:rewrite href="../scripts/jquery-ui-1.7.2.custom.min.js"/>'></script>
<script type="text/javascript" src='<ispac:rewrite href="../scripts/jquery.alerts.js"/>'></script>
<ispac:calendar-config imgDir='<%= imgcalendar %>' scriptFile='<%= jscalendar %>'/>

<script language="javascript">
//<!--
	function doSubmit() {
		document.getElementById('formSustitucion').submit();
	}
	
	$(document).ready(function(){
		$("#move").draggable();
	});
//-->

	function hide(){
	parent.hideFrame('workframesearch','<ispac:rewrite page="wait.jsp"/>');
	parent.hideFrame('workframe','<ispac:rewrite page="wait.jsp"/>');
	}
</script>



<div id="move">

<div class="encabezado_ficha">
	<div class="titulo_ficha">
		<h4><bean:message key="form.sustitucion.periodo.mainTitle"/></h4>
			<div class="acciones_ficha">
			
			<ispac:hasFunction functions="FUNC_PERM_EDIT">
			<a  class="btnOk" href="javascript:doSubmit()">
				<bean:message key="forms.button.accept"/>
			</a>
			</ispac:hasFunction>
			
			<a  class="btnCancel" href="javascript:hide();">
				<bean:message key="forms.button.cancel"/>
			</a>
		
		</div>
	</div>
</div>





<div id="navmenu">
	<ispac:hasFunction functions="FUNC_PERM_EDIT">
	<ul class="actionsMenuList">

		<logic:notEmpty name="sustitutionsForm" property="property(ID_FECH_SUSTITUCION)">
		
			<%-- Enlace a la pantalla de añadir sustitutos --%>
			<c:url value="gestionSustituciones.do" var="_showSustitutos">
				<c:param name="method" value="showSustitutes"/>
				<c:param name="uid">
					<bean:write name="uid"/>
				</c:param>
				<logic:present name="uidGroup">
					<c:param name="uidGroup">
						<bean:write name="uidGroup" />
					</c:param>
				</logic:present>
				<c:param name="regId">
					<bean:write name="sustitutionsForm" property="property(ID_FECH_SUSTITUCION)"/>
				</c:param>
				<c:param name="captionkey" value="catalog.sustitucion.title"/>
			</c:url>
			<jsp:useBean id="_showSustitutos" type="java.lang.String"/>	
		
			<li>
				<a href='<ispac:rewrite action='<%=_showSustitutos%>'/>'>
					<bean:message key="catalog.sustitucion.button"/>
				</a>
			</li>
			
		</logic:notEmpty>
	</ul>
	</ispac:hasFunction>
</div>

<c:url value="gestionSustituciones.do" var="_gestionSustitutciones">
	<c:param name="method" value="addFechaSustitucion"/>
	<logic:present name="uid">
		<c:param name="uid">
			<bean:write name="uid"/>
		</c:param>
	</logic:present>
	<logic:present name="uidGroup">
		<c:param name="uidGroup">
			<bean:write name="uidGroup" />
		</c:param>
	</logic:present>
</c:url>
<jsp:useBean id="_gestionSustitutciones" type="java.lang.String"/>

<html:form styleId="formSustitucion" action='<%=_gestionSustitutciones%>'>

	<html:hidden property="property(ID_FECH_SUSTITUCION)"/>
	<html:hidden property="property(UID_SUSTITUIDO)"/>
	<html:hidden property="property(UIDS_SUSTITUTOS)"/>

	<table width="100%" border="0" cellspacing="1" cellpadding="0">
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
												<html:errors />
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
															<tr>
																<td height="20" class="formsTitle">
																	<ispac:tooltipLabel labelKey="form.sustitucion.propertyLabel.fechaInicio" tooltipKey="form.sustitucion.propertyLabel.fechaInicio.tooltip"/>
																</td>
																<td>										
																	&nbsp;&nbsp;<ispac:htmlTextCalendar formId="formSustitucion" property="property(FECHA_INICIO)" readonly="false" 
																	propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" 
																	size="14" maxlength="10" image='<%= buttoncalendar %>' format="dd/mm/yyyy" enablePast="true" fixedY="140" />
																	&nbsp; <bean:message key="catalog.data.obligatory"/>
																</td>
															</tr>
															<tr>
																<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="8px"/></td>
															</tr>
															<tr>
																<td height="20" class="formsTitle">
																	<ispac:tooltipLabel labelKey="form.sustitucion.propertyLabel.fechaFin" tooltipKey="form.sustitucion.propertyLabel.fechaFin.tooltip"/>
																</td>
																<td>										
																	&nbsp;&nbsp;<ispac:htmlTextCalendar formId="formSustitucion" property="property(FECHA_FIN)" readonly="false" 
																	propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" 
																	size="14" maxlength="10" image='<%= buttoncalendar %>' format="dd/mm/yyyy" enablePast="true" fixedY="160" />
																	&nbsp; <bean:message key="catalog.data.obligatory"/>
																</td>
															</tr>
															<tr>
																<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="8px"/></td>
															</tr>
															<tr>
																<td height="20" class="formsTitle">
																	<div id="form.calendar.holiday.propertyLabel.name">
																		<ispac:tooltipLabel labelKey="form.sustitucion.propertyLabel.description" tooltipKey="form.sustitucion.propertyLabel.description.tooltip"/>
																	</div>
																</td>
																<td height="20" class="formsTitle">
																	&nbsp;&nbsp;<html:text property="property(DESCRIPCION)" styleClass="inputSelectS" readonly="false" maxlength="250"/>
																	&nbsp; <bean:message key="catalog.data.obligatory"/>
																</td>
															</tr>
															<tr>
																<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="8px"/></td>
															</tr>
														</table>
													</td>
												</tr>
												<tr>
													<td height="15px"><img src='<ispac:rewrite href="img/pixel.gif"/>'/></td>
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

</html:form>
</div>
