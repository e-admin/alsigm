<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>

<c:set var="descriptor" value="${requestScope[appConstants.documentos.DESCRIPTOR_KEY]}"/>
<c:set var="dockableContent" value="true" />

<tiles:importAttribute name="dockableContent" ignore="true" />


<c:if test="${!empty descriptor}">

	<tiles:insert page="/pages/tiles/PADockableBlockLayout.jsp"  flush="false">
		<tiles:put name="blockName" direct="true">descriptor</tiles:put>
		<tiles:put name="blockTitle" direct="true"><bean:message key="archigest.archivo.descripcion.descriptor.form.descriptor.caption"/></tiles:put>
		<tiles:put name="dockableContentVisible" direct="true"><c:out value="${dockableContent}"/></tiles:put>
		<tiles:put name="dockableContent" direct="true">
			<table class="formulario">
				<tr>
					<td class="tdTitulo" width="200px"><bean:message key="archigest.archivo.descripcion.descriptor.form.nombre"/>:&nbsp;</td>
					<td class="tdDatos"><c:out value="${descriptor.nombre}"/>&nbsp;</td>
				</tr>
				<tr>
					<td class="tdTitulo"><bean:message key="archigest.archivo.descripcion.descriptor.form.nombreLista"/>:&nbsp;</td>
					<td class="tdDatos"><c:out value="${descriptor.nombreLista}"/>&nbsp;</td>
				</tr>
				<tr>
					<td class="tdTitulo"><bean:message key="archigest.archivo.descripcion.descriptor.form.tipo"/>:&nbsp;</td>
					<td class="tdDatos">
						<c:choose>
							<c:when test="${descriptor.tipo == 0}">
								<bean:message key="archivo.descriptores.tipo.sin_tipo_especifico"/>
							</c:when>
							<c:when test="${descriptor.tipo == 1}">
								<bean:message key="archivo.descriptores.tipo.entidad"/>
							</c:when>
							<c:when test="${descriptor.tipo == 2}">
								<bean:message key="archivo.descriptores.tipo.geografico"/>
							</c:when>
							<c:when test="${descriptor.tipo == 3}">
								<bean:message key="archivo.descriptores.tipo.materia"/>
							</c:when>
							<c:otherwise>&nbsp;</c:otherwise>
						</c:choose>
					</td>
				</tr>
			</table>
		</tiles:put>
	</tiles:insert>
	<div class="separador8">&nbsp;</div>

</c:if>
