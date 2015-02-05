<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>

<%@page import="ieci.tecdoc.sgm.geolocalizacion.utils.Defs"%>
<%@page import="java.util.ArrayList"%>

<%
ArrayList arrayTiposVia = (ArrayList)request.getSession().getServletContext().getAttribute(Defs.PLUGIN_LISTADO_TIPO_VIAS);
String viaSel = (String)request.getAttribute(Defs.PARAMETRO_TIPO_VIA);
viaSel = (viaSel != null) ? viaSel : "";

boolean mostrarPortales = false;
if (Defs.ACCION_PORTALES.equals(request.getParameter(Defs.PARAMETRO_ACCION)) || 
	(Defs.ACCION_BUSQUEDA.equals(request.getParameter(Defs.PARAMETRO_ACCION)) &&
	Defs.ACCION_PORTALES.equals((String)request.getAttribute(Defs.PARAMETRO_ACCION))))
	mostrarPortales = true;

%>

<%@page import="ieci.tecdoc.sgm.core.services.geolocalizacion.Portal"%>
<html>
	<head>
		<link href="css/estilos.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" language="javascript" src="js/navegador.js"></script>
		
		<script language="javascript">
			var cantidad = 0;
			
			function cambioColor(filaRegistro,event){
				if (window.event) {
					filaRegistro.style.cursor = "hand";
					var miArray = filaRegistro.getElementsByTagName("td");
					for (i=0;i<miArray.length;i++){
						miArray[i].style.backgroundColor="#FFCC80";
					}
					
				} else {
					filaRegistro.style.cursor = "pointer";
					filaRegistro.style.backgroundColor="#FFCC80";
				}
			}
			
			function cambioColorBlanco(filaRegistro,event){
				if (window.event) {
					var miArray = filaRegistro.getElementsByTagName("td");
					for (i=0;i<miArray.length;i++){
						miArray[i].style.backgroundColor="#FFFFFF";
					}
					
				} else {
					filaRegistro.style.backgroundColor="#FFFFFF";
				}
			}
			
			function changeColor(tabla, color){
				if (color == 'true'){
					document.getElementById('tabla0'+tabla).style.backgroundColor = "#FFCC80";
					document.getElementById('tabla1'+tabla).style.backgroundColor = "#FFCC80";
				}else {
					document.getElementById('tabla0'+tabla).style.backgroundColor = "transparent";
					document.getElementById('tabla1'+tabla).style.backgroundColor = "transparent";
				}
			}
			
			function buscar(){
				var tipoVia = document.getElementById('<%=Defs.PARAMETRO_TIPO_VIA%>').value;
				var nombreVia = document.getElementById('<%=Defs.PARAMETRO_NOMBRE_VIA%>').value;
				var numeroPortal = document.getElementById('<%=Defs.PARAMETRO_NUMERO_PORTAL%>').value;
				document.forms[0].action = 'validarDireccionPostal.do?<%=Defs.PARAMETRO_TIPO_VIA%>=' + tipoVia + '&<%=Defs.PARAMETRO_NOMBRE_VIA%>=' + nombreVia + '&<%=Defs.PARAMETRO_NUMERO_PORTAL%>=' + numeroPortal + '&<%=Defs.PARAMETRO_ACCION%>=<%=Defs.ACCION_BUSQUEDA%>';
				return true;
			}
			
			function portales(idVia, tipoVia, nombreVia){
				location.href = 'validarDireccionPostal.do?<%=Defs.PARAMETRO_TIPO_VIA%>=' + tipoVia + '&<%=Defs.PARAMETRO_NOMBRE_VIA%>=' + nombreVia + '&<%=Defs.PARAMETRO_ACCION%>=<%=Defs.ACCION_PORTALES%>&<%=Defs.PARAMETRO_ID_VIA%>=' + idVia;
			}
			
			
			
			<%if(mostrarPortales){%>
			function ver(tipo){
				var frameMapa = document.getElementById('visorMapa');
				var esperaMapa = document.getElementById('esperaMapa');
				var fondoMapa = document.getElementById('fondoMapa');
				var portalSel = document.getElementById('idPortal');
				var idPortal = portalSel.options[portalSel.selectedIndex].value;
				var numeroPortal = portalSel.options[portalSel.selectedIndex].text;

				if (tipo == 'detalle')
					frameMapa.src = 'obtenerDetalle.do?<%=Defs.PARAMETRO_NOMBRE_VIA%>=<bean:write name="<%=Defs.PARAMETRO_NOMBRE_VIA%>" />&<%=Defs.PARAMETRO_TIPO_VIA%>=<bean:write name="<%=Defs.PARAMETRO_TIPO_VIA%>" />&<%=Defs.PARAMETRO_NUMERO_PORTAL%>=' + numeroPortal + '&<%=Defs.PARAMETRO_ID_PORTAL%>=' + idPortal;
				else if (tipo == 'mapa')
					frameMapa.src = 'obtenerMapa.do?<%=Defs.PARAMETRO_ID_VIA%>=<bean:write name="<%=Defs.PARAMETRO_ID_VIA%>" />&<%=Defs.PARAMETRO_NUMERO_PORTAL%>=' + numeroPortal;

				esperaMapa.style.visibility = 'visible';
				fondoMapa.style.visibility = 'visible';
				document.getElementById('tdPortales').style.visibility = 'hidden';
				document.getElementById('idPortal').disabled = true;
			}
			
			function volver(){
				var tipoVia = '<bean:write name="<%=Defs.PARAMETRO_TIPO_VIA_BUSQUEDA%>" />';
				var nombreVia = '<bean:write name="<%=Defs.PARAMETRO_NOMBRE_VIA_BUSQUEDA%>" />';
				var numeroPortal = '<bean:write name="<%=Defs.PARAMETRO_NUMERO_PORTAL_BUSQUEDA%>" />';
				location.href = 'validarDireccionPostal.do?<%=Defs.PARAMETRO_TIPO_VIA%>=' + tipoVia + '&<%=Defs.PARAMETRO_NOMBRE_VIA%>=' + nombreVia + '&<%=Defs.PARAMETRO_NUMERO_PORTAL%>=' + numeroPortal + '&<%=Defs.PARAMETRO_ACCION%>=<%=Defs.ACCION_BUSQUEDA%>';
			}
			
			function devolverValor(){
			}
			<%}%>
			
			function conMayusculas(field) {
            	field.value = field.value.toUpperCase()
			}
			
			function cerrarMensaje(num) {
				document.getElementById('tipoVia').disabled = false;
				document.getElementById('fondo'+num).style.visibility = 'hidden';
				document.getElementById('mensaje'+num).style.visibility = 'hidden';
			}
			
			function abrirMensaje(num) {
				document.getElementById('tipoVia').disabled = true;
				document.getElementById('fondo'+num).style.visibility = 'visible';
				document.getElementById('mensaje'+num).style.visibility = 'visible';
			}
			
			function ocultarBusqueda(){
				document.getElementById('divOcultar').style.position="absolute";
				document.getElementById('divOcultar').style.visibility="hidden";
				document.getElementById('divMostrar').style.position="relative";
				document.getElementById('divMostrar').style.visibility="visible";
				document.getElementById('divOcultarResultados').style.position="relative";
				document.getElementById('divOcultarResultados').style.visibility="visible";
				document.getElementById('divMostrarResultados').style.position="absolute";
				document.getElementById('divMostrarResultados').style.visibility="hidden";
				var c = 0;
				for(c=0; c<cantidad; c++){
					document.getElementById('tabla0'+c).style.position="relative";
					document.getElementById('tabla0'+c).style.visibility="visible";
					document.getElementById('tabla0'+c).style.width="100%";
					document.getElementById('tabla1'+c).style.position="relative";
					document.getElementById('tabla1'+c).style.visibility="visible";
					document.getElementById('tabla1'+c).style.width="100%";
				}
				if(document.getElementById('tdPortales') != undefined)
					document.getElementById('tdPortales').style.visibility = 'visible';
			}
			
			function mostrarBusqueda(){
				document.getElementById('divOcultar').style.position="relative";
				document.getElementById('divOcultar').style.visibility="visible";
				document.getElementById('divMostrar').style.position="absolute";
				document.getElementById('divMostrar').style.visibility="hidden";
				document.getElementById('divOcultarResultados').style.position="absolute";
				document.getElementById('divOcultarResultados').style.visibility="hidden";
				document.getElementById('divMostrarResultados').style.position="relative";
				document.getElementById('divMostrarResultados').style.visibility="visible";			
				var c = 0;
				for(c=0; c<cantidad; c++){
					document.getElementById('tabla0'+c).style.position="absolute";
					document.getElementById('tabla0'+c).style.visibility="hidden";
					document.getElementById('tabla0'+c).style.width="10px";
					document.getElementById('tabla1'+c).style.position="absolute";
					document.getElementById('tabla1'+c).style.visibility="hidden";
					document.getElementById('tabla1'+c).style.width="10px";
				}
				if(document.getElementById('tdPortales') != undefined)
					document.getElementById('tdPortales').style.visibility = 'hidden';
			}

		</script>
	</head>
	<body>
		<table border="0px" width="100%" cellpadding="0px" cellspacing="0px">
			<tr style="background-image: url(img/logo_back.jpg); border: 0px">
				<td align="left">
					<img src="img/logo.gif" />
				</td>
				<td align="right">
					<img src="img/logo_end.jpg" />
				</td>
			</tr>
		</table>
		<br/>
		<div id="contenedora">
	    	<div class="cuerpo">
				<div class="cuerporight">
					<div class="cuerpomid">
						<h1>Búsqueda por vía</h1>
						<div class="submenu3"></div>
						<div id="divMostrar" class="cuadroFX" style="width: 100%; padding-top: 0px; padding-bottom: 0px; visibility: visible; position: relative">
							<div style="border:0px; width: 100%; height: 20px; padding: 5px; padding-left: 0px;" align="right"> 
								<label class="gr" style="width: 100%"><a onclick="javascript:mostrarBusqueda();" style="cursor: pointer">Mostrar búsqueda&nbsp;<img src="img/arrow_down.gif" /></a>&nbsp;&nbsp;</label>
							</div>
						</div>
						<div id="divOcultar" class="cuadroFX" style="width: 100%; padding-top: 0px; visibility:hidden; position: absolute">
							<div style="border:0px; width: 100%; height: 20px; padding: 5px; padding-left: 0px;" align="right"> 
								<label class="gr" style="width: 100%"><a onclick="javascript:ocultarBusqueda();" style="cursor: pointer">Ocultar búsqueda&nbsp;<img src="img/arrow_up.gif" /></a>&nbsp;&nbsp;</label>
							</div>
							<form method="POST" onsubmit="return buscar()">
								<input type="hidden" id="accion" name="accion" value=""/>
								<table  cellpadding="0px" cellspacing="0px" align="center">
									<tr>
										<td>
											<label class="gr"><bean:message key="ieci.tecdoc.sgm.geolocalizacion.texto.tipoVia"/></label>
										</td>
										<td>
											<label class="gr"><bean:message key="ieci.tecdoc.sgm.geolocalizacion.texto.nombreVia"/></label>
										</td>
										<td>
											<label class="gr"><bean:message key="ieci.tecdoc.sgm.geolocalizacion.texto.numeroPortal"/></label>
										</td>
									</tr>
									<tr>
										<td width="100px">
											<select class="gr" id="<%=Defs.PARAMETRO_TIPO_VIA%>" style="width: 100px">
												<option value=""></option>	
												<%for(int i=0; i<arrayTiposVia.size(); i++){
													String[] via = (String[])arrayTiposVia.get(i);
												%>
												<option value="<%=via[0]%>" <%=(!viaSel.equals(via[0]))?"":"selected"%>><%=via[1]%></option>
												<%}%>
											</select>
										</td>
										<td width="320px">
											<input class="gr" id="<%=Defs.PARAMETRO_NOMBRE_VIA%>" style="width: 320px" value='<bean:write name="<%=Defs.PARAMETRO_NOMBRE_VIA%>" />' onChange="javascript:conMayusculas(this);" />
										</td>
										<td width="80px">
											<input class="gr" id="<%=Defs.PARAMETRO_NUMERO_PORTAL%>" style="width: 80px" value='<bean:write name="<%=Defs.PARAMETRO_NUMERO_PORTAL%>" />' onChange="javascript:conMayusculas(this);" />							
										</td>
									</tr>
									<tr><td height="20px" colspan="3"></td></tr>
									<tr>
										<td width="160px" colspan="3" align="left">
											<input type="submit" value='<bean:message key="ieci.tecdoc.sgm.geolocalizacion.boton.buscar" />' id="realizarBusqueda" class="ok" />
										</td>
									</tr>
								</table>
							</form>
						</div>
						<br/>
						<div id="divMostrarResultados" class="cuadroFX" style="width: 100%; padding-top: 0px; padding-bottom: 0px; visibility: hidden; position: relative; border-top: 1px solid #c6c9d5">
							<div style="border:0px; width: 100%; height: 20px; padding: 5px; padding-left: 0px;" align="right"> 
								<label class="gr" style="width: 100%"><a onclick="javascript:ocultarBusqueda();" style="cursor: pointer">Mostrar resultados&nbsp;<img src="img/arrow_down.gif" /></a>&nbsp;&nbsp;</label>
							</div>
						</div>
						<div id="divOcultarResultados" class="cuadroFX" class="cuadro" style="width: 100%; padding-top: 0px; visibility:visible; position: absolute; border-top: 1px solid #c6c9d5">
							<div style="border:0px; width: 100%; height: 20px; padding: 5px; padding-left: 0px;" align="right"> 
								<label class="gr" style="width: 100%"><a onclick="javascript:mostrarBusqueda();" style="cursor: pointer">Ocultar resultados&nbsp;<img src="img/arrow_up.gif" /></a>&nbsp;&nbsp;</label>
							</div>
							<table width="90%" cellpadding="0px" cellspacing="0px" align="center" id="tablaVias" name="tablaVias">
								<%if(!mostrarPortales){%>
									<tr>
										<td>
											<%int i=0; %>
											<display:table name="<%=Defs.LISTADO_VIAS%>" class="table-display-tag" uid="via" 
												pagesize="10" 
												requestURI="validarDireccionPostal.do"
												export="false" 
												class="tablaListado"
												sort="list"
												style="width:100%;">
													<display:column paramId="tabla<%=i%>" style="width: 25%" paramName="tabla<%=i%>"  media="html" titleKey="ieci.tecdoc.sgm.geolocalizacion.texto.tipoVia" sortable="false" headerClass="cabeceraTabla">
														<table id="tabla0<%=i%>" width="100%" height="100%" onmouseover="javascript:changeColor(<%=i%>, 'true');" onmouseout="javascript:changeColor(<%=i%>, 'false');">
															<tr height="100%" style="cursor:pointer;" onClick="javascript:portales('<bean:write name="via" property="idVia" />','<bean:write name="via" property="tipoVia" />','<bean:write name="via" property="nombreVia" />');">
																<td style="padding-left: 6px">
																	<bean:write name="via" property="tipoVia" />
																</td>
															</tr>
														</table>
													</display:column>
													<display:column media="html" style="width: 75%" titleKey="ieci.tecdoc.sgm.geolocalizacion.texto.nombreVia" sortable="false" headerClass="cabeceraTabla">
														<table id="tabla1<%=i%>" width="100%" height="100%" onmouseover="javascript:changeColor(<%=i%>, 'true');" onmouseout="javascript:changeColor(<%=i%>, 'false');">
															<tr height="100%" style="cursor:pointer;" onClick="javascript:portales('<bean:write name="via" property="idVia" />','<bean:write name="via" property="tipoVia" />','<bean:write name="via" property="nombreVia" />');">
																<td style="padding-left: 6px">
																	<bean:write name="via" property="nombreVia" />
																</td>
															</tr>
														</table>
														<% i++; %>
													</display:column>
											</display:table>
										</td>
									</tr>
									<script>
										cantidad = <%=i%>;
									</script>
								<%}%>
								<%if(mostrarPortales){%>
									<tr>
										<td width="18%">
											<label class="gr">
												<b><bean:message key="ieci.tecdoc.sgm.geolocalizacion.texto.tipoVia"/>:</b>
											</label>
										</td>
										<td class="gr">
											<bean:write name="<%=Defs.PARAMETRO_TIPO_VIA%>" />
										</td>
									</tr>
									<tr>
										<td width="18%">
											<label class="gr">
												<b><bean:message key="ieci.tecdoc.sgm.geolocalizacion.texto.nombreVia"/>:</b>
											</label>
										</td>
										<td class="gr">
											<bean:write name="<%=Defs.PARAMETRO_NOMBRE_VIA%>" />
										</td>
									</tr>
									<tr>
										<td width="18%">
											<label class="gr">
												<b><bean:message key="ieci.tecdoc.sgm.geolocalizacion.texto.numeroPortal"/>:</b>
											</label>
										</td>
										<td id="tdPortales" class="gr">
											<%ArrayList portales = (ArrayList)request.getAttribute(Defs.LISTADO_PORTALES); %>
											<select id="<%=Defs.PARAMETRO_ID_PORTAL%>" name="<%=Defs.PARAMETRO_ID_PORTAL%>" class="gr" style="width:50px">
												<%for(int h=0; h<portales.size(); h++){%>
												<option value="<%=((Portal)portales.get(h)).getIdPortal()%>"><%=((Portal)portales.get(h)).getNumPortal()%></option>
												<%}%>
											</select>
										</td>
									</tr>
									<tr><td colspan=2><br/></td></tr>
									<tr align="center">
										<td colspan=2>
											<input type="button" value='<bean:message key="ieci.tecdoc.sgm.geolocalizacion.boton.volver" />' id="volverListado" class="ok" onclick="javascript:volver();"/>
											&nbsp;&nbsp;
											<input type="button" value='<bean:message key="ieci.tecdoc.sgm.geolocalizacion.boton.verDetalle" />' id="verDetalles" class="ok" onclick="javascript:ver('detalle');"/>
											&nbsp;&nbsp;
											<input type="button" value='<bean:message key="ieci.tecdoc.sgm.geolocalizacion.boton.verMapa" />' id="verMapa" class="ok" onclick="javascript:ver('mapa');"/>
											&nbsp;&nbsp;
											<input type="button" value='<bean:message key="ieci.tecdoc.sgm.geolocalizacion.boton.devolverValor" />' id="devolverValor" class="ok" onclick="javascript:devolverValor();"/>
										</td>
									</tr>
								<%}%>
							</table>
							<table>
								<tr>
									<td>
										
									</td>
								</tr>
							</table>
						</div>
					</div>
				</div>
			</div>
			<div class="cuerpobt">
		    	<div class="cuerporightbt">
   		   			<div class="cuerpomidbt">&nbsp;</div>
     			</div>
	   		</div>
		</div>
		<%if(mostrarPortales){%>
			<div id="fondoMapa" name="fondoMapa" style="width: 100%; height: 80%; top: 0px; left: 0px; background-color: #ffffff; filter: alpha(opacity=50); position: absolute; z-index:10; visibility: hidden;">
						
			</div>
			<div id="esperaMapa" name="esperaMapa" style="position: absolute; width: 360px; height: 120px; margin-top: 50%; margin-left: 50%; top: -60px; left: -180px; background-color: #ffffff; border: 1px solid #7f9db9; filter: alpha(opacity=100); z-index: 11; visibility: hidden;">
				<table align="center" width="100%">
					<tr>
						<td height="80px" class="gr" align="center">
							<bean:message key="ieci.tecdoc.sgm.geolocalizacion.texto.cargandoDatos" />
						</td>
					</tr>
				</table>
			</div>
			<iframe name="visorMapa" id="visorMapa" scrolling="no" frameborder="0" style="visibility:hidden; width:320px; height: 360px; position: absolute; margin-top: 50%; margin-left: 50%; top: -180px; left: -160px; background-color: #ffffff; border: 1px solid #7f9db9; filter: alpha(opacity=100); z-index: 11;">
			
			</iframe>
		<%}%>
		<logic:notEmpty name="<%=Defs.ERROR_KEY%>">
			<div id="fondo" name="fondo" style="width: 102%; height: 100%; top: 0px; left: 0px; background-color: #ffffff; filter: alpha(opacity=50); position: absolute; z-index:10; visibility: hidden;">
				
			</div>
			<div id="mensaje" name="mensaje" style="position: absolute; width: 360px; height: 120px; margin-top: 50%; margin-left: 50%; top: -60px; left: -180px; background-color: #ffffff; border: 1px solid #7f9db9; filter: alpha(opacity=100); z-index: 11; visibility: hidden;">
				<table align="center" width="100%">
					<tr>
						<td height="80px" class="gr" align="center">
							<bean:write name="<%=Defs.ERROR_KEY%>" />
						</td>
					</tr>
					<tr>
						<td align="center">
							<input type="submit" value='<bean:message key="ieci.tecdoc.sgm.geolocalizacion.boton.aceptar" />' class="ok" onclick="javascript:cerrarMensaje('');"/>
						</td>
					</tr>
				</table>
			</div>
			<script language="Javascript">
				abrirMensaje('');
			</script>
		</logic:notEmpty>
		
		<logic:empty name="<%=Defs.ERROR_KEY%>">
			<% if (Defs.ACCION_BUSQUEDA.equals(request.getAttribute(Defs.PARAMETRO_ACCION))) {%>
				<logic:empty name="<%=Defs.LISTADO_VIAS%>">
					<div id="fondo1" name="fondo1" style="width: 102%; height: 100%; top: 0px; left: 0px; background-color: #ffffff; filter: alpha(opacity=50); position: absolute; z-index:10; visibility: hidden;">
						
					</div>
					<div id="mensaje1" name="mensaje1" style="position: absolute; width: 360px; height: 120px; margin-top: 50%; margin-left: 50%; top: -60px; left: -180px; background-color: #ffffff; border: 1px solid #7f9db9; filter: alpha(opacity=100); z-index: 11; visibility: hidden;">
						<table align="center" width="100%">
							<tr>
								<td height="80px" class="gr" align="center">
									<bean:message key="ieci.tecdoc.sgm.geolocalizacion.texto.notFound" />
								</td>
							</tr>
							<tr>
								<td align="center">
									<input type="submit" value='<bean:message key="ieci.tecdoc.sgm.geolocalizacion.boton.aceptar" />' class="ok" onclick="javascript:cerrarMensaje('1');"/>
								</td>
							</tr>
						</table>
					</div>
					<script language="Javascript">
						abrirMensaje('1');
					</script>
				</logic:empty>
			<% } %>
		</logic:empty>		
		<script>
			<logic:notEmpty name="<%=Defs.LISTADO_VIAS%>">
				ocultarBusqueda();
			</logic:notEmpty>
			<logic:empty name="<%=Defs.LISTADO_VIAS%>">
				mostrarBusqueda();
			</logic:empty>
			<logic:equal parameter="<%=Defs.PARAMETRO_ACCION%>" value="<%=Defs.ACCION_PORTALES%>">
				ocultarBusqueda();
			</logic:equal>
			<%if(mostrarPortales){%>
				ocultarBusqueda();
			<%}%>
		</script>
	</body>
</html>