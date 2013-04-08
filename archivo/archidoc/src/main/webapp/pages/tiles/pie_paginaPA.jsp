<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>

<%@ page import="common.OrganizationMessages" %>

<div id="copyright" class="pie">
	<bean:message key="pie_pagina.linea1" arg0="<%=OrganizationMessages.getCopyright()%>" />
</div>
<hr />
<div id="optimizado" class="pie">
	<%--Optimizado para IE5+ o NN 4.x - Resoluci&oacute;n M&iacute;nima: 800x600--%>
	<bean:message key="pie_pagina.linea2" />	
</div>

<div id="legal" class="pie">
	<html:link forward="avisolegal" target="_blank">
		<%--Aviso Legal y Responsabilidad--%>
		<bean:message key="pie_pagina.linea3"/>
	</html:link>
</div>