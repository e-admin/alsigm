<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>

<%@page import="ieci.tecdoc.sgm.admsistema.util.Defs"%>

<%
Boolean b = (Boolean)request.getAttribute(Defs.PARAMETRO_VALIDACION_CONEXION_RP);
boolean validacionRP = false;
if (b != null)
	validacionRP = b.booleanValue();

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
		var si_bbdd = false; 
		
		<%if (validacionRP) {%>
				if ((parent.getPassRP() == '') || (parent.getPassRP() == '') ||
					(parent.getInstancia() == '') ) {
					parent.activarMensaje('campos_obligatorios');	
					parent.activarBoton('boton_comprobar');
				} else {
					parent.activarMensaje('si_conexion_basedatos');
					si_bbdd = true;
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
					if (si_bbdd == true) {
						var imps = parent.getImportacion();
						if (parent.getImportacion() == '') {
							parent.activarMensaje('si_conexion_ftp_nobbdd');
							parent.activarBoton('boton_comprobar');
							parent.activarMensaje('seleccion_importacion');
						} else {
							parent.activarMensaje('si_conexion_ftp_sibbdd');
							parent.activarBoton('boton_aceptar');
						}
					} else {
						parent.activarBoton('boton_comprobar');
						parent.activarMensaje('si_conexion_ftp_nobbdd');
					}
				}
		<%} %>
		<%if (!(validacionRP) || !validacionFtp) {%>
				parent.activarBoton('boton_comprobar');
				if ( (parent.getDireccion() == '') || (parent.getPuerto() == '') || 
					(parent.getPassRP() == '') || (parent.getPassRP() == '') || 
					(parent.getInstancia() == '') ){
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
					var val2 = <%=validacionFtp%>;
					<%if(!validacionRP) out.write("parent.activarMensaje('no_conexion_rp');");%>
					if (val2 == false)
						parent.activarMensaje('no_conexion_ftp');	
				}
		<% } %>
		</SCRIPT>
	</head>
	<body>

	</body>
</html>