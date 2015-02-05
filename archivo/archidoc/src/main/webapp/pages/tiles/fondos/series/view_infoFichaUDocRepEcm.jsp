<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>

<table class="formulario" cellpadding="0" cellspacing="0">
<tr>
	<td class="tdTitulo">
		<bean:message key="archigest.archivo.cf.uDocsSerie"/>
	</td>
</tr>
<tr>
	<td class="tdDatos">
		<table class="tablaFicha">
		<thead>
		<tr>
			<th><bean:message key="archigest.archivo.transferencias.nivelDocumental"/></th>
			<th><bean:message key="archigest.archivo.cf.fichaDescAsociada"/></th>
			<th><bean:message key="archigest.archivo.cf.fichaClasifDocAsociada"/></th>
			<th><bean:message key="archigest.archivo.repositorio.ecm"/></th>
		</tr>
		</thead>
		<tbody>
			<c:forEach var="infoUDoc" items="${infoUDocsSerie}">
				<tr class="odd">
					<td style="tdDatos"><c:out value="${infoUDoc.nivelUDoc.nombre}"> -- </c:out></td>
					<td style="tdDatos"><c:out value="${infoUDoc.ficha.nombre}"> -- </c:out></td>
					<td style="tdDatos"><c:out value="${infoUDoc.fichaClf.nombre}"> -- </c:out></td>
					<td style="tdDatos"><c:out value="${infoUDoc.repositorioEcm.nombre}"> -- </c:out></td>
				</tr>
			</c:forEach>
		</tbody>
		</table>
	</td>
</tr>
</table>