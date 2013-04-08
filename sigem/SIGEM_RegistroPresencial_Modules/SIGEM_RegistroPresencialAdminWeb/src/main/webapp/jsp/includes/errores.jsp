<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>

<span class="error">
	<html:errors/>
	
	<span class="message">
		<html:messages message="true" id="msg">
			<bean:write name="msg"/>
		</html:messages>
	</span>
</span>