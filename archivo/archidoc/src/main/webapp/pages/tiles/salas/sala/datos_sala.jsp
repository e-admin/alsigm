<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>

<c:set var="sala" value="${sessionScope[appConstants.salas.SALA_KEY]}" />

<table class="formulario">
	<tr>
		<td width="200px" class="tdTitulo">
			<bean:message key="archigest.archivo.nombre"/>:&nbsp;
		</td>
		<td class="tdDatos">
			<c:out value="${sala.nombre}"/>
		</td>
	</tr>
	<tr>
		<td class="tdTitulo">
			<bean:message key="archigest.archivo.descripcion"/>:&nbsp;
		</td>
		<td class="tdDatos">
			<c:out value="${sala.descripcion}"/>
		</td>
	</tr>
	<tr>
		<td class="tdTitulo">
			<bean:message key="archigest.archivo.salas.equipo.informatico"/>:&nbsp;
		</td>
		<td class="tdDatos">
			<c:choose>
				<c:when test="${sala.equipoInformatico == 'S'}">
					<html:img page="/pages/images/checkbox-yes.gif" titleKey="archigest.archivo.salas.equipo.informatico" />
				</c:when>
				<c:otherwise>
					<html:img page="/pages/images/checkbox-no.gif" titleKey="archigest.archivo.salas.equipo.informatico" />
				</c:otherwise>
			</c:choose>
		</td>
	</tr>
	<tr>
		<td class="tdTitulo">
			<bean:message key="archigest.archivo.salas.mesas"/>:&nbsp;
		</td>
		<td class="tdDatos">
			 <c:out value="${sala.numMesas}"/>
		</td>
	</tr>
</table>