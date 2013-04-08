<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/ispac-util.tld" prefix="ispac" %>

<bean:define name="defaultForm" property="property(PUB_HITOS_ACTIVOS:ID_HITO)" id="milestoneId"/>
<bean:define name="defaultForm" property="property(PUB_HITOS_ACTIVOS:ID_APLICACION)" id="applicationId"/>
<bean:define name="defaultForm" property="property(PUB_HITOS_ACTIVOS:ID_SISTEMA)" id="systemId"/>
<bean:define name="defaultForm" property="property(PUB_HITOS_ACTIVOS:ID_OBJETO)" id="objectId"/>
<% String queryString = "?milestoneId=" + milestoneId + "&applicationId=" + applicationId + "&systemId=" + systemId + "&objectId=" + objectId;%>

<script language='JavaScript' type='text/javascript'><!--

	function deletePubErrorMilestone() {
		jConfirm('<bean:message key="form.pubErrorMilestone.confirm.delete"/>', '<bean:message key="common.confirm"/>', '<bean:message key="common.message.ok"/>' , '<bean:message key="common.message.cancel"/>', function(r) {
			if(r){
			document.location = '<%=request.getContextPath() + "/deleteErrorMilestone.do" + queryString%>';
			}
								
		});	
	}

//--></script>

<div id="navmenutitle">
	<bean:message key="form.pubErrorMilestone.mainTitle"/>
</div>
<div id="navSubMenuTitle">
</div>

<div id="navmenu">
	<ul class="actionsMenuList">
		<li>		
			<a href='<%=request.getContextPath() + "/showPubErrorMilestonesList.do"%>'>
				<bean:message key="forms.button.close"/>
			</a>
		</li>
		<ispac:hasFunction functions="FUNC_PUB_MILESTONES_EDIT">
		<li>
			<a href='<%=request.getContextPath() + "/reactivateMilestone.do" + queryString%>'>
				<bean:message key="form.pubErrorMilestone.button.reactivate"/>
			</a>
		</li>
		<li>
			<a href="javascript: deletePubErrorMilestone();">
				<bean:message key="forms.button.delete"/>
			</a>
		</li>
		</ispac:hasFunction>
	</ul>
