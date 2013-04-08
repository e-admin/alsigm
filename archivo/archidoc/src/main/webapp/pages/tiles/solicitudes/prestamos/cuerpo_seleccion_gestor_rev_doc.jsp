<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>

<c:set var="revisionesDoc" value="${sessionScope[appConstants.prestamos.REVISION_DOC_KEY]}" />
<c:set var="gestores" value="${sessionScope[appConstants.prestamos.LISTA_GESTORES_KEY]}" />

<bean:struts id="actionMapping" mapping="/cesionRevisionDocumentacion" />

<script>
	function select(){
		document.forms["<c:out value="${actionMapping.name}" />"].submit();
	}
</script>

<html:form action="/cesionRevisionDocumentacion">
<html:hidden property="tipoBusqueda"/>
<input type="hidden" name="method" value="asignarGestor"/>

<tiles:insert template="/pages/tiles/PABoxLayout.jsp">

	<tiles:put name="boxTitle" direct="true">
		<bean:message key="archigest.archivo.revisiones.documentacion.cederControl"/>
	</tiles:put>

	<tiles:put name="buttonBar" direct="true">
		<table cellpadding="0" cellspacing="0"><tr>
			<td width="10">&nbsp;</td>
	        <td>
				<tiles:insert definition="button.closeButton" flush="true">
					<tiles:put name="labelKey" direct="true">archigest.archivo.atras</tiles:put>
					<tiles:put name="imgIcon" direct="true">/pages/images/Previous.gif</tiles:put>
				</tiles:insert>
		    </td>
			<c:if test="${!empty revisionesDoc}">
			<td width="10">&nbsp;</td>
			<td>
				<a class="etiquetaAzul12Bold" href="javascript:select()" >
				<html:img page="/pages/images/Ok_Si.gif" 
					altKey="archigest.archivo.aceptar" 
					titleKey="archigest.archivo.aceptar" 
					styleClass="imgTextMiddle" />
				&nbsp;<bean:message key="archigest.archivo.aceptar"/>
				</a>
			</td>
			</c:if>
			<td width="10">&nbsp;</td>
	        <td>
				<tiles:insert definition="button.closeButton" flush="true">
					<tiles:put name="action" direct="true">goReturnPoint</tiles:put>
					<tiles:put name="labelKey" direct="true">archigest.archivo.cancelar</tiles:put>
					<tiles:put name="imgIcon" direct="true">/pages/images/Ok_No.gif</tiles:put>
				</tiles:insert>
		    </td>
		</tr></table>
	</tiles:put>

	<tiles:put name="boxContent" direct="true">
		
		<tiles:insert template="/pages/tiles/PABlockLayout.jsp">
			<tiles:put name="blockTitle" direct="true">
				<bean:message key="archigest.archivo.prestamos.revisionDocumentacionACeder"/>:
			</tiles:put>
			
			<tiles:put name="blockContent" direct="true">
			<div class="separador8"></div>

			<display:table name="pageScope.revisionesDoc"
				id="revDoc" 
				style="width:99%;margin-left:auto;margin-right:auto">
				<display:setProperty name="basic.msg.empty_list">
					<bean:message key="archigest.archivo.prestamos.noRevisionesDocumentacionACeder"/>
				</display:setProperty>
				
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

			<div class="separador5"></div>

			<input type="hidden" name="method" value="asignarGestor">
			<table class="w98">
			<tr>
				<td class="etiquetaAzul11Bold">
					&nbsp;&nbsp;<bean:message key="archigest.archivo.prestamos.nuevoGestorPrestamo"/>:&nbsp;&nbsp;
					<select name="gestor">
						<option value=""></option>
						<c:forEach var="gestor" items="${gestores}">
							<option value="<c:out value="${gestor.id}" />"> <c:out value="${gestor.nombreCompleto}" />
						</c:forEach>
					</select>
				</td>
			</tr></table>
			<div class="separador5"></div>
			</tiles:put>
		</tiles:insert>
	</tiles:put>
</tiles:insert>
</html:form>
