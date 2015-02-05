<%@ page language="java"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>

<%@page import="ieci.tecdoc.sgm.administracion.utils.Defs"%>
<%@page import="ieci.tecdoc.sgm.core.services.gestion_administracion.ConstantesGestionUsuariosAdministracion"%>
<%@page import="ieci.tecdoc.idoc.admin.api.EstructuraOrganizativaLdapManager" %>
<%@ page import="ieci.tecdoc.sgm.core.config.ports.PortsConfig"%>
<%@ page import="ieci.tecdoc.sgm.core.services.gestion_backoffice.ConstantesGestionUsuariosBackOffice"%>

<%
	String idApp = (String)session.getAttribute(ConstantesGestionUsuariosAdministracion.PARAMETRO_ID_APLICACION);

	boolean canChangePassword = false;
	String idEntidad = (String)session.getAttribute(ConstantesGestionUsuariosAdministracion.PARAMETRO_ID_ENTIDAD);

	boolean sso = false;
	String singleSignOn = (String) session.getServletContext().getAttribute(Defs.PLUGIN_SINGLE_SIGN_ON);

	if ((singleSignOn != null) && (singleSignOn.equalsIgnoreCase("true"))) {
		sso = true;
	} else {
		if ((idEntidad != null) && (idEntidad.trim().length() > 0)){
			canChangePassword = !EstructuraOrganizativaLdapManager.esEntidadLdap(idEntidad);
		}
	}
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
			serverPort = proxyHttpsSiCertPort;
		}
		else {
			serverPort = PortsConfig.getCertPort();
		}


		%>