</div>
<html:form action='/showPubErrorMilestone.do'>

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
	
	<html:hidden property="property(PUB_HITOS_ACTIVOS:ID_APLICACION)"/>

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
													<div style="display:block" id="page1">
														<table border="0" cellspacing="0" cellpadding="0" align="center" width="90%">
															<tr>
																<td>
																	<table border="0" cellspacing="0" cellpadding="0" width="100%">
																	
																		<tr>
																			<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="8px"/></td>
																		</tr>													
																		<tr>
																			<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="8px"/></td>
																		</tr>
																		<tr>
																			<td height="20" class="formsTitle" width="1%">
																				<ispac:tooltipLabel labelKey="form.pubErrorMilestone.propertyLabel.milestone" tooltipKey="form.pubErrorMilestone.propertyLabel.milestone.tooltip"/>
																			</td>
																			<td height="20">
																				&nbsp;&nbsp;<html:text property="property(PUB_HITOS_ACTIVOS:ID_HITO)" size="30" styleClass="inputReadOnly" readonly="true" />
																			</td>
																		</tr>
																		
																		<tr>
																			<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="8px"/></td>
																		</tr>
																		<tr>
																			<td height="20" class="formsTitle" width="1%">
																				<ispac:tooltipLabel labelKey="form.pubErrorMilestone.propertyLabel.application" tooltipKey="form.pubErrorMilestone.propertyLabel.application.tooltip"/>
																			</td>
																			<td height="20">
																				&nbsp;&nbsp;<html:text property="property(PUB_APLICACIONES_HITOS:NOMBRE)" size="80" styleClass="inputReadOnly" readonly="true" />
																			</td>
																		</tr>
																		
																		<tr>
																			<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="8px"/></td>
																		</tr>
																		<tr>
																			<td height="20" class="formsTitle" width="1%">
																				<ispac:tooltipLabel labelKey="form.pubErrorMilestone.propertyLabel.system" tooltipKey="form.pubErrorMilestone.propertyLabel.system.tooltip"/>
																			</td>
																			<td height="20">
																				&nbsp;&nbsp;<html:text property="property(PUB_HITOS_ACTIVOS:ID_SISTEMA)" size="80" styleClass="inputReadOnly" readonly="true" />
																			</td>
																		</tr>
																		
																		<tr>
																			<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="8px"/></td>
																		</tr>
																		<tr>
																			<td height="20" class="formsTitle" width="1%">
																				<ispac:tooltipLabel labelKey="form.pubErrorMilestone.propertyLabel.object" tooltipKey="form.pubErrorMilestone.propertyLabel.object.tooltip"/>
																			</td>
																			<td height="20">
																				&nbsp;&nbsp;<html:text property="property(PUB_HITOS_ACTIVOS:ID_OBJETO)" size="80" styleClass="inputReadOnly" readonly="true" />
																			</td>
																		</tr>
																		
																		<tr>
																			<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="8px"/></td>
																		</tr>
																		<tr>
																			<td height="20" class="formsTitle" width="1%">
																				<ispac:tooltipLabel labelKey="form.pubErrorMilestone.propertyLabel.error.code" tooltipKey="form.pubErrorMilestone.propertyLabel.error.code.tooltip"/>
																			</td>
																			<td height="20">
																				&nbsp;&nbsp;<html:text property="property(PUB_ERRORES:ID_ERROR)" size="5" styleClass="inputReadOnly" readonly="true" />
																			</td>
																		</tr>
																		
																		<tr>
																			<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="8px"/></td>
																		</tr>
																		<tr>
																			<td height="20" class="formsTitle" width="1%">
																				<ispac:tooltipLabel labelKey="form.pubErrorMilestone.propertyLabel.description" tooltipKey="form.pubErrorMilestone.propertyLabel.description.tooltip"/>
																			</td>
																			<td height="20">
																				&nbsp;&nbsp;<html:textarea property="property(PUB_ERRORES:DESCRIPCION)" styleClass="inputReadOnly" readonly="true" cols="80" rows="3" />
																			</td>
																		</tr>
																		
																		<tr>
																			<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="20px"/></td>
																		</tr>
																		<tr>
																			<td height="20" class="formsTitle" width="1%">
																				<ispac:tooltipLabel labelKey="form.pubErrorMilestone.propertyLabel.procedure" tooltipKey="form.pubErrorMilestone.propertyLabel.procedure.tooltip"/>
																			</td>
																			<td height="20">
																				&nbsp;&nbsp;<html:text property="property(SPAC_P_PROCEDIMIENTOS:NOMBRE)" size="80" styleClass="inputReadOnly" readonly="true" />
																			</td>
																		</tr>

																		<tr>
																			<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="8px"/></td>
																		</tr>																
																		<tr>
																			<td height="20" class="formsTitle" width="1%">
																				<ispac:tooltipLabel labelKey="form.pubErrorMilestone.propertyLabel.stage" tooltipKey="form.pubErrorMilestone.propertyLabel.stage.tooltip"/>
																			</td>
																			<td height="20">
																				&nbsp;&nbsp;<html:text property="property(SPAC_P_FASES:NOMBRE)" size="80" styleClass="inputReadOnly" readonly="true" />
																			</td>
																		</tr>
																																				
																		<tr>
																			<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="8px"/></td>
																		</tr>
																		<tr>
																			<td height="20" class="formsTitle" width="1%">
																				<ispac:tooltipLabel labelKey="form.pubErrorMilestone.propertyLabel.task" tooltipKey="form.pubErrorMilestone.propertyLabel.task.tooltip"/>
																			</td>
																			<td height="20">
																				&nbsp;&nbsp;<html:text property="property(SPAC_P_TRAMITES:NOMBRE)" size="80" styleClass="inputReadOnly" readonly="true" />
																			</td>
																		</tr>

																		<tr>
																			<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="8px"/></td>
																		</tr>
																		<tr>
																			<td height="20" class="formsTitle" width="1%">
																				<ispac:tooltipLabel labelKey="form.pubErrorMilestone.propertyLabel.typeDoc" tooltipKey="form.pubErrorMilestone.propertyLabel.typeDoc.tooltip"/>
																			</td>
																			<td height="20">
																				&nbsp;&nbsp;<html:text property="property(SPAC_CT_TPDOC:NOMBRE)" size="80" styleClass="inputReadOnly" readonly="true" />
																			</td>
																		</tr>
																		
																		<tr>
																			<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="8px"/></td>
																		</tr>
																		<tr>
																			<td height="20" class="formsTitle" width="1%">
																				<ispac:tooltipLabel labelKey="form.pubErrorMilestone.propertyLabel.event" tooltipKey="form.pubErrorMilestone.propertyLabel.event.tooltip"/>
																			</td>
																			<td height="20">
																				&nbsp;&nbsp;<html:text property="entityApp.property(EVENT_NAME)" size="80" styleClass="inputReadOnly" readonly="true" />
																			</td>
																		</tr>
																		
																		<tr>
																			<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="8px"/></td>
																		</tr>
																		<tr>
																			<td height="20" class="formsTitle" width="1%">
																				<ispac:tooltipLabel labelKey="form.pubErrorMilestone.propertyLabel.info" tooltipKey="form.pubErrorMilestone.propertyLabel.info.tooltip"/>
																			</td>
																			<td height="20">
																				&nbsp;&nbsp;<html:text property="entityApp.property(INFO_NAME)" size="80" styleClass="inputReadOnly" readonly="true" />
																			</td>
																		</tr>
																		
																		<tr>
																			<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="8px"/></td>
																		</tr>
																		<tr>
																			<td height="20" class="formsTitle" width="1%">
																				<ispac:tooltipLabel labelKey="form.pubErrorMilestone.propertyLabel.ip" tooltipKey="form.pubErrorMilestone.propertyLabel.ip.tooltip"/>
																			</td>
																			<td height="20">
																				&nbsp;&nbsp;<html:text property="property(PUB_HITOS_ACTIVOS:IP_MAQUINA)" size="80" styleClass="inputReadOnly" readonly="true" />
																			</td>
																		</tr>
																		
																		<tr>
																			<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="8px"/></td>
																		</tr>
																		<tr>
																			<td height="20" class="formsTitle" width="1%">
																				<ispac:tooltipLabel labelKey="form.pubErrorMilestone.propertyLabel.date" tooltipKey="form.pubErrorMilestone.propertyLabel.date.tooltip"/>
																			</td>
																			<td height="20">
																				&nbsp;&nbsp;<html:text property="entityApp.property(FECHA)" size="80" styleClass="inputReadOnly" readonly="true" />
																			</td>
																		</tr>
																		
																		<tr>
																			<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="8px"/></td>
																		</tr>
																		<tr>
																			<td height="20" class="formsTitle" width="1%">
																				<ispac:tooltipLabel labelKey="form.pubErrorMilestone.propertyLabel.infoaux" tooltipKey="form.pubErrorMilestone.propertyLabel.infoaux.tooltip"/>
																			</td>
																			<td height="20">
																				&nbsp;&nbsp;<html:textarea property="property(PUB_HITOS_ACTIVOS:INFO_AUX)" styleClass="inputReadOnly" readonly="true" cols="80" rows="10" />
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