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

<html:html>
	<head>

	</head>
	<body onload="javascript:parent.document.location.href = parent.document.location.href;">
	
	</body>
</html:html>