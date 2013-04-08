<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/taglibs/securityTag.tld" prefix="security"%>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>

<c:set var="vFondo" value="${sessionScope[appConstants.fondos.FONDO_KEY]}"/>
<c:set var="vEntidad" value="${vFondo.entidadProductora}" />
<c:set var="jerarquia" value="${sessionScope[appConstants.fondos.JERARQUIA_ELEMENTO_CUADRO_CLASIFICACION]}"/>

<tiles:insert template="/pages/tiles/PABoxLayout.jsp">
	<tiles:put name="boxTitle" direct="true">
	<bean:message key="archigest.archivo.cf.fondo"/>
	</tiles:put>

	<tiles:put name="buttonBar" direct="true">
		<TABLE cellpadding=0 cellspacing=0>
		<TR>


			<c:set var="treeView" value="${sessionScope[appConstants.fondos.CUADRO_CLF_VIEW_NAME]}" />
			<c:set var="parentNode" value="${treeView.selectedNode.parent}" />

			<c:if test="${!empty parentNode}">
				<td>
					<c:url var="viewElementURL" value="/action/manageVistaCuadroClasificacion">
						<c:param name="actionToPerform" value="verNodo" />
						<c:param name="node" value="${parentNode.nodePath}" />
						<c:param name="refreshView" value="true" />
					</c:url>
					<a class="etiquetaAzul12Bold" href="<c:out value="${viewElementURL}" escapeXml="false"/>" >
						<html:img page="/pages/images/treePadre.gif" altKey="archigest.archivo.cf.verPadre" titleKey="archigest.archivo.cf.verPadre" styleClass="imgTextMiddle" />
						<bean:message key="archigest.archivo.cf.verPadre"/>
					</a>
				</td>
				<TD width="10">&nbsp;</TD>
			</c:if>
			<security:permissions action="${appConstants.fondosActions.MODIFICAR_ELEMENTO_ACTION}">
				<td>
					<c:url var="moverFondotURL" value="/action/gestionFondoAction">
						<c:param name="method" value="seleccionarNuevoPadre" />
						<c:param name="idFondo" value="${vFondo.id}" />
					</c:url>
					<a class="etiquetaAzul12Bold" href="<c:out value="${moverFondotURL}" escapeXml="false"/>" >
						<html:img page="/pages/images/treeMover.gif" altKey="archigest.archivo.cf.mover" titleKey="archigest.archivo.cf.mover" styleClass="imgTextMiddle" />
						<bean:message key="archigest.archivo.cf.mover"/>
					</a>
				</td>
				<TD width="10">&nbsp;</TD>
			</security:permissions>
			<security:permissions action="${appConstants.fondosActions.MODIFICAR_ELEMENTO_ACTION}">
				<c:url var="cambiarEstadoFondoURL" value="/action/gestionFondoAction">
					<c:param name="method" value="cambiarEstadoVigenciaFondo" />
					<c:param name="idelementocf" value="${vFondo.id}" />
				</c:url>
				<c:choose>
					<c:when test="${vFondo.vigente}">
						<TD>
							<a class="etiquetaAzul12Bold" href="<c:out value="${cambiarEstadoFondoURL}" escapeXml="false"/>">
								<html:img page="/pages/images/no_vigente.gif" altKey="archigest.archivo.cf.noVigente" titleKey="archigest.archivo.cf.noVigente" styleClass="imgTextMiddle" />
								<bean:message key="archigest.archivo.cf.noVigente"/>
							</a>
						</TD>
						<TD width="10">&nbsp;</TD>
					</c:when>
					<c:otherwise>
						<security:permissions action="${appConstants.fondosActions.ELIMINACION_ELEMENTO_ACTION}">
							<c:if test="${vFondo.puedeSerEliminado}">
								<TD>
									<c:url var="eliminarURI" value="/action/gestionFondoAction">
										<c:param name="method" value="eliminar" />
										<c:param name="idelementocf" value="${vFondo.id}" />
									</c:url>
									<script>
										function eliminarClasificadorSeries() {
											if (confirm("<bean:message key='archigest.archivo.cf.msgConfirmFondoEliminar'/>"))
												window.location = '<c:out value="${eliminarURI}" escapeXml="false"/>';
										}
									</script>
									<a class="etiquetaAzul12Bold" href="javascript:eliminarClasificadorSeries()">
										<html:img page="/pages/images/delete.gif" altKey="archigest.archivo.eliminar" titleKey="archigest.archivo.eliminar" styleClass="imgTextMiddle"/>
										&nbsp;<bean:message key="archigest.archivo.eliminar"/>
									</a>
								</TD>
								<TD width="10">&nbsp;</TD>
							</c:if>
						</security:permissions>
						<TD>
							<a class="etiquetaAzul12Bold" href="<c:out value="${cambiarEstadoFondoURL}" escapeXml="false"/>">
								<html:img page="/pages/images/vigente.gif" altKey="archigest.archivo.cf.vigente" titleKey="archigest.archivo.cf.vigente" styleClass="imgTextMiddle" />
								<bean:message key="archigest.archivo.cf.vigente"/>
							</a>
						</TD>
						<TD width="10">&nbsp;</TD>
					</c:otherwise>
				</c:choose>
			</security:permissions>
			<TD nowrap>
				<tiles:insert definition="button.closeButton" />
			</TD>
		</TR>
		</TABLE>
	</tiles:put>
	<tiles:put name="boxContent" direct="true">

	<tiles:insert template="/pages/tiles/PABlockLayout.jsp">
	<tiles:put name="buttonBar" direct="true">

			<TABLE cellpadding=0 cellspacing=0>
			  <TR>
			  <td>
				<security:permissions action="${appConstants.descripcionActions.CONSULTAR_FICHA_ELEMENTO_ACTION}">
				<security:access object="${vFondo}" permission="${appConstants.permissions.CONSULTA_DESCRIPCION_CUADRO_CLASIFICACION}">
				   	<td nowrap="true">
					   	<c:url var="URL" value="/action/isadg">
							<c:param name="method" value="retrieve" />
							<c:param name="id" value="${vFondo.id}" />
						</c:url>
				   		<a class="etiquetaAzul12Bold"
						   href="<c:out value="${URL}" escapeXml="false" />"><html:img page="/pages/images/searchDoc.gif"
						        altKey="archigest.archivo.descripcion.descriptor.button.verDescripcion"
						        titleKey="archigest.archivo.descripcion.descriptor.button.verDescripcion"
						        styleClass="imgTextMiddle"/>&nbsp;<bean:message key="archigest.archivo.descripcion.descriptor.button.verDescripcion"/></a>
					</td>
					<td width="10">&nbsp;</td>
				</security:access>
				</security:permissions>
				<security:permissions action="${appConstants.fondosActions.MODIFICAR_ELEMENTO_ACTION}">
					<TD style="text-align:right">
						<c:url var="modificarURI" value="/action/gestionFondoAction">
							<c:param name="method" value="editar" />
							<c:param name="idFondo" value="${vFondo.id}" />
						</c:url>
						<a class="etiquetaAzul12Bold" href="<c:out value="${modificarURI}" escapeXml="false"/>">
							<html:img page="/pages/images/edit.gif" altKey="archigest.archivo.editar" titleKey="archigest.archivo.editar" styleClass="imgTextMiddle"/>
							&nbsp;<bean:message key="archigest.archivo.editar"/>
						</a>
					</TD>
					<TD width="10">&nbsp;</TD>
				</security:permissions>
				</td>
			  </TR>
			</TABLE>
	</tiles:put>
	<tiles:put name="blockContent" direct="true">
		<table class="formulario">
			<c:if test="${not empty jerarquia}">
			<tr>
				<td width="180px" class="tdTitulo">
					<bean:message key="archigest.archivo.jerarquia.elemento"/>:&nbsp;
				</td>
				<td class="tdDatosBold" nowrap="nowrap">
					<tiles:insert definition="fondos.cf.jerarquiaElemento" />
				</td>
			</tr>
			</c:if>
			<tr>
				<td width="180px" class="tdTitulo">
					<bean:message key="archigest.archivo.cf.codReferencia"/>:&nbsp;
				</td>
				<td class="tdDatosBold">
					<c:out value="${vFondo.codReferenciaPersonalizado}" />
				</td>
			</tr>
			<tr>
				<td class="tdTitulo">
					<bean:message key="archigest.archivo.cf.denominacion"/>:&nbsp;
				</td>
				<td class="tdDatosBold">
					<c:out value="${vFondo.denominacion}"/>
				</td>
			</tr>
			<tr>
				<td colspan="2">
				<div class="separador5">&nbsp;</div>
				</td>
			</tr>
			<tr>
				<td class="tdTitulo" >
					<bean:message key="archigest.archivo.transferencias.pais"/>:&nbsp;
				</td>
				<td class="tdDatos">
					<c:out value="${vFondo.pais.nombre}"/>
				</td>
			</tr>
			<tr>
				<td class="tdTitulo">
					<bean:message key="archigest.archivo.comunidadAutonoma"/>:&nbsp;
				</td>
				<td class="tdDatos">
					<c:out value="${vFondo.comunidad.nombre}"/>
				</td>
			</tr>
			<tr>
				<td class="tdTitulo">
					<bean:message key="archigest.archivo.archivo"/>:&nbsp;
				</td>
				<td class="tdDatos">
					<c:out value="${vFondo.archivo.nombre}"/>
				</td>
			</tr>
			<tr>
				<td class="tdTitulo">
					<bean:message key="archigest.archivo.cf.estado"/>:&nbsp;
				</td>
				<td class="tdDatosBold">
					<fmt:message key="archigest.archivo.cf.estadoElementoCF.${vFondo.estado}" />
				</td>
			</tr>
			<c:if test="${appConstants.configConstants.mostrarCampoOrdenacionCuadro}">
				<tr>
					<td class="tdTitulo">
						<bean:message key="archigest.archivo.cf.codigoOrdenacion"/>:&nbsp;
					</td>
					<td class="tdDatosBold">
						<c:out value="${vFondo.ordPos}"/>
					</td>
				</tr>
			</c:if>
		</table>
		<table class="formulario" cellpadding=0 cellspacing=0>
			<tr>
				<td class="tdTitulo" width="200px">
					<bean:message key="archigest.archivo.cf.entidadProductora"/>&nbsp;
				</td>
				<td class="tdDatos">
				</td>
			</tr>
		</table>
		<table class="formulario" cellpadding=0 cellspacing=0>
			<tr>
				<td width="50px">
				</td>
				<td class="tdTitulo" width="150px">
					<bean:message key="archigest.archivo.cf.tipo" />:&nbsp;&nbsp;
				</td>
				<td class="tdDatos">
						<c:if test="${vEntidad.tipoentidad == appConstants.fondos.tiposEntidad.FAMILIA.identificador}">
						<bean:message key="archigest.archivo.cf.familia"/>
					</c:if>
						<c:if test="${vEntidad.tipoentidad == appConstants.fondos.tiposEntidad.INSTITUCION.identificador}">
						<bean:message key="archigest.archivo.cf.institucion"/>
					</c:if>
						<c:if test="${vEntidad.tipoentidad == appConstants.fondos.tiposEntidad.PERSONA.identificador}">
						<bean:message key="archigest.archivo.cf.persona"/>
					</c:if>
				</td>
			</tr>
			<tr>
				<td width="50px">
				</td>
				<td class="tdTitulo">
					<bean:message key="archigest.archivo.cf.nombre" />:&nbsp;
				</td>
				<td class="tdDatos">
					<c:out value="${vEntidad.nombre}" />
				</td>
			</tr>
		</table>
	</tiles:put>
	</tiles:insert>

	<div class="separador8">&nbsp;</div>

	<tiles:insert page="/pages/tiles/PADockableBlockLayout.jsp">
			<tiles:put name="blockName" direct="true">controlAcceso</tiles:put>
			<tiles:put name="blockTitle" direct="true">
				<bean:message key="archigest.archivo.cf.datosGestionVisualizacion"/>
			</tiles:put>
			<tiles:put name="buttonBar" direct="true">
				<TABLE>
					<TR>
						<TD>
							<c:url var="edicionFondoURL" value="/action/gestionFondoAction">
								<c:param name="method" value="editarInfoFondo" />
								<c:param name="idFondo" value="${vFondo.id}" />
							</c:url>
							<a class="etiquetaAzul12Bold" href="<c:out value="${edicionFondoURL}" escapeXml="false"/>" >
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
							<c:out value="${vFondo.id}"/>
						</td>
					</tr>

					<tr>
						<td class="tdTitulo" width="250px">
							<bean:message key="archigest.archivo.nivelAcceso"/>:&nbsp;
						</td>
						<td class="tdDatos">
							<c:choose>
								<c:when test="${vFondo.nivelAcceso == '1'}">
									<bean:message key="archivo.nivel_acceso.publico"/>
								</c:when>
								<c:when test="${vFondo.nivelAcceso == '2'}">
									<bean:message key="archivo.nivel_acceso.archivo"/>
								</c:when>
								<c:when test="${vFondo.nivelAcceso == '3'}">
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
							<c:out value="${vFondo.listaControlAcceso.nombre}"> -- </c:out>
						</td>
					</tr>
				</table>
			</tiles:put>
		</tiles:insert>

	<div class="separador8">&nbsp;</div>

	<tiles:insert template="/pages/tiles/PABlockLayout.jsp">
	<tiles:put name="blockTitle" direct="true"><bean:message key="archigest.archivo.cf.clasificadores"/></tiles:put>
	<tiles:put name="buttonBar" direct="true">
			<TABLE cellpadding=0 cellspacing=0>
			  <TR>
				<TD>
					<html:img page="/pages/images/pixel.gif" styleClass="imgTextMiddle" height="18" />
				</TD>
					<jsp:include page="../accionesElementoCF.jsp" flush="true" />
			  </TR>
			</TABLE>
	</tiles:put>
	<tiles:put name="blockContent" direct="true">

		<div class="separador5">&nbsp;</div>
		<c:url var="viewFondoPaginationURL" value="/action/gestionFondoAction">
			<c:param name="method" value="verfondo" />
			<c:param name="idelementocf" value="${vFondo.id}" />
		</c:url>
		<jsp:useBean id="viewFondoPaginationURL" type="java.lang.String" />


		<c:set var="descendientes" value="${vFondo.descendientes}" />

		<display:table
			name="pageScope.descendientes"
			id="elementoCS"
			style="width:99%;margin-left:auto;margin-right:auto"
			requestURI="<%=viewFondoPaginationURL%>">

			<display:setProperty name="basic.msg.empty_list">
				&nbsp;<bean:message key="archigest.archivo.cf.fondoSinClasificadores"/>
			</display:setProperty>

			<display:column titleKey="archigest.archivo.cf.codigo" sortable="true">
				<c:set var="treeView" value="${sessionScope[appConstants.fondos.CUADRO_CLF_VIEW_NAME]}" />
				<c:set var="selectedNode" value="${treeView.selectedNode}" />

				<c:choose>
					<c:when test="${elementoCS.estado == appConstants.fondos.estadoElementoCF.TEMPORAL}">
						<c:url var="viewElementURL" value="/action/gestionSeries">
							<c:param name="method" value="verSolicitudBySerie" />
							<c:param name="idSerie" value="${elementoCS.id}" />
						</c:url>
					</c:when>
					<c:otherwise>
						<c:url var="viewElementURL" value="/action/manageVistaCuadroClasificacion">
							<c:param name="actionToPerform" value="verNodo" />
							<c:param name="node" value="${selectedNode.nodePath}/item${elementoCS.id}" />
							<c:param name="refreshView" value="true" />
						</c:url>
					</c:otherwise>
				</c:choose>

				<a href="<c:out value="${viewElementURL}" escapeXml="false"/>" class="tdlink">
					<c:out value="${elementoCS.codigo}" />
				</a>
			</display:column>
			<display:column titleKey="archigest.archivo.cf.denominacion" sortable="true">
				<a href="<c:out value="${viewElementURL}" escapeXml="false"/>" class="tdlink">
					<c:out value="${elementoCS.titulo}" />
				</a>
			</display:column>
			<display:column titleKey="archigest.archivo.cf.estado">
				<fmt:message key="archigest.archivo.cf.estadoElementoCF.${elementoCS.estado}" />
			</display:column>

		</display:table>
		<div class="separador5">&nbsp;</div>

	</tiles:put>
	</tiles:insert>

	</tiles:put>

</tiles:insert>