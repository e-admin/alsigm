<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>

<%
	String url = ieci.tecdoc.sgm.core.admin.web.AutenticacionBackOffice.obtenerUrlLogin(request,
			ieci.tecdoc.sgm.core.services.gestion_backoffice.ConstantesGestionUsuariosBackOffice.APLICACION_CONSULTA_REGISTROS_TELEMATICOS);
%>

<html:html>
<head>
    <META HTTP-EQUIV="Refresh" CONTENT="0;URL=<%=url%>"/>
</head>

<body>
<p><bean:message key="mensaje.cargando"/></p>
</body>
</html:html>
