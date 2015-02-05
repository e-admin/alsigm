<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/taglibs/securityTag.tld" prefix="security"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>

<c:set var="elementoNoAsignable" value="${sessionScope[appConstants.deposito.ELEMENTO_NO_ASIGNABLE_KEY]}" />
<c:set var="ubicacion" value="${sessionScope[appConstants.deposito.EDIFICIO_KEY]}" />

<tiles:insert template="/pages/tiles/PABlockLayout.jsp">
	<tiles:put name="blockTitle" direct="true">
		<bean:message key="archigest.archivo.deposito.datosElemNoAsignable" />
	</tiles:put>
	<tiles:put name="buttonBar" direct="true">
		<c:if test="${informeOcupacion}">
			<TABLE cellpadding=0 cellspacing=0>
			<TR>
				<TD>
				<c:url var="verOcupacionURL" value="/action/gestionTipoNoAsignableAction">
					<c:param name="method" value="verOcupacion" />
					<c:choose>
						<c:when test="${not empty elementoNoAsignable}">
							<c:param name="idNoAsignable" value="${elementoNoAsignable.id}" />
						</c:when>
						<c:when test="${empty elementoNoAsignable}">
							<c:param name="idNoAsignable" value="${ubicacion.id}" />
						</c:when>
					</c:choose>
				</c:url>
				<a class="etiquetaAzul12Bold" href="<c:out value="${verOcupacionURL}" escapeXml="false"/>" >
					<html:img page="/pages/images/ocupacion.gif" altKey="archigest.archivo.deposito.informeOcupacion" titleKey="archigest.archivo.deposito.informeOcupacion" styleClass="imgTextMiddle" />
					<bean:message key="archigest.archivo.deposito.informeOcupacion" />
				</a>
				</TD>
			</TR>
			</TABLE>
		</c:if>
	</tiles:put>
	<tiles:put name="blockContent" direct="true">
	<table class="formulario">
		<c:choose>
			<c:when test="${not empty elementoNoAsignable}">
			<tr>
				<td width="100px" class="tdTitulo">
					<bean:message key="archigest.archivo.deposito.nombre"/>:&nbsp;
				</td>
				<td class="tdDatosBold">
					<c:out value="${elementoNoAsignable.pathName}" />
				</td>
			</tr>
			</c:when>
			<c:when test="${empty elementoNoAsignable}">
			<tr>
				<td width="100px" class="tdTitulo">
					<bean:message key="archigest.archivo.deposito.nombre"/>:&nbsp;
				</td>
				<td class="tdDatosBold">
					<c:out value="${ubicacion.nombre}" />
				</td>
			</tr>
			<tr>
				<td class="tdTitulo">
					<bean:message key="archigest.archivo.deposito.ubicacion"/>:&nbsp;
				</td>
				<td class="tdDatos">
					<c:out value="${ubicacion.ubicacion}" />
				</td>
			</tr>
			<tr>
				<td class="tdTitulo">
					<bean:message key="archigest.archivo.archivo"/>:&nbsp;
				</td>
				<td class="tdDatos">
					<c:out value="${ubicacion.nombreArchivo}" />
				</td>
			</tr>
			</c:when>
		</c:choose>
	</table>
	</tiles:put>
</tiles:insert>