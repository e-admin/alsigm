<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>

<%@ page import="ieci.tecdoc.sgm.catalogo_tramites.utils.Defs" %>

<html:html>

	<head>
		<%
			String path = request.getContextPath();
			String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
		%>
		<base href="<%= basePath %>">
		<link rel="stylesheet" href="css/estilos.css" type="text/css" />
		<script type="text/javascript" language="javascript" src="js/navegador.js"></script>
	</head>

	<body>

	<div id="contenedora">
	<div id="cabecera">
		<h1>&nbsp;</h1>
		<h3>&nbsp;</h3>
	</div>

		<div class="centered">
		<div class="contenedor_error">

			<label class="error" id="mensaje">
			<%
				String mensaje = (String)request.getParameter(Defs.MENSAJE_ERROR);
				if(mensaje != null && !mensaje.equals("")){
			%>
				<bean:message key="<%= mensaje %>"/>
			<% } %>
			</label>

			<br/><br/>

			<label class="error_rojo" id="detalle">
				<%
					String detalle = (String)request.getParameter(Defs.MENSAJE_ERROR_DETALLE);
					if(detalle != null && !detalle.equals("")){
				%>
					<%= request.getParameter(Defs.MENSAJE_ERROR_DETALLE) %>
				<% } %>
       		</label>

		</div>
		</div>
	</div>

	<script language="Javascript">
	if(isIE){
		var array = window.dialogArguments;
   		document.getElementById('mensaje').innerHTML = array[0].toString();
   		document.getElementById('detalle').innerHTML = array[1].toString();
	}
	</script>

    </body>
</html:html>