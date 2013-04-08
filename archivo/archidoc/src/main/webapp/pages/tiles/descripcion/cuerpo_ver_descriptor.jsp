<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo"%>
<%@ taglib uri="/WEB-INF/taglibs/securityTag.tld" prefix="security"%>

<bean:struts id="actionMapping" mapping="/descriptor" />

<script language="JavaScript1.2" type="text/JavaScript">
	function showDescription(){
		<c:url var="showDescriptionURL" value="/action/isaar">
				<c:param name="method" value="retrieve" />
				<c:param name="id" value="${descriptorForm.id}"/>
		</c:url>

		window.location.href = '<c:out value="${showDescriptionURL}" escapeXml="false"/>';
	}
	function showDocuments(){
		<c:url var="showDocumentsURL" value="/action/documentos">
				<c:param name="actionToPerform" value="homeUDoc" />
				<c:param name="id" value="${descriptorForm.id}"/>
				<c:param name="tipo" value="2"/>
		</c:url>
		window.location.href = '<c:out value="${showDocumentsURL}" escapeXml="false"/>';
	}
	function edit(){
		<c:url var="editURL" value="/action/descriptor">
				<c:param name="method" value="formDescriptor" />
				<c:param name="id" value="${descriptorForm.id}"/>
		</c:url>
		window.location.href = '<c:out value="${editURL}" escapeXml="false"/>';
	}
	function close(){
		<c:url var="closeURL" value="/action/descriptor">
				<c:param name="method" value="goBack" />
		</c:url>
		window.location.href = '<c:out value="${closeURL}" escapeXml="false"/>';
	}
	function remove()
	{
		if (confirm("<bean:message key="archigest.archivo.descripcion.descriptor.delete.confirm.msg"/>"))
		{
			<c:url var="removeURL" value="/action/descriptor">
				<c:param name="method" value="removeDescriptor" />
				<c:param name="id" value="${descriptorForm.id}"/>
			</c:url>
			window.location.href = "<c:out value="${removeURL}" escapeXml="false"/>";
		}
	}
</script>

