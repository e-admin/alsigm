<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/ispac-util.tld" prefix="ispac" %>

<div id="navmenutitle">
	<bean:message key="form.entity.mainTitle"/>
</div>

<div id="navSubMenuTitle">
	<bean:message key="form.entity.subtitle"/>
</div>

<div id="navmenu">
	<ul  class="actionsMenuList">
		<logic:equal name="defaultForm" property="key" value="-1">
			<li>
				<a href="javascript:submit('<%= request.getContextPath() + "/storeCTEntity.do"%>')">
					<nobr><bean:message key="forms.button.accept"/></nobr>
				</a>		
			</li>		
			<li>
				<a href='<%=request.getContextPath() + "/showCTEntitiesList.do"%>'>
					<nobr><bean:message key="forms.button.cancel"/></nobr>
				</a>		
		  	</li>
		</logic:equal>
		<logic:notEqual name="defaultForm" property="key" value="-1">
			<li>
				<a href="javascript:submit('<%= request.getContextPath() + "/storeCTEntity.do"%>')">
					<nobr><bean:message key="forms.button.save"/></nobr>
				</a>		
			</li>		
			<li>
				<a href='<%=request.getContextPath() + "/showCTEntitiesList.do"%>'>
					<nobr><bean:message key="forms.button.close"/></nobr>
				</a>		
		  	</li>
			<li>
				<a href="javascript:submit('<%=request.getContextPath() + "/showComponentsUseList.do"%>')">
					<nobr><bean:message key="forms.button.use"/></nobr>
				</a>		
		  	</li>
			<li>
				<a href="javascript:query('<%= request.getContextPath() + "/deleteCTEntity.do"%>', '<bean:message key="message.deleteConfirm"/>','<bean:message key="common.confirm"/>', '<bean:message key="common.message.ok"/>','<bean:message key="common.message.cancel"/>')">
					<nobr><bean:message key="forms.button.delete"/></nobr>
				</a>		
		  	</li>
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
	<html:hidden property="property(SPAC_CT_ENTIDADES:ID)"/>
	<!-- Tipo: siempre a 0 -->
	<html:hidden property="property(SPAC_CT_ENTIDADES:TIPO)" value="0" />	

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
																				<td height="20" class="formsTitle" width="1%">
																					<div id="form.entity.propertyLabel.id">
																						<ispac:tooltipLabel labelKey="form.entity.propertyLabel.id" tooltipKey="form.entity.propertyLabel.id.tooltip"/>
																					</div>
																				</td>
																				<td height="20">
																					&nbsp;&nbsp;<html:text property="property(SPAC_CT_ENTIDADES:ID)" styleClass="inputReadOnly" size="5" readonly="true" maxlength="10"/>
																					<div id="formErrors">
																						<html:errors property="property(SPAC_CT_ENTIDADES:ID)"/>
																					</div>																				
																				</td>
																			</tr>
																		</logic:notEqual>
																		<tr>
																			<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="8px"/></td>
																		</tr>
																		<tr>
																			<td height="20" class="formsTitle">
																				<div id="form.entity.propertyLabel.name">
																					<ispac:tooltipLabel labelKey="form.entity.propertyLabel.name" tooltipKey="form.entity.propertyLabel.name.tooltip"/>
																				</div>
																			</td>
																			<td height="20">
																				&nbsp;&nbsp;<html:text property="property(SPAC_CT_ENTIDADES:ETIQUETA)" styleClass="inputSelectS" readonly="false" maxlength="250"/>
																				<div id="formErrors">
																					<html:errors property="property(SPAC_CT_ENTIDADES:ETIQUETA)"/>
																				</div>
																			</td>
																		</tr>
																		<tr>
																			<td height="20" class="formsTitle">
																				<div id="form.entity.propertyLabel.descripcion">
																					<ispac:tooltipLabel labelKey="form.entity.propertyLabel.descripcion" tooltipKey="form.entity.propertyLabel.descripcion.tooltip"/>
																				</div>
																			</td>
																			<td height="20">
																				&nbsp;&nbsp;<html:text property="property(SPAC_CT_ENTIDADES:DESCRIPCION)" styleClass="inputSelectS" readonly="false" maxlength="250"/>
																				<div id="formErrors">
																					<html:errors property="property(SPAC_CT_ENTIDADES:DESCRIPCION)"/>
																				</div>
																			</td>
																		</tr>
																		<tr>
																			<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="8px"/></td>
																		</tr>
																		<tr>
																			<td height="20" class="formsTitle">
																				<div id="form.entity.propertyLabel.table">
																					<ispac:tooltipLabel labelKey="form.entity.propertyLabel.table" tooltipKey="form.entity.propertyLabel.table.tooltip"/>
																				</div>
																			</td>
																			<td height="20" class="formsTitle">
																				&nbsp;&nbsp;<ispac:suggestText property="property(SPAC_CT_ENTIDADES:NOMBRE)" 
																						styleId="propertyTable" 
																						suggestAction="/getFilteredTables.do"
																						paramName="filter"
																						styleClass="input" 
																						suggestListStyleClass="autocompleteList"
																						numRows="10"
																						minChars="3"
																						showButton="true"
																						imageButton="img/arrow_down.gif"
																						size="35" readonly="false"/>
																				<div id="formErrors">
																					<html:errors property="property(SPAC_CT_ENTIDADES:NOMBRE)"/>
																				</div>
																			</td>
																		</tr>
																		<tr>
																			<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="8px"/></td>
																		</tr>																		
																		<tr>
																			<td height="20" class="formsTitle">
																				<div id="form.entity.propertyLabel.idField">
																					<ispac:tooltipLabel labelKey="form.entity.propertyLabel.idField" tooltipKey="form.entity.propertyLabel.idField.tooltip"/>
																				</div>
																			</td>
																			<td height="20">
																				&nbsp;&nbsp;<ispac:suggestText property="property(SPAC_CT_ENTIDADES:CAMPO_PK)" 
																						styleId="propertyId" 
																						suggestAction="/getEntityTableData.do"
																						paramName="filter"
																						extraParam="propertyTable"
																						styleClass="input" 
																						suggestListStyleClass="autocompleteList"
																						numRows="10"
																						minChars="1"
																						showButton="true"
																						imageButton="img/arrow_down.gif"
																						size="35" readonly="false"/>
																				<div id="formErrors">
																					<html:errors property="property(SPAC_CT_ENTIDADES:CAMPO_PK)"/>
																				</div>	
																			</td>
																		</tr>
																		<tr>
																			<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="8px"/></td>
																		</tr>
																		<tr>
																			<td height="20" class="formsTitle">
																				<div id="form.entity.propertyLabel.expNumField">
																					<ispac:tooltipLabel labelKey="form.entity.propertyLabel.expNumField" tooltipKey="form.entity.propertyLabel.expNumField.tooltip"/>
																				</div>
													     				</td>
																			<td height="20">
																				&nbsp;&nbsp;<ispac:suggestText property="property(SPAC_CT_ENTIDADES:CAMPO_NUMEXP)" 
																						styleId="propertyNumExp" 
																						suggestAction="/getEntityTableData.do"
																						paramName="filter"
																						extraParam="propertyTable"
																						styleClass="input" 
																						suggestListStyleClass="autocompleteList"
																						numRows="10"
																						minChars="1"
																						showButton="true"
																						imageButton="img/arrow_down.gif"
																						size="35" readonly="false"/>
																				<div id="formErrors">
																					<html:errors property="property(SPAC_CT_ENTIDADES:CAMPO_NUMEXP)"/>
																				</div>
																			</td>
																		</tr>
																		<tr>
																			<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="8px"/></td>
																		</tr>
																		<tr>
																			<td height="20" class="formsTitle">
																				<div id="form.entity.propertyLabel.sequence">
																					<ispac:tooltipLabel labelKey="form.entity.propertyLabel.sequence" tooltipKey="form.entity.propertyLabel.sequence.tooltip"/>
																				</div>
																			</td>
																			<td height="20" class="formsTitle">
																				&nbsp;&nbsp;<ispac:suggestText property="property(SPAC_CT_ENTIDADES:SEC_PK)" 
																						styleId="propertySecuence" 
																						suggestAction="/getFilteredSequences.do"
																						paramName="filter"
																						styleClass="input" 
																						suggestListStyleClass="autocompleteList"
																						numRows="10"
																						minChars="1"
																						showButton="true"
																						imageButton="img/arrow_down.gif"
																						size="35" readonly="false"/>
																				<div id="formErrors">
																					<html:errors property="property(SPAC_CT_ENTIDADES:SEC_PK)"/>
																				</div>
																			</td>
																		</tr>
																		<tr>
																			<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="8px"/></td>
																		</tr>																		
																		<tr>
																			<td height="20" class="formsTitle">
																				<div id="form.entity.propertyLabel.expScheme">
																					<ispac:tooltipLabel labelKey="form.entity.propertyLabel.expScheme" tooltipKey="form.entity.propertyLabel.expScheme.tooltip"/>
																				</div>
																			</td>
																			<td height="20">
																				&nbsp;&nbsp;<html:text property="property(SPAC_CT_ENTIDADES:SCHEMA_EXPR)" styleClass="inputSelectS" readonly="false" maxlength="100"/>
																				<div id="formErrors">
																					<html:errors property="property(SPAC_CT_ENTIDADES:CAMPO_NUMEXP)"/>
																				</div>
																			</td>
																		</tr>
																		<tr>
																			<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="8px"/></td>
																		</tr>
																		<tr>
																			<td height="20" class="formsTitle">
																				<div id="form.entity.propertyLabel.defaultApp">
																					<ispac:tooltipLabel labelKey="form.entity.propertyLabel.defaultApp" tooltipKey="form.entity.propertyLabel.defaultApp.tooltip"/>
																				</div>
																			</td>
																			<td height="20">
																				&nbsp;&nbsp;<html:text property="property(SPAC_CT_ENTIDADES:FRM_EDIT)" styleClass="inputReadOnly" size="3" readonly="true" />
																				<html:text property="property(SPAC_CT_APLICACIONES:NOMBRE)" styleClass="inputReadOnly" readonly="true" maxlength="100"/>
																				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
																					
																					<ispac:linkframe id="SPAC_CT_APLICACIONES"
																													target="workframe"
																													action="selectObject.do?codetable=SPAC_CT_APLICACIONES&codefield=ID&valuefield=NOMBRE&decorator=/formatters/substituteformatter.xml"
																													titleKey="catalog.choose.application"
																													width="640"
																													height="480"
																													styleClass="form_button_white"
																													showFrame="true">
																						<ispac:parameter name="SPAC_CT_APLICACIONES" id="property(SPAC_CT_ENTIDADES:FRM_EDIT)" property="ID"/>
																						<ispac:parameter name="SPAC_CT_APLICACIONES" id="property(SPAC_CT_APLICACIONES:NOMBRE)" property="NOMBRE"/>
																					</ispac:linkframe>
																				<div id="formErrors">
																					<html:errors property="property(SPAC_CT_ENTIDADES:FRM_EDIT)"/>
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
