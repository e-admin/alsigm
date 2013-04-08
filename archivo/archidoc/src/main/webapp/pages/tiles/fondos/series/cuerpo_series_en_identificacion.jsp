<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>

<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>

<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>

<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo" %>
<%@ taglib uri="/WEB-INF/taglibs/securityTag.tld" prefix="security"%>



<c:set var="FORMATO_FECHA" value="${appConstants.common.FORMATO_FECHA}"/>


<tiles:insert template="/pages/tiles/PABoxLayout.jsp">
	<tiles:put name="boxTitle" direct="true"><bean:message key="archigest.archivo.series.enIdentificacion"/></tiles:put>
	<tiles:put name="buttonBar" direct="true">
		<table cellpadding="0" cellspacing="0">
			<tr>
				<td nowrap>
					<tiles:insert definition="button.closeButton" />
				</td>
			</tr>
		</table>
	</tiles:put>
	<tiles:put name="boxContent" direct="true">

		<c:set var="seriesEnIdentificacion" value="${requestScope[appConstants.fondos.SERIES_EN_IDENTIFICACION]}"/>
		<c:url var="paginationURL" value="/action/gestionSeries" />
		<jsp:useBean id="paginationURL" type="java.lang.String" />
		<div class="bloque">
			<display:table name="pageScope.seriesEnIdentificacion"
				id="serie"
				pagesize="15"
				requestURI="<%=paginationURL%>"
				export="false"
				style="width:99%;margin-left:auto;margin-right:auto">

				<display:setProperty name="basic.msg.empty_list">
					<div class="separador8"></div>
					<bean:message key="archigest.archivo.series.noSeriesEnIdentificacion"/>
					<div class="separador8"></div>
				</display:setProperty>
				
				<display:column titleKey="archigest.archivo.codigo" sortProperty="codReferencia" sortable="true" headerClass="sortable">
					<c:url var="infoSeriesURL" value="/action/manageVistaCuadroClasificacion">
						<c:param name="actionToPerform" value="goHome" />
						<c:param name="itemID" value="${serie.id}" />
					</c:url>
					<a class="tdlink" href="<c:out value="${infoSeriesURL}" escapeXml="false"/>"><c:out value="${serie.codReferencia}" /></a>
				</display:column>
				<display:column titleKey="archigest.archivo.titulo" property="titulo" sortable="true" headerClass="sortable" />
				<display:column titleKey="archigest.archivo.estado" style="width:30%">
					<fmt:message key="archigest.archivo.cf.estadoSerie.${serie.estadoserie}" />
				</display:column>
			</display:table>
		</div>
	</tiles:put>
</tiles:insert>