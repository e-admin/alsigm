<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>

<c:set var="previsiones" value="${sessionScope[appConstants.transferencias.LISTA_PREVISIONES_KEY]}" />
<c:set var="gestor" value="${requestScope[appConstants.transferencias.GESTOR_KEY]}" />

<tiles:insert template="/pages/tiles/PABoxLayout.jsp">

	<tiles:put name="boxTitle" direct="true">
		<bean:message key="archigest.archivo.transferencias.previsiones.cederControl"/>
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
					<bean:message key="archigest.archivo.transferencias.previsiones.gestorAsignadoMsg"/>
				</span>
			</tiles:put>
			<tiles:put name="blockContent" direct="true">
			<span class="separador5"></span>
			<display:table name="pageScope.previsiones"
				id="prevision" 
				style="width:99%;margin-left:auto;margin-right:auto">
				<display:column titleKey="archigest.archivo.transferencias.prevision">
					<c:out value="${prevision.codigoTransferencia}"/>
				</display:column>
				
				<display:column titleKey="archigest.archivo.transferencias.tipoTransf">
					<c:set var="keyTitulo">
						archigest.archivo.transferencias.tipooperacion<c:out value="${prevision.tipooperacion}"/>
					</c:set>
					<fmt:message key="${keyTitulo}" />		
				</display:column>
				
				<display:column titleKey="archigest.archivo.transferencias.estado" >
					<fmt:message key="archigest.archivo.transferencias.estadoPrevision.${prevision.estado}" />
				</display:column>

				<display:column titleKey="archigest.archivo.transferencias.fEstado">
					<fmt:formatDate value="${prevision.fechaestado}" 
						pattern="${appConstants.common.FORMATO_FECHA}" />
				</display:column>
			</display:table>
			<div class="separador8">&nbsp;</div>
			</tiles:put>
		</tiles:insert>
	</tiles:put>
</tiles:insert>