<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/archigest.tld" prefix="archigest" %>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo" %>

<script language="JavaScript1.2" src="<%=request.getContextPath()%>/js/utils.js" type="text/JavaScript"></script>

<bean:struts id="mappingGestionDetallePrestamo" mapping="/gestionDetallesPrestamos" />
<bean:define id="actionMappingName" scope="page" name="mappingGestionDetallePrestamo" property="name" toScope="request"/>

<tiles:insert template="/pages/tiles/PABoxLayout.jsp">

	<c:set var="beanBusqueda" value="${sessionScope[appConstants.fondos.CFG_BUSQUEDA_KEY]}" scope="session"/>

	<c:if test="${beanBusqueda.mapEntrada[appConstants.prestamos.camposBusquedas.CAMPO_ENTRADA_BUSQUEDA_CONDICIONES_AVANZADAS] != null}">
		<tiles:insert page="../../fondos/busquedas/div_busqueda_descriptores.jsp" flush="true"/>
	</c:if>

	<tiles:put name="boxTitle" direct="true"><bean:message key="archigest.archivo.prestamos.anadirDetalleAlPrestamo" /></tiles:put>
	<tiles:put name="buttonBar" direct="true">
		<TABLE cellpadding=0 cellspacing=0>
		 <TR>
			<TD>
				<script>
					function anadirDetalle() {
						var form = document.forms['<c:out value="${mappingGestionDetallePrestamo.name}" />'];
						if (form.detallesseleccionados && elementSelected(form.detallesseleccionados))
							form.submit();
						else
							alert("<bean:message key='errors.solicitudes.prestamos.seleccionarAlMenosUnExp'/>");
					}
				</script>
				<a class="etiquetaAzul12Bold" href="javascript:anadirDetalle()" >
					<html:img
						page="/pages/images/Ok_Si.gif" border="0"
						altKey="archigest.archivo.aceptar"
						titleKey="archigest.archivo.aceptar" styleClass="imgTextBottom" />
					&nbsp;<bean:message key="archigest.archivo.aceptar"/>
				</a>
			</TD>
		   <TD width="10">&nbsp;</TD>
		   <TD>
				<c:url var="cancelURL" value="/action/gestionPrestamos">
					<c:param name="method" value="goBack" />
				</c:url>
				<a class=etiquetaAzul12Bold href="javascript:window.location='<c:out value="${cancelURL}" escapeXml="false"/>'">
					<html:img
						page="/pages/images/Ok_No.gif" border="0"
						altKey="archigest.archivo.cancelar"
						titleKey="archigest.archivo.cancelar" styleClass="imgTextBottom" />
					&nbsp;<bean:message key="archigest.archivo.cancelar"/>
				</a>
		   </TD>
		   <c:if test="${appConstants.configConstants.mostrarAyuda}">
				<td width="10">&nbsp;</td>
				<td>
					<c:set var="requestURI" value="${pageContext.request.scheme}://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}" />
					<c:set var="carpetaIdioma" value="${sessionScope[appConstants.commonConstants.LOCALEKEY]}" />
					<a class="etiquetaAzul12Bold" href="javascript:popupHelp('<c:out value="${requestURI}" escapeXml="false"/>/help/<c:out value="${carpetaIdioma}"/>/prestamos/AniadirUnidadDocumental.htm');">
					<html:img page="/pages/images/help.gif"
						altKey="archigest.archivo.ayuda"
						titleKey="archigest.archivo.ayuda"
						styleClass="imgTextMiddle"/>
						&nbsp;<bean:message key="archigest.archivo.ayuda"/></a>
				</td>
			</c:if>
		 </TR>
		</TABLE>
	</tiles:put>
	<tiles:put name="boxContent" direct="true">
		<tiles:insert page="/pages/tiles/PADockableBlockLayout.jsp">
			<tiles:put name="blockName" direct="true">previsionInfo</tiles:put>
			<tiles:put name="blockTitle" direct="true"><bean:message key="archigest.archivo.prestamos.prestamo"/></tiles:put>
			<tiles:put name="visibleContent" direct="true">
				<tiles:insert page="/pages/tiles/solicitudes/prestamos/cuerpo_cabeceracte_prestamo.jsp" />
			</tiles:put>
			<tiles:put name="dockableContent" direct="true">
				<tiles:insert page="/pages/tiles/solicitudes/prestamos/datos_prestamo.jsp" />
			</tiles:put>
		</tiles:insert>

		<html:form action="/gestionDetallesPrestamos">

			<input type="hidden" name="method" value="crearDetalle">
			<input type="hidden" name="send" value="1">

			<tiles:insert page="/pages/tiles/PABlockLayout.jsp">
				<tiles:put name="blockTitle" direct="true"><bean:message key="archigest.archivo.prestamos.seleccionUDocs"/></tiles:put>
				<tiles:put name="buttonBar" direct="true">
					<TABLE cellpadding=0 cellspacing=0>
					<TR>
					<td>
					<a class="etiquetaAzul12Bold" href="javascript:cleanForm(document.forms['<c:out value="${actionMappingName}" />'])">
					<html:img page="/pages/images/clear0.gif"
					        altKey="archigest.archivo.limpiar"
					        titleKey="archigest.archivo.limpiar"
					        styleClass="imgTextMiddle"/>
						&nbsp;<bean:message key="archigest.archivo.limpiar"/></a>
					</td>
					<td width="10">&nbsp;</td>
					<td align="right">
						<script>
							function buscarUdocs() {
								var form = document.forms['<c:out value="${mappingGestionDetallePrestamo.name}" />'];
								/*form.method.value = 'nuevoDetalle';*/
								/*form.method.value = 'buscarUDocs';*/
								form.method.value = 'nuevaBusquedaUDocs';
								if (window.top.showWorkingDiv) {
									var title = '<bean:message key="archigest.archivo.buscando.realizandoBusqueda"/>';
									var message = '<bean:message key="archigest.archivo.buscando.msgUDocs"/>';
									var message2 = '<bean:message key="archigest.archivo.msgOperacionLenta"/>';
									window.top.showWorkingDiv(title, message, message2);
								}
								form.submit();
							}
						</script>

				   		<a class="etiquetaAzul12Bold" href="javascript:buscarUdocs()">
							<html:img page="/pages/images/buscar.gif" altKey="archigest.archivo.buscar" titleKey="archigest.archivo.buscar" styleClass="imgTextMiddle" />
				   		 	&nbsp;<bean:message key="archigest.archivo.buscar"/>
				   		</a>
					</td>
					</TR>
					</TABLE>
				</tiles:put>

				<tiles:put name="blockContent" direct="true">
					<%--Tile de búsqueda de Unidades Documentales --%>
					<tiles:insert page="/pages/tiles/solicitudes/prestamos/busquedas/busqueda_udocs.jsp" flush="true"/>
					<div class="separador5">&nbsp;</div>
					<%--Tile de resultados de búsqueda de Unidades Documentales --%>
					<%--Si hay udocs las mostramos --%>
					<c:if test="${requestScope[appConstants.prestamos.MOSTRAR_LISTADO_BUSQUEDA_UDOCS]}">
						<tiles:insert page="/pages/tiles/solicitudes/prestamos/busquedas/resultado_busqueda_udocs.jsp" flush="true"/>
						<div class="separador5">&nbsp;</div>
					</c:if>
				</tiles:put>
			</tiles:insert>
		</html:form>
	</tiles:put>
</tiles:insert>