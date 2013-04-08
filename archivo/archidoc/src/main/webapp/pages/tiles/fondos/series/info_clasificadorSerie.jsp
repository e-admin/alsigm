<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>

<c:set var="clasificadorSeries" value="${sessionScope[appConstants.fondos.ELEMENTO_CF_KEY]}"/>
<c:set var="nivel" value="${sessionScope[appConstants.fondos.NIVEL_CF_KEY]}" />

<tr>
	<td width="180px" class="tdTitulo">
		<bean:message key="archigest.archivo.cf.codReferencia"/>:&nbsp;
	</td>
	<td class="tdDatosBold">
		<c:out value="${clasificadorSeries.codReferenciaPersonalizado}" />
	</td>
</tr>
<tr>
	<td class="tdTitulo">
		<bean:message key="archigest.archivo.cf.denominacion"/>:&nbsp;
	</td>
	<td class="tdDatosBold">
		<c:out value="${clasificadorSeries.titulo}" />
	</td>
</tr>
<tr>
	<td colspan="2">
		<div class="separador5">&nbsp;</div>
	</td>
</tr>
<tr>
	<td class="tdTitulo">
		<bean:message key="archigest.archivo.cf.tipo"/>:&nbsp;
	</td>
	<td class="tdDatos">
		<c:out value="${nivel.nombre}" />
	</td>
</tr>
<tr>
	<td class="tdTitulo">
		<bean:message key="archigest.archivo.cf.estado"/>:&nbsp;
	</td>
	<td class="tdDatos">
		<fmt:message key="archigest.archivo.cf.estadoElementoCF.${clasificadorSeries.estado}" />
	</td>
</tr>
<c:if test="${appConstants.configConstants.mostrarCampoOrdenacionCuadro}">
	<tr>
		<td class="tdTitulo">
			<bean:message key="archigest.archivo.cf.codigoOrdenacion"/>:&nbsp;
		</td>
		<td class="tdDatosBold">
			<c:out value="${clasificadorSeries.ordPos}"/>
		</td>
	</tr>
</c:if>