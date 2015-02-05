<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/ispac-util.tld" prefix="ispac" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>



<script type="text/javascript" src='<ispac:rewrite href="../scripts/jquery-1.3.2.min.js"/>'></script>
<script type="text/javascript" src='<ispac:rewrite href="../scripts/jquery-ui-1.7.2.custom.min.js"/>'></script>
<script type="text/javascript" src='<ispac:rewrite href="../scripts/jquery.alerts.js"/>'></script>

<c:set var="entityId" value="${param.entityId}"/>             
<c:set var="key" value="${param.key}"/>


<div id="move">
<div class="encabezado_ficha">
	<div class="titulo_ficha">
		<h4><bean:message key="catalog.create.ctplant"/></h4>
		<div class="acciones_ficha">
			<a href="javascript:document.uploadForm.submit();"  class="btnOk">
					<bean:message key="forms.button.accept"/>
			</a>
			
			<a href="javascript:parent.hideFrame('workframe','<ispac:rewrite page="wait.jsp"/>')"  class="btnCancel">
					<bean:message key="forms.button.cancel"/>
			</a>
		</div>
	</div>
</div>



<div id="navmenu">
		<div id="navmenu">
			<tiles:insert page="../tiles/AppErrorsTile.jsp" ignore="true" flush="false"/>
		</div>
	
</div>
<div class="stdform">
	
<html:form action='/storeTemplateFromCuadroView.do' enctype="multipart/form-data">
	<html:hidden property="entityAppName" value="EditCTTemplate"/> 
	<!-- Identificador de la entidad -->
	<html:hidden property="entity"/>
	<!-- Identificador del registro -->
	<html:hidden property="key"/>
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
												<logic:notEqual name="uploadForm" property="key" value="-1">
													<tr>
														<td height="20" class="formsTitle" width="1%">
															<ispac:tooltipLabel labelKey="form.template.propertyLabel.id" tooltipKey="form.template.propertyLabel.id.tooltip"/>
														</td>
														<td height="20">
															&nbsp;&nbsp;<html:text property="property(ID)" styleClass="inputReadOnly" size="5" readonly="true" maxlength="10"/>
														</td>
													</tr>
												</logic:notEqual>
												<tr>
													<td height="20" class="formsTitle" width="1%">
														<ispac:tooltipLabel labelKey="form.template.propertyLabel.type" tooltipKey="form.template.propertyLabel.type.tooltip"/>
													</td>
													<td height="20">
														&nbsp;&nbsp;<html:text property="property(ID_TPDOC)" styleClass="inputReadOnly" size="5" readonly="true" maxlength="10"/>
														<div id="formErrors">
																<html:errors property="property(ID_TPDOC)"/>
														</div>
													</td>
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
														<ispac:tooltipLabel labelKey="form.template.propertyLabel.name" tooltipKey="form.template.propertyLabel.name.tooltip"/>
													</td>
													<td height="20">
														&nbsp;&nbsp;<html:text property="property(NOMBRE)" styleClass="inputSelectS" readonly="false" maxlength="100"/>&nbsp; <bean:message key="catalog.data.obligatory"/>
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
														<ispac:tooltipLabel labelKey="form.template.propertyLabel.code" tooltipKey="form.template.propertyLabel.code.tooltip"/>
													</td>
													<td height="20">
														&nbsp;&nbsp;<html:text property="property(COD_PLANT)" styleClass="inputSelectS" readonly="false" maxlength="16"/>
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
														&nbsp;&nbsp;<input type="text" value='<bean:write name="uploadForm" property="entityApp.property(PROCEDURE_TEMPLATE)"/>' class="inputReadOnly" size="15" readonly="readonly">
													</td>
												</tr>
												<tr>
													<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="8px"/></td>
												</tr>
												<tr>
													<td class="formsTitle">
														<ispac:tooltipLabel labelKey="form.template.propertyLabel.expression" tooltipKey="form.template.propertyLabel.expression.tooltip"/>
													</td>
													<td>
														&nbsp;&nbsp;<html:textarea property="property(EXPRESION)" styleClass="inputSelectS" readonly="false"
															cols="60" rows="10" styleId="templateExp" onkeypress="javascript:maxlength('templateExp', 2000)"/>
														<div id="formErrors">
																<html:errors property="property(EXPRESION)"/>
														</div>
													</td>
												</tr>
												<tr>
													<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="8px"/></td>
												</tr>
												<tr>
													<td class="formsTitle">
														<ispac:tooltipLabel labelKey="form.template.propertyLabel.documento" tooltipKey="form.template.propertyLabel.documento.tooltip"/>
													</td>
													<td>
														&nbsp;&nbsp;<html:file property="uploadFile" styleClass="input"/>
														<div id="formErrors">
																<html:errors property="uploadFile"/>
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
													  &nbsp;&nbsp;<html:text property="property(FECHA)" styleClass="inputReadOnly" size="16" readonly="true" maxlength="10"/>
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
<script>

function redirect(url)
	{
		submit('<%=request.getContextPath()%>' + url);
	}
$(document).ready(function() {$("#move").draggable();});
</script>