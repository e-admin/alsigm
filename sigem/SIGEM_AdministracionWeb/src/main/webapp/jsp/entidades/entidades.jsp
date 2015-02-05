<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>

<%@page import="ieci.tecdoc.sgm.admsistema.util.Defs"%>

<html>
	<head>
		<LINK href="css/estilos.css" type=text/css rel=stylesheet />

		<SCRIPT language=javascript>
			function realizarOperacion(idEntidad, accion) {
				var formulario = document.getElementById("formulario");
				formulario.action = 'accionesEntidad.do';
				var campo = document.getElementById("<%=Defs.PARAMETRO_ENTIDAD_SELECCIONADA%>");
				campo.value = idEntidad;
				campo = document.getElementById("<%=Defs.PARAMETRO_ACCION_ENTIDAD%>");
				campo.value = accion;
				formulario.submit();
			}

			function realizarOperacionEntidad(accion) {
				var radios = document.entidades.checkEntidad;
				if (radios != null) {
					if (radios.length) {
						for (var i=0; i < radios.length; i++) {
							if (radios[i].checked) {
								mostrarMensaje (false, '');
								return realizarOperacion(radios[i].value, accion);
							}
						}
					} else if (radios.checked) {
						mostrarMensaje (false, '');
						return realizarOperacion(radios.value, accion);
					}
				}

				ocultarMensajes();
				mostrarMensaje (true, "<bean:message key="mensaje.error.entidad.seleccione" />");
			}

			function realizarBusqueda() {
				var formulario = document.getElementById("busquedaBean");
				formulario.submit();
			}

			function ocultarMensajes(){
				if (document.getElementById('mensaje_error') != undefined) {
					document.getElementById('mensaje_error').style.visibility = 'hidden';
					document.getElementById('mensaje_error').style.position = 'absolute';
				}
				if (document.getElementById('mensaje_informativo') != undefined) {
					document.getElementById('mensaje_informativo').style.visibility = 'hidden';
					document.getElementById('mensaje_informativo').style.position = 'absolute';
				}
			}

			function mostrarMensaje(mostrar, mensaje) {
				var tabla = document.getElementById('tabla_mensajes_entidades');
				var msj = document.getElementById('mensajes_entidades');
				msj.innerHTML = mensaje;
				if (mostrar == true) {
					tabla.style.position = 'relative'; tabla.style.visibility = 'visible';
					msj.style.position = 'relative'; msj.style.visibility = 'visible';
				} else {
					tabla.style.position = 'absolute'; tabla.style.visibility = 'hidden';
					msj.style.position = 'absolute'; msj.style.visibility = 'hidden';
				}
			}
		</SCRIPT>
	</head>
	<body>
		<%String locale = (request.getLocale() != null) ? request.getLocale().getLanguage() : ""; if (locale == null || "".equals(locale)) locale = "es";%>
		<jsp:include flush="true" page="../cabecera.jsp">
			<jsp:param name="ayuda" value='<%=request.getContextPath() + "/jsp/ayudas/" + locale + "/entidades/entidades.html"%>' />
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
								<h1><bean:message key="entidades.configuradas"/></h1>
								<div class="col" align="right" style="height: 30px;">
									<table border="0" cellspacing="0" cellpadding="0">
       									<tr align=right>
						       				<td width="100%">&nbsp;</td>
						       				<td width="20%">
						       					<img src="img/ico_accionesmultientidad.png" style="cursor: pointer" onclick="javascript:realizarOperacion('', '<%=Defs.ACCION_ACCIONES_MULTIENTIDAD%>')" alt="<bean:message key="entidades.boton.acciones.multientidad"/>" title="<bean:message key="entidades.boton.acciones.multientidad"/>"/>
						       				</td>
						       				<td style="font-size: 130%;" valign=top>&nbsp;&nbsp;|&nbsp;&nbsp;</td>
						       				
						       				<td width="20%">
						       					<img src="img/ico_new.jpg" style="cursor: pointer" onclick="javascript:realizarOperacion('', '<%=Defs.ACCION_NUEVA_ENTIDAD%>')" alt="<bean:message key="entidades.boton.nueva"/>" title="<bean:message key="entidades.boton.nueva"/>"/>
						       				</td>
						       				<td style="font-size: 130%;" valign=top>&nbsp;&nbsp;|&nbsp;&nbsp;</td>
						       				<td width="20%">
						       					<img src="img/ico_monitorizar.jpg" style="cursor: pointer" onclick="javascript:realizarOperacion('', '<%=Defs.ACCION_MONITORIZAR_ENTIDAD%>')" alt="<bean:message key="entidades.boton.monitorizar"/>" title="<bean:message key="entidades.boton.monitorizar"/>"/>
						       				</td>
						       				<td style="font-size: 130%;" valign=top>&nbsp;&nbsp;|&nbsp;&nbsp;</td>
						       				<td width="20%">
						       					<img src="img/ico_delete_proc.jpg" style="cursor: pointer" onclick="javascript:realizarOperacion('', '<%=Defs.ACCION_BORRAR_PROCESO%>')" alt="<bean:message key="entidades.boton.borrar"/>" title="<bean:message key="entidades.boton.borrar"/>"/>
						       				</td>
						       				<td style="font-size: 130%;" valign=top>&nbsp;&nbsp;|&nbsp;&nbsp;</td>
						       				<td width="20%">
						       					<img src="img/ico_configuration.jpg" style="cursor: pointer" onclick="javascript:realizarOperacion('', '<%=Defs.ACCION_CONFIGURAR_SERVIDOR%>')" alt="<bean:message key="entidades.boton.configurar"/>" title="<bean:message key="entidades.boton.configurar"/>"/>
						       				</td>
						       				<td style="font-size: 130%;" valign=top>&nbsp;&nbsp;|&nbsp;&nbsp;</td>
						       				<html:form styleId="busquedaBean" action="/busquedaEntidades.do" method="post">
							       				<td width="20%">
	       											<input type="text" id="<%=Defs.PARAMETRO_BUSQUEDA%>" name="<%=Defs.PARAMETRO_BUSQUEDA%>"  />
						    					</td>
							       				<td width="20%">
							       					<img src="img/ico_search.jpg" style="cursor: pointer" onclick="javascript:realizarBusqueda()" alt="<bean:message key="entidades.boton.buscar"/>" title="<bean:message key="entidades.boton.buscar"/>"/>
							       				</td>
							       			</html:form>
       									</tr>
       								</table>
								</div>
								<div align="right" style="height: 30px; margin-top: 20px;">
									<table border="0" cellspacing="0" cellpadding="0">
       									<tr align=right>
						       				<td width="100%">&nbsp;</td>
						       				<td width="20%">
						       					<img src="img/ico_admin.jpg" style="cursor: pointer" onclick="javascript:realizarOperacionEntidad('<%=Defs.ACCION_ADMINISTRAR_ENTIDAD%>')" alt="<bean:message key="entidades.boton.administrar"/>" title="<bean:message key="entidades.boton.administrar"/>"/>
						       				</td>
						       				<td style="font-size: 130%;" valign=top>&nbsp;&nbsp;|&nbsp;&nbsp;</td>
						       				<td width="20%">
						       					<img src="img/ico_exportar.jpg" style="cursor: pointer" onclick="javascript:realizarOperacionEntidad('<%=Defs.ACCION_EXPORTAR_ENTIDAD%>')" alt="<bean:message key="entidades.boton.exportar"/>" title="<bean:message key="entidades.boton.exportar"/>"/>
						       				</td>
						       				<td style="font-size: 130%;" valign=top>&nbsp;&nbsp;|&nbsp;&nbsp;</td>
						       				<td width="20%">
						       					<img src="img/ico_clonar.jpg" style="cursor: pointer" onclick="javascript:realizarOperacionEntidad('<%=Defs.ACCION_CLONAR_ENTIDAD%>')" alt="<bean:message key="entidades.boton.clonar"/>" title="<bean:message key="entidades.boton.clonar"/>"/>
						       				</td>
						       				<td style="font-size: 130%;" valign=top>&nbsp;&nbsp;|&nbsp;&nbsp;</td>
						       				<td width="20%">
						       					<img src="img/ico_delete.jpg" style="cursor: pointer" onclick="javascript:realizarOperacionEntidad('<%=Defs.ACCION_SEGREGAR_ENTIDAD%>')" alt="<bean:message key="entidades.boton.segregar"/>" title="<bean:message key="entidades.boton.segregar"/>"/>
						       				</td>
       									</tr>
       								</table>
								</div>
								<br/>
								<logic:present name="<%=Defs.MENSAJE_ERROR%>">
									<table id=mensaje_error border=0 cellpadding=0 cellspacing=0 align=center style="padding-left: 12px; padding-bottom:22px;">
										<tr>
											<td colspan=2>
						                		<label class="gr_error"><bean:message key="<%=(String)request.getAttribute(Defs.MENSAJE_ERROR)%>"/></label>
						                	</td>
										</tr>
										<tr><td colspan=2><br/></td></tr>
									</table>
								</logic:present>
								<logic:present name="<%=Defs.MENSAJE_INFORMATIVO%>">
									<table id=mensaje_informativo border=0 cellpadding=0 cellspacing=0 align=center style="padding-left: 12px; padding-bottom:22px;">
										<tr>
											<td colspan=2>
						                		<label class="gr_informativo"><bean:message key="<%=(String)request.getAttribute(Defs.MENSAJE_INFORMATIVO)%>"/></label>
						                	</td>
										</tr>
										<tr><td colspan=2><br/></td></tr>
									</table>
								</logic:present>
		                		<table align="center" id="tabla_mensajes_entidades" border=0 cellpadding=0 cellspacing=0 style="padding-bottom:22px; position: absolute; visibility: hidden;">
									<tr>
										<td>
				    	            		<label id="mensajes_entidades" class="gr_error" style="visibility: hidden; position:absolute"></label>
					                	</td>
									</tr>
									<tr><td><br/></td></tr>
								</table>

								<form name="entidades">
								<display:table name="<%=Defs.LISTADO_ENTIDADES%>"
									uid="entidad"
									pagesize="10"
									requestURI="listadoEntidades.do"
									export="false"
									class="tablaListado"
									sort="list"
									style="width:96%;">
									<display:column  style="width: 20px; height: 100%" media="html" titleKey="cabeceraTabla.entidades.panelBotones" sortable="false" headerClass="cabeceraTabla" >
										<table width="20px" height="100%">
											<tr height="100%">
												<td width="20px" height="100%">
													<input type="radio" id="checkEntidad" name="checkEntidad" value='<bean:write name="entidad" property="identificador" />' style="border: 0px; width: 20px"/>
												</td>
											</tr>
										</table>
									</display:column>
									<display:column  style="height: 100%" media="html" titleKey="cabeceraTabla.entidades.nombreEntidad" sortable="true" headerClass="cabeceraTabla" >
										<acronym title="<bean:message key="entidades.modificar" />">
											<table width="100%" height="100%" onclick="javascript:realizarOperacion('<bean:write name="entidad" property="identificador" />', '<%=Defs.ACCION_MODIFICAR_ENTIDAD%>')" style="cursor: pointer">
												<tr height="100%">
													<td width="100%" height="100%">
														<bean:write name="entidad" property="nombreLargo" />
													</td>
												</tr>
											</table>
										</acronym>
									</display:column>
									<display:column  style="width: 150px; height: 100%" media="html" titleKey="cabeceraTabla.entidades.idEntidad" sortable="true" headerClass="cabeceraTabla">
										<acronym title="<bean:message key="entidades.modificar"/>">
											<table width="100%" height="100%" onclick="javascript:realizarOperacion('<bean:write name="entidad" property="identificador" />', '<%=Defs.ACCION_MODIFICAR_ENTIDAD%>')" style="cursor: pointer">
												<tr height="100%">
													<td width="100%" height="100%">
														<bean:write name="entidad" property="identificador" />
													</td>
												</tr>
											</table>
										</acronym>
									</display:column>
								</display:table>
								</form>
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