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
			function enviar() {
				selectorExp = document.getElementById('<%=Defs.PARAMETRO_MONITORIZACION_EXPORTACIONES%>');
				selectorImp = document.getElementById('<%=Defs.PARAMETRO_MONITORIZACION_IMPORTACIONES%>');
				selectorAcc = document.getElementById('<%=Defs.PARAMETRO_MONITORIZACION_ACCIONES_MULTIENTIDAD%>');
				var idproceso, tipo;
				var confirmado = false;

				if (selectorExp.selectedIndex > 0) {
					idproceso = selectorExp.options[selectorExp.selectedIndex].value;
					tipo = '<%=Defs.EXPORTAR%>';
					if (confirm("<bean:message key="mensaje.confirmacion.proceso.confirmar_eliminar_exp" />"))
						confirmado = true;
				} else if (selectorImp.selectedIndex > 0) {
					idproceso = selectorImp.options[selectorImp.selectedIndex].value;
					tipo = '<%=Defs.IMPORTAR%>';
					if (confirm("<bean:message key="mensaje.confirmacion.proceso.confirmar_eliminar_imp" />"))
						confirmado = true;
				} else if (selectorAcc.selectedIndex > 0) {
					idproceso = selectorAcc.options[selectorAcc.selectedIndex].value;
					tipo = '<%=Defs.ACCION_MULTIENTIDAD%>';
					if (confirm("<bean:message key="mensaje.confirmacion.proceso.confirmar_eliminar_acc_multientidad" />"))
						confirmado = true;
				} else {
					return;
				}

				if (confirmado == true) {
					document.getElementById('<%=Defs.PARAMETRO_ID_PROCESO%>').value = idproceso;
					document.getElementById('<%=Defs.PARAMETRO_TIPO_PROCESO%>').value = tipo;
					document.getElementById('borrarForm').submit();
				}
			}

			function cancelar() {
				var formulario = document.getElementById('formulario');
				formulario.action = 'listadoEntidades.do';
				formulario.submit();
			}

			function mostrarInformacion(tipo) {
				var selector, deselector, deselector2;

				if (tipo == '<%=Defs.EXPORTAR%>') {
					selector = document.getElementById('<%=Defs.PARAMETRO_MONITORIZACION_EXPORTACIONES%>');
					deselector = document.getElementById('<%=Defs.PARAMETRO_MONITORIZACION_IMPORTACIONES%>');
					deselector2 = document.getElementById('<%=Defs.PARAMETRO_MONITORIZACION_ACCIONES_MULTIENTIDAD%>');
				} else if (tipo == '<%=Defs.IMPORTAR%>') {
					selector = document.getElementById('<%=Defs.PARAMETRO_MONITORIZACION_IMPORTACIONES%>');
					deselector = document.getElementById('<%=Defs.PARAMETRO_MONITORIZACION_EXPORTACIONES%>');
					deselector2 = document.getElementById('<%=Defs.PARAMETRO_MONITORIZACION_ACCIONES_MULTIENTIDAD%>');
				} else if (tipo == '<%=Defs.ACCION_MULTIENTIDAD%>') {
					selector = document.getElementById('<%=Defs.PARAMETRO_MONITORIZACION_ACCIONES_MULTIENTIDAD%>');
					deselector = document.getElementById('<%=Defs.PARAMETRO_MONITORIZACION_IMPORTACIONES%>');
					deselector2 = document.getElementById('<%=Defs.PARAMETRO_MONITORIZACION_EXPORTACIONES%>');
				} else {
					var frameInfo = document.getElementById('informacion');
					frameInfo.src = '<html:rewrite page="/jsp/blank.html" />';
					return;
				}

				deselector.selectedIndex = 0;
				deselector2.selectedIndex = 0;
				if (selector.selectedIndex  > 0) {
					var frameInfo = document.getElementById('informacion');
					frameInfo.src = 'informacionProceso.do?<%=Defs.PARAMETRO_ID_PROCESO%>='+selector.options[selector.selectedIndex].value+'&<%=Defs.PARAMETRO_TIPO_PROCESO%>='+tipo;
				} else {
					var frameInfo = document.getElementById('informacion');
					frameInfo.src = '<html:rewrite page="/jsp/blank.html" />';
					return;
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
			<jsp:param name="ayuda" value='<%=request.getContextPath() + "/jsp/ayudas/" + locale + "/procesos/eliminar.html"%>' />
		</jsp:include>

		<form id=formulario action="" method=post>
			<input id=<%=Defs.PARAMETRO_ENTIDAD_SELECCIONADA%> type=hidden name=<%=Defs.PARAMETRO_ENTIDAD_SELECCIONADA%>>
			<input id=<%=Defs.PARAMETRO_ACCION_ENTIDAD%> type=hidden name=<%=Defs.PARAMETRO_ACCION_ENTIDAD%>>
		</form>

		<div id=contenedora align=center>
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
								<h1><bean:message key="entidades.borrar"/></h1>
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
								<logic:present name="<%=Defs.MENSAJE_ERROR%>">
									<table border=0 cellpadding=0 cellspacing=0 align=center>
										<tr>
											<td>
						                		<label class="gr_error"><bean:message key="<%=(String)request.getAttribute(Defs.MENSAJE_ERROR)%>"/></label>
						                	</td>
										</tr>
									</table>
								</logic:present>
								<logic:present name="<%=Defs.MENSAJE_INFORMATIVO%>">
									<table border=0 cellpadding=0 cellspacing=0 align=center>
										<tr>
											<td>
						                		<label class="gr_informativo"><bean:message key="<%=(String)request.getAttribute(Defs.MENSAJE_INFORMATIVO)%>"/></label>
						                	</td>
										</tr>
									</table>
								</logic:present>
								<div align="center">
									<form id=borrarForm action="<html:rewrite page="/borrarProceso.do" />" method="post">
										<input type="hidden" id="<%=Defs.PARAMETRO_ID_PROCESO%>" name="<%=Defs.PARAMETRO_ID_PROCESO%>" value=""/>
										<input type="hidden" id="<%=Defs.PARAMETRO_TIPO_PROCESO%>" name="<%=Defs.PARAMETRO_TIPO_PROCESO%>" value=""/>
										<table>
											<tr>
												<td colspan=2>
													<label class="gr_ext"><bean:message key="proceso.borrar.seleccione"/></label>
												</td>
											</tr>
											<tr><td><br /></td></tr>
											<tr>
												<td class="td-azul" style="border-right: 2px solid #fff; width: 350px;">
													<bean:message key="proceso.borrar.procesos_disponibles" />
												</td>
												<td class="td-azul" style="border-left: 2px solid #fff; width: 350px;">
													<bean:message key="proceso.borrar.informacion_proceso" />
												</td>
											</tr>
											<tr valign="top">
												<td>
													<table>
														<tr>
															<td>
																<label class="gr_ext"><bean:message key="proceso.borrar.exportaciones"/> </label>
																<select id="<%=Defs.PARAMETRO_MONITORIZACION_EXPORTACIONES%>" class="gr" onchange="javascript:mostrarInformacion('<%=Defs.EXPORTAR%>');">
																	<logic:notEmpty name="<%=Defs.PARAMETRO_MONITORIZACION_EXPORTACIONES%>">
																	<%for(int k=0; k<procesosExportacion.size(); k++){
																			Proceso proceso = (Proceso)procesosExportacion.get(k);%>
																		<option value="<%=proceso.getValor()%>"><%=proceso.getNombre()%></option>
																	<%}%>
																	</logic:notEmpty>
																	<logic:empty name="<%=Defs.PARAMETRO_MONITORIZACION_EXPORTACIONES%>">
																		<option value=""><bean:message key="proceso.borrar.no_exportaciones"/></option>
																	</logic:empty>
																</select>
															</td>
														</tr>
														<tr><td><br /></td></tr>
														<tr>
															<td>
																<label class="gr_ext"><bean:message key="proceso.borrar.importaciones"/> </label>
																<select id="<%=Defs.PARAMETRO_MONITORIZACION_IMPORTACIONES%>" class="gr" onchange="javascript:mostrarInformacion('<%=Defs.IMPORTAR%>');">
																	<logic:notEmpty name="<%=Defs.PARAMETRO_MONITORIZACION_IMPORTACIONES%>">
																	<%for(int k=0; k<procesosImportacion.size(); k++){
																			Proceso proceso = (Proceso)procesosImportacion.get(k);%>
																		<option value="<%=proceso.getValor()%>"><%=proceso.getNombre()%></option>
																	<%}%>
																	</logic:notEmpty>
																	<logic:empty name="<%=Defs.PARAMETRO_MONITORIZACION_IMPORTACIONES%>">
																		<option value=""><bean:message key="proceso.borrar.no_importaciones"/></option>
																	</logic:empty>
																</select>
															</td>
														</tr>
														<tr><td><br /></td></tr>
														<tr>
															<td>
																<label class="gr_ext"><bean:message key="proceso.borrar.acciones.multientidad"/> </label>
																<select id="<%=Defs.PARAMETRO_MONITORIZACION_ACCIONES_MULTIENTIDAD%>" class="gr" onchange="javascript:mostrarInformacion('<%=Defs.ACCION_MULTIENTIDAD%>');">
																	<logic:notEmpty name="<%=Defs.PARAMETRO_MONITORIZACION_ACCIONES_MULTIENTIDAD%>">
																	<%for(int k=0; k<procesosAccionesMultientidad.size(); k++){
																			Proceso proceso = (Proceso)procesosAccionesMultientidad.get(k);%>
																		<option value="<%=proceso.getValor()%>"><%=proceso.getNombre()%></option>
																	<%}%>
																	</logic:notEmpty>
																	<logic:empty name="<%=Defs.PARAMETRO_MONITORIZACION_ACCIONES_MULTIENTIDAD%>">
																		<option value=""><bean:message key="proceso.borrar.no_acciones_multientidad"/></option>
																	</logic:empty>
																</select>
															</td>
														</tr>
													</table>
												</td>
												<td>
													<table>
														<tr>
															<td>
																<iframe id=informacion name=informacion src="<html:rewrite page="/jsp/blank.html" />" style="position:relative; width: 100%;" frameborder=0></iframe>
															</td>
														</tr>
													</table>
												</td>
											</tr>
										</table>
										<input id=boton_eliminar type="button" value="<bean:message key="proceso.boton.eliminar" />" class="ok" onclick="javascript:enviar();" title="<bean:message key="proceso.boton.eliminar" />"/>
										<input id=boton_salir type="button" value="<bean:message key="boton.salir" />" class="ok" onclick="javascript:cancelar();" title="<bean:message key="boton.salir" />"/>
									</form>
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
	</body>
</html>