<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/archigest-adds.tld" prefix="pa" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/taglibs/securityTag.tld" prefix="security"%>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>

<bean:struts id="mappingGestionUsuarios" mapping="/gestionUsuarios" />

<script>
	function eliminarUsuarios() {
		var form = document.forms['<c:out value="${mappingGestionUsuarios.name}" />'];
		if (form.usuariosABorrar && elementSelected(form.usuariosABorrar)) {
			if (confirm("<bean:message key='archigest.archivo.cacceso.msgConfirmUsuariosEliminar'/>")) {
				form.method.value = 'eliminarUsuarios';
				form.submit();
			}
		} else
			alert("<bean:message key='archigest.archivo.cacceso.msgNoUsuariosEliminar'/>");
	}
</script>
<html:form action="/gestionUsuarios">
<input type="hidden" name="method" value="eliminarUsuario"/>
<tiles:insert template="/pages/tiles/PABoxLayout.jsp">
	<tiles:put name="boxTitle" direct="true">
		<bean:message key="archigest.archivo.cacceso.gestionUsuarios"/>
	</tiles:put>

	<tiles:put name="buttonBar" direct="true">
	</script>
		<table>
		<tr>
		<security:permissions action="${appConstants.controlAccesoActions.ALTA_USUARIO}">
			<td width="10px"></td>
			<td nowrap>
				<c:url var="createURL" value="/action/gestionUsuarios">
					<c:param name="method" value="nuevoUsuario" />
				</c:url>
				<a class="etiquetaAzul12Bold" href="<c:out value="${createURL}" escapeXml="false"/>" >
				<html:img page="/pages/images/newDoc.gif" altKey="archigest.archivo.crear" titleKey="archigest.archivo.crear" styleClass="imgTextMiddle" />
				<bean:message key="archigest.archivo.crear"/></a>
			</td>
		</security:permissions>
		<security:permissions action="${appConstants.controlAccesoActions.ELIMINAR_USUARIO}">
			<td width="10px"></td>
			<td nowrap>
				<a class="etiquetaAzul12Bold" href="javascript:eliminarUsuarios()" >
				<html:img page="/pages/images/deleteDoc.gif" altKey="archigest.archivo.eliminar" titleKey="archigest.archivo.eliminar" styleClass="imgTextMiddle" />
				<bean:message key="archigest.archivo.eliminar"/></a>
			</td>
		</security:permissions>
		<td width="10px"></td>
			<c:url var="busquedaUsuariosURL" value="/action/gestionUsuarios">
				<c:param name="method" value="verBuscador" />
			</c:url>
			<td nowrap>
				<a class="etiquetaAzul12Bold" href="<c:out value="${busquedaUsuariosURL}" escapeXml="false"/>" >
				<html:img page="/pages/images/buscar_go.gif" altKey="archigest.archivo.buscar" titleKey="archigest.archivo.buscar" styleClass="imgTextMiddle" />
				<bean:message key="archigest.archivo.buscar"/></a>
			</td>
		<c:if test="${appConstants.configConstants.mostrarAyuda}">
			<td width="10">&nbsp;</td>
			<td>
				<c:set var="requestURI" value="${pageContext.request.scheme}://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}" />
				<c:set var="carpetaIdioma" value="${sessionScope[appConstants.commonConstants.LOCALEKEY]}" />
				<a class="etiquetaAzul12Bold" href="javascript:popupHelp('<c:out value="${requestURI}" escapeXml="false"/>/help/<c:out value="${carpetaIdioma}"/>/controlAcceso/GestionUsuarios.htm');">
				<html:img page="/pages/images/help.gif"
				        altKey="archigest.archivo.ayuda"
				        titleKey="archigest.archivo.ayuda"
				        styleClass="imgTextMiddle"/>
				&nbsp;<bean:message key="archigest.archivo.ayuda"/></a>
			</td>
		</c:if>
		<td width="10px"></td>
		<td nowrap>
			<tiles:insert definition="button.closeButton" flush="true"/>
		</td>
		</tr></table>
	</tiles:put>

	<c:url var="paginationURL" value="/action/gestionUsuarios" >
		<c:param name="method" value="listadoUsuarios"/>
	</c:url>
	<jsp:useBean id="paginationURL" type="java.lang.String" />
	<tiles:put name="boxContent" direct="true">
		<div class="bloque">
			<c:set var="listaUsuarios" value="${sessionScope[appConstants.controlAcceso.LISTA_USUARIOS]}" />
			<display:table name="pageScope.listaUsuarios"
					id="usuario"
					style="width:98%;margin-left:auto;margin-right:auto"
					sort="list"
					requestURI='<%=paginationURL%>'
					excludedParams="*"
					pagesize="15">
				<display:column style="width:10px" title="">
					<input type="checkbox" name="usuariosABorrar" value="<c:out value="${usuario.id}" />" >
				</display:column>
				<display:column titleKey="archigest.archivo.usuario" sortProperty="usuario" sortable="true" headerClass="sortable">
					<c:url var="verURL" value="/action/gestionUsuarios">
						<c:param name="method" value="verUsuario" />
						<c:param name="idUsuarioSeleccionado" value="${usuario.id}" />
					</c:url>
					<a class="tdlink" href="<c:out value="${verURL}" escapeXml="false"/>" >
						<c:out value="${usuario.nombreCompleto}" />
					</a>
				</display:column>
				<display:column titleKey="archigest.archivo.tipoUsuario" sortProperty="tipo" sortable="true" headerClass="sortable">
					<fmt:message key="archigest.archivo.cacceso.tipoUsuario.${usuario.tipo}"/>
				</display:column>
				<display:column titleKey="archigest.archivo.habilitado" sortProperty="habilitado" sortable="true" headerClass="sortable">
					<c:choose>
						<c:when test="${usuario.habilitado=='S'}">
							<bean:message key="archigest.archivo.si"/>
						</c:when>
						<c:otherwise>
							<bean:message key="archigest.archivo.no"/>
						</c:otherwise>
					</c:choose>
				</display:column>
				<display:column titleKey="archigest.archivo.organo">
					<c:out value="${usuario.nombreLargoOrgano}" />

					  &nbsp;<c:if test="${not empty usuario.vigenciaOrgano && not usuario.vigenciaOrgano}">
				      <html:img page="/pages/images/checkbox-no.gif"
				        altKey="archigest.archivo.organo.pasar.a.no.vigente"
  				        titleKey="archigest.archivo.organo.pasar.a.no.vigente"
				        styleClass="imgTextTop"/>
					</c:if>
				</display:column>
			</display:table>
		</div>
	</tiles:put>
</tiles:insert>
</html:form>