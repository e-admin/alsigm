<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-html-el.tld" prefix="html-el"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ page import="ieci.tecdoc.isicres.rpadmin.struts.util.AutenticacionAdministracion" %>
<%@ page import="es.ieci.tecdoc.isicres.admin.core.services.ConstantesGestionUsuariosAdministracion" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="es" xml:lang="es">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<meta name="author" content="IECISA" />
<title><bean:message key="ieci.tecdoc.sgm.pgadmin.title.aplication"/> - <bean:message key="ieci.tecdoc.sgm.rpadmin.botones.asuntos" /></title>
<link href="css/estilos.css" rel="stylesheet" type="text/css" />
<link href="css/adminApp.css" rel="stylesheet" type="text/css" />
<link href="css/calendar-win2k-1.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="js/common.js"></script>
<script type="text/javascript" src="js/tabsAsunto.js"></script>
<script type="text/javascript" src="js/controlCambios.js"></script>
<script type="text/javascript" src="js/calendar.js"></script>
<script type="text/javascript" src="js/calendar-es.js"></script>
</head>
<body onload="init(<bean:write name="asuntoForm" property="activeTab"/>)">
<html:form action="/nuevoAsunto.do">
	<html:hidden property="activeTab" styleId="activeTab"/>
	<html:hidden property="cambios"/>
	<div id="contenedora">
		<jsp:include page="includes/cabecera.jsp"/>
		<div id="contenido">
			<div class="cuerpo">
				<div class="cuerporight">
					<div class="cuerpomid">
						<jsp:include page="includes/pestanas.jsp">
							<jsp:param name="pestana" value="asuntos" />
						</jsp:include>
						<div class="cuadro">
							<div id="migas"><bean:message key="ieci.tecdoc.sgm.rpadmin.botones.nuevo" /></div>
								<div class="col" align="right">
								<table>
									<tr>
										<td width="100%" align="left">
											<jsp:include page="includes/errores.jsp"/>
										</td>
										<td class="col_guardar" onclick="llamadaAction('<html:rewrite page="/guardarNuevoAsunto.do"/>')">
											<bean:message key="ieci.tecdoc.sgm.rpadmin.botones.aceptar" />
										</td>

										<c:set var="funcionllamadaActionComprobarCambios">
											llamadaActionComprobarCambios("<html:rewrite page='/listadoAsuntos.do'/>",true,"<bean:message key='ieci.tecdoc.sgm.rpadmin.cambios.sin.guardar'/>")
										</c:set>
										<td class="col_volver" onclick="<c:out value="${funcionllamadaActionComprobarCambios}"/>">
											<bean:message key="ieci.tecdoc.sgm.rpadmin.botones.cancelar" />
										</td>
									</tr>
								</table>
							</div><!-- class=col -->
							<div class="cuerpomidUsuarios">
								<div class="submenuUsuario" id="nuevoUsuarioPestana">
									<table cellpadding="0" cellspacing="0" border="0">
										<tr>
											<td>
												<div id="tab1" onmouseover="tabover(1)" onmouseout="tabout(1)" onclick="choosebox(1,2)">
													<table summary="" border="0" cellpadding="0" cellspacing="0">
													<tbody><tr>
														<td class="tableft" height="17" width="7">
															<img src="img/dot.gif" alt="" border="0" height="17" width="7"/></td>
														<td class="tabmiddle1" id="tabmiddle1" onclick="tabClick(1)"><bean:message key="ieci.tecdoc.sgm.rpadmin.asuntos.tipo.asunto"/></td>
														<td class="tabright"><img src="img/dot.gif" alt="" border="0" height="17" width="7"/></td>
													</tr>
													</tbody></table>
												</div>
											</td>
											<td>
												<div id="tab2" onmouseover="tabover(2)" onmouseout="tabout(2)" onclick="choosebox(2,2)">
													<table border="0" cellpadding="0" cellspacing="0">
													<tbody><tr>
														<td class="tableft" height="17" width="7">
															<img src="img/dot.gif" alt="" border="0" height="17" width="7"/></td>
														<td class="tabmiddle2" id="tabmiddle2" onclick="tabClick(2)"><bean:message key="ieci.tecdoc.sgm.rpadmin.asuntos.oficinas.registro"/></td>
														<td class="tabright"><img src="img/dot.gif" alt="" border="0" height="17" width="7"/></td>
													</tr>
													</tbody></table>
												</div>
											</td>
											<td>
												<div id="tab3" onmouseover="tabover(3)" onmouseout="tabout(3)" onclick="choosebox(3,2)">
													<table border="0" cellpadding="0" cellspacing="0">
													<tbody><tr>
														<td class="tableft" height="17" width="7">
															<img src="img/dot.gif" alt="" border="0" height="17" width="7"/></td>
														<td class="tabmiddle3" id="tabmiddle3" onclick="tabClick(3)"><bean:message key="ieci.tecdoc.sgm.rpadmin.asuntos.documentos"/></td>
														<td class="tabright"><img src="img/dot.gif" alt="" border="0" height="17" width="7"/></td>
													</tr>
													</tbody></table>
												</div>
											</td>

											<td width="35%"></td>
										</tr>
									</table>
								</div><!-- nuevoUsuarioPestaña -->
		          				<div class="cuadroUsuario">
									<div style="height: 260px;">
										<div id="tipoAsunto">
											<table cellpadding="0" cellspacing="0" border="0" width="100%">
												<tr>
													<td width="10" rowspan="80"></td>
													<td class="col" height="20" colspan="2"></td>
												</tr>
												<tr class="col">
													<td class="txt" width="100px" valign="top">
														<bean:message key="ieci.tecdoc.sgm.rpadmin.asuntos.codigo"/>&nbsp;&nbsp;
													</td>
													<td class="txt">
														<html:text property="codigo" styleClass="textInput"  style="width:30%" maxlength="16"/>
													</td>
												</tr>
												<tr class="col">
													<td class="txt" nowrap="nowrap" valign="top">
														<bean:message key="ieci.tecdoc.sgm.rpadmin.asuntos.nombre"/>&nbsp;&nbsp;
													</td>
													<td class="txt">
														<html:text property="nombre" styleClass="textInput" style="width:90%" maxlength="250"/>
													</td>
												</tr>
												<tr class="col">
													<td class="txt" nowrap="nowrap" valign="top"><bean:message key="ieci.tecdoc.sgm.rpadmin.asuntos.unidad.administrativa" />&nbsp;&nbsp;</td>
													<td class="txt" valign="top">
														<html:hidden property="idUnidadAdministrativa"/>
														<html:text property="codigoUnidadAdministrativa" styleClass="textInputRO"  readonly="true" maxlength="5" size="5" style="width:35px"/>
														<html:text property="nombreUnidadAdministrativa" readonly="true" styleClass="textInputRO" style="width:500px" />
															<a href="#" onclick="javascript:chequearSessionBuscar('<html-el:rewrite page="/jsp/buscarUnidadesAsuntosPopup.jsp"/>','<html:rewrite page="/chequearSession.do"/>','<%=AutenticacionAdministracion.obtenerUrlLogin(request, ConstantesGestionUsuariosAdministracion.APLICACION_REGISTRO) %>')">
																<img src="<html:rewrite page="/img/ico_buscar.gif"/>" border="0" align="top"/>
															</a>
													</td>
												</tr>
												<tr class="col">
													<td class="txt" nowrap="nowrap" valign="top"><bean:message key="ieci.tecdoc.sgm.rpadmin.asuntos.fecha.creacion"/>&nbsp;&nbsp;</td>
													<td class="txt" valign="top">
														<html:text property="fechaCreacion" styleId="dat1" styleClass="textInput" maxlength="10" style="width:65px" /><img src='<html:rewrite page="/img/img_calendar.gif"/>' border="0" onclick="return showCalendar('dat1', '%d/%m/%Y', true);" style="cursor:hand" hspace="2"/>&nbsp;<bean:message key="ieci.tecdoc.sgm.rpadmin.texto.formato.fechas"/>
													</td>
												</tr>
												<tr class="col">
													<td class="txt" nowrap="nowrap" valign="top"><bean:message key="ieci.tecdoc.sgm.rpadmin.asuntos.deshabilitado"/>&nbsp;&nbsp;</td>
													<td class="txt" valign="top">
														<html:checkbox property="deshabilitado" onclick="verificarHabilitado()"/>
														<span id="divFechaBaja">
															&nbsp;&nbsp;<bean:message key="ieci.tecdoc.sgm.rpadmin.asuntos.fecha.baja"/>&nbsp;&nbsp;<html:text property="fechaBaja" styleId="dat2" styleClass="textInput" maxlength="10" style="width:65px"/><img src='<html:rewrite page="/img/img_calendar.gif"/>' border="0" id="imgCalFechaBaja" onclick="return showCalendar('dat2', '%d/%m/%Y', true);" style="cursor:hand" hspace="2"/>&nbsp;<bean:message key="ieci.tecdoc.sgm.rpadmin.texto.formato.fechas"/>
														</span>
													</td>
												</tr>
												<tr class="col">
													<td class="txt" nowrap="nowrap" valign="top"><bean:message key="ieci.tecdoc.sgm.rpadmin.asuntos.usar.para.entrada"/>&nbsp;&nbsp;</td>
													<td class="txt" valign="top"><html:checkbox property="usarParaEntrada"/></td>
												</tr>
												<tr class="col">
													<td class="txt" nowrap="nowrap" valign="top"><bean:message key="ieci.tecdoc.sgm.rpadmin.asuntos.usar.para.salida"/>&nbsp;&nbsp;</td>
													<td class="txt" valign="top"><html:checkbox property="usarParaSalida"/></td>
												</tr>
												<tr class="col">
													<td class="txt" nowrap="nowrap" valign="top"><bean:message key="ieci.tecdoc.sgm.rpadmin.asuntos.informacion.auxiliar"/>&nbsp;&nbsp;</td>
													<td class="txt" valign="top"><html:textarea property="infoAuxiliar" rows="2" cols="70"/></td>
												</tr>
												<tr><td height="100%" colspan="2">&nbsp;</td></tr>
											</table>
										</div><!-- tipoAsunto -->
										<div id="oficinasRegistro"  style="display:none">
											<table cellpadding="0" cellspacing="0" border="0" width="100%">
												<tr>
													<td width="10" rowspan="80"></td>
													<td class="col" height="20" colspan="2"></td>
												</tr>
												<tr class="col">
													<td class="txt" valign="top"><html:checkbox property="usarParaTodasOficinas"/></td>
													<td class="txt" nowrap="nowrap" valign="top"><bean:message key="ieci.tecdoc.sgm.rpadmin.asuntos.disponible.todas.oficinas"/>&nbsp;&nbsp;</td>
												</tr>
												<tr>
													<td colspan="2">&nbsp;

													</td>
												</tr>
												<tr>
													<td colspan="2" class="col" align="right">
														<table><tr>
															<td class="col_nuevo" onclick="chequearSessionBuscarOficinas('<html:rewrite page="/listadoSeleccionOficinas.do"/>','<html:rewrite page="/chequearSession.do"/>','<%=AutenticacionAdministracion.obtenerUrlLogin(request, ConstantesGestionUsuariosAdministracion.APLICACION_REGISTRO) %>')">
																<bean:message key="ieci.tecdoc.sgm.rpadmin.botones.anadir"/>
															</td>
														</tr>
														</table>
													</td>
												</tr>
												<tr>
													<td colspan="2">
													<logic:notEmpty name="listaOficinasAsuntos">
													<display:table name="sessionScope.listaOficinasAsuntos" uid="fila" sort="page" style="width:100%">
															<display:column titleKey="ieci.tecdoc.sgm.rpadmin.asuntos.codigo" sortable="false" style="width:5%;">
																<bean:write name="fila" property="codigoOficina"/>
															</display:column>
															<display:column titleKey="ieci.tecdoc.sgm.rpadmin.asuntos.nombre" sortable="false">
																<bean:write name="fila" property="nombreOficina"/>
															</display:column>
															<display:column style="width: 10%;">
																<table><tr>
																	<c:set var="funcioneliminarOficina">
																		eliminarOficina("<html-el:rewrite page='/removeOficinaNuevoAsunto.do'/>","<bean:write name='fila_rowNum' filter='false'/>","<bean:message key='ieci.tecdoc.sgm.rpadmin.oficinas.eliminar.oficina'/>","<bean:write name='fila' property='nombreOficina' filter='false'/>",ESTADO_ELIMINADO_NUEVO)
																	</c:set>
																	<td class="col_eliminar" onclick="<c:out value="${funcioneliminarOficina}"/>">
																		<bean:message key="ieci.tecdoc.sgm.rpadmin.botones.eliminar"/></td>
																</tr></table>
															</display:column>
													</display:table>
													</logic:notEmpty>
														<input type="hidden" name="estadoOficina" id="estadoOficina"/>
														<input type="hidden" name="posOficina" id="posOficina" />
														<input type="hidden" name="idOficinaAsunto" id="idOficinaAsunto"/>
														<input type="hidden" name="idOficina" id="idOficinaAsunto"/>
														<input type="hidden" name="codigoOficina" id="codigoOficina"/>
														<input type="hidden" name="nombreOficina" id="nombreOficina"/>
														<input type="hidden" name="urlAddOficina" id="urlAddOficina" value='<html-el:rewrite page="/addOficinaNuevoAsunto.do"/>'/>
													</td>
												</tr>

											</table>
										</div><!-- oficinasRegistro -->
										<div id="documentos"  style="display:none">
											<table cellpadding="0" cellspacing="0" border="0" width="100%">
												<tr>
													<td width="10" rowspan="80"></td>
													<td class="col" height="20" colspan="2"></td>
												</tr>
												<tr class="col" id="colNuevoDocumento">
													<td class="txt" width="10%" nowrap="nowrap"><bean:message key="ieci.tecdoc.sgm.rpadmin.asuntos.documento"/></td>
													<td class="txt" nowrap="nowrap">
														<table width="100%" >
															<tr>
																<td class="txt" width="90%" valign="top">
																	<html:text property="nombreDocumento" styleId="nombreDocumento" style="width:98%" maxlength="32" styleClass="textInput"/>
																	<input type="hidden" name="estadoDocumento" id="estadoDocumento"/>
																	<input type="hidden" name="posDocumento" id="posDocumento"/>
																	<input type="hidden" name="idDocumento" id="idDocumento"/>
																</td>
																<td class="txt" valign="top">
																	<table><tr>
																			<c:set var="funcionaddDocumento">
																				addDocumento("<html-el:rewrite page='/addDocumentoNuevoAsunto.do'/>","<bean:message key='ieci.tecdoc.sgm.rpadmin.asuntos.documento.obligatorio'/>")
																			</c:set>
																			<td class="col_nuevo" onclick="<c:out value="${funcionaddDocumento}"/>" valign="top">
																				<bean:message key="ieci.tecdoc.sgm.rpadmin.botones.anadir"/>
																			</td>
																		</tr>
																	</table>
																</td>
															</tr>
														</table>
													</td>
												</tr>
												<tr>
													<td colspan="2">&nbsp;</td>
												</tr>
												<logic:notEmpty name="listaDocumentosAsuntos">
												<tr>
													<td colspan="2">
													<display:table name="sessionScope.listaDocumentosAsuntos" uid="fila" sort="page" style="width:100%">
															<display:column titleKey="ieci.tecdoc.sgm.rpadmin.asuntos.nombre" sortable="false">
																<span id="labelDocumento_<bean:write name="fila_rowNum"/>">
																	<bean:write name="fila" property="description"/>
																</span>
																<span id="txtDocumento_<bean:write name="fila_rowNum"/>" style="display:none">
																	<input type="text" name="editarNombreDocumento" style="width:96%" maxlength="32" class="textInput" id='editarNombreDocumento_<bean:write name="fila_rowNum"/>' value='<bean:write name="fila" property="description"/>'/>
																</span>
															</display:column>
															<display:column style="width: 75px;">
																<span id='btnEditar_<bean:write name="fila_rowNum"/>'>
																	<table><tr>
																		<c:set var="funcioneditarDocumento">
																			editarDocumento(<bean:write name='fila_rowNum' filter='false'/>,"<bean:write name='fila' property='description' filter='false'/>")
																		</c:set>
																		<td class="col_editar" onclick="<c:out value="${funcioneditarDocumento}"/>">
																			<bean:message key="ieci.tecdoc.sgm.rpadmin.botones.editar"/></td>
																	</tr></table>
																</span>
																<span id='btnAceptar_<bean:write name="fila_rowNum"/>' style="display:none;text-align:center">
																	<table><tr>
																		<c:set var="funcionaceptarEdicionDocumento">
																			aceptarEdicionDocumento("<html-el:rewrite page='/editDocumentoNuevoAsunto.do'/>","<bean:message key="ieci.tecdoc.sgm.rpadmin.asuntos.documento.obligatorio"/>",ESTADO_NUEVO)
																		</c:set>
																		<td class="col_aceptar" nowrap="nowrap" onclick="<c:out value="${funcionaceptarEdicionDocumento}"/>" title='<bean:message key="ieci.tecdoc.sgm.rpadmin.asuntos.documentos.aceptar.edicion"/>'>
																			<bean:message key="ieci.tecdoc.sgm.rpadmin.asuntos.documentos.aceptar.edicion"/>
																		</td>
																	</tr></table>
																</span>
															</display:column>
															<display:column style="width: 75px;">
																<span id='btnEliminar_<bean:write name="fila_rowNum"/>'>
																<table><tr>
																	<c:set var="funcioneliminarDocumento">
																		eliminarDocumento("<html-el:rewrite page='/removeDocumentoNuevoAsunto.do'/>","<bean:write name='fila_rowNum' filter='false'/>","<bean:message key='ieci.tecdoc.sgm.rpadmin.asuntos.eliminar.documento'/>","<bean:write name='fila' property='description' filter='false'/>",ESTADO_ELIMINADO_NUEVO)
																	</c:set>
																	<td class="col_eliminar" nowrap="nowrap" onclick="<c:out value="${funcioneliminarDocumento}"/>">
																		<bean:message key="ieci.tecdoc.sgm.rpadmin.botones.eliminar"/></td>
																</tr></table>
																</span>
																<span id='btnCancelar_<bean:write name="fila_rowNum"/>' style="display:none;text-align:center">
																	<table><tr>
																		<td class="col_cancelar" nowrap="nowrap" onclick="cancelarEdicionDocumento()" title='<bean:message key="ieci.tecdoc.sgm.rpadmin.asuntos.documentos.cancelar.edicion"/>'>
																			<bean:message key="ieci.tecdoc.sgm.rpadmin.asuntos.documentos.cancelar.edicion"/>
																		</td>
																	</tr></table>
																</span>
															</display:column>
													</display:table>
													</td>
												</tr>
												</logic:notEmpty>
											</table>
										</div><!-- documentos -->
									</div>
								</div><!-- Cuadro Usuario -->
							</div><!-- cuerpomidUsuarios -->
						</div><!-- cuadro -->
					</div><!-- cuerpomid -->
				</div><!-- cuerporight -->
			</div><!-- cuerpo -->
  			<div id="pie"></div>
		</div><!-- Contenido -->
	</div><!-- contenedora -->
</html:form>
</body>
</html>
