<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@taglib prefix="bean" uri="/WEB-INF/struts-bean.tld" %>
<jsp:useBean id="detalle" type="ieci.tecdoc.sgm.core.services.notificaciones.Notificacion" scope="request" />

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
    	<script type="text/javascript" language="javascript" src="js/idioma.js"></script>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title><bean:message key="ieci.tecdoc.sgm.nt.notificacion.titulo"/></title>
    </head>

	<body>
		<div id="contenedora">
   			<jsp:include flush="true" page="Cabecera.jsp"></jsp:include>
    		<div class="cuerpo">
				<div class="cuerporight">
	        		<div class="cuerpomid">
						<h1><bean:message key="ieci.tecdoc.sgm.nt.notificacion.label"/>: <bean:write name="detalle" property="codigoNoti" scope="request"/></h1>
						<div class="submenu3">
							<ul>
								<li class="submen1off">
									<img src="img/<%=_rutaImagenes%>subme3_off.gif" alt="" />
									<a href="./verNotificaciones.do">
										<bean:message key="ieci.tecdoc.sgm.nt.verNotificaciones.titulo"/>
									</a>
								</li>
								<li class="submen1off">
									<img src="img/<%=_rutaImagenes%>subme3_of_of.gif" alt="" />
									<a href="./BusquedaNotificaciones.do">
										<bean:message key="ieci.tecdoc.sgm.nt.buscarNotificaciones.titulo"/>
									</a>
								</li>
								<li class="submen1on">
									<img src="img/<%=_rutaImagenes%>subme3_of_on.gif" alt=""/>
									<bean:message key="ieci.tecdoc.sgm.nt.notificacion.titulo"/>
									<img src="img/<%=_rutaImagenes%>subme3_on_0.gif" alt=""/>
								</li>
							</ul>
						</div>
						<div class="cuadro" align="center">
							<br/>
							<div class="submenu" style="width: 90%; text-align: left">
								<h1><bean:message key="ieci.tecdoc.sgm.nt.notificacion.notificacion.titulo"/></h1>
   							</div>
							<div class="col" style="width: 90%; text-align: left">
								<table width="100%" cellspacing="6px">
							        <tr>
							            <th width="15%"><bean:message key="ieci.tecdoc.sgm.nt.notificacion.notificacion.nombreApellidos"/>:</th>
							            <td width="35%"><bean:write name="detalle" property="apellidosDest" scope="request"/>, <bean:write name="detalle" property="nombreDest"  scope="request"/></td>
							            <th width="15%"><bean:message key="ieci.tecdoc.sgm.nt.notificacion.notificacion.expediente"/>:</th>
							            <td width="35%"><bean:write name="detalle" property="numeroExpediente" scope="request"/></td>
							        </tr>
							        <tr>
							            <th width="15%"><bean:message key="ieci.tecdoc.sgm.nt.notificacion.notificacion.asunto"/>:</th>
							            <td width="35%"><bean:write name="detalle" property="asunto" scope="request"/></td>
							            <th width="15%"><bean:message key="ieci.tecdoc.sgm.nt.notificacion.notificacion.entidadEmisora"/>:</th>
							            <td width="35%"><bean:write name="detalle" property="organismo" scope="request"/></td>            
							        </tr>
							         <tr>
							            <th width="15%"><bean:message key="ieci.tecdoc.sgm.nt.notificacion.notificacion.estado"/>:</th>
							            <td colspan="3"><bean:write name="detalle" property="descripcionEstado" scope="request"/></td>
							            <%-- <th width="15%"><bean:message key="ieci.tecdoc.sgm.nt.notificacion.notificacion.tipoNotificacion"/>:</th>
							            <td width="35%"><bean:write name="detalle" property="tipo" scope="request"/></td> --%>            
							        </tr>
	        						<tr>
	            						<th width="15%"><bean:message key="ieci.tecdoc.sgm.nt.notificacion.notificacion.texto"/>:</th>
	             						<td colspan="3"><bean:write name="detalle" property="texto" scope="request"/></td>
	             					</tr>
	    						</table>
	    					</div>
    						<br/><br/>
    						<%--
    						<div class="submenu" style="width: 90%; text-align: left">
	    						<h1><bean:message key="ieci.tecdoc.sgm.nt.notificacion.destinatario.titulo"/></h1>
   							</div>
							<div class="col" style="width: 90%; text-align: left">
	    						<table width="100%" cellspacing="6px">
						            <tr>
						                <th width="15%"><bean:message key="ieci.tecdoc.sgm.nt.notificacion.destinatario.tipoVia"/>:</th>
						                <td width="35%"><bean:write name="detalle" property="tipoViaDireccion" scope="request"/></td>
						                <th width="15%"><bean:message key="ieci.tecdoc.sgm.nt.notificacion.destinatario.nombreVia"/>:</th>
						            	<td width="35%"><bean:write name="detalle" property="viaDireccion" scope="request"/></td>
						            </tr>
						            <tr>
						                <th width="15%"><bean:message key="ieci.tecdoc.sgm.nt.notificacion.destinatario.numero"/>:</th>
						                <td width="35%"><bean:write name="detalle" property="numeroDireccion" scope="request"/></td>
						                <th width="15%"><bean:message key="ieci.tecdoc.sgm.nt.notificacion.destinatario.piso"/>:</th>
						                <td width="35%"><bean:write name="detalle" property="pisoDireccion" scope="request"/></td>
						            </tr>    
						            <tr>
						                <th width="15%"><bean:message key="ieci.tecdoc.sgm.nt.notificacion.destinatario.escalera"/>:</th>
						                <td width="35%"><bean:write name="detalle" property="escaleraDireccion" scope="request"/></td>
						                <th width="15%"><bean:message key="ieci.tecdoc.sgm.nt.notificacion.destinatario.puerta"/>:</th>
						                <td width="35%"><bean:write name="detalle" property="puertaDireccion" scope="request"/></td>
						            </tr> 
						             <tr>
						                <th width="15%"><bean:message key="ieci.tecdoc.sgm.nt.notificacion.destinatario.cp"/>:</th>
						                <td width="35%"><bean:write name="detalle" property="codigoPostal" scope="request"/></td>
						                <th width="15%"><bean:message key="ieci.tecdoc.sgm.nt.notificacion.destinatario.telefono"/>:</th>
						                <td width="35%"><bean:write name="detalle" property="telefono" scope="request"/></td>
						            </tr> 
						            <tr>
						                <th width="15%"><bean:message key="ieci.tecdoc.sgm.nt.notificacion.destinatario.provincia"/>:</th>
						                <td width="35%"><bean:write name="detalle" property="provincia" scope="request"/></td>
						                <th width="15%"><bean:message key="ieci.tecdoc.sgm.nt.notificacion.destinatario.municipio"/>:</th>
						                <td width="35%"><bean:write name="detalle" property="municipio" scope="request"/></td>
						            </tr> 
	    						</table>
	    					</div>
	    					--%>
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
