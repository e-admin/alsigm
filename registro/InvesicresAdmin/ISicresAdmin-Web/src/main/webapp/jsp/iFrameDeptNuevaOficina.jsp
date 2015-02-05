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

				var nombreId;
				var IdDept;
				var codigoDept;

				function selectNombre() {
					var check = chequearSession('<html:rewrite page="/chequearSession.do"/>');
			    	if( check == "false" ) {
						if( window.parent.nombreDept != undefined ) {
			    	  		window.parent.opener.document.forms[0].nombreDept.value = nombreId;
			    	  		window.parent.opener.document.forms[0].deptId.value = IdDept;
							window.parent.close();
				    	  } else {
			    	 	  	window.parent.nombreDept = nombreId;
			    	 	  	window.parent.idSelect = IdDept;
			    	 	  }
			    	 }
				}

				function eventExpand(id){
					if((id!=null)&&(id!="undefined")){
						document.location.href = '<html:rewrite page="/cargarDepartamentosNuevaOficina.do"/>' + '?id='+id;
					}
				}

			    function carga(id, nombre, codigo){
			    	var check = chequearSession('<html:rewrite page="/chequearSession.do"/>');
			    		if( check == "false" ) {
			    			nombreId = nombre;
			    			IdDept = id;
			    			codigoDept = codigo;
		    	 	  		window.parent.nombreDept = nombre;
		    	 	  		window.parent.idSelect = id;
		    	 	  		window.parent.codigoDept = codigo;
			     		} else {
		    	 	  		window.parent.redirect('<%=AutenticacionAdministracion.obtenerUrlLogin(request, ConstantesGestionUsuariosAdministracion.APLICACION_REGISTRO) %>');
			    	}
			    }

			    function nodoRaizEvent()
			    {
				    //parent.propiedades.location.href = appBase + '/adminUser/bd/deptNewOnRoot.jsp';
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
