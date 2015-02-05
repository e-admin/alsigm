<%@ page language="java"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ page import="ieci.tecdoc.sgm.core.config.ports.PortsConfig"%>

<%
String rutaEstilos = (String)session.getAttribute("PARAMETRO_RUTA_ESTILOS");
if (rutaEstilos == null) rutaEstilos = "";
String rutaImagenes = (String)session.getAttribute("PARAMETRO_RUTA_IMAGENES");
if (rutaImagenes == null) rutaImagenes = "";
%>

<html:html locale="true">
	<head>
		<%
		String path = request.getContextPath();
		String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

		String serverPort = String.valueOf(request.getServerPort());

		String proxyHttpPort = PortsConfig.getHttpFrontendPort();
		String proxyHttpsNoCertPort = PortsConfig.getHttpsFrontendPort();

		if ((proxyHttpPort != null && proxyHttpPort.equals(serverPort)) ||
			(proxyHttpsNoCertPort != null && proxyHttpsNoCertPort.equals(serverPort))) {

			// Servidor Frontend por delante del Servidor de Aplicaciones (Ej: APACHE + TOMCAT)
			serverPort = proxyHttpsNoCertPort;
		}
		else {
			serverPort = PortsConfig.getHttpsPort();
		}
		%>
		<base href="<%= basePath %>" />
		<link rel="stylesheet" href="css/<%=rutaEstilos%>estilos.css" type="text/css" />
		<script type="text/javascript" language="javascript" src="js/idioma.js"></script>
	</head>
	<body>


	<div id="contenedora">
		<jsp:include flush="true" page="Cabecera.jsp"></jsp:include>
		<div class="centered">
		<div class="contenedor_centrado">
			<div class="cuerpo">
		      	<div class="cuerporight">
		        	<div class="cuerpomid">
		          		<h1><bean:message key="autenticacion.table_title" /></h1>

					<div class="contenido_cuerpo_login">
						<div class="seccion_login">

			            		<logic:equal name="invalid_user" value="true">
			            			<label class="error"><bean:message key="autenticacion.error"/></label>
						</logic:equal>

			            		<form id="form_auth" name="form_auth" action='<%="https://"+request.getServerName()+":"+serverPort+request.getContextPath()+"/validacionUsuario.do"%>' method="post">

					              <p class="fila_sub">
			                		<label for="username" class="login" ><bean:message key="autenticacion.username" /></label>
			                		<input type="text" name="username" id="username" class="login"/>
					              </p>
					              <p class="fila_sub">
			                		<label for="password" class="login"><bean:message key="autenticacion.password" /></label>
			                		<input type="password" name="password" id="password" class="login" />
					              </p>

					              <p class="fila">&nbsp;</p>
					              <p class="fila_right">
			                		<input type="submit" class="botonFondo" value='<bean:message key="autenticacion.aceptar" />'/>
					              </p>
					              <p class="fila">&nbsp;</p>

			            		</form>
						</div> <!-- fin seccion -->
					</div> <!-- contenido_cuerpo -->
	          		</div>
		        </div>
		      	</div>

			<div class="cuerpobt">
	      		<div class="cuerporightbt">
	        		<div class="cuerpomidbt"></div>
	      		</div>
			</div>
		</div>
		</div>
	</div>

	<script language="Javascript">
		document.getElementById('username').focus();
	</script>

	</body>
</html:html>