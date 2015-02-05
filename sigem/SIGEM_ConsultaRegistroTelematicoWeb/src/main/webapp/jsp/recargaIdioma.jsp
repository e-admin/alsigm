<%@ page language="java"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>

<%@page import="java.util.Locale"%>
<%@page import="ieci.tecdoc.sgm.consulta_telematico.utils.Defs"%>

<%
String refresco = request.getParameter(Defs.REFRESCO);
boolean bRefresco = true;
if (refresco != null && !"".equals(refresco))
	bRefresco = new Boolean(refresco).booleanValue();
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
	<%if (bRefresco){%>
		<body onload="javascript:parent.document.location.reload();">
	<%}else{ %>
		<body onload="javascript:parent.document.location.href = parent.document.location.href + '?<%=Defs.REFRESCO%>=false'">
	<%}%>
	</body>
</html:html>