<%@ page contentType="text/html;charset=ISO-8859-1" language="java" %>
<%@ taglib uri="/WEB-INF/ieci.tld" prefix="ieci"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>

<%@ page import="ieci.tecdoc.sgm.core.admin.web.AutenticacionAdministracion" %>
<%@ page import="ieci.tecdoc.sgm.core.services.gestion_administracion.ConstantesGestionUsuariosAdministracion" %>

<%
response.setHeader("Cache-Control","no-cache"); //HTTP 1.1
response.setHeader("Pragma","no-cache"); //HTTP 1.0
response.setDateHeader ("Expires", 0); //prevents caching at the proxy server
%>
<html>
<head>
<ieci:baseInvesDoc/>
<link rel="stylesheet" type="text/css" href="include/css/common.css"/>
<link rel="stylesheet" type="text/css" href="include/css/xtree.css">
<script type="text/javascript" language="JavaScript" src="include/js/tree.js"></script>
<script src="include/js/validations.js" type="text/javascript"></script>

<style>
body { background: white; color: black; }
</style>
</head>
<body>

<script>
	var appBase = '<c:out value="${pageContext.request.contextPath}"/>';
	function eventExpand(id){
    }
    
    function carga(s,name)
    {
    	var check = chequearSession('<html:rewrite page="/chequearSession.do"/>');
		if( check == "false" ) {    
		    parent.propiedades.location.href = appBase + '/volume/volumeList.do?id='+s+'&tipo=2';
	    	parent.edicion.location.href = appBase + '/blank.do';   	
	    	parent.parent.header.document.getElementById('status').innerHTML = "<bean:message key='message.comun.miga.inicio.volumenes.listas'/>" + " | " + name ;
		} else {
			window.parent.redirect('<%=AutenticacionAdministracion.obtenerUrlLogin(request, ConstantesGestionUsuariosAdministracion.APLICACION_REPOSITORIOS_DOCUMENTALES) %>');	
		}	    	
    }
    function nodoRaizEvent()
    {
    	var check = chequearSession('<html:rewrite page="/chequearSession.do"/>');
		if( check == "false" ) {     
		    parent.parent.header.document.getElementById('status').innerHTML = "<bean:message key='message.comun.miga.inicio.volumenes.listas'/>" ;
	    	parent.propiedades.location.href = appBase + '/volume/ListNewOnRoot.jsp';
	    	parent.edicion.location.href = appBase + '/blank.do';
		} else {
			window.parent.redirect('<%=AutenticacionAdministracion.obtenerUrlLogin(request, ConstantesGestionUsuariosAdministracion.APLICACION_REPOSITORIOS_DOCUMENTALES) %>');	
		}
    }
    
	parent.document.getElementById('carga_volumen').style.visibility = 'hidden';
    parent.document.getElementById('carga_volumen').style.position = 'absolute';
    parent.document.getElementById('divListTree').style.position = 'relative';
    parent.document.getElementById('divListTree').style.width = '240px';
    parent.document.getElementById('divListTree').style.height = '260px';
	parent.document.getElementById('divListTree').style.visibility = 'visible';
		
	<c:out value="${requestScope.tree}" escapeXml="false"></c:out>
	document.write(tree);    
	
 </script>
</body>

</html>