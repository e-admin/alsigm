<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/ispac-util.tld" prefix="ispac" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>

<ispac:hasFunction var="editionMode" functions="FUNC_INV_STAGES_EDIT" />

<script type="text/javascript" src='<ispac:rewrite href="../scripts/ispac-forms.js"/>'> </script>

<div id="navmenutitle">
	<bean:message key="form.fase.mainTitle"/>
</div>

<div id="navSubMenuTitle">
	<bean:message key="form.fase.subtitle"/>
</div>

<div id="navmenu" >
	<ul class="actionsMenuList">

		<logic:equal name="defaultForm" property="key" value="-1">
			<c:if test="${editionMode}">
			<li>
				<a href="javascript:submit('<%= request.getContextPath() + "/storeCTEntity.do"%>')">
					<nobr><bean:message key="forms.button.accept"/></nobr>
				</a>
			</li>
			</c:if>
			<li>
				<a href='<%=request.getContextPath() + "/showCTStagesList.do"%>'>
					<nobr><bean:message key="forms.button.cancel"/></nobr>
				</a>
			</li>
		</logic:equal>
		<logic:notEqual name="defaultForm" property="key" value="-1">
			<c:if test="${editionMode}">
			<li>
				<a href="javascript:submit('<%= request.getContextPath() + "/storeCTEntity.do"%>')">
					<nobr><bean:message key="forms.button.save"/></nobr>
				</a>
			</li>
			</c:if>
			<li>
				<a href='<%=request.getContextPath() + "/showCTStagesList.do"%>'>
					<nobr><bean:message key="forms.button.close"/></nobr>
				</a>
			</li>
			<li>
				<html:link action="showCTStageTasks" paramId="ctstageId" paramName="KeyId">
					<bean:message key="form.fase.buttonLabel.tasks"/>
	  			</html:link>
			</li>
			<li>
				<a href="javascript:submit('<%=request.getContextPath() + "/showComponentsUseList.do"%>')">
					<nobr><bean:message key="forms.button.use"/></nobr>
				</a>		
			</li>
			<c:if test="${editionMode}">
			<li>
				<a href="javascript:submit_confirm('<%= request.getContextPath() + "/deleteCTEntity.do"%>','<bean:message key="form.fase.confirm.delete"/>','<bean:message key="common.confirm"/>', '<bean:message key="common.message.ok"/>','<bean:message key="common.message.cancel"/>' )">
					<nobr><bean:message key="forms.button.delete"/></nobr>
				</a>		
			</li>
			</c:if>
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
																	<logic:notEqual name="defaultForm" property="key" value="-1"> 
																		<tr>
																			<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="8px"/></td>
																		</tr>
																		<tr>
																			<td height="20" class="formsTitle" width="1%">
																				<ispac:tooltipLabel labelKey="form.fase.propertyLabel.id" tooltipKey="form.fase.propertyLabel.id.tooltip"/>
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
																				<ispac:tooltipLabel labelKey="form.fase.propertyLabel.name" tooltipKey="form.fase.propertyLabel.name.tooltip"/>
																			</td>
																			<td height="20">
																				&nbsp;&nbsp;<html:text property="property(NOMBRE)" styleClass="inputSelectS" readonly="false" maxlength="250"/>&nbsp; <bean:message key="catalog.data.obligatory"/>
																				<div id="formErrors">
																					<html:errors property="property(NOMBRE)"/>
																				</div>
																			</td>
																		</tr>
																		<tr>
																			<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="8px"/></td>
																		</tr>
																	<logic:notEqual name="defaultForm" property="key" value="-1"> 
																		<tr>
																			<td height="20" class="formsTitle">
																				<ispac:tooltipLabel labelKey="form.fase.propertyLabel.order" tooltipKey="form.fase.propertyLabel.order.tooltip"/>
																			</td>
																			<td height="20">
																				&nbsp;&nbsp;<html:text property="property(ORDEN)" styleClass="inputReadOnly" size="5" readonly="false" maxlength="10"/>
																				<div id="formErrors">
																					<html:errors property="property(ORDEN)"/>
																				</div>
																			</td>
																		</tr>
																	</logic:notEqual>
																		<tr>
																			<td colspan="2" height="20" class="formsTitle">
																				<hr size="2"/>
																			</td>
																		</tr>
																		<tr>
																			<td height="20" class="formsTitle">
																				<ispac:tooltipLabel labelKey="form.fase.propertyLabel.author" tooltipKey="form.fase.propertyLabel.author.tooltip"/>
																			</td>
																			<td height="20">
																				&nbsp;&nbsp;<html:text property="property(AUTOR)" styleClass="inputReadOnly" size="50" readonly="true" maxlength="64"/>
																				<div id="formErrors">
																					<html:errors property="property(AUTOR)"/>
																				</div>
																			</td>
																		</tr>
																		<tr>
																			<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="8px"/></td>
																		</tr>
																		<tr>
																			<td height="20" class="formsTitle">
																				<ispac:tooltipLabel labelKey="form.fase.propertyLabel.creationDate" tooltipKey="form.fase.propertyLabel.creationDate.tooltip"/>
																			</td>
																			<td height="20">
																				&nbsp;&nbsp;<html:text property="property(FALTA)" styleClass="inputReadOnly" size="14" readonly="true" maxlength="23"/>
																				<div id="formErrors">
																					<html:errors property="property(FALTA)"/>
																				</div>
																			</td>
																		</tr>
																		<tr>
																			<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="8px"/></td>
																		</tr>
																		<tr>
																			<td height="20" class="formsTitle">
																				<ispac:tooltipLabel labelKey="form.fase.propertyLabel.code" tooltipKey="form.fase.propertyLabel.code.tooltip"/>
																			</td>
																			<td height="20">
																				&nbsp;&nbsp;<html:text property="property(COD_FASE)" styleClass="input" readonly="false" size="14" maxlength="16"/>
																				<div id="formErrors">
																					<html:errors property="property(COD_FASE)"/>
																				</div>
																			</td>
																		</tr>
																		<tr>
																			<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="8px"/></td>
																		</tr>
																		<tr>
																			<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="8px"/></td>
																		</tr>
																		<tr>
																			<td height="20" class="formsTitle">
																				<ispac:tooltipLabel labelKey="form.fase.propertyLabel.description" tooltipKey="form.fase.propertyLabel.description.tooltip"/>
																			</td>
																			<td height="20">
																				&nbsp;&nbsp;<html:textarea property="property(DESCRIPCION)" styleClass="input" readonly="false" cols="72" rows="4"
																					styleId="texta" onkeypress="javascript:maxlength('texta', 1024)"/>
																				<div id="formErrors">
																					<html:errors property="property(DESCRIPCION)"/>
																				</div>
																			</td>
																		</tr>
																		<tr>
																			<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="8px"/></td>
																		</tr>
																		<tr>
																			<td height="20" class="formsTitle">
																				<ispac:tooltipLabel labelKey="form.fase.propertyLabel.observations" tooltipKey="form.fase.propertyLabel.observations.tooltip"/>
																			</td>
																			<td height="20">
																				&nbsp;&nbsp;<html:textarea property="property(OBSERVACIONES)" styleClass="input" readonly="false" cols="72" rows="4"
																					styleId="observ" onkeypress="javascript:maxlength('observ', 500)"/>
																				<div id="formErrors">
																					<html:errors property="property(OBSERVACIONES)"/>
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