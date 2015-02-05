<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo" %>
<%@ taglib uri="/WEB-INF/taglibs/securityTag.tld" prefix="security"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>

<tiles:importAttribute/>
<bean:struts id="actionMapping" mapping="/busquedaUISAnioYSerieAction" />
<bean:define id="actionMappingName" scope="page" name="actionMapping" property="name" toScope="request"/>
<bean:define id="actionMappingName" scope="page" name="actionMapping" property="name" toScope="request"/>
<script language="JavaScript1.2" type="text/JavaScript">

	function cleanForm(){
		enviarFormulario("formulario","limpiar");
	}

	function buscarUI(){
		if (window.top.showWorkingDiv) {
			var title = '<bean:message key="archigest.archivo.buscando.realizandoBusqueda"/>';
			var message = '<bean:message key="archigest.archivo.buscando.msgUinst"/>';
			var message2 = '<bean:message key="archigest.archivo.msgOperacionLenta"/>';
			window.top.showWorkingDiv(title, message, message2);
		}
		enviarFormulario("formulario","buscar");
	}

</script>

<tiles:insert template="/pages/tiles/PABoxLayout.jsp">
	<tiles:put name="boxTitle" direct="true">
		<bean:message key="archigest.archivo.deposito.consultaUISAnioYSerie.caption"/>
	</tiles:put>
	<tiles:put name="buttonBar" direct="true">
		<table cellpadding="0" cellspacing="0">
			<tr>
			   	<td>
					<a class="etiquetaAzul12Bold" href="javascript:buscarUI();">
					<html:img page="/pages/images/buscarCaja.gif"
					        altKey="archigest.archivo.buscar"
					        titleKey="archigest.archivo.buscar"
					        styleClass="imgTextMiddle"/>
					&nbsp;<bean:message key="archigest.archivo.buscar"/></a>
				</td>
				<td width="10">&nbsp;</td>
			   	<td>
					<c:url var="buscarUISAnioYSerieURL" value="/action/busquedaUISAnioYSerieAction">
						<c:param name="method" value="initBusqueda" />
					</c:url>
					<a class="etiquetaAzul12Bold" href="<c:out value="${buscarUISAnioYSerieURL}" escapeXml="false"/>">
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
		<html:form action="/busquedaUISAnioYSerieAction" styleId="formulario">
			<input type="hidden" id="method" name="method" value="buscar"/>

			<div class="bloque">
				<bean:define id="classTdTituloCampo" value="tdTituloFicha" toScope="request"/>
				<bean:define id="classTdCampo" value="tdDatosFicha" toScope="request"/>
				<bean:define id="sizeCampo" value="200" toScope="request"/>
				<bean:define id="busquedaForm" name="BusquedaUISAnioYSerieForm" toScope="request"/>
				<table class="formulario">
					<tiles:insert page="/pages/tiles/deposito/busquedas/campo_busqueda_ambito_archivo.jsp" ignore="false"/>
					<tiles:insert page="/pages/tiles/fondos/busquedas/campo_busqueda_condiciones_ambito.jsp" ignore="false"/>
					<tiles:insert page="/pages/tiles/deposito/busquedas/campo_busqueda_select_ubicacion.jsp" ignore="false"/>
					<tiles:insert page="/pages/tiles/deposito/busquedas/campo_busqueda_condiciones_ambito_deposito.jsp" ignore="false"/>
					<tiles:insert page="/pages/tiles/deposito/busquedas/campo_busqueda_formato.jsp" ignore="false"/>
					<tiles:insert page="/pages/tiles/fondos/busquedas/campo_busqueda_fecha_inicial.jsp" ignore="false"/>
					<tiles:insert page="/pages/tiles/fondos/busquedas/campo_busqueda_fecha_final.jsp" ignore="false"/>
					<tiles:insert page="/pages/tiles/fondos/busquedas/campo_busqueda_productor.jsp" ignore="false"/>
				</table>
			</div>
		</html:form>

		<div class="separador2">&nbsp;</div>

		<c:set var="unidsInst" value="${sessionScope[appConstants.deposito.LISTA_UIS_ANIO_SERIE_KEY]}" />
		<c:if test="${unidsInst != null}">
			<div class="cabecero_bloque" id="capaCabeceraResultadosBusqueda">
				<TABLE class="w98m1" cellpadding=0 cellspacing=0 height="100%">
				  <TR>
					<TD class="etiquetaAzul12Bold">
						<bean:message key="archigest.archivo.descripcion.descriptor.resultadoBusqueda"/>
					</TD>
				  </TR>
				</TABLE>
			</div>
			 <div class="bloque" style="position:static" id="capaResultadosBusqueda">
			<div class="separador8">&nbsp;</div>
				<display:table name="pageScope.unidsInst"
						id="uInst"
						style="width:98%;margin-left:auto;margin-right:auto"
						export="true"
						requestURI="/action/busquedaUISAnioYSerieAction"
						pagesize="15">
					<display:setProperty name="basic.msg.empty_list">
						<bean:message key="archigest.archivo.deposito.unidsinst.busquedaVacia"/>
					</display:setProperty>
					<display:column titleKey="archigest.archivo.signatura" sortable="true" media="html">
										<c:url var="urlVerUdocs" value="/action/verHuecoAction">
						<c:param name="method" value="listadoudocs"/>
						<c:param name="idHueco" value="${uInst.idHueco}"/>
					</c:url>
					<a href="javascript:window.location='<c:out value="${urlVerUdocs}" escapeXml="false"/>'" class="hueco">
						<c:out value="${uInst.signaturaui}"/>
					</a>
					</display:column>

					<display:column titleKey="archigest.archivo.signatura" property="signaturaui" media="csv excel xml pdf"/>
					<display:column titleKey="archigest.archivo.series.titulo" property="tituloSerie" sortable="true"/>
					<display:column titleKey="archigest.archivo.cf.nombreProductor" property="nombreProductor" sortable="true"/>
					<display:column titleKey="archigest.archivo.deposito.fecha.inicial.menor" property="fechaInicialMenorAsString" sortable="true"/>
					<display:column titleKey="archigest.archivo.deposito.fecha.inicial.mayor" property="fechaInicialMayorAsString" sortable="true"/>
					<display:column titleKey="archigest.archivo.deposito.formato" property="nombreFormato" sortable="true"/>
					<display:column titleKey="archigest.archivo.deposito.uis.mismo.anio">
						<c:choose>
							<c:when test="${uInst.mismoAnio}">
								<bean:message key="archigest.archivo.si"/>
							</c:when>
							<c:otherwise>
								<bean:message key="archigest.archivo.no"/>
							</c:otherwise>
						</c:choose>
					</display:column>
					<display:column titleKey="archigest.archivo.deposito.uis.otras.series">
						<c:choose>
							<c:when test="${uInst.otrasSeries}">
								<bean:message key="archigest.archivo.si"/>
							</c:when>
							<c:otherwise>
								<bean:message key="archigest.archivo.no"/>
							</c:otherwise>
						</c:choose>
					</display:column>
				</display:table>
			</div>
		</c:if>
	</tiles:put>
</tiles:insert>