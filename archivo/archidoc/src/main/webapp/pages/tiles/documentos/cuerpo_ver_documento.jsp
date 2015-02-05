<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo"%>
<%@ taglib uri="/WEB-INF/taglibs/securityTag.tld" prefix="security"%>

<c:set var="unidadDocumental" value="${requestScope[appConstants.documentos.UNIDAD_DOCUMENTAL_KEY]}"/>
<c:set var="serie" value="${sessionScope[appConstants.documentos.SERIE_KEY]}"/>
<c:set var="descriptor" value="${requestScope[appConstants.documentos.DESCRIPTOR_KEY]}"/>
<c:set var="clasificador" value="${requestScope[appConstants.documentos.FOLDER_KEY]}"/>
<c:set var="infoFichero" value="${requestScope[appConstants.documentos.FILE_INFO_KEY]}"/>




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

<script language="JavaScript1.2" type="text/JavaScript">
	function remove()
	{
		<c:url var="URL" value="/action/documento">
			<c:param name="method" value="remove" />
   			<c:param name="id" value="${documentoForm.id}" />
   			<c:param name="idObjeto" value="${documentoForm.idObjeto}" />
   			<c:param name="tipoObjeto" value="${documentoForm.tipoObjeto}" />
   		</c:url>
		if (confirm("<bean:message key="archigest.archivo.documentos.documento.delete.confirm.msg"/>"))
			window.location.href = "<c:out value="${URL}" escapeXml="false"/>";
	}
	function validar(){
		<c:url var="URL" value="/action/clasificador">
			<c:param name="method" value="validarElemento" />
			<c:param name="eliminarDocumento" value="${documentoForm.id}" />
		</c:url>
		window.location.href="<c:out value="${URL}" escapeXml="false"/>";
	}
	function invalidar(){
		<c:url var="URL" value="/action/clasificador">
			<c:param name="method" value="invalidarElemento" />
			<c:param name="eliminarDocumento" value="${documentoForm.id}" />
		</c:url>
		window.location.href="<c:out value="${URL}" escapeXml="false"/>";
	}

</script>

