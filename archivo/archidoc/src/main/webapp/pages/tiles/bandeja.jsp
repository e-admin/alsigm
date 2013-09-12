<%@ taglib uri="/WEB-INF/struts-menu-el.tld" prefix="menu" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/taglibs/securityTag.tld" prefix="security"%>

<c:set var="beanBusqueda" value="${sessionScope[appConstants.fondos.CFG_BUSQUEDA_KEY]}"/>
<c:set var="beanBandeja" value="${requestScope[appConstants.common.BANDEJA_KEY]}"/>

<c:if test="${beanBusqueda.mapEntrada[appConstants.fondos.camposBusquedas.CAMPO_ENTRADA_BUSQUEDA_CONDICIONES_AVANZADAS] != null}">
	<tiles:insert page="./fondos/busquedas/div_busqueda_descriptores.jsp" flush="true"/>
</c:if>

<c:set var="appUser" value="${sessionScope[appConstants.common.USUARIOKEY]}" />

<bean:struts id="actionMapping" mapping="/descripcion" />
<bean:define id="actionMappingName" scope="page" name="actionMapping" property="name" toScope="request"/>
<script language="JavaScript1.2" src="<%=request.getContextPath()%>/js/clean_campos_busqueda.js" type="text/JavaScript"></script>
<script language="JavaScript1.2" src="<%=request.getContextPath()%>/js/busquedasUtils.js" type="text/JavaScript"></script>

<div id="contenedor_ficha" style="-moz-box-sizing: border-box;padding-bottom:25px;margin:0px">

