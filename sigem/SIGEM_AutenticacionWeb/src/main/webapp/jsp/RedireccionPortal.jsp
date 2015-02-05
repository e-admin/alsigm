<%@ page language="java"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ page import="ieci.tecdoc.sgm.core.config.ports.PortsConfig"%>

<html:html locale="true">
	<head>
		<%
		String serverPort = String.valueOf(request.getServerPort());
		
		String proxyHttpPort = PortsConfig.getHttpFrontendPort();
		String proxyHttpsNoCertPort = PortsConfig.getHttpsFrontendPort();
		String proxyHttpsSiCertPort = PortsConfig.getHttpsFrontendAuthclientPort();
		
		if ((proxyHttpPort != null && proxyHttpPort.equals(serverPort)) ||
			(proxyHttpsNoCertPort != null && proxyHttpsNoCertPort.equals(serverPort)) ||
			(proxyHttpsSiCertPort != null && proxyHttpsSiCertPort.equals(serverPort))) {
			
			// Servidor Frontend por delante del Servidor de Aplicaciones (Ej: APACHE + TOMCAT)
			serverPort = proxyHttpPort;
		}
		else {
			serverPort = PortsConfig.getHttpPort();
		}
		
		String dir = "http://" + request.getServerName() + ":" + serverPort + "/portal";
		%>
		<META HTTP-EQUIV="Refresh" CONTENT="0;URL=<%=dir%>">
	</head>
	<body>
		<p><bean:message key="cargando"/></p>
	</body>
</html:html>