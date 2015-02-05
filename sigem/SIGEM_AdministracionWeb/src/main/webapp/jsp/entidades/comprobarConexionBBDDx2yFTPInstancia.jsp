<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>

<%@page import="ieci.tecdoc.sgm.admsistema.util.Defs"%>

<%
Boolean b = (Boolean)request.getAttribute(Defs.PARAMETRO_VALIDACION_CONEXION_RP_EXP);
boolean validacionRPExp = false;
if (b != null)
	validacionRPExp = b.booleanValue();

b = (Boolean)request.getAttribute(Defs.PARAMETRO_VALIDACION_CONEXION_RP_IMP);
boolean validacionRPImp = false;
if (b != null)
	validacionRPImp = b.booleanValue();

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
		
		<%if (validacionRPExp) {%>
			parent.activarMensaje('si_conexion_basedatos_exp');
			si_bbdd_exp = true;
		<%}%>
		<%if (validacionRPImp) {%>
			parent.activarMensaje('si_conexion_basedatos_imp');
			si_bbdd_imp = true;
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
		<%if (!(validacionRPExp) || 
				!(validacionRPImp) || !validacionFtp) {%>
				parent.activarBoton('boton_comprobar');
				if ( (parent.getDireccionExp() == '') || (parent.getPuertoExp() == '') || (parent.getInstanciaExp() == '') ||
					(parent.getPassRPExp() == '') || (parent.getPassRPExp() == '') ){
					parent.activarMensaje('campos_obligatorios');	
				} 
				else if ( (parent.getDireccionImp() == '') || (parent.getPuertoImp() == '') || (parent.getInstanciaImp() == '') ||
					(parent.getPassRPImp() == '') || (parent.getPassRPImp() == '') ){
					parent.activarMensaje('campos_obligatorios');	
				} 
				else if ( (parent.getDireccionFtp() == '') || (parent.getPuertoFtp() == '') || (parent.getRutaFtp() == '') || 
						(parent.getUsuarioFtp() == '') || (parent.getPass1Ftp() == '') || (parent.getPass2Ftp() == '') ){
					parent.activarMensaje('campos_obligatorios');	
				}
				else if (parent.getPass1Ftp() != parent.getPass2Ftp()) {
					parent.activarMensaje('password_incorrecto_ftp');	
				}
				else {
					var val = <%=validacionFtp%>;
					<%if(!validacionRPExp) out.write("parent.activarMensaje('no_conexion_rp_exp');");%>
					<%if(!validacionRPImp) out.write("parent.activarMensaje('no_conexion_rp_imp');");%>
					if (val == false)
						parent.activarMensaje('no_conexion_ftp');	
				}
		<% } %>
		</SCRIPT>
	</head>
	<body>

	</body>
</html>