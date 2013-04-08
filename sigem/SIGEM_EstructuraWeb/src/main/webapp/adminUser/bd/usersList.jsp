<%@ page contentType="text/html;charset=ISO-8859-1" language="java" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/ieci.tld" prefix="ieci"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>

<%@ page import="ieci.tecdoc.sgm.core.admin.web.AutenticacionAdministracion" %>
<%@ page import="ieci.tecdoc.sgm.core.services.gestion_administracion.ConstantesGestionUsuariosAdministracion" %>

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
<script src="include/js/validations.js" type="text/javascript"></script>

<script language="javascript">
var appBase = '<c:out value="${pageContext.request.contextPath}"/>';
var id = '<bean:write name='usersListForm' property='id'/>';
var tipo ='<bean:write name='usersListForm' property='tipo'/>';

function userProperties(id)
{
	parent.edicion.location.href = appBase + '/user/bd/userProperties.do?id='+id;
}
function userEdit(id, mgrDeptId)
{
	if (mgrDeptId == '' )
		parent.edicion.location.href = appBase + '/user/bd/userEdit.do?id='+id;
	else
		parent.edicion.location.href = appBase + '/user/bd/userEdit.do?id='+id + '&mgrDeptId=' + mgrDeptId;
}
function properties(){
	var check = chequearSession('<html:rewrite page="/chequearSession.do"/>');
	if( check == "false" ) {
		if (tipo == 32 )
			parent.edicion.location.href = appBase + '/user/bd/deptProperties.do?id='+id;
		else
			parent.edicion.location.href = appBase + '/user/bd/groupProperties.do?id='+id;
	} else {
		window.parent.redirect('<%=AutenticacionAdministracion.obtenerUrlLogin(request, ConstantesGestionUsuariosAdministracion.APLICACION_ESTRUCTURA_ORGANIZATIVA) %>');	
	}		
}
function edit (){
	var check = chequearSession('<html:rewrite page="/chequearSession.do"/>');
	if( check == "false" ) {
		if (tipo == 32 )
			parent.edicion.location.href = appBase + '/user/bd/deptEdit.do?id='+id;
		else
			parent.edicion.location.href = appBase + '/user/bd/groupEdit.do?id='+id;
	} else {
		window.parent.redirect('<%=AutenticacionAdministracion.obtenerUrlLogin(request, ConstantesGestionUsuariosAdministracion.APLICACION_ESTRUCTURA_ORGANIZATIVA) %>');	
	}	
}
function newItem(id,tipo)
{
	parent.edicion.location.href = appBase + '/user/bd/deptNew.do?idPadre='+id;
}

function newUser(id)
{
	parent.edicion.location.href = appBase + '/user/bd/userNew.do?idPadre='+id;
}

function newDept(id){
	var check = chequearSession('<html:rewrite page="/chequearSession.do"/>');
	if( check == "false" ) {
		parent.edicion.location.href = appBase + '/user/bd/deptNew.do?idPadre='+id;
	} else {
		window.parent.redirect('<%=AutenticacionAdministracion.obtenerUrlLogin(request, ConstantesGestionUsuariosAdministracion.APLICACION_ESTRUCTURA_ORGANIZATIVA) %>');	
	}		
}

function changeStatus(idUser)
{
	document.location.href = appBase + '/user/bd/usersList.do?id='+id+'&tipo='+tipo+'&idUser='+idUser;
}



</script>

</head>
<body bgcolor="#FFFFFF">
<%! int sdfs=0; %>
<c:set scope="session" var="managerId" >
	<c:out value="${usersListForm.managerId}"/>
</c:set>

    <div id="contenedora">
		<div class="cuerpo" style="width:530px; height:120px;">
	   		<div class="cuerporight">
	    		<div class="cuerpomid">
	    			<h1>
	    				<c:choose>
							<c:when test="${usersListForm.tipo == 32}">
								<bean:message key='message.comun.etiqueta.departamento'/>
							</c:when>
							<c:otherwise>
								<bean:message key="message.comun.etiqueta.grupo"/>
							</c:otherwise>
						</c:choose>
						<bean:write name="usersListForm" property="nombre"/>
	    			</h1>
	    			<br/>
	    			<div class="cuadro" style="height: 120px;">
	    			
	    				<a class="menu0" href="javascript:properties();"><bean:message key="message.comun.etiqueta.propiedades"/> </a>
						<a class="menu0" href="javascript:edit();"><bean:message key="message.comun.etiqueta.editar"/> </a>    
						<c:if test="${usersListForm.tipo == 32}"> <%-- Muestra la opcion de Nuevo usuario, solo si es departamento --%>		
							<a class="menu0" href="javascript:newDept('<bean:write name='usersListForm' property='id'/>');"><bean:message key="message.departamento.etiqueta.nuevodepartamento"/></a>   
						</c:if>
						
						<div id="lista" style="position:absolute; left:10px; top:75px; width:500px; height:75px; z-index:1; border: 1px none #000000;">
						  <iframe name="lista" src='<html:rewrite page="/adminUser/bd/userTable.jsp"/>' frameborder="no" width="500px" height="75px;" scrolling="yes" >
						  
						  </iframe>
						</div>
						 	       		
	       			</div>
	       		</div>  				
     		</div>
	   	</div> 	
		

	</div>

		<div class="cuerpobt" style="width:530px">
	    	<div class="cuerporightbt">
	      		<div class="cuerpomidbt"></div>
	    	</div>
		</div>


<!-- 
<c:set scope="session" var="managerId" >
	<c:out value="${usersListForm.managerId}"/>
</c:set>


		<div class="encabezado1">
		<c:choose>
			<c:when test="${usersListForm.tipo == 32}">
				<c:out value="Departamento: " />
			</c:when>
			<c:otherwise>
				<c:out value="Grupo: " />
			</c:otherwise>
		</c:choose>
		<bean:write name="usersListForm" property="nombre"/>
		</div>
		<a href="javascript:properties();">Propiedades </a>
		<a href="javascript:edit();">Editar </a>
		
		<c:choose>
			<c:when test="${usersListForm.tipo == 32}">
				<td>

					<a href="javascript:newItem('<bean:write name='usersListForm' property='id'/>', '<bean:write name='usersListForm' property='tipo'/>');">Nuevo Departamento</a>
					<%-- Se realiza la validacion en el Action 
					<c:choose>
						<c:when test="${sessionScope.user.id == usersListForm.managerId || sessionScope.user.userProfileSuperuser}">
							<a href="javascript:newItem('<bean:write name='usersListForm' property='id'/>', '<bean:write name='usersListForm' property='tipo'/>');">Nuevo Departamento</a>
						</c:when>
						<c:otherwise>
							Nuevo Departamento
						</c:otherwise>
					</c:choose>
					--%>
				</td>
			</c:when>
		</c:choose>

-->

<!-- Manager Id: <c:out value="${usersListForm.managerId}"/> -->

	

	

</body>
</html>
