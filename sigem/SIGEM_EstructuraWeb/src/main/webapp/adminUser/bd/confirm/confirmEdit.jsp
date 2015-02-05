<%@ page contentType="text/html;charset=ISO-8859-1" language="java" %>
<%@ taglib uri="/WEB-INF/ieci.tld" prefix="ieci"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<ieci:baseInvesDoc/>
	<link rel="stylesheet" type="text/css" href="include/css/adminApp.css"/>
	<link rel="stylesheet" type="text/css" href="include/css/estilos.css"/>
	
	<script language="javascript" >
		function actualiza(){
			var appBase = '<c:out value="${pageContext.request.contextPath}"/>'; 
			<c:choose>
				<c:when test="${requestScope.tipo == 'Usuario'}">
					var deptId = '<c:out value="${requestScope.id}"/>';
					parent.propiedades.location.href = appBase + '/user/bd/usersList.do?id='+deptId+'&tipo=32';			
				</c:when>
				<c:when test="${requestScope.tipo == 'Grupo'}">
					parent.grupos.location.href = appBase + '/user/bd/groupTree.do';
					var groupId = '<c:out value="${requestScope.id}"/>';
					parent.propiedades.location.href = appBase + '/user/bd/usersList.do?id='+groupId+'&tipo=2';
				</c:when>
				<c:otherwise>
					parent.deptos.location.href = appBase + '/user/bd/deptTree.do';
					var deptId = '<c:out value="${requestScope.id}"/>';
					parent.propiedades.location.href = appBase + '/user/bd/usersList.do?id='+deptId+'&tipo=32';					
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
						  	<bean:message key="message.departamento.edit"/>
						  </c:when> 
						  <c:when test="${tipo eq 'Grupo'}" > 
							<bean:message key="message.grupo.edit"/>
						  </c:when> 
						  <c:otherwise>
						  	<bean:message key="message.usuario.edit"/>
						  </c:otherwise> 
						</c:choose> 
						
					</div>
				</td>
			</tr>
		</table>
	</div>

</body>
</html>