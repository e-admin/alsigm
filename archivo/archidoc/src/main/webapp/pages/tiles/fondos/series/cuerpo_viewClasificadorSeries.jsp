<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo" %>
<%@ taglib uri="/WEB-INF/taglibs/securityTag.tld" prefix="security"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>

<c:set var="clasificadorSeries" value="${sessionScope[appConstants.fondos.ELEMENTO_CF_KEY]}" />
<c:set var="nivel" value="${sessionScope[appConstants.fondos.NIVEL_CF_KEY]}" />
<c:set var="nivelPadre" value="${sessionScope[appConstants.fondos.NIVEL_CF_PADRE_KEY]}" />
<c:set var="jerarquia" value="${sessionScope[appConstants.fondos.JERARQUIA_ELEMENTO_CUADRO_CLASIFICACION]}"/>
<c:set var="listaAcceso" value="${sessionScope[appConstants.fondos.LISTAS_CONTROL_ACCESO_KEY]}" />

<div id="contenedor_ficha">

<div class="ficha">

<h1><span>
	<div class="w100">
	<TABLE class="w98" cellpadding=0 cellspacing=0>
	<TR>
	    <TD class="etiquetaAzul12Bold" height="25px">
			&nbsp;<bean:message key="archigest.archivo.cf.clasificador"/>
		</TD>
	    <TD align="right">
			<TABLE cellpadding=0 cellspacing=0>
			<TR>
				<c:set var="treeView" value="${sessionScope[appConstants.fondos.CUADRO_CLF_VIEW_NAME]}" />
				<c:set var="parentNode" value="${treeView.selectedNode.parent}" />

				<c:if test="${!empty parentNode}">
					<TD>
						<c:url var="viewElementURL" value="/action/manageVistaCuadroClasificacion">
							<c:param name="actionToPerform" value="verNodo" />
							<c:param name="node" value="${parentNode.nodePath}" />
							<c:param name="refreshView" value="true" />
						</c:url>
						<a class="etiquetaAzul12Bold" href="<c:out value="${viewElementURL}" escapeXml="false"/>" >
							<html:img page="/pages/images/treePadre.gif" altKey="archigest.archivo.cf.verPadre" titleKey="archigest.archivo.cf.verPadre" styleClass="imgTextMiddle" />
							<bean:message key="archigest.archivo.cf.verPadre"/>
						</a>
					</TD>
					<TD width="10">&nbsp;</TD>
				</c:if>
				<security:permissions action="${appConstants.fondosActions.MODIFICAR_ELEMENTO_ACTION}">
						<TD>
							<c:url var="moverClasificadorURL" value="/action/gestionClasificadorSeriesAction">
								<c:param name="method" value="move" />
								<c:param name="idClasificador" value="${clasificadorSeries.id}" />
							</c:url>
							<a class="etiquetaAzul12Bold" href="<c:out value="${moverClasificadorURL}" escapeXml="false"/>" >
								<html:img page="/pages/images/treeMover.gif" altKey="archigest.archivo.cf.mover" titleKey="archigest.archivo.cf.mover" styleClass="imgTextMiddle" />
								<bean:message key="archigest.archivo.cf.mover"/>
							</a>
						</TD>
						<TD width="10">&nbsp;</TD>
				</security:permissions>
				<security:permissions action="${appConstants.fondosActions.ELIMINACION_ELEMENTO_ACTION}">
					<c:url var="eliminarClasificadorSeriesURL" value="/action/gestionClasificadorSeriesAction">
						<c:param name="method" value="eliminarClasificadorSeries" />
						<c:param name="idelementocf" value="${clasificadorSeries.id}" />
					</c:url>
					<TD>
						<script>
							function eliminar() {
								if (confirm("<bean:message key='archigest.archivo.cf.eliminarClasificadorSeries'/>"))
									window.location='<c:out value="${eliminarClasificadorSeriesURL}" escapeXml="false"/>'
							}
						</script>
						<a class="etiquetaAzul12Bold" href="javascript:eliminar();">
							<html:img page="/pages/images/delete.gif" altKey="archigest.archivo.eliminar" titleKey="archigest.archivo.eliminar" styleClass="imgTextMiddle" />
							&nbsp;<bean:message key="archigest.archivo.eliminar"/>
						</a>
					</TD>
					<TD width="10">&nbsp;</TD>
				</security:permissions>

				<c:url var="cambiarEstadoClasificadorSeriesURL" value="/action/gestionClasificadorSeriesAction">
					<c:param name="method" value="cambiarEstadoVigenciaClasificadorSeries" />
					<c:param name="idelementocf" value="${clasificadorSeries.id}" />
				</c:url>

				<security:permissions action="${appConstants.fondosActions.MODIFICAR_ELEMENTO_ACTION}">
					<c:choose>
						<c:when test="${clasificadorSeries.vigente}">
							<TD>
								<a class="etiquetaAzul12Bold" href="<c:out value="${cambiarEstadoClasificadorSeriesURL}" escapeXml="false"/>">
									<html:img page="/pages/images/no_vigente.gif" altKey="archigest.archivo.cf.noVigente" titleKey="archigest.archivo.cf.noVigente" styleClass="imgTextMiddle" />
									<bean:message key="archigest.archivo.cf.noVigente"/>
								</a>
							</TD>
						</c:when>
						<c:otherwise>
							<TD>
								<a class="etiquetaAzul12Bold" href="<c:out value="${cambiarEstadoClasificadorSeriesURL}" escapeXml="false"/>">
									<html:img page="/pages/images/vigente.gif" altKey="archigest.archivo.cf.vigente" titleKey="archigest.archivo.cf.vigente" styleClass="imgTextMiddle" />
									<bean:message key="archigest.archivo.cf.vigente"/>
								</a>
							</TD>
						</c:otherwise>
					</c:choose>
					<TD width="10">&nbsp;</TD>
				</security:permissions>


				<TD nowrap>
				<tiles:insert definition="button.closeButton" />
				</TD>
			</TR>
			</TABLE>
		</TD>
	</TR>
	</TABLE>
	</div>
