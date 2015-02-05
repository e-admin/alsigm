<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>

<c:set var="unidadDocumental" value="${requestScope[appConstants.documentos.UNIDAD_DOCUMENTAL_KEY]}"/>

<c:set var="dockableContent" value="true" />

<tiles:importAttribute name="dockableContent" ignore="true" />


<c:if test="${!empty unidadDocumental}">

	<tiles:insert page="/pages/tiles/PADockableBlockLayout.jsp" flush="false">
		<tiles:put name="blockName" direct="true">uDoc</tiles:put>
		<tiles:put name="blockTitle" direct="true"><c:out value="${unidadDocumental.nombreNivel}"/></tiles:put>
		<tiles:put name="dockableContentVisible" direct="true">
				<c:out value="${dockableContent}"/>
		</tiles:put>

		<tiles:put name="dockableContent" direct="true">

			<table class="formulario">
				<tr>
					<td class="tdTitulo" width="200px"><bean:message key="archigest.archivo.cf.codigo"/>:&nbsp;</td>
					<td class="tdDatos"><c:out value="${unidadDocumental.codReferenciaPersonalizado}"/>&nbsp;</td>
				</tr>
				<tr>
					<td class="tdTitulo"><c:out value="${unidadDocumental.nombreNivel}"/>:&nbsp;</td>
					<td class="tdDatos"><c:out value="${unidadDocumental.numExp}"/> <c:out value="${unidadDocumental.titulo}"/>&nbsp;</td>
				</tr>
			</table>

		</tiles:put>
	</tiles:insert>

	<div class="separador8">&nbsp;</div>
</c:if>
