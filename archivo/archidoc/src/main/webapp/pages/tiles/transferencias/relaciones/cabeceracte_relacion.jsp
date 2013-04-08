<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>

<c:set var="FORMATO_FECHA" value="${appConstants.common.FORMATO_FECHA}"/>
<c:set var="FORMAT_ORDEN" value="${appConstants.transferencias.FORMAT_ORDEN}"/>

<c:set var="vRelacion" value="${sessionScope[appConstants.transferencias.RELACION_KEY]}"/>

<TABLE class="w98m1" cellpadding=0 cellspacing=2>
	<TR>
		<TD width="20%" class="etiquetaAzul11Bold">
			<c:choose>
				<c:when test="${vRelacion.isIngresoDirecto}">
					<bean:message key="archigest.archivo.codigo"/>:&nbsp;
				</c:when>
				<c:otherwise>
					<bean:message key="archigest.archivo.transferencias.relacion"/>:&nbsp;
				</c:otherwise>
			</c:choose>
			<span class="etiquetaNegra11Normal">
				<c:out value="${vRelacion.codigoTransferencia}"/>
			</span>
		</TD>

		<c:if test="${not vRelacion.isIngresoDirecto}">
			<TD width="25%" class="etiquetaAzul11Bold">
				<bean:message key="archigest.archivo.transferencias.estado"/>:&nbsp;
				<span class="etiquetaNegra11Normal">
					<c:out value="${vRelacion.nombreestado}"/>
				</span>
			</TD>
		</c:if>
		<TD width="20%" class="etiquetaAzul11Bold">
			<c:choose>
			<c:when test="${not vRelacion.isIngresoDirecto}">
				<bean:message key="archigest.archivo.transferencias.fEstado"/>:&nbsp;
			</c:when>
			<c:otherwise>
				<bean:message key="archigest.archivo.fecha"/>:&nbsp;
			</c:otherwise>
			</c:choose>
			<span class="etiquetaNegra11Normal">
				<fmt:formatDate value="${vRelacion.fechaestado}" pattern="${FORMATO_FECHA}" />
			</span>
		</TD>
		<TD width="35%" class="etiquetaAzul11Bold">
			<bean:message key="archigest.archivo.transferencias.gestor"/>:&nbsp;
			<span class="etiquetaNegra11Normal">
				<c:out value="${vRelacion.gestorEnOrganoRemitente.nombreCompleto}"/>
			</span>
		</TD>
	</TR>
</TABLE>