<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
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
<link href="css/calendar-win2k-1.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="js/common.js"></script>
<script type="text/javascript" src="js/tabsUnidadRoot.js"></script>
<script type="text/javascript" src="js/calendar.js"></script>
<script type="text/javascript" src="js/calendar-es.js"></script>
<script>
	function init() {
		choosebox(1,2);
	}

	var urlSessionExpired = '<%=AutenticacionAdministracion.obtenerUrlLogin(request, ConstantesGestionUsuariosAdministracion.APLICACION_REGISTRO) %>';

	function chequear( url ) {
		var check = chequearSession('<html:rewrite page="/chequearSession.do"/>');
		if( check == "false") {
			llamadaAction( url );
		} else {
			window.parent.redirect(urlSessionExpired);
		}
	}

	function chequearIdUnidadRoot( url, id ) {
		var check = chequearSession('<html:rewrite page="/chequearSession.do"/>');
		if( check == "false") {
			llamadaActionIdUnidadRoot( url, id );
		} else {
			window.parent.redirect(urlSessionExpired);
		}
	}


</script>
</head>

<body onload="init()">
<html:form action="/nuevaUnidad.do">
<table cellpadding="0" cellspacing="0" border="0">
<tr>
	<td>
 	 <!-- Inicio Contenido -->
			<table cellspacing="0" cellpadding="0" border="0">
				<tr>
					<td width="100%" align="left">
						<jsp:include page="includes/errores.jsp"/>
					</td>
					<td align="right" class="col_guardar" onclick="chequearIdUnidadRoot('<html:rewrite page="/guardarEdicionUnidadRoot.do"/>', <bean:write name="idUnidad" />)"><bean:message key="ieci.tecdoc.sgm.rpadmin.botones.guardar"/></td>
					<td class="col_eliminar" onclick="chequear('<html:rewrite page="/listadoUnidad.do"/>?nodoRaiz=true')"><bean:message key="ieci.tecdoc.sgm.rpadmin.botones.cancelar"/></td>
				</tr>
			</table>
			<div class="cuerpomidUsuarios">

				<div class="submenuUsuario" id="nuevaUnidadPestana" >
					<table cellpadding="0" cellspacing="0" border="0">
						<tr>
							<td>
								<div id="tab1" onmouseover="tabover(1)" onmouseout="tabout(1)" onclick="choosebox(1,2)">
									<table summary="" border="0" cellpadding="0" cellspacing="0">
									<tbody><tr>
										<td class="tableft" height="17" width="7">
											<img src="img/dot.gif" alt="" border="0" height="17" width="7"/></td>
										<td class="tabmiddle1" id="tabmiddle1"><bean:message key="ieci.tecdoc.sgm.rpadmin.unidades.pestana"/></td>
										<td class="tabright"><img src="img/dot.gif" alt="" border="0" height="17" width="7"/></td>
									</tr>
									</tbody></table>
								</div>
							</td>
							<td width="45%"></td>
						</tr>
						</table>
				</div>
				<div class="cuadroUsuario">
					<div id="nuevaUnidad">
						<%--
						<html:hidden property="id"/>
						<html:hidden property="tipo"/>
						<html:hidden property="fechaBajaVista"/>
						<html:hidden property="fechaAltaVista"/>
						<html:hidden property="idPadre"/>
						--%>
						<table cellpadding="0" cellspacing="0" border="0" width="460">
							<tr>
								<td width="10" rowspan="80"></td>
								<td class="col" height="20" colspan="2"></td>
								<td width="100" rowspan="30" ></td>
								<td class="col" colspan="2"></td>
							</tr>
							<tr class="col" style="width:100%">
								<td class="txt"><bean:message key="ieci.tecdoc.sgm.rpadmin.unidades.nueva.uid"/>&nbsp;&nbsp;</td>
								<td><html:text property="uid" styleClass="textInput" style="width:360px;color:#808080" maxlength="1" readonly="true"/></td>
							</tr>
							<tr class="col" style="width:100%">
								<td class="txt"><bean:message key="ieci.tecdoc.sgm.rpadmin.unidades.nueva.nombre"/>&nbsp;&nbsp;</td>
								<td><html:text property="nombre" styleClass="textInput" style="width:360px" maxlength="50"/></td>
							</tr>
							<tr><td height="20" colspan="2"></td></tr>
						</table>
					</div>
			</div>
		  </div>
	</td>
</tr>
</table>
</html:form>

</body>
</html>
