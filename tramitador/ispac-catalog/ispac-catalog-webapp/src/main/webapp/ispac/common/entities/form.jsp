<%@ page language="java" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/ispac-util.tld" prefix="ispac" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>


<script type="text/javascript" src='<ispac:rewrite href="../scripts/utilities.js"/>'> </script>
<script type="text/javascript">

function selectOnChecked(_objSrc, _objDes){
    if (_objSrc.checked == true)
        _objDes.value = 'SI';
    else
        _objDes.value = 'NO';
}
</script>
<c:set var="action">
   <c:out value="${requestScope['ActionDestino']}" default="/showEntityWizard.do"/>
</c:set>

<jsp:useBean id="action" type="java.lang.String"/>
<html:form action='<%=action%>'>

	<!-- Identificador de la entidad -->
	<html:hidden property="entity"/>
	<!-- Identificador del registro -->
	<html:hidden property="key"/>
	
	<table cellpadding="1" cellspacing="0" border="0" width="100%">
		<tr>
			<td>

				<table class="box" width="100%" border="0" cellspacing="1" cellpadding="0">
					<!-- FORMULARIO -->
					<tr>
						<td class="blank">
							<table width="100%" border="0" cellspacing="2" cellpadding="2">
								<tr>
									<td colspan="3">

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
																				<td height="20" class="formsTitle">
																					<div id="form.app.propertyLabel.id">
																						<ispac:tooltipLabel labelKey="form.app.propertyLabel.id" tooltipKey="form.app.propertyLabel.id.tooltip"/>
																					</div>
																				</td>
																				<td height="20" class="formsTitle">
																					&nbsp;&nbsp;<html:text property="property(ID)" styleClass="inputReadOnly" size="5" readonly="true" maxlength="22"/>
																					&nbsp; <bean:message key="catalog.data.obligatory"/>
																					<div id="formErrors">
																						<html:errors property="property(ID)"/>
																					</div>
																				</td>
																			</tr>
																			<tr>
																				<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="8px"/></td>
																			</tr>
																		</logic:notEmpty>
																		<tr>
																			<td height="20" class="formsTitle">
																				<div id="form.app.propertyLabel.name">
																					<ispac:tooltipLabel labelKey="form.app.propertyLabel.name" tooltipKey="form.app.propertyLabel.name.tooltip"/>
																				</div>
																			</td>
																			<td height="20" class="formsTitle">
																				&nbsp;&nbsp;<html:text property="property(NOMBRE)" styleClass="inputSelectScustomWidth" readonly="false" maxlength="100" size="61"/>
																				&nbsp; <bean:message key="catalog.data.obligatory"/>
																				<div id="formErrors">
																					<html:errors property="property(NOMBRE)"/>
																				</div>
																			</td>
																		</tr>
																		<tr>
																			<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="8px"/></td>
																		</tr>
																		<tr>
																			<td height="20" class="formsTitle">
																				<div id="form.app.propertyLabel.description">
																					<ispac:tooltipLabel labelKey="form.app.propertyLabel.description" tooltipKey="form.app.propertyLabel.description.tooltip"/>
																				</div>
																			</td>
																			<td height="20" class="formsTitle">
																				<%--
																				&nbsp;&nbsp;<html:text property="property(DESCRIPCION)" styleClass="inputSelectScustomWidth" readonly="false" maxlength="250" size="61"/>
																				--%>
																				&nbsp;&nbsp;<html:textarea property="property(DESCRIPCION)" styleClass="inputSelectScustomWidth" readonly="false" cols="60" rows="3" styleId="formDesc" onkeypress="javascript:maxlength('formDesc', 250)"/>
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
																				<div id="form.app.propertyLabel.class">
																					<ispac:tooltipLabel labelKey="form.app.propertyLabel.class" tooltipKey="form.app.propertyLabel.class.tooltip"/>
																				</div>
																			</td>
																			<td height="20" class="formsTitle">
																				&nbsp;&nbsp;<html:text property="property(CLASE)" styleClass="inputSelectScustomWidth" readonly="false" maxlength="250" size="61"/>
																				&nbsp; <bean:message key="catalog.data.obligatory"/>
																				<div id="formErrors">
																					<html:errors property="property(CLASE)"/>
																				</div>
																			</td>
																		</tr>
																		<tr>
																			<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="8px"/></td>
																		</tr>
																		<tr>
																			<td height="20" class="formsTitle">
																				<div id="form.app.propertyLabel.parameters">
																					<ispac:tooltipLabel labelKey="form.app.propertyLabel.parameters" tooltipKey="form.app.propertyLabel.parameters.tooltip"/>
																				</div>
																			</td>
																			<td height="20" class="formsTitle">
																				&nbsp;&nbsp;<html:textarea property="property(PARAMETROS)" styleClass="inputSelectScustomWidth" readonly="false" cols="60" rows="10"  styleId="formParam" />
																				<div id="formErrors">
																					<html:errors property="property(PARAMETROS)"/>
																				</div>
																			</td>
																		</tr>
																		<tr>
																			<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="8px"/></td>
																		</tr>
																		<tr>
																			<td height="20" class="formsTitle">
																				<div id="form.app.propertyLabel.formatter">
																					<ispac:tooltipLabel labelKey="form.app.propertyLabel.formatter" tooltipKey="form.app.propertyLabel.formatter.tooltip"/>
																				</div>
																			</td>
																			<td height="20" class="formsTitle">
																				&nbsp;&nbsp;<html:text property="property(FORMATEADOR)" styleClass="inputSelectScustomWidth" readonly="false" maxlength="250" size="61"/>
																				<div id="formErrors">
																					<html:errors property="property(FORMATEADOR)"/>
																				</div>
																			</td>
																		</tr>

																		<tr>
																			<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="8px"/></td>
																		</tr>
																		<tr>
																			<td height="20" class="formsTitle">
																				<div id="form.app.propertyLabel.formatterXML">
																					<ispac:tooltipLabel labelKey="form.app.propertyLabel.formatterXML" tooltipKey="form.app.propertyLabel.formatterXML.tooltip"/>
																				</div>
																			</td>
																			<td height="20" class="formsTitle">
																				&nbsp;&nbsp;<html:textarea property="property(XML_FORMATEADOR)" styleClass="inputSelectScustomWidth" readonly="false" cols="60" rows="10"/>
																				<div id="formErrors">
																					<html:errors property="property(XML_FORMATEADOR)"/>
																				</div>
																			</td>
																		</tr>
																		
																		<tr>
																			<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="8px"/></td>
																		</tr>
																		<tr>
																			<td height="20" class="formsTitle">
																				<div id="form.app.propertyLabel.documents">
																					<ispac:tooltipLabel labelKey="form.app.propertyLabel.documents" tooltipKey="form.app.propertyLabel.documents.tooltip"/>
																				</div>
																			</td>
																			<td height="20" class="formsTitle">
																				&nbsp;&nbsp;<ispac:htmlCheckbox property="property(DOCUMENTOS)" readonly="false" propertyReadonly="readonly" valueChecked="SI" styleClassCheckbox="inputSelectP"/>
																				<div id="formErrors">
																					<html:errors property="property(DOCUMENTOS)"/>
																				</div>
																			</td>
																		</tr>
																		
																		
																		<logic:notEmpty name="defaultForm" property="property(ID)">
																		
																			<tr>
																				<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="8px"/></td>
																			</tr>
																			<tr>
																				<td height="20" class="formsTitle">
																					<div id="form.app.propertyLabel.page">
																						<ispac:tooltipLabel labelKey="form.app.propertyLabel.page" tooltipKey="form.app.propertyLabel.page.tooltip"/>
																					</div>
																				</td>
																				<td height="20" class="formsTitle">
																					<logic:notEmpty name="defaultForm" property="property(FRM_VERSION)">
																						&nbsp;&nbsp;<html:text property="property(PAGINA)" styleClass="inputSelectScustomWidth" readonly="false" maxlength="250" size="61"/>
																						&nbsp; <bean:message key="catalog.data.obligatory"/>
																						<div id="formErrors">
																							<html:errors property="property(PAGINA)"/>
																						</div>
																					</logic:notEmpty>
																					<logic:empty name="defaultForm" property="property(FRM_VERSION)">
																						&nbsp;&nbsp;<html:text property="property(PAGINA)" styleClass="inputReadOnly" readonly="true" maxlength="250" size="61"/>
																					</logic:empty>
																				</td>
																			</tr>
																			
																			<logic:notEmpty name="defaultForm" property="property(FRM_VERSION)">
																			
																				<tr>
																					<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="8px"/></td>
																				</tr>
																				<tr>
																					<td height="20" class="formsTitle">
																						<div id="form.app.propertyLabel.version">
																							<ispac:tooltipLabel labelKey="form.app.propertyLabel.version" tooltipKey="form.app.propertyLabel.version.tooltip"/>
																						</div>
																					</td>
																					<td height="20" class="formsTitle">
																						&nbsp;&nbsp;<html:text property="property(FRM_VERSION)" styleClass="inputReadOnly" size="5" readonly="true" maxlength="22"/>
																					</td>
																				</tr>
																				
																			</logic:notEmpty>
																			
																		</logic:notEmpty>
																		
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

<script> 
    clase = document.defaultForm.elements[ 'property(CLASE)' ];
    if (clase.value == '') {
    	clase.value = "ieci.tdw.ispac.ispaclib.app.GenericSecondaryEntityApp";
    }
</script>
