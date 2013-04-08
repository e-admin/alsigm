<%@ page contentType="text/html; charset=ISO-8859-1" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>

<html:html lang="true">
<head>
	<%
		String url = new String();
		url=ieci.tecdoc.sgm.core.user.web.WebAuthenticationHelper.getWebAuthDesconectURL(request);
		request.getSession().invalidate();
	%>
	<META HTTP-EQUIV="Refresh" CONTENT="0;URL=<%=url%>">
</head>

<body>
<p><bean:message key="cargando"/></p>
</body>
</html:html>
