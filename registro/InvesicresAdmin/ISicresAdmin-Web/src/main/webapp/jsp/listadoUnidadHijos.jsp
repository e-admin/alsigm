<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
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

	function chequearSessionNew( url, id ) {
		var check = chequearSession('<html:rewrite page="/chequearSession.do"/>');
		if( check == "false") {
			llamadaActionIdPadre(url, id);
		} else {
			window.parent.redirect(urlSessionExpired);
		}
	}
	function chequearIdUnidad( url, id) {
		var check = chequearSession('<html:rewrite page="/chequearSession.do"/>');
		if( check == "false") {
			llamadaActionIdUnidad( url, id);
		} else {
			window.parent.redirect(urlSessionExpired);
		}
	}

	function chequearIdDistribucion( url, id) {
		var check = chequearSession('<html:rewrite page="/chequearSession.do"/>');
		if( check == "false") {
			llamadaActionIdDistribucion( url, id);
		} else {
			window.parent.redirect(urlSessionExpired);
		}
	}

	function chequearIdUnidadEliminar( url, id, msg, nombre) {
		var check = chequearSession('<html:rewrite page="/chequearSession.do"/>');
		if( check == "false") {
			llamadaActionIdUnidadEliminar( url, id, msg, nombre);
		} else {
			window.parent.redirect(urlSessionExpired);
		}
	}

	function importarDepartamentos(url) {
		var check = chequearSession('<html:rewrite page="/chequearSession.do"/>');
		if( check == "false") {
			abreDepartamentos(url);
		} else {
			window.document.location.href = urlSessionExpired;
		}
	}
	function importarDepartamentosLdap(url) {
		var check = chequearSession('<html:rewrite page="/chequearSession.do"/>');
		if( check == "false") {
			abreDepartamentosLdap(url);
		} else {
			window.document.location.href = urlSessionExpired;
		}
	}

	function importarDepts(idSelect, nodoSelected){
		llamadaAction('<html:rewrite page="/guardarDepartamentosImportarUnidad.do"/>' + '?idSelect='+ idSelect + '&nodoSelected=' + nodoSelected);
	}
</script>
</head>

