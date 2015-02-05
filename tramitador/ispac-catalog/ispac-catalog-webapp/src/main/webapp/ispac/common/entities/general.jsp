<%@ page language="java" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/ispac-util.tld" prefix="ispac" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>

<script language="javascript">
//<!--

	function changeVldTblType() {
		var form = document.defaultForm;
		var elem = form.elements["property(SPAC_CT_ENTIDADES:TIPO)"];
		if (elem != null) {
			var tipo = elem.options[elem.selectedIndex].value;
			var sustituto = form.elements["property(TAM_SUSTITUTO)"];
			if (tipo == 3) {
				sustituto.className = "input";
				sustituto.readOnly = false;
			}
			else {
				sustituto.value = "";
				sustituto.className = "inputReadOnly";
				sustituto.readOnly = true;
			}
		}
	}

//-->
</script>

<c:choose>
	<c:when test="${defaultForm.readonly}">
		<c:set var="soloLectura" value="true"/>
	</c:when>
	<c:otherwise>
		<c:set var="soloLectura" value="false"/>
	</c:otherwise>
</c:choose>

<c:set var="isEntidadTblVld" value="${requestScope['TIPO_TBLVLD']}"/>
<c:set var="isEditando" value="${requestScope.EDITANDO}"/>

<html:form action="/showEntityWizard.do">

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
																		<logic:notEmpty name="defaultForm" property="key">
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
																					<html:errors property="property(SPAC_CT_ENTIDADES:ID)"/>
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

																				&nbsp;&nbsp;<html:text property="property(ETIQUETA)"
																					styleClass="inputReadOnly" readonly="true" size="35" maxlength="250"/>
																					&nbsp; <bean:message key="catalog.data.obligatory"/>
																				</c:when>
																				<c:otherwise>
																				&nbsp;&nbsp;<html:text property="property(ETIQUETA)"
																					styleClass="input" readonly="false" size="35" maxlength="250"/>
																					&nbsp; <bean:message key="catalog.data.obligatory"/>
																				</c:otherwise>
																			</c:choose>
																				<div id="formErrors">
																					<html:errors property="property(ETIQUETA)"/>
																				</div>
																			</td>
																		</tr>
																		<tr>
																			<td height="20" class="formsTitle">
																				<div id="form.entity.propertyLabel.table">
																					<ispac:tooltipLabel labelKey="form.entity.propertyLabel.table" tooltipKey="form.entity.propertyLabel.table.tooltip"/>
																				</div>
																			</td>
																			<td height="20" class="formsTitle" width="100%">
																			<c:choose>
																				<c:when test="${defaultForm.readonly || isEditando}">
																					&nbsp;&nbsp;<html:text property="property(SPAC_CT_ENTIDADES:NOMBRE)"
																						styleClass="inputReadOnly" readonly="true" size="35" maxlength="28"/>
																					&nbsp; <bean:message key="catalog.data.obligatory"/>
																				</c:when>
																				<c:otherwise>
																					&nbsp;&nbsp;<html:text property="property(SPAC_CT_ENTIDADES:NOMBRE)"
																						styleClass="input" readonly="false" size="35" maxlength="28"/>
																					&nbsp; <bean:message key="catalog.data.obligatory"/>
																				</c:otherwise>
																			</c:choose>
																				<div id="formErrors">
																					<html:errors property="property(SPAC_CT_ENTIDADES:NOMBRE)"/>
																				</div>
																			</td>
																		</tr>

																		<c:if test="${isEntidadTblVld}">

																			<tr>
																				<td height="20" class="formsTitle">
																					<div id="form.entity.propertyLabel.tablaYaExistente">
																						<ispac:tooltipLabel labelKey="form.entity.propertyLabel.tablaYaExistente" tooltipKey="form.entity.propertyLabel.tablaYaExistente.tooltip"/>
																					</div>
																				</td>
																				<td height="20" class="formsTitle">
																					&nbsp;&nbsp;<html:checkbox property="property(TBL_EXISTENTE)" styleClass="checkbox" />
																				</td>
																			</tr>

																		</c:if>

																		<tr>
																			<td height="20" class="formsTitle">
																				<div id="form.entity.propertyLabel.descripcion">
																					<ispac:tooltipLabel labelKey="form.entity.propertyLabel.descripcion" tooltipKey="form.entity.propertyLabel.descripcion.tooltip"/>
																				</div>
																			</td>
																			<td height="20" class="formsTitle">
																			<c:choose>
																				<c:when test="${defaultForm.readonly}">
																				&nbsp;&nbsp;<html:textarea property="property(SPAC_CT_ENTIDADES:DESCRIPCION)"
																					styleClass="inputReadOnly" readonly="true" rows="6" cols="40"/>
																					&nbsp;
																				</c:when>
																				<c:otherwise>
																				&nbsp;&nbsp;<html:textarea property="property(SPAC_CT_ENTIDADES:DESCRIPCION)"
																					styleClass="input" readonly="false" rows="6" cols="40" styleId="descripcion" onkeypress="javascript:maxlength('descripcion', 250)"/>
																					&nbsp;
																				</c:otherwise>
																			</c:choose>
																				<div id="formErrors">
																					<html:errors property="property(SPAC_CT_ENTIDADES:DESCRIPCION)"/>
																				</div>
																			</td>
																		</tr>

																		<logic:notEmpty name="defaultForm" property="property(TYPE_TBLVLD)">

																			<tr>
																				<td height="20" class="formsTitle">
																					<div id="form.entity.propertyLabel.tipoTblvld">
																						<ispac:tooltipLabel labelKey="form.entity.propertyLabel.tipoTblvld" tooltipKey="form.entity.propertyLabel.tipoTblvld.tooltip"/>
																					</div>
																				</td>
																				<td height="20" class="formsTitle">
																					&nbsp;&nbsp;<html:text property="property(TYPE_TBLVLD)"
																						styleClass="inputReadOnly" readonly="true" size="40"/>
																				</td>
																			</tr>

																		</logic:notEmpty>

																		<c:if test="${isEntidadTblVld}">

																			<tr>
																				<td height="20" class="formsTitle">
																					<div id="form.entity.propertyLabel.tipoTblvld">
																						<ispac:tooltipLabel labelKey="form.entity.propertyLabel.tipoTblvld" tooltipKey="form.entity.propertyLabel.tipoTblvld.tooltip"/>
																					</div>
																				</td>
																				<td height="20" class="formsTitle">
																					<c:choose>
																						<c:when test="${defaultForm.readonly || isEditando}">
																							&nbsp;&nbsp;<html:text property="property(SPAC_CT_ENTIDADES:TIPO)"
																								styleClass="inputReadOnly" readonly="true" size="35" maxlength="100"/>
																							&nbsp; <bean:message key="catalog.data.obligatory"/>
																						</c:when>
																						<c:otherwise>
																							&nbsp;&nbsp;<html:select property="property(SPAC_CT_ENTIDADES:TIPO)" styleClass="input"
																								onchange="javascript:changeVldTblType()">
																									<html:option value="2" key="form.entity.tablaValidacionSimple"/>
																									<html:option value="3" key="form.entity.tablaValidacionSustituto"/>
																								</html:select>
																						</c:otherwise>
																					</c:choose>
																					<div id="formErrors">
																						<html:errors property="property(SPAC_CT_ENTIDADES:TIPO)"/>
																					</div>
																				</td>
																			</tr>
																			<tr>
																				<td height="20" class="formsTitle">
																					<div id="form.entity.propertyLabel.tamValor">
																						<ispac:tooltipLabel labelKey="form.entity.propertyLabel.tamValor" tooltipKey="form.entity.propertyLabel.tamValor.tooltip"/>
																					</div>
																				</td>
																				<td height="20" class="formsTitle">
																					&nbsp;&nbsp;<html:text property="property(TAM_VALOR)"
																						styleClass="input" readonly="false" size="5" maxlength="5"/>
																					<div id="formErrors">
																						<html:errors property="property(TAM_VALOR)"/>
																					</div>
																				</td>
																			</tr>
																			<tr>
																				<td height="20" class="formsTitle">
																					<div id="form.entity.propertyLabel.tamSustituto">
																						<ispac:tooltipLabel labelKey="form.entity.propertyLabel.tamSustituto" tooltipKey="form.entity.propertyLabel.tamSustituto.tooltip"/>
																					</div>
																				</td>
																				<td height="20" class="formsTitle">
																					&nbsp;&nbsp;<html:text property="property(TAM_SUSTITUTO)"
																						styleClass="inputReadOnly" readonly="true" size="5" maxlength="5"/>
																					<div id="formErrors">
																						<html:errors property="property(TAM_SUSTITUTO)"/>
																					</div>
																				</td>
																			</tr>

																		</c:if>

																		<%--
																		<c:if test="${requestScope.TIENE_APPDEFAULT}">

																			<tr>
																				<td height="20" class="formsTitle">
																					<div id="form.entity.propertyLabel.defaultApp">
																						<ispac:tooltipLabel labelKey="form.entity.propertyLabel.defaultApp" tooltipKey="form.entity.propertyLabel.defaultApp.tooltip"/>
																					</div>
																				</td>

																				<td height="20" class="formsTitle">
																					&nbsp;<html:hidden property="property(SPAC_CT_ENTIDADES:FRM_EDIT)" styleClass="inputReadOnly"  />
																					<html:text property="property(SPAC_CT_APLICACIONES:NOMBRE)" size="35" styleClass="inputReadOnly" readonly="true" maxlength="150"/>
																					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
																					<c:choose>
																						<c:when test="${!defaultForm.readonly}">
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
																						</c:when>
																					</c:choose>
																				</td>
																			</tr>

																		</c:if>
																		--%>

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

<script language="javascript">
//<!--

	changeVldTblType();

//-->
</script>
