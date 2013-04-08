<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/ispac-util.tld" prefix="ispac" %>
<html>
<head>
	<script language="javascript">
	//<!--
		var message = '<c:out value="${requestScope.MESSAGE}"/>';
		if (message) {
			jAlert(message);
		}
	//-->
	</script>
</head>
<body>
<ispac:hideframe event="false" refresh="true"/>
</body>
</html>