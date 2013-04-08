<%@ page language="java"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>

<%@page import="java.util.Locale"%>

<%
String idioma = request.getParameter("idioma");
if (idioma != null && !"".equals(idioma)){
	int index = idioma.indexOf("_");
	String lang = idioma.substring(0, index);
	String country = idioma.substring(index+1, idioma.length());
	request.getSession().setAttribute("org.apache.struts.action.LOCALE", new Locale(lang, country));
}
%>

<html:html locale="true">
	<head>

	</head>
	<body onload="javascript:parent.document.location.reload();">
	
	</body>
</html:html>