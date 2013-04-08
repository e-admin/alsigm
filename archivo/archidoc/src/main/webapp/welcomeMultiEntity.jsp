<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>

<%@page import="ieci.tecdoc.sgm.core.services.gestion_backoffice.ConstantesGestionUsuariosBackOffice"%>
<%@page import="ieci.tecdoc.sgm.core.admin.web.AutenticacionBackOffice"%>
<%@page import="gcontrol.ControlAccesoConstants"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">

<%
	String url = (String)request.getAttribute(ControlAccesoConstants.MULTIENTITY_LOGOUT_URL);
	if ((url == null) || ("".equals(url))){
		url = AutenticacionBackOffice.obtenerUrlLogin(request,ConstantesGestionUsuariosBackOffice.APLICACION_ARCHIVO);
	} else {
		url = "../" + url;
	}
%>

<html>
	<head>
		<script language="Javascript">
			function redirigir(){
				document.forms[0].submit();	
			}
		</script>
	</head>

	<body onload="javascript:redirigir();">			
		<p><bean:message key="archigest.multientity.logout.cargando"/></p>
		<form action="<html:rewrite href="<%=url%>"/>" method="POST">
		</form>
	</body>
</html>