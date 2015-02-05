<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/archigest-adds.tld" prefix="pa" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/taglibs/securityTag.tld" prefix="security"%>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>

<bean:struts id="mappingGestionRoles" mapping="/gestionRoles" />
<script>
	function filtrarListado() {
		var form = document.forms['<c:out value="${mappingGestionRoles.name}" />'];
		form.method.value = 'listaRoles';
		form.submit();
	}
	function eliminarRoles() {
		var form = document.forms['<c:out value="${mappingGestionRoles.name}" />'];
		if (form.rolesSeleccionados && elementSelected(form.rolesSeleccionados)) {
			if (confirm("<bean:message key='archigest.archivo.cacceso.msgConfirmPerfilesEliminar'/>")) {
				form.method.value = 'eliminarRoles';
				form.submit();
			}
		} else
			alert("<bean:message key='archigest.archivo.cacceso.msgNoPerfilesEliminar'/>");
	}
</script>
<html:form action="/gestionRoles">
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
			<select name="modulo" onchange="filtrarListado()">
				<option value=""> -- </option>
				<c:forEach var="modulo" items="${listaModulos}">
					<option value="<c:out value="${modulo.key}"/>"
						<c:if test="${modulo.key==param.modulo}">selected="true"</c:if>>
						<fmt:message key="${modulo.value}"/>
					</option>
				</c:forEach>
			</select>
		</td>
		<security:permissions action="${appConstants.controlAccesoActions.ALTA_ROL}">
			<td width="10px"></td>
			<td nowrap>
				<c:url var="createURL" value="/action/gestionRoles">
					<c:param name="method" value="altaRol" />
				</c:url>
				<a class="etiquetaAzul12Bold" href="<c:out value="${createURL}" escapeXml="false"/>" >
				<html:img page="/pages/images/newDoc.gif" altKey="archigest.archivo.crear" titleKey="archigest.archivo.crear" styleClass="imgTextMiddle" />
				<bean:message key="archigest.archivo.crear"/></a>
			</td>
		</security:permissions>

		<security:permissions action="${appConstants.controlAccesoActions.ELIMINAR_ROL}">
		<td width="10px"></td>
		<td nowrap>
			<a class="etiquetaAzul12Bold" href="javascript:eliminarRoles()" >
			<html:img page="/pages/images/deleteDoc.gif" altKey="archigest.archivo.eliminar" titleKey="archigest.archivo.eliminar" styleClass="imgTextMiddle" />
			<bean:message key="archigest.archivo.eliminar"/></a>
		</td>
		</security:permissions>
		<c:if test="${appConstants.configConstants.mostrarAyuda}">
			<td width="10">&nbsp;</td>
			<td>
				<c:set var="requestURI" value="${pageContext.request.scheme}://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}" />
				<c:set var="carpetaIdioma" value="${sessionScope[appConstants.commonConstants.LOCALEKEY]}" />
				<a class="etiquetaAzul12Bold" href="javascript:popupHelp('<c:out value="${requestURI}" escapeXml="false"/>/help/<c:out value="${carpetaIdioma}"/>/controlAcceso/GestionPerfiles.htm');">
				<html:img page="/pages/images/help.gif" 
				        altKey="archigest.archivo.ayuda" 
				        titleKey="archigest.archivo.ayuda" 
				        styleClass="imgTextMiddle"/>
				&nbsp;<bean:message key="archigest.archivo.ayuda"/></a>
			</td>
		</c:if>
		<td width="10px"></td>
		<td nowrap>
			<tiles:insert definition="button.closeButton" />
		</td>
		</tr></table>
	</tiles:put>

	<tiles:put name="boxContent" direct="true">
		<input type="hidden" name="method">
		<div class="bloque">
		<c:set var="listaRoles" value="${requestScope[appConstants.controlAcceso.LISTA_ROLES]}" />

		<c:url var="listaRolesPaginationURI" value="/action/gestionRoles">
			<c:param name="method" value="${param.method}" />
		</c:url>
		<jsp:useBean id="listaRolesPaginationURI" type="java.lang.String" />

		<display:table name="pageScope.listaRoles" 
				id="rol" 
				style="width:98%;margin-left:auto;margin-right:auto"
				sort="list"
				requestURI="<%=listaRolesPaginationURI%>"	
				pagesize="10">
			<display:setProperty name="basic.msg.empty_list">
				<bean:message key="archigest.archivo.cacceso.msgNoRolesModulo"/>
			</display:setProperty>

			<security:permissions action="${appConstants.controlAccesoActions.ELIMINAR_ROL}">
				<display:column style="width:10px" title="">
					<input type="checkbox" name="rolesSeleccionados" value="<c:out value="${rol.id}" />" >
				</display:column>
			</security:permissions>			

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
				<fmt:message key="nombreModulo.modulo${rol.tipoModulo}" />
			</display:column>
			<display:column titleKey="archigest.archivo.descripcion" property="descripcion" maxLength="150"/>
		</display:table>
		</div>

	</tiles:put>
</tiles:insert>
</html:form>