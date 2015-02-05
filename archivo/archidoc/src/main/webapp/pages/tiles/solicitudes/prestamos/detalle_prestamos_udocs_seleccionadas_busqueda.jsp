<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>

<c:set var="unidadesDocumentales" value="${sessionScope[appConstants.solicitudes.LISTA_DETALLES_UDOCS_SOLICITUD_KEY]}" />
<c:if test="${not empty unidadesDocumentales}">
<div class="separador8">&nbsp;</div>
<tiles:insert template="/pages/tiles/PABlockLayout.jsp">
	<tiles:put name="blockTitle" direct="true">
		<bean:message key="archigest.archivo.detalles"/>
	</tiles:put>
	<tiles:put name="blockContent" direct="true">
				<display:table
					name="pageScope.unidadesDocumentales" id="detallePrestamo"
					style="width:99%;margin-left:auto;margin-right:auto;margin-bottom:auto;margin-top:auto"
					pagesize="0"
				decorator="solicitudes.prestamos.decorators.ViewDetallePrestamoDecorator"
				>
				<display:column titleKey="archigest.archivo.solicitudes.signaturaudoc">
					<c:out value="${detallePrestamo.signaturaudoc}" />
				</display:column>

				<display:column titleKey="archigest.archivo.solicitudes.expedienteudoc" >
					<c:choose>
						<c:when test="${detallePrestamo.subtipoCaja}">
							<html:img page="/pages/images/rango2.gif" width="26" styleClass="imgTextMiddle" />
							<c:if test="${!empty detallePrestamo.nombreRangos}">
								<c:out value="${detallePrestamo.nombreRangos}"/>
							</c:if>
							<div id="divLabelCampoExpedienteFS<c:out value='${detallePrestamo_rowNum}' />"
								>
								<bean:write name="detallePrestamo" property="expedienteudoc"/>
							</div>
						</c:when>
						<c:otherwise>
							<c:out value="${detallePrestamo.expedienteudoc}" />&nbsp;
						</c:otherwise>
					</c:choose>
				</display:column>
				<display:column titleKey="archigest.archivo.solicitudes.titulo" >
						<bean:write name="detallePrestamo" property="titulo"/>
				</display:column>
			</display:table>
			</tiles:put>
		</tiles:insert>
</c:if>