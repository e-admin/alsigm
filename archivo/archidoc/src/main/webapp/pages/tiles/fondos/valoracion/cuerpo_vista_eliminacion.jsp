<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/taglibs/securityTag.tld" prefix="security"%>

<c:set var="serie" value="${sessionScope[appConstants.valoracion.SERIE_KEY]}" />
<c:set var="eliminacion" value="${sessionScope[appConstants.valoracion.ELIMINACION_KEY]}" />
<c:set var="archivo" value="${sessionScope[appConstants.transferencias.ARCHIVO_KEY]}" />
<c:set var="ocultarFinalizarEliminacion" value="${requestScope[appConstants.valoracion.ELIMINACION_OCULTAR_FINALIZAR_ELIMINACION_KEY]}"/>

<tiles:insert template="/pages/tiles/PABoxLayout.jsp">
	<tiles:put name="boxTitle" direct="true">
		<bean:message key="archigest.archivo.eliminacion.datosSelecion"/>
	</tiles:put>

	<tiles:put name="buttonBar" direct="true">
		<script>
			function rechazarEliminacion() {
				var form = document.forms[0];
				form.method.value = 'rechazarEliminacion';
				form.submit();
			}
		</script>
		<table cellpadding="0" cellspacing="0">
			<tr>
				<security:permissions action="${appConstants.fondosActions.FINALIZAR_ELIMINACION_ACTION}">
				<c:if test="${eliminacion.permitidaFinalizacion && !ocultarFinalizarEliminacion}">
					<td nowrap>
						<c:url var="finalizarEliminacionURL" value="/action/gestionEliminacion">
							<c:param name="method" value="finalizarEliminacion" />
							<c:param name="id" value="${eliminacion.id}" />
						</c:url>
						<a class="etiquetaAzul12Bold" href="<c:out value="${finalizarEliminacionURL}" escapeXml="false" />" >
						<html:img page="/pages/images/aceptar.gif" altKey="archigest.archivo.eliminacion.finalizarEliminacion" titleKey="archigest.archivo.eliminacion.finalizarEliminacion" styleClass="imgTextMiddle" />
						&nbsp;<bean:message key="archigest.archivo.eliminacion.finalizarEliminacion"/></a>
					</td>
					<td width="10">&nbsp;</td>
				</c:if>
				</security:permissions>

				<security:permissions action="${appConstants.fondosActions.DESTRUIR_FISICAMENTE_ACTION}">
				<c:if test="${eliminacion.permitidaDestruccionFisica}">
					<td nowrap>
						<c:url var="recogerFechaDestruccionURL" value="/action/gestionEliminacion">
							<c:param name="method" value="recogerFechaDestruccion" />
						</c:url>
						<a class="etiquetaAzul12Bold" href="<c:out value="${recogerFechaDestruccionURL}" escapeXml="false" />" >
						<html:img page="/pages/images/aceptar.gif" altKey="archigest.archivo.eliminacion.destruirFisicamente" titleKey="archigest.archivo.eliminacion.destruirFisicamente" styleClass="imgTextMiddle" />
						&nbsp;<bean:message key="archigest.archivo.eliminacion.destruirFisicamente"/></a>
					</td>
					<td width="10">&nbsp;</td>
				</c:if>
				</security:permissions>

				<security:permissions action="${appConstants.fondosActions.EJECUTAR_ELIMINACION_ACTION}">
				<c:if test="${eliminacion.permitidaEjecucionEliminacion}">
					<td nowrap>
						<c:url var="aceptarEjecucionURL" value="/action/gestionEliminacion">
							<c:param name="method" value="aceptarEjecucion" />
							<c:param name="id" value="${eliminacion.id}" />
						</c:url>
						<script>
							function ejecutarEliminacion()
							{
								if (window.top.showWorkingDiv) {
									var title = '<bean:message key="archigest.archivo.operacion.ejecutandoEliminacion"/>';
									var message = '<bean:message key="archigest.archivo.operacion.msgEjecutandoEliminacion"/>';
									var message2 = '<bean:message key="archigest.archivo.msgOperacionLenta"/>';
									window.top.showWorkingDiv(title, message, message2);
								}
								window.location = "<c:out value="${aceptarEjecucionURL}" escapeXml="false" />";
							}
						</script>
						<a class="etiquetaAzul12Bold" href="javascript:ejecutarEliminacion()">
						<html:img page="/pages/images/aceptar.gif" altKey="archigest.archivo.eliminacion.aceptarEjecucion" titleKey="archigest.archivo.eliminacion.aceptarEjecucion" styleClass="imgTextMiddle" />
						&nbsp;<bean:message key="archigest.archivo.eliminacion.aceptarEjecucion"/></a>
					</td>
					<td width="10">&nbsp;</td>
				</c:if>
				</security:permissions>

				<security:permissions action="${appConstants.fondosActions.APROBAR_ELIMINACION_ACTION}">
				<c:if test="${!empty eliminacion.fechaEjecucion && eliminacion.pendienteAprobar}">
					<td nowrap>
						<c:url var="aceptarEliminacionURL" value="/action/gestionEliminacion">
							<c:param name="method" value="aceptarEliminacion" />
							<c:param name="id" value="${eliminacion.id}" />
						</c:url>
						<a class="etiquetaAzul12Bold" href="<c:out value="${aceptarEliminacionURL}" escapeXml="false" />" >
						<html:img page="/pages/images/aceptar.gif" altKey="archigest.archivo.eliminacion.aceptarEliminacion" titleKey="archigest.archivo.eliminacion.aceptarEliminacion" styleClass="imgTextMiddle" />
						&nbsp;<bean:message key="archigest.archivo.eliminacion.aceptarEliminacion"/></a>
					</td>
					<td width="10">&nbsp;</td>
					<td nowrap>
						<c:url var="rechazarSeleccionURL" value="/action/gestionEliminacion">
							<c:param name="method" value="pantallaRechazoSeleccion" />
						</c:url>
						<a class="etiquetaAzul12Bold" href="<c:out value="${rechazarSeleccionURL}" escapeXml="false"/>" >
						<html:img page="/pages/images/rechazar.gif" altKey="archigest.archivo.eliminacion.rechazarEliminacion" titleKey="archigest.archivo.eliminacion.rechazarEliminacion" styleClass="imgTextTop" />
						&nbsp;<bean:message key="archigest.archivo.eliminacion.rechazarEliminacion"/></a>
					</td>
					<td width="10">&nbsp;</td>
				</c:if>
				</security:permissions>

				<security:permissions action="${appConstants.fondosActions.SOLICITAR_APROBACION_ELIMINACION_ACTION}">
				<c:if test="${eliminacion.permitidaSolicitudAprobacionEliminacion}">
					<td nowrap>
						<c:url var="solicitudAprobacionURL" value="/action/gestionEliminacion">
							<c:param name="method" value="pantallaSolicitudAprobacion" />
						</c:url>
						<a class="etiquetaAzul12Bold" href="<c:out value="${solicitudAprobacionURL}" escapeXml="false" />" >
						<html:img page="/pages/images/go.gif" altKey="archigest.archivo.eliminacion.solicitarAprobacion" titleKey="archigest.archivo.eliminacion.solicitarAprobacion" styleClass="imgTextTop" />
						&nbsp;<bean:message key="archigest.archivo.eliminacion.solicitarAprobacion"/></a>
					</td>
					<td width="10">&nbsp;</td>
				</c:if>
				</security:permissions>

				<security:permissions action="${appConstants.fondosActions.EDITAR_SELECCIONES_ACTION}">
				<c:if test="${eliminacion.puedeSerEliminada}">
					<td nowrap>
						<c:url var="eliminarEliminacionURL" value="/action/gestionEliminacion">
							<c:param name="method" value="eliminarEliminacion" />
							<c:param name="id" value="${eliminacion.id}" />
						</c:url>
						<a class="etiquetaAzul12Bold" href="<c:out value="${eliminarEliminacionURL}" escapeXml="false" />" >
						<html:img page="/pages/images/deleteDoc.gif" altKey="archigest.archivo.eliminar" titleKey="archigest.archivo.eliminar" styleClass="imgTextMiddle" />
						&nbsp;<bean:message key="archigest.archivo.eliminar"/></a>
					</td>
					<td width="10">&nbsp;</td>
				</c:if>
				</security:permissions>


				<c:if test="${eliminacion.finalizada}">
					<td nowrap>
							<c:url var="informeURL" value="/action/informeEliminacion">
								<c:param name="id" value="${eliminacion.id}" />
							</c:url>
							<a class="etiquetaAzul12Bold" href="<c:out value="${informeURL}" escapeXml="false"/>">
							<html:img page="/pages/images/documentos/doc_pdf.gif"
								border="0"
								altKey="archigest.archivo.informe"
								titleKey="archigest.archivo.informe"
								styleClass="imgTextBottom"/>&nbsp;<bean:message key="archigest.archivo.informe"/></a>
						</td>
						<td width="10">&nbsp;</td>
				</c:if>

				<td nowrap>
					<c:url var="cerrarURL" value="/action/gestionEliminacion">
						<c:param name="method" value="goBack" />
					</c:url>
					<a class="etiquetaAzul12Bold" href="<c:out value="${cerrarURL}" escapeXml="false" />" >
					<html:img page="/pages/images/close.gif" altKey="archigest.archivo.cerrar" titleKey="archigest.archivo.cerrar" styleClass="imgTextMiddle" />
					&nbsp;<bean:message key="archigest.archivo.cerrar"/></a>
				</td>
			</tr>
		</table>
	</tiles:put>

	<tiles:put name="boxContent" direct="true">

		<tiles:insert template="/pages/tiles/PABlockLayout.jsp">

			<tiles:put name="blockTitle" direct="true">
				<bean:message key="archigest.archivo.transferencias.archivoOrigen"/>
			</tiles:put>

			<tiles:put name="blockContent" direct="true">
				<table class="formulario">
					<tr>
						<td  width="180px" class="tdTitulo">
							<bean:message key="archigest.archivo.archivo"/>:&nbsp;
						</td>
						<td class="tdDatos">
							<c:if test="${!empty archivo}">
								<c:out value="${archivo.nombre}"></c:out>
							</c:if>

						</td>
					</tr>
				</table>
			</tiles:put>
		</tiles:insert>

		<div class="separador8">&nbsp;</div>

		<tiles:insert template="/pages/tiles/PABlockLayout.jsp">

			<tiles:put name="blockTitle" direct="true">
				<bean:message key="archigest.archivo.eliminacion.serie.caption"/>
			</tiles:put>

			<tiles:put name="blockContent" direct="true">
				<table class="formulario">
					<tr>
						<td  width="180px" class="tdTitulo">
							<bean:message key="archigest.archivo.cf.codigo"/>:&nbsp;
						</td>
						<td class="tdDatosBold">
							<c:url var="vistaSerieURL" value="/action/gestionSeries">
								<c:param name="method" value="verEnFondos" />
								<c:param name="id" value="${serie.id}" />
							</c:url>
							<a class="tdlink" href="<c:out value="${vistaSerieURL}" escapeXml="false" />" ><c:out value="${serie.codigo}" /></a>
						</td>
					</tr>
					<tr>
						<td class="tdTitulo">
							<bean:message key="archigest.archivo.cf.denominacion"/>:&nbsp;
						</td>
						<td class="tdDatosBold">
							<c:out value="${serie.denominacion}" />
						</td>
					</tr>
				</table>
			</tiles:put>
		</tiles:insert>

		<div class="separador8">&nbsp;</div>

		<tiles:insert template="/pages/tiles/PABlockLayout.jsp">

			<tiles:put name="blockTitle" direct="true">
				<bean:message key="archigest.archivo.informacion"/>
			</tiles:put>

			<tiles:put name="buttonBar" direct="true">
				<table cellpadding=0 cellspacing=0>
					<tr>
						<c:if test="${eliminacion.autorizada && not eliminacion.finalizada}">
								<td nowrap>
									<c:url var="URL" value="/action/informeSeleccion">
										<c:param name="id" value="${eliminacion.id}" />
										<c:if test="${!empty archivo}">
											<c:param name="idArchivo" value="${archivo.id}" />
										</c:if>
									</c:url>
									<a class="etiquetaAzul12Bold"
									   href="<c:out value="${URL}" escapeXml="false"/>"
									><html:img page="/pages/images/documentos/doc_pdf.gif"
									        border="0"
									        altKey="archigest.archivo.informe"
									        titleKey="archigest.archivo.informe"
									        styleClass="imgTextMiddle"/>&nbsp;<bean:message key="archigest.archivo.informe"/></a>
								</td>
								<td width="20px">&nbsp;</td>
						</c:if>

						<td nowrap>
							<c:if test="${eliminacion.permitidaSolicitudAprobacionEliminacion || eliminacion.permitidaAprobacionEliminacion || eliminacion.puedeSerEditada || eliminacion.permitidaFinalizacion || eliminacion.permitidaDestruccionFisica || eliminacion.permitidaEjecucionEliminacion}">
							<td nowrap>
								<script>
									function solicitarUDocsElim() {
										<c:choose>
										<c:when test="${empty eliminacion.fechaEjecucion}">
											alert("<bean:message key='archigest.archivo.valoraciones.fechaEjecucionSeleccion'/>");
										</c:when>
										<c:otherwise>
											if (window.top.showWorkingDiv) {
												var title = '<bean:message key="archigest.archivo.operacion.eliminacionVerUnidades"/>';
												var message = '<bean:message key="archigest.archivo.operacion.msgEliminacionVerUnidades"/>';
												var message2 = '<bean:message key="archigest.archivo.msgOperacionLenta"/>';
												window.top.showWorkingDiv(title, message, message2);
											}
											<c:url var="verUDOCSURL" value="/action/gestionEliminacion">
												<c:param name="method" value="verUdocs" />
												<c:param name="id" value="${eliminacion.id}" />
												<c:if test="${!empty archivo}">
													<c:param name="idArchivo" value="${archivo.id}" />
												</c:if>

											</c:url>
											window.location.href = "<c:out value="${verUDOCSURL}" escapeXml="false"/>";
										</c:otherwise>
										</c:choose>
									}
									function solicitarUDocsConserv() {
										<c:choose>
										<c:when test="${empty eliminacion.fechaEjecucion}">
											alert("<bean:message key='archigest.archivo.valoraciones.fechaEjecucionSeleccion'/>");
										</c:when>
										<c:otherwise>
											if (window.top.showWorkingDiv) {
												var title = '<bean:message key="archigest.archivo.operacion.eliminacionVerUnidades"/>';
												var message = '<bean:message key="archigest.archivo.operacion.msgEliminacionVerUnidades"/>';
												var message2 = '<bean:message key="archigest.archivo.msgOperacionLenta"/>';
												window.top.showWorkingDiv(title, message, message2);
											}
											<c:url var="verUDOCSCONSURL" value="/action/gestionEliminacion">
												<c:param name="method" value="verUdocsConservacion" />
												<c:param name="id" value="${eliminacion.id}" />
												<c:param name="ocultarInforme" value="true" />
												<c:if test="${!empty archivo}">
													<c:param name="idArchivo" value="${archivo.id}" />
												</c:if>

											</c:url>
											window.location.href = "<c:out value="${verUDOCSCONSURL}" escapeXml="false"/>";
										</c:otherwise>
										</c:choose>
									}
								</script>
								<c:if test="${!ocultarFinalizarEliminacion}">
									<a class="etiquetaAzul12Bold" href="javascript:solicitarUDocsElim()" >
									<html:img page="/pages/images/searchDoc.gif" altKey="archigest.archivo.eliminacion.verUdocs" titleKey="archigest.archivo.eliminacion.verUdocs" styleClass="imgTextMiddle" />
									&nbsp;<bean:message key="archigest.archivo.eliminacion.verUdocs"/></a>
								</c:if>


							</td>
							<td width="10">&nbsp;</td>
							</c:if>
							<c:if test="${eliminacion.puedeSerEditada}">
							<td nowrap>
								<c:url var="edicionEliminacionDatosURL" value="/action/gestionEliminacion">
									<c:param name="method" value="edicionEliminacion" />
									<c:param name="id" value="${eliminacion.id}" />
									<c:param name="type" value="1"/>
								</c:url>
								<a class="etiquetaAzul12Bold" href="<c:out value="${edicionEliminacionDatosURL}" escapeXml="false" />" >
								<html:img page="/pages/images/editDoc.gif" altKey="archigest.archivo.editar" titleKey="archigest.archivo.editar" styleClass="imgTextMiddle" />
								&nbsp;<bean:message key="archigest.archivo.editar"/></a>
							</td>
							</c:if>
						</td>
					</tr>
				</table>
			</tiles:put>

			<tiles:put name="blockContent" value="valoracion.datosEliminacion" type="definition" />
		</tiles:insert>


