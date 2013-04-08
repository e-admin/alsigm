<%@ page language="java" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/ispac-util.tld" prefix="ispac" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<c:set var="listaTablasValidacion" value="${requestScope['LIST_TBL_VALIDATION']}" />
<bean:define id="listaTablasValidacion" name="listaTablasValidacion" scope="page"/>
<c:set var="listaTablasJerarquicas" value="${requestScope['LIST_TBL_HIERARCHICAL']}" />
<bean:define id="listaTablasJerarquicas" name="listaTablasJerarquicas" scope="page"/>


<c:set var="fields" value="${requestScope['LIST_FIELDS']}"/>
<bean:define id="fields" name="fields" scope="page"/>

<script language="javascript">
//<!--

	function selectTableType() {
		var tableElem = document.defaultForm.elements['property(TABLE)'];
		tableElem.disabled = false;
		tableElem.focus();

//var tableHierarchical = document.defaultForm.elements['property(HIERARCHICAL_TABLE)'];
//tableHierarchical.disabled = false;

var hierarchicalTableValidationCheck = document.getElementById('hierarchicalTableValidation');
if (hierarchicalTableValidationCheck) {
	hierarchicalTableValidationCheck.disabled = false;
}


		<%--		
		var clazzElem = document.defaultForm.elements['property(CLAZZ)'];
		clazzElem.value = "";
		clazzElem.readOnly = true;
		clazzElem.className = "inputReadOnly";
		--%>
	}
	
	function selectClassType() {
		var tableElem = document.defaultForm.elements['property(TABLE)'];
		tableElem.value = "";
		tableElem.disabled = true;
		
var tableHierarchical = document.defaultForm.elements['property(HIERARCHICAL_TABLE)'];
if (tableHierarchical) {
	tableHierarchical.value = "";
	tableHierarchical.disabled = false;
}
var hierarchicalTableValidationCheck = document.getElementById('hierarchicalTableValidation');
if (hierarchicalTableValidationCheck) {
	hierarchicalTableValidationCheck.disabled=false;
}
		
		

		<%--		
		var clazzElem = document.defaultForm.elements['property(CLAZZ)'];
		clazzElem.readOnly = false;
		clazzElem.className = "input";
		clazzElem.focus();
		--%>
	}

	function selectValidation() {
		var validationCheck = document.getElementById('validation');
		var tableElem = document.defaultForm.elements['property(TABLE)'];
		
var tableHierarchical = document.defaultForm.elements['property(HIERARCHICAL_TABLE)'];
var hierarchicalTableValidationCheck = document.getElementById('hierarchicalTableValidation');


		
		
		var tableRadio = document.getElementById('entidad');
		<%--
		var clazzElem = document.defaultForm.elements['property(CLAZZ)'];
		var clazzRadio = document.getElementById('clase');
		--%>

		if (validationCheck.checked) {
			tableRadio.disabled = false;
			<%--clazzRadio.disabled = false;--%>
			if (tableRadio.checked) {
				tableElem.disabled = false;
if (tableHierarchical) {
	tableHierarchical.disabled = false;
}
//hierarchicalTableValidationCheck.disabled=false;
			}
		}
		else {
			tableElem.value = "";
			tableElem.disabled = true;

if (tableHierarchical) {
	tableHierarchical.value = "";
	tableHierarchical.disabled = true;
}
if (hierarchicalTableValidationCheck) {
	hierarchicalTableValidationCheck.disabled=true;
}

			tableRadio.disabled = true;
			tableRadio.checked = false;
			<%--
			clazzElem.value = "";
			clazzElem.readOnly = true;
			clazzElem.className = "inputReadOnly";
			clazzRadio.disabled = true;
			clazzRadio.checked = false;
			--%>
		}
	}
	
	function selectHierarchicalTableValidation(){
		var tableHierarchical = document.defaultForm.elements['property(HIERARCHICAL_TABLE)'];
		var hierarchicalTableValidationCheck = document.getElementById('hierarchicalTableValidation');
		if (hierarchicalTableValidationCheck && tableHierarchical) {
			if (hierarchicalTableValidationCheck.checked) {
				tableHierarchical.disabled = false;
			}else{
				tableHierarchical.disabled = true;
				tableHierarchical.value = "";
			}
		}
	}
	

//-->
</script>

<c:set var="action">
   <c:out value="${requestScope['ActionDestino']}" default="/showEntityWizard.do"/>
