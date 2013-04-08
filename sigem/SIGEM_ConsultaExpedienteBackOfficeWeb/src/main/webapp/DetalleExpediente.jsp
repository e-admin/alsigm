<%@ page import="ieci.tecdoc.sgm.core.config.ports.PortsConfig"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>

<%@page import="ieci.tecdoc.sgm.core.services.consulta.FicherosHito"%>
<%@page import="ieci.tecdoc.sgm.core.services.consulta.FicheroHito"%>
<%@page import="ieci.tecdoc.sgm.core.services.consulta.Subsanacion"%>
<%@page import="ieci.tecdoc.sgm.core.services.consulta.Notificacion"%>
<%@page import="ieci.tecdoc.sgm.core.services.consulta.Pago"%>
<%@page import="ieci.tecdoc.sgm.core.services.consulta.Subsanaciones"%>
<%@page import="ieci.tecdoc.sgm.core.services.consulta.Pagos"%>
<%@page import="ieci.tecdoc.sgm.core.services.consulta.Notificaciones"%>
<%@page import="ieci.tecdoc.sgm.ct.utilities.Misc"%>
<%@page import="java.util.Locale"%>

<%
String rutaEstilos = (String)session.getAttribute("PARAMETRO_RUTA_ESTILOS");
if (rutaEstilos == null) rutaEstilos = "";
String rutaImagenes = (String)session.getAttribute("PARAMETRO_RUTA_IMAGENES");
if (rutaImagenes == null) rutaImagenes = "";

%>

<html:html>

<head>
	<title><bean:message key="listaExpedientes"/></title>
	<link href="css/<%=rutaEstilos%>estilos.css" rel="stylesheet" type="text/css" />

	<!--[if lte IE 5]>
		<link rel="stylesheet" type="text/css" href="css/estilos_ie5.css"/>
	<![endif]-->

	<!--[if IE 6]>
		<link rel="stylesheet" type="text/css" href="css/estilos_ie6.css"/>
	<![endif]-->

	<!--[if IE 7]>
		<link rel="stylesheet" type="text/css" href="css/estilos_ie7.css"/>
	<![endif]-->

	<script type="text/javascript" language="javascript" src="js/idioma.js"></script>

	<script language="javascript">

	function verDocumentos(guid) {

		var i=0;
		var casilla = document.getElementById(i+"_"+guid);
		var desplegar = true;

		while(casilla != undefined){
			if (i==0){
				if (casilla.style.display=='block')
					desplegar = false;
			}

			if (desplegar){
				casilla.style.display='block';
				document.getElementById('tabla_'+guid).style.display = 'inline';
			}else{
				casilla.style.display='none';
				document.getElementById('tabla_'+guid).style.display = 'block';
			}

			i++;
			casilla = document.getElementById(i+"_"+guid);
		}
		if (desplegar){
			document.getElementById('listado_'+guid).innerHTML = '<bean:message key="lista.ocultar"/>';
			document.getElementById('imagen_'+guid).src = 'img/<%=rutaImagenes%>menos.gif';
		}else{
			document.getElementById('listado_'+guid).innerHTML = '<bean:message key="lista.mostrar"/>';
			document.getElementById('imagen_'+guid).src = 'img/<%=rutaImagenes%>mas.gif';
		}
	}
	</script>
</head>

