<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo" %>
<%@ taglib uri="/WEB-INF/taglibs/securityTag.tld" prefix="security"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>

<bean:struts id="actionMapping" mapping="/descripcion" />
<bean:define id="actionMappingName" scope="page" name="actionMapping" property="name" toScope="request"/>

<c:set var="beanBusqueda" value="${sessionScope[appConstants.fondos.CFG_BUSQUEDA_KEY]}"/>

<c:if test="${beanBusqueda.mapEntrada[appConstants.fondos.camposBusquedas.CAMPO_ENTRADA_BUSQUEDA_CONDICIONES_AVANZADAS] != null}">
	<tiles:insert page="/pages/tiles/fondos/busquedas/div_busqueda_descriptores.jsp" flush="true"/>
</c:if>

<script language="JavaScript1.2" src="<%=request.getContextPath()%>/js/utils.js" type="text/JavaScript"></script>
<script language="JavaScript1.2" src="<%=request.getContextPath()%>/js/clean_campos_busqueda.js" type="text/JavaScript"></script>
<script language="JavaScript1.2" src="<%=request.getContextPath()%>/js/busquedasUtils.js" type="text/JavaScript"></script>
<script language="JavaScript1.2" type="text/JavaScript">
	function close(){
		<c:url var="closeURL" value="/action/descripcion">
				<c:param name="method" value="goBack" />
		</c:url>
		window.location.href = '<c:out value="${closeURL}" escapeXml="false"/>';
	}

	function buscarElementos()
	{
		if (window.top.showWorkingDiv) {
			var title = '<bean:message key="archigest.archivo.buscando.realizandoBusqueda"/>';
			var message = '<bean:message key="archigest.archivo.buscando.msgDescripcionesArchivisticas"/>';
			var message2 = '<bean:message key="archigest.archivo.msgOperacionLenta"/>';
			window.top.showWorkingDiv(title, message, message2);
		}
		document.forms['<c:out value="${actionMapping.name}" />'].method.value="busq";
		document.forms['<c:out value="${actionMapping.name}" />'].submit();
	}

</script>

<tiles:insert template="/pages/tiles/PABoxLayout.jsp">
	<tiles:put name="boxTitle" direct="true">
		<bean:message key="archigest.archivo.busqueda.form.caption"/>
	</tiles:put>
	<tiles:put name="buttonBar" direct="true">
		<table cellpadding="0" cellspacing="0">
			<tr>
				<c:set var="exportarBusqueda" value="${sessionScope[appConstants.common.SHOW_INFORME_BUSQUEDA_BUTTON]}"/>
				<c:if test="${exportarBusqueda}">
				<td>
					<script language="javascript">
					function generarInformeResultadoBusqueda(){
						var title = '<bean:message key="archigest.archivo.msg.titulo"/>';
						var message = '<bean:message key="archigest.archivo.msg.proceso.en.ejecucion"/>';
						enviarFormulario("formulario", "generarInformeBusqueda", title, message);
					}
					</script>


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
				<td width="10">&nbsp;</td>
				<td>
					<tiles:insert definition="button.closeButton" flush="true"/>
				</td>
			</tr>
		</table>
	</tiles:put>
	<tiles:put name="boxContent" direct="true">
		<div class="bloque">
			<html:form action="/descripcion" styleId="formulario">
				<input type="hidden" name="tipoBusqueda" value="<c:out value="${appConstants.fondos.tiposBusquedas.TIPO_BUSQUEDA_BANDEJA_AMPLIADA}"/>"/>
				<html:hidden property="method" styleId="method" value="busq"/>

				<table class="formulario">
					<bean:define id="classTdTituloCampo" value="tdTituloFicha" toScope="request"/>
					<bean:define id="classTdTituloCampoSinBorde" value="tdTituloFichaSinBorde" toScope="request"/>
					<bean:define id="classTdCampo" value="tdDatosFicha" toScope="request"/>
					<bean:define id="classTdCampoSinBorde" value="" toScope="request"/>
					<bean:define id="sizeCampo" value="200" toScope="request"/>
					<bean:define id="busquedaForm" name="busquedaElementosForm" toScope="request"/>
					<bean:define id="methodBuscarProcedimiento" value="buscarProcedimientoAmpliada" toScope="request"/>
					<bean:define id="methodLoadFicha" value="formBusq" toScope="request"/>
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