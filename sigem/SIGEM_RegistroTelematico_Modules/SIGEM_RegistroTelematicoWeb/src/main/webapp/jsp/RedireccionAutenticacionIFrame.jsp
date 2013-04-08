<%@ page language="java"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ page import="ieci.tecdoc.sgm.registro.utils.Defs" %>

<%
String rutaEstilos = (String)session.getAttribute("PARAMETRO_RUTA_ESTILOS");
if (rutaEstilos == null) rutaEstilos = "";
String rutaImagenes = (String)session.getAttribute("PARAMETRO_RUTA_IMAGENES");
if (rutaImagenes == null) rutaImagenes = "";
%>

<html:html locale="true">
	<head>
		<%
			String path = request.getContextPath();
			String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
		%> 
			<base href="<%= basePath %>" />
			<link rel="stylesheet" href="css/<%=rutaEstilos%>estilos.css" type="text/css" />
		<%
			String tramiteId = (String)session.getAttribute(Defs.TRAMITE_ID);
			if (tramiteId == null || tramiteId.equals("null"))
				tramiteId = new String("");
			String url_parcial = (String)request.getSession().getServletContext().getAttribute("redirAutenticacion");
			String dir = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + "/" + url_parcial + "&" + Defs.TRAMITE_ID + "=" + tramiteId;
		%>
		
		<script type="text/javascript" language="javascript">
		
			function redireccionAutenticacion() {
			
				parent.location = '<%=dir%>';
			}
		
		</script>
		
	</head>

	<body onload="javascript:redireccionAutenticacion();">
	</body>

</html:html>