<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/ispac-util.tld" prefix="ispac" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>

<bean:define id="entapp" name="defaultForm" property="entityApp" />

<div class="tabButtons">
	<ispac:hasFunction functions="FUNC_INV_PROCEDURES_EDIT">
	<ul class="tabButtonList">
		<li>
			<a href="javascript:submit('<c:url value="/storeProcedure.do"/>')">
				<nobr><bean:message key="forms.button.save"/></nobr>
			</a>
		</li>
	</ul>
	</ispac:hasFunction>
</div>

<div class="tabContent">
<html:form action='/showProcedureEntity.do'>

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

	<!-- Información sobre los plazos -->
	<html:hidden property="property(SPAC_P_PLAZO:ID)"/>
	<html:hidden name="entapp" property="property(SPAC_P_PLAZO:UNITS)"/>
	<html:hidden name="entapp" property="property(SPAC_P_PLAZO:MAGNITUDE)"/>
	<html:hidden name="entapp" property="property(SPAC_P_PLAZO:BASEDATE_TYPE)"/>
	<html:hidden name="entapp" property="property(SPAC_P_PLAZO:BASEDATE_ENTITY_ID)"/>
	<html:hidden name="entapp" property="property(SPAC_P_PLAZO:BASEDATE_ENTITY_FIELD)"/>
	<html:hidden name="entapp" property="property(SPAC_P_PLAZO:BASEDATE_RULE_ID)"/>
	
	<input type="hidden" name="method" value="properties"/>

	<table width="100%" border="0" cellspacing="1" cellpadding="0">
		<tr>
			<td class="blank">
				<table width="100%" border="0" cellspacing="2" cellpadding="2">
					<tr>
						<td height="5px" colspan="3"><html:errors/></td>
					</tr>
					<tr>
						<td width="8"><img height="1" width="8px" src='<ispac:rewrite href="img/pixel.gif"/>'/></td>
						<td width="100%">
							<div style="display:block" id="page1" class="formtable">
								<!-- TABLA DE CAMPOS -->
								<table border="0" cellspacing="0" cellpadding="0" align="center" width="90%">
									<tr>
										<td>
											<table border="0" cellspacing="0" cellpadding="0" width="100%">
												<tr>
													<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="16px"/></td>
												</tr>

												<tr>
													<td height="20" class="formsTitle" width="1%">
														<ispac:tooltipLabel labelKey="form.pstage.propertyLabel.procedure" tooltipKey="form.pstage.propertyLabel.procedure.tooltip"/>
													</td>
													<td height="20">
														&nbsp;&nbsp;<html:text property="property(SPAC_P_PCD:NOMBRE)" styleClass="inputReadOnly" size="40" readonly="true" maxlength="250"/>
													</td>
												</tr>
												<tr>
													<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="8px"/></td>
												</tr>
												<tr>
													<td height="20" class="formsTitle">
														<ispac:tooltipLabel labelKey="form.pstage.propertyLabel.name" tooltipKey="form.pstage.propertyLabel.name.tooltip"/>
													</td>
													<td height="20">
														&nbsp;&nbsp;<html:text property="property(SPAC_P_FASES:NOMBRE)" styleClass="inputReadOnly" size="40" readonly="true" maxlength="250"/>
													</td>
												</tr>
												<tr>
													<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="8px"/></td>
												</tr>
												<tr>
													<td height="20" class="formsTitle">
														<ispac:tooltipLabel labelKey="form.pstage.propertyLabel.responsable" tooltipKey="form.pstage.propertyLabel.responsable.tooltip"/>
													</td>
													<td height="20">
														
														<%--
														&nbsp;&nbsp;<html:text name="entapp" property="property(PRESP_NAME:NOMBRE)" styleClass="inputReadOnly" size="40" readonly="true"/>
														<%String URL = "viewUsersManager.do?captionkey=usrmgr.responsable.caption";%>
														&nbsp;&nbsp;<ispac:linkframe id="USRMANAGER"
																					 styleClass="form_button_white"
																				     target="workframe"
																					 action='<%=URL%>'
																					 titleKey="catalog.mod"
																					 width="640"
																					 height="480"
																					 showFrame="true">
																		<ispac:parameter name="USRMANAGER"
																 						 id="property(SPAC_P_FASES:ID_RESP)"
																 						 property="UID"/>
																 		<ispac:parameter name="USRMANAGER"
																 						 id="property(PRESP_NAME:NOMBRE)" property="NAME"/>
																	</ispac:linkframe>
														--%>
														
														<bean:define id="_valueNombreResp" type="java.lang.String" name="entapp" property="property(PRESP_NAME:NOMBRE)" toScope="request"/>
														&nbsp;&nbsp;<nobr><ispac:htmlTextareaImageFrame property="property(PRESP_NAME:NOMBRE)"
																				readonly="true"
																				readonlyTag="false"
																				propertyReadonly="readonly"
																				styleClass="input"
																				styleClassReadonly="inputReadOnly"
																				cols="70"
																				rows="2"
																				id="USRMANAGER"
																				target="workframe"
																				action='<%="viewUsersManager.do?captionkey=usrmgr.responsable.caption"%>'
																				image="img/search-mg.gif" 
																				titleKeyLink="usrmgr.responsable" 
																				showFrame="true"
																				value='<%=_valueNombreResp%>'>
																				
																<ispac:parameter name="USRMANAGER" id="property(SPAC_P_FASES:ID_RESP)" property="UID"/>
																<ispac:parameter name="USRMANAGER" id="property(PRESP_NAME:NOMBRE)" property="RESPNAME"/>
																
														</ispac:htmlTextareaImageFrame></nobr>
													</td>
												</tr>
												<tr>
													<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="8px"/></td>
												</tr>
												<tr>
													<td height="20" class="formsTitle" width="1%">
														<ispac:tooltipLabel labelKey="form.pstage.propertyLabel.idResponsable" tooltipKey="form.pstage.propertyLabel.idResponsable.tooltip"/>
													</td>
													<td height="20">
														&nbsp;&nbsp;<html:text property="property(SPAC_P_FASES:ID_RESP)" size="40" styleClass="inputReadOnly" readonly="true" maxlength="250"/>
													</td>
												</tr>
												<tr>
													<td colspan="2"><hr/></td>
												</tr>
												<tr>
													<td height="20" class="formsTitle" width="1%">
														<ispac:tooltipLabel labelKey="form.pstage.propertyLabel.procedureId" tooltipKey="form.pstage.propertyLabel.procedureId.tooltip"/>
													</td>
													<td height="20">
														&nbsp;&nbsp;<html:text property="property(SPAC_P_PCD:ID)" styleClass="inputReadOnly" size="5" readonly="true" maxlength="10"/>
													</td>
												</tr>
												<tr>
													<td height="20" class="formsTitle" width="1%">
														<ispac:tooltipLabel labelKey="form.pstage.propertyLabel.stageId" tooltipKey="form.pstage.propertyLabel.stageId.tooltip"/>
													</td>
													<td height="20">
														&nbsp;&nbsp;<html:text property="property(SPAC_P_FASES:ID)" styleClass="inputReadOnly" size="5" readonly="true" maxlength="10"/>
													</td>
												</tr>
												<logic:notEqual name="defaultForm" property="property(SPAC_P_FASES:ID_CTFASE)" value="-1">								
												<tr>
													<td height="20" class="formsTitle" width="1%">
														<ispac:tooltipLabel labelKey="form.pstage.propertyLabel.catalogStageId" tooltipKey="form.pstage.propertyLabel.catalogStageId.tooltip"/>
													</td>
													<td height="20">
														&nbsp;&nbsp;<html:text property="property(SPAC_P_FASES:ID_CTFASE)" styleClass="inputReadOnly" size="5" readonly="true" maxlength="10"/>
													</td>
												</tr>
												</logic:notEqual>
												<tr>
													<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="15px"/></td>
												</tr>
											</table>
										</td>
									</tr>
								</table>
							</div>
						</td>
					</tr>
					<tr>
						<td height="5px"><img src='<ispac:rewrite href="img/pixel.gif"/>'/></td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
</html:form>
</div>