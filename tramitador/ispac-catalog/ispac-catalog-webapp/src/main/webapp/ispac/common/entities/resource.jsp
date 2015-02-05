<%@ page language="java" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/ispac-util.tld" prefix="ispac" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>


<script type="text/javascript" src='<ispac:rewrite href="../scripts/jquery-1.3.2.min.js"/>'></script>
<script type="text/javascript" src='<ispac:rewrite href="../scripts/jquery-ui-1.7.2.custom.min.js"/>'></script>
<script type="text/javascript" src='<ispac:rewrite href="../scripts/jquery.alerts.js"/>'></script>


<script language='JavaScript' type='text/javascript'>

	function doSubmit() {
		document.defautForm.submit();
	}
	
	$(document).ready(function() {

			$("#move").draggable();
			
		});

//--></script>


<div id="move">
<div class="encabezado_ficha">
	<div class="titulo_ficha">
		<h4><bean:message key="entityManage.resource.mainTitle"/></h4>
		<div class="acciones_ficha">
			<bean:define id="language" name="language"/>
			<c:url var="addResource" value="/showCTEntityToManage.do">
				<c:param name="method" value="addOtherResource"></c:param>
				<c:param name="language" value="${language}"/>
			</c:url>
			<a  class="btnOk" href="javascript:submit('<c:out value="${addResource}" escapeXml="false" />')">
					<nobr><bean:message key="forms.button.accept"/></nobr>
			</a>
			<a  class="btnCancel" href="javascript:parent.hideFrame('workframe','<ispac:rewrite page="wait.jsp"/>')">
				<bean:message key="forms.button.cancel"/>
			</a>	
		</div>
	</div>
</div>


<div id="navmenu">

</div>

<html:form action="/showCTEntityToManage.do">

	<table width="100%" border="0" cellspacing="1" cellpadding="0">
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
												<html:errors />
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
															<tr>
															<tr>
																<td height="20" class="formsTitle">
																	<div id="entityManage.label.clave">
																		<ispac:tooltipLabel labelKey="entityManage.resource.propertyLabel.key" tooltipKey="entityManage.resource.propertyLabel.key.tooltip"/>
																	</div>
																</td>
																<td height="20" class="formsTitle">
																	&nbsp;&nbsp;<html:text property="property(CLAVE)" styleClass="inputSelectS" readonly="false" maxlength="250"/>
																	&nbsp; <bean:message key="catalog.data.obligatory"/>
																	<%--
																	<div id="formErrors">
																		<html:errors property="property(CLAVE)"/>
																	</div>
																	--%>
																</td>
															</tr>
															<tr>
																<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="8px"/></td>
															</tr>
															<tr>
																<td height="20" class="formsTitle">
																	<div id="entityManage.label.value">
																		<ispac:tooltipLabel labelKey="entityManage.resource.propertyLabel.text" tooltipKey="entityManage.resource.propertyLabel.text.tooltip"/>
																	</div>
																</td>
																<td height="20" class="formsTitle">
																	&nbsp;&nbsp;<html:text property="property(VALOR)" styleClass="inputSelectS" readonly="false" maxlength="250"/>
																	&nbsp; <bean:message key="catalog.data.obligatory"/>
																	<%--
																	<div id="formErrors">
																		<html:errors property="property(VALOR)"/>
																	</div>
																	--%>
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
</html:form>
</div>