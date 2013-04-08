<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>

<c:set var="vSerie" value="${sessionScope[appConstants.fondos.SERIE_KEY]}"/>
<c:set var="vSerieDestino" value="${sessionScope[appConstants.fondos.SERIE_DESTINO]}"/>

<tiles:definition id="idClasificador" template="/pages/tiles/PABlockLayout.jsp">
	<tiles:put name="blockTitle" direct="true">
		<fmt:message key="archigest.archivo.cf.datosSerie"/>:&nbsp;
	</tiles:put>
	<tiles:put name="blockContent" direct="true">
	<html:form action="/gestionClasificadorSeriesAction">
		<table class="formulario">
			<tr>
				<td width="200px" class="tdTitulo">
					<bean:message key="archigest.archivo.cf.codReferencia.antiguo"/>:&nbsp;
				</td>
				<td class="tdDatosBold">
					<c:out value="${vSerie.codReferencia}" />
				</td>
			</tr>
			<tr>
				<td width="200px" class="tdTitulo">
					<bean:message key="archigest.archivo.cf.codReferencia.nuevo"/>:&nbsp;
				</td>
				<td class="tdDatosBold">
					<c:out value="${vSerieDestino.codReferencia}" />
				</td>
			</tr>
			<tr>
				<td width="200px" class="tdTitulo">
					<bean:message key="archigest.archivo.cf.denominacion"/>:&nbsp;
				</td>
				<td class="tdDatosBold">
					<c:out value="${vSerieDestino.titulo}"/>
				</td>
			</tr>
		</table>
		<div class="separador5">&nbsp;</div>

		<table class="formulario">
			<tr>
				<td class="tdTitulo" width="200px">
					<bean:message key="archigest.archivo.cf.tipo"/>:&nbsp;
				</td>
				<td class="tdDatos">
					<bean:message key="archigest.archivo.serie"/>
				</td>
			</tr>
			<tr>
				<td width="100px" class="tdTitulo">
					<bean:message key="archigest.archivo.cf.estado"/>:&nbsp;
				</td>
				<td class="tdDatos">
					<fmt:message key="archigest.archivo.cf.estadoElementoCF.${vSerieDestino.estado}" />
				</td>
			</tr>
		</table>
	</html:form>
	</tiles:put>
</tiles:definition>

<%-- COMPOSICION DE PAGINA A MOSTRAR --%>
<tiles:insert template="/pages/tiles/PABoxLayout.jsp">
	<tiles:put name="boxTitle" direct="true">
		<bean:message key="archigest.archivo.informe.movimiento.serie"/>
	</tiles:put>
	<tiles:put name="buttonBar" direct="true">
		<table cellpadding="0" cellspacing="0">
		<tr>
			<td>
				<c:url var="informeURL" value="/action/informeMoverSerie"/>
				<a class="etiquetaAzul12Bold" href="<c:out value="${informeURL}" escapeXml="false"/>">
					<html:img page="/pages/images/documentos/doc_pdf.gif"
				        altKey="archigest.archivo.informe"
				        titleKey="archigest.archivo.informe"
				        styleClass="imgTextMiddle"/>
					<bean:message key="archigest.archivo.informe"/>
				</a>
			</td>
			<td width="10">&nbsp;</td>
			<td>
				<tiles:insert definition="button.closeButton" flush="true">
					<tiles:put name="action" direct="true">goReturnPoint</tiles:put>
				</tiles:insert>
			</td>
			<td width="10">&nbsp;</td>

		</tr>
		</table>
	</tiles:put>
	<tiles:put name="boxContent" direct="true">
		<tiles:insert beanName="idClasificador" />
	</tiles:put>
</tiles:insert>
