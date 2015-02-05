<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>

<c:set var="clasificadorFondos" value="${sessionScope[appConstants.fondos.ELEMENTO_CF_KEY]}"/>

<tr>
	<td width="180px" class="tdTitulo">
		<bean:message key="archigest.archivo.cf.codReferencia"/>:&nbsp;
	</td>
	<td class="tdDatosBold" nowrap="nowrap">
		<c:out value="${clasificadorFondos.codReferencia}" />
	</td>
</tr>
<tr>
	<td width="180px" class="tdTitulo">
		<bean:message key="archigest.archivo.cf.codigo"/>:&nbsp;
	</td>
	<td class="tdDatosBold">
		<c:out value="${clasificadorFondos.codigo}" />
	</td>
</tr>
<tr>
	<td width="180px" class="tdTitulo">
		<bean:message key="archigest.archivo.cf.denominacion"/>:&nbsp;
	</td>
	<td class="tdDatosBold">
		<c:out value="${clasificadorFondos.titulo}" />
	</td>
</tr>
<c:if test="${appConstants.configConstants.mostrarCampoOrdenacionCuadro}">
	<tr>
		<td class="tdTitulo">
			<bean:message key="archigest.archivo.cf.codigoOrdenacion"/>:&nbsp;
		</td>
		<td class="tdDatosBold">
			<c:out value="${clasificadorFondos.ordPos}" />
		</td>
	</tr>
</c:if>
