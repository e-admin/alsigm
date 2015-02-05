<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>

<bean:struts id="mappingGestionUsuarios" mapping="/gestionUsuariosOrganizacionAction" />
<c:set var="formBean" value="${sessionScope[mappingGestionUsuarios.name]}"/>
<c:set var="vUsuario" value="${sessionScope[appConstants.organizacion.USUARIO_VER_USUARIO]}"/>

<tiles:insert template="/pages/tiles/PABoxLayout.jsp" flush="false">
	<tiles:put name="boxTitle" direct="true">
		<bean:message key="organizacion.user.ver"/>
	</tiles:put>

	<tiles:put name="buttonBar" direct="true">
		<script>
			<c:url var="eliminacionUsuarioURL" value="/action/gestionUsuariosOrganizacionAction">
				<c:param name="method" value="eliminarUsuario" />
				<c:param name="usuariosABorrar" value="${vUsuario.idUsuario}" />
				<c:param name="target" value="_self" />
			</c:url>
			function eliminarUsuario() {
				if (confirm("<bean:message key='organizacion.user.msgConfirmUsuariosEliminar'/>"))
					window.location = '<c:out value="${eliminacionUsuarioURL}" escapeXml="false"/>';
			}
		</script>
	
		<table>
			<tr>
				<td width="10px"></td>
				<td nowrap>
					<a class="etiquetaAzul12Bold" href="javascript:eliminarUsuario()" >
					<html:img page="/pages/images/deleteDoc.gif" altKey="organizacion.eliminar" titleKey="organizacion.eliminar" styleClass="imgTextMiddle" />
					<bean:message key="organizacion.eliminar"/></a>
				</td>
				<td width="10px"></td>
				<td nowrap>
					<tiles:insert definition="button.closeButton" flush="false"/>
				</td>
			</tr>
		</table>
	</tiles:put>

	<tiles:put name="boxContent" direct="true">

		<div id="barra_errores">
			<organizacion:errors />
		</div>
		
		<div class="separador1"></div>
		<div class="cabecero_bloque">
				<table class="w98m1" cellpadding=0 cellspacing=0 height="100%">
				  <tr>
					<td class="etiquetaAzul12Bold" width="40%" >
						<bean:message key="organizacion.user.informacion"/>
					</td>
				    <td class="etiquetaAzul12Bold" align="right" >
						<table cellpadding=0 cellspacing=0>
						<tr>
							<td nowrap>
								<c:url var="anadirURL" value="/action/gestionUsuariosOrganizacionAction">
									<c:param name="method" value="edicionUsuario" />
								</c:url>
								<a class="etiquetaAzul12Bold" href="<c:out value="${anadirURL}" escapeXml="false"/>" target="_self">
								<html:img page="/pages/images/editDoc.gif" altKey="organizacion.editar" titleKey="organizacion.editar" styleClass="imgTextMiddle" />
								<bean:message key="organizacion.editar"/></a>
							</td>			
						</tr>
						</table>
					</td>
				  </tr>
				</table>
		</div>
		<div class="bloque"> <!-- primer bloque de datos -->
			<table class="formulario" >
				<tr>
					<td class="tdTitulo" width="190px">
			  			<bean:message key="organizacion.user.sistExt"/>:&nbsp;
					</td>
					<td class="tdDatos">
						<c:out value="${vUsuario.sistemaExt}"></c:out>
					</td>
				</tr>
				<tr>
					<td class="tdTitulo" width="190px">
						<bean:message key="organizacion.user.nombre"/>:&nbsp;
					</td>
					<td class="tdDatos"><c:out value="${vUsuario.nombreUsuario}"/></td>
				</tr>
				<tr>
					<td class="tdTitulo" width="190px">
						<bean:message key="organizacion.user.organo"/>:&nbsp;
					</td>
					<td class="tdDatos"><c:out value="${vUsuario.nombreOrgano}"/></td>
				</tr>
			</table>
		</div>
	</tiles:put>
</tiles:insert>