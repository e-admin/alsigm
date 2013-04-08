<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>

<c:set var="serie" value="${requestScope[appConstants.fondos.SERIE_KEY]}" />

<table class="formulario">
	<tr>
		<td width="100px" class="tdTitulo">
			<bean:message key="archigest.archivo.cf.codigo"/>:&nbsp;
		</td>
		<td class="tdDatosBold">
			<c:out value="${serie.codigo}" />
		</td>
	</tr>
	<tr>
		<td width="100px" class="tdTitulo">
			<bean:message key="archigest.archivo.cf.denominacion"/>:&nbsp;
		</td>
		<td class="tdDatosBold">
			<c:out value="${serie.denominacion}" />
		</td>
	</tr>
	<tr>
		<td width="100px" class="tdTitulo">
			<bean:message key="archigest.archivo.cf.estado"/>:&nbsp;
		</td>
		<td class="tdDatosBold">
			<fmt:message key="archigest.archivo.cf.estadoSerie.${serie.estadoserie}" />
		</td>
	</tr>
	<c:if test="${appConstants.configConstants.mostrarCampoOrdenacionCuadro}">
		<tr>
			<td class="tdTitulo">
				<bean:message key="archigest.archivo.cf.codigoOrdenacion"/>:&nbsp;
			</td>
			<td class="tdDatosBold">
				<c:out value="${serie.ordPos}"/>
			</td>
		</tr>
	</c:if>
</table>