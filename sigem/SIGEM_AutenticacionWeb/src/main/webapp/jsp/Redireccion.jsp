<%@ page language="java"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>

<%@ page import="ieci.tecdoc.sgm.autenticacion.utils.Defs" %>
<%@page import="java.util.Locale"%>

<%
String rutaEstilos = (String)session.getAttribute("PARAMETRO_RUTA_ESTILOS");
if (rutaEstilos == null) rutaEstilos = "";
String rutaImagenes = (String)session.getAttribute("PARAMETRO_RUTA_IMAGENES");
if (rutaImagenes == null) rutaImagenes = "";
%>

<html:html locale="true">
	<head>
		<%
		String path = request.getContextPath();
		String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
		%> 
		<base href="<%= basePath %>" />
		<link rel="stylesheet" href="css/<%=rutaEstilos%>estilos.css" type="text/css" />
		
		<%
		String url = (String)request.getSession().getAttribute(Defs.URL_REDIRECCION);
		String puerto = (String)request.getSession().getAttribute(Defs.URL_PUERTO);
		String sesionId = (String)request.getSession().getAttribute(Defs.SESION_ID);
		String tramiteId = (String)request.getSession().getAttribute(Defs.TRAMITE_ID);
		String entidadId = (String)request.getSession().getAttribute(Defs.ENTIDAD_ID);
		Locale locale = (Locale)request.getSession().getAttribute("org.apache.struts.action.LOCALE");
		String lang = locale.getLanguage();
		String country = locale.getCountry();
		//String url_completa = "https://"+request.getServerName()+":"+puerto+"/"+url+"?"+Defs.ENTIDAD_ID+"="+entidadId+"&"+Defs.TRAMITE_ID+"="+tramiteId+"&"+Defs.SESION_ID+"="+sesionId+"&"+Defs.LANG+"="+lang+"&"+Defs.COUNTRY+"="+country;
		String url_completa = "../"+url+"?"+Defs.ENTIDAD_ID+"="+entidadId+"&"+Defs.TRAMITE_ID+"="+tramiteId+"&"+Defs.SESION_ID+"="+sesionId+"&"+Defs.LANG+"="+lang+"&"+Defs.COUNTRY+"="+country;
		%>
		<script language="Javascript">
			document.location.href = '<%= url_completa%>';
		</script>
	</head>
	<body>
	
	</body>
</html:html>