<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/archigest-adds.tld" prefix="pa" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>

<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>

<bean:struts id="mappingGestionUsuarios" mapping="/gestionUsuarios" />

<html:form action="/gestionUsuarios" >
<tiles:insert template="/pages/tiles/PABoxLayout.jsp">

	<tiles:put name="boxTitle" direct="true">
		<bean:message key="archigest.archivo.cacceso.altaUsuario"/>
	</tiles:put>

	<tiles:put name="buttonBar" direct="true">
		<script>
			function buscarUsuario() {
				var form = document.forms['<c:out value="${mappingGestionUsuarios.name}" />'];
				form.method.value = 'realizarBusquedaUsuarios';
				if (window.top.showWorkingDiv) {
					var title = '<bean:message key="archigest.archivo.msg.titulo"/>';
					var message = '<bean:message key="archigest.archivo.msg.proceso.en.ejecucion"/>';
					window.top.showWorkingDiv(title, message);
				}

				form.submit();
			}
			function seleccionarUsuario() {
				var form = document.forms['<c:out value="${mappingGestionUsuarios.name}" />'];
				form.method.value = 'seleccionarUsuario';
				form.submit();
			}
			function cambiarTipoUsuario(){
				var form = document.forms['<c:out value="${mappingGestionUsuarios.name}" />'];
				form.method.value = 'cambiarTipoUsuario';
				form.submit();
			}
		</script>

		<table>
		<tr>
			<td nowrap>
				<a class="etiquetaAzul12Bold" href="javascript:seleccionarUsuario()" >
				<html:img page="/pages/images/Next.gif" altKey="archigest.archivo.siguiente" titleKey="archigest.archivo.siguiente" styleClass="imgTextMiddle" />
				&nbsp;<bean:message key="archigest.archivo.siguiente"/></a>
			</td>
			<td width="10"></td>
			<td nowrap>
				<tiles:insert definition="button.closeButton" flush="true">
					<tiles:put name="labelKey" direct="true">archigest.archivo.cancelar</tiles:put>
					<tiles:put name="imgIcon" direct="true">/pages/images/Ok_No.gif</tiles:put>
				</tiles:insert>
			</td>
		</tr>
		</table>
	</tiles:put>

	<tiles:put name="boxContent" direct="true">

		<tiles:insert page="/pages/tiles/PABlockLayout.jsp">
			<tiles:put name="blockTitle" direct="true">
				<bean:message key="archigest.archivo.buscarEn"/>
				<bean:message key="archigest.archivo.sistExterno"/>:
			</tiles:put>
			<tiles:put name="buttonBar" direct="true">
				<table cellpadding=0 cellspacing=0>
					<tr><td align="right">
							<a class="etiquetaAzul12Bold" href="javascript:buscarUsuario()">
								<html:img titleKey="archigest.archivo.buscar" altKey="archigest.archivo.buscar" page="/pages/images/buscar.gif" styleClass="imgTextMiddle" />
								<bean:message key="archigest.archivo.buscar"/>&nbsp;
							</a>
						</td>
					</tr>
				</table>
			</tiles:put>
			<tiles:put name="blockContent" direct="true">

				<input type="hidden" name="method" value="realizarBusquedaUsuarios">
				<c:set var="listaUsuarios" value="${sessionScope[appConstants.controlAcceso.LISTA_USUARIOS_EXTERNOS]}" />
				<table  class="formulario" align="center" style="width:99%">
					<tr>
						<td class="tdTitulo" width="300px">
							<bean:message key="archigest.archivo.tipoUsuario"/>:&nbsp;
						</td>
						<td class="tdDatos">
							<c:set var="tiposUsuarios" value="${sessionScope[appConstants.controlAcceso.LISTA_TIPOS_USUARIOS]}" />
							<select name="tipoUsuario" onchange="javascript:cambiarTipoUsuario();">
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
						<td class="tdTitulo">
							<bean:message key="archigest.archivo.nombre"/>&nbsp;/
							<bean:message key="archigest.archivo.apellidos"/>/
							<bean:message key="archigest.archivo.email"/>
							:
						</td>
						<td class="tdDatos">
							<html:text styleClass="input99" property="nombreUsuarioABuscar" />
						</td>
					</tr>

				</table>
			</tiles:put>
		</tiles:insert>

		<c:if test="${listaUsuarios != null}">

			<div class="separador8"></div>

			<div class="cabecero_bloque">
				<table class="w98" cellpadding=0 cellspacing=0 height="100%"><tr>
				<td class="etiquetaAzul12Bold">
					<bean:message key="archigest.archivo.resultadosBusqueda"/>
				</td>
				</tr></table>
			</div>

			<div class="bloque" style="padding-top:8px;padding-bottom:8px">

				<table class="w98m1">
			    <TR><TD class="etiquetaAzul12Bold">
					<bean:message key="archigest.archivo.cacceso.msgSelUserAlta"/>:
				</TD></TR>
				</table>

				<display:table name="pageScope.listaUsuarios"
					id="usuario"
					style="width:98%;margin-left:auto;margin-right:auto"
					requestURI="../../action/gestionUsuarios?method=realizarBusquedaUsuarios"
					sort="list"
					pagesize="10">
					<display:setProperty name="basic.msg.empty_list">
						<bean:message key="errors.gcontrol.altaUsuario.noUsuariosSistExternoCriterioBusqueda"/>
					</display:setProperty>

					<display:column style="width:10px">
						<input type="radio" name="idUsrSisExtGestorSeleccionado"
							value="<c:out value="${usuario.externalUserId}" />"
							<c:if test="${usuario_rowNum==1}">checked="true"</c:if>
							/>
					</display:column>
					<display:column titleKey="archigest.archivo.nombre">
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