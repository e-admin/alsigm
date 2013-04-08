<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/ispac-util.tld" prefix="ispac" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>


<html:errors/>
<script>
	function redirect(url)
	{
		submit('<%=request.getContextPath()%>' + url);
	}
	function deleteCalendar(url)
	{
		if (checkboxElement(document.defaultForm.multibox) == "") {
			jAlert('<bean:message key="error.entity.noSelected"/>', '<bean:message key="common.alert"/>' , '<bean:message key="common.message.ok"/>' , '<bean:message key="common.message.cancel"/>');
		} else {
			var frm = document.forms[0];
			var url = '<%=request.getContextPath()%>' + '/showCTCalendar.do?method=deleteCalendar';
		jConfirm('<bean:message key="calendar.confirm.eliminarCalendario"/>', '<bean:message key="common.confirm"/>', '<bean:message key="common.message.ok"/>' , '<bean:message key="common.message.cancel"/>', function(r) {
		if(r){
				frm.action = url;
				frm.submit();
		}
							
	});	
		
		}
	}
</script>

<div id="navmenutitle">
	<bean:message key="title.calendarslist"/>
</div>
<div id="navSubMenuTitle">
</div>
<div id="navmenu">
	<ispac:hasFunction functions="FUNC_COMP_CALENDARS_EDIT">
	<ul class="actionsMenuList">
		<li>
			<a href="javascript:redirect('/showCTCalendar.do?method=show&entityId=58&regId=-1')">
				<bean:message key="new.calendar"/>
			</a>
		</li>
		<li>
			<a href="javascript:deleteCalendar()">
				<bean:message key="delete.calendar"/>
			</a>
		</li>
	</ul>
	</ispac:hasFunction>
</div>

<html:form action='/showCTCalendarsList.do'>

	<!-- BUSCADOR -->
	<tiles:insert page="ajax/ajaxSearchTile.jsp" ignore="true" flush="false" >	
		<tiles:put name="suggestEntityId" value="58"/>
	</tiles:insert>
	
	<tiles:insert page="tiles/displaytagList.jsp" ignore="true" flush="false">
		<tiles:put name="ItemListAttr" value="CTCalendarsList"/>
		<tiles:put name="ItemFormatterAttr" value="CTCalendarsListFormatter"/>
		<tiles:put name="ItemActionAttr" value="/showCTCalendarsList.do"/>
	</tiles:insert>

</html:form>
