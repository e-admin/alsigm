<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/taglibs/securityTag.tld" prefix="security"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/struts-html-el.tld" prefix="html-el" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/struts-bean-el.tld" prefix="bean-el" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>

		<script type="text/javascript">
			function crearCaja(url){
				if (window.top.showWorkingDiv) {
					var title = '<bean:message key="archigest.archivo.msg.titulo"/>';
					var message = '<bean:message key="archigest.archivo.msg.proceso.en.ejecucion"/>';
					window.top.showWorkingDiv(title, message);
				}
				goTo(url);
			}
		</script>

		<div class="cabecero_bloque_tab"> <%--cabecero segundo bloque de datos --%>
				<TABLE class="w98m1" cellpadding=0 cellspacing=0>
				  <TBODY>
				  <TR>
				    <TD width="100%" align="right">
					<TABLE cellpadding="0" cellspacing="0">
					  <TR>
						<%--ORGANIZAR ASIGNACION DE UNIDADES DOCUMENTALES A UNIDADES DE INSTALACION --%>
						<c:if test="${vRelacion.permitidaModificacionAsignacionCajas}">
							<c:if test="${vRelacion.entreArchivos}">
								<security:permissions action="${appConstants.transferenciaActions.GESTION_RELACION_EN_ORGANO_REMITENTE}">
									<TD>
										<c:if test="${!empty infoUdocReeacr.udocsSinAsignar}">
												<c:url var="creacionCajaURL" value="/action/asignacionCajasReencajado">
													<c:param name="method" value="nuevaCaja" />
													<c:param name="idRelacion" value="${vRelacion.id}" />
												</c:url>
												<a class="etiquetaAzul12Normal" href="javascript:crearCaja('<c:out value="${creacionCajaURL}" escapeXml="false"/>');">
													<html:img page="/pages/images/caja_new.gif"
														altKey="archigest.archivo.transferencias.crearCaja"
														titleKey="archigest.archivo.transferencias.crearCaja"
														styleClass="imgTextBottom"/>
													&nbsp;<bean:message key="archigest.archivo.transferencias.crearCaja"/>
												</a>
										</c:if>
									</TD>
								</security:permissions>
							</c:if>
						</c:if>
					</TR>
					</TABLE>
					</TD>
			</TR></TBODY></TABLE>
			</div> <%--cabecero bloque tab --%>

			<%--Expedientes sin asignar --%>
			<c:if test="${!empty infoUdocReeacr.udocsSinAsignar}">
				<c:set var="udocsSinCaja" value="${infoUdocReeacr.udocsSinAsignar}" />
				<div class="titulo_gris_bloque">
					<table class="tablaAncho99">
						<tr>
							<td class="etiquetaAzul11Bold">
								<html:img page="/pages/images/up.gif" styleId="imgListaUdocsSinAsignar" onclick="switchDivVisibility('ListaUdocsSinAsignar')" />
								&nbsp;&nbsp;<bean:message key="archigest.archivo.transferencias.UDsinCaja" /> (<c:out value="${infoUdocReeacr.numUdocsSinAsignar}" />)
							</td>
						</tr>
					</table>
				</div>

				<div isOpen="true" id="divListaUdocsSinAsignar">
					<display:table name="pageScope.udocsSinCaja" id="udocSinCaja"
						sort="list"
						style="width:99%;margin-left:auto;margin-right:auto">
						<display:setProperty name="basic.show.header" value="false" />
						<display:column title="" style="text-align:right" >
							<c:out value="${udocSinCaja_rowNum}" />:
						</display:column>
						<c:if test="${!empty udocSinCaja.signaturaUDoc}">
							<display:column titleKey="archigest.archivo.transferencias.signatura">
								<c:out value="${udocSinCaja.signaturaUDoc}" />
							</display:column>
						</c:if>

						<display:column titleKey="archigest.archivo.transferencias.nExp">
							<c:url var="infoUdocURL" value="/action/gestionUdocsRelacion">
								<c:param name="method">
								<c:choose><c:when test="${relacionEnArchivo}">infoUnidadDocumentalAGestionar</c:when><c:otherwise>infoUnidadDocumental</c:otherwise></c:choose>
								</c:param>
								<c:param name="udocID" value="${udocSinCaja.id}" />
								<c:param name="numOrden" value="${udocSinCaja.posUdocEnUI}"/>
							</c:url>
							<a href="<c:out value="${infoUdocURL}" escapeXml="false"/>" class="tdlink">
								<c:out value="${udocSinCaja.numExp}" /><c:out value="${udocSinCaja.parteExp}"/>
							</a>
						</display:column>

						<display:column titleKey="archigest.archivo.transferencias.asunto">
							<c:url var="infoUdocURL" value="/action/gestionUdocsRelacion">
								<c:param name="method">
									<c:choose>
										<c:when test="${relacionEnArchivo}">infoUnidadDocumentalAGestionar</c:when>
										<c:otherwise>infoUnidadDocumental</c:otherwise>
									</c:choose>
								</c:param>
							</c:url>
							<a href="<c:out value="${infoUdocURL}" escapeXml="false"/>" class="tdlink">
								<c:out value="${udocSinCaja.asunto}" />
							</a>
						</display:column>
					</display:table>
				</div> <%--divListaUdocsSinAsignar --%>
				<span class="separador5">&nbsp;</span>
			</c:if>

			<%--Expedientes asignados: Cajas  --%>
			<c:set var="cajasRelacion" value="${infoUdocReeacr.uisWithUDocs}" />

			<c:if test="${empty infoUdocReeacr.udocsSinAsignar && empty infoUdocReeacr.uisWithUDocs}">
				<c:if test="${vRelacion.entreArchivos}">
					<c:choose>
						<c:when test="${vRelacion.nivelDocumental.subtipo == appConstants.fondos.subtiposNivel.CAJA.identificador}">
							<bean:message key="archigest.archivo.transferencias.relacionSinFraccionesSerie" />
						</c:when>
						<c:otherwise>
							<c:if test="${not vRelacion.sinDocsFisicos}">
								<bean:message key="archigest.archivo.transferencias.relacionSinUnidadesDocumentales" />
							</c:if>
						</c:otherwise>
					</c:choose>
				</c:if>
			</c:if>

			<div style="margin:8px">
			<tiles:insert page="/pages/tiles/PABlockLayout.jsp">
				<tiles:put name="blockTitle" direct="true">
					<html:img page="/pages/images/caja_closed.gif" altKey="archigest.archivo.transferencias.UndInstal" titleKey="archigest.archivo.transferencias.UndInstal" styleClass="imgTextBottom" />
					&nbsp;<bean:message key="archigest.archivo.transferencias.UndInstal" />
				</tiles:put>
				<tiles:put name="buttonBar" direct="true">
					<c:if test="${vRelacion.permitidaImpresionCartelasProvisionales}">
						<security:permissions action="${appConstants.transferenciaActions.IMPRESION_CARTELAS_PROVISIONALES}">
							<c:url var="urlImprimir" value="/action/gestionRelaciones">
								<c:param name="method" value="selCartelas"/>
							</c:url>
							<a class="etiquetaAzul12Normal" href="<c:out value="${urlImprimir}" escapeXml="false"/>" >
								<html:img titleKey="archigest.archivo.transferencias.generarCartelas" altKey="archigest.archivo.transferencias.generarCartelas" page="/pages/images/cartela.gif" styleClass="imgTextMiddle" />
									 &nbsp;<bean:message key="archigest.archivo.transferencias.generarCartelas"/>&nbsp;&nbsp;
							</a>
						</security:permissions>
					</c:if>
					<c:if test="${vRelacion.permitidoMarcarRevisada and vRelacion.permitidoCorregirErrores}">
				   		<a class="etiquetaAzul12Normal" href="javascript:marcarRevisada()">
					   <html:img page="/pages/images/caja_ok.gif"
								altKey="archigest.archivo.transferencias.marcarRevisada"
								titleKey="archigest.archivo.transferencias.marcarRevisada"
								styleClass="imgTextMiddle"/>
							<bean:message key="archigest.archivo.transferencias.marcarRevisada"/></a>
					</c:if>
				</tiles:put>
				<tiles:put name="blockContent" direct="true">
					<c:forEach items="${cajasRelacion}" var="unidadInstalacion" varStatus="nCajas" >
						<c:set var="cajaPanelName" value="CajaReencajado${nCajas.count}" />
						<jsp:useBean id="cajaPanelName" type="java.lang.String" />

						<div class="titulo_gris_bloque">
							<c:set var="panelControlImg" value="img${cajaPanelName}" />
							<jsp:useBean id="panelControlImg" type="java.lang.String" />
							<c:set var="panelVisibilityCommand" value="switchDivVisibility('${cajaPanelName}')" />
							<jsp:useBean id="panelVisibilityCommand" type="java.lang.String" />
							<table class="w98" cellpadding="0" cellspacing="0"><tr>
							<td width="40%" class="etiquetaAzul12Normal">
								<c:choose>
									<c:when test="${unidadInstalacion.key.conErrores and vRelacion.permitidoCorregirErrores}">
										<input type="checkbox" name="elementosseleccionados" value="<c:out value="${unidadInstalacion.key.id}" />" >
									</c:when>
									<c:otherwise>
										&nbsp;<html:img page="/pages/images/pixel.gif" width="15" border="0" altKey="" titleKey="" styleClass="imgTextMiddle"/>
									</c:otherwise>
								</c:choose>
								<html:img page="/pages/images/down.gif" styleId="<%=panelControlImg%>" onclick="<%=panelVisibilityCommand%>" styleClass="imgTextMiddle"/>
								&nbsp;&nbsp;<bean:message key="archigest.archivo.transferencias.caja" />&nbsp;<c:out value="${nCajas.count}" />

								<c:choose>
									<c:when test="${unidadInstalacion.key.estadoCotejo == appConstants.transferencias.estadoCotejo.ERRORES.identificador}">
										&nbsp;&nbsp;<html:img page="/pages/images/cajaError.gif" border="0" altKey="archigest.archivo.transferencias.cajaConErrores" titleKey="archigest.archivo.transferencias.cajaConErrores" styleClass="imgTextMiddle"/>
									</c:when>
									<c:otherwise>
										&nbsp;&nbsp;<html:img page="/pages/images/pixel.gif" width="15" border="0" altKey="archigest.archivo.transferencias.cajaConErrores" titleKey="archigest.archivo.transferencias.cajaConErrores" styleClass="imgTextMiddle"/>
									</c:otherwise>
								</c:choose>
								<c:choose>
								<c:when test="${unidadInstalacion.key.devolucion}">
									&nbsp;&nbsp<html:img page="/pages/images/cajaDevuelta.gif" border="0" altKey="archigest.archivo.transferencias.devolver" titleKey="archigest.archivo.transferencias.devolver" styleClass="imgTextMiddle"/>
								</c:when>
									<c:otherwise>
										&nbsp;&nbsp;<html:img page="/pages/images/pixel.gif" width="26" border="0" altKey="archigest.archivo.transferencias.cajaConErrores" titleKey="archigest.archivo.transferencias.cajaConErrores" styleClass="imgTextMiddle"/>
									</c:otherwise>
								</c:choose>
								&nbsp;&nbsp;
									<bean-el:message key="archigest.archivo.caja.contiene.unidades.documentales" arg0="${unidadInstalacion.key.numUdocsUI}"/>
							</td>
							<td width="15%" class="etiquetaAzul11Normal">
								<c:if test="${!empty unidadInstalacion.key.signaturaUI}">
									<b><bean:message key="archigest.archivo.transferencias.signatura" />:</b>
									&nbsp;<c:out value="${unidadInstalacion.key.signaturaUI}" />
								</c:if>
								&nbsp;
							</td>
							<c:if test="${vRelacion.entreArchivos && vRelacion.permitidaModificacionAsignacionCajas}">
									<td width="40%" style="text-align:right;" class="etiquetaAzul11Normal">
										<table cellpadding="0" cellspacing="0"><tr>
										<td width="100px">
											<c:url var="editarURL" value="/action/asignacionCajasReencajado">
												<c:param name="method" value="editarCaja" />
												<c:param name="idUnidadInstalacion" value="${unidadInstalacion.key.id}" />
												<c:param name="idRelacion" value="${vRelacion.id}" />
												<c:param name="numeroCaja" value="${nCajas.count}" />
											</c:url>
											<a class="etiquetaAzul12Normal" href="javascript:window.location='<c:out value="${editarURL}" escapeXml="false"/>'">
												<html:img titleKey="archigest.archivo.transferencias.organizarCajas" altKey="archigest.archivo.transferencias.organizarCajas" page="/pages/images/caja_open.gif" styleClass="imgTextMiddle"/>
												<bean:message key="archigest.archivo.transferencias.organizarCajas"/>
											</a>
										</td>
										<TD width="10">&nbsp;</TD>
										<TD width="100px">
										<c:if test="${unidadInstalacion.key.borrable}">
											<c:url var="eliminarURL" value="/action/asignacionCajasReencajado">
												<c:param name="method" value="eliminarCaja" />
												<c:param name="idUnidadInstalacion" value="${unidadInstalacion.key.id}" />
												<c:param name="idRelacion" value="${vRelacion.id}" />
											</c:url>
											<a class="etiquetaAzul12Normal" href="javascript:window.location='<c:out value="${eliminarURL}" escapeXml="false"/>'">
												<html:img titleKey="archigest.archivo.eliminar" altKey="archigest.archivo.eliminar" page="/pages/images/caja_delete.gif" styleClass="imgTextMiddle"/>
												&nbsp;<bean:message key="archigest.archivo.eliminar"/>
											</a>
										</c:if>	&nbsp;
										</TD>
										</tr></table>
									</c:if>
								</td>
							</tr></table>
							</div>

							<c:if test="${!empty unidadInstalacion.key.notasCotejo}">
								<div class="titulo_gris_bloque">
									<html:img page="/pages/images/pixel.gif" width="20"/>
										<b><bean:message key="archigest.archivo.transferencias.observaciones" />
									</b>:&nbsp;<c:out value="${unidadInstalacion.key.notasCotejo}" />
								</div>
							</c:if>

						<div isOpen="false" id="div<%=cajaPanelName%>" style="display:none;">
							<c:set var="udocs" value="${unidadInstalacion.value}" />

							<display:table name="pageScope.udocs" id="udoc"
								style="width:99%;margin-left:auto;margin-right:auto">

								<display:setProperty name="basic.show.header" value="false" />

								<display:setProperty name="basic.msg.empty_list">
									<div style="width:99%;border-bottom:1px dotted #999999;text-align:left;">
										&nbsp;&nbsp;<bean:message key="archigest.archivo.transferencias.noUDCaja"/>
									</div>
								</display:setProperty>

								<display:column title="" style="width:30px;text-align:right;">
									<c:out value="${udoc_rowNum}" />
								</display:column>
								<c:if test="${!empty unidadInstalacion.key.estadoCotejo}">
									<display:column titleKey="archigest.archivo.transferencias.cotejoUDoc" style="width:2%">
										<c:choose>
											<c:when test="${unidadInstalacion.key.pendiente}">
												<html:img page="/pages/images/cajaPendiente.gif" altKey="archigest.archivo.transferencias.pendienteCotejo" titleKey="archigest.archivo.transferencias.pendienteCotejo"/>
											</c:when>
											<c:when test="${unidadInstalacion.key.revisada}">
												<html:img page="/pages/images/cajaRevisada.gif" altKey="archigest.archivo.transferencias.cotejoCorrecto" titleKey="archigest.archivo.transferencias.cotejoCorrecto"/>
											</c:when>
											<c:when test="${unidadInstalacion.key.conErrores}">
												<html:img page="/pages/images/cajaError.gif" altKey="archigest.archivo.transferencias.cotejoConErrores" titleKey="archigest.archivo.transferencias.cotejoConErrores"/>
											</c:when>
										</c:choose>
									</display:column>

								</c:if>
								<display:column titleKey="archigest.archivo.transferencias.nExp" style="width:13%">
									<c:out value="${udoc.numExp}" /><c:out value="${udoc.parteExp}"/>
								</display:column>
								<display:column titleKey="archigest.archivo.transferencias.asunto">
									<c:url var="infoUdocURL" value="/action/gestionUdocsCF">
										<c:param name="method" value="verEnFondos" />
										<c:param name="unidadDocumental" value="${udoc.idUnidadDoc}" />
									</c:url>
									<a href="<c:out value="${infoUdocURL}" escapeXml="false"/>" class="tdlink">
										<c:out value="${udoc.asunto}" />
									</a>
								</display:column>
								<display:column style="width:20%">
									<c:out value="${udoc.signaturaUdoc}" />
								</display:column>

							</display:table>
						</div> <%--divcajaPanelNameN--%>
						<span class="separador1"></span>
					</c:forEach>
					<span class="separador5">&nbsp;</span>
				</tiles:put>
			</tiles:insert>
			</div>