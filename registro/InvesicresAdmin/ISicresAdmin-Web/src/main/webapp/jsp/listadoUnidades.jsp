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
<script type="text/javascript" src="js/common.js"></script>
<script>
	function loading() {
		if( document.getElementById('iframeUnidades').src != "null") {
			document.getElementById('capaCargando').style.display = "none";
			document.getElementById('capaUnidades').style.display = "block";
		} else {
			setTimeout("loading()", 100);
		}
	}

	function refrescar() {

		var urlSessionExpired = '<%=AutenticacionAdministracion.obtenerUrlLogin(request, ConstantesGestionUsuariosAdministracion.APLICACION_REGISTRO) %>';

		var check = chequearSession('<html:rewrite page="/chequearSession.do"/>');
		if( check == "false") {
			document.getElementById("arbolUnidades").src = '<html:rewrite page="/cargarUnidades.do"/>';
		} else {
			redirect(urlSessionExpired);
		}
	}

	function redirect( url ) {
		document.location.href = url;
	}
</script>
</head>

<body onload="loading()">
<form name="frmListadoUsuarios" action="" method="post">
<div id="contenedora">
	<div id="wip" style="background-image:url('img/fondo.gif');display:none;z-index:100;position:absolute;width:900px;height:500px;top:110px;left:100px">
		<div id="text-wip" style="position:absolute;top:240px;left:350px">
			<bean:message key="cargando"/>
		</div>
		<div id="imagen-wip" style="position:absolute;top:225px;left:450px">
			<img src="<html:rewrite page="/img/loading.gif"/>" />
		</div>
	</div>

  	<!-- Inicio Cabecera -->
	<jsp:include page="includes/cabecera.jsp"/>


  <!-- Inicio Contenido -->
  <div id="contenido" >
    <div class="cuerpo">
      <div class="cuerporight">
        <div class="cuerpomid">
	      <jsp:include page="includes/pestanas.jsp">
			<jsp:param name="pestana" value="unidades"/>
		  </jsp:include>

          <div class="cuadro">
			<div class="col" style="height: 350px;">
			<table cellspacing="0" cellpadding="0" border="0">
				<tr>
					<td width="100%" align="left">
						<jsp:include page="includes/errores.jsp"/>
					</td>
					<td align="right"></td>
				</tr>
			</table>
			<div id="capaCargando" style="width:100px; height:100px;display:block">
				<table cellpadding="0" cellspacing="0" border="0" width="100">
					<tr>
						<td><bean:message key="cargando"/></td>
						<td align="center">
							<img src="<html:rewrite page="/img/loading.gif"/>" />
						</td>
					</tr>
				</table>
			</div>
			<div id="capaUnidades" style="display:none">
				<table cellpadding="0" cellspacing="0" border="0" width="100%">
					<tr>
						<td valign="top" width="100%">
							<table cellpadding="0" cellspacing="0" border="0" width="100%">
								<tr>
									<td width="15"></td>
									<td class="col_refrescar" onclick="refrescar();" height="20"><bean:message key="ieci.tecdoc.sgm.rpadmin.unidades.refrescar.arbol"/></td>
									<td width="100%"></td>
								</tr>
								<tr>
									<td height="3" colspan="2" class="col">
								</tr>
								<tr>
									<td valign="top" width="100%" class="cuadroIframe" colspan="2" align="center">
										<iframe src="<html:rewrite page="/cargarUnidades.do"/>" id="arbolUnidades" frameborder='0' width="240" height="315"></iframe>
									</td>
								</tr>
								<tr><td height="8" colspan="2"></td></tr>
							</table>
						</td>
						<td valign="top">
						    <iframe src="<html:rewrite page="/blank.htm"/>" id="iframeUnidades" frameborder="0" scrolling="auto" height="350" width="550" />
						</td>
					</tr>
				</table>
			</div>
			</div>
          </div>
        </div>
      </div>
    </div>
    <div class="cuerpobt">
      <div class="cuerporightbt">
        <div class="cuerpomidbt"></div>
      </div>
    </div>
  </div>
  <!-- Fin Contenido -->
  <div id="pie"></div>
</div>
</form>
</body>
</html>
