<%@ page language="java"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>

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

<html:html lang="true">
	<head>

	</head>
	<body onload="javascript:parent.document.location.reload();">
	
	</body>
</html:html>