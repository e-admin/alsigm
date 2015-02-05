<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>

<%@page import="ieci.tecdoc.sgm.core.admin.web.AutenticacionAdministracion"%>
<%@page import="ieci.tecdoc.sgm.core.services.gestion_administracion.ConstantesGestionUsuariosAdministracion"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">

<%
	String url = AutenticacionAdministracion.obtenerUrlLogin(request, ConstantesGestionUsuariosAdministracion.APLICACION_ARCHIVO);
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