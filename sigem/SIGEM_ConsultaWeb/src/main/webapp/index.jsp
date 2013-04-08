<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>

<%@page import="java.util.ArrayList"%>
<%@page import="ieci.tecdoc.sgm.core.services.idioma.LectorIdiomas"%>
<%@page import="ieci.tecdoc.sgm.ct.utilities.Misc"%>
<%@page import="java.util.Locale"%>
<%@page import="java.io.File"%>

<html:html lang="true">

<head>
	<%
	String sessionId = (String)request.getParameter(Misc.SESION_ID);
	if (sessionId == null || sessionId.equals(""))
		sessionId = (String)request.getSession().getAttribute(Misc.SESION_ID);
	String entidadId = (String)request.getParameter("ENTIDAD_ID");
	if (entidadId == null || entidadId.equals(""))
		entidadId = (String)request.getSession().getAttribute("ENTIDAD_ID");
	
	ArrayList idiomas = LectorIdiomas.getIdiomas();
	session.setAttribute(Misc.IDIOMAS_DISPONIBLES, idiomas);
	
	String lang = (String)request.getParameter(Misc.LANG);
	String country = (String)request.getParameter(Misc.COUNTRY);
	
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
    <META HTTP-EQUIV="Refresh" CONTENT="0;URL=ListaExpedientes.do?ENTIDAD_ID=<%=entidadId%>&SESION_ID=<%=sessionId%>"/>
</head>

<body>
<p><bean:message key="cargando"/></p>
</body>
</html:html>
