<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>

<c:set var="FORMATO_FECHA" value="${appConstants.common.FORMATO_FECHA}"/>
<c:set var="FORMAT_ORDEN" value="${appConstants.transferencias.FORMAT_ORDEN}"/>

<c:set var="vRelacion" value="${sessionScope[appConstants.transferencias.RELACION_KEY]}"/>

<tiles:insert page="/pages/tiles/transferencias/relaciones/cabeceracte_relacion.jsp" />

<div class="separador1">&nbsp;</div>

	<c:set var="unidadDocumental" value="${sessionScope[appConstants.transferencias.UNIDAD_DOCUMENTAL]}"/>

	<TABLE class="w98m1" cellpadding=0 cellspacing=2>
		<TR>
			<TD width="30%" class="etiquetaAzul11Bold">
				<bean:message key="archigest.archivo.transferencias.expediente"/>:&nbsp;
				<span class="etiquetaNegra11Normal">
					<c:out value="${unidadDocumental.numeroExpediente}" />
				</span>
			</TD>
			<TD width="35%" class="etiquetaAzul11Bold">
				<bean:message key="archigest.archivo.transferencias.fIni"/>:&nbsp;
				<span class="etiquetaNegra11Normal">
					<fmt:formatDate value="${unidadDocumental.fechaInicio}" pattern="${FORMATO_FECHA}" />
				</span>
			</TD>
			<TD width="30%" class="etiquetaAzul11Bold">
				<bean:message key="archigest.archivo.transferencias.fFin"/>:&nbsp;
				<span class="etiquetaNegra11Normal">
					<fmt:formatDate value="${unidadDocumental.fechaFin}" pattern="${FORMATO_FECHA}" />
				</span>
			</TD>
		</TR>
	</TABLE>

