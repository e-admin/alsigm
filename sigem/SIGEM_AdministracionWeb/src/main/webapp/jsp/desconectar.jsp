<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<%@page import="ieci.tecdoc.sgm.admsistema.util.Defs"%>

<%
String Url = (String)request.getAttribute(Defs.PARAMETRO_URL_REDIRECCION);
%>

<html>
	<head>
		<META HTTP-EQUIV="Refresh" CONTENT="0;URL=<%=Url%>">
	</head>
	<body>
		<p>
			<bean:message key="cargando"/>
		</p>
	</body>
</html>