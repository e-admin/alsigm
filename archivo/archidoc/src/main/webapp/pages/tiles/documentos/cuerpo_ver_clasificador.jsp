<%@ taglib uri="/WEB-INF/struts-menu-el.tld" prefix="menu" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo"%>
<%@ taglib uri="/WEB-INF/taglibs/securityTag.tld" prefix="security"%>

<bean:struts id="actionMapping" mapping="/clasificador" />
<bean:struts id="actionMappingDigital" mapping="/gestionTareasDigitalizacion" />

<c:set var="unidadDocumental" value="${requestScope[appConstants.documentos.UNIDAD_DOCUMENTAL_KEY]}"/>
<c:set var="serie" value="${sessionScope[appConstants.documentos.SERIE_KEY]}"/>
<c:set var="descriptor" value="${requestScope[appConstants.documentos.DESCRIPTOR_KEY]}"/>
<c:set var="contenido" value="${requestScope[appConstants.documentos.FOLDER_CONTENT_KEY]}"/>
<c:set var="tarea" value="${sessionScope[appConstants.documentos.TAREA_KEY]}"/>

<c:set var="treeView" value="${sessionScope[appConstants.documentos.DOCUMENT_TREE_KEY]}" />
<c:set var="parentNode" value="${treeView.selectedNode.parent}" />

<c:choose>
	<c:when test="${!empty unidadDocumental}">
		<c:set var="contenedorDocs" value="${unidadDocumental}"/>
	</c:when>
	<c:when test="${!empty serie}">
		<c:set var="contenedorDocs" value="${serie}"/>
	</c:when>
	<c:otherwise>
		<c:set var="contenedorDocs" value="${descriptor}"/>
	</c:otherwise>
</c:choose>

<%--CONTROL DE ACCESO Y PERMISOS SOBRE EL OBJETO CONTENEDOR DE DOCUMENTOS --%>

<%--permiso de administracion de tareas (validacion, finalizacion) --%>
<c:set var="permisoAdministracionTarea" value="false"/>
<security:permissions action="${appConstants.documentosActions.ADMINISTRACION_TAREAS_CAPTURA_ACTION}">
<c:set var="permisoAdministracionTarea" value="true"/>
</security:permissions>


<c:set var="tienePermisoEdicionEnObjeto" value="false"/>
<security:permissions action="${appConstants.documentosActions.EDICION_DOCUMENTOS_CUADRO_CLASIFICACION_ACTION}">
	<c:set var="tienePermisoEdicionEnObjeto" value="true"/>
</security:permissions>
<c:if test="${permisoAdministracionTarea}">
	<c:set var="tienePermisoEdicionEnObjeto" value="true"/>
</c:if>

<c:set var="accesoPermitido" value="false"/>
<security:access object="${contenedorDocs}"
	permission="${appConstants.permissions.EDICION_DOCUMENTOS_CUADRO_CLASIFICACION}">
	<c:set var="accesoPermitido" value="true"/>
</security:access>



