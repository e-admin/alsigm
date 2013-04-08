<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/taglibs/securityTag.tld" prefix="security"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/archigest.tld" prefix="archigest" %>

<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>

<script language="JavaScript1.2" src="<%=request.getContextPath()%>/js/ficha.js" type="text/JavaScript"></script>

<c:set var="udoc" value="${sessionScope[appConstants.fondos.UDOC_A_DIVIDIR]}" />
<c:set var="divisionFraccionSerie" value="${sessionScope[appConstants.fondos.DIVISION_FRACCION_SERIE]}" />
<c:set var="FORMATO_FECHA" value="${appConstants.common.FORMATO_FECHA}"/>
<bean:struts id="actionMapping" mapping="/gestionFraccionSerie" />
<c:set var="__FORM_NAME" value="${actionMapping.name}" />
<jsp:useBean id="__FORM_NAME" type="java.lang.String" />
<c:url var="calendarImgDir" value="/pages/images/calendario/" />
<jsp:useBean id="calendarImgDir" type="java.lang.String" />
<archigest:calendar-config imgDir="<%=calendarImgDir%>" scriptFile="../scripts/calendar.js"/>

<tiles:insert template="/pages/tiles/PABoxLayout.jsp">
	<tiles:put name="boxTitle" direct="true">
		<bean:message key="archigest.archivo.cf.dividir"/><c:out value="${udoc.nombreNivel}"/>
	</tiles:put>

	<tiles:put name="buttonBar" direct="true">
		<table>
			<tr>
				<td>
						<script>
							var enviado = false;
							function crearUDocsFraccionSerie() {
								if (!enviado){
									var form = document.forms['<c:out value="${actionMapping.name}" />'];	
									if (window.top.showWorkingDiv) {
										var title = '<bean:message key="archigest.archivo.msg.titulo"/>';
										var message = '<bean:message key="archigest.archivo.msg.proceso.en.ejecucion"/>';
										window.top.showWorkingDiv(title, message);
									}
									form.method.value="crearUDocsFraccionSerie";
									enviado = true;
									form.submit();
								} 
							}
						</script>
						<a class="etiquetaAzul12Bold" 
							href="javascript:crearUDocsFraccionSerie();" >
							<html:img page="/pages/images/dividir_fraccion_serie_color.gif" 
								altKey="archigest.archivo.cf.dividirFraccionSerie" 
								titleKey="archigest.archivo.cf.dividirFraccionSerie" 
								styleClass="imgTextBottom" />
								&nbsp;<bean:message key="archigest.archivo.cf.dividirFraccionSerie"/>
						</a>
					</td>
					<td width="10">&nbsp;</td>
					<td nowrap>
						<tiles:insert definition="button.closeButton" flush="true">
							<tiles:put name="labelKey" direct="true">archigest.archivo.cerrar</tiles:put>
							<tiles:put name="imgIcon" direct="true">/pages/images/close.gif</tiles:put>
						</tiles:insert>
					</td>
			</tr>
		</table>
	</tiles:put>
	
	<tiles:put name="boxContent" direct="true">	
					
		<html:form action="/gestionFraccionSerie" >
			<input type="hidden" name="method" value=""/>
			<tiles:insert page="/pages/tiles/PABlockLayout.jsp">
			<tiles:put name="blockTitle" direct="true">
				<c:out value="${udoc.nombreNivel}"/>
			</tiles:put>
			<tiles:put name="blockContent" direct="true">	
				<TABLE class="formulario"> <%-- para aspecto de formulario con lineas bottom de celda --%>
						<TR>
							<TD class="tdTitulo" width="200px">
								<bean:message key="archigest.archivo.cf.codigo"/>:&nbsp;
							</TD>
							<TD class="tdDatos">
								<c:out value="${udoc.codReferencia}" />
							</TD>
						</TR>
						<TR>
							<TD class="tdTitulo">
								<c:out value="${udoc.nombreNivel}"/>:&nbsp;
							</TD>
							<TD class="tdDatos">
								<c:out value="${udoc.titulo}" />
							</TD>
						</TR>
						<TR>
							<TD class="tdTitulo">
								<bean:message key="archigest.archivo.archivoCustodia"/>:&nbsp;
							</TD>
							<TD class="tdDatos">
								<c:out value="${udoc.archivo.nombre}" />
							</TD>
						</TR>				
						<TR>
							<TD class="tdTitulo">
								<bean:message key="archigest.archivo.cf.serie"/>:&nbsp;
							</TD>
							<TD class="tdDatos">
								<c:url var="verSerieURL" value="/action/gestionIdentificacionAction">
									<c:param name="method" value="veridentificacionserie" />
									<c:param name="idelementocf" value="${udoc.serie.id}" />
								</c:url>
								<a href="<c:out value="${verSerieURL}" escapeXml="false"/>" class="tdlink">
									<c:out value="${udoc.serie.titulo}" />
								</a>
							</TD>
						</TR>
					</TABLE>
				</tiles:put>
			</tiles:insert>	
			
			<div class="separador8">&nbsp;</div>
				
			<%-- Solicitar Unidades Documentales en las que se quiere dividir la Fracción de Serie --%>
						<tiles:insert page="/pages/tiles/PABlockLayout.jsp">
							<tiles:put name="blockTitle" direct="true">
								<bean:message key="archigest.archivo.numeroUnidadesDocumentales"/>
							</tiles:put>
							<tiles:put name="blockContent" direct="true">
								<TABLE class="formulario">
										<TR>
											<TD colspan="2">
												<TABLE class="w98m1" cellpadding="0" cellspacing="0">
													<TR>
														<TD class="tdTitulo" width="560px">
															<bean:message key="archigest.archivo.cf.introducirNumeroUDocs"/>:&nbsp;															
														</TD>	
														<TD class="tdDatos">
															<html:text property="nofUDocs" styleClass="input" styleId="nofUDocs"></html:text>
														</TD>
													</TR>
												</TABLE>
											</TD>
										</TR>
									</TABLE>
							</tiles:put>	
						</tiles:insert>
						
						<div class="separador8">&nbsp;</div>
						
						<tiles:insert page="/pages/tiles/PABlockLayout.jsp">
							<tiles:put name="blockTitle" direct="true">
								<bean:message key="archigest.archivo.valoresDefectoUDocs"/>
							</tiles:put>						
						    <tiles:put name="blockContent" direct="true">
								<TABLE class="formulario">
										<TR>
											<TD colspan="2">
												<TABLE class="w98m1" cellpadding="0" cellspacing="0">
													<script>
														function cargarFichas() {
															var form = document.forms['<c:out value="${actionMapping.name}" />'];
															var idFicha = document.getElementById("idFicha");
															var idNivelDocumental = document.getElementById("idNivelDocumental");
															
															if ((idFicha != null) && (String(idFicha) != "undefined")
																	&& ((idNivelDocumental == null) 
																		|| ((idNivelDocumental != null) && (String(idNivelDocumental) != "") && (String(idNivelDocumental) != "undefined")))) {
																	
																	form.method.value = "cargarFichas";
																	form.submit();
															}
														}
																												
														function establecerNivel() {
															var form = document.forms['<c:out value="${actionMapping.name}" />'];
															var idNivelDocumental = document.getElementById("idNivelDocumental");
															if ( idNivelDocumental == null
																|| 
																( idNivelDocumental != null 
																 && String(idNivelDocumental) != ""
																 && String(idNivelDocumental) != "undefined"
																 )
																) 
															{
																form.method.value = "establecerNivel";
																form.submit();
															}
														}
													</script>
													<c:set var="nivelesDocumentales" value="${sessionScope[appConstants.transferencias.LISTA_NIVELES_DOCUMENTALES_KEY]}" />
													<c:set var="isPermitidoUsarFicha" value="false" />
													<security:permissions action="${appConstants.descripcionActions.USO_FICHA_ALTA_TRANSFERENCIA_ACTION}">
														<c:if test="${appConstants.configConstants.permitirFichaAltaRelacion}">
															<c:set var="isPermitidoUsarFicha" value="true" />
														</c:if>													
													</security:permissions>													
													<c:if test="${!empty nivelesDocumentales}">
														<tr>
															<td class="tdTitulo" width="220px">
																<bean:message key="archigest.archivo.transferencias.nivelDocumental"/>:&nbsp;
															</td>
															<td class="tdDatos">
																<c:choose>
																	<c:when test="${isPermitidoUsarFicha}">
																		<html:select property="idNivelDocumental" onchange="javascript:cargarFichas();" styleId="idNivelDocumental">						
																			<html:option value=""></html:option>
																			<html:options collection="nivelesDocumentales" labelProperty="nombre" property="id" />
																		</html:select>
																	</c:when>
																	<c:otherwise>
																		<html:select property="idNivelDocumental" onchange="javascript:establecerNivel();" styleId="idNivelDocumental">						
																			<html:option value=""></html:option>
																			<html:options collection="nivelesDocumentales" labelProperty="nombre" property="id" />
																		</html:select>
																 </c:otherwise>
																</c:choose>
															</td>
														</tr>
													</c:if>
													<c:if test="${appConstants.configConstants.permitirFichaAltaRelacion}">
														<security:permissions action="${appConstants.descripcionActions.USO_FICHA_ALTA_TRANSFERENCIA_ACTION}">
															<c:set var="fichas" value="${sessionScope[appConstants.transferencias.LISTA_FICHAS_KEY]}" />
															<tr>
																<td class="tdTitulo" width="220px">
																	<bean:message key="archigest.archivo.transferencias.ficha"/>:&nbsp;
																</td>
																<td class="tdDatos">
																	<c:choose>
																		<c:when test="${empty fichas}">
																			<html:select property="idFicha" styleId="idFicha">
																				<html:option value=""></html:option>
																			</html:select>
																		</c:when>
																		<c:otherwise>
																			<html:select property="idFicha" styleId="idFicha">
																				<html:option value=""></html:option>
																				<html:options collection="fichas" labelProperty="nombre" property="id" />
																			</html:select>								
																		</c:otherwise>
																	</c:choose> 
																</td>
															</tr>
														</security:permissions>
													</c:if>																				
													<TR>
														<c:set var="isNivelCaja" value="${sessionScope[appConstants.fondos.BANDERA_MOSTRAR_RANGOS]}" />
														<c:choose>
															<c:when test="${isNivelCaja}">
																<c:set var="rangosUDoc" value="${sessionScope[appConstants.fondos.LISTA_RANGOS_UDOC]}" />
																<c:if test="${!empty rangosUDoc}">
																	<bean:define id="rangosUDoc" name="rangosUDoc" toScope="request"/>
																</c:if>
																<tiles:insert page="/pages/tiles/transferencias/relaciones/info_rangos_udoc.jsp" flush="true"/>
															</c:when>
															<c:otherwise>
																<TD class="tdTitulo" width="220px">
																	<bean:message key="archigest.archivo.transferencias.numExp"/>:&nbsp;
																</TD>
																<TD class="tdDatos">
																	<html:text property="numExp" styleClass="input99" maxlength="256"/>
																</TD>
															</c:otherwise>
														</c:choose>
													</TR>
													<TR>
														<TD class="tdTitulo">
															<bean:message key="archigest.archivo.titulo"/>:&nbsp;
														</TD>
														<TD class="tdDatos">
															<html:textarea property="asunto" rows="3" onkeypress="maxlength(this,1024,false)" onchange="maxlength(this,1024,true)"/>
														</TD>
													</TR>
													<TR>
														<TD class="tdTitulo">
															<bean:message key="archigest.archivo.transferencias.fIni"/>:&nbsp;
														</TD>
														<TD class="tdDatos">
															<TABLE cellpadding="0" cellspacing="0">
															<TR>
																<TD width="150px" class="td2Datos">
																	<html:text property="fechaInicio" size="10" maxlength="10" styleClass="input"/>
																	<archigest:calendar 
																					image="../images/calendar.gif"
																					formId="<%=__FORM_NAME%>" 
																					componentId="fechaInicio" 
																					format="dd/mm/yyyy" 
																					enablePast="true" />
																</TD>
																<TD width="20px">
																</TD>
																<TD width="100px" class="td2Titulo">
																	<bean:message key="archigest.archivo.transferencias.fFin"/>:&nbsp;
																</TD>
																<TD width="150px" class="td2Datos">
																	<html:text property="fechaFin" size="10" maxlength="10" styleClass="input"/>
																	<archigest:calendar 
																					image="../images/calendar.gif"
																					formId="<%=__FORM_NAME%>" 
																					componentId="fechaFin" 
																					format="dd/mm/yyyy" 
																					enablePast="true" />
																</TD>
															</TR>
														</TABLE>
													</TD>
												</TR>
												<TR>
													<TD colspan="2" class="separador12">&nbsp;
													</TD>
												</TR>
												</TABLE>
											</TD>
										</TR>
								</TABLE>
							</tiles:put>
						</tiles:insert>
		</html:form>
	</tiles:put>	
</tiles:insert>