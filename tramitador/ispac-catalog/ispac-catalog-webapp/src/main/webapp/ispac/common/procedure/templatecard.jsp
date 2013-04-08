<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/ispac-util.tld" prefix="ispac" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>

<script>
	function editTemplate() {

		<c:if test="${PLANT_ESPECIFICA}">
			jAlert('<bean:message key="form.template.edicionTemplateEspecifica"/>', '<bean:message key="common.alert"/>', '<bean:message key="common.message.ok"/>' , '<bean:message key="common.message.cancel"/>',function(r) {
			if(r) {
			</c:if>	
			var url = '<%= request.getContextPath()%>' + '/editTemplate.do?template=' + '<bean:write name="defaultForm" property="property(ID)"/>';     
			document.forms[0].target = "editTemplateFrame";
			document.forms[0].action = url;
			document.forms[0].submit();
		
		<c:if test="${PLANT_ESPECIFICA}">	
			}});
		</c:if>
	}
</script>

<div class="tabButtons">
	<ispac:hasFunction functions="FUNC_INV_PROCEDURES_EDIT">
	<ul class="tabButtonList">
		<li>
			<a href='javascript:editTemplate()'>
				<nobr><bean:message key="forms.button.edit"/></nobr>
			</a>
		</li>
	</ul>
	</ispac:hasFunction>
</div>

<tiles:insert page="../tiles/pcdBCTile.jsp" ignore="true" flush="false"/>

<div class="tabContent">
<html:form action='/storeTemplateFromCuadroView.do'>
	<html:hidden property="entityAppName" value="EditCTTemplate"/> 
	<!-- Identificador de la entidad -->
	<html:hidden name="defaultForm" property="entity"/>
	<!-- Identificador del registro -->
	<html:hidden name="defaultForm" property="key"/>
	<table width="100%" cellspacing="1" cellpadding="0">
		<tr>
			<td class="blank">
				<table width="100%" border="0" cellspacing="2" cellpadding="2">
					<tr>
						<td width="100%">
							<div class="formtable">
								<!-- TABLA DE CAMPOS -->
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
															<ispac:tooltipLabel labelKey="form.template.propertyLabel.id" tooltipKey="form.template.propertyLabel.id.tooltip"/>
														</td>
														<td height="20">
															&nbsp;&nbsp;<html:text name="defaultForm" property="property(ID)" styleClass="inputReadOnly" size="5" readonly="true" maxlength="10"/>
														</td>
													</tr>
												</logic:notEqual>
												<tr>
													<td height="20" class="formsTitle" width="1%">
														<ispac:tooltipLabel labelKey="form.template.propertyLabel.type" tooltipKey="form.template.propertyLabel.type.tooltip"/>
													</td>
													<td height="20">
														&nbsp;&nbsp;<html:text name="defaultForm" property="property(ID_TPDOC)" styleClass="inputReadOnly" size="5" readonly="true" maxlength="10"/>
														<div id="formErrors">
																<html:errors property="property(ID_TPDOC)"/>
														</div>
													</td>
												</tr>
												<tr>
													<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="4px"/></td>
												</tr>
												<tr>
													<td colspan="2" height="20" class="formsTitle">
														<hr size="2"/>
													</td>
												</tr>
												<tr>
													<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="4px"/></td>
												</tr>
												<tr>
													<td height="20" class="formsTitle">
														<ispac:tooltipLabel labelKey="form.template.propertyLabel.name" tooltipKey="form.template.propertyLabel.name.tooltip"/>
													</td>
													<td height="20">
														&nbsp;&nbsp;<html:text name="defaultForm" property="property(NOMBRE)" styleClass="inputReadOnly" size="50" readonly="true" maxlength="100"/>&nbsp;
														<div id="formErrors">
																<html:errors property="property(NOMBRE)"/>
														</div>
													</td>
												</tr>
												<tr>
													<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="4px"/></td>
												</tr>
												<tr>
													<td height="20" class="formsTitle">
														<ispac:tooltipLabel labelKey="form.template.propertyLabel.code" tooltipKey="form.template.propertyLabel.code.tooltip"/>
													</td>
													<td height="20">
														&nbsp;&nbsp;<html:text name="defaultForm" property="property(COD_PLANT)" styleClass="inputReadOnly" size="10" readonly="true" maxlength="16"/>
														<div id="formErrors">
																<html:errors property="property(COD_PLANT)"/>
														</div>
													</td>
												</tr>
												<tr>
													<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="8px"/></td>
												</tr>
												<tr>
													<td height="20" class="formsTitle" width="1%">
														<ispac:tooltipLabel labelKey="form.template.propertyLabel.typeTemplate" tooltipKey="form.template.propertyLabel.typeTemplate.tooltip"/>
													</td>
													<td height="20">
														&nbsp;&nbsp;<input type="text" value='<bean:write name="TEMPLATE_ITEM" property="property(PROCEDURE_TEMPLATE)"/>' class="inputReadOnly" size="15" readonly="readonly">
													</td>
												</tr>
												<tr>
													<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="8px"/></td>
												</tr>
												<tr>
													<td class="formsTitle" style="vertical-align:top;">
														<ispac:tooltipLabel labelKey="form.template.propertyLabel.expression" tooltipKey="form.template.propertyLabel.expression.tooltip"/>
													</td>
													<td>
														&nbsp;&nbsp;<html:textarea name="defaultForm" property="property(EXPRESION)" styleClass="inputReadOnly" readonly="true"
															cols="60" rows="8" styleId="expresion" onkeypress="javascript:maxlength('expresion', 2000)"/>
														<div id="formErrors">
																<html:errors property="property(EXPRESION)"/>
														</div>
													</td>
												</tr>
												<tr>
													<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="8px"/></td>
												</tr>
												<tr>
													<td height="20" class="formsTitle">
														<ispac:tooltipLabel labelKey="form.template.propertyLabel.creationDate" tooltipKey="form.template.propertyLabel.creationDate.tooltip"/>
													</td>
													<td height="20">
													  &nbsp;&nbsp;<html:text name="defaultForm" property="property(FECHA)" styleClass="inputReadOnly" size="16" readonly="true" maxlength="10"/>
													  <div id="formErrors">
														<html:errors property="property(FECHA)"/>
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
				</table>
			</td>
		</tr>
	</table>
</html:form>
</div>
<iframe src='' id='editTemplateFrame' name='editTemplateFrame' style='visibility:visible;height:0px;margin:0px;padding:0px;border:none;'></iframe>