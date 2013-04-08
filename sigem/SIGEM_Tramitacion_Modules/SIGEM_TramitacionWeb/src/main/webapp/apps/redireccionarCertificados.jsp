<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/ispac-util.tld" prefix="ispac"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ page import="ieci.tecdoc.sgm.core.config.ports.PortsConfig"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<?xml version="1.0" encoding="iso-8859-1"?>
<html:html>
  <head>
    <title><bean:message key="head.title" /></title>

    <link rel="shortcut icon" href='<ispac:rewrite href="img/favicon.ico"/>' type="image/x-icon"/>
  	<link rel="icon" href='<ispac:rewrite href="img/favicon.ico"/>' type="image/x-icon"/>
    <link rel="stylesheet" href='<ispac:rewrite href="css/styles.css"/>'/>
	<link rel="stylesheet" href='<ispac:rewrite href="css/estilos.css"/>'/>
	<link rel="stylesheet" href='<ispac:rewrite href="css/menus.css"/>'/>

	<link rel="stylesheet" type="text/css" href='<ispac:rewrite href="css/estilos_cab.css"/>'/>
	
	<!--[if lte IE 5]>
		<link rel="stylesheet" type="text/css" href='<ispac:rewrite href="css/estilos_ie5.css"/>'/>
		<link rel="stylesheet" type="text/css" href='<ispac:rewrite href="css/estilos_cab_ie5.css"/>'/>
	<![endif]-->	

	<!--[if IE 6]>
		<link rel="stylesheet" type="text/css" href='<ispac:rewrite href="css/estilos_ie6.css"/>'>
		<link rel="stylesheet" type="text/css" href='<ispac:rewrite href="css/estilos_cab_ie6.css"/>'/>
	<![endif]-->	

	<!--[if gte IE 7]>
		<link rel="stylesheet" type="text/css" href='<ispac:rewrite href="css/estilos_ie7.css"/>'>
		<link rel="stylesheet" type="text/css" href='<ispac:rewrite href="css/estilos_cab_ie7.css"/>'/>
	<![endif]-->

    <link rel="stylesheet" href='<ispac:rewrite href="css/nicetabs.css"/>'/>
    <script type="text/javascript" src='<ispac:rewrite href="../scripts/forms.js"/>'></script>
    <script type="text/javascript" src='<ispac:rewrite href="../scripts/sorttable.js"/>'> </script>
    <script type="text/javascript" src='<ispac:rewrite href="../scripts/utilities.js"/>'> </script>
    <ispac:javascriptLanguage/>
    <script type="text/javascript" src='<ispac:rewrite href="../scripts/numberFormat.js"/>'> </script>
	 <script type="text/javascript" src='<ispac:rewrite href="../scripts/utils.js"/>'> </script>
	 
	<script type="text/javascript" src='<ispac:rewrite href="../scripts/jquery-1.3.2.min.js"/>'></script>
  	<script type="text/javascript" src='<ispac:rewrite href="../scripts/jquery-ui-1.7.2.custom.min.js"/>'></script>
 	<script type="text/javascript" src='<ispac:rewrite href="../scripts/jquery.alerts.js"/>'></script>

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
			serverPort = PortsConfig.getCertPort();
		}
		%>
  	
    <script>
    	window.name="ParentWindow";
		function redirige(){
			document.location.href = '<%="https://"+request.getServerName()+":"+serverPort+request.getContextPath()+"/obtenerServiciosIntermediacion.do"%>';
		}
    </script>
  </head>

  <body onLoad="javascript:redirige()">
  	<ispac:keepalive />

      <table border="0" width="100%" cellpadding="0" cellspacing="0">
        <tr valign="top">
			<td width="100%" height="100%">
		       	<div id="body" style="visibility:visible;">

					<div id="contenido" class="move" >
					<div class="ficha">
					<div class="encabezado_ficha">
					<div class="titulo_ficha">

					<script language='JavaScript' type='text/javascript'><!--
					
					//--></script>

					<div class="acciones_ficha">
					</div>
					<%--fin acciones ficha --%></div>
					<%--fin titulo ficha --%></div>
					<%--fin encabezado ficha --%>
					<div class="cuerpo_ficha">
					<div class="seccion_ficha">
						<p class="fila_sub clearfix">
								<label class="mid"><nobr><bean:message key="svd.services.loading" /></nobr></label>
						</p>
					</div>
					<%--seccion ficha --%> 
					</div>
					<%--fin cuerpo ficha --%>
					</div>
					<%--fin  ficha --%>
					<div>
					<%--fin contenido --%>
					<script>
						positionMiddleScreen('contenido');
						$(document).ready(function(){
							$("#contenido").draggable();
						});
					</script>
				
				</div>
			</td>
        </tr>
      </table>
	<ispac:layer id="waitInProgress" msgKey="msg.layer.downloadDocument" showCloseLink="true" styleClassMsg="messageShowLayer" />
    <ispac:layer id="waitOperationInProgress" msgKey="msg.layer.operationInProgress" styleClassMsg="messageShowLayer" />

    <ispac:layer/>
    <ispac:frame/>
    <ispac:layer id="configLayer"/>
    <ispac:frame id="configFrame"/>
  </body>
</html:html>