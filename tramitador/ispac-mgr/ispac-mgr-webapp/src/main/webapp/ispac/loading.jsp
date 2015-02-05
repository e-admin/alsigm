<%@ taglib uri="/WEB-INF/ispac-util.tld" prefix="ispac" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<html>
<head>
<title><bean:message key="head.title"/></title>
<link rel="stylesheet" href='<ispac:rewrite href="css/styles.css"/>'/>
</head>
<body>
	<table cellpadding="0" cellspacing="0" border="0" width="100%" height="100%">
		<tr>
			<td class="loading" align="center" valign="middle">
				<bean:message key="loading.text"/>
			</td>
		</tr>
	</table>
	<ispac:errors />
	</body>
</html>