</c:set>
<jsp:useBean id="action" type="java.lang.String"/>
<html:form action='<%=action%>'>

	
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
														<html:errors property="property(ERROR_OPCION)"/>
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
																			<td height="20" class="formsTitle" colspan="2">
																				<div id="form.entity.validation.propertyLabel.field">
																					<ispac:tooltipLabel labelKey="form.entity.validation.propertyLabel.field" tooltipKey="form.entity.validation.propertyLabel.field.tooltip"/>
																				</div>
																			</td>
																			<td height="20" class="formsTitle" width="99%">
																				&nbsp;&nbsp;<html:select property="property(FIELDID)" styleClass="inputSelectS">
																					<html:options collection="fields" property="property(ID)" labelProperty="property(ETIQUETA)"/>
																				</html:select>
																				<div id="formErrors">
																					<html:errors property="property(FIELDID)"/>
																				</div>
																			</td>
																		</tr>
																		<tr>
																			<td colspan="3"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="8px"/></td>
																		</tr>

																		<tr>
																			<td height="20" class="formsTitle" colspan="2">
																				<div id="form.entity.validation.propertyLabel.validated">
																					<ispac:tooltipLabel labelKey="form.entity.validation.propertyLabel.validated" tooltipKey="form.entity.validation.propertyLabel.validated.tooltip"/>
																				</div>
																			</td>
																			<td height="20">
																				&nbsp;&nbsp;<html:checkbox property="property(VALIDATION_WITH)" styleClass="checkbox" value="true" 
																					styleId="validation" onclick="javascript:selectValidation();"/>
																				<div id="formErrors">
																					<html:errors property="property(VALIDATION_TYPE)"/>
																				</div>	
																			</td>
																		</tr>
																		<tr>
																			<td colspan="3"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="8px"/></td>
																		</tr>																		
																		<tr>
																			<td colspan="3">
																				<table cellpadding="0" cellspacing="0">
																				<tr>
																					<td height="20" class="formsTitle" width="50px">&nbsp;</td>
																					<td height="20" class="formsTitle">
																						<div id="form.entity.validation.propertyLabel.table">
																							<nobr>
																								<html:radio styleId="entidad" property="property(VALIDATION_TYPE)" value="TABLE" 
																									disabled="true" onclick="javascript:selectTableType()"/>
																								<ispac:tooltipLabel labelKey="form.entity.validation.propertyLabel.table" tooltipKey="form.entity.validation.propertyLabel.table.tooltip" nobr="false"/>
																							</nobr>
																						</div>
																					</td>
																					<td height="20">
																						&nbsp;&nbsp;<html:select property="property(TABLE)" styleClass="inputSelectS" disabled="true">
																							<html:option value="">&nbsp;</html:option>
																							<html:options collection="listaTablasValidacion" property="property(ID)" labelProperty="property(LOGICALNAME)"/>
																						</html:select><div id="formErrors">
																							<html:errors property="property(TABLE)"/>
																						</div>
																					</td>
																				<%--  Tabla jerarquica--%>
																				<c:set var="hierarchicalTablesManagementActive" value="${ISPACConfiguration.HIERARCHICAL_TABLES_MANAGEMENT_ACTIVE}"/>
																				<c:if test="${hierarchicalTablesManagementActive == 'true'}">
																				<td height="20" class="formsTitle" colspan="2">
																					<div id="form.entity.validation.propertyLabel.hierarchicalTableUse">
																						<ispac:tooltipLabel labelKey="form.entity.validation.propertyLabel.hierarchicalTableUse" tooltipKey="form.entity.validation.propertyLabel.hierarchicalTableUse.tooltip"/>
																					</div>
																				</td>
																				<td height="20">
																					&nbsp;&nbsp;<html:checkbox property="property(HIERARCHICAL_TABLE_VALIDATION)" styleClass="checkbox" value="true" 
																						styleId="hierarchicalTableValidation" onclick="javascript:selectHierarchicalTableValidation();"/>
																					<div id="formErrors">
																						<html:errors property="property(HIERARCHICAL_TABLE_VALIDATION)"/>
																					</div>	
																				</td>
																				<td height="20">
																					&nbsp;&nbsp;<html:select property="property(HIERARCHICAL_TABLE)" styleClass="inputSelectS" disabled="true">
																						<html:option value="">&nbsp;</html:option>
																						<html:options collection="listaTablasJerarquicas" property="property(ID)" labelProperty="property(NOMBRE)"/>
																					</html:select><div id="formErrors">
																						<html:errors property="property(HIERARCHICAL_TABLE)"/>
																					</div>
																				</td>
																				</c:if>																					
																				<%-- --%>
																				</tr>
																				<%--
																				<tr>
																					<td colspan="3"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="8px"/></td>
																				</tr>
																				<tr>
																					<td height="20" class="formsTitle">&nbsp;</td>
																					<td height="20" class="formsTitle">
																						<div id="form.entity.validation.propertyLabel.class">
																							<nobr>
																								<html:radio styleId="clase" property="property(VALIDATION_TYPE)" value="CLAZZ" 
																									disabled="true" onclick="javascript:selectClassType()"/>
																								<ispac:tooltipLabel labelKey="form.entity.validation.propertyLabel.class" tooltipKey="form.entity.validation.propertyLabel.class.tooltip" nobr="false"/>
																							</nobr>
																						</div>
																					</td>
																					<td height="20">
																						&nbsp;&nbsp;<html:text property="property(CLAZZ)" styleClass="inputReadOnly" size="60" readonly="true" maxlength="250"/>
																						<div id="formErrors">
																							<html:errors property="property(CLAZZ)"/>
																						</div>	
																					</td>
																				</tr>
																				--%>

																				</table>
																			</td>
																		</tr>																		
																		<tr>
																			<td colspan="3"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="16px"/></td>
																		</tr>
																		<tr>
																			<td height="20" class="formsTitle" colspan="2">
																				<div id="form.entity.validation.propertyLabel.mandatory">
																					<ispac:tooltipLabel labelKey="form.entity.validation.propertyLabel.mandatory" tooltipKey="form.entity.validation.propertyLabel.mandatory.tooltip"/>
																				</div>
														     				</td>
																			<td height="20">
																				&nbsp;&nbsp;<html:checkbox property="property(MANDATORY)" styleClass="checkbox" value="S" />
																				<div id="formErrors">
																					<html:errors property="property(MANDATORY)"/>
																				</div>
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
			</td>
		</tr>
	</table>

</html:form>

<script language="javascript">
//<!--

	selectValidation();
	
//-->
</script>