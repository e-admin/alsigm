
<%@ page contentType="text/html;charset=ISO-8859-1" language="java" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ page import="ieci.tecdoc.isicres.rpadmin.struts.util.AutenticacionAdministracion" %>
<%@ page import="es.ieci.tecdoc.isicres.admin.core.services.ConstantesGestionUsuariosAdministracion" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>

<%
response.setHeader("Cache-Control","no-cache"); //HTTP 1.1
response.setHeader("Pragma","no-cache"); //HTTP 1.0
response.setDateHeader ("Expires", 0); //prevents caching at the proxy server
%>


<html>
<head>
<link href="<html:rewrite page="/css/estilos.css"/>" rel="stylesheet" type="text/css" />
<link href="<html:rewrite page="/css/xtree.css"/>" rel="stylesheet" type="text/css" />
<script type="text/javascript" language="JavaScript" src="js/common.js"></script>
<script>
	
	var idTipo = <bean:write name="tipo" />;
	var tamanio = <bean:write name="size" />
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

	function valueItems( id ) {
		parent.idSelect = id;
		parent.idTipo = idTipo;
	}
	
	function dblclickUser( id, nombre, index ) {
		
		var check = chequearSession('<html:rewrite page="/chequearSession.do"/>'); 
		
		if( check == "false" ) {
			window.parent.opener.document.forms[0].nombre.value = nombre;
			window.parent.close();
   	    } else {
		    window.parent.redirect('<%=AutenticacionAdministracion.obtenerUrlLogin(request, ConstantesGestionUsuariosAdministracion.APLICACION_REGISTRO) %>');	
		}
		
	}

	function clickUser( id, nombre, index ) {
		
		var check = chequearSession('<html:rewrite page="/chequearSession.do"/>'); 
		
		if( check == "false" ) {
   	    	parent.idSelect = id;
			parent.idTipo = 1; // Correspondientes a usuarios
			parent.nombreUser = nombre;
			clickFilas(index);
   	    } else {
		    window.parent.redirect('<%=AutenticacionAdministracion.obtenerUrlLogin(request, ConstantesGestionUsuariosAdministracion.APLICACION_REGISTRO) %>');	
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
	
	

	function overFilas( index ) {
		
		document.getElementById('fila_' + index).style.textDecoration = "underline";
		
	}
	
	function outFilas( index ) {
		
		document.getElementById('fila_' + index).style.textDecoration= "none";
		
	}

</script>
</head>

<body topmargin="0" leftmargin="0" onload="valueItems('<bean:write name="id" />')"> 
<table cellpadding="0" cellspacing="0" border="0" width="100%" height="100%">
	<tr>
		<td valign="top" width="100%">
			<table cellpadding="0" cellspacing="0" border="0" width="100%">
				<logic:notEqual name="size" value="0">
					<logic:iterate id="usuario" name="usuarios" property="lista" indexId="index" offset="tamano" scope="request">
						<c:set var="funciondblclickUser">
							dblclickUser("<bean:write name='usuario' property='codigo' filter='false'/>", "<bean:write name='usuario' property='descripcion' filter='false'/>", "<bean:write name='index' filter='false'/>")
						</c:set>
						<c:set var="funcionclickUser">
							clickUser("<bean:write name='usuario' property='codigo' filter='false'/>", "<bean:write name='usuario' property='descripcion' filter='false'/>", "<bean:write name='index' filter='false'/>")
						</c:set>
						<tr class="usuarioSeleccionado" id='fila_<bean:write name="index" />' style="cursor:hand" ondblclick="<c:out value="${funciondblclickUser}"/>" 
						    onclick="<c:out value="${funcionclickUser}"/>" 
							onmouseover="overFilas(<bean:write name="index" />)" onmouseout="outFilas(<bean:write name="index" />)">
							<td width="20" align="left"><img src='<html:rewrite page="/img/usuario.gif"/>' /></td>
							<td><bean:write name="usuario" property="descripcion"/></td>
						</tr>
					</logic:iterate>
				</logic:notEqual>
				<logic:equal name="size" value="0">
					<tr>
						<td class="txt" style="color:#000000">
							<logic:equal name="tipo" value="2">
								<bean:message key="ieci.tecdoc.sgm.rpadmin.unidades.distribucion.usuarios.departamento.no.existen"/>
							</logic:equal>
							<logic:equal name="tipo" value="3">
								<bean:message key="ieci.tecdoc.sgm.rpadmin.unidades.distribucion.usuarios.grupo.no.existen"/>
							</logic:equal>
						</td>
					</tr>
				</logic:equal>
				<tr>
					<td height="10" colspan="2"></td>
				</tr>
			</table>
		</td>
	</tr>
</table>
</body>
</html>
