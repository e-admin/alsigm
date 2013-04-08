<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ieci.tld" prefix="ieci"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>

<html:html locale="true">
<head>
	<title><bean:message key="message.common.title"/></title>
	<ieci:baseInvesDoc/>
	<link rel="stylesheet" type="text/css" href="include/css/adminApp.css"/>
	
	<script language="javascript">
	function addCertificate()
	{
		var appBase = '<c:out value="${pageContext.request.contextPath}"/>';
		if (window.opener) {
			window.opener.parent.document.location.href = appBase + '/acs/loginCert.jsp?addCertificate=true';
		}
		else
		{
			var dargs = window.dialogArguments;
			dargs.location.href = appBase + '/acs/loginCert.jsp?addCertificate=true';
		}
		window.close();
	}
	</script>
	
</head>
<body>
	<table style="margin-left: 30; margin-top: 40; margin-right: 30"  >
		<tr>
			<td colspan="2">Aviso: va a asociar su usuario w@rdA actual a un certificado digital. Una vez realizada la asociación podrá conectarse a w@rdA utilizando este certificado.</td>
		</tr>
		<tr>

		<td> <input type="button" class="genericButton"  value="Aceptar" onclick="addCertificate();" ></td>
		<td> <input type="button" class="genericButton" value="Cancelar" onclick="window.close();"></td>
		</tr>
	</table>
</body>
</html:html>