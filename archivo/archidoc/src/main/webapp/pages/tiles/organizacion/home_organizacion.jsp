<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>

<c:set var="listaInstituciones" value="${requestScope[appConstants.organizacion.LISTA_INSTITUCIONES_KEY]}" />

<tiles:insert template="/pages/tiles/PABoxLayout.jsp">

	<tiles:put name="boxTitle" direct="true">
		<bean:message key="organizacion.org.listado"/>
	</tiles:put>
	
	<tiles:put name="buttonBar" direct="true">
		<table>
		<tr>
			<td nowrap>
				<c:url var="createURL" value="/action/gestionOrganizacionAction">
					<c:param name="method" value="altaInstitucion" />
				</c:url>
				<a class="etiquetaAzul12Bold" href="<c:out value="${createURL}" escapeXml="false"/>" target="_self" >
				<html:img page="/pages/images/newDoc.gif" altKey="organizacion.crear.institucion" titleKey="organizacion.crear.institucion" styleClass="imgTextMiddle" />
				<bean:message key="organizacion.crear.institucion"/></a>
			</td>
			<c:if test="${!empty listaInstituciones}">
			<td width="10px"></td>
				<td nowrap>
					<c:url var="busquedaOrganosURL" value="/action/gestionOrganizacionAction">
						<c:param name="method" value="verBuscador" />
					</c:url>
					<a class="etiquetaAzul12Bold" href="<c:out value="${busquedaOrganosURL}" escapeXml="false"/>" target="_self">
					<html:img page="/pages/images/buscar_go.gif" altKey="organizacion.buscar" titleKey="organizacion.buscar" styleClass="imgTextMiddle" />
					<bean:message key="organizacion.buscar"/></a>
				</td>
			</c:if>
			<%--<td width="10px"></td>
			<td nowrap>
				<tiles:insert definition="button.closeButton" flush="false">
					<tiles:put name="action" direct="true">goReturnPoint</tiles:put>
				</tiles:insert>
			</td>--%>
		</tr>
		</table>
	</tiles:put>

	<tiles:put name="boxContent" direct="true">
		<tiles:insert template="/pages/tiles/PABlockLayout.jsp" flush="false">
		
			<tiles:put name="blockTitle" direct="true">
				<bean:message key="organizacion.name"/>
			</tiles:put>
			
			<tiles:put name="buttonBar" direct="true">

			</tiles:put>
			
			<c:url var="listaInstitucionesPaginationURI" value="/action/manageVistaOrganizacion">
				<c:param name="actionToPerform" value="${param.method}" />
				<c:param name="target" value="_self" />
			</c:url>	
			<jsp:useBean id="listaInstitucionesPaginationURI" type="java.lang.String" />
			
			<tiles:put name="blockContent" direct="true">
				<div class="separador8"></div>

				<c:set var="treeView" value="${sessionScope[appConstants.organizacion.ORGANIZACION_VIEW_NAME]}" />
				<c:set var="selectedNode" value="${treeView.selectedNode}" />
				<display:table name="pageScope.listaInstituciones" id="organizacion" 
							style="width:98%;margin-left:auto;margin-right:auto"
							requestURI="<%=listaInstitucionesPaginationURI%>"
							sort="list"
							defaultsort="1"
							pagesize="20">
						<display:column titleKey="organizacion.org.codigo" sortProperty="codigo" sortable="true" headerClass="sortable" style="width:150">
							<c:out value="${organizacion.codigo}"/>
						</display:column>
						<display:column titleKey="organizacion.org.nombre" sortProperty="nombre" sortable="true" headerClass="sortable">
							<c:url var="verOrganizacionURL" value="/action/manageVistaOrganizacion">
								<c:param name="actionToPerform" value="verNodo" />
								<c:param name="node" value="${selectedNode.nodePath}/item${organizacion.id}" />
								<%-- <c:param name="listado" value="true" /> --%>
								<c:param name="refreshView" value="true" />
							</c:url>
							<a class="tdlink" href="<c:out value="${verOrganizacionURL}" escapeXml="false"/>" target="_self" >
								<c:out value="${organizacion.nombre}"/>
							</a>
						</display:column>
						<display:column titleKey="organizacion.org.estado" sortProperty="estado" sortable="true" headerClass="sortable">
							<c:choose>
								<c:when test="${organizacion.estado == 1}">
									<c:set var="estado" value="borrador" />
								</c:when>
								<c:when test="${organizacion.estado == 2}">
									<c:set var="estado" value="vigente" />
								</c:when>
								<c:otherwise>
									<c:set var="estado" value="historico" />
								</c:otherwise>
							</c:choose>
							<fmt:message key="organizacion.org.estado.${estado}"/>
						</display:column>
				</display:table>

				<div class="separador8"></div>
			</tiles:put>
		</tiles:insert>
	</tiles:put>
</tiles:insert>