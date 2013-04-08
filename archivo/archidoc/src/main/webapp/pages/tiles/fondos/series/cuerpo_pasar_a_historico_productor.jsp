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
<c:set var="productorAHistorico" value="${sessionScope[appConstants.fondos.PRODUCTOR_A_HISTORICO_KEY]}"/>
<c:set var="desdeMetodo" value="${requestScope[appConstants.fondos.DESDE_METODO_KEY]}" />

<tiles:insert template="/pages/tiles/PABoxLayout.jsp">
	<tiles:put name="boxTitle" direct="true">
		<bean:message key="archigest.archivo.series.productor.pasar.historico"/>
	</tiles:put>
	<tiles:put name="buttonBar" direct="true">
		<TABLE cellpadding=0 cellspacing=0>
		<TR>
			<c:choose>
				<c:when test="${!empty desdeMetodo}">

				<tiles:insert definition="button.returnButton" flush="true">
						<tiles:put name="labelKey" direct="true">archigest.archivo.atras</tiles:put>
						<tiles:put name="imgIcon" direct="true">/pages/images/Previous.gif</tiles:put>
					<tiles:put name="action" direct="true">edicionIdentificacion</tiles:put>
				</tiles:insert>

				</c:when>
			<c:otherwise>
				<td>
				   <bean:struts id="mappingIdentificacionSerie" mapping="/gestionIdentificacionAction" />

						<script>
							function aceptar() {
								var form = document.forms['<c:out value="${mappingIdentificacionSerie.name}" />'];
								form.submit();
							}

						</script>
						<a class=etiquetaAzul12Bold href="javascript:aceptar()">
							<html:img page="/pages/images/Ok_Si.gif" border="0" altKey="archigest.archivo.aceptar" titleKey="archigest.archivo.aceptar" styleClass="imgTextBottom" />
							&nbsp;<bean:message key="archigest.archivo.aceptar"/>
						</a>

				</td>
				<td width="10">&nbsp;</td>
				<td>
					<tiles:insert definition="button.returnButton" flush="true">
							<tiles:put name="labelKey" direct="true">archigest.archivo.cancelar</tiles:put>
							<tiles:put name="imgIcon" direct="true">/pages/images/Ok_No.gif</tiles:put>
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

		<input type="hidden" name="method" value="realizarPasarAHistorico">


		<tiles:insert page="/pages/tiles/PABlockLayout.jsp">
		<tiles:put name="blockTitle" direct="true"><bean:message key="archigest.archivo.series.productor.nombre"/></tiles:put>
		<tiles:put name="blockContent" direct="true">
			<table class="formulario" width="99%">
				<tr>
					<td class="tdTitulo" width="300px" nowrap="nowrap">
						<bean:message key="archigest.archivo.cf.nombreProductor"/>:&nbsp;
					</td>
					<td class="tdDatos"><c:out value="${productorAHistorico.nombre}"/></td>

				</tr>
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
		</tiles:put>
		</tiles:insert>

		</html:form>
	</tiles:put>
</tiles:insert>