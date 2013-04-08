<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>

<%@page import="ieci.tecdoc.sgm.admsistema.util.Defs"%>
<%@page import="ieci.tecdoc.sgm.core.services.administracion.Aplicacion"%>
<%@page import="ieci.tecdoc.sgm.core.services.administracion.Perfil"%>
<%@page import="ieci.tecdoc.sgm.core.services.entidades.Entidad"%>

<%
Entidad[] entidadesCargadas = (Entidad[])request.getAttribute(Defs.PARAMETRO_ADMINISTRADOR_ENTIDADES_CARGADAS);
Perfil[][] permisosCargadas = (Perfil[][])request.getAttribute(Defs.PARAMETRO_ADMINISTRADOR_PERMISOS_ENTIDADES_CARGADOS);
boolean hayDifs = ((Boolean)request.getAttribute(Defs.PARAMETRO_ADMINISTRADOR_SIN_PERMISOS)).booleanValue();
boolean modificar = !((Boolean)request.getAttribute(Defs.PARAMETRO_ADMINISTRADOR_MODIFICAR)).booleanValue();
%>

<%@page import="ieci.tecdoc.sgm.admsistema.form.UsuarioForm"%>
<html>
	<head>
		<LINK href="css/estilos.css" type=text/css rel=stylesheet />
		
		<script language=javascript>
			var _libre = "false";
			var _username;
			var _entidades;
			var _permisos;
			var _descapps;
			var _hayDifs = <%=hayDifs%>;
			
			_entidades = new Array();
			_permisos = new Array();
			_descapps = new Array();
			
			<%for(int cents=0; cents < entidadesCargadas.length; cents++) {%>
				_entidades[<%=cents%>] = new Array('<%=entidadesCargadas[cents].getIdentificador()%>', '<%=entidadesCargadas[cents].getNombreLargo()%>');
				_permisos[<%=cents%>] = new Array();
			<%for(int cperms=0; cperms<permisosCargadas[cents].length; cperms++) {%>
					_permisos[<%=cents%>][<%=cperms%>] = '<%=permisosCargadas[cents][cperms].getIdAplicacion()%>';
			<%}}%>
			
			<%
			Aplicacion[] oApps = (Aplicacion[])session.getAttribute(Defs.PARAMETRO_ADMINISTRADOR_APLICACIONES_A_ELEGIR);
			for(int capps=0; capps<oApps.length; capps++){
			%>
			_descapps[<%=oApps[capps].getIdentificador()%>] = "<bean:message key='<%="entidad.administrar.descripcion_corta." + oApps[capps].getIdentificador()%>' />";
			<%}%>
			function getEntidades() {return _entidades; }
			function getPermisos() {return _permisos; }
			function getDescripciones() {return _descapps; }

			function enviar() {
				var p1 = document.getElementById('<%=Defs.PARAMETRO_ADMINISTRADOR_PASSWORD%>').value;
				var p2 = document.getElementById('<%=Defs.PARAMETRO_ADMINISTRADOR_PASSWORD_NUEVO%>').value;
				var p3 = document.getElementById('<%=Defs.PARAMETRO_ADMINISTRADOR_PASSWORD_NUEVO_REPETIDO%>').value;
				var error = true;
				
				if (document.getElementById('<%="_"+Defs.PARAMETRO_ADMINISTRADOR_NOMBRE%>').value == '' ||
					document.getElementById('<%="_"+Defs.PARAMETRO_ADMINISTRADOR_APELLIDOS%>').value == '' ||
					document.getElementById('<%="_"+Defs.PARAMETRO_ADMINISTRADOR_USERNAME%>').value == '') {
					mostrarMensaje (true, "<bean:message key="mensaje.error.usuario.campos_obligatorios" />");
				} else if (p1 != '' || p2 != '' || p3 != '') { //si hay algún campo relleno
					if (p1 != '' && p2 != '' && p3 != '') { //si están todos rellenos
						if (p1 == p2) { //contraseña antigua igual a la nueva
							mostrarMensaje (true, "<bean:message key="mensaje.error.usuario.password_antiguo_igual_nuevo" />");
						} else if (p2 != p3) { //contraseña nueva no coincide con la repetición
							mostrarMensaje (true, "<bean:message key="mensaje.error.usuario.password_nuevo_distintos" />");
						} else { //esta bien
							error = false;						
						}
					} else {
						mostrarMensaje (true, "<bean:message key="mensaje.error.usuario.password_rellene_todos" />");
					}
				}else{
					error = false;	
				}
						
				if ((error == false) && document.getElementById('<%=Defs.PARAMETRO_ADMINISTRADOR_PASSWORD_NUEVO%>').value != 
					document.getElementById('<%=Defs.PARAMETRO_ADMINISTRADOR_PASSWORD_NUEVO_REPETIDO%>').value) {
					mostrarMensaje (true, "<bean:message key="mensaje.error.usuario.password_nuevo_distintos" />");
				} else if ((error == false) && (_entidades == undefined || _entidades.length == 0) && (_hayDifs == false)) {
					mostrarMensaje (true, "<bean:message key="mensaje.error.usuario.asignar_permisos" />");
				} else if (error == false){
					mostrarMensaje (false, '');
					document.getElementById('<%=Defs.PARAMETRO_ADMINISTRADOR_USERNAME%>').value = document.getElementById('<%="_"+Defs.PARAMETRO_ADMINISTRADOR_USERNAME%>').value;
					document.getElementById('<%=Defs.PARAMETRO_ADMINISTRADOR_NOMBRE%>').value = document.getElementById('<%="_"+Defs.PARAMETRO_ADMINISTRADOR_NOMBRE%>').value;
					document.getElementById('<%=Defs.PARAMETRO_ADMINISTRADOR_APELLIDOS%>').value = document.getElementById('<%="_"+Defs.PARAMETRO_ADMINISTRADOR_APELLIDOS%>').value;
					document.getElementById('<%=Defs.PARAMETRO_ADMINISTRADOR_ENTIDADES %>').value = cadenaEntidades();
					document.getElementById('<%=Defs.PARAMETRO_ADMINISTRADOR_PERMISOS_ENTIDADES %>').value = cadenaPermisos();
					document.getElementById('usuarioBean').submit();
				}
			}

			
			function cadenaEntidades () {
				if (_entidades != undefined) {
					var i;
					var cadena = '';
					for(i=0; i<_entidades.length; i++) {
						cadena = cadena + _entidades[i][0];
						if (i+1 != _entidades.length) 
							cadena = cadena + ',';
					}
					return cadena;
				}
			}
			
			function cadenaPermisos () {
				if (_permisos != undefined) {
					var i, j;
					var cadena = '';
					for(i=0; i<_permisos.length; i++) {
						j=0;
						cadena = cadena + '[';
						for(j=0; j<_permisos[i].length; j++) {
							cadena = cadena + _permisos[i][j];
							if (j+1 != _permisos[i].length) 
								cadena = cadena + ',';
						}
						cadena = cadena + ']';
					}
					return cadena;
				}
			}
			
			function cancelar() {
				var formulario = document.getElementById('usuarioBean');
				formulario.action = 'listadoUsuarios.do';
				formulario.submit();
			}
			
			function realizarBusqueda() {
				var formulario = document.getElementById("busquedaBean");
				formulario.submit();
			}
			
			function comprobarUsuario(){
				var nombre = document.getElementById('<%=Defs.PARAMETRO_ADMINISTRADOR_USERNAME%>').value;
				if (nombre != '')
					document.getElementById('frameLibre').src = 'comprobarUsuario.do?<%=Defs.PARAMETRO_ADMINISTRADOR_USERNAME%>=' + nombre;
			}
			
			function usuarioLibre(libre) {
				var imagen = document.getElementById('ico_libre');
				_libre = libre;
				mostrarMensaje (false, '');
				if (libre == "true") {
					activarBoton('boton_aceptar');
					imagen.src = "img/correcto.gif";
					imagen.alt = "<bean:message key="usuario.nuevo.username.libre_si" />";
				} else if (libre == "false") {
					desactivarBoton('boton_aceptar');
					imagen.src = "img/incorrecto.gif";
					imagen.alt = "<bean:message key="usuario.nuevo.username.libre_no" />";
				} else if (libre == "comprobar") {
					desactivarBoton('boton_aceptar');
					imagen.src = "img/help.gif";
					imagen.alt = "<bean:message key="usuario.nuevo.username.libre" />";
				}
			}

			function activarBoton(boton) {
				document.getElementById(boton).disabled = false;
				document.getElementById(boton).style.background = 'url(img/fondo_boton.jpg)';
			}
			
			function desactivarBoton(boton) {
				document.getElementById(boton).disabled = true;
				document.getElementById(boton).style.background = 'url(img/fondo_boton_desactivado.jpg)';
			}

			function getEntidadYaAsociadas() {
				if (_entidades != undefined){
					if (_entidades.length > 0) {
						var i;
						var ents = '';
						for(i=0; i<_entidades.length; i++) {
							ents = ents + _entidades[i][0] + ',';
						}
						return ents;
					}
				} 
				return '';
			}
			
			function getEntidadMarcadas() {
				if (_entidades != undefined){
					if (_entidades.length > 0) {
						var i;
						var ents = '';
						for(i=0; i<_entidades.length; i++) {
							if (document.getElementById('frameEntidades').contentWindow.getCheck(i).checked == true) {
							ents = ents + _entidades[i][0] + ',';
							}
						}
						return ents;
					}
				} 
				return '';
			}
			
			function gestionarPermisos(accion) {
				mostrarMensaje (false, '');
				if (accion == '<%=Defs.ACCION_NUEVO_ASOCIAR_ENTIDAD%>') {
					var frm = document.getElementById('framePermisosEntidades');
					frm.src = 'asociarEntidades.do?<%=Defs.PARAMETRO_ENTIDADES_YA_ASOCIADAS%>=' + getEntidadYaAsociadas() + '&<%=Defs.PARAMETRO_TIPO_ACCION_ENTIDADES%>=<%=Defs.ACCION_NUEVO_ASOCIAR_ENTIDAD%>';
				} else if (accion == '<%=Defs.ACCION_NUEVO_MODIFICAR_ENTIDAD%>') {
					if (_entidades != undefined && _entidades.length > 0) {
						var i;
						var cambios = false;
						for(i=0; i<_entidades.length; i++) {
							if (document.getElementById('frameEntidades').contentWindow.getCheck(i).checked == true) {
								cambios = true;
								break;
							}
						}
						if (cambios == true) {
							var frm = document.getElementById('framePermisosEntidades');
							frm.src = 'asociarEntidades.do?<%=Defs.PARAMETRO_TIPO_ACCION_ENTIDADES%>=<%=Defs.ACCION_NUEVO_MODIFICAR_ENTIDAD%>';
						} else {
							mostrarMensaje (true, "<bean:message key="mensaje.error.usuario.selecionar_para_modificar" />");
						}
					} else {
						mostrarMensaje (true, "<bean:message key="mensaje.error.usuario.modificar_no_entidades" />");
					}
				} else if (accion == '<%=Defs.ACCION_NUEVO_ELIMINAR_ENTIDAD%>') {
					if (_entidades != undefined && _entidades.length > 0) {
						var i;
						var cambios = false;
						for(i=_entidades.length-1; i>=0; i--) {
							if (document.getElementById('frameEntidades').contentWindow.getCheck(i).checked == true) {
								remove(_entidades, i);
								remove(_permisos, i);
								cambios = true;
							}
						}
						if (cambios == true) {
							document.getElementById('frameEntidades').src = 'mostrarEntidadesAsociadas.do';
						} else {
							mostrarMensaje (true, "<bean:message key="mensaje.error.usuario.selecionar_para_eliminar" />");
						}
					} else {
						mostrarMensaje (true, "<bean:message key="mensaje.error.usuario.eliminar_no_entidades" />");
					}
				} else if (accion == '<%=Defs.ACCION_NUEVO_SELECCIONAR_TODAS%>') {
					if (_entidades != undefined) {
						var i;
						for(i=0; i<_entidades.length; i++) {
							document.getElementById('frameEntidades').contentWindow.getCheck(i).checked = true;
						}
					}
				} else if (accion == '<%=Defs.ACCION_NUEVO_DESELECCIONAR_TODAS%>') {
					if (_entidades != undefined) {
						var i;
						for(i=0; i<_entidades.length; i++) {
							document.getElementById('frameEntidades').contentWindow.getCheck(i).checked = false;
						}
					}
				}
			}
			
			function remove(datos, posicion) {
				if (datos != undefined) {
					if (posicion < datos.length) {
						var i=0;
						for(i=posicion; i<datos.length-1; i++)
							datos[i] = datos[i+1];
						datos.length--; 
					}
				}
			}
			
			function comprobarCambioUsuario(tipo) {
				var username  = document.getElementById('<%=Defs.PARAMETRO_ADMINISTRADOR_USERNAME%>').value;
				if (username == '') {
					desactivarBoton('boton_aceptar');
					document.getElementById('ico_libre').src = 'img/help.gif';
					document.getElementById('ico_libre').alt = "<bean:message key="usuario.nuevo.username.libre" />";
				} else if (username != _username) {
					comprobarUsuario();
					_username = username;
				}
			}
			
			function mostrarMensaje(mostrar, mensaje) {
				var tabla = document.getElementById('tabla_mensajes_usuario');
				var msj = document.getElementById('mensajes_usuario');
				msj.innerHTML = mensaje;
				if (mostrar == true) {
					tabla.style.position = 'relative'; tabla.style.visibility = 'visible';
					msj.style.position = 'relative'; msj.style.visibility = 'visible';
				} else {
					tabla.style.position = 'absolute'; tabla.style.visibility = 'hidden';
					msj.style.position = 'absolute'; msj.style.visibility = 'hidden';
				}
				document.getElementById('frameEntidades').style.width = '301px';
				document.getElementById('frameEntidades').style.width = '300px';
			}
		</script>
	</head>
	<body>
		<%String locale = (request.getLocale() != null) ? request.getLocale().getLanguage() : ""; if (locale == null || "".equals(locale)) locale = "es";%>
		<jsp:include flush="true" page="../cabecera.jsp">
			<jsp:param name="ayuda" value='<%=request.getContextPath() + "/jsp/ayudas/" + locale + "/usuarios/modificar.html"%>' />
		</jsp:include>

		<div id=contenedora align=center>
			<div class=cuerpo style="width: 80%">
				<div class=cuerporight>
					<div class=cuerpomid>
						<h1><bean:message key="entidades.titulo"/></h1>
						<div class=submenu3>
							<ul>
  								<li class=submen1off>
  									<img src="img/subme3_off.gif">
  									<a href="listadoEntidades.do">
  										<bean:message key="tab.entidades"/>
  									</a>
  									<img src="img/subme3_of_on.gif">
  								</li>
								<li class="submen1on">
									<a href="listadoUsuarios.do" class=submen1on_a>
										<bean:message key="tab.usuarios"/>
									</a>
									<img src="img/subme3_on_0.gif">
		  						</li>       
  							</ul>	
  						</div>
						<div id=cuadro class=cuadro style="visibility: visible">
							<div>
								<h1><bean:message key="usuarios.nuevo"/></h1>
								<div class="col" align="right" style="height: 30px;">
									<table border="0" cellspacing="0" cellpadding="0">
       									<tr align=right>
						       				<td width="100%">&nbsp;</td>    
											<html:form styleId="busquedaBean" action="/busquedaUsuarios.do" method="post">
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
								<html:form styleId="usuarioBean" action="/modificarUsuario.do" method="post">
									<html:hidden property="<%=Defs.PARAMETRO_ADMINISTRADOR_ENTIDADES %>" styleId="<%=Defs.PARAMETRO_ADMINISTRADOR_ENTIDADES %>" value=""/>
									<html:hidden property="<%=Defs.PARAMETRO_ADMINISTRADOR_PERMISOS_ENTIDADES %>" styleId="<%=Defs.PARAMETRO_ADMINISTRADOR_PERMISOS_ENTIDADES %>" value=""/>
									<html:hidden property="<%=Defs.PARAMETRO_ADMINISTRADOR_USERNAME %>" styleId="<%=Defs.PARAMETRO_ADMINISTRADOR_USERNAME %>" value=""/>
									<html:hidden property="<%=Defs.PARAMETRO_ADMINISTRADOR_NOMBRE %>" styleId="<%=Defs.PARAMETRO_ADMINISTRADOR_NOMBRE %>" value=""/>
									<html:hidden property="<%=Defs.PARAMETRO_ADMINISTRADOR_APELLIDOS %>" styleId="<%=Defs.PARAMETRO_ADMINISTRADOR_APELLIDOS %>" value=""/>
									<table id=tabla_cuadro border=0 cellpadding=0 cellspacing=0 align=center style="padding-left: 12px; padding-bottom:22px;">
										<tr>
											<td colspan=2>
						                		<table id="tabla_mensajes_usuario" border=0 cellpadding=0 cellspacing=0 style="padding-bottom:22px; position: absolute; visibility: hidden;">
													<tr>
														<td>
									                		<label id="mensajes_usuario" class="gr_error" style="visibility: hidden; position:absolute"></label>
									                	</td>
													</tr>
													<tr><td><br/></td></tr>
												</table>
						                	</td>
										</tr>
										<tr>
											<td class="td-azul" style="border-right: 2px solid #fff">
												<bean:message key="usuario.nuevo.datos_usuario" />
											</td>
											<td class="td-azul" style="border-left: 2px solid #fff">
												<bean:message key="usuario.nuevo.gestionar_permisos" />
											</td>
										</tr>
										<tr valign="top">
											<td>
												<table>
													<tr>
														<td>
															<label for="<%=Defs.PARAMETRO_ADMINISTRADOR_USERNAME%>" class="gr_ext"><bean:message key="usuario.nuevo.username" /></label>
														</td>
														<td>
									                		<html:text property="<%=Defs.PARAMETRO_ADMINISTRADOR_USERNAME%>" styleId='<%="_"+Defs.PARAMETRO_ADMINISTRADOR_USERNAME%>' maxlength="15" tabindex="1" disabled="true"></html:text>
									                	</td>
													</tr>
													<tr>
														<td>
															<label for="<%=Defs.PARAMETRO_ADMINISTRADOR_NOMBRE%>" class="gr_ext"><bean:message key="usuario.nuevo.nombre" /></label>
														</td>
														<td>
									                		<html:text property="<%=Defs.PARAMETRO_ADMINISTRADOR_NOMBRE%>" styleId='<%="_"+Defs.PARAMETRO_ADMINISTRADOR_NOMBRE%>' maxlength="50" tabindex="2"></html:text>
									                	</td>
													</tr>
													<tr>
														<td>
															<label for="<%=Defs.PARAMETRO_ADMINISTRADOR_APELLIDOS%>" class="gr_ext"><bean:message key="usuario.nuevo.apellidos" /></label>
														</td>
														<td>
									                		<html:text property="<%=Defs.PARAMETRO_ADMINISTRADOR_APELLIDOS%>" styleId='<%="_"+Defs.PARAMETRO_ADMINISTRADOR_APELLIDOS%>' maxlength="50" tabindex="3"></html:text>
									                	</td>
													</tr>
													<tr>
														<td>
															<label for="<%=Defs.PARAMETRO_ADMINISTRADOR_PASSWORD%>" class="gr_ext"><bean:message key="usuario.modificar.password_antiguo" /></label>
														</td>
														<td>
									                		<html:password property="<%=Defs.PARAMETRO_ADMINISTRADOR_PASSWORD%>" styleId="<%=Defs.PARAMETRO_ADMINISTRADOR_PASSWORD%>" maxlength="20" tabindex="4"></html:password>
									                	</td>
													</tr>
													<tr>
														<td>
															<label for="<%=Defs.PARAMETRO_ADMINISTRADOR_PASSWORD_REPETIDO%>" class="gr_ext"><bean:message key="usuario.modificar.password_nuevo" /></label>
														</td>
														<td>
									                		<html:password property="<%=Defs.PARAMETRO_ADMINISTRADOR_PASSWORD_NUEVO%>" styleId="<%=Defs.PARAMETRO_ADMINISTRADOR_PASSWORD_NUEVO%>" maxlength="20" tabindex="5"></html:password>
									                	</td>
													</tr>
													<tr>
														<td>
															<label for="<%=Defs.PARAMETRO_ADMINISTRADOR_PASSWORD_NUEVO_REPETIDO%>" class="gr_ext"><bean:message key="usuario.modificar.password_nuevo_repetido" /></label>
														</td>
														<td>
									                		<html:password property="<%=Defs.PARAMETRO_ADMINISTRADOR_PASSWORD_NUEVO_REPETIDO%>" styleId="<%=Defs.PARAMETRO_ADMINISTRADOR_PASSWORD_NUEVO_REPETIDO%>" maxlength="20" tabindex="5"></html:password>
									                	</td>
													</tr>
												</table>
											</td>
											<td>
												<table>
													<tr valign="top">
														<td height="300px" width="300px" class=permisos>
															<iframe src="mostrarEntidadesAsociadas.do" class="frame_barra" id=frameEntidades frameborder="0" style="position:relative; width: 300px; height: 300px">
															</iframe>
														</td>
														<td width="25px">
															<img id=ico_new src="img/ico_new.jpg" style="cursor: pointer;" alt="<bean:message key="usuario.nuevo.nuevos_permisos" />" title="<bean:message key="usuario.nuevo.nuevos_permisos" />" tabindex="7" onclick="javascript:gestionarPermisos('<%=Defs.ACCION_NUEVO_ASOCIAR_ENTIDAD%>');"/>
															<img id=ico_edit src="img/ico_edit.jpg" style="cursor: pointer;" alt="<bean:message key="usuario.nuevo.modificar_permisos" />" title="<bean:message key="usuario.nuevo.modificar_permisos" />" tabindex="8" onclick="javascript:gestionarPermisos('<%=Defs.ACCION_NUEVO_MODIFICAR_ENTIDAD%>');"/>
															<img id=ico_delete src="img/ico_delete.jpg" style="cursor: pointer;" alt="<bean:message key="usuario.nuevo.eliminar_permisos" />" title="<bean:message key="usuario.nuevo.eliminar_permisos" />" tabindex="8" onclick="javascript:gestionarPermisos('<%=Defs.ACCION_NUEVO_ELIMINAR_ENTIDAD%>');"/>
															<img id=ico_check src="img/ico_check.jpg" style="cursor: pointer;" alt="<bean:message key="usuario.nuevo.marcar_entidades" />" title="<bean:message key="usuario.nuevo.marcar_entidades" />" tabindex="9" onclick="javascript:gestionarPermisos('<%=Defs.ACCION_NUEVO_SELECCIONAR_TODAS%>');"/>
															<img id=ico_uncheck src="img/ico_uncheck.jpg" style="cursor: pointer;" alt="<bean:message key="usuario.nuevo.desmarcar_entidades" />" title="<bean:message key="usuario.nuevo.desmarcar_entidades" />" tabindex="10"  onclick="javascript:gestionarPermisos('<%=Defs.ACCION_NUEVO_DESELECCIONAR_TODAS%>');"/>
														</td>
													</tr>
												</table>
											</td>
										</tr>
										<tr><td colspan=2><br/></td></tr>
										<tr align=center>
											<td colspan=2>
												<input id="boton_aceptar" onclick="javascript:enviar()" type=button value="<bean:message key="boton.aceptar"/>" class="ok" tabindex="11" title="<bean:message key="boton.aceptar"/>" /> 
												<input id="boton_cancelar" onclick="javascript:cancelar()" type=button value="<bean:message key="boton.cancelar"/>" class="ok" tabindex="12" title="<bean:message key="boton.cancelar"/>"/> 
											</td>
										</tr>
									</table>
								</html:form>
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
		<iframe id=frameLibre src="<html:rewrite page="/jsp/blank.html" />" style="position:absolute; top: 0px;visibility: hidden; width: 0px; height: 0px"></iframe>
		<iframe id=framePermisosEntidades class=permisos frameborder="0" src="<html:rewrite page="/jsp/blank.html" />" style="position:absolute; visibility: hidden; width: 60%; left: 50%; margin-left: -30%; height: 420px; top: 50%; margin-top: -200px;"></iframe>
		

	</body>
</html>