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
	<bean:message key="title.taskslist"/>
</div>

<div id="navSubMenuTitle">
</div>

<div id="navmenu">
	<ispac:hasFunction functions="FUNC_INV_TASKS_EDIT">
	<ul class="actionsMenuList">
		<li>
			<a href="javascript:redirect('/showCTEntity.do?entityId=6&regId=-1')">
				<bean:message key="new.task"/>
			</a>
		</li>
	</ul>
	</ispac:hasFunction>
</div>

<html:form action='/showCTTasksList.do'>
	
	<!-- BUSCADOR -->
	<tiles:insert page="ajax/ajaxSearchTile.jsp" ignore="true" flush="false" >	
		<tiles:put name="suggestEntityId" value="6"/>
	</tiles:insert>
	
	<tiles:insert page="tiles/displaytagList.jsp" ignore="true" flush="false">
		<tiles:put name="ItemListAttr" value="CTTaskList"/>
		<tiles:put name="ItemFormatterAttr" value="CTTaskListFormatter"/>
		<tiles:put name="ItemActionAttr" value="/showCTTasksList.do"/>
	</tiles:insert>

</html:form>

