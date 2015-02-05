<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/ispac-util.tld" prefix="ispac" %>

<div id="navmenutitle">
	<bean:message key="usrmgr.supervision.mainTitle"/>
</div>
<div id="navmenu">
	<ul class="actionsMenuList">
		<li><a href="showInfoEntry.do?view=organization" id="current">
			<bean:message key="catalog.supervision.organization"/></a></li>
		<li><a href="showInfoEntry.do?view=groups">
			<bean:message key="catalog.supervision.groups"/></a></li>
		<li><a href="javascript:showFrame('workframe','<ispac:rewrite action='entrySearch.do?view=showSearch&actionSetResponsibles=/showInfoEntry.do&mode=Simple'/>')">
			<bean:message key="search.button"/></a></li>
	</ul>
</div>
