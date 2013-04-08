<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>

<c:set var="series" value="${requestScope[appConstants.valoracion.LISTA_SERIES_KEY]}"/>

<tiles:insert template="/pages/tiles/PABoxLayout.jsp">
	<tiles:put name="boxTitle" direct="true">
		<bean:message key="archigest.archivo.seleccion.seriesSeleccionables.caption"/>
	</tiles:put>
	<tiles:put name="buttonBar" direct="true">
		<table>
			<tr>
				<td width="10">&nbsp;</td>
				<td nowrap>
					<tiles:insert definition="button.closeButton" flush="true" />
				</td>
			</tr>
		</table>
	</tiles:put>
	<tiles:put name="boxContent" direct="true">
		<div class="bloque">
			<display:table name="pageScope.series"
				id="serie" 
				pagesize="15"
				requestURI="../../action/gestionEliminacion"			
				sort="list"
				export="false"
				style="width:99%;margin-left:auto;margin-right:auto">
	
				<display:setProperty name="basic.msg.empty_list">
					<bean:message key="archigest.archivo.seleccion.seriesSeleccionables.empty"/>
				</display:setProperty>
				
				<display:column titleKey="archigest.archivo.codigo" sortProperty="codigo" sortable="true" headerClass="sortable">
					<c:url var="SerieURL" value="/action/gestionSeries">
						<%-- Se sustituye verEnFondos por verDesdeFondos --%>
						<c:param name="method" value="verEnFondos" />
						<c:param name="id" value="${serie.id}" />
					</c:url>
					<a class="tdlink" href="<c:out value="${SerieURL}" escapeXml="false"/>" target="_self">
						<c:out value="${serie.codigo}" /></a>
				</display:column>
				<display:column titleKey="archigest.archivo.titulo" property="titulo" sortable="true" headerClass="sortable"/>
				<display:column titleKey="archigest.archivo.fondo" property="fondo" sortable="true" headerClass="sortable"/>
				<display:column titleKey="archigest.archivo.uDocs" property="numeroUdocs" sortable="true" headerClass="sortable"/>
			</display:table>
		</div>
	</tiles:put>
</tiles:insert>

