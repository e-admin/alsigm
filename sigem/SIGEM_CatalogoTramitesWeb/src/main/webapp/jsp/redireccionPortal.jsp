<%@ page import="ieci.tecdoc.sgm.core.config.ports.PortsConfig"%>
<%@ page language="java"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>

<html:html>
	<head>
		<%
		String path = request.getContextPath();
		String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
		String http_port = PortsConfig.getHttpPort();
		%> 
		<base href="<%= basePath %>" />
		<link rel="stylesheet" href="css/estilos.css" type="text/css" />
		
		<script language="Javascript">
			function redirige(){
				document.location.href = '<%="http://"+request.getServerName()+":"+http_port+"/portal"%>';
			}
		</script>
	</head>
	<body onLoad="javascript:redirige()">
	
	</body>
</html:html>