<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/ispac-util.tld" prefix="ispac" %>

<script language='JavaScript' type='text/javascript'><!--

	function accept(url) {
	
		if (document.getElementById('checkPropertyActiva').checked) {
			document.getElementById('hiddenPropertyActiva').value = '1';
		}
		else {
			document.getElementById('hiddenPropertyActiva').value = '0';
		}

		submit(url);
	}
	
//--></script>

<div id="navmenutitle">
	<bean:message key="form.pubApplication.mainTitle"/>
</div>
<div id="navSubMenuTitle">
</div>

<div id="navmenu">
	<ul class="actionsMenuList">
		<logic:equal name="defaultForm" property="key" value="-1">
			<ispac:hasFunction functions="FUNC_PUB_APPLICATIONS_EDIT">
			<li>
				<a href="javascript:accept('<%= request.getContextPath() + "/storeCTEntity.do"%>')">
					<nobr><bean:message key="forms.button.accept"/></nobr>
				</a>
			</li>
			</ispac:hasFunction>
			<li>
				<a href='<%=request.getContextPath() + "/showPubApplicationsList.do"%>'>
					<nobr><bean:message key="forms.button.cancel"/></nobr>
				</a>
		  	</li>
		</logic:equal>
		<logic:notEqual name="defaultForm" property="key" value="-1">
			<ispac:hasFunction functions="FUNC_PUB_APPLICATIONS_EDIT">
			<li>
				<a href="javascript:accept('<%= request.getContextPath() + "/storeCTEntity.do"%>')">
					<nobr><bean:message key="forms.button.save"/></nobr>
				</a>
			</li>
			</ispac:hasFunction>
			<li>
				<a href='<%=request.getContextPath() + "/showPubApplicationsList.do"%>'>
					<nobr><bean:message key="forms.button.close"/></nobr>
				</a>
		  	</li>
		  	<ispac:hasFunction functions="FUNC_PUB_APPLICATIONS_EDIT">
		  	<li>
				<a href="javascript:query('<%= request.getContextPath() + "/deletePubApplication.do"%>', '<bean:message key="message.deleteConfirm"/>','<bean:message key="common.confirm"/>', '<bean:message key="common.message.ok"/>','<bean:message key="common.message.cancel"/>')">
					<nobr><bean:message key="forms.button.delete"/></nobr>
				</a>		
		  	</li>
		  	</ispac:hasFunction>
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
	
	<html:hidden property="property(ACTIVA)" styleId="hiddenPropertyActiva"/>

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
																	
																		<tr>
																			<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="8px"/></td>
																		</tr>
																		<logic:notEqual name="defaultForm" property="key" value="-1">
																			<tr>
																				<td height="20" class="formsTitle" width="1%">
																					<ispac:tooltipLabel labelKey="form.pubApplication.propertyLabel.id" tooltipKey="form.pubApplication.propertyLabel.id.tooltip"/>
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
																				<ispac:tooltipLabel labelKey="form.pubApplication.propertyLabel.name" tooltipKey="form.pubApplication.propertyLabel.name.tooltip"/>
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
																				<ispac:tooltipLabel labelKey="form.pubApplication.propertyLabel.active" tooltipKey="form.pubApplication.propertyLabel.active.tooltip"/>
																			</td>
																			<td height="20">
																				&nbsp;&nbsp;<html:checkbox property="property(ACTIVA)" styleClass="checkbox" value="1" styleId="checkPropertyActiva"/>
																				<div id="formErrors">
																					<html:errors property="property(ACTIVA)"/>
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
