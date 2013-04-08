<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="es" xml:lang="es">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<meta name="author" content="IECISA" />
<title><bean:message key="ieci.tecdoc.sgm.pgadmin.title.aplication"/> - <bean:message key="ieci.tecdoc.sgm.rpadmin.unidades.distribucion.seleccionar.destino"/></title>
<link href="<html:rewrite page="/css/estilos.css"/>" rel="stylesheet" type="text/css" />
<link href="css/adminApp.css" rel="stylesheet" type="text/css" />
<script language="Javascript">
	var skipcycle = false;
	var idSelect;
	var idTipo;
	var nombreDept;
	var codigoDept;

	function init() {
		mytimer = setTimeout('fcsOnMe()', 50);
	}

	function fcsOnMe(){

		if (!skipcycle){
			window.focus();
		}
		mytimer = setTimeout('fcsOnMe()', 50);
	}

	function llamadaActionAceptarPopup() {

		if( idTipo == undefined && idSelect == undefined &&
		    nombreDept == undefined) {
			alert("<bean:message key="ieci.tecdoc.sgm.rpadmin.error.mensaje.required.select.grupo"/>");
			return;
		} else {
			var nodoSelected = document.getElementById('nodoSelected').checked;
			window.opener.parent.document.getElementById('wip').style.display="block";
			window.opener.importarDepts(idSelect, nodoSelected);
			window.close();
		}
	}

	function detectKey() {
		if(window.event && window.event.keyCode == 116){
			window.event.keyCode = 0;
			return false;
		}
	}


	function clickIE4(){
		if (event.button==2){
			return false;
		}
	}

	function clickNS4(e){
		if (document.layers||document.getElementById&&!document.all){
			if (e.which==2||e.which==3){
				return false;
			}
		}
	}

	function callGrupos( url ) {
		document.getElementById("usuarios").src = url;
		document.getElementById('capaCargandoUser').style.display = "block";
		loadingUser();
	}

	function callDept( url ) {
		document.getElementById("usuarios").src = url;
		document.getElementById('capaCargandoUser').style.display = "block";
		loadingUser();
	}

	if (document.layers){
		document.captureEvents(Event.MOUSEDOWN);
		document.onmousedown=clickNS4;
	}
	else if (document.all && !document.getElementById){
		document.onmousedown=clickIE4;
	}

	document.oncontextmenu=new Function("return false");
	document.onkeydown = detectKey;

	function loadingUser() {
		if( document.getElementById('usuarios').src != "null") {
			document.getElementById('capaCargandoUser').style.display = "none"
			document.getElementById('capaUser').style.display = "block";
		} else {
			setTimeout("loadingUser()", 100);
		}
	}

	function loadingDept() {
		if( document.getElementById('iframeDept').src != "null") {
			document.getElementById('capaCargandoDept').style.display = "none";
			document.getElementById('capaDept').style.display = "block";
		} else {
			setTimeout("loadingDept()", 100);
		}
	}

	function redirect( url ) {
		window.opener.redirectAnadir(url);
		window.close();
	}

</script>
</head>

<body onload="init();loadingDept();">

<table cellpadding="0" cellspacing="0" border="0" width="360">
<tr>
	<td valign="top">
	  <!-- Inicio Contenido -->
			<table cellspacing="2" cellpadding="2" border="0" width="100%">
				<tr>
					<td width="100%" align="left">
						<jsp:include page="includes/errores.jsp"/>
					</td>
					<td align="right" class="col_nuevo" onclick="llamadaActionAceptarPopup();"><bean:message key="ieci.tecdoc.sgm.rpadmin.botones.aceptar"/></td>
					<td align="right" class="col_eliminar" onclick="window.close();"><bean:message key="ieci.tecdoc.sgm.rpadmin.botones.cancelar"/></td>
				</tr>
				<tr>
					<td width="100%" align="left">
						<input type="checkbox" name="nodoSelected" id="nodoSelected" style="width:20px" checked="checked"/>
						&nbsp;<b><bean:message key="ieci.tecdoc.sgm.rpadmin.unidades.importar.departamentos"/></b>
					</td>
				</tr>
			</table>
			<table cellspacing="2" cellpadding="2" border="0" width="100%">
				<tr>
					<td valign="top" colspan="5">
						<table cellspacing="0" cellpadding="0" border="0" width="350">
							<tr>
								<td valign="top">
									<table cellpadding="0" cellspacing="0" border="0" width="100%">

										<tr>
											<td class="txt">
												<b><bean:message key="ieci.tecdoc.sgm.rpadmin.unidades.importar.gruposLdap"/></b>
											</td>
										</tr>
										<tr>
											<td height="3" class="col">
										</tr>
										<tr>
											<td>
												<div id="capaCargandoDept" style="width:100px; height:100px;display:block">
													<table cellpadding="0" cellspacing="0" border="0" width="350">
														<tr>
															<td><bean:message key="cargando"/></td>
															<td align="center">
																<img src="<html:rewrite page="/img/loading.gif"/>" />
															</td>
														</tr>
													</table>
												</div>
											</td>
										</tr>
										<tr>
											<td>
												<div id="capaDept" style="display:none">
													<table cellpadding="0" cellspacing="0" border="0" width="100%">
														<tr>
															<td class="cuadro">
																<iframe src="<html:rewrite page="/ldapGroupTree.do"/>" id="iframeDept" frameborder='0' width="400" height="280"></iframe>
															</td>
														</tr>
													</table>
												</div>
											</td>
										</tr>
										<tr>
											<td height="10">
										</tr>
									</table>
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</td>
	</tr>
	</table>
</body>
</html>