<body>
	<div id="contenedora">
		<jsp:include flush="true" page="Cabecera.jsp"></jsp:include>

		<div class="centered">
		<div class="contenedor_centrado">

			<div class="cuerpo">
      			<div class="cuerporight">
        			<div class="cuerpomid">

				<h1><bean:message key="listaExpedientes"/>: <%=request.getParameter("id")%></h1>

				<div class="tabs_submenus">
					<ul>
						<li class="subOff">
							<h3><a href="BusquedaExpedientes.do?<%=("true".equals(request.getAttribute("busqueda")))?"rebuscar=false":""%>">
							<bean:message key="busquedaExpedientes"/>
							</a></h3>
						</li>
						<li class="subOn">
							<h3><a><bean:message key="detalleExpedientes"/></a></h3>
						</li>
					</ul>
				</div>

				<div class="cuerpo_subs clearfix" >

					<div class="seccion">
					<%if(request.getAttribute(Misc.MENSAJE_ERROR) != null){%>
						<label class="error_rojo"><%=request.getAttribute(Misc.MENSAJE_ERROR)%></label>
					<%}%>

					<p class="titulo"><bean:message key="historiaExpediente"/></p>

					<!--  Tabla del estado del expediente-->
				  	<table class="tablaListado">
						<tbody>
							<logic:notEmpty name="DetalleExpediente" property="hitoEstado">

									<!--  cabecera de tabla de estado de expediente -->
									<tr>
										<th class="cabeceraTabla anchoImg"></th>
										<th class="cabeceraTabla anchoFch"><bean:message key="fecha"/></th>
										<th class="cabeceraTabla anchoDes"><bean:message key="descripcion"/></th>
										<th class="cabeceraTabla anchoDoc"><bean:message key="documentos"/></th>
										<th class="cabeceraTabla anchoAvs"><bean:message key="avisos"/></th>
									</tr>
									<bean:define name="DetalleExpediente" id="ficherosEstado" type="java.util.ArrayList" property="ficherosEstado"/>
									<!--  Fin cabecera de tabla de estado de expediente -->
									<tr>
										<td><img src="img/<%=rutaImagenes%>actual.gif" alt='<bean:message key="estado.actual"/>'/></td>
										<td class="borde"><bean:write name="DetalleExpediente" property="hitoEstadoFecha" /></td>
										<td class="borde"><bean:write name="DetalleExpediente" property="hitoEstadoDescripcion" /></td>
										<td class="borde">
										<!-- TABLA FICHEROS -->
											<bean:define name="DetalleExpediente" id="guidHitoAct" property="hitoEstadoGuid"/>

											<table id="tabla_<%=guidHitoAct%>">
												<%
												if(ficherosEstado.size() ==0){%>
													<tr>
														<td>
																&nbsp;
														</td>
													</tr>
												<%} else{%>
													<tr id="<%=guidHitoAct%>">
														<td>
															<a style="cursor: pointer" onclick="javascript:verDocumentos('<%=guidHitoAct%>');"><img id="imagen_<%=guidHitoAct%>" src="img/<%=rutaImagenes%>mas.gif"/>&nbsp;<label id="listado_<%=guidHitoAct%>"><bean:message key="lista.mostrar"/></label></a>
														</td>
													</tr>
												<%
													for(int a=0; a<ficherosEstado.size();a++){
														ieci.tecdoc.sgm.core.services.consulta.FicheroHito ficheroHito = (ieci.tecdoc.sgm.core.services.consulta.FicheroHito)ficherosEstado.get(a);
														%>
														<tr style="display: none" id='<%=a+"_"+guidHitoAct%>' valign="middle">
															<td>
																&nbsp;&nbsp;&nbsp;<img id="imagen_<%=guidHitoAct%>" src="img/<%=rutaImagenes%>book-<%=(a%2)+1%>.gif"/>&nbsp;
																<a href="RecogerDocumento.do?guid=<%=ficheroHito.getGuid()%>" target="_blank">
																	<%=ficheroHito.getTitulo()%>
																</a>
															</td>
														</tr>
													<%
													}
												}
												%>
											</table>
										</td>
										<td class="borde">
											<bean:define name="DetalleExpediente" id="subsActual" type="ieci.tecdoc.sgm.core.services.consulta.Subsanaciones" property="subsActual"/>
											<bean:define name="DetalleExpediente" id="notifsActual" type="ieci.tecdoc.sgm.core.services.consulta.Notificaciones" property="notifsActual"/>
											<bean:define name="DetalleExpediente" id="pagosActual" type="ieci.tecdoc.sgm.core.services.consulta.Pagos" property="pagosActual"/>
											<%
											boolean total = false, bSubs = false, bNotifs = false, bPagos = false;
											for (int i=0; i<subsActual.count(); i++){
												Subsanacion s = (Subsanacion)subsActual.get(i);
												if (!bSubs){
											%>
												<div class="titular">
													<h1><bean:message key="subsanaciones" /></h1>
										   		</div>
											<%	}%>
												<p class="detalle">
													<%=(bSubs)?"<div class='hr'><hr/></div>":""%>
													<%=s.getMensajeParaElCiudadano()%>
												</p>
												<p class="detalle"><bean:message key="fecha" />: <%=s.getFecha()%></p>
											<%
												bSubs = true;
											}
											for (int i=0; i<notifsActual.count(); i++){
												Notificacion s = (Notificacion)notifsActual.get(i);
												if (!bNotifs){
											%>
												<div class="titular">
													<h1><bean:message key="notificaciones" /></h1>
										   		</div>
											<%	}%>
												<p class="detalle">
													<%=(bNotifs)?"<div class='hr'><hr/></div>":""%>
													<%=s.getDescripcion()%>
												</p>
												<p class="detalle"><bean:message key="fecha" />: <%=s.getFechaNotificacion()%></p>
											<%
												bNotifs = true;
											}
											for (int i=0; i<pagosActual.count(); i++){
												Pago s = (Pago)pagosActual.get(i);
												if (!bPagos){
											%>
												<div class="titular">
													<h1><bean:message key="pagos" /></h1>
										   		</div>
											<%	}%>
												<p class="detalle">
													<%=(bPagos)?"<div class='hr'><hr/></div>":""%>
													<%=s.getMensajeParaElCiudadano()%>
												</p>
												<p class="detalle"><bean:message key="fecha" />: <%=s.getFecha()%></p>
											<%
												bPagos = true;
											}%>
											<%= (bSubs || bNotifs || bPagos)?"":"&nbsp;" %>
										</td>
									</tr>

							</logic:notEmpty>

							<logic:notEmpty name="DetalleExpediente" property="hitos">

								<bean:define name="DetalleExpediente" id="guidHitoAct" property="hitoEstadoGuid"/>

								<bean:define name="DetalleExpediente" id="ficherosHashtable" type="java.util.Hashtable" property="ficherosHashtable"/>


								<!--  Fin cabecera de tabla de historico de expedientes-->
								<logic:iterate name="DetalleExpediente" property="hitos" id="hito">
									<tr>
										<%-- Escribo la informacion necesaria de los expedientes --%>
										<td><img src="img/<%=rutaImagenes%>historico.gif" alt='<bean:message key="estado.historico"/>'/></td>
										<td class="borde"><bean:write name="hito" property="fecha" /></td>
										<td class="borde"><bean:write name="hito" property="descripcion" /></td>
										<td class="borde">

											<!-- TABLA FICHEROS -->
											<!--bean:define id="fic" property="ficheros" value="112345"/-->

											<bean:define name="hito" id="guidHito" property="guid"/>

											<table id="tabla_<%=guidHito%>">
												<%
												java.util.List miArray = ((FicherosHito)ficherosHashtable.get(guidHito)).getFicheros();
												if(miArray.size()==0){%>
													<tr>
															<td>
																&nbsp;
															</td>
														</tr>
												<%} else{%>
													<tr id="<%=guidHito%>">
														<td>
															<a style="cursor: pointer" onclick="javascript:verDocumentos('<%=guidHito%>');"><img id="imagen_<%=guidHito%>" src="img/<%=rutaImagenes%>mas.gif"/>&nbsp;<label id="listado_<%=guidHito%>"><bean:message key="lista.mostrar"/></label></a>
														</td>
													</tr>
												<%
													for(int a=0; a<miArray.size();a++){
														FicheroHito ficheroHito = (FicheroHito)miArray.get(a);
														%>
														<tr style="display: none" id='<%=a+"_"+guidHito%>' valign="middle">
															<td>
																&nbsp;&nbsp;&nbsp;<img id="imagen_<%=guidHito%>" src="img/<%=rutaImagenes%>book-<%=(a%2)+1%>.gif"/>&nbsp;
																<a href="RecogerDocumento.do?guid=<%=ficheroHito.getGuid()%>" target="_blank">
																	<%=ficheroHito.getTitulo()%>
																</a>
															</td>
														</tr>
													<%
													}
												}
												%>
											</table>
										</td>
										<td class="borde">
											<bean:define name="DetalleExpediente" id="subsHistoricas" type="java.util.ArrayList" property="subsHistoricas"/>
											<bean:define name="DetalleExpediente" id="notifsHistoricas" type="java.util.ArrayList" property="notifsHistoricas"/>
											<bean:define name="DetalleExpediente" id="pagosHistoricas" type="java.util.ArrayList" property="pagosHistoricas"/>
											<%
											boolean toral1 = false, bSubs1 = false, bNotifs1 = false, bPagos1 = false;
											for (int j=0; j<subsHistoricas.size(); j++){
												Subsanaciones ss = (Subsanaciones)subsHistoricas.get(j);
												if (ss != null && ss.count()>0){
													String _guid = ((Subsanacion)ss.get(0)).getIdentificadorHito();
													if (guidHito.equals(_guid)){
														for(int h=0; h<ss.count(); h++){
															Subsanacion s = (Subsanacion)ss.get(h);
															if (!bSubs1){
														%>
															<div class="titular">
																<h1><bean:message key="subsanaciones" /></h1>
													   		</div>
														<%	}%>
															<p class="detalle">
																<%=(bSubs1)?"<div class='hr'><hr/></div>":""%>
																<%=s.getMensajeParaElCiudadano()%>
															</p>
															<p class="detalle"><bean:message key="fecha" />: <%=s.getFecha()%></p>
														<%  if (guidHito.equals(guidHitoAct)){ %>
														<%
															}
															bSubs1 = true;
														}
													}
												}
											}
											for (int j=0; j<notifsHistoricas.size(); j++){
												Notificaciones nn = (Notificaciones)notifsHistoricas.get(j);
												if (nn != null && nn.count()>0){
													String _guid = ((Notificacion)nn.get(0)).getHitoId();
													if (guidHito.equals(_guid)){
														for(int h=0; h<nn.count(); h++){
															Notificacion n = (Notificacion)nn.get(h);
															if (!bNotifs1){
														%>
															<div class="titular">
																<h1><bean:message key="notificaciones" /></h1>
													   		</div>
														<%	}%>
															<p class="detalle">
																<%=(bNotifs1)?"<div class='hr'><hr/></div>":""%>
																<%=n.getDescripcion()%>
															</p>
															<p class="detalle"><bean:message key="fecha" />: <%=n.getFechaNotificacion()%></p>
														<%  if (guidHito.equals(guidHitoAct)){ %>
														<%
															}
															bNotifs1 = true;
														}
													}
												}
											}
											for (int j=0; j<pagosHistoricas.size(); j++){
												Pagos pp = (Pagos)pagosHistoricas.get(j);
												if (pp != null && pp.count()>0){
													String _guid = ((Pago)pp.get(0)).getIdentificadorHito();
													if (guidHito.equals(_guid)){
														for(int h=0; h<pp.count(); h++){
															Pago p = (Pago)pp.get(h);
															if (!bPagos1){
														%>
															<div class="titular">
																<h1><bean:message key="pagos" /></h1>
													   		</div>
														<%	}%>
															<p class="detalle">
																<%=(bPagos1)?"<hr class='hr'>":""%>
																<%=p.getMensajeParaElCiudadano()%>
															</p>
															<p class="detalle"><bean:message key="fecha" />: <%=p.getFecha()%></p>
														<%  if (guidHito.equals(guidHitoAct)){ %>
														<%
															}
														%>
														<%  if (guidHito.equals(guidHitoAct)){ %>
														<%
															}
															bPagos1 = true;
														}
													}
												}
											}
											%>
											<%= (bSubs1 || bNotifs1 || bPagos1)?"":"&nbsp;" %>
										</td>
									</tr>
								</logic:iterate>
							</logic:notEmpty>
						</tbody>
					</table>



					</div>

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
    		</div>
    	</div>


<!-- Fin Contenido -->
<%@ include file="Pie.jsp" %>
</body>
</html:html>