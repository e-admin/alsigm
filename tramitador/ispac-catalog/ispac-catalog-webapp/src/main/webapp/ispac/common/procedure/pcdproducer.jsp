<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/ispac-util.tld" prefix="ispac" %>

<bean:define id="pcd" name="PCD_ITEM"/>

<ispac:rewrite id="imgcalendar" href="img/calendar/"/>
<ispac:rewrite id="jscalendar" href="../scripts/calendar.js"/>
<ispac:rewrite id="buttoncalendar" href="img/calendar/calendarM.gif"/>


<script type="text/javascript" src='<ispac:rewrite href="../scripts/jquery-1.3.2.min.js"/>'></script>
<script type="text/javascript" src='<ispac:rewrite href="../scripts/jquery-ui-1.7.2.custom.min.js"/>'></script>
<script type="text/javascript" src='<ispac:rewrite href="../scripts/jquery.alerts.js"/>'></script>


<ispac:calendar-config imgDir='<%= imgcalendar %>' scriptFile='<%= jscalendar %>'/>

<script type="text/javascript">
<!--
	function selectOrgUnit() {
		document.defaultForm.method.value = "showOrgUnits";
		document.defaultForm.submit();
	}
//-->
</script>

<div id="move">
<div class="encabezado_ficha">
	<div class="titulo_ficha">
		<h4><bean:message key="procedure.card.producer.caption"/></h4>
		<div class="acciones_ficha">
			
				<a href="#" id="btnOk" class="btnOk"><bean:message key="forms.button.accept"/></a>
			
				<a href="#" id="btnCancel" class="btnCancel"><bean:message key="forms.button.cancel"/></a>
		</div>
	</div>
</div>




<html:form action="/producers.do">

	<input type="hidden" name="method" value="save"/>
	<input type="hidden" name="retEntityId" value='<c:out value="${param.retEntityId}"/>'/>
	<input type="hidden" name="retKeyId" value='<c:out value="${param.retKeyId}"/>'/>
	<input type="hidden" name="block" value='<c:out value="${param.block}"/>'/>
	<input type="hidden" name="property(COD_PCD)" value='<bean:write name="pcd" property="property(COD_PCD)"/>'/>
	<html:hidden property="property(ID_UNIDAD_TRAMITADORA)"/>

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
																<td colspan="3"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="8px"/></td>
															</tr>
															<tr>
																<td height="20" class="formsTitle" width="30%">
																	<ispac:tooltipLabel labelKey="procedure.card.producer.procedure" tooltipKey="procedure.card.producer.procedure.tooltip"/>
																</td>
																<td height="20" colspan="2">
																	&nbsp;&nbsp;<bean:write name="pcd" property="property(NOMBRE)"/>
																</td>
															</tr>
															<tr>
																<td colspan="3"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="8px"/></td>
															</tr>
															<tr valign="top">
																<td height="20" class="formsTitle">
																	<ispac:tooltipLabel labelKey="procedure.card.producer.orgunit" tooltipKey="procedure.card.producer.orgunit.tooltip"/>
																</td>
																<td height="20">
																	&nbsp;&nbsp;<html:textarea property="property(NOMBRE_UNIDAD_TRAMITADORA)" rows="3" cols="40" readonly="readonly" styleClass="inputReadOnly"/>
																	&nbsp;<a href="javascript:selectOrgUnit();"><img src='<ispac:rewrite href="img/search-mg.gif"/>' 
																			border="0" style="cursor:pointer" 
																			title='<bean:message key="procedure.card.producer.orgunit.select"/>'/>
																	</a>
																</td>
																<td></td>
															</tr>
															<tr>
																<td colspan="3"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="8px"/></td>
															</tr>
															<tr>
																<td height="20" class="formsTitle">
																	<ispac:tooltipLabel labelKey="procedure.card.producer.initdate" tooltipKey="procedure.card.producer.initdate.tooltip"/>
																</td>
																<td height="20" colspan="2">
																	&nbsp;&nbsp;<ispac:htmlTextCalendar property="property(FECHA_INI_PROD)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="14" maxlength="10" image='<%= buttoncalendar %>' format="dd/mm/yyyy" enablePast="true" />
																</td>
															</tr>
															<tr>
																<td colspan="3"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="8px"/></td>
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
	
</html:form>
</div>
<script>

	$(document).ready(function(){
		$("#move").draggable();
	});
	
	
	$("#btnOk").click(function() {
				submit('<%= request.getContextPath() + "/producers.do"%>');
	});
		
	$("#btnCancel").click(function() {
				parent.hideFrame('workframe','<ispac:rewrite page="wait.jsp"/>');
	});
</script>

