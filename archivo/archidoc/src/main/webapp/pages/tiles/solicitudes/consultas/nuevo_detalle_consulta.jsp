<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/archigest.tld" prefix="archigest" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo"%>

<script language="JavaScript1.2" src="<%=request.getContextPath()%>/js/utils.js" type="text/JavaScript"></script>
<bean:struts id="mappingGestionDetalleConsulta" mapping="/gestionDetallesConsultas" />
<bean:define id="actionMappingName" scope="page" name="mappingGestionDetalleConsulta" property="name" toScope="request"/>

<tiles:insert template="/pages/tiles/PABoxLayout.jsp">

	<c:set var="beanBusqueda" value="${sessionScope[appConstants.fondos.CFG_BUSQUEDA_KEY]}" scope="session"/>

	<c:if test="${beanBusqueda.mapEntrada[appConstants.consultas.camposBusquedas.CAMPO_ENTRADA_BUSQUEDA_CONDICIONES_AVANZADAS] != null}">
		<tiles:insert page="../../fondos/busquedas/div_busqueda_descriptores.jsp" flush="true"/>
	</c:if>

	<tiles:put name="boxTitle" direct="true"><bean:message key="archigest.archivo.consultas.anadirDetalleAConsulta" /></tiles:put>
	<tiles:put name="buttonBar" direct="true">
		<TABLE cellpadding=0 cellspacing=0>
		 <TR>
			<TD>
				<script>
					function anadirDetalle() {
						var form = document.forms['<c:out value="${mappingGestionDetalleConsulta.name}" />'];
						if (form.detallesseleccionados && elementSelected(form.detallesseleccionados))
							form.submit();
						else
							alert("<bean:message key='archigest.archivo.solicitudes.consultaWarningMsg'/>");
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
				<c:url var="cancelURL" value="/action/gestionConsultas">
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
		 </TR>
		</TABLE>
	</tiles:put>
	<tiles:put name="boxContent" direct="true">

		<DIV class="cabecero_bloque"> <%--primer bloque de datos (resumen siempre visible) --%>
			<jsp:include page="cuerpo_cabeceracte_consulta.jsp" flush="true" />
		</DIV> <%--primer bloque de datos (resumen siempre visible) --%>

		<html:form action="/gestionDetallesConsultas">

		<input type="hidden" name="method" value="crearDetalle">

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
							var form = document.forms['<c:out value="${mappingGestionDetalleConsulta.name}" />'];
							//form.method.value = 'nuevoDetalle';
							form.method.value = 'nuevaBusquedaUDocs';
							if (window.top.showWorkingDiv) {
								var title = '<bean:message key="archigest.archivo.buscando.realizandoBusqueda"/>';
								var message = '<bean:message key="archigest.archivo.buscando.msgUDocs"/>';
								var message2 = '<bean:message key="archigest.archivo.msgOperacionLenta"/>';
								window.top.showWorkingDiv(title, message, message2);
							}
							form .submit();
						}
					</script>
			   		<a class="etiquetaAzul12Bold" href="javascript:buscarUdocs()">
						<html:img
							page="/pages/images/buscar.gif"
							altKey="archigest.archivo.buscar"
							titleKey="archigest.archivo.buscar" styleClass="imgTextMiddle" />
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
				<c:if test="${requestScope[appConstants.consultas.MOSTRAR_LISTADO_BUSQUEDA_UDOCS]}">
					<tiles:insert page="/pages/tiles/solicitudes/consultas/busquedas/resultado_busqueda_udocs.jsp" flush="true"/>
					<div class="separador5">&nbsp;</div>
				</c:if>

			</tiles:put>
		</tiles:insert>
		</html:form>
	</tiles:put>
</tiles:insert>