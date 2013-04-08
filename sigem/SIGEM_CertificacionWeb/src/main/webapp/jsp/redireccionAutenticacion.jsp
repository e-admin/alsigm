<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>

<%
String rutaEstilos = (String)session.getAttribute("PARAMETRO_RUTA_ESTILOS");
if (rutaEstilos == null) rutaEstilos = "";
String rutaImagenes = (String)session.getAttribute("PARAMETRO_RUTA_IMAGENES");
if (rutaImagenes == null) rutaImagenes = "";
%>

<%@page import="ieci.tecdoc.sgm.certificacion.utilsweb.Defs"%>
<html xmlns="http://www.w3.org/1999/xhtml" lang="es" xml:lang="es">
<head>
	<%
		String path = request.getContextPath();
		String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	%> 
		<base href="<%= basePath %>" />
		<link rel="stylesheet" href="css/<%=rutaEstilos%>estilos.css" type="text/css" />
	<%
		//int port = request.getServerPort();
		//String dir = request.getScheme() + "://"+request.getServerName() + ":" + port + "/" + session.getServletContext().getAttribute(Defs.PLUGIN_REDIRAUTENTICACION);
		String dir = "../" + session.getServletContext().getAttribute(Defs.PLUGIN_REDIRAUTENTICACION);
	%>
    <META HTTP-EQUIV="Refresh" CONTENT="0;URL=<%=dir%>">
</head>

<body>
<p><bean:message key="cargando"/></p>
</body>
</html>