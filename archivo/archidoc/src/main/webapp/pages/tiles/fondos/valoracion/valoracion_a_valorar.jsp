<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>

<c:set var="serie" value="${sessionScope[appConstants.valoracion.SERIE_KEY]}" />
<c:set var="valoracion" value="${sessionScope[appConstants.valoracion.VALORACION_KEY]}" />

<table class="formulario">
	<tr>
		<td width="150px" class="tdTitulo">
			<bean:message key="archigest.archivo.cf.codigoSerie"/>:&nbsp;
		</td>
		<td class="tdDatosBold">
			<c:out value="${serie.codigo}" />
		</td>
	</tr>
	<tr>
		<td class="tdTitulo">
			<bean:message key="archigest.archivo.cf.tituloSerie"/>:&nbsp;
		</td>
		<td class="tdDatosBold">
			<c:out value="${serie.denominacion}" />
		</td>
	</tr>
	<tr>
		<td class="tdTitulo">
			<bean:message key="archigest.archivo.cf.estado"/>:&nbsp;
		</td>
		<td class="tdDatosBold">
			<fmt:message key="archigest.archivo.cf.estadoSerie.${serie.estadoserie}" />
		</td>
	</tr>
	<tr><td colspan="2">&nbsp;</td></tr>
	<tr>
		<td class="tdTitulo">
			<bean:message key="archigest.archivo.eliminacion.valoracion.titulo"/>:&nbsp;
		</td>
		<td class="tdDatosBold">
			<c:out value="${valoracion.titulo}" />
		</td>
	</tr>
</table>