<div class="content_container">
<html:form action="/documento">

	<html:hidden property="id"/>
	<html:hidden property="idObjeto"/>
	<html:hidden property="tipoObjeto"/>

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
									<td>
										<c:url var="URL" value="/action/clasificador">
											<c:param name="method" value="retrieve" />
											<c:param name="id" value="${documentoForm.idClfPadre}" />
											<c:param name="idObjeto" value="${documentoForm.idObjeto}" />
											<c:param name="tipoObjeto" value="${documentoForm.tipoObjeto}" />
										</c:url>
										<a class="etiquetaAzul12Bold" href="<c:out value="${URL}" escapeXml="false"/>"
										><html:img page="/pages/images/folder-up.gif"
										        border="0"
										        altKey="archigest.archivo.cf.verPadre"
										        titleKey="archigest.archivo.cf.verPadre"
										        styleClass="imgTextMiddle"/>&nbsp;<bean:message key="archigest.archivo.cf.verPadre"/></a>
									</td>
									<td width="10">&nbsp;</td>
								   	<td nowrap="true">
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

			<c:set var="tarea" value="${sessionScope[appConstants.documentos.TAREA_KEY]}"/>
			<bean:define id="dockableContent" value="false" toScope="request"/>

			<c:choose>
			<c:when test="${!empty tarea}">
				<tiles:insert template="/pages/tiles/documentos/info_tarea.jsp" flush="false">
					<tiles:put name="dockableContent" direct="true"><c:out value="${dockableContent}"/></tiles:put>
				</tiles:insert>
			</c:when>
			<c:otherwise>
				<c:if test="${!empty unidadDocumental}">
					<tiles:insert template="/pages/tiles/documentos/info_udoc.jsp" flush="false">
						<tiles:put name="dockableContent" direct="true"><c:out value="${dockableContent}"/></tiles:put>
					</tiles:insert>
				</c:if>

				<c:if test="${!empty serie}">
					<tiles:insert template="/pages/tiles/documentos/info_serie.jsp" flush="false">
						<tiles:put name="dockableContent" direct="true"><c:out value="${dockableContent}"/></tiles:put>
					</tiles:insert>
				</c:if>

				<c:if test="${!empty descriptor}">
					<tiles:insert template="/pages/tiles/documentos/info_descriptor.jsp" flush="false">
						<tiles:put name="dockableContent" direct="true"><c:out value="${dockableContent}"/></tiles:put>
					</tiles:insert>
				</c:if>
			</c:otherwise>
			</c:choose>


			<c:choose>
				<c:when test="${!empty documentoForm.idClfPadre}">

					<tiles:insert page="/pages/tiles/PADockableBlockLayout.jsp" flush="false">
						<tiles:put name="blockName" direct="true">clasificador</tiles:put>
						<tiles:put name="blockTitle" direct="true"><bean:message key="archigest.archivo.documentos.clasificador.caption"/></tiles:put>
						<tiles:put name="buttonBar" direct="true">
							<tiles:insert page="/pages/tiles/documentos/cuerpo_botonera_navegacion_documentos.jsp" ignore="false"/>
						</tiles:put>

						<tiles:put name="dockableContent" direct="true">
							<table class="formulario">
								<tr>
									<td class="tdTitulo" width="200px"><bean:message key="archigest.archivo.documentos.clasificador.form.nombre"/>:&nbsp;</td>
									<td class="tdDatos"><c:out value="${clasificador.nombre}" />&nbsp;</td>
								</tr>
								<tr>
									<td class="tdTitulo"><bean:message key="archigest.archivo.documentos.clasificador.form.descripcion"/>:&nbsp;</td>
									<td class="tdDatos"><c:out value="${clasificador.descripcion}"/>&nbsp;</td>
								</tr>
								<c:if test="${!empty tarea}">
									<tr><td class="separador5" colspan="2">&nbsp;</td></tr>
									<tr>
										<td class="tdTitulo"><bean:message key="archigest.archivo.documentos.clasificador.form.estado"/>:&nbsp;</td>
										<td class="tdDatos">
											<c:choose>
												<c:when test="${clasificador.estado == 0}">
													<bean:message key="archigest.archivo.documentos.estado.sinValidar"/>
												</c:when>
												<c:when test="${clasificador.estado == 1}">
													<bean:message key="archigest.archivo.documentos.estado.validado"/>
												</c:when>
												<c:when test="${clasificador.estado == 2}">
													<bean:message key="archigest.archivo.documentos.estado.noValido"/>
												</c:when>
												<c:when test="${clasificador.estado == 3}">
													<bean:message key="archigest.archivo.documentos.estado.publicado"/>
												</c:when>
												<c:otherwise>&nbsp;</c:otherwise>
											</c:choose>
										</td>
									</tr>
								</c:if>
							</table>
						</tiles:put>
					</tiles:insert>

				</c:when>
				<c:otherwise>
					<div class="cabecero_bloque">
					<table class="w98m1" cellpadding="0" cellspacing="0">
						<tbody>
							<tr>
								<td class="etiquetaAzul12Bold">
									<bean:message key="archigest.archivo.documentos.clasificador.caption"/>
								</td>
								<td align="right">
									<tiles:insert page="/pages/tiles/documentos/cuerpo_botonera_navegacion_documentos.jsp" ignore="false"/>
								</td>
							</tr>
						</tbody>
					</table>
					</div>
				</c:otherwise>
			</c:choose>

			<div class="separador8">&nbsp;</div>

			<tiles:insert page="/pages/tiles/PADockableBlockLayout.jsp"  flush="false">
				<tiles:put name="blockName" direct="true">documento</tiles:put>
				<tiles:put name="blockTitle" direct="true"><bean:message key="archigest.archivo.documentos.documento.caption"/></tiles:put>
				<tiles:put name="buttonBar" direct="true">
								<table cellpadding=0 cellspacing=0>
									<tr>
									   	<td>
									   		<c:url var="URL" value="/action/documento">
									   			<c:param name="method" value="download" />
									   			<c:param name="id" value="${documentoForm.id}" />
									   			<c:param name="idObjeto" value="${documentoForm.idObjeto}" />
									   			<c:param name="tipoObjeto" value="${documentoForm.tipoObjeto}" />
									   		</c:url>
									   		<a class="etiquetaAzul12Normal"
									   		   href="<c:out value="${URL}" escapeXml="false"/>"
											><html:img
													page="/pages/images/caja_bajar.gif"
											        border="0"
											        altKey="archigest.archivo.abrir"
											        titleKey="archigest.archivo.abrir"
											        styleClass="imgTextMiddle"/>&nbsp;<bean:message key="archigest.archivo.abrir"/></a></td>
										<td width="15">&nbsp;</td>
										<security:permissions action="${appConstants.documentosActions.EDICION_DOCUMENTOS_CUADRO_CLASIFICACION_ACTION}">
										<security:access object="${contenedorDocs}" permission="${appConstants.permissions.EDICION_DOCUMENTOS_CUADRO_CLASIFICACION}">
									   	<td>
									   		<c:url var="URL" value="/action/documento">
									   			<c:param name="method" value="form" />
									   			<c:param name="id" value="${documentoForm.id}" />
									   			<c:param name="idObjeto" value="${documentoForm.idObjeto}" />
									   			<c:param name="tipoObjeto" value="${documentoForm.tipoObjeto}" />
									   		</c:url>
									   		<a class="etiquetaAzul12Normal"
											   href="<c:out value="${URL}" escapeXml="false"/>"
											><html:img
													page="/pages/images/editDoc.gif"
											        border="0"
											        altKey="archigest.archivo.editar"
											        titleKey="archigest.archivo.editar"
											        styleClass="imgTextMiddle"/>&nbsp;<bean:message key="archigest.archivo.editar"/></a></td>
										<td width="10">&nbsp;</td>
									   	<td>
									   		<a class="etiquetaAzul12Normal"
											   href="javascript:remove()"
											><html:img
													page="/pages/images/deleteDoc.gif"
											        border="0"
											        altKey="archigest.archivo.eliminar"
											        titleKey="archigest.archivo.eliminar"
											        styleClass="imgTextMiddle"/>&nbsp;<bean:message key="archigest.archivo.eliminar"/></a></td>
										<td width="10">&nbsp;</td>
										</security:access>
										</security:permissions>
									</tr>
								</table>
				</tiles:put>
				<tiles:put name="dockableContent" direct="true">
					<table class="formulario">
						<tr>
							<td class="tdTitulo" width="200px"><bean:message key="archigest.archivo.documentos.documento.form.nombre"/>:&nbsp;</td>
							<td class="tdDatos"><c:out value="${documentoForm.nombre}"/>&nbsp;</td>
						</tr>
						<tr>
							<td class="tdTitulo"><bean:message key="archigest.archivo.documentos.documento.form.documentoElectronico"/>:&nbsp;</td>
							<td class="tdDatos">
								<c:choose>
									<c:when test="${documentoForm.extFich!=''}">
										<c:out value="${documentoForm.nombreOrgFich}"/>.<c:out value="${documentoForm.extFich}"/>
									</c:when>
									<c:otherwise>
										<c:out value="${documentoForm.nombreOrgFich}"/>
									</c:otherwise>
								</c:choose>
								&nbsp;
							</td>
						</tr>
						<c:if test="${!empty documentoForm.nombreDeposito}">
						<tr>
							<td class="tdTitulo"><bean:message key="archigest.archivo.documentos.documento.form.deposito"/>:&nbsp;</td>
							<td class="tdDatos"><c:out value="${documentoForm.nombreDeposito}"/>&nbsp;</td>
						</tr>
						</c:if>
						<c:if test="${!empty tarea}">
							<tr><td class="separador5" colspan="2">&nbsp;</td></tr>
							<tr>
								<td class="tdTitulo"><bean:message key="archigest.archivo.documentos.clasificador.form.estado"/>:&nbsp;</td>
								<td class="tdDatos">
									<c:choose>
										<c:when test="${documentoForm.estado == 0}">
											<bean:message key="archigest.archivo.documentos.estado.sinValidar"/>
										</c:when>
										<c:when test="${documentoForm.estado == 1}">
											<bean:message key="archigest.archivo.documentos.estado.validado"/>
										</c:when>
										<c:when test="${documentoForm.estado == 2}">
											<bean:message key="archigest.archivo.documentos.estado.noValido"/>
										</c:when>
										<c:when test="${documentoForm.estado == 3}">
											<bean:message key="archigest.archivo.documentos.estado.publicado"/>
										</c:when>
										<c:otherwise>&nbsp;</c:otherwise>
									</c:choose>
								</td>
							</tr>
						</c:if>
						<tr>
							<td colspan="2">&nbsp;</td>
						</tr>
						<tr>
							<td class="tdTitulo"><bean:message key="archigest.archivo.documentos.documento.form.tamano"/>:&nbsp;</td>
							<td class="tdDatos">
								<c:choose>
								<c:when test="${documentoForm.tamanoFich >= 0}">
									<fmt:formatNumber value="${documentoForm.tamanoFich/1024}" pattern="#,###.##"/>&nbsp;
									<bean:message key="archigest.archivo.documentos.documento.form.tamano.unidades"/>
								</c:when>
								<c:otherwise>--</c:otherwise>
								</c:choose>
							</td>
						</tr>
						<c:if test="${!empty infoFichero && !empty infoFichero.fechaAlta}">
							<tr>
								<td class="tdTitulo">
									<bean:message key="archigest.archivo.documentos.clasificador.form.fechaCreacion"/>:&nbsp;
								</td>
								<td class="tdDatos">
									<fmt:formatDate value="${infoFichero.fechaAlta}" pattern="${appConstants.common.FORMATO_FECHA_DETALLADA}" />
								</td>
							</tr>
						</c:if>
						<tr>
							<td class="tdTitulo"><bean:message key="archigest.archivo.documentos.documento.form.descripcion"/>:&nbsp;</td>
							<td class="tdDatos"><c:out value="${documentoForm.descripcion}"/>&nbsp;</td>
						</tr>
						<c:if test="${!empty infoFichero && !empty infoFichero.firmas}">
							<tr><td colspan="2">&nbsp;</td></tr>
							<tr>
								<td class="tdTitulo">
									<bean:message key="archigest.archivo.documentos.clasificador.form.firmas"/>:&nbsp;
								</td>
								<td class="tdDatos">
									<table class="its" style="width:99%;margin-left:auto;margin-right:auto">
										<thead>
											<tr>
												<th><bean:message key="archigest.archivo.documentos.clasificador.form.firmas.autor"/></th>
												<th><bean:message key="archigest.archivo.documentos.clasificador.form.firmas.autenticada"/></th>
											</tr>
										</thead>
										<tbody>
											<c:forEach var="firma" items="${infoFichero.firmas}">
											<tr class="odd">
												<td><c:out value="${firma.autor}"/></td>
												<td>
													<c:choose>
														<c:when test="${firma.autenticada}">
															&nbsp;&nbsp;
															<html:img page="/pages/images/checkbox-yes.gif"
														        border="0"
														        altKey="archigest.archivo.si"
														        titleKey="archigest.archivo.si"
														        styleClass="imgTextMiddle"/>
														</c:when>
														<c:otherwise>
															&nbsp;&nbsp;
															<html:img page="/pages/images/checkbox-no.gif"
														        border="0"
														        altKey="archigest.archivo.no"
														        titleKey="archigest.archivo.no"
														        styleClass="imgTextMiddle"/>
														</c:otherwise>
													</c:choose>
												</td>
											</tr>
											</c:forEach>
										</tbody>
									</table>
								</td>
							</tr>
						</c:if>
					</table>
				</tiles:put>
			</tiles:insert>

			<div class="separador8">&nbsp;</div>

			<security:permissions action="${appConstants.documentosActions.ADMINISTRACION_TAREAS_CAPTURA_ACTION}">
				<c:if test="${!empty tarea && tarea.validable}">
					<div class="cabecero_bloque">
						<TABLE class="w98m1" cellpadding=0 cellspacing=0>
						<TR>
							<TD width="100%" align="right">
								<TABLE cellpadding=0 cellspacing=0>
									<TR>
										<td><a class="etiquetaAzul12Normal" href="javascript:validar();">
											<html:img
											page="/pages/images/cajaRevisada.gif"
											styleClass="imgTextMiddle"/>&nbsp;<bean:message key="archigest.archivo.documentos.marcarValido"/></a></td>
										<td width="10">&nbsp;</td>
										<td><a class="etiquetaAzul12Normal" href="javascript:invalidar();">
											<html:img
											page="/pages/images/cajaError.gif"
											styleClass="imgTextMiddle"/>&nbsp;<bean:message key="archigest.archivo.documentos.marcarNoValido"/></a></td>
										<td width="10">&nbsp;</td>
									</TR>
								</TABLE>
							</TD>
						</TR></TABLE>
					</div>
					<div class="separador1">&nbsp;</div>
				</c:if>
			</security:permissions>

			<c:set var="requestURI" value="${pageContext.request.scheme}://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}" />
			<c:choose>
				<c:when test="${documentoForm.esImagen}">
					<c:if test="${appConstants.configConstants.usarVisorOcx}">
						<c:set var="obtenerDocumentoURL"><c:out value="${requestURI}" escapeXml="false"/>/action/documento?method=download&id=<c:out value="${documentoForm.id}" />&idObjeto=<c:out value="${documentoForm.idObjeto}" />&tipoObjeto=<c:out value="${documentoForm.tipoObjeto}" /></c:set>
						<object id="ImageViewer"
							classid="CLSID:24C6D59E-6D0D-11D4-8128-00C0F049167F"
							codebase="<c:url value="/plugin/archi.cab" />#version=3,3,0,0"
							width="100%" height="560">
							<param name="FileName" value="<c:out value="${obtenerDocumentoURL}" escapeXml="false"/>">
							<param name="FitMode" value="1"><%--Ajusta la imagen al visualizador: 0=Vertical, 1=Horizontal --%>
							<param name="Enhancement" value="2">
							<param name="ShowToolbar" value="1">
							<param name="EnablePrint" value="0">
							<div class="bloque" style="width:99%;color:red;background-color:#F0F0F2;padding:3px;text-align:left">
								<html:img page="/pages/images/info.gif" style="float:left;margin-top:4px;margin-right:8px" />
								<bean:message key="archigest.archivo.documentos.documento.errorVisorImagenes.msg"/>
							</div>
						</object>
					</c:if>
					<c:if test="${appConstants.configConstants.usarVisorOcx == appConstants.common.FALSE_STRING}">
						<c:set var="obtenerDocumentoIFrameURL"><c:out value="${requestURI}" escapeXml="false"/>/action/documento?method=downloadIFrame&id=<c:out value="${documentoForm.id}" />&idObjeto=<c:out value="${documentoForm.idObjeto}" />&tipoObjeto=<c:out value="${documentoForm.tipoObjeto}" /></c:set>
						<div class="iFrameDiv">
							<iframe width="100%" height="560" src="<c:out value="${obtenerDocumentoIFrameURL}" escapeXml="false"/>" name="visor">
								<bean:message key="iframes.not.supported"/>
							</iframe>
						</div>
					</c:if>
				</c:when>
				<c:otherwise>
					<c:set var="obtenerDocumentoIFrameURL"><c:out value="${requestURI}" escapeXml="false"/>/action/documento?method=downloadIFrame&id=<c:out value="${documentoForm.id}" />&idObjeto=<c:out value="${documentoForm.idObjeto}" />&tipoObjeto=<c:out value="${documentoForm.tipoObjeto}" /></c:set>
					<div class="iFrameDiv">
						<iframe width="100%" height="560" src="<c:out value="${obtenerDocumentoIFrameURL}" escapeXml="false"/>" name="visor" id="visor">
							<bean:message key="iframes.not.supported"/>
						</iframe>
					</div>

				</c:otherwise>
			</c:choose>

		</div> <%--cuerpo_izda --%>
		</div> <%--cuerpo_drcha --%>

		<h2><span>&nbsp;</span></h2>
	</div> <%--ficha --%>
</html:form>
</div> <%--contenedor_ficha --%>
