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
<c:if test="${ requestScope.borraCookies}">
    <script type="text/javascript" language="javaScript" src="include/js/cookies.js"></script>
</c:if>

<script type="text/javascript" language="JavaScript" src="include/js/tree.js"></script>
<script src="include/js/validations.js" type="text/javascript"></script>
<style>
body { background: white; color: black; }
</style>
</head>
<body>

<script>
	var appBase = '<c:out value="${pageContext.request.contextPath}"/>';
	var typeTree = '<bean:write name="typeTree" />'
	function eventExpand(id){
		var check = chequearSession('<html:rewrite page="/chequearSession.do"/>');
		if( check == "false" ) {
			if( typeTree == 1)
				document.location.href = appBase + '/user/ldap/userTree.do?id='+id;
			else if( typeTree == 2)
				document.location.href = appBase + '/user/ldap/groupTree.do?id='+id;
		} else {
    		window.parent.redirect('<%=AutenticacionAdministracion.obtenerUrlLogin(request, ConstantesGestionUsuariosAdministracion.APLICACION_ESTRUCTURA_ORGANIZATIVA) %>');	
    	}
    }
    function carga(s)
    {
    	var check = chequearSession('<html:rewrite page="/chequearSession.do"/>');
    	if( check == "false" ) {
    	  var elementProp = parent.document.getElementById("propiedadesText");
		  elementProp.style.visibility = "visible";
	      //parent.propiedadesText.style.visibility = "visible";
	      //parent.estirarIframePropiedades();
	      parent.typeTree = typeTree;
	      //parent.titulo.innerHTML = '<bean:message key="message.comun.etiqueta.propiedades" />';
	      //if(typeTree == "1") parent.fillBotones();
		  //else parent.emptyBotones(); 
		  parent.propiedades.location.href = appBase + '/user/ldap/properties.do?id='+s+"&typeTree="+typeTree;
	      
    	} else {
    		window.parent.redirect('<%=AutenticacionAdministracion.obtenerUrlLogin(request, ConstantesGestionUsuariosAdministracion.APLICACION_ESTRUCTURA_ORGANIZATIVA) %>');	
    	}
    }
    function nodoRaizEvent()
    {
    	var check = chequearSession('<html:rewrite page="/chequearSession.do"/>');
    	if( check == "false" ) {
    		if( typeTree == 1)
				document.location.href = appBase + '/user/ldap/userTree.do';
			else if( typeTree == 2)
				document.location.href = appBase + '/user/ldap/groupTree.do';
			
			parent.propiedadesText.style.visibility = "hidden";
	    	// parent.edicion.location.href = appBase + '/blank.do';
	    } else {
    		window.parent.redirect('<%=AutenticacionAdministracion.obtenerUrlLogin(request, ConstantesGestionUsuariosAdministracion.APLICACION_ESTRUCTURA_ORGANIZATIVA) %>');	
    	}
    }
	<c:out value="${requestScope.tree}" escapeXml="false"></c:out>
	document.write(tree);    
 </script>
</body>

</html>