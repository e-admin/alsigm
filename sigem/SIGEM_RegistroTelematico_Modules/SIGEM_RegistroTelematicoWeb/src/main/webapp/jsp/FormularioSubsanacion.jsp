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
				<meta http-equiv="Refresh" content="0;URL=<%=dir%>" />
		<%
			}
		%>

		<%
		String path = request.getContextPath();
		String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
		%>

		<base href="<%= basePath %>" />

		<link rel="stylesheet" href="css/<%=rutaEstilos%>estilos.css" type="text/css" />

		<script type="text/javascript" language="javascript" src="js/idioma.js"></script>
		<script type="text/javascript" language="javascript" src="js/base64.js"></script>
		<script type="text/javascript" language="javascript" src="js/navegador.js"></script>
		<script type="text/javascript" language="javascript" src="js/utils.js"></script>

		<script type="text/javascript">

			var array = new Array('SUBSANACIOND1','SUBSANACIOND2','SUBSANACIOND3');

			function comprobar() {

				var i=0;
				var alguno = 0;
				var files = new Array();
				var repetido = false;

				for (i=0; i<array.length; i++){

					fileName = document.getElementById(array[i]).value;
					if(fileName != "") {
						alguno = 1;
					}

					files[i] = fileName;
				}

				try{
					if (alguno == 0)
						alert("<bean:message key="registro.formulario.mensaje.algun_fichero"/>");
					else {

						for (i=0; i<files.length; i++) {

							fileName = files[i];
							if (fileName != "") {

								for (j=i+1; j<files.length; j++) {
									if (files[i] == files[j]) {

										repetido = true;
										break;
									}
								}
							}

							if (repetido)
								break;
						}

						if (repetido) {
							alert("<bean:message key="registro.formulario.mensaje.fichero_repetido"/>");
						}
						else {

							var formulario = document.getElementById('form_send');

							document.body.scrollTop = 0;
							showLayer('layer');

							var iframe = document.getElementById("enviarSolicitud");

							iframe.style.height = size() - 120 + "px";
							iframe.style.width = "780px";
							iframe.style.left = "50%";
							iframe.style.top = "100px";
							iframe.style.marginLeft = "-390px";
							iframe.style.visibility = "visible";

							formulario.target = 'enviarSolicitud';

							formulario.submit();
						}
					}
				}
				catch(e){
					alert(e.message);
					if (e.name == 'TypeError')
						alert("<bean:message key="registro.formulario.mensaje.fichero"/>");
					return false;
				}
			}

			function mostrarSolicitud() {

				var iframe = document.getElementById("enviarSolicitud");

				iframe.style.height = "0px";
				iframe.style.width = "0px";
				iframe.style.left = "0px";
				iframe.style.top = "0px";
				iframe.style.marginLeft = "0px";
				iframe.style.marginTop = "0px";
				iframe.style.visibility = "hidden";
				iframe.src = "jsp/loading.jsp";

				hideLayer('layer');
			}

		</script>

		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1"/>
	</head>
	<body>
	<% if (logado){ %>
		<div id="contenedora">
			<jsp:include flush="true" page="Cabecera.jsp"></jsp:include>
			<div class="centered">
			<div class="contenedor_centrado">
				<form id="form_send" name="form_send" action="<html:rewrite page="/enviarSubsanacion.do"/>" method="post" enctype="multipart/form-data">
					<div class="cuerpo">
					      	<div class="cuerporight">
				    	    	<div class="cuerpomid">
								<%= session.getAttribute(Defs.FORMULARIO) %>
					        </div>
					      	</div>
				    	</div>
					<div class="cuerpobt">
		      				<div class="cuerporightbt">
	        				<div class="cuerpomidbt">
	        					<input type="button" value="<bean:message key="formulario.aceptar"/>" id="enviar_solicitud" class="ok" onclick="javascript:comprobar();" />
	        				</div>
			      			</div>
			      		</div>
		      		</form>
		    	</div>
	    		</div>
	    	</div>

    <%} else { %>
    	<p><bean:message key="cargando"/></p>
    <% } %>

		<iframe id="enviarSolicitud" name="enviarSolicitud" src="blank.html" class="frame_barra" style="visibility: hidden; position: absolute; height: 0px; width: 0px; z-index:2000;" frameborder='0' title='<bean:message key="resultado.validar"/>'></iframe>
		<div id="layer" style="display:none;z-index:1000;background:white;filter:alpha(opacity=50);-moz-opacity:.50;opacity:.50;"/></div>

	</body>
</html:html>