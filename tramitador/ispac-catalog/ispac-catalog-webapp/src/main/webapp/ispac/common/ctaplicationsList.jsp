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
	<bean:message key="title.aplicationlist"/>
</div>
<div id="navSubMenuTitle">
</div>
<div id="navmenu">
	<ul class="actionsMenuList">
		<li>
			<a href="javascript:redirect('/showCTEntity.do?entityId=2&regId=-1')">
				<bean:message key="new.application"/>
			</a>
		</li>
	</ul>
</div>
<html:form action='/showCTAplicationsList.do'>
	
	<!-- BUSCADOR -->
	<tiles:insert page="ajax/ajaxSearchTile.jsp" ignore="true" flush="false" >	
		<tiles:put name="suggestEntityId" value="2"/>
	</tiles:insert>
	
	<!-- LISTADO DE APLICACIONES -->
	<tiles:insert page="tiles/displaytagList.jsp" ignore="true" flush="false">
		<tiles:put name="ItemListAttr" value="CTEntityList"/>
		<tiles:put name="ItemFormatterAttr" value="CTEntityListFormatter"/>
		<tiles:put name="ItemActionAttr" value="/showCTAplicationsList.do"/>
	</tiles:insert>

</html:form>