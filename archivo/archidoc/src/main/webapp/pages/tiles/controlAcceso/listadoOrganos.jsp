<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/archigest-adds.tld" prefix="pa" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>

<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo" %>
<%@ taglib uri="/WEB-INF/taglibs/securityTag.tld" prefix="security"%>

<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>

<tiles:insert template="/pages/tiles/PABoxLayout.jsp">
	<tiles:put name="boxTitle" direct="true">
		<bean:message key="archigest.archivo.cacceso.listaOrganos"/>
	</tiles:put>

	<tiles:put name="buttonBar" direct="true">
		<table><tr>

		<security:permissions action="${appConstants.controlAccesoActions.ALTA_ORGANO}">
			<td nowrap>
				<c:url var="createURL" value="/action/gestionOrganos">
					<c:param name="method" value="altaOrgano" />
				</c:url>
				<a class="etiquetaAzul12Bold" href="<c:out value="${createURL}" escapeXml="false"/>" >
				<html:img page="/pages/images/newDoc.gif" altKey="archigest.archivo.crear" titleKey="archigest.archivo.crear" styleClass="imgTextMiddle" />
				<bean:message key="archigest.archivo.crear"/></a>
			</td>
			<td width="10px"></td>
		</security:permissions>
		<td nowrap>
			<c:url var="busquedaOrganosURL" value="/action/gestionOrganos">
				<c:param name="method" value="verBuscador" />
			</c:url>
			<a class="etiquetaAzul12Bold" href="<c:out value="${busquedaOrganosURL}" escapeXml="false"/>" >
			<html:img page="/pages/images/buscar_go.gif" altKey="archigest.archivo.buscar" titleKey="archigest.archivo.buscar" styleClass="imgTextMiddle" />
			<bean:message key="archigest.archivo.buscar"/></a>
		</td>
		<c:if test="${appConstants.configConstants.mostrarAyuda}">
			<td width="10">&nbsp;</td>
			<td>
				<c:set var="requestURI" value="${pageContext.request.scheme}://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}" />
				<c:set var="carpetaIdioma" value="${sessionScope[appConstants.commonConstants.LOCALEKEY]}" />
				<a class="etiquetaAzul12Bold" href="javascript:popupHelp('<c:out value="${requestURI}" escapeXml="false"/>/help/<c:out value="${carpetaIdioma}"/>/controlAcceso/GestionOrganos.htm');">
				<html:img page="/pages/images/help.gif"
				        altKey="archigest.archivo.ayuda"
				        titleKey="archigest.archivo.ayuda"
				        styleClass="imgTextMiddle"/>
				&nbsp;<bean:message key="archigest.archivo.ayuda"/></a>
			</td>
		</c:if>
		<td width="10px"></td>
		<td nowrap>
			<tiles:insert definition="button.closeButton" flush="true">
				<tiles:put name="action" direct="true">goReturnPoint</tiles:put>
			</tiles:insert>
		</td>
		</tr></table>
	</tiles:put>

	<c:url var="listaOrganosPaginationURI" value="/action/gestionOrganos">
		<c:param name="method" value="${param.method}" />
	</c:url>
	<jsp:useBean id="listaOrganosPaginationURI" type="java.lang.String" />

	<tiles:put name="boxContent" direct="true">
		<div class="bloque">
		<c:set var="listaOrganos" value="${requestScope[appConstants.controlAcceso.LISTA_ORGANOS]}" />
		<display:table name="pageScope.listaOrganos"
				id="organo"
				style="width:98%;margin-left:auto;margin-right:auto"
				requestURI="<%=listaOrganosPaginationURI%>"
				pagesize="10">
			<display:column titleKey="archigest.archivo.codigo" sortProperty="codigo" sortable="true" headerClass="sortable" style="width:150">
				<c:out value="${organo.codigo}"> -- </c:out>
			</display:column>
			<display:column titleKey="archigest.archivo.organo" sortProperty="nombre" sortable="true" headerClass="sortable">
				<c:url var="verURL" value="/action/gestionOrganos">
					<c:param name="method" value="infoOrgano" />
					<c:param name="idOrgano" value="${organo.idOrg}" />
				</c:url>
				<a class="tdlink" href="<c:out value="${verURL}" escapeXml="false"/>" >
					<c:out value="${organo.nombreLargo}"> -- </c:out>
				</a>
			</display:column>
			<display:column titleKey="organizacion.org.estado.vigente" sortProperty="vigente" sortable="true" headerClass="sortable" style="width:30px;text-align:center">
			  <c:choose>
			    <c:when test="${organo.organoVigente}">
			    <html:img page="/pages/images/checkbox-yes.gif"
			        altKey="archigest.archivo.si"
 				        titleKey="archigest.archivo.si"
			        styleClass="imgTextMiddle"/>
			    </c:when>
			    <c:otherwise>
			      <html:img page="/pages/images/checkbox-no.gif"
			        altKey="archigest.archivo.no"
 				        titleKey="archigest.archivo.no"
			        styleClass="imgTextMiddle"/>
			    </c:otherwise>
			    </c:choose>
			</display:column>
		</display:table>
		</div>
	</tiles:put>
</tiles:insert>