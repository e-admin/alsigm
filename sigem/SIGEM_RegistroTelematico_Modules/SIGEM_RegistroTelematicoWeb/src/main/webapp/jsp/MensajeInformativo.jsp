<%@ page language="java"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
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
		<script type="text/javascript" language="javascript" src="js/idioma.js"></script>
	</head>
	<body>

	<div id="contenedora">
		<jsp:include flush="true" page="Cabecera.jsp"></jsp:include>
		<div class="centered">
		<div class="contenedor_centrado_info">

			<div class="cuerpo">
		      	<div class="cuerporight">
		        	<div class="cuerpomid">

	          		<h1><bean:message key="mensaje.informativo"/></h1>
				<div class="contenido_cuerpo clearfix">
					<div class="seccion"> 

		            		<%
		            			String mensaje = (String)request.getAttribute(Defs.MENSAJE_INFORMATIVO);
		            			if(mensaje != null && !mensaje.equals("")){
		            		%>
		            			<label class="error"><bean:message key="<%= mensaje %>"/></label>
		            		<%
		            			}
		            		%>
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
	</body>
</html:html>