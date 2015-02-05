<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo"%>
<c:set var="FORMATO_FECHA" value="${appConstants.common.FORMATO_FECHA}"/>

<c:set var="vRelacion" value="${sessionScope[appConstants.deposito.RELACION_KEY]}"/>

<DIV class="cabecero_bloque">

	<TABLE class="w98m1" cellpadding=0 cellspacing=2>
		<TR>
			<TD width="20%" class="etiquetaAzul11Bold">
				<bean:message key="archigest.archivo.transferencias.relacion"/>:&nbsp;
				<span class="etiquetaNegra12Normal">
					<c:out value="${vRelacion.codigoTransferencia}"/>
				</span>
			</TD>
			<TD width="25%" class="etiquetaAzul11Bold">
				<bean:message key="archigest.archivo.transferencias.estado"/>:&nbsp;
				<span class="etiquetaNegra12Normal">
					<c:out value="${vRelacion.nombreestado}"/>
				</span>
			</TD>
			<TD width="20%" class="etiquetaAzul11Bold">
				<bean:message key="archigest.archivo.transferencias.fEstado"/>:&nbsp;
				<span class="etiquetaNegra12Normal">
					<fmt:formatDate value="${vRelacion.fechaestado}" pattern="${FORMATO_FECHA}" />
				</span>
			</TD>
			<TD width="35%" class="etiquetaAzul11Bold">
				<bean:message key="archigest.archivo.transferencias.gestor"/>:&nbsp;
				<span class="etiquetaNegra12Normal">
					<c:set var="gestorEnOrganoRemitente" value="${vRelacion.gestorEnOrganoRemitente}" />
					<c:out value="${gestorEnOrganoRemitente.apellidos}"/>,
					<c:out value="${gestorEnOrganoRemitente.nombre}"/>
				</span>
			</TD>
		</TR>
		<TR>
			<TD colspan="2" class="etiquetaAzul11Bold">
				Número de Unidades de Instalación:&nbsp;
				<span class="etiquetaNegra12Normal"><c:out value="${vRelacion.numeroUnidadesInstalacion}"/>
				</span>
			</TD>
			<TD colspan="2" class="etiquetaAzul11Bold">
				Formato:&nbsp;
				<span class="etiquetaNegra12Normal"><c:out value="${vRelacion.formato.nombre}"/>
				</span>
			</TD>
	</TABLE>
</DIV>
<div class="separador5">&nbsp;</div>