<body onload="javascript:window.parent.document.getElementById('wip').style.display='none';">
<form name="frmListadoUsuarios" action="" method="post">
<table cellpadding="0" cellspacing="0" border="0" width="525">
<tr>
	<td>
  		<!-- Inicio Contenido -->
			<div id="migas">
				<bean:message key="ieci.tecdoc.sgm.rpadmin.botones.listado"/>&nbsp;
			</div>
			<table cellspacing="0" cellpadding="0" border="0">
				<tr>
					<td width="100%" align="left">
						<jsp:include page="includes/errores.jsp"/>
					</td>
					<logic:equal name="idTipo" value="1">
						<logic:equal name="tipoDepartamento" value="2">
							<td align="right" class="col_nuevo" onclick="importarDepartamentosLdap('<html:rewrite page="/jsp/importarGrupoLdapPopup.jsp"/>')"><bean:message key="ieci.tecdoc.sgm.rpadmin.botones.importar"/></td>
						</logic:equal>
						<logic:equal name="tipoDepartamento" value="1">
							<td align="right" class="col_nuevo" onclick="importarDepartamentos('<html:rewrite page="/jsp/importarDepartamentosPopup.jsp"/>')"><bean:message key="ieci.tecdoc.sgm.rpadmin.botones.importar"/></td>
						</logic:equal>
					</logic:equal>
					<td align="right" class="col_nuevo" onclick="chequearSessionNew('<html:rewrite page="/nuevaUnidad.do"/>', '<bean:write name="idPadre" />')"><bean:message key="ieci.tecdoc.sgm.rpadmin.botones.nueva"/></td>
					<td width="20"><img src="../img/dot.gif" width="20" height="1" /></td>
				</tr>
			</table>
			<div id="wip" style="display:none;">
				HOLA
			</div>
			<div id="tableUnidadHijos" style="overflow:auto;height:270px;width:100%">
				<display:table name="organizaciones.lista" uid="fila"
				 requestURI="/listadoUnidadHijos.do" class="tablaListado" sort="page" style="width:97%" >
					<display:column property="uid" titleKey="ieci.tecdoc.sgm.rpadmin.unidades.listado.uid"
						 sortable="false" style="width: 30px;"/>
					<logic:present name="fila">
						<logic:notEmpty name="fila" property="fechaBaja">
							<display:column property="nombre" titleKey="ieci.tecdoc.sgm.rpadmin.unidades.nueva.nombre"
								sortable="false" style="width: 240px%; color: #FF0000"/>
						</logic:notEmpty>
						<logic:empty name="fila" property="fechaBaja">
							<display:column property="nombre" titleKey="ieci.tecdoc.sgm.rpadmin.unidades.nueva.nombre"
								sortable="false" style="width: 240px%"/>
						</logic:empty>
					</logic:present>
					<logic:notPresent name="fila">
						<display:column property="nombre" titleKey="ieci.tecdoc.sgm.rpadmin.unidades.nueva.nombre"
							sortable="false" style="width: 240px%"/>
					</logic:notPresent>
					<display:column property="abreviatura" titleKey="ieci.tecdoc.sgm.rpadmin.unidades.listado.abreviatura"
						 sortable="false" style="width: 100px;"/>
					<display:column property="fechaAltaVista" titleKey="ieci.tecdoc.sgm.rpadmin.unidades.listado.fechaAlta"
						 sortable="false" style="width: 80px;"/>
					<display:column property="fechaBajaVista" titleKey="ieci.tecdoc.sgm.rpadmin.unidades.listado.fechaBaja"
						 sortable="false" style="width: 80px;"/>
					<display:column style="width: 25px;">
						<table cellpadding="0" cellspacing="0" border="0">
							<tr>
								<td>
									<a href="#" onclick="chequearIdUnidad('<html:rewrite page="/editarUnidad.do"/>','<bean:write name="fila" property="id" />')"><img src="<html:rewrite page="/img/ico_edit.gif"/>" alt='<bean:message key="ieci.tecdoc.sgm.rpadmin.botones.editar"/>' border="0"/></a>
								</td>
							</tr>
						</table>
					</display:column>
					<display:column style="width: 25px;">
						<table cellpadding="0" cellspacing="0" border="0">
							<tr>
								<td>
									<c:set var="funcionchequearIdUnidadEliminar">
										chequearIdUnidadEliminar("<html:rewrite page='/eliminarUnidad.do'/>","<bean:write name='fila' property='id' filter='false'/>","<bean:message key='ieci.tecdoc.sgm.rpadmin.unidades.eliminar.unidad'/>","<bean:write name='fila' property='nombre' filter='false'/>")
									</c:set>
									<a href="#" onclick="<c:out value="${funcionchequearIdUnidadEliminar}"/>" ><img src='<html:rewrite page="/img/ico_delete.gif"/>' alt='<bean:message key="ieci.tecdoc.sgm.rpadmin.botones.eliminar"/>' border="0"/></a>
								</td>
							</tr>
						</table>
					</display:column>
					<logic:equal name="idTipo" value="1">
					<display:column style="width: 25px;">
						<table cellpadding="0" cellspacing="0" border="0">
							<tr>
								<td>
								 <a href="#" onclick="chequearIdDistribucion('<html:rewrite page="/listadoDistribucionUnidad.do"/>','<bean:write name="fila" property="id" />')"><img src='<html:rewrite page="/img/ico_distrib.gif"/>' alt='<bean:message key="ieci.tecdoc.sgm.rpadmin.botones.distribucion"/>' border = "0"/></a>
								</td>
							</tr>
						</table>
					</display:column>
					</logic:equal>
				</display:table>
			</div>
	</td>
</tr>
</table>
</form>
</body>
</html>
