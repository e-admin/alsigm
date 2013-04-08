<%@ page language="java"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ page import="ieci.tecdoc.sgm.autenticacion.utils.Defs" %>
<%@page import="java.util.Locale"%>
<%@page import="java.io.File"%>
<html:html locale="true">
<head>
	<%
	String redireccion = (String)request.getParameter(Defs.REDIRECCION);
	String tramiteId = (String)request.getParameter(Defs.TRAMITE_ID);
	String sessionId = (String)request.getParameter(Defs.SESION_ID);
	String entidadId = (String)request.getParameter(Defs.ENTIDAD_ID);
	String lang = (String)request.getParameter(Defs.LANG);
	String country = (String)request.getParameter(Defs.COUNTRY);
	if (redireccion == null)
		redireccion = new String("");
	if (tramiteId == null)
		tramiteId = new String("");
	if (sessionId == null)
		sessionId = new String("");
	if (entidadId == null)
		entidadId = new String("");
	if (lang == null)
		lang = new String("");
	if (country == null)
		country = new String("");

	if (lang != null && !"".equals(lang) && country != null && !"".equals(country)) {
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
    <META HTTP-EQUIV="Refresh" CONTENT="0;URL=seleccionEntidad.do?<%=Defs.REDIRECCION%>=<%=redireccion%>&<%=Defs.TRAMITE_ID%>=<%=tramiteId%>&<%=Defs.SESION_ID%>=<%=sessionId%>&<%=Defs.ENTIDAD_ID%>=<%=entidadId%>&<%=Defs.LANG%>=<%=lang%>&<%=Defs.COUNTRY%>=<%=country%>">
</head>

<body>
<p><bean:message key="cargando"/></p>
</body>
</html:html>
