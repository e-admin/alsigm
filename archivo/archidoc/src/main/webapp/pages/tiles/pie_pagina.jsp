<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>

<%@ page import="common.OrganizationMessages" %>

<div id="pie_archigest">
	<div class="separador8">&nbsp; <%-- 8 pixels de separacion --%>
	</div>
	<div class="separador8">&nbsp; <%-- 8 pixels de separacion --%>
	</div>

	<div id="copyright" class="pie">
		<bean:message key="pie_pagina.linea1" arg0="<%=OrganizationMessages.getCopyright()%>" />
	</div>
	<hr />
	<div id="optimizado" class="pie">
		<bean:message key="pie_pagina.linea2" />
	</div>

	<div id="legal" class="pie">
        <html:link forward="avisolegal" target="_blank">
			<%--Aviso Legal y Responsabilidad--%>
			<bean:message key="pie_pagina.linea3"/>
		</html:link>
	</div>
	<div class="separador8">&nbsp; <%-- 8 pixels de separacion --%>
	</div>
	<div class="separador8">&nbsp; <%-- 8 pixels de separacion --%>
	</div>
</div>

