<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="java.util.ArrayList"%>
<%@page import="ieci.tecdoc.sgm.core.services.idioma.LectorIdiomas"%>
<%@page import="ieci.tecdoc.sgm.registro.utils.Defs"%>
<%@page import="java.util.Locale"%>
<%@page import="java.io.File"%>
<html lang="es" xml:lang="es">
<head>
	<%
	String sessionId = (String)request.getParameter("SESION_ID");
	if (sessionId == null || sessionId.equals(""))
		sessionId = (String)request.getSession().getAttribute("SESION_ID");
	String entidadId = (String)request.getParameter("ENTIDAD_ID");
	if (entidadId == null || entidadId.equals(""))
		entidadId = (String)request.getSession().getAttribute("ENTIDAD_ID");
	String tramiteId = (String)request.getParameter("tramiteId");
	if (tramiteId == null || tramiteId.equals(""))
		tramiteId = (String)request.getSession().getAttribute("tramiteId");
	String lang = (String)request.getParameter(Defs.LANG);
	if (lang == null || lang.equals(""))
		lang = (String)request.getSession().getAttribute(Defs.LANG);
	String country = (String)request.getParameter(Defs.COUNTRY);
	if (country == null || country.equals(""))
		country = (String)request.getSession().getAttribute(Defs.COUNTRY);

	String idioma = new String("");
	if (lang != null && !"".equals(lang) && country != null && !"".equals(country)) {
		idioma = lang + "_" + country;
		request.getSession().setAttribute("org.apache.struts.action.LOCALE", new Locale(lang, country));
	}

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
    <META HTTP-EQUIV="Refresh" CONTENT="0;URL=realizarSolicitudRegistro.do?ENTIDAD_ID=<%=entidadId%>&SESION_ID=<%=sessionId%>&tramiteId=<%=tramiteId%>&idioma=<%=idioma%>"/>
</head>

<body>
<p><bean:message key="cargando"/></p>
</body>
</html>
