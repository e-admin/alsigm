<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<%
String rutaEstilos = (String)session.getAttribute("PARAMETRO_RUTA_ESTILOS");
if (rutaEstilos == null) rutaEstilos = "";
String rutaImagenes = (String)session.getAttribute("PARAMETRO_RUTA_IMAGENES");
if (rutaImagenes == null) rutaImagenes = "";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="es" xml:lang="es">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
		<meta name="author" content="IECISA" />
		<title><bean:message key="texto.web"/></title>
		<link href="css/<%=rutaEstilos%>estilos.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" language="javascript" src="js/idioma.js"></script>
	</head>
	<body>
		<div id="contenedora">
			<jsp:include flush="true" page="cabecera.jsp"></jsp:include>
		  	<!-- Inicio Contenido -->
		  	<table align="center">
		  	<tr>
		  	<td>
			  	<div id="contenedora">
					<div class="cuerpo" style="width:830px">
		   				<div class="cuerporight">
		     				<div class="cuerpomid">
		     					<h1><bean:message key="mostrar.titulo"/></h1>
		     					<br/>
								<div class="cuadro" style="height: 120px;" align="center">
									<form id="BusquedaForm" action='<html:rewrite page="/obtenerCertificacion.do"/>' method="post">
										<input type="hidden" name="idUsuario" id="idUsuario" value=""/>
										<input type="hidden" name="idFichero" id="idFichero" value=""/>
										<table>
											<tr>
												<td>
													<iframe id="documento" name="documento" src="jsp/contenidoCertificacion.jsp"
														frameborder='0' scrolling="no" height='100px' width='400px' title='<bean:message key="mostrar.titulo"/>'>
													</iframe>
												</td>
											</tr>
											<tr>
												<td>
													<input type="submit" class="ok" value='<bean:message key="boton.volver"/>' id="volver" name="volver" />
												</td>
											</tr>
										</table>
									</form>
		    	       			</div>
		        	   		</div>
		      			</div>
			    	</div>
					<div class="cuerpobt" style="width:830px">
		    	  		<div class="cuerporightbt">
		        			<div class="cuerpomidbt"></div>
		      			</div>
					</div>
		  		</div>
		  	</td>
		  	</tr>
		  	</table>
		</div>
	</body>
</html>
