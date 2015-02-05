<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>

<%@ page import="ieci.tecdoc.sgm.registro.utils.Defs" %>
<%@ page import="ieci.tecdoc.sgm.core.config.ports.PortsConfig"%>


<%
String rutaEstilos = (String)session.getAttribute("PARAMETRO_RUTA_ESTILOS");
if (rutaEstilos == null) rutaEstilos = "";
String rutaImagenes = (String)session.getAttribute("PARAMETRO_RUTA_IMAGENES");
if (rutaImagenes == null) rutaImagenes = "";
%>


<html:html locale="es">
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

			boolean logado = true;
			String sessionIdIni = "";
	    	sessionIdIni = (String)session.getAttribute(Defs.SESION_ID);

			if (sessionIdIni == null || sessionIdIni.equals("") || sessionIdIni.equals("null")) {

				String tramiteId = (String)session.getAttribute(Defs.TRAMITE_ID);

				if (tramiteId == null || tramiteId.equals("null")) {
					tramiteId = new String("");
				}

				String url_parcial = (String)request.getSession().getServletContext().getAttribute("redirAutenticacion");
				String dir = "http://" + request.getServerName() + ":" + serverPort + "/" + url_parcial + "&" + Defs.TRAMITE_ID + "=" + tramiteId;

				logado = false;
		%>
				<META HTTP-EQUIV="Refresh" CONTENT="0;URL=<%=dir%>">
		<%
			}
		%>

		<script language="Javascript">
			function obtenerJustificante(){
				var nr = document.getElementById("numeroRegistro").value;
				document.location.href = "descargarJustificante.do?numeroRegistro=" + nr;
			}
		</script>
		<%
		String path = request.getContextPath();
		String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
		%>
		<base href="<%= basePath %>" />
		<link rel="stylesheet" href="css/<%=rutaEstilos%>estilos.css" type="text/css" />
		<script type="text/javascript" language="javascript" src="js/idioma.js"></script>
	</head>
	<body>

	<%if (logado){ %>

	<div id="contenedora">
		<jsp:include flush="true" page="Cabecera.jsp"></jsp:include>
		<div class="centered">
		<div class="contenedor_centrado">
			<form id="form_receipt" name="form_receipt" action="<html:rewrite page="/jsp/index.jsp"/>" method="post">

			<div class="cuerpo">
		      	<div class="cuerporight">
		        	<div class="cuerpomid">
		          		<h1><bean:message key="justificante.informacion"/></h1>
		          		<div class="cuadro">
						<%= request.getSession().getAttribute(Defs.INFORMACION_REGISTRO) %>
					</div>
		        	</div>
		      	</div>
			</div>

			<div class="cuerpobt">
      			<div class="cuerporightbt">
        			<div class="cuerpomidbt">
					<input type="button" value="<bean:message key="justificante.descargar"/>" class="ok" id="aceptar_justificante" onclick="obtenerJustificante();"/>
					<input type="button" value="<bean:message key="justificante.aceptar"/>" class="ok" id="aceptar_justificante" onclick="document.location.href='<%=request.getContextPath()%>/jsp/RedireccionPortal.jsp'"/>
        			</div>
      			</div>
			</div>

			</form>
		</div>
		</div>
	</div>

	<%}else{%>
		<p><bean:message key="cargando"/></p>
	<%}%>
	</body>
</html:html>