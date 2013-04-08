<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>

<bean:define id="target" name="target"/>
<bean:define id="url" name="url"/>

<html>
<head>
	<title><bean:message key="head.title"/></title>
		
	<script language="javascript">
		
		var win = window.<bean:write name="target"/>;
		var url = "<bean:write name="url" filter="false"/>";
		
		if (typeof win.ispac_needToConfirm != 'undefined') {
			win.ispac_needToConfirm = false;
		}
		
		if (win) {	
			win.location = url;
		}
		else {
			window.open(url, "<bean:write name="target"/>");
		}
		
	</script>
	
</head>
<body/>
</html>