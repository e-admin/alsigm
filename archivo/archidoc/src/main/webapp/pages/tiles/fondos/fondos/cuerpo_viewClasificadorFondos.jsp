<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/taglibs/securityTag.tld" prefix="security"%>

<c:set var="clasificadorFondos" value="${sessionScope[appConstants.fondos.ELEMENTO_CF_KEY]}" />
<c:set var="listaAcceso" value="${sessionScope[appConstants.fondos.LISTAS_CONTROL_ACCESO_KEY]}" />
<c:set var="jerarquia" value="${sessionScope[appConstants.fondos.JERARQUIA_ELEMENTO_CUADRO_CLASIFICACION]}"/>

<div id="contenedor_ficha">

<div class="ficha">

<h1><span>
	<div class="w100">
	<TABLE class="w98" cellpadding=0 cellspacing=0>
	<TR>
		<TD class="etiquetaAzul12Bold" height="25px">
			&nbsp;<bean:message key="archigest.archivo.cf.clasificadorDe"/>
			<bean:message key="archigest.archivo.cf.fondo"/>
		</TD>
		<TD align="right">
		<TABLE cellpadding=0 cellspacing=0>
			<TR>
			<c:set var="treeView" value="${sessionScope[appConstants.fondos.CUADRO_CLF_VIEW_NAME]}" />
			<c:set var="parentNode" value="${treeView.selectedNode.parent}" />
			<c:if test="${!empty parentNode}">
					<c:url var="viewElementURL" value="/action/manageVistaCuadroClasificacion">
						<c:param name="actionToPerform" value="verNodo" />
						<c:param name="node" value="${parentNode.nodePath}" />
						<c:param name="refreshView" value="true" />
					</c:url>
					<TD nowrap>
						<a class="etiquetaAzul12Bold" href="<c:out value="${viewElementURL}" escapeXml="false"/>" >
							<html:img page="/pages/images/treePadre.gif" altKey="archigest.archivo.cf.verPadre" titleKey="archigest.archivo.cf.verPadre" styleClass="imgTextMiddle" />
							<bean:message key="archigest.archivo.cf.verPadre"/>
						</a>
					</TD>
					<TD width="10">&nbsp;</TD>
			</c:if>

				<security:permissions action="${appConstants.fondosActions.ELIMINACION_ELEMENTO_ACTION}">
					<c:url var="eliminarClasificadorFondosURL" value="/action/gestionClasificadorFondosAction">
						<c:param name="method" value="eliminarClasificadorFondos" />
						<c:param name="idelementocf" value="${clasificadorFondos.id}" />
					</c:url>
					<TD>
						<script>
							function eliminar() {
								if (confirm("<bean:message key='archigest.archivo.cf.eliminarClasificadorFondos'/>"))
									window.location='<c:out value="${eliminarClasificadorFondosURL}" escapeXml="false"/>'
							}
						</script>
						<a class="etiquetaAzul12Bold" href="javascript:eliminar();">
							<html:img page="/pages/images/delete.gif" altKey="archigest.archivo.eliminar" titleKey="archigest.archivo.eliminar" styleClass="imgTextMiddle" />
							&nbsp;<bean:message key="archigest.archivo.eliminar"/>
						</a>
					</TD>
					<TD width="10">&nbsp;</TD>
				</security:permissions>

			<c:url var="cambiarEstadoClasificadorFondosURL" value="/action/gestionClasificadorFondosAction">
				<c:param name="method" value="cambiarEstadoVigenciaClasificadorFondos" />
				<c:param name="idelementocf" value="${clasificadorFondos.id}" />
			</c:url>

			<security:permissions action="${appConstants.fondosActions.MODIFICAR_ELEMENTO_ACTION}">
				<c:if test="${!clasificadorFondos.vigente}">
					<TD>
						<a class="etiquetaAzul12Bold" href="<c:out value="${cambiarEstadoClasificadorFondosURL}" escapeXml="false"/>">
							<html:img page="/pages/images/vigente.gif" altKey="archigest.archivo.cf.vigente" titleKey="archigest.archivo.cf.vigente" styleClass="imgTextMiddle" />
							<bean:message key="archigest.archivo.cf.vigente"/>
						</a>
					</TD>
				</c:if>
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
				<security:permissions action="${appConstants.fondosActions.MODIFICAR_ELEMENTO_ACTION}">
					<TD>
						<c:url var="edicionClasificadorFondosURL" value="/action/gestionClasificadorFondosAction">
							<c:param name="method" value="editarClasificadorFondos" />
							<c:param name="idClfFondos" value="${clasificadorFondos.id}" />
						</c:url>
						<a class="etiquetaAzul12Bold" href="<c:out value="${edicionClasificadorFondosURL}" escapeXml="false"/>">
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

				<tiles:insert definition="fondos.cf.infoClasificadorFondo" />
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
							<c:url var="edicionClasificadorFondoURL" value="/action/gestionClasificadorFondosAction">
								<c:param name="method" value="editarInfoClasificadorFondo" />
								<c:param name="idClasificadorFondo" value="${clasificadorFondos.id}" />
							</c:url>
							<a class="etiquetaAzul12Bold" href="<c:out value="${edicionClasificadorFondoURL}" escapeXml="false"/>" >
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
							<c:out value="${clasificadorFondos.id}"/>
						</td>
					</tr>
					<tr>
						<td class="tdTitulo" width="250px">
							<bean:message key="archigest.archivo.nivelAcceso"/>:&nbsp;
						</td>
						<td class="tdDatos">
							<c:choose>
								<c:when test="${clasificadorFondos.nivelAcceso == '1'}">
									<bean:message key="archivo.nivel_acceso.publico"/>
								</c:when>
								<c:when test="${clasificadorFondos.nivelAcceso == '2'}">
									<bean:message key="archivo.nivel_acceso.archivo"/>
								</c:when>
								<c:when test="${clasificadorFondos.nivelAcceso == '3'}">
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
					<html:img page="/pages/images/pixel.gif" styleClass="imgTextMiddle" height="18"/>
				</TD>
				<jsp:include page="../accionesElementoCF.jsp" flush="true" />
			  </TR>
			</TABLE>
			</TD>
		   </TR>
		</TABLE>
	</DIV>

	<div class="bloque"> <%--primer bloque de datos --%>

		<div class="separador5">&nbsp;</div>
		<c:url var="viewClasificadorFondosPaginationURL" value="/action/gestionFondoAction">
			<c:param name="method" value="verClasificadorFondos" />
			<c:param name="idelementocf" value="${clasificadorFondos.id}" />
		</c:url>
		<jsp:useBean id="viewClasificadorFondosPaginationURL" type="java.lang.String" />

		<c:set var="descendientes" value="${requestScope[appConstants.fondos.LISTA_ELEMENTOS_CF]}" />

		<display:table
			name="pageScope.descendientes"
			id="elementoCF"
			style="width:99%;margin-left:auto;margin-right:auto"
			requestURI="<%=viewClasificadorFondosPaginationURL%>">

			<display:setProperty name="basic.msg.empty_list">
				&nbsp;<bean:message key="archigest.archivo.cf.noDescendientes"/>
			</display:setProperty>

			<display:column titleKey="archigest.archivo.cf.codigo" sortable="true">
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
			</display:column>

			<display:column titleKey="archigest.archivo.cf.denominacion" sortable="true" >
				<a href="<c:out value="${viewElementURL}" escapeXml="false"/>" class="tdlink">
					<c:out value="${elementoCF.titulo}" />
				</a>
			</display:column>

		</display:table>
		<div class="separador5">&nbsp;</div>

	</div> <%--bloque --%>

</div> <%--cuerpo_drcha --%>
</div> <%--cuerpo_izda --%>

<h2><span>&nbsp;
</span></h2>

</div> <%--ficha --%>
</div> <%--contenedor_ficha --%>