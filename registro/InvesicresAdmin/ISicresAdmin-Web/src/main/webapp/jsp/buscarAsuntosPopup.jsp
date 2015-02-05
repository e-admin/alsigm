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
<title><bean:message key="ieci.tecdoc.sgm.pgadmin.title.aplication"/> - <bean:message key="ieci.tecdoc.sgm.rpadmin.libros.filtros.listado.asuntos"/></title>
<link href="<html:rewrite page="/css/estilos.css"/>" rel="stylesheet" type="text/css" />
<link href="css/adminApp.css" rel="stylesheet" type="text/css" />
<script language="Javascript">
	var skipcycle = false;
	var tamanio = <bean:write name="size" />;
	var codigoTipoAsunto;
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

	function getCodigoTipoAsunto(codigo) {
		codigoTipoAsunto = codigo;
	}

	function overFilas( index ) {
		document.getElementById('fila_' + index).style.textDecoration = "none";
	}

	function outFilas( index ) {
		document.getElementById('fila_' + index).style.textDecoration= "none";
	}

	function dblclickTipoAsunto(codigo) {
		window.parent.opener.document.forms[0].filtros_valor_<bean:write name="fila" />.value = codigo;
		window.close();
	}

	function clickTipoAsunto(codigo, index) {
		codigoTipoAsunto = codigo;
		clickFilas(index);
		var formu = document.forms[0];
		for( var i = 0; i < formu.tipoAsunto.length; i++) {
			if( i == index ) formu.tipoAsunto[i].checked = "true";
		}
	}

	function llamadaActionAceptarPopup() {
		if(codigoTipoAsunto != undefined){
			dblclickTipoAsunto(codigoTipoAsunto);
			window.close();
		}else{
			alert("<bean:message key="ieci.tecdoc.sgm.rpadmin.error.mensaje.required.select.asunto"/>")
			return;
		}

	}

	function clickFilas( index ) {

		for( var i = 0; i < tamanio; i++) {
			document.getElementById('fila_' + i).style.backgroundColor= "#ffffff";
			document.getElementById('fila_' + i).style.color = "#000000";
		}
		document.getElementById('fila_' + index).style.backgroundColor= "#639ACE";
		document.getElementById('fila_' + index).style.color = "#ffffff";
	}

</script>
</head>

<body onload="init();">

<form name="asuntos" >
<table cellpadding="0" cellspacing="0" border="0" width="360">
<tr>
	<td valign="top">
	  <!-- Inicio Contenido -->
			<table cellspacing="2" cellpadding="2" border="0" width="100%">
				<tr>
					<td width="100%" align="left">
						<jsp:include page="includes/errores.jsp"/>
					</td>
					<td align="right" class="col_guardar" onclick="llamadaActionAceptarPopup()"><bean:message key="ieci.tecdoc.sgm.rpadmin.botones.guardar"/></td>
					<td align="right" class="col_nuevo" onclick="javascript:window.close()"><bean:message key="ieci.tecdoc.sgm.rpadmin.botones.cancelar"/></td>
				</tr>
			</table>
			<table cellspacing="2" cellpadding="2" border="0" width="100%">
				<tr>
					<td valign="top" colspan="5">
						<display:table name="requestScope.tiposAsunto.lista" uid="asunto"
									   requestURI="/listadoAsuntos.do" class="usuarioSeleccionado"
									   sort="list" style="width: 400;"
									   pagesize="8"
									   >
							<display:column titleKey="ieci.tecdoc.sgm.rpadmin.libros.asociacion.select"
											style="width: 10%" sortable="false">
								<input type="radio" id="tipoAsunto_<c:out value="${asunto_rowNum-1}"/>"
									   name="tipoAsunto" onclick="Javascript:getCodigoTipoAsunto('<bean:write name="asunto" property="code" />')"/>
							</display:column>
							<display:column titleKey="ieci.tecdoc.sgm.rpadmin.asuntos.codigo"
											style="width: 20%" sortable="true"
											property="code">
							</display:column>
							<display:column titleKey="ieci.tecdoc.sgm.rpadmin.libros.filtros.listado.asuntos.titulo"
											style="width: 70%" sortable="true"
											property="matter">
							</display:column>
						</display:table>
					</td>
				</tr>
			</table>
		</td>
	</tr>
	</table>
</form>
</body>
</html>