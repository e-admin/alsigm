<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<html:html locale="true">
<head>
	<title></title>
</head>
<script>
	function Do()
	{
		alert('pa tras!');
		history.back();
	}
</script>
<body>
	<a href="javascript: void(null)" onclick="Do()"> Pulsa </a>
</body>
</html:html>