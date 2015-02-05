<%@ page contentType="text/html;charset=ISO-8859-1" language="java" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/ieci.tld" prefix="ieci"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
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
<link rel="stylesheet" type="text/css" href="include/css/estilos.css"/>

<script language="javascript">

var appBase = '<c:out value="${pageContext.request.contextPath}"/>';
var id = '<bean:write name='userBean' property='id'/>';

function userProperties()
{
	parent.edicion.location.href = appBase + '/user/bd/userProperties.do?id='+id;
}
function userEdit()
{
	parent.edicion.location.href = appBase + '/user/bd/userEdit.do?id='+id;
}
function changeStatus()
{
	document.location.href = appBase + '/user/bd/userMenu.do?id='+id+'&changeStatus=true';
}

</script>
<html:messages id="msg" message="true" bundle="general_errors">
<script>
	window.alert("<bean:write name='msg'/>");
</script>
</html:messages>
</head>
<body>

    <div id="contenedora">
		<div class="cuerpo" style="width:390px; height:100px;">
	   		<div class="cuerporight">
	    		<div class="cuerpomid">
	    			<h1>Usuarios</h1>
	    			<br/>
	    			<div class="cuadro" style="height: 100px;">
	
						<table border class="tableBase" >
						<tr>
							<th>
								Nombre
							</th>
							<th colspan="3"></th>
						</tr>
						<tr>
							<td><b><bean:write name="userBean" property="nombre"/></b></td>
							<td><a href="javascript:userProperties();">Propiedades </a></td>
							<td><a href="javascript:userEdit();">Editar </a></td>
						
								<c:choose>
										<c:when test="${userBean.bloqueado}">
											<td><a href="javascript:changeStatus();">Desbloquear</a> </td> 
										</c:when>
										<c:otherwise>
											<td><a href="javascript:changeStatus();">Bloquear</a> </td> 
										</c:otherwise>
									</c:choose>
							<%-- Ya se hace la validacion en el action 
							<c:choose>
								<c:when test="${sessionScope.user.id == requestScope.mgrDeptId || sessionScope.user.userProfileSuperuser}">
									<c:choose>
										<c:when test="${userBean.bloqueado}">
											<td><a href="javascript:changeStatus();">Desbloquear</a> </td> 
										</c:when>
										<c:otherwise>
											<td><a href="javascript:changeStatus();">Bloquear</a> </td> 
										</c:otherwise>
									</c:choose>
								</c:when>
								<c:otherwise>
									<c:choose>
										<c:when test="${userBean.bloqueado}">
											<td>Desbloquear</td> 
										</c:when>
										<c:otherwise>
											<td>Bloquear</td> 
										</c:otherwise>
									</c:choose>
								</c:otherwise>
							</c:choose>
							--%>
						</tr>
						</table>
						
	       			</div>
	       		</div>  				
     		</div>
	   	</div> 	
		
		<div class="cuerpobt" style="width:390px">
	    	<div class="cuerporightbt">
	      		<div class="cuerpomidbt"></div>
	    	</div>
		</div>
	</div>					
	

</body>
</html>