<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>

<%
	String url = ieci.tecdoc.sgm.core.admin.web.AutenticacionBackOffice.obtenerUrlLogin(request,
			ieci.tecdoc.sgm.core.services.gestion_backoffice.ConstantesGestionUsuariosBackOffice.APLICACION_CONSULTA_EXPEDIENTES);
%>

<html:html>
<head>
    <META HTTP-EQUIV="Refresh" CONTENT="0;URL=<%=url%>"/>
</head>

<body>
<p><bean:message key="cargando"/></p>
</body>
</html:html>
