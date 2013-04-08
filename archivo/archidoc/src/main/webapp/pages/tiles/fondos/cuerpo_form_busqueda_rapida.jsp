<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo" %>
<%@ taglib uri="/WEB-INF/taglibs/securityTag.tld" prefix="security"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>

<bean:struts id="actionMapping" mapping="/buscarElementos" />
<bean:define id="actionMappingName" scope="page" name="actionMapping" property="name" toScope="request"/>

<script language="JavaScript1.2" type="text/JavaScript">
	function buscarElementos()
	{
		if (window.top.showWorkingDiv) {
			var title = '<bean:message key="archigest.archivo.buscando.realizandoBusqueda"/>';
			var message = '<bean:message key="archigest.archivo.buscando.msgFondos"/>';
			var message2 = '<bean:message key="archigest.archivo.msgOperacionLenta"/>';
			window.top.showWorkingDiv(title, message, message2);
		}
		document.forms['<c:out value="${actionMapping.name}" />'].method.value="buscar";
		document.forms['<c:out value="${actionMapping.name}" />'].submit();
	}

	function generarInformeResultadoBusqueda(){
		if (window.top.showWorkingDiv) {
			var title = '<bean:message key="archigest.archivo.buscando.realizandoBusqueda"/>';
			var message = '<bean:message key="archigest.archivo.buscando.msgFondos"/>';
			var message2 = '<bean:message key="archigest.archivo.msgOperacionLenta"/>';
			window.top.showWorkingDiv(title, message, message2);
		}

		document.forms['<c:out value="${actionMapping.name}" />'].method.value="generarInformeBusqueda";
		document.forms['<c:out value="${actionMapping.name}" />'].submit();
	}
</script>

<tiles:insert template="/pages/tiles/PABoxLayout.jsp">
	<c:set var="beanBusqueda" value="${sessionScope[appConstants.fondos.CFG_BUSQUEDA_KEY]}"/>

	<c:if test="${beanBusqueda.mapEntrada[appConstants.fondos.camposBusquedas.CAMPO_ENTRADA_BUSQUEDA_CONDICIONES_AVANZADAS] != null}">
		<tiles:insert page="/pages/tiles/fondos/busquedas/div_busqueda_descriptores.jsp" flush="true"/>
	</c:if>

	<tiles:put name="boxTitle" direct="true">
		<bean:message key="archigest.archivo.cf.busqueda.rapida.caption"/>
	</tiles:put>
	<tiles:put name="buttonBar" direct="true">
		<table cellpadding="0" cellspacing="0">
			<tr>

				<c:set var="exportarBusqueda" value="${sessionScope[appConstants.common.SHOW_INFORME_BUSQUEDA_BUTTON]}"/>
				<c:if test="${exportarBusqueda}">
				<td>
					<a class="etiquetaAzul12Bold" href="javascript:generarInformeResultadoBusqueda();">
					<html:img page="/pages/images/documentos/doc_pdf.gif"
					        altKey="archigest.archivo.informe"
					        titleKey="archigest.archivo.informe"
					        styleClass="imgTextMiddle"/>
					&nbsp;<bean:message key="archigest.archivo.informeResultadoBusqueda"/></a>
				</td>
				<td width="10">&nbsp;</td>
				</c:if>

			   	<td>
					<a class="etiquetaAzul12Bold" href="javascript:buscarElementos();">
					<html:img page="/pages/images/buscar.gif"
					        altKey="archigest.archivo.buscar"
					        titleKey="archigest.archivo.buscar"
					        styleClass="imgTextMiddle"/>
					&nbsp;<bean:message key="archigest.archivo.buscar"/></a>
				</td>
				<td width="10">&nbsp;</td>
			   	<td>
					<a class="etiquetaAzul12Bold" href="javascript:cleanForm(document.forms['<c:out value="${actionMappingName}" />'])">
					<html:img page="/pages/images/clear0.gif"
					        altKey="archigest.archivo.limpiar"
					        titleKey="archigest.archivo.limpiar"
					        styleClass="imgTextMiddle"/>
					&nbsp;<bean:message key="archigest.archivo.limpiar"/></a>
				</td>
				<c:if test="${appConstants.configConstants.mostrarAyuda}">
					<td width="10">&nbsp;</td>
					<td>
						<c:set var="requestURI" value="${pageContext.request.scheme}://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}" />
						<c:set var="carpetaIdioma" value="${sessionScope[appConstants.commonConstants.LOCALEKEY]}" />
						<a class="etiquetaAzul12Bold" href="javascript:popupHelp('<c:out value="${requestURI}" escapeXml="false"/>/help/<c:out value="${carpetaIdioma}"/>/fondos/busquedaRapida.html');">
						<html:img page="/pages/images/help.gif"
						        altKey="archigest.archivo.ayuda"
						        titleKey="archigest.archivo.ayuda"
						        styleClass="imgTextMiddle"/>
						&nbsp;<bean:message key="archigest.archivo.ayuda"/></a>
					</td>
				</c:if>
				<td width="10">&nbsp;</td>
				<td>
					<tiles:insert definition="button.closeButton" flush="true"/>
				</td>
			</tr>
		</table>
	</tiles:put>
	<tiles:put name="boxContent" direct="true">
		<div class="bloque">
			<html:form action="/buscarElementos">
				<input type="hidden" name="method" value="buscar"/>
				<input type="hidden" name="tipoBusqueda" value="<c:out value="${appConstants.fondos.tiposBusquedas.TIPO_BUSQUEDA_FONDOS_RAPIDA}"/>"/>
				<input type="hidden" name="postBack" value="S"/>
 				<table class="formulario">

					<bean:define id="classTdTituloCampo" value="tdTituloFicha" toScope="request"/>
					<bean:define id="classTdTituloCampoSinBorde" value="tdTituloFichaSinBorde" toScope="request"/>
					<bean:define id="classTdCampo" value="tdDatosFicha" toScope="request"/>
					<bean:define id="classTdCampoSinBorde" value="" toScope="request"/>
					<bean:define id="sizeCampo" value="200" toScope="request"/>
					<bean:define id="busquedaForm" name="busquedaElementosForm" toScope="request"/>
					<bean:define id="methodBuscarProcedimiento" value="buscarProcedimientoRapida" toScope="request"/>
					<bean:define id="methodLoadFicha" value="formBusquedaRapida" toScope="request"/>
					<c:set var="listaFondos" value="${sessionScope[appConstants.fondos.LISTA_FONDOS_KEY]}"/>
					<c:if test="${listaFondos != null}">
						<bean:define id="listaFondos" name="listaFondos" toScope="request"/>
					</c:if>

					<c:forEach var="elementos" items="${beanBusqueda.listaCamposEntrada}">
						<bean:define id="elemento" name="elementos" toScope="request"/>
						<tiles:insert name="campos.busqueda" flush="true"/>
					</c:forEach>
				</table>
			</html:form>
		</div>
	</tiles:put>
</tiles:insert>