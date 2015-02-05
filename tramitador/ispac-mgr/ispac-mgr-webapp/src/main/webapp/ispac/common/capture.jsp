<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ page import="ieci.tdw.ispac.ispacweb.api.ManagerState" %>

<table border="0" width="100%">
	<tr>
		<td align="center">
			<<bean:message key="expedient.noResponsability.needCapture"/><br/>
			<html:link action="captureExpedient.do" paramId='<%=ManagerState.PARAM_STAGEID%>' paramName='<%=ManagerState.PARAM_STAGEID%>'><bean:message key="expedient.capture"/></html:link>
		</td>
	</tr>
</table>

