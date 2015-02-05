<%@ page language="java" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/ispac-util.tld" prefix="ispac" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>

<c:choose>
	<c:when test="${defaultForm.readonly}">
		<c:set var="soloLectura" value="true"/>
	</c:when>
	<c:otherwise>
		<c:set var="listaTablasValidacion" value="${requestScope['LIST_TBL_VALIDATION']}" />
		<bean:define id="listaTablasValidacion" name="listaTablasValidacion" scope="page"/>
		<c:set var="soloLectura" value="false"/>
	</c:otherwise>	
</c:choose>

<c:set var="isEditando" value="${requestScope.EDITANDO}"/>

<html:form action="/hierarchicalTableManage.do">

	<table cellpadding="1" cellspacing="0" border="0" width="100%">
		<tr>
			<td>
				<table class="box" width="100%" border="0" cellspacing="1" cellpadding="0">
					<!-- FORMULARIO -->
					<tr>
						<td class="blank">
							<table width="100%" border="0" cellspacing="2" cellpadding="2">
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
																	
																		<logic:notEmpty name="defaultForm" property="property(ID)">
																			<tr>
																				<td height="20" class="formsTitle" width="1%">
																					<div id="form.entity.propertyLabel.id">
																						<ispac:tooltipLabel labelKey="form.entity.propertyLabel.id" tooltipKey="form.entity.propertyLabel.id.tooltip"/>
																					</div>
																				</td>
																				<td height="20">
																					&nbsp;&nbsp;<html:text property="property(ID)" 
																						styleClass="inputReadOnly" size="5" readonly="true" 
																						maxlength="10"/>
																					<div id="formErrors">
																						<html:errors property="property(ID)"/>
																					</div>																				
																				</td>
																			</tr>
																		</logic:notEmpty>

																		<tr>
																			<td height="20" class="formsTitle">
																				<div id="form.entity.propertyLabel.name">
																					<ispac:tooltipLabel labelKey="form.entity.propertyLabel.name" tooltipKey="form.entity.propertyLabel.name.tooltip"/>
																				</div>
																			</td>
																			<td height="20" class="formsTitle">
																				<c:choose>
																					<c:when test="${defaultForm.readonly}">
																					&nbsp;&nbsp;<html:text property="property(NOMBRE)" 
																						styleClass="inputReadOnly" readonly="true" size="35"/>
																						&nbsp; <bean:message key="catalog.data.obligatory"/>
																					</c:when>
																					<c:otherwise>
																					&nbsp;&nbsp;<html:text property="property(NOMBRE)" 
																						styleClass="input" readonly="false" size="35" maxlength="250"/>
																						&nbsp; <bean:message key="catalog.data.obligatory"/>
																					</c:otherwise>	
																				</c:choose>
																				<div id="formErrors">
																					<html:errors property="property(NOMBRE)"/>
																				</div>
																			</td>
																		</tr>
																		
																		<tr>
																			<td height="20" class="formsTitle">
																				<div id="form.entity.propertyLabel.descripcion">
																					<ispac:tooltipLabel labelKey="form.entity.propertyLabel.descripcion" tooltipKey="form.entity.propertyLabel.descripcion.tooltip"/>
																				</div>
																			</td>
																			<td height="20" class="formsTitle">
																				<c:choose>
																					<c:when test="${defaultForm.readonly}">
																						&nbsp;&nbsp;<html:text property="property(DESCRIPCION)" 
																							styleClass="inputReadOnly" readonly="true" size="35"/>
																							&nbsp;
																					</c:when>
																					<c:otherwise>
																						&nbsp;&nbsp;<html:text property="property(DESCRIPCION)" 
																							styleClass="input" readonly="false" size="35" maxlength="250"/>
																							&nbsp;
																					</c:otherwise>	
																				</c:choose>
																				<div id="formErrors">
																					<html:errors property="property(DESCRIPCION)"/>
																				</div>
																			</td>
																		</tr>
																		
																		<tr>
																			<td height="20" class="formsTitle">
																				<div id="form.hierarchicaltables.entidadPadre">
																					<nobr><bean:message key="form.hierarchicaltables.entidadPadre"/>:</nobr>
																				</div>
																			</td>
																			<td height="20" class="formsTitle">
																				<c:choose>
																					<c:when test="${defaultForm.readonly || isEditando}">
																						&nbsp;&nbsp;<html:text property="property(DES_ENTIDAD_PADRE)" 
																							styleClass="inputReadOnly" readonly="true" size="35"/>
																							&nbsp;
																					</c:when>
																					<c:otherwise>
																						&nbsp;&nbsp;<html:select property="property(ID_ENTIDAD_PADRE)" styleClass="input">
																							<html:option value="">&nbsp;</html:option>
																							<html:options collection="listaTablasValidacion" property="property(ID)" labelProperty="property(LOGICALNAME)"/>
																						</html:select>
																						&nbsp; <bean:message key="catalog.data.obligatory"/>
																					</c:otherwise>	
																				</c:choose>
																				<div id="formErrors">
																					<html:errors property="property(ID_ENTIDAD_PADRE)"/>
																				</div>
																			</td>
																		</tr>
																		
																		<tr>
																			<td height="20" class="formsTitle">
																				<div id="form.hierarchicaltables.entidadHija">
																					<nobr><bean:message key="form.hierarchicaltables.entidadHija"/>:</nobr>
																				</div>
																			</td>
																			<td height="20" class="formsTitle">
																				<c:choose>
																					<c:when test="${defaultForm.readonly || isEditando}">
																						&nbsp;&nbsp;<html:text property="property(DES_ENTIDAD_HIJA)" 
																							styleClass="inputReadOnly" readonly="true" size="35"/>
																							&nbsp;

																					</c:when>
																					<c:otherwise>
																						&nbsp;&nbsp;<html:select property="property(ID_ENTIDAD_HIJA)" styleClass="input">
																							<html:option value="">&nbsp;</html:option>
																							<html:options collection="listaTablasValidacion" property="property(ID)" labelProperty="property(LOGICALNAME)"/>
																						</html:select>
																						&nbsp; <bean:message key="catalog.data.obligatory"/>
																					</c:otherwise>	
																				</c:choose>
																				<div id="formErrors">
																					<html:errors property="property(ID_ENTIDAD_HIJA)"/>
																				</div>
																			</td>
																		</tr>	
																		<tr>
																			<td height="20" class="formsTitle">
																				<div id="form.hierarchicaltables.useView">
																				<c:choose>
																					<c:when test="${defaultForm.readonly}">
																						<nobr><bean:message key="form.hierarchicaltables.tableOrView"/>:</nobr>
																					</c:when>
																					<c:otherwise>
																						<nobr><bean:message key="form.hierarchicaltables.useView"/>:</nobr>
																					</c:otherwise>
																				</c:choose>
																				</div>
																			</td>
																			<td height="20" class="formsTitle">
																				<c:choose>
																					<c:when test="${defaultForm.readonly}">
																						<c:set var="tableOrView"><bean:write name="defaultForm" property="property(TIPO)"/></c:set>
																						<c:set var="viewType"><%=ieci.tdw.ispac.ispaclib.common.constants.HierarchicalTablesConstants.HIERARCHICAL_TABLE_VIEW_TYPE%></c:set>
																						<c:choose>
																						<c:when test="${tableOrView == viewType}">&nbsp;&nbsp;<bean:message key="form.hierarchicaltables.view"/></c:when>
																						<c:otherwise>&nbsp;&nbsp;<bean:message key="form.hierarchicaltables.table"/></c:otherwise>
																						</c:choose>
																					</c:when>
																					<c:otherwise>
																						&nbsp;<html:checkbox property="property(USE_VIEW)"/>
																					</c:otherwise>
																				</c:choose>
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