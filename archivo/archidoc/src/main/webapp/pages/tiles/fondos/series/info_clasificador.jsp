<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>

<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>

<c:set var="clasificadorSeries" value="${sessionScope[appConstants.fondos.ELEMENTO_CF_KEY]}" />
<c:set var="nivel" value="${sessionScope[appConstants.fondos.NIVEL_CF_KEY]}" />


	<table class="formulario">
		<tr>
			<td width="100px" class="tdTitulo">
				<bean:message key="archigest.archivo.cf.codigo"/>:&nbsp;
			</td>
			<td class="tdDatosBold">
				<c:out value="${clasificadorSeries.codReferencia}" />
			</td>
		</tr>
		<tr>
			<td width="100px" class="tdTitulo">
				<bean:message key="archigest.archivo.cf.denominacion"/>:&nbsp;
			</td>
			<td class="tdDatosBold">
				<c:out value="${clasificadorSeries.titulo}" />
			</td>
		</tr>
	</table>
	<div class="separador5">&nbsp;</div>
	<table class="formulario">
		<tr>
			<td width="100px" class="tdTitulo">
				<bean:message key="archigest.archivo.cf.tipo"/>:&nbsp;
			</td>
			<td class="tdDatos">
				<c:out value="${nivel.nombre}" />
			</td>
		</tr>
		<tr>
			<td width="100px" class="tdTitulo">
				<bean:message key="archigest.archivo.cf.estado"/>:&nbsp;
			</td>
			<td class="tdDatos">
				<fmt:message key="archigest.archivo.cf.estadoElementoCF.${clasificadorSeries.estado}" />
			</td>
		</tr>
	</table>