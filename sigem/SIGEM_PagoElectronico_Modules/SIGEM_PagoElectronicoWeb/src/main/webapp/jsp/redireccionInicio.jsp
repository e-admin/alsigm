<%@ page language="java"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>

<%@page import="java.util.ArrayList"%>
<%@page import="ieci.tecdoc.sgm.core.services.idioma.LectorIdiomas"%>
<%@page import="ieci.tecdoc.sgm.pe.struts.Constantes"%>
<%@page import="java.util.Locale"%>

<%
String rutaEstilos = (String)session.getAttribute("PARAMETRO_RUTA_ESTILOS");
if (rutaEstilos == null) rutaEstilos = "";
String rutaImagenes = (String)session.getAttribute("PARAMETRO_RUTA_IMAGENES");
if (rutaImagenes == null) rutaImagenes = "";
%>

<%@page import="java.io.File"%>
<%@page import="ieci.tecdoc.sgm.pe.struts.PagoElectronicoManagerHelper"%>

<html:html>
	<head>
		<%
		ArrayList idiomas = LectorIdiomas.getIdiomas();
		session.setAttribute(Constantes.IDIOMAS_DISPONIBLES, idiomas);

		String lang = (String)request.getParameter(Constantes.KEY_LANGUAGE);
		String country = (String)request.getParameter(Constantes.KEY_COUNTRY);
	
		if (lang!=null && country!=null)
			request.getSession().setAttribute("org.apache.struts.action.LOCALE", new Locale(lang, country));

		String idEntidad = request.getParameter("ENTIDAD_ID");
		
		if (idEntidad != null && !idEntidad.equals("")) {
			String ruta = request.getRealPath("");
			String ruta_entera = ruta + "/img/" + idEntidad;
			File f = new File(ruta_entera);
			if (f.exists())	
				session.setAttribute("PARAMETRO_RUTA_IMAGENES", idEntidad + "/");
			else
				session.setAttribute("PARAMETRO_RUTA_IMAGENES", "");
			
			ruta_entera = ruta + "/css/" + idEntidad;
			f = new File(ruta_entera);
			if (f.exists())	
				session.setAttribute("PARAMETRO_RUTA_ESTILOS", idEntidad + "/");
			else
				session.setAttribute("PARAMETRO_RUTA_ESTILOS", "");
		}
		
		String path = request.getContextPath();
		String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
		%> 
		<base href="<%= basePath %>" />
		<link rel="stylesheet" href="css/<%=rutaEstilos%>estilos.css" type="text/css" />
		<%
		//String port = "4443";
		//if (request.isSecure()) {
		//  port = String.valueOf(request.getServerPort());
		//}
		//String dir = "https://" + request.getServerName() + ":" + port + request.getContextPath() + "/login.do?ENTIDAD_ID=" + request.getParameter("ENTIDAD_ID") + "&SESION_ID=" + request.getParameter("SESION_ID");
		
		String dir = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/login.do?ENTIDAD_ID=" + request.getParameter("ENTIDAD_ID") + "&SESION_ID=" + request.getParameter("SESION_ID");
		%>
		<META HTTP-EQUIV="Refresh" CONTENT="0;URL=<%=dir%>">
	</head>
	<body>
		<p><bean:message key="cargando"/></p>
	</body>
</html:html>