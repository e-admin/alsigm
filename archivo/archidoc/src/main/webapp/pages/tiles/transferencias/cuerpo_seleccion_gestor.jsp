<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>

<c:set var="previsiones" value="${sessionScope[appConstants.transferencias.LISTA_PREVISIONES_KEY]}" />
<c:set var="gestores" value="${sessionScope[appConstants.transferencias.LISTA_GESTORES_KEY]}" />

<bean:struts id="actionMapping" mapping="/cesionPrevisiones" />

<script>
	function select() 
	{
		document.forms["<c:out value="${actionMapping.name}" />"].submit();
	}
</script>

<html:form action="/cesionPrevisiones">
<html:hidden property="tipoBusqueda"/>
<input type="hidden" name="method" value="asignarGestor"/>

<tiles:insert template="/pages/tiles/PABoxLayout.jsp">

	<tiles:put name="boxTitle" direct="true">
		<bean:message key="archigest.archivo.transferencias.previsiones.cederControl"/>
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
			<c:if test="${!empty previsiones}">
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
				<bean:message key="archigest.archivo.transferencias.previsiones.modificables"/>:
			</tiles:put>
			
			<tiles:put name="blockContent" direct="true">
			<span class="separador5"></span>

			<display:table name="pageScope.previsiones"
				id="prevision" 
				style="width:99%;margin-left:auto;margin-right:auto">
				<display:setProperty name="basic.msg.empty_list">
					<bean:message key="archigest.archivo.transferencias.previsiones.noPrevisionesACeder"/>
				</display:setProperty>

				<display:column titleKey="archigest.archivo.transferencias.prevision">
					<c:out value="${prevision.codigoTransferencia}"/>
					<input type="hidden" name="previsionesSeleccionadas"
						value="<c:out value="${prevision.id}"/>"/>
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

			<span class="separador5"></span>

			<input type="hidden" name="method" value="asignarGestor">
			<table class="w98">
			<tr>
				<td class="etiquetaAzul12Bold">
					<bean:message key="archigest.archivo.transferencias.previsiones.nuevoGestor"/>:&nbsp;&nbsp;
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