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
%>

<html>
	<head>
		<LINK href="css/estilos.css" type=text/css rel=stylesheet />
		
		<SCRIPT language=javascript>
		<%if (validacionRP) {%>
				parent.desactivarMensajes();
				if (parent.getExisteFichero() == true) {
					if (parent.getFichero() == '') {
						parent.setFichero();
						parent.activarMensaje('campos_obligatorios');	
						parent.activarBoton('boton_comprobar');
					} else {
						parent.setFichero();
						parent.activarMensaje('si_conexion');
						parent.activarBoton('boton_aceptar');
					}
				} else {
					parent.activarMensaje('si_conexion');
					parent.activarBoton('boton_aceptar');
				}
		<%} else {%>
				parent.activarBoton('boton_comprobar');
				parent.desactivarMensajes();	
				if ( (parent.getDireccion() == '') || (parent.getPuerto() == '') || 
					(parent.getUsuarioRP() == '') || (parent.getPassRP() == '') || 
				 	(parent.getInstancia() == '') ){
					parent.activarMensaje('campos_obligatorios');	
				}
				else {
					<%if(!validacionRP) out.write("parent.activarMensaje('no_conexion_rp');");%>
				}
		<% } %>
		</SCRIPT>
	</head>
	<body>

	</body>
</html>