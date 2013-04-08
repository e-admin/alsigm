<%@ page contentType="text/html;charset=ISO-8859-1" language="java" %>
<%@ taglib uri="/WEB-INF/ieci.tld" prefix="ieci"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%
response.setHeader("Cache-Control","no-cache"); //HTTP 1.1
response.setHeader("Pragma","no-cache"); //HTTP 1.0
response.setDateHeader ("Expires", 0); //prevents caching at the proxy server
%>

<html>
<head>
<ieci:baseInvesDoc/>
<link rel="stylesheet" type="text/css" href="include/css/adminApp.css"/>
<script src="include/js/validations.js" type="text/javascript"></script>

<script language="javascript">
	var guid = '<c:out value="${param.guid}"/>';
	var tipo = <c:out value="${param.tipo}"/>;
	var appBase = '<c:out value="${pageContext.request.contextPath}"/>';

	function darDeAlta()
	{
		
		if (tipo == 1){ // dar de alta a usuario
			document.location.href = appBase + '/user/ldap/userNew.do?guid='+guid;
		}
		else
			document.location.href = appBase + '/user/ldap/groupNew.do?guid='+guid;
			
	}

</script>

</head>
<body>

	<table>
		<tr> <td colspan="2">&nbsp;</td> </tr>
		<%-- Se añade el certificado digital, cuando se modifica el usuario
			<c:if  test="${param.tipo == 1}">
				<tr> <td>Id.Certificado: </td> <td><input type="text" name="idCert" id="idCert"/></td> </tr>
				<tr> <td colspan="2">&nbsp;</td> </tr>
			</c:if>
		--%>
		<tr> <td colspan="2" align="center"><a href="javascript:darDeAlta();">Dar de Alta</a>	</td> </tr>
	</table>

</body>
</html>