<c:if test="${eliminacion.autorizada && not eliminacion.finalizada}">
		<div class="separador8">&nbsp;</div>

		<tiles:insert template="/pages/tiles/PABlockLayout.jsp">

			<tiles:put name="blockTitle" direct="true">
				<bean:message key="archigest.archivo.eliminacion.resumen"/>
			</tiles:put>

			<tiles:put name="buttonBar" direct="true">
			</tiles:put>

			<tiles:put name="blockContent" value="valoracion.resumen.datosEliminacion" type="definition" />
		</tiles:insert>
</c:if>


		<c:choose>
		<c:when test="${eliminacion.tieneUdocs && eliminacion.tipoEliminacion==1}">
			<div class="separador8">&nbsp;</div>
			<tiles:insert template="/pages/tiles/PABlockLayout.jsp">

				<tiles:put name="blockTitle" direct="true"><bean:message key="archigest.archivo.conservacion"/></tiles:put>

				<tiles:put name="buttonBar" direct="true">
					<table cellpadding=0 cellspacing=0>
						<tr>
							<c:if test="${eliminacion.puedeSerEditada}">
								<td nowrap>
									<script>
										function definirCriterios() {
											<c:choose>
											<c:when test="${empty eliminacion.fechaEjecucion}">
												alert("<bean:message key='archigest.archivo.valoraciones.fechaEjecucionSeleccion'/>");
											</c:when>
											<c:otherwise>
												<c:url var="edicionEliminacionCriteriosURL" value="/action/gestionEliminacion">
													<c:param name="method" value="edicionEliminacion" />
													<c:param name="id" value="${eliminacion.id}" />
													<c:param name="type" value="2"/>
												</c:url>
												window.location.href = "<c:out value="${edicionEliminacionCriteriosURL}" escapeXml="false"/>";
											</c:otherwise>
											</c:choose>
										}
										function definirCriteriosAvanzado() {
											<c:choose>
											<c:when test="${empty eliminacion.fechaEjecucion}">
												alert("<bean:message key='archigest.archivo.valoraciones.fechaEjecucionSeleccion'/>");
											</c:when>
											<c:otherwise>
												<c:url var="edicionEliminacionAvanzadoURL" value="/action/gestionEliminacion">
													<c:param name="method" value="edicionEliminacionAvanzado" />
													<c:param name="id" value="${eliminacion.id}" />
													<c:param name="type" value="2"/>
												</c:url>
												window.location.href = "<c:out value="${edicionEliminacionAvanzadoURL}" escapeXml="false"/>";
											</c:otherwise>
											</c:choose>
										}
									</script>
									<a class="etiquetaAzul12Bold" href="javascript:solicitarUDocsConserv()" >
									<html:img page="/pages/images/searchDoc.gif" altKey="archigest.archivo.eliminacion.verUdocs.conservar" titleKey="archigest.archivo.eliminacion.verUdocs.conservar" styleClass="imgTextMiddle" />
									&nbsp;<bean:message key="archigest.archivo.eliminacion.verUdocs.conservar"/></a>
								</td>
							</c:if>
						</tr>
					</table>
				</tiles:put>

				<tiles:put name="blockContent" direct="true">
					<c:set var="criterios" value="${eliminacion.listaCriterios}" />
					<c:set var="unidadesConservar" value="${sessionScope[appConstants.valoracion.ELIMINACION_NUM_UDOCS_CONSERVAR_KEY]}" />
					<table class="w98mtb10" cellpadding=0 cellspacing=0>
						<tr>
							<td class="etiquetaAzul11Bold" width="45%" style="vertical-align:top;" valign="top">
								<bean:message key="archigest.archivo.valoraciones.conservacionUDocs"/>&nbsp;
							</td>
							<td width="10px">&nbsp;</td>
							<td class="etiquetaNegra11Normal">
								<display:table name="pageScope.criterios"
									id="criterio"
									decorator="valoracion.decorators.CriterioDecorator"
									style="width:99%;margin-left:auto;margin-right:auto">
									<display:setProperty name="basic.msg.empty_list">
										<bean:message key="archigest.archivo.valoraciones.noCriteriosconservacionParcial"/>
									</display:setProperty>
									<display:column titleKey="archigest.archivo.eliminacion.fecha" property="fechaInicial"/>
									<display:column titleKey="archigest.archivo.Y" style="width:10px">&nbsp;&amp;&nbsp;</display:column>
									<display:column titleKey="archigest.archivo.eliminacion.fecha" property="fechaFinal"/>
								</display:table>
							</td>
							<c:if test="${eliminacion.puedeSerEditada}">
								<td width="10px">&nbsp;</td>
								<td style="vertical-align:top;" valign="top">
										<a class="etiquetaAzul12Bold" href="javascript:definirCriterios()" >
										<html:img page="/pages/images/editDoc.gif" altKey="archigest.archivo.editar" titleKey="archigest.archivo.editar" styleClass="imgTextMiddle" />
										&nbsp;<bean:message key="archigest.archivo.conservacion.definir.criterios"/></a>
								</td>
							</c:if>
							<tr><td><div class="separador8">&nbsp;</div></td></tr>
						</tr>
						<tr>
							<td class="etiquetaAzul11Bold" width="45%" style="vertical-align:top;" valign="top">
								<bean:message key="archigest.archivo.valoraciones.num.udocs.conservacion.avanzado"/>&nbsp;
							</td>
							<td width="10px">&nbsp;</td>
							<td class="etiquetaNegra11Normal">
								<c:out value="${unidadesConservar}"/>
							</td>
							<c:if test="${eliminacion.puedeSerEditada}">
								<td width="10px">&nbsp;</td>
								<td width="15%" style="vertical-align:top;" valign="top">
										<a class="etiquetaAzul12Bold" href="javascript:definirCriteriosAvanzado()" >
										<html:img page="/pages/images/editDoc.gif" altKey="archigest.archivo.editar" titleKey="archigest.archivo.editar" styleClass="imgTextMiddle" />
										&nbsp;<bean:message key="archigest.archivo.conservacion.seleccion.avanzada"/></a>
								</td>
							</c:if>
						</tr>
					</table>
				</tiles:put>
			</tiles:insert>
		</c:when>
		</c:choose>

	</tiles:put>
</tiles:insert>