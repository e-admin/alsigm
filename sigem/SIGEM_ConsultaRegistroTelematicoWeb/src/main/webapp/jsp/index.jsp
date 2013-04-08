<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>

<%@page import="java.util.ArrayList"%>
<%@page import="ieci.tecdoc.sgm.core.services.idioma.LectorIdiomas"%>
<%@page import="java.util.Locale"%>
<%@page import="ieci.tecdoc.sgm.consulta_telematico.utils.Defs"%>
<%@page import="java.io.File"%>
<html:html locale="true">
<head>
	<%
	String sessionId = (String)request.getParameter("SESION_ID");
	if (sessionId == null || sessionId.equals(""))
		sessionId = (String)request.getSession().getAttribute("SESION_ID");
	String entidadId = (String)request.getParameter("ENTIDAD_ID");
	if (entidadId == null || entidadId.equals(""))
		entidadId = (String)request.getSession().getAttribute("ENTIDAD_ID");
	
	ArrayList idiomas = LectorIdiomas.getIdiomas();
	session.setAttribute(Defs.IDIOMAS_DISPONIBLES, idiomas);
	
	String lang = (String)request.getParameter(Defs.LANG);
	String country = (String)request.getParameter(Defs.COUNTRY);
	
	if (lang == null || "".equals(lang))
		lang = "es";
	if (country == null || "".equals(country))
		country = "es";
	
	if (lang!=null && country!=null)
		request.getSession().setAttribute("org.apache.struts.action.LOCALE", new Locale(lang, country));

	if (entidadId != null && !entidadId.equals("")) {
		String ruta = request.getRealPath("");
		String ruta_entera = ruta + "/img/" + entidadId;
		File f = new File(ruta_entera);
		if (f.exists())	
			session.setAttribute("PARAMETRO_RUTA_IMAGENES", entidadId + "/");
		else
			session.setAttribute("PARAMETRO_RUTA_IMAGENES", "");
		
		ruta_entera = ruta + "/css/" + entidadId;
		f = new File(ruta_entera);
		if (f.exists())	
			session.setAttribute("PARAMETRO_RUTA_ESTILOS", entidadId + "/");
		else
			session.setAttribute("PARAMETRO_RUTA_ESTILOS", "");
	}
	%>
    <META HTTP-EQUIV="Refresh" CONTENT="0;URL=listadoRegistros.do?ENTIDAD_ID=<%=entidadId%>&SESION_ID=<%=sessionId%>"/>
</head>

<body>
<p><bean:message key="mensaje.cargando"/></p>
</body>
</html:html>
