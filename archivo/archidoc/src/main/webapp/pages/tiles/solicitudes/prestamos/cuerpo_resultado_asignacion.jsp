<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>

<c:set var="prestamos" value="${sessionScope[appConstants.prestamos.PRESTAMO_KEY]}" />
<c:set var="gestor" value="${requestScope[appConstants.prestamos.GESTOR_KEY]}" />

<tiles:insert template="/pages/tiles/PABoxLayout.jsp">

	<tiles:put name="boxTitle" direct="true">
		<bean:message key="archigest.archivo.prestamos.cederControl"/>
	</tiles:put>

	<tiles:put name="buttonBar" direct="true">
		<table cellpadding="0" cellspacing="0"><tr>
	        <td>
				<tiles:insert definition="button.closeButton" flush="true">
					<tiles:put name="action" direct="true">goReturnPoint</tiles:put>
				</tiles:insert>
		    </td>
		</tr></table>
	</tiles:put>

	<tiles:put name="boxContent" direct="true">
		<tiles:insert template="/pages/tiles/PABlockLayout.jsp">
			<tiles:put name="blockTitle" direct="true">
				<c:out value="${gestor.nombreCompleto}"/>&nbsp;
				<span class="etiquetaNegra12Normal">
					<bean:message key="archigest.archivo.prestamos.gestorAsignadoMsg"/>
				</span>
			</tiles:put>
			<tiles:put name="blockContent" direct="true">
			<span class="separador5"></span>
			<display:table name="pageScope.prestamos"
				id="prestamo" 
				style="width:99%;margin-left:auto;margin-right:auto">
				<display:column titleKey="archigest.archivo.prestamos.prestamo">
					<c:out value="${prestamo.codigoTransferencia}"/>
				</display:column>
				<display:column titleKey="archigest.archivo.prestamos.norgsolicitante" property="norgsolicitante" />
				<display:column titleKey="archigest.archivo.prestamos.nusrsolicitante" property="nusrsolicitante"/>
				<display:column titleKey="archigest.archivo.prestamos.estado">
					<fmt:message key="archigest.archivo.solicitudes.estado.${prestamo.estado}" />
				</display:column>
				<display:column titleKey="archigest.archivo.prestamos.festado">
					<fmt:formatDate value="${prestamo.festado}" 
						pattern="${appConstants.common.FORMATO_FECHA}" />
				</display:column>
				<display:column titleKey="archigest.archivo.prestamos.fPrevDev">
					<fmt:formatDate value="${prestamo.fmaxfinprestamo}" 
						pattern="${appConstants.common.FORMATO_FECHA}" />
				</display:column>
			</display:table>
			<div class="separador8">&nbsp;</div>
			</tiles:put>
		</tiles:insert>
	</tiles:put>
</tiles:insert>