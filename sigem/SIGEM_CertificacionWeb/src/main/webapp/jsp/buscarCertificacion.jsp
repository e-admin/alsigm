<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<%@page import="ieci.tecdoc.sgm.certificacion.utilsweb.Defs"%>

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
	<body onload="document.getElementById('idFichero').focus();">
		<div id="contenedora">
			<jsp:include flush="true" page="cabecera.jsp"></jsp:include>
		  	<table align="center">
		  	<tr>
		  	<td>
			  	<div id="contenedora">
					<div class="cuerpo" style="width:830px">
		   				<div class="cuerporight">
		     				<div class="cuerpomid">
		     					<h1><bean:message key="texto.titulo"/></h1>
		     					<br/>
								<div class="cuadro" style="height: 120px;">
									<form id="BusquedaForm" action='<html:rewrite page="/obtenerCertificacion.do"/>' method="post">
										<table width="100%">
											<%
											String error = (String)request.getAttribute("error");
											if (error != null && !"".equals(error)) {
											%>
											<tr>
												<td colspan="2" style="text-align:center;">
													<label class="error"><bean:message key='<%=(String)request.getAttribute("error")%>'/></label>
												</td>
											</tr>
											<%}else{ %>
											<tr><td colspan="2">&nbsp;</td></tr>
											<%}%>
										</table>
										<table>
											<tr><td colspan="2">&nbsp;</td></tr>
											<tr>
								            	<td width="60%" style="text-align:right;">
								            		<bean:message key="buscar.idUsuario"/>
								            	</td>
								            	<td width="40%" style="text-align:left;">
								            		<html:text property="idUsuario" value="<%=(String)session.getAttribute(Defs.CNIF_USUARIO)%>" disabled="true"/>
								            	</td>
								           	</tr>
								           	<tr>
								            	<td width="60%" style="text-align:right;">
								            		<bean:message key="buscar.idFichero"/>
								            	</td>
								            	<td width="40%" style="text-align:left;">
								            		<html:text property="idFichero" value=""/>		
								            	</td>                    	
								           	</tr>
								           	<tr><td colspan="2">&nbsp;</td></tr>
								           	<tr>
								            	<td width="60%" style="text-align:right;">&nbsp;</td>
								            	<td width="40%" style="text-align:left;">
									            	<input type="submit" class="ok" value='<bean:message key="boton.buscar"/>' id="buscar" name="buscar" />
								            	</td>                    	
								           	</tr>
								           	<tr><td colspan="2">&nbsp;</td></tr>
								           	<tr><td colspan="2">&nbsp;</td></tr>
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
