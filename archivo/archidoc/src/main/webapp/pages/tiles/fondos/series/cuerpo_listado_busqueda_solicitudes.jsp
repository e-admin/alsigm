<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/taglibs/securityTag.tld" prefix="security"%>

<bean:struts id="mappingGestionSeries" mapping="/gestionSeries" />

<tiles:insert template="/pages/tiles/PABoxLayout.jsp">
	<tiles:put name="boxTitle" direct="true">
		<fmt:message key="archigest.archivo.cf.listadoSolicitudes"/>
	</tiles:put>
	<tiles:put name="buttonBar" direct="true">
		<table cellpadding="0" cellspacing="0">
			<tr>
				<td nowrap>
					<tiles:insert definition="button.closeButton" />
				</td>
			</tr>
		</table>
	</tiles:put>
	<tiles:put name="boxContent" direct="true">

		<c:set var="solicitudes" value="${requestScope[appConstants.fondos.SOLICITUDES_KEY]}"/>
		<c:set var="FORMATO_FECHA" value="${appConstants.common.FORMATO_FECHA}"/>


		<c:url var="listaSolicitudesAprobacionPaginationURI" value="/action/buscarSolicitudesSeries" />
		<jsp:useBean id="listaSolicitudesAprobacionPaginationURI" type="java.lang.String" />

		<div class="bloque">
		<display:table name="pageScope.solicitudes"
			id="solicitud" 
			pagesize="15"
			sort="list"
			requestURI="<%=listaSolicitudesAprobacionPaginationURI%>"
			export="false"
			style="width:99%;margin-left:auto;margin-right:auto"
			defaultorder="descending"
			defaultsort="2">

			<display:setProperty name="basic.msg.empty_list">
				<bean:message key="archigest.archivo.fondos.busqueda.noSolicitudes"/>
			</display:setProperty>
			
			<display:column titleKey="archigest.archivo.cf.serie" sortProperty="etiqueta" sortable="true">
				<c:url var="verSolicitudURL" value="/action/gestionSeries">
					<c:param name="method" value="verSolicitud" />
					<c:param name="idSolicitud" value="${solicitud.id}" />
				</c:url>
				<a href="<c:out value="${verSolicitudURL}" escapeXml="false"/>" class="tdlink">
					<c:out value="${solicitud.etiquetaSerie}" />
				</a>
			</display:column>
			<display:column titleKey="archigest.archivo.cf.tipo" sortProperty="tipo" sortable="true">
				<fmt:message key="${appConstants.i18nPrefixes.PETICION_CAMBIO_SERIE}${solicitud.tipo}" />		
			</display:column>
			<display:column titleKey="archigest.archivo.cf.estado" sortProperty="estado" sortable="true">
				<fmt:message key="archigest.archivo.cf.estadoSolicitudAlta.${solicitud.estado}" />
			</display:column>
			<display:column titleKey="archigest.archivo.fEstado" sortProperty="fechaEstado" sortable="true">
				<fmt:formatDate value="${solicitud.fechaEstado}" pattern="${FORMATO_FECHA}"/>
			</display:column>
			<display:column titleKey="archigest.archivo.cf.solicitante" sortProperty="apellidosUsuarioSolicitante" sortable="true">
				<c:out value="${solicitud.apellidosUsuarioSolicitante}" />, <c:out value="${solicitud.nombreUsuarioSolicitante}" />
			</display:column>
			<display:column titleKey="archigest.archivo.cf.gestor" sortProperty="apellidosUsuarioGestor" sortable="true">
				<c:out value="${solicitud.apellidosUsuarioGestor}" />, <c:out value="${solicitud.nombreUsuarioGestor}" />
			</display:column>
		</display:table>
		</div>
	</tiles:put>
</tiles:insert>