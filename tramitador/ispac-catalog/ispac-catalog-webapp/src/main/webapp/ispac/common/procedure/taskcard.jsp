<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/ispac-util.tld" prefix="ispac" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>

<div class="tabButtons">
	<ul class="tabButtonList">
		<bean:define id="subPcdId" name="defaultForm" property="property(ID_SUBPROCESO)"/>
		<c:if test="${!empty subPcdId}">
		<li>
			<c:url var="showSubProcedureURL" value="manageVistaCuadroProcedimiento.do">
				<c:param name="method" value="inithome"/>
				<c:param name="entityId" value="33"/>
				<c:param name="regId" value="${subPcdId}"/>
			</c:url>
			<a href="#"
				onclick="javascript:return messageConfirm('<c:out value="${showSubProcedureURL}"/>' ,'<bean:message key="procedure.card.confirm.showSubProcedure"/>', '<bean:message key="common.confirm"/>', '<bean:message key="common.message.ok"/>' ,'<bean:message key="common.message.cancel"/>', window.top)">
				<nobr><bean:message key="procedure.versions.actions.showSubProcedure"/></nobr>
			</a>
		</li>
		</c:if>
	</ul>
</div>

<tiles:insert page="../tiles/pcdBCTile.jsp" ignore="true" flush="false"/>

<div class="tabContent">
	
<html:form action='/showProcedureEntity.do'>

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
														<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="12px"/></td>
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
														&nbsp;&nbsp;<html:text property="property(NOMBRE)" styleClass="inputReadOnly" size="50" readonly="true" maxlength="250"/>
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
														<ispac:tooltipLabel labelKey="form.task.propertyLabel.type" tooltipKey="form.task.propertyLabel.type.tooltip"/>
													</td>
													<td height="20">
														&nbsp;&nbsp;<html:text property="property(TIPO)" styleClass="inputReadOnly" size="50" readonly="true" maxlength="32"/>
													</td>
												</tr>
												<tr>
													<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="8px"/></td>
												</tr>
												<tr>
													<td height="20" class="formsTitle">
														<ispac:tooltipLabel labelKey="form.task.propertyLabel.code" tooltipKey="form.task.propertyLabel.code.tooltip"/>
													</td>
													<td height="20">
														&nbsp;&nbsp;<html:text property="property(COD_TRAM)" styleClass="inputReadOnly" size="10" readonly="true" maxlength="16"/>
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
														&nbsp;&nbsp;<html:text property="property(ORDENACION)" styleClass="inputReadOnly" size="10" readonly="true" maxlength="2"/>
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
														&nbsp;&nbsp;<html:textarea property="property(DESCRIPCION)" styleClass="inputReadOnly" readonly="true" cols="72" rows="2"
															styleId="texta" onkeypress="javascript:maxlength('texta', 1024)"/>
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
														&nbsp;&nbsp;<html:textarea property="property(OBSERVACIONES)" styleClass="inputReadOnly" readonly="true" cols="72" rows="2"
															styleId="observ" onkeypress="javascript:maxlength('observ', 512)"/>
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