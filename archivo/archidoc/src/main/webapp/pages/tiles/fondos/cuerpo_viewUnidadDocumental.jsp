<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/archigest-adds.tld" prefix="pa" %>
<%@ taglib uri="/WEB-INF/taglibs/securityTag.tld" prefix="security"%>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>

<c:set var="udoc" value="${sessionScope[appConstants.fondos.UDOC_KEY]}" />

<c:url var="URL" value="/action/gestionFraccionSerie">
	<c:param name="method" value="divide" />
	<c:param name="id" value="${udoc.id}" />
</c:url>

<script language="javascript">
	function dividir(){
		if (window.top.showWorkingDiv) {
			var title = '<bean:message key="archigest.archivo.msg.titulo"/>';
			var message = '<bean:message key="archigest.archivo.msg.proceso.en.ejecucion"/>';
			window.top.showWorkingDiv(title, message);
		}
		window.location = "<c:out value="${URL}" escapeXml="false" />";
	}
</script>

<tiles:insert template="/pages/tiles/PABoxLayout.jsp">
	<tiles:put name="boxTitle" direct="true">
		<c:out value="${udoc.nombreNivel}"/>
	</tiles:put>

	<tiles:put name="buttonBar" direct="true">
		<table>
			<tr>
				<%-- Permitir dividir fracciones de serie en unidades documentales --%>
				<security:permissions action="${appConstants.fondosActions.ALTA_ELEMENTO_ACTION}">
				<c:if test="${udoc.subtipo == appConstants.fondos.subtiposNivel.CAJA.identificador}">
				   	<td nowrap="nowrap">
				   		<a class="etiquetaAzul12Bold" href="javascript:dividir()"><html:img page="/pages/images/dividir_fraccion_serie_color.gif"
						        altKey="archigest.archivo.cf.dividirFraccionSerie"
						        titleKey="archigest.archivo.cf.dividirFraccionSerie"
						        styleClass="imgTextMiddle"/>&nbsp;<bean:message key="archigest.archivo.cf.dividirFraccionSerie"/></a>
					</td>
					<td width="10">&nbsp;</td>
				</c:if>
				</security:permissions>
				<security:permissions action="${appConstants.descripcionActions.CONSULTAR_FICHA_ELEMENTO_ACTION}">
				<security:access object="${udoc}" permission="${appConstants.permissions.CONSULTA_DESCRIPCION_CUADRO_CLASIFICACION}">
				   	<td nowrap="true">
				   		<c:url var="URL" value="/action/isadg">
							<c:param name="method" value="retrieve" />
							<c:param name="id" value="${udoc.id}" />
						</c:url>
				   		<a class="etiquetaAzul12Bold" href="<c:out value="${URL}" escapeXml="false" />"><html:img page="/pages/images/searchDoc.gif"
						        altKey="archigest.archivo.descripcion.descriptor.button.verDescripcion"
						        titleKey="archigest.archivo.descripcion.descriptor.button.verDescripcion"
						        styleClass="imgTextMiddle"/>&nbsp;<bean:message key="archigest.archivo.descripcion.descriptor.button.verDescripcion"/></a>
					</td>
					<td width="10">&nbsp;</td>
				</security:access>
				</security:permissions>
				<security:permissions action="${appConstants.documentosActions.CONSULTA_DOCUMENTOS_CUADRO_CLASIFICACION_ACTION}">
				<security:access object="${udoc}" permission="${appConstants.permissions.CONSULTA_DOCUMENTOS_CUADRO_CLASIFICACION}">
				   	<td nowrap="nowrap">
				   		<c:url var="URL" value="/action/documentos">
							<c:param name="actionToPerform" value="homeUDoc" />
							<c:param name="id" value="${udoc.id}" />
							<c:param name="tipo" value="1" />
						</c:url>
				   		<a class="etiquetaAzul12Bold"
				   		   target="_self"
						   href="<c:out value="${URL}" escapeXml="false" />"><html:img page="/pages/images/docsElectronicos.gif"
						        altKey="archigest.archivo.descripcion.descriptor.button.verDocumentos"
						        titleKey="archigest.archivo.descripcion.descriptor.button.verDocumentos"
						        styleClass="imgTextMiddle"/>&nbsp;<bean:message key="archigest.archivo.descripcion.descriptor.button.verDocumentos"/></a>
					</td>
					<td width="10">&nbsp;</td>
				</security:access>
				</security:permissions>
				<td nowrap>
					<tiles:insert definition="button.closeButton" />
				</td>
			</tr>
		</table>
	</tiles:put>

	<tiles:put name="boxContent" direct="true">
		<div class="separador1">&nbsp;</div>
		<div class="bloque_tab">
			<TABLE class="formulario"> <%-- para aspecto de formulario con lineas bottom de celda --%>
				<TR>
					<TD class="tdTitulo" width="200px">
						<bean:message key="archigest.archivo.cf.codigo"/>:&nbsp;
					</TD>
					<TD class="tdDatos">
						<c:out value="${udoc.codReferenciaPersonalizado}" />
					</TD>
				</TR>
				<TR>
					<TD class="tdTitulo">
						<c:out value="${udoc.nombreNivel}"/>:&nbsp;
					</TD>
					<TD class="tdDatos">
						<c:out value="${udoc.numExp}" /> <c:out value="${udoc.titulo}" />
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
				<c:if test="${!empty udoc.solicitudEntregada}">
				<tr><td colspan="2">&nbsp;</td></tr>
				<TR>
					<TD class="tdTitulo">
						<bean:message key="archigest.archivo.estado"/>:&nbsp;
					</TD>
					<TD class="tdDatos">
						<c:choose>
							<c:when test="${udoc.solicitudEntregada.tiposolicitud==1}">
								<c:url var="verSolicitudURL" value="/action/gestionPrestamos">
									<c:param name="method" value="verprestamo" />
									<c:param name="id" value="${udoc.solicitudEntregada.idsolicitud}" />
								</c:url>
							</c:when>
							<c:otherwise>
								<c:url var="verSolicitudURL" value="/action/gestionConsultas">
									<c:param name="method" value="verconsulta" />
									<c:param name="id" value="${udoc.solicitudEntregada.idsolicitud}" />
								</c:url>
							</c:otherwise>
						</c:choose>
						<a href="<c:out value="${verSolicitudURL}" escapeXml="false"/>" class="tdlink">
							<fmt:message key="archigest.archivo.solicitudes.detalle.estado.${udoc.solicitudEntregada.estado}"/>
						</a>
					</TD>
				</TR>
				</c:if>
				<TR>
					<TD><html:img page="/pages/images/pixel.gif" width="1" height="5"/></TD>
				</TR>
			</TABLE>
		</div>

		<div class="separador8">&nbsp;</div>

		<security:permissions action="${appConstants.fondosActions.GESTION_SOLICITUDES_SERIE_ACTION}">
		<tiles:insert page="/pages/tiles/PADockableBlockLayout.jsp">
			<tiles:put name="blockName" direct="true">controlAcceso</tiles:put>
			<tiles:put name="blockTitle" direct="true">
				<bean:message key="archigest.archivo.cf.datosGestionVisualizacionAlmacenamiento"/>
			</tiles:put>
			<tiles:put name="buttonBar" direct="true">
				<TABLE>
					<TR>
						<TD>
							<c:url var="edicionUdocURL" value="/action/gestionUdocsCF">
								<c:param name="method" value="editarInfoUdoc" />
								<c:param name="idUdoc" value="${udoc.id}" />
							</c:url>
							<a class="etiquetaAzul12Bold" href="<c:out value="${edicionUdocURL}" escapeXml="false"/>" >
								<html:img page="/pages/images/edit.gif" altKey="archigest.archivo.editar" titleKey="archigest.archivo.editar" styleClass="imgTextMiddle" />
								<bean:message key="archigest.archivo.editar"/>
							</a>
						</TD>
					</TR>
				</TABLE>
			</tiles:put>

			<tiles:put name="dockableContent" direct="true">
				<table class="formulario">
					<tr>

						<td class="tdTitulo" width="250px">
							<bean:message key="archigest.archivo.identificador"/>:&nbsp;
						</td>
						<td class="tdDatos">
							<c:out value="${udoc.id}"/>
						</td>
					</tr>

					<tr>

						<td class="tdTitulo" width="250px">
							<bean:message key="archigest.archivo.nivelAcceso"/>:&nbsp;
						</td>
						<td class="tdDatos">
							<c:choose>
								<c:when test="${udoc.nivelAcceso == '1'}">
									<bean:message key="archivo.nivel_acceso.publico"/>
								</c:when>
								<c:when test="${udoc.nivelAcceso == '2'}">
									<bean:message key="archivo.nivel_acceso.archivo"/>
								</c:when>
								<c:when test="${udoc.nivelAcceso == '3'}">
									<bean:message key="archivo.nivel_acceso.restringido"/>
								</c:when>
								<c:otherwise>&nbsp;</c:otherwise>
							</c:choose>
						</td>
					</tr>
					<tr>
						<td class="tdTitulo" width="200px">
							<bean:message key="archigest.archivo.listaControlAcceso"/>:&nbsp;
						</td>
						<td class="tdDatos">
							<c:choose>
								<c:when test="${udoc.nivelAcceso == '1'}">
									--
								</c:when>
								<c:otherwise>
									<c:out value="${udoc.listaControlAcceso.nombre}"/>
								</c:otherwise>
							</c:choose>
						</td>
					</tr>
					<tr>
						<td class="tdTitulo" width="250px">
							<bean:message key="archigest.archivo.cf.fichaDescAsociada"/>:&nbsp;
						</td>
						<td class="tdDatos">
							<c:out value="${udoc.fichaDescriptiva.nombre}"> -- </c:out>
						</td>
					</tr>
					<tr>
						<td class="tdTitulo">
							<bean:message key="archigest.archivo.repositorio.ecm"/>:&nbsp;
						</td>
						<td class="tdDatos">
							<c:out value="${udoc.repositorioEcm.nombre}" > -- </c:out>
						</td>
					</tr>
				</table>
			</tiles:put>
		</tiles:insert>

		<div class="separador8">&nbsp;</div>

		</security:permissions>

		<c:if test="${sessionScope[appConstants.fondos.PERMITIR_VER_SIGNATURAS]}">
			<tiles:insert page="/pages/tiles/PABlockLayout.jsp">
				<tiles:put name="blockTitle" direct="true">
					<bean:message key="archigest.archivo.signaturas"/>
				</tiles:put>
				<tiles:put name="blockContent" direct="true">
					<c:forEach var="signaturaUdoc" items="${udoc.signaturas}">
						<TABLE class="formulario">
							<TR>
								<TD colspan="2">
									<TABLE class="w98m1" cellpadding="0" cellspacing="0">
										<TR>
											<TD class="etiquetaAzul11Bold" width="100%">
												<c:out value="${signaturaUdoc.signaturaudoc}" />
											</TD>
										</TR>
										<TR>
											<TD><html:img page="/pages/images/linea.jpg" width="100%" height="1"/></TD>
										</TR>
										<TR><TD class="separador1">&nbsp;</TD></TR>
									</TABLE>
								</TD>
							</TR>
							<TR>
								<TD class="tdTitulo" width="200px">
									<bean:message key="archigest.archivo.ubicacion"/>:&nbsp;
								</TD>
								<TD class="tdDatos">
									<security:permissions action="${appConstants.depositoActions.CONSULTA_DEPOSITO}">
										<c:url var="verHuecoURL" value="/action/gestionUdocsCF">
											<c:param name="method" value="verHuecoEnDeposito" />
											<c:param name="idHueco" value="${signaturaUdoc.ubicacionFisica.idHueco}" />
										</c:url>
										<a class="tdlink" href="<c:out value="${verHuecoURL}" escapeXml="false"/>" target="_self">
									</security:permissions>
										<c:out value="${signaturaUdoc.ubicacionFisica.path}" />
									<security:permissions action="${appConstants.depositoActions.CONSULTA_DEPOSITO}">
										</a>
									</security:permissions>
								</TD>
							</TR>
							<TR>
								<TD class="tdTitulo">
									<bean:message key="archigest.archivo.contenido"/>:&nbsp;
								</TD>
								<TD class="tdDatos">
									<c:out value="${signaturaUdoc.descripcion}"> -- </c:out>
								</TD>
							</TR>
							<TR>
								<TD><html:img page="/pages/images/pixel.gif" width="1" height="1"/></TD>
							</TR>
						</TABLE>
					</c:forEach>
				</tiles:put>
			</tiles:insert>
		</c:if>
	</tiles:put>
</tiles:insert>