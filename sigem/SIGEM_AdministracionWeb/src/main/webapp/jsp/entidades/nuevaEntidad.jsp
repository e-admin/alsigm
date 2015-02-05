<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>

<%@page import="ieci.tecdoc.sgm.admsistema.util.Defs"%>

<%
String ayuda = "";
String accionHelp = (String)session.getAttribute(Defs.ACTION_FORMULARIO_NUEVA_ENTIDAD);
String locale = (request.getLocale() != null) ? request.getLocale().getLanguage() : ""; if (locale == null || "".equals(locale)) locale = "es";
if (Defs.ACCION_NUEVA_ENTIDAD.equals(accionHelp))
	ayuda = "/jsp/ayudas/" + locale + "/procesos/importar.html";
else if (Defs.ACCION_CLONAR_ENTIDAD.equals(accionHelp))
	ayuda = "/jsp/ayudas/" + locale + "/procesos/clonar.html";
%>

<html>
	<head>
		<LINK href="css/estilos.css" type=text/css rel=stylesheet />

		<script language=javascript>
			function cargarLocalidades() {
				var idProv = document.getElementById('<%=Defs.PARAMETRO_PROVINCIA%>');
				var value = idProv.options[idProv.selectedIndex].value;
				if (value != "") {
					var frameLocalidades = document.getElementById('frameLocalidades');
					frameLocalidades.src = 'listadoMunicipios.do?<%=Defs.PARAMETRO_PROVINCIA%>=' + value;
				} else {
					var municipios = parent.document.getElementById('<%=Defs.PARAMETRO_MUNICIPIO%>');
					while (municipios.length > 0)
						municipios.remove(municipios.length-1);
				}
			}

			function enviar() {
				<%--
				var idProv = document.getElementById('<%=Defs.PARAMETRO_PROVINCIA%>');
				var idMun = document.getElementById('<%=Defs.PARAMETRO_MUNICIPIO%>');
				if (idProv.value != "" && idMun.value != "")
					document.getElementById('<%=Defs.PARAMETRO_CODIGO_INE%>').value = idProv.value + idMun.value;
				--%>
				document.getElementById('entidadBean').submit();
			}

			function cancelar() {
				var formulario = document.getElementById('entidadBean');
				formulario.action = 'listadoEntidades.do';
				formulario.submit();
			}

			function realizarBusqueda() {
				var formulario = document.getElementById("busquedaBean");
				formulario.submit();
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
		</script>
	</head>
	<body>
		<jsp:include flush="true" page="../cabecera.jsp">
			<jsp:param name="ayuda" value="<%=request.getContextPath() + ayuda%>" />
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
								<h1><bean:message key="entidades.nueva"/></h1>
								<div class="col" align="right" style="height: 30px;">
									<table border="0" cellspacing="0" cellpadding="0">
       									<tr align=right>
						       				<td width="100%">&nbsp;</td>
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
								<html:form styleId="entidadBean" action="/nuevaEntidad.do" method="post">
									<input type=hidden id=<%=Defs.PARAMETRO_ID_ENTIDAD%> name=<%=Defs.PARAMETRO_ID_ENTIDAD%> value="" />
									<input type=hidden id=<%=Defs.PARAMETRO_CODIGO_INE%> name=<%=Defs.PARAMETRO_CODIGO_INE%> value="" />
									<table border=0 cellpadding=0 cellspacing=0 align=center style="padding-left: 12px; padding-bottom:22px;">
										<logic:present name="<%=Defs.MENSAJE_ERROR%>">
											<tr>
												<td colspan=2>
							                		<label class="gr_error"><bean:message key="<%=(String)request.getAttribute(Defs.MENSAJE_ERROR)%>"/></label>
							                	</td>
											</tr>
											<tr><td colspan=2><br/></td></tr>
										</logic:present>
										<tr>
											<td>
												<label for="<%=Defs.PARAMETRO_NOMBRE_CORTO%>" class="gr_ext"><bean:message key="entidad.nueva.nombreCorto" /></label>
											</td>
											<td>
						                		<html:text property="<%=Defs.PARAMETRO_NOMBRE_CORTO%>" styleId="<%=Defs.PARAMETRO_NOMBRE_CORTO%>" maxlength="70" tabindex="2" style="width: 330px"></html:text>
						                	</td>
										</tr>
										<tr>
											<td>
												<label for="<%=Defs.PARAMETRO_NOMBRE_LARGO%>" class="gr_ext"><bean:message key="entidad.nueva.nombreLargo" /></label>
											</td>
											<td>
						                		<html:text property="<%=Defs.PARAMETRO_NOMBRE_LARGO%>" styleId="<%=Defs.PARAMETRO_NOMBRE_LARGO%>" maxlength="500" tabindex="3" style="width: 330px"></html:text>
						                	</td>
										</tr>
										<%--
										<tr>
											<td>
												<label for="<%=Defs.PARAMETRO_CODIGO_INE%>" class="gr_ext"><bean:message key="entidad.nueva.callejero" /></label>
											</td>
											<td>
						                		<html:select property="<%=Defs.PARAMETRO_PROVINCIA%>" styleClass="gr" onchange="javascript:cargarLocalidades();" styleId="<%=Defs.PARAMETRO_PROVINCIA%>" tabindex="4">
						                			<html:optionsCollection name="<%=Defs.PARAMETRO_PROVINCIAS%>" label="descripcion" value="idProvincia"/>
						                		</html:select>
						                		<html:select property="<%=Defs.PARAMETRO_MUNICIPIO%>" styleClass="gr" styleId="<%=Defs.PARAMETRO_MUNICIPIO%>" tabindex="5">
													<html:optionsCollection name="<%=Defs.PARAMETRO_MUNICIPIOS%>" label="descripcion" value="idMunicipio" />
						                		</html:select>
						                	</td>
										</tr>
										--%>
										<tr><td colspan=2><br/></td></tr>
										<tr align=center>
											<td colspan=2>
												<input onclick="javascript:enviar()" type=button value="<bean:message key="boton.aceptar"/>" class="ok" tabindex="6" title="<bean:message key="boton.aceptar"/>" />
												<input onclick="javascript:cancelar()" type=button value="<bean:message key="boton.cancelar"/>" class="ok" tabindex="7" title="<bean:message key="boton.cancelar"/>"/>
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
		<iframe id=frameLocalidades src="<html:rewrite page="/jsp/blank.html" />" style="position:absolute; top: 0px;visibility: hidden; width: 0px; height: 0px"></iframe>
	</body>
</html>