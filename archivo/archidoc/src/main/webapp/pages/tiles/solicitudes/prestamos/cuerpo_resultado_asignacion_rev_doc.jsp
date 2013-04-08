<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>

<c:set var="revisionesDoc" value="${sessionScope[appConstants.prestamos.REVISION_DOC_KEY]}" />
<c:set var="gestor" value="${requestScope[appConstants.prestamos.GESTOR_KEY]}" />

<tiles:insert template="/pages/tiles/PABoxLayout.jsp">

	<tiles:put name="boxTitle" direct="true">
		<bean:message key="archigest.archivo.revisiones.documentacion.cederControl"/>
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
				<span class="etiquetaAzul11Bold">
					<c:out value="${gestor.nombreCompleto}"/>&nbsp;
				</span>
				<span class="etiquetaNegra11Normal">
					<bean:message key="archigest.archivo.prestamos.revisionesDoc.gestorAsignadoMsg"/>
				</span>
			</tiles:put>
			<tiles:put name="blockContent" direct="true">
			<div class="separador8"></div>
			<display:table name="pageScope.revisionesDoc"
					id="revDoc" 
					style="width:99%;margin-left:auto;margin-right:auto">
				<display:column titleKey="archigest.archivo.titulo">
					<c:out value="${revDoc.titulo}"/>
					<input type="hidden" name="revisionDocSeleccionada"
						value="<c:out value="${revDoc.idRevDoc}"/>"/>
				</display:column>
				<display:column titleKey="archigest.archivo.solicitudes.numExp" property="expedienteUdoc"/>
				<display:column titleKey="archigest.archivo.signatura" property="signaturaUdoc"/>
				<display:column titleKey="archigest.archivo.solicitudes.observaciones" property="observaciones"/>
				<display:column titleKey="archigest.archivo.prestamos.gestor" property="nombreGestor"/>
			</display:table>
			<div class="separador8">&nbsp;</div>
			</tiles:put>
		</tiles:insert>
	</tiles:put>
</tiles:insert>