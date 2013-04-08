<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>

<html>
<head>
	<script language="Javascript">
		function redirigir(){
			document.forms[0].submit();			
		}
	</script>	
</head>

<body onload="javascript:redirigir();">
	<p><bean:message key="cargando"/></p>
	<form action="../portal/" method="POST">
	</form>
</body>
</html>
