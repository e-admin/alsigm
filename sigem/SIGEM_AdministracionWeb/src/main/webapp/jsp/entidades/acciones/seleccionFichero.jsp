<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-html-el.tld" prefix="html-el" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>

<%@page import="ieci.tecdoc.sgm.admsistema.util.Defs"%>
<%@page import="ieci.tecdoc.sgm.admsistema.vo.AccionMultientidadVO"%>

<%
	AccionMultientidadVO accionMultientidadVO = (AccionMultientidadVO) request.getSession().getAttribute(Defs.ACCION_MULTIENTIDAD_WIZARD);
	String nombreAccion = accionMultientidadVO.getNombreAccion();
%>

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
					mostrarMensaje (true, "<bean:message key="acciones.multientidad.mensaje.error.fichero.seleccione" />");
				}
			}

			function validateFormulario() {
				if(!document.getElementById("fichero").value){
                    return false;
               	}
				return true;
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
			<jsp:param name="ayuda" value='<%=request.getContextPath() + "/jsp/ayudas/" + locale + "/accionesmultientidad/seleccionFichero.html"%>' />
		</jsp:include>

		<div id=contenedora align=center>
			<div class=cuerpo style="width: 80%">
				<div class=cuerporight>
					<div class=cuerpomid>
						<h1><bean:message key="acciones.multientidad.seleccion.fichero.titulo"/></h1>
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
						       				<td width="100%">&nbsp;</td>
						       				<td width="40%">
						       					<span style="white-space: nowrap;">
													<label class="gr_boton"><bean:message key="acciones.multientidad.label.continuar.configuracion"/>&nbsp;<b><%=nombreAccion%></b></label>
							       					<img src="img/icon_go.gif" style="cursor: pointer" onclick="javascript:enviarFormulario()" alt="<bean:message key="acciones.multientidad.boton.paso.siguiente"/>" title="<bean:message key="acciones.multientidad.boton.paso.siguiente"/>"/>
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

								<html:form styleId="formulario" action="/ejecutarConfiguracionAccionMultientidad.do" method="post" enctype="multipart/form-data">
									<html:hidden property="idAccion"/>
									<html:hidden property="paso"/>
									<html:file styleId="fichero" property="fichero" style="width: 400px"></html:file>
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