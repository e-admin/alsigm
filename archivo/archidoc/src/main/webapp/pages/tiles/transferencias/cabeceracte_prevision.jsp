<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>

<c:set var="bPrevision" value="${sessionScope[appConstants.transferencias.PREVISION_KEY]}"/>
<c:set var="FORMATO_FECHA" value="${appConstants.common.FORMATO_FECHA}"/>
<c:set var="FORMAT_ORDEN" value="${appConstants.transferencias.FORMAT_ORDEN}"/>

			
<TABLE class="w98m1" cellpadding=0 cellspacing=2>
	<TR>
		<TD width="20%" class="etiquetaAzul11Bold">
			<bean:message key="archigest.archivo.transferencias.prevision"/>:&nbsp;
			<span class="etiquetaNegra12Normal">
			<c:out value="${bPrevision.codigoTransferencia}"/>
			</span>
		</TD>
		<TD width="25%" class="etiquetaAzul11Bold">
			<bean:message key="archigest.archivo.transferencias.estado"/>:&nbsp;
			<span class="etiquetaNegra12Normal">
				<c:out value="${bPrevision.nombreestado}"/>
			</span>
		</TD>
		<TD width="20%" class="etiquetaAzul11Bold">
			<bean:message key="archigest.archivo.transferencias.fEstado"/>:&nbsp;
			<span class="etiquetaNegra12Normal">
				<fmt:formatDate value="${bPrevision.fechaestado}" pattern="${FORMATO_FECHA}" />
			</span>
		</TD>
		<TD width="35%" class="etiquetaAzul11Bold">
			<bean:message key="archigest.archivo.transferencias.gestor"/>:&nbsp;
			<span class="etiquetaNegra12Normal">
				<c:out value="${bPrevision.gestor.nombreCompleto}"/>
			</span>
		</TD>
	</TR>
</TABLE>