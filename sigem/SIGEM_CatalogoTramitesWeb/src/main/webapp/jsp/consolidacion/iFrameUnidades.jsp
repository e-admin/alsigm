<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ page contentType="text/html;charset=ISO-8859-1" language="java" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%
response.setHeader("Cache-Control","no-cache"); //HTTP 1.1
response.setHeader("Pragma","no-cache"); //HTTP 1.0
response.setDateHeader ("Expires", 0); //prevents caching at the proxy server
%>


<html>
<head>
<link rel="stylesheet" type="text/css" href="css/xtreeUnidades.css">
<logic:equal name="borraCookies" value="true">
    <script type="text/javascript" language="javaScript" src="js/cookies.js"></script>
</logic:equal>
<script type="text/javascript" language="JavaScript" src="js/treeUnidades.js"></script>
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
				var codigoUnidad;
				var nombreUnidad;
				var idUnidad;
				function eventExpand(id){
					if((id!=null)&&(id!="undefined")){
						document.location.href = '<html:rewrite page="/cargarUnidades.do"/>' + '?id='+id;
					}
			    }

			    function carga(id, nombre, codigo, tipo){
			    	var form = parent.document.getElementById("addresseeForm");
			       	form.code.value = codigo;
			       	form.nodoseleccionado.value = nombre;
			    }

			    function nodoRaizEvent()
			    {
			    	var form = parent.document.getElementById("addresseeForm");
			       	form.code.value = '0';
			       	form.nodoseleccionado.value = '<bean:message key="ieci.tecdoc.sgm.rpadmin.unidades.titulo"/>';
			    }
				<c:out value="${requestScope.treeString}" escapeXml="false"></c:out>
				document.write(tree);
			 </script>
		</td>
	</tr>
</table>
</body>
</html>
