<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>

<%@page import="ieci.tecdoc.sgm.core.services.gestion_administracion.ConstantesGestionUsuariosAdministracion"%>

<%
String idEntidad = (String)request.getParameter(ConstantesGestionUsuariosAdministracion.PARAMETRO_ID_ENTIDAD);
if (idEntidad==null) idEntidad = "";
session.setAttribute(ConstantesGestionUsuariosAdministracion.PARAMETRO_ID_ENTIDAD, idEntidad);
%>

<html>
	<head>
	</head>
	<body onload="document.forms[0].submit();">
		<form action="<html:rewrite page="/inicio.do"/>">
			<input type="hidden" id="<%=ConstantesGestionUsuariosAdministracion.PARAMETRO_ID_ENTIDAD%>" name="<%=ConstantesGestionUsuariosAdministracion.PARAMETRO_ID_ENTIDAD %>" value="<%=idEntidad%>" />
		</form>
	</body>
</html>