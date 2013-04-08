<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>

<%@page import="ieci.tecdoc.sgm.admsistema.util.Defs"%>

<%
Boolean b = (Boolean)request.getAttribute(Defs.PARAMETRO_VALIDACION_CONEXION_EXP);
boolean validacionExp = false;
if (b != null)
	validacionExp = b.booleanValue();

b = (Boolean)request.getAttribute(Defs.PARAMETRO_VALIDACION_CONEXION_IMP);
boolean validacionImp = false;
if (b != null)
	validacionImp = b.booleanValue();

b = (Boolean)request.getAttribute(Defs.PARAMETRO_VALIDACION_CONEXION_FTP);
boolean validacionFtp = false;
if (b != null)
	validacionFtp = b.booleanValue();
%>

<html>
	<head>
		<LINK href="css/estilos.css" type=text/css rel=stylesheet />
		
		<SCRIPT language=javascript>
		parent.desactivarMensajes();
		var si_bbdd_exp = false; 
		var si_bbdd_imp = false; 
		
		<%if (validacionExp) {%>
				if ((parent.getPass1Exp() == '') || (parent.getPass2Exp() == '')) {
					parent.activarMensaje('campos_obligatorios');	
					parent.activarBoton('boton_comprobar');
				} else if (parent.getPass1Exp() != parent.getPass2Exp()) {
					parent.activarMensaje('password_incorrecto_basedatos_exp');	
					parent.activarBoton('boton_comprobar');
				} else {
					parent.activarMensaje('si_conexion_basedatos_exp');
					si_bbdd_exp = true;
				}
		<%}%>
		<%if (validacionImp) {%>
				if ((parent.getPass1Imp() == '') || (parent.getPass2Imp() == '')) {
					parent.activarMensaje('campos_obligatorios');	
					parent.activarBoton('boton_comprobar');
				} else if (parent.getPass1Imp() != parent.getPass2Imp()) {
					parent.activarMensaje('password_incorrecto_basedatos_imp');	
					parent.activarBoton('boton_comprobar');
				} else {
					parent.activarMensaje('si_conexion_basedatos_imp');
					si_bbdd_imp = true;
				}
		<%}%>
		<%if (validacionFtp) {%>
				if ((parent.getPass1Ftp() == '') || (parent.getPass2Ftp() == '')) {
					parent.activarMensaje('campos_obligatorios');	
					parent.activarBoton('boton_comprobar');
				} else if (parent.getPass1Ftp() != parent.getPass2Ftp()) {
					parent.activarMensaje('password_incorrecto_ftp');	
					parent.activarBoton('boton_comprobar');
				} else {
					if (si_bbdd_exp == true && si_bbdd_imp == true) {
						parent.activarMensaje('si_conexion_ftp_sibbdd');
						parent.activarBoton('boton_aceptar');
					} else {
						parent.activarBoton('boton_comprobar');
						parent.activarMensaje('si_conexion_ftp_nobbdd');
					}
				}
		<%} %>
		<%if (!validacionExp || !validacionImp || !validacionFtp) {%>
				parent.activarBoton('boton_comprobar');
				if ( (parent.getDireccionExp() == '') || (parent.getPuertoExp() == '') || (parent.getUsuarioExp() == '') || 
						(parent.getPass1Exp() == '') || (parent.getPass2Exp() == '') ){
					parent.activarMensaje('campos_obligatorios');	
				} 
				else if ( (parent.getDireccionImp() == '') || (parent.getPuertoImp() == '') || (parent.getUsuarioImp() == '') || 
						(parent.getPass1Imp() == '') || (parent.getPass2Imp() == '') ){
					parent.activarMensaje('campos_obligatorios');	
				} 
				else if ( (parent.getDireccionFtp() == '') || (parent.getPuertoFtp() == '') || (parent.getRutaFtp() == '') || 
						(parent.getUsuarioFtp() == '') || (parent.getPass1Ftp() == '') || (parent.getPass2Ftp() == '') ){
					parent.activarMensaje('campos_obligatorios');	
				}
				else if (parent.getPass1Exp() != parent.getPass2Exp()) {
					parent.activarMensaje('password_incorrecto_basedatos_exp');	
				}
				else if (parent.getPass1Imp() != parent.getPass2Imp()) {
					parent.activarMensaje('password_incorrecto_basedatos_imp');	
				}
				else if (parent.getPass1Ftp() != parent.getPass2Ftp()) {
					parent.activarMensaje('password_incorrecto_ftp');	
				}
				else {
					var val1 = <%=validacionExp%>;
					var val2 = <%=validacionImp%>;
					var val3 = <%=validacionFtp%>;
					if (val1 == false)
						parent.activarMensaje('no_conexion_basedatos_exp');
					if (val2 == false)
						parent.activarMensaje('no_conexion_basedatos_imp');
					if (val3 == false)
						parent.activarMensaje('no_conexion_ftp');	
				}
		<% } %>
		</SCRIPT>
	</head>
	<body>

	</body>
</html>