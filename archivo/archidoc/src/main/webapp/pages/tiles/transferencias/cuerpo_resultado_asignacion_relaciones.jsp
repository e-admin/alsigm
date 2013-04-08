<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>

<c:set var="tipoRelacion" value="${sessionScope[appConstants.transferencias.TIPO_RELACION_KEY]}" />
<c:set var="relaciones" value="${sessionScope[appConstants.transferencias.LISTA_RELACIONES_KEY]}" />
<c:set var="gestor" value="${requestScope[appConstants.transferencias.GESTOR_KEY]}" />

<tiles:insert template="/pages/tiles/PABoxLayout.jsp">

	<tiles:put name="boxTitle" direct="true">
		<c:choose>
			<c:when test="${tipoRelacion == appConstants.transferencias.RELACION_EN_ARCHIVO}">
				<bean:message key="archigest.archivo.transferencias.relaciones.archivo.cederControl"/>
			</c:when>
			<c:otherwise>
				<c:choose>
					<c:when test="${tipoRelacion == appConstants.transferencias.INGRESO_DIRECTO_KEY}">
						<bean:message key="archigest.archivo.fondos.ingreso.directo.cederControl"/>
					</c:when>
					<c:otherwise>
						<bean:message key="archigest.archivo.transferencias.relaciones.orgrem.cederControl"/>
					</c:otherwise>
				</c:choose>			
			</c:otherwise>
		</c:choose>
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
					<c:choose>
						<c:when test="${tipoRelacion == appConstants.transferencias.INGRESO_DIRECTO_KEY}">
							<bean:message key="archigest.archivo.fondos.ingreso.directo.gestorAsignadoMsg"/>
						</c:when>
						<c:otherwise>
							<bean:message key="archigest.archivo.transferencias.relaciones.gestorAsignadoMsg"/>
						</c:otherwise>
					</c:choose>			
				</span>
			</tiles:put>
			<tiles:put name="blockContent" direct="true">
			<span class="separador5"></span>
			<display:table name="pageScope.relaciones"
				id="relacion" 
				style="width:99%;margin-left:auto;margin-right:auto">
				
				<c:choose>
					<c:when test="${tipoRelacion == appConstants.transferencias.INGRESO_DIRECTO_KEY}">
						<display:column titleKey="archigest.archivo.fondos.ingreso.directo" sortable="true" headerClass="sortable" >
							<c:out value="${relacion.codigoTransferencia}"/>
						</display:column>					
					</c:when>
					<c:otherwise>
						<display:column titleKey="archigest.archivo.relacion" sortable="true" headerClass="sortable" >
							<c:out value="${relacion.codigoTransferencia}"/>
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
				</c:if>
				<c:if test="${tipoRelacion == appConstants.transferencias.INGRESO_DIRECTO_KEY}">
					<display:column titleKey="archigest.archivo.archivoCustodia" sortable="true" headerClass="sortable">
						<c:out value="${relacion.nombreArchivoReceptor}"/>
					</display:column>					
				</c:if>	
				<c:if test="${tipoRelacion != appConstants.transferencias.INGRESO_DIRECTO_KEY}">
					<display:column titleKey="archigest.archivo.transferencias.tipoTrans" sortable="true" headerClass="sortable">
						<c:set var="keyTitulo">
							archigest.archivo.transferencias.tipotrans<c:out value="${relacion.tipotransferencia}"/>
						</c:set>
						<fmt:message key="${keyTitulo}" />
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
			<div class="separador8">&nbsp;</div>
			</tiles:put>
		</tiles:insert>
	</tiles:put>
</tiles:insert>