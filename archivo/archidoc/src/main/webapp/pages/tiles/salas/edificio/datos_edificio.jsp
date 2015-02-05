<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>

<c:set var="edificio" value="${sessionScope[appConstants.salas.EDIFICIO_KEY]}" />

<table class="formulario">
	<tr>
		<td width="100px" class="tdTitulo">
			<bean:message key="archigest.archivo.deposito.nombre"/>:&nbsp;
		</td>
		<td class="tdDatos">
			<html:hidden property="idEdificio" styleId="idEdificio"/>
			<c:out value="${edificio.nombre}"/>
		</td>
	</tr>
	<tr>
		<td class="tdTitulo">
			<bean:message key="archigest.archivo.salas.edificio.ubicacion.label"/>:&nbsp;
		</td>
		<td class="tdDatos">
			<c:out value="${edificio.ubicacion}"/>
		</td>
	</tr>
	<tr>
		<td class="tdTitulo">
			<bean:message key="archigest.archivo.salas.edificio.archivo.label"/>:&nbsp;
		</td>
		<td class="tdDatos">
			<c:out value="${edificio.nombreArchivo}"/>
		</td>
	</tr>
</table>