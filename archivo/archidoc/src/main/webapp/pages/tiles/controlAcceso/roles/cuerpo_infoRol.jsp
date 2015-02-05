<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/archigest-adds.tld" prefix="pa" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/taglibs/securityTag.tld" prefix="security"%>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>

<c:set var="infoRol" value="${sessionScope[appConstants.controlAcceso.INFO_ROL]}" />
<bean:struts id="mappingGestionRoles" mapping="/gestionRoles" />

<script>
	<c:url var="eliminarURL" value="/action/gestionRoles">
		<c:param name="method" value="eliminarRol" />
		<c:param name="idRol" value="${infoRol.id}" />
	</c:url>
	function eliminarPerfil() {
		if (confirm("<bean:message key='archigest.archivo.cacceso.msgConfirmPerfilEliminar'/>"))
			window.location = '<c:out value="${eliminarURL}" escapeXml="false"/>';
	}
</script>

<tiles:insert template="/pages/tiles/PABoxLayout.jsp">
	<tiles:put name="boxTitle" direct="true">
		<bean:message key="archigest.archivo.cacceso.datosPerfil"/>
	</tiles:put>

	<tiles:put name="buttonBar" direct="true">
		<table><tr>
		<security:permissions action="${appConstants.controlAccesoActions.MODIFICACION_ROL}">	
			<td nowrap>
				<a class="etiquetaAzul12Bold" href="javascript:eliminarPerfil()" >
				<html:img page="/pages/images/deleteDoc.gif" altKey="archigest.archivo.eliminar" titleKey="archigest.archivo.eliminar" styleClass="imgTextMiddle" />
				<bean:message key="archigest.archivo.eliminar"/></a>
			</td>
			<td width="10px"></td>
		</security:permissions>			
		<td nowrap>
			<tiles:insert definition="button.closeButton" />			
		</td>
		</tr></table>
	</tiles:put>

	<tiles:put name="boxContent" direct="true">
		<div class="separador1"></div>
		<div class="cabecero_bloque">
				<TABLE class="w98m1" cellpadding=0 cellspacing=0 height="100%">
				  <TR>
					<TD class="etiquetaAzul12Bold" width="40%" >
						<bean:message key="archigest.archivo.informacion"/>
					</TD>

					<security:permissions action="${appConstants.controlAccesoActions.MODIFICACION_ROL}">
					<TD class="etiquetaAzul12Bold" align="right" >
						<TABLE cellpadding=0 cellspacing=0>
						<TR>
							<td nowrap>
								<c:url var="edicionURL" value="/action/gestionRoles">
									<c:param name="method" value="edicionRol" />
									<c:param name="idRol" value="${infoRol.id}" />
								</c:url>
					
								<a class="etiquetaAzul12Bold" href="<c:out value="${edicionURL}" escapeXml="false"/>" >
								<html:img page="/pages/images/editDoc.gif" altKey="archigest.archivo.editar" titleKey="archigest.archivo.editar" styleClass="imgTextMiddle" />
								<bean:message key="archigest.archivo.editar"/></a>
							</td>
							<td width="5px"></td>
						</TR>
						</TABLE>
					</TD>
					</security:permissions>					
				  </TR>
				</TABLE>
		</div>

		<div class="bloque" style="margin-bottom:6px">
		<table class="formulario" width="99%">
			<tr>
				<td class="tdTitulo" width="120px">
					<bean:message key="archigest.archivo.nombre"/>:&nbsp;
				</td>
				<td class="tdDatos"><c:out value="${infoRol.nombre}" /></td>
			</tr>
			<tr>
				<td class="tdTitulo">
					<bean:message key="archigest.archivo.modulo"/>:&nbsp;
				</td>
				<td class="tdDatos">
				<c:choose>
					<c:when test="${infoRol.tipoModulo>0}">
					<fmt:message key="nombreModulo.modulo${infoRol.tipoModulo}" />
					</c:when>
					<c:otherwise><c:out value="-"/></c:otherwise>				
				</c:choose>
				</td>
					
			</tr>
			<tr><td>&nbsp;</td></tr>
			<tr>
				<td class="tdTitulo">
					<bean:message key="archigest.archivo.descripcion"/>:&nbsp;
				</td>
				<td class="tdDatos"><c:out value="${infoRol.descripcion}" /></td>
			</tr>
		</table>
		</div>

		<c:choose>
			<c:when test="${param.method == 'verRol'}">
				<c:set var="classTabPermisos" value="tabActual" />
				<c:set var="classTabUsuarios" value="tabSel" />
			</c:when>
			<c:when test="${param.method == 'usuariosEnRol'}">
				<c:set var="classTabPermisos" value="tabSel" />
				<c:set var="classTabUsuarios" value="tabActual" />
			</c:when>
		</c:choose>	
		<div class="cabecero_tabs">
			<table cellspacing="0" cellpadding=0>
				<tr>
			    	<td class="<c:out value="${classTabPermisos}" />" id="ptabPermisos">
						<c:url var="infoRolURL" value="/action/gestionRoles">
							<c:param name="method" value="verRol" />
							<c:param name="idRol" value="${infoRol.id}" />
						</c:url>
						<a href="<c:out value="${infoRolURL}" escapeXml="false"/>" id="ttabPermisos" class="textoPestana">
							<bean:message key="archigest.archivo.cacceso.permisos" />
						</a>
				    </td>
					<td width="5px">&nbsp;</td>
			    	<td class="<c:out value="${classTabUsuarios}" />" id="ptabUsuarios">
						<c:url var="usuariosEnRolURL" value="/action/gestionRoles">
							<c:param name="method" value="usuariosEnRol" />
							<c:param name="idRol" value="${infoRol.id}" />
						</c:url>
						<a href="<c:out value="${usuariosEnRolURL}" escapeXml="false"/>" id="ttabUsuarios" class="textoPestana">
							<bean:message key="archigest.archivo.cacceso.usuariosRol" />
						</a>
				    </td>
				</tr>
			<html:form action="/gestionRoles">
			</table>
		</div>
		<input type="hidden" name="method" value="quitarRolAUsuarios">
		<input type="hidden" name="idRol" value="<c:out value="${infoRol.id}" />">

		<c:choose>
		<c:when test="${param.method == 'verRol'}">
			<div id="tabPermisos" class="bloque_tab">
				<div class="cabecero_bloque_tab">
				<security:permissions action="${appConstants.controlAccesoActions.MODIFICACION_ROL}">
					<table width="100%" cellpadding="0" cellspacing="0"><tr>
					<td class="etiquetaAzul12Bold" align="right">
						<table><tr>
						<td nowrap>
							<c:url var="agregarPermisosURL" value="/action/gestionRoles">
								<c:param name="method" value="mostrarSeleccionPermisosRol" />
							</c:url>
							<a class="etiquetaAzul12Bold" href="<c:out value="${agregarPermisosURL}" escapeXml="false"/>" >
							<html:img titleKey="archigest.archivo.anadir" altKey="archigest.archivo.anadir" page="/pages/images/addDoc.gif" styleClass="imgTextMiddle"/>
							<bean:message key="archigest.archivo.anadir"/></a>
						</td>

						<td width="10px"></td>
						<td>
							<script>
								function quitarPermisosRol() {
									var form = document.forms['<c:out value="${mappingGestionRoles.name}" />'];
									if (form.permisoRol && elementSelected(form.permisoRol)) {
										form.method.value = 'quitarPermisosRol';
										form.submit();
									} else
										alert("<bean:message key='archigest.archivo.cacceso.msgNoPermisosEliminar'/>");
								}
							</script>
						
							<a class="etiquetaAzul12Bold" href="javascript:quitarPermisosRol()" >
							<html:img page="/pages/images/delDoc.gif" altKey="archigest.archivo.eliminar" titleKey="archigest.archivo.eliminar" styleClass="imgTextMiddle" />
							<bean:message key="archigest.archivo.eliminar"/></a>
						</td>
						<td width="5px"></td>

						</tr></table>
					</td>		
					</tr></table>
				</security:permissions>											
				</div>
				<div class="separador8">&nbsp;</div>

				<security:permissions action="${appConstants.controlAccesoActions.MODIFICACION_ROL}">
					<c:set var="defaultsort" value="2" />
				</security:permissions>
				<c:if test="${empty defaultsort}">
					<c:set var="defaultsort" value="1" />
				</c:if>
				<jsp:useBean id="defaultsort" type="java.lang.String" />
				<c:set var="listaPermisos" value="${infoRol.permisos}" />
				<display:table name="pageScope.listaPermisos" 
						id="permiso" 
						style="width:98%;margin-left:auto;margin-right:auto"
						sort="list"
						defaultsort='<%=(new java.lang.Integer(defaultsort)).intValue()%>'
						>
					<display:setProperty name="basic.msg.empty_list">
						<bean:message key="archigest.archivo.cacceso.msgNoPermisosRol" />
					</display:setProperty>
					<security:permissions action="${appConstants.controlAccesoActions.MODIFICACION_ROL}">	
						<display:column style="width:20px" headerClass="minusDocIcon">
							<input type="checkbox" name="permisoRol" value="<c:out value="${permiso.perm}" />">
						</display:column>
					</security:permissions>						
					<display:column titleKey="archigest.archivo.nombre">
					<c:set var="permisoKey">archigest.archivo.permiso.<c:out value="${permiso.perm}"/></c:set>
						<fmt:message key="${permisoKey}" />
					</display:column>
				</display:table>
				<div class="separador8">&nbsp;</div>
			</div>
		</c:when>

		<c:when test="${param.method == 'usuariosEnRol'}">
			<div id="tabUsuarios" class="bloque_tab">
				<div class="cabecero_bloque_tab">
				<security:permissions action="${appConstants.controlAccesoActions.MODIFICACION_ROL}">				
					<table width="100%" cellpadding=0 cellspacing=0><tr>
					<td class="etiquetaAzul12Bold" align="right">
						<table><tr>
							<td nowrap>
								<c:url var="ponerRolURL" value="/action/gestionRoles">
									<c:param name="method" value="busquedaUsuario" />
								</c:url>
								<a class="etiquetaAzul12Bold" href="<c:out value="${ponerRolURL}" escapeXml="false"/>" >
								<html:img titleKey="archigest.archivo.anadir" altKey="archigest.archivo.anadir" page="/pages/images/addDoc.gif" styleClass="imgTextMiddle"/>
								<bean:message key="archigest.archivo.anadir"/></a>
							</td>
							<td width="10px"></td>
							<td nowrap>
								<script>
									function eliminarUsuariosRol() {
										var form = document.forms['<c:out value="${mappingGestionRoles.name}" />'];
										if (form.usuarioSeleccionado && elementSelected(form.usuarioSeleccionado))
											form.submit();
										else
											alert('<bean:message key="archigest.archivo.cacceso.msgNoUsuariosEliminar"/>');
									}
								</script>
								<a class="etiquetaAzul12Bold" href="javascript:eliminarUsuariosRol()" >
								<html:img page="/pages/images/delDoc.gif" altKey="archigest.archivo.eliminar" titleKey="archigest.archivo.eliminar" styleClass="imgTextMiddle" />
								<bean:message key="archigest.archivo.eliminar"/></a>
							</td>
						</tr></table>
					</td>		
					</tr></table>
				</security:permissions>					
				</div>

				<c:set var="listaUsuarios" value="${sessionScope[appConstants.controlAcceso.LISTA_USUARIOS_EN_ROL]}" />
				<security:permissions action="${appConstants.controlAccesoActions.MODIFICACION_ROL}">
					<c:set var="defaultsortUsuarios" value="2" />
				</security:permissions>
				<c:if test="${empty defaultsortUsuarios}">
					<c:set var="defaultsortUsuarios" value="1" />
				</c:if>
				<jsp:useBean id="defaultsortUsuarios" type="java.lang.String" />

				<div class="separador8">&nbsp;</div>
				<display:table name="pageScope.listaUsuarios" 
						id="usuario" 
						style="width:98%;margin-left:auto;margin-right:auto"
						sort="list"
						defaultsort='<%=(new java.lang.Integer(defaultsortUsuarios)).intValue()%>'>
					<display:setProperty name="basic.msg.empty_list">
						<bean:message key="archigest.archivo.cacceso.msgNoUsuarioRol"/></a>
					</display:setProperty>

					<security:permissions action="${appConstants.controlAccesoActions.MODIFICACION_ROL}">	
						<display:column title="" style="width:15px" headerClass="minusDocIcon">
							<input type="checkbox" name="usuarioSeleccionado" value="<c:out value="${usuario.id}" />">
						</display:column>
					</security:permissions>	
					<display:column titleKey="archigest.archivo.nombre">
						<c:url var="infoUsuarioURL" value="/action/gestionUsuarios">
							<c:param name="method" value="verUsuario" />
							<c:param name="idUsuarioSeleccionado" value="${usuario.id}" />
						</c:url>
						<a class="tdlink" href="<c:out value="${infoUsuarioURL}" escapeXml="false"/>">
						<c:out value="${usuario.nombre}" /> <c:out value="${usuario.apellidos}" />
						</a>
					</display:column>
				</display:table>
				<div class="separador8">&nbsp;</div>

			</div>
		</c:when>
		</c:choose>
<div style="display:none;"></html:form></div>
	</tiles:put>
</tiles:insert>