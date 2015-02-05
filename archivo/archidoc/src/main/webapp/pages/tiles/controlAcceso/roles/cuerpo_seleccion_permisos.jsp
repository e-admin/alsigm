<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/archigest-adds.tld" prefix="pa" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>

<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>

<c:set var="infoRol" value="${sessionScope[appConstants.controlAcceso.INFO_ROL]}" />
<bean:struts id="mappingGestionRoles" mapping="/gestionRoles" />
<html:form action="/gestionRoles">	

	<tiles:insert template="/pages/tiles/PABoxLayout.jsp">
		<tiles:put name="boxTitle" direct="true">
			<bean:message key="archigest.archivo.cacceso.permisosPerfil"/>
		</tiles:put>

		<tiles:put name="buttonBar" direct="true">
			<table><tr>
				<td nowrap="nowrap">
					<script>
						function incorporarPermisos() {
							var form = document.forms['<c:out value="${mappingGestionRoles.name}" />'];
							if (form && form.permisoRol && elementSelected(form.permisoRol))
								form.submit();
							else
								alert("<bean:message key='archigest.archivo.cacceso.msgSelPermisosAgregarPerfil'/>");
						}
					</script>
					<a class="etiquetaAzul12Bold" href="javascript:incorporarPermisos()" >
						<html:img page="/pages/images/Ok_Si.gif" altKey="archigest.archivo.aceptar" titleKey="archigest.archivo.aceptar" styleClass="imgTextMiddle" />
						&nbsp;<bean:message key="archigest.archivo.aceptar"/>
					</a>
				</td>		
				<td nowrap="nowrap" width="10px"></td>
				<td nowrap="nowrap">
					<tiles:insert definition="button.closeButton" flush="true">
						<tiles:put name="labelKey" direct="true">archigest.archivo.cancelar</tiles:put>
						<tiles:put name="imgIcon" direct="true">/pages/images/Ok_No.gif</tiles:put>
					</tiles:insert>
				</td>
			</tr></table>
		</tiles:put>

		<tiles:put name="boxContent" direct="true">
			
			<tiles:insert flush="false" template="/pages/tiles/PABlockLayout.jsp">
				<tiles:put name="blockTitle" direct="true">
					<bean:message key="archigest.archivo.cacceso.datosPerfil"/>
				</tiles:put>
				<tiles:put name="blockContent" direct="true">
					<table class="formulario" width="99%">
						<tr>
							<td class="tdTitulo" width="140px"><bean:message key="archigest.archivo.nombre"/>:</td>
							<td class="tdDatos"><c:out value="${infoRol.nombre}" /></td>
						</tr>
						<tr>
							<td class="tdTitulo"><bean:message key="archigest.archivo.descripcion"/>:</td>
							<td class="tdDatos"><c:out value="${infoRol.descripcion}"> -- </c:out></td>
						</tr>
						<tr>
							<td class="tdTitulo"><bean:message key="archigest.archivo.cacceso.permisosActuales"/>:</td>
							<td class="tdDatos">&nbsp;</td>
						</tr>
						<tr>
							<td colspan="2" style="text-align:center;" class="etiquetaNegra11Normal">
								<c:set var="listaPermisos" value="${infoRol.permisos}" />
								<div class="separador5">&nbsp;</div>
									<display:table name="pageScope.listaPermisos" 
										id="permiso" 
										style="width:95%;margin-left:auto;margin-right:auto"
										sort="list"
										defaultsort="1">
										<display:setProperty name="basic.msg.empty_list">
											<bean:message key="archigest.archivo.cacceso.msgNoPermisosRol"/>
										</display:setProperty>
		
										<display:column titleKey="archigest.archivo.nombre">
											<c:set var="permisoKey">archigest.archivo.permiso.<c:out value="${permiso.perm}"/></c:set>
											<fmt:message key="${permisoKey}" />
										</display:column>
									</display:table>
									<div class="separador5">&nbsp;</div>
							</td>
						</tr>
					</table>
				</tiles:put>
			</tiles:insert>

			<span class="separador8">&nbsp;</span>
			<tiles:insert flush="false" template="/pages/tiles/PABlockLayout.jsp">
				<tiles:put name="blockTitle" direct="true">
					<bean:message key="archigest.archivo.cacceso.msgSelPermisosRol"/>
				</tiles:put>
				<tiles:put name="buttonBar" direct="true">
					<table>
						<tr>
							<td nowrap="nowrap" class="etiquetaAzul12Bold">
								<bean:message key="archigest.archivo.modulo"/>:&nbsp;
							</td>
							<td nowrap="nowrap" >
								<script>
									function seleccionarModulo() {
										var form = document.forms['<c:out value="${mappingGestionRoles.name}" />'];
										form.method.value = 'mostrarSeleccionPermisosRol';
										form.submit();
									}
								</script>
								<c:set var="listaModulos" value="${sessionScope[appConstants.controlAcceso.LISTA_MODULOS]}" />
								<select name="modulo" onchange="seleccionarModulo()">
									<option value=""> -- </option>
								<c:forEach var="modulo" items="${listaModulos}">
									<option value="<c:out value="${modulo.key}"/>"
									<c:if test="${modulo.key==param.modulo}">selected="true"</c:if>>
										<fmt:message key="${modulo.value}"/>
									</option>
								</c:forEach>
								</select>
							</td>
						</tr>
					</table>
				</tiles:put>
		
				<tiles:put name="blockContent" direct="true">
					<input type="hidden" name="method" value="agregarPermisosRol">
					<input type="hidden" name="idRol" value="<c:out value="${infoRol.id}" />">
					<c:set var="listaPermisos" value="${requestScope[appConstants.controlAcceso.LISTA_PERMISOS]}" />

					<div style="padding-top:5px; padding-bottom:5px">
						<display:table name="pageScope.listaPermisos" 
							id="permiso" 
							style="width:98%;margin-left:auto;margin-right:auto"
							sort="list"
							defaultsort="2">
							<display:setProperty name="basic.msg.empty_list">
								<bean:message key="archigest.archivo.cacceso.msgNoPermisosListaAcceso"/>
							</display:setProperty>
							<display:column style="width:15px">
								<input type="checkbox" name="permisoRol" value="<c:out value="${permiso}" />">
							</display:column>

							<display:column titleKey="archigest.archivo.nombre">
								<c:set var="permisoKey">archigest.archivo.permiso.<c:out value="${permiso}"/></c:set>
								<fmt:message key="${permisoKey}" />
							</display:column>
						</display:table>
					</div>
					<span class="separador5">&nbsp;</span>
				</tiles:put>
		</tiles:insert>
	</tiles:put>
</tiles:insert>
</html:form>
