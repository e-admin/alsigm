<!--
JSP de entrada a la aplicación.
Redirecciona al usuario al default.htm. 
Captura el lenguaje del navegador.
-->


<%@page import="com.ieci.tecdoc.isicres.desktopweb.utils.RequestUtils"%>
<%@page import="com.ieci.tecdoc.common.utils.Configurator"%>
<%@page import="com.ieci.tecdoc.common.keys.ConfigurationKeys"%>
<%@page import="com.ieci.tecdoc.isicres.servlets.core.IndexJspHelper"%>
<%@ include file="__headerLocale.jsp" %>


<%

	IndexJspHelper indexJspHelper = new IndexJspHelper();
	
	indexJspHelper.doWork(request,response);
	
	
%>