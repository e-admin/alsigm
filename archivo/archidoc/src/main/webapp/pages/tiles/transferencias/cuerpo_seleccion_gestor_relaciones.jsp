<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>

<c:set var="tipoRelacion" value="${sessionScope[appConstants.transferencias.TIPO_RELACION_KEY]}" />
<c:set var="relaciones" value="${sessionScope[appConstants.transferencias.LISTA_RELACIONES_KEY]}" />
<c:set var="gestores" value="${sessionScope[appConstants.transferencias.LISTA_GESTORES_KEY]}" />

<bean:struts id="actionMappingArchivo" mapping="/cesionRelacionesArchivo" />
<bean:struts id="actionMappingOrgRemitente" mapping="/cesionRelacionesOrgRemitente" />
<bean:struts id="actionMappingIngreso" mapping="/cesionIngresoDirecto" />

<c:choose>
	<c:when test="${tipoRelacion == appConstants.transferencias.RELACION_EN_ARCHIVO}">
		<c:set var="mappingName" value="${actionMappingArchivo.name}" />
		<c:set var="mappingPath" value="${actionMappingArchivo.path}" />
	</c:when>
	<c:otherwise>
		<c:choose>
			<c:when test="${tipoRelacion == appConstants.transferencias.RELACION_EN_ORG_REMITENTE}">
				<c:set var="mappingName" value="${actionMappingOrgRemitente.name}" />
				<c:set var="mappingPath" value="${actionMappingOrgRemitente.path}" />
			</c:when>
			<c:otherwise>
				<c:set var="mappingName" value="${actionMappingIngreso.name}" />
				<c:set var="mappingPath" value="${actionMappingIngreso.path}" />
			</c:otherwise>
		</c:choose>
	</c:otherwise>
</c:choose>	

<script>
	function select() 
	{
		document.forms["<c:out value="${mappingName}" />"].submit();
	}
</script>

<form name="<c:out value="${mappingName}" />" action="../../action<c:out value="${mappingPath}" escapeXml="false"/>">
<input type="hidden" name="tipoBusqueda" value="<c:out value="${CesionRelacionesForm.tipoBusqueda}" />" />
<input type="hidden" name="method" value="asignarGestor"/>

<tiles:insert template="/pages/tiles/PABoxLayout.jsp">

	<tiles:put name="boxTitle" direct="true">
		<c:choose>
			<c:when test="${tipoRelacion == appConstants.transferencias.RELACION_EN_ARCHIVO}">
				<bean:message key="archigest.archivo.transferencias.relaciones.archivo.cederControl"/>
			</c:when>
			<c:otherwise>
				<c:choose>
					<c:when test="${tipoRelacion == appConstants.transferencias.RELACION_EN_ORG_REMITENTE}">
						<bean:message key="archigest.archivo.transferencias.relaciones.orgrem.cederControl"/>
					</c:when>
					<c:otherwise>
						<bean:message key="archigest.archivo.fondos.ingreso.directo.cederControl"/>
					</c:otherwise>
				</c:choose>
			</c:otherwise>
		</c:choose>	
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
			<c:if test="${!empty relaciones}">
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
				<c:choose>
					<c:when test="${tipoRelacion == appConstants.transferencias.INGRESO_DIRECTO_KEY}">
						<bean:message key="archigest.archivo.fondos.ingreso.directo.modificables"/>:
					</c:when>
					<c:otherwise>
						<bean:message key="archigest.archivo.transferencias.relaciones.modificables"/>:
					</c:otherwise>
				</c:choose>
			</tiles:put>
			
			<tiles:put name="blockContent" direct="true">
			<span class="separador5"></span>

			<display:table name="pageScope.relaciones"
				id="relacion" 
				style="width:99%;margin-left:auto;margin-right:auto">
				<display:setProperty name="basic.msg.empty_list">
					<bean:message key="archigest.archivo.transferencias.relaciones.noRelacionesACeder"/>
				</display:setProperty>

				<c:choose>
					<c:when test="${tipoRelacion == appConstants.transferencias.INGRESO_DIRECTO_KEY}">
						<display:column titleKey="archigest.archivo.fondos.ingreso.directo" sortable="true" headerClass="sortable" >
							<c:out value="${relacion.codigoTransferencia}"/>
							<input type="hidden" name="relacionesSeleccionadas" 
								value="<c:out value="${relacion.id}"/>"/>
						</display:column>					
					</c:when>
					<c:otherwise>
						<display:column titleKey="archigest.archivo.relacion" sortable="true" headerClass="sortable" >
							<c:out value="${relacion.codigoTransferencia}"/>
							<input type="hidden" name="relacionesSeleccionadas" 
								value="<c:out value="${relacion.id}"/>"/>
						</display:column>
					</c:otherwise>
				</c:choose>
				<display:column titleKey="archigest.archivo.transferencias.relaciones.usuarioRemitente">
					<c:out value="${relacion.gestorEnOrganoRemitente.nombreCompleto}"/>
				</display:column>
				<c:if test="${tipoRelacion != appConstants.transferencias.INGRESO_DIRECTO_KEY}">
					<display:column titleKey="archigest.archivo.transferencias.relaciones.usuarioArchivo">
						<c:out value="${relacion.gestorEnArchivo.nombreCompleto}"/>
					</display:column>

					<display:column titleKey="archigest.archivo.transferencias.tipoTrans" sortable="true" headerClass="sortable">
						<c:set var="keyTitulo">
							archigest.archivo.transferencias.tipotrans<c:out value="${relacion.tipotransferencia}"/>
						</c:set>
						<fmt:message key="${keyTitulo}" />
					</display:column>
				</c:if>
				<c:if test="${tipoRelacion == appConstants.transferencias.INGRESO_DIRECTO_KEY}">
					<display:column titleKey="archigest.archivo.archivoCustodia" sortable="true" headerClass="sortable">
						<c:out value="${relacion.nombreArchivoReceptor}"/>
					</display:column>					
				</c:if>					
				<display:column titleKey="archigest.archivo.transferencias.estado" sortable="true" headerClass="sortable">
					<c:set var="keyTituloEstado">
						archigest.archivo.transferencias.estadoRelacion.<c:out value="${relacion.estado}"/>
					</c:set>
					<fmt:message key="${keyTituloEstado}" />
				</display:column>
				<display:column titleKey="archigest.archivo.transferencias.orgRem" sortable="true" headerClass="sortable">
					<c:out value="${relacion.organoRemitente.nombreLargo}"/>
				</display:column>
			</display:table>

			<span class="separador5"></span>

			<input type="hidden" name="method" value="asignarGestor">
			<table class="w98">
			<tr>
				<td class="etiquetaAzul12Bold">
					<c:choose>
						<c:when test="${tipoRelacion == appConstants.transferencias.INGRESO_DIRECTO_KEY}">
							<bean:message key="archigest.archivo.fondos.ingreso.directo.nuevoGestor"/>:&nbsp;&nbsp;					
						</c:when>
						<c:otherwise>
							<bean:message key="archigest.archivo.transferencias.relaciones.nuevoGestor"/>:&nbsp;&nbsp;
						</c:otherwise>
					</c:choose>
					<select name="gestor">
						<option value="" />
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
</form>