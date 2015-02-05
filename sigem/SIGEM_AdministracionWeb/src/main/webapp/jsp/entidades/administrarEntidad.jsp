<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>

<%@page import="ieci.tecdoc.sgm.admsistema.util.Defs"%>
<%@page import="ieci.tecdoc.sgm.core.services.gestion_administracion.ConstantesGestionUsuariosAdministracion"%>
<%@page import="ieci.tecdoc.sgm.admsistema.util.Utilidades"%>

<%
	Aplicacion[] aplicaciones = (Aplicacion[])request.getAttribute(Defs.PARAMETRO_APLICACIONES);
	String nombreEntidad = (String)request.getAttribute(Defs.PARAMETRO_NOMBRE_ENTIDAD);
	String idEntidad = (String)request.getAttribute(Defs.PARAMETRO_ID_ENTIDAD);
	if (Utilidades.esNuloOVacio(idEntidad))
		idEntidad = "";
	String key = (String)session.getAttribute(ConstantesGestionUsuariosAdministracion.PARAMETRO_KEY_SESION_USUARIO_ADM);
	if (Utilidades.esNuloOVacio(key))
		key = "";
%>

<%@page import="ieci.tecdoc.sgm.core.services.administracion.Aplicacion"%>
<html>
	<head>
		<LINK href="css/estilos.css" type=text/css rel=stylesheet />
		
		<SCRIPT language=javascript>
			function volver() {
				var formulario = document.getElementById('formulario');
				formulario.action = 'listadoEntidades.do';
				formulario.submit();
			}
		</SCRIPT>
	</head>
	<body>
		<%String locale = (request.getLocale() != null) ? request.getLocale().getLanguage() : ""; if (locale == null || "".equals(locale)) locale = "es";%>
		<jsp:include flush="true" page="../cabecera.jsp">
			<jsp:param name="ayuda" value='<%=request.getContextPath() + "/jsp/ayudas/" + locale + "/entidades/administrar.html"%>' />
		</jsp:include>
		
		<form id=formulario action="" method=post></form>

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
								<h1><bean:message key="entidades.administrar" arg0="<%=nombreEntidad%>"/></h1>
								<div class="col" align="right" style="height: 30px;">
									<table border="0" cellspacing="0" cellpadding="0">
       									<tr align=right>
						       				<td width="100%">&nbsp;</td>    
       									</tr>
       								</table>
								</div>
								<br/>
								<table align="center">
								<% 
									int ajuste = 0;
									for(int i=0; i<aplicaciones.length; i++) { 
										Aplicacion aplicacion = (Aplicacion)aplicaciones[i];
										if (!ConstantesGestionUsuariosAdministracion.APLICACION_ADMINISTRACION.equals(aplicacion.getIdentificador())) {
											if ((i+ajuste)%2==0) {
									%>
										<tr valign="top">
									<% } %>
									
										<td style="padding-bottom: 20px;">
											<table>
												<tr>
													<td class="td-azul"><bean:message key='<%="entidad.administrar.nombre."+aplicacion.getIdentificador()%>'/></td>
												</tr>
												<tr>
													<td>
														<table width="400" border="0">	
															<tr>
																<td>
																	<h1>
																		<%String url = Utilidades.obtenerUrlAplicacion(request, aplicacion);%>
																		<a href='<%=url%>?<%=ConstantesGestionUsuariosAdministracion.PARAMETRO_ID_ENTIDAD%>=<%=idEntidad%>&<%=ConstantesGestionUsuariosAdministracion.PARAMETRO_ID_APLICACION%>=<%=aplicacion.getIdentificador()%>&<%=ConstantesGestionUsuariosAdministracion.PARAMETRO_KEY_SESION_USUARIO_ADM_ENTIDAD%>=<%=key + "_" + idEntidad%>'>
																			<bean:message key='<%="entidad.administrar.descripcion_corta."+aplicacion.getIdentificador()%>'/>
																		</a>
																	</h1>
																</td>
						                      				</tr>
						                      				<tr>
						                      					<td class="td-gris">
																	 <bean:message key='<%="entidad.administrar.descripcion_larga."+aplicacion.getIdentificador()%>'/>
						                      					</td>
						                      				</tr>                    			
						                      			</table>
													</td>
												</tr>
											</table>
										</td>
								<%
											if ((i+ajuste)%2==1) {
								%>
											</tr>
								<% 		}
										} else {
											ajuste++;
										}
									}
									if (aplicaciones.length == 1 && ajuste == 1) {
								%>
										<td style="padding-bottom: 20px;">
											<table border=0 cellpadding=0 cellspacing=0 align=center style="padding-left: 12px; padding-bottom:22px;">
												<tr>
													<td colspan=2>
								                		<label class="gr_informativo"><bean:message key="mensaje.informativo.entidad.administrar.sin_permiso"/></label>
								                	</td>
												</tr>
											</table>
										</td>
								<%
									}
									%>
								</table>
								<div style="padding-top: 10px; padding-bottom: 10px" align=center>
									<input onclick="javascript:volver()" type=button value="<bean:message key="boton.volver"/>" class="ok" tabindex="2" title="<bean:message key="boton.volver"/>"/> 
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