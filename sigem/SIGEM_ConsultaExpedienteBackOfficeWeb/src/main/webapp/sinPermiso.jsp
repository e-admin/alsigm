<%@ page language="java"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>

<%
String rutaEstilos = (String)session.getAttribute("PARAMETRO_RUTA_ESTILOS");
if (rutaEstilos == null) rutaEstilos = "";
String rutaImagenes = (String)session.getAttribute("PARAMETRO_RUTA_IMAGENES");
if (rutaImagenes == null) rutaImagenes = "";
%>

<html:html>
	<head>
		<%
		String path = request.getContextPath();
		String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
		%>
		<base href="<%= basePath %>" />
		<link rel="stylesheet" href="css/<%=rutaEstilos%>estilos.css" type="text/css" />

		<!--[if lte IE 5]>
			<link rel="stylesheet" type="text/css" href="css/estilos_ie5.css"/>
		<![endif]-->

		<!--[if IE 6]>
			<link rel="stylesheet" type="text/css" href="css/estilos_ie6.css"/>
		<![endif]-->

		<!--[if IE 7]>
			<link rel="stylesheet" type="text/css" href="css/estilos_ie7.css"/>
		<![endif]-->

	</head>

	<body>

	<div id="contenedora">

		<jsp:include flush="true" page="Cabecera.jsp"></jsp:include>

		<div class="centered">
			<div class="contenedor_centrado">

				<div class="cuerpo">
		      		<div class="cuerporight">
		        		<div class="cuerpomid">

		          			<h1><bean:message key="error"/></h1>

							<div class="contenido_cuerpo clearfix">

								<div class="seccion">

				            		<label class="error_rojo">
				            			<bean:message key="error.sin.permisos"/>
				            		</label>

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

	</div>

	</body>
</html:html>