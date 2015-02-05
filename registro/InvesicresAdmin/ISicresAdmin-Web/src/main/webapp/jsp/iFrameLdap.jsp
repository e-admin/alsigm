<%@ page contentType="text/html;charset=ISO-8859-1" language="java" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ page import="ieci.tecdoc.isicres.rpadmin.struts.util.AutenticacionAdministracion" %>
<%@ page import="es.ieci.tecdoc.isicres.admin.core.services.ConstantesGestionUsuariosAdministracion" %>

<%
response.setHeader("Cache-Control","no-cache"); //HTTP 1.1
response.setHeader("Pragma","no-cache"); //HTTP 1.0
response.setDateHeader ("Expires", 0); //prevents caching at the proxy server
%>


<html>
<head>
<link rel="stylesheet" type="text/css" href="css/xtree.css">
<logic:equal name="borraCookies" value="true">
    <script type="text/javascript" language="javaScript" src="js/cookies.js"></script>
</logic:equal>
<script type="text/javascript" language="JavaScript" src="js/tree.js"></script>
<script type="text/javascript" language="JavaScript" src="js/common.js"></script>
<script>


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
<style>
	body { background: white; color: black; }
</style>
</head>

<body topmargin="0" leftmargin="0"> 
<table cellpadding="0" cellspacing="0" border="0" width="100%" height="100%">
	<tr>
		<td valign="top">
			<script>
				
				var nombreSel;
				var idSel;
				var codigoSel;
				var nodoSeleccionable;

				function selectNombre() {
					if (nodoSeleccionable!="1"){
						alert("<bean:message key="ieci.tecdoc.sgm.rpadmin.error.mensaje.select.tipo.grupo.no.valido"/>")
						return;	
					}
					
					var check = chequearSession('<html:rewrite page="/chequearSession.do"/>');
			    	if( check == "false" ) {
			    		<logic:equal scope="request" name="typeTree" value="2">
			    	  		window.parent.opener.selectTreeNode(nombreSel, idSel);
							window.parent.close();
						</logic:equal>
						<logic:equal scope="request" name="typeTree" value="1">
		    	  			window.parent.opener.selectTreeNode(nombreSel, idSel);
							window.parent.close();
						</logic:equal>							  	 
						<logic:equal scope="request" name="typeTree" value="4">
		    	  			window.parent.opener.selectTreeNode(nombreSel, idSel);
							window.parent.close();
						</logic:equal>
			    	 } else {
		    	 	  window.parent.redirect('<%=AutenticacionAdministracion.obtenerUrlLogin(request, ConstantesGestionUsuariosAdministracion.APLICACION_REGISTRO) %>');	
			         }
				}
				
				function eventExpand(id){
					if((id!=null)&&(id!="undefined")){
					<logic:equal scope="request" name="typeTree" value="4">
						document.location.href = '<html:rewrite page="/ldapGroupTree.do"/>' + '?id='+id;
					</logic:equal>
					<logic:equal scope="request" name="typeTree" value="2">
						document.location.href = '<html:rewrite page="/ldapGroupTree.do"/>' + '?id='+id;
					</logic:equal>
					<logic:equal scope="request" name="typeTree" value="1">
						document.location.href = '<html:rewrite page="/ldapUserTree.do"/>' + '?id='+id;
					</logic:equal>
					}
			    }
			    
			    function carga(id, nombre, codigo, tipo, seleccionable){
			    	var check = chequearSession('<html:rewrite page="/chequearSession.do"/>');
			    	if( check == "false" ) {
			    		nombreSel = nombre;
			    		idSel = id;
			    		codigoSel = codigo;
			    		nodoSeleccionable = seleccionable;
		    	 	  	window.parent.nombreSel = nombre;
		    	 	  	window.parent.idSelect = id;
		    	 	  	window.parent.codigoSel = codigo;
		    	 	  	window.parent.nodoSeleccionable = seleccionable;
		    	    } else {
		    	 	  window.parent.redirect('<%=AutenticacionAdministracion.obtenerUrlLogin(request, ConstantesGestionUsuariosAdministracion.APLICACION_REGISTRO) %>');	
			    	}
			    }
			    
			    function nodoRaizEvent(id, nombre, codigo, seleccionable)
			    {
			    	var check = chequearSession('<html:rewrite page="/chequearSession.do"/>');
			    	if( check == "false" ) {
			    		nombreSel = nombre;
			    		idSel = id;
			    		codigoSel = codigo;
			    		nodoSeleccionable = seleccionable;
		    	 	  	window.parent.nombreSel = nombre;
		    	 	  	window.parent.idSelect = id;
		    	 	  	window.parent.codigoSel = codigo;
		    	 	  	window.parent.nodoSeleccionable = seleccionable;
		    	    } else {
		    	 	  window.parent.redirect('<%=AutenticacionAdministracion.obtenerUrlLogin(request, ConstantesGestionUsuariosAdministracion.APLICACION_REGISTRO) %>');	
			    	}
			    }
				<c:out value="${requestScope.treeString}" escapeXml="false"></c:out>
				document.write(tree);    
			 </script>
		</td>
	</tr>
</table>
<script>
	document.ondblclick = selectNombre;
</script>
</body>
</html>
