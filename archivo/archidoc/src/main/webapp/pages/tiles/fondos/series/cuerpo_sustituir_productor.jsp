<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-html-el.tld" prefix="html-el" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo"%>
<%@ taglib uri="/WEB-INF/archigest.tld" prefix="archigest" %>
<archigest:calendar-config imgDir="../images/calendario/" scriptFile="../scripts/calendar.js"/>

<c:set var="serie" value="${sessionScope[appConstants.fondos.ELEMENTO_CF_KEY]}" />

<c:set var="identificacionSerie" value="${sessionScope[appConstants.fondos.IDENTIFICACION_SERIE_KEY]}" />
<c:set var="productorSustituidor" value="${sessionScope[appConstants.fondos.PRODUCTOR_SUSTITUIDOR_KEY]}" />
<c:set var="productorSustituido" value="${sessionScope[appConstants.fondos.PRODUCTOR_SUSTITUIDO_KEY]}" />
<c:set var="vListaPosiblesProductores" value="${sessionScope[appConstants.fondos.LISTA_POSIBLES_PRODUCTORES_KEY]}"/>
<c:set var="productoresVigentes" value="${sessionScope[appConstants.fondos.PRODUCTORES_VIGENTES_KEY]}" />
<c:set var="productoresOriginalesVigentes" value="${sessionScope[appConstants.fondos.PRODUCTORES_ORIGINALES_VIGENTES_KEY]}" />
<c:set var="productoresOriginalesVigentesAElegir" value="${sessionScope[appConstants.fondos.PRODUCTORES_ORIGINALES_VIGENTES_A_ELEGIR_KEY]}" />
<c:set var="desdeMetodo" value="${requestScope[appConstants.fondos.DESDE_METODO_KEY]}" />
 <bean:struts id="mappingIdentificacionSerie" mapping="/gestionIdentificacionAction" />
<tiles:insert template="/pages/tiles/PABoxLayout.jsp">
	<tiles:put name="boxTitle" direct="true">
		<bean:message key="archigest.archivo.transferencias.reemplazarProductor"/>
	</tiles:put>
	<tiles:put name="buttonBar" direct="true">

		<TABLE cellpadding=0 cellspacing=0>
		<TR>

			<c:choose>
					<c:when test="${!empty desdeMetodo}">
						<td>
							<tiles:insert definition="button.returnButton" flush="true">
									<tiles:put name="labelKey" direct="true">archigest.archivo.cerrar</tiles:put>
									<tiles:put name="imgIcon" direct="true">/pages/images/close.gif</tiles:put>
								<tiles:put name="action" direct="true">edicionIdentificacion</tiles:put>
							</tiles:insert>

						</td>
					</c:when>

			<c:otherwise>

				<td>

						<script>
							function seleccionarProductores() {
								var form = document.forms['<c:out value="${mappingIdentificacionSerie.name}" />'];
								form.submit();
							}
							function realizarSustitucion() {
								var form = document.forms['<c:out value="${mappingIdentificacionSerie.name}" />'];
								form.method.value="realizarSustitucion";
								form.submit();
							}

						</script>
						<a class=etiquetaAzul12Bold href="javascript:realizarSustitucion()">
							<html:img page="/pages/images/Ok_Si.gif" border="0" altKey="archigest.archivo.aceptar" titleKey="archigest.archivo.aceptar" styleClass="imgTextBottom" />
							&nbsp;<bean:message key="archigest.archivo.aceptar"/>
						</a>

				</td>
				<td width="10">&nbsp;</td>
				<td>
					<tiles:insert definition="button.returnButton" flush="true">
						<tiles:put name="labelKey" direct="true">archigest.archivo.cerrar</tiles:put>
						<tiles:put name="imgIcon" direct="true">/pages/images/close.gif</tiles:put>
						<tiles:put name="action" direct="true">edicionIdentificacion</tiles:put>
					</tiles:insert>

				</td>
				</c:otherwise>
			</c:choose>
		</TR>

		</TABLE>
	</tiles:put>

	<tiles:put name="boxContent" direct="true">

		<html:form action="/gestionIdentificacionAction" method="post">

		<input type="hidden" name="method" value="incorporarProductores">

		<div id="busquedaProductores" <c:if test="${tipoOrgano == 1}">style="display:none"</c:if>>
		<tiles:insert page="/pages/tiles/PABlockLayout.jsp">
		<tiles:put name="blockTitle" direct="true"><bean:message key="archigest.archivo.transferencias.nuevoProductor"/></tiles:put>
		<tiles:put name="blockContent" direct="true">
			<table class="formulario" width="99%">
				<tr>
					<td class="tdDatos"><c:out value="${productorSustituidor.nombre}"/></td>
				</tr>
			</table>
		</tiles:put>
		</tiles:insert>
		<c:if test='${!empty productorSustituido}'>

			<div class="separador5">&nbsp;</div>

			<tiles:insert page="/pages/tiles/PABlockLayout.jsp">
			<tiles:put name="blockTitle" direct="true"><bean:message key="archigest.archivo.transferencias.productorActual"/></tiles:put>
			<tiles:put name="blockContent" direct="true">
				<table class="formulario" width="99%">
					<tr>
						<td class="tdDatos"><c:out value="${productorSustituido.nombre}"/></td>
					</tr>
				</table>
			</tiles:put>
			</tiles:insert>
		</c:if>
		<c:if test="${empty desdeMetodo}">
		<div class="separador5">&nbsp;</div>

		<tiles:insert page="/pages/tiles/PABlockLayout.jsp">
		<tiles:put name="blockTitle" direct="true"><bean:message key="archigest.archivo.transferencias.reemplazaA"/></tiles:put>

		<tiles:put name="blockContent" direct="true">
			<c:if test="${not empty productoresOriginalesVigentesAElegir}">
			<div class="separador5">&nbsp;</div>
				<table class="formulario" width="99%">
					<tr>
						<td class="tdTitulo" width="300px" nowrap="nowrap">
							<bean:message key="archigest.archivo.fecha.fin.vigencia.productor"/>:&nbsp;
						</td>
						<td class="tdDatos">
						<html:text property="fechaFinVigenciaProductor" styleClass="input" size="12" maxlength="10"/>
						&nbsp;<archigest:calendar
							image="../images/calendar.gif"
		                    formId="IdentificacionForm"
		                    componentId="fechaFinVigenciaProductor"
		                    format="dd/mm/yyyy"
		                    enablePast="true" />
						</td>
					</tr>
				</table>
			</c:if>
			<div class="separador5">&nbsp;</div>
			<display:table name='pageScope.productoresOriginalesVigentesAElegir'
							style="width:99%;margin-left:auto;margin-right:auto"
							id="productor"
							sort="list"
							export="false">
				<display:column style="width:1%">
					<c:if test="${productor.guid != productorSustituido.guid}">
					<html-el:radio property="guidProductor" value="${productor.guid}"/>
					</c:if>
				</display:column>

				<display:column titleKey="archigest.archivo.nombre" property="nombre" />

			</display:table>
			<div class="separador5">&nbsp;</div>

		</tiles:put>

		</tiles:insert>
		</c:if>
		</div>

		</html:form>






	</tiles:put>
</tiles:insert>