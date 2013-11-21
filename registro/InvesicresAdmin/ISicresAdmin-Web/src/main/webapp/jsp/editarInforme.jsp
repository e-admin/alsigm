<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-html-el.tld" prefix="html-el"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ page
	import="ieci.tecdoc.isicres.rpadmin.struts.util.AutenticacionAdministracion"%>
<%@ page
	import="es.ieci.tecdoc.isicres.admin.core.services.ConstantesGestionUsuariosAdministracion"%>
<%@page pageEncoding="ISO-8859-1" contentType="text/html; charset=ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="es" xml:lang="es">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<meta name="author" content="IECISA" />
<title><bean:message key="ieci.tecdoc.sgm.pgadmin.title.aplication"/> - <bean:message
	key="ieci.tecdoc.sgm.rpadmin.botones.informes" /></title>
<link href="css/estilos.css" rel="stylesheet" type="text/css" />
<link href="css/adminApp.css" rel="stylesheet" type="text/css" />
<link href="css/calendar-win2k-1.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="js/common.js"></script>
<script type="text/javascript" src="js/tabsInforme.js"></script>
<script type="text/javascript" src="js/controlCambios.js"></script>
</head>
<body
	onload="init(<bean:write name="informeForm" property="activeTab"/>)">
<html:form action="/editarInforme.do" enctype="multipart/form-data">
	<html:hidden property="id" styleId="id" />
	<html:hidden property="activeTab" styleId="activeTab" />
	<html:hidden property="cambios" />
	<div id="contenedora"><jsp:include page="includes/cabecera.jsp" />
	<div id="contenido">
	<div class="cuerpo">
	<div class="cuerporight">
	<div class="cuerpomid"><jsp:include page="includes/pestanas.jsp">
		<jsp:param name="pestana" value="informes" />
	</jsp:include>
	<div class="cuadro">
	<div id="migas"><bean:message
		key="ieci.tecdoc.sgm.rpadmin.botones.propiedadesEditar" />&nbsp; <span
		class="migasElementoSeleccionado"><bean:write
		name="informeForm" property="description" /></span>&nbsp;</div>
	<div class="col" align="right">
	<table>
		<tr>
			<td width="100%" align="left"><jsp:include
				page="includes/errores.jsp" /></td>
			<td class="col_guardar"
				onclick="llamadaAction('<html:rewrite page="/guardarEdicionInforme.do"/>')">
			<bean:message key="ieci.tecdoc.sgm.rpadmin.botones.aceptar" /></td>
			<c:set var="funcionllamadaActionComprobarCambios">
											llamadaActionComprobarCambios("<html:rewrite
					page='/listadoInformes.do' />",true,"<bean:message
					key='ieci.tecdoc.sgm.rpadmin.cambios.sin.guardar' />")
										</c:set>
			<td class="col_volver"
				onclick="<c:out value="${funcionllamadaActionComprobarCambios}"/>">
			<bean:message key="ieci.tecdoc.sgm.rpadmin.botones.cancelar" /></td>
		</tr>
	</table>
	</div>
	<!-- class=col -->
	<div class="cuerpomidUsuarios">
	<div class="submenuUsuario" id="nuevoUsuarioPestana">
	<table cellpadding="0" cellspacing="0" border="0">
		<tr>
			<td>
			<div id="tab1" onmouseover="tabover(1)" onmouseout="tabout(1)"
				onclick="choosebox(1,2)">
			<table summary="" border="0" cellpadding="0" cellspacing="0">
				<tbody>
					<tr>
						<td class="tableft" height="17" width="7"><img
							src="img/dot.gif" alt="" border="0" height="17" width="7" /></td>
						<td class="tabmiddle1" id="tabmiddle1" onclick="tabClick(1)"><bean:message
							key="ieci.tecdoc.sgm.rpadmin.informes" /></td>
						<td class="tabright"><img src="img/dot.gif" alt="" border="0"
							height="17" width="7" /></td>
					</tr>
				</tbody>
			</table>
			</div>
			</td>
			<td>
			<div id="tab2" onmouseover="tabover(2)" onmouseout="tabout(2)"
				onclick="choosebox(2,2)">
			<table border="0" cellpadding="0" cellspacing="0">
				<tbody>
					<tr>
						<td class="tableft" height="17" width="7"><img
							src="img/dot.gif" alt="" border="0" height="17" width="7" /></td>
						<td class="tabmiddle2" id="tabmiddle2" onclick="tabClick(2)"><bean:message
							key="ieci.tecdoc.sgm.rpadmin.informes.oficinas.registro" /></td>
						<td class="tabright"><img src="img/dot.gif" alt="" border="0"
							height="17" width="7" /></td>
					</tr>
				</tbody>
			</table>
			</div>
			</td>
			<td>
			<div id="tab3" onmouseover="tabover(3)" onmouseout="tabout(3)"
				onclick="choosebox(3,2)">
			<table border="0" cellpadding="0" cellspacing="0">
				<tbody>
					<tr>
						<td class="tableft" height="17" width="7"><img
							src="img/dot.gif" alt="" border="0" height="17" width="7" /></td>
						<td class="tabmiddle3" id="tabmiddle3" onclick="tabClick(3)"><bean:message
							key="ieci.tecdoc.sgm.rpadmin.informes.prefiles.usuario" /></td>
						<td class="tabright"><img src="img/dot.gif" alt="" border="0"
							height="17" width="7" /></td>
					</tr>
				</tbody>
			</table>
			</div>
			</td>
			<td>
			<div id="tab4" onmouseover="tabover(4)" onmouseout="tabout(4)"
				onclick="choosebox(4,2)">
			<table border="0" cellpadding="0" cellspacing="0">
				<tbody>
					<tr>
						<td class="tableft" height="17" width="7"><img
							src="img/dot.gif" alt="" border="0" height="17" width="7" /></td>
						<td class="tabmiddle4" id="tabmiddle4" onclick="tabClick(4)">
						<bean:message key="ieci.tecdoc.sgm.rpadmin.informes.libros.registro" /></td>
						<td class="tabright"><img src="img/dot.gif" alt="" border="0"
							height="17" width="7" /></td>
					</tr>
				</tbody>
			</table>
			</div>
			</td>
			<td></td>
		</tr>
	</table>
	</div>
	<!-- nuevoUsuarioPestaña -->
	<div class="cuadroUsuario">
	<div style="height: 260px;">
		<!-- INFORME -->
		<div id="informe">
			<table cellpadding="0" cellspacing="0" border="0" width="100%">
				<tr>
					<td width="10" rowspan="80"></td>
					<td class="col" height="20" colspan="2"></td>
				</tr>
				<tr class="col">
					<td class="txt" width="100px" valign="top"><bean:message
						key="ieci.tecdoc.sgm.rpadmin.informes.titulo" />&nbsp;&nbsp;</td>
					<td class="txt"><html:textarea property="description"
						style="width:80%" cols="80" rows="5" /></td>
				</tr>
				<tr class="col">
					<td class="txt" nowrap="nowrap" valign="top"><bean:message
						key="ieci.tecdoc.sgm.rpadmin.informes.tipo.archivo" />&nbsp;&nbsp;</td>
					<td class="txt"><html:radio property="typeArch" value="1">Entrada</html:radio>
					<html:radio property="typeArch" value="2">Salida</html:radio></td>
				</tr>
				<tr class="col">
					<td class="txt" nowrap="nowrap" valign="top"><bean:message
						key="ieci.tecdoc.sgm.rpadmin.informes.tipo.informe" />&nbsp;&nbsp;</td>
					<td class="txt"><html:select property="typeReport"
						styleClass="textInput" style="width:50%">
					<html:option value="">
					<bean:message key="ieci.tecdoc.sgm.rpadmin.informes.seleccione.tipo" />
					</html:option>
					<html:optionsCollection name="tiposInformes" property="lista"
						value="codigo" label="descripcion" />
					</html:select></td>
				</tr>
				<tr class="col">
					<td class="txt" nowrap="nowrap" valign="top"><bean:message
						key="ieci.tecdoc.sgm.rpadmin.informes.plantilla" />&nbsp;&nbsp;</td>
					<td class="txt"><html:text property="report"
						styleClass="textInput" style="width:50%" maxlength="30" readonly="true" /></td>
				</tr>
				<tr>
					<td height="100%" colspan="2">&nbsp;</td>
				</tr>
				<tr class="col">
					<td class="txt" nowrap="nowrap" valign="top"><bean:message
						key="ieci.tecdoc.sgm.rpadmin.informes.file" />&nbsp;&nbsp;</td>
					<td class="txt">
						<html:file property="informeFile" accept="application/zip" style="width:80%"></html:file>
					</td>
				</tr>
				<tr>
					<td height="100%" colspan="2">&nbsp;</td>
				</tr>
			</table>
		</div><!-- FIN Informe -->
		<!-- tipoAsunto -->
		<div id="oficinasRegistro" style="display: none">
			<table cellpadding="0" cellspacing="0" border="0" width="100%">
				<tr>
					<td width="10" rowspan="80"></td>
					<td class="col" height="20" colspan="2"></td>
				</tr>
				<tr class="col">
					<td class="txt" valign="top"><html:checkbox
						property="usarParaTodasOficinas" /></td>
					<td class="txt" nowrap="nowrap" valign="top"><bean:message
						key="ieci.tecdoc.sgm.rpadmin.informes.disponible.todas.oficinas" />&nbsp;&nbsp;</td>
				</tr>
				<tr>
					<td colspan="2">&nbsp;</td>
				</tr>
				<tr>
					<td colspan="2" class="col" align="right">
						<table>
							<tr>
								<td class="col_nuevo"
									onclick="chequearSessionBuscarOficinas('<html:rewrite page="/listadoSeleccionOficinas.do"/>','<html:rewrite page="/chequearSession.do"/>','<%=AutenticacionAdministracion.obtenerUrlLogin(request, ConstantesGestionUsuariosAdministracion.APLICACION_REGISTRO) %>')">
									<bean:message key="ieci.tecdoc.sgm.rpadmin.botones.anadir" />
								</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td colspan="2">
					<logic:notEmpty name="listaOficinasInformes">
						<display:table name="sessionScope.listaOficinasInformes" uid="fila"
							sort="page" style="width:100%">
							<display:column titleKey="ieci.tecdoc.sgm.rpadmin.asuntos.codigo"
								sortable="false" style="width:5%;">
								<bean:write name="fila" property="codigoOficina" />
							</display:column>
							<display:column titleKey="ieci.tecdoc.sgm.rpadmin.asuntos.nombre"
								sortable="false">
								<bean:write name="fila" property="nombreOficina" />
							</display:column>
							<display:column style="width: 10%;">
								<table>
									<tr>
										<logic:equal name="fila" property="estado" value="1">
											<c:set var="funcioneliminarOficina">
											eliminarOficina("<html-el:rewrite
												page='/removeOficinaEditarInforme.do' />","<bean:write
												name='fila_rowNum' filter='false' />","<bean:message
												key='ieci.tecdoc.sgm.rpadmin.oficinas.eliminar.oficina' />","<bean:write
												name='fila' property='nombreOficina' filter='false' />",ESTADO_ELIMINADO_NUEVO)
											</c:set>
											<td class="col_eliminar"
												onclick="<c:out value="${funcioneliminarOficina}"/>"><bean:message
												key="ieci.tecdoc.sgm.rpadmin.botones.eliminar" /></td>
										</logic:equal>
										<logic:notEqual name="fila" property="estado" value="1">
											<c:set var="funcioneliminarOficina">
											eliminarOficina("<html-el:rewrite
												page='/removeOficinaEditarInforme.do' />","<bean:write
												name='fila_rowNum' filter='false' />","<bean:message
												key='ieci.tecdoc.sgm.rpadmin.oficinas.eliminar.oficina' />","<bean:write
												name='fila' property='nombreOficina' filter='false' />",ESTADO_ELIMINADO)
											</c:set>
											<td class="col_eliminar"
												onclick="<c:out value="${funcioneliminarOficina}"/>"><bean:message
												key="ieci.tecdoc.sgm.rpadmin.botones.eliminar" /></td>
										</logic:notEqual>
									</tr>
								</table>
							</display:column>
						</display:table>
					</logic:notEmpty>
					<input type="hidden" name="estadoOficina" id="estadoOficina" />
					<input type="hidden" name="posOficina" id="posOficina"/>
					<input type="hidden" name="idOficinaAsunto" id="idOficinaAsunto"/>
					<input type="hidden" name="idOficina" id="idOficinaAsunto"/>
					<input type="hidden" name="codigoOficina" id="codigoOficina"/>
					<input type="hidden" name="nombreOficina" id="nombreOficina"/>
					<input type="hidden" name="urlAddOficina" id="urlAddOficina"
							value='<html-el:rewrite page="/addOficinaEditarInforme.do"/>' />
					</td>
				</tr>
			</table>
		</div><!-- Fin Oficinas de registro -->
			<!-- Perfiles -->
	<div id="usuarios" style="display: none">
	<table cellpadding="0" cellspacing="0" border="0" width="100%">
		<tr>
			<td width="10" rowspan="80"></td>
			<td class="col" height="20" colspan="2"></td>
		</tr>
		<tr class="col">
			<td class="txt" valign="top"><html:checkbox
				property="usarParaTodosPerfiles" /></td>
			<td class="txt" nowrap="nowrap" valign="top"><bean:message
				key="ieci.tecdoc.sgm.rpadmin.informes.disponible.todos.perfiles" />&nbsp;&nbsp;</td>
		</tr>

		<tr>
			<td colspan="2">&nbsp;</td>
		</tr>
		<tr>
			<td colspan="2" class="col" align="right">
			<table>
				<tr>
					<td class="col_nuevo"
						onclick="chequearSessionBuscarOficinas('<html:rewrite page="/listadoSeleccionPerfiles.do"/>','<html:rewrite page="/chequearSession.do"/>','<%=AutenticacionAdministracion.obtenerUrlLogin(request, ConstantesGestionUsuariosAdministracion.APLICACION_REGISTRO) %>')">
					<bean:message key="ieci.tecdoc.sgm.rpadmin.botones.anadir" /></td>
				</tr>
			</table>
			</td>
		</tr>
		<tr>
			<td colspan="2"><logic:notEmpty name="listaPerfilesInforme">
				<display:table name="sessionScope.listaPerfilesInforme" uid="fila"
					sort="page" style="width:100%">
					<display:column titleKey="ieci.tecdoc.sgm.rpadmin.asuntos.codigo"
						sortable="false" style="width:5%;">
						<bean:write name="fila" property="idPerfil" />
					</display:column>
					<display:column titleKey="ieci.tecdoc.sgm.rpadmin.asuntos.nombre"
						sortable="false">
						<bean:write name="fila" property="nombrePerfil" />
					</display:column>

					<display:column style="width: 10%;">
						<table>
							<tr>
								<logic:equal name="fila" property="estado" value="1">
									<c:set var="funcioneliminarPerfil">
										eliminarPerfil("<html-el:rewrite
										page='/removePerfilEditarInforme.do' />","<bean:write
										name='fila_rowNum' filter='false' />","<bean:message
										key='ieci.tecdoc.sgm.rpadmin.perfil.eliminar.perfil' />","<bean:write
										name='fila' property='nombrePerfil' filter='false' />",ESTADO_ELIMINADO_NUEVO)
																		</c:set>
									<td class="col_eliminar"
										onclick="<c:out value="${funcioneliminarPerfil}"/>"><bean:message
										key="ieci.tecdoc.sgm.rpadmin.botones.eliminar" /></td>
								</logic:equal>
								<logic:notEqual name="fila" property="estado" value="1">
									<c:set var="funcioneliminarPerfil">
										eliminarPerfil("<html-el:rewrite
										page='/removePerfilEditarInforme.do' />","<bean:write
										name='fila_rowNum' filter='false' />","<bean:message
										key='ieci.tecdoc.sgm.rpadmin.perfil.eliminar.perfil' />","<bean:write
										name='fila' property='nombrePerfil' filter='false' />",ESTADO_ELIMINADO)
																		</c:set>
									<td class="col_eliminar"
										onclick="<c:out value="${funcioneliminarPerfil}"/>"><bean:message
										key="ieci.tecdoc.sgm.rpadmin.botones.eliminar" /></td>
								</logic:notEqual>
							</tr>
						</table>
					</display:column>
				</display:table>
			</logic:notEmpty>
				<input type="hidden" name="posPerfil" id="posPerfil" />
				<input type="hidden" name="idPerfil" id="idPerfil" />
				<input type="hidden" name="nombrePerfil" id="nombrePerfil" />
				<input type="hidden" name="estadoPerfil" id="estadoPerfil" />
				<input	type="hidden" name="urlAddPerfil" id="urlAddPerfil"
				value='<html-el:rewrite page="/addPerfilEditarInforme.do"/>' />
			</td>
		</tr>

	</table>
	</div><!-- Perfiles -->
	<!-- Libros -->
	<div id="libros" style="display: none">
		<table cellpadding="0" cellspacing="0" border="0" width="100%">
			<tr>
				<td width="10" rowspan="80"></td>
				<td class="col" height="20" colspan="2"></td>
			</tr>
			<tr class="col">
				<td class="txt" valign="top"><html:checkbox
					property="usarParaTodosLibros" /></td>
				<td class="txt" nowrap="nowrap" valign="top"><bean:message
					key="ieci.tecdoc.sgm.rpadmin.informes.disponible.todos.libros" />&nbsp;&nbsp;</td>
			</tr>
			<tr>
				<td colspan="2">&nbsp;</td>
			</tr>
			<tr>
				<td colspan="2" class="col" align="right">
					<table>
						<tr>
							<td class="col_nuevo"
								onclick="chequearSessionBuscar('<html:rewrite page="/listadoSeleccionLibros.do"/>','<html:rewrite page="/chequearSession.do"/>','<%=AutenticacionAdministracion.obtenerUrlLogin(request, ConstantesGestionUsuariosAdministracion.APLICACION_REGISTRO) %>')">
								<bean:message key="ieci.tecdoc.sgm.rpadmin.botones.anadir" /></td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td colspan="2">
				<logic:notEmpty name="listaLibrosInforme">
					<display:table name="sessionScope.listaLibrosInforme" uid="fila"
						sort="page" style="width:100%">
						<display:column titleKey="ieci.tecdoc.sgm.rpadmin.asuntos.codigo"
							sortable="false" style="width:5%;">
							<bean:write name="fila" property="codigoArchivo" />
						</display:column>
						<display:column titleKey="ieci.tecdoc.sgm.rpadmin.asuntos.nombre"
							sortable="false">
							<bean:write name="fila" property="nombreArchivo" />
						</display:column>
						<display:column style="width:10%;">
							<table>
								<tr>
									<logic:equal name="fila" property="estado" value="1">
										<c:set var="funcioneliminarLibro">
											eliminarLibro("<html-el:rewrite
											page='/removeLibroEditarInforme.do' />","<bean:write
											name='fila_rowNum' filter='false' />","<bean:message
											key='ieci.tecdoc.sgm.rpadmin.informes.eliminar.libro' />","<bean:write
											name='fila' property='nombreArchivo' filter='false' />",ESTADO_ELIMINADO_NUEVO)
										</c:set>
										<td class="col_eliminar"
											onclick="<c:out value="${funcioneliminarLibro}"/>"><bean:message
											key="ieci.tecdoc.sgm.rpadmin.botones.eliminar" /></td>
									</logic:equal>
									<logic:notEqual name="fila" property="estado" value="1">
										<c:set var="funcioneliminarLibro">
											eliminarLibro("<html-el:rewrite
											page='/removeLibroEditarInforme.do' />","<bean:write
											name='fila_rowNum' filter='false' />","<bean:message
											key='ieci.tecdoc.sgm.rpadmin.informes.eliminar.libro' />","<bean:write
											name='fila' property='nombreArchivo' filter='false' />",ESTADO_ELIMINADO)
										</c:set>
										<td class="col_eliminar"
											onclick="<c:out value="${funcioneliminarLibro}"/>"><bean:message
											key="ieci.tecdoc.sgm.rpadmin.botones.eliminar" /></td>
									</logic:notEqual>
								</tr>
							</table>
						</display:column>
					</display:table>
				</logic:notEmpty>
				<input type="hidden" name="posLibro" id="posLibro" />
				<input type="hidden" name="idLibro" id="idLibro" />
				<input type="hidden" name="nombreLibro" id="nombreLibro" />
				<input type="hidden" name="estadoLibro" id="estadoLibro" />
				<input	type="hidden" name="urlAddLibro" id="urlAddLibro"
				value='<html-el:rewrite page="/addLibroEditarInforme.do"/>' />
				</td>
			</tr>
		</table>
	</div>
	<!-- Libros --></div>
	<!-- Estile --></div>
	<!-- Cuadro Usuario --></div>
	<!-- cuerpomidUsuarios --></div>
	<!-- cuadro --></div>
	<!-- cuerpomid --></div>
	<!-- cuerporight --></div>
	<!-- cuerpo -->
	<div id="pie"></div>
	</div>
	<!-- Contenido --></div>
	<!-- contenedora -->
</html:form>
</body>
</html>
