<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@page import="ieci.tecdoc.sgm.consulta_telematico.utils.Defs"%>

<%
String rutaEstilos = (String)session.getAttribute("PARAMETRO_RUTA_ESTILOS");
if (rutaEstilos == null) rutaEstilos = "";
String rutaImagenes = (String)session.getAttribute("PARAMETRO_RUTA_IMAGENES");
if (rutaImagenes == null) rutaImagenes = "";
%>

<html:html locale="true">
<head>
	<title><bean:message key="titulo.aplicacion"/></title>
	<%
		String path = request.getContextPath();
		String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	%> 
		<base href="<%= basePath %>" />
		<link rel="stylesheet" href="css/<%=rutaEstilos%>estilos.css" type="text/css" />
	<%
		int port = request.getServerPort();
		String dir = request.getScheme() + "://"+request.getServerName() + ":" + port + "/" + session.getServletContext().getAttribute(Defs.REDIRECCION_AUTENTICACION);
	%>
    <META HTTP-EQUIV="Refresh" CONTENT="0;URL=<%=dir%>">
</head>

<body>
<p><bean:message key="mensaje.cargando"/></p>
</body>
</html:html>