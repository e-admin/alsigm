<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>

<%@ page import="ieci.tecdoc.sgm.registro.utils.Defs" %>
<%@page import="ieci.tecdoc.sgm.core.config.ports.PortsConfig"%>
<%@page import="ieci.tecdoc.sgm.core.services.idioma.ConstantesIdioma"%>
<%@page import="ieci.tecdoc.sgm.core.services.idioma.Idioma"%>
<%@page import="java.util.Locale"%>
<%@page import="ieci.tecdoc.sgm.core.web.locale.LocaleFilterHelper"%>

<%
	String rutaEstilos = (String) session
			.getAttribute("PARAMETRO_RUTA_ESTILOS");
	if (rutaEstilos == null)
		rutaEstilos = "";
	String rutaImagenes = (String) session
			.getAttribute("PARAMETRO_RUTA_IMAGENES");
	if (rutaImagenes == null)
		rutaImagenes = "";
%>


<html:html locale="true">

	<head>
		<%
			String entidad = (String) request.getSession().getAttribute("ENTIDAD_ID");
			String serverPort = String.valueOf(request.getServerPort());

			String proxyHttpPort = PortsConfig.getHttpFrontendPort();
			String proxyHttpsNoCertPort = PortsConfig
					.getHttpsFrontendPort();
			String proxyHttpsSiCertPort = PortsConfig
					.getHttpsFrontendAuthclientPort();

			if ((proxyHttpPort != null && proxyHttpPort.equals(serverPort))
					|| (proxyHttpsNoCertPort != null && proxyHttpsNoCertPort
							.equals(serverPort))
					|| (proxyHttpsSiCertPort != null && proxyHttpsSiCertPort
							.equals(serverPort))) {

				// Servidor Frontend por delante del Servidor de Aplicaciones (Ej: APACHE + TOMCAT)
				serverPort = proxyHttpPort;
			} else {
				serverPort = PortsConfig.getHttpPort();
			}

			boolean logado = true;
			String sessionIdIni = "";
			sessionIdIni = (String) session.getAttribute(Defs.SESION_ID);

			if (sessionIdIni == null || sessionIdIni.equals("")
					|| sessionIdIni.equals("null")) {

				String tramiteId = (String) session
						.getAttribute(Defs.TRAMITE_ID);

				if (tramiteId == null || tramiteId.equals("null")) {
					tramiteId = new String("");
				}

				String url_parcial = (String) request.getSession()
						.getServletContext().getAttribute(
								"redirAutenticacion");
				String dir = "http://" + request.getServerName() + ":"
						+ serverPort + "/" + url_parcial + "&"
						+ Defs.TRAMITE_ID + "=" + tramiteId;

				logado = false;
		%>
				<meta http-equiv="Refresh" content="0;URL=<%=dir%>" />
		<%
			}
		%>
		<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1"/>
		<%
		String path = request.getContextPath();
		String basePath = request.getScheme() + "://"+ request.getServerName() + ":"+ request.getServerPort() + path + "/";
		%>

		<base href="<%= basePath %>" />

		<link rel="stylesheet" href="css/<%=rutaEstilos%>estilos.css" type="text/css" />

		<script type="text/javascript" language="javascript" src="js/idioma.js"></script>
		<script type="text/javascript" language="javascript" src="js/base64.js"></script>
		<script type="text/javascript" language="javascript" src="js/navegador.js"></script>
		<script type="text/javascript" language="javascript" src="js/utils.js"></script>

		<script type="text/javascript" src="dwr/engine.js"></script>
		<script type="text/javascript" src="dwr/interface/RemoteFunctions.js"></script>
		<script type="text/javascript" src="dwr/interface/TercerosRemoteFunctions.js"></script>

		<script language="Javascript">

			function comprobar() {

				var formulario = document.forms[0];
				var i=0;
				if (validar != undefined){
					if (validar.length > 0){
						for(i=0; i<validar.length; i++){
							var campo = document.getElementById(validar[i][0]);
							if(campo.value == ""){
								alert("<bean:message key="registro.formulario.mensaje.obligatorio1"/> '" + validar[i][1] + "' <bean:message key="registro.formulario.mensaje.obligatorio2"/>");
								campo.focus();
								return false;
							}else{
								if(campo.type == "file"){
									var tipo = document.getElementById(validar[i][0] + "_Tipo");
									var fichero = campo.value.substring(campo.value.lastIndexOf('.')+1, campo.value.length).toUpperCase();
									var tipo_fichero = tipo.value.toUpperCase();
									var tipos_aceptados = parsearTipos(tipo_fichero);
									var encontrado = false;

									for(var it=0; it<tipos_aceptados.length; it++){
										if (fichero == tipos_aceptados[it])
											encontrado = true;
									}
									if (encontrado != true){
										alert("<bean:message key="registro.formulario.mensaje.extension1"/> '" + validar[i][1] + "' <bean:message key="registro.formulario.mensaje.extension2"/> " + tipo_fichero);
										campo.focus();
										return false;
									}
								} //else if (campo.type == "textarea"){
								//	if (campo.value.length > 250){
								//		alert('<bean:message key="registro.formulario.mensaje.extenso1"/> "' + validar[i][1] + '" <bean:message key="registro.formulario.mensaje.extenso2"/>');
								//		campo.focus();
								//		return false;
								//	}
								//}
							}
						}
					}
				}

				if (typeof window.verificacionesEspecificas == 'function') {
					if (verificacionesEspecificas() == false) {
						return false;
					}
				}

				if (typeof window.verificacionesDWR == 'function') {

					var locale;
					var ctlSelIdioma = document.getElementById("selIdioma");
					if (ctlSelIdioma != null) {
						locale = ctlSelIdioma.value;
					}

					var entidad = '<%=entidad%>';

					if (verificacionesDWR(entidad, locale) == false) {
						return false;
					}
				}

				var email = document.getElementById("emailSolicitante");
				if (validarEmail(email) == false){
					alert('<bean:message key="registro.formulario.mensaje.email"/>');
					email.focus();
					return false;
				}

				if (validarNumero != undefined){
					if (validarNumero.length > 0){
						for(i=0; i<validarNumero.length; i++){
							var campo = document.getElementById(validarNumero[i][0]);
							var valor = campo.value;
							if (valor != ""){
								if ((valor.charAt(0) == ' ') || (valor.charAt(valor.length - 1) == ' ') || (isNaN(valor))) {
									alert("<bean:message key="registro.formulario.mensaje.numerico1"/> '" + validarNumero[i][1] + "' <bean:message key="registro.formulario.mensaje.numerico2"/>");
									campo.focus();
									return false;
								}
								else if (campo.value.length > parseInt(validarNumero[i][2]) && campo.value.length > 0){
									alert("<bean:message key="registro.formulario.mensaje.longitud1"/> '" + validarNumero[i][1] + "' <bean:message key="registro.formulario.mensaje.longitud2"/>");
									campo.focus();
									return false;
								}
							}
						}
					}
				}
				if (validarDEU() == false){
					alert("<bean:message key="registro.formulario.mensaje.deu"/>");
					campo.focus();
					return false;
				}
				var arrayDocumentos = Array();
				for (i=0; i<formulario.length; i++){
					if (formulario[i].type == 'file'){
						var campo = formulario[i];
						if (campo.value != null && campo.value != '') {
							var tipo = document.getElementById(campo.id + "_Tipo");
							var fichero = campo.value.substring(campo.value.lastIndexOf('.')+1, campo.value.length).toUpperCase();
							var tipo_fichero = tipo.value.toUpperCase();
							var tipos_aceptados = parsearTipos(tipo_fichero);
							var encontrado = false;
							arrayDocumentos.push(campo.value);
							for(var it=0; it<tipos_aceptados.length; it++){
								if (fichero == tipos_aceptados[it])
									encontrado = true;
							}
							if (encontrado != true){
								alert("<bean:message key="registro.formulario.mensaje.extension3"/> '" + campo.value + "' <bean:message key="registro.formulario.mensaje.extension2"/> " + tipo_fichero);
								campo.focus();
								return false;
							}
						}
					}
				}
				var validDocuments = validarDocumentos(arrayDocumentos);
				if(!validDocuments)
				 {
				 	return false;
				 }
				for (i=0; i<formulario.length; i++){
					if ((formulario[i].type == 'text') || (formulario[i].type == 'textarea')) {
						while (formulario[i].value.indexOf("<") != -1)
							formulario[i].value = formulario[i].value.replace("<","&lt;");
						while (formulario[i].value.indexOf(">") != -1)
							formulario[i].value = formulario[i].value.replace(">","&gt;");
					}
				}

				return true;
			}

			function validarDocumentos(arrayDocumentos)
			{
				for(i=0;i<arrayDocumentos.length;i++)
				{
					var documentLocation = arrayDocumentos[i];
					for(j=i+1;j<arrayDocumentos.length;j++)
					{

						var documentLocation2 = arrayDocumentos[j];

						if(documentLocation==documentLocation2)
						{
							alert("<bean:message key="registro.formulario.mensaje.documentos.duplicados"/>");
							return false;
						}
					}
				}
				return true;
			}

			function parsearTipos(tipos){
				var cantidad = 0;
				var num_exts = 0;
				var i = 0;
				var j = 0;

				for(i=0; i<tipos.length; i++){
					if (tipos.charAt(i) == ','){
						cantidad = cantidad + 1;
					}
				}

				num_exts = cantidad + 1;
				var tipos_exts = new Array(num_exts);
				for(i=0; i<num_exts; i++)
					tipos_exts[i] = '';
				for(i=0; i<tipos.length; i++){
					if (tipos.charAt(i) != ',')
						tipos_exts[j] = tipos_exts[j] + tipos.charAt(i);
					else j++;
				}
				return tipos_exts;

			}

			function rellenarDatosEspecificos(){
				if (comprobar()){
					var formulario = document.forms[0];
					var i = 0;
					var datos = "";
					if (especificos != undefined){
						if (especificos.length > 0){
							for(i=0; i<especificos.length; i++){
								var cf = document.getElementById(especificos[i][0]);
								var value = cf.value;
								if (cf.type != "checkbox") {
									if (cf.type != "select-one") {
										datos += "<" + especificos[i][1] + "><![CDATA[" + value + "]]></" + especificos[i][1] + ">";
									} else {
										datos += "<" + especificos[i][1] + "><![CDATA[" + value + "]]></" + especificos[i][1] + ">";
										datos += "<Descripcion_" + especificos[i][1] + "><![CDATA[" + cf.options[cf.selectedIndex].text + "]]></Descripcion_" + especificos[i][1] + ">";
									}
								} else{
									if (document.getElementById(especificos[i][0]).checked == true)
										datos += "<" + especificos[i][1] + ">Si</" + especificos[i][1] + ">";
									else datos += "<" + especificos[i][1] + ">No</" + especificos[i][1] + ">";
								}
							}
						}
					}
					try {
						if (idiomas != undefined){
							if (idiomas.length > 0){
								var datos_idioma = "";
								var hayIdiomas = false;
								datos_idioma += "<Idioma_Documentos>";
								for(i=0; i<idiomas.length; i++){
									if (document.getElementById(idiomas[i][0]).value != "") {
										var id = document.getElementById(idiomas[i][1]);
										datos_idioma += '<Idioma_Documento id="' + idiomas[i][0] + '">' + id.value + '</Idioma_Documento>';
										hayIdiomas = true;
									}
								}
								datos_idioma += "</Idioma_Documentos>";
								if (hayIdiomas)
									datos +=datos_idioma;
							}
						}
					} catch (err) {

					}

					var idiomaSolicitud = document.getElementById("idiomaPresentacion");
					if (idiomaSolicitud!=undefined){

						datos_idioma = "<Idioma>";
						datos_idioma += idiomaSolicitud.options[idiomaSolicitud.selectedIndex].text;
						datos_idioma += "</Idioma>";
						datos +=datos_idioma;
					}

					var ctrl_de = document.getElementById("datosEspecificos");
					ctrl_de.value = datos;
					//if (ctrl_de != undefined){
					//	var datosCod = Base64.encode(datos);
					//	ctrl_de.value = datosCod;
					//}

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

					try{
						formulario.submit();
					}catch(e){
						if (e.name == 'TypeError')
							alert("<bean:message key="registro.formulario.mensaje.fichero"/>");
						return false;
					}
				}else return false;
			}

			function validarDEU(){
				if (document.getElementById("solicitarEnvio") == undefined)
					return true;
				if (document.getElementById("direccionElectronicaUnica") == undefined)
					return true;

				if (document.getElementById("solicitarEnvio").checked == true){
					if (document.getElementById("direccionElectronicaUnica").value == "")
						return false;
					else return true;
				} else return true;
			}

			function activarDEU(){
				if (document.getElementById("solicitarEnvio").checked == true)
					document.getElementById("DEU").style.visibility = "visible";
				else
					document.getElementById("DEU").style.visibility = "hidden";
			}

			function validarEmail(email) {
				var s = email.value;
				var filter=/^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/;
				if (s.length == 0 )
					return true;
				if (filter.test(s))
					return true;
				return false;
			}

			function validarCampoNumerico(campo, etiqueta) {
				valor=campo.value;
				if (valor != ""){
					if ((valor.charAt(0) == ' ') || (valor.charAt(valor.length - 1) == ' ') || (isNaN(valor))) {
						alert("<bean:message key="registro.formulario.mensaje.numerico1"/> '" + etiqueta + "' <bean:message key="registro.formulario.mensaje.numerico2"/>");
						campo.focus();
						return false;
					}else if (campo.value.length > campo.maxLength.value && campo.value.length > 0){
						alert("<bean:message key="registro.formulario.mensaje.longitud1"/> '" + etiqueta + "' <bean:message key="registro.formulario.mensaje.longitud2"/>");
						campo.focus();
						return false;
					}
					return true;
				}
				return true;
			}

			function validarFormatoFecha(campo, etiqueta){
				var filter1=/^\d{2}\/\d{2}\/\d{4}$/;
				var filter2=/^\d{2}-\d{2}-\d{4}$/;
				valor=campo.value;
				if (valor != ""){
					if (campo.value.length > 0 && !filter1.test(valor) && !filter2.test(valor)){
						alert("<bean:message key="registro.formulario.mensaje.fecha1"/> '" + etiqueta + "' <bean:message key="registro.formulario.mensaje.fecha2"/>");
						campo.focus();
						return false;
					}
					return true;
				}
				return true;
			}

			function validarNIF(nif){
				return true;
			}

			function permitirSoloNumericos(event){
				// Backspace = 8, Enter = 13, '0' = 48, '9' = 57, '.' = 46
				var key=(document.all) ? event.keyCode : event.which;
				if ((key < 48 || key > 57) && key != 8){
				  //window.event.keyCode=0;
				  //(document.all) ? event.keyCode=0 : event.which=0;
				  return false;
				}
				return true;
			}

			function permitirSoloImportes(event){
				var filter=/^\d+(,\d{0,4}){0,1}$/;
				var campo;
				if (!event) var event = window.event;
				if (event.target) campo = event.target;
				else if (event.srcElement) campo = event.srcElement;
				if (campo.nodeType == 3) // defeat Safari bug
					campo = campo.parentNode;

				var key=(document.all) ? event.keyCode : event.which;
				valor=campo.value+String.fromCharCode(key);
				if (valor != ""){
					if (!filter.test(valor)){
						return false;
					}
					return true;
				}
				return true;
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
	</head>
	<body>
	<% if (logado) { %>
		<div id="contenedora">
			<jsp:include flush="true" page="Cabecera.jsp"></jsp:include>
			<div class="centered">
			<div class="contenedor_centrado">
				<form id="form_send" name="form_send" action="<html:rewrite page="/enviarSolicitud.do"/>" method="post" enctype="multipart/form-data">
					<div class="cuerpo">
					      	<div class="cuerporight">

					<logic:present scope="session"
						name="<%=ConstantesIdioma.MOSTRAR_IDIOMAS_PRESENTACION_KEY%>">
						<bean:define id="idiomasDesplegables" type="java.util.ArrayList"
							name="<%=ConstantesIdioma.IDIOMAS_PRESENTACION_DISPONIBLES%>" />

						<div class="cuerpomid" style="height: 30px">
						<table cellpadding="0" cellspacing="0" cellpadding="0" border="0">
							<tr>
								<td rowspan="4" style="width: 20px"></td>
								<td style="height: 8px" colspan="4"></td>
							</tr>
							<tr>
								<td
									style="background: url(../img/bg_mid_cuerpo.jpg) repeat-x left top; margin: 0px 6px 0px 10px; padding: 6px 0px 0px; color: #006699"><bean:message key="formulario.idioma.presentacion"/> &nbsp;&nbsp;</td>
								<td>

					 	 <select id="idiomaPresentacion" style="height:20px;font-size:100%; padding:0;border:1px solid #7f9db9;width:160px;margin-right:10px">

					 	<%

							Locale locale = LocaleFilterHelper.getCurrentLocale(request);
							String strIdioma = locale.getLanguage() + "_" + locale.getCountry();
							if(locale.getCountry()==null || locale.getCountry().equals(Defs.EMPTY_STRING)){
								strIdioma =locale.getLanguage() + "_" + ConstantesIdioma.ESPANA;
							}

						for(int indIdioma = 	0; indIdioma<idiomasDesplegables.size(); indIdioma++){
							Idioma objIdioma = (Idioma)idiomasDesplegables.get(indIdioma);
						%>
							<option value="<%=objIdioma.getCodigo()%>" <%=(objIdioma.getCodigo().equals(strIdioma) ? "selected" : "")%>><%=objIdioma.getDescripcion()%></option>
						<%}%>
						</select>

							</td>
							</tr>
							<tr>
								<td style="width: 8px" colspan="4"></td>
							</tr>
						</table>
						</div>

					</logic:present>

				    	    	<div class="cuerpomid">
								<%= request.getSession().getAttribute(Defs.FORMULARIO)%>
					      	 	</div>
					      	</div>
				    	</div>
					<div class="cuerpobt">
		      				<div class="cuerporightbt">
	        				<div class="cuerpomidbt">
	        					<input type="button" value="<bean:message key="formulario.aceptar"/>" id="enviar_solicitud" class="ok" onclick="javascript:rellenarDatosEspecificos();" />
	        				</div>
			      			</div>
			      		</div>
		      		</form>
		    	</div>
	    		</div>
	    	</div>

    	<script language="Javascript">
    		var f = "<%= (String)request.getAttribute(Defs.FECHA)%>";
    		var fecha = document.getElementById("fecha");
    		if (fecha != undefined){
    			fecha.innerHTML = f;
    		}
    	</script>

	<%} else { %>
		<p><bean:message key="cargando"/></p>
	<% } %>

		<iframe id="enviarSolicitud" name="enviarSolicitud" src="blank.html" class="frame_barra" style="visibility: hidden; position: absolute; height: 0px; width: 0px; z-index:2000;" frameborder='0' title='<bean:message key="resultado.validar"/>'></iframe>
		<div id="layer" style="display:none;z-index:1000;background:white;filter:alpha(opacity=50);-moz-opacity:.50;opacity:.50;"/></div>

	</body>

</html:html>