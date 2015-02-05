<%@ page contentType="text/html;charset=ISO-8859-1" language="java" %>
<%@ taglib uri="/WEB-INF/ieci.tld" prefix="ieci"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%
response.setHeader("Cache-Control","no-cache"); //HTTP 1.1
response.setHeader("Pragma","no-cache"); //HTTP 1.0
response.setDateHeader ("Expires", 0); //prevents caching at the proxy server
%>
 
<!DOCTYPE HTML PUBLIC "-//w3c//dtd html 4.0 transitional//en">
<html>
<head>
<ieci:baseInvesDoc/>
<link rel="stylesheet" type="text/css" href="include/css/adminApp.css"/>
<link rel="Stylesheet" rev="Stylesheet" href="include/css/estilos.css" />
<style>
	.primeraColumna {
		font-size:10px;
	}
	.segundaColumna {
		color: #636DAD;
		text-decoration: none;
		font-size:10px;
	}
</style>
<%-- 
<link rel="stylesheet" type="text/css" href="include/css/common.css"/>
<link rel="stylesheet" type="text/css" href="include/css/styles.css"/>
--%>
<script language="javascript">

	var guid ='<c:out value="${requestScope.propiedades.valorGuid}"/>';
	var appBase = '<c:out value="${pageContext.request.contextPath}"/>';
	var valAtt= '<c:out value="${requestScope.propiedades.valorAtributo}"/>';

	function darDeAlta(tipo)
	{
		
		if (tipo == 1){ // dar de alta a usuario
			document.location.href = appBase + '/user/ldap/userNew.do?guid='+guid;
		}
		else
			document.location.href = appBase + '/user/ldap/groupNew.do?guid='+guid;
			
	}
	
	function darDeBaja()
	{
		document.location.href = appBase + '/user/ldap/groupDelete.do?guid='+guid;
	}
	
	function userEdit()
	{
		parent.propiedades.location.href = appBase + '/user/ldap/userEdit.do?guid='+guid;	
	}
		
	/*
	function nuevoUsuario()
	{
		var nombAtt = '<c:out value="${requestScope.propiedades.nombreAtributo}"/>';
		var valAtt= '<c:out value="${requestScope.propiedades.valorAtributo}"/>';
		parent.permisos.location.href = appBase + '/idoc/user.do?do=newUserLdap&nombreAtributo='+nombAtt+'&valorAtributo='+valAtt;
	}
	function userEdit()
	{
		parent.edicion.location.href = appBase + '/user/ldap/userEdit.do?guid='+guid;	
	}
	
	function darDeAlta(tipo)
	{
		parent.edicion.location.href = appBase + '/adminUser/ldap/darDeAlta.jsp?guid='+guid+'&tipo='+tipo+'&valAtt='+valAtt;
	}
	function groupEdit()
	{
		parent.edicion.location.href = appBase + '/user/ldap/groupEdit.do?guid='+guid;	
		//parent.permisos.location.href = '/idoc/user.do?do=editGroup&guid='+guid;	
		//alert ('grupo dado de alta');
	}
	function goToBlank()
	{
		parent.edicion.location.href = appBase + '/blank.do';	
	}
	
	function loadUser() {
		parent.user = '<c:out value="${requestScope.cn}"/>';
		parent.pwd = '<c:out value="${requestScope.pwd}"/>';
	}
	
	*/

</script>
</head>
<body topmargin="0" leftmargin="0">
<table border class="tableBase" width="300">
	<tr>
		<td class="primeraColumna"><b>dn</b></td>
		<td class="segundaColumna"><c:out value="${requestScope.propiedades.dn}" /></td>
	</tr>
	<c:forEach items="${requestScope.propiedades.attsMap}" var="att">
	<tr>
		<td class="primeraColumna"><b><c:out value="${att.key}"/></b></td>
		<td class="segundaColumna"><c:out value="${att.value}"/></td>
	</tr>
	</c:forEach>
</table>


<c:choose>
	<c:when test="${requestScope.propiedades.esUsuario}">
		<c:choose>
			<c:when test="${requestScope.propiedades.dadoDeAlta}">
				<table>
					<tr>
						<td align="center">
							<input type="button" onclick="javascript:userEdit();" value="<bean:message key='message.comun.boton.editar'/>" class="ok" /> 	
						</td>
					</tr>
				</table>
			</c:when>
			<c:otherwise >
				<table>
					<tr>
						<td align="center">
							<input type="button" onclick="javascript:darDeAlta(1);" value="<bean:message key='message.comun.boton.darDeAlta'/>" class="ok" /> 	
						</td>
					</tr>
				</table>
			</c:otherwise>
		</c:choose> 
	</c:when>
	<%--
	<c:when test="${requestScope.propiedades.esGrupo}">
		<c:choose>
			<c:when test="${requestScope.propiedades.dadoDeAlta}">
				<table>
					<tr>
						<td align="center">
							<input type="button" onclick="javascript:darDeBaja();" value="<bean:message key='message.comun.boton.darDeBaja'/>" class="ok" /> 	
						</td>
					</tr>
				</table>		
			</c:when>
			<c:otherwise >
				<table>
					<tr>
						<td align="center">
							<input type="button" onclick="javascript:darDeAlta(2);" value="<bean:message key='message.comun.boton.darDeAlta'/>" class="ok" /> 	
						</td>
					</tr>
				</table>
			</c:otherwise>
		</c:choose> 
	</c:when>
	--%>
	<c:otherwise >
	</c:otherwise>
</c:choose>


</body>
</html>
