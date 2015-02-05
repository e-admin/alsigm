<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/taglibs/string" prefix="str" %>

<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>

<c:set var="resumenOcupacion" value="${requestScope[appConstants.deposito.RESUMEN_OCUPACION]}" />

<table class="formulario">
	<tr>
		<td class="tdTitulo" width="200"><bean:message key="archigest.archivo.deposito.total"/> <bean:message key="archigest.archivo.deposito.huecos"/>: </td>
		<td class="tdDatos"><c:out value="${resumenOcupacion.totalHuecos}" /></td>
	</tr>
	<tr>
		<td class="tdTitulo" width="200"><bean:message key="archigest.archivo.deposito.huecos"/> <bean:message key="archigest.archivo.deposito.ocupados"/>: </td>
		<td class="tdDatos"><c:out value="${resumenOcupacion.huecosOcupados}" /></td>
	</tr>
	<tr>
		<td class="tdTitulo"><bean:message key="archigest.archivo.deposito.huecos"/> <bean:message key="archigest.archivo.deposito.libres"/>: </td>
		<td class="tdDatos"><c:out value="${resumenOcupacion.huecosLibres}" /></td>
	</tr>
	<tr>
		<td class="tdTitulo"><bean:message key="archigest.archivo.deposito.huecos"/> <bean:message key="archigest.archivo.deposito.reservados"/>: </td>
		<td class="tdDatos"><c:out value="${resumenOcupacion.huecosReservados}" /></td>
	</tr>
	<tr>
		<td class="tdTitulo"><bean:message key="archigest.archivo.deposito.huecos"/> <bean:message key="archigest.archivo.deposito.inutilizados"/>: </td>
		<td class="tdDatos"><c:out value="${resumenOcupacion.huecosInutilizados}" /></td>
	</tr>
	<tr>
		<td colspan="2" class="separador5">&nbsp;</td>
	</tr>
	
	<c:if test="${resumenOcupacion.totalHuecos > 0}">

	<fmt:formatNumber var="porcentajeLibreStr" value="${(resumenOcupacion.huecosLibres / resumenOcupacion.totalHuecos)*100}" maxFractionDigits="1"/>
	<c:set var="porcentajeLibre">
		<str:replace replace="," with="."><c:out value="${porcentajeLibreStr}"/></str:replace>
	</c:set>
	<fmt:formatNumber var="porcentajeOcupado" value="${100 - porcentajeLibre}" maxFractionDigits="1"/>
	<tr>
		<td colspan="2" align="center">
		<table width="90%" height="18px"><tr>
			<c:if test="${porcentajeLibre >= 90.0 && porcentajeLibre < 100.0}">
				<td bgcolor="#FF6666" class="etiquetaBlanca11Bold" align="center">
					<c:out value="${porcentajeOcupado}" />%
				</td>
			</c:if>
			<c:if test="${porcentajeLibre < 90.0}">
				<td bgcolor="#FF6666" class="etiquetaBlanca11Bold" align="center">
					<c:out value="${porcentajeOcupado}" /> % <bean:message key="archigest.archivo.deposito.espacioOcupado"/>
				</td>
			</c:if>
			<c:if test="${porcentajeLibre > 10.0}">
				<td bgcolor="#66AD6F" class="etiquetaBlanca11Bold" width="<c:out value="${porcentajeLibre}" />%" align="center">
					<c:out value="${porcentajeLibreStr}" /> % <bean:message key="archigest.archivo.deposito.espacioLibre"/>
				</td>
			</c:if>
			<c:if test="${porcentajeLibre > 0.0 && porcentajeLibre <= 10.0 }">
				<td bgcolor="#66AD6F" class="etiquetaBlanca11Bold" width="<c:out value="${porcentajeLibre}" />%" align="center">
					<c:out value="${porcentajeLibreStr}" />%
				</td>
			</c:if>
		</tr></table>
		</td>
	</tr>
	</c:if>
</table>