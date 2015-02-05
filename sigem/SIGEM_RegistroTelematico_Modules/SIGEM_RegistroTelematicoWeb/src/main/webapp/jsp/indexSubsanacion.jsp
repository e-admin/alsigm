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
	String numExpediente = (String)request.getParameter("numExpediente");
	if (numExpediente == null || numExpediente.equals(""))
		numExpediente = (String)request.getSession().getAttribute("numExpediente");
	String numRegistro = (String)request.getParameter("numRegistro");
	if (numRegistro == null || numRegistro.equals(""))
		numRegistro = (String)request.getSession().getAttribute("numRegistro");
	String idDocumento = (String)request.getParameter("idDocumento");
	if (idDocumento == null || idDocumento.equals(""))
		idDocumento = (String)request.getSession().getAttribute("idDocumento");
	

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
    <META HTTP-EQUIV="Refresh" CONTENT="0;URL=/SIGEM_RegistroTelematicoWeb/prepararSubsanacion.do?tramiteId=<%=tramiteId%>&numExpediente=<%=numExpediente%>&SESION_ID=<%=sessionId%>&ENTIDAD_ID=<%=entidadId%>&numRegistro=<%=numRegistro%>&idDocumento=<%=idDocumento%>"/>
</head>

<body>
<p><bean:message key="cargando"/></p>
</body>
</html>
