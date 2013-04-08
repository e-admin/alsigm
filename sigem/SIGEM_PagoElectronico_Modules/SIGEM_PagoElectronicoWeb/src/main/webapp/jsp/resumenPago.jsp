<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<%@ page import="ieci.tecdoc.sgm.core.config.ports.PortsConfig"%>

<%
String rutaEstilos = (String)session.getAttribute("PARAMETRO_RUTA_ESTILOS");
if (rutaEstilos == null) rutaEstilos = "";
String rutaImagenes = (String)session.getAttribute("PARAMETRO_RUTA_IMAGENES");
if (rutaImagenes == null) rutaImagenes = "";
%>

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
			} else {
				serverPort = PortsConfig.getHttpPort();
			}
%>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="es" xml:lang="es">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<meta name="author" content="IECISA" />
<title>sigem</title>
<link href="css/<%=rutaEstilos%>estilos.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" language="javascript" src="js/idioma.js"></script>
<!-- FIRMA ELECTRONICA -->
		<script type="text/javascript" language="javascript" src="install_files/common-js/deployJava.js"></script>
		<script type="text/javascript" language="javascript" src="install_files/common-js/instalador.js"></script>
		<script type="text/javascript" language="javascript" src="install_files/common-js/firma.js"></script>
		<script type="text/javascript" language="javascript" src="install_files/common-js/constantes.js"></script>
		
		<%
			String appletDir = "http://" + request.getServerName() + ":" + serverPort + request.getContextPath();
		%>
		
		
		<script type="text/javascript" language="javascript">
			function getBase()
			{
				baseHREF='<%=appletDir%>';
				return baseHREF;
			}

			var base= getBase() + '/install_files' ;
			baseDownloadURL = base;
			
			var clienteFirmaCargado = false;

			function Firma(){
				document.getElementById('boton').disabled = true;
				initialize();
				clienteFirma.setData("<%=request.getAttribute("datosParaFirmar")%>");
				clienteFirma.setShowErrors(false);
				firmar();
		
				if(!clienteFirma.isError()){
					var campoFirma = document.getElementById("idFirma");
					var formulario = document.getElementById("formulario");
					campoFirma.value = clienteFirma.getSignatureBase64Encoded();
					formulario.submit();
				}else{
					alert('<bean:message key="ieci.tecdoc.sgm.pe.struts.common.firma.cliente.msg2"/>: '+clienteFirma.getErrorMessage());
					document.getElementById('boton').disabled = false;
					return false;
				}
			}
			
			clienteFirmaCargadoShowButtons = false;
			
			function finInstalacion(){
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
				clearInterval();
			}

			setInterval("finInstalacion();", 1000);
		</script>
<!-- FIN FIRMA ELECTRONICA -->
</head>
<body>
<script language="javascript">
function volver(){
	var url = '<html:rewrite page="/recargarFormulario.do"/>';
	var formulario = document.getElementById("formulario");
	formulario.action = url;
	formulario.target="";
	formulario.submit();
}

function justificante(){
	var formulario = document.getElementById("formulario");
	formulario.action="<html:rewrite page="/obtenerJustificante.do"/>"
	formulario.target="_blank";
	formulario.submit();	
}
</script>

<div id="contenedora">
  <!-- Inicio Cabecera -->
	<jsp:include page="cabecera.jsp"/>
	
  <div id="migas">
  <img src="img/<%=rutaImagenes%>flecha_migas.gif" width="13" height="9" class="margen" alt=""/><a href="<html:rewrite page="/buscarLiquidaciones.do"/>"><bean:message key="ieci.tecdoc.sgm.pe.struts.common.liquidaciones.label"/></a> &gt; <a href="<html:rewrite page="/buscarLiquidaciones.do"/>"><bean:message key="ieci.tecdoc.sgm.pe.struts.common.formularioPago"/></a> &gt;<span class="activo"> <bean:message key="ieci.tecdoc.sgm.pe.struts.common.liquidaciones.resumenFirma"/></span> </div>
  <!-- Fin Cabecera -->
  <!-- Inicio Contenido -->
  <div id="contenido">
    <div class="cuerpo">
      <div class="cuerporight">
        <div class="cuerpomid">
          <h1><bean:message key="ieci.tecdoc.sgm.pe.struts.common.liquidaciones.formulario.title"/>: <bean:message key="ieci.tecdoc.sgm.pe.struts.common.liquidaciones.resumenFirma"/></h1>
          <div class="submenu3">
            <ul>
            </ul>
          </div>
          <div class="cuadro">
          <div class="cuadroForm" style="width: 750px;">
          	<div id="divInfoInstall"> 
                <div id="cargando">
          				<br/>
                  <img src="img/<%=rutaImagenes%>bg-wait.gif" alt=""/>
          				<h2><label id="lblInfoInstall"><bean:message key="mensaje.appletFirma.instalando"/></label></h2>
                </div>
                <br/>
        	 </div>          
          <html:form styleId="formulario" action="realizarPago.do">
          	<html:hidden styleId="idFirma" property="firma"/>
          	<input type="hidden" name="datosFirmados" value="<bean:write name="datosFirmados"/>"/>
			<div style="position:relative;left:50px;top:0px;width:690px;height:370px;">          	                    
			<%=request.getAttribute("documentoPago")%>
			</div>
			<div id="divButtons" style="position:relative;left:270px;height:40px;display:none;">          							
				<html:button property="Enviar" styleId="boton" onclick="Firma()"><bean:message key="ieci.tecdoc.sgm.pe.struts.common.firmarEnviar"/></html:button>
				<!-- html:button property="Enviar" styleId="boton" onclick="javascript:volver();" --><!-- bean:message key="ieci.tecdoc.sgm.pe.struts.common.back"/--><!-- /html:button-->										
			</div>
			<div id="divNoButtons" style="position:relative;left:270px;height:40px;" >
				&nbsp;
			</div>
          </html:form>			
          </div>
          </div>
        </div>
      </div>
    </div>
    <div class="cuerpobt">
      <div class="cuerporightbt">
        <div class="cuerpomidbt"></div>
      </div>
    </div>
  </div>
  <!-- Fin Contenido -->
  <div id="pie"></div>
</div>
	<script type="text/javascript" language="javascript">
			cargarAppletFirma(defaultBuild);
	</script>
</body>
</html>