<html:html>
	<head>
		<%
		String path = request.getContextPath();
		String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
		%>
		<base href="<%= basePath %>" />
		<link rel="stylesheet" href="css/estilos.css" type="text/css" />
		<script type="text/javascript" src="js/idioma.js" ></script>

		<script type="text/javascript">

			function getSelectedElementValue(elementId) {

			    var elt = document.getElementById(elementId);
			    if (elt.selectedIndex == -1){
			        return null;
			    }
			    return elt.options[elt.selectedIndex].value;
			}

			function mostrarEntidades() {

				var check = document.getElementById('interno');
				var formulario = document.getElementById('loginAccesoBean');
				var sel = document.getElementById('tr_entidades');
				var ents = document.getElementById('idEntidadInterno');

				var cert = document.getElementById('tr_certificado');

				if (check.checked == true) {

					<% if (sso) { %>

					// Deshabilitar los campos de usuario y password
					ctlUserName = document.getElementById('username');
					disabledInput(ctlUserName);
					ctlPassword = document.getElementById('password');
					disabledInput(ctlPassword);

					if (ents.options.length == 1) {
						formulario.action = '<html:rewrite page="/loginInterno.do" />';
						formulario.submit();
					} else {
						msgAutenticacionSSO = document.getElementById('msg_autenticacion_sso');
						msgAutenticacionSSO.style.visibility = 'visible';
						sel.style.visibility = 'visible';

						formulario.action = '<html:rewrite page="/loginInterno.do" />';
					}

					<% } else { %>

					if (ents.options.length >= 1) {
						sel.style.visibility = 'visible';
						cert.style.visibility="visible";
					} else {
						sel.style.visibility = 'hidden';
						cert.style.visibility="hidden";
					}
					formulario.action = '<html:rewrite page="/loginInterno.do" />';

					<% } %>
				} else {

					<% if (sso) { %>

					// Habilitar los campos de usuario y password
					ctlUserName = document.getElementById('username');
					enabledInput(ctlUserName);
					ctlPassword = document.getElementById('password');
					enabledInput(ctlPassword);

					msgAutenticacionSSO = document.getElementById('msg_autenticacion_sso');
					msgAutenticacionSSO.style.visibility = 'hidden';

					<% } %>

					sel.style.visibility = 'hidden';
					cert.style.visibility="hidden";
					formulario.action = '<html:rewrite page="/login.do" />';
				}
			}

			function disabledInput(ctl) {

				ctl.disabled = "disabled";
				ctl.className = 'login_disabled';
			}

			function enabledInput(ctl) {

				ctl.disabled = "";
				ctl.className = 'login';
			}

			function loginCertificado() {

				var idEntidadSeleccionada = getSelectedElementValue('idEntidadInterno');
				var linkOcultoLoginCertificado = '<%="https://"+request.getServerName()+":"+serverPort+request.getContextPath()+"/validacionCertificado.do"%>';

				var hrefLink = linkOcultoLoginCertificado +"?"+'<%=ConstantesGestionUsuariosAdministracion.PARAMETRO_ID_ENTIDAD%>'+"="+idEntidadSeleccionada;
				document.location.href = hrefLink;
				return false;
			}

		</script>
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

					              <p class="fila">
				            		<logic:present name="<%=Defs.MENSAJE_ERROR%>">
				            			<label class="error"><bean:message key="<%=(String)request.getAttribute(Defs.MENSAJE_ERROR)%>"/></label>
			            			</logic:present>
					              </p>

			            		<html:form styleId="loginAccesoBean" action="/login.do" method="post">

					              <p class="fila_sub">
			                		<label for="username" class="login" ><bean:message key="autenticacion.username" /></label>
			                		<input type="text" name="username" id="username" class="login"/>
					              </p>
					              <p class="fila_sub">
			                		<label for="password" class="login"><bean:message key="autenticacion.password" /></label>
			                		<input type="password" name="password" id="password" class="login" />
					              </p>

					                <%
					                if (!ConstantesGestionUsuariosAdministracion.APLICACION_ADMINISTRACION.equals(idApp)) {
					                //DESCOMENTAR LINEA ANTERIOR Y COMENTAR SIGUIENTE PARA QUE FUNCIONE CON USUARIOS DE BACKOFFICE
					                //if (ConstantesGestionUsuariosAdministracion.APLICACION_ADMINISTRACION.equals("2002020321284391.123")) {
					                %>

						              <p class="fila">
					                		<label for="interno" class="login" ><bean:message key="autenticacion.interno" /></label>
						                	<input type="checkbox" class="checkbox" name="interno" id="interno" onclick="javascript:mostrarEntidades();"/>
						                	<label for="linkLoginCertificado" id="tr_certificado" style="visibility:hidden;">
						                		<a href="javascript:loginCertificado();" id="linkLoginCertificado" style="font-size: 100%;margin-left:10px;">
						                			<bean:message key="autenticacion.acceso.certificado"/>
						                		</a>
						                	</label>
						              </p>
						              <% if (sso) { %>
						              <p class="fila" id="msg_autenticacion_sso" style="visibility: hidden;">
						              		<span class="login">&nbsp;</span>
						                	<span><bean:message key="autenticacion.sso" /></span>
						              </p>
						              <% } %>
						              <p class="fila" id="tr_entidades" style="visibility: hidden;">
					                		<label for="idEntidadInterno" class="login" ><bean:message key="autenticacion.entidad" /></label>
			    			            	<html:select property="idEntidadInterno" styleId="idEntidadInterno"  styleClass="login">
						            			<html:optionsCollection name="entidades" value="identificador" label="nombreLargo"/>
											</html:select>
						              </p>

			    			        <% } else { %>
						              <p class="fila">&nbsp;</p>
			    			        <% } %>
					              <p class="fila">&nbsp;</p>
					              <p class="fila_right">
									<input type="submit" class="botonFondo" value='<bean:message key="autenticacion.aceptar" />'/>
					              </p>
			            		</html:form>

			            		<% if (canChangePassword) { %>
									<p class="fila">
				            			<html:link action="seleccionClaveUsuario.do">
											<bean:message key="autenticacion.changepass" />
				            			</html:link>
					        		</p>
					        	<% } %>

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
		</div>
	</div>

  		<script language="Javascript">
  			document.getElementById('username').focus();
  		</script>
	</body>
</html:html>