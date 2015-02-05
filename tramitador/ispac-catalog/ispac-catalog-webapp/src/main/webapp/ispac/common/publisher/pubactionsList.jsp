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
	<bean:message key="title.pubActions"/>
</div>
<div id="navSubMenuTitle">
</div>
<div id="navmenu">
	<ispac:hasFunction functions="FUNC_PUB_ACTIONS_EDIT">
	<ul class="actionsMenuList">
		<li>
			<a href="javascript:redirect('/showCTEntity.do?entityId=40&regId=-1')">
				<bean:message key="new.action"/>
			</a>
		</li>
	</ul>
	</ispac:hasFunction>
</div>
<html:form action='/showPubActionsList.do'>
	
	<!-- BUSCADOR -->
	<tiles:insert page="../ajax/ajaxSearchTile.jsp" ignore="true" flush="false" >	
		<tiles:put name="suggestEntityId" value="40"/>
	</tiles:insert>
	
	<!-- LISTA DE ACCIONES -->
	<tiles:insert page="../tiles/displaytagList.jsp" ignore="true" flush="false">
		<tiles:put name="ItemListAttr" value="PubActionsList"/>
		<tiles:put name="ItemFormatterAttr" value="PubActionsListFormatter"/>
		<tiles:put name="ItemActionAttr" value="/showPubActionsList.do"/>
	</tiles:insert>

</html:form>

