<%@ taglib uri="/WEB-INF/ispac-util.tld" prefix="ispac" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>

<%@ page import="ieci.tecdoc.sgm.core.config.ports.PortsConfig"%>

<html>
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
				// HTTP
				serverPort = proxyHttpPort;
				// HTTPs sin certificado
				// serverPort = proxyHttpsNoCertPort;
			}
			else {
				// HTTP
				serverPort = PortsConfig.getHttpPort();
				// HTTPs sin certificado
				// serverPort = PortsConfig.getHttpsPort();
			}

			String appletDownload = "http://" + request.getServerName() + ":" + serverPort;
		%>

		<title><bean:message key="head.title"/></title>
		<link rel="stylesheet" href='<ispac:rewrite href="css/styles.css"/>'/>
	</head>
	<body>
		<ispac:rewrite id="appletsDir" href="../applets"/>

		<applet code="ieci.tdw.applets.applauncher.AppLauncherApplet"
				codeBase='<%=appletDownload%><ispac:rewrite href="../../"/>'
				archive='<%=appletDownload + appletsDir + "/applauncherapplet.jar"%>'
				width='0' height='0'>
			<param name="config" value="true"/>
		</applet>
	</body>
</html>