<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>

<%@page import="ieci.tecdoc.sgm.admsistema.util.Defs"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>

<%
List procesosExportacion = (ArrayList)request.getAttribute(Defs.PARAMETRO_MONITORIZACION_EXPORTACIONES);
List procesosImportacion = (ArrayList)request.getAttribute(Defs.PARAMETRO_MONITORIZACION_IMPORTACIONES);
List procesosAccionesMultientidad = (ArrayList)request.getAttribute(Defs.PARAMETRO_MONITORIZACION_ACCIONES_MULTIENTIDAD);
%>

<%@page import="ieci.tecdoc.sgm.admsistema.proceso.Proceso"%>
<html>
	<head>
		<LINK href="css/estilos.css" type=text/css rel=stylesheet />

		<script language=javascript>
			var _terminado = false;
			var _idtime = '';

			function setTerminado (terminado) {
				_terminado = terminado;
			}

			function getSegundos () {
				return document.getElementById('cada_x_segundos').value;
			}

			function setSegundos (segundos) {
				document.getElementById('cada_x_segundos').value = segundos;
			}

			function setTimer () {
				_idtime = setTimeout("actualizarCadaX()", document.getElementById('cada_x_segundos').value*1000);
			}

			function getTimer () {
				return _idtime;
			}

			function salir() {
				var formulario = document.getElementById('formulario');
				formulario.action = 'listadoEntidades.do';
				formulario.submit();
			}

			function mostrarExportacion() {
				var selector = document.getElementById('<%=Defs.PARAMETRO_MONITORIZACION_EXPORTACIONES%>');
				if (selector.selectedIndex != -1) {
					_segs = document.getElementById('cada_x_segundos').value;
					_terminado = false;
					desactivarBoton('boton_actualizar');
					pararActualizacion();
					seleccionarValorDefecto('<%=Defs.PARAMETRO_MONITORIZACION_IMPORTACIONES%>');
					seleccionarValorDefecto('<%=Defs.PARAMETRO_MONITORIZACION_ACCIONES_MULTIENTIDAD%>');
					var frameActualizar = document.getElementById('actualizar');
					frameActualizar.src = 'actualizarMonitorizacion.do?<%=Defs.PARAMETRO_ID_PROCESO%>=' + selector.options[selector.selectedIndex].value+'&<%=Defs.PARAMETRO_TIPO_PROCESO%>=<%=Defs.EXPORTAR%>';
				}
			}

			function mostrarImportacion() {
				var selector = document.getElementById('<%=Defs.PARAMETRO_MONITORIZACION_IMPORTACIONES%>');
				if (selector.selectedIndex != -1) {
					_segs = document.getElementById('cada_x_segundos').value;
					_terminado = false;
					desactivarBoton('boton_actualizar');
					pararActualizacion();
					seleccionarValorDefecto('<%=Defs.PARAMETRO_MONITORIZACION_EXPORTACIONES%>');
					seleccionarValorDefecto('<%=Defs.PARAMETRO_MONITORIZACION_ACCIONES_MULTIENTIDAD%>');
					var frameActualizar = document.getElementById('actualizar');
					frameActualizar.src = 'actualizarMonitorizacion.do?<%=Defs.PARAMETRO_ID_PROCESO%>=' + selector.options[selector.selectedIndex].value+'&<%=Defs.PARAMETRO_TIPO_PROCESO%>=<%=Defs.IMPORTAR%>';

				}
			}

			function mostrarAccionMultientidad() {
				var selector = document.getElementById('<%=Defs.PARAMETRO_MONITORIZACION_ACCIONES_MULTIENTIDAD%>');
				if (selector.selectedIndex != -1) {
					_segs = document.getElementById('cada_x_segundos').value;
					_terminado = false;
					desactivarBoton('boton_actualizar');
					pararActualizacion();
					seleccionarValorDefecto('<%=Defs.PARAMETRO_MONITORIZACION_IMPORTACIONES%>');
					seleccionarValorDefecto('<%=Defs.PARAMETRO_MONITORIZACION_EXPORTACIONES%>');
					var frameActualizar = document.getElementById('actualizar');
					frameActualizar.src = 'actualizarMonitorizacion.do?<%=Defs.PARAMETRO_ID_PROCESO%>=' + selector.options[selector.selectedIndex].value + '&<%=Defs.PARAMETRO_TIPO_PROCESO%>=<%=Defs.ACCION_MULTIENTIDAD%>';
				}
			}

			function seleccionarValorDefecto(tipo) {
				var elemento = document.getElementById(tipo);
				elemento.selectedIndex = 0;
			}

			function actualizarCadaX() {
				var segs = document.getElementById('cada_x_segundos').value;
				if (segs == '') {
					actualiza();
					clearTimeout(_idtime);
				} else {
					if (segs < 10) {
						segs = 10;
						document.getElementById('cada_x_segundos').value = 10;
					}
					actualiza();
					_idtime = setTimeout("actualizarCadaX()", segs*1000);
				}
			}

			function desactivarBoton(boton) {
				document.getElementById(boton).disabled = true;
				document.getElementById(boton).style.background = 'url(img/fondo_boton_desactivado.jpg)';
			}

			function activarBoton(boton) {
				document.getElementById(boton).disabled = false;
				document.getElementById(boton).style.background = 'url(img/fondo_boton.jpg)';
			}

			function activarMensaje (campo) {
				var tabla = document.getElementById('tabla_mensajes_' + campo);
				tabla.style.visibility = 'visible';
				tabla.style.position = 'relative';
				var field = document.getElementById(campo);
				field.style.visibility = 'visible';
				field.style.position = 'relative';
			}

			function desactivarMensaje (campo) {
				var tabla = document.getElementById('tabla_mensajes_' + campo);
				tabla.style.visibility = 'hidden';
				tabla.style.position = 'absolute';
				var field = document.getElementById(campo);
				field.style.visibility = 'hidden';
				field.style.position = 'absolute';
			}

			function actualiza() {
				pararActualizacion();
				desactivarBoton('boton_actualizar');

				var selectorExp = document.getElementById('<%=Defs.PARAMETRO_MONITORIZACION_EXPORTACIONES%>');
				var selectorImp = document.getElementById('<%=Defs.PARAMETRO_MONITORIZACION_IMPORTACIONES%>');
				var selectorAcc = document.getElementById('<%=Defs.PARAMETRO_MONITORIZACION_ACCIONES_MULTIENTIDAD%>');
				if (selectorExp.selectedIndex > 0)
					mostrarExportacion();
				else if (selectorImp.selectedIndex > 0)
					mostrarImportacion();
				else if (selectorAcc.selectedIndex > 0)
					mostrarAccionMultientidad();
			}

			function pararActualizacion() {
				if (_idtime != '') {
					clearTimeout(_idtime);
					_idtime = '';
				}
			}

			function realizarOperacion(idEntidad, accion) {
				var formulario = document.getElementById("formulario");
				formulario.action = 'accionesEntidad.do';
				var campo = document.getElementById("<%=Defs.PARAMETRO_ENTIDAD_SELECCIONADA%>");
				campo.value = idEntidad;
				campo = document.getElementById("<%=Defs.PARAMETRO_ACCION_ENTIDAD%>");
				campo.value = accion;
				formulario.submit();
			}

			function realizarBusqueda() {
				var formulario = document.getElementById("busquedaBean");
				formulario.submit();
			}
		</script>
	</head>
	<body>
		<%String locale = (request.getLocale() != null) ? request.getLocale().getLanguage() : ""; if (locale == null || "".equals(locale)) locale = "es";%>
		<jsp:include flush="true" page="../cabecera.jsp">
			<jsp:param name="ayuda" value='<%=request.getContextPath() + "/jsp/ayudas/" + locale + "/procesos/monitorizar.html"%>' />
		</jsp:include>

		<form id=formulario action="" method=post>
			<input id=<%=Defs.PARAMETRO_ENTIDAD_SELECCIONADA%> type=hidden name=<%=Defs.PARAMETRO_ENTIDAD_SELECCIONADA%>>
			<input id=<%=Defs.PARAMETRO_ACCION_ENTIDAD%> type=hidden name=<%=Defs.PARAMETRO_ACCION_ENTIDAD%>>
		</form>

		<div id=contenedora align=center style="overflow-x: hidden;">
			<div class=cuerpo style="width: 80%">
				<div class=cuerporight>
					<div class=cuerpomid>
						<h1><bean:message key="entidades.titulo"/></h1>
						<div class=submenu3>
							<ul>
  								<li class=submen1on>
  									<img src="img/subme3_on.gif">
  									<a href="listadoEntidades.do" class=submen1on_a>
  										<bean:message key="tab.entidades"/>
  									</a>
  								</li>
								<li class="submen1off">
						        	<img src="img/subme3_on_of.gif" />
									<a href="listadoUsuarios.do">
										<bean:message key="tab.usuarios"/>
									</a>
									<img src="img/subme3_of_0.gif">
		  						</li>
  							</ul>
  						</div>
						<div class=cuadro>
							<div>
								<h1><bean:message key="entidades.configuradas"/></h1>
								<div class="col" align="right" style="height: 30px;">
									<table border="0" cellspacing="0" cellpadding="0">
       									<tr align=right>
						       				<td width="100%">&nbsp;</td>
						       				<html:form styleId="busquedaBean" action="/busquedaEntidades.do" method="post">
							       				<td width="20%">
	       											<html:text property="<%=Defs.PARAMETRO_BUSQUEDA%>" styleId="<%=Defs.PARAMETRO_BUSQUEDA%>"></html:text>
						    					</td>
							       				<td width="20%">
							       					<img src="img/ico_search.jpg" style="cursor: pointer" onclick="javascript:realizarBusqueda()" alt="<bean:message key="entidades.boton.buscar"/>" title="<bean:message key="entidades.boton.buscar"/>"/>
							       				</td>
							       			</html:form>
       									</tr>
       								</table>
								</div>
								<br/>
								<table id="tabla_mensajes_tiempo_espera" align="center" border=0 cellpadding=0 cellspacing=0 style="width: 90%; padding-bottom:22px; position: absolute; visibility: hidden;">
									<tr>
										<td colspan=2>
					                		<label id="tiempo_espera" class="gr_informativo" style="visibility: hidden; position:absolute"><bean:message key="mensaje.informativo.entidad.tiempo_espera"/></label>
					                	</td>
									</tr>
								</table>
								<table id="tabla_mensajes_finalizado" align="center" border=0 cellpadding=0 cellspacing=0 style="width: 90%; padding-bottom:22px; position: absolute; visibility: hidden;">
									<tr>
										<td colspan=2>
					                		<label id="finalizado" class="gr_informativo" style="visibility: hidden; position:absolute"><bean:message key="mensaje.informativo.entidad.importacion_finalizada"/></label>
					                	</td>
									</tr>
								</table>
								<div align="center">
									<table>
										<tr>
											<td>
												<label class="gr_ext"><bean:message key="entidad.monitorizar.seleccione"/></label>
											</td>
										</tr>
									</table>
									<table>
										<tr>
											<td style="width:50px"></td>
											<td>
												<label class="gr_ext"><bean:message key="entidad.monitorizar.exportaciones"/> </label>
												<select id="<%=Defs.PARAMETRO_MONITORIZACION_EXPORTACIONES%>" class="gr" onchange="javascript:mostrarExportacion();">
													<logic:notEmpty name="<%=Defs.PARAMETRO_MONITORIZACION_EXPORTACIONES%>">
													<%for(int k=0; k<procesosExportacion.size(); k++){
															Proceso proceso = (Proceso)procesosExportacion.get(k);%>
														<option value="<%=proceso.getValor()%>"><%=proceso.getNombre()%></option>
													<%}%>
													</logic:notEmpty>
													<logic:empty name="<%=Defs.PARAMETRO_MONITORIZACION_EXPORTACIONES%>">
														<option value=""><bean:message key="entidad.monitorizar.no_exportaciones"/></option>
													</logic:empty>
												</select>
											</td>
											<td>
												<label class="gr_ext"><bean:message key="entidad.monitorizar.importaciones"/> </label>
												<select id="<%=Defs.PARAMETRO_MONITORIZACION_IMPORTACIONES%>" class="gr" onchange="javascript:mostrarImportacion();">
													<logic:notEmpty name="<%=Defs.PARAMETRO_MONITORIZACION_IMPORTACIONES%>">
													<%for(int k=0; k<procesosImportacion.size(); k++){
															Proceso proceso = (Proceso)procesosImportacion.get(k);%>
														<option value="<%=proceso.getValor()%>"><%=proceso.getNombre()%></option>
													<%}%>
													</logic:notEmpty>
													<logic:empty name="<%=Defs.PARAMETRO_MONITORIZACION_IMPORTACIONES%>">
														<option value=""><bean:message key="entidad.monitorizar.no_importaciones"/></option>
													</logic:empty>
												</select>
											</td>
											<td>
												<label class="gr_ext"><bean:message key="entidad.monitorizar.acciones.multientidad"/> </label>
												<select id="<%=Defs.PARAMETRO_MONITORIZACION_ACCIONES_MULTIENTIDAD%>" class="gr" onchange="javascript:mostrarAccionMultientidad();">
													<logic:notEmpty name="<%=Defs.PARAMETRO_MONITORIZACION_ACCIONES_MULTIENTIDAD%>">
													<%for(int k=0; k<procesosAccionesMultientidad.size(); k++){
															Proceso proceso = (Proceso)procesosAccionesMultientidad.get(k);%>
														<option value="<%=proceso.getValor()%>"><%=proceso.getNombre()%></option>
													<%}%>
													</logic:notEmpty>
													<logic:empty name="<%=Defs.PARAMETRO_MONITORIZACION_ACCIONES_MULTIENTIDAD%>">
														<option value=""><bean:message key="entidad.monitorizar.no_acciones_multientidad"/></option>
													</logic:empty>
												</select>
											</td>
										</tr>
									</table>
									<iframe id=actualizar name=actualizar src="<html:rewrite page="/jsp/blank.html" />" style="width: 92%; height: 350px;" frameborder="0" scrolling="no">
									</iframe>
									<br/>
									<bean:message key="entidad.monitorizar.actualizar"/>
									<input type="text" class="gr" style="width: 30px;" id="cada_x_segundos" value="" />
									<bean:message key="entidad.monitorizar.segundos"/>
									<input id=boton_actualizar type="button" value="<bean:message key="entidades.boton.actualizar" />" class="ok" onclick="javascript:actualizarCadaX();" title="<bean:message key="entidades.boton.actualizar" />" />
									<input id=boton_salir type="button" value="<bean:message key="boton.salir" />" class="ok" onclick="javascript:salir();" title="<bean:message key="boton.salir" />"/>
								</div>
							</div>
						</div>
					</div>
					<div class=cuerpobt>
						<div class=cuerporightbt>
							<div class=cuerpomidbt></div>
						</div>
					</div>
				</div>
				<div id=pie></div>
			</div>
		</div>
		<script language="Javascript">
			desactivarBoton('boton_actualizar');;
		</script>
	</body>
</html>