<tiles:insert page="/pages/tiles/PABoxLayout.jsp">
	<tiles:put name="boxTitle" direct="true"><bean:message key="archigest.archivo.bandeja.bandejaUsuario"/></tiles:put>
	<tiles:put name="boxContent" direct="true">

	<security:permissions action="${appConstants.descripcionActions.CONSULTAR_FICHA_ELEMENTO_ACTION}">
	<script>
		function buscar(){
			var title = '<bean:message key="archigest.archivo.buscando.realizandoBusqueda"/>';
			var message = '<bean:message key="archigest.archivo.msgOperacionLenta"/>';
			enviarFormulario("formulario", "busq", title, message);
		}
	</script>

	<html:form action="/descripcion" styleId="formulario">
		<input type="hidden" name="tipoBusqueda" value="<c:out value="${appConstants.fondos.tiposBusquedas.TIPO_BUSQUEDA_BANDEJA_SIMPLE}"/>"/>
		<html:hidden property="method" styleId="method" value="busq"/>

		<tiles:insert page="/pages/tiles/PABlockLayout.jsp">
			<tiles:put name="blockTitle" direct="true"><bean:message key="archigest.archivo.bandeja.descripcionArchivistica"/></tiles:put>

			<tiles:put name="buttonBar" direct="true">
				<table><tr>

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


				<td nowrap>
					<a class="etiquetaAzul12Bold" href="javascript:buscar()" >
					<html:img page="/pages/images/buscar.gif" altKey="archigest.archivo.buscar" titleKey="archigest.archivo.buscar" styleClass="imgTextMiddle" />
					<bean:message key="archigest.archivo.buscar"/></a>
				</td>
				<td width="10">&nbsp;</td>
			  	<c:if test="${appConstants.configConstants.mostrarBusquedaAvanzada || (!appConstants.configConstants.mostrarBusquedaAvanzada &&  appUser.usuarioArchivo) }">

				<td nowrap>
					<c:url var="busquedaEnCuadroClasificacionURL" value="/action/navigateAction">
						<c:param name="method" value="menuOption" />
						<c:param name="menuOption" value="busquedaEnCuadroClasificacion" />
						<c:param name="menuName" value="MenuDescripcionFondo" />
					</c:url>
					<a class="etiquetaAzul12Bold" href="<c:out value="${busquedaEnCuadroClasificacionURL}" escapeXml="false"/>" >
					<html:img page="/pages/images/buscar_go.gif" altKey="archigest.archivo.buscar" titleKey="archigest.archivo.buscar" styleClass="imgTextMiddle" />
					<bean:message key="archigest.archivo.busquedaAmpliada"/></a>
				</td>
				</c:if>
				<c:if test="${appConstants.configConstants.mostrarAyuda}">
				<td width="10">&nbsp;</td>
					<td>
						<c:set var="requestURI" value="${pageContext.request.scheme}://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}" />
						<c:set var="carpetaIdioma" value="${sessionScope[appConstants.commonConstants.LOCALEKEY]}" />
						<a class="etiquetaAzul12Bold" href="javascript:popupHelp('<c:out value="${requestURI}" escapeXml="false"/>/help/<c:out value="${carpetaIdioma}"/>/general/bandejaEntrada.html');">
						<html:img page="/pages/images/help.gif"
						        altKey="archigest.archivo.ayuda"
						        titleKey="archigest.archivo.ayuda"
						        styleClass="imgTextMiddle"/>
						&nbsp;<bean:message key="archigest.archivo.ayuda"/></a>
					</td>
				</c:if>
				</tr></table>
			</tiles:put>

			<tiles:put name="blockContent" direct="true">

				<table class="form_centrado_vtop" align="center" width="90%" style="margin-top:10px;margin-bottom:10px;">

					<bean:define id="classTdTituloCampo" value="tdTitulo" toScope="request"/>
					<bean:define id="classTdTituloCampoSinBorde" value="tdTitulo" toScope="request"/>
					<bean:define id="classTdCampo" value="" toScope="request"/>
					<bean:define id="classTdCampoSinBorde" value="" toScope="request"/>
					<bean:define id="sizeCampo" value="200px" toScope="request"/>
					<bean:define id="busquedaForm" name="busquedaElementosForm" toScope="request"/>
					<bean:define id="methodBuscarProcedimiento" value="buscarProcedimientoBandeja" toScope="request"/>
					<bean:define id="methodLoadFicha" value="formBandeja" toScope="request"/>
					<c:set var="listaFondos" value="${sessionScope[appConstants.fondos.LISTA_FONDOS_KEY]}"/>
					<c:if test="${listaFondos != null}">
						<bean:define id="listaFondos" name="listaFondos" toScope="request"/>
					</c:if>

					<c:forEach var="elementos" items="${beanBusqueda.listaCamposEntrada}">
						<bean:define id="elemento" name="elementos" toScope="request"/>
						<tiles:insert name="campos.busqueda" flush="true"/>
					</c:forEach>
				</table>
			</tiles:put>
		</tiles:insert>
	</html:form>
	<div class="separador5">&nbsp;</div>

	</security:permissions>

	<%-- GESTIÓN DE PREVISIONES Y RELACIONES --%>
	<tiles:insert page="./bandeja/bandeja_previsiones_relaciones.jsp" flush="true"/>

	<%-- GESTIÓN DE INGRESOS DIRECTOS --%>
	<tiles:insert page="./bandeja/bandeja_ingresos.jsp" flush="true"/>

	<%-- GESTIÓN DE DIVISIONES DE FRACCIÓN DE SERIE --%>
	<tiles:insert page="./bandeja/bandeja_divisionesfs.jsp" flush="true"/>

	<%-- GESTIÓN DE PRÉSTAMOS Y CONSULTAS --%>
	<tiles:insert page="./bandeja/bandeja_prestamos_consultas.jsp" flush="true"/>

	<%-- GESTIÓN DE REVISIONES DE DOCUMENTACIÓN --%>
	<tiles:insert page="./bandeja/bandeja_revisiones_documentacion.jsp" flush="true"/>

	<%-- GESTIÓN DE SERIES --%>
	<tiles:insert page="./bandeja/bandeja_series.jsp" flush="true"/>

	<%-- GESTIÓN DE DIGITALIZACION --%>
	<tiles:insert page="./bandeja/bandeja_digitalizacion.jsp" flush="true"/>

	<%-- GESTIÓN DE DOCUMENTOS VITALES --%>
	<tiles:insert page="./bandeja/bandeja_documentos_vitales.jsp" flush="true"/>

	</tiles:put>
</tiles:insert>
</div>