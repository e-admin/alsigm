<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>

<c:set var="FORMATO_FECHA" value="${appConstants.common.FORMATO_FECHA}"/>
<c:set var="FORMAT_ORDEN" value="${appConstants.transferencias.FORMAT_ORDEN}"/>
<c:set var="vRelacion" value="${sessionScope[appConstants.transferencias.RELACION_KEY]}"/>

<tiles:insert page="/pages/tiles/PABlockLayout.jsp">
	<tiles:put name="blockTitle" direct="true">
	<c:choose>
		<c:when test="${vRelacion.nivelDocumental.subtipo == appConstants.fondos.subtiposNivel.CAJA.identificador}">
			<bean:message key="archigest.archivo.transferencias.fraccionSerie"/>
		</c:when>
		<c:otherwise>
			<bean:message key="archigest.archivo.unidadDoc"/>
		</c:otherwise>
	</c:choose>
	</tiles:put>
	<tiles:put name="blockContent" direct="true">
		<c:set var="unidadDocumental" value="${sessionScope[appConstants.transferencias.UNIDAD_DOCUMENTAL]}"/>
		<TABLE class="formulario" cellpadding=0 cellspacing=2>
			<TR>
				<c:choose>
					<c:when test="${vRelacion.nivelDocumental.subtipo == appConstants.fondos.subtiposNivel.CAJA.identificador}">
					<TD width="150px" class="tdTitulo"><bean:message key="archigest.archivo.transferencias.rangoExpedientes"/>:</TD>
					<TD class="tdDatos">
						<table class="tablaFicha">
							<thead>
								<tr>
									<th style="">Desde</th>
									<th style="">Hasta</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="rangosUdoc" items="${unidadDocumental.rangos}">
									<tr class="odd">
										<td style=""><c:out value="${rangosUdoc.desde}" /></td>
										<td style=""><c:out value="${rangosUdoc.hasta}" /></td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</TD>
					</c:when>
					<c:otherwise>
						<TD class="tdTitulo" width="150px"><bean:message key="archigest.archivo.transferencias.expediente"/>:</TD>
						<td class="tdDatos"><c:out value="${unidadDocumental.numeroExpediente}" /></td>
					</c:otherwise>
				</c:choose>
			</tr>
			<TR>
				<TD width="150px" class="tdTitulo"><bean:message key="archigest.archivo.transferencias.asunto"/>:</TD>
				<td class="tdDatos"><c:out value="${unidadDocumental.asunto}" /></td>
			</tr>
			<tr>
				<TD class="tdTitulo"><bean:message key="archigest.archivo.transferencias.fIni"/>:</TD>
				<td class="tdDatos"><fmt:formatDate value="${unidadDocumental.fechaInicio}" pattern="${FORMATO_FECHA}" /></td>
			</tr>
			<tr>
				<TD class="tdTitulo"><bean:message key="archigest.archivo.transferencias.fFin"/>:</TD>
				<td class="tdDatos"><fmt:formatDate value="${unidadDocumental.fechaFin}" pattern="${FORMATO_FECHA}" /></td>
			</TR>
		</TABLE>
		<div style="padding:3px">
		<tiles:insert page="/pages/tiles/PADockableBlockLayout.jsp">
			<tiles:put name="blockName" direct="true">relacionContextoUdoc</tiles:put>
			<c:choose>
				<c:when test="${!vRelacion.isIngresoDirecto}">
					<tiles:put name="blockTitle" direct="true"><bean:message key="archigest.archivo.transferencias.relacionEntrega"/></tiles:put>
				</c:when>
				<c:otherwise>
					<tiles:put name="blockTitle" direct="true"><bean:message key="archigest.archivo.fondos.datos.ingreso.directo"/></tiles:put>
				</c:otherwise>
			</c:choose>
			<tiles:put name="visibleContent" direct="true">
				<tiles:insert page="/pages/tiles/transferencias/relaciones/cabeceracte_relacion.jsp" />
			</tiles:put>
			<tiles:put name="dockableContent" direct="true">
				<c:choose>
					<c:when test="${!vRelacion.isIngresoDirecto}">
						<tiles:insert page="/pages/tiles/transferencias/relaciones/datos_view_relacion.jsp" />
					</c:when>
					<c:otherwise>
						<tiles:insert page="/pages/tiles/transferencias/relaciones/datos_view_ingreso.jsp" />
					</c:otherwise>
				</c:choose>
			</tiles:put>
		</tiles:insert>
		</div>
	</tiles:put>
</tiles:insert>