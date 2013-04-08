<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-html-el.tld" prefix="html-el" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>

<bean:struts id="mappingGestionUsuarios" mapping="/gestionUsuariosOrganizacionAction" />

<c:set var="listaUsuarios" value="${requestScope[appConstants.organizacion.LISTA_USUARIOS_KEY]}" />

<html:form action="/gestionUsuariosOrganizacionAction">
<input type="hidden" name="method" value="buscar">

<tiles:insert template="/pages/tiles/PABoxLayout.jsp" flush="false">
	<tiles:put name="boxTitle" direct="true">
		<bean:message key="organizacion.user.buscar"/>
	</tiles:put>
	
	<tiles:put name="buttonBar" direct="true">
		<table cellspacing=0 cellpadding=0>
			<tr>
				<td nowrap>
					<script>
						function buscar() {
							var form = document.forms['<c:out value="${mappingGestionUsuarios.name}" />'];
							form.submit();
						}
					</script>
					<a class="etiquetaAzul12Bold" href="javascript:buscar()" >
					<html:img page="/pages/images/buscar.gif" altKey="organizacion.buscar" titleKey="organizacion.buscar" styleClass="imgTextMiddle" />
					<bean:message key="organizacion.buscar"/></a>
				</td>
				<td nowrap width="15px">&nbsp;</td>
				<td nowrap>
					<tiles:insert definition="button.closeButton" flush="false"/>
				</td>
			</tr>
		</table>
	</tiles:put>

	<tiles:put name="boxContent" direct="true">

		<div class="bloque">
				<table class="formulario">
					<tr>
						<td class="tdTitulo" width="160px">
							<bean:message key="organizacion.user.sistExt"/>:&nbsp;
						</td>
						<td class="tdDatos">
							<html:select property="tipoSistema">															
								<html-el:optionsCollection name="LISTA_SISTEMAS_EXTERNOS" label="nombre" value="id"/>								
							</html:select>
						</td>
					</tr>
					<tr>
					<td class="tdTitulo"><bean:message key="organizacion.user.nombre"/>:</td>
					<td class="tdDatos">
						<html:text property="searchTokenNombre" styleClass="input" size="50" />
					</td>
					</tr>
				</table>
		</div>

		<div class="separador8">&nbsp;</div>

		<c:if test="${not empty listaUsuarios}">
			<tiles:insert template="/pages/tiles/PABlockLayout.jsp" flush="false">
				<tiles:put name="blockTitle" direct="true"><bean:message key="organizacion.resultadosBusqueda"/></tiles:put>
				<tiles:put name="blockContent" direct="true">
	
				<c:url var="paginationURL" value="/action/gestionUsuariosOrganizacionAction" />
				<jsp:useBean id="paginationURL" type="java.lang.String" />
	
				<display:table name="pageScope.listaUsuarios" 
						id="usuario" 
						style="width:98%;margin-left:auto;margin-right:auto"
						sort="list"
						requestURI="<%=paginationURL%>"
						pagesize="10">
						
					<display:setProperty name="basic.msg.empty_list">
						<bean:message key="organizacion.user.busquedausuariosinresultado"/>
					</display:setProperty>
	
					<display:column titleKey="organizacion.user.usuario" sortProperty="nombreUsuario" sortable="true" headerClass="sortable">
						<c:url var="verURL" value="/action/gestionUsuariosOrganizacionAction">
							<c:param name="method" value="verUsuario" />
							<c:param name="idUsuarioSeleccionado" value="${usuario.idUsuario}" />
						</c:url>
						<a class="tdlink" href="<c:out value="${verURL}" escapeXml="false"/>" target="_self">
							<c:out value="${usuario.nombreUsuario}" />
						</a>
					</display:column>
					<display:column titleKey="organizacion.user.sistExt" sortable="true" headerClass="sortable">
						<c:out value="${usuario.sistemaExt}"/>
					</display:column>
	
					<display:column titleKey="organizacion.user.organo" sortable="true" headerClass="sortable">
						<c:out value="${usuario.nombreOrgano}" />
					</display:column>
				</display:table>
				</tiles:put>
			</tiles:insert>
		</c:if>

	</tiles:put>
</tiles:insert>
</html:form>