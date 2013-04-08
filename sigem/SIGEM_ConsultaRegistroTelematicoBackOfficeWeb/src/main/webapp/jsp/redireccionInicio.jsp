<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>

<%@page import="ieci.tecdoc.sgm.consulta_telematico.utils.Defs"%>
<html:html>
<head>
	<%
		String dir = (String)request.getAttribute(Defs.URL);
	%>
    <META HTTP-EQUIV="Refresh" CONTENT="0;URL=<%=dir%>">
</head>

<body>
<p><bean:message key="mensaje.cargando"/></p>
</body>
</html:html>