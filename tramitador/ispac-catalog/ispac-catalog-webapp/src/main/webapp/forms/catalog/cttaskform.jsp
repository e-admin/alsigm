<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/ispac-util.tld" prefix="ispac" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>

<ispac:hasFunction var="editionMode" functions="FUNC_INV_TASKS_EDIT" />

<div id="navmenutitle">
	<bean:message key="form.task.mainTitle"/>
</div>
<div id="navSubMenuTitle">
	<bean:message key="form.task.subtitle"/>
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
				<a href='<%=request.getContextPath() + "/showCTTasksList.do"%>'>
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
				<a href='<%=request.getContextPath() + "/showCTTasksList.do"%>'>
					<nobr><bean:message key="forms.button.close"/></nobr>
				</a>	       	
			</li>	
		 	<logic:empty  name="defaultForm" property="property(ID_SUBPROCESO)">															
			<li>
				
				<html:link action="showCTTaskTPDocs.do" paramId="cttaskId" paramName="KeyId">
					<bean:message key="form.task.button.documents"/>
				</html:link>
			</li>
			</logic:empty>
			
			<li>
				<a href="javascript:submit('<%=request.getContextPath() + "/showComponentsUseList.do"%>')">
					<nobr><bean:message key="forms.button.use"/></nobr>
				</a>		
			</li>
			<c:if test="${editionMode}">
			<li>
				<a href="javascript:submit_confirm('<%= request.getContextPath() + "/deleteCTEntity.do"%>','<bean:message key="form.task.confirm.delete"/>','<bean:message key="common.confirm"/>', '<bean:message key="common.message.ok"/>','<bean:message key="common.message.cancel"/>' )">
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
																				<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="16px"/></td>
																			</tr>
																			<tr>
																				<td height="20" class="formsTitle" width="1%">
																					<ispac:tooltipLabel labelKey="form.task.propertyLabel.id" tooltipKey="form.task.propertyLabel.id.tooltip"/>
																				</td>
																				<td height="20">
																					&nbsp;&nbsp;<html:text property="property(ID)" styleClass="inputReadOnly" size="5" readonly="true" maxlength="10"/>
																				</td>
																			</tr>
																		</logic:notEqual>
																		<tr>
																			<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="8px"/></td>
																		</tr>
																		<tr>
																			<td height="20" class="formsTitle">
																				<ispac:tooltipLabel labelKey="form.task.propertyLabel.name" tooltipKey="form.task.propertyLabel.name.tooltip"/>
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
																		<tr>
																			<td colspan="2" height="20" class="formsTitle">
																				<hr size="2"/>
																			</td>
																		</tr>
																		<tr>
																			<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="8px"/></td>
																		</tr>
																		<tr>
																			<td height="20" class="formsTitle">
																				<ispac:tooltipLabel labelKey="form.task.propertyLabel.type" tooltipKey="form.task.propertyLabel.type.tooltip"/>
																			</td>
																			<td height="20">
																				&nbsp;&nbsp;<nobr><ispac:htmlTextImageFrame property="property(TIPO)"
																										readonly="true"
																										readonlyTag="false"
																										propertyReadonly="readonly"
																										styleClass="input"
																										styleClassReadonly="inputReadOnly"
																										size="30"
																										id="SELECT_TIPO"
																										target="workframe"
																										action="selectObject.do?codetable=SPAC_VLDTBL_TIPOS_TRAM&codefield=ID&valuefield=VALOR&caption=select.taskType.caption&sqlquery=where vigente = 1&decorator=/formatters/entities/selectSimpleformatter.xml"
																										image="img/search-mg.gif" 
																										titleKeyLink="select.taskType" 
																										showFrame="true">
																																  	  
																					<ispac:parameter name="SELECT_TIPO" id="property(TIPO)" property="VALOR"/>

																				</ispac:htmlTextImageFrame></nobr>

																				<%--&nbsp;&nbsp;<html:text property="property(TIPO)" styleClass="inputSelectS" readonly="false" maxlength="32"/> --%>
																				<div id="formErrors">
																					<html:errors property="property(TIPO)"/>
																				</div>
																			</td>
																		</tr>
																		<logic:empty  name="defaultForm" property="entityApp.property(EXISTE_TIPOS_DOCS_ASOCIADOS)">	 
																		<tr>
																			<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="8px"/></td>
																		</tr>
																		<tr>
																			<td height="20" class="formsTitle">
																				<ispac:tooltipLabel labelKey="form.task.propertyLabel.subprocedure" tooltipKey="form.task.propertyLabel.subprocedure.tooltip"/>
																			</td>
																			<td height="20">
																				<html:hidden property="property(ID_SUBPROCESO)" />
																				&nbsp;&nbsp;<nobr><ispac:htmlTextImageFrame property="property(SPAC_P_PROCEDIMIENTOS:NOMBRE)"
																										readonly="true"
																										readonlyTag="false"
																										propertyReadonly="readonly"
																										styleClass="input"
																										styleClassReadonly="inputReadOnly"
																										size="80"
																										id="SELECT_ID_SUBPROCESO"
																										target="workframe"
																										action="selectObject.do?codetable=SPAC_P_PROCEDIMIENTOS&codefield=ID&valuefield=NOMBRE&caption=select.subprocedure.caption&sqlquery=WHERE TIPO=2 AND ESTADO=2&decorator=/formatters/entities/selectpcdformatter.xml"
																										image="img/search-mg.gif" 
																										titleKeyLink="select.subprocedure" 
																										showFrame="true"
																										>
																										
																																  	  
																					<ispac:parameter name="SELECT_ID_SUBPROCESO" id="property(ID_SUBPROCESO)" property="ID"/>
																					<ispac:parameter name="SELECT_ID_SUBPROCESO" id="property(SPAC_P_PROCEDIMIENTOS:NOMBRE)" property="NOMBRE"/>

																				</ispac:htmlTextImageFrame></nobr>
																				<div id="formErrors">
																					<html:errors property="property(ID_SUBPROCESO)"/>
																				</div>
																			</td>
																		</tr>
																		</logic:empty>
																		<tr>
																			<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="8px"/></td>
																		</tr>
																		<tr>
																			<td height="20" class="formsTitle">
																				<ispac:tooltipLabel labelKey="form.task.propertyLabel.code" tooltipKey="form.task.propertyLabel.code.tooltip"/>
																			</td>
																			<td height="20">
																				&nbsp;&nbsp;<html:text property="property(COD_TRAM)" styleClass="input" size="10" readonly="false" maxlength="16"/>
																				<div id="formErrors">
																					<html:errors property="property(COD_TRAM)"/>
																				</div>
																			</td>
																		</tr>
																		
																		<%--
																		<tr>
																			<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="8px"/></td>
																		</tr>
																		<tr>
																			<td height="20" class="formsTitle">
																				<ispac:tooltipLabel labelKey="form.task.propertyLabel.order" tooltipKey="form.task.propertyLabel.order.tooltip"/>
																			</td>
																			<td height="20">
																				&nbsp;&nbsp;<html:text property="property(ORDENACION)" styleClass="input" size="10" readonly="false" maxlength="2"/>
																				<div id="formErrors">
																					<html:errors property="property(ORDENACION)"/>
																				</div>
																			</td>
																		</tr>
																		--%>
																		
																		<tr>
																			<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="8px"/></td>
																		</tr>
																		<tr>
																			<td height="20" class="formsTitle">
																				<ispac:tooltipLabel labelKey="form.task.propertyLabel.author" tooltipKey="form.task.propertyLabel.author.tooltip"/>
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
																				<ispac:tooltipLabel labelKey="form.task.propertyLabel.creationDate" tooltipKey="form.task.propertyLabel.creationDate.tooltip"/>
																			</td>
																			<td height="20">
																				&nbsp;&nbsp;<html:text property="property(FCREACION)" styleClass="inputReadOnly" size="12" readonly="true" maxlength="10"/>
																				<div id="formErrors">
																					<html:errors property="property(FCREACION)"/>
																				</div>
																			</td>
																		</tr>
																		<tr>
																			<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="8px"/></td>
																		</tr>
																		<tr>
																			<td height="20" class="formsTitle">
																				<ispac:tooltipLabel labelKey="form.task.propertyLabel.description" tooltipKey="form.task.propertyLabel.description.tooltip"/>
																			</td>
																			<td height="20">
																				&nbsp;&nbsp;<html:textarea property="property(DESCRIPCION)" styleClass="input" readonly="false" cols="72" rows="2"
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
																				<ispac:tooltipLabel labelKey="form.task.propertyLabel.observations" tooltipKey="form.task.propertyLabel.observations.tooltip"/>
																			</td>
																			<td height="20">
																				&nbsp;&nbsp;<html:textarea property="property(OBSERVACIONES)" styleClass="input" readonly="false" cols="72" rows="2"
																					styleId="observ" onkeypress="javascript:maxlength('observ', 512)"/>
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