</span></h1>

	<div class="cuerpo_drcha">
	<div class="cuerpo_izda">

	<DIV id="barra_errores">
		<archivo:errors />
	</DIV>

	<div class="separador1">&nbsp;</div>

	<div class="cabecero_bloque">
		<table class="w98m1" cellpadding=0 cellspacing=0>
		  <tr>
			<TD width="100%" align="right">
			<TABLE cellpadding=0 cellspacing=0>
			  <TR>
				<security:permissions action="${appConstants.descripcionActions.CONSULTAR_FICHA_ELEMENTO_ACTION}">
				<security:access object="${clasificadorSeries}" permission="${appConstants.permissions.CONSULTA_DESCRIPCION_CUADRO_CLASIFICACION}">
				   	<td nowrap="true">
				   		<c:url var="URL" value="/action/isadg">
							<c:param name="method" value="retrieve" />
							<c:param name="id" value="${clasificadorSeries.id}" />
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
				<TD>
					<c:url var="edicionClasificadorSeriesURL" value="/action/gestionClasificadorSeriesAction">
						<c:param name="method" value="editarClasificadorSeries" />
						<c:param name="idClfSeries" value="${clasificadorSeries.id}" />
					</c:url>
					<a class="etiquetaAzul12Bold" href="<c:out value="${edicionClasificadorSeriesURL}" escapeXml="false"/>">
						<html:img page="/pages/images/edit.gif" altKey="archigest.archivo.editar" titleKey="archigest.archivo.editar" styleClass="imgTextMiddle" />
						&nbsp;<bean:message key="archigest.archivo.editar"/>
					</a>
				</TD>
				<TD width="10">&nbsp;</TD>
				</security:permissions>
			  </TR>
			</TABLE>
			</TD>
		  </tr>
		</table>
	</div>

		<div class="bloque"> <%--primer bloque de datos --%>
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

				<tiles:insert definition="fondos.cf.infoClasificadorSerie" />
			</table>
		</div>

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
							<c:url var="edicionClasificadorSerieURL" value="/action/gestionClasificadorSeriesAction">
								<c:param name="method" value="editarInfoClasificadorSerie" />
								<c:param name="idClasificadorSerie" value="${clasificadorSeries.id}" />
							</c:url>
							<a class="etiquetaAzul12Bold" href="<c:out value="${edicionClasificadorSerieURL}" escapeXml="false"/>" >
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
							<c:out value="${clasificadorSeries.id}"/>
						</td>
					</tr>


					<tr>
						<td class="tdTitulo" width="250px">
							<bean:message key="archigest.archivo.nivelAcceso"/>:&nbsp;
						</td>
						<td class="tdDatos">
							<c:choose>
								<c:when test="${clasificadorSeries.nivelAcceso == '1'}">
									<bean:message key="archivo.nivel_acceso.publico"/>
								</c:when>
								<c:when test="${clasificadorSeries.nivelAcceso == '2'}">
									<bean:message key="archivo.nivel_acceso.archivo"/>
								</c:when>
								<c:when test="${clasificadorSeries.nivelAcceso == '3'}">
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
							<c:out value="${listaAcceso.nombre}"> -- </c:out>
						</td>
					</tr>
				</table>
			</tiles:put>
		</tiles:insert>

	<div class="separador8">&nbsp;</div>

	<DIV class="cabecero_bloque"> <%--cabecero primer bloque de datos --%>
		<TABLE class="w98m1" cellpadding=0 cellspacing=0>
		  <TR>
		    <TD class="etiquetaAzul12Bold" width="40%" >
				&nbsp;<bean:message key="archigest.archivo.cf.descendientes"/>
			</TD>
		    <TD width="60%" align="right">
			<TABLE cellpadding=0 cellspacing=0>
			  <TR>
				<TD>
					<IMG src="../images/pixel.gif" class="imgTextMiddle" height="18"/>
				</TD>
				<jsp:include page="../accionesElementoCF.jsp" flush="true" />
			  </TR>
			</TABLE>
		   </TR>
		</TABLE>
	</DIV>

	<div class="bloque"> <%--primer bloque de datos --%>

		<div class="separador5">&nbsp;</div>



		<c:url var="viewClasificadorSeriesPaginationURL" value="/action/gestionClasificadorSeriesAction">
			<c:param name="method" value="verClasificadorSeries" />
			<c:param name="idelementocf" value="${clasificadorSeries.id}" />
		</c:url>
		<jsp:useBean id="viewClasificadorSeriesPaginationURL" type="java.lang.String" />

		<c:set var="descendientes" value="${sessionScope[appConstants.fondos.CHILDS_ELEMENTO_CF_KEY]}"/>

		<display:table
			name="pageScope.descendientes"
			id="elementoCF"
			style="width:99%;margin-left:auto;margin-right:auto"
			requestURI="<%=viewClasificadorSeriesPaginationURL%>">
			<display:setProperty name="basic.msg.empty_list">
				&nbsp;<bean:message key="archigest.archivo.cf.noDescendientes"/>
			</display:setProperty>

			<display:column titleKey="archigest.archivo.codigo" sortable="true">
				<c:choose>
					<c:when test="${!elementoCF.temporal}">
						<c:set var="treeView" value="${sessionScope[appConstants.fondos.CUADRO_CLF_VIEW_NAME]}" />
						<c:set var="selectedNode" value="${treeView.selectedNode}" />
						<c:url var="viewElementURL" value="/action/manageVistaCuadroClasificacion">
							<c:param name="actionToPerform" value="verNodo" />
							<c:param name="node" value="${selectedNode.nodePath}/item${elementoCF.id}" />
							<c:param name="refreshView" value="true" />
						</c:url>
						<a href="<c:out value="${viewElementURL}" escapeXml="false"/>" class="tdlink">
							<c:out value="${elementoCF.codigo}" />
						</a>
					</c:when>
					<c:otherwise>
						<c:out value="${elementoCF.codigo}" />
					</c:otherwise>
				</c:choose>
			</display:column>

			<display:column titleKey="archigest.archivo.denominacion" sortable="true">
				<c:choose>
					<c:when test="${!elementoCF.temporal}">
						<a href="<c:out value="${viewElementURL}" escapeXml="false"/>" class="tdlink">
							<c:out value="${elementoCF.titulo}" />
						</a>
					</c:when>
					<c:otherwise>
						<c:out value="${elementoCF.titulo}" />
					</c:otherwise>
				</c:choose>
			</display:column>
			<display:column titleKey="archigest.archivo.estado">
				<c:choose>
					<c:when test="${!elementoCF.tipoSerie || elementoCF.estado==appConstants.fondos.estadoElementoCF.TEMPORAL}">
						<fmt:message key="archigest.archivo.cf.estadoElementoCF.${elementoCF.estado}" />
					</c:when>
					<c:otherwise>
						<fmt:message key="archigest.archivo.cf.estadoSerie.${elementoCF.estado}" />
					</c:otherwise>
				</c:choose>
			</display:column>

		</display:table>

		<div class="separador5">&nbsp;</div>

	</div>

</div> <%--cuerpo_drcha --%>
</div> <%--cuerpo_izda --%>

<h2><span>&nbsp;
</span></h2>

</div> <%--ficha --%>
</div> <%--contenedor_ficha --%>
