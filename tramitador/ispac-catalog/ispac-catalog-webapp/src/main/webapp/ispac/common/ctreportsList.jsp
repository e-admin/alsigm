<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/ispac-util.tld" prefix="ispac" %>

<script>
	function redirect(url)
	{
		submit('<%=request.getContextPath()%>' + url);
	}
</script>

<div id="navmenutitle">
	<bean:message key="title.reportslist"/>
</div>
<div id="navSubMenuTitle">
</div>
<div id="navmenu">
	<ispac:hasFunction functions="FUNC_COMP_REPORTS_EDIT">
	<ul class="actionsMenuList">
		<li>
			<a href="javascript:redirect('/showCTEntityUp.do?entityId=61&regId=-1')">
				<bean:message key="new.report"/>
			</a>
		</li>
	</ul>
	</ispac:hasFunction>
</div>
<html:form action='/showCTReportsList.do'>
	
	<!-- BUSCADOR -->
	<tiles:insert page="ajax/ajaxSearchTile.jsp" ignore="true" flush="false" >	
		<tiles:put name="suggestEntityId" value="61"/>
	</tiles:insert>
	
	<!-- LISTA DE REGLAS -->
	<tiles:insert page="tiles/displaytagList.jsp" ignore="true" flush="false">
		<tiles:put name="ItemListAttr" value="CTReportsList"/>
		<tiles:put name="ItemFormatterAttr" value="CTReportsListFormatter"/>
		<tiles:put name="ItemActionAttr" value="/showCTReportsList.do"/>
	</tiles:insert>

</html:form>