<script language="JavaScript1.2" type="text/JavaScript">
	function remove()
	{
		<c:url var="URL" value="/action/clasificador">
			<c:param name="method" value="remove" />
   			<c:param name="id" value="${clasificadorForm.id}" />
   			<c:param name="idObjeto" value="${clasificadorForm.idObjeto}" />
   			<c:param name="tipoObjeto" value="${clasificadorForm.tipoObjeto}" />
   		</c:url>
		if (confirm("<bean:message key="archigest.archivo.documentos.clasificador.delete.confirm.msg"/>"))
			window.location.href = "<c:out value="${URL}" escapeXml="false"/>";
	}
	function addClasificador()
	{
		<c:url var="URL" value="/action/clasificador">
			<c:param name="method" value="form" />
   			<c:param name="idObjeto" value="${clasificadorForm.idObjeto}" />
   			<c:param name="tipoObjeto" value="${clasificadorForm.tipoObjeto}" />
   			<c:param name="idClfPadre" value="${clasificadorForm.id}" />
   		</c:url>
		window.location.href = "<c:out value="${URL}" escapeXml="false"/>";
	}
	function removeContenido()
	{
		var form = document.forms["<c:out value="${actionMapping.name}" />"];
		if ((form.eliminarClasificador && elementSelected(form.eliminarClasificador)) ||
			(form.eliminarDocumento && elementSelected(form.eliminarDocumento))) {
			if (confirm("<bean:message key="archigest.archivo.documentos.carpeta_documento.delete.confirm.msg"/>")) {
				form.method.value = "removeContenido";
				form.submit();
			}
		} else
			alert("<bean:message key='archigest.archivo.documentos.carpeta_documento.delete.warning.msg'/>");
	}
	function addDocumento()
	{
		<c:url var="URL" value="/action/documento">
			<c:param name="method" value="form" />
   			<c:param name="idObjeto" value="${clasificadorForm.idObjeto}" />
   			<c:param name="tipoObjeto" value="${clasificadorForm.tipoObjeto}" />
   			<c:param name="idClfPadre" value="${clasificadorForm.id}" />
   		</c:url>
		window.location.href = "<c:out value="${URL}" escapeXml="false"/>";
	}
	function validar(){
		var form = document.forms["<c:out value="${actionMapping.name}" />"];
		form.method.value = "validarElemento";
		form.submit();
	}
	function invalidar(){
		var form = document.forms["<c:out value="${actionMapping.name}" />"];
		form.method.value = "invalidarElemento";
		form.submit();
	}

	function eliminarTarea(){
		<c:url var="urlEliminar" value="/action/gestionTareasDigitalizacion">
			<c:param name="method" value="eliminarTarea"/>
			<c:param name="idTarea" value="${tarea.id}"/>
		</c:url>
		if (confirm("<bean:message key="archigest.archivo.documentos.digitalizacion.delete.confirm.msg"/>")) {
			window.location="<c:out value="${urlEliminar}" escapeXml="false" />";
		}
	}

	function finalizarValidacion(){
		<c:url var="urlFinalizar" value="/action/gestionTareasDigitalizacion">
			<c:param name="method" value="finalizarValidacionTarea"/>
			<c:param name="idTarea" value="${tarea.id}"/>
		</c:url>
		if (confirm("<bean:message key="archigest.archivo.documentos.digitalizacion.finValidacion.confirm.msg"/>")) {
			window.location='<c:out value="${urlFinalizar}" escapeXml="false"/>';
		}

	}

</script>

