<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>

<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>

<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>

<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo" %>
<%@ taglib uri="/WEB-INF/taglibs/securityTag.tld" prefix="security"%>



<c:set var="FORMATO_FECHA" value="${appConstants.common.FORMATO_FECHA}"/>


<tiles:insert template="/pages/tiles/PABoxLayout.jsp">
	<tiles:put name="boxTitle" direct="true"><bean:message key="archigest.archivo.series.identificacion"/></tiles:put>
	<tiles:put name="buttonBar" direct="true">
		<table cellpadding="0" cellspacing="0">
			<tr>
				<security:permissions action="${appConstants.fondosActions.CONSULTA_SOLICITUDES_SERIE_ACTION}">
				<td nowrap>
				<c:url var="buscarSolicitudesURL" value="/action/buscarSolicitudesSeries">
					<c:param name="method" value="formBusqueda" />
				</c:url>
				<a class="etiquetaAzul12Bold" href="<c:out value="${buscarSolicitudesURL}" escapeXml="false"/>">
					<html:img page="/pages/images/buscar_go.gif" 
						altKey="archigest.archivo.menu.fondosDocumentales.gestionSeries.buscarSolicitudes" 
						titleKey="archigest.archivo.menu.fondosDocumentales.gestionSeries.buscarSolicitudes" 
						styleClass="imgTextMiddle" />
					&nbsp;<bean:message key="archigest.archivo.menu.fondosDocumentales.gestionSeries.buscarSolicitudes"/>
				</a>
				</td>
				<td width="10">&nbsp;</td>
				</security:permissions>
				<td nowrap>
					<tiles:insert definition="button.closeButton" />
				</td>
			</tr>
		</table>
	</tiles:put>
	<tiles:put name="boxContent" direct="true">

	<security:permissions permission="${appConstants.permissions.GESTOR_SERIE}">
		<%-- SERIES EN IDENTIFICACIÓN --%>
		<c:set var="seriesEnIdentificacion" value="${requestScope[appConstants.fondos.SERIES_EN_IDENTIFICACION]}"/>

		<tiles:insert page="/pages/tiles/PABlockLayout.jsp">
			<tiles:put name="blockTitle" direct="true"><bean:message key="archigest.archivo.series.enIdentificacion"/></tiles:put>
			<tiles:put name="blockContent" direct="true">
			<div class="separador8"></div>
			<display:table name="pageScope.seriesEnIdentificacion"
				id="serie" 
				export="false"
				style="width:99%;margin-left:auto;margin-right:auto">

				<display:setProperty name="basic.msg.empty_list">
					<div class="separador8"></div>
					<bean:message key="archigest.archivo.series.noSeriesEnIdentificacion"/>
					<div class="separador8"></div>
				</display:setProperty>
				
				<display:column titleKey="archigest.archivo.cf.titulo">
					<c:url var="infoSeriesURL" value="/action/gestionSeries">
						<c:param name="method" value="verEnFondos" />
						<c:param name="id" value="${serie.id}" />
					</c:url>
					<a class="tdlink" href="<c:out value="${infoSeriesURL}" escapeXml="false"/>" target="_self"><c:out value="${serie.codReferencia}" /> - <c:out value="${serie.titulo}" /></a>
				</display:column>
				<display:column titleKey="archigest.archivo.cf.estado" style="width:30%">
					<fmt:message key="archigest.archivo.cf.estadoSerie.${serie.estadoserie}" />
				</display:column>
			</display:table>
			<div class="separador8"></div>
			</tiles:put>
		</tiles:insert>
		<c:if test="${!empty seriesEnIdentificacion[0]}">
		<div class="pie_bloque_right">
			<c:url var="seriesEnIdentificacionURL" value="/action/gestionSeries">
				<c:param name="method" value="verSeriesEnIdentificacion" />
			</c:url>
			<a class="etiq_pie_bloque" href="<c:out value="${seriesEnIdentificacionURL}" escapeXml="false"/>">
				<html:img page="/pages/images/go_all.gif" styleClass="imgTextMiddle" /> <bean:message key="archigest.archivo.verMas"/>
			</a>
		</div>
		<div style="height:12px"></div>
		</c:if>
		<div class="separador20"></div>

		<%-- SOLICITUDES PENDIENTES DE APROBACIÓN --%>
		<c:set var="solicitudes" value="${requestScope[appConstants.fondos.LISTA_SOLICITUDES_PENDIENTES_APROBACION_KEY]}"/>

		<tiles:insert page="/pages/tiles/PABlockLayout.jsp">
			<tiles:put name="blockTitle" direct="true"><bean:message key="archigest.archivo.cf.solicitudesVer"/></tiles:put>
			<tiles:put name="blockContent" direct="true">
			<div class="separador8"></div>
			<display:table name="pageScope.solicitudes"
				id="solicitud" 
				export="false"
				style="width:99%;margin-left:auto;margin-right:auto">

				<display:setProperty name="basic.msg.empty_list">
					<bean:message key="archigest.archivo.fondos.noSolicitudes"/>
				</display:setProperty>
				
				<display:column titleKey="archigest.archivo.cf.serie" style="width:40%">
					<c:url var="verSolicitudURL" value="/action/gestionSeries">
						<c:param name="method" value="verSolicitud" />
						<c:param name="idSolicitud" value="${solicitud.id}" />
					</c:url>
					<a href="<c:out value="${verSolicitudURL}" escapeXml="false"/>" class="tdlink">
						<c:out value="${solicitud.etiquetaSerie}"  />
					</a>
				</display:column>
				<display:column titleKey="archigest.archivo.cf.tipo" style="width:15%">
					<fmt:message key="${appConstants.i18nPrefixes.PETICION_CAMBIO_SERIE}${solicitud.tipo}" />		
				</display:column>
				<display:column titleKey="archigest.archivo.cf.fechaSolicitud" style="width:15%">
					<fmt:formatDate value="${solicitud.fecha}" pattern="${FORMATO_FECHA}"/>
				</display:column>
				<display:column titleKey="archigest.archivo.cf.estado" style="width:30%">
					<fmt:message key="archigest.archivo.cf.estadoSolicitudAlta.${solicitud.estado}" />
				</display:column>
			</display:table>
			<div class="separador8"></div>
			</tiles:put>
		</tiles:insert>
		<c:if test="${!empty solicitudes[0]}">
		<div class="pie_bloque_right">
			<c:url var="misSolicitudesAprobacionURL" value="/action/gestionSeries">
				<c:param name="method" value="misSolicitudesAprobacion" />
			</c:url>
			<a class="etiq_pie_bloque" href="<c:out value="${misSolicitudesAprobacionURL}" escapeXml="false"/>">
				<html:img page="/pages/images/go_all.gif" styleClass="imgTextMiddle" /> <bean:message key="archigest.archivo.verMas"/>
			</a>
		</div>
		<div style="height:12px"></div>
		</c:if>
		<div class="separador20"></div>
	</security:permissions>

	<security:permissions action="${appConstants.fondosActions.CONSULTA_SOLICITUDES_SERIE_ACTION}">

		<%-- SOLICITUDES A GESTIONAR --%>
		<c:set var="solicitudes" value="${requestScope[appConstants.fondos.LISTA_SOLICITUDES_A_GESTIONAR_KEY]}"/>

		<tiles:insert page="/pages/tiles/PABlockLayout.jsp">
			<tiles:put name="blockTitle" direct="true"><bean:message key="archigest.archivo.series.solicitudesAGestionar"/></tiles:put>
			<tiles:put name="blockContent" direct="true">
			<div class="separador8"></div>
			<display:table name="pageScope.solicitudes"
				id="solicitud" 
				export="false"
				style="width:99%;margin-left:auto;margin-right:auto">

				<display:setProperty name="basic.msg.empty_list">
					<bean:message key="archigest.archivo.fondos.noSolicitudes"/>
				</display:setProperty>
				
				<display:column titleKey="archigest.archivo.cf.serie" style="width:40%">
					<c:url var="verSolicitudURL" value="/action/gestionSeries">
						<c:param name="method" value="verSolicitud" />
						<c:param name="idSolicitud" value="${solicitud.id}" />
					</c:url>
					<a href="<c:out value="${verSolicitudURL}" escapeXml="false"/>" class="tdlink">
						<c:out value="${solicitud.etiquetaSerie}"  />
					</a>
				</display:column>
				<display:column titleKey="archigest.archivo.cf.tipo" style="width:15%">
					<fmt:message key="${appConstants.i18nPrefixes.PETICION_CAMBIO_SERIE}${solicitud.tipo}" />		
				</display:column>
				<display:column titleKey="archigest.archivo.cf.fechaSolicitud" style="width:15%">
					<fmt:formatDate value="${solicitud.fecha}" pattern="${FORMATO_FECHA}"/>
				</display:column>
				<display:column titleKey="archigest.archivo.cf.estado" style="width:15%">
					<fmt:message key="archigest.archivo.cf.estadoSolicitudAlta.${solicitud.estado}" />
				</display:column>
				<display:column titleKey="archigest.archivo.cf.solicitante" style="width:15%">
					<c:out value="${solicitud.usuarioSolicitante.nombreCompleto}" />
				</display:column>
			</display:table>
			<div class="separador8"></div>
			</tiles:put>
		</tiles:insert>
		<c:if test="${!empty solicitudes[0]}">
		<div class="pie_bloque_right">
			<c:url var="solicitudesAGestionarURL" value="/action/gestionSeries">
				<c:param name="method" value="listaSolicitudesAprobacion" />
			</c:url>
			<a class="etiq_pie_bloque" href="<c:out value="${solicitudesAGestionarURL}" escapeXml="false"/>">
				<html:img page="/pages/images/go_all.gif" styleClass="imgTextMiddle" /> <bean:message key="archigest.archivo.verMas"/>
			</a>
		</div>
		<div style="height:12px"></div>
		</c:if>
	</security:permissions>
	
	</tiles:put>
</tiles:insert>