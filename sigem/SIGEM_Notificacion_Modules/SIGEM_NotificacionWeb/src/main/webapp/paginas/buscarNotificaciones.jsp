<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@taglib prefix="bean" uri="/WEB-INF/struts-bean.tld" %>

<%
String _rutaEstilos = (String)session.getAttribute("PARAMETRO_RUTA_ESTILOS");
if (_rutaEstilos == null) _rutaEstilos = "";
String _rutaImagenes = (String)session.getAttribute("PARAMETRO_RUTA_IMAGENES");
if (_rutaImagenes == null) _rutaImagenes = "";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="es">
	<head>
    	<link rel="stylesheet" href="css/<%=_rutaEstilos%>estilos.css" type="text/css" />
	   	<link rel="stylesheet" type="text/css" media="all" href="js/skins/aqua/theme.css" title="Aqua" />
		<script type="text/javascript" src="js/calendar.js"></script>
		<script type="text/javascript" src="js/lang/calendar-es.js"></script>
		<script type="text/javascript" src="js/calendario.js"></script>
		<script type="text/javascript" language="javascript" src="js/idioma.js"></script>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title><bean:message key="ieci.tecdoc.sgm.nt.buscarNotificaciones.titulo"/></title>
    </head>
    
    <body>
   		<div id="contenedora">
   			<jsp:include flush="true" page="Cabecera.jsp"></jsp:include>
	     	<div class="cuerpo">
	        	<div class="cuerporight">
	          		<div class="cuerpomid">
	    				<h1><bean:message key="ieci.tecdoc.sgm.nt.buscarNotificaciones.titulo"/></h1>
	    				<div class="submenu3">
	            			<ul>
	              				<li class="submen1off">
	              					<img src="img/<%=_rutaImagenes%>subme3_off.gif" alt=""/>
	              					<a href="verNotificaciones.do">
	              						<bean:message key="ieci.tecdoc.sgm.nt.verNotificaciones.titulo"/>
	              					</a>
	              				</li>
	              				<li class="submen1on"><img src="img/<%=_rutaImagenes%>subme3_of_on.gif" alt=""/>
	              					<bean:message key="ieci.tecdoc.sgm.nt.buscarNotificaciones.titulo"/>
	              					<img src="img/<%=_rutaImagenes%>subme3_on_0.gif" alt=""/>
	              				</li>
	            			</ul>
	          			</div>
	 					<div class="cuadro">
							<form action="./verNotificaciones.do" name="form2">   
	    						<table width="100%">
	        						<tr>
	            						<td>
	            							<bean:message key="ieci.tecdoc.sgm.nt.buscarNotificaciones.comboEstado.label"/>
	            						</td>
	           							<td>
	           								<select name="estado" class="gr">
								                <option value="-3"><bean:message key="ieci.tecdoc.sgm.nt.buscarNotificaciones.comboEstado.cualquiera"/></option>
								                <option value="-2"><bean:message key="ieci.tecdoc.sgm.nt.buscarNotificaciones.comboEstado.conErrores"/></option>
								                <option value="-1"><bean:message key="ieci.tecdoc.sgm.nt.buscarNotificaciones.comboEstado.fallo"/></option>
								                <option value="0"><bean:message key="ieci.tecdoc.sgm.nt.buscarNotificaciones.comboEstado.creada"/></option>
								                <option value="1"><bean:message key="ieci.tecdoc.sgm.nt.buscarNotificaciones.comboEstado.enviada"/></option>
								                <option value="2"><bean:message key="ieci.tecdoc.sgm.nt.buscarNotificaciones.comboEstado.disposicion"/></option>
								                <option value="3"><bean:message key="ieci.tecdoc.sgm.nt.buscarNotificaciones.comboEstado.leida"/></option>
								                <option value="4"><bean:message key="ieci.tecdoc.sgm.nt.buscarNotificaciones.comboEstado.expirada"/></option>
								                <option value="5"><bean:message key="ieci.tecdoc.sgm.nt.buscarNotificaciones.comboEstado.rechazada"/></option>
								                <option value="6"><bean:message key="ieci.tecdoc.sgm.nt.buscarNotificaciones.comboEstado.finalizada"/></option>
								                <option value="7"><bean:message key="ieci.tecdoc.sgm.nt.buscarNotificaciones.comboEstado.noSuscrito"/></option>               
	            							</select>
	            						</td>
	            						<td>
	            							<bean:message key="ieci.tecdoc.sgm.nt.buscarNotificaciones.codigo"/>
	            						</td>
	            						<td>
	            							<input type="text" name="codNoti"/>
	            						</td>
	        						</tr>
	        						<tr>
	            						<td>
	            							<bean:message key="ieci.tecdoc.sgm.nt.buscarNotificaciones.desde"/>
	            						</td>
										<td>
											<div style="position:relative;top:7px">
	                    						<input type="text" name="desde" readonly id="fechaDesde" class="pequenio" maxlength="10" onclick="return showCalendar('fechaDesde', '%d-%m-%Y');"><p name="desde" id="f" class="pequenio" />
	                    					</div>
										</td>
	            						<td>
	            							<bean:message key="ieci.tecdoc.sgm.nt.buscarNotificaciones.hasta"/>
	            						</td>
	            						<td>
	            							<div style="position:relative;top:7px">
	            								<input type="text" name="hasta" readonly id="fechaHasta" class="pequenio" maxlength="10" onclick="return showCalendar('fechaHasta', '%d-%m-%Y');"><p name="hasta" id="f" class="pequenio" />
	            							</div>
										</td>
									</tr>
	         						<tr>
	            						<td>
	            							<bean:message key="ieci.tecdoc.sgm.nt.buscarNotificaciones.tipoNotificacion"/>
	            						</td>
	            						<td colspan="2">
	            							<input type="text" name="tipo" value="" />
	            						</td>  
	            						<td>
	            							<div align="center">
												<input type="submit" value="<bean:message key="ieci.tecdoc.sgm.nt.buscarNotificaciones.buscar"/>" id="busqueda_expedientes" class="ok" onclick="" />
				  							</div>
				  						</td>
	        						</tr>
	    						</table>
	    					</form>   
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
	</body>
</html>
