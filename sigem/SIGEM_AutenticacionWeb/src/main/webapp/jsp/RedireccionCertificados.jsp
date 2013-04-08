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
			serverPort = proxyHttpsSiCertPort;
		}
		else {
			serverPort = PortsConfig.getCertPort();
		}
		%>
		<script language="Javascript">
			function redirige(){
				document.location.href = '<%="https://"+request.getServerName()+":"+serverPort+request.getContextPath()+"/validacionCertificado.do"%>';
			}
		</script>
	</head>
	<body onLoad="javascript:redirige()">
	</body>
</html:html>