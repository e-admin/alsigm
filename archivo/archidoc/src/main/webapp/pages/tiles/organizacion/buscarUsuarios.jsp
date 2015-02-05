<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-html-el.tld" prefix="html-el" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>

<bean:struts id="mappingGestionUsuarios" mapping="/gestionUsuariosOrganizacionAction" />
<c:set var="busqueda" value="${usuarioOrganoForm.origen=='true'}" />

<html:form action="/gestionUsuariosOrganizacionAction">
	<html:hidden property="idOrgano"/>
	<html:hidden property="origen"/>

	<tiles:insert template="/pages/tiles/PABoxLayout.jsp" flush="false">

		<tiles:put name="buttonBar" direct="true">
			<script>
				function buscarUsuario() {
					var form = document.forms['<c:out value="${mappingGestionUsuarios.name}" />'];
					form.method.value = 'realizarBusquedaUsuarios';
					form.submit();
				}
				function anadirUsuario() {
					var form = document.forms['<c:out value="${mappingGestionUsuarios.name}" />'];
					form.method.value = 'agregarUsuariosAOrgano';
					form.submit();
				}
				function seleccionarUsuario() {
					var form = document.forms['<c:out value="${mappingGestionUsuarios.name}" />'];
					form.method.value = 'agregarUsuarioABusqueda';
					form.submit();
				}
			</script>

			<table>
			<tr>
				<c:if test="${!busqueda}">
					<td nowrap>
						<a class="etiquetaAzul12Bold" href="javascript:anadirUsuario()" >
							<html:img page="/pages/images/Ok_Si.gif" altKey="organizacion.aceptar" titleKey="organizacion.aceptar" styleClass="imgTextBottom" />
							&nbsp;<bean:message key="organizacion.aceptar"/>
						</a>
					</td>
				</c:if>
				<c:if test="${busqueda}">
					<td nowrap>
						<a class="etiquetaAzul12Bold" href="javascript:seleccionarUsuario()" >
							<html:img page="/pages/images/Ok_Si.gif" altKey="organizacion.aceptar" titleKey="organizacion.aceptar" styleClass="imgTextBottom" />
							&nbsp;<bean:message key="organizacion.aceptar"/>
						</a>
					</td>
				</c:if>
				<td width="10"></td>
				<td nowrap>
					<tiles:insert definition="button.closeButton" flush="false">
						<tiles:put name="labelKey" direct="true">organizacion.cancelar</tiles:put>
						<tiles:put name="imgIcon" direct="true">/pages/images/Ok_No.gif</tiles:put>
					</tiles:insert>
				</td>
			</tr>
			</table>
		</tiles:put>

		<tiles:put name="boxContent" direct="true">

			<tiles:insert page="/pages/tiles/PABlockLayout.jsp" flush="false">

				<tiles:put name="blockTitle" direct="true">
					<bean:message key="organizacion.buscarEn"/>
					<bean:message key="organizacion.sistExterno"/>:
				</tiles:put>

				<tiles:put name="buttonBar" direct="true">
					<table cellpadding=0 cellspacing=0>
						<tr>
							<td align="right">
								<a class="etiquetaAzul12Bold" href="javascript:buscarUsuario()">
									<html:img titleKey="organizacion.buscar" altKey="organizacion.buscar" page="/pages/images/buscar.gif" styleClass="imgTextMiddle" />
									<bean:message key="organizacion.buscar"/>&nbsp;
								</a>
							</td>
						</tr>
					</table>
				</tiles:put>

				<tiles:put name="blockContent" direct="true">
					<input type="hidden" name="method" value="realizarBusquedaUsuarios">
					<c:set var="listaUsuarios" value="${sessionScope[appConstants.organizacion.LISTA_USUARIOS_EXTERNOS]}" />
					<table  class="formulario" align="center" style="width:99%">
						<tr>
							<td class="tdTitulo" width="300px">
								<bean:message key="organizacion.user.sistExt"/>:&nbsp;
							</td>
							<td class="tdDatos">
								<html:select property="tipoSistema">
									<html-el:optionsCollection name="LISTA_SISTEMAS_EXTERNOS" label="nombre" value="id"/>
								</html:select>
							</td>
						</tr>
						<tr>
							<td class="tdTitulo">
								<bean:message key="organizacion.user.nombre"/>&nbsp;/
								<bean:message key="organizacion.user.apellidos"/>&nbsp;/
								<bean:message key="archigest.archivo.email"/>
								:
							</td>
							<td class="tdDatos">
								<html:text styleClass="input95" property="nombreUsuarioABuscar" />
							</td>
						</tr>

					</table>
				</tiles:put>
			</tiles:insert>

			<c:if test="${not empty listaUsuarios}">

				<div class="separador8"></div>
				<div class="cabecero_bloque">
					<table class="w98" cellpadding=0 cellspacing=0 height="100%">
						<tr>
							<td class="etiquetaAzul12Bold">
								<bean:message key="organizacion.resultadosBusqueda"/>
							</td>
						</tr>
					</table>
				</div>

				<div class="bloque" style="padding-top:8px;padding-bottom:8px">
					<table class="w98m1">
				    	<tr>
				    		<td class="etiquetaAzul12Bold">
				    			<c:if test="${!busqueda}">
									<bean:message key="organizacion.user.msgSelUserAlta"/>:
								</c:if>
								<c:if test="${busqueda}">
									<bean:message key="organizacion.user.msgSelUser"/>:
								</c:if>
							</td>
						</tr>
					</table>

					<display:table name="pageScope.listaUsuarios"
						id="usuario"
						style="width:98%;margin-left:auto;margin-right:auto"
						requestURI="../../action/gestionUsuariosOrganizacionAction?method=realizarBusquedaUsuarios"
						sort="list"
						pagesize="10">

						<display:setProperty name="basic.msg.empty_list">
							<bean:message key="errors.altaUsuario.noUsuariosSistExternoCriterioBusqueda"/>
						</display:setProperty>

						<c:if test="${!busqueda}">
							<display:column style="width:10px">
								<input type="checkbox" name="usuariosSeleccionados"
									value="<c:out value="${usuario.externalUserId}" />"	/>
							</display:column>
						</c:if>

						<c:if test="${busqueda}">
							<display:column style="width:10px">
								<input type="radio" name="idUsrSisExtGestorSeleccionado"
									value="<c:out value="${usuario.externalUserId}" />"
									<c:if test="${usuario_rowNum==1}">checked="true"</c:if>
									/>
							</display:column>
						</c:if>

						<display:column titleKey="organizacion.user.nombre">
							<c:out value="${usuario.name}"/>&nbsp;<c:out value="${usuario.surname}"/>
						</display:column>

						<display:column titleKey="archigest.archivo.descripcion">
							<c:out value="${usuario.description}"/>
						</display:column>

						<display:column titleKey="archigest.archivo.email">
							<c:out value="${usuario.email}"/>
						</display:column>
					</display:table>
				</div>
			</c:if>
		</tiles:put>
	</tiles:insert>
</html:form>