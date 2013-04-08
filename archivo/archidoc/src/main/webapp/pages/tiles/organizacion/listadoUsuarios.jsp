<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>

<bean:struts id="mappingGestionUsuarios" mapping="/gestionUsuariosOrganizacionAction" />
<c:set var="listaUsuarios" value="${sessionScope[appConstants.organizacion.LISTA_USUARIOS_KEY]}" />

<script>
	function eliminarUsuarios() {
		var form = document.forms['<c:out value="${mappingGestionUsuarios.name}" />'];
		if (form.usuariosABorrar && elementSelected(form.usuariosABorrar)) {
			if (confirm("<bean:message key='organizacion.user.msgConfirmUsuariosEliminar'/>")) {
				form.method.value = 'eliminarUsuarios';
				form.submit();
			}
		} else
			alert("<bean:message key='organizacion.user.msgNoUsuariosEliminar'/>");
	}
</script>
<html:form action="/gestionUsuariosOrganizacionAction">
<input type="hidden" name="method" value="eliminarUsuario"/>
<tiles:insert template="/pages/tiles/PABoxLayout.jsp" flush="false">
	<tiles:put name="boxTitle" direct="true">
		<bean:message key="organizacion.user.gestionusuarios"/>
	</tiles:put>

	<tiles:put name="buttonBar" direct="true">
		<table>
		<tr>
			<td width="10px"></td>
			<td nowrap>
				<c:url var="createURL" value="/action/gestionUsuariosOrganizacionAction">
					<c:param name="method" value="nuevoUsuario" />
				</c:url>
				<a class="etiquetaAzul12Bold" href="<c:out value="${createURL}" escapeXml="false"/>" >
				<html:img page="/pages/images/newDoc.gif" altKey="organizacion.crear" titleKey="organizacion.crear" styleClass="imgTextMiddle" />
				<bean:message key="organizacion.crear"/></a>
			</td>
			<c:if test="${!empty listaUsuarios}">
				<td width="10px"></td>
				<td nowrap>
					<a class="etiquetaAzul12Bold" href="javascript:eliminarUsuarios()" >
					<html:img page="/pages/images/deleteDoc.gif" altKey="organizacion.eliminar" titleKey="organizacion.eliminar" styleClass="imgTextMiddle" />
					<bean:message key="organizacion.eliminar"/></a>
				</td>
				<td width="10px"></td>
				<c:url var="busquedaUsuariosURL" value="/action/gestionUsuariosOrganizacionAction">
					<c:param name="method" value="verBuscador" />
				</c:url>
				<td nowrap>
					<a class="etiquetaAzul12Bold" href="<c:out value="${busquedaUsuariosURL}" escapeXml="false"/>" >
					<html:img page="/pages/images/buscar_go.gif" altKey="organizacion.buscar" titleKey="organizacion.buscar" styleClass="imgTextMiddle" />
					<bean:message key="organizacion.buscar"/></a>
				</td>
			</c:if>
			<td width="10px"></td>
			<td nowrap>
				<tiles:insert definition="button.closeButton" flush="false"/>
			</td>
		</tr>
		</table>
	</tiles:put>

	<c:url var="paginationURL" value="/action/gestionUsuariosOrganizacionAction" >
		<c:param name="method" value="listadoUsuarios"/>
	</c:url>
	<jsp:useBean id="paginationURL" type="java.lang.String" />
	
	<tiles:put name="boxContent" direct="true">
		<div class="bloque">			
			<display:table name="pageScope.listaUsuarios"
					id="usuario"
					style="width:98%;margin-left:auto;margin-right:auto"
					sort="list"
					requestURI='<%=paginationURL%>'
					excludedParams="*"
					pagesize="15">
				<display:column style="width:10px" title="">
					<input type="checkbox" name="usuariosABorrar" value="<c:out value="${usuario.idUsuario}" />" >
				</display:column>
				<display:column titleKey="organizacion.user.usuario" sortProperty="usuario" sortable="true" headerClass="sortable">
					<c:url var="verURL" value="/action/gestionUsuariosOrganizacionAction">
						<c:param name="method" value="verUsuario" />
						<c:param name="idUsuarioSeleccionado" value="${usuario.idUsuario}" />
					</c:url>
					<a class="tdlink" href="<c:out value="${verURL}" escapeXml="false"/>" target="_self" >
						<c:out value="${usuario.nombreUsuario}" />
					</a>
				</display:column>
				<display:column titleKey="organizacion.user.organo">
					<c:out value="${usuario.nombreOrgano}" />
				</display:column>
			</display:table>
		</div>
	</tiles:put>
</tiles:insert>
</html:form>