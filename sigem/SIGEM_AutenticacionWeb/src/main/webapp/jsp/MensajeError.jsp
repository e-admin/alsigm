<%@ page language="java"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ page import="ieci.tecdoc.sgm.autenticacion.utils.Defs" %>

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
		
	</head>
	<body>

	<div id="contenedora">
		<jsp:include flush="true" page="Cabecera.jsp"></jsp:include>
		<div class="centered">
		<div class="contenedor_centrado_ancho">
			<div class="cuerpo">
		      	<div class="cuerporight">
		        	<div class="cuerpomid">

		          		<h1><bean:message key="mensaje.error"/></h1>

					<div class="contenido_cuerpo"> 

			            		<p class="fila">
			            		<label class="error">
			            		<%
			            			String mensaje = (String)request.getAttribute(Defs.MENSAJE_ERROR);
			            			if(mensaje != null && !mensaje.equals("")){
			            		%>
			            				<bean:message key="<%= mensaje %>"/>
			            		<%
			            			}
			            		%>
			            		</label>
			            		</p>
						<logic:present name="<%= Defs.MENSAJE_ERROR_DETALLE %>">
						<p class="fila">
				            		<label class="error">
								<%= request.getAttribute(Defs.MENSAJE_ERROR_DETALLE) %>
				            		</label>
				            	</p>
			            		</logic:present>
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