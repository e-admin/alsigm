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
	<bean:message key="title.frmsearchlist"/>
</div>

<div id="navSubMenuTitle">
</div>

<div id="navmenu">
	<ispac:hasFunction functions="FUNC_COMP_SEARCH_FORMS_EDIT">
	<ul class="actionsMenuList">
		<li>
			<a href="javascript:redirect('/showCTEntityUp.do?entityId=4&regId=-1')">
				<bean:message key="new.form"/>
			</a>
		</li>
	</ul>
	</ispac:hasFunction>
</div>

<html:form action='/showCTFrmSearchList.do'>
	
	<!-- BUSCADOR -->
	<tiles:insert page="ajax/ajaxSearchTile.jsp" ignore="true" flush="false" >	
		<tiles:put name="suggestEntityId" value="4"/>
		<tiles:put name="suggestSearchField" value="DESCRIPCION"/>
		<tiles:put name="containsTextKey" value="catalog.search.description.containsText"/>
	</tiles:insert>
	
	<tiles:insert page="tiles/displaytagList.jsp" ignore="true" flush="false">
		<tiles:put name="ItemListAttr" value="CTFrmSearchList"/>
		<tiles:put name="ItemFormatterAttr" value="CTFrmSearchListFormatter"/>
		<tiles:put name="ItemActionAttr" value="/showCTFrmSearchList.do"/>
	</tiles:insert>

</html:form>

