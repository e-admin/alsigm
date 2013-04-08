<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/archigest-adds.tld" prefix="pa" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>

<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>

<bean:struts id="mappingGestionUsuarios" mapping="/gestionUsuarios" />
<c:set var="formulario" value="${sessionScope[mappingGestionUsuarios.name]}"/>
<script>
	function filtrarListado() {
		var form = document.forms['<c:out value="${mappingGestionUsuarios.name}" />'];
		form.method.value = 'listadoRolesParaAnadir';
		form.submit();
	}
	function aceptarRoles() {
		var form = document.forms['<c:out value="${mappingGestionUsuarios.name}" />'];
		form.method.value = 'anadirRoleAUsuario';
		form.submit();
	}
</script>
<html:form action="/gestionUsuarios">
<tiles:insert template="/pages/tiles/PABoxLayout.jsp">
	<tiles:put name="boxTitle" direct="true">
		<bean:message key="archigest.archivo.cacceso.listaPerfiles"/>
	</tiles:put>

	<tiles:put name="buttonBar" direct="true">
		<table><tr>
		<td nowrap class="etiquetaAzul12Bold">
			<bean:message key="archigest.archivo.modulo"/>:&nbsp;
		</td>
		<td nowrap>
			<c:set var="listaModulos" value="${requestScope[appConstants.controlAcceso.LISTA_MODULOS]}" />
			<html:select property="idModuloSeleccionado" onchange="filtrarListado()">
				<option> -- </option>
				<c:forEach var="rol" items="${listaModulos}">
					<c:set var="valueOption" value="${rol.key}"/>
					<jsp:useBean id="valueOption" type="java.lang.Integer"/>

					<c:choose>
						<c:when test="${formulario.idModuloSeleccionado!='--' && formulario.idModuloSeleccionado==valueOption}">
							<option value="<%=valueOption%>" selected><fmt:message key="${rol.value}" /></option>
						</c:when>
						<c:otherwise>
							<option value="<%=valueOption%>"><fmt:message key="${rol.value}" /></option>
						</c:otherwise>
					</c:choose>
				</c:forEach>
			</html:select>
		</td>
		<td width="10px"></td>
		<td nowrap>
			<a class="etiquetaAzul12Bold" href="javascript:aceptarRoles()" >
			<html:img page="/pages/images/Ok_Si.gif" altKey="archigest.archivo.aceptar" titleKey="archigest.archivo.aceptar" styleClass="imgTextMiddle" />
			<bean:message key="archigest.archivo.aceptar"/></a>
		</td>
		<td width="10px"></td>
		<td nowrap>
			<tiles:insert definition="button.closeButton" flush="true">
				<tiles:put name="labelKey" direct="true">archigest.archivo.cancelar</tiles:put>
				<tiles:put name="imgIcon" direct="true">/pages/images/Ok_No.gif</tiles:put>
			</tiles:insert>
		</td>
		</tr></table>
	</tiles:put>

	<tiles:put name="boxContent" direct="true">
		<input type="hidden" name="method">

		<tiles:insert page="infoUsuario.jsp"/>

		<div class="bloque">
		<c:url var="listaRolesPaginationURI" value="/action/gestionUsuarios">
			<c:param name="method" value="${param.method}" />
		</c:url>
		<jsp:useBean id="listaRolesPaginationURI" type="java.lang.String" />
		<c:set var="listaRoles" value="${sessionScope[appConstants.controlAcceso.LISTA_ROLES]}" />
		<display:table name="pageScope.listaRoles"
				id="rol"
				style="width:98%;margin-top:1%;margin-bottom:1%;margin-left:auto;margin-right:auto"
				sort="list"
			    requestURI="<%=listaRolesPaginationURI%>"
				pagesize="0">
			<display:setProperty name="basic.msg.empty_list">
				<bean:message key="archigest.archivo.cacceso.msgNoRolesModulo"/>
			</display:setProperty>

			<display:column style="width:10px" title="">
					<input type="checkbox" name="rolesSeleccionados" value="<c:out value="${rol.id}" />" >
			</display:column>
			<display:column titleKey="archigest.archivo.cacceso.perfil" sortProperty="nombre" sortable="true" headerClass="sortable">
				<c:url var="verURL" value="/action/gestionRoles">
					<c:param name="method" value="verRol" />
					<c:param name="idRol" value="${rol.id}" />
				</c:url>
				<a class="tdlink" href="<c:out value="${verURL}" escapeXml="false"/>" >
				<c:out value="${rol.nombre}" />
				</a>
			</display:column>
			<display:column titleKey="archigest.archivo.modulo" sortable="true" headerClass="sortable">
				<fmt:message key="nombreModulo.modulo${rol.tipoModulo}"/>
			</display:column>
			<display:column titleKey="archigest.archivo.descripcion" property="descripcion" maxLength="150"/>
		</display:table>
		</div>

	</tiles:put>
</tiles:insert>
</html:form>

<script>removeCookie('tabsRol');</script>