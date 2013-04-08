<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>

<%@page import="java.util.Locale"%>
<%@page import="ieci.tecdoc.core.base64.Base64Util"%>
<%@page import="ieci.tecdoc.sgm.core.services.gestion_backoffice.ConstantesGestionUsuariosBackOffice"%>
<%@page import="ieci.tecdoc.sgm.backoffice.utils.Defs"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<%
	String key = (String) session
			.getAttribute(ConstantesGestionUsuariosBackOffice.PARAMETRO_KEY_SESION_USUARIO);
	String url = (String) request.getAttribute(Defs.PARAMETRO_URL);

	// SLuna-20081217-I

	// Usamos la 'llave' creada con el usuario codificado porque vamos a entrar
	// en el módulo de Registro Presencial
	if (((String) session
			.getAttribute(ConstantesGestionUsuariosBackOffice.PARAMETRO_ID_APLICACION))
			.equals(ConstantesGestionUsuariosBackOffice.APLICACION_REGISTRO_PRESENCIAL)) {

		key = (String) session.getAttribute("keySesionUsuarioRP");

	}

	// SLuna-20081217-F

	session
			.removeAttribute(ConstantesGestionUsuariosBackOffice.PARAMETRO_ID_APLICACION);
	session
			.removeAttribute(ConstantesGestionUsuariosBackOffice.PARAMETRO_ID_ENTIDAD);
	session
			.removeAttribute(ConstantesGestionUsuariosBackOffice.PARAMETRO_USUARIO);
	session
			.removeAttribute(ConstantesGestionUsuariosBackOffice.PARAMETRO_PASSWORD);
	
	Locale locale = (Locale) session.getAttribute("org.apache.struts.action.LOCALE");
	String idiomaStr = "";
	if (locale!=null)
		idiomaStr = locale.getLanguage()+"_"+locale.getCountry();
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
	<p><bean:message key="cargando"/></p>
	<form action="<html:rewrite href="<%=url%>"/>" method="POST">
		<input type="hidden" name="<%=ConstantesGestionUsuariosBackOffice.PARAMETRO_KEY_SESION_USUARIO%>" id="<%=ConstantesGestionUsuariosBackOffice.PARAMETRO_KEY_SESION_USUARIO%>" value="<%=key%>" />
		<input type="hidden" name="<%=Defs.PARAMETRO_IDIOMA_STR%>" id="<%=Defs.PARAMETRO_IDIOMA_STR%>" value="<%=idiomaStr%>" />
	</form>
</body>
</html>
