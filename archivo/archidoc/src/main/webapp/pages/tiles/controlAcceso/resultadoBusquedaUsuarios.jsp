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

<c:set var="listaUsuarios" value="${requestScope[appConstants.controlAcceso.LISTA_USUARIOS]}" />

<html:form action="/gestionUsuarios">
<input type="hidden" name="method" value="buscar">

<tiles:insert template="/pages/tiles/PABoxLayout.jsp">
	<tiles:put name="boxTitle" direct="true">
		<bean:message key="archigest.archivo.cacceso.busquedaUsuarios"/>
	</tiles:put>
	<tiles:put name="buttonBar" direct="true">
	<table cellspacing=0 cellpadding=0><tr>
		<td nowrap>
			<script>
				function buscar() {
					var form = document.forms['<c:out value="${mappingGestionUsuarios.name}" />'];
					form.submit();
				}
			</script>
			<a class="etiquetaAzul12Bold" href="javascript:buscar()" >
			<html:img page="/pages/images/buscar.gif" altKey="archigest.archivo.buscar" titleKey="archigest.archivo.buscar" styleClass="imgTextMiddle" />
			<bean:message key="archigest.archivo.buscar"/></a>
		</td>
		<td nowrap width="15px">&nbsp;</td>
		<td nowrap>
			<tiles:insert definition="button.closeButton" flush="true"/>
		</td>
	</tr></table>
	</tiles:put>

	<tiles:put name="boxContent" direct="true">

		<div class="bloque">
				<table class="formulario">
					<tr>
						<td class="tdTitulo" width="140px"><bean:message key="archigest.archivo.tipoUsuario"/>:</td>
						<td class="tdDatos">
							<c:set var="tiposUsuarios" value="${sessionScope[appConstants.controlAcceso.LISTA_TIPOS_USUARIOS]}" />
							<select name="tipoUsuario">
								<option value="">--</option>
								<c:forEach var="tipoUsuario" items="${tiposUsuarios}">
									<option value="<c:out value="${tipoUsuario.tipo}"/>"
										<c:if test="${tipoUsuario.tipo==param.tipoUsuario}">selected="true"</c:if>>
										<fmt:message key="archigest.archivo.cacceso.tipoUsuario.${tipoUsuario.tipo}"/>
									</option>
								</c:forEach>
							</select>
						</td>
					</tr>
					<tr>
					<td class="tdTitulo"><bean:message key="archigest.archivo.nombre"/>:</td>
					<td class="tdDatos">
						<html:text property="searchTokenNombre" styleClass="input" size="50" />
					</td>
					</tr>
					<tr>
					<td class="tdTitulo"><bean:message key="archigest.archivo.apellidos"/>:</td>
					<td class="tdDatos">
						<html:text property="searchTokenApellidos" styleClass="input99"/>
					</td>
					</tr>
				</table>
		</div>

		<div class="separador8">&nbsp;</div>

		<c:if test="${listaUsuarios != null}">
		<tiles:insert template="/pages/tiles/PABlockLayout.jsp">
			<tiles:put name="blockTitle" direct="true"><bean:message key="archigest.archivo.resultadosBusqueda"/></tiles:put>
			<tiles:put name="blockContent" direct="true">

			<c:url var="paginationURL" value="/action/gestionUsuarios" />
			<jsp:useBean id="paginationURL" type="java.lang.String" />

			<display:table name="pageScope.listaUsuarios" 
					id="usuario" 
					style="width:98%;margin-left:auto;margin-right:auto"
					sort="list"
					requestURI="<%=paginationURL%>"
					pagesize="10">
				<display:setProperty name="basic.msg.empty_list">
					<bean:message key="archigest.archivo.cacceso.busquedaUsuarioSinResultado"/>
				</display:setProperty>

				<display:column titleKey="archigest.archivo.usuario" sortProperty="nombreCompleto" sortable="true" headerClass="sortable">
					<c:url var="verURL" value="/action/gestionUsuarios">
						<c:param name="method" value="verUsuario" />
						<c:param name="idUsuarioSeleccionado" value="${usuario.id}" />
					</c:url>
					<a class="tdlink" href="<c:out value="${verURL}" escapeXml="false"/>" >
					<c:out value="${usuario.nombreCompleto}" />
					</a>
				</display:column>
				<display:column titleKey="archigest.archivo.tipo" sortable="true" headerClass="sortable">
					<fmt:message key="archigest.archivo.cacceso.tipoUsuario.${usuario.tipo}"/>
				</display:column>
				<display:column titleKey="archigest.archivo.habilitado" sortable="true" headerClass="sortable">
					<c:choose>
						<c:when test="${usuario.habilitado=='S'}">
							<bean:message key="archigest.archivo.si"/>
						</c:when>
						<c:otherwise>
							<bean:message key="archigest.archivo.no"/>
						</c:otherwise>
					</c:choose>
				</display:column>
				<display:column titleKey="archigest.archivo.organo" sortable="true" headerClass="sortable">
					<c:out value="${usuario.organoEnArchivo.nombreLargo}" />
				</display:column>
			</display:table>
			</tiles:put>
		</tiles:insert>
		</c:if>

	</tiles:put>
</tiles:insert>
</html:form>