<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<logic:present name="ErrorMessage">
<div class='default'>
	<div id="appErrors">
		<bean:write name="ErrorMessage"/>
	</div>
</div>
</logic:present>