<div class="content_container">
<html:form action="/clasificador">

	<html:hidden property="id"/>
	<html:hidden property="idObjeto"/>
	<html:hidden property="tipoObjeto"/>
	<input type="hidden" name="method"/>

	<div class="ficha">
		<h1><span>
			<div class="w100">
				<table class="w98" cellpadding=0 cellspacing=0>
					<tr>
					<td class="etiquetaAzul12Bold" height="25px">
						<bean:message key="archigest.archivo.documentos.caption"/>
					</td>
					<td align="right">
							<table cellpadding=0 cellspacing=0>
								<tr>
								<c:if test="${not(clasificadorForm.root)}">
									<td nowrap="nowrap">
											<c:url var="URL" value="/action/clasificador">
												<c:param name="method" value="retrieve" />
												<c:param name="id" value="${clasificadorForm.idClfPadre}" />
												<c:param name="idObjeto" value="${clasificadorForm.idObjeto}" />
												<c:param name="tipoObjeto" value="${clasificadorForm.tipoObjeto}" />
												<c:param name="node" value="${parentNode.nodePath}" />
												<c:param name="refresh" value="1" />
											</c:url>
											<a class="etiquetaAzul12Bold"
											   href="<c:out value="${URL}" escapeXml="false"/>"
											><html:img page="/pages/images/folder-up.gif"
												border="0"
												altKey="archigest.archivo.cf.verPadre"
												titleKey="archigest.archivo.cf.verPadre"
												styleClass="imgTextMiddle"/>&nbsp;<bean:message key="archigest.archivo.cf.verPadre"/></a>
									</td>
									<td width="10">&nbsp;</td>
								</c:if>


								<c:if test="${tienePermisoEdicionEnObjeto && accesoPermitido && !empty tarea && tarea.finalizable}">
									<c:url var="urlFinalizar" value="/action/gestionTareasDigitalizacion">
										<c:param name="method" value="finalizarCaptura"/>
										<c:param name="idTarea" value='<c:out value="${tarea.id}"/>'/>
									</c:url>
									<td><a class="etiquetaAzul12Bold" href="<c:out value="${urlFinalizar}" escapeXml="false"/>">
										<html:img page="/pages/images/aceptar.gif" styleClass="imgTextMiddle"/>&nbsp;Finalizar Captura</a></td>
									<td width="10">&nbsp;</td>
								</c:if>


								<c:if test="${permisoAdministracionTarea}">
									<c:if test="${!empty tarea}">
										<c:if test="${tarea.validacionFinalizable}">
											<td><a class="etiquetaAzul12Bold" href="javascript:finalizarValidacion();">
												<html:img
												page="/pages/images/finCorrecion.gif"
												styleClass="imgTextMiddle"/>&nbsp;Finalizar Validación</a></td>
											<td width="10">&nbsp;</td>
										</c:if>
										<c:if test="${tarea.eliminable}">
											<td><a class="etiquetaAzul12Bold" href="javascript:eliminarTarea()">
												<html:img
												page="/pages/images/delete.gif"
												styleClass="imgTextMiddle"/>&nbsp;Eliminar Tarea</a></td>
											<td width="10">&nbsp;</td>
										</c:if>
									</c:if>
								</c:if>
									<td nowrap="nowrap">
										<tiles:insert definition="button.closeButton" flush="false" />
									</td>
								</tr>
							</table>
						</td>
					</tr>
				</table>
			</div>
		</span></h1>

		<div class="cuerpo_drcha">
		<div class="cuerpo_izda">

			<div id="barra_errores"><archivo:errors /></div>
			<div class="separador1">&nbsp;</div>


			<c:choose>
			<c:when test="${!empty tarea}">
				<tiles:insert template="info_tarea.jsp" flush="true"/>
			</c:when>
			<c:otherwise>
				<c:if test="${!empty unidadDocumental}">
					<tiles:insert template="info_udoc.jsp" flush="true"/>
				</c:if>

				<c:if test="${!empty serie}">
					<tiles:insert template="info_serie.jsp" flush="true"/>
				</c:if>

				<c:if test="${!empty descriptor}">
					<tiles:insert template="info_descriptor.jsp" flush="true"/>
				</c:if>
			</c:otherwise>
			</c:choose>


			<c:if test="${not(clasificadorForm.root)}">
			<div class="cabecero_bloque">
				<table class="w98m1" cellpadding="0" cellspacing="0">
					<tbody>
						<tr>
							<td class="etiquetaAzul12Bold">
								<bean:message key="archigest.archivo.documentos.clasificador.caption"/>
							</td>


					<c:if test="${tienePermisoEdicionEnObjeto && accesoPermitido}">
							<td align="right">
								<table cellpadding=0 cellspacing=0>
									<tr>
										<td>
											<c:url var="URL" value="/action/clasificador">
												<c:param name="method" value="form" />
												<c:param name="id" value="${clasificadorForm.id}" />
												<c:param name="idObjeto" value="${clasificadorForm.idObjeto}" />
												<c:param name="tipoObjeto" value="${clasificadorForm.tipoObjeto}" />
											</c:url>
											<a class="etiquetaAzul12Normal" href="<c:out value="${URL}" escapeXml="false"/>">
											<html:img page="/pages/images/editDoc.gif"
											border="0"
											altKey="archigest.archivo.editar"
											titleKey="archigest.archivo.editar"
											styleClass="imgTextBottom"/>&nbsp;<bean:message key="archigest.archivo.editar"/>
											</a></td>
										<td width="10">&nbsp;</td>
										<c:if test="${clasificadorForm.marcas != 1}">
										<td><a class="etiquetaAzul12Normal"
											href="javascript:remove()"
											><html:img
													page="/pages/images/deleteDoc.gif"
											border="0"
											altKey="archigest.archivo.eliminar"
											titleKey="archigest.archivo.eliminar"
											styleClass="imgTextBottom"/>&nbsp;<bean:message key="archigest.archivo.eliminar"/></a></td>
										<td width="10">&nbsp;</td>
										</c:if>
									</tr>
								</table>
							</td>
					</c:if>
						</tr>
					</tbody>
				</table>
			</div>

			<div class="bloque">
				<table class="formulario">
					<tr>
						<td class="tdTitulo" width="200px"><bean:message key="archigest.archivo.documentos.clasificador.form.nombre"/>:&nbsp;</td>
						<td class="tdDatos"><c:out value="${clasificadorForm.nombre}"/>&nbsp;</td>
					</tr>
					<tr>
						<td class="tdTitulo"><bean:message key="archigest.archivo.documentos.clasificador.form.descripcion"/>:&nbsp;</td>
						<td class="tdDatos"><c:out value="${clasificadorForm.descripcion}"/>&nbsp;</td>
					</tr>
					<c:if test="${!empty tarea }">
						<tr><td class="separador5" colspan="2">&nbsp;</td></tr>
						<tr>
							<td class="tdTitulo"><bean:message key="archigest.archivo.documentos.clasificador.form.estado"/>:&nbsp;</td>
							<td class="tdDatos">
								<c:choose>
									<c:when test="${clasificadorForm.estado == 0}">
										<bean:message key="archigest.archivo.documentos.estado.sinValidar"/>
									</c:when>
									<c:when test="${clasificadorForm.estado == 1}">
										<bean:message key="archigest.archivo.documentos.estado.validado"/>
									</c:when>
									<c:when test="${clasificadorForm.estado == 2}">
										<bean:message key="archigest.archivo.documentos.estado.noValido"/>
									</c:when>
									<c:when test="${clasificadorForm.estado == 3}">
										<bean:message key="archigest.archivo.documentos.estado.publicado"/>
									</c:when>
									<c:otherwise>&nbsp;</c:otherwise>
								</c:choose>
							</td>
						</tr>
					</c:if>

				</table>
			</div>
			<div class="separador8">&nbsp;</div>
			</c:if>

			<div class="cabecero_bloque">
				<table class="w98m1" cellpadding="0" cellspacing="0" height="100%">
					<tbody>
						<tr>
							<td class="etiquetaAzul12Bold">
								<bean:message key="archigest.archivo.documentos.clasificador.contenido.caption"/>
							</td>



							<c:if test="${tienePermisoEdicionEnObjeto}">
								<c:set var="permitidaModificacionElementos" value="false"/>
								<c:if test="${accesoPermitido}">
									<c:set var="permitidaModificacionElementos" value="true"/>
								</c:if>

								<c:if test="${!empty tarea && accesoPermitido}">
									<c:set var="permitidaModificacionElementos" value="${tarea.modificablePorElementos}"/>
								</c:if>

								<td align="right">
									<table cellpadding=0 cellspacing=0>
										<tr>
											<c:if test="${permitidaModificacionElementos }">
												<td><a class="etiquetaAzul12Normal" href="javascript:addClasificador()">
												<html:img
												page="/pages/images/new_plus.gif"
												border="0"
												altKey="archigest.archivo.documentos.clasificador.addClasificador.caption"
												titleKey="archigest.archivo.documentos.clasificador.addClasificador.caption"
												styleClass="imgTextMiddle"/>&nbsp;<bean:message key="archigest.archivo.documentos.clasificador.addClasificador.caption"/></a></td>
												<td width="10">&nbsp;</td>
												<td><a class="etiquetaAzul12Normal" href="javascript:addDocumento()">
												<html:img
												page="/pages/images/addDoc.gif"
												border="0"
												altKey="archigest.archivo.documentos.clasificador.addDocumento.caption"
												titleKey="archigest.archivo.documentos.clasificador.addDocumento.caption"
												styleClass="imgTextMiddle"/>&nbsp;<bean:message key="archigest.archivo.documentos.clasificador.addDocumento.caption"/></a></td>
												<td width="10">&nbsp;</td>

												<c:if test="${!empty contenido}">
													<td><a class="etiquetaAzul12Normal" href="javascript:removeContenido()">
													<html:img
													page="/pages/images/delDocFolder.gif"
													border="0"
													altKey="archigest.archivo.eliminar"
													titleKey="archigest.archivo.eliminar"
													styleClass="imgTextMiddle"/>&nbsp;<bean:message key="archigest.archivo.eliminar"/></a></td>
													<td width="10">&nbsp;</td>
												</c:if>

											</c:if>


											<c:if test="${!empty tarea && tarea.validable && permisoAdministracionTarea}">
												<td><a class="etiquetaAzul12Bold" href="javascript:validar();">
													<html:img
													page="/pages/images/cajaRevisada.gif"
													styleClass="imgTextMiddle"/>&nbsp;<bean:message key="archigest.archivo.documentos.marcarValido"/></a></td>
												<td width="10">&nbsp;</td>
												<td><a class="etiquetaAzul12Bold" href="javascript:invalidar();">
													<html:img
													page="/pages/images/cajaError.gif"
													styleClass="imgTextMiddle"/>&nbsp;<bean:message key="archigest.archivo.documentos.marcarNoValido"/></a></td>
												<td width="10">&nbsp;</td>
											</c:if>
										</tr>
									</table>
								</td>
							</c:if>
						</tr>
					</tbody>
				</table>
			</div>

			<div class="bloque">

				<display:table name="pageScope.contenido"
					style="width:99%;margin-left:auto;margin-right:auto"
					id="objeto"
					pagesize="15"
					requestURI="/action/clasificador?method=retrieve"
					export="false">
					<display:setProperty name="basic.msg.empty_list">
						<bean:message key="archigest.archivo.documentos.clasificador.contenidoVacio"/>
					</display:setProperty>

				<c:set var="tienePermisoEnObjeto" value="false"/>
				<security:permissions action="${appConstants.documentosActions.EDICION_DOCUMENTOS_CUADRO_CLASIFICACION_ACTION}">
					<c:set var="tienePermisoEnObjeto" value="true"/>
				</security:permissions>
				<security:permissions action="${appConstants.documentosActions.ADMINISTRACION_TAREAS_CAPTURA_ACTION}">
					<c:set var="tienePermisoEnObjeto" value="true"/>
				</security:permissions>

				<c:set var="elementoSeleccionable" value="false"/>
				<c:if test="${tienePermisoEnObjeto}">
					<c:set var="accesoPermitido" value="false"/>
					<security:access object="${contenedorDocs}" permission="${appConstants.permissions.EDICION_DOCUMENTOS_CUADRO_CLASIFICACION}">
						<c:set var="accesoPermitido" value="true"/>
						<c:set var="elementoSeleccionable" value="true" />
					</security:access>
					<c:if test="${!empty tarea && accesoPermitido}">
						<c:set var="elementoSeleccionable" value="${tarea.modificablePorElementos || objeto.validable}"/>
					</c:if>
				</c:if>

						<display:column style="width:23px">
						<c:if test="${elementoSeleccionable}">
							<c:choose>
								<c:when test="${objeto.class.name=='docelectronicos.actions.DocClasificadorPO'}">
									<c:if test="${objeto.eliminable || objeto.validable}">
										<input type="checkbox" name="eliminarClasificador"
											value="<c:out value="${objeto.id}"/>"/>
									</c:if>
								</c:when>
								<c:otherwise>
									<c:if test="${objeto.eliminable || objeto.validable}">
										<input type="checkbox" name="eliminarDocumento"
										value="<c:out value="${objeto.id}"/>"/>
									</c:if>
								</c:otherwise>
							</c:choose>
						</c:if>
						</display:column>

					<display:column titleKey="archigest.archivo.nombre" sortProperty="nombre" sortable="true" headerClass="sortable">
						<c:choose>
						<c:when test="${objeto.class.name=='docelectronicos.actions.DocClasificadorPO'}">
							<c:set var="parentNode" value="${treeView.selectedNode}" />
							<c:url var="URL" value="/action/clasificador">
								<c:param name="method" value="retrieve" />
								<c:param name="id" value="${objeto.id}" />
								<c:param name="idObjeto" value="${objeto.idObjeto}" />
								<c:param name="tipoObjeto" value="${objeto.tipoObjeto}" />
								<c:param name="node" value="${parentNode.nodePath}" />
								<c:param name="verContent" value="1" />
								<c:param name="refresh" value="1" />
							</c:url>
							<html:img page="/pages/images/arboles/folder-close.gif" border="0" styleClass="imgTextBottom"/>
						</c:when>
						<c:otherwise>
							<c:url var="URL" value="/action/documento">
								<c:param name="method" value="retrieve" />
								<c:param name="id" value="${objeto.id}" />
								<c:param name="idObjeto" value="${objeto.idObjeto}" />
								<c:param name="tipoObjeto" value="${objeto.tipoObjeto}" />
							</c:url>
							<c:choose>
								<c:when test="${objeto.extFich=='doc' || objeto.extFich=='docx'}">
									<html:img page="/pages/images/documentos/doc_doc.gif" border="0" styleClass="imgTextBottom"/>
								</c:when>
								<c:when test="${objeto.extFich=='pdf'}">
									<html:img page="/pages/images/documentos/doc_pdf.gif" border="0" styleClass="imgTextBottom"/>
								</c:when>
								<c:when test="${objeto.extFich=='jpg'||objeto.extFich=='jpeg'||objeto.extFich=='jpe' ||objeto.extFich=='png'}">
									<html:img page="/pages/images/documentos/doc_jpg.gif" border="0" styleClass="imgTextBottom"/>
								</c:when>
								<c:when test="${objeto.extFich=='tif'||objeto.extFich=='tiff'}">
									<html:img page="/pages/images/documentos/doc_tif.gif" border="0" styleClass="imgTextBottom"/>
								</c:when>
								<c:when test="${objeto.extFich=='zip'}">
									<html:img page="/pages/images/documentos/doc_zip.gif" border="0" styleClass="imgTextBottom"/>
								</c:when>
								<c:when test="${objeto.extFich=='xls' || objeto.extFich=='xlsx'}">
									<html:img page="/pages/images/documentos/doc_xls.gif" border="0" styleClass="imgTextBottom"/>
								</c:when>
								<c:when test="${objeto.extFich=='ppt'}">
									<html:img page="/pages/images/documentos/doc_ppt.gif" border="0" styleClass="imgTextBottom"/>
								</c:when>
								<c:when test="${objeto.extFich=='bmp'}">
									<html:img page="/pages/images/documentos/doc_bmp.gif" border="0" styleClass="imgTextBottom"/>
								</c:when>
								<c:when test="${objeto.extFich=='exe'}">
									<html:img page="/pages/images/documentos/doc_exe.gif" border="0" styleClass="imgTextBottom"/>
								</c:when>
								<c:when test="${objeto.extFich=='gif'}">
									<html:img page="/pages/images/documentos/doc_gif.gif" border="0" styleClass="imgTextBottom"/>
								</c:when>
								<c:when test="${objeto.extFich=='mp3'}">
									<html:img page="/pages/images/documentos/doc_mp3.gif" border="0" styleClass="imgTextBottom"/>
								</c:when>
								<c:when test="${objeto.extFich=='rar'}">
									<html:img page="/pages/images/documentos/doc_rar.gif" border="0" styleClass="imgTextBottom"/>
								</c:when>
								<c:when test="${objeto.extFich=='txt'}">
									<html:img page="/pages/images/documentos/doc_txt.gif" border="0" styleClass="imgTextBottom"/>
								</c:when>
								<c:when test="${objeto.extFich=='wav'}">
									<html:img page="/pages/images/documentos/doc_wav.gif" border="0" styleClass="imgTextBottom"/>
								</c:when>
								<c:when test="${objeto.extFich=='html' || objeto.extFich=='htm'}">
									<html:img page="/pages/images/documentos/doc_html.gif" border="0" styleClass="imgTextBottom"/>
								</c:when>
								<c:otherwise>
									<html:img page="/pages/images/documentos/doc_unknown.gif" border="0" styleClass="imgTextBottom"/>
								</c:otherwise>
							</c:choose>
						</c:otherwise>
						</c:choose>
						<a class="tdlink" href="<c:out value="${URL}" escapeXml="false"/>"><c:out value="${objeto.nombre}"/></a>
					</display:column>
					<display:column titleKey="archigest.archivo.tipo" sortable="true" headerClass="sortable">
						<c:choose>
						<c:when test="${objeto.class.name=='docelectronicos.actions.DocClasificadorPO'}">
							<bean:message key="archigest.archivo.documentos.carpeta"/>
						</c:when>
						<c:otherwise>
							<c:out value="${objeto.extFich}"/>
						</c:otherwise>
						</c:choose>
					</display:column>
					<display:column titleKey="archigest.archivo.documentos.documento.form.tamano" sortProperty="tamanoFich" sortable="true" headerClass="sortable">
						<c:if test="${objeto.class.name=='docelectronicos.actions.DocDocumentoPO'}">
							<c:choose>
							<c:when test="${objeto.tamanoFich >= 0}">
								<fmt:formatNumber value="${objeto.tamanoFich/1024}" pattern="#,###.##"/>&nbsp;
								<bean:message key="archigest.archivo.documentos.documento.form.tamano.unidades"/>
							</c:when>
							<c:otherwise>--</c:otherwise>
							</c:choose>
						</c:if>
					</display:column>

					<display:column titleKey="archigest.archivo.documentos.documento.form.estado" sortProperty="estado" sortable="true" headerClass="sortable">
						<c:choose>
							<c:when test="${objeto.estado == 0}">
								<bean:message key="archigest.archivo.documentos.estado.sinValidar"/>
							</c:when>
							<c:when test="${objeto.estado == 1}">
								<bean:message key="archigest.archivo.documentos.estado.validado"/>
							</c:when>
							<c:when test="${objeto.estado == 2}">
								<bean:message key="archigest.archivo.documentos.estado.noValido"/>
							</c:when>
							<c:when test="${objeto.estado == 3}">
								<bean:message key="archigest.archivo.documentos.estado.publicado"/>
							</c:when>
							<c:otherwise>&nbsp;</c:otherwise>
						</c:choose>
					</display:column>

					<c:if test="${!empty unidadDocumental || !empty serie}">
					<display:column titleKey="archigest.archivo.documentos.documento.form.deposito" style="width:20px; text-align:center;">
						<c:choose>
							<c:when test="${objeto.class.name=='docelectronicos.actions.DocDocumentoPO'}">
								<c:choose>
									<c:when test="${!empty objeto.idExtDeposito}">
										<c:out value="${objeto.idExtDeposito}"/>
									</c:when>
									<c:otherwise>
										<c:out value="-"/>
									</c:otherwise>
								</c:choose>
							</c:when>
							<c:otherwise>
								<c:out value="--"/>
							</c:otherwise>
						</c:choose>
					</display:column>
					</c:if>

					<display:column titleKey="archigest.archivo.documentos.documento.form.descripcion" property="descripcion" maxLength="100"/>
				</display:table>
			 </div>
		</div>
		</div>

		<h2><span>&nbsp;</span></h2>
	</div>
</html:form>
</div>