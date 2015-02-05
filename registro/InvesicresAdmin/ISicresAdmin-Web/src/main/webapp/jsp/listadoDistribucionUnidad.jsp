
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ page import="ieci.tecdoc.isicres.rpadmin.struts.util.AutenticacionAdministracion" %>
<%@ page import="es.ieci.tecdoc.isicres.admin.core.services.ConstantesGestionUsuariosAdministracion" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="es" xml:lang="es">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<meta name="author" content="IECISA" />
<title><bean:message key="ieci.tecdoc.sgm.pgadmin.title.aplication"/> - <bean:message key="ieci.tecdoc.sgm.rpadmin.unidades.titulo"/></title>
<link href="css/estilos.css" rel="stylesheet" type="text/css" />
<link href="css/adminApp.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="js/common.js"></script>
<script>

	var urlSessionExpired = '<%=AutenticacionAdministracion.obtenerUrlLogin(request, ConstantesGestionUsuariosAdministracion.APLICACION_REGISTRO) %>';

	function chequearPopup( url ) {
		var check = chequearSession('<html:rewrite page="/chequearSession.do"/>');
		if( check == "false") {
			abreListaDistribucion( url );
		} else {
			window.parent.redirect(urlSessionExpired);
		}
	}

	function chequearPopupLdap( url ) {
		var check = chequearSession('<html:rewrite page="/chequearSession.do"/>');
		if( check == "false") {
			abreListaDistribucionLdap( url );
		} else {
			window.parent.redirect(urlSessionExpired);
		}
	}

	function chequear( url ) {
		var check = chequearSession('<html:rewrite page="/chequearSession.do"/>');
		if( check == "false") {
			llamadaAction( url );
		} else {
			window.parent.redirect(urlSessionExpired);
		}
	}

	function chequearIdDistribucionEliminar( url, id, msg, nombre ) {
		var check = chequearSession('<html:rewrite page="/chequearSession.do"/>');
		if( check == "false") {
			llamadaActionIdDistribucionEliminar( url, id, msg, nombre );
		} else {
			window.parent.redirect(urlSessionExpired);
		}
	}

	function redirectAnadir( url ) {
		window.parent.redirect(url);
	}

	function selectTreeNode(nombre, id){
		var action = '<html:rewrite page="/guardarDistribucionLdap.do"/>';
		document.forms[0].action = action+"?id="+id;
		document.forms[0].submit()
	}

</script>
</head>

<body>
<html:form action="/listadoDistribucionUnidad.do">
<table cellpadding="0" cellspacing="0" border="0" width="525">
<tr>
	<td>
  <!-- Inicio Contenido -->
			<div id="migas">
				<bean:message key="ieci.tecdoc.sgm.rpadmin.botones.distribucion"/>&nbsp;
				<span class="migasElementoSeleccionado"><bean:write name="nombreOrganizacion"/></span>&nbsp;
			</div>
			<table cellspacing="0" cellpadding="0" border="0">
				<tr>
					<td width="100%" align="left">
						<jsp:include page="includes/errores.jsp"/>
					</td>
					<logic:equal name="isLdapMethod" value="true" scope="request">
						<td align="right" class="col_nuevo" onclick="chequearPopupLdap('<html:rewrite page="/jsp/anadirLdapDistribucionUnidad.jsp"/>')"><bean:message key="ieci.tecdoc.sgm.rpadmin.botones.anadir"/></td>
					</logic:equal>
					<logic:notEqual name="isLdapMethod" value="true" scope="request">
						<td align="right" class="col_nuevo" onclick="chequearPopup('<html:rewrite page="/jsp/anadirDistribucionUnidad.jsp"/>')"><bean:message key="ieci.tecdoc.sgm.rpadmin.botones.anadir"/></td>
					</logic:notEqual>
					<td align="right" class="col_eliminar" onclick="chequear('<html:rewrite page="/listadoUnidad.do"/>')"><bean:message key="ieci.tecdoc.sgm.rpadmin.botones.cancelar"/></td>
					<td width="20"><img src="../img/dot.gif" width="20" height="1" /></td>
				</tr>
			</table>
			<div id="tableUnidadHijos" style="overflow:auto;height:270px;width:100%">
				<display:table name="distribuciones.lista" uid="fila"
				 requestURI="/listadoDistribucionUnidad.do" class="tablaListado" sort="page" style="width:95%">
					<display:column titleKey="ieci.tecdoc.sgm.rpadmin.unidades.distribucion.tipo"
						 sortable="false" style="width: 45%;">
						 <table cellpadding="0" cellspacing="0" border="0">
						 	<tr>

						 		<logic:equal name="fila" property="tipoDestino" value="1">
						 			<td><img src='<html:rewrite page="/img/usuario.gif"/>' />&nbsp;<bean:message key="ieci.tecdoc.sgm.rpadmin.unidades.distribucion.usuario" /></td>
						 		</logic:equal>

						 		<logic:equal name="fila" property="tipoDestino" value="2">
						 			<td><img src='<html:rewrite page="/img/departamento.gif"/>' />&nbsp;<bean:message key="ieci.tecdoc.sgm.rpadmin.unidades.distribucion.departamento" /></td>
						 		</logic:equal>

						 		<logic:equal name="fila" property="tipoDestino" value="3">
						 			<td><img src='<html:rewrite page="/img/grupo.gif"/>' />&nbsp;<bean:message key="ieci.tecdoc.sgm.rpadmin.unidades.distribucion.grupo" /></td>
						 		</logic:equal>

						 	</tr>
						 </table>
					</display:column>
					<display:column property="nombre" titleKey="ieci.tecdoc.sgm.rpadmin.unidades.distribucion.destino"
						 sortable="false" style="width: 45%"/>
					<display:column style="width: 10%;">
						<table><tr>
							<c:set var="funcionchequearIdDistribucionEliminar">
								chequearIdDistribucionEliminar("<html:rewrite page='/eliminarDistribucion.do'/>","<bean:write name='fila' property='id' filter='false'/>","<bean:message key='ieci.tecdoc.sgm.rpadmin.unidades.eliminar.distribucion'/>","<bean:write name='fila' property='nombre' filter='false'/>")
							</c:set>
							<td class="col_eliminar" onclick="<c:out value="${funcionchequearIdDistribucionEliminar}"/>"><bean:message key="ieci.tecdoc.sgm.rpadmin.botones.eliminar"/></td>
						</tr></table>
					</display:column>
				</display:table>
			</div>
	</td>
</tr>
</table>
</html:form>
</body>
</html>

