<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>

<%@page import="java.util.Locale"%>
<%@page import="ieci.tecdoc.sgm.core.services.gestion_administracion.ConstantesGestionUsuariosAdministracion"%>
<%@page import="ieci.tecdoc.sgm.administracion.utils.Defs"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<%
	String key = (String)session.getAttribute(ConstantesGestionUsuariosAdministracion.PARAMETRO_KEY_SESION_USUARIO_ADM);
	String keyEntidad = (String)session.getAttribute(ConstantesGestionUsuariosAdministracion.PARAMETRO_KEY_SESION_USUARIO_ADM_ENTIDAD);

	String url = (String)request.getAttribute(Defs.PARAMETRO_URL);
	
	session.removeAttribute(ConstantesGestionUsuariosAdministracion.PARAMETRO_ID_APLICACION);
	//session.removeAttribute(ConstantesGestionUsuariosAdministracion.PARAMETRO_ID_ENTIDAD);
	session.removeAttribute(ConstantesGestionUsuariosAdministracion.PARAMETRO_USUARIO);
	session.removeAttribute(ConstantesGestionUsuariosAdministracion.PARAMETRO_PASSWORD);
	
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
		<input type="hidden" name="<%=ConstantesGestionUsuariosAdministracion.PARAMETRO_KEY_SESION_USUARIO_ADM%>" id="<%=ConstantesGestionUsuariosAdministracion.PARAMETRO_KEY_SESION_USUARIO_ADM%>" value="<%=key%>" />
		<input type="hidden" name="<%=ConstantesGestionUsuariosAdministracion.PARAMETRO_KEY_SESION_USUARIO_ADM_ENTIDAD%>" id="<%=ConstantesGestionUsuariosAdministracion.PARAMETRO_KEY_SESION_USUARIO_ADM_ENTIDAD%>" value="<%=keyEntidad%>" />
		<input type="hidden" name="<%=Defs.PARAMETRO_IDIOMA_STR%>" id="<%=Defs.PARAMETRO_IDIOMA_STR%>" value="<%=idiomaStr%>" />
	</form>
</body>
</html>
