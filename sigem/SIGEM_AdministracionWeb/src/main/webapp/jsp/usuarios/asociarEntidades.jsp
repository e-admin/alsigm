<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>

<%@page import="ieci.tecdoc.sgm.admsistema.util.Defs"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="ieci.tecdoc.sgm.core.services.entidades.Entidad"%>
<%@page import="ieci.tecdoc.sgm.core.services.administracion.Aplicacion"%>

<%
Aplicacion[] aplicaciones = (Aplicacion[])session.getAttribute(Defs.PARAMETRO_ADMINISTRADOR_APLICACIONES_A_ELEGIR);
%>

<html>
	<head>
		<LINK href="css/estilos.css" type=text/css rel=stylesheet />
		
		<SCRIPT language=javascript>
			function checkEntidades(marcar) {
				var i=0;
				while (document.getElementById('posiblesEntidades['+i+']') != undefined) {
					document.getElementById('posiblesEntidades['+i+']').checked = marcar;
					i++;
				}
			}
			
			function checkAplicaciones(marcar) {
				var i=0;
				while (document.getElementById('posiblesAplicaciones['+i+']') != undefined) {
					document.getElementById('posiblesAplicaciones['+i+']').checked = marcar;
					i++;
				}
			}

			function enviarInsertar() {
				if (algunaMarcada('posiblesEntidades')) {
					if (algunaMarcada('posiblesAplicaciones')) {
						desactivarMensaje('seleccione_entidad');
						desactivarMensaje('seleccione_aplicacion');
						var ents = parent.getEntidades();
						var permisos = parent.getPermisos();
						var apps = getArrayAplicaciones();
						var i=0, j=0, cant=0;
						if (ents != undefined) cant = ents.length;
						else cant = 0;
						while (document.getElementById('posiblesEntidades['+i+']') != undefined) {
							if (document.getElementById('posiblesEntidades['+i+']').checked == true) {
								ents[cant+j] = new Array(document.getElementById('posiblesEntidades['+i+']').value, document.getElementById('nombreEntidad['+i+']').innerHTML);
								permisos[cant+j] = apps;
								j++;
							}
							i++;
						}
						parent.document.getElementById('tabla_cuadro').disabled = false;
						parent.document.getElementById('tabla_cuadro').style.visibility = 'visible';
						parent.document.getElementById('framePermisosEntidades').style.visibility = 'hidden';
						
						parent.document.getElementById('frameEntidades').src = 'mostrarEntidadesAsociadas.do';
					} else {
						desactivarMensaje('seleccione_entidad');
						activarMensaje('seleccione_aplicacion');
					}
				} else {
					desactivarMensaje('seleccione_aplicacion');
					activarMensaje('seleccione_entidad');
				}
			}
			
			
			function enviarModificar() {
				if (algunaMarcada('posiblesAplicaciones')) {
					desactivarMensaje('seleccione_aplicacion');
					var ents = parent.getEntidades();
					var perms = parent.getPermisos();
					var i, j, k;
					for(i=0; i<ents.length; i++) {
						if (parent.document.getElementById('frameEntidades').contentWindow.getCheck(i).checked == true) {
							perms[i] = new Array();
							j=0; k=0;
							while (document.getElementById('posiblesAplicaciones['+j+']') != undefined) {
								if (document.getElementById('posiblesAplicaciones['+j+']').checked == true) {
									perms[i][k] = document.getElementById('posiblesAplicaciones['+j+']').value;
									k++;
								}
								j++;
							}
						}
					}
					parent.document.getElementById('tabla_cuadro').disabled = false;
					parent.document.getElementById('tabla_cuadro').style.visibility = 'visible';
					parent.document.getElementById('framePermisosEntidades').style.visibility = 'hidden';
						
					parent.document.getElementById('frameEntidades').src = 'mostrarEntidadesAsociadas.do';
				} else {
					activarMensaje('seleccione_aplicacion');
				}
			}
			
			
			function activarMensaje(mensaje) {
				var _tabla = document.getElementById('mensajes');
				_tabla.style.position = 'relative';
				_tabla.style.visibility = 'visible';
				var _label = document.getElementById(mensaje);
				_label.style.visibility = 'visible';
				_label.style.position = 'relative';
			}
			
			function desactivarMensaje(mensaje) {
				var _tabla = document.getElementById('mensajes');
				_tabla.style.position = 'absolute';
				_tabla.style.visibility = 'hidden';
				var _label = document.getElementById(mensaje);
				_label.style.visibility = 'hidden';
				_label.style.position = 'absolute';
			}
			
			function cancelar() {
				parent.document.getElementById('tabla_cuadro').disabled = false;
				parent.document.getElementById('tabla_cuadro').style.visibility = 'visible';
				parent.document.getElementById('framePermisosEntidades').style.visibility = 'hidden';
			}
			
			function getArrayAplicaciones() {
				var apps = new Array();
				var i=0, j=0;
				while (document.getElementById('posiblesAplicaciones['+i+']') != undefined) {
					if (document.getElementById('posiblesAplicaciones['+i+']').checked == true) {
						apps[j] = document.getElementById('posiblesAplicaciones['+i+']').value;
						j++;
					}
					i++;
				}
				return apps
			}
			
			function algunaMarcada(tipo) {
				var i=0;
				while (document.getElementById(tipo+'['+i+']') != undefined) {
					if (document.getElementById(tipo+'['+i+']').checked == true)
						return true;
					i++;
				}
				return false;
			}
			
			function mostrarVentana() {
				parent.document.getElementById('framePermisosEntidades').style.visibility = 'visible';
				parent.document.getElementById('tabla_cuadro').disabled = true;
				parent.document.getElementById('tabla_cuadro').style.visibility = 'hidden';
			}
			
			var marcados = 0;
			var leyenda = false;
			
			function cargarEntidades() {
				var ents = parent.getEntidades();
				var _tabla = document.getElementById('tabla_entidades');
				var i, j=0;
				for(i=0; i<ents.length; i++) {
					if (parent.document.getElementById('frameEntidades').contentWindow.getCheck(i).checked == true) {
						marcados++;
						var _tbody = document.createElement('TBODY');
						var _tr = document.createElement('TR');
						var _td1 = document.createElement('TD');
						var _td2 = document.createElement('TD');
						var _text = document.createTextNode(ents[i][1]);
						var cadena = 'mostrarAplicaciones('+i+')';
						_tr.onclick = new Function(cadena);
						cadena = 'ocultarAplicaciones('+i+')';
						_tr.onmouseout = new Function(cadena);
						_td2.appendChild(_text);
						_td1.setAttribute('width', '26px');
						_tr.appendChild(_td1);
						_tr.appendChild(_td2);
						if (j%2==0)
							_tr.setAttribute('className', 'odd_list');
						else _tr.setAttribute('className', 'even_list');
						_tbody.appendChild(_tr);
						_tabla.appendChild(_tbody);
						j++;
					}
				}
			}
			
			function cargarAplicaciones() {
				var perm = parent.getPermisos();
				var ents = parent.getEntidades();
				var marc = new Array();
				<%for(int k=0; k<aplicaciones.length; k++){%>
				marc[<%=aplicaciones[k].getIdentificador()%>] = 0;
				<%}%>
				var i, j;
				for(i=0; i<perm.length; i++) {
					if (parent.document.getElementById('frameEntidades').contentWindow.getCheck(i).checked == true) {
						for(j=0; j<perm[i].length; j++) {
							marc[perm[i][j]]++;
						}
					}
				}
				i=0;
				while(document.getElementById('posiblesAplicaciones['+i+']') != undefined) {
					var h = document.getElementById('posiblesAplicaciones['+i+']').value;
					if (marc[h] > 0) {
						document.getElementById('posiblesAplicaciones['+i+']').checked = true;
						if (marcados != marc[h]) {
							document.getElementById('labelAplicaciones['+i+']').className = 'gr_small_red';
							leyenda = true;
						}
					}
					i++;
				}
			}
			
			function cargarLeyenda() { 
				if (leyenda == true) {
					document.getElementById('tr_leyenda').style.visibility = 'visible';
				}
			}
			
			function mostrarAplicaciones(posicion) {
				var _apps = document.getElementById('aplicaciones');
				var _desc = parent.getDescripciones();
				var _li = document.createElement('LI');
				var i;
				var permisos_i = parent.getPermisos()[posicion];
				var _list = document.getElementById('listado_aplicaciones');
				if (_list != undefined)
					_apps.removeChild(_list);				
				var _ul = document.createElement('UL');
				_ul.id = 'listado_aplicaciones';
				for(i=0; i<	permisos_i.length; i++){
					var _li = document.createElement('LI');
					var _text = document.createTextNode(_desc[permisos_i[i]]);
					_li.appendChild(_text);
					_ul.appendChild(_li);
				}
				_apps.appendChild(_ul);
				document.getElementById('aplicaciones').style.visibility = 'visible';
			}
			
			function ocultarAplicaciones(posicion) {
				document.getElementById('aplicaciones').style.visibility = 'hidden';
			}
		</SCRIPT>
	</head>
	<body class="frame_barra_sin" onload="javascript:mostrarVentana();">
		<form id="formulario_entidades_asignadas">
			<table width="80%" border=0 align=center>
				<tr id=mensajes align=center style="margin-top: 5px; visibility: hidden; position:absolute" height="30px">
					<logic:equal name="<%=Defs.PARAMETRO_TIPO_ACCION_ENTIDADES%>" value="<%=Defs.ACCION_NUEVO_ASOCIAR_ENTIDAD%>">
					<td colspan=4>
						<label id="seleccione_entidad" class="gr_error_frame" style="visibility:hidden; position:absolute; font-size: 80%"><bean:message key="usuario.nuevo.seleccione_entidad" /></label>
						<label id="seleccione_aplicacion" class="gr_error_frame" style="visibility:hidden; position:absolute; font-size: 80%"><bean:message key="usuario.nuevo.seleccione_aplicacion" /></label>
					</td>
					</logic:equal>
					<logic:equal name="<%=Defs.PARAMETRO_TIPO_ACCION_ENTIDADES%>" value="<%=Defs.ACCION_NUEVO_MODIFICAR_ENTIDAD%>">
					<td colspan=3>
						<label id="seleccione_entidad" class="gr_error_frame" style="visibility:hidden; position:absolute; font-size: 80%"><bean:message key="usuario.nuevo.seleccione_entidad" /></label>
						<label id="seleccione_aplicacion" class="gr_error_frame" style="visibility:hidden; position:absolute; font-size: 80%"><bean:message key="usuario.nuevo.seleccione_aplicacion" /></label>
					</td>
					</logic:equal>
				</tr>
				<tr>
					<td>
							<label class="gr_small_bold" style="margin-left: 10px;">
							<logic:equal name="<%=Defs.PARAMETRO_TIPO_ACCION_ENTIDADES%>" value="<%=Defs.ACCION_NUEVO_ASOCIAR_ENTIDAD%>">
								<bean:message key="usuario.nuevo.seleccione_entidades" />
							</logic:equal>
							<logic:equal name="<%=Defs.PARAMETRO_TIPO_ACCION_ENTIDADES%>" value="<%=Defs.ACCION_NUEVO_MODIFICAR_ENTIDAD%>">
								<bean:message key="usuario.nuevo.modificaciones_entidades" />
							</logic:equal>
						</label>
					</td>
					<logic:equal name="<%=Defs.PARAMETRO_TIPO_ACCION_ENTIDADES%>" value="<%=Defs.ACCION_NUEVO_ASOCIAR_ENTIDAD%>">
					<td></td>
					</logic:equal>
					<td>
						<br/><label class="gr_small_bold" style="margin-left: 30px;">
								<bean:message key="usuario.nuevo.aplicaciones_disponibles" />
						</label>
					</td>
					<td></td>
				</tr>
				<tr valign="top">
					<td>
						<div id=listado_asignado style="margin-top: 10px; margin-left: 10px; width: 300px; height: 300px; overflow-y: auto" align="center" class=permisos>
							<table id="tabla_entidades" width="100%" style="margin: 0px" cellpadding="0px" cellspacing="0px">
								<logic:equal name="<%=Defs.PARAMETRO_TIPO_ACCION_ENTIDADES%>" value="<%=Defs.ACCION_NUEVO_ASOCIAR_ENTIDAD%>">
								<%
									List entidades = (ArrayList)session.getAttribute(Defs.PARAMETRO_ADMINISTRADOR_ENTIDADES_A_ELEGIR);
									for(int i=0; i<entidades.size(); i++){
								%>
									<tr class='<%=((i%2==0) ? "odd_list" : "even_list")%>'>
										<td width="26px">
											<input type="checkbox" id="posiblesEntidades[<%=i%>]" value="<%=((Entidad)entidades.get(i)).getIdentificador()%>" />
										</td>
										<td>
											<label id="nombreEntidad[<%=i%>]"><%=((Entidad)entidades.get(i)).getNombreLargo()%></label>
										</td>
									</tr>
								<%
									}
								%>
								</logic:equal>
							</table>
						</div>
						<logic:equal name="<%=Defs.PARAMETRO_TIPO_ACCION_ENTIDADES%>" value="<%=Defs.ACCION_NUEVO_MODIFICAR_ENTIDAD%>">
						<div id=aplicaciones class=ventana_aplicaciones style="font-size: 80%; padding: 5px; position: absolute; top: 100px; width: 90%; visibility: hidden">
							<br/>
						</div>
						</logic:equal>
					</td>
					<logic:equal name="<%=Defs.PARAMETRO_TIPO_ACCION_ENTIDADES%>" value="<%=Defs.ACCION_NUEVO_ASOCIAR_ENTIDAD%>">
					<td>
						<div style="margin-top: 10px;">
							<img id=ico_check src="img/ico_check.jpg" style="cursor: pointer;" alt="<bean:message key="usuario.nuevo.marcar_entidades" />" title="<bean:message key="usuario.nuevo.marcar_entidades" />" tabindex="1" onclick="javascript:checkEntidades(true);"/>
							<img id=ico_uncheck src="img/ico_uncheck.jpg" style="cursor: pointer;" alt="<bean:message key="usuario.nuevo.desmarcar_entidades" />" title="<bean:message key="usuario.nuevo.desmarcar_entidades" />" tabindex="2"  onclick="javascript:checkEntidades(false);"/>
						</div>
					</td>
					</logic:equal>
					<td>
						<div style="margin-top: 10px; margin-left: 30px; width: 300px;">
							<table id="tabla_aplicaciones" width="100%" style="margin: 0px" cellpadding="0px" cellspacing="0px" class=permisos>
							<%
							for(int j=0; j<aplicaciones.length; j++) {
							%>
								<tr height="24px">
									<td width="26px">
										<input type="checkbox" id="posiblesAplicaciones[<%=j%>]" value="<%=aplicaciones[j].getIdentificador()%>" />
									</td>
									<td>
										<label class="gr_small" id="labelAplicaciones[<%=j%>]" for="posiblesAplicaciones[<%=j%>]"><bean:message key='<%="entidad.administrar.descripcion_corta." + aplicaciones[j].getIdentificador()%>' /></label>
									</td>
								</tr>
							<%
							}
							%>
							</table>
							<table>
								<tr>
									<td colspan=2 align=center>
										<br/><br/>
										<logic:equal name="<%=Defs.PARAMETRO_TIPO_ACCION_ENTIDADES%>" value="<%=Defs.ACCION_NUEVO_ASOCIAR_ENTIDAD%>">
										<input onclick="javascript:enviarInsertar()" type=button value="<bean:message key="boton.aceptar"/>" title="<bean:message key="boton.aceptar"/>" class="ok" tabindex="5" /> 
										</logic:equal>
										<logic:equal name="<%=Defs.PARAMETRO_TIPO_ACCION_ENTIDADES%>" value="<%=Defs.ACCION_NUEVO_MODIFICAR_ENTIDAD%>">
										<input onclick="javascript:enviarModificar()" type=button value="<bean:message key="boton.aceptar"/>" title="<bean:message key="boton.aceptar"/>" class="ok" tabindex="5" /> 
										</logic:equal>
										<input onclick="javascript:cancelar()" type=button value="<bean:message key="boton.cancelar"/>" title="<bean:message key="boton.cancelar"/>" class="ok" tabindex="6" /> 
									</td>
								</tr>
								<tr id=tr_leyenda style="visibility: hidden">
									<td colspan=2 align=center>
										<br/>
										<label class="gr_small"><bean:message key="usuario.nuevo.azul"/></label>
										<br/>
										<label class="gr_small_red"><bean:message key="usuario.nuevo.rojo"/></label>
									</td>
								</tr>
							</table>
						</div>
					</td>
					<td>
						<div style="margin-top: 10px;">
							<img id=ico_check_1 src="img/ico_check.jpg" style="cursor: pointer;" alt="<bean:message key="usuario.nuevo.marcar_aplicaciones" />" title="<bean:message key="usuario.nuevo.marcar_aplicaciones" />" tabindex="3" onclick="javascript:checkAplicaciones(true);"/>
							<img id=ico_uncheck_1 src="img/ico_uncheck.jpg" style="cursor: pointer;" alt="<bean:message key="usuario.nuevo.desmarcar_aplicaciones" />" title="<bean:message key="usuario.nuevo.desmarcar_aplicaciones" />" tabindex="4"  onclick="javascript:checkAplicaciones(false);"/>
						</div>
					</td>
				</tr>
			</table>
		</form>
		<logic:equal name="<%=Defs.PARAMETRO_TIPO_ACCION_ENTIDADES%>" value="<%=Defs.ACCION_NUEVO_MODIFICAR_ENTIDAD%>">
		<script language="Javascript">
			cargarEntidades();
			cargarAplicaciones();
			cargarLeyenda();
		</script>
		</logic:equal>
	</body>
</html>