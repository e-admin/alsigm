<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<html>
	<head>

	</head>
	<body onload="document.forms[0].submit();">
		<p>
			<bean:message key="cargando"/>
		</p>
		<form action="inicio.do">
			
		</form>
	</body>
</html>