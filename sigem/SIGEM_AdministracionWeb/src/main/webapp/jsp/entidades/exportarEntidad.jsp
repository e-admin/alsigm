<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>

<%@page import="ieci.tecdoc.sgm.admsistema.util.Defs"%>

<%
String[] tipos = (String[])session.getServletContext().getAttribute(Defs.PLUGIN_BASE_DATOS);
%>

<html>
	<head>
		<LINK href="css/estilos.css" type=text/css rel=stylesheet />

		<script language=javascript>
			var _direccionBase = '';
			var _puertoBase = '';
			var _usuarioBase = '';
			var _passwordBase = '';
			var _passwordRepetidoBase = '';
			var _usuarioBaseAD = '';
			var _passwordBaseAD = '';
			var _usuarioBaseGE = '';
			var _passwordBaseGE = '';
			var _usuarioBaseRP = '';
			var _passwordBaseRP = '';
			var _usuarioBaseTE = '';
			var _passwordBaseTE = '';
			var _usuarioBaseAudit = '';
			var _passwordBaseAudit = '';
			var _usuarioBaseSIR = '';
			var _passwordBaseSIR = '';
			var _instancia = '';

			function getDireccion() { return _direccionBase; }
			function getPuerto() { return _puertoBase; }
			function getUsuario() { return _usuarioBase; }
			function getPass1() { return _passwordBase; }
			function getPass2() { return _passwordRepetidoBase; }
			function getExisteFichero() { return false; }
			function getUsuarioAD() { return _usuarioBaseAD; }
			function getPassAD() { return _passwordBaseAD; }
			function getUsuarioGE() { return _usuarioBaseGE; }
			function getPassGE() { return _passwordBaseGE; }
			function getUsuarioRP() { return _usuarioBaseRP; }
			function getPassRP() { return _passwordBaseRP; }
			function getUsuarioTE() { return _usuarioBaseTE; }
			function getPassTE() { return _passwordBaseTE; }
			function getUsuarioAudit() { return _usuarioBaseAudit; }
			function getPassAudit() { return _passwordBaseAudit; }
			function getUsuarioSIR() { return _usuarioBaseSIR; }
			function getPassSIR() { return _passwordBaseSIR; }
			function getInstancia() { return _instancia; }

			function enviar() {
				document.getElementById('tipoBase').value = document.getElementById('<%=Defs.PARAMETRO_TIPO_BASE_DATOS%>').value;
				document.getElementById('baseDatosBean').submit();
			}

			function cancelar() {
				var formulario = document.getElementById('baseDatosBean');
				formulario.action = 'listadoEntidades.do';
				formulario.submit();
			}

			function realizarBusqueda() {
				var formulario = document.getElementById("busquedaBean");
				formulario.submit();
			}


			function comprobarConexionBBDD() {
				var tipo = document.getElementById('<%=Defs.PARAMETRO_TIPO_BASE_DATOS%>').value;
				if (tipo == '<%=Defs.PLUGIN_BASE_DATOS_POSTGRES%>') {
					comprobarConexionBBDDSeparadas();
				} else if (tipo == '<%=Defs.PLUGIN_BASE_DATOS_ORACLE%>') {
					comprobarConexionBBDDInstancia();
				} else if (tipo == '<%=Defs.PLUGIN_BASE_DATOS_DB2%>') {
					comprobarConexionBBDDInstancia();
				} else if (tipo == '<%=Defs.PLUGIN_BASE_DATOS_SQLSERVER%>') {
					comprobarConexionBBDDSeparadas();
				}
			}

			function comprobarConexionBBDDSeparadas() {
				var conexionFrame = document.getElementById('conexion');
				var direccionBase = document.getElementById('<%=Defs.PARAMETRO_DIRECCION_BASE_DATOS%>').value;
				var puertoBase = document.getElementById('<%=Defs.PARAMETRO_PUERTO_BASE_DATOS%>').value;
				var usuarioBase = document.getElementById('<%=Defs.PARAMETRO_USUARIO_BASE_DATOS%>').value;
				var password1Base = document.getElementById('<%=Defs.PARAMETRO_PASSWORD_BASE_DATOS%>').value;
				var password2Base = document.getElementById('<%=Defs.PARAMETRO_PASSWORD_REPETIDO_BASE_DATOS%>').value;
				_direccionBase = direccionBase;
				_puertoBase = puertoBase;
				_usuarioBase = usuarioBase;
				_passwordBase = password1Base;
				_passwordRepetidoBase = password2Base;
				desactivarBoton('boton_comprobar');
				conexionFrame.src = 'comprobarConexionBBDD.do?<%=Defs.PARAMETRO_DIRECCION_BASE_DATOS%>='+direccionBase+'&<%=Defs.PARAMETRO_PUERTO_BASE_DATOS%>='+puertoBase+'&<%=Defs.PARAMETRO_USUARIO_BASE_DATOS%>='+usuarioBase+'&<%=Defs.PARAMETRO_PASSWORD_BASE_DATOS%>='+password1Base+'&<%=Defs.PARAMETRO_PASSWORD_REPETIDO_BASE_DATOS%>='+password2Base;
			}

			function comprobarConexionBBDDInstancia() {
				var conexionFrame = document.getElementById('conexion');
				var direccionBase = document.getElementById('<%=Defs.PARAMETRO_DIRECCION_BASE_DATOS%>').value;
				var puertoBase = document.getElementById('<%=Defs.PARAMETRO_PUERTO_BASE_DATOS%>').value;
				var instancia = document.getElementById('<%=Defs.PARAMETRO_INSTANCIA%>').value;
				var usuarioBaseRP = document.getElementById('<%=Defs.PARAMETRO_USUARIO_BASE_DATOS_RP%>').value;
				var passwordBaseRP = document.getElementById('<%=Defs.PARAMETRO_PASSWORD_BASE_DATOS_RP%>').value;
				_direccionBase = direccionBase;
				_puertoBase = puertoBase;
				_instancia = instancia;
				_usuarioBaseAD = "";
				_passwordBaseAD = "";
				_usuarioBaseGE = "";
				_passwordBaseGE = "";
				_usuarioBaseRP = usuarioBaseRP;
				_passwordBaseRP = passwordBaseRP;
				_usuarioBaseTE = "";
				_passwordBaseTE = "";
				_usuarioBaseAudit = "";
				_passwordBaseAudit = "";
				_usuarioBaseSIR = "";
				_passwordBaseSIR = "";
				desactivarBoton('boton_comprobar');
				conexionFrame.src = 'comprobarConexionBBDDInstancia.do?<%=Defs.PARAMETRO_DIRECCION_BASE_DATOS%>='+direccionBase+'&<%=Defs.PARAMETRO_PUERTO_BASE_DATOS%>='+puertoBase+'&<%=Defs.PARAMETRO_USUARIO_BASE_DATOS_RP%>='+usuarioBaseRP+'&<%=Defs.PARAMETRO_PASSWORD_BASE_DATOS_RP%>='+passwordBaseRP+'&<%=Defs.PARAMETRO_INSTANCIA%>='+instancia;
			}

			function desactivarMensajes() {
				desactivarMensaje('tabla_mensajes_campos_obligatorios');
				desactivarMensaje('campos_obligatorios');
				desactivarMensaje('tabla_mensajes_password_incorrecto');
				desactivarMensaje('password_incorrecto');
				desactivarMensaje('tabla_mensajes_no_conexion');
				desactivarMensaje('no_conexion');
				desactivarMensaje('tabla_mensajes_no_conexion_ad');
				desactivarMensaje('no_conexion_ad');
				desactivarMensaje('tabla_mensajes_no_conexion_ge');
				desactivarMensaje('no_conexion_ge');
				desactivarMensaje('tabla_mensajes_no_conexion_rp');
				desactivarMensaje('no_conexion_rp');
				desactivarMensaje('tabla_mensajes_no_conexion_te');
				desactivarMensaje('no_conexion_te');
				desactivarMensaje('tabla_mensajes_datos_modificados');
				desactivarMensaje('datos_modificados');
				desactivarMensaje('tabla_mensajes_si_conexion');
				desactivarMensaje('si_conexion');
			}

			function desactivarMensaje(campo) {
				var field = document.getElementById(campo);
				field.style.visibility = 'hidden';
				field.style.position = 'absolute';
			}

			function activarMensaje(campo) {
				var tabla = document.getElementById('tabla_mensajes_' + campo);
				tabla.style.visibility = 'visible';
				tabla.style.position = 'relative';
				var field = document.getElementById(campo);
				field.style.visibility = 'visible';
				field.style.position = 'relative';
			}

			function activarBoton(boton) {
				document.getElementById(boton).disabled = false;
				document.getElementById(boton).style.background = 'url(img/fondo_boton.jpg)';
			}

			function desactivarBoton(boton) {
				document.getElementById(boton).disabled = true;
				document.getElementById(boton).style.background = 'url(img/fondo_boton_desactivado.jpg)';
			}

			function comprobarCambio(tipo) {
				var revalidar = false;
				if (document.getElementById('boton_aceptar').disabled == false) {
					if (tipo == '1') {
						var direccionBase = document.getElementById('<%=Defs.PARAMETRO_DIRECCION_BASE_DATOS%>').value;
						if (direccionBase != _direccionBase) {
							_direccionBase = direccionBase;
							revalidar = true;
						}
					} else if (tipo == '2') {
						var puertoBase = document.getElementById('<%=Defs.PARAMETRO_PUERTO_BASE_DATOS%>').value;
						if (puertoBase != _puertoBase) {
							_puertoBase = puertoBase;
							revalidar = true;
						}
					} else if (tipo == '3') {
						var usuarioBase = document.getElementById('<%=Defs.PARAMETRO_USUARIO_BASE_DATOS%>').value;
						if (usuarioBase != _usuarioBase) {
							_usuarioBase = usuarioBase;
							revalidar = true;
						}
					} else if (tipo == '4') {
						var passwordBase = document.getElementById('<%=Defs.PARAMETRO_PASSWORD_BASE_DATOS%>').value;
						if (passwordBase != _passwordBase) {
							_passwordBase = passwordBase;
							revalidar = true;
						}
					} else if (tipo == '5') {
						var passwordRepetidoBase = document.getElementById('<%=Defs.PARAMETRO_PASSWORD_REPETIDO_BASE_DATOS%>').value;
						if (passwordRepetidoBase != _passwordRepetidoBase) {
							_passwordRepetidoBase = passwordRepetidoBase;
							revalidar = true;
						}
					} else if (tipo == '6') {
						var instancia = document.getElementById('<%=Defs.PARAMETRO_INSTANCIA%>').value;
						if (instancia != _instancia) {
							_instancia = instancia;
							revalidar = true;
						}
					} else if (tipo == '11') {
						var usuarioBaseRP = document.getElementById('<%=Defs.PARAMETRO_USUARIO_BASE_DATOS_RP%>').value;
						if (usuarioBaseRP != _usuarioBaseRP) {
							_usuarioBaseRP = usuarioBaseRP;
							revalidar = true;
						}
					} else if (tipo == '12') {
						var passwordBaseRP = document.getElementById('<%=Defs.PARAMETRO_PASSWORD_BASE_DATOS_RP%>').value;
						if (passwordBaseRP != _passwordBaseRP) {
							_passwordBaseRP = passwordBaseRP;
							revalidar = true;
						}
					}

					if (revalidar == true) {
						activarBoton('boton_comprobar');
						desactivarBoton('boton_aceptar');
						desactivarMensajes();
						activarMensaje('datos_modificados');
					}
				}
			}

			function realizarOperacion(idEntidad, accion) {
				var formulario = document.getElementById("formulario");
				formulario.action = 'accionesEntidad.do';
				var campo = document.getElementById("<%=Defs.PARAMETRO_ENTIDAD_SELECCIONADA%>");
				campo.value = idEntidad;
				campo = document.getElementById("<%=Defs.PARAMETRO_ACCION_ENTIDAD%>");
				campo.value = accion;
				formulario.submit();
			}

			function mostrarCampos(valor){
				if (valor == false){
					activarCampoEsquema('3',1);
					document.getElementById('tre').style.visibility = 'hidden';
					document.getElementById('tre').style.display = 'none';
					activarCampoSeparados(0);
				} else {
					activarCampoSeparados(1);
					activarCampoEsquema('3',0);
					document.getElementById('tre').style.visibility = 'visible';
					document.getElementById('tre').style.display = 'block';
				}

				if (document.getElementById('boton_aceptar').disabled == false) {
					activarBoton('boton_comprobar');
					desactivarBoton('boton_aceptar');
					desactivarMensajes();
					activarMensaje('datos_modificados');
				} else {
					desactivarMensajes();
				}
			}

			function activarCampoEsquema(valor, ocultar) {
				var array = new Array('visible', 'hidden', 'block', 'none');
				document.getElementById('tru' + valor).style.visibility = array[ocultar];
				document.getElementById('tru' + valor).style.display = array[ocultar+2];
				document.getElementById('trp' + valor).style.visibility = array[ocultar];
				document.getElementById('trp' + valor).style.display = array[ocultar+2];
			}

			function activarCampoSeparados(ocultar) {
				var array = new Array('visible', 'hidden', 'block', 'none');
				document.getElementById('tru').style.visibility = array[ocultar];
				document.getElementById('tru').style.display = array[ocultar+2];
				document.getElementById('trp').style.visibility = array[ocultar];
				document.getElementById('trp').style.display = array[ocultar+2];
				document.getElementById('trpr').style.visibility = array[ocultar];
				document.getElementById('trpr').style.display = array[ocultar+2];
			}

			function desplegarCampos() {
				var tipo = document.getElementById('<%=Defs.PARAMETRO_TIPO_BASE_DATOS%>').value;
				if (tipo == '<%=Defs.PLUGIN_BASE_DATOS_POSTGRES%>') {
					mostrarCampos(false);
				} else if (tipo == '<%=Defs.PLUGIN_BASE_DATOS_ORACLE%>') {
					mostrarCampos(true);
				} else if (tipo == '<%=Defs.PLUGIN_BASE_DATOS_DB2%>') {
					mostrarCampos(true);
				} else if (tipo == '<%=Defs.PLUGIN_BASE_DATOS_SQLSERVER%>') {
					mostrarCampos(false);
				}
			}
		</script>
	</head>
	<body>
		<%String locale = (request.getLocale() != null) ? request.getLocale().getLanguage() : ""; if (locale == null || "".equals(locale)) locale = "es";%>
		<jsp:include flush="true" page="../cabecera.jsp">
			<jsp:param name="ayuda" value='<%=request.getContextPath() + "/jsp/ayudas/" + locale + "/procesos/exportar.html"%>' />
		</jsp:include>

		<form id=formulario action="" method=post>
			<input id=<%=Defs.PARAMETRO_ENTIDAD_SELECCIONADA%> type=hidden name=<%=Defs.PARAMETRO_ENTIDAD_SELECCIONADA%>>
			<input id=<%=Defs.PARAMETRO_ACCION_ENTIDAD%> type=hidden name=<%=Defs.PARAMETRO_ACCION_ENTIDAD%>>
		</form>

		<div id=contenedora align=center>
			<div class=cuerpo style="width: 80%">
				<div class=cuerporight>
					<div class=cuerpomid>
						<h1><bean:message key="entidades.titulo"/></h1>
						<div class=submenu3>
							<ul>
  								<li class=submen1on>
  									<img src="img/subme3_on.gif">
  									<a href="listadoEntidades.do" class=submen1on_a>
  										<bean:message key="tab.entidades"/>
  									</a>
  								</li>
								<li class="submen1off">
						        	<img src="img/subme3_on_of.gif" />
									<a href="listadoUsuarios.do">
										<bean:message key="tab.usuarios"/>
									</a>
									<img src="img/subme3_of_0.gif">
		  						</li>
  							</ul>
  						</div>
						<div class=cuadro>
							<div>
								<h1><bean:message key="entidades.exportar"/></h1>
								<div class="col" align="right" style="height: 30px;">
									<table border="0" cellspacing="0" cellpadding="0">
       									<tr align=right>
						       				<td width="100%">&nbsp;</td>
						       				<td width="20%">
						       					<img src="img/ico_monitorizar.jpg" style="cursor: pointer" onclick="javascript:realizarOperacion('', '<%=Defs.ACCION_MONITORIZAR_ENTIDAD%>')" alt="<bean:message key="entidades.boton.monitorizar"/>" title="<bean:message key="entidades.boton.monitorizar"/>"/>
						       				</td>
						       				<td style="font-size: 130%;" valign=top>&nbsp;&nbsp;|&nbsp;&nbsp;</td>
						       				<td width="20%">
						       					<img src="img/ico_delete_proc.jpg" style="cursor: pointer" onclick="javascript:realizarOperacion('', '<%=Defs.ACCION_BORRAR_PROCESO%>')" alt="<bean:message key="entidades.boton.borrar"/>" title="<bean:message key="entidades.boton.borrar"/>"/>
						       				</td>
						       				<td style="font-size: 130%;" valign=top>&nbsp;&nbsp;|&nbsp;&nbsp;</td>
						       				<td width="20%">
						       					<img src="img/ico_configuration.jpg" style="cursor: pointer" onclick="javascript:realizarOperacion('', '<%=Defs.ACCION_CONFIGURAR_SERVIDOR%>')" alt="<bean:message key="entidades.boton.configurar"/>" title="<bean:message key="entidades.boton.configurar"/>"/>
						       				</td>
						       				<td style="font-size: 130%;" valign=top>&nbsp;&nbsp;|&nbsp;&nbsp;</td>
						       				<html:form styleId="busquedaBean" action="/busquedaEntidades.do" method="post">
							       				<td width="20%">
	       											<html:text property="<%=Defs.PARAMETRO_BUSQUEDA%>" styleId="<%=Defs.PARAMETRO_BUSQUEDA%>"></html:text>
						    					</td>
							       				<td width="20%">
							       					<img src="img/ico_search.jpg" style="cursor: pointer" onclick="javascript:realizarBusqueda()" alt="<bean:message key="entidades.boton.buscar"/>" title="<bean:message key="entidades.boton.buscar"/>"/>
							       				</td>
							       			</html:form>
       									</tr>
       								</table>
								</div>
								<br/>
								<html:form styleId="baseDatosBean" action="/exportarEntidad.do" method="post">
									<html:hidden styleId="<%=Defs.PARAMETRO_ID_ENTIDAD%>" property="<%=Defs.PARAMETRO_ID_ENTIDAD%>"/>
									<html:hidden styleId="tipoBase" property="tipoBase"/>
									<html:hidden styleId="<%=Defs.PARAMETRO_USUARIO_BASE_DATOS_AD%>" property="<%=Defs.PARAMETRO_USUARIO_BASE_DATOS_AD%>"/>
									<html:hidden styleId="<%=Defs.PARAMETRO_PASSWORD_BASE_DATOS_AD%>" property="<%=Defs.PARAMETRO_PASSWORD_BASE_DATOS_AD%>"/>
									<html:hidden styleId="<%=Defs.PARAMETRO_USUARIO_BASE_DATOS_GE%>" property="<%=Defs.PARAMETRO_USUARIO_BASE_DATOS_GE%>"/>
									<html:hidden styleId="<%=Defs.PARAMETRO_PASSWORD_BASE_DATOS_GE%>" property="<%=Defs.PARAMETRO_PASSWORD_BASE_DATOS_GE%>"/>
									<html:hidden styleId="<%=Defs.PARAMETRO_USUARIO_BASE_DATOS_TE%>" property="<%=Defs.PARAMETRO_USUARIO_BASE_DATOS_TE%>"/>
									<html:hidden styleId="<%=Defs.PARAMETRO_PASSWORD_BASE_DATOS_TE%>" property="<%=Defs.PARAMETRO_PASSWORD_BASE_DATOS_TE%>"/>
									<html:hidden styleId="<%=Defs.PARAMETRO_USUARIO_BASE_DATOS_AUDIT%>" property="<%=Defs.PARAMETRO_USUARIO_BASE_DATOS_AUDIT%>"/>
									<html:hidden styleId="<%=Defs.PARAMETRO_PASSWORD_BASE_DATOS_AUDIT%>" property="<%=Defs.PARAMETRO_PASSWORD_BASE_DATOS_AUDIT%>"/>
									<html:hidden styleId="<%=Defs.PARAMETRO_USUARIO_BASE_DATOS_SIR%>" property="<%=Defs.PARAMETRO_USUARIO_BASE_DATOS_SIR%>"/>
									<html:hidden styleId="<%=Defs.PARAMETRO_PASSWORD_BASE_DATOS_SIR%>" property="<%=Defs.PARAMETRO_PASSWORD_BASE_DATOS_SIR%>"/>
									<table border=0 cellpadding=0 cellspacing=0 align=center style="padding-left: 12px; padding-bottom:22px;">
										<tr>
											<td colspan=2>
												<table id="tabla_mensajes_campos_obligatorios" border=0 cellpadding=0 cellspacing=0 style="padding-bottom:22px; position: absolute; visibility: hidden;">
													<tr>
														<td colspan=2>
									                		<label id="campos_obligatorios" class="gr_error" style="visibility: hidden; position:absolute"><bean:message key="mensaje.error.entidad.campos_obligatorios"/></label>
									                	</td>
													</tr>
													<tr><td colspan=2><br/></td></tr>
												</table>
												<table id="tabla_mensajes_password_incorrecto" border=0 cellpadding=0 cellspacing=0 style="padding-bottom:22px; position: absolute; visibility: hidden;">
													<tr>
														<td colspan=2>
									                		<label id="password_incorrecto" class="gr_error" style="visibility: hidden; position:absolute"><bean:message key="mensaje.error.entidad.password_incorrecto"/></label>
									                	</td>
													</tr>
													<tr><td colspan=2><br/></td></tr>
												</table>
												<table id="tabla_mensajes_no_conexion" border=0 cellpadding=0 cellspacing=0 style="padding-bottom:22px; position: absolute; visibility: hidden;">
													<tr>
														<td colspan=2>
									                		<label id="no_conexion" class="gr_error" style="visibility: hidden; position:absolute"><bean:message key="mensaje.error.entidad.no_conexion"/></label>
									                	</td>
													</tr>
													<tr><td colspan=2><br/></td></tr>
												</table>
												<table id="tabla_mensajes_no_conexion_ad" border=0 cellpadding=0 cellspacing=0 style="position: absolute; visibility: hidden;">
													<tr>
														<td colspan=2>
									                		<label id="no_conexion_ad" class="gr_error" style="visibility: hidden; position:absolute"><bean:message key="mensaje.error.entidad.no_conexion_ad"/></label>
									                	</td>
													</tr>
													<tr><td colspan=2><br/></td></tr>
												</table>
												<table id="tabla_mensajes_no_conexion_ge" border=0 cellpadding=0 cellspacing=0 style="position: absolute; visibility: hidden;">
													<tr>
														<td colspan=2>
									                		<label id="no_conexion_ge" class="gr_error" style="visibility: hidden; position:absolute"><bean:message key="mensaje.error.entidad.no_conexion_ge"/></label>
									                	</td>
													</tr>
													<tr><td colspan=2><br/></td></tr>
												</table>
												<table id="tabla_mensajes_no_conexion_rp" border=0 cellpadding=0 cellspacing=0 style="position: absolute; visibility: hidden;">
													<tr>
														<td colspan=2>
									                		<label id="no_conexion_rp" class="gr_error" style="visibility: hidden; position:absolute"><bean:message key="mensaje.error.entidad.no_conexion_rp"/></label>
									                	</td>
													</tr>
													<tr><td colspan=2><br/></td></tr>
												</table>
												<table id="tabla_mensajes_no_conexion_te" border=0 cellpadding=0 cellspacing=0 style="position: absolute; visibility: hidden;">
													<tr>
														<td colspan=2>
									                		<label id="no_conexion_te" class="gr_error" style="visibility: hidden; position:absolute"><bean:message key="mensaje.error.entidad.no_conexion_te"/></label>
									                	</td>
													</tr>
													<tr><td colspan=2><br/></td></tr>
												</table>
												<table id="tabla_mensajes_datos_modificados" border=0 cellpadding=0 cellspacing=0 style="padding-bottom:22px; position: absolute; visibility: hidden;">
													<tr>
														<td colspan=2>
									                		<label id="datos_modificados" class="gr_error" style="visibility: hidden; position:absolute"><bean:message key="mensaje.error.entidad.datos_modificados"/></label>
									                	</td>
													</tr>
													<tr><td colspan=2><br/></td></tr>
												</table>
												<table id="tabla_mensajes_si_conexion" border=0 cellpadding=0 cellspacing=0 style="padding-bottom:22px; position: absolute; visibility: hidden;">
													<tr>
														<td colspan=2>
									                		<label id="si_conexion" class="gr_informativo" style="visibility: hidden; position:absolute"><bean:message key="mensaje.informativo.entidad.si_conexion"/></label>
									                	</td>
													</tr>
													<tr><td colspan=2><br/></td></tr>
												</table>
											</td>
										</tr>
										<tr>
											<td>
												<label for='<%="_"+Defs.PARAMETRO_ID_ENTIDAD%>' class="gr_ext"><bean:message key="entidad.exportar.idEntidad" /></label>
											</td>
											<td>
						                		<html:text property="<%=Defs.PARAMETRO_ID_ENTIDAD%>" styleId='<%="_"+Defs.PARAMETRO_ID_ENTIDAD%>' maxlength="3" disabled="true"></html:text>
						                	</td>
										</tr>
										<tr><td colspan=2>&nbsp;</td></tr>
										<tr>
											<td colspan=2 class="td-azul" style="border-right: 2px solid #fff">
												<bean:message key="entidad.importar.configuracion_basedatos" />
											</td>
										</tr>
										<tr>
											<td>
												<label for="<%=Defs.PARAMETRO_DIRECCION_BASE_DATOS%>" class="gr_ext"><bean:message key="entidad.exportar.direccion" /></label>
											</td>
											<td>
						                		<html:text property="<%=Defs.PARAMETRO_DIRECCION_BASE_DATOS%>" styleId="<%=Defs.PARAMETRO_DIRECCION_BASE_DATOS%>" tabindex="1" onchange="javascript:comprobarCambio('1');"></html:text>
						                	</td>
										</tr>
										<tr>
											<td>
												<label for="<%=Defs.PARAMETRO_PUERTO_BASE_DATOS%>" class="gr_ext"><bean:message key="entidad.exportar.puerto" /></label>
											</td>
											<td>
						                		<html:text property="<%=Defs.PARAMETRO_PUERTO_BASE_DATOS%>" styleId="<%=Defs.PARAMETRO_PUERTO_BASE_DATOS%>" tabindex="2" onchange="javascript:comprobarCambio('2');"></html:text>
						                	</td>
										</tr>
										<tr>
											<td>
												<label for="<%=Defs.PARAMETRO_TIPO_BASE_DATOS%>" class="gr_ext"><bean:message key="entidad.exportar.tipoBaseDatos" /></label>
											</td>
											<td>
						                		<select id="<%=Defs.PARAMETRO_TIPO_BASE_DATOS%>" name="<%=Defs.PARAMETRO_TIPO_BASE_DATOS%>" tabindex="3" onchange="javascript:desplegarCampos();" class="gr" style="color: black">
						                			<%for (int ind=0; ind<tipos.length; ind++){%>
						                				<option value="<%=tipos[ind]%>"><%=tipos[ind]%></option>
						                			<%}%>
						                		</select>
						                	</td>
										</tr>
										<tr id="tru">
											<td>
												<label for="<%=Defs.PARAMETRO_USUARIO_BASE_DATOS%>" class="gr_ext"><bean:message key="entidad.exportar.usuario" /></label>
											</td>
											<td>
						                		<html:text property="<%=Defs.PARAMETRO_USUARIO_BASE_DATOS%>" styleId="<%=Defs.PARAMETRO_USUARIO_BASE_DATOS%>" tabindex="4" onchange="javascript:comprobarCambio('3');"></html:text>
						                	</td>
										</tr>
										<tr id="trp">
											<td>
												<label for="<%=Defs.PARAMETRO_PASSWORD_BASE_DATOS%>" class="gr_ext"><bean:message key="entidad.exportar.password" /></label>
											</td>
											<td>
						                		<html:password property="<%=Defs.PARAMETRO_PASSWORD_BASE_DATOS%>" styleId="<%=Defs.PARAMETRO_PASSWORD_BASE_DATOS%>" tabindex="5"  onchange="javascript:comprobarCambio('4');"></html:password>
						                	</td>
										</tr>
										<tr id="trpr">
											<td>
												<label for="<%=Defs.PARAMETRO_PASSWORD_REPETIDO_BASE_DATOS%>" class="gr_ext"><bean:message key="entidad.exportar.password_repetido" /></label>
											</td>
											<td>
						                		<html:password property="<%=Defs.PARAMETRO_PASSWORD_REPETIDO_BASE_DATOS%>" styleId="<%=Defs.PARAMETRO_PASSWORD_REPETIDO_BASE_DATOS%>" tabindex="6" onchange="javascript:comprobarCambio('5');"></html:password>
						                	</td>
										</tr>
										<tr id="tre" style="visibility: hidden; display: none;">
											<td>
												<label for="<%=Defs.PARAMETRO_INSTANCIA%>" class="gr_ext"><bean:message key="entidad.exportar.instanciaDesc" /></label>
											</td>
											<td>
						                		<html:text property="<%=Defs.PARAMETRO_INSTANCIA%>" styleId="<%=Defs.PARAMETRO_INSTANCIA%>" tabindex="7" onchange="javascript:comprobarCambio('6');"></html:text>
						                	</td>
										</tr>
										<tr id="tru3" style="visibility: hidden; display: none;">
											<td>
												<label for="<%=Defs.PARAMETRO_USUARIO_BASE_DATOS_RP%>" class="gr_ext"><bean:message key="entidad.exportar.usuarioRP" /></label>
											</td>
											<td>
						                		<html:text property="<%=Defs.PARAMETRO_USUARIO_BASE_DATOS_RP%>" styleId="<%=Defs.PARAMETRO_USUARIO_BASE_DATOS_RP%>" tabindex="12" onchange="javascript:comprobarCambio('11');"></html:text>
						                	</td>
										</tr>
										<tr id="trp3" style="visibility: hidden; display: none;">
											<td>
												<label for="<%=Defs.PARAMETRO_PASSWORD_BASE_DATOS_RP%>" class="gr_ext"><bean:message key="entidad.exportar.passwordRP" /></label>
											</td>
											<td>
						                		<html:password property="<%=Defs.PARAMETRO_PASSWORD_BASE_DATOS_RP%>" styleId="<%=Defs.PARAMETRO_PASSWORD_BASE_DATOS_RP%>" tabindex="13"  onchange="javascript:comprobarCambio('12');"></html:password>
						                	</td>
										</tr>
										<tr><td colspan=2><br/></td></tr>
										<tr align=center>
											<td colspan=2>
												<input id="boton_comprobar" onclick="javascript:comprobarConexionBBDD()" type=button value="<bean:message key="entidades.boton.comprobar"/>" class="ok" tabindex="16" title="<bean:message key="entidades.boton.comprobar"/>"/>
												<input id="boton_aceptar" onclick="javascript:enviar()" type=button value="<bean:message key="boton.aceptar"/>" class="ok_desactivado" tabindex="17" disabled='true' title="<bean:message key="boton.aceptar"/>"/>
												<input onclick="javascript:cancelar()" type=button value="<bean:message key="boton.cancelar"/>" class="ok" tabindex="18" title="<bean:message key="boton.cancelar"/>"/>
											</td>
										</tr>
									</table>
								</html:form>
							</div>
						</div>
					</div>
					<div class=cuerpobt>
						<div class=cuerporightbt>
							<div class=cuerpomidbt></div>
						</div>
					</div>
				</div>
				<div id=pie></div>
			</div>
		</div>
		<iframe id=conexion name=conexion src="<html:rewrite page="/jsp/blank.html" />" style="width: 0px; height: 0px; top: 0px; visibility: hidden"></iframe>
	</body>
</html>