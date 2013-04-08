<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>

<%@ page import="ieci.tecdoc.sgm.registro.utils.Defs" %>
<%@ page import="ieci.tecdoc.sgm.autenticacion.util.TipoAutenticacionCodigos" %>
<%@ page import="ieci.tecdoc.sgm.core.services.telematico.PeticionDocumento" %>
<%@ page import="ieci.tecdoc.sgm.core.services.telematico.PeticionDocumentos" %>
<%@ page import="ieci.tecdoc.sgm.core.config.ports.PortsConfig"%>

<%
String rutaEstilos = (String)session.getAttribute("PARAMETRO_RUTA_ESTILOS");
if (rutaEstilos == null) rutaEstilos = "";
String rutaImagenes = (String)session.getAttribute("PARAMETRO_RUTA_IMAGENES");
if (rutaImagenes == null) rutaImagenes = "";

boolean bFirma = true;
String firmar_solicitud = (String)session.getAttribute(Defs.FIRMAR_SOLICITUD);
if (firmar_solicitud == null || firmar_solicitud.equals(""))
	firmar_solicitud = "1";
if (firmar_solicitud.equals("0"))
	bFirma = false;

boolean bVirus = false;
bVirus = ((Boolean)session.getAttribute(Defs.HAY_VIRUS)).booleanValue();

