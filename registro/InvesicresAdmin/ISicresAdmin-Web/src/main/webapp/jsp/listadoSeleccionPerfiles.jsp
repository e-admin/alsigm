<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="es" xml:lang="es">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<meta name="author" content="IECISA" />
<title><bean:message key="ieci.tecdoc.sgm.pgadmin.title.aplication"/> - <bean:message key="ieci.tecdoc.sgm.rpadmin.libros.filtros.listado.oficinas"/></title>
<link href='<html:rewrite page="/css/estilos.css"/>' rel="stylesheet" type="text/css" />
<link href="css/adminApp.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="js/common.js"></script>
<script>
	var skipcycle = false;


	function guardar() {

		var posSeleccionada = -1;
		for( var i = 0; i < document.getElementsByName("seleccionada").length; i++) {
			if( document.getElementsByName("seleccionada")[i].checked) {
				posSeleccionada = i+1;
				break;
			}
		}

		if(posSeleccionada == -1) {
			alert("<bean:message key="ieci.tecdoc.sgm.rpadmin.libros.asociar.oficinas" />");
			return;
		}
		var id = document.getElementById("ids_" + posSeleccionada).value;
		var codigo = document.getElementById("codigos_" + posSeleccionada).value;
		var nombre = document.getElementById("nombres_" + posSeleccionada).value;;

		window.opener.addPerfil(id, codigo, nombre);
		window.close();
	}

	function init() {
		mytimer = setTimeout('fcsOnMe()', 50);
	}

	function fcsOnMe(){

		if (!skipcycle){
			window.focus();
		}
		mytimer = setTimeout('fcsOnMe()', 50);
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

	if (document.layers){
		document.captureEvents(Event.MOUSEDOWN);
		document.onmousedown=clickNS4;
	}
	else if (document.all && !document.getElementById){
		document.onmousedown=clickIE4;
	}

	document.oncontextmenu=new Function("return false");
	document.onkeydown = detectKey;
</script>
</head>
<body onload="init()">
<form action="/listadoListas.do">
<table cellpadding="0" cellspacing="0" border="0" width="100%">
	<tr>
		<td valign="top">
			<table cellspacing="2" cellpadding="2" border="0" width="100%">
				<tr>
					<td width="100%" align="left">
						<jsp:include page="includes/errores.jsp"/>
					</td>
					<td align="right" class="col_guardar" onclick="guardar()"><bean:message key="ieci.tecdoc.sgm.rpadmin.botones.guardar"/></td>
					<td align="right" class="col_nuevo" onclick="javascript:window.close()"><bean:message key="ieci.tecdoc.sgm.rpadmin.botones.cancelar"/></td>
				</tr>
			</table>
			<table cellpadding="0" cellspacing="0" border="0" width="100%">
				<tr><td height="5"></td></tr>
				<tr>
					<td>
						<div id="tablaListas" style="overflow:auto; width:320px; height:210px; ">

						<display:table name="listas" uid="fila" requestURI="/listadoSeleccionPerfiles.do" class="tablaListado" sort="page">
						 	<display:column style="width:5%px" titleKey="ieci.tecdoc.sgm.rpadmin.libros.asociacion.select">
						 		<table cellpadding="0" cellspacing="0" border="0" align="center">
						 			<tr>
						 				<td align="center">
						 					<input type="radio" name="seleccionada"/>
						 				</td>
						 			</tr>
						 		</table>
						 	</display:column>
							<display:column titleKey="ieci.tecdoc.sgm.rpadmin.oficinas.codigo" sortable="false" style="width: 15%;">
								<bean:write name="fila" property="codigo"/>

								<input type="hidden" name="ids" id='ids_<bean:write name="fila_rowNum"/>' value ='<bean:write name="fila" property="codigo"/>'/>
								<input type="hidden" name="codigos" id='codigos_<bean:write name="fila_rowNum"/>' value ='<bean:write name="fila" property="codigo"/>'/>
							</display:column>
							<display:column titleKey="ieci.tecdoc.sgm.rpadmin.oficinas.nombre" sortable="false">
								<bean:write name="fila" property="descripcion"/>
								<input type="hidden" name="nombres" id='nombres_<bean:write name="fila_rowNum"/>' value='<bean:write name="fila" property="descripcion"/>'/>
							</display:column>
					</display:table>
						</div>
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>
</form>
</body>
</html>