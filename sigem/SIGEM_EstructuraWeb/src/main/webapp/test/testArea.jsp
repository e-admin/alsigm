<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<html:html locale="true">
<head>
	<title></title>
	<script type="text/javascript">
		function check()
		{	
			nameDescription = "fields.fldsList[0].description";
			description = document.getElementsByName(nameDescription)[0];
			alert (description + ": " +description.value);
		}
	</script>
</head>
<body>

description = document.getElementsByName(nameDescription)[0];
<form name="mi form" method="post" action="/AdminApp/test/testArea.jsp">
	Mi textArea: <textarea name="fields.fldsList[0].description"></textarea>
	<br>
	<input type="button" onclick="check();" value="botoncito"/>
</form>
	
</body>
</html:html>