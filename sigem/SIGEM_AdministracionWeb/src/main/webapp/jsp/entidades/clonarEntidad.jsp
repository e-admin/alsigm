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
			var _direccionBaseExp = '';
			var _puertoBaseExp = '';
			var _usuarioBaseExp = '';
			var _passwordBaseExp = '';
			var _passwordRepetidoBaseExp = '';
			var _direccionBaseImp = '';
			var _puertoBaseImp = '';
			var _usuarioBaseImp = '';
			var _passwordBaseImp = '';
			var _passwordRepetidoBaseImp = '';
			var _direccionFtp = '';
			var _puertoFtp = '';
			var _rutaFtp = '';
			var _usuarioFtp = '';
			var _passwordFtp = '';
			var _passwordRepetidoFtp = '';
			var _usuarioBaseADExp = '';
			var _passwordBaseADExp = '';
			var _usuarioBaseGEExp = '';
			var _passwordBaseGEExp = '';
			var _usuarioBaseRPExp = '';
			var _passwordBaseRPExp = '';
			var _usuarioBaseTEExp = '';
			var _passwordBaseTEExp = '';
			var _usuarioBaseAuditExp = '';
			var _passwordBaseAuditExp = '';
			var _usuarioBaseSIRExp = '';
			var _passwordBaseSIRExp = '';
			var _instanciaExp = '';
			var _usuarioBaseADImp = '';
			var _passwordBaseADImp = '';
			var _usuarioBaseGEImp = '';
			var _passwordBaseGEImp = '';
			var _usuarioBaseRPImp = '';
			var _passwordBaseRPImp = '';
			var _usuarioBaseTEImp = '';
			var _passwordBaseTEImp = '';
			var _usuarioBaseAuditImp = '';
			var _passwordBaseAuditImp = '';
			var _usuarioBaseSIRImp = '';
			var _passwordBaseSIRImp = '';
			var _instanciaImp = '';

			function getDireccionExp() { return _direccionBaseExp; }
			function getPuertoExp() { return _puertoBaseExp; }
			function getUsuarioExp() { return _usuarioBaseExp; }
			function getPass1Exp() { return _passwordBaseExp; }
			function getPass2Exp() { return _passwordRepetidoBaseExp; }
			function getDireccionImp() { return _direccionBaseImp; }
			function getPuertoImp() { return _puertoBaseImp; }
			function getUsuarioImp() { return _usuarioBaseImp; }
			function getPass1Imp() { return _passwordBaseImp; }
			function getPass2Imp() { return _passwordRepetidoBaseImp; }
			function getDireccionFtp() { return _direccionFtp; }
			function getPuertoFtp() { return _puertoFtp; }
			function getRutaFtp() { return _rutaFtp; }
			function getUsuarioFtp() { return _usuarioFtp; }
			function getPass1Ftp() { return _passwordFtp; }
			function getPass2Ftp() { return _passwordRepetidoFtp; }
			function getUsuarioADExp() { return _usuarioBaseADExp; }
			function getPassADExp() { return _passwordBaseADExp; }
			function getUsuarioGEExp() { return _usuarioBaseGEExp; }
			function getPassGEExp() { return _passwordBaseGEExp; }
			function getUsuarioRPExp() { return _usuarioBaseRPExp; }
			function getPassRPExp() { return _passwordBaseRPExp; }
			function getUsuarioTEExp() { return _usuarioBaseTEExp; }
			function getPassTEExp() { return _passwordBaseTEExp; }
			function getUsuarioAuditExp() { return _usuarioBaseAuditExp; }
			function getPassAuditExp() { return _passwordBaseAuditExp; }
			function getUsuarioSIRExp() { return _usuarioBaseSIRExp; }
			function getPassSIRExp() { return _passwordBaseSIRExp; }
			function getInstanciaExp() { return _instanciaExp; }
			function getUsuarioADImp() { return _usuarioBaseADImp; }
			function getPassADImp() { return _passwordBaseADImp; }
			function getUsuarioGEImp() { return _usuarioBaseGEImp; }
			function getPassGEImp() { return _passwordBaseGEImp; }
			function getUsuarioRPImp() { return _usuarioBaseRPImp; }
			function getPassRPImp() { return _passwordBaseRPImp; }
			function getUsuarioTEImp() { return _usuarioBaseTEImp; }
			function getPassTEImp() { return _passwordBaseTEImp; }
			function getUsuarioAuditImp() { return _usuarioBaseAuditImp; }
			function getPassAuditImp() { return _passwordBaseAuditImp; }
			function getUsuarioSIRImp() { return _usuarioBaseSIRImp; }
			function getPassSIRImp() { return _passwordBaseSIRImp; }
			function getInstanciaImp() { return _instanciaImp; }

			function enviar() {
				document.getElementById('tipoBaseExp').value = document.getElementById('<%=Defs.PARAMETRO_TIPO_BASE_DATOS_EXP%>').value;
				document.getElementById('tipoBaseImp').value = document.getElementById('<%=Defs.PARAMETRO_TIPO_BASE_DATOS_IMP%>').value;
				if (confirm("<bean:message key="mensaje.informativo.proceso.limpieza" />"))
					document.getElementById('<%=Defs.PARAMETRO_LIMPIAR%>').value = 'true';

				document.getElementById('baseDatosx2AndFtpBean').submit();
			}

			function cancelar() {
				var formulario = document.getElementById('baseDatosx2AndFtpBean');
				formulario.action = 'listadoEntidades.do';
				formulario.submit();
			}

			function realizarBusqueda() {
				var formulario = document.getElementById("busquedaBean");
				formulario.submit();
			}

			function comprobarConexionBBDD() {
				var tipo = document.getElementById('<%=Defs.PARAMETRO_TIPO_BASE_DATOS_EXP%>').value;
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
				var direccionBaseExp = document.getElementById('<%=Defs.PARAMETRO_DIRECCION_BASE_DATOS_EXP%>').value;
				var puertoBaseExp = document.getElementById('<%=Defs.PARAMETRO_PUERTO_BASE_DATOS_EXP%>').value;
				var usuarioBaseExp = document.getElementById('<%=Defs.PARAMETRO_USUARIO_BASE_DATOS_EXP%>').value;
				var password1BaseExp = document.getElementById('<%=Defs.PARAMETRO_PASSWORD_BASE_DATOS_EXP%>').value;
				var password2BaseExp = document.getElementById('<%=Defs.PARAMETRO_PASSWORD_REPETIDO_BASE_DATOS_EXP%>').value;
				var direccionBaseImp = document.getElementById('<%=Defs.PARAMETRO_DIRECCION_BASE_DATOS_IMP%>').value;
				var puertoBaseImp = document.getElementById('<%=Defs.PARAMETRO_PUERTO_BASE_DATOS_IMP%>').value;
				var usuarioBaseImp = document.getElementById('<%=Defs.PARAMETRO_USUARIO_BASE_DATOS_IMP%>').value;
				var password1BaseImp = document.getElementById('<%=Defs.PARAMETRO_PASSWORD_BASE_DATOS_IMP%>').value;
				var password2BaseImp = document.getElementById('<%=Defs.PARAMETRO_PASSWORD_REPETIDO_BASE_DATOS_IMP%>').value;
				var direccionFtp = document.getElementById('<%=Defs.PARAMETRO_DIRECCION_FTP%>').value;
				var puertoFtp = document.getElementById('<%=Defs.PARAMETRO_PUERTO_FTP%>').value;
				var rutaFtp = document.getElementById('<%=Defs.PARAMETRO_RUTA_FTP%>').value;
				var usuarioFtp = document.getElementById('<%=Defs.PARAMETRO_USUARIO_FTP%>').value;
				var password1Ftp = document.getElementById('<%=Defs.PARAMETRO_PASSWORD_FTP%>').value;
				var password2Ftp = document.getElementById('<%=Defs.PARAMETRO_PASSWORD_REPETIDO_FTP%>').value;
				_direccionBaseExp = direccionBaseExp;
				_puertoBaseExp = puertoBaseExp;
				_usuarioBaseExp = usuarioBaseExp;
				_passwordBaseExp = password1BaseExp;
				_passwordRepetidoBaseExp = password2BaseExp;
				_direccionBaseImp = direccionBaseImp;
				_puertoBaseImp = puertoBaseImp;
				_usuarioBaseImp = usuarioBaseImp;
				_passwordBaseImp = password1BaseImp;
				_passwordRepetidoBaseImp = password2BaseImp;
				_direccionFtp = direccionFtp;
				_puertoFtp = puertoFtp;
				_rutaFtp = rutaFtp;
				_usuarioFtp = usuarioFtp;
				_passwordFtp = password1Ftp;
				_passwordRepetidoFtp = password2Ftp;
				desactivarBoton('boton_comprobar');
				conexionFrame.src = 'comprobarConexionBBDDx2yFTP.do?<%=Defs.PARAMETRO_DIRECCION_BASE_DATOS_EXP%>='+direccionBaseExp+'&<%=Defs.PARAMETRO_PUERTO_BASE_DATOS_EXP%>='+puertoBaseExp+'&<%=Defs.PARAMETRO_USUARIO_BASE_DATOS_EXP%>='+usuarioBaseExp+'&<%=Defs.PARAMETRO_PASSWORD_BASE_DATOS_EXP%>='+password1BaseExp+'&<%=Defs.PARAMETRO_PASSWORD_REPETIDO_BASE_DATOS_EXP%>='+password2BaseExp+'&<%=Defs.PARAMETRO_DIRECCION_BASE_DATOS_IMP%>='+direccionBaseImp+'&<%=Defs.PARAMETRO_PUERTO_BASE_DATOS_IMP%>='+puertoBaseImp+'&<%=Defs.PARAMETRO_USUARIO_BASE_DATOS_IMP%>='+usuarioBaseImp+'&<%=Defs.PARAMETRO_PASSWORD_BASE_DATOS_IMP%>='+password1BaseImp+'&<%=Defs.PARAMETRO_PASSWORD_REPETIDO_BASE_DATOS_IMP%>='+password2BaseImp+'&<%=Defs.PARAMETRO_DIRECCION_FTP%>='+direccionFtp+'&<%=Defs.PARAMETRO_PUERTO_FTP%>='+puertoFtp+'&<%=Defs.PARAMETRO_RUTA_FTP%>='+rutaFtp+'&<%=Defs.PARAMETRO_USUARIO_FTP%>='+usuarioFtp+'&<%=Defs.PARAMETRO_PASSWORD_FTP%>='+password1Ftp+'&<%=Defs.PARAMETRO_PASSWORD_REPETIDO_FTP%>='+password2Ftp;
			}

			function comprobarConexionBBDDInstancia() {
				var conexionFrame = document.getElementById('conexion');
				var direccionBaseExp = document.getElementById('<%=Defs.PARAMETRO_DIRECCION_BASE_DATOS_EXP%>').value;
				var puertoBaseExp = document.getElementById('<%=Defs.PARAMETRO_PUERTO_BASE_DATOS_EXP%>').value;
				var usuarioBaseExp = document.getElementById('<%=Defs.PARAMETRO_USUARIO_BASE_DATOS_EXP%>').value;
				var password1BaseExp = document.getElementById('<%=Defs.PARAMETRO_PASSWORD_BASE_DATOS_EXP%>').value;
				var password2BaseExp = document.getElementById('<%=Defs.PARAMETRO_PASSWORD_REPETIDO_BASE_DATOS_EXP%>').value;
				var direccionBaseImp = document.getElementById('<%=Defs.PARAMETRO_DIRECCION_BASE_DATOS_IMP%>').value;
				var puertoBaseImp = document.getElementById('<%=Defs.PARAMETRO_PUERTO_BASE_DATOS_IMP%>').value;
				var usuarioBaseImp = document.getElementById('<%=Defs.PARAMETRO_USUARIO_BASE_DATOS_IMP%>').value;
				var password1BaseImp = document.getElementById('<%=Defs.PARAMETRO_PASSWORD_BASE_DATOS_IMP%>').value;
				var password2BaseImp = document.getElementById('<%=Defs.PARAMETRO_PASSWORD_REPETIDO_BASE_DATOS_IMP%>').value;
				var direccionFtp = document.getElementById('<%=Defs.PARAMETRO_DIRECCION_FTP%>').value;
				var puertoFtp = document.getElementById('<%=Defs.PARAMETRO_PUERTO_FTP%>').value;
				var rutaFtp = document.getElementById('<%=Defs.PARAMETRO_RUTA_FTP%>').value;
				var usuarioFtp = document.getElementById('<%=Defs.PARAMETRO_USUARIO_FTP%>').value;
				var password1Ftp = document.getElementById('<%=Defs.PARAMETRO_PASSWORD_FTP%>').value;
				var password2Ftp = document.getElementById('<%=Defs.PARAMETRO_PASSWORD_REPETIDO_FTP%>').value;

				var instanciaExp = document.getElementById('<%=Defs.PARAMETRO_INSTANCIA_EXP%>').value;
				var usuarioBaseRPExp = document.getElementById('<%=Defs.PARAMETRO_USUARIO_BASE_DATOS_RP_EXP%>').value;
				var passwordBaseRPExp = document.getElementById('<%=Defs.PARAMETRO_PASSWORD_BASE_DATOS_RP_EXP%>').value;
				var instanciaImp = document.getElementById('<%=Defs.PARAMETRO_INSTANCIA_IMP%>').value;
				var usuarioBaseRPImp = document.getElementById('<%=Defs.PARAMETRO_USUARIO_BASE_DATOS_RP_IMP%>').value;
				var passwordBaseRPImp = document.getElementById('<%=Defs.PARAMETRO_PASSWORD_BASE_DATOS_RP_IMP%>').value;
				var usuarioBaseTEImp = document.getElementById('<%=Defs.PARAMETRO_USUARIO_BASE_DATOS_TE_IMP%>').value;
				var passwordBaseTEImp = document.getElementById('<%=Defs.PARAMETRO_PASSWORD_BASE_DATOS_TE_IMP%>').value;
				var usuarioBaseGEImp = document.getElementById('<%=Defs.PARAMETRO_USUARIO_BASE_DATOS_GE_IMP%>').value;
				var passwordBaseGEImp = document.getElementById('<%=Defs.PARAMETRO_PASSWORD_BASE_DATOS_GE_IMP%>').value;
				var usuarioBaseADImp = document.getElementById('<%=Defs.PARAMETRO_USUARIO_BASE_DATOS_AD_IMP%>').value;
				var passwordBaseADImp = document.getElementById('<%=Defs.PARAMETRO_PASSWORD_BASE_DATOS_AD_IMP%>').value;
				var usuarioBaseAuditImp = document.getElementById('<%=Defs.PARAMETRO_USUARIO_BASE_DATOS_AUDIT_IMP%>').value;
				var passwordBaseAuditImp = document.getElementById('<%=Defs.PARAMETRO_PASSWORD_BASE_DATOS_AUDIT_IMP%>').value;
				var usuarioBaseSIRImp = document.getElementById('<%=Defs.PARAMETRO_USUARIO_BASE_DATOS_SIR_IMP%>').value;
				var passwordBaseSIRImp = document.getElementById('<%=Defs.PARAMETRO_PASSWORD_BASE_DATOS_SIR_IMP%>').value;

				_direccionBaseExp = direccionBaseExp;
				_puertoBaseExp = puertoBaseExp;
				_usuarioBaseExp = usuarioBaseExp;
				_passwordBaseExp = password1BaseExp;
				_passwordRepetidoBaseExp = password2BaseExp;
				_direccionBaseImp = direccionBaseImp;
				_puertoBaseImp = puertoBaseImp;
				_usuarioBaseImp = usuarioBaseImp;
				_passwordBaseImp = password1BaseImp;
				_passwordRepetidoBaseImp = password2BaseImp;
				_direccionFtp = direccionFtp;
				_puertoFtp = puertoFtp;
				_rutaFtp = rutaFtp;
				_usuarioFtp = usuarioFtp;
				_passwordFtp = password1Ftp;
				_passwordRepetidoFtp = password2Ftp;

				_instanciaExp = instanciaExp;
				_usuarioBaseADExp = "";
				_passwordBaseADExp = "";
				_usuarioBaseGEExp = "";
				_passwordBaseGEExp = "";
				_usuarioBaseRPExp = usuarioBaseRPExp;
				_passwordBaseRPExp = passwordBaseRPExp;
				_usuarioBaseTEExp = "";
				_passwordBaseTEExp = "";
				_usuarioBaseAuditExp = "";
				_passwordBaseAuditExp = "";
				_usuarioBaseSIRExp = "";
				_passwordBaseSIRExp = "";
				_instanciaImp = instanciaImp;
				_usuarioBaseADImp = usuarioBaseADImp;
				_passwordBaseADImp = passwordBaseADImp;
				_usuarioBaseGEImp = usuarioBaseGEImp;
				_passwordBaseGEImp = passwordBaseGEImp;
				_usuarioBaseRPImp = usuarioBaseRPImp;
				_passwordBaseRPImp = passwordBaseRPImp;
				_usuarioBaseTEImp = usuarioBaseTEImp;
				_passwordBaseTEImp = passwordBaseTEImp;
				_usuarioBaseAuditImp = usuarioBaseAuditImp;
				_passwordBaseAuditImp = passwordBaseAuditImp;
				_usuarioBaseSIRImp = usuarioBaseSIRImp;
				_passwordBaseSIRImp = passwordBaseSIRImp;

				desactivarBoton('boton_comprobar');
				conexionFrame.src = 'comprobarConexionBBDDx2yFTPInstancia.do?<%=Defs.PARAMETRO_DIRECCION_BASE_DATOS_EXP%>='+direccionBaseExp+'&<%=Defs.PARAMETRO_PUERTO_BASE_DATOS_EXP%>='+puertoBaseExp+'&<%=Defs.PARAMETRO_USUARIO_BASE_DATOS_RP_EXP%>='+usuarioBaseRPExp+'&<%=Defs.PARAMETRO_PASSWORD_BASE_DATOS_RP_EXP%>='+passwordBaseRPExp+'&<%=Defs.PARAMETRO_INSTANCIA_EXP%>='+instanciaExp+'&<%=Defs.PARAMETRO_DIRECCION_BASE_DATOS_IMP%>='+direccionBaseImp+'&<%=Defs.PARAMETRO_PUERTO_BASE_DATOS_IMP%>='+puertoBaseImp+'&<%=Defs.PARAMETRO_USUARIO_BASE_DATOS_RP_IMP%>='+usuarioBaseRPImp+'&<%=Defs.PARAMETRO_PASSWORD_BASE_DATOS_RP_IMP%>='+passwordBaseRPImp+'&<%=Defs.PARAMETRO_INSTANCIA_IMP%>='+instanciaImp+'&<%=Defs.PARAMETRO_DIRECCION_FTP%>='+direccionFtp+'&<%=Defs.PARAMETRO_PUERTO_FTP%>='+puertoFtp+'&<%=Defs.PARAMETRO_RUTA_FTP%>='+rutaFtp+'&<%=Defs.PARAMETRO_USUARIO_FTP%>='+usuarioFtp+'&<%=Defs.PARAMETRO_PASSWORD_FTP%>='+password1Ftp+'&<%=Defs.PARAMETRO_PASSWORD_REPETIDO_FTP%>='+password2Ftp;
			}

			function desactivarMensajes() {
				desactivarMensaje('tabla_mensajes_campos_obligatorios');
				desactivarMensaje('campos_obligatorios');
				desactivarMensaje('tabla_mensajes_password_incorrecto_basedatos_exp');
				desactivarMensaje('password_incorrecto_basedatos_exp');
				desactivarMensaje('tabla_mensajes_password_incorrecto_basedatos_imp');
				desactivarMensaje('password_incorrecto_basedatos_imp');
				desactivarMensaje('tabla_mensajes_password_incorrecto_ftp');
				desactivarMensaje('password_incorrecto_ftp');
				desactivarMensaje('tabla_mensajes_no_conexion_basedatos_exp');
				desactivarMensaje('no_conexion_basedatos_exp');
				desactivarMensaje('tabla_mensajes_no_conexion_basedatos_imp');
				desactivarMensaje('no_conexion_basedatos_imp');
				desactivarMensaje('tabla_mensajes_no_conexion_ad_exp');
				desactivarMensaje('no_conexion_ad_exp');
				desactivarMensaje('tabla_mensajes_no_conexion_ge_exp');
				desactivarMensaje('no_conexion_ge_exp');
				desactivarMensaje('tabla_mensajes_no_conexion_rp_exp');
				desactivarMensaje('no_conexion_rp_exp');
				desactivarMensaje('tabla_mensajes_no_conexion_te_exp');
				desactivarMensaje('no_conexion_te_exp');
				desactivarMensaje('tabla_mensajes_no_conexion_ad_imp');
				desactivarMensaje('no_conexion_ad_imp');
				desactivarMensaje('tabla_mensajes_no_conexion_ge_imp');
				desactivarMensaje('no_conexion_ge_imp');
				desactivarMensaje('tabla_mensajes_no_conexion_rp_imp');
				desactivarMensaje('no_conexion_rp_imp');
				desactivarMensaje('tabla_mensajes_no_conexion_te_imp');
				desactivarMensaje('no_conexion_te_imp');
				desactivarMensaje('tabla_mensajes_no_conexion_ftp');
				desactivarMensaje('no_conexion_ftp');
				desactivarMensaje('tabla_mensajes_datos_modificados');
				desactivarMensaje('datos_modificados');
				desactivarMensaje('tabla_mensajes_si_conexion_basedatos_exp');
				desactivarMensaje('si_conexion_basedatos_exp');
				desactivarMensaje('tabla_mensajes_si_conexion_basedatos_imp');
				desactivarMensaje('si_conexion_basedatos_imp');
				desactivarMensaje('tabla_mensajes_si_conexion_ftp_nobbdd');
				desactivarMensaje('si_conexion_ftp_nobbdd');
				desactivarMensaje('tabla_mensajes_si_conexion_ftp_sibbdd');
				desactivarMensaje('si_conexion_ftp_sibbdd');
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
						var direccionBaseExp = document.getElementById('<%=Defs.PARAMETRO_DIRECCION_BASE_DATOS_EXP%>').value;
						if (direccionBaseExp != _direccionBaseExp) {
							_direccionBaseExp = direccionBaseExp;
							revalidar = true;
						}
					} else if (tipo == '2') {
						var puertoBaseExp = document.getElementById('<%=Defs.PARAMETRO_PUERTO_BASE_DATOS_EXP%>').value;
						if (puertoBaseExp != _puertoBaseExp) {
							_puertoBaseExp = puertoBaseExp;
							revalidar = true;
						}
					} else if (tipo == '3') {
						var usuarioBaseExp = document.getElementById('<%=Defs.PARAMETRO_USUARIO_BASE_DATOS_EXP%>').value;
						if (usuarioBaseExp != _usuarioBaseExp) {
							_usuarioBaseExp = usuarioBaseExp;
							revalidar = true;
						}
					} else if (tipo == '4') {
						var passwordBaseExp = document.getElementById('<%=Defs.PARAMETRO_PASSWORD_BASE_DATOS_EXP%>').value;
						if (passwordBaseExp != _passwordBaseExp) {
							_passwordBaseExp = passwordBaseExp;
							revalidar = true;
						}
					} else if (tipo == '5') {
						var passwordRepetidoBaseExp = document.getElementById('<%=Defs.PARAMETRO_PASSWORD_REPETIDO_BASE_DATOS_EXP%>').value;
						if (passwordRepetidoBaseExp != _passwordRepetidoBaseExp) {
							_passwordRepetidoBaseExp = passwordRepetidoBaseExp;
							revalidar = true;
						}
					} else if (tipo == '6') {
						var direccionBaseImp = document.getElementById('<%=Defs.PARAMETRO_DIRECCION_BASE_DATOS_IMP%>').value;
						if (direccionBaseImp != _direccionBaseImp) {
							_direccionBaseImp = direccionBaseImp;
							revalidar = true;
						}
					} else if (tipo == '7') {
						var puertoBaseImp = document.getElementById('<%=Defs.PARAMETRO_PUERTO_BASE_DATOS_IMP%>').value;
						if (puertoBaseImp != _puertoBaseImp) {
							_puertoBaseImp = puertoBaseImp;
							revalidar = true;
						}
					} else if (tipo == '8') {
						var usuarioBaseImp = document.getElementById('<%=Defs.PARAMETRO_USUARIO_BASE_DATOS_IMP%>').value;
						if (usuarioBaseImp != _usuarioBaseImp) {
							_usuarioBaseImp = usuarioBaseImp;
							revalidar = true;
						}
					} else if (tipo == '9') {
						var passwordBaseImp = document.getElementById('<%=Defs.PARAMETRO_PASSWORD_BASE_DATOS_IMP%>').value;
						if (passwordBaseImp != _passwordBaseImp) {
							_passwordBaseImp = passwordBaseImp;
							revalidar = true;
						}
					} else if (tipo == '10') {
						var passwordRepetidoBaseImp = document.getElementById('<%=Defs.PARAMETRO_PASSWORD_REPETIDO_BASE_DATOS_IMP%>').value;
						if (passwordRepetidoBaseImp != _passwordRepetidoBaseImp) {
							_passwordRepetidoBaseImp = passwordRepetidoBaseImp;
							revalidar = true;
						}
					} else if (tipo == '11') {
						var direccionFtp = document.getElementById('<%=Defs.PARAMETRO_DIRECCION_FTP%>').value;
						if (direccionFtp != _direccionFtp) {
							_direccionFtp = direccionFtp;
							revalidar = true;
						}
					} else if (tipo == '12') {
						var puertoFtp = document.getElementById('<%=Defs.PARAMETRO_PUERTO_FTP%>').value;
						if (puertoFtp != _puertoFtp) {
							_puertoFtp = puertoFtp;
							revalidar = true;
						}
					} else if (tipo == '13') {
						var rutaFtp = document.getElementById('<%=Defs.PARAMETRO_RUTA_FTP%>').value;
						if (rutaFtp != _rutaFtp) {
							_rutaFtp = rutaFtp;
							revalidar = true;
						}
					} else if (tipo == '14') {
						var usuarioFtp = document.getElementById('<%=Defs.PARAMETRO_USUARIO_FTP%>').value;
						if (usuarioFtp != _usuarioFtp) {
							_usuarioFtp = usuarioFtp;
							revalidar = true;
						}
					} else if (tipo == '15') {
						var passwordFtp = document.getElementById('<%=Defs.PARAMETRO_PASSWORD_FTP%>').value;
						if (passwordFtp != _passwordFtp) {
							_passwordFtp = passwordFtp;
							revalidar = true;
						}
					} else if (tipo == '16') {
						var passwordRepetidoFtp = document.getElementById('<%=Defs.PARAMETRO_PASSWORD_REPETIDO_FTP%>').value;
						if (passwordRepetidoFtp != _passwordRepetidoFtp) {
							_passwordRepetidoFtp = passwordRepetidoFtp;
							revalidar = true;
						}
					} else if (tipo == '17') {
						var instanciaExp = document.getElementById('<%=Defs.PARAMETRO_INSTANCIA_EXP%>').value;
						if (instanciaExp != _instanciaExp) {
							_instanciaExp = instanciaExp;
							revalidar = true;
						}
					} else if (tipo == '22') {
						var usuarioBaseRPExp = document.getElementById('<%=Defs.PARAMETRO_USUARIO_BASE_DATOS_RP_EXP%>').value;
						if (usuarioBaseRPExp != _usuarioBaseRPExp) {
							_usuarioBaseRPExp = usuarioBaseRPExp;
							revalidar = true;
						}
					} else if (tipo == '23') {
						var passwordBaseRPExp = document.getElementById('<%=Defs.PARAMETRO_PASSWORD_BASE_DATOS_RP_EXP%>').value;
						if (passwordBaseRPExp != _passwordBaseRPExp) {
							_passwordBaseRPExp = passwordBaseRPExp;
							revalidar = true;
						}
					} else if (tipo == '26') {
						var instanciaImp = document.getElementById('<%=Defs.PARAMETRO_INSTANCIA_IMP%>').value;
						if (instanciaImp != _instanciaImp) {
							_instanciaImp = instanciaImp;
							revalidar = true;
						}
					} else if (tipo == '31') {
						var usuarioBaseRPImp = document.getElementById('<%=Defs.PARAMETRO_USUARIO_BASE_DATOS_RP_IMP%>').value;
						if (usuarioBaseRPImp != _usuarioBaseRImp) {
							_usuarioBaseRPImp = usuarioBaseRPImp;
							revalidar = true;
						}
					} else if (tipo == '32') {
						var passwordBaseRPImp = document.getElementById('<%=Defs.PARAMETRO_PASSWORD_BASE_DATOS_RP_IMP%>').value;
						if (passwordBaseRPImp != _passwordBaseRPImp) {
							_passwordBaseRPImp = passwordBaseRPImp;
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
					document.getElementById('tre_exp').style.visibility = 'hidden';
					document.getElementById('tre_exp').style.display = 'none';
					document.getElementById('tre_imp').style.visibility = 'hidden';
					document.getElementById('tre_imp').style.display = 'none';
					activarCampoSeparados(0);
				} else {
					activarCampoSeparados(1);
					activarCampoEsquema('3',0);
					document.getElementById('tre_exp').style.visibility = 'visible';
					document.getElementById('tre_exp').style.display = 'block';
					document.getElementById('tre_imp').style.visibility = 'visible';
					document.getElementById('tre_imp').style.display = 'block';
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
				document.getElementById('tru' + valor + '_exp').style.visibility = array[ocultar];
				document.getElementById('tru' + valor + '_exp').style.display = array[ocultar+2];
				document.getElementById('trp' + valor + '_exp').style.visibility = array[ocultar];
				document.getElementById('trp' + valor + '_exp').style.display = array[ocultar+2];
				document.getElementById('tru' + valor + '_imp').style.visibility = array[ocultar];
				document.getElementById('tru' + valor + '_imp').style.display = array[ocultar+2];
				document.getElementById('trp' + valor + '_imp').style.visibility = array[ocultar];
				document.getElementById('trp' + valor + '_imp').style.display = array[ocultar+2];

				document.getElementById('tteu' + valor + '_imp').style.visibility = array[ocultar];
				document.getElementById('tteu' + valor + '_imp').style.display = array[ocultar+2];
				document.getElementById('ttep' + valor + '_imp').style.visibility = array[ocultar];
				document.getElementById('ttep' + valor + '_imp').style.display = array[ocultar+2];

				document.getElementById('tgeu' + valor + '_imp').style.visibility = array[ocultar];
				document.getElementById('tgeu' + valor + '_imp').style.display = array[ocultar+2];
				document.getElementById('tgep' + valor + '_imp').style.visibility = array[ocultar];
				document.getElementById('tgep' + valor + '_imp').style.display = array[ocultar+2];

				document.getElementById('tadu' + valor + '_imp').style.visibility = array[ocultar];
				document.getElementById('tadu' + valor + '_imp').style.display = array[ocultar+2];
				document.getElementById('tadp' + valor + '_imp').style.visibility = array[ocultar];
				document.getElementById('tadp' + valor + '_imp').style.display = array[ocultar+2];
			}

			function activarCampoSeparados(ocultar) {
				var array = new Array('visible', 'hidden', 'block', 'none');
				document.getElementById('tru_exp').style.visibility = array[ocultar];
				document.getElementById('tru_exp').style.display = array[ocultar+2];
				document.getElementById('trp_exp').style.visibility = array[ocultar];
				document.getElementById('trp_exp').style.display = array[ocultar+2];
				document.getElementById('trpr_exp').style.visibility = array[ocultar];
				document.getElementById('trpr_exp').style.display = array[ocultar+2];
				document.getElementById('tru_imp').style.visibility = array[ocultar];
				document.getElementById('tru_imp').style.display = array[ocultar+2];
				document.getElementById('trp_imp').style.visibility = array[ocultar];
				document.getElementById('trp_imp').style.display = array[ocultar+2];
				document.getElementById('trpr_imp').style.visibility = array[ocultar];
				document.getElementById('trpr_imp').style.display = array[ocultar+2];
			}

			function desplegarCampos(tipoSel) {
				var tipo;
				if (tipoSel == 'Exp') {
					tipo = document.getElementById('<%=Defs.PARAMETRO_TIPO_BASE_DATOS_EXP%>').value;
					document.getElementById('<%=Defs.PARAMETRO_TIPO_BASE_DATOS_IMP%>').value = tipo;
				} else if (tipoSel == 'Imp') {
					tipo = document.getElementById('<%=Defs.PARAMETRO_TIPO_BASE_DATOS_IMP%>').value;
					document.getElementById('<%=Defs.PARAMETRO_TIPO_BASE_DATOS_EXP%>').value = tipo;
				}
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
			<jsp:param name="ayuda" value='<%=request.getContextPath() + "/jsp/ayudas/" + locale + "/procesos/clonar.html"%>' />
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
								<h1><bean:message key="entidades.importar"/></h1>
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
								<html:form styleId="baseDatosx2AndFtpBean" action="/clonarEntidad.do" method="post">
									<html:hidden styleId="<%=Defs.PARAMETRO_RUTA_FTP%>" name="<%=Defs.PARAMETRO_RUTA_FTP%>" property="<%=Defs.PARAMETRO_RUTA_FTP%>" value="/" />
									<html:hidden styleId="<%=Defs.PARAMETRO_LIMPIAR%>" name="<%=Defs.PARAMETRO_LIMPIAR%>" property="<%=Defs.PARAMETRO_LIMPIAR%>" value="false" />
									<html:hidden styleId="<%=Defs.PARAMETRO_ID_ENTIDAD_EXP%>" property="<%=Defs.PARAMETRO_ID_ENTIDAD_EXP%>"/>
									<html:hidden styleId="<%=Defs.PARAMETRO_ID_ENTIDAD_IMP%>" property="<%=Defs.PARAMETRO_ID_ENTIDAD_IMP%>"/>
									<html:hidden styleId="<%=Defs.PARAMETRO_USUARIO_BASE_DATOS_AD_EXP%>" property="<%=Defs.PARAMETRO_USUARIO_BASE_DATOS_AD_EXP%>"/>
									<html:hidden styleId="<%=Defs.PARAMETRO_PASSWORD_BASE_DATOS_AD_EXP%>" property="<%=Defs.PARAMETRO_PASSWORD_BASE_DATOS_AD_EXP%>"/>
									<html:hidden styleId="<%=Defs.PARAMETRO_USUARIO_BASE_DATOS_GE_EXP%>" property="<%=Defs.PARAMETRO_USUARIO_BASE_DATOS_GE_EXP%>"/>
									<html:hidden styleId="<%=Defs.PARAMETRO_PASSWORD_BASE_DATOS_GE_EXP%>" property="<%=Defs.PARAMETRO_PASSWORD_BASE_DATOS_GE_EXP%>"/>
									<html:hidden styleId="<%=Defs.PARAMETRO_USUARIO_BASE_DATOS_TE_EXP%>" property="<%=Defs.PARAMETRO_USUARIO_BASE_DATOS_TE_EXP%>"/>
									<html:hidden styleId="<%=Defs.PARAMETRO_PASSWORD_BASE_DATOS_TE_EXP%>" property="<%=Defs.PARAMETRO_PASSWORD_BASE_DATOS_TE_EXP%>"/>
									<html:hidden styleId="<%=Defs.PARAMETRO_USUARIO_BASE_DATOS_AUDIT_EXP%>" property="<%=Defs.PARAMETRO_USUARIO_BASE_DATOS_AUDIT_EXP%>"/>
									<html:hidden styleId="<%=Defs.PARAMETRO_PASSWORD_BASE_DATOS_AUDIT_EXP%>" property="<%=Defs.PARAMETRO_PASSWORD_BASE_DATOS_AUDIT_EXP%>"/>
									<html:hidden styleId="<%=Defs.PARAMETRO_USUARIO_BASE_DATOS_SIR_EXP%>" property="<%=Defs.PARAMETRO_USUARIO_BASE_DATOS_SIR_EXP%>"/>
									<html:hidden styleId="<%=Defs.PARAMETRO_PASSWORD_BASE_DATOS_SIR_EXP%>" property="<%=Defs.PARAMETRO_PASSWORD_BASE_DATOS_SIR_EXP%>"/>
									<html:hidden styleId="tipoBaseExp" property="tipoBaseExp"/>
									<html:hidden styleId="tipoBaseImp" property="tipoBaseImp"/>
									<table width="70%" align="center" class="gr_borde_tabla">
										<tr align="center">
											<td width="100%">
												<label class="gr" style="width: 100%"><b><bean:message key="mensaje.informativo.proceso.clonar" /></b></label>
											</td>
										</tr>
									</table>
									<table border=0 cellpadding=0 cellspacing=0 align=center style="padding-left: 12px; padding-bottom:22px;">
										<tr>
											<td colspan=2>
												<table id="tabla_mensajes_campos_obligatorios" border=0 cellpadding=0 cellspacing=0 style="padding-bottom:22px; position: absolute; visibility: hidden;">
													<tr>
														<td colspan=2>
									                		<label id="campos_obligatorios" class="gr_error" style="visibility: hidden; position:absolute"><bean:message key="mensaje.error.entidad.campos_obligatorios"/></label>
									                	</td>
													</tr>
												</table>
												<table id="tabla_mensajes_password_incorrecto_basedatos_exp" border=0 cellpadding=0 cellspacing=0 style="padding-bottom:22px; position: absolute; visibility: hidden;">
													<tr>
														<td colspan=2>
									                		<label id="password_incorrecto_basedatos_exp" class="gr_error" style="visibility: hidden; position:absolute"><bean:message key="mensaje.error.entidad.password_incorrecto_basedatos_exp"/></label>
									                	</td>
													</tr>
												</table>
												<table id="tabla_mensajes_password_incorrecto_basedatos_imp" border=0 cellpadding=0 cellspacing=0 style="padding-bottom:22px; position: absolute; visibility: hidden;">
													<tr>
														<td colspan=2>
									                		<label id="password_incorrecto_basedatos_imp" class="gr_error" style="visibility: hidden; position:absolute"><bean:message key="mensaje.error.entidad.password_incorrecto_basedatos_imp"/></label>
									                	</td>
													</tr>
												</table>
												<table id="tabla_mensajes_password_incorrecto_ftp" border=0 cellpadding=0 cellspacing=0 style="padding-bottom:22px; position: absolute; visibility: hidden;">
													<tr>
														<td colspan=2>
									                		<label id="password_incorrecto_ftp" class="gr_error" style="visibility: hidden; position:absolute"><bean:message key="mensaje.error.entidad.password_incorrecto_ftp"/></label>
									                	</td>
													</tr>
												</table>
												<table id="tabla_mensajes_no_conexion_basedatos_exp" border=0 cellpadding=0 cellspacing=0 style="padding-bottom:22px; position: absolute; visibility: hidden;">
													<tr>
														<td colspan=2>
									                		<label id="no_conexion_basedatos_exp" class="gr_error" style="visibility: hidden; position:absolute"><bean:message key="mensaje.error.entidad.no_conexion_basedatos_exp"/></label>
									                	</td>
													</tr>
												</table>
												<table id="tabla_mensajes_no_conexion_basedatos_imp" border=0 cellpadding=0 cellspacing=0 style="padding-bottom:22px; position: absolute; visibility: hidden;">
													<tr>
														<td colspan=2>
									                		<label id="no_conexion_basedatos_imp" class="gr_error" style="visibility: hidden; position:absolute"><bean:message key="mensaje.error.entidad.no_conexion_basedatos_imp"/></label>
									                	</td>
													</tr>
												</table>
												<table id="tabla_mensajes_no_conexion_ftp" border=0 cellpadding=0 cellspacing=0 style="padding-bottom:22px; position: absolute; visibility: hidden;">
													<tr>
														<td colspan=2>
									                		<label id="no_conexion_ftp" class="gr_error" style="visibility: hidden; position:absolute"><bean:message key="mensaje.error.entidad.no_conexion_ftp"/></label>
									                	</td>
													</tr>
												</table>
												<table id="tabla_mensajes_datos_modificados" border=0 cellpadding=0 cellspacing=0 style="padding-bottom:22px; position: absolute; visibility: hidden;">
													<tr>
														<td colspan=2>
									                		<label id="datos_modificados" class="gr_error" style="visibility: hidden; position:absolute"><bean:message key="mensaje.error.entidad.datos_modificados_bbdd_ftp"/></label>
									                	</td>
													</tr>
												</table>
												<table id="tabla_mensajes_si_conexion_basedatos_exp" border=0 cellpadding=0 cellspacing=0 style="padding-bottom:22px; position: absolute; visibility: hidden;">
													<tr>
														<td colspan=2>
									                		<label id="si_conexion_basedatos_exp" class="gr_informativo" style="visibility: hidden; position:absolute"><bean:message key="mensaje.informativo.entidad.si_conexion_basedatos_exp"/></label>
									                	</td>
													</tr>
												</table>
												<table id="tabla_mensajes_no_conexion_ad_exp" border=0 cellpadding=0 cellspacing=0 style="position: absolute; visibility: hidden;">
													<tr>
														<td colspan=2>
									                		<label id="no_conexion_ad_exp" class="gr_error" style="visibility: hidden; position:absolute"><bean:message key="mensaje.error.entidad.no_conexion_ad_exp"/></label>
									                	</td>
													</tr>
													<tr><td colspan=2><br/></td></tr>
												</table>
												<table id="tabla_mensajes_no_conexion_ge_exp" border=0 cellpadding=0 cellspacing=0 style="position: absolute; visibility: hidden;">
													<tr>
														<td colspan=2>
									                		<label id="no_conexion_ge_exp" class="gr_error" style="visibility: hidden; position:absolute"><bean:message key="mensaje.error.entidad.no_conexion_ge_exp"/></label>
									                	</td>
													</tr>
													<tr><td colspan=2><br/></td></tr>
												</table>
												<table id="tabla_mensajes_no_conexion_rp_exp" border=0 cellpadding=0 cellspacing=0 style="position: absolute; visibility: hidden;">
													<tr>
														<td colspan=2>
									                		<label id="no_conexion_rp_exp" class="gr_error" style="visibility: hidden; position:absolute"><bean:message key="mensaje.error.entidad.no_conexion_rp_exp"/></label>
									                	</td>
													</tr>
													<tr><td colspan=2><br/></td></tr>
												</table>
												<table id="tabla_mensajes_no_conexion_te_exp" border=0 cellpadding=0 cellspacing=0 style="position: absolute; visibility: hidden;">
													<tr>
														<td colspan=2>
									                		<label id="no_conexion_te_exp" class="gr_error" style="visibility: hidden; position:absolute"><bean:message key="mensaje.error.entidad.no_conexion_te_exp"/></label>
									                	</td>
													</tr>
													<tr><td colspan=2><br/></td></tr>
												</table>
												<table id="tabla_mensajes_si_conexion_basedatos_imp" border=0 cellpadding=0 cellspacing=0 style="padding-bottom:22px; position: absolute; visibility: hidden;">
													<tr>
														<td colspan=2>
									                		<label id="si_conexion_basedatos_imp" class="gr_informativo" style="visibility: hidden; position:absolute"><bean:message key="mensaje.informativo.entidad.si_conexion_basedatos_imp"/></label>
									                	</td>
													</tr>
												</table>
												<table id="tabla_mensajes_no_conexion_ad_imp" border=0 cellpadding=0 cellspacing=0 style="position: absolute; visibility: hidden;">
													<tr>
														<td colspan=2>
									                		<label id="no_conexion_ad_imp" class="gr_error" style="visibility: hidden; position:absolute"><bean:message key="mensaje.error.entidad.no_conexion_ad_imp"/></label>
									                	</td>
													</tr>
													<tr><td colspan=2><br/></td></tr>
												</table>
												<table id="tabla_mensajes_no_conexion_ge_imp" border=0 cellpadding=0 cellspacing=0 style="position: absolute; visibility: hidden;">
													<tr>
														<td colspan=2>
									                		<label id="no_conexion_ge_imp" class="gr_error" style="visibility: hidden; position:absolute"><bean:message key="mensaje.error.entidad.no_conexion_ge_imp"/></label>
									                	</td>
													</tr>
													<tr><td colspan=2><br/></td></tr>
												</table>
												<table id="tabla_mensajes_no_conexion_rp_imp" border=0 cellpadding=0 cellspacing=0 style="position: absolute; visibility: hidden;">
													<tr>
														<td colspan=2>
									                		<label id="no_conexion_rp_imp" class="gr_error" style="visibility: hidden; position:absolute"><bean:message key="mensaje.error.entidad.no_conexion_rp_imp"/></label>
									                	</td>
													</tr>
													<tr><td colspan=2><br/></td></tr>
												</table>
												<table id="tabla_mensajes_no_conexion_te_imp" border=0 cellpadding=0 cellspacing=0 style="position: absolute; visibility: hidden;">
													<tr>
														<td colspan=2>
									                		<label id="no_conexion_te_imp" class="gr_error" style="visibility: hidden; position:absolute"><bean:message key="mensaje.error.entidad.no_conexion_te_imp"/></label>
									                	</td>
													</tr>
													<tr><td colspan=2><br/></td></tr>
												</table>
												<table id="tabla_mensajes_si_conexion_ftp_nobbdd" border=0 cellpadding=0 cellspacing=0 style="padding-bottom:22px; position: absolute; visibility: hidden;">
													<tr>
														<td colspan=2>
									                		<label id="si_conexion_ftp_nobbdd" class="gr_informativo" style="visibility: hidden; position:absolute"><bean:message key="mensaje.informativo.entidad.si_conexion_ftp_nobbdd"/></label>
									                	</td>
													</tr>
												</table>
												<table id="tabla_mensajes_si_conexion_ftp_sibbdd" border=0 cellpadding=0 cellspacing=0 style="padding-bottom:22px; position: absolute; visibility: hidden;">
													<tr>
														<td colspan=2>
									                		<label id="si_conexion_ftp_sibbdd" class="gr_informativo" style="visibility: hidden; position:absolute"><bean:message key="mensaje.informativo.entidad.si_conexion_ftp_sibbdd"/></label>
									                	</td>
													</tr>
												</table>
											</td>
										</tr>
										<tr valign=top>
											<td colspan=3>
												<table>
													<tr>
														<td>
															<label for='<%="_"+Defs.PARAMETRO_ID_ENTIDAD_EXP%>' class="gr_ext"><bean:message key="entidad.importar.idEntidadOrigen" /></label>
														</td>
														<td>
									                		<html:text property="<%=Defs.PARAMETRO_ID_ENTIDAD_EXP%>" styleId='<%="_"+Defs.PARAMETRO_ID_ENTIDAD_EXP%>' maxlength="3" disabled="true"></html:text>
									                	</td>
														<td width="20px"></td>
														<td>
															<label for='<%="_"+Defs.PARAMETRO_ID_ENTIDAD_IMP%>' class="gr_ext"><bean:message key="entidad.importar.idEntidadDestino" /></label>
														</td>
														<td>
									                		<html:text property="<%=Defs.PARAMETRO_ID_ENTIDAD_IMP%>" styleId='<%="_"+Defs.PARAMETRO_ID_ENTIDAD_IMP%>' maxlength="3" disabled="true"></html:text>
									                	</td>
													</tr>
												</table>
											</td>
										</tr>
										<tr><td colspan=3><br/></td></tr>
										<tr>
											<td class="td-azul" style="border-right: 4px solid #fff">
												<bean:message key="entidad.clonar.configuracion_basedatos_origen" />
											</td>
											<td class="td-azul" style="border-right: 2px solid #fff">
												<bean:message key="entidad.clonar.configuracion_basedatos_destino" />
											</td>
											<td class="td-azul" style="border-left: 2px solid #fff">
												<bean:message key="entidad.clonar.configuracion_ftp_destino" />
											</td>
										</tr>
										<tr valign=top>
											<td>
												<table>
													<tr>
														<td>
															<label for="<%=Defs.PARAMETRO_DIRECCION_BASE_DATOS_EXP%>" class="gr_ext"><bean:message key="entidad.importar.direccion_basedatos" /></label>
														</td>
														<td>
									                		<html:text property="<%=Defs.PARAMETRO_DIRECCION_BASE_DATOS_EXP%>" styleId="<%=Defs.PARAMETRO_DIRECCION_BASE_DATOS_EXP%>" tabindex="1" onchange="javascript:comprobarCambio('1');"></html:text>
									                	</td>
													</tr>
													<tr>
														<td>
															<label for="<%=Defs.PARAMETRO_PUERTO_BASE_DATOS_EXP%>" class="gr_ext"><bean:message key="entidad.importar.puerto_basedatos" /></label>
														</td>
														<td>
									                		<html:text property="<%=Defs.PARAMETRO_PUERTO_BASE_DATOS_EXP%>" styleId="<%=Defs.PARAMETRO_PUERTO_BASE_DATOS_EXP%>" tabindex="2" onchange="javascript:comprobarCambio('2');"></html:text>
									                	</td>
													</tr>
													<tr>
														<td>
															<label for="<%=Defs.PARAMETRO_TIPO_BASE_DATOS_EXP%>" class="gr_ext"><bean:message key="entidad.exportar.tipoBaseDatos" /></label>
														</td>
														<td>
									                		<select id="<%=Defs.PARAMETRO_TIPO_BASE_DATOS_EXP%>" name="<%=Defs.PARAMETRO_TIPO_BASE_DATOS_EXP%>" tabindex="3" onchange="javascript:desplegarCampos('Exp');" class="gr" style="color: black">
									                			<%for (int ind=0; ind<tipos.length; ind++){%>
									                				<option value="<%=tipos[ind]%>"><%=tipos[ind]%></option>
									                			<%}%>
									                		</select>
									                	</td>
													</tr>
													<tr id="tru_exp">
														<td>
															<label for="<%=Defs.PARAMETRO_USUARIO_BASE_DATOS_EXP%>" class="gr_ext"><bean:message key="entidad.importar.usuario_basedatos" /></label>
														</td>
														<td>
									                		<html:text property="<%=Defs.PARAMETRO_USUARIO_BASE_DATOS_EXP%>" styleId="<%=Defs.PARAMETRO_USUARIO_BASE_DATOS_EXP%>" tabindex="4" onchange="javascript:comprobarCambio('3');"></html:text>
									                	</td>
													</tr>
													<tr id="trp_exp">
														<td>
															<label for="<%=Defs.PARAMETRO_PASSWORD_BASE_DATOS_EXP%>" class="gr_ext"><bean:message key="entidad.importar.password_basedatos" /></label>
														</td>
														<td>
									                		<html:password property="<%=Defs.PARAMETRO_PASSWORD_BASE_DATOS_EXP%>" styleId="<%=Defs.PARAMETRO_PASSWORD_BASE_DATOS_EXP%>" tabindex="5"  onchange="javascript:comprobarCambio('4');"></html:password>
									                	</td>
													</tr>
													<tr id="trpr_exp">
														<td>
															<label for="<%=Defs.PARAMETRO_PASSWORD_REPETIDO_BASE_DATOS_EXP%>" class="gr_ext"><bean:message key="entidad.importar.password_repetido_basedatos" /></label>
														</td>
														<td>
									                		<html:password property="<%=Defs.PARAMETRO_PASSWORD_REPETIDO_BASE_DATOS_EXP%>" styleId="<%=Defs.PARAMETRO_PASSWORD_REPETIDO_BASE_DATOS_EXP%>" tabindex="6" onchange="javascript:comprobarCambio('5');"></html:password>
									                	</td>
													</tr>

													<tr id="tre_exp" style="visibility: hidden; display: none;">
														<td>
															<label for="<%=Defs.PARAMETRO_INSTANCIA_EXP%>" class="gr_ext"><bean:message key="entidad.exportar.instanciaDesc" /></label>
														</td>
														<td>
									                		<html:text property="<%=Defs.PARAMETRO_INSTANCIA_EXP%>" styleId="<%=Defs.PARAMETRO_INSTANCIA_EXP%>" tabindex="7" onchange="javascript:comprobarCambio('17');"></html:text>
									                	</td>
													</tr>
													<tr id="tru3_exp" style="visibility: hidden; display: none;">
														<td>
															<label for="<%=Defs.PARAMETRO_USUARIO_BASE_DATOS_RP_EXP%>" class="gr_ext"><bean:message key="entidad.exportar.usuarioRP" /></label>
														</td>
														<td>
									                		<html:text property="<%=Defs.PARAMETRO_USUARIO_BASE_DATOS_RP_EXP%>" styleId="<%=Defs.PARAMETRO_USUARIO_BASE_DATOS_RP_EXP%>" tabindex="12" onchange="javascript:comprobarCambio('22');"></html:text>
									                	</td>
													</tr>
													<tr id="trp3_exp" style="visibility: hidden; display: none;">
														<td>
															<label for="<%=Defs.PARAMETRO_PASSWORD_BASE_DATOS_RP_EXP%>" class="gr_ext"><bean:message key="entidad.exportar.passwordRP" /></label>
														</td>
														<td>
									                		<html:password property="<%=Defs.PARAMETRO_PASSWORD_BASE_DATOS_RP_EXP%>" styleId="<%=Defs.PARAMETRO_PASSWORD_BASE_DATOS_RP_EXP%>" tabindex="13"  onchange="javascript:comprobarCambio('23');"></html:password>
									                	</td>
													</tr>
												</table>
											</td>
											<td>
												<table>
													<tr>
														<td>
															<label for="<%=Defs.PARAMETRO_DIRECCION_BASE_DATOS_IMP%>" class="gr_ext"><bean:message key="entidad.importar.direccion_basedatos" /></label>
														</td>
														<td>
									                		<html:text property="<%=Defs.PARAMETRO_DIRECCION_BASE_DATOS_IMP%>" styleId="<%=Defs.PARAMETRO_DIRECCION_BASE_DATOS_IMP%>" tabindex="16" onchange="javascript:comprobarCambio('6');"></html:text>
									                	</td>
													</tr>
													<tr>
														<td>
															<label for="<%=Defs.PARAMETRO_PUERTO_BASE_DATOS_IMP%>" class="gr_ext"><bean:message key="entidad.importar.puerto_basedatos" /></label>
														</td>
														<td>
									                		<html:text property="<%=Defs.PARAMETRO_PUERTO_BASE_DATOS_IMP%>" styleId="<%=Defs.PARAMETRO_PUERTO_BASE_DATOS_IMP%>" tabindex="17" onchange="javascript:comprobarCambio('7');"></html:text>
									                	</td>
													</tr>
													<tr>
														<td>
															<label for="<%=Defs.PARAMETRO_TIPO_BASE_DATOS_IMP%>" class="gr_ext"><bean:message key="entidad.exportar.tipoBaseDatos" /></label>
														</td>
														<td>
									                		<select id="<%=Defs.PARAMETRO_TIPO_BASE_DATOS_IMP%>" name="<%=Defs.PARAMETRO_TIPO_BASE_DATOS_IMP%>" tabindex="18" onchange="javascript:desplegarCampos('Imp');" class="gr" style="color: black">
									                			<%for (int ind=0; ind<tipos.length; ind++){%>
									                				<option value="<%=tipos[ind]%>"><%=tipos[ind]%></option>
									                			<%}%>
									                		</select>
									                	</td>
													</tr>
													<tr id="tru_imp">
														<td>
															<label for="<%=Defs.PARAMETRO_USUARIO_BASE_DATOS_IMP%>" class="gr_ext"><bean:message key="entidad.importar.usuario_basedatos" /></label>
														</td>
														<td>
									                		<html:text property="<%=Defs.PARAMETRO_USUARIO_BASE_DATOS_IMP%>" styleId="<%=Defs.PARAMETRO_USUARIO_BASE_DATOS_IMP%>" tabindex="19" onchange="javascript:comprobarCambio('8');"></html:text>
									                	</td>
													</tr>
													<tr id="trp_imp">
														<td>
															<label for="<%=Defs.PARAMETRO_PASSWORD_BASE_DATOS_IMP%>" class="gr_ext"><bean:message key="entidad.importar.password_basedatos" /></label>
														</td>
														<td>
									                		<html:password property="<%=Defs.PARAMETRO_PASSWORD_BASE_DATOS_IMP%>" styleId="<%=Defs.PARAMETRO_PASSWORD_BASE_DATOS_IMP%>" tabindex="20"  onchange="javascript:comprobarCambio('9');"></html:password>
									                	</td>
													</tr>
													<tr id="trpr_imp">
														<td>
															<label for="<%=Defs.PARAMETRO_PASSWORD_REPETIDO_BASE_DATOS_IMP%>" class="gr_ext"><bean:message key="entidad.importar.password_repetido_basedatos" /></label>
														</td>
														<td>
									                		<html:password property="<%=Defs.PARAMETRO_PASSWORD_REPETIDO_BASE_DATOS_IMP%>" styleId="<%=Defs.PARAMETRO_PASSWORD_REPETIDO_BASE_DATOS_IMP%>" tabindex="21" onchange="javascript:comprobarCambio('10');"></html:password>
									                	</td>
													</tr>
													<tr id="tre_imp" style="visibility: hidden; display: none;">
														<td>
															<label for="<%=Defs.PARAMETRO_INSTANCIA_IMP%>" class="gr_ext"><bean:message key="entidad.exportar.instanciaDesc" /></label>
														</td>
														<td>
									                		<html:text property="<%=Defs.PARAMETRO_INSTANCIA_IMP%>" styleId="<%=Defs.PARAMETRO_INSTANCIA_IMP%>" tabindex="22" onchange="javascript:comprobarCambio('26');"></html:text>
									                	</td>
													</tr>
													<tr id="tru3_imp" style="visibility: hidden; display: none;">
														<td>
															<label for="<%=Defs.PARAMETRO_USUARIO_BASE_DATOS_RP_IMP%>" class="gr_ext"><bean:message key="entidad.importar.usuarioRP" /></label>
														</td>
														<td>
									                		<html:text property="<%=Defs.PARAMETRO_USUARIO_BASE_DATOS_RP_IMP%>" styleId="<%=Defs.PARAMETRO_USUARIO_BASE_DATOS_RP_IMP%>" tabindex="23" onchange="javascript:comprobarCambio('31');"></html:text>
									                	</td>
													</tr>
													<tr id="trp3_imp" style="visibility: hidden; display: none;">
														<td>
															<label for="<%=Defs.PARAMETRO_PASSWORD_BASE_DATOS_RP_IMP%>" class="gr_ext"><bean:message key="entidad.importar.passwordRP" /></label>
														</td>
														<td>
									                		<html:password property="<%=Defs.PARAMETRO_PASSWORD_BASE_DATOS_RP_IMP%>" styleId="<%=Defs.PARAMETRO_PASSWORD_BASE_DATOS_RP_IMP%>" tabindex="24"  onchange="javascript:comprobarCambio('32');"></html:password>
									                	</td>
													</tr>
													<tr id="tteu3_imp" style="visibility: hidden; display: none;">
														<td>
															<label for="<%=Defs.PARAMETRO_USUARIO_BASE_DATOS_TE_IMP%>" class="gr_ext"><bean:message key="entidad.importar.usuarioTE" /></label>
														</td>
														<td>
									                		<html:text property="<%=Defs.PARAMETRO_USUARIO_BASE_DATOS_TE_IMP%>" styleId="<%=Defs.PARAMETRO_USUARIO_BASE_DATOS_TE_IMP%>" tabindex="25" onchange="javascript:comprobarCambio('33');"></html:text>
									                	</td>
													</tr>
													<tr id="ttep3_imp" style="visibility: hidden; display: none;">
														<td>
															<label for="<%=Defs.PARAMETRO_PASSWORD_BASE_DATOS_TE_IMP%>" class="gr_ext"><bean:message key="entidad.importar.passwordTE" /></label>
														</td>
														<td>
									                		<html:password property="<%=Defs.PARAMETRO_PASSWORD_BASE_DATOS_TE_IMP%>" styleId="<%=Defs.PARAMETRO_PASSWORD_BASE_DATOS_TE_IMP%>" tabindex="26"  onchange="javascript:comprobarCambio('34');"></html:password>
									                	</td>
													</tr>
													<tr id="tgeu3_imp" style="visibility: hidden; display: none;">
														<td>
															<label for="<%=Defs.PARAMETRO_USUARIO_BASE_DATOS_GE_IMP%>" class="gr_ext"><bean:message key="entidad.importar.usuarioGE" /></label>
														</td>
														<td>
									                		<html:text property="<%=Defs.PARAMETRO_USUARIO_BASE_DATOS_GE_IMP%>" styleId="<%=Defs.PARAMETRO_USUARIO_BASE_DATOS_GE_IMP%>" tabindex="27" onchange="javascript:comprobarCambio('35');"></html:text>
									                	</td>
													</tr>
													<tr id="tgep3_imp" style="visibility: hidden; display: none;">
														<td>
															<label for="<%=Defs.PARAMETRO_PASSWORD_BASE_DATOS_GE_IMP%>" class="gr_ext"><bean:message key="entidad.importar.passwordGE" /></label>
														</td>
														<td>
									                		<html:password property="<%=Defs.PARAMETRO_PASSWORD_BASE_DATOS_GE_IMP%>" styleId="<%=Defs.PARAMETRO_PASSWORD_BASE_DATOS_GE_IMP%>" tabindex="28"  onchange="javascript:comprobarCambio('36');"></html:password>
									                	</td>
													</tr>
													<tr id="tadu3_imp" style="visibility: hidden; display: none;">
														<td>
															<label for="<%=Defs.PARAMETRO_USUARIO_BASE_DATOS_AD_IMP%>" class="gr_ext"><bean:message key="entidad.importar.usuarioAD" /></label>
														</td>
														<td>
									                		<html:text property="<%=Defs.PARAMETRO_USUARIO_BASE_DATOS_AD_IMP%>" styleId="<%=Defs.PARAMETRO_USUARIO_BASE_DATOS_AD_IMP%>" tabindex="29" onchange="javascript:comprobarCambio('37');"></html:text>
									                	</td>
													</tr>
													<tr id="tadp3_imp" style="visibility: hidden; display: none;">
														<td>
															<label for="<%=Defs.PARAMETRO_PASSWORD_BASE_DATOS_AD_IMP%>" class="gr_ext"><bean:message key="entidad.importar.passwordAD" /></label>
														</td>
														<td>
									                		<html:password property="<%=Defs.PARAMETRO_PASSWORD_BASE_DATOS_AD_IMP%>" styleId="<%=Defs.PARAMETRO_PASSWORD_BASE_DATOS_AD_IMP%>" tabindex="30"  onchange="javascript:comprobarCambio('38');"></html:password>
									                	</td>
													</tr>
													<tr id="tauditu3_imp" style="visibility: hidden; display: none;">
														<td>
															<label for="<%=Defs.PARAMETRO_USUARIO_BASE_DATOS_AUDIT_IMP%>" class="gr_ext"><bean:message key="entidad.importar.usuarioAudit" /></label>
														</td>
														<td>
									                		<html:text property="<%=Defs.PARAMETRO_USUARIO_BASE_DATOS_AUDIT_IMP%>" styleId="<%=Defs.PARAMETRO_USUARIO_BASE_DATOS_AUDIT_IMP%>" tabindex="31" onchange="javascript:comprobarCambio('39');"></html:text>
									                	</td>
													</tr>
													<tr id="tauditp3_imp" style="visibility: hidden; display: none;">
														<td>
															<label for="<%=Defs.PARAMETRO_PASSWORD_BASE_DATOS_AUDIT_IMP%>" class="gr_ext"><bean:message key="entidad.importar.passwordAudit" /></label>
														</td>
														<td>
									                		<html:password property="<%=Defs.PARAMETRO_PASSWORD_BASE_DATOS_AUDIT_IMP%>" styleId="<%=Defs.PARAMETRO_PASSWORD_BASE_DATOS_AUDIT_IMP%>" tabindex="32"  onchange="javascript:comprobarCambio('40');"></html:password>
									                	</td>
													</tr>
													<tr id="tsiru3_imp" style="visibility: hidden; display: none;">
														<td>
															<label for="<%=Defs.PARAMETRO_USUARIO_BASE_DATOS_SIR_IMP%>" class="gr_ext"><bean:message key="entidad.importar.usuarioSIR" /></label>
														</td>
														<td>
									                		<html:text property="<%=Defs.PARAMETRO_USUARIO_BASE_DATOS_SIR_IMP%>" styleId="<%=Defs.PARAMETRO_USUARIO_BASE_DATOS_SIR_IMP%>" tabindex="33" onchange="javascript:comprobarCambio('41');"></html:text>
									                	</td>
													</tr>
													<tr id="tsirp3_imp" style="visibility: hidden; display: none;">
														<td>
															<label for="<%=Defs.PARAMETRO_PASSWORD_BASE_DATOS_SIR_IMP%>" class="gr_ext"><bean:message key="entidad.importar.passwordSIR" /></label>
														</td>
														<td>
									                		<html:password property="<%=Defs.PARAMETRO_PASSWORD_BASE_DATOS_SIR_IMP%>" styleId="<%=Defs.PARAMETRO_PASSWORD_BASE_DATOS_SIR_IMP%>" tabindex="34"  onchange="javascript:comprobarCambio('42');"></html:password>
									                	</td>
													</tr>

												</table>
											</td>
											<td>
												<table>
													<tr>
														<td>
															<label for="<%=Defs.PARAMETRO_DIRECCION_FTP%>" class="gr_ext"><bean:message key="entidad.importar.direccion_ftp" /></label>
														</td>
														<td>
									                		<html:text property="<%=Defs.PARAMETRO_DIRECCION_FTP%>" styleId="<%=Defs.PARAMETRO_DIRECCION_FTP%>" tabindex="35" onchange="javascript:comprobarCambio('11');"></html:text>
									                	</td>
													</tr>
													<tr>
														<td>
															<label for="<%=Defs.PARAMETRO_PUERTO_FTP%>" class="gr_ext"><bean:message key="entidad.importar.puerto_ftp" /></label>
														</td>
														<td>
									                		<html:text property="<%=Defs.PARAMETRO_PUERTO_FTP%>" styleId="<%=Defs.PARAMETRO_PUERTO_FTP%>" tabindex="36" onchange="javascript:comprobarCambio('12');"></html:text>
									                	</td>
													</tr>
													<tr>
														<td>
															<label for="<%=Defs.PARAMETRO_USUARIO_FTP%>" class="gr_ext"><bean:message key="entidad.importar.usuario_ftp" /></label>
														</td>
														<td>
									                		<html:text property="<%=Defs.PARAMETRO_USUARIO_FTP%>" styleId="<%=Defs.PARAMETRO_USUARIO_FTP%>" tabindex="37" onchange="javascript:comprobarCambio('14');"></html:text>
									                	</td>
													</tr>
													<tr>
														<td>
															<label for="<%=Defs.PARAMETRO_PASSWORD_FTP%>" class="gr_ext"><bean:message key="entidad.importar.password_ftp" /></label>
														</td>
														<td>
									                		<html:password property="<%=Defs.PARAMETRO_PASSWORD_FTP%>" styleId="<%=Defs.PARAMETRO_PASSWORD_FTP%>" tabindex="38"  onchange="javascript:comprobarCambio('15');"></html:password>
									                	</td>
													</tr>
													<tr>
														<td>
															<label for="<%=Defs.PARAMETRO_PASSWORD_REPETIDO_FTP%>" class="gr_ext"><bean:message key="entidad.importar.password_repetido_ftp" /></label>
														</td>
														<td>
									                		<html:password property="<%=Defs.PARAMETRO_PASSWORD_REPETIDO_FTP%>" styleId="<%=Defs.PARAMETRO_PASSWORD_REPETIDO_FTP%>" tabindex="39" onchange="javascript:comprobarCambio('16');"></html:password>
									                	</td>
													</tr>
												</table>
											</td>
										</tr>
										<tr><td colspan=3><br/></td></tr>
										<tr align=center>
											<td colspan=3>
												<input id="boton_comprobar" onclick="javascript:comprobarConexionBBDD()" type=button value="<bean:message key="entidades.boton.comprobar"/>" class="ok" tabindex="40" title="<bean:message key="entidades.boton.comprobar"/>"/>
												<input id="boton_aceptar" onclick="javascript:enviar()" type=button value="<bean:message key="boton.aceptar"/>" class="ok_desactivado" tabindex="41" disabled='true' title="<bean:message key="boton.aceptar"/>"/>
												<input onclick="javascript:cancelar()" type=button value="<bean:message key="boton.cancelar"/>" class="ok" tabindex="42" title="<bean:message key="boton.cancelar"/>"/>
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