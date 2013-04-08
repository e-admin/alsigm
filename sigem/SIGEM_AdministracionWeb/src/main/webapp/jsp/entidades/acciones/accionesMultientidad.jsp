<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-html-el.tld" prefix="html-el" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>

<%@page import="ieci.tecdoc.sgm.admsistema.util.Defs"%>

<html>
	<head>
		<LINK href="css/estilos.css" type=text/css rel=stylesheet />

		<SCRIPT language=javascript>
			function enviarFormulario() {
				if (validateFormulario()){
					var formulario = document.getElementById("formulario");
					formulario.submit();
				} else {
					ocultarMensajes();
					mostrarMensaje (true, "<bean:message key="acciones.multientidad.mensaje.error.accion.seleccione" />");
				}
			}

			function validateFormulario() {
				var radios = document.accionMultientidadBean.checkAccion;
				if (radios != null) {
					if (radios.length) {
						for (var i=0; i < radios.length; i++) {
							if (radios[i].checked) {
								return true;
							}
						}
					} else if (radios.checked) {
						return true;
					}
				}
				return false;

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
		<jsp:include flush="true" page="../../cabecera.jsp">
			<jsp:param name="ayuda" value='<%=request.getContextPath() + "/jsp/ayudas/" + locale + "/accionesmultientidad/seleccionAccion.html"%>' />
		</jsp:include>

		<div id=contenedora align=center>
			<div class=cuerpo style="width: 80%">
				<div class=cuerporight>
					<div class=cuerpomid>
						<h1><bean:message key="acciones.multientidad.titulo"/></h1>
						<div class=submenu3>
							<ul>
								<li class=submen1on>
  									<img src="img/subme3_on.gif">
  									<a href="listadoAccionesMultientidad.do" class=submen1on_a>
  										<bean:message key="tab.acciones.multientidad"/>
  									</a>
  								</li>
  								<li class=submen1off>
  									<img src="img/subme3_on_of.gif">
  									<a href="listadoEntidades.do">
  										<bean:message key="tab.entidades"/>
  									</a>

  								</li>
								<li class="submen1off">
						        	<img src="img/subme3_of_of.gif" />
									<a href="listadoUsuarios.do">
										<bean:message key="tab.usuarios"/>
									</a>
									<img src="img/subme3_of_0.gif">
		  						</li>
  							</ul>
  						</div>
						<div class=cuadro>
							<div>
								<div class="col" align="right" style="height: 30px;">
									<table border="0" cellspacing="0" cellpadding="0">
       									<tr align=right>
						       				<td width="100%">&nbsp</td>
						       				<td width="40%">
						       					<span style="white-space: nowrap;">
													<label class="gr_boton"><bean:message key="acciones.multientidad.label.iniciar.configuracion"/></label>
													<img src="img/ico_accionesmultientidad.png" style="cursor: pointer" onclick="javascript:enviarFormulario()" alt="<bean:message key="acciones.multientidad.boton.iniciar.configuracion"/>" title="<bean:message key="acciones.multientidad.boton.iniciar.configuracion"/>"/>
												</span>
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

								<html:form styleId="formulario" action="/ejecutarConfiguracionAccionMultientidad.do" method="post">
								<display:table name="<%=Defs.LISTADO_ACCIONES_MULTIENTIDAD%>"
									uid="accionMultientidad"
									pagesize="10"
									requestURI="listadoAccionesMultientidad.do"
									export="false"
									class="tablaListado"
									sort="list"
									style="width:96%;"
									defaultsort="1">
									<display:column  style="width: 20px; height: 100%" media="html" titleKey="cabeceraTabla.acciones.multientidad.panelBotones" sortable="false" headerClass="cabeceraTabla" >
										<html-el:radio styleId="checkAccion" property="idAccion" value="${accionMultientidad.identificador}" style="border: 0px; width: 20px"/>
									</display:column>
									<display:column  style="height: 100%" media="html" titleKey="cabeceraTabla.acciones.multientidad.nombre" sortable="true" headerClass="cabeceraTabla" >
										<bean:write name="accionMultientidad" property="nombre" />
									</display:column>
								</display:table>
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
	</body>
</html>