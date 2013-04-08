<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%--<logic:redirect page="/inicio.do" />--%>
<html>
<head>
</head>
<body onload="document.forms[0].submit()">
<form name="frmInicio" action="<html:rewrite page="/inicio.do"/>">
	<% String idEntidad = request.getParameter("idEntidad"); %>
	<input type="hidden" name="idEntidad" value="<%=idEntidad %>">
</form>
</body>
</html>