<%@ page language="java"%>
<%@ page contentType="text/html; charset=ISO-8859-1" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>

<%@page import="ieci.tecdoc.sgm.ct.utilities.Misc"%>

<%
	// Eliminar el acceso
	session.removeAttribute(Misc.SESION_ID);
	session.removeAttribute(Misc.ENTIDAD_ID);
	session.removeAttribute(Misc.PERMISO_CE);

	// Limpiar el formulario de búsqueda
	session.removeAttribute(Misc.CNIF);
	session.removeAttribute(Misc.EXPEDIENTE);
	session.removeAttribute(Misc.PROCEDIMIENTO);
	session.removeAttribute(Misc.NUMERO_REGISTRO_INICIAL);
	session.removeAttribute(Misc.FECHA_DESDE);
	session.removeAttribute(Misc.FECHA_DESDE_BUSQUEDA);
	session.removeAttribute(Misc.OPERADOR_CONSULTA);
	session.removeAttribute(Misc.FECHA_HASTA);
	session.removeAttribute(Misc.FECHA_HASTA_BUSQUEDA);
	session.removeAttribute(Misc.FECHA_REGISTRO_INICIAL_DESDE);
	session.removeAttribute(Misc.FECHA_REGISTRO_INICIAL_DESDE_BUSQUEDA);
	session.removeAttribute(Misc.OPERADOR_CONSULTA_FECHA_INICIAL);
	session.removeAttribute(Misc.FECHA_REGISTRO_INICIAL_HASTA);
	session.removeAttribute(Misc.FECHA_REGISTRO_INICIAL_HASTA_BUSQUEDA);
	session.removeAttribute(Misc.ESTADO);

	String url = ieci.tecdoc.sgm.core.admin.web.AutenticacionBackOffice.obtenerUrlLogout(request);
%>

<html:html>
<head>
    <META HTTP-EQUIV="Refresh" CONTENT="0;URL=<%=url%>"/>
</head>

<body>
<p><bean:message key="cargando"/></p>
</body>
</html:html>