<div id="contenedor_ficha">
  <html:form action="/descriptor">

  <html:hidden property="id"/>
  <html:hidden property="nombre"/>
  <html:hidden property="idLista"/>
  <html:hidden property="nombreLista"/>
  <html:hidden property="tipo"/>
  <html:hidden property="tieneDescr"/>
  <html:hidden property="estado"/>
  <html:hidden property="idSistExt"/>
  <html:hidden property="idDescrSistExt"/>
  <html:hidden property="timestamp"/>
  <html:hidden property="nombreFichaDescr"/>
  <html:hidden property="nombreRepEcm"/>
  <html:hidden property="nivelAcceso"/>
  <html:hidden property="idLCA"/>
  <html:hidden property="idRepEcm"/>
  <html:hidden property="editClfDocs"/>

  <input type="hidden" name="method" value="formDescriptor"/>

	<div class="ficha">

		<h1><span>
			<div class="w100">
				<table class="w98" cellpadding=0 cellspacing=0>
					<tr>
				    	<td class="etiquetaAzul12Bold" height="25px">
				    		<bean:message key="archigest.archivo.descripcion.descriptor.form.caption"/>
				    	</td>
				    	<td align="right">
							<table cellpadding=0 cellspacing=0>
								<tr>
								   	<td>
										<a class="etiquetaAzul12Bold"
										   href="javascript:close()"
										><html:img page="/pages/images/close.gif"
										        border="0"
										        altKey="archigest.archivo.cerrar"
										        titleKey="archigest.archivo.cerrar"
										        styleClass="imgTextMiddle"/>&nbsp;<bean:message key="archigest.archivo.cerrar"/></a>
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

			<div class="cabecero_bloque"><%-- cabecero datos del descriptor --%>
				<table class="w98m1" cellpadding="0" cellspacing="0">
					<tbody>
						<tr>
							<td class="etiquetaAzul12Bold">
								<bean:message key="archigest.archivo.descripcion.descriptor.form.descriptor.caption"/>
							</td>
					    	<td align="right">
								<table cellpadding=0 cellspacing=0>
									<tr>
										<c:if test="${! empty descriptorForm.idFichaDescr}">
											<security:permissions action="${appConstants.descripcionActions.CONSULTAR_FICHA_DESCRIPTOR_ACTION}">
											<security:access object="${descriptorForm.descriptor}" permission="${appConstants.permissions.CONSULTA_DESCRIPCION_DESCRIPTOR}">
											   	<td><a class="etiquetaAzul12Normal"
													   href="javascript:showDescription()"
													><input type="image"
															src="../images/searchDoc.gif"
													        border="0"
													        alt="<bean:message key="archigest.archivo.descripcion.descriptor.button.verDescripcion"/>"
													        title="<bean:message key="archigest.archivo.descripcion.descriptor.button.verDescripcion"/>"
													        class="imgTextMiddle"/>&nbsp;<bean:message key="archigest.archivo.descripcion.descriptor.button.verDescripcion"/></a></td>
												<td width="10">&nbsp;</td>
											</security:access>
											</security:permissions>
										</c:if>
											<security:permissions action="${appConstants.documentosActions.CONSULTA_DOCUMENTOS_CUADRO_CLASIFICACION_ACTION}">
											<security:access object="${descriptorForm.descriptor}" permission="${appConstants.permissions.CONSULTA_DOCUMENTOS_CUADRO_CLASIFICACION}">
											   	<td><a class="etiquetaAzul12Normal"
													   href="javascript:showDocuments()"
													><input type="image"
															src="../images/docsElectronicos.gif"
													        border="0"
													        alt="<bean:message key="archigest.archivo.descripcion.descriptor.button.verDocumentos"/>"
													        title="<bean:message key="archigest.archivo.descripcion.descriptor.button.verDocumentos"/>"
													        class="imgTextMiddle"/>&nbsp;<bean:message key="archigest.archivo.descripcion.descriptor.button.verDocumentos"/></a></td>
												<td width="10">&nbsp;</td>
											</security:access>
											</security:permissions>
										<security:permissions action="${appConstants.descripcionActions.ADMINISTRAR_DESCRIPTORES_ACTION}">
									   	<td><a class="etiquetaAzul12Normal"
											   href="javascript:edit()"
											><input type="image"
													src="../images/editDoc.gif"
											        border="0"
											        alt="<bean:message key="archigest.archivo.editar"/>"
											        title="<bean:message key="archigest.archivo.editar"/>"
											        class="imgTextMiddle"/>&nbsp;<bean:message key="archigest.archivo.editar"/></a></td>
										<td width="10">&nbsp;</td>
										<c:if test="${!empty descriptorForm.id}">
									   	<td>
											<a class="etiquetaAzul12Normal"
											   href="javascript:remove()"
											><html:img page="/pages/images/deleteDoc.gif"
											        altKey="archigest.archivo.eliminar"
											        titleKey="archigest.archivo.eliminar"
											        styleClass="imgTextMiddle"/>
									        &nbsp;<bean:message key="archigest.archivo.eliminar"/></a>
										</td>
										<td width="10">&nbsp;</td>
										</c:if>
										</security:permissions>
									</tr>
								</table>
							</td>
						</tr>
					</tbody>
				</table>
			</div> <%-- cabecero datos del descriptor --%>

			<div class="bloque"><%-- bloque datos descriptor --%>
				<table class="formulario">
					<tr>
						<td class="tdTitulo" width="250"><bean:message key="archigest.archivo.descripcion.descriptor.form.nombre"/>:&nbsp;</td>
						<td class="tdDatos"><c:out value="${descriptorForm.nombre}"/>&nbsp;</td>
					</tr>
					<tr>
						<td class="tdTitulo" width="250"><bean:message key="archigest.archivo.descripcion.descriptor.form.estado"/>:&nbsp;</td>
						<td class="tdDatos">
							<c:choose>
								<c:when test="${descriptorForm.estado == 1}">
									<bean:message key="archivo.estado.validacion.validado"/>
								</c:when>
								<c:when test="${descriptorForm.estado == 2}">
									<bean:message key="archivo.estado.validacion.no_validado"/>
								</c:when>
								<c:otherwise>&nbsp;</c:otherwise>
							</c:choose>
						</td>
					</tr>
					<tr><td>&nbsp;</td></tr>
					<tr>
						<td class="tdTitulo" width="250"><bean:message key="archigest.archivo.descripcion.descriptor.form.nombreLista"/>:&nbsp;</td>
						<td class="tdDatos"><c:out value="${descriptorForm.nombreLista}"/>&nbsp;</td>
					</tr>
					<tr>
						<td class="tdTitulo" width="250"><bean:message key="archigest.archivo.descripcion.descriptor.form.tipo"/>:&nbsp;</td>
						<td class="tdDatos">
							<c:choose>
								<c:when test="${descriptorForm.tipo == 0}">
									<bean:message key="archivo.descriptores.tipo.sin_tipo_especifico"/>
								</c:when>
								<c:when test="${descriptorForm.tipo == 1}">
									<bean:message key="archivo.descriptores.tipo.entidad"/>
								</c:when>
								<c:when test="${descriptorForm.tipo == 2}">
									<bean:message key="archivo.descriptores.tipo.geografico"/>
								</c:when>
								<c:when test="${descriptorForm.tipo == 3}">
									<bean:message key="archivo.descriptores.tipo.materia"/>
								</c:when>
								<c:otherwise>&nbsp;</c:otherwise>
							</c:choose>
						</td>
					</tr>
					<tr><td>&nbsp;</td></tr>
					<tr>
						<td class="tdTitulo" width="250"><bean:message key="archigest.archivo.descripcion.descriptor.form.fichasDescr"/>:&nbsp;</td>
						<td class="tdDatos"><c:out value="${descriptorForm.nombreFichaDescr}"/>&nbsp;</td>
					</tr>
					<tr>
						<td class="tdTitulo" width="250"><bean:message key="archigest.archivo.repositorio.ecm"/>:&nbsp;</td>
						<td class="tdDatos"><c:out value="${descriptorForm.nombreRepEcm}"/>&nbsp;</td>
					</tr>
					<tr>
						<td class="tdTitulo" width="250"><bean:message key="archigest.archivo.descripcion.descriptor.form.nivelAcceso"/>:&nbsp;</td>
						<td class="tdDatos">
							<c:choose>
								<c:when test="${descriptorForm.nivelAcceso == '1'}">
									<bean:message key="archivo.nivel_acceso.publico"/>
								</c:when>
								<c:when test="${descriptorForm.nivelAcceso == '2'}">
									<bean:message key="archivo.nivel_acceso.archivo"/>
								</c:when>
								<c:when test="${descriptorForm.nivelAcceso == '3'}">
									<bean:message key="archivo.nivel_acceso.restringido"/>
								</c:when>
								<c:otherwise>&nbsp;</c:otherwise>
							</c:choose>
						</td>
					</tr>
					<c:if test="${descriptorForm.nivelAcceso != '1'}">
					<tr>
						<td class="tdTitulo" width="250"><bean:message key="archigest.archivo.descripcion.descriptor.form.listaControlAcceso"/>:&nbsp;</td>
						<td class="tdDatos"><c:out value="${descriptorForm.nombreLCA}"/>&nbsp;</td>
					</tr>
					</c:if>
				</table>
			</div><%-- bloque datos descriptor --%>

		</div> <%-- cuerpo_izda --%>
		</div> <%-- cuerpo_drcha --%>

		<h2><span>&nbsp;</span></h2>
	</div> <%-- ficha --%>
  </html:form>

</div> <%-- contenedor_ficha --%>


