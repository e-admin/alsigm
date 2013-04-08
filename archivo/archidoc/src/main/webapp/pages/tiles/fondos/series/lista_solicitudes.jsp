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
		<c:choose>
		<c:when test="${param.method == 'misSolicitudesAprobacion'}">
			<bean:message key="archigest.archivo.cf.solicitudesVer"/>
		</c:when>
		<c:when test="${param.method == 'listaSolicitudesAprobacion'}">
			<bean:message key="archigest.archivo.cf.solicitudesGestionar"/>
		</c:when>
		</c:choose>
	</tiles:put>
	<tiles:put name="buttonBar" direct="true">

		<table cellpadding="0" cellspacing="0">
			<tr>
				<security:permissions action="${appConstants.fondosActions.GESTION_SOLICITUDES_SERIE_ACTION}">
				<c:if test="${param.method == 'misSolicitudesAprobacion'}">
				<td nowrap>
				<script>
					function eliminarSolicitudes() {
						var form = document.forms['<c:out value="${mappingGestionSeries.name}" />'];
						if (form.solicitudSeleccionada && elementSelected(form.solicitudSeleccionada)) {
							if (confirm("<bean:message key='archigest.archivo.cf.msgConfirmSolicitudEliminar'/>")) {
								form.method.value = 'eliminarSolicitudes';
								form.submit();
							}
						} else
							alert("<bean:message key='archigest.archivo.cf.deleteSolicitud.warning.msg'/>");
					}
				</script>

					<a class="etiquetaAzul12Bold" href="javascript:eliminarSolicitudes()" >
					<html:img page="/pages/images/deleteDoc.gif" altKey="archigest.archivo.eliminar" titleKey="archigest.archivo.eliminar" styleClass="imgTextMiddle" />
					<bean:message key="archigest.archivo.eliminar"/></a>
				</td>
				<td width="10px"></td>
				</c:if>
				</security:permissions>
				<td nowrap>
					<tiles:insert definition="button.closeButton" />
				</td>
			</tr>
		</table>
	</tiles:put>
	<tiles:put name="boxContent" direct="true">

		<c:set var="solicitudes" value="${requestScope[appConstants.fondos.LISTA_SOLICITUDES_KEY]}"/>
		<c:set var="FORMATO_FECHA" value="${appConstants.common.FORMATO_FECHA}"/>


		<c:url var="listaSolicitudesAprobacionPaginationURI" value="/action/gestionSeries" />
		<jsp:useBean id="listaSolicitudesAprobacionPaginationURI" type="java.lang.String" />

		<html:form action="/gestionSeries">
		<input type="hidden" name="method" value="eliminarSolicitudes">

		<div class="bloque">
		<display:table name="pageScope.solicitudes"
			id="solicitud" 
			pagesize="10"
			sort="list"
			requestURI="<%=listaSolicitudesAprobacionPaginationURI%>"
			export="false"
			style="width:99%;margin-left:auto;margin-right:auto"
			defaultorder="descending"
			defaultsort="2">

			<display:setProperty name="basic.msg.empty_list">
				<bean:message key="archigest.archivo.fondos.noSolicitudes"/>
			</display:setProperty>
			
			<security:permissions action="${appConstants.fondosActions.GESTION_SOLICITUDES_SERIE_ACTION}">
			<display:column title="">
				<c:if test="${solicitud.puedeSerEliminada}">
				<c:set var="idSolicitud" value="${solicitud.id}" />
				<jsp:useBean id="idSolicitud" type="java.lang.String" />
				<html:checkbox property="solicitudSeleccionada" value="<%=idSolicitud%>" />
				</c:if>
			</display:column>
			</security:permissions>
			
			<display:column titleKey="archigest.archivo.cf.serie" sortable="true">
				<c:url var="verSolicitudURL" value="/action/gestionSeries">
					<c:param name="method" value="verSolicitud" />
					<c:param name="idSolicitud" value="${solicitud.id}" />
				</c:url>
				<a href="<c:out value="${verSolicitudURL}" escapeXml="false"/>" class="tdlink">
					<c:out value="${solicitud.etiquetaSerie}" />
				</a>
			</display:column>
			<display:column titleKey="archigest.archivo.cf.tipo" sortable="true">
				<fmt:message key="${appConstants.i18nPrefixes.PETICION_CAMBIO_SERIE}${solicitud.tipo}" />		
			</display:column>
			<display:column titleKey="archigest.archivo.cf.fechaSolicitud" sortable="true">
				<fmt:formatDate value="${solicitud.fecha}" pattern="${FORMATO_FECHA}"/>
			</display:column>
			<display:column titleKey="archigest.archivo.cf.estado" sortable="true">
				<fmt:message key="archigest.archivo.cf.estadoSolicitudAlta.${solicitud.estado}" />
			</display:column>
			<c:if test="${param.method == 'listaSolicitudesAprobacion'}">
				<display:column titleKey="archigest.archivo.cf.solicitante" sortable="true">
					<c:out value="${solicitud.usuarioSolicitante.nombreCompleto}" />
				</display:column>
			</c:if>
		</display:table>
		</div>
		</html:form>
	
	</tiles:put>
</tiles:insert>