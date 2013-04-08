<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<%
String idEntidad = (String)request.getParameter(Defs.PARAMETRO_ID_ENTIDAD);
if (Utilidades.esVacio(idEntidad)) idEntidad = "";
String tipoVia = (String)request.getParameter(Defs.PARAMETRO_TIPO_VIA);
if (Utilidades.esVacio(tipoVia)) tipoVia = "";
String nombreVia = (String)request.getParameter(Defs.PARAMETRO_NOMBRE_VIA);
if (Utilidades.esVacio(nombreVia)) nombreVia = "";
String numeroPortal = (String)request.getParameter(Defs.PARAMETRO_NUMERO_PORTAL);
if (Utilidades.esVacio(numeroPortal)) numeroPortal = "";
String accion = "";
if (!Utilidades.esVacio(nombreVia))
	accion = Defs.ACCION_BUSQUEDA;
%>

<%@page import="ieci.tecdoc.sgm.geolocalizacion.utils.Defs"%>
<%@page import="ieci.tecdoc.sgm.geolocalizacion.utils.Utilidades"%>
<html>
	<head>
	</head>
	<body onload="document.forms[0].submit();">
		<form action="validarDireccionPostal.do" method="post">
			<input type="hidden" id="<%=Defs.PARAMETRO_ID_ENTIDAD%>" name="<%=Defs.PARAMETRO_ID_ENTIDAD%>" value="<%=idEntidad%>" />
			<input type="hidden" id="<%=Defs.PARAMETRO_TIPO_VIA%>" name="<%=Defs.PARAMETRO_TIPO_VIA%>" value="<%=tipoVia%>" />
			<input type="hidden" id="<%=Defs.PARAMETRO_NOMBRE_VIA%>" name="<%=Defs.PARAMETRO_NOMBRE_VIA%>" value="<%=nombreVia%>" />
			<input type="hidden" id="<%=Defs.PARAMETRO_NUMERO_PORTAL%>" name="<%=Defs.PARAMETRO_NUMERO_PORTAL%>" value="<%=numeroPortal%>" />
			<input type="hidden" id="<%=Defs.PARAMETRO_ACCION%>" name="<%=Defs.PARAMETRO_ACCION%>" value="<%=accion%>" />
		</form>	
	</body>
</html>