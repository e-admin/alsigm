<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<%@page import="ieci.tecdoc.sgm.usuario.admin.struts.Constants"%>

<%
	String url = (String)request.getAttribute(Constants.URL_REDIRECCION);
%>

<html>
	<%	if (url != null && !"".equals(url)) { %>
	<head>
		<META HTTP-EQUIV="Refresh" CONTENT="0;URL=<%=url%>">	
	</head>
	<body>
		<p>
			<bean:message key="cargando"/>
		</p>
	</body>
	<% } %>
</html>