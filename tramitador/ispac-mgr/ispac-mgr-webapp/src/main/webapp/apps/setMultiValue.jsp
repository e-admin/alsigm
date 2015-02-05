<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/ispac-util.tld" prefix="ispac" %>
<%
	String parameters = request.getParameter("property(parameters)");
%>

<%-- [ildfns]--%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html">
<logic:present name="ValueList">
	<ispac:updatefields name="ValueList" parameters='<%= parameters %>'/>
</logic:present>
<ispac:hideframe event="false"/>
</head>
<body/>
</html>