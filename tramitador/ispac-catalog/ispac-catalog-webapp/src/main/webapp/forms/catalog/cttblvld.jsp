<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/ispac-util.tld" prefix="ispac" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>

<div id="navmenutitle">
	<bean:message key="form.tblvldvalue.mainTitle"/>
</div>
<div id="navSubMenuTitle">
	<bean:message key="form.tblvldvalue.subtitle"/>
</div>
<div id="navmenu" >

	<ul class="actionsMenuList">
	
		<logic:equal name="defaultForm" property="key" value="-1">
			<ispac:hasFunction functions="FUNC_COMP_VALIDATION_TABLES_EDIT">			
			<li>
				<a href="javascript:submit('<%= request.getContextPath() + "/ManageTblVld.do?method=store"%>')">
					<nobr><bean:message key="forms.button.accept"/></nobr>
				</a>	       	
			</li>
			</ispac:hasFunction>
			<li>
				<a href='<%=request.getContextPath() + "/showCTEntityToManage.do?method=listavalores"%>'>
					<nobr><bean:message key="forms.button.cancel"/></nobr>
				</a>	       	
			</li>	  
		</logic:equal>
		<logic:notEqual name="defaultForm" property="key" value="-1">			
			<ispac:hasFunction functions="FUNC_COMP_VALIDATION_TABLES_EDIT">
			<li>
				<a href="javascript:submit('<%= request.getContextPath() + "/ManageTblVld.do?method=store"%>')">
					<nobr><bean:message key="forms.button.save"/></nobr>
				</a>	       	
			</li>
			</ispac:hasFunction>
			<li>
				<a href='<%=request.getContextPath() + "/showCTEntityToManage.do?method=listavalores"%>'>
					<nobr><bean:message key="forms.button.close"/></nobr>
				</a>	       	
			</li>	  
		</logic:notEqual>
	</ul>
</div>

<html:form action='/ManageTblVld.do'>

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

	<c:set var="pType" value="${param['type']}"/>
	<input type="hidden" name="type" value='<c:out value="${pType}"/>'/>
	
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
																				<td height="20" class="formsTitle" width="1%">
																					<ispac:tooltipLabel labelKey="form.tblvld.codigo" tooltipKey="form.tblvld.codigo.tooltip"/>
																				</td>
																				<td height="20">
																					&nbsp;&nbsp;<html:text property="property(ID)" styleClass="inputReadOnly" size="5" readonly="true" maxlength="10"/>
																				</td>
																			</tr>
																		</logic:notEqual>
																		<c:if test="${!requestScope['TIENE_SUSTITUTO']}">
																		<tr>
																			<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="8px"/></td>
																		</tr>
																		<tr>
																			<td height="20" class="formsTitle" width="1%">
																				<ispac:tooltipLabel labelKey="form.tblvld.valor" tooltipKey="form.tblvld.valor.tooltip"/>
																			</td>
																			<td height="20">
																				<bean:define id="tamValor" name="TAM_VALOR" type="java.lang.Integer"/>																				
																				<%-- &nbsp;&nbsp;<html:text property="property(VALOR)" styleClass="input" readonly="false" size='<%=String.valueOf(tamValor)%>' maxlength='<%=String.valueOf(tamValor)%>'/>&nbsp; <bean:message key="catalog.data.obligatory"/> --%>
																				&nbsp;&nbsp;<html:text property="property(VALOR)" styleClass="input" readonly="false" size="80" maxlength='<%=String.valueOf(tamValor)%>'/>&nbsp; <bean:message key="catalog.data.obligatory"/>
																				<div id="formErrors">
																					<html:errors property="property(VALOR)"/>
																				</div>
																			</td>
																		</tr>
																		</c:if>
																		<c:if test="${requestScope['TIENE_SUSTITUTO']}">
																		<tr>
																			<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="8px"/></td>
																		</tr>
																		<tr>
																			<td height="20" class="formsTitle" width="1%">
																				<ispac:tooltipLabel labelKey="form.tblvld.valor" tooltipKey="form.tblvld.valor.tooltip"/>
																			</td>
																			<td height="20">
																				<bean:define id="tamValor" name="TAM_VALOR" type="java.lang.Integer"/>
																				&nbsp;&nbsp;<html:text property="property(VALOR)" styleClass="input" readonly="false" size='<%=String.valueOf(tamValor)%>' maxlength='<%=String.valueOf(tamValor)%>'/>&nbsp; <bean:message key="catalog.data.obligatory"/>
																				<div id="formErrors">
																					<html:errors property="property(VALOR)"/>
																				</div>
																			</td>
																		</tr>
																		<tr>
																			<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="8px"/></td>
																		</tr>
																		<tr>
																			<td height="20" class="formsTitle">
																				<ispac:tooltipLabel labelKey="form.tblvld.sustituto" tooltipKey="form.tblvld.sustituto.tooltip"/>
																			</td>
																			<td height="20">
																				<bean:define id="tamSustituto" name="TAM_SUSTITUTO" type="java.lang.Integer"/>																				
																				<%-- &nbsp;&nbsp;<html:text property="property(SUSTITUTO)" styleClass="input" size='<%=String.valueOf(tamSustituto)%>' readonly="false" maxlength='<%=String.valueOf(tamSustituto)%>'/>&nbsp; <bean:message key="catalog.data.obligatory"/> --%>
																				&nbsp;&nbsp;<html:text property="property(SUSTITUTO)" styleClass="input" size='80' readonly="false" maxlength='<%=String.valueOf(tamSustituto)%>'/>&nbsp; <bean:message key="catalog.data.obligatory"/>
																				<div id="formErrors">
																					<html:errors property="property(SUSTITUTO)"/>
																				</div>
																			</td>
																		</tr>
																		</c:if>
																		<tr>
																			<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="8px"/></td>
																		</tr>
																		<tr>
																			<td height="20" class="formsTitle">
																				<ispac:tooltipLabel labelKey="form.tblvld.vigente" tooltipKey="form.tblvld.vigente.tooltip"/>
																			</td>
																			<td height="20">
																				&nbsp;&nbsp;<html:checkbox property="property(VIGENTE)" styleClass="checkbox" value="1" />
																				<div id="formErrors">
																					<html:errors property="property(VIGENTE)"/>
																				</div>
																			</td>
																		</tr>
																	  <logic:notEqual name="defaultForm" property="key" value="-1"> 
																		<tr>
																			<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="8px"/></td>
																		</tr>
																		<tr>
																			<td height="20" class="formsTitle">
																				<ispac:tooltipLabel labelKey="form.tblvld.orden" tooltipKey="form.tblvld.orden.tooltip"/>
																			</td>
																			<td height="20">
																					<bean:define id="tamOrden" name="TAM_ORDEN" type="java.lang.Integer"/>
																				&nbsp;&nbsp;<html:text property="property(ORDEN)" styleClass="inputReadOnly" readonly="true" size='<%=String.valueOf(tamOrden)%>' maxlength='<%=String.valueOf(tamOrden)%>'/>
																				<div id="formErrors">
																					<html:errors property="property(ORDEN)"/>
																				</div>
																			</td>
																		</tr>
																		</logic:notEqual>
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
