<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>

<tiles:insert template="/pages/tiles/PABoxLayout.jsp">
	<tiles:put name="boxTitle" direct="true">
	<bean:message key="archigest.archivo.cf.cesionDeControlDeSeries"/></tiles:put>

	<tiles:put name="buttonBar" direct="true">
		<table cellpadding="0" cellspacing="0"><tr>
	        <td>
				<c:url var="closeURL" value="/action/navigateAction">
					<c:param name="method" value="goBack" />
				</c:url>
		   		<a class=etiquetaAzul12Bold href="<c:out value="${closeURL}" escapeXml="false"/>">
					<html:img page="/pages/images/close.gif" altKey="archigest.archivo.cerrar" titleKey="archigest.archivo.cerrar" styleClass="imgTextMiddle" />
		   		 	&nbsp;<bean:message key="archigest.archivo.cerrar"/>
		   		</a>
		    </td>
		</tr></table>
	</tiles:put>

	<tiles:put name="boxContent" direct="true">
		<tiles:insert template="/pages/tiles/PABlockLayout.jsp">
			<tiles:put name="blockTitle" direct="true">
				<c:set var="gestorSeries" value="${requestScope[appConstants.fondos.GESTOR_SERIES_KEY]}" />
				<c:out value="${gestorSeries.nombreCompleto}"/>&nbsp;<span class="etiquetaNegra12Normal"><bean:message key="archigest.archivo.cf.haSidoAsignadoComoGestorDeLasSiguientesSeries"/> </span>
			</tiles:put>
			<tiles:put name="blockContent" direct="true">
			<span class="separador5"></span>

			<c:set var="listaSeriesAAsignar" value="${requestScope[appConstants.fondos.SERIE_KEY]}" />
			<display:table 
				name="pageScope.listaSeriesAAsignar" 
				id="serie" 
				style="width:99%;margin-left:auto;margin-right:auto">
			
				<display:column titleKey="archigest.archivo.cf.codigo">
					<c:out value="${serie.codReferencia}" />
				</display:column>
				<display:column titleKey="archigest.archivo.cf.denominacion" property="titulo" />
				
			</display:table>
			<div class="separador8">&nbsp;</div>
			</tiles:put>
		</tiles:insert>
	</tiles:put>
</tiles:insert>