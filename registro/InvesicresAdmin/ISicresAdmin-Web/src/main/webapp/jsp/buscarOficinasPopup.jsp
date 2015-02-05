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
<link href="<html:rewrite page="/css/estilos.css"/>" rel="stylesheet" type="text/css" />
<link href="css/adminApp.css" rel="stylesheet" type="text/css" />
<script language="Javascript">
	var skipcycle = false;
	var tamanio = <bean:write name="size" />;
	var codigoOficina;
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

	function getCodigoOfic(codigo) {
		codigoOficina = codigo;
	}

	function overFilas( index ) {
		document.getElementById('fila_' + index).style.textDecoration = "none";
	}

	function outFilas( index ) {
		document.getElementById('fila_' + index).style.textDecoration= "none";
	}

	function dblclickOfic(codigo) {
		window.parent.opener.document.forms[0].filtros_valor_<bean:write name="fila" />.value = codigo;
		window.close();
	}

	function clickOfic(codigo, index) {
		codigoOficina = codigo;
		clickFilas(index);
		var formu = document.forms[0];
		for( var i = 0; i < formu.ofic.length; i++) {
			if( i == index ) formu.ofic[i].checked = "true";
		}
	}

	function llamadaActionAceptarPopup() {
		if(codigoOficina != undefined){
			dblclickOfic(codigoOficina);
			window.close();
		}else{
			alert("<bean:message key="ieci.tecdoc.sgm.rpadmin.error.mensaje.required.select.oficina"/>")
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

<form name="oficinas" >
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
						<display:table name="requestScope.oficinas.lista" uid="oficina"
									   requestURI="/listadoOficinas.do" class="usuarioSeleccionado"
									   sort="list" style="width: 400;"
									   pagesize="8"
									   >
							<display:column titleKey="ieci.tecdoc.sgm.rpadmin.libros.asociacion.select"
											style="width: 10%" sortable="false">
								<input type="radio" id="ofic_<c:out value="${oficina_rowNum-1}"/>"
									   name="ofic" onclick="Javascript:getCodigoOfic('<bean:write name="oficina" property="codigo" />')"/>
							</display:column>
							<display:column titleKey="ieci.tecdoc.sgm.rpadmin.oficinas.codigo"
											style="width: 20%" sortable="true"
											property="codigo">
							</display:column>
							<display:column titleKey="ieci.tecdoc.sgm.rpadmin.libros.filtros.listado.oficinas.titulo"
											style="width: 70%" sortable="true"
											property="nombre">
							</display:column>

						</display:table>
						<%--
						<table cellspacing="0" cellpadding="0" border="0" width="400">
							<tr>
								<td valign="top">
									<table cellpadding="0" cellspacing="0" border="0" width="100%">
										<tr>
											<td class="txt"><b><bean:message key="ieci.tecdoc.sgm.rpadmin.libros.asociacion.select" /></b></td>
											<td width="3"></td>
											<td></td>
											<td class="txt"><b><bean:message key="ieci.tecdoc.sgm.rpadmin.oficinas.codigo" /></b></td>
											<td width="3"></td>
											<td></td>
											<td class="txt" colspan="2">
												<b><bean:message key="ieci.tecdoc.sgm.rpadmin.libros.filtros.listado.oficinas.titulo"/></b>
											</td>
										</tr>
										<tr><td colspan="10" height="3"></td></tr>
										<tr>
											<td height="3" class="col" colspan="10">
										</tr>
											<logic:iterate id="oficina" name="oficinas" property="lista" indexId="index"  offset="tamano" scope="request">
												<tr class="usuarioSeleccionado" id='fila_<bean:write name="index" />' style="cursor:hand" ondblclick="dblclickOfic('<bean:write name="oficina" property="codigo" />')"
													onclick="clickOfic('<bean:write name="oficina" property="codigo" />', <bean:write name="index" />)"
													onmouseover="overFilas(<bean:write name="index" />)" onmouseout="outFilas(<bean:write name="index" />)">
													<td width="35" align="left"><input type="radio" id="ofic_<bean:write name="index" />" name="ofic" onclick="Javascript:getCodigoOfic('<bean:write name="oficina" property="codigo" />')"/></td>
													<td width="3"></td>
													<td>|&nbsp;&nbsp;</td>
													<td><bean:write name="oficina" property="codigo"/></td>
													<td width="3"></td>
													<td>&nbsp;|&nbsp;&nbsp;</td>
													<td width="3"></td>
													<td width="80%"><bean:write name="oficina" property="nombre"/></td>
												</tr>
											</logic:iterate>
										<tr>
											<td height="10">
										</tr>
									</table>
								</td>
							</tr>
						</table>
						--%>
					</td>
				</tr>
			</table>
		</td>
	</tr>
	</table>
</form>
</body>
</html>