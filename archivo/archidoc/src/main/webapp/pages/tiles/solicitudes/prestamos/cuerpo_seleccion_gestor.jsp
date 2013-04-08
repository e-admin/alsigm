<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>

<c:set var="prestamos" value="${sessionScope[appConstants.prestamos.PRESTAMO_KEY]}" />
<c:set var="gestores" value="${sessionScope[appConstants.prestamos.LISTA_GESTORES_KEY]}" />

<bean:struts id="actionMapping" mapping="/cesionPrestamos" />

<script>
	function select() 
	{
		document.forms["<c:out value="${actionMapping.name}" />"].submit();
	}
</script>

<html:form action="/cesionPrestamos">
<html:hidden property="tipoBusqueda"/>
<input type="hidden" name="method" value="asignarGestor"/>

<tiles:insert template="/pages/tiles/PABoxLayout.jsp">

	<tiles:put name="boxTitle" direct="true">
		<bean:message key="archigest.archivo.prestamos.cederControl"/>
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
			<c:if test="${!empty prestamos}">
			<td width="10">&nbsp;</td>
			<td>
				<a class="etiquetaAzul12Bold" href="javascript:select()" >
				<html:img page="/pages/images/Ok_Si.gif" 
					altKey="archigest.archivo.aceptar" 
					titleKey="archigest.archivo.aceptar" 
					styleClass="imgTextBottom" />
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
				<bean:message key="archigest.archivo.prestamos.prestamoACeder"/>:
			</tiles:put>
			
			<tiles:put name="blockContent" direct="true">
			<span class="separador5"></span>

			<display:table name="pageScope.prestamos"
				id="prestamo" 
				style="width:99%;margin-left:auto;margin-right:auto">
				<display:setProperty name="basic.msg.empty_list">
					<bean:message key="archigest.archivo.prestamos.noPrestamosACeder"/>
				</display:setProperty>

				<display:column titleKey="archigest.archivo.prestamos.prestamo">
					<c:out value="${prestamo.codigoTransferencia}"/>
					<input type="hidden" name="prestamosSeleccionados"
						value="<c:out value="${prestamo.id}"/>"/>
				</display:column>
				<display:column titleKey="archigest.archivo.prestamos.norgsolicitante" property="norgsolicitante" />
				<display:column titleKey="archigest.archivo.prestamos.nusrsolicitante" property="nusrsolicitante"/>
				<display:column titleKey="archigest.archivo.solicitudes.estado">
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

			<span class="separador5"></span>

			<input type="hidden" name="method" value="asignarGestor">
			<table class="w98">
			<tr>
				<td class="etiquetaAzul12Bold">
					<bean:message key="archigest.archivo.prestamos.nuevoGestorPrestamo"/>:&nbsp;&nbsp;
					<select name="gestor">
						<c:forEach var="gestor" items="${gestores}">
							<option value="<c:out value="${gestor.id}" />"> <c:out value="${gestor.nombreCompleto}" />
						</c:forEach>
					</select>
				</td>
			</tr></table>
			<span class="separador5"></span>
			</tiles:put>
		</tiles:insert>
	</tiles:put>
</tiles:insert>
</html:form>
