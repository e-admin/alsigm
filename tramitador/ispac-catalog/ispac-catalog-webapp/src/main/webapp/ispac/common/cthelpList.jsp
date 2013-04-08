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
	<bean:message key="title.helplist"/>
</div>
<div id="navSubMenuTitle">
</div>
<div id="navmenu">
	<ispac:hasFunction functions="FUNC_COMP_HELPS_EDIT">
	<ul class="actionsMenuList">
		<li>
			<a href="javascript:redirect('/showCTEntityUp.do?entityId=63&regId=-1')">
				<bean:message key="new.help"/>
			</a>
		</li>
	</ul>
	</ispac:hasFunction>
</div>
<html:form action='/showCTHelpList.do'>
	
	<!-- BUSCADOR -->
	<tiles:insert page="ajax/ajaxSearchTile.jsp" ignore="true" flush="false" >	
		<tiles:put name="suggestEntityId" value="62"/>
	</tiles:insert>
	
	<!-- LISTA DE Ayudas-->
	<tiles:insert page="tiles/displaytagList.jsp" ignore="true" flush="false">
		<tiles:put name="ItemListAttr" value="CTHelpsList"/>
		<tiles:put name="ItemFormatterAttr" value="CTHelpsListFormatter"/>
		<tiles:put name="ItemActionAttr" value="/showCTHelpList.do"/>
	</tiles:insert>

</html:form>