<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/ispac-util.tld" prefix="ispac" %>

<script type="text/javascript" src='<ispac:rewrite href="../scripts/ispac-forms.js"/>'> </script>

<div id="navmenutitle">
	<bean:message key="error.missingApp.title"/>
</div>
<div id="navSubMenuTitle">
	<bean:message key="error.missingApp.detail"/>
</div>
<div id="navmenu">
	<ul  class="actionsMenuList">

       	<li>
			<a href='javascript:history.back();'>
				<bean:message key="forms.button.back"/>
			</a>
	    </li>
	</ul>
</div>