boolean bRegistrar = false;
try {
	bRegistrar = new Boolean((String)session.getServletContext().getAttribute(Defs.PLUGIN_REGISTRARCONVIRUS)).booleanValue();
} catch(Exception e) { }
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
			String tramiteId = (String)session.getAttribute(Defs.TRAMITE_ID);

			if (sessionIdIni == null || sessionIdIni.equals("") || sessionIdIni.equals("null")) {

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

		<link rel="stylesheet" href="css/<%=rutaEstilos%>estilos.css" type="text/css" />
		<script type="text/javascript" language="javascript" src="js/idioma.js"></script>

		<%
		if (bFirma) {
		%>
		<script type="text/javascript" language="javascript" src="install_files/common-js/constantes.js"></script>
		<script type="text/javascript" language="javascript" src="install_files/common-js/deployJava.js"></script>
		<script type="text/javascript" language="javascript" src="install_files/common-js/instalador.js"></script>
		<script type="text/javascript" language="javascript" src="install_files/common-js/firma.js"></script>
		<%
		}
		%>

		<script type="text/javascript" language="javascript" src="js/navegador.js"></script>

		<%
			String appletDir = "http://" + request.getServerName() + ":" + serverPort + request.getContextPath();
		%>

		<script type="text/javascript" language="javascript">

			<%
			if (bFirma) {
			%>

			function getBase()
			{
				var baseHREF = '<%=appletDir%>';
				return baseHREF;
			}

			// Variables en constantes.js
			base = getBase() + '/install_files';
			baseDownloadURL = base;

			var clienteFirmaCargado = false;

			// Boton de firmar
			function Firma()
			{
				try {
					document.getElementById('acciones').style.visibility = "hidden"

					// Deshabilitar los botones hasta finalizar el proceso de firma
					document.getElementById('aceptar_registro').disabled = true;
					document.getElementById('firmar_solicitud').disabled = true;
					document.getElementById('corregir').disabled = true;

					// Inicializar la configuracion del cliente
					initialize();

					// Configurar la operacion de firma
					configuraFirma();

					// Establecer los datos a firmar
					clienteFirma.setData("<%=request.getSession().getAttribute(Defs.DATOS_A_FIRMAR)%>");

					// Ejecutar la operacion de firma
					firmar();

					// Comprobar que el proceso de firma no tenga errores
					if (!clienteFirma.isError())
					{
						var campoFirma = document.getElementById("<%= Defs.FIRMA %>");
						var campoCertificado = document.getElementById("<%= Defs.CERTIFICADO %>");

						// Campo de firma con la firma en Base64
						campoFirma.value = clienteFirma.getSignatureBase64Encoded();

						if (campoFirma.value != null && campoFirma.value != '' && campoFirma.value != 'undefined' && campoFirma.value != 'null') {

							// Bug en la version 3.2: getSignCertificateBase64Encoded() no retorna el certificado en Base64
							// Establecer el MandatoryCert a TRUE para que la funcion anterior retorne el certificado en Base64
							clienteFirma.setMandatoryCertificateConditionRFC2254(null, null, true);

							// Campo de certificado con el certificado en Base64
							campoCertificado.value = clienteFirma.getSignCertificateBase64Encoded();

							if (campoCertificado.value != null && campoCertificado.value != '' && campoCertificado.value != 'undefined' && campoCertificado.value != 'null') {

								// Submit del formulario
								return true;
							}
						}
					}
					else
					{
						alert('<bean:message key="firma.no_firma"/>' + ' ' + clienteFirma.getErrorMessage());
					}
				} catch (err) {
					error = '<bean:message key="firma.no_firma_recarga"/>' + ' \'';
					try {
						errorMessage = clienteFirma.getErrorMessage();
						if (errorMessage != null && errorMessage != '') {
							error = error + errorMessage;
						} else {
							error = error + err.description;
						}
					} catch(err) {
						error = error + err.description;
					}
					alert(error + '\'.\n\n' + '<bean:message key="firma.no_firma_recarga.actualizar"/>');
				}

				// Si ha habido algun error
				// habilitar los botoner para volver a realizar la firma
				document.getElementById('aceptar_registro').disabled = false;
				document.getElementById('firmar_solicitud').disabled = false;
				document.getElementById('corregir').disabled = false;

				document.getElementById('acciones').style.visibility = "visible"

				return false;
			}

			clienteFirmaCargadoShowButtons = false;

			function finInstalacion()
			{
				try {
					if (clienteFirmaCargado)
					{
						element = document.getElementById("lblInfoInstall");
						element.innerHTML = "<bean:message key="mensaje.appletFirma.instalado"/>";

						if (clienteFirmaCargadoShowButtons == false) {

							setTimeout("showButtons();", 1000);
						}
					}
					else if (clienteFirma != null && clienteFirma != undefined && clienteFirma.isInitialized()) {
						clienteFirmaCargado = true;
					}
				} catch(err) {
				}
			}

			function showButtons() {

				elementDivInfoInstall = document.getElementById("divInfoInstall");
				elementDivInfoInstall.style.display = "none";

				elementDivNoButtons = document.getElementById("divNoButtons");
				elementDivNoButtons.style.display = "none";
				elementDivButtons = document.getElementById("divButtons");
				elementDivButtons.style.display = "block";

				clienteFirmaCargadoShowButtons = true;
			}

			setInterval("finInstalacion();", 1000);

			<%
			}
			else {
			%>

			function Firma()
			{
				document.getElementById('acciones').style.visibility = "hidden"

				// Deshabilitar los botones una vez aceptada la solicitud
				document.getElementById('aceptar_registro').disabled = true;
				document.getElementById('aceptar_solicitud').disabled = true;
				document.getElementById('corregir').disabled = true;

				return true;
			}

			<%
			}
			%>

			// Corregir la solicitud
			function mostrarSolicitud() {

				parent.mostrarSolicitud();
			}

		</script>

	</head>

	<body>

	<%
	if (logado){
	%>

	<%
	if (!bVirus || (bVirus && bRegistrar)){
		if (bFirma) {
	%>
	<script type="text/javascript" language="javascript">

		// Cargar el cliente de firma
		cargarAppletFirma(defaultBuild);

	</script>
	<%
		}
	}
	%>

	<div id="contenedora">

		<%--
		<jsp:include flush="true" page="Cabecera.jsp"></jsp:include>
		--%>

		<div class="centered">

			<div id="divInfoInstall">
				<img src="img/<%=rutaImagenes%>bg-wait.gif" alt=""/>
				<h2><label id="lblInfoInstall"><bean:message key="mensaje.appletFirma.instalando"/></label></h2>
			</div>

			<div class="contenedor_centrado">

				<form id="form_acept_request" name="form_acept_request" action="<html:rewrite page="/registrarSolicitud.do"/>" method="post" onsubmit="return Firma();">

					<div class="cuerpo">
		      			<div class="cuerporight">
		        			<div class="cuerpomid">

		        				<div id="acciones" class="acciones"><a href="javascript:mostrarSolicitud();" class="cerrar">&nbsp;</a></div>

		          				<h1><bean:message key="resultado.validar"/></h1>

								<div class="cuadro">

									<%if (bVirus) {%>
										<div class="seccion">
											<%if (!bRegistrar) { %>
												<label class="error_rojo"><bean:message key="mensaje.virus"/></label>
											<%} else { %>
												<label class="error_rojo"><bean:message key="mensaje.virus_informativo"/></label>
											<%}%>

											<ul class="error_rojo">
											<logic:iterate id="document" name="<%= Defs.HAY_VIRUS_DOCUMENTOS %>">
												<li><bean:write name="document"/></li>
											</logic:iterate>
											</ul>
										</div>
									<%}%>

									<input type="hidden" value="" id="<%= Defs.FIRMA %>" name="<%= Defs.FIRMA %>" />
									<input type="hidden" value="" id="<%= Defs.CERTIFICADO %>" name="<%= Defs.CERTIFICADO %>" />

									<%= request.getSession().getAttribute(Defs.SOLICITUD_REGISTRO) %>

									<%
										PeticionDocumentos petDocs = (PeticionDocumentos)session.getAttribute(Defs.DOCUMENTOS_REQUEST);
										if(petDocs != null && petDocs.count()>0){
									%>
											<div class="submenu">
									   			<h1><bean:message key="resultado.documentos_anexos"/></h1>
	   										</div>

									<%
											for(int h=0; h<petDocs.count(); h++){
												PeticionDocumento petDoc = petDocs.get(h);
									%>

										   		<div class="col clearfix">
													<label class="gr">
														<bean:message key="resultado.documento"/><%=h+1%>:
													</label>
													<label>
														<%=petDoc.getFileName()%>
													</label>
												</div>
									<%
											}
									%>
									<%
										}
									%>
								</div>
							</div>
						</div>
		      		</div>

					<div class="cuerpobt">
      					<div class="cuerporightbt">
        					<div class="cuerpomidbt" id="divButtons" style="display: none;">

								<input type="button" value="<bean:message key="resultado.ver_solicitud"/>" class="ok" id="aceptar_registro" onclick="window.open('<%=request.getSession().getAttribute(Defs.XML_REQUEST_FILE)%>','ventana1','width=600px,height=800px,scrollbars=yes');"/>

								<%
								if (!bVirus || (bVirus && bRegistrar)) {
									if (!bFirma) {
									%>
									<input type="submit" value="<bean:message key="resultado.aceptar"/>" class="ok" id="aceptar_solicitud" name="aceptar_solicitud" />
									<%
									}
									else {
									%>
									<input type="submit" value="<bean:message key="resultado.firmar"/>" class="ok" id="firmar_solicitud" name="firmar_solicitud" />
									<%
									}
								}
								%>

								&nbsp;&nbsp;&nbsp;&nbsp;
								<input class="ok" type="button" id="corregir" value="<bean:message key="resultado.corregir"/>" onClick="javascript:mostrarSolicitud();">

        					</div>
							<div class="cuerpomidbt" id="divNoButtons">
								&nbsp;
							</div>
      					</div>
					</div>

				</form>

			</div>
		</div>
	</div>

	<%
	}
	else {
	%>
		<p><bean:message key="cargando"/></p>
	<%
	}
	%>

	<%
	if (!bFirma) {
	%>
	<script type="text/javascript" language="javascript">

		elementDivInfoInstall = document.getElementById("divInfoInstall");
		elementDivInfoInstall.style.display = "none";

		elementDivNoButtons = document.getElementById("divNoButtons");
		elementDivNoButtons.style.display = "none";
		elementDivButtons = document.getElementById("divButtons");
		elementDivButtons.style.display = "block";

	</script>
	<%
	}
	%>

	</body>

</html:html>