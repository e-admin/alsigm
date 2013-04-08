<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-html-el.tld" prefix="html-el" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="es" xml:lang="es">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<meta name="author" content="IECISA" />
<title><bean:message key="ieci.tecdoc.sgm.pgadmin.title.aplication"/> - <bean:message key="ieci.tecdoc.sgm.rpadmin.oficinas.seleccion.usuarios"/></title>
<link href='<html:rewrite page="/css/estilos.css"/>' rel="stylesheet" type="text/css" />
<link href="css/adminApp.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="js/common.js"></script>
<script>
	var skipcycle = false;

	function guardar(idOficina) {
		var ids = "";
		var ids_selected = document.getElementsByName("idsUser");
		var num_selected = ids_selected.length;
		if(num_selected > 0){
			for(var i = 0; i < num_selected; i++) {
				if(ids_selected[i].checked)
					ids += "&idsUser=" + ids_selected[i].value;
			}
			if(ids != ""){
				window.opener.location.href = '<html:rewrite page="/asociarUsuarioOficina.do"/>' + '?idOficina=' + idOficina + ids + '&accion=ASOCIAR_USUARIOS';
				window.close();
			}else{
				alert("<bean:message key="ieci.tecdoc.sgm.rpadmin.oficinas.seleccionar.usuarios"/>");
				return;
			}
		}
		else{
			alert("<bean:message key="ieci.tecdoc.sgm.rpadmin.oficinas.seleccionar.usuarios"/>");
			return;
		}
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
<html:form action="/asociarUsuarioOficina.do" method="post">
<table cellpadding="0" cellspacing="0" border="0" width="100%">
	<tr>
		<td valign="top">
			<table cellspacing="2" cellpadding="2" border="0" width="100%">
				<tr>
					<td width="100%" align="left">
						<jsp:include page="includes/errores.jsp"/>
					</td>
					<logic:notEmpty name="usuarios" property="lista">
						<td align="right" class="col_nuevo" onclick="guardar('<bean:write name="idOficina"/>')"><bean:message key="ieci.tecdoc.sgm.rpadmin.botones.asociar"/></td>
					</logic:notEmpty>
					<td align="right" class="col_eliminar" onclick="window.close();"><bean:message key="ieci.tecdoc.sgm.rpadmin.botones.cancelar"/></td>
				</tr>
			</table>
			<table cellpadding="0" cellspacing="0" border="0" width="100%">
				<tr><td height="5"></td></tr>
				<tr>
					<td>
						<div id="tablaUsuarios" style="overflow:auto; width:100%; height:150px; ">
							<display:table name="usuarios.lista" uid="fila"
								 requestURI="/usuarios.do" sort="page" class="tablaListado" style="width:100%" defaultsort="2">
								 	<display:setProperty name="basic.msg.empty_list">
										<bean:message key="ieci.tecdoc.sgm.rpadmin.oficinas.asociar.sin.usuarios"/>
									</display:setProperty>
								 	<display:column style="width:3px" titleKey="ieci.tecdoc.sgm.rpadmin.libros.asociacion.select">
					 					<html-el:multibox property="idsUser" value="${fila.id}"/>
									</display:column>
									<display:column property="nombre" titleKey="ieci.tecdoc.sgm.rpadmin.usuarios.nombre"
										 sortable="false" style="width: 24%;"/>
									<display:column property="perfil" titleKey="ieci.tecdoc.sgm.rpadmin.usuarios.perfil"
										 sortable="false" style="width: 28%"/>
									<display:column property="nombreCompleto" titleKey="ieci.tecdoc.sgm.rpadmin.usuarios.nombreCompleto"
										 sortable="false" style="width: 450%;"/>
							</display:table>
						</div>
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>
</html:form>
</body>
</html>