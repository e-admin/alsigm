<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo"%>

<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>

<c:set var="fichasDescriptoras" value="${requestScope[appConstants.descripcion.FICHAS_DESCRIPCION_KEY]}"/>
<c:set var="fichasClfDoc" value="${requestScope[appConstants.descripcion.FICHAS_CLF_DOC_KEY]}"/>
<c:set var="tieneDescriptores" value="${requestScope[appConstants.descripcion.TIENE_DESCRIPTORES_KEY]}"/>

<bean:struts id="actionMapping" mapping="/listasDescriptoras" />

<script language="JavaScript1.2" type="text/JavaScript">
	function save()
	{
		document.forms["<c:out value="${actionMapping.name}" />"].method.value = "save";
		document.forms["<c:out value="${actionMapping.name}" />"].submit();
	}
	function cancel(){
		<c:url var="cancelURL" value="/action/listasDescriptoras">
				<c:param name="method" value="goBack" />
		</c:url>
		window.location.href = '<c:out value="${cancelURL}" escapeXml="false"/>';
	}
</script>

<div id="contenedor_ficha">
  <html:form action="/listasDescriptoras">
  <html:hidden property="id"/>
  <input type="hidden" name="method" value="save"/>

	<div class="ficha">

		<h1><span>
			<div class="w100">
				<table class="w98" cellpadding=0 cellspacing=0>
					<tr>
				    	<td class="etiquetaAzul12Bold" height="25px">
				    		<bean:message key="archigest.archivo.descripcion.listasDescriptoras.form.listaDescriptora.caption"/>
				    	</td>
				    	<td align="right">
							<table cellpadding=0 cellspacing=0>
								<tr>
								   	<td>
										<a class="etiquetaAzul12Bold"
										   href="javascript:save()"
										><input type="image"
												src="../images/Ok_Si.gif"
										        alt="<bean:message key="archigest.archivo.aceptar"/>"
										        title="<bean:message key="archigest.archivo.aceptar"/>"
										        class="imgTextMiddle"/>&nbsp;<bean:message key="archigest.archivo.aceptar"/></a>
									</td>
									<td width="10">&nbsp;</td>
								   	<td>
										<a class="etiquetaAzul12Bold"
										   href="javascript:cancel()"
										><html:img page="/pages/images/Ok_No.gif"
										        border="0"
										        altKey="archigest.archivo.cancelar"
										        titleKey="archigest.archivo.cancelar"
										        styleClass="imgTextMiddle"/>&nbsp;<bean:message key="archigest.archivo.cancelar"/></a>
									</td>
									<c:if test="${appConstants.configConstants.mostrarAyuda}">
										<td width="10">&nbsp;</td>
										<td>
											<c:set var="requestURI" value="${pageContext.request.scheme}://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}" />
											<c:set var="carpetaIdioma" value="${sessionScope[appConstants.commonConstants.LOCALEKEY]}" />
											<a class="etiquetaAzul12Bold" href="javascript:popupHelp('<c:out value="${requestURI}" escapeXml="false"/>/help/<c:out value="${carpetaIdioma}"/>/descriptores/CrearListaDescriptora.htm');">
											<html:img page="/pages/images/help.gif"
											        altKey="archigest.archivo.ayuda"
											        titleKey="archigest.archivo.ayuda"
											        styleClass="imgTextMiddle"/>
											&nbsp;<bean:message key="archigest.archivo.ayuda"/></a>
										</td>
									</c:if>
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

			<div class="bloque"><%--bloque datos tabla --%>
				<table class="formulario">
					<tr>
						<td class="tdTitulo" width="150"><bean:message key="archigest.archivo.descripcion.listasDescriptoras.form.nombre"/>:&nbsp;</td>
						<td class="tdDatos"><html:text property="nombre" styleClass="input99" maxlength="64"/>&nbsp;</td>
					</tr>
					<tr>
						<td class="tdTitulo" width="150"><bean:message key="archigest.archivo.descripcion.listasDescriptoras.form.tipo"/>:&nbsp;</td>
						<td class="tdDatos">
							<html:select property="tipo">
								<html:option key="archivo.listaDescriptora.tipo.abierta" value="1"/>
								<html:option key="archivo.listaDescriptora.tipo.cerrada" value="2"/>
							</html:select>
						</td>
					</tr>
					<tr>
						<td class="tdTitulo" width="150"><bean:message key="archigest.archivo.descripcion.listasDescriptoras.form.tipoDescriptor"/>:&nbsp;</td>
						<td class="tdDatos">
							<c:choose>
								<c:when test="${tieneDescriptores}">
									<c:choose>
										<c:when test="${listaDescriptoraForm.tipoDescriptor == 0}">
											<bean:message key="archivo.descriptores.tipo.sin_tipo_especifico"/>
										</c:when>
										<c:when test="${listaDescriptoraForm.tipoDescriptor == 1}">
											<bean:message key="archivo.descriptores.tipo.entidad"/>
										</c:when>
										<c:when test="${listaDescriptoraForm.tipoDescriptor == 2}">
											<bean:message key="archivo.descriptores.tipo.geografico"/>
										</c:when>
										<c:when test="${listaDescriptoraForm.tipoDescriptor == 3}">
											<bean:message key="archivo.descriptores.tipo.materia"/>
										</c:when>
									</c:choose>
									<html:hidden property="tipoDescriptor"/>
								</c:when>
								<c:otherwise>
									<html:select property="tipoDescriptor">
										<html:option key="archivo.descriptores.tipo.sin_tipo_especifico" value="0"/>
										<html:option key="archivo.descriptores.tipo.entidad" value="1"/>
										<html:option key="archivo.descriptores.tipo.geografico" value="2"/>
										<html:option key="archivo.descriptores.tipo.materia" value="3"/>
									</html:select>
								</c:otherwise>
							</c:choose>
						</td>
					</tr>
					<tr><td>&nbsp;</td></tr>
					<tr>
						<td class="tdTitulo" width="150"><bean:message key="archigest.archivo.descripcion.listasDescriptoras.form.nombreFichaDescrPref"/>:&nbsp;</td>
						<td class="tdDatos">
							<html:select property="idFichaDescrPref">
								<html:option value=""/>
								<html:optionsCollection name="fichasDescriptoras" label="nombre" value="id"/>
							</html:select>
						</td>
					</tr>
					<tr>
						<td class="tdTitulo" width="150"><bean:message key="archigest.archivo.descripcion.listasDescriptoras.form.nombreFichaClfDocPref"/>:&nbsp;</td>
						<td class="tdDatos">
							<html:select property="idFichaClfDocPref">
								<html:option value=""/>
								<html:optionsCollection name="fichasClfDoc" label="nombre" value="id"/>
							</html:select>
						</td>
					</tr>
					<tr>
						<td class="tdTitulo" width="150"><bean:message key="archigest.archivo.repositorio.ecm"/>:&nbsp;</td>
						<td class="tdDatos">
					<bean:define id="nombreCampo" value="idRepEcmPref" toScope="request"/>
					<tiles:insert name="lista.repositorios.ecm" flush="true"/>
						</td>
					</tr>
					<tr><td>&nbsp;</td></tr>
					<tr>
						<td class="tdTitulo" width="150" style="vertical-align:top;"><bean:message key="archigest.archivo.descripcion.listasDescriptoras.form.descripcion"/>:&nbsp;</td>
						<td class="tdDatos"><html:textarea property="descripcion" rows="4" onchange="maxlength(this,254,true)" onkeypress="maxlength(this,254,false)"/>&nbsp;</td>
					</tr>
				</table>
			</div><%--bloque datos tabla --%>

		</div> <%--cuerpo_izda --%>
		</div> <%--cuerpo_drcha --%>

		<h2><span>&nbsp;</span></h2>
	</div> <%--ficha --%>
  </html:form>

</div> <%--contenedor_ficha --%>


