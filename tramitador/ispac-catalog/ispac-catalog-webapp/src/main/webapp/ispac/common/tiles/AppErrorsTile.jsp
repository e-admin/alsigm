<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/ispac-util.tld" prefix="ispac" %>

<logic:messagesPresent name="appErrors">
	<div id="appErrors">
		<bean:message key="forms.errors.appErrors"/>
		<html:messages name="appErrors" id="error">
			<li><bean:write name="error"/></li>
		</html:messages>
	</div>
</logic:messagesPresent>
