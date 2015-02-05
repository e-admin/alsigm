<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/ispac-util.tld" prefix="ispac"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>

<script language='JavaScript' type='text/javascript'><!--

	function save()
	{
		document.defaultForm.target = "ParentWindow";
		document.defaultForm.action = "storeEntity.do";
		//document.defaultForm.name = "Subvenciones";

//		if (validateSubvenciones(document.defaultForm))
			document.defaultForm.submit();
	}

//--></script>

<ispac:rewrite id="imgcalendar" href="img/calendar/"/>
<ispac:rewrite id="jscalendar" href="../scripts/calendar.js"/>
<ispac:rewrite id="buttoncalendar" href="img/calendar/calendarM.gif"/>

<ispac:calendar-config imgDir='<%= imgcalendar %>' scriptFile='<%= jscalendar %>'/>
<html:form action="storeEntity.do">

	<!-- Solapa que se muestra -->
	<html:hidden property="block" value="1"/>

	<!-- Nombre de Aplicación.
		 Necesario para realizar la validación -->
	<html:hidden property="entityAppName"/>

	<!-- Identificador de la entidad -->
	<html:hidden property="entity"/>
	<!-- Identificador del registro -->
	<html:hidden property="key"/>
	<!-- Registro de solo lectura-->
	<html:hidden property="readonly"/>

	<table cellpadding="0" cellspacing="0" border="0" width="100%">
		<tr>
			<td>
				<table class="boxTab" width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td class="title" height="28px">
							<table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%">
								<tr>
									<td>
										<table align="center" cellpadding="0" cellspacing="0" border="0" width="100%">
											<tr valign="middle" height="28">
												<td>
												<!-- COMIENZO DE LAS ACCIONES -->
													<table cellpadding="0" cellspacing="0" border="0" width="100%" height="100%">
														<tr>
															<td width="4px" height="28px"><img height="1" width="4px" src='<ispac:rewrite href="img/pixel.gif"/>'/></td>
															<!-- ACCION SALVAR -->
															<logic:equal value="false" property="readonly" name="defaultForm">
																<td class="formaction" height="28px"><a href="javascript:save();" class="formaction">
																	<bean:message key="forms.button.save"/>
																</a></td>
															</logic:equal>
															<!--FIN ACCION SALVAR -->
															<td height="28px"><img height="1" src='<ispac:rewrite href="img/pixel.gif"/>'/></td>
														</tr>
													</table>
												<!-- FINAL DE LAS ACCIONES -->
												</td>
											</tr>
										</table>
									</td>
								</tr>
							</table>
						</td>
					</tr>
					<!-- FORMULARIO -->
					<tr>
						<td class="blank">
							<table width="100%" border="0" cellspacing="2" cellpadding="2">
								<tr>
									<td height="5px" colspan="3"><html:errors/></td>
								</tr>
								<tr>
									<td width="8"><img height="1" width="8px" src='<ispac:rewrite href="img/pixel.gif"/>'/></td>
									<td width="100%">
										<table width="100%" border="0" class="formtable" cellspacing="0" cellpadding="0">
											<tr>
												<td><img height="8px" src='<ispac:rewrite href="img/pixel.gif"/>'/></td>
											</tr>
											<tr>
												<td><img height="1px" width="10px" src='<ispac:rewrite href="img/pixel.gif"/>'/><!-- aqui van los errores--></td>
											</tr>
											<tr>
												<td>
													<!-- TABLA DE CAMPOS -->
													<logic:iterate id="ppt" name="defaultForm" property="entityApp.item.properties" type="ieci.tdw.ispac.api.item.Property">
													<c:set var="propertyName"><bean:write name="ppt" property="name"/></c:set>
													<jsp:useBean id="propertyName" type="java.lang.String"/>
													<tr>
														<td><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="8px"/></td>
													</tr>
													<tr>
														<td>
															<table border="0" cellspacing="0" cellpadding="0" width="100%">
																<tr>
																	<td width="15px"><img height="1" width="15px" src='<ispac:rewrite href="img/pixel.gif"/>'/></td>
																	<td height="20" width="350px" class="formsTitleB" valign="top">
																		<nobr><%=propertyName%>:</nobr>
																	</td>
																	<td height="20">
																		<logic:equal value="false" property="readonly" name="defaultForm">
																			<html:text property='<%="property("+propertyName+")"%>' styleClass="input" size="50"/>
																		</logic:equal>
																		<logic:equal value="true" property="readonly" name="defaultForm">
																			<html:text property='<%="property("+propertyName+")"%>' styleClass="inputReadOnly" size="50" readonly="true"/>
																		</logic:equal>
																	</td>
																</tr>
															</table>
														</td>
													</tr>			
													</logic:iterate>
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

<%-- Manejador de block para resituarse en la pestaña en la que nos encontrabamos --%>
<tiles:insert template="/forms/common/manageBlock.jsp"/>

<%-- Para informar si se intenta salir del formulario sin guardar --%>
<tiles:insert template="/forms/common/observer.jsp"/>
