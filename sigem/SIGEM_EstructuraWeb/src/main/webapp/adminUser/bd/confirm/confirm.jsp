<%@ page contentType="text/html;charset=ISO-8859-1" language="java" %>
<%@ taglib uri="/WEB-INF/ieci.tld" prefix="ieci"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%
response.setHeader("Cache-Control","no-cache"); //HTTP 1.1
response.setHeader("Pragma","no-cache"); //HTTP 1.0
response.setDateHeader ("Expires", 0); //prevents caching at the proxy server
%>

<html>
<head>
<ieci:baseInvesDoc/>
<link rel="stylesheet" type="text/css" href="include/css/adminApp.css"/>
<link rel="stylesheet" type="text/css" href="include/css/estilos.css"/>
<script language="javascript" >
function actualiza()
{
	var appBase = '<c:out value="${pageContext.request.contextPath}"/>'; 
	<c:choose>
	<c:when test="${requestScope.tipo == 'Usuario'}">
		var tipoDept = '<c:out value="${requestScope.deptToken}"/>';
		var deptId = '<c:out value="${requestScope.id}"/>';
		parent.propiedades.location.href = appBase + '/user/bd/usersList.do?id='+deptId+'&tipo='+tipoDept;	
	</c:when>
	<c:when test="${requestScope.tipo == 'Grupo'}">
		parent.grupos.location.href = appBase + '/user/bd/groupTree.do';
	</c:when>
	<c:otherwise>
		parent.deptos.location.href = appBase + '/user/bd/deptTree.do';
	</c:otherwise>
	</c:choose>	
}




</script>


</head>
<body onload="actualiza();">
	<div id="contenedora">
		<table height="60%">
		<tr>
			<td>
				<div class="encabezado1">
					
						<c:choose> 
						  <c:when test="${tipo eq 'Departamento'}" > 
						  	<bean:message key="message.departamento.nuevo"/>
						  </c:when> 
						  <c:when test="${tipo eq 'Grupo'}" > 
							<bean:message key="message.grupo.nuevo"/>
						  </c:when> 
						  <c:otherwise>
						  	<bean:message key="message.usuario.nuevo"/>
						  </c:otherwise> 
						</c:choose> 					
					
				</div>
			</td>
		</tr>
		</table>
	</div>
</